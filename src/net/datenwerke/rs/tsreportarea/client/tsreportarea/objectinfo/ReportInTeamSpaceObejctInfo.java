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
 
 
package net.datenwerke.rs.tsreportarea.client.tsreportarea.objectinfo;

import java.util.List;

import net.datenwerke.gxtdto.client.baseex.widget.DwContentPanel;
import net.datenwerke.gxtdto.client.dtomanager.callback.RsAsyncCallback;
import net.datenwerke.gxtdto.client.objectinformation.hooks.ObjectInfoAdditionalInfoProvider;
import net.datenwerke.gxtdto.client.ui.helper.info.InfoWindow;
import net.datenwerke.rs.core.client.reportmanager.dto.reports.ReportDto;
import net.datenwerke.rs.teamspace.client.teamspace.dto.TeamSpaceDto;
import net.datenwerke.rs.tsreportarea.client.tsreportarea.TsDiskDao;
import net.datenwerke.rs.tsreportarea.client.tsreportarea.locale.TsFavoriteMessages;

import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.inject.Inject;

/**
 * 
 *
 */
public class ReportInTeamSpaceObejctInfo implements ObjectInfoAdditionalInfoProvider {

	private final TsDiskDao favoriteDao;
	
	@Inject
	public ReportInTeamSpaceObejctInfo(
		TsDiskDao favoriteDao	
		){
		
		/* store object */
		this.favoriteDao = favoriteDao;
	}
	
	@Override
	public boolean consumes(Object object) {
		return object instanceof ReportDto;
	}

	@Override
	public void addInfoFor(Object object, InfoWindow window) {
		final DwContentPanel panel = window.addDelayedSimpelInfoPanel("TeamSpace");
		
		favoriteDao.getTeamSpacesWithReferenceTo((ReportDto)object, new RsAsyncCallback<List<TeamSpaceDto>>(){
			@Override
			public void onSuccess(List<TeamSpaceDto> result) {
				panel.clear();
				
				if(result.isEmpty())
					panel.add(new Label(TsFavoriteMessages.INSTANCE.reportNotInTeamSpacesMessages()));
				else {
					SafeHtmlBuilder builder = new SafeHtmlBuilder();
					builder.appendHtmlConstant("<div class=\"rs-infopanel-reportinteamspace\"><ul>");
					for(TeamSpaceDto ts : result)
						builder.appendHtmlConstant("<li>").appendEscaped(ts.getName() + " (" + ts.getId() + ")").appendHtmlConstant("</li>");
					builder.appendHtmlConstant("</ul></div>");
					
					panel.add(new HTML(builder.toSafeHtml()));
				}
					
				panel.forceLayout();
			}
		});
	}

}
