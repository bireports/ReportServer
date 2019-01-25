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
 
 
package net.datenwerke.rs.base.ext.service.reportmanager.eximport;

import net.datenwerke.rs.core.service.reportmanager.entities.AbstractReportManagerNode;
import net.datenwerke.rs.core.service.reportmanager.entities.ReportFolder;
import net.datenwerke.rs.core.service.reportmanager.entities.reports.Report;
import net.datenwerke.treedb.ext.service.eximport.TreeNodeExporter;
import net.datenwerke.treedb.service.treedb.AbstractNode;

public class ReportManagerExporter extends TreeNodeExporter {

	public static final String EXPORTER_ID = "ReportManagerExporter";
	
	@Override
	public String getExporterId() {
		return EXPORTER_ID;
	}
	
	@Override
	protected Class<? extends AbstractNode<?>> getTreeType() {
		return AbstractReportManagerNode.class;
	}
	
	@Override
	protected Class<?>[] getExportableTypes() {
		return new Class<?>[]{Report.class, ReportFolder.class};
	}


}
