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
 
 
package net.datenwerke.gf.client.fileselection;

import net.datenwerke.gf.client.download.dto.DownloadProperties;
import net.datenwerke.gf.client.fileselection.dto.SelectedFileWrapper;

import com.google.gwt.resources.client.ImageResource;
import com.sencha.gxt.widget.core.client.grid.Grid;
import com.sencha.gxt.widget.core.client.toolbar.ToolBar;

public interface FileSelectorSource {

	void configureToolbar(FileSelectionWidget fileSelectionWidget,
			ToolBar toolbar);

	void configureGrid(FileSelectionWidget fileSelectionWidget, Grid<SelectedFileWrapper> grid);

	void init(FileSelectionWidget fileSelectionWidget);

	boolean consumes(SelectedFileWrapper value);

	ImageResource getIconFor(SelectedFileWrapper value);

	boolean isEditNameEnabled(SelectedFileWrapper selectedItem);

	boolean isDownloadEnabled(SelectedFileWrapper item);

	DownloadProperties getDownloadPropertiesFor(SelectedFileWrapper selectedItem);

	String getTypeDescription(SelectedFileWrapper value);

	boolean isValid();

}
