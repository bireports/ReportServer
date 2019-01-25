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
 
 
package net.datenwerke.rs.utils.mockrequest;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionContext;

import org.apache.sshd.server.session.ServerSession;

public class MockHttpSessionWrapper implements HttpSession {

	public static final Map<String, Map<String, Object>> valueMap = new HashMap<String, Map<String,Object>>();
	
	private ServerSession session;

	public void setSession(ServerSession session){
		this.session = session;
	}
	
	@Override
	public Object getAttribute(String key) {
		if(valueMap.containsKey(new String(session.getSessionId())))
			return valueMap.get(new String(session.getSessionId())).get(key);
		return null;
	}

	@Override
	public Enumeration getAttributeNames() {
		return null;
	}

	@Override
	public long getCreationTime() {
		return 0;
	}

	@Override
	public String getId() {
		return new String(session.getSessionId());
	}

	@Override
	public long getLastAccessedTime() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getMaxInactiveInterval() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public ServletContext getServletContext() {
		return null;
	}

	@Override
	public HttpSessionContext getSessionContext() {
		return null;
	}

	@Override
	public Object getValue(String key) {
		if(valueMap.containsKey(new String(session.getSessionId())))
			return valueMap.get(new String(session.getSessionId())).get(key);
		return null;
	}

	@Override
	public String[] getValueNames() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void invalidate() {
		if(valueMap.containsKey(new String(session.getSessionId())))
			valueMap.get(new String(session.getSessionId())).clear();
	}

	@Override
	public boolean isNew() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void putValue(String key, Object value) {
		if(! valueMap.containsKey(new String(session.getSessionId())))
			valueMap.put(new String(session.getSessionId()), new HashMap<String, Object>());
		valueMap.get(new String(session.getSessionId())).put(key,value);
	}

	@Override
	public void removeAttribute(String key) {
		if(valueMap.containsKey(new String(session.getSessionId())))
			valueMap.get(new String(session.getSessionId())).remove(key);
	}

	@Override
	public void removeValue(String arg0) {

	}

	@Override
	public void setAttribute(String key, Object value) {
		if(! valueMap.containsKey(new String(session.getSessionId())))
			valueMap.put(new String(session.getSessionId()), new HashMap<String, Object>());
		
		valueMap.get(new String(session.getSessionId())).put(key, value);
	}

	@Override
	public void setMaxInactiveInterval(int arg0) {
		// TODO Auto-generated method stub

	}

}
