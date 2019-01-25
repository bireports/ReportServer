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
 
 
package net.datenwerke.gf.service.lateinit;

import java.util.concurrent.atomic.AtomicBoolean;

import javax.persistence.EntityManager;

import net.datenwerke.hookhandler.shared.hookhandler.HookHandlerService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Provider;
import com.google.inject.persist.UnitOfWork;

public class LateInitStartup {

	private final Logger logger = LoggerFactory.getLogger(getClass().getName());
	private static AtomicBoolean startupCompleted = new AtomicBoolean(false);

	@Inject
	public LateInitStartup(
			final HookHandlerService hookHandler,
			final Injector injector,
			final Provider<EntityManager> entityManagerProvider,
			final Provider<UnitOfWork> unitOfWorkProvider

			){

		Thread sStarter = new Thread(new Runnable() {

			@Override
			public void run() {
				while(true){
					try{
						injector.getInstance(EntityManager.class);
						break;
					} catch(Exception e){
						try {
							Thread.sleep(10);
						} catch (InterruptedException e1) {
						}
					}
				}
				try{
					/* begin transaction */
					UnitOfWork unitOfWork = unitOfWorkProvider.get();

					EntityManager em = null;
					boolean success = false;

					try{
						/* begin unit of work not necessary, since get Instance of Entity Manager starts uow*/

						/* begin transaction */
						em = entityManagerProvider.get();
						em.getTransaction().begin();

						/* perform steps */
						for(LateInitHook hooker : hookHandler.getHookers(LateInitHook.class))
							hooker.initialize();
						startupCompleted.set(true);
						
						logger.info("Startup completed");

						/* indicate success */
						success = true;
					} finally {
						try {
							if(null != em && success)
								em.getTransaction().commit();
							else if(null != em)
								em.getTransaction().rollback();
						} finally {
							unitOfWork.end();
						}
					}
				} catch(Exception e){
					logger.error( "Error in LateInitHook", e);
				}
			}
		});
		sStarter.setDaemon(true);
		sStarter.start();
	}

	public static boolean isStartupCompleted() {
		return startupCompleted.get();
	}

}
