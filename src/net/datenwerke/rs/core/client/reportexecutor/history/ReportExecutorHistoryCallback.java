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
 
 
package net.datenwerke.rs.core.client.reportexecutor.history;

import net.datenwerke.gf.client.history.HistoryCallback;
import net.datenwerke.gf.client.history.HistoryLocation;
import net.datenwerke.gxtdto.client.dtomanager.callback.RsAsyncCallback;
import net.datenwerke.rs.core.client.reportexecutor.ExecuteReportConfiguration;
import net.datenwerke.rs.core.client.reportexecutor.ReportExecutorDao;
import net.datenwerke.rs.core.client.reportexecutor.ReportExecutorUIService;
import net.datenwerke.rs.core.client.reportexecutor.variantstorer.VariantStorerConfig;
import net.datenwerke.rs.core.client.reportmanager.dto.reports.ReportDto;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;

public class ReportExecutorHistoryCallback implements HistoryCallback {

	private final ReportExecutorDao reportExecutorDao;
	private final ReportExecutorUIService executorService;

	@Inject
	public ReportExecutorHistoryCallback(
		ReportExecutorDao reportExecutorDao,
		ReportExecutorUIService executorService
		){
		
		this.reportExecutorDao = reportExecutorDao;
		this.executorService = executorService;
	}
	
	@Override
	public void locationChanged(final HistoryLocation location) {
		reportExecutorDao.loadReportForExecutionFrom(location, new RsAsyncCallback<ReportDto>(){
			@Override
			public void onSuccess(ReportDto result) {
				executorService.executeReportDirectly(result, null, new ExecuteReportConfiguration() {
					
					@Override
					public String getViewId() {
						return location.hasParameter("v") ? location.getParameterValue("v") : null;
					}
					
					@Override
					public VariantStorerConfig getVariantStorerConfig() {
						return new VariantStorerConfig() {
							
							@Override
							public VariantStorerHandleServerCalls getServerCallHandler() {
								return new VariantStorerHandleServerCalls() {
									
									@Override
									public void editVariant(ReportDto report, String executeToken, String name,
											String description, AsyncCallback<ReportDto> callback) {
										throw new IllegalStateException("not implemented");
									}
									
									@Override
									public void deleteVariant(ReportDto report, AsyncCallback<Void> callback) {
										throw new IllegalStateException("not implemented");
									}
									
									@Override
									public void createNewVariant(ReportDto report, String executeToken,
											String name, String description, AsyncCallback<ReportDto> callback) {
										reportExecutorDao.createNewVariant(report, executeToken, name, description, callback);
									}
								};
							}
							
							@Override
							public boolean displayVariantStorer() {
								return true;
							}
							
							@Override
							public boolean displayEditVariantOnStore() {
								return false;
							}
							
							@Override
							public boolean allowEditVariant() {
								return false;
							}
						};
					}
					
					@Override
					public boolean handleError(Throwable t) {
						return false;
					}
					
					@Override
					public boolean accepctView(String viewId) {
						return true;
					}
				});
			}
		});
	}

}
