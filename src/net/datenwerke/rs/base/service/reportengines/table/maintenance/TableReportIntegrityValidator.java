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
 
 
package net.datenwerke.rs.base.service.reportengines.table.maintenance;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import net.datenwerke.gf.service.maintenance.hooks.MaintenanceTask;
import net.datenwerke.rs.base.service.reportengines.table.entities.Column;
import net.datenwerke.rs.base.service.reportengines.table.entities.FilterRange;
import net.datenwerke.rs.base.service.reportengines.table.entities.filters.Filter;
import net.datenwerke.rs.base.service.reportengines.table.entities.filters.FilterBlock;
import net.datenwerke.rs.base.service.reportengines.table.entities.filters.FilterSpec;

import org.hibernate.SQLQuery;
import org.hibernate.ejb.HibernateEntityManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;
import com.google.inject.Provider;

public class TableReportIntegrityValidator implements MaintenanceTask{

	private final Logger logger = LoggerFactory.getLogger(getClass().getName());
	

	private final Provider<EntityManager> entityManagerProvider;

	@Inject
	public TableReportIntegrityValidator(
			Provider<EntityManager> entityManagerProvider) {

		this.entityManagerProvider = entityManagerProvider;
	}

	@Override
	public void performMaintenance() {
		try{
			/* get entity manager and flush changes so that cascades were processed */
			EntityManager entityManager = entityManagerProvider.get();
			entityManager.flush();
	
			List<Column> columnOrphans = getOrphanedColumns();
			if (null != columnOrphans)
				for (Column orphan : columnOrphans)
					entityManager.remove(orphan);
	
			List<Filter> filterOrphans = getOrphanedSapFilters();
			if (null != filterOrphans)
				for (Filter orphan : filterOrphans)
					entityManager.remove(orphan);
	
			List<FilterRange> rangeOrphans = getOrphanedRanges();
			if (null != rangeOrphans)
				for (FilterRange orphan : rangeOrphans)
					entityManager.remove(orphan);
	
			List<FilterBlock> filterBlockOrphans = getOrphanedFilterBlocks();
			if (null != filterBlockOrphans)
				for (FilterBlock orphan : filterBlockOrphans)
					entityManager.remove(orphan);
	
			List<FilterSpec> filterSpecOrphans = getOrphanedFilterSpecs();
			if (null != filterSpecOrphans)
				for (FilterSpec orphan : filterSpecOrphans)
					entityManager.remove(orphan);
		
		} catch(Exception e){
			logger.warn( e.getMessage(), e);
		}
	}

	private List<FilterSpec> getOrphanedFilterSpecs() {
		HibernateEntityManager em = (HibernateEntityManager) entityManagerProvider
				.get();
		SQLQuery query = em
				.getSession()
				.createSQLQuery(
						"SELECT ENTITY_ID FROM RS_FILTER_SPEC WHERE RS_FILTER_SPEC.ENTITY_ID NOT IN ( SELECT FILTERS_ID FROM RS_FILTER_BLOCK_2_FILTERS) ");
		List list = query.list();
		
		List<FilterSpec> specList = new ArrayList<FilterSpec>();
		for(Object id : list){
			if(null != id){
				FilterSpec spec = null;
				if(id instanceof BigInteger)
					spec = em.find(FilterSpec.class, ((BigInteger)id).longValue());
				else if(id instanceof Long)
					spec = em.find(FilterSpec.class, id);
				else
					spec = em.find(FilterSpec.class, Long.valueOf(id.toString()));
				
				if(null != spec)
					specList.add(spec);
			}
		}
		
		return specList;
	}

	protected List<FilterBlock> getOrphanedFilterBlocks() {
		HibernateEntityManager em = (HibernateEntityManager) entityManagerProvider
				.get();
		SQLQuery query = em
				.getSession()
				.createSQLQuery(
						"SELECT * FROM RS_FILTER_BLOCK WHERE RS_FILTER_BLOCK.ENTITY_ID NOT IN ( SELECT ROOT_BLOCK_ID AS BID FROM RS_PRE_FILTER UNION SELECT CHILD_BLOCKS_ID AS BID FROM RS_FILTER_BLOCK_2_CHILD_BL) ");
		query.addEntity(FilterBlock.class);
		List list = query.list();
		return list;
	}

	public List<Column> getOrphanedColumns() {
		HibernateEntityManager em = (HibernateEntityManager) entityManagerProvider
				.get();
		SQLQuery query = em
				.getSession()
				.createSQLQuery(
						"SELECT ENTITY_ID FROM RS_COLUMN WHERE RS_COLUMN.ENTITY_ID NOT IN ( " +
							"SELECT COLUMNS_ID AS CID FROM RS_TABLE_REPORT_2_COLUMN " +
							" UNION " +
							"SELECT ADDITIONAL_COLUMNS_ID AS CID FROM RS_TABLE_REPORT_2_ADD_COLUMN " +
							" UNION " +
							"SELECT COLUMN_ID AS CID FROM RS_COLUMN_FILTER " +
							" UNION " +
							"SELECT COLUMNA_ID AS CID FROM RS_BINARY_COLUMN_FILTER " +
							" UNION " +
							"SELECT COLUMNB_ID AS CID FROM RS_BINARY_COLUMN_FILTER " +
						") ");
		List list = query.list();
		
		List<Column> colList = new ArrayList<Column>();
		for(Object id : list){
			if(null != id){
				Column column = null;
				if(id instanceof BigInteger)
					column = em.find(Column.class, ((BigInteger)id).longValue());
				else if(id instanceof Long)
					column = em.find(Column.class, id);
				else
					column = em.find(Column.class, Long.valueOf(id.toString()));
				
				if(null != column)
					colList.add(column);
			}
		}
		return colList;
	}

	public List<Filter> getOrphanedSapFilters() {
		HibernateEntityManager em = (HibernateEntityManager) entityManagerProvider
				.get();
		SQLQuery query = em
				.getSession()
				.createSQLQuery(
						"SELECT * FROM RS_FILTER WHERE RS_FILTER.ENTITY_ID NOT IN (SELECT FILTER_ID FROM RS_COLUMN WHERE NOT FILTER_ID IS NULL ) ");
		query.addEntity(Filter.class);
		List list = query.list();
		return list;
	}

	public List<FilterRange> getOrphanedRanges() {
		HibernateEntityManager em = (HibernateEntityManager) entityManagerProvider
				.get();
		SQLQuery query = em
				.getSession()
				.createSQLQuery(
						"SELECT * FROM RS_FILTER_RANGE WHERE RS_FILTER_RANGE.ENTITY_ID  NOT IN (SELECT EXCLUDE_RANGES_ID AS RANGE_ID FROM RS_FILTER_2_FILTER_RNG_EXC UNION SELECT INCLUDE_RANGES_ID AS RANGE_ID FROM RS_FILTER_2_FILTER_RNG_INC)");
		query.addEntity(FilterRange.class);
		List list = query.list();
		return list;
	}


}
