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
 
 
package net.datenwerke.gf.client.homepage.ui;


import java.util.Collection;

import net.datenwerke.gf.client.homepage.modules.ClientTempModule;
import net.datenwerke.gf.client.homepage.modules.ui.ModuleManagerModuleSelector;
import net.datenwerke.gf.client.homepage.modules.ui.ModuleManagerPanel;
import net.datenwerke.gf.client.theme.ThemeUiService;
import net.datenwerke.gxtdto.client.locale.BaseMessages;
import net.datenwerke.gxtdto.client.statusbar.StatusBarUIService;
import net.datenwerke.gxtdto.client.theme.CssClassConstant;

import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer.VerticalLayoutData;
import com.sencha.gxt.widget.core.client.container.Viewport;

/**
 * This {@link LayoutContainer} divides the screen in two parts. 
 * Header and main
 * 
 *
 */
@Singleton
public class DwMainViewport extends Viewport {

	@CssClassConstant
	public static final String CSS_NAME = "rs-viewport";
	
	final private HeaderPanel header;
	final private ModuleManagerPanel mainPanel;
	final private StatusBarUIService statusBarService;
	final private ThemeUiService themeService;
	
	private final ModuleManagerModuleSelector moduleSelector;
	
	@Inject
	public DwMainViewport(
		HeaderPanel header, 
		ModuleManagerPanel mainPanel,
		StatusBarUIService statusBarService,
		ThemeUiService themeService,
		ModuleManagerModuleSelector moduleSelector
	) {

		this.header = header;
		this.mainPanel = mainPanel;
		this.statusBarService = statusBarService;
		this.themeService = themeService;
		this.moduleSelector = moduleSelector;

		moduleSelector.setViewport(this);
		
		initializeUI();
		
		getElement().addClassName(getCssName());
	}
		
	public String getCssName() {
		return CSS_NAME;
	}

	private void initializeUI() {
		
		VerticalLayoutContainer container = new VerticalLayoutContainer();
		
		container.add(header, new VerticalLayoutData(1, themeService.getThemeConfig().getHeaderHeight()));
		container.add(mainPanel, new VerticalLayoutData(1, 1));
		container.add(statusBarService.getStatusBarWidget(), new VerticalLayoutData(1, -1));
		
		statusBarService.setContainer(this);
		
		add(container);
	}
	
	public Widget getHeader() {
		return header;
	}

	public void addTempModule(final ClientTempModule module){
		moduleSelector.addTempModule(module);
	}
	

	public void removeTempModule(ClientTempModule module){
		moduleSelector.removeTempModule(module);
	}
	
	public Collection<ClientTempModule> getTempModules() {
		return moduleSelector.getTempModules();
	}

	public void setLoadingMask(){
		mask(BaseMessages.INSTANCE.loadingMsg());
	}

}
