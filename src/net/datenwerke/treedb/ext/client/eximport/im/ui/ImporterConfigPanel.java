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
 
 
package net.datenwerke.treedb.ext.client.eximport.im.ui;

import net.datenwerke.gxtdto.client.baseex.widget.DwTabPanel;
import net.datenwerke.rs.eximport.client.eximport.im.dto.ImportConfigDto;
import net.datenwerke.rs.eximport.client.eximport.im.exceptions.NotProperlyConfiguredException;
import net.datenwerke.rs.eximport.client.eximport.locale.ExImportMessages;

import com.google.gwt.core.client.GWT;
import com.sencha.gxt.widget.core.client.TabItemConfig;

/**
 * 
 *
 */

abstract public class ImporterConfigPanel<C extends ImportConfigDto> extends DwTabPanel {

	protected final ImporterItemsPanel<C> itemsPanel;
	protected final ImporterMainPropertiesPanel<C> mainPropertiesPanel;
	
	public ImporterConfigPanel(
		ImporterItemsPanel<C> itemsPanel,
		ImporterMainPropertiesPanel<C> mainPropertiesPanel
		){
		super(GWT.<TabPanelAppearance> create(TabPanelBottomAppearance.class));
		
		/* store objects */
		this.itemsPanel = itemsPanel;
		this.mainPropertiesPanel = mainPropertiesPanel;

		/* init */
		initializeUI();
		
	}

	private void initializeUI() {
		setBodyBorder(false);
		setBorders(false);
		
		TabItemConfig tabMainProperties = new TabItemConfig(ExImportMessages.INSTANCE.importMainProperties());
		add(mainPropertiesPanel, tabMainProperties);
		
		TabItemConfig tabItems = new TabItemConfig(ExImportMessages.INSTANCE.importItems());
		add(itemsPanel, tabItems);
	}

	public ImportConfigDto getConfiguration() throws NotProperlyConfiguredException {
		C config = createConfigObject();
		
		itemsPanel.populateConfig(config);
		mainPropertiesPanel.populateConfig(config);
		
		itemsPanel.validateConfig(config);
		mainPropertiesPanel.validateConfig(config);
		
		return config;
	}
	
	public void resetConfig() {
		itemsPanel.resetConfig();
		mainPropertiesPanel.resetConfig();
	}

	abstract protected C createConfigObject();
	
}
