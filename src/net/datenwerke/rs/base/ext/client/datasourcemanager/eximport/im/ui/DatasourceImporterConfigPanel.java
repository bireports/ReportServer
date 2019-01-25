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
 
 
package net.datenwerke.rs.base.ext.client.datasourcemanager.eximport.im.ui;

import net.datenwerke.rs.base.ext.client.datasourcemanager.eximport.im.dto.DatasourceManagerImportConfigDto;
import net.datenwerke.treedb.ext.client.eximport.im.ui.ImporterConfigPanel;

import com.google.inject.Inject;

public class DatasourceImporterConfigPanel extends ImporterConfigPanel<DatasourceManagerImportConfigDto> {

	@Inject
	public DatasourceImporterConfigPanel(
		DatasourceImporterItemsPanel itemsPanel,
		DatasourceImporterMainPropertiesPanel mainPropertiesPanel) {
		super(itemsPanel, mainPropertiesPanel);
	}

	@Override
	protected DatasourceManagerImportConfigDto createConfigObject() {
		return new DatasourceManagerImportConfigDto();
	}

}
