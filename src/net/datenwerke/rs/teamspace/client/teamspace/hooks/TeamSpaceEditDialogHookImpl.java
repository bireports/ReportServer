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
 
 
package net.datenwerke.rs.teamspace.client.teamspace.hooks;

import net.datenwerke.gxtdto.client.utilityservices.submittracker.SubmitTrackerToken;
import net.datenwerke.rs.teamspace.client.teamspace.dto.TeamSpaceDto;

public abstract class TeamSpaceEditDialogHookImpl implements
		TeamSpaceEditDialogHook {

	protected TeamSpaceDto teamSpace;
	
	@Override
	public void setCurrentSpace(TeamSpaceDto teamSpace) {
		this.teamSpace = teamSpace;
	}

	@Override
	public int getHeight() {
		return 480;
	}

	@Override
	public void cancelPressed() {
	}

	@Override
	public final void submitPressed() {
	}
	
	@Override
	public void submitPressed(SubmitTrackerToken submitTrackerToken) {
		submitTrackerToken.setCompleted();
	}
	
	@Override
	public boolean applies(TeamSpaceDto teamSpace) {
		return true;
	}
	
	@Override
	public String isValid() {
		return null;
	}

}
