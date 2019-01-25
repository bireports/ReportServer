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
 
 
package net.datenwerke.gf.client.history;

import net.datenwerke.gf.client.history.dto.HistoryLinkDto;
import net.datenwerke.gxtdto.client.dtomanager.Dto;

import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.sencha.gxt.widget.core.client.menu.MenuItem;

public interface HistoryUiService extends ValueChangeHandler<String>{
	
	public interface JumpToObjectResultCallback{
		void setResult(Dto dto);
	}
	
	public interface JumpToObjectCallback{
		void getDtoTarget(JumpToObjectResultCallback callback);

		boolean haveToUpdate();
	}
	
	public abstract class JumpToObjectCallbackImpl implements JumpToObjectCallback{
		private Dto lastSelection;
		
		@Override
		final public boolean haveToUpdate() {
			return null == getDtoTarget() || lastSelection != getDtoTarget();
		}
		
		@Override
		final public void getDtoTarget(JumpToObjectResultCallback callback) {
			lastSelection = getDtoTarget();
			callback.setResult(lastSelection);
		}
		
		public abstract Dto getDtoTarget();
	}
	
	public void addHistoryCallback(String location, HistoryCallback callback);

	public MenuItem getJumpToMenuItem(JumpToObjectCallback jumpToObjectCallback);

	void fire(HistoryLinkDto link);

	public void jumpTo(Dto node);

}
