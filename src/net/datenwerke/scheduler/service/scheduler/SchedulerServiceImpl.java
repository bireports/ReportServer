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
 
 
package net.datenwerke.scheduler.service.scheduler;

import java.util.List;
import java.util.concurrent.Executors;

import net.datenwerke.hookhandler.shared.hookhandler.HookHandlerService;
import net.datenwerke.rs.utils.config.ConfigService;
import net.datenwerke.rs.utils.eventbus.EventBus;
import net.datenwerke.scheduler.service.scheduler.annotations.DwSchedulerConfig;
import net.datenwerke.scheduler.service.scheduler.annotations.SchedulerDefaultJobStore;
import net.datenwerke.scheduler.service.scheduler.entities.AbstractJob;
import net.datenwerke.scheduler.service.scheduler.entities.AbstractTrigger;
import net.datenwerke.scheduler.service.scheduler.events.JobScheduled;
import net.datenwerke.scheduler.service.scheduler.events.JobUnscheduled;
import net.datenwerke.scheduler.service.scheduler.events.SchedulerFailedToSopEvent;
import net.datenwerke.scheduler.service.scheduler.events.SchedulerRestartEvent;
import net.datenwerke.scheduler.service.scheduler.events.SchedulerShutdownEvent;
import net.datenwerke.scheduler.service.scheduler.events.SchedulerShutdownNowEvent;
import net.datenwerke.scheduler.service.scheduler.events.SchedulerShutdownWatchdogEvent;
import net.datenwerke.scheduler.service.scheduler.events.SchedulerStartEvent;
import net.datenwerke.scheduler.service.scheduler.events.SchedulerStartedEvent;
import net.datenwerke.scheduler.service.scheduler.exceptions.SchedulerNotEnabledException;
import net.datenwerke.scheduler.service.scheduler.exceptions.SchedulerStartupException;
import net.datenwerke.scheduler.service.scheduler.hooks.SchedulerInfoHook;
import net.datenwerke.scheduler.service.scheduler.stores.JobStore;
import net.datenwerke.scheduler.service.scheduler.stores.jpa.filter.JobFilterConfiguration;
import net.datenwerke.security.service.eventlogger.annotations.FireForceRemoveEntityEvents;
import net.datenwerke.security.service.eventlogger.annotations.FireMergeEntityEvents;
import net.datenwerke.security.service.eventlogger.annotations.FireRemoveEntityEvents;

import org.apache.commons.configuration.Configuration;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Provider;

public class SchedulerServiceImpl implements SchedulerService {

	private final Provider<SchedulerDaemon> daemonProvider;
	private final Provider<Injector> injectorProvider;
	private final HookHandlerService hookHandler;
	private final EventBus eventBus;
	private final Provider<SchedulerWatchdog> watchdogProvider;

	private SchedulerWatchdog watchdog;
	private SchedulerDaemon schedulerDaemon;
	private Thread daemonThread;
	private Thread watchdogThread;
	
	private JobStore jobStore;
	
	private boolean orderdShutdown = true; 
	private int threadNr = 1;
	private Provider<Configuration> schedulerConfig;
	private ConfigService configService;
	
	@Inject
	public SchedulerServiceImpl(
		@SchedulerDefaultJobStore JobStore defaultStore,
		@DwSchedulerConfig Provider<Configuration> schedulerConfig,
		
		EventBus eventBus,
		HookHandlerService hookHandler,
		Provider<Injector> injectorProvider,
		Provider<SchedulerDaemon> daemonProvider,
		Provider<SchedulerWatchdog> watchdogProvider,
		ConfigService configService
		){
		
		/* store obejcts */
		this.jobStore = defaultStore;
		this.schedulerConfig = schedulerConfig;
		this.eventBus = eventBus;
		this.hookHandler = hookHandler;
		this.injectorProvider = injectorProvider;
		this.daemonProvider = daemonProvider;
		this.watchdogProvider = watchdogProvider;
		this.configService = configService;
	}
	
