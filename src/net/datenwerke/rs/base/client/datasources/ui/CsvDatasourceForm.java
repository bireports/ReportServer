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
 
 
package net.datenwerke.rs.base.client.datasources.ui;

import net.datenwerke.gxtdto.client.forms.simpleform.SimpleForm;
import net.datenwerke.rs.base.client.datasources.dto.pa.CsvDatasourceDtoPA;
import net.datenwerke.rs.base.client.datasources.locale.BaseDatasourceMessages;

/**
 * 
 *
 */
public class CsvDatasourceForm extends FormatBasedDatasourceForm{

	protected void addSpecificFields(SimpleForm form) {
		form.beginRow();
		form.addField(String.class, CsvDatasourceDtoPA.INSTANCE.quote(), BaseDatasourceMessages.INSTANCE.csvQuoteLabel()); 

		form.addField(String.class, CsvDatasourceDtoPA.INSTANCE.separator(), BaseDatasourceMessages.INSTANCE.csvSeparatorLabel());
		form.endRow();
		
		form.setFieldWidth(0.3);
		form.addField(Integer.class, CsvDatasourceDtoPA.INSTANCE.databaseCache(), BaseDatasourceMessages.INSTANCE.csvDatabaseCacheLabel());
		form.setFieldWidth(1);
	}


	
}
