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
 
 
package net.datenwerke.rs.jxlsreport.service.jxlsreport;

import javax.inject.Singleton;
import javax.persistence.EntityManager;

import net.datenwerke.rs.jxlsreport.service.jxlsreport.entities.JxlsReportFile;
import net.datenwerke.security.service.eventlogger.annotations.FireRemoveEntityEvents;

import com.google.inject.Inject;
import com.google.inject.Provider;

@Singleton
public class JxlsReportServiceImpl implements JxlsReportService {

	private final Provider<EntityManager> entityManagerProvider;
	
	@Inject
	public JxlsReportServiceImpl(
		Provider<EntityManager> entityManagerProvider
		) {
		this.entityManagerProvider = entityManagerProvider;
	}

	@Override
	@FireRemoveEntityEvents
	public void remove(JxlsReportFile file) {
		EntityManager em = entityManagerProvider.get();
		file = em.find(file.getClass(), file.getId());
		if(null != file)
			em.remove(file);
	}

}
