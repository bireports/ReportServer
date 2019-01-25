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
 
 
package net.datenwerke.rs.grideditor.client.grideditor.dto.decorator;

import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import net.datenwerke.gxtdto.client.utils.modelkeyprovider.BasicObjectModelKeyProvider;
import net.datenwerke.rs.grideditor.client.grideditor.dto.GridEditorRecordDto;
import net.datenwerke.rs.grideditor.client.grideditor.dto.LongSelectionListEditorDto;

import com.sencha.gxt.cell.core.client.form.ComboBoxCell.TriggerAction;
import com.sencha.gxt.data.shared.Converter;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.data.shared.StringLabelProvider;
import com.sencha.gxt.widget.core.client.form.ComboBox;
import com.sencha.gxt.widget.core.client.form.Field;
import com.sencha.gxt.widget.core.client.grid.ColumnConfig;
import com.sencha.gxt.widget.core.client.grid.editing.GridEditing;

/**
 * Dto Decorator for {@link LongSelectionListEditorDto}
 *
 */
public class LongSelectionListEditorDtoDec extends LongSelectionListEditorDto {


	private static final long serialVersionUID = 1L;

	public LongSelectionListEditorDtoDec() {
		super();
	}

	@Override
	public Field addEditor(ColumnConfig columnConfig,
			GridEditing<GridEditorRecordDto> editing) {
		ListStore<String> store = new ListStore<String>(new BasicObjectModelKeyProvider<String>());
		Map<String, Long> valueMap = getValueMap();
		if(null == valueMap){
			valueMap = new TreeMap<String, Long>();
			for(Long i : getValues())
				valueMap.put(""+i, i);
		}

		store.addAll(valueMap.keySet());
		
		ComboBox<String> combo = new ComboBox<String>(store, new StringLabelProvider<String>());
	    combo.setAllowBlank(true);
	    combo.setForceSelection(isForceSelection());
	    combo.setTriggerAction(TriggerAction.ALL);

	    final Map<String, Long> map = valueMap; 
    	editing.addEditor(columnConfig, new Converter<Long,String>() {

			@Override
			public Long convertFieldValue(String object) {
				if(null == object)
					return null;
				return map.get(object);
			}
			
			@Override
			public String convertModelValue(Long object) {
				if(null == object)
					return null;
				for(Entry<String, Long> e: map.entrySet())
					if(object.equals(e.getValue()))
						return e.getKey();
				return null;
			}
		}, combo);
		
		return combo;
	}
}
