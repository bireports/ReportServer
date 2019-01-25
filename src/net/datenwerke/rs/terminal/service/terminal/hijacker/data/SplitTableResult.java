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
 
 
package net.datenwerke.rs.terminal.service.terminal.hijacker.data;

import java.util.List;

import net.datenwerke.rs.base.service.reportengines.table.output.object.RSStringTableRow;
import net.datenwerke.rs.base.service.reportengines.table.output.object.RSTableModel;
import net.datenwerke.rs.base.service.reportengines.table.output.object.TableDefinition;
import net.datenwerke.rs.terminal.service.terminal.obj.CommandResult;
import net.datenwerke.rs.terminal.service.terminal.obj.PressKeyResultModifier;

public class SplitTableResult extends CommandResult {

	private List<List<String>> data;
	private int perView;
	private TableDefinition td;

	private int offset = 0;
	private boolean finished = false;
	
	public SplitTableResult(TableDefinition td, List<List<String>> data, int perView){
		this.td = td;
		this.data = data;
		this.perView = perView;
	}

	public CommandResult getNext() {
		RSTableModel table = new RSTableModel();
		table.setTableDefinition(td);
		
		int nrOfRows = Math.min(data.size() - offset, perView);
		for(int i = 0; i < nrOfRows; i++)
			table.addDataRow(new RSStringTableRow(data.get(i + offset)));
		offset += perView;
		
		CommandResult result = new CommandResult(table);
		result.addModifier(PressKeyResultModifier.class);
		
		if(offset >= data.size()){
			finished = true;
		} else {
			result.addResultLine("press enter for more results");
		}
		
		
		return result;
	}

	public boolean hasNext() {
		return ! finished;
	}
}
