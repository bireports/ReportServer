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
 
 
package net.datenwerke.rs.dashboard.client.dashboard.ui.admin.forms;

import net.datenwerke.gf.client.managerhelper.mainpanel.SimpleFormView;
import net.datenwerke.gxtdto.client.forms.simpleform.SimpleForm;
import net.datenwerke.gxtdto.client.forms.simpleform.providers.configs.impl.SFFCTextAreaImpl;
import net.datenwerke.gxtdto.client.locale.BaseMessages;
import net.datenwerke.rs.dashboard.client.dashboard.dto.pa.DashboardFolderDtoPA;
import net.datenwerke.rs.dashboard.client.dashboard.locale.DashboardMessages;

/**
 * 
 *
 */
public class FolderForm extends SimpleFormView {

	@Override
	public void configureSimpleForm(SimpleForm form) {
		form.setHeadingText(DashboardMessages.INSTANCE.editFolder() + (getSelectedNode() == null ? "" : " (" + getSelectedNode().getId() + ")"));
		
		form.addField(String.class, DashboardFolderDtoPA.INSTANCE.name(), BaseMessages.INSTANCE.propertyName()); 
		
		form.addField(String.class, DashboardFolderDtoPA.INSTANCE.description(), BaseMessages.INSTANCE.propertyDescription(), new SFFCTextAreaImpl());
		
	}


	
}