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
 
 
package net.datenwerke.rs.scheduler.service.scheduler.sendto;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import net.datenwerke.hookhandler.shared.hookhandler.HookHandlerService;
import net.datenwerke.rs.core.service.mail.MailService;
import net.datenwerke.rs.core.service.reportmanager.engine.config.ReportExecutionConfig;
import net.datenwerke.rs.core.service.reportmanager.exceptions.ReportExecutorException;
import net.datenwerke.rs.core.service.sendto.hooks.SendToTargetProviderHook;
import net.datenwerke.rs.scheduler.service.scheduler.jobs.report.ReportExecuteJob;
import net.datenwerke.scheduler.service.scheduler.entities.AbstractAction;
import net.datenwerke.scheduler.service.scheduler.entities.AbstractJob;
import net.datenwerke.scheduler.service.scheduler.exceptions.ActionExecutionException;
import net.datenwerke.scheduler.service.scheduler.exceptions.JobExecutionException;
import net.datenwerke.security.service.authenticator.AuthenticatorService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;
import com.google.inject.Provider;

@Entity
@Table(name="SCHED_ACTION_SEND_TO")
@Inheritance(strategy=InheritanceType.JOINED)
public class SendToReportAction extends AbstractAction {

	@Transient
	private final Logger logger = LoggerFactory.getLogger(getClass().getName());

	@Transient @Inject private HookHandlerService hookHandlerService;
	@Inject @Transient private Provider<AuthenticatorService> authenticatorServiceProvider;

	
	private String sendToId;
	
	@JoinColumn(name="SEND_TO")
	@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.EAGER, orphanRemoval=true)
    private Set<SendToReportActionValue> configValues;

	public String getSendToId() {
		return sendToId;
	}

	public void setSendToId(String sendToId) {
		this.sendToId = sendToId;
	}
	
	public Set<SendToReportActionValue> getConfigValues() {
		return configValues;
	}
	
	public void setConfigValues(Set<SendToReportActionValue> values) {
		this.configValues = values;
	}
	
	@Transient
	public void setConfigValues(Map<String, String> values){
		if(null == this.configValues)
			this.configValues = new HashSet<SendToReportActionValue>();
		this.configValues.clear();
		
		if(null != values){
			for(String key : values.keySet()){
				String value = values.get(key);
				this.configValues.add(new SendToReportActionValue(key,value));
			}
		}
	}
	
	public HashMap<String, String> getValueMap() {
		if(null == configValues || configValues.isEmpty())
			return null;
		
		HashMap<String, String> valueMap = new HashMap<String, String>();
		
		for(SendToReportActionValue value : configValues)
			valueMap.put(value.getValueId(), value.getTheValue());
		
		return valueMap;
	}
	

	public Logger getLogger() {
		return logger;
	}
	
	@Override
	public void doExecute(AbstractJob abstractJob)  throws ActionExecutionException {
		if(!(abstractJob instanceof ReportExecuteJob) || null == sendToId)
			return;
		
		ReportExecuteJob reportJob = (ReportExecuteJob) abstractJob;
		
		try {
			try{
				authenticatorServiceProvider.get().setAuthenticatedInThread(reportJob.getUser().getId());
				
				for(SendToTargetProviderHook hooker : hookHandlerService.getHookers(SendToTargetProviderHook.class)){
					if(sendToId.equals(hooker.getId())){
						hooker.scheduledSendTo(reportJob.getExecutedReport(), reportJob.getReport(), reportJob, reportJob.getOutputFormat(), getValueMap());
						return;
					}
				}
				throw new ActionExecutionException("Could not find target handler for id: " + sendToId);
			}finally{
				authenticatorServiceProvider.get().logoffUserInThread();
			}
		} catch (RuntimeException e) {
			throw new ActionExecutionException(e);
		}
		

	}

	
}
