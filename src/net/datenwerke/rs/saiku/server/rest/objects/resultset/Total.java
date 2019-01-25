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
 
 
package net.datenwerke.rs.saiku.server.rest.objects.resultset;

import org.saiku.service.olap.totals.TotalNode;
import org.saiku.service.olap.totals.aggregators.TotalAggregator;

public class Total {
	private final Cell[][] cells;
	private final String[] captions;
	private final int span;
	private final int width;
	
	public Total(TotalNode node) {
		this(node.getTotalGroups(), node.getMemberCaptions(), node.getSpan(), node.getWidth());
	}
	
	private Total(TotalAggregator[][] values, String[] captions, int span, int width) {
		if (values.length > 0)
			this.cells = new Cell[values.length][values[0].length];
		else
			this.cells = new Cell[0][];
		
		this.captions = captions;
		
		for (int i = 0; i < values.length; i++) {
			for (int j = 0; j < values[0].length; j++) {
				this.cells[i][j] = new Cell(values[i][j].getFormattedValue(), Cell.Type.DATA_CELL);
			}
		}
		this.span = span;
		this.width = width;
	}

	public Cell[][] getCells() {
		return cells;
	}
	
	public String[] getCaptions() {
		return captions;
	}

	public int getSpan() {
		return span;
	}
	
	public int getWidth() {
		return width;
	}
}
