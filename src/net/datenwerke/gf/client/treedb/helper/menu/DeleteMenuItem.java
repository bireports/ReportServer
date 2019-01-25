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
 
 
package net.datenwerke.gf.client.treedb.helper.menu;

import net.datenwerke.gf.client.treedb.UITree;
import net.datenwerke.gxtdto.client.baseex.widget.mb.DwConfirmMessageBox;
import net.datenwerke.gxtdto.client.dtomanager.callback.RsAsyncCallback;
import net.datenwerke.gxtdto.client.locale.BaseMessages;
import net.datenwerke.rs.theme.client.icon.BaseIcon;
import net.datenwerke.security.client.security.dto.DeleteDto;
import net.datenwerke.security.client.treedb.dto.decorator.SecuredAbstractNodeDtoDec;
import net.datenwerke.treedb.client.treedb.TreeDbManagerDao;
import net.datenwerke.treedb.client.treedb.dto.AbstractNodeDto;
import net.datenwerke.treedb.client.treedb.locale.TreedbMessages;

import com.sencha.gxt.widget.core.client.Dialog.PredefinedButton;
import com.sencha.gxt.widget.core.client.box.ConfirmMessageBox;
import com.sencha.gxt.widget.core.client.event.DialogHideEvent;
import com.sencha.gxt.widget.core.client.event.DialogHideEvent.DialogHideHandler;
import com.sencha.gxt.widget.core.client.info.Info;

public class DeleteMenuItem extends TreeMenuItem {
	
	public DeleteMenuItem(final TreeDbManagerDao treeManager){
		super();
		
		setIcon(BaseIcon.DELETE);
		setText(BaseMessages.INSTANCE.remove());
		addMenuSelectionListener(new TreeMenuSelectionEvent() {
			
			public void menuItemSelected(final UITree tree, final AbstractNodeDto node) {
				/* confirm delete */
				ConfirmMessageBox cmb = new DwConfirmMessageBox(
						BaseMessages.INSTANCE.confirmDeleteTitle(), 
						BaseMessages.INSTANCE.confirmDeleteMsg(node.toDisplayTitle()));
				
				cmb.addDialogHideHandler(new DialogHideHandler() {
					
					@Override
					public void onDialogHide(DialogHideEvent event) {
						if (event.getHideButton() == PredefinedButton.YES) {
							/* delete */
							treeManager.deleteNodeAndAskForMoreForce(node, new RsAsyncCallback<Boolean>() { 
								@Override
								public void onSuccess(Boolean result) {
									if(Boolean.TRUE.equals(result)){
										tree.getStore().remove(node);
										Info.display(BaseMessages.INSTANCE.changesApplied(), TreedbMessages.INSTANCE.deleted());
									}
								}
							});
							
							/* select parent */
							final AbstractNodeDto parent = tree.getStore().getParent(node);
							if(null != parent) {
								/* https://www.sencha.com/forum/showthread.php?308983-GXT-3.1.4.-TreeSelectionModel-RightClick-sets-mousdown-flag&p=1128504#post1128504 */
								tree.releaseMouseDownFlag();
								tree.getSelectionModel().select(parent, false);
							}
						}	
					}
				});
				
				cmb.show();
			}
		});
	}
	
	@Override
	public void toBeDisplayed(AbstractNodeDto selectedItem) {
		disable();
		
		if(! (selectedItem instanceof SecuredAbstractNodeDtoDec) || ! ((SecuredAbstractNodeDtoDec)selectedItem).isAccessRightsLoaded()|| ((SecuredAbstractNodeDtoDec)selectedItem).hasAccessRight(DeleteDto.class))
			enable();
	}
}
