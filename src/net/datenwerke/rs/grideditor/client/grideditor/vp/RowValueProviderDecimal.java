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
 
 
package net.datenwerke.rs.grideditor.client.grideditor.vp;

import java.math.BigDecimal;

import net.datenwerke.rs.grideditor.client.grideditor.dto.GridEditorRecordDto;
import net.datenwerke.rs.grideditor.client.grideditor.dto.GridEditorRecordEntryDto;

public class RowValueProviderDecimal extends RowValueProvider<BigDecimal> {
	
	public RowValueProviderDecimal(int index) {
		super(index);
	}

	@Override
	public BigDecimal getValue(GridEditorRecordDto object) {
		GridEditorRecordEntryDto entry = object.getData().get(index);
		if(entry.isEntryNull())
			return null;
		
		return entry.getDecimalValue();
	}

	@Override
	public void setValue(GridEditorRecordDto object, BigDecimal value) {
		GridEditorRecordEntryDto entry = object.getData().get(index);
		if(null == value)
			entry.setEntryNull(true);
		else {
			entry.setEntryNull(false);
			entry.setDecimalValue(value);
		}
	}

}