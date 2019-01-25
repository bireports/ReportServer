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
 
 
package net.datenwerke.gxtdto.client.widgets;

import com.google.gwt.user.client.ui.Label;
import com.sencha.gxt.widget.core.client.container.MarginData;

public class SeparatorTextLabel extends Label {

	public SeparatorTextLabel(String label) {
		super(label);
	}
	
	public static SeparatorTextLabel createHeadlineLarge(String label){
		return createHeadlineLarge(label, null);
	}
	
	public static SeparatorTextLabel createHeadlineLarge(String label, MarginData layoutData){
		SeparatorTextLabel l = new SeparatorTextLabel(label);
		l.addStyleName("rs-l-head-large");
		if(null != layoutData)
			l.setLayoutData(layoutData);
		return l;
	}
	
	public static SeparatorTextLabel createHeadlineMedium(String label){
		return createHeadlineMedium(label, null);
	}
	
	public static SeparatorTextLabel createHeadlineMedium(String label, MarginData layoutData){
		SeparatorTextLabel l = new SeparatorTextLabel(label);
		l.addStyleName("rs-l-head-medium");
		if(null != layoutData)
			l.setLayoutData(layoutData);
		return l;
	}
	
	public static SeparatorTextLabel createHeadlineSmall(String label){
		return createHeadlineSmall(label, null);
	}
	
	public static SeparatorTextLabel createHeadlineSmall(String label, MarginData layoutData){
		SeparatorTextLabel l = new SeparatorTextLabel(label);
		l.addStyleName("rs-l-head-small");
		if(null != layoutData)
			l.setLayoutData(layoutData);
		return l;
	}

	public static SeparatorTextLabel createText(String text) {
		return createText(text, null);
	}
	
	public static SeparatorTextLabel createText(String text, MarginData layoutData) {
		SeparatorTextLabel l = new SeparatorTextLabel(text);
		l.addStyleName("rs-l-text");
		if(null != layoutData)
			l.setLayoutData(layoutData);
		return l;
	}
	
	public static SeparatorTextLabel createSmallText(String text) {
		return createSmallText(text, null);
	}
	
	public static SeparatorTextLabel createSmallText(String text, MarginData layoutData) {
		SeparatorTextLabel l = new SeparatorTextLabel(text);
		l.addStyleName("rs-l-text-small");
		if(null != layoutData)
			l.setLayoutData(layoutData);
		return l;
	}
	
}
