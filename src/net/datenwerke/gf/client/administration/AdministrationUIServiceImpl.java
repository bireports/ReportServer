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
 
 
package net.datenwerke.gf.client.administration;

import net.datenwerke.gf.client.administration.locale.AdministrationMessages;
import net.datenwerke.gf.client.administration.ui.AdministrationPanel;
import net.datenwerke.gf.client.homepage.modules.ui.ClientModuleSelector;
import net.datenwerke.gxtdto.client.resources.BaseResources;
import net.datenwerke.rs.theme.client.icon.BaseIcon;

import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;

@Singleton
public class AdministrationUIServiceImpl implements AdministrationUIService {
	
	final private Provider<AdministrationPanel> adminPanelProvider;
	
	@Inject
	public AdministrationUIServiceImpl(
		Provider<AdministrationPanel> adminPanel	
		){
		this.adminPanelProvider = adminPanel;
		
	}

	@Override
	public Widget getMainWidget() {
		return adminPanelProvider.get();
	}

	@Override
	public ImageResource getIcon() {
		return BaseIcon.COG.toImageResource();
	}
	
	@Override
	public String getModuleName() {
		return AdministrationMessages.INSTANCE.administration();
	}

	@Override
	public void selected() {
	}

	@Override
	public void setClientModuleSelector(
			ClientModuleSelector moduleManagerModuleSelector) {
		// we do not care
	}
	
	@Override
	public String getToolTip() {
		return null;
	}
	
	@Override
	public boolean isRecyclable() {
		return true;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (isRecyclable() ? 1231 : 1237);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		return isRecyclable();
	}


}
