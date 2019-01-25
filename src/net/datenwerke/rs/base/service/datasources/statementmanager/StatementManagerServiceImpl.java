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
 
 
package net.datenwerke.rs.base.service.datasources.statementmanager;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;

import net.datenwerke.hookhandler.shared.hookhandler.HookHandlerService;
import net.datenwerke.rs.base.service.datasources.statementmanager.hooks.StatementCancellationHook;
import net.datenwerke.security.service.authenticator.AuthenticatorService;

@Singleton
public class StatementManagerServiceImpl implements StatementManagerService {
	private Map<Long, Map<String, Map<String, Statement>>> statementMap = new ConcurrentHashMap<Long, Map<String,Map<String,Statement>>>();
	private Map<String, Date> dateMap = new ConcurrentHashMap<String, Date>();
	private Provider<AuthenticatorService> authenticatorService;
	private HookHandlerService hookHandlerService;
	
	@Inject
	public StatementManagerServiceImpl(
			Provider<AuthenticatorService> authenticatorService, 
			HookHandlerService hookHandlerService) {
		
		this.authenticatorService = authenticatorService;
		this.hookHandlerService = hookHandlerService;
	}
	
	public synchronized void registerStatement(String statementId, Statement statement){
		Map<String,Statement> userStatementMap = getUserStatementMap(statementId);
		dateMap.put(statementId, new Date());
		if(null != userStatementMap) {
			if(statementId.contains(":")){
				userStatementMap.put(statementId.substring(1 + statementId.indexOf(":")),statement);
			}else{
				userStatementMap.put("anonstmt-" + UUID.randomUUID() ,statement);
			}
		}
	}
	
	@Override
	public synchronized void unregisterStatement(String statementId){
		dateMap.remove(statementId);
		
		if(statementId.contains(":")){
			Map<String,Statement> userStatementMap = getUserStatementMap(statementId);
			if(null != userStatementMap){
				userStatementMap.remove(statementId.substring(1 + statementId.indexOf(":")));
				if(userStatementMap.isEmpty()){
					unregisterStatement(statementId.substring(0, statementId.indexOf(":")));
				}
			}
		}else{
			Map<String,Map<String,Statement>> userStatementMap = getUserStatementMap();
			if(null != userStatementMap){
				if(userStatementMap.isEmpty())
					statementMap.remove(statementId);
			}
		}
	}
	
	@Override
	public Map<Long, Map<String, Map<String, Statement>>> getAllStatements(){
		return statementMap;
	}
	
	@Override
	public Date getRegisterDate(String id){
		return dateMap.get(id);
	}
	
	@Override
	public synchronized Collection<Statement> getStatements(String statementId) {
		Map<String,Statement> userStatementMap = getUserStatementMap(statementId);
		if(null == userStatementMap)
			return Collections.EMPTY_SET;
		if(statementId.contains(":")){
			Statement statement = userStatementMap.get(statementId.substring(1 + statementId.indexOf(":")));
			if(null == statement)
				return Collections.EMPTY_SET;
			return Collections.singleton(statement);
		}else{
			return userStatementMap.values();
		}
	}
	
	
	private synchronized Map<String, Statement> getUserStatementMap(String statementId){
		Map<String,Map<String,Statement>> userStatementMap = getUserStatementMap();
		if(null == userStatementMap)
			return null;
		if(statementId.contains(":")){
			statementId = statementId.substring(statementId.indexOf(":"));
		}
		
		Map<String, Statement> hashMap = userStatementMap.get(statementId);
		if(null == hashMap){
			hashMap = new ConcurrentHashMap<String, Statement>();
			userStatementMap.put(statementId, hashMap);
		}
		
		return hashMap;
	}
	
	private Long getCurrentUserId(){
		try{
			AuthenticatorService as = authenticatorService.get();
			return as.getCurrentUser().getId();
		}catch(Exception e){
			return -1L;
		}
	}
	
	@Override
	public Map<String, Map<String, Statement>> getUserStatementMap() {
		Long currentUserId = getCurrentUserId();
		return getUserStatementMap(currentUserId);
	}
	
	@Override
	public synchronized Map<String, Map<String, Statement>> getUserStatementMap(Long currentUserId) {
		
		Map<String,Map<String, Statement>> map = statementMap.get(currentUserId);
		if(null == map){
			map = new ConcurrentHashMap<String,Map<String, Statement>>();
			statementMap.put(currentUserId, map);
		}
		return map;
	}
	
	@Override
	public void cancelStatement(String statementId) {
		Collection<Statement> statements = getStatements(statementId);
		for(Statement statement: statements){
			boolean handled = false;
			for(StatementCancellationHook hooker : hookHandlerService.getHookers(StatementCancellationHook.class)){
				if(hooker.consumes(statement)){
					hooker.cancelStatement(statement);
					handled = hooker.overridesDefaultMechanism(statement);
				}
			}
			if(!handled){
				try {
					statement.cancel();
				} catch (SQLException e) { }
			}
		}
	}
	
}
