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
 
 
package net.datenwerke.gxtdto.client.baseex.widget;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.widget.core.client.Window;
import com.sencha.gxt.widget.core.client.container.MarginData;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;

import net.datenwerke.gxtdto.client.baseex.widget.btn.DwTextButton;
import net.datenwerke.gxtdto.client.baseex.widget.layout.DwFlowContainer;
import net.datenwerke.gxtdto.client.forms.simpleform.SimpleForm;
import net.datenwerke.gxtdto.client.locale.BaseMessages;
import net.datenwerke.gxtdto.client.theme.CssClassConstant;
import net.datenwerke.rs.theme.client.icon.BaseIcon;


public class DwWindow extends Window {

	
	@CssClassConstant
	public static final String CSS_NAME = "rs-w";
	
	@CssClassConstant
	public static final String CSS_HEADER_NAME = "rs-w-header";
	
	@CssClassConstant
	public static final String CSS_BODY_NAME = "rs-w-body";
	
	@CssClassConstant
	public static final String CSS_CONTENT_NAME = "rs-w-content";	
	
	@CssClassConstant
	public static final String CSS_BBAR = "rs-w-bbar";	
	
	public DwWindow() {
		this(GWT.<WindowAppearance>create(WindowAppearance.class));
	}

	public DwWindow(WindowAppearance appearance) {
		super(appearance);
		initCss();
	}
	
	@Override
	/* weird sencha bug, that otherwise window position is off */
	public void add(final Widget child) {
		if(!(child instanceof SimpleForm))
			super.add(child);
		else {
			final DwFlowContainer container = new DwFlowContainer();
			Scheduler.get().scheduleDeferred(new ScheduledCommand() {
				
				@Override
				public void execute() {
					container.add(child);
				}
			});
			super.add(container);
		}
	}
	
	@Override
	/* weird sencha bug, that otherwise window position is off */
	public void add(final Widget child, final MarginData layoutData) {
		if(!(child instanceof SimpleForm))
			super.add(child, layoutData);
		else {
			final DwFlowContainer container = new DwFlowContainer();
			Scheduler.get().scheduleDeferred(new ScheduledCommand() {
				
				@Override
				public void execute() {
					container.add(child, layoutData);
				}
			});
			super.add(container);
		}
	}
	
	protected void initCss() {
		getElement().addClassName(getCssName());
		getAppearance().getHeaderElem(getElement()).addClassName(getCssHeaderName());
		getAppearance().getBodyWrap(getElement()).addClassName(getCssBodyName());
		getAppearance().getContentElem(getElement()).addClassName(getCssContentName());
		
		getButtonBar().getElement().addClassName(CSS_BBAR);
	}

	public void addClassName(String name){
		getElement().addClassName(name);
	}
	
	public String getCssName() {
		return CSS_NAME;
	}
	
	public String getCssHeaderName(){
		return CSS_HEADER_NAME;
	}
	
	public String getCssBodyName(){
		return CSS_BODY_NAME;
	}
	
	public String getCssContentName(){
		return CSS_CONTENT_NAME;
	}
	
	public interface OnButtonClickHandler{
		public void onClick();
	}
	
	public void setSize(int width, int height) {
		super.setSize(width + "px", height + "px");
	}
	
	public DwTextButton addCancelButton(){
		return addCancelButton(new OnButtonClickHandler() {
			@Override
			public void onClick() {
			}
		});
	}
	
	public DwTextButton addCancelButton(final OnButtonClickHandler handler) {
		return addSpecButton(handler, BaseMessages.INSTANCE.cancel());
	}
	
	/**
	 * 
	 * 
	 * Careful with real form submits, as the form needs to be attached during the submit.
	 * 
	 * @param handler
	 * @return
	 */
	public DwTextButton addSubmitButton(final OnButtonClickHandler handler) {
		return addSpecButton(handler, BaseMessages.INSTANCE.submit());
	}
	
	public DwTextButton addSpecButton(final OnButtonClickHandler handler, String label) {
		DwTextButton specBtn = new DwTextButton(label);
		addButton(specBtn);
		specBtn.addSelectHandler(new SelectHandler() {
			@Override
			public void onSelect(SelectEvent event) {
				handler.onClick();
				hide();				
			}
		});
		
		return specBtn;
	}

	public void setHeaderIcon(BaseIcon icon) {
		getHeader().setIcon(icon.toImageResource());
	}
}
