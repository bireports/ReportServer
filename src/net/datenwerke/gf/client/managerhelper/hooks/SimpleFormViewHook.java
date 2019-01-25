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
 
 
package net.datenwerke.gf.client.managerhelper.hooks;

import java.util.ArrayList;
import java.util.Collection;

import net.datenwerke.gf.client.managerhelper.mainpanel.SimpleFormView;
import net.datenwerke.gxtdto.client.forms.simpleform.SimpleForm;
import net.datenwerke.hookhandler.shared.hookhandler.interfaces.Hook;
import net.datenwerke.treedb.client.treedb.dto.AbstractNodeDto;

public abstract class SimpleFormViewHook implements Hook {
	
	private Collection<Class<? extends SimpleFormView>>  appliesTo; 
	
	public SimpleFormViewHook(Class<? extends SimpleFormView>... appliesTo) {
		this.appliesTo = new ArrayList<Class<? extends SimpleFormView>>();
		
		for(Class<? extends SimpleFormView> c : appliesTo){
			this.appliesTo.add(c);
		}
	}

	public boolean appliesTo(Class<? extends SimpleFormView> clazz){
		return this.appliesTo.contains(clazz);
	}

	public abstract void configureSimpleForm(SimpleForm form, AbstractNodeDto selectedNode);

}
