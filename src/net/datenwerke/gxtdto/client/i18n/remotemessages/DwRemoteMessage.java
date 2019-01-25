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
 
 
package net.datenwerke.gxtdto.client.i18n.remotemessages;

import com.sencha.gxt.core.client.util.Format;


public abstract class DwRemoteMessage {
	
	protected RemoteMessageCache msgcache = RemoteMessageCache.getInstance();

	public String getMessage(String msgClass, String key, Object... args) {
		String msgtpl = msgcache.getMessage(msgClass, key);
		if(null == args){
			return msgtpl;
		}else{
			return Format.substitute(msgtpl, args);
		}
	}

}