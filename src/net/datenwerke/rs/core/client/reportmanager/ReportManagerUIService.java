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
 
 
package net.datenwerke.rs.core.client.reportmanager;

import java.util.List;

import net.datenwerke.rs.core.client.reportmanager.dto.reports.ReportDto;

import com.google.gwt.resources.client.ImageResource;
import com.sencha.gxt.widget.core.client.grid.ColumnConfig;

public interface ReportManagerUIService {

	public abstract List<ColumnConfig<ReportDto,?>> getColumnConfigForReportGrid();

	public List<ColumnConfig<ReportDto,?>> getColumnConfigForReportGrid(boolean includeVariants, boolean icon);
	
	public ImageResource getIconFor(ReportDto report);
	
	public ImageResource getLinkIconFor(ReportDto report);
	
	public ImageResource getLargeIconFor(ReportDto report);
	
	public ImageResource getLargeLinkIconFor(ReportDto report);

	boolean supportsVariants(ReportDto report);
}
