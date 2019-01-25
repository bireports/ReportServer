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
 
 
package net.datenwerke.rs.incubator.client.scriptdatasource.ui;

import net.datenwerke.gf.client.managerhelper.mainpanel.SimpleFormView;
import net.datenwerke.gf.client.treedb.UITree;
import net.datenwerke.gf.client.treedb.simpleform.SFFCGenericTreeNode;
import net.datenwerke.gxtdto.client.forms.simpleform.SimpleForm;
import net.datenwerke.gxtdto.client.forms.simpleform.providers.configs.impl.SFFCTextAreaImpl;
import net.datenwerke.gxtdto.client.locale.BaseMessages;
import net.datenwerke.rs.fileserver.client.fileserver.dto.FileServerFileDto;
import net.datenwerke.rs.fileserver.client.fileserver.provider.annotations.FileServerTreeBasic;
import net.datenwerke.rs.incubator.client.scriptdatasource.dto.pa.ScriptDatasourceDtoPA;
import net.datenwerke.rs.incubator.client.scriptdatasource.locale.ScriptDatasourceMessages;

import com.google.inject.Inject;

/**
 * 
 *
 */
public class ScriptDatasourceForm extends SimpleFormView{

	@Inject @FileServerTreeBasic UITree tree;
	
	@Override
	protected void configureSimpleForm(SimpleForm form) {
		/* configure form */
		form.setHeadingText(ScriptDatasourceMessages.INSTANCE.editDataSource() + (getSelectedNode() == null ? "" : " (" + getSelectedNode().getId() + ")")); 
		
		/* name name */
		form.addField(String.class, ScriptDatasourceDtoPA.INSTANCE.name(), BaseMessages.INSTANCE.propertyName()); 
		
		form.addField(String.class, ScriptDatasourceDtoPA.INSTANCE.description(), BaseMessages.INSTANCE.propertyDescription(), new SFFCTextAreaImpl());
		
		form.setFieldWidth(0.4);
		form.addField(FileServerFileDto.class, ScriptDatasourceDtoPA.INSTANCE.script(), ScriptDatasourceMessages.INSTANCE.scriptLabel(), new SFFCGenericTreeNode(){
			@Override
			public UITree getTreeForPopup() {
				return tree;
			}
		});
		
		form.addField(Boolean.class, ScriptDatasourceDtoPA.INSTANCE.defineAtTarget(), ScriptDatasourceMessages.INSTANCE.defineAtTargetLabel());
		
		form.setFieldWidth(0.4);
		form.addField(Integer.class, ScriptDatasourceDtoPA.INSTANCE.databaseCache(), ScriptDatasourceMessages.INSTANCE.databaseCacheLabel());
		form.setFieldWidth(1);
	}



	
}
