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
 
 
package net.datenwerke.rs.core.client.reportexecutor.dto;

import java.util.ArrayList;
import java.util.List;

import net.datenwerke.gxtdto.client.dtomanager.Dto;

public class PreviewModel extends Dto {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6799539859715703002L;

	private List<String> columnNames = new ArrayList<String>();
	private List<String[]> rows = new ArrayList<String[]>();
	
	private List<String[]> rawRows = new ArrayList<String[]>();
	
	private int size;
	
	public PreviewModel(){
		super();
	}

	public List<String[]> getRows() {
		return rows;
	}

	public void setRows(List<String[]> rows) {
		this.rows = rows;
	}
	
	public void addRow(String[] sRow) {
		rows.add(sRow);
	}
	
	public List<String[]> getRawRows() {
		return rawRows;
	}

	public void setRawRows(List<String[]> rows) {
		this.rawRows = rows;
	}
	
	public void addRawRow(String[] row) {
		rawRows.add(row);
	}
	
	public String getRawData(int i, int j) {
		return rawRows.get(i)[j];
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public void setColumnNames(List<String> columnNames) {
		this.columnNames = columnNames;
	}

	public List<String> getColumnNames() {
		return columnNames;
	}

	public String getColumnName(int i) {
		return columnNames.get(i);
	}

	
}
