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
 
 
package net.datenwerke.rs.saiku.client.datasource;

import net.datenwerke.gf.client.managerhelper.mainpanel.SimpleFormView;
import net.datenwerke.gxtdto.client.baseex.widget.menu.DwMenuItem;
import net.datenwerke.gxtdto.client.codemirror.CodeMirrorPanel.ToolBarEnhancer;
import net.datenwerke.gxtdto.client.forms.simpleform.SimpleForm;
import net.datenwerke.gxtdto.client.forms.simpleform.providers.configs.SFFCCodeMirror;
import net.datenwerke.gxtdto.client.forms.simpleform.providers.configs.SFFCNoHtmlDecode;
import net.datenwerke.gxtdto.client.forms.simpleform.providers.configs.SFFCPasswordField;
import net.datenwerke.gxtdto.client.forms.simpleform.providers.configs.impl.SFFCTextAreaImpl;
import net.datenwerke.gxtdto.client.locale.BaseMessages;
import net.datenwerke.rs.core.client.datasourcemanager.locale.DatasourcesMessages;
import net.datenwerke.rs.saiku.client.datasource.dto.MondrianDatasourceDto;
import net.datenwerke.rs.saiku.client.datasource.dto.pa.MondrianDatasourceDtoPA;
import net.datenwerke.rs.saiku.client.saiku.locale.SaikuMessages;

import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.sencha.gxt.widget.core.client.menu.Item;
import com.sencha.gxt.widget.core.client.menu.Menu;
import net.datenwerke.gxtdto.client.baseex.widget.menu.DwMenu;
import com.sencha.gxt.widget.core.client.menu.MenuItem;

public class MondrianDatasourceForm extends SimpleFormView {

	@Override
	protected void configureSimpleForm(SimpleForm form) {
		/* configure form */
		form.setHeadingText(SaikuMessages.INSTANCE.editDataSource() + (getSelectedNode() == null ? "" : " (" + getSelectedNode().getId() + ")")); 
		
		/* add form fields */
		form.addField(String.class, MondrianDatasourceDtoPA.INSTANCE.name(), BaseMessages.INSTANCE.propertyName()); 
		form.addField(String.class, MondrianDatasourceDtoPA.INSTANCE.description(), BaseMessages.INSTANCE.propertyDescription(), new SFFCTextAreaImpl());
		
		form.setFieldWidth(250);
		form.beginFloatRow();
		
		/* username */
		form.addField(String.class, MondrianDatasourceDtoPA.INSTANCE.username(), DatasourcesMessages.INSTANCE.username()); 
		
		/* password */
		String passwordKey = form.addField(String.class, MondrianDatasourceDtoPA.INSTANCE.password(), DatasourcesMessages.INSTANCE.password(), new SFFCPasswordField() {
			@Override
			public Boolean isPasswordSet() {
				return null;
			}
		}); //$NON-NLS-1$
		Menu clearPwMenu = new DwMenu();
		MenuItem clearPwItem = new DwMenuItem(DatasourcesMessages.INSTANCE.clearPassword());
		clearPwMenu.add(clearPwItem);
		clearPwItem.addSelectionHandler(new SelectionHandler<Item>() {
			@Override
			public void onSelection(SelectionEvent<Item> event) {
				((MondrianDatasourceDto)getSelectedNode()).setPassword(null);
			}
		});
		form.addFieldMenu(passwordKey, clearPwMenu);
		
		form.endRow();
		
		/* url */
		form.setFieldWidth(1);
		form.addField(String.class, MondrianDatasourceDtoPA.INSTANCE.url(), DatasourcesMessages.INSTANCE.url()); 
		
		form.addField(String.class, MondrianDatasourceDtoPA.INSTANCE.properties(), SaikuMessages.INSTANCE.propertyProperties(),  new SFFCCodeMirror() {
			
			@Override
			public int getWidth() {
				return -1;
			}
			
			@Override
			public int getHeight() {
				return 150;
			}
			
			@Override
			public String getLanguage() {
				return "text/x-ini";
			}
			
			@Override
			public boolean lineNumbersVisible() {
				return true;
			}

			@Override
			public ToolBarEnhancer getEnhancer() {
				return null;
			}
		},SFFCNoHtmlDecode.INSTANCE);
		
		
		form.addField(String.class, MondrianDatasourceDtoPA.INSTANCE.mondrianSchema(), SaikuMessages.INSTANCE.propertySchema(), new SFFCCodeMirror() {
			
			@Override
			public int getWidth() {
				return -1;
			}
			
			@Override
			public int getHeight() {
				return 300;
			}

			@Override
			public boolean lineNumbersVisible() {
				return true;
			}
			
			@Override
			public String getLanguage() {
				return "application/xml";
			}
			
			@Override
			public ToolBarEnhancer getEnhancer() {
				return null;
			}
		},SFFCNoHtmlDecode.INSTANCE);
		
	}

}
