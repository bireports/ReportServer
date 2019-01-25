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
 
 
package net.datenwerke.rs.reportdoc.service.helper;

import java.util.Collection;
import java.util.List;

import javax.persistence.EntityManager;

import com.google.inject.Inject;
import com.google.inject.Injector;

import net.datenwerke.rs.terminal.service.terminal.TerminalService;
import net.datenwerke.rs.terminal.service.terminal.TerminalSession;
import net.datenwerke.rs.terminal.service.terminal.objresolver.exceptions.ObjectResolverException;
import net.datenwerke.security.service.security.rights.Right;

public class ReportDocGroovyHelper {

	private final Injector injector;
	private TerminalSession session;

	@Inject
	public ReportDocGroovyHelper(Injector injector) {
		super();
		this.injector = injector;
	}
	
	public Object getInstance(Class<?> type){
		return injector.getInstance(type);
	}
	
	public Object findObject(String objectLocation, Class<? extends Right> ... rights) throws ObjectResolverException{
		TerminalSession session = this.session;
		if(null == session)
			session = (TerminalSession) injector.getInstance(TerminalService.class).getUnscopedTerminalSession();
		return session.getObjectResolver().getObject(objectLocation, rights);
	}
	
	public Collection<Object> findObjects(String objectLocation, Class<? extends Right> ... rights) throws ObjectResolverException{
		TerminalSession session = this.session;
		if(null == session)
			session = (TerminalSession) injector.getInstance(TerminalService.class).getUnscopedTerminalSession();
		return session.getObjectResolver().getObjects(objectLocation, rights);
	}
	
	public <T> List<T> getEntitiesByType(Class<T> type){
		return injector.getInstance(EntityManager.class).createQuery("FROM " + type.getSimpleName()).getResultList();
	}

	public void setSession(TerminalSession session) {
		this.session = session;
	}
}
