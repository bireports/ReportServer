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
 
 
package net.datenwerke.rs.core.client.reportexecutor.module;

import javax.inject.Provider;

import net.datenwerke.gf.client.homepage.modules.ClientTempModuleImpl;
import net.datenwerke.rs.core.client.reportexecutor.locale.ReportexecutorMessages;
import net.datenwerke.rs.core.client.reportmanager.dto.reports.ReportDto;
import net.datenwerke.rs.theme.client.icon.BaseIcon;

import com.google.gwt.event.dom.client.MouseEvent;
import com.google.gwt.resources.client.ImageResource;
import com.google.inject.Inject;
import com.sencha.gxt.widget.core.client.Component;

public class ReportExecuteAreaModule extends ClientTempModuleImpl {

	private final Provider<ReportExecuteAreaMainWidget> mainWidgetProvider;
	private ReportExecuteAreaMainWidget mainWidget;
	
	@Inject
	public ReportExecuteAreaModule(
			Provider<ReportExecuteAreaMainWidget> mainWidgetProvider) {
		this.mainWidgetProvider = mainWidgetProvider;
	}

	@Override
	public String getModuleName() {
		return ReportexecutorMessages.INSTANCE.executeAreaModule();
	}

	@Override
	public ImageResource getIcon() {
		return BaseIcon.REPORT.toImageResource();
	}

	@Override
	public ReportExecuteAreaMainWidget getMainWidget() {
		if(null == mainWidget){
			mainWidget = mainWidgetProvider.get();
			mainWidget.setModule(this);
		}
		return mainWidget;
	}

	public void addExecutionComponent(ReportDto report, Component displayComponent) {
		getMainWidget().addExecutionComponent(report, displayComponent);
	}

	public void closeCurrent() {
		getMainWidget().closeCurrent();
	}
	
	public void removeModule(){
		viewport.removeTempModule(this);
	}
	
	@Override
	public void onMouseOver(MouseEvent be) {

	}

	public void markCurrentChanged() {
		getMainWidget().markCurrentChanged();
	}

	public void forceCloseCurrent() {
		getMainWidget().forceCloseCurrent();
	}
	
	@Override
	public boolean isRecyclable() {
		return true;
	}
}
