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
 
 
package net.datenwerke.rs.theme.client.toolbar;

import net.datenwerke.rs.theme.client.icon.BaseIcon;

import com.google.gwt.resources.client.ImageResource;
import com.sencha.gxt.theme.neptune.client.base.toolbar.Css3PagingToolBarAppearance;

public class RsPagingToolbarAppearance extends Css3PagingToolBarAppearance {

	 @Override
	  public ImageResource first() {
	    return BaseIcon.ANGLE_DOUBLE_LEFT.toImageResource();
	  }

	  @Override
	  public ImageResource last() {
		  return BaseIcon.ANGLE_DOUBLE_RIGHT.toImageResource();
	  }

	  @Override
	  public ImageResource next() {
	    return BaseIcon.CHEVRON_RIGHT.toImageResource();
	  }

	  @Override
	  public ImageResource prev() {
		  return BaseIcon.CHEVRON_LEFT.toImageResource();
	  }

	  @Override
	  public ImageResource refresh() {
		  return BaseIcon.REFRESH.toImageResource();
	  }

	  @Override
	  public ImageResource loading() {
		  return BaseIcon.LOADING.toImageResource();
	  }
}
