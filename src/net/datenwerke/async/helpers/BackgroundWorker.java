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
 
 
package net.datenwerke.async.helpers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class BackgroundWorker implements Runnable {
	
	private final Logger logger = LoggerFactory.getLogger(getClass().getName());

	private final Runnable runnable;
	private final long delay;
	
	private boolean cancel = false;
	
	public BackgroundWorker(long delay, Runnable runnable) {
		this.runnable = runnable;
		this.delay = delay;
	}


	@Override
	public void run() {
		while(! cancel){
			try{
				runnable.run();
			} catch(RuntimeException e){
				logger.warn( "Background task failed", e);
				throw e;
			}
			
			try {
				Thread.sleep(delay);
			} catch (InterruptedException e) {
			} 
		}
	}
	
	public void cancel(){
		this.cancel = true;
	}

}
