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
 
 
package net.datenwerke.rs.authenticator.client.login.pam;

import java.util.List;

import net.datenwerke.security.client.login.AuthToken;

import com.sencha.gxt.widget.core.client.container.FlowLayoutContainer;

public interface ClientPAM {

	public interface ProceeedCallback{
		public void submit();
	}
	
	public void addFields(FlowLayoutContainer container, ProceeedCallback callback);
	
	public void addResult(List<AuthToken> results, List<ClientPAM> next);
	
	public String getModuleName();
	
}
