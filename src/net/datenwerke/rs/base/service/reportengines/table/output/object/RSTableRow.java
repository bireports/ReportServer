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
 
 
package net.datenwerke.rs.base.service.reportengines.table.output.object;

import java.io.Serializable;

import net.datenwerke.dtoservices.dtogenerator.annotations.ExposeToClient;
import net.datenwerke.dtoservices.dtogenerator.annotations.GenerateDto;


/**
 * Represents a simple table row
 *
 */
@GenerateDto(
	dtoPackage="net.datenwerke.rs.base.client.reportengines.table.dto",
	generateDto2Poso=false
)
public class RSTableRow implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -9154292377110916477L;

	@ExposeToClient
	protected TableDefinition tableDefinition;
	
	protected boolean subtotalRow = false;
	
	protected Object[] row;
	
	public RSTableRow() {
	}
	
	public RSTableRow(TableDefinition tableDefinition, Object[] row) {
		this.tableDefinition = tableDefinition;
		this.row = row;
	}
	
	public TableDefinition getTableDefinition() {
		return tableDefinition;
	}

	public void setTableDefinition(TableDefinition tableDefinition) {
		this.tableDefinition = tableDefinition;
	}
	
	public Object[] getRow() {
		return row;
	}

	public void setRow(Object[] row) {
		this.row = row;
	}


	public Object getAt(int columnIndex) {
		return row[columnIndex];
	}

	public void setAt(int columnIndex, Object value) {
		row[columnIndex] = value;
	}
	
	public Object getValueFor(String name){
		int i = tableDefinition.getColumnIndexOf(name);
		return row[i];
	}
	
	public boolean isSubtotalRow() {
		return subtotalRow;
	}
	
	public void setSubtotalRow(boolean subtotalRow) {
		this.subtotalRow = subtotalRow;
	}

}
