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
 
 
package net.datenwerke.rs.utils.simplequery.byatt;

import java.lang.reflect.ParameterizedType;
import java.util.Collection;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import net.datenwerke.rs.utils.reflection.ReflectionService;
import net.datenwerke.rs.utils.simplequery.SimpleQueryHelper;
import net.datenwerke.rs.utils.simplequery.annotations.QueryByAttribute;

import org.aopalliance.intercept.MethodInvocation;
import org.hibernate.annotations.common.reflection.MetadataProvider;

import com.google.inject.Inject;
import com.google.inject.Provider;

/**
 * 
 *
 */
public class QueryByAttProcessor {

	private final Provider<EntityManager> entityManagerProvider;
	private final ReflectionService reflectionService;
	private final SimpleQueryHelper queryHelper;
	
	@Inject
	public QueryByAttProcessor(
		Provider<EntityManager> entityManagerProvider,
		ReflectionService reflectionService,
		SimpleQueryHelper queryHelper
		){
		
		/* store objects */
		this.entityManagerProvider = entityManagerProvider;
		this.reflectionService = reflectionService;
		this.queryHelper = queryHelper;
	}
	
	public Object process(QueryByAttHandler handler, MethodInvocation invocation) {
		QueryByAttribute metadata = handler.getMetadata();
		Class<?> from = metadata.from();
		if(Void.class.equals(from)){
			from = invocation.getMethod().getReturnType();
			if(reflectionService.isCollection(from))
				from = reflectionService.getGenericType((ParameterizedType)invocation.getMethod().getGenericReturnType());
		}
		
		if(null == from || ! from.isAnnotationPresent(Entity.class))
			throw new IllegalArgumentException("Can only perform queries for entities: " + invocation.getMethod().getName());
		
		EntityManager em = entityManagerProvider.get();
		CriteriaBuilder qb = em.getCriteriaBuilder();
		
		try {
			Class<? extends MetadataProvider> fromMetadata = queryHelper.getMetadataProviderFor(from);

			/* create query */
			CriteriaQuery c = queryHelper.createQuery(qb,from,fromMetadata,metadata.select());
			Root r = c.from(from);
			
			/* distinct */
			c.distinct(metadata.distinct());
			
			/* selection */
			queryHelper.amendSingleProjection(qb, r, c,fromMetadata,metadata.select());
			
			Object providedValue = null;
			if(invocation.getArguments().length > 0)
				providedValue = invocation.getArguments()[0];
			
			Predicate condition = queryHelper.getPredicate(invocation, fromMetadata, qb, r, metadata.type(), metadata.where(), providedValue);
			c.where(condition);
			
			/* order by */
			queryHelper.amendOrderBy(qb, r, c,fromMetadata,metadata.orderBy());
			
			TypedQuery<?> q = em.createQuery(c);
//			q.setFlushMode(FlushModeType.COMMIT);
			
			/* limit and offset */
			if(metadata.limit() > 0)
				q.setMaxResults(metadata.limit());
			if(metadata.offset() > 0)
				q.setFirstResult(metadata.offset());
			
			/* if return type is collection return collection */
			Class<?> returnType = invocation.getMethod().getReturnType();
			if(reflectionService.isCollection(returnType)){
				List result = q.getResultList();
				if(reflectionService.isList(returnType))
					return result;
				Collection<?> resultCol = reflectionService.createCollection(returnType);
				resultCol.addAll(result);
				return resultCol;
			}
			
			Object result = q.getSingleResult();
			return result;
		} catch(NoResultException e){
			if(metadata.throwNoResultException())
				throw e;
			return null;
		} catch (ClassNotFoundException e) {
			throw new IllegalStateException(e);
		} 
	}

}
