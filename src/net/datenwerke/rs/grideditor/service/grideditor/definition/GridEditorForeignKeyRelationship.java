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
 
 
package net.datenwerke.rs.grideditor.service.grideditor.definition;

public class GridEditorForeignKeyRelationship {

	private String tableName;
	private String fkColumn;
	private String displayExpression;
	private GridEditorColumnConfig config; 

	public GridEditorForeignKeyRelationship(){}
	
	public GridEditorForeignKeyRelationship(String tableName, String fkColumn,
			String displayExpression, String displayName) {
		this.tableName = tableName;
		this.fkColumn = fkColumn;
		this.displayExpression = displayExpression;
		
		config = new GridEditorColumnConfig();
		config.setDisplayName(displayName);
	}
	
	public GridEditorForeignKeyRelationship(String tableName, String fkColumn,
			String displayExpression, GridEditorColumnConfig config) {
		this.tableName = tableName;
		this.fkColumn = fkColumn;
		this.displayExpression = displayExpression;
		
		this.config = config;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getFkColumn() {
		return fkColumn;
	}

	public void setFkColumn(String fkColumn) {
		this.fkColumn = fkColumn;
	}

	public String getDisplayExpression() {
		return displayExpression;
	}

	public void setDisplayExpression(String displayExpression) {
		this.displayExpression = displayExpression;
	}

	public GridEditorColumnConfig getConfig() {
		return config;
	}

	public void setConfig(GridEditorColumnConfig config) {
		this.config = config;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((fkColumn == null) ? 0 : fkColumn.hashCode());
		result = prime * result
				+ ((tableName == null) ? 0 : tableName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		GridEditorForeignKeyRelationship other = (GridEditorForeignKeyRelationship) obj;
		if (fkColumn == null) {
			if (other.fkColumn != null)
				return false;
		} else if (!fkColumn.equals(other.fkColumn))
			return false;
		if (tableName == null) {
			if (other.tableName != null)
				return false;
		} else if (!tableName.equals(other.tableName))
			return false;
		return true;
	}


	
	
	
}
