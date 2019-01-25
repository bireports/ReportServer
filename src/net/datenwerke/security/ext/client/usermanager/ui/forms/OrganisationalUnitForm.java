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
 
 
package net.datenwerke.security.ext.client.usermanager.ui.forms;

import net.datenwerke.gf.client.managerhelper.mainpanel.SimpleFormView;
import net.datenwerke.gxtdto.client.forms.simpleform.SimpleForm;
import net.datenwerke.gxtdto.client.forms.simpleform.providers.configs.impl.SFFCTextAreaImpl;
import net.datenwerke.gxtdto.client.locale.BaseMessages;
import net.datenwerke.security.client.usermanager.dto.pa.OrganisationalUnitDtoPA;
import net.datenwerke.security.ext.client.usermanager.locale.UsermanagerMessages;

public class OrganisationalUnitForm extends SimpleFormView {
	
	public void configureSimpleForm(SimpleForm form) {
		/* build form */
		form.setHeadingText(UsermanagerMessages.INSTANCE.editOU() + (getSelectedNode() == null ? "" : " (" + getSelectedNode().getId() + ")"));
		
		/* name name */
		form.addField(String.class, OrganisationalUnitDtoPA.INSTANCE.name(), BaseMessages.INSTANCE.name()); //$NON-NLS-1$
		
		form.addField(String.class, OrganisationalUnitDtoPA.INSTANCE.description(), BaseMessages.INSTANCE.propertyDescription(), new SFFCTextAreaImpl());
	}

	
}
