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
 
 
package net.datenwerke.rs.grideditor.client.grideditor.ui;

import net.datenwerke.rs.grideditor.client.grideditor.dto.GridEditorRecordDto;

import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.sencha.gxt.data.shared.Converter;
import com.sencha.gxt.widget.core.client.form.Field;
import com.sencha.gxt.widget.core.client.form.IsField;
import com.sencha.gxt.widget.core.client.grid.ColumnConfig;
import com.sencha.gxt.widget.core.client.grid.Grid.GridCell;
import com.sencha.gxt.widget.core.client.grid.editing.AbstractGridEditing;

public class DummyGridEditingCapturer extends AbstractGridEditing<GridEditorRecordDto> {

	private ColumnConfig<GridEditorRecordDto, ?> columnConfig;
	private Converter<?, ?> converter;
	private IsField<?> field;

	
	@Override
	public <N, O> void addEditor(
			ColumnConfig<GridEditorRecordDto, N> columnConfig,
			Converter<N, O> converter, IsField<O> field) {
		this.columnConfig = columnConfig;
		this.converter = converter;
		this.field = field;
	}
	
	@Override
	public <N> void addEditor(
			ColumnConfig<GridEditorRecordDto, N> columnConfig, IsField<N> field) {
		this.columnConfig = columnConfig;
		this.field = field;
	}
	
	public ColumnConfig<GridEditorRecordDto, ?> getColumnConfig() {
		return columnConfig;
	}
	
	public Converter getConverter() {
		return converter;
	}
	
	public Field<?> getField() {
		//TODO: GXT CHECK
		if(field instanceof Field)
			return (Field<?>)field;
		throw new IllegalArgumentException("Expected field but got only isField");
	}
	
	@Override
	public void fireEvent(GwtEvent<?> event) {
	}

	@Override
	public void cancelEditing() {
		// TODO Auto-generated method stub

	}

	@Override
	public void completeEditing() {
		// TODO Auto-generated method stub

	}

	@Override
	public void startEditing(GridCell cell) {
		// TODO Auto-generated method stub

	}

	@Override
	protected SafeHtml getErrorHtml() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected boolean isValid() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected void showTooltip(SafeHtml content) {
		// TODO Auto-generated method stub

	}

}
