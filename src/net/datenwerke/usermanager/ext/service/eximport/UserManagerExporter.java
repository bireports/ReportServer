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
 
 
package net.datenwerke.usermanager.ext.service.eximport;

import net.datenwerke.eximport.obj.ComplexItemProperty;
import net.datenwerke.eximport.obj.ExportedItem;
import net.datenwerke.security.service.usermanager.entities.AbstractUserManagerNode;
import net.datenwerke.security.service.usermanager.entities.Group;
import net.datenwerke.security.service.usermanager.entities.OrganisationalUnit;
import net.datenwerke.security.service.usermanager.entities.User;
import net.datenwerke.treedb.ext.service.eximport.TreeNodeExporter;
import net.datenwerke.treedb.service.treedb.AbstractNode;

public class UserManagerExporter extends TreeNodeExporter {

	private static final String EXPORTER_ID = "UserManagerExporter";
	
	@Override
	public String getExporterId() {
		return EXPORTER_ID;
	}
	
	@Override
	protected Class<? extends AbstractNode<?>> getTreeType() {
		return AbstractUserManagerNode.class;
	}
	
	@Override
	protected Class<?>[] getExportableTypes() {
		return new Class<?>[]{User.class, OrganisationalUnit.class, Group.class};
	}

	@Override
	public String getDisplayNameFor(ExportedItem exportedItem) {

		if(exportedItem.getType().equals(User.class)){
			String name = "";

			ComplexItemProperty firstNameProperty = (ComplexItemProperty) exportedItem.getPropertyByName("firstname");
			if(null != firstNameProperty)
				name += firstNameProperty.getElement().getValue() + " ";

			ComplexItemProperty lastNameProperty = (ComplexItemProperty) exportedItem.getPropertyByName("lastname");
			if(null != lastNameProperty)
				name += lastNameProperty.getElement().getValue();

			return name;
		}else
			return super.getDisplayNameFor(exportedItem);
	}

}
