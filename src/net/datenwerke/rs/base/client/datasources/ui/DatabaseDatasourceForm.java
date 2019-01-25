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
 
 
package net.datenwerke.rs.base.client.datasources.ui;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.inject.Inject;
import com.sencha.gxt.widget.core.client.menu.Item;
import com.sencha.gxt.widget.core.client.menu.Menu;
import com.sencha.gxt.widget.core.client.menu.MenuItem;

import net.datenwerke.gf.client.managerhelper.mainpanel.SimpleFormView;
import net.datenwerke.gxtdto.client.baseex.widget.menu.DwMenu;
import net.datenwerke.gxtdto.client.baseex.widget.menu.DwMenuItem;
import net.datenwerke.gxtdto.client.forms.simpleform.SimpleForm;
import net.datenwerke.gxtdto.client.forms.simpleform.providers.configs.SFFCPasswordField;
import net.datenwerke.gxtdto.client.forms.simpleform.providers.configs.impl.SFFCStaticDropdownList;
import net.datenwerke.gxtdto.client.forms.simpleform.providers.configs.impl.SFFCTextAreaImpl;
import net.datenwerke.gxtdto.client.locale.BaseMessages;
import net.datenwerke.rs.base.client.datasources.BaseDatasourceUiService;
import net.datenwerke.rs.base.client.datasources.dto.DatabaseDatasourceDto;
import net.datenwerke.rs.base.client.datasources.dto.pa.DatabaseDatasourceDtoPA;
import net.datenwerke.rs.base.client.datasources.locale.BaseDatasourceMessages;
import net.datenwerke.rs.base.client.dbhelper.dto.DatabaseHelperDto;
import net.datenwerke.rs.core.client.datasourcemanager.locale.DatasourcesMessages;

/**
 * 
 *
 */
public class DatabaseDatasourceForm extends SimpleFormView{

	private final BaseDatasourceUiService baseDatasourceService;
	
	@Inject
	public DatabaseDatasourceForm(
			BaseDatasourceUiService baseDatasourceService	
		){
		super();
		
		/* store objects */
		this.baseDatasourceService = baseDatasourceService;
	}
	
	public void configureSimpleForm(SimpleForm form) {
		/* configure form */
		form.setHeadingText(DatasourcesMessages.INSTANCE.editDataSource() + (getSelectedNode() == null ? "" : " (" + getSelectedNode().getId() + ")")); 
		
		/* name name */
		form.addField(String.class, DatabaseDatasourceDtoPA.INSTANCE.name(), BaseMessages.INSTANCE.name()); 
		
		form.addField(String.class, DatabaseDatasourceDtoPA.INSTANCE.description(), BaseMessages.INSTANCE.description(), new SFFCTextAreaImpl());
		
		/* database */
		form.setFieldWidth(250);
		form.addField(
			List.class, DatabaseDatasourceDtoPA.INSTANCE.databaseDescriptor(), DatasourcesMessages.INSTANCE.database(), 
			new SFFCStaticDropdownList<String>() {
				public Map<String, String> getValues() {
					Map<String, String> map = new TreeMap<String, String>();
					
					for(DatabaseHelperDto dbh : baseDatasourceService.getDatabaseHelpers()){
						String name = dbh.getName();
						if(! dbh.isJdbcDriverAvailable())
							name += " " + BaseDatasourceMessages.INSTANCE.jdbcDriverIsNotAvailable();
						map.put(name, dbh.getDescriptor());
					}
					
					return map;
				}
		});
		
		form.setFieldWidth(250);
		form.beginFloatRow();
		
		/* username */
		form.addField(String.class, DatabaseDatasourceDtoPA.INSTANCE.username(), DatasourcesMessages.INSTANCE.username()); 
		
		/* password */
		String passwordKey = form.addField(String.class, DatabaseDatasourceDtoPA.INSTANCE.password(), DatasourcesMessages.INSTANCE.password(), new SFFCPasswordField() {
			@Override
			public Boolean isPasswordSet() {
				return ((DatabaseDatasourceDto)getSelectedNode()).isHasPassword();
			}
		}); //$NON-NLS-1$
		Menu clearPwMenu = new DwMenu();
		MenuItem clearPwItem = new DwMenuItem(DatasourcesMessages.INSTANCE.clearPassword());
		clearPwMenu.add(clearPwItem);
		clearPwItem.addSelectionHandler(new SelectionHandler<Item>() {
			@Override
			public void onSelection(SelectionEvent<Item> event) {
				((DatabaseDatasourceDto)getSelectedNode()).setPassword(null);
			}
		});
		form.addFieldMenu(passwordKey, clearPwMenu);
		
		form.endRow();
		
		/* url */
		form.setFieldWidth(1);
		form.addField(String.class, DatabaseDatasourceDtoPA.INSTANCE.url(), DatasourcesMessages.INSTANCE.url()); 
		

	}
	
}
