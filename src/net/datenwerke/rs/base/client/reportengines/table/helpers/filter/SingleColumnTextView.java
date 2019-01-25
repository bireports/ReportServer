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
 
 
package net.datenwerke.rs.base.client.reportengines.table.helpers.filter;

import java.util.ArrayList;
import java.util.List;

import net.datenwerke.gxtdto.client.model.StringBaseModel;
import net.datenwerke.gxtdto.client.utils.SqlTypes;
import net.datenwerke.gxtdto.client.utils.handlers.GenericStoreHandler;
import net.datenwerke.rs.base.client.reportengines.table.dto.ColumnDto;
import net.datenwerke.rs.base.client.reportengines.table.helpers.validator.IntegerFieldValidator;
import net.datenwerke.rs.base.client.reportengines.table.helpers.validator.NumericalFieldValidator;
import net.datenwerke.rs.base.client.reportengines.table.helpers.validator.SqlDateValidator;
import net.datenwerke.rs.base.client.reportengines.table.helpers.validator.SqlTimestampValidator;

import com.google.gwt.editor.client.EditorError;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.dnd.core.client.DndDropEvent;
import com.sencha.gxt.widget.core.client.TabPanel;
import com.sencha.gxt.widget.core.client.form.Validator;


/**
 * 
 *
 */
public class SingleColumnTextView extends TextView<StringBaseModel> {
	

	public SingleColumnTextView(final ListStore<StringBaseModel> store, ColumnDto column, SelectionPanel<StringBaseModel> selectionPanel, TabPanel tabPanel) {
		super(store, selectionPanel, column, tabPanel);
		
		store.addStoreHandlers(new GenericStoreHandler<StringBaseModel>(){
			@Override
			protected void handleDataChangeEvent() {
				StringBuffer buf = new StringBuffer();
				for(StringBaseModel v : store.getAll())
					buf.append(filterService.getStringValue(v.getValue(), SingleColumnTextView.this.column.getType())).append("\r\n"); //$NON-NLS-1$
				textArea.setValue(buf.toString());
			}
		});
	}

	@Override
	protected List<StringBaseModel> tryParseText() throws RuntimeException{
		ArrayList<StringBaseModel> dtos = new ArrayList<StringBaseModel>();
		String s = textArea.getValue();
		
		Validator<String> validator = null;
		if(SqlTypes.isInteger(this.column.getType()))
			validator = new IntegerFieldValidator();
		else if(SqlTypes.isNumerical(column.getType()))
			validator = new NumericalFieldValidator();
		else if(SqlTypes.TIMESTAMP == column.getType())
			validator = new SqlTimestampValidator();
		else if(SqlTypes.DATE == this.column.getType())
			validator = new SqlDateValidator();
		
		if(null != s){
			String[] lines = s.split("\r?\n\r?"); //$NON-NLS-1$
			for(String line : lines){
				if("".equals(line))
					continue;
				
				if(null != validator){
					List<EditorError> err = validator.validate(null, line);
					if(null != err && ! err.isEmpty())
						throw new RuntimeException(err.get(0).getMessage());
				}
				
				StringBaseModel dto = new StringBaseModel();
				dto.setValue(line);
				dtos.add(dto);
			}
		}
		return dtos;
	}

	@Override
	protected void handleDroppedData(List<Object> models, DndDropEvent e) {
    	for(Object model : models)
    		if(model instanceof StringBaseModel)
    			store.add((StringBaseModel) model);
	}	
	
}
