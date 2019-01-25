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
 
 
package net.datenwerke.rs.birt.client.datasources.ui;

import net.datenwerke.gf.client.managerhelper.mainpanel.SimpleFormView;
import net.datenwerke.gxtdto.client.forms.simpleform.SimpleForm;
import net.datenwerke.gxtdto.client.forms.simpleform.providers.configs.impl.SFFCTextAreaImpl;
import net.datenwerke.gxtdto.client.locale.BaseMessages;
import net.datenwerke.rs.base.client.datasources.dto.pa.DatabaseDatasourceDtoPA;
import net.datenwerke.rs.base.client.datasources.locale.BaseDatasourceMessages;
import net.datenwerke.rs.birt.client.datasources.dto.pa.BirtReportDatasourceDefinitionDtoPA;

public class BirtReportDatasourceForm extends SimpleFormView {

	@Override
	protected void configureSimpleForm(SimpleForm form) {
		/* configure form */
		form.setHeadingText(BaseDatasourceMessages.INSTANCE.editDataSource() + (getSelectedNode() == null ? "" : " (" + getSelectedNode().getId() + ")")); 
		
		/* name name */
		form.addField(String.class, DatabaseDatasourceDtoPA.INSTANCE.name(), BaseMessages.INSTANCE.propertyName()); 
		
		form.addField(String.class, DatabaseDatasourceDtoPA.INSTANCE.description(), BaseMessages.INSTANCE.propertyDescription(), new SFFCTextAreaImpl());
		
		form.setFieldWidth(0.3);
		form.addField(Integer.class, BirtReportDatasourceDefinitionDtoPA.INSTANCE.databaseCache(), BaseDatasourceMessages.INSTANCE.csvDatabaseCacheLabel());
		form.setFieldWidth(1);
	}

}
