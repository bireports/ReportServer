/*
 *  ReportServer
 *  Copyright (c) 2016 datenwerke Jan Albrecht
 *  http://reportserver.datenwerke.net
 *
 *
 * This file is part of ReportServer.
 *
 * ReportServer is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
 
 
package net.datenwerke.rs.scheduler.service.scheduler.jobs.filter;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import net.datenwerke.dtoservices.dtogenerator.annotations.ExposeToClient;
import net.datenwerke.dtoservices.dtogenerator.annotations.GenerateDto;
import net.datenwerke.rs.core.service.reportmanager.entities.reports.Report;
import net.datenwerke.rs.core.service.reportmanager.entities.reports.Report__;
import net.datenwerke.rs.scheduler.service.scheduler.jobs.report.ReportExecuteJob;
import net.datenwerke.rs.scheduler.service.scheduler.jobs.report.ReportExecuteJob__;
import net.datenwerke.scheduler.service.scheduler.entities.AbstractJob;
import net.datenwerke.scheduler.service.scheduler.stores.jpa.filter.JobFilterConfiguration;
import net.datenwerke.security.service.authenticator.AuthenticatorService;
import net.datenwerke.security.service.usermanager.entities.User;
import net.datenwerke.security.service.usermanager.entities.User__;

import com.google.inject.Inject;
import com.google.inject.Provider;

@GenerateDto(
	dtoPackage = "net.datenwerke.rs.scheduler.client.scheduler.dto"
)
public class ReportServerJobFilter extends JobFilterConfiguration {

	@Inject
	static private Provider<AuthenticatorService> authenticatorServiceProvider; 
	
	@ExposeToClient
	private boolean toCurrentUser = false;
	
	@ExposeToClient
	private boolean fromCurrentUser = false;
	
	@ExposeToClient
	private Set<Long> reports = new HashSet<Long>();
	
	@ExposeToClient
	private User toUser = null;
	
	@ExposeToClient
	private User fromUser = null;
	
	
	public void setAnyUser(){
		fromCurrentUser = false;
		toCurrentUser = false;
		toUser = null;
		fromUser = null;
	}
	
	public boolean isAnyUser() {
		return ! fromCurrentUser && ! toCurrentUser &&  null == toUser & null == fromUser;
	}

	public void setToCurrentUser(boolean toCurrentUser) {
		this.toCurrentUser = toCurrentUser;
	}

	public boolean isToCurrentUser() {
		return toCurrentUser;
	}

	public void setFromCurrentUser(boolean fromCurrentUser) {
		this.fromCurrentUser = fromCurrentUser;
	}

	public boolean isFromCurrentUser() {
		return fromCurrentUser;
	}
	
	public Set<Long> getReports() {
		return reports;
	}
	
	public void setReports(Set<Long> reports) {
		this.reports = reports;
	}
	
	public void addReport(Long reportId) {
		this.reports.add(reportId);
	}

	public void addReport(Report report) {
		this.reports.add(report.getId());
	}

	public void setFromUser(User fromUser) {
		this.fromUser = fromUser;
	}

	public User getFromUser() {
		return fromUser;
	}

	public void setToUser(User toUser) {
		this.toUser = toUser;
	}

	public User getToUser() {
		return toUser;
	}
	
	@Override
	public boolean validateSortField(String sortField) {
		if("reportId".equals(sortField))
			return true;
		if("reportName".equals(sortField))
			return true;
		if("ownerId".equals(sortField))
			return true;
		if("ownerLastName".equals(sortField))
			return true;
		
		return super.validateSortField(sortField);
	}

	@Override
	public Expression<?> transformSortField(String sortField, CriteriaBuilder builder, CriteriaQuery<AbstractJob> cQuery, Root<? extends AbstractJob> root) {
		if("reportId".equals(sortField))
			return root.join(ReportExecuteJob__.report, JoinType.LEFT).get(Report__.id);
		if("reportName".equals(sortField))
			return root.join(ReportExecuteJob__.report, JoinType.LEFT).get(Report__.name);
		if("ownerId".equals(sortField))
			return root.join(ReportExecuteJob__.user, JoinType.LEFT).get(User__.id);
		if("ownerLastName".equals(sortField))
			return root.join(ReportExecuteJob__.user, JoinType.LEFT).get(User__.lastname);
		
		return super.transformSortField(sortField, builder, cQuery, root);
	}

	
	@Override
	public List<Predicate> prepareCriteriaQuery(CriteriaBuilder builder,
			CriteriaQuery<?> cQuery, Root<? extends AbstractJob> root) {
		List<Predicate> predicates = super.prepareCriteriaQuery(builder, cQuery, root);
		
		if(fromCurrentUser){
			AuthenticatorService authService = authenticatorServiceProvider.get();
			User currentUser = authService.getCurrentUser();
			
			Join<ReportExecuteJob, User> user = root.join(ReportExecuteJob__.user);
			predicates.add(user.in(currentUser));
		} else if(null != fromUser){
			Join<ReportExecuteJob, User> user = root.join(ReportExecuteJob__.user);
			predicates.add(user.in(fromUser));
		}
		if(toCurrentUser){
			AuthenticatorService authService = authenticatorServiceProvider.get();
			User currentUser = authService.getCurrentUser();
			
			Join<ReportExecuteJob, User> rcpt = root.join(ReportExecuteJob__.rcptIDs);
			predicates.add(rcpt.in(currentUser.getId()));
		} else if(null != toUser){
			Join<ReportExecuteJob, User> rcpt = root.join(ReportExecuteJob__.rcptIDs);
			predicates.add(rcpt.in(toUser.getId()));
		}
		
		if(null != fromUser && fromCurrentUser)
			throw new IllegalArgumentException();
		if(null != toUser && toCurrentUser)
			throw new IllegalArgumentException();
		
		if(null != getReports() && ! getReports().isEmpty())
			predicates.add(root.join(ReportExecuteJob__.report).get(Report__.id).in(getReports()));
		
		return predicates;
	}

}
