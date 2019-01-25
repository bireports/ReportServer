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
 
 
package net.datenwerke.rs.core.service.reportmanager;

import java.util.List;
import java.util.Set;

import net.datenwerke.gxtdto.client.servercommunication.exceptions.ExpectedException;
import net.datenwerke.rs.core.service.datasourcemanager.entities.DatasourceDefinition;
import net.datenwerke.rs.core.service.parameters.entities.ParameterDefinition;
import net.datenwerke.rs.core.service.reportmanager.entities.AbstractReportManagerNode;
import net.datenwerke.rs.core.service.reportmanager.entities.reports.Report;
import net.datenwerke.rs.core.service.reportmanager.entities.reports.ReportMetadata;
import net.datenwerke.rs.core.service.reportmanager.entities.reports.ReportProperty;
import net.datenwerke.rs.core.service.reportmanager.interfaces.ReportVariant;
import net.datenwerke.security.service.usermanager.entities.User;
import net.datenwerke.treedb.service.treedb.TreeDBManager;


/**
 * 
 *
 */
public interface ReportService extends TreeDBManager<AbstractReportManagerNode> {

	public static final String SECUREE_ID = "ReportManagerService_Default"; //$NON-NLS-1$
	
	/**
	 * Returns all installed report types.
	 * 
	 * @return
	 */
	public Set<Class<? extends Report>> getInstalledReportTypes();
	
	public List<Report> getAllReports();
	
	public Report getReportById(long id);
	
	public Report getUnmanagedReportById(long id);

	
	public void persist(ReportMetadata reportMetadata);
	
	public void remove(Report report, ReportMetadata reportMetadata);
	
	public void persist(ReportProperty property);

	void remove(Report report, ReportProperty property);

	public List<ReportVariant> getVariantsOf(
			AbstractReportManagerNode realReport);

	public List<ReportVariant> getVariantsOf(AbstractReportManagerNode report, User user);

	public long getReportIdFromKey(String key);

	public List<String> getReportMetadataKeys();

	public Report getReportByKey(String conditionKey);

	List<Report> getReportsByDatasource(DatasourceDefinition ds);

	void updateParameterDefinitions(Report report,
			List<ParameterDefinition> newDefinitions,
			boolean allowParameterRemoval);

	ReportMetadata getOrCreateMetadata(Report report, String name);
	
	ReportMetadata removeMetadataByName(Report report, String name);

	Report getReportByUUID(String UUID);

	public void prepareVariantForStorage(ReportVariant report, String executeToken) throws ExpectedException;

	public List<String> getReportStringPropertyKeys();

	AbstractReportManagerNode getNodeByPath(String path);
	AbstractReportManagerNode getNodeByPath(String path, boolean checkRights);

}