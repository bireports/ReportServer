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
 
 
package net.datenwerke.rs.tsreportarea.client.tsreportarea.objectinfo;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.inject.Inject;

import net.datenwerke.gxtdto.client.baseex.widget.DwContentPanel;
import net.datenwerke.gxtdto.client.dtomanager.callback.RsAsyncCallback;
import net.datenwerke.gxtdto.client.objectinformation.hooks.ObjectInfoAdditionalInfoProvider;
import net.datenwerke.gxtdto.client.ui.helper.info.InfoWindow;
import net.datenwerke.rs.core.client.reportmanager.dto.reports.ReportDto;
import net.datenwerke.rs.teamspace.client.teamspace.dto.TeamSpaceDto;
import net.datenwerke.rs.tsreportarea.client.tsreportarea.TsDiskDao;
import net.datenwerke.rs.tsreportarea.client.tsreportarea.dto.AbstractTsDiskNodeDto;
import net.datenwerke.rs.tsreportarea.client.tsreportarea.dto.TsDiskFolderDto;
import net.datenwerke.rs.tsreportarea.client.tsreportarea.dto.TsDiskGeneralReferenceDto;
import net.datenwerke.rs.tsreportarea.client.tsreportarea.dto.TsDiskReportReferenceDto;
import net.datenwerke.rs.tsreportarea.client.tsreportarea.dto.TsDiskRootDto;
import net.datenwerke.rs.tsreportarea.client.tsreportarea.locale.TsFavoriteMessages;

/**
 * 
 *
 */
public class ReportInTeamSpaceObjectInfo implements ObjectInfoAdditionalInfoProvider {

	private final TsDiskDao favoriteDao;
	
	@Inject
	public ReportInTeamSpaceObjectInfo(
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
		
		favoriteDao.getTeamSpacesWithPathsThatLinkTo((ReportDto)object, new RsAsyncCallback<Map<TeamSpaceDto, List<List<AbstractTsDiskNodeDto>>>>(){
			@Override
			public void onSuccess(Map<TeamSpaceDto, List<List<AbstractTsDiskNodeDto>>> result) {
				panel.clear();
				panel.enableScrollContainer();
				
				if(result.isEmpty())
					panel.add(new Label(TsFavoriteMessages.INSTANCE.reportNotInTeamSpacesMessages()));
				else {
					SafeHtmlBuilder builder = new SafeHtmlBuilder();
					builder.appendHtmlConstant("<div class=\"rs-infopanel-reportinteamspace\"><ol>");
					Iterator<TeamSpaceDto> it = result.keySet().iterator();
					while (it.hasNext()) {
						TeamSpaceDto ts = it.next();
						builder.appendHtmlConstant("<li>").appendEscaped(ts.getName() + " (" + ts.getId() + ")");
						builder.appendHtmlConstant("<ul>");
						List<List<AbstractTsDiskNodeDto>> paths = result.get(ts);
						for (List<AbstractTsDiskNodeDto> path: paths) {
							StringBuilder pathStr = new StringBuilder();
							AbstractTsDiskNodeDto lastNode = path.get(path.size()-1);
							for (AbstractTsDiskNodeDto node: path) {
								if (node instanceof TsDiskFolderDto ) {
									pathStr.append(((TsDiskFolderDto)node).getName());
								} else if (node instanceof TsDiskRootDto) {
									pathStr.append(((TsDiskRootDto)node).getName());
								} else if (node instanceof TsDiskGeneralReferenceDto) {
									pathStr.append(((TsDiskGeneralReferenceDto)node).getName());
								} else {
									pathStr.append(node.getId());
								}
								if (node != lastNode) {
									pathStr.append("/");
								}
							}
							builder.appendHtmlConstant("<li>").appendEscaped(pathStr.toString()).appendHtmlConstant("</li>");
						}
						builder.appendHtmlConstant("</ul></li>");
					}
					builder.appendHtmlConstant("</ol></div>");
					
					panel.add(new HTML(builder.toSafeHtml()));
				}
					
				panel.forceLayout();
			}
		});
	}

}
