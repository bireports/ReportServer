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
 
 
package net.datenwerke.rs.search.service.search;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.metamodel.EntityType;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;
import com.google.inject.Provider;

import net.datenwerke.gf.base.service.annotations.Indexed;
import net.datenwerke.hookhandler.shared.hookhandler.HookHandlerService;
import net.datenwerke.rs.search.service.search.hooks.SearchProvider;
import net.datenwerke.rs.search.service.search.index.SearchIndexService;
import net.datenwerke.rs.search.service.search.results.SearchFilter;
import net.datenwerke.rs.search.service.search.results.SearchResultEntry;
import net.datenwerke.rs.search.service.search.results.SearchResultList;
import net.datenwerke.rs.search.service.search.results.SearchResultTag;
import net.datenwerke.rs.utils.config.ConfigService;
import net.datenwerke.rs.utils.eventbus.EventBus;
import net.datenwerke.rs.utils.eventbus.EventHandler;
import net.datenwerke.rs.utils.jpa.EntityUtils;
import net.datenwerke.rs.utils.reflection.ReflectionService;
import net.datenwerke.security.service.eventlogger.jpa.AfterMergeEntityEvent;
import net.datenwerke.security.service.eventlogger.jpa.AfterPersistEntityEvent;
import net.datenwerke.security.service.eventlogger.jpa.JpaEvent;

/**
 * 
 *
 */
public class SearchServiceImpl implements SearchService {
	
	private static final String CONFIG_FILE = "main/main.cf";

	public static final String SEARCH_QUERY_TIMEOUT = "search.timeout";
	
	private final Logger logger = LoggerFactory.getLogger(getClass().getName());
	
	private final Provider<EntityManager> entityManagerProvider;
	private final EntityUtils entityUtils;
	private final ReflectionService reflectionService;
	private final HookHandlerService hookHandler;
	private ConfigService configService;
	
	private SearchIndexService searchIndexService;
	
	private final HashMap<Class<?>, Set<String>> fieldCache = new HashMap<Class<?>, Set<String>>();

	private EventBus eventBus;

	
	@Inject
	public SearchServiceImpl(
			Provider<EntityManager> emp,
			EntityUtils entityUtils,
			ReflectionService reflectionService,
			HookHandlerService hookHandler, 
			ConfigService configService, 
			final SearchIndexService searchIndexService, 
			EventBus eventBus
		) {
		
		/* store objects */
		this.entityManagerProvider = emp;
		this.entityUtils = entityUtils;
		this.reflectionService = reflectionService;
		this.hookHandler = hookHandler;
		this.configService = configService;
		this.searchIndexService = searchIndexService;
		this.eventBus = eventBus;
		
		
		eventBus.attachEventHandler(JpaEvent.class, new EventHandler<JpaEvent>(){
			@Override
			public void handle(JpaEvent event) {
				if(event instanceof AfterPersistEntityEvent || event instanceof AfterMergeEntityEvent) {
					searchIndexService.addToIndex(event.getObject());
					searchIndexService.commit();
				}
			}
		});
	}
	
	
	@Override
	public String rebuildIndex() {
		logger.info("Rebuilding search index...");
		long cnt = 0;
		long classCount = 0;
		long start = System.currentTimeMillis();
		long lastlogged = start;
		searchIndexService.flushIndex();
		
		
		long logInterval = 30000; 
		
		List<Class<?>> allEntityClasses = entityUtils.getAllEntityClasses();
		for(Class c : allEntityClasses){
			classCount++;
			if(c.isAnnotationPresent(Indexed.class)){
				List resultList = entityManagerProvider.get().createQuery("FROM " + c.getSimpleName()).getResultList();
				for(Object o : resultList){
					searchIndexService.addToIndex(o);
					cnt++;
					
					if(lastlogged + logInterval < System.currentTimeMillis()){
						logger.info("Rebuilding search index (entity: " + classCount + "/" + allEntityClasses.size() + ", index-size: " + cnt + ", current: " + c.getSimpleName()+")");
						lastlogged = System.currentTimeMillis();
					}
				}
			}
		}
		
		searchIndexService.commit();
		
		if(lastlogged + logInterval < System.currentTimeMillis())
			logger.info("Index update completed");
		
		double time = (System.currentTimeMillis() - start) / 1000d;
		return "Reindexed " + cnt + " entities in " + time;
	}
	
	
	public List<?> locate(String query) {
		return locate(Object.class, query);
	}
	
