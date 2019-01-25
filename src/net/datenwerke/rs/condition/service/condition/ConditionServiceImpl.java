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
 
 
package net.datenwerke.rs.condition.service.condition;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import net.datenwerke.rs.base.service.reportengines.table.entities.Column;
import net.datenwerke.rs.base.service.reportengines.table.entities.TableReport;
import net.datenwerke.rs.base.service.reportengines.table.output.object.RSTableModel;
import net.datenwerke.rs.base.service.reportengines.table.output.object.RSTableRow;
import net.datenwerke.rs.condition.service.condition.entity.Condition;
import net.datenwerke.rs.core.service.reportmanager.ReportExecutorService;
import net.datenwerke.rs.core.service.reportmanager.exceptions.ReportExecutorException;
import net.datenwerke.rs.utils.juel.SimpleJuel;
import net.datenwerke.rs.utils.simplequery.annotations.QueryById;
import net.datenwerke.rs.utils.simplequery.annotations.SimpleQuery;
import net.datenwerke.security.service.authenticator.AuthenticatorService;
import net.datenwerke.security.service.eventlogger.annotations.FireMergeEntityEvents;
import net.datenwerke.security.service.eventlogger.annotations.FirePersistEntityEvents;
import net.datenwerke.security.service.eventlogger.annotations.FireRemoveEntityEvents;
import net.datenwerke.security.service.usermanager.entities.User;

import com.google.inject.Inject;
import com.google.inject.Provider;

public class ConditionServiceImpl implements ConditionService {

	private final Provider<EntityManager> entityManagerFactory;
	private final Provider<SimpleJuel> juelProvider;
	private final Provider<AuthenticatorService> authServiceProvider;
	private final ReportExecutorService reportExecutor;
	
	@Inject
	public ConditionServiceImpl(
		Provider<EntityManager> entityManagerFactory,
		Provider<SimpleJuel> juelProvider,
		Provider<AuthenticatorService> authServiceProvider, 
		ReportExecutorService reportExecutor
		) {
		
		/* store object */
		this.entityManagerFactory = entityManagerFactory;
		this.juelProvider = juelProvider;
		this.authServiceProvider = authServiceProvider;
		this.reportExecutor = reportExecutor;
	}

	@Override
	@FirePersistEntityEvents
	public void persist(Condition condition) {
		entityManagerFactory.get().persist(condition);
	}

	@Override
	@FireMergeEntityEvents
	public Condition merge(Condition condition) {
		condition = entityManagerFactory.get().merge(condition);
		return condition;
	}

	@Override
	@FireRemoveEntityEvents
	public void remove(Condition condition) {
		EntityManager em = entityManagerFactory.get();
		condition = em.find(Condition.class, condition.getId());
		if(null != condition)
			em.remove(condition);
	}

	@QueryById
	public Condition getConditionById(Long id) {
		return null; // magic
	}

	@SimpleQuery
	public List<Condition> getConditions() {
		return null; // magic
	}

	@Override
	public List<String> getReplacementsFor(Condition condition) {
		TableReport report = (TableReport) condition.getReport();
		
		if(null == report)
			throw new IllegalArgumentException();
		
		try {
			List<String> result = new ArrayList<String>();
			
			for(Column col : report.getColumns())
				result.add(col.getName());
			
			return result;
		} catch (Exception e) {
			throw new IllegalArgumentException(e);
		}
	}

	@Override
	public boolean executeCondition(Condition condition, String expression, User user) throws ReportExecutorException {
		if(null == expression)
			return false;
		
		TableReport report = condition.getReport();
		
		if(null == report)
			throw new IllegalArgumentException();
		
		expression = "${" + expression + "}";
		
		SimpleJuel juel = juelProvider.get();
		RSTableModel data = (RSTableModel) reportExecutor.execute(report, user, ReportExecutorService.OUTPUT_FORMAT_TABLE);
		if(data.getRowCount() < 1)
			throw new IllegalArgumentException("The report should return at least one row");
		RSTableRow row = data.getData().get(0);
		for(int i = 0; i < data.getColumnCount(); i++)
			juel.addReplacement(data.getColumnName(i), row.getAt(i));
		
		Object result = juel.parseAsObject(expression);
		if(null == result)
			return false;
		
		if(! (result instanceof Boolean))
			return true;
		
		return (Boolean) result;
	}
	
	@Override
	public boolean executeCondition(Condition condition, String expression) throws ReportExecutorException {
		User currentUser = authServiceProvider.get().getCurrentUser();
		return executeCondition(condition, expression, currentUser);
	}

}
