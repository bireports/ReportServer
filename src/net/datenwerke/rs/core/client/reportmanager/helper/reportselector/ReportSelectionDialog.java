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
 
 
package net.datenwerke.rs.core.client.reportmanager.helper.reportselector;

import net.datenwerke.gxtdto.client.baseex.widget.btn.DwTextButton;
import net.datenwerke.gxtdto.client.locale.BaseMessages;
import net.datenwerke.gxtdto.client.ui.helper.nav.WestPropertiesDialog;
import net.datenwerke.hookhandler.shared.hookhandler.HookHandlerService;
import net.datenwerke.rs.core.client.reportmanager.dto.interfaces.ReportContainerDto;
import net.datenwerke.rs.core.client.reportmanager.hooks.ReportSelectionRepositoryProviderHook;
import net.datenwerke.rs.core.client.reportmanager.locale.ReportmanagerMessages;
import net.datenwerke.rs.theme.client.icon.BaseIcon;

import com.google.gwt.dom.client.NativeEvent;
import com.google.inject.Inject;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.menu.Menu;
import net.datenwerke.gxtdto.client.baseex.widget.menu.DwMenu;

public class ReportSelectionDialog extends WestPropertiesDialog {

	public interface ReportSelectionDialogEventHandler{
		public void handleDoubleClick(ReportContainerDto dto, ReportSelectionRepositoryProviderHook hooker, NativeEvent event, Object... info);

		public Menu getContextMenuFor(ReportContainerDto dto,
				ReportSelectionRepositoryProviderHook hooker, Object... info);
	}
	
	public interface RepositoryProviderConfig{}
	
	private final HookHandlerService hookHandler;
	
	private ReportSelectionDialogEventHandler eventHandler;

	@Inject
	public ReportSelectionDialog(
		HookHandlerService hookHandler
		){
	
		this.hookHandler = hookHandler;
		
		init();
	}

	public void init() {
		setHeadingText(ReportmanagerMessages.INSTANCE.selectReportHeader());
		setHeaderIcon(BaseIcon.REPORT_ADD);
		setClosable(true);
		setOnEsc(true);
		
		/* add close buttons */
		DwTextButton closeBtn = new DwTextButton(BaseMessages.INSTANCE.close());
		closeBtn.addSelectHandler(new SelectHandler() {
			@Override
			public void onSelect(SelectEvent event) {
				hide();
			}
		});
		addButton(closeBtn);
	}

	public void initRepositories(RepositoryProviderConfig... configs) {
		for(ReportSelectionRepositoryProviderHook hooker : hookHandler.getHookers(ReportSelectionRepositoryProviderHook.class))
			hooker.addCards(this, configs);
	}

	public void handleDoubleClick(ReportContainerDto dto, ReportSelectionRepositoryProviderHook hooker, NativeEvent event, Object... info) {
		if(null != eventHandler)
			eventHandler.handleDoubleClick(dto, hooker, event, info);
	}

	public Menu getContextMenuFor(ReportContainerDto dto,
			ReportSelectionRepositoryProviderHook hooker, Object... info) {
		if(null != eventHandler)
			return eventHandler.getContextMenuFor(dto, hooker, info);
		return null;
	}

	public ReportSelectionDialogEventHandler getEventHandler() {
		return eventHandler;
	}

	public void setEventHandler(ReportSelectionDialogEventHandler eventHandler) {
		this.eventHandler = eventHandler;
	}


	
	
}