	@Override
	public boolean isActive() {
		return null != daemonThread && daemonThread.isAlive() && null != schedulerDaemon && ! schedulerDaemon.isShutdown();
	}
	
	@Override
	public boolean isShutdown() {
		return null == daemonThread || null == schedulerDaemon || schedulerDaemon.isShutdown();
	}
	
	@Override
	public boolean isTerminated() {
		return null == daemonThread || null == schedulerDaemon || schedulerDaemon.isTerminated();
	}
	
	@Override
	public void shutdown() {
		eventBus.fireEvent(new SchedulerShutdownEvent());
		
		/* remember that we have been shutdown properly */
		orderdShutdown = true;
		
		if(null != schedulerDaemon && ! schedulerDaemon.isShutdown()){
			schedulerDaemon.shutdown();
			daemonThread.interrupt();
		}
	}

	@Override
	public List<Runnable> shutdownNow() {
		eventBus.fireEvent(new SchedulerShutdownNowEvent());
		
		shutdown();
		
		return schedulerDaemon.shutdownNow();
	}
	
	public boolean isOrderdShutdown() {
		return orderdShutdown;
	}
	
	@Override
	public void restart() throws SchedulerStartupException {
		eventBus.fireEvent(new SchedulerRestartEvent());
		
		SchedulerDaemon sd = schedulerDaemon;
		if(null == sd){
			start();
			return;
		}
		
		if(! sd.isShutdown())
			shutdown();
		
		try {
			if(null != daemonThread)
				daemonThread.join(1000*60);
		} catch (InterruptedException e) {
			schedulerDaemon.shutdownNow();
			
			try {
				if(null != daemonThread)
					daemonThread.join(1000*60);
			} catch (InterruptedException e2) {
				eventBus.fireEvent(new SchedulerFailedToSopEvent());
				
				throw new IllegalStateException("Could not shutdown scheduler");
			}
		}
		
		start();
	}

	@Override
	public synchronized void start() throws SchedulerStartupException {
		if(!isEnabled())
			throw new SchedulerNotEnabledException();
		
		eventBus.fireEvent(new SchedulerStartEvent());

		if(schedulerDaemon != null){
			if(isActive())
				return;
			else
				schedulerDaemon.shutdownNow();
		}
		
		/* make sure no daemon is running */
		try {
			if(null != daemonThread)
				daemonThread.join(1000*60);
		} catch (InterruptedException e1) {
			eventBus.fireEvent(new SchedulerFailedToSopEvent());
			
			throw new IllegalStateException("Could not shutdown scheduler");
		}
		
		schedulerDaemon = daemonProvider.get();

		/* we have not been shutdown properly */
		orderdShutdown = false;
		
		daemonThread = Executors.defaultThreadFactory().newThread(schedulerDaemon);
		daemonThread.setDaemon(true);
		daemonThread.setName("scheduler-thread-" + threadNr++);
		daemonThread.start();
		
		/* start watchdog */
		startWatchdog();
		
		eventBus.fireEvent(new SchedulerStartedEvent());
	}
	
	@Override
	public void startWatchdog() {
		if(null != watchdogThread && watchdogThread.isAlive() && ! watchdog.isShutdown())
			return;
		
		watchdog = watchdogProvider.get();
		
		watchdogThread = Executors.defaultThreadFactory().newThread(watchdog);
		watchdogThread.setDaemon(true);
		watchdogThread.setName("scheduler-watchdog");
		watchdogThread.start();
	}
	
	@Override
	public boolean isWatchdogActive(){
		return null != watchdogThread && watchdogThread.isAlive() && null != watchdog && ! watchdog.isShutdown();
	}
	

	@Override
	public void restartWatchdog(){
		shutdownWatchdog();
		startWatchdog();
	}
	
	@Override
	public void shutdownWatchdog(){
		eventBus.fireEvent(new SchedulerShutdownWatchdogEvent());
		
		if(null != watchdog)
			watchdog.shutdown();
	}

