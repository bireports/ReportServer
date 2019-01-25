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
 
 
package net.datenwerke.rs.core.client.reportexecutor.variantstorer;

import net.datenwerke.rs.core.client.reportexecutor.ReportExecutorDao;
import net.datenwerke.rs.core.client.reportmanager.dto.reports.ReportDto;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;


public class VariantStorerConfigImpl implements VariantStorerConfig {

	private final ReportExecutorDao executerDao;
	
	private boolean display = true;
	
	
	@Inject
	public VariantStorerConfigImpl(ReportExecutorDao executerDao){
		this.executerDao = executerDao;
	}
	
	@Override
	public boolean displayVariantStorer() {
		return display;
	}
	
	public void setDisplay(boolean display) {
		this.display = display;
	}
	
	@Override
	public boolean allowEditVariant() {
		return true;
	}
	
	@Override
	public boolean displayEditVariantOnStore() {
		return true;
	}

	@Override
	public VariantStorerHandleServerCalls getServerCallHandler() {
		return new VariantStorerHandleServerCalls() {
			
			@Override
			public void editVariant(ReportDto report, String executeToken, String name,
					String description, AsyncCallback<ReportDto> callback) {
				executerDao.editVariant(
					report,
					executeToken,
					name,
					description,
					callback
				);				
			}
			
			@Override
			public void deleteVariant(ReportDto report, AsyncCallback<Void> callback) {
				executerDao.deleteVariant(report, callback);
			}
			
			@Override
			public void createNewVariant(ReportDto report, String executeToken,
					String name, String description, AsyncCallback<ReportDto> callback) {
				executerDao.createNewVariant(report, executeToken, name, description, callback);
			}
		};
	}
	
	

}
