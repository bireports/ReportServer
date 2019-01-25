/*
 *  ReportServer
 *  Copyright (c) 2018 InfoFabrik GmbH
 *  http://reportserver.net/
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
 
 
package net.datenwerke.rs.compiledreportstore;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.List;

import javax.persistence.EntityManager;

import net.datenwerke.rs.compiledreportstore.entities.PersistentCompiledReport;
import net.datenwerke.rs.compiledreportstore.entities.PersistentCompiledReport__;
import net.datenwerke.rs.core.service.reportmanager.engine.CompiledReport;
import net.datenwerke.rs.core.service.reportmanager.entities.reports.Report;
import net.datenwerke.rs.utils.simplequery.annotations.QueryByAttribute;
import net.datenwerke.rs.utils.simplequery.annotations.QueryById;
import net.datenwerke.security.service.eventlogger.annotations.FireMergeEntityEvents;
import net.datenwerke.security.service.eventlogger.annotations.FirePersistEntityEvents;
import net.datenwerke.security.service.eventlogger.annotations.FireRemoveEntityEvents;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.name.Named;

public class CompiledReportStoreServiceImpl implements CompiledReportStoreService {

	private final Provider<EntityManager> entityManagerProvider;
	
	@Inject
	public CompiledReportStoreServiceImpl(
		Provider<EntityManager> entityManagerProvider
		){
		
		/* store objects */
		this.entityManagerProvider = entityManagerProvider;
	}
	
	@QueryById
	@Override
	public PersistentCompiledReport getById(Long id) {
		return null; // magic
	}
	
	@Override
	@QueryByAttribute(where=PersistentCompiledReport__.report)
	public List<PersistentCompiledReport> getCompiledReportsFor(@Named("report") Report report) {
		return null; // by magic
	}
	
	@Override
	public PersistentCompiledReport toPersistenReport(CompiledReport compiledReport, Report report){
		PersistentCompiledReport cReport = new PersistentCompiledReport();
		cReport.setReport(report);
		
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		ObjectOutputStream oOut;
		try {
			oOut = new ObjectOutputStream(out);
			oOut.writeObject(compiledReport);
			cReport.setSerializedReport(out.toByteArray());
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		
		return cReport;
	}
	
	@Override
	@FirePersistEntityEvents
	public void persist(PersistentCompiledReport cReport) {
		entityManagerProvider.get().persist(cReport);
	}

	@Override
	@FireMergeEntityEvents
	public PersistentCompiledReport merge(PersistentCompiledReport cReport) {
		cReport = entityManagerProvider.get().merge(cReport);
		return cReport;
	}

	@Override
	@FireRemoveEntityEvents
	public void remove(PersistentCompiledReport cReport) {
		EntityManager em = entityManagerProvider.get();
		cReport = em.find(PersistentCompiledReport.class, cReport.getId());
		if(null != cReport)
			em.remove(cReport);
	}

	@Override
	public void removeForReport(Report report) {
		if(null != report)
			for(PersistentCompiledReport cReport : getCompiledReportsFor(report))
				remove(cReport);
	}
	
	@Override
	public void unsetForReport(Report report) {
		if(null != report)
			for(PersistentCompiledReport cReport : getCompiledReportsFor(report)){
				cReport.setReport(null);
				merge(cReport);
			}
	}

}
