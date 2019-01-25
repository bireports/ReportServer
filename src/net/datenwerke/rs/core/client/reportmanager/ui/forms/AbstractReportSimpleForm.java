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
 
 
package net.datenwerke.rs.core.client.reportmanager.ui.forms;

import com.sencha.gxt.widget.core.client.container.MarginData;

import net.datenwerke.gf.client.managerhelper.mainpanel.SimpleFormView;
import net.datenwerke.gxtdto.client.forms.simpleform.SimpleForm;
import net.datenwerke.gxtdto.client.forms.simpleform.providers.configs.impl.SFFCTextAreaImpl;
import net.datenwerke.gxtdto.client.locale.BaseMessages;
import net.datenwerke.rs.core.client.datasourcemanager.dto.DatasourceContainerDto;
import net.datenwerke.rs.core.client.datasourcemanager.helper.forms.simpleform.SFFCDatasourceSuppressConfig;
import net.datenwerke.rs.core.client.reportmanager.dto.reports.pa.ReportDtoPA;
import net.datenwerke.rs.core.client.reportmanager.locale.ReportmanagerMessages;

public abstract class AbstractReportSimpleForm extends SimpleFormView {

	@Override
	protected void configureSimpleForm(SimpleForm form){
		form.beginRow();
		
		/* name */
		form.beginColumn(2,new MarginData(0,5,0,0));
		form.addField(String.class, ReportDtoPA.INSTANCE.name(), BaseMessages.INSTANCE.name());
		form.endColumn();
		
		/* key */
		form.beginColumn(1,new MarginData(0,0,0,0));
		form.addField(String.class, ReportDtoPA.INSTANCE.key(), ReportmanagerMessages.INSTANCE.key());
		form.endColumn();
		
		form.endRow();
		
		/* description */
		form.setFieldWidth(1);
		form.addField(String.class, ReportDtoPA.INSTANCE.description(), BaseMessages.INSTANCE.propertyDescription(), new SFFCTextAreaImpl());

		form.setFieldWidth(-1);
		
		/* datasource */
		if(! isDisplayConfigFieldsForDatasource())
			form.addField(DatasourceContainerDto.class, ReportDtoPA.INSTANCE.datasourceContainer(), ReportmanagerMessages.INSTANCE.datasource(), new SFFCDatasourceSuppressConfig(){});
		else 
			form.addField(DatasourceContainerDto.class, ReportDtoPA.INSTANCE.datasourceContainer(), ReportmanagerMessages.INSTANCE.datasource());
	}
	
	protected boolean isDisplayConfigFieldsForDatasource() {
		return true;
	}


}
