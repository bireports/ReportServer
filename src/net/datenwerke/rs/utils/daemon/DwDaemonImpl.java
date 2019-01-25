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
 
 
package net.datenwerke.rs.utils.daemon;

import javax.inject.Provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class DwDaemonImpl implements DwDaemon {

	protected final Logger logger = LoggerFactory.getLogger(getClass().getName());
	
	protected final Provider<Long> checkInterval;
	
	protected boolean shutdown;
	protected boolean terminated;
	
	
	public DwDaemonImpl(){
		this(60000l);
	}
	
	public DwDaemonImpl(Provider<Long> checkIntervalProvider) {
		this.checkInterval = checkIntervalProvider;
	}
	
	public DwDaemonImpl(final Long checkInterval){
		this.checkInterval = new Provider<Long>() {
			@Override
			public Long get() {
				return checkInterval;
			}
		};
	}
	
	@Override
	public void run() {
		while(! shutdown){
			try{
				doWork();
			} catch(Throwable e){
				try{
					logger.warn( e.getMessage(), e);
				} finally {
					if(e instanceof Error){
						shutdown = true;
						throw (Error)e;
					}
				}
			}
			
			/* sleep till next fire time */ 
			try {
				Thread.sleep(getNextSleepTime());
			} catch (InterruptedException e) {
			}
		}
		
		/* oderly shutdown */
		this.terminated = true;
	}
	
	abstract protected void doWork();

	protected long getNextSleepTime() {
		return Math.max(0, checkInterval.get());
	}

	@Override
	public synchronized void shutdown(){
		shutdown = true;
	}
	
	@Override
	public boolean isShutdown() {
		return shutdown;
	}

	@Override
	public boolean isTerminated() {
		return terminated;
	}
	
}
