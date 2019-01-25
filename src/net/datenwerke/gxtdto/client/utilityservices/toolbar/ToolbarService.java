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

import net.datenwerke.gxtdto.client.baseex.widget.btn.DwTextButton;
import net.datenwerke.rs.theme.client.icon.BaseIcon;

import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.widget.core.client.Component;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.toolbar.ToolBar;

public interface ToolbarService {

	public DwTextButton createSmallButtonLeft(BaseIcon icon);
	public DwTextButton createSmallButtonLeft(String text, BaseIcon icon);
	public DwTextButton createSmallButtonLeft(String text, ImageResource icon);
	
	public DwTextButton createLargeButtonLeft(String text, ImageResource icon);
	public DwTextButton createLargeButtonTop(String text, ImageResource icon);
	public DwTextButton createLargeButtonTop(String text, ImageResource icon, String tooltip);

	public <D extends TextButton> D configureButton(D button, String text, ImageResource icon);
	public <D extends TextButton> D configureButton(D button, String text, BaseIcon icon);
	
	public void addPlainToolbarItem(ToolBar toolbar, ImageResource icon);

	DwTextButton createPlainToolbarItem(ImageResource icon);

	DwTextButton createPlainToolbarItem(String name, ImageResource icon);

	public Component createText(String views);

	public DwTextButton createLargeButtonTop(String label, BaseIcon icon);

	DwTextButton createUnstyledToolbarItem(String name, ImageResource icon);
	DwTextButton createUnstyledToolbarItem(String name, BaseIcon icon);

	public void addPlainToolbarItem(ToolBar toolbar, BaseIcon icon);

	Widget createPlainToolbarItem(BaseIcon icon);
	
	
}