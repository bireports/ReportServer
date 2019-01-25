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
 
 
package net.datenwerke.rs.dashboard.client.dashboard.hooks;

import com.google.gwt.user.client.ui.Widget;

import net.datenwerke.gxtdto.client.forms.simpleform.SimpleForm;
import net.datenwerke.hookhandler.shared.hookhandler.interfaces.Hook;
import net.datenwerke.rs.dashboard.client.dashboard.dto.DadgetDto;
import net.datenwerke.rs.dashboard.client.dashboard.ui.DadgetPanel;
import net.datenwerke.rs.theme.client.icon.BaseIcon;

public interface DadgetProcessorHook extends Hook {

	public interface DadgetConfigureCallback{
		void configuringDone();
		void cancelled();
	}
	
	public BaseIcon getIcon();
	
	public String getTitle();
	
	public String getDescription();
	
	public boolean consumes(DadgetDto dadget);

	public DadgetDto instantiateDadget();

	public void draw(DadgetDto dadget, DadgetPanel panel);

	public Widget getAdminConfigDialog(DadgetDto dadget, SimpleForm form);
	
	public void displayConfigDialog(DadgetDto dadget,
			DadgetConfigureCallback dadgetConfigureCallback);

	public boolean hasConfigDialog();

	public void addTools(DadgetPanel dadgetPanel);

	public boolean isRedrawOnMove();

	public boolean supportsDadgetLibrary();

	public boolean readyToDisplayParameters(DadgetPanel dadgetPanel);
}
