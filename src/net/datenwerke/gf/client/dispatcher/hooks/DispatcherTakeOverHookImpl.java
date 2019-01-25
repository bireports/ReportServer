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
 
 
package net.datenwerke.gf.client.dispatcher.hooks;

import net.datenwerke.gf.client.history.HistoryLocation;

import com.google.gwt.user.client.Window;

public abstract class DispatcherTakeOverHookImpl implements
		DispatcherTakeOverHook {

	@Override
	public boolean isActive() {
		HistoryLocation hLocation = getHistoryLocation();
		return getLocation().equals(hLocation.getLocation()) && checkParameters(hLocation);
	}
	
	protected boolean checkParameters(HistoryLocation hLocation) {
		return true;
	}

	public HistoryLocation getHistoryLocation(){
		String hash = Window.Location.getHash();
		if(null == hash || ! hash.startsWith("#"))
			return new HistoryLocation();
		
		HistoryLocation location = HistoryLocation.fromString(hash.substring(1));

		return location;
	}

	public abstract String getLocation();
	

}
