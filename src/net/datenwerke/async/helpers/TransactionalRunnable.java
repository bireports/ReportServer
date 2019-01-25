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

import javax.persistence.EntityManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.assistedinject.Assisted;
import com.google.inject.persist.UnitOfWork;

public class TransactionalRunnable implements Runnable{
	
	private final Logger logger = LoggerFactory.getLogger(getClass().getName());

	private final Provider<UnitOfWork> unitOfWorkProvider;
	private final Provider<EntityManager> entityManagerProvider;
	private final Runnable runnable;

	@Inject
	public TransactionalRunnable(
		Provider<UnitOfWork> unitOfWorkProvider,
		Provider<EntityManager> entityManagerProvider,
		@Assisted Runnable runnable	
		){
		this.unitOfWorkProvider = unitOfWorkProvider;
		this.entityManagerProvider = entityManagerProvider;
		this.runnable = runnable;
	}

	@Override
	public void run() {
		UnitOfWork uow = unitOfWorkProvider.get();
		uow.begin();
		
		EntityManager em = null;
		boolean success = false;
		try{
			em = entityManagerProvider.get();
			em.getTransaction().begin();
		
			/* call runnable */
			runnable.run();
			success = true;
		} catch(Exception e){
			logger.warn( e.getMessage(), e);
		} finally {
			try{
				if(null != em && null != em.getTransaction())
					if(success)
						em.getTransaction().commit();
					else
						em.getTransaction().rollback();
			} finally {
				uow.end();
			}
		}
	}
}
