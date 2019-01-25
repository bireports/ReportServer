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
 
 
package net.datenwerke.gxtdto.client.utilityservices.grid;

import java.util.Comparator;

import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.Converter;
import com.sencha.gxt.data.shared.LabelProvider;
import com.sencha.gxt.data.shared.SortDir;
import com.sencha.gxt.widget.core.client.form.SimpleComboBox;
import com.sencha.gxt.widget.core.client.grid.ColumnConfig;


public interface GridHelperService {

	public static class CCContainer<D,V> {
		ColumnConfig<D,V> config;
		SimpleComboBox<Object> combo;
		Converter<V, Object> converter;
		public CCContainer(ColumnConfig<D, V> config,
				SimpleComboBox<Object> combo, Converter<V, Object> converter) {
			super();
			this.config = config;
			this.combo = combo;
			this.converter = converter;
		}
		public ColumnConfig<D, V> getConfig() {
			return config;
		}
		public SimpleComboBox<Object> getCombo() {
			return combo;
		}
		public Converter<V, Object> getConverter() {
			return converter;
		}
	}
	
	public <D, V extends Enum<?>> CCContainer<D, V> createComboBoxColumnConfig(
			V[] values, ValueProvider<D, V> vp, boolean nullable, SortDir sortDir, int width);

	public <D, V> CCContainer<D, V> createComboBoxColumnConfig(
			V[] values, ValueProvider<D, V> vp, Converter<V, Object> converter, Comparator<Object> comparator, 
			boolean nullable, SortDir sortDir, int width);

	public <D, V> CCContainer<D, V> createComboBoxColumnConfig(
			V[] values, ValueProvider<D, V> vp, Converter<V, Object> converter,
			LabelProvider<? super Object> labelProvider, Comparator<Object> comparator, boolean nullable,
			SortDir sortDir, int width);

	public <D> CCContainer<D, Boolean> createBooleanComboBoxColumnConfig(
			 ValueProvider<D, Boolean> vp,
			boolean nullable, boolean trueFirst, int width, String tString,
			String fString);
}
