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
 
 
package net.datenwerke.gf.client.homepage.ui;

import java.util.List;

import net.datenwerke.gf.client.homepage.hooks.HomepageHeaderContentHook;
import net.datenwerke.gf.client.homepage.modules.ui.ModuleManagerModuleSelector;
import net.datenwerke.gf.client.theme.ThemeUiService;
import net.datenwerke.gf.client.theme.dto.ThemeUiConfig;
import net.datenwerke.gxtdto.client.baseex.widget.DwContentPanel;
import net.datenwerke.gxtdto.client.theme.CssClassConstant;
import net.datenwerke.hookhandler.shared.hookhandler.HookHandlerService;

import com.google.gwt.dom.client.Style.Cursor;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.inject.Inject;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.core.client.util.Padding;
import com.sencha.gxt.widget.core.client.container.BoxLayoutContainer.BoxLayoutData;
import com.sencha.gxt.widget.core.client.container.BoxLayoutContainer.BoxLayoutPack;
import com.sencha.gxt.widget.core.client.container.HBoxLayoutContainer;
import com.sencha.gxt.widget.core.client.container.HBoxLayoutContainer.HBoxLayoutAlign;
import com.sencha.gxt.widget.core.client.toolbar.FillToolItem;

/**
 * A panel divided into 3 columns
 * 
 *
 */
public class HeaderPanel extends DwContentPanel {

	@CssClassConstant
	public static final String CSS_NAME = "rs-header";
	
	@CssClassConstant
	public static final String CSS_HEADER_SEP = "rs-header-r-sep";
	
	@CssClassConstant
	public static final String CSS_HEADER_TEXT = "rs-header-r-text";
	
	@CssClassConstant
	public static final String CSS_LOGO_NAME = "rs-logo";
	
	private final HookHandlerService hookHandler;
	private final ModuleManagerModuleSelector moduleSelector;

	private ThemeUiService themeService;
	
	@Inject
	public HeaderPanel(
		HookHandlerService hookHandler,
		ModuleManagerModuleSelector moduleSelector,
		ThemeUiService themeService
		) {
		
		/* store objects */
		this.hookHandler = hookHandler;
		this.moduleSelector = moduleSelector;
		this.themeService = themeService;
		
		initializeUI();
	}
	
	@Override
	public String getCssName() {
		return super.getCssName() + " " + CSS_NAME;
	}
	
	public static String getCssLogoName() {
		return CSS_LOGO_NAME;
	}
	
	private void initializeUI() {
		setHeaderVisible(false);
		setBodyBorder(false);
		setBorders(false);
		
		HBoxLayoutContainer container = new HBoxLayoutContainer();
		container.setEnableOverflow(false);
		container.setPack(BoxLayoutPack.START);
		container.setPadding(new Padding(0, 15, 0, 15));
		container.setAllowTextSelection(false);
		container.setHBoxLayoutAlign(HBoxLayoutAlign.MIDDLE);
		add(container);
		
		HTML logo = themeService.getHeaderLogo();
		container.add(logo);
		
		/* fill ponels */ 
		List<HomepageHeaderContentHook> hookers = hookHandler.getHookers(HomepageHeaderContentHook.class);
		moduleSelector.addModules(container);
		
		container.add(new FillToolItem());

		boolean first = true;
		for(HomepageHeaderContentHook hooker : hookers){
			final DwMainViewportTopBarElement tbElement = hooker.homepageHeaderContentHook_addTopRight(container);
			if(null == tbElement)
				continue;
			
			if(first)
				first = false;
			else {
				Label sep = new Label("|");
				sep.setStyleName(CSS_HEADER_SEP);
				container.add(sep, new BoxLayoutData(new Margins(0, 5, 0, 5)));
			}
			
			ClickHandler elClickHandler = new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					tbElement.onClick();					
				}
			};
			
			if(tbElement instanceof DwMainViewportTopBarWidget){
				container.add(((DwMainViewportTopBarWidget)tbElement).getComponent());
			} else {
				String textLabel = tbElement.getName();
				
				Label text = new Label(textLabel);
				text.addStyleName(CSS_HEADER_TEXT);
				text.getElement().getStyle().setCursor(Cursor.POINTER);
				text.addDomHandler(elClickHandler, ClickEvent.getType());
				container.add(text);
			}
		}
	}


}
