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
 
 
package net.datenwerke.gf.client.treedb.helper.menu;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.client.Command;
import com.sencha.gxt.widget.core.client.event.ExpandItemEvent;
import com.sencha.gxt.widget.core.client.event.ExpandItemEvent.ExpandItemHandler;

import net.datenwerke.gf.client.treedb.UITree;
import net.datenwerke.gxtdto.client.servercommunication.callback.NotamCallback;
import net.datenwerke.rs.theme.client.icon.BaseIcon;
import net.datenwerke.security.client.security.dto.WriteDto;
import net.datenwerke.security.client.treedb.dto.decorator.SecuredAbstractNodeDtoDec;
import net.datenwerke.treedb.client.treedb.TreeDbManagerDao;
import net.datenwerke.treedb.client.treedb.dto.AbstractNodeDto;
import net.datenwerke.treedb.client.treedb.dto.decorator.AbstractNodeDtoDec;
import net.datenwerke.treedb.client.treedb.locale.TreedbMessages;

public class InsertMenuItem extends TreeMenuItem {
	
	private AvailabilityCallback availableCallback = AvailabilityCallback.TRUE_INSTANCE;

	public InsertMenuItem(final AbstractNodeDto dummyNode, String text, final TreeDbManagerDao treeManager){
		this(dummyNode, text, treeManager, (BaseIcon) null);
	}
	
	public InsertMenuItem(final AbstractNodeDto dummyNode, String text, final TreeDbManagerDao treeManager, BaseIcon icon){
		this(dummyNode, text, treeManager, null == icon ? (ImageResource) null: icon.toImageResource());
		if(null != icon)
			setIcon(icon);
	}
	
	public InsertMenuItem(final AbstractNodeDto dummyNode, String text, final TreeDbManagerDao treeManager, ImageResource icon){
		super();
		
		if(null != icon)
			setIcon(icon);
		
		setText(text);
		
		addMenuSelectionListener(new TreeMenuSelectionEvent() {
			@SuppressWarnings("unchecked")
			public void menuItemSelected(final UITree tree, final AbstractNodeDto node) {
				treeManager.insertNode(dummyNode, node, new NotamCallback<AbstractNodeDto>(TreedbMessages.INSTANCE.inserted()) {
					private HandlerRegistration addExpandHandler;

					@Override
					public void doOnSuccess(final AbstractNodeDto insertedNode) {
						/* tell new parent that it has now children */
						((AbstractNodeDtoDec)node).setHasChildren(true);

						/* https://www.sencha.com/forum/showthread.php?308983-GXT-3.1.4.-TreeSelectionModel-RightClick-sets-mousdown-flag&p=1128504#post1128504 */
						tree.releaseMouseDownFlag();
						
						/* if not expanded .. expand node */
						if(! tree.isExpanded(node)){
							if(null != addExpandHandler)
								addExpandHandler.removeHandler();
							
							addExpandHandler = tree.addExpandHandler(new ExpandItemHandler<AbstractNodeDto>() {
								@Override
								public void onExpand(ExpandItemEvent<AbstractNodeDto> event) {
									if(null != addExpandHandler)
										addExpandHandler.removeHandler();
									Scheduler.get().scheduleDeferred(new Command(){
										@Override
										public void execute() {
											tree.getSelectionModel().select(insertedNode, false);
										}
									});
								}
							});
							tree.setLeaf(node, false);
							tree.setExpanded(node, true);
						} else {
							/* the node should have been inserted directly .. select node */
							Scheduler.get().scheduleDeferred(new Command(){
								@Override
								public void execute() {
									if(tree.isExpanded(node) )
										tree.getSelectionModel().select(insertedNode, false);								
								}
							});
						}
					}
					
				});

			}
		});
	}

	@Override
	public void toBeDisplayed(AbstractNodeDto selectedItem) {
		disable();
		
		if(! availableCallback.isAvailable())
			return;
		
		if(! (selectedItem instanceof SecuredAbstractNodeDtoDec) || 
		   ! ((SecuredAbstractNodeDtoDec)selectedItem).isAccessRightsLoaded() || 
		   (
			    ((SecuredAbstractNodeDtoDec)selectedItem).hasAccessRight(WriteDto.class) &&
			    ((SecuredAbstractNodeDtoDec)selectedItem).hasInheritedAccessRight(WriteDto.class)
		   )
		) {
			enable();
		}
	}

	public void setAvailableCallback(AvailabilityCallback availableCallback) {
		this.availableCallback = availableCallback;
	}
}
