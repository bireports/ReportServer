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
 
 
package net.datenwerke.rs.fileserver.client.fileserver;

import net.datenwerke.gf.client.administration.interfaces.AdminModule;
import net.datenwerke.gxtdto.client.resources.BaseResources;
import net.datenwerke.rs.fileserver.client.fileserver.locale.FileServerMessages;
import net.datenwerke.rs.fileserver.client.fileserver.ui.FileServerManagerPanel;
import net.datenwerke.rs.theme.client.icon.BaseIcon;

import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.inject.Provider;

/**
 * 
 *
 */
public class FileServerAdminModule implements AdminModule {

	final private Provider<FileServerManagerPanel> managerPanelanelProvider;
	
	@Inject
	public FileServerAdminModule(
		Provider<FileServerManagerPanel> managerPanelanelProvider
		){
		
		/* store objects */
		this.managerPanelanelProvider = managerPanelanelProvider;
	}
	
	@Override
	public ImageResource getNavigationIcon(){
		return BaseIcon.FLOPPY_O.toImageResource();
	}
	
	@Override
	public String getNavigationText() {
		return FileServerMessages.INSTANCE.adminLabel();
	}

	@Override
	public Widget getMainWidget() {
		return managerPanelanelProvider.get();
	}
	
	@Override
	public void notifyOfSelection() {
		
	}
}
