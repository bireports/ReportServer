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
 
 
package net.datenwerke.rs.remoteaccess.service.sftp.mockup;

import net.datenwerke.rs.utils.mockrequest.MockHttpSessionWrapper;
import net.datenwerke.rs.utils.mockrequest.MockServletRequest;

import org.apache.sshd.server.session.ServerSession;

public class SftpServletRequest extends MockServletRequest {

	public void setSftpSession(ServerSession session) {
		if(getWrapperSession() instanceof MockHttpSessionWrapper)
			((MockHttpSessionWrapper)this.getWrapperSession()).setSession(session);
	}
}
