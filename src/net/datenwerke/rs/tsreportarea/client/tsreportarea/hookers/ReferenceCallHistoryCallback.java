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
 
 
package net.datenwerke.rs.tsreportarea.client.tsreportarea.hookers;

import java.util.Collection;

import net.datenwerke.gf.client.history.HistoryCallback;
import net.datenwerke.gf.client.history.HistoryLocation;
import net.datenwerke.gxtdto.client.dtomanager.callback.RsAsyncCallback;
import net.datenwerke.gxtdto.client.eventbus.events.SubmoduleDisplayRequest;
import net.datenwerke.gxtdto.client.eventbus.events.SubmoduleDisplayRequest.ParentDisplayedCallback;
import net.datenwerke.rs.teamspace.client.teamspace.TeamSpaceApp;
import net.datenwerke.rs.teamspace.client.teamspace.TeamSpaceUIModule;
import net.datenwerke.rs.tsreportarea.client.tsreportarea.TsDiskTeamSpaceApp;
import net.datenwerke.rs.tsreportarea.client.tsreportarea.TsDiskTreeLoaderDao;
import net.datenwerke.rs.tsreportarea.client.tsreportarea.dto.AbstractTsDiskNodeDto;
import net.datenwerke.rs.tsreportarea.client.tsreportarea.dto.decorator.AbstractTsDiskNodeDtoDec;
import net.datenwerke.rs.tsreportarea.client.tsreportarea.ui.TsDiskMainComponent;
import net.datenwerke.treedb.client.treedb.dto.AbstractNodeDto;

import com.google.gwt.event.shared.EventBus;
import com.google.inject.Inject;

public class ReferenceCallHistoryCallback implements HistoryCallback {

	private final TsDiskTreeLoaderDao diskDao;
	private final EventBus eventbus;

	@Inject
	public ReferenceCallHistoryCallback(
		TsDiskTreeLoaderDao diskDao,
		final EventBus eventbus
		){
		
		this.diskDao = diskDao;
		this.eventbus = eventbus;
	}
	
	@Override
	public void locationChanged(final HistoryLocation location) {
		String strId = location.getParameterValue("id");
		try{
			final Long id = Long.valueOf(strId);
			
			
			diskDao.loadNodeById(id, new RsAsyncCallback<AbstractNodeDto>(){
				@Override
				public void onSuccess(final AbstractNodeDto result) {
					if(null == result)
						return;

					Long tsId = ((AbstractTsDiskNodeDtoDec)result).getTeamSpaceId();
					if(null == tsId)
						return;
					
					SubmoduleDisplayRequest req = new SubmoduleDisplayRequest(null, TeamSpaceUIModule.TEAMSPACE_PANEL_ID);
					req.addParameter(TeamSpaceUIModule.TEAMSPACE_ID_KEY, tsId);
					req.setCallback(new ParentDisplayedCallback() {
						@Override
						public void notify(Object obj) {
							Collection<TeamSpaceApp> apps = (Collection<TeamSpaceApp>) obj;
							for(final TeamSpaceApp app : apps){
								if(app instanceof TsDiskTeamSpaceApp){
									((TsDiskMainComponent)app.getAppComponent()).itemOpened((AbstractTsDiskNodeDto) result);
									break;
								}
							}
						}
					});
					
					eventbus.fireEvent(req);
				}
			});
		} catch(NumberFormatException e){
			return;
		}
		
		
		
		
	}

}
