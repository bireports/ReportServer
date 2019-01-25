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
 
 
package net.datenwerke.rs.incubator.service.versioning;

import java.util.Set;

import net.datenwerke.rs.incubator.service.versioning.entities.Revision;

import org.hibernate.envers.AuditReader;

public interface VersioningService {

	/**
	 * Returns a {@link Set} of all {@link Revision}s of the given {@link Object}
	 * 
	 * @param object The {@link Object}
	 * @return A {@link Set} of {@link Revision}s
	 */
	public Set<Revision> getRevisions(Object object);
	public Set<Revision> getRevisions(Set<Number> revisionNumbers);
	
	/**
	 * Returns an instance of the given {@link Object} at the given <i>revision</i>. The
	 * result gets casted to the {@link Class} <i>T</i>
	 * 
	 * @param <T> The class to cast to
	 * @param clazz The {@link Class} object
	 * @param object The {@link Object}
	 * @param revision The <i>revision</i> as a {@link Number}
	 * @return An instance of the given {@link Object} with the data of the given <i>revision</i>
	 */
	public <T> T getAtRevision(Class<T> clazz, Object object, Number revision);
	
	public <T> T getAtRevision(Class<T> clazz, long objectId, Number revision);
	
	/**
	 * Returns a {@link Set} of {@link Number}s identified all revisions of the given {@link Object}
	 * 
	 * @param object The {@link Object} to get the revisions from
	 * @return A {@link Set} holding the revision {@link Number}s
	 */
	public Set<Number> getRevisionNumbers(Object object);
	public Set<Number> getRevisionNumbers(Class<?> clazz, long objectId);
	
	/**
	 * Returns the used {@link AuditReader}
	 * @return The used {@link AuditReader}
	 */
	public AuditReader getAuditReader();


	

}
