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
 
 
package net.datenwerke.gf.client.uiutils.date;

import java.text.ParseException;
import java.util.Date;

import com.google.gwt.i18n.shared.DateTimeFormat;
import com.google.gwt.i18n.shared.DateTimeFormat.PredefinedFormat;
import com.sencha.gxt.widget.core.client.form.PropertyEditor;

public class DateFormulaPickerEditor extends PropertyEditor<DateFormulaContainer>{

	protected DateTimeFormat format = DateTimeFormat.getFormat(PredefinedFormat.DATE_SHORT);
	protected boolean parseStrict = true;
	
	public DateFormulaPickerEditor(){
	}
	
	public DateFormulaPickerEditor(DateTimeFormat format) {
		if(null != format)
			this.format = format;
	}

	@Override
	public String render(DateFormulaContainer object) {
		if(null == object)
			return null;
		if(null != object.getFormula())
			return object.getFormula();
		if(null != object.getDate())
			return format.format(object.getDate());
		return null;
	}

	@Override
	public DateFormulaContainer parse(CharSequence text) throws ParseException {
		if(null == text)
			return null;
		String trimmed = text.toString().trim();
		if(trimmed.startsWith("${"))
			return new DateFormulaContainer(null, trimmed);
		
		try{
			Date date = format.parseStrict(trimmed);
			return new DateFormulaContainer(date, null);
		} catch (Exception ex) {
		      throw new ParseException(ex.getMessage(), 0);
	    }
	}

	public String format(Date value) {
		return format.format(value);
	}
	
}