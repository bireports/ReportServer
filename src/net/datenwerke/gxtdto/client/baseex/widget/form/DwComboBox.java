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
 
 
package net.datenwerke.gxtdto.client.baseex.widget.form;

import net.datenwerke.rs.theme.client.field.RsTriggerFieldAppearance;
import net.datenwerke.rs.theme.client.icon.BaseIcon;

import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.sencha.gxt.cell.core.client.form.ComboBoxCell;
import com.sencha.gxt.data.shared.LabelProvider;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.widget.core.client.ListView;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.form.ComboBox;

public class DwComboBox<M> extends ComboBox<M> {

	public DwComboBox(ComboBoxCell<M> cell) {
		super(cell);

		adaptListContainer();
	}

	public DwComboBox(ListStore<M> store, LabelProvider<? super M> labelProvider) {
		super(store, labelProvider);

		adaptListContainer();
	}

	public DwComboBox(ListStore<M> store, LabelProvider<? super M> labelProvider, ListView<M, ?> listView) {
		super(store, labelProvider, listView);
		
		adaptListContainer();
	}

	protected void adaptListContainer() {
		VerticalLayoutContainer listContainer = getListContainer(getCell());
		listContainer.setBorders(false);
	}

	/* resort to violator pattern */
	protected native VerticalLayoutContainer getListContainer(ComboBoxCell cbc) /*-{
	  return cbc.@com.sencha.gxt.cell.core.client.form.ComboBoxCell::listContainer;
	}-*/;

	public void plainAppearance() {
		((RsTriggerFieldAppearance)getCell().getAppearance()).setPlainAppearance(true);
		addValueChangeHandler(new ValueChangeHandler<M>() {
			@Override
			public void onValueChange(ValueChangeEvent<M> event) {
				((RsTriggerFieldAppearance)getCell().getAppearance()).updatePlainValue(getElement());
			}
		});
	}

	public void setTriggerIcon(BaseIcon icon){
		((RsTriggerFieldAppearance)getCell().getAppearance()).setTriggerIcon(icon);
	}
	
}