	@Override
	public JobStore getJobStore(){
		return jobStore;
	}

	@Override
	public void schedule(AbstractJob job, AbstractTrigger trigger) {
		injectorProvider.get().injectMembers(trigger);
		injectorProvider.get().injectMembers(trigger);
		
		for(SchedulerInfoHook hooker : hookHandler.getHookers(SchedulerInfoHook.class))
			hooker.beforeJobSchedule(job, trigger);
		
		jobStore.scheduleJob(job, trigger);
		
		eventBus.fireEvent(new JobScheduled(job));
		
		for(SchedulerInfoHook hooker : hookHandler.getHookers(SchedulerInfoHook.class))
			hooker.jobScheduled(job, trigger);
	}
	
	@Override
	public void schedule(AbstractJob job, AbstractTrigger trigger, AbstractJob previous) {
		injectorProvider.get().injectMembers(job);
		injectorProvider.get().injectMembers(trigger);

		for(SchedulerInfoHook hooker : hookHandler.getHookers(SchedulerInfoHook.class))
			hooker.beforeJobRescheduled(job, trigger, previous);
		
		job.setLinkToPrevious(previous);
		jobStore.scheduleJob(job, trigger);
		
		eventBus.fireEvent(new JobScheduled(job));
		
		for(SchedulerInfoHook hooker : hookHandler.getHookers(SchedulerInfoHook.class))
			hooker.jobRescheduled(job, trigger);
	}

	@Override
	public void unschedule(AbstractJob job) {
		for(SchedulerInfoHook hooker : hookHandler.getHookers(SchedulerInfoHook.class))
			hooker.beforeJobUnschedule(job);
		
		jobStore.unschedule(job);
		
		eventBus.fireEvent(new JobUnscheduled(job));
		
		for(SchedulerInfoHook hooker : hookHandler.getHookers(SchedulerInfoHook.class))
			hooker.jobUnscheduled(job);
	}
	
	@Override
	public void clearErrorState(AbstractJob job) {
		jobStore.clearErrorState(job);
	}

	@Override
	@FireRemoveEntityEvents
	public void remove(AbstractJob job) {
		for(SchedulerInfoHook hooker : hookHandler.getHookers(SchedulerInfoHook.class))
			hooker.beforeJobRemove(job);
		
		jobStore.remove(job);
		
		for(SchedulerInfoHook hooker : hookHandler.getHookers(SchedulerInfoHook.class))
			hooker.jobRemoved(job);
	}
	
	@Override
	@FireForceRemoveEntityEvents
	public void forceRemove(AbstractJob job) {
		jobStore.remove(job);
	}

	@Override
	public AbstractJob getJobById(Long id) {
		if(null == id)
			return null;
		return jobStore.getJobById((long)id);
	}

	@Override
	@FireMergeEntityEvents
	public void merge(AbstractJob job) {
		jobStore.merge(job);
	}

	@Override
	public SchedulerDaemon getDeamon() {
		return schedulerDaemon;
	}

	@Override
	public List<AbstractJob> getJobsBy(JobFilterConfiguration filterConfig) {
		return getJobStore().getJobsBy(filterConfig);
	}

	@Override
	public void enable() {
		Configuration configuration = schedulerConfig.get();
		configuration.setProperty(SchedulerModule.PROPERTY_SCHEDULER_DISABLED, false);
		configService.storeConfig(configuration, SchedulerModule.CONFIG_FILE);
	}

	@Override
	public void disable() {
		Configuration configuration = schedulerConfig.get();
		configuration.setProperty(SchedulerModule.PROPERTY_SCHEDULER_DISABLED, true);
		configService.storeConfig(configuration, SchedulerModule.CONFIG_FILE);
	}

	@Override
	public boolean isEnabled() {
		return !schedulerConfig.get().getBoolean(SchedulerModule.PROPERTY_SCHEDULER_DISABLED, false);
	}

	

}
