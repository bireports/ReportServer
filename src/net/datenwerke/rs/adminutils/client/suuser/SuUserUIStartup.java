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
 
 
package net.datenwerke.rs.adminutils.client.suuser;

import net.datenwerke.gxtdto.client.baseex.widget.DwWindow;
import net.datenwerke.gxtdto.client.baseex.widget.DwWindow.OnButtonClickHandler;
import net.datenwerke.gxtdto.client.dtomanager.callback.RsAsyncCallback;
import net.datenwerke.gxtdto.client.forms.simpleform.SimpleForm;
import net.datenwerke.gxtdto.client.utilityservices.UtilsUIService;
import net.datenwerke.gxtdto.client.waitonevent.SynchronousCallbackOnEventTrigger;
import net.datenwerke.gxtdto.client.waitonevent.WaitOnEventTicket;
import net.datenwerke.gxtdto.client.waitonevent.WaitOnEventUIService;
import net.datenwerke.hookhandler.shared.hookhandler.HookHandlerService;
import net.datenwerke.rs.adminutils.client.suuser.locale.SuMessages;
import net.datenwerke.rs.adminutils.client.suuser.security.SuGenericTargetIdentifier;
import net.datenwerke.rs.adminutils.client.suuser.security.SuViewSecurityTargetDomainHooker;
import net.datenwerke.rs.theme.client.icon.BaseIcon;
import net.datenwerke.security.client.security.SecurityUIService;
import net.datenwerke.security.client.security.dto.ExecuteDto;
import net.datenwerke.security.client.security.hooks.GenericSecurityViewDomainHook;
import net.datenwerke.security.client.security.hooks.GenericTargetProviderHook;
import net.datenwerke.security.client.usermanager.dto.ie.StrippedDownUser;
import net.datenwerke.security.ext.client.usermanager.helper.simpleform.SFFCUser;

import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Event.NativePreviewEvent;
import com.google.gwt.user.client.Event.NativePreviewHandler;
import com.google.inject.Inject;
import com.sencha.gxt.widget.core.client.container.MarginData;

public class SuUserUIStartup {

	@Inject
	public SuUserUIStartup(
			final HookHandlerService hookHandler,
			final SecurityUIService securityService,
			final WaitOnEventUIService waitOnEventService,

			SuViewSecurityTargetDomainHooker securityTargetDomain,
			
			final SuUserDao suUserDao,
			final UtilsUIService utilsService
			){
			
			/* attach security target domains */
			hookHandler.attachHooker(GenericTargetProviderHook.class, new GenericTargetProviderHook(securityTargetDomain.genericSecurityViewDomainHook_getTargetId()));
			hookHandler.attachHooker(GenericSecurityViewDomainHook.class, securityTargetDomain);

		
			waitOnEventService.callbackOnEvent(SecurityUIService.REPORTSERVER_EVENT_GENERIC_RIGHTS_LOADED, new SynchronousCallbackOnEventTrigger(){
				public void execute(final WaitOnEventTicket ticket) {
					if(securityService.hasRight(SuGenericTargetIdentifier.class, ExecuteDto.class))
						attachSu(suUserDao, utilsService);

					waitOnEventService.signalProcessingDone(ticket);
				}
			});
		}

	protected void attachSu(final SuUserDao suUserDao, final UtilsUIService utilsService) {
		Event.addNativePreviewHandler(new NativePreviewHandler(){
			@Override
			public void onPreviewNativeEvent(NativePreviewEvent event) {
				if(event.getTypeInt() == Event.ONKEYUP){
					boolean ctrlKey = event.getNativeEvent().getCtrlKey();
					if(ctrlKey){
						int keyCode = event.getNativeEvent().getKeyCode();
						if(ctrlKey && (keyCode == 'L' || keyCode == 'l')){
							DwWindow window = new DwWindow();
							window.setModal(true);
							window.setHeaderIcon(BaseIcon.USER_SECRET);
							window.setSize(300, 180);
							window.setResizable(false);
							window.setHeadingText(SuMessages.INSTANCE.suPromptHeader());
							
							final SimpleForm form = SimpleForm.getInlineInstance();
							final String userKey = form.addField(StrippedDownUser.class, SuMessages.INSTANCE.userLabel(), new SFFCUser() {
								@Override
								public boolean isMultiSelect() {
									return false;
								}
							});
							
							form.loadFields();
							window.add(form, new MarginData(10));
							
							window.addCancelButton();
							window.addSubmitButton(new OnButtonClickHandler() {
								@Override
								public void onClick() {
									StrippedDownUser user = (StrippedDownUser) form.getValue(userKey);
									suUserDao.su(user.getId(), new RsAsyncCallback<Void>(){
										public void onSuccess(Void result) {
											utilsService.reloadPageWithoutAsking();
										};
									});
								}
							});
							
							window.show();
						}
					}
				}
			}
			
		}); 
	}
}
