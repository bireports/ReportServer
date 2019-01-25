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
 
 
package net.datenwerke.async;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * A service that allows to run asynchronous tasks.
 * 
 * Via the service managed threadpools (see {@link ExecutorService}) can be created. 
 * 
 *
 */
public interface DwAsyncService  {

	/**
	 * Returns true if pool identified by poolToken exists
	 * @param poolToken The pool identifier
	 * @return
	 */
	public boolean poolExists(String poolToken);
	
	/**
	 * Initializes a new pool for the given configuration.
	 * 
	 * If a pool for poolToken already exists, the old pool is terminated.
	 * 
	 * @param poolToken
	 * @param configuration
	 * 
	 * @return The pool
	 */
	public DwAsyncPool initPool(String poolToken, PoolConfiguration configuration);
	
	/**
	 * Returns the pool identified by poolToken or null.
	 * 
	 * @param poolToken
	 * @return
	 */
	public DwAsyncPool getPool(String poolToken);
	
	/**
	 * If pool exists, the pool is asked to orderly shutdown.
	 * 
	 * @param poolToken
	 * @see ExecutorService#shutdown()
	 */
	public void shutdownPool(String poolToken);
	
	/**
	 * If pool exists, the pool is shutdown immediately.
	 * 
	 * @param poolToken
	 * @see ExecutorService#shutdownNow()
	 * 
	 * @return list of tasks that never commenced execution
	 */
	public List<Runnable> shutdownPoolNow(String poolToken);
	
	/**
	 * Blocks until all tasks have completed execution after a shutdown
     * request for the given pool
     * 
	 * @param poolToken The pool identifier
	 * @param timeout
	 * @param unit
	 * 
	 * @see ExecutorService#awaitTermination(long, TimeUnit)
	 * 
	 * @return
	 * @throws InterruptedException
	 */
	public boolean awaitTerminationForPool(String poolToken, long timeout, TimeUnit unit) throws InterruptedException;
	
	/**
	 * Returns true if the pool was asked to shutdown.
	 * 
	 * @param poolToken
	 * @see ExecutorService#isShutdown()
	 * 
	 * @return
	 */
	public boolean isShutdownPool(String poolToken);
	
	/**
	 * Returns <tt>true</tt> if all tasks have completed following shut down.
	 * 
	 * @param poolToken
	 * @return
	 */
	public boolean isTerminatedPool(String poolToken);
	
	/**
	 * Returns the number of pools
	 * 
	 * @return the number of pools
	 */
	public int getNrOfPools();
	
	/**
	 * Returns the approximately number of active threads in all pools.
	 * @return the number of threads
	 */
	int getActiveCountAll();
	
	/**
	 * Returns the approximate total number of tasks that have ever been
     * scheduled for execution summed over all pools.
     * 
	 * @return The number of tasks
	 */
	long getTaskCountAll();
	
	/**
	 * Returns the approximate total number of tasks that have
     * completed execution summed over all pools.
     * 
	 * @return the number of tasks
	 */
	long getCompletedTaskCountAll();
	
	/**
	 * Calls shutdown on all pools.
	 * 
	 * @see DwAsyncPool#shutdown()
	 */
	void shutdownAll();
	
	/**
	 * Calls shutdownNow on all pools.
	 * 
	 * @see DwAsyncPool#shutdownNow()
	 * @return
	 */
	List<Runnable> shutdownAllNow();

	/**
	 * Submits a task to be run on the default thread pool
	 * 
	 * @param updateUserProperties
	 */
	public void submit(Runnable updateUserProperties);

	/**
	 * Shuts down the default thread pool
	 */
	void suhtdownDefault();

	/**
	 * Terminates the default thread pool.
	 * 
	 * @return
	 */
	List<Runnable> shutdownNowDefault();
}
