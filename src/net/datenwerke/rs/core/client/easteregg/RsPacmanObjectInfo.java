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
 
 
package net.datenwerke.rs.core.client.easteregg;

import java.util.ArrayList;
import java.util.List;

import net.datenwerke.gxtdto.client.baseex.widget.DwContentPanel;
import net.datenwerke.gxtdto.client.objectinformation.hooks.ObjectPreviewTabProviderHook;
import net.datenwerke.rs.core.client.reportmanager.dto.interfaces.ReportVariantDto;
import net.datenwerke.rs.core.client.reportmanager.dto.reports.ReportDto;

import com.sencha.gxt.widget.core.client.Component;

public class RsPacmanObjectInfo implements ObjectPreviewTabProviderHook {

	@Override
	public boolean consumes(Object object) {
		return object instanceof ReportVariantDto && 
				object instanceof ReportDto && 
				null != ((ReportDto)object).getName() && 
						((ReportDto)object).getName().endsWith("LET_ME_PLAY_PACMAN");
	}


	@Override
	public List<PreviewComponent> getInfoComponents(Object object)  {
		List<ObjectPreviewTabProviderHook.PreviewComponent> list = new ArrayList<ObjectPreviewTabProviderHook.PreviewComponent>();
		list.add(new PreviewComponent() {

			@Override
			public String getInfoName() {
				return "Pacman";
			}
			@Override
			public Component getInfoComponent(Object object) {
				DwContentPanel panel = new DwContentPanel();
				panel.setHeaderVisible(false);
				
				panel.setUrl("resources/ee/pm/index.html");
				
				return panel;
			}
		});
		
		return list;

	}

}
