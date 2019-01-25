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
 
 
package net.datenwerke.gxtdto.client.resources;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ImageResource;

public interface BaseResources extends ClientBundle {

	public static BaseResources INSTANCE = GWT.create(BaseResources.class);
	
	@Source("img/i/s.gif")
	ImageResource emptyImage();
	
	@Source("img/i/column_single.png")
	ImageResource columnSingle();

	@Source("img/i/column_double.png")
	ImageResource columnDouble();

	@Source("img/i/column_double_l.png")
	ImageResource columnDoubleL();

	@Source("img/i/column_double_r.png")
	ImageResource columnDoubleR();

	@Source("img/i/column_tripple.png")
	ImageResource columnTripple();
	
	@Source("img/i/100bar.png")
	ImageResource saikuChart100bar();
	
	@Source("img/i/area.png")
	ImageResource saikuChartArea();
	
	@Source("img/i/bar.png")
	ImageResource saikuChartBar();
	
	@Source("img/i/dot.png")
	ImageResource saikuChartDot();
	
	@Source("img/i/heat.png")
	ImageResource saikuChartHeat();
	
	@Source("img/i/line.png")
	ImageResource saikuChartLine();
	
	@Source("img/i/multiple.png")
	ImageResource saikuChartMultiple();
	
	@Source("img/i/pie.png")
	ImageResource saikuChartPie();
	
	@Source("img/i/stackedbar.png")
	ImageResource saikuChartStackedbar();
	
	@Source("img/i/waterfall.png")
	ImageResource saikuChartWaterfall();
}
