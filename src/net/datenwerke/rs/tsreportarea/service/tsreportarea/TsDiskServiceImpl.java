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
 
 
package net.datenwerke.rs.tsreportarea.service.tsreportarea;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.NoResultException;

import org.hibernate.proxy.HibernateProxy;

import com.google.inject.Inject;
import com.google.inject.name.Named;

import net.datenwerke.rs.core.service.reportmanager.ReportService;
import net.datenwerke.rs.core.service.reportmanager.entities.reports.Report;
import net.datenwerke.rs.core.service.reportmanager.interfaces.ReportVariant;
import net.datenwerke.rs.teamspace.service.teamspace.entities.TeamSpace;
import net.datenwerke.rs.tsreportarea.service.tsreportarea.entities.AbstractTsDiskNode;
import net.datenwerke.rs.tsreportarea.service.tsreportarea.entities.TsDiskFolder;
import net.datenwerke.rs.tsreportarea.service.tsreportarea.entities.TsDiskReportReference;
import net.datenwerke.rs.tsreportarea.service.tsreportarea.entities.TsDiskReportReference__;
import net.datenwerke.rs.tsreportarea.service.tsreportarea.entities.TsDiskRoot;
import net.datenwerke.rs.tsreportarea.service.tsreportarea.entities.TsDiskRoot__;
import net.datenwerke.rs.utils.simplequery.PredicateType;
import net.datenwerke.rs.utils.simplequery.annotations.Predicate;
import net.datenwerke.rs.utils.simplequery.annotations.QueryByAttribute;
import net.datenwerke.rs.utils.simplequery.annotations.QueryById;
import net.datenwerke.rs.utils.simplequery.annotations.SimpleQuery;
import net.datenwerke.security.service.eventlogger.annotations.FireForceRemoveEntityEvents;
import net.datenwerke.security.service.eventlogger.annotations.FireRemoveEntityEvents;
import net.datenwerke.security.service.treedb.LoggedTreeDbManagerImpl;


/**
 * 
 *
 */
public class TsDiskServiceImpl extends LoggedTreeDbManagerImpl<AbstractTsDiskNode> implements TsDiskService {

	private final ReportService reportService;
	
	/**
	 * 
	 */
	@Inject
	public TsDiskServiceImpl(
		ReportService reportService
		){
		
		/* store objects */
		this.reportService = reportService;
	}
	
	@Override
	@QueryById
	public AbstractTsDiskNode getNodeById(@Named("id")long id) {
		return null; // by magic
	}
	
	@Override
	@SimpleQuery
	public List<AbstractTsDiskNode> getAllNodes(){
		return null;
	}

	@Override
	@FireForceRemoveEntityEvents
	public void forceRemove(AbstractTsDiskNode node) {
		doPreRemove(node, true);
		super.forceRemove(node);
	}

	@Override
	@FireRemoveEntityEvents
	public void remove(AbstractTsDiskNode node) {
		doPreRemove(node, false);
		super.remove(node);
	}
	
	protected void doPreRemove(AbstractTsDiskNode node, boolean force) {
		if(node instanceof TsDiskReportReference){
			TsDiskReportReference reference = (TsDiskReportReference) node;
			
			Report report = reference.getReport();
			if(null != report && report instanceof ReportVariant){
				List<TsDiskReportReference> references = getReferencesTo(report);
				if(null != references && 1 == references.size() && reference.equals(references.get(0)) && ! report.isWriteProtected()){
					/* remove report from node */
					reference.setReport(null);
					merge(reference);
					
					if(force)
						reportService.forceRemove(report);
					else
						reportService.remove(report);
				}
			}
		}  
	}

	@Override
	@QueryByAttribute(where=TsDiskRoot__.parent,type=PredicateType.IS_NULL)
	public List<AbstractTsDiskNode> getRoots() {
		return null; // by magic
	}

	@Override
	@SimpleQuery(where={@Predicate(attribute=TsDiskRoot__.parent,type=PredicateType.IS_NULL),
						@Predicate(attribute=TsDiskRoot__.teamSpace,value="ts")},
						throwNoResultException=true)
	public TsDiskRoot getRoot(@Named("ts") TeamSpace teamSpace) {
		return null; // by magic
	}

	@Override
	public TsDiskRoot createRoot(TeamSpace teamSpace) {
		try{
			TsDiskRoot root = getRoot(teamSpace);
			if(null != root)
				return root;
		} catch(NoResultException e){
		}
		TsDiskRoot root = new TsDiskRoot();
		root.setTeamSpace(teamSpace);
		persist(root);
		
		return root;
	}

	@Override
	public List<TsDiskReportReference> getReferencesFor(TeamSpace teamSpace) {
		List<TsDiskReportReference> references = new ArrayList<TsDiskReportReference>();
		if(null == teamSpace)
			return references;
		
		TsDiskRoot root = getRoot(teamSpace);
		addReferences(root, references);
		
		return references;
	}
	
	@Override
	public List<TsDiskReportReference> getReferencesIn(AbstractTsDiskNode folder) {
		List<TsDiskReportReference> references = new ArrayList<TsDiskReportReference>();
		
		addReferences(folder, references);
		
		return references;
	}

	private void addReferences(AbstractTsDiskNode parent,List<TsDiskReportReference> references) {
		for(AbstractTsDiskNode child : parent.getChildren()){
			if(child instanceof TsDiskReportReference)
				references.add((TsDiskReportReference) child);
			else if(child instanceof TsDiskFolder)
				addReferences(child, references);
		}
	}

	@Override
	public TsDiskRoot getRootFor(AbstractTsDiskNode node){
		if(node instanceof HibernateProxy)
			node = (AbstractTsDiskNode) ((HibernateProxy)node).getHibernateLazyInitializer().getImplementation();
		if(null == node)
			return null;
		
		if(node instanceof TsDiskRoot)
			return ((TsDiskRoot)node);
		return getRootFor(node.getParent());
	}
	
	@Override
	public TeamSpace getTeamSpaceFor(AbstractTsDiskNode node) {
		if(null == node)
			return null;
		
		TsDiskRoot root = getRootFor(node);
		if(null == root)
			throw new IllegalStateException("A node should always have a root object." + node.getId());
		return root.getTeamSpace();
	}

	@Override
	public Set<TeamSpace> getTeamSpacesThatLinkTo(Report report) {
		List<TsDiskReportReference> references = getReferencesTo(report);
		
		Set<TeamSpace> ts = new HashSet<TeamSpace>();
		for(TsDiskReportReference ref : references)
			ts.add(getTeamSpaceFor(ref));
		
		return ts;
	}
	
	
	@Override
	@QueryByAttribute(where=TsDiskReportReference__.report)
	public List<TsDiskReportReference> getReferencesTo(Report report) {
		return null; //magic
	}

	@Override
	public TsDiskReportReference importReport(Report report, AbstractTsDiskNode parent, boolean copyReport, boolean isReference) {
		/* create variant */
		if(copyReport && report instanceof ReportVariant){
			Report variant = report.createNewVariant(report);
			variant.setName(report.getName());
			variant.setDescription(report.getDescription());
			reportService.persist(variant);
			
			/* "copy" to report */
			report = variant;
		}
		
		/* create reference */
		TsDiskReportReference reference = new TsDiskReportReference();
		reference.setName(report.getName());
		reference.setDescription(report.getDescription());
		reference.setReport(report);
		reference.setHardlink(report instanceof ReportVariant && ! isReference);
		parent.addChild(reference);
		
		/* persist reference */
		persist(reference);
		
		return reference;
	}

}
