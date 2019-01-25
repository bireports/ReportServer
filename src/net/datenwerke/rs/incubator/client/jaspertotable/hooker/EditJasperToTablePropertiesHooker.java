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
 
 
package net.datenwerke.rs.incubator.client.jaspertotable.hooker;

import com.google.inject.Inject;
import com.sencha.gxt.core.client.dom.ScrollSupport.ScrollMode;
import com.sencha.gxt.widget.core.client.container.MarginData;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.toolbar.ToolBar;

import net.datenwerke.gf.client.managerhelper.hooks.MainPanelViewToolbarConfiguratorHook;
import net.datenwerke.gf.client.managerhelper.mainpanel.MainPanelView;
import net.datenwerke.gxtdto.client.baseex.widget.DwWindow;
import net.datenwerke.gxtdto.client.baseex.widget.btn.DwTextButton;
import net.datenwerke.gxtdto.client.baseex.widget.layout.DwFlowContainer;
import net.datenwerke.gxtdto.client.dtomanager.callback.RsAsyncCallback;
import net.datenwerke.gxtdto.client.forms.simpleform.SimpleForm;
import net.datenwerke.gxtdto.client.forms.simpleform.actions.EnableDisableAction;
import net.datenwerke.gxtdto.client.forms.simpleform.conditions.FieldEquals;
import net.datenwerke.gxtdto.client.locale.BaseMessages;
import net.datenwerke.gxtdto.client.utilityservices.toolbar.ToolbarService;
import net.datenwerke.rs.base.client.reportengines.jasper.dto.JasperReportDto;
import net.datenwerke.rs.core.client.datasourcemanager.dto.DatasourceContainerDto;
import net.datenwerke.rs.incubator.client.jaspertotable.JasperToTableDao;
import net.datenwerke.rs.incubator.client.jaspertotable.dto.JasperToTableConfigDto;
import net.datenwerke.rs.incubator.client.jaspertotable.dto.decorator.JasperToTableConfigDtoDec;
import net.datenwerke.rs.incubator.client.jaspertotable.dto.pa.JasperToTableConfigDtoPA;
import net.datenwerke.rs.incubator.client.jaspertotable.locale.JasperToTableMessages;
import net.datenwerke.rs.theme.client.icon.BaseIcon;
import net.datenwerke.treedb.client.treedb.dto.AbstractNodeDto;

/**
 * 
 *
 */
public class EditJasperToTablePropertiesHooker implements
		MainPanelViewToolbarConfiguratorHook {

	
	private final JasperToTableDao jttDao;
	private final ToolbarService toolbarUtils;
	
	@Inject
	public EditJasperToTablePropertiesHooker(
		JasperToTableDao jttDao,
		ToolbarService toolbarUtils	
		){
		
		/* store objects */
		this.jttDao = jttDao;
		this.toolbarUtils = toolbarUtils;
	}
	
	@Override
	public void mainPanelViewToolbarConfiguratorHook_addLeft(
			MainPanelView view, ToolBar toolbar, final AbstractNodeDto selectedNode) {
		if(! MainPanelView.ID_MAIN_PROPERTIES_VIEW.equals(view.getViewId()))
			return;
		if(! (selectedNode instanceof JasperReportDto))
			return;

		/* add parameter */
		DwTextButton editJasperToTableBtn = toolbarUtils.createSmallButtonLeft(JasperToTableMessages.INSTANCE.editJasperToTableBtnText(), BaseIcon.fromFileExtension("xls"));
		editJasperToTableBtn.addSelectHandler(new SelectHandler() {
			
			@Override
			public void onSelect(SelectEvent event) {
				displayEditDialog((JasperReportDto)selectedNode);
			}
		});
		
		toolbar.add(editJasperToTableBtn);
	}

	protected void displayEditDialog(final JasperReportDto report) {
		final DwWindow window = new DwWindow();
		window.setHeadingText(JasperToTableMessages.INSTANCE.editJasperToTableBtnText());
		window.setSize(600, 450);
		
		final SimpleForm form = SimpleForm.getInlineInstance();
		final DwFlowContainer container = new DwFlowContainer();
		container.setScrollMode(ScrollMode.AUTOY);
		window.add(container, new MarginData(10));
		window.mask(BaseMessages.INSTANCE.loadingMsg());
		
		final String activeKey = form.addField(
				Boolean.class,
				JasperToTableConfigDtoPA.INSTANCE.active(),
				JasperToTableMessages.INSTANCE.allowJasperToTableLabel());
		
		final String datasourceKey = form.addField(
					DatasourceContainerDto.class, 
					JasperToTableConfigDtoPA.INSTANCE.datasourceContainer(),
					JasperToTableMessages.INSTANCE.datasourceLabel()); 
		
		form.addCondition(activeKey, new FieldEquals(Boolean.TRUE), new EnableDisableAction(datasourceKey));
		
			
		jttDao.getConfig(report, new RsAsyncCallback<JasperToTableConfigDto>(){
			@Override
			public void onSuccess(JasperToTableConfigDto result) {
				window.unmask();
				
				if(null != result){
					((JasperToTableConfigDtoDec)result).setActive(true);
					form.bind(result);
				} else
					form.bind(new JasperToTableConfigDtoDec());
				
				/* only attach after bind */
				container.add(form);
			}
		});
		
		DwTextButton submitBtn = new DwTextButton(BaseMessages.INSTANCE.submit());
		submitBtn.addSelectHandler(new SelectHandler() {
			@Override
			public void onSelect(SelectEvent event) {
				jttDao.setConfig(report, (JasperToTableConfigDto) form.getBoundModel(), new RsAsyncCallback<Void>(){
					@Override
					public void onSuccess(Void result) {
						window.hide();
					}
				});
			}
		});
		window.addButton(submitBtn);
		
		window.show();
	}

	@Override
	public void mainPanelViewToolbarConfiguratorHook_addRight(
			MainPanelView view, ToolBar toolbar, AbstractNodeDto selectedNode) {

	}

}
