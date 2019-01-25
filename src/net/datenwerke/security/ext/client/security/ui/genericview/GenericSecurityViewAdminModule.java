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
 
 
package net.datenwerke.security.ext.client.security.ui.genericview;

import net.datenwerke.gf.client.administration.interfaces.AdminModule;
import net.datenwerke.gxtdto.client.resources.BaseResources;
import net.datenwerke.rs.theme.client.icon.BaseIcon;
import net.datenwerke.security.ext.client.security.locale.SecurityMessages;

import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.inject.Provider;

public class GenericSecurityViewAdminModule implements AdminModule {
	
	private final Provider<GenericSecurityView> genericSecurityViewProvider;
	
	private GenericSecurityView genericSecurityViewInstance;
	
	
	@Inject
	public GenericSecurityViewAdminModule(
		Provider<GenericSecurityView> genericSecurityViewProvider	
		){
		
		/* store objects */
		this.genericSecurityViewProvider = genericSecurityViewProvider;
	}
	
	@Override
	public Widget getMainWidget() {
		if(null == genericSecurityViewInstance)
			genericSecurityViewInstance = genericSecurityViewProvider.get();
		return genericSecurityViewInstance;
	}

	@Override
	public ImageResource getNavigationIcon() {
		return BaseIcon.LOCK.toImageResource(); 
	}

	@Override
	public String getNavigationText() {
		return SecurityMessages.INSTANCE.genericSecurityViewAdminModuleHeading(); 
	}

	@Override
	public void notifyOfSelection() {
	}
}
