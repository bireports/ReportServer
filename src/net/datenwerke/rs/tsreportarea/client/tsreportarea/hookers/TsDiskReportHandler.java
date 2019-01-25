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
 
 
package net.datenwerke.rs.tsreportarea.client.tsreportarea.hookers;

import net.datenwerke.gxtdto.client.dtomanager.callback.RsAsyncCallback;
import net.datenwerke.gxtdto.client.locale.BaseMessages;
import net.datenwerke.gxtdto.client.servercommunication.exceptions.ViolatedSecurityExceptionDto;
import net.datenwerke.rs.core.client.reportexecutor.ExecuteReportConfiguration;
import net.datenwerke.rs.core.client.reportexecutor.ReportExecutorUIService;
import net.datenwerke.rs.core.client.reportexecutor.variantstorer.VariantStorerConfig;
import net.datenwerke.rs.core.client.reportmanager.dto.reports.ReportDto;
import net.datenwerke.rs.teamspace.client.teamspace.TeamSpaceUIService;
import net.datenwerke.rs.tsreportarea.client.tsreportarea.TsDiskDao;
import net.datenwerke.rs.tsreportarea.client.tsreportarea.TsDiskTreeManagerDao;
import net.datenwerke.rs.tsreportarea.client.tsreportarea.dto.TsDiskGeneralReferenceDto;
import net.datenwerke.rs.tsreportarea.client.tsreportarea.dto.TsDiskReportReferenceDto;
import net.datenwerke.rs.tsreportarea.client.tsreportarea.helper.config.TeamSpaceViewConfig;
import net.datenwerke.rs.tsreportarea.client.tsreportarea.hooks.GeneralReferenceHandlerHook;
import net.datenwerke.rs.tsreportarea.client.tsreportarea.locale.TsFavoriteMessages;
import net.datenwerke.rs.tsreportarea.client.tsreportarea.ui.TsDiskMainComponent;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;
import net.datenwerke.gxtdto.client.baseex.widget.mb.DwAlertMessageBox;

public class TsDiskReportHandler implements GeneralReferenceHandlerHook {

	private final ReportExecutorUIService reportExecutorService;
	private final TeamSpaceUIService teamSpaceService;
	private final TsDiskDao favoriteDao;
	private final TsDiskTreeManagerDao treeManagerDao;

	
	@Inject
	public TsDiskReportHandler(ReportExecutorUIService reportExecutorService,
			TeamSpaceUIService teamSpaceService,
			TsDiskDao favoriteDao,
			TsDiskTreeManagerDao treeManagerDao) {
		this.reportExecutorService = reportExecutorService;
		this.teamSpaceService = teamSpaceService;
		this.favoriteDao = favoriteDao;
		this.treeManagerDao = treeManagerDao;
	}

	@Override
	public boolean consumes(TsDiskGeneralReferenceDto item) {
		return item instanceof TsDiskReportReferenceDto;
	}

	@Override
	public void handle(TsDiskGeneralReferenceDto genReference, TsDiskMainComponent mainComponent) {
		TsDiskReportReferenceDto reference = (TsDiskReportReferenceDto) genReference;
		
		if(null == reference.getReport())
			new DwAlertMessageBox(BaseMessages.INSTANCE.error(), TsFavoriteMessages.INSTANCE.reportCouldNotBeLoaded()).show();
		else
			displayReport(reference.getReport(), reference, mainComponent);
	}

	private void displayReport(ReportDto report, final TsDiskReportReferenceDto reference, final TsDiskMainComponent mainComponent){
		reportExecutorService.executeReport(report, null, new ExecuteReportConfiguration(){
			
			private TsDiskReportReferenceDto currentReference = reference;
			
			@Override
			public boolean handleError(Throwable t) {
				if(t instanceof ViolatedSecurityExceptionDto){
					new DwAlertMessageBox(TsFavoriteMessages.INSTANCE.violatedSecurityTitle(), TsFavoriteMessages.INSTANCE.violatedSecurityMessage()).show();
					return true;
				}
				return false;
			}
			
			@Override
			public boolean accepctView(String viewId) {
				return true;
			}
			
			@Override
			public VariantStorerConfig getVariantStorerConfig() {
				return new VariantStorerConfig() {
					@Override
					public boolean displayVariantStorer() {
						return teamSpaceService.isUser(mainComponent.getCurrentSpace());
					}
					@Override
					public boolean allowEditVariant() {
						return currentReference.isHardlink();
					}
					@Override
					public boolean displayEditVariantOnStore() {
						return false;
					}
					@Override
					public VariantStorerHandleServerCalls getServerCallHandler() {
						return new VariantStorerHandleServerCalls() {
							
							@Override
							public void editVariant(ReportDto report, String executeToken, String name,
									String description, final AsyncCallback<ReportDto> callback) {
								mainComponent.mask(BaseMessages.INSTANCE.storingMsg());
								reference.setName(name);
								reference.setDescription(description);
								favoriteDao.updateReferenceAndReport(currentReference, report, executeToken, name, description, new RsAsyncCallback<TsDiskReportReferenceDto>(){
									@Override
									public void onSuccess(TsDiskReportReferenceDto result) {
										mainComponent.unmask();
										currentReference = result;
										callback.onSuccess(result.getReport());
									}
									@Override
									public void onFailure(Throwable caught) {
										mainComponent.unmask();
									}
								});
							}
							
							@Override
							public void deleteVariant(ReportDto report, final AsyncCallback<Void> callback) {
								mainComponent.mask(BaseMessages.INSTANCE.storingMsg());
								treeManagerDao.deleteNode(currentReference, new RsAsyncCallback<Void>(){
									@Override
									public void onSuccess(Void result) {
										mainComponent.unmask();
										callback.onSuccess(null);
									}
									@Override
									public void onFailure(Throwable caught) {
										mainComponent.unmask();
									}
								});
							}
							
							@Override
							public void createNewVariant(ReportDto report, String executeToken,
									String name, String desc, final AsyncCallback<ReportDto> callback) {
								/* import report */
								mainComponent.mask(BaseMessages.INSTANCE.storingMsg());
								favoriteDao.createAndImportVariant(mainComponent.getCurrentSpace(), mainComponent.getCurrentFolder(), report, executeToken, name, desc, new RsAsyncCallback<TsDiskReportReferenceDto>(){
									@Override
									public void onSuccess(TsDiskReportReferenceDto result) {
										if(null == result)
											return;
										
										mainComponent.addToStore(result);
										mainComponent.unmask();
										
										currentReference = result;
										callback.onSuccess(result.getReport());
									}
									@Override
									public void onFailure(Throwable caught) {
										mainComponent.unmask();
									}
								});
							}
						};
					}
				};
			}
			
			@Override
			public String getViewId() {return null;};
			
		}, new TeamSpaceViewConfig(mainComponent.getCurrentSpace())
		);

	}
}
