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
 
 
package net.datenwerke.rs.globalconstants.client.globalconstants;

import net.datenwerke.gf.client.administration.interfaces.AdminModule;
import net.datenwerke.gxtdto.client.resources.BaseResources;
import net.datenwerke.rs.globalconstants.client.globalconstants.locale.GlobalConstantsMessages;
import net.datenwerke.rs.globalconstants.client.globalconstants.ui.GlobalConstantsAdminPanel;
import net.datenwerke.rs.theme.client.icon.BaseIcon;

import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.inject.Provider;

/**
 * 
 *
 */
public class GlobalConstantsAdminModule implements AdminModule {

	private final Provider<GlobalConstantsAdminPanel> mainWidgetProvider;
	
	@Inject
	public GlobalConstantsAdminModule(Provider<GlobalConstantsAdminPanel> mainWidgetProvider){
		this.mainWidgetProvider = mainWidgetProvider;
	}
	
	@Override
	public Widget getMainWidget() {
		return mainWidgetProvider.get();
	}

	@Override
	public ImageResource getNavigationIcon() {
		return BaseIcon.EDIT.toImageResource();
	}

	@Override
	public String getNavigationText() {
		return GlobalConstantsMessages.INSTANCE.viewNavigationTitle();
	}

	@Override
	public void notifyOfSelection() {
		mainWidgetProvider.get().notifyOfSelection();
	}
}
