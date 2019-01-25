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
 
 
package net.datenwerke.gxtdto.client.baseex.widget.form;

import net.datenwerke.gxtdto.client.baseex.widget.btn.DwTextButton;

import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.form.FileUploadField;

public class DwFileUploadField extends FileUploadField {

	public DwFileUploadField(){
		super();
		TextButton button = getInputButton(this);
		
		button.getElement().addClassName(DwTextButton.CSS_NAME);
		button.getCell().getAppearance().getButtonElement(getElement()).addClassName(DwTextButton.CSS_BODY_NAME);
	}
	
	/* resort to violator pattern */
	protected native TextButton getInputButton(FileUploadField field) /*-{
	  return field.@com.sencha.gxt.widget.core.client.form.FileUploadField::button;
	}-*/;
}
