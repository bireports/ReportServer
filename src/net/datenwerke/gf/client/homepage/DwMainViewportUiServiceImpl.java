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
 
 
package net.datenwerke.gf.client.homepage;

import java.util.Collection;

import net.datenwerke.gf.client.homepage.modules.ClientTempModule;
import net.datenwerke.gf.client.homepage.ui.DwMainViewport;

import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;

/**
 * This implementation of {@link DwMainViewportUiService} configure the 
 * ReportServer default screens setup and handles events raised
 * by components on these screen parts. 
 * 
 *
 */
@Singleton
public class DwMainViewportUiServiceImpl implements DwMainViewportUiService{

	private final Provider<DwMainViewport> viewportProvider;
	private DwMainViewport viewport;

	@Inject
	public DwMainViewportUiServiceImpl(
		Provider<DwMainViewport> DwMainViewport
		) {
		
		this.viewportProvider = DwMainViewport;
	}
	
	/**
	 * Returns the main widget and sets some server calls in motion.
	 */
	@Override
	public Widget getWidget() {
		if(null == viewport)
			viewport = viewportProvider.get();
		return viewport;
	}

	@Override
	public void addTempModule(ClientTempModule module){
		viewport.addTempModule(module);
	}
	
	@Override
	public void removeTempModule(ClientTempModule module){
		viewport.removeTempModule(module);
	}


	@Override
	public Collection<ClientTempModule> getTempModules() {
		return viewport.getTempModules();
	}


	@Override
	public void setLoadingMask() {
		viewport.setLoadingMask();
	}


	@Override
	public void unmask() {
		viewport.unmask();
	}
	
}
