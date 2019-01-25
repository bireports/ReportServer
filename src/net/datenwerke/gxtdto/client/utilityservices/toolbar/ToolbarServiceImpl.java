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
 
 
package net.datenwerke.gxtdto.client.utilityservices.toolbar;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.cell.core.client.ButtonCell.ButtonArrowAlign;
import com.sencha.gxt.cell.core.client.ButtonCell.ButtonScale;
import com.sencha.gxt.cell.core.client.ButtonCell.IconAlign;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.toolbar.LabelToolItem;
import com.sencha.gxt.widget.core.client.toolbar.ToolBar;

import net.datenwerke.gxtdto.client.baseex.widget.btn.DwSplitButton;
import net.datenwerke.gxtdto.client.baseex.widget.btn.DwTextButton;
import net.datenwerke.rs.theme.client.icon.BaseIcon;

/**
 * 
 *
 */
public class ToolbarServiceImpl implements ToolbarService {

	private final Resources resources = GWT.create(Resources.class);
	
	interface Resources extends ClientBundle {
		@Source("toolbar.css")
		Style css();
	}

	interface Style extends CssResource {
		  @ClassName("rs-toolbar-item-plain")
		  String itemPlain();
	}
	
	public ToolbarServiceImpl(){
		super();
		resources.css().ensureInjected();
	}
	
	@Override
	public DwTextButton createSmallButtonLeft(String text, ImageResource icon){
		DwTextButton btn = new DwTextButton(text);
		btn.setIcon(icon);
		btn.setIconAlign(IconAlign.LEFT);
		btn.setArrowAlign(ButtonArrowAlign.RIGHT);
		btn.setScale(ButtonScale.SMALL);
		return btn;
	}
	
	
	@Override
	public DwTextButton createSmallButtonLeft(BaseIcon icon){
		DwTextButton btn = new DwTextButton(icon);
		btn.setIconAlign(IconAlign.LEFT);
		btn.setArrowAlign(ButtonArrowAlign.RIGHT);
		btn.setScale(ButtonScale.SMALL);
		return btn;
	}
	
	@Override
	public DwTextButton createSmallButtonLeft(String text, BaseIcon icon){
		DwTextButton btn = new DwTextButton(text, icon);
		btn.setIconAlign(IconAlign.LEFT);
		btn.setArrowAlign(ButtonArrowAlign.RIGHT);
		btn.setScale(ButtonScale.SMALL);
		return btn;
	}
	
	@Override
	public DwTextButton createLargeButtonLeft(String text, ImageResource icon){
		DwTextButton btn = new DwTextButton(text);
		btn.setIcon(icon);
		btn.setIconAlign(IconAlign.LEFT);
		btn.setArrowAlign(ButtonArrowAlign.BOTTOM);
		btn.setScale(ButtonScale.LARGE);
		return btn;
	}
	
	@Override
	public <D extends TextButton> D configureButton(D button, String text, ImageResource icon) {
		button.setText(text);
		button.setIcon(icon);
		button.setIconAlign(IconAlign.LEFT);
		button.setArrowAlign(ButtonArrowAlign.RIGHT);
		return button;
	}
	
	@Override
	public <D extends TextButton> D configureButton(D button, String text, BaseIcon icon) {
		button.setText(text);
		if(button instanceof DwTextButton)
			((DwTextButton)button).setIcon(icon);
		if(button instanceof DwSplitButton)
			((DwSplitButton)button).setIcon(icon);
		button.setIconAlign(IconAlign.LEFT);
		button.setArrowAlign(ButtonArrowAlign.RIGHT);
		return button;
	}
	
	@Override
	public DwTextButton createLargeButtonTop(String text, ImageResource icon,
			String tooltip) {
		DwTextButton btn = createLargeButtonTop(text, icon);
		btn.setToolTip(tooltip);
		return btn;
	}
	
	@Override
	public DwTextButton createLargeButtonTop(String text, ImageResource icon){
		DwTextButton btn = new DwTextButton(text);
		btn.setIcon(icon);
		btn.setIconAlign(IconAlign.TOP);
		btn.setArrowAlign(ButtonArrowAlign.BOTTOM);
		btn.setScale(ButtonScale.LARGE);
		return btn;
	}
	
	@Override
	public DwTextButton createLargeButtonTop(String text, BaseIcon icon){
		DwTextButton btn;
		if(null != icon)
			btn = new DwTextButton(text, icon);
		else
			btn = new DwTextButton();
		btn.setIconAlign(IconAlign.TOP);
		btn.setArrowAlign(ButtonArrowAlign.BOTTOM);
		btn.setScale(ButtonScale.LARGE);
		return btn;
	}
	
	@Override
	public DwTextButton createPlainToolbarItem(ImageResource icon){
		return createPlainToolbarItem("", icon);
	}
	
	@Override
	public Widget createPlainToolbarItem(BaseIcon icon) {
		return new HTML(icon.toSafeHtml());
	}
	
	
	@Override
	public DwTextButton createPlainToolbarItem(String name, ImageResource icon){
		DwTextButton ib = new DwTextButton(name, icon){ //$NON-NLS-1$
			@Override
			public void onBrowserEvent(Event event) {
				// swallow
			}
		};
	    ib.addStyleName(resources.css().itemPlain());
	    return ib;
	}
	
	@Override
	public DwTextButton createUnstyledToolbarItem(String name, ImageResource icon){
		DwTextButton ib = new DwTextButton(name, icon);
	    ib.addStyleName(resources.css().itemPlain());
	    return ib;
	}
	
	@Override
	public DwTextButton createUnstyledToolbarItem(String name, BaseIcon icon){
		DwTextButton ib = new DwTextButton(name, icon);
	    ib.addStyleName(resources.css().itemPlain());
	    return ib;
	}
	
	@Override
	public void addPlainToolbarItem(ToolBar toolbar, ImageResource icon){
	    toolbar.add(createPlainToolbarItem(icon));
	}
	
	@Override
	public void addPlainToolbarItem(ToolBar toolbar, BaseIcon icon) {
		toolbar.add(createPlainToolbarItem(icon));
	}

	@Override
	public LabelToolItem createText(String text) {
		return new LabelToolItem(text);
	}


}
