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
 
 
package net.datenwerke.rs.dashboard.service.dashboard;

import java.util.List;

import javax.persistence.EntityManager;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.name.Named;

import net.datenwerke.rs.core.service.reportmanager.entities.reports.Report__;
import net.datenwerke.rs.dashboard.service.dashboard.entities.AbstractDashboardManagerNode;
import net.datenwerke.rs.dashboard.service.dashboard.entities.AbstractDashboardManagerNode__;
import net.datenwerke.rs.dashboard.service.dashboard.entities.Dadget;
import net.datenwerke.rs.dashboard.service.dashboard.entities.Dashboard;
import net.datenwerke.rs.dashboard.service.dashboard.entities.DashboardContainer;
import net.datenwerke.rs.dashboard.service.dashboard.entities.DashboardContainer__;
import net.datenwerke.rs.dashboard.service.dashboard.entities.DashboardNode;
import net.datenwerke.rs.dashboard.service.dashboard.entities.DashboardNode__;
import net.datenwerke.rs.dashboard.service.dashboard.entities.Dashboard__;
import net.datenwerke.rs.dashboard.service.dashboard.entities.UserDashboard;
import net.datenwerke.rs.dashboard.service.dashboard.entities.UserDashboard__;
import net.datenwerke.rs.utils.simplequery.PredicateType;
import net.datenwerke.rs.utils.simplequery.annotations.Join;
import net.datenwerke.rs.utils.simplequery.annotations.Predicate;
import net.datenwerke.rs.utils.simplequery.annotations.QueryByAttribute;
import net.datenwerke.rs.utils.simplequery.annotations.QueryById;
import net.datenwerke.rs.utils.simplequery.annotations.SimpleQuery;
import net.datenwerke.security.service.eventlogger.annotations.FireMergeEntityEvents;
import net.datenwerke.security.service.eventlogger.annotations.FirePersistEntityEvents;
import net.datenwerke.security.service.eventlogger.annotations.FireRemoveEntityEvents;
import net.datenwerke.security.service.usermanager.entities.User;

public class DashboardServiceImpl implements DashboardService{

	private final Provider<EntityManager> entityManagerProvider;

	@Inject
	public DashboardServiceImpl(
		Provider<EntityManager> entityManagerProvider	
		){
		this.entityManagerProvider = entityManagerProvider;
		
	}
	
	@Override
	public DashboardContainer getDashboardFor(User user) {
		if(null == user)
			return null;
		
		UserDashboard ud = getUserDashboard(user);
		if(null == ud)
			ud = createDashboardForUser(user);
		return ud.getDashboardContainer();
	}
	
	@Override
	@QueryByAttribute(where=UserDashboard__.dashboardContainer)
	public UserDashboard getUserDashboardFor(DashboardContainer container) {
		return null; //magic
	}
	
	public UserDashboard createDashboardForUser(User user) {
		if(null == user)
			throw new IllegalArgumentException();
		
		UserDashboard ud = getUserDashboard(user);
		if(null == ud){
			ud = new UserDashboard();
			ud.setDashboardContainer(new DashboardContainer());
			ud.setUser(user);
			persist(ud);
		}
		return ud;
	}
	
	@Override
	@SimpleQuery(from=DashboardContainer.class,join=@Join(joinAttribute=DashboardContainer__.dashboards, where=@Predicate(attribute="",value="db")))
	public DashboardContainer getDashboardContainerFor(@Named("db") Dashboard db) {
		return null; // magic
	}
	
	@Override
	@SimpleQuery(from=Dashboard.class,join=@Join(joinAttribute=Dashboard__.dadgets, where=@Predicate(attribute="",value="dadget")))
	public Dashboard getDashboardFor(@Named("dadget")Dadget dadget) {
		return null; // magic
	}
	
	@Override
	@QueryByAttribute(where=DashboardNode__.dashboard)
	public DashboardNode getNodeFor(Dashboard dashboard) {
		return null;
	}
	
	@Override
	@QueryById(from=Dashboard.class)
	public Dashboard getDashboardById(Long id) {
		return null; //magic
	}
	
	@Override
	public void removeDashboardFor(User user) {
		UserDashboard ud = getUserDashboard(user);
		if(null != ud)
			remove(ud);
	}

	@FireRemoveEntityEvents
	public void remove(UserDashboard ud) {
		if(null == ud)
			return;
		EntityManager em = entityManagerProvider.get();
		ud = em.find(ud.getClass(), ud.getId());
		if(null != ud)
			em.remove(ud);		
	}
	
	@Override
	@FireRemoveEntityEvents
	public void remove(Dashboard dashboard) {
		EntityManager em = entityManagerProvider.get();
		dashboard = em.find(dashboard.getClass(), dashboard.getId());
		if(null != dashboard)
			em.remove(dashboard);		
	}
	
	@Override
	@FireRemoveEntityEvents
	public void remove(Dadget dadget) {
		EntityManager em = entityManagerProvider.get();
		dadget = em.find(dadget.getClass(), dadget.getId());
		if(null != dadget)
			em.remove(dadget);		
	}
	
	@FirePersistEntityEvents
	public void persist(UserDashboard ud) {
		entityManagerProvider.get().persist(ud);
	}

	@Override
	@QueryByAttribute(where=UserDashboard__.user)
	public UserDashboard getUserDashboard(User user) {
		return null; //magic
	}
	
	@Override
	@FireMergeEntityEvents
	public DashboardContainer merge(DashboardContainer dashboardContainer) {
		return entityManagerProvider.get().merge(dashboardContainer);
	}
	
	@Override
	@FireMergeEntityEvents
	public Dashboard merge(Dashboard dashboard) {
		return entityManagerProvider.get().merge(dashboard);
	}
	
	@Override
	@FireMergeEntityEvents
	public Dadget merge(Dadget dadget) {
		return entityManagerProvider.get().merge(dadget);
	}

	@Override
	@FirePersistEntityEvents
	public void persist(Dadget dadget) {
		entityManagerProvider.get().persist(dadget);
	}
	
	@Override
	@FirePersistEntityEvents
	public void persist(Dashboard dashboard) {
		entityManagerProvider.get().persist(dashboard);
	}
}
