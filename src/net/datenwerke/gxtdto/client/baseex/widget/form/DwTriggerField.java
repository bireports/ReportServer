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

import net.datenwerke.rs.theme.client.field.RsTriggerFieldAppearance;
import net.datenwerke.rs.theme.client.icon.BaseIcon;

import com.sencha.gxt.cell.core.client.form.TriggerFieldCell;
import com.sencha.gxt.widget.core.client.form.PropertyEditor;
import com.sencha.gxt.widget.core.client.form.TriggerField;

public class DwTriggerField<D> extends TriggerField<D> {

	public DwTriggerField(TriggerFieldCell<D> cell) {
		super(cell);
	}
	
	public DwTriggerField(TriggerFieldCell<D> cell,
			PropertyEditor<D> propertyEditor) {
		super(cell, propertyEditor);
	}
	
	public void setTriggerIcon(BaseIcon icon) {
		((RsTriggerFieldAppearance)getCell().getAppearance()).setTriggerIcon(icon);
	}

}
