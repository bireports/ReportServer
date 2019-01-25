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
 
 
package net.datenwerke.gxtdto.client.forms.simpleform.providers.configs;

import java.util.ArrayList;
import java.util.List;

import net.datenwerke.gxtdto.client.forms.locale.FormsMessages;
import net.datenwerke.gxtdto.client.i18n.I18nToolsUIService;

import com.google.gwt.editor.client.Editor;
import com.google.gwt.editor.client.EditorError;
import com.google.inject.Inject;
import com.sencha.gxt.widget.core.client.form.Validator;
import com.sencha.gxt.widget.core.client.form.error.DefaultEditorError;

public class SFFCStringValidatorBigDecimal implements SFFCStringValidator {
	
	@Inject
	protected static I18nToolsUIService i18nTools;
	private boolean allowBlank = false;
	
	public Validator<String> getValidator() {
		return new Validator<String>() {

			@Override
			public List<EditorError> validate(Editor<String> editor, String value) {
				value = i18nTools.translateNumberFromUserToSystem(value);
				
				if(allowBlank && (null == value || value.isEmpty()))
					return null;
				
				try{
					Double.valueOf(value);
					return null;
				} catch(NumberFormatException e){
					List<EditorError> list = new ArrayList<EditorError>();
					list.add(new DefaultEditorError(editor, FormsMessages.INSTANCE.invalidFloatingpoint(), value));
					return list;
				}
			}
		};
	}

	@Override
	public void setAllowBlank(boolean allowBlank) {
		this.allowBlank = allowBlank;
	}


}
