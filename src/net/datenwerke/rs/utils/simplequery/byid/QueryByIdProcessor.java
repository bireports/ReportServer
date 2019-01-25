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
 
 
package net.datenwerke.rs.utils.simplequery.byid;

import java.lang.reflect.Field;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import net.datenwerke.rs.utils.jpa.EntityUtils;
import net.datenwerke.rs.utils.simplequery.annotations.QueryById;

import org.aopalliance.intercept.MethodInvocation;

import com.google.inject.Inject;
import com.google.inject.Provider;

/**
 * 
 *
 */
public class QueryByIdProcessor {

	private final Provider<EntityManager> entityManagerProvider;
	private final EntityUtils entityUtils;
	
	@Inject
	public QueryByIdProcessor(
		Provider<EntityManager> entityManagerProvider,
		EntityUtils entityUtils
		){
		
		/* store objects */
		this.entityManagerProvider = entityManagerProvider;
		this.entityUtils = entityUtils;
	}
	
	public Object process(QueryByIdHandler handler, MethodInvocation invocation) {
		QueryById metadata = handler.getMetadata();
		Class<?> from = metadata.from();
		if(Void.class.equals(from))
			from = invocation.getMethod().getReturnType();
		
		if(! from.isAnnotationPresent(Entity.class))
			throw new IllegalArgumentException("Can only perform queries for entities");
		
		EntityManager em = entityManagerProvider.get();
		CriteriaBuilder qb = em.getCriteriaBuilder();
		
		CriteriaQuery c = qb.createQuery(from);
		Root r = c.from(from);
		
		Field idField;
		try {
			idField = entityUtils.getIdField(from);
			if(null == idField)
				throw new IllegalArgumentException("Could not find ID field on " + from);
			Object providedId = invocation.getArguments()[0];
			Predicate condition = qb.equal(r.get(idField.getName()), providedId);
			c.where(condition);
			
			TypedQuery<?> q = em.createQuery(c);
//			q.setFlushMode(FlushModeType.COMMIT);
			
			Object result = q.getSingleResult();
			return result;
		} catch(NoResultException e){
			return null;
		}
	}

}
