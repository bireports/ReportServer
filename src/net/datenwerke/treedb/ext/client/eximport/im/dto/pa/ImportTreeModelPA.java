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
 
 
package net.datenwerke.treedb.ext.client.eximport.im.dto.pa;

import java.util.List;

import net.datenwerke.treedb.ext.client.eximport.im.dto.ImportTreeModel;

import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.Editor.Path;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;

public interface ImportTreeModelPA extends PropertyAccess<ImportTreeModel> {

	public static ImportTreeModelPA INSTANCE = GWT.create(ImportTreeModelPA.class);
	
	@Path("id")
	public ModelKeyProvider<ImportTreeModel> dtoId();

	/* Properties */
	public ValueProvider<ImportTreeModel,String> id();
	public ValueProvider<ImportTreeModel,String> name();
	public ValueProvider<ImportTreeModel,String> parentId();
	public ValueProvider<ImportTreeModel,String> type();
	
	public ValueProvider<ImportTreeModel,List<ImportTreeModel>> children();
}
