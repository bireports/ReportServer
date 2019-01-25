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
 
 
package net.datenwerke.scheduler.service.scheduler;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.XMLConfiguration;

import com.google.inject.AbstractModule;
import com.google.inject.Inject;
import com.google.inject.Provides;
import com.google.inject.Singleton;

import net.datenwerke.rs.utils.config.ConfigFileNotFoundException;
import net.datenwerke.rs.utils.config.ConfigService;
import net.datenwerke.scheduler.service.scheduler.annotations.DwSchedulerConfig;
import net.datenwerke.scheduler.service.scheduler.annotations.SchedulerAsyncPoolName;
import net.datenwerke.scheduler.service.scheduler.annotations.SchedulerCheckInterval;
import net.datenwerke.scheduler.service.scheduler.annotations.SchedulerDefaultJobStore;
import net.datenwerke.scheduler.service.scheduler.annotations.SchedulerNrOfWorkingThreads;
import net.datenwerke.scheduler.service.scheduler.annotations.SchedulerWaitBeforeForcedShutdown;
import net.datenwerke.scheduler.service.scheduler.annotations.StandardVetoDelay;
import net.datenwerke.scheduler.service.scheduler.annotations.StandardVetoRandomDelay;
import net.datenwerke.scheduler.service.scheduler.stores.JobStore;
import net.datenwerke.scheduler.service.scheduler.stores.RamJobStore;

public class SchedulerModule extends AbstractModule {
	
	public static final String CONFIG_FILE = "scheduler/scheduler.cf";
	
	protected static final String PROPERTY_ASYNC_POOL_NAME = "scheduler.properties.asyncpoolname";
	protected static final String PROPERTY_CHECK_INTERVAL = "scheduler.properties.checkinterval";
	protected static final String PROPERTY_NR_WORKING_THREADS = "scheduler.properties.workingthreads";
	protected static final String PROPERTY_WAIT_BEFORE_FORCED_SHUTDOWN = "scheduler.properties.shutdownwait";
	protected static final String PROPERTY_STANDARD_VETO_DELAY = "scheduler.properties.stdvetodelay";
	protected static final String PROPERTY_VETO_RANDOM_DELAY = "scheduler.properties.rndvetodealy";
	protected static final String PROPERTY_SCHEDULER_DISABLED = "scheduler.properties.disabled";
	

	private final Class<? extends JobStore> storeType;
	
	public SchedulerModule(){
		this.storeType = RamJobStore.class;
	}
	
	public SchedulerModule(
		Class<? extends JobStore> storeType
		){
		
		this.storeType = storeType;
	}
	
	@Override
	protected void configure() {
		bind(JobStore.class).annotatedWith(SchedulerDefaultJobStore.class).to(storeType);

		/* services */
		bind(SchedulerService.class).to(SchedulerServiceImpl.class).in(Singleton.class);
		
		bind(SchedulerStartup.class).asEagerSingleton();
	}

	
	@Provides @Inject @DwSchedulerConfig
	public Configuration providePropertyContainer(ConfigService configService){
		try{
			return configService.getConfigFailsafe(CONFIG_FILE);
		}catch(ConfigFileNotFoundException e){
			return new XMLConfiguration();
		}
	}

	@Provides @Inject @SchedulerAsyncPoolName
	public String provideAsyncPoolName(@DwSchedulerConfig Configuration config){
		return config.getString(PROPERTY_ASYNC_POOL_NAME, "dwScheduler");	
	}

	@Provides @Inject @SchedulerCheckInterval
	public Long provideSchedulerCheckInterval(@DwSchedulerConfig Configuration config){
		return config.getLong(PROPERTY_CHECK_INTERVAL, 10*1000l);	
	}
	
	@Provides @Inject @SchedulerNrOfWorkingThreads
	public Integer provideSchedulerNrOfWorkingThreads(@DwSchedulerConfig Configuration config){
		return config.getInteger(PROPERTY_NR_WORKING_THREADS, 5);	
	}
	
	@Provides @Inject @SchedulerWaitBeforeForcedShutdown
	public Long provideSchedulerWaitBeforeForcedShutdown(@DwSchedulerConfig Configuration config){
		return config.getLong(PROPERTY_WAIT_BEFORE_FORCED_SHUTDOWN, 60*1000l);	
	}
	
	@Provides @Inject @StandardVetoDelay
	public Integer provideStandardVetoDelay(@DwSchedulerConfig Configuration config){
		return config.getInteger(PROPERTY_STANDARD_VETO_DELAY, 180);	
	}
	
	@Provides @Inject @StandardVetoRandomDelay
	public Integer provideStandardVetoRandomDelay(@DwSchedulerConfig Configuration config){
		return config.getInteger(PROPERTY_VETO_RANDOM_DELAY, 20);	
	}
	

}
