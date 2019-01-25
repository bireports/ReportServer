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
 
 
package net.datenwerke.rs.dashboard.client.dashboard.ui.admin.forms;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.datenwerke.gf.client.managerhelper.mainpanel.SimpleFormView;
import net.datenwerke.gxtdto.client.forms.simpleform.SimpleForm;
import net.datenwerke.gxtdto.client.forms.simpleform.providers.configs.SFFCCustomComponent;
import net.datenwerke.gxtdto.client.forms.simpleform.providers.configs.SFFCStaticLabel;
import net.datenwerke.gxtdto.client.forms.simpleform.providers.configs.impl.SFFCStaticDropdownList;
import net.datenwerke.gxtdto.client.forms.simpleform.providers.configs.impl.SFFCTextAreaImpl;
import net.datenwerke.gxtdto.client.forms.simpleform.providers.dummy.CustomComponent;
import net.datenwerke.gxtdto.client.forms.simpleform.providers.dummy.StaticLabel;
import net.datenwerke.gxtdto.client.locale.BaseMessages;
import net.datenwerke.hookhandler.shared.hookhandler.HookHandlerService;
import net.datenwerke.rs.dashboard.client.dashboard.dadgets.LibraryDadgetProcessor;
import net.datenwerke.rs.dashboard.client.dashboard.dto.DadgetDto;
import net.datenwerke.rs.dashboard.client.dashboard.dto.DadgetNodeDto;
import net.datenwerke.rs.dashboard.client.dashboard.dto.pa.DadgetNodeDtoPA;
import net.datenwerke.rs.dashboard.client.dashboard.hooks.DadgetProcessorHook;
import net.datenwerke.rs.dashboard.client.dashboard.locale.DashboardMessages;

import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

/**
 * 
 *
 */
public class DadgetNodeForm extends SimpleFormView {

	private final HookHandlerService hookHandler;
	
	@Inject
	public DadgetNodeForm(HookHandlerService hookHandler) {
		super();
		this.hookHandler = hookHandler;
	}



	@Override
	public void configureSimpleForm(final SimpleForm form) {
		form.setHeadingText(DashboardMessages.INSTANCE.editDadgetNode() + (getSelectedNode() == null ? "" : " (" + getSelectedNode().getId() + ")"));
		
		form.addField(String.class, DadgetNodeDtoPA.INSTANCE.name(), BaseMessages.INSTANCE.propertyName()); 
		
		form.addField(String.class, DadgetNodeDtoPA.INSTANCE.description(), BaseMessages.INSTANCE.propertyDescription(), new SFFCTextAreaImpl());
		
		final DadgetNodeDto dadgetNode = (DadgetNodeDto) getSelectedNode();
		if(null == dadgetNode.getDadget()){
			form.setFieldWidth(0.3);
			final String dadgetKey = form.addField(List.class, new SFFCStaticDropdownList<DadgetDto>() {
				private Map<String, DadgetDto> map;
				@Override
				public Map<String, DadgetDto> getValues() {
					if(null == map){
						map = new HashMap<String, DadgetDto>();
					
						for(DadgetProcessorHook processor : hookHandler.getHookers(DadgetProcessorHook.class)){
							if(processor.supportsDadgetLibrary())
								map.put(processor.getTitle(), processor.instantiateDadget());
						}
					}
					
					return map;
				}
			});
			
			form.addValueChangeHandler(dadgetKey, new ValueChangeHandler() {
				@Override
				public void onValueChange(ValueChangeEvent event) {
					dadgetNode.setDadget((DadgetDto) form.getValue(dadgetKey));
				}
			});
		} else {
			DadgetDto dadget = dadgetNode.getDadget();
			for(final DadgetProcessorHook processor : hookHandler.getHookers(DadgetProcessorHook.class)){
				if(processor.consumes(dadget) && processor.hasConfigDialog()){
					form.addField(StaticLabel.class, new SFFCStaticLabel() {
						@Override
						public String getLabel() {
							return processor.getTitle();
						}
					});
					
					final Widget adminConfigDialog = processor.getAdminConfigDialog(dadget, form);
					if(null != adminConfigDialog){
						form.addField(CustomComponent.class, new SFFCCustomComponent() {
							@Override
							public Widget getComponent() {
								return adminConfigDialog;
							}
						});
					}
				}
			}
		}
	}
	

	@Override
	protected void onSuccessfulSubmit(){
		reloadView();
	}

	
}