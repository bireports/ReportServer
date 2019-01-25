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
 
 
package net.datenwerke.rs.dashboard.client.dashboard.ui;

import net.datenwerke.gxtdto.client.baseex.widget.DwContentPanel;
import net.datenwerke.gxtdto.client.baseex.widget.mb.DwConfirmMessageBox;
import net.datenwerke.gxtdto.client.locale.BaseMessages;
import net.datenwerke.gxtdto.client.theme.CssClassConstant;
import net.datenwerke.hookhandler.shared.hookhandler.HookHandlerService;
import net.datenwerke.rs.dashboard.client.dashboard.dto.DadgetDto;
import net.datenwerke.rs.dashboard.client.dashboard.hooks.DadgetProcessorHook;
import net.datenwerke.rs.dashboard.client.dashboard.hooks.DadgetProcessorHook.DadgetConfigureCallback;
import net.datenwerke.rs.dashboard.client.dashboard.locale.DashboardMessages;
import net.datenwerke.rs.theme.client.icon.BaseIcon;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.RepeatingCommand;
import com.google.inject.Inject;
import com.sencha.gxt.widget.core.client.Dialog.PredefinedButton;
import com.sencha.gxt.widget.core.client.box.ConfirmMessageBox;
import com.sencha.gxt.widget.core.client.button.ToolButton;
import com.sencha.gxt.widget.core.client.event.DialogHideEvent;
import com.sencha.gxt.widget.core.client.event.DialogHideEvent.DialogHideHandler;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;

public class DadgetPanel extends DwContentPanel {

	@CssClassConstant
	public static final String CSS_NAME = "rs-dadget";
	
	
	private final HookHandlerService hookHandler;
	
	protected DadgetDto dadget;
	protected DashboardView view;
	
	private int reloadCounter = 0;
	
	@Inject
	public DadgetPanel(
		HookHandlerService hookHandler	
		){
		this.hookHandler = hookHandler;
	}
	
	@Override
	public String getCssName() {
		return super.getCssName() + " " + CSS_NAME;
	}

	public void init(DashboardView view, DadgetDto dadget) {
		this.dadget = dadget;
		this.view = view;
		
		setHeadingText(null);
	}

	@Override
	protected void onAfterFirstAttach() {
		super.onAfterFirstAttach();
		initReload();
	}

	protected void initReload() {
		if(dadget.getReloadInterval() > 0){
			reloadCounter++;
			final int myCounter = reloadCounter;
			Scheduler.get().scheduleFixedPeriod(new RepeatingCommand() {
				@Override
				public boolean execute() {
					if(reloadCounter != myCounter) 
						return false;
					
					/* if not visible do not reload */
					if(! isVisible(true))
						return false;
					
					clear();
					getProcessor().draw(dadget, DadgetPanel.this);
					forceLayout();
					
					return true;
				}
			}, (int) dadget.getReloadInterval()*1000);
		} else 
			reloadCounter = 0;
	}
	
	@Override
	protected void onShow() {
		super.onShow();
		initReload();
	}


	public DadgetDto getDadget() {
		return dadget;
	}
	
	public void updateDadget(DadgetDto dadget){
		this.dadget = dadget;
	}
	
	@Override
	public void setHeadingText(String text) {
		if(null == text || "".equals(text.trim()))
			super.setHeadingText(BaseMessages.INSTANCE.unnamed());
		else
			super.setHeadingText(text);
	}

	public void refresh(){
		clear();
		getProcessor().draw(dadget, DadgetPanel.this);
		forceLayout();
	}
	
	public void onMove() {
		if(getProcessor().isRedrawOnMove()){
			refresh();
		}
	}
	
	@Override
	protected void initTools() {
		super.initTools();
		
		getProcessor().addTools(this);
		
		ToolButton refreshBtn = new ToolButton(ToolButton.REFRESH);
		addTool(refreshBtn);
		refreshBtn.addSelectHandler(new SelectHandler() {
			@Override
			public void onSelect(SelectEvent event) {
				refresh();
			}
		});

		if(! view.isProtected()){
			if(getProcessor().hasConfigDialog()){
				ToolButton configBtn = new ToolButton(ToolButton.GEAR);
				addTool(configBtn);
				configBtn.addSelectHandler(new SelectHandler() {
					@Override
					public void onSelect(SelectEvent event) {
						getProcessor().displayConfigDialog(dadget, new DadgetConfigureCallback(){
							@Override
							public void configuringDone() {
								view.dadgetConfigured(DadgetPanel.this);
								
								initReload();
							}
							@Override
							public void cancelled() {
								
							}
						});
					}
				});
			}
			
			ToolButton closeBtn = new ToolButton(ToolButton.CLOSE);
			addTool(closeBtn);
			closeBtn.addSelectHandler(new SelectHandler() {
				@Override
				public void onSelect(SelectEvent event) {
					ConfirmMessageBox cmb = new DwConfirmMessageBox(DashboardMessages.INSTANCE.removeDadgetConfirmTitle(), DashboardMessages.INSTANCE.removeDadgetConfirmMsg());
					cmb.addDialogHideHandler(new DialogHideHandler() {
						@Override
						public void onDialogHide(DialogHideEvent event) {
							if (event.getHideButton() == PredefinedButton.YES) 
								removeDadget();	
						}
					});
					
					cmb.show();
				}
			});
		}
	}
	
	protected void removeDadget() {
		view.removeDadget(this);
	}
	
	protected DadgetProcessorHook getProcessor() {
		for(DadgetProcessorHook processor : hookHandler.getHookers(DadgetProcessorHook.class))
			if(processor.consumes(dadget))
				return processor;
		throw new IllegalArgumentException("Could not find dadgetprocessor for " + dadget.getClass());
	}
	
	public DashboardView getView() {
		return view;
	}

	public void setHeaderIcon(BaseIcon icon) {
		getHeader().setIcon(icon.toImageResource());
	}

	public void makeAwareOfSelection() {
		initReload();
	}


}
