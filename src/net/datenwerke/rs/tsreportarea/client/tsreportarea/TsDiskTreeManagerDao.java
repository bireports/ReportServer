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
 
 
package net.datenwerke.rs.tsreportarea.client.tsreportarea;

import java.util.List;

import net.datenwerke.gxtdto.client.baseex.widget.mb.DwConfirmMessageBox;
import net.datenwerke.gxtdto.client.dtomanager.Dto2PosoMapper;
import net.datenwerke.gxtdto.client.dtomanager.callback.DaoAsyncCallback;
import net.datenwerke.gxtdto.client.dtomanager.callback.RsAsyncCallback;
import net.datenwerke.gxtdto.client.locale.BaseMessages;
import net.datenwerke.gxtdto.client.servercommunication.exceptions.NeedForcefulDeleteClientException;
import net.datenwerke.rs.tsreportarea.client.tsreportarea.dto.AbstractTsDiskNodeDto;
import net.datenwerke.rs.tsreportarea.client.tsreportarea.rpc.TsDiskRpcServiceAsync;
import net.datenwerke.treedb.client.treedb.TreeDbManagerDao;
import net.datenwerke.treedb.client.treedb.dto.AbstractNodeDto;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;
import com.sencha.gxt.widget.core.client.Dialog.PredefinedButton;
import com.sencha.gxt.widget.core.client.box.ConfirmMessageBox;
import com.sencha.gxt.widget.core.client.event.DialogHideEvent;
import com.sencha.gxt.widget.core.client.event.DialogHideEvent.DialogHideHandler;

public class TsDiskTreeManagerDao extends TreeDbManagerDao<TsDiskRpcServiceAsync> {

	@Inject
	public TsDiskTreeManagerDao(
			TsDiskRpcServiceAsync treeLoader) {
		super(treeLoader);
	}

	
	public void updateName(AbstractNodeDto nodeDto, boolean changeUnderlyingReport, String name, String description, AsyncCallback<AbstractNodeDto> callback){
		treeManager.updateNode(nodeDto, changeUnderlyingReport, name, description, state, transformDtoCallback(callback));
	}
	
	@Override
	public Dto2PosoMapper getBaseNodeMapper() {
		return AbstractTsDiskNodeDto.newPosoMapper();
	}
	
	public void deleteNodes(List<AbstractNodeDto> nodes, AsyncCallback<Void> callback){
		DaoAsyncCallback<Void> daoCallback = transformAndKeepCallback(callback);
		for(AbstractNodeDto node : nodes)
			daoCallback.addDtoToDetach(node);
		treeManager.deleteNodes(nodes, state, daoCallback);
	}
	
	public void deleteNodesWithForce(List<AbstractNodeDto> nodes, AsyncCallback<Void> callback){
		DaoAsyncCallback<Void> daoCallback = transformAndKeepCallback(callback);
		for(AbstractNodeDto node : nodes)
			daoCallback.addDtoToDetach(node);
		treeManager.deleteNodesWithForce(nodes, state, daoCallback);
	}
	
	public void deleteNodesAndAskForMoreForce(final List<AbstractNodeDto> nodes, final AsyncCallback<Boolean> callback){
		final DaoAsyncCallback<Boolean> daoCallback = transformAndKeepCallback(callback);
		daoCallback.ignoreExpectedExceptions(true);
		treeManager.deleteNodes(nodes, state, new RsAsyncCallback<Void>(){
			@Override
			public void onSuccess(Void result) {
				for(AbstractNodeDto node : nodes)
					daoCallback.addDtoToDetach(node);
				callback.onSuccess(true);
			}
			
			@Override
			public void onFailure(Throwable caught) {
				if(caught instanceof NeedForcefulDeleteClientException){
					ConfirmMessageBox cmb = new DwConfirmMessageBox(BaseMessages.INSTANCE.needForcefulDeleteTitle(), BaseMessages.INSTANCE.needForcefulDeleteMsg(caught.getMessage()));
					cmb.addDialogHideHandler(new DialogHideHandler() {
						
						@Override
						public void onDialogHide(DialogHideEvent event) {
							if (event.getHideButton() == PredefinedButton.YES) {
								deleteNodesWithForce(nodes, new AsyncCallback<Void>() {
									@Override
									public void onFailure(Throwable caught) {
										callback.onFailure(caught);
									}

									@Override
									public void onSuccess(Void result) {
										for(AbstractNodeDto node : nodes)
											daoCallback.addDtoToDetach(node);
										callback.onSuccess(true);
									}
								});
							} else
								daoCallback.onSuccess(false);
						}
					});
					cmb.show();
				} else  {
					daoCallback.ignoreExpectedExceptions(false);
					daoCallback.onFailure(caught);
				}
			}
		});
	}
}
