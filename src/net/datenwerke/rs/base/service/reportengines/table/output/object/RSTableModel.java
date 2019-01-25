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
 
 
package net.datenwerke.rs.base.service.reportengines.table.output.object;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.swing.event.TableModelListener;

import net.datenwerke.dtoservices.dtogenerator.annotations.ExposeToClient;
import net.datenwerke.dtoservices.dtogenerator.annotations.GenerateDto;
import net.datenwerke.rs.utils.entitycloner.annotation.EnclosedEntity;

import org.apache.commons.lang.NotImplementedException;

/**
 * 
 *
 */
@GenerateDto(
	dtoPackage="net.datenwerke.rs.base.client.reportengines.table.dto",
	generateDto2Poso=false
)
public class RSTableModel extends CompiledTableReport implements Iterable<RSTableRow>, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6867038322739389992L;

	@ExposeToClient
	@EnclosedEntity
	private TableDefinition tableDefinition;
	
	@ExposeToClient
	@EnclosedEntity
	private List<RSTableRow> data = new ArrayList<RSTableRow>();
	
	public RSTableModel() {
	}
	
	public void setTableDefinition(TableDefinition tableDefinition) {
		this.tableDefinition = tableDefinition;
	}

	public RSTableModel(TableDefinition tableDefinition) {
		super();
		this.tableDefinition = tableDefinition;
	}

	public TableDefinition getTableDefinition() {
		return tableDefinition;
	}

	public RSTableRow addDataRow(Collection<?> row){
		Object[] data = row.toArray(new Object[row.size()]);
		return addDataRow(data);
	}
	
	public RSTableRow addDataRow(Object... row){
		if(row.length != tableDefinition.size())
			throw new IllegalArgumentException("Number of items in row must match table definition"); //$NON-NLS-1$
		
		RSTableRow tableRow = new RSTableRow(tableDefinition,row); 
		addDataRow(tableRow);
		
		return tableRow;
	}
	
	public void addDataRow(RSTableRow row){
		data.add(row);
	}
	
	public List<RSTableRow> getData() {
		return data;
	}

	public void setData(List<RSTableRow> data) {
		this.data = data;
	}

	public Iterator<RSTableRow> iterator() {
		return data.iterator();
	}

	public Class<?> getColumnType(int columnIndex) {
		return getTableDefinition().getColumnTypes().get(columnIndex);
	}

	public int getColumnCount() {
		return getTableDefinition().size();
	}

	public String getColumnName(int columnIndex) {
		return getTableDefinition().getColumnNames().get(columnIndex);
	}

	public int getRowCount() {
		return data.size();
	}

	public Object getValueAt(int rowIndex, int columnIndex) {
		return data.get(rowIndex).getAt(columnIndex);
	}

	public boolean isCellEditable(int rowIndex, int columnIndex) {
		throw new NotImplementedException();
	}

	public void removeTableModelListener(TableModelListener l) {
		throw new NotImplementedException();
	}

	public void setValueAt(Object value, int rowIndex, int columnIndex) {
		data.get(rowIndex).setAt(columnIndex, value);
	}

	public Object getReport() {
		return data;
	}

	public String getFileExtension() {
		return null;
	}

	public String getMimeType() {
		return null;
	}
	
	@Override
	public boolean isStringReport() {
		return false;
	}
	
	@Override
	public String toString() {
		return  "Table(definition:" + (null != tableDefinition ? tableDefinition.toString() : "null" )+ ", rows: " + getRowCount() + ")";
	}
}