	@Override
	public List<?> locate(Class<?> clazz, String query){
		return searchIndexService.locate(clazz, query);
	}
	

	@Override
	public SearchResultList findAsResultList(String query){
		return findAsResultList(query, new SearchFilter());
	}

	@Override
	public SearchResultList findAsResultList(String query, SearchFilter filter){
		SearchResultList resultList = new SearchResultList();
		
		Map<Object, Integer> lookupMap = new HashMap<Object, Integer>();
		
		List<SearchResultEntry> overallEntryList = new ArrayList<SearchResultEntry>();
		int index = 0;
		for(SearchProvider provider : hookHandler.getHookers(SearchProvider.class)){
			List<SearchResultEntry> entryList = provider.search(query, filter);
			
			if(overallEntryList.isEmpty()){
				overallEntryList.addAll(entryList);
				
				/* fill lookup table */
				for(SearchResultEntry e : entryList){
					if(null != e.getResultObject())
						lookupMap.put(e.getResultObject(), index);
					index++;
				}
			} else {
				for(SearchResultEntry e : entryList){
					if(null == e.getResultObject() || ! lookupMap.containsKey(e.getResultObject())){
						overallEntryList.add(e);
						index++;
					} else {
						SearchResultEntry prevEntry = overallEntryList.get(index);
						combine(prevEntry, e);
					}
				}
			}
		}

		Set<SearchResultTag> tagSet = new HashSet<SearchResultTag>();
		for(SearchResultEntry e : overallEntryList)
			tagSet.addAll(e.getTags());
		resultList.setTags(tagSet);
		
		/* filter by tags */
		if(! filter.getTags().isEmpty()){
			Iterator<SearchResultEntry> it = overallEntryList.iterator();
			while(it.hasNext()){
				if(! CollectionUtils.containsAny(filter.getTags(), it.next().getTags()))
					it.remove();
			}
		}
		
		resultList.setTotalLength(overallEntryList.size());
		
		if(overallEntryList.size() > filter.getOffset()){
			int toIndex = Math.min(overallEntryList.size(), filter.getLimit() + filter.getOffset());
			resultList.setData(overallEntryList.subList(filter.getOffset(), toIndex));

			resultList.setOffset(filter.getOffset());
		}
		
		
		return resultList;
	}

	
	private void combine(SearchResultEntry prevEntry, SearchResultEntry e) {
		for(SearchResultTag tag : e.getTags())
			prevEntry.addTag(tag);
	}


	private Set<String> getAllIndexedFields(Class<?> clazz){
		if(null == clazz)
			return new HashSet<String>();
		
		if(! fieldCache.containsKey(clazz)){
			Set<String> fieldNames = new HashSet<String>();
			for(EntityType<?>  et : entityUtils.getMetaModel().getEntities()){
				Class<?> jt = et.getJavaType();
				if(null != jt && clazz.isAssignableFrom(jt)){
					for(Field f : reflectionService.getFieldsByAnnotation(et.getJavaType(), net.datenwerke.gf.base.service.annotations.Field.class))
						fieldNames.add(f.getName());
					
				}
			}
			fieldCache.put(clazz, fieldNames);
		}
	
		return fieldCache.get(clazz);
	}
	
	protected int getSearchTimeout() {
		return configService.getConfigFailsafe(CONFIG_FILE).getInt(SEARCH_QUERY_TIMEOUT, 5000);
	}


}
