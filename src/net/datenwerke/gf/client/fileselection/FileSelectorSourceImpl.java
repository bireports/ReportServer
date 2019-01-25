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
import net.datenwerke.gxtdto.client.resources.BaseResources;
import net.datenwerke.rs.theme.client.icon.BaseIcon;

import com.google.gwt.resources.client.ImageResource;
import com.sencha.gxt.widget.core.client.grid.Grid;
import com.sencha.gxt.widget.core.client.toolbar.ToolBar;

public abstract class FileSelectorSourceImpl implements FileSelectorSource {

	protected FileSelectionWidget fileSelectionWidget;

	@Override
	public void configureToolbar(FileSelectionWidget fileSelectionWidget,
			ToolBar toolbar) {
	}

	@Override
	public void configureGrid(FileSelectionWidget fileSelectionWidget,
			Grid<SelectedFileWrapper> grid) {
	}

	@Override
	public void init(FileSelectionWidget fileSelectionWidget) {
		this.fileSelectionWidget = fileSelectionWidget;
	}

	@Override
	public boolean consumes(SelectedFileWrapper value) {
		return false;
	}

	@Override
	public ImageResource getIconFor(SelectedFileWrapper value) {
		return BaseIcon.FILE_O.toImageResource();
	}

	@Override
	public boolean isEditNameEnabled(SelectedFileWrapper selectedItem) {
		return true;
	}

	@Override
	public boolean isDownloadEnabled(SelectedFileWrapper item) {
		return true;
	}
	
	@Override
	public DownloadProperties getDownloadPropertiesFor(
			SelectedFileWrapper selectedItem) {
		return null;
	}
	
	@Override
	public String getTypeDescription(SelectedFileWrapper value) {
		return "";
	}
	
	@Override
	public boolean isValid() {
		return true;
	}

}
