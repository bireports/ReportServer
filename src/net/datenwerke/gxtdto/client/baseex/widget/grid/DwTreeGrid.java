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
 
 
package net.datenwerke.gxtdto.client.baseex.widget.grid;

import com.sencha.gxt.data.shared.TreeStore;
import com.sencha.gxt.widget.core.client.grid.ColumnConfig;
import com.sencha.gxt.widget.core.client.grid.ColumnModel;
import com.sencha.gxt.widget.core.client.tree.TreeStyle;
import com.sencha.gxt.widget.core.client.treegrid.TreeGrid;

import net.datenwerke.gxtdto.client.baseex.widget.DwTreePanel;

public class DwTreeGrid<M> extends TreeGrid<M> {

	public DwTreeGrid(TreeStore<M> store, ColumnModel<M> cm, ColumnConfig<M, ?> treeColumn) {
		super(store, cm, treeColumn);
		_setTreeStyle(this, new DwTreePanel.DwTreeStyle());
		setView(new DwTreeGridView<M>());
	}
	
	/* resort to violator pattern */
	protected native void _setTreeStyle(TreeGrid<M> grid, TreeStyle ts) /*-{
	  grid.@com.sencha.gxt.widget.core.client.treegrid.TreeGrid::style = ts;
	}-*/;

	
	

}
