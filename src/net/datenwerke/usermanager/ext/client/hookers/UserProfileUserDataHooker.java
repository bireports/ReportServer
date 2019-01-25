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
 
 
package net.datenwerke.usermanager.ext.client.hookers;

import net.datenwerke.gf.client.login.LoginService;
import net.datenwerke.gxtdto.client.baseex.widget.btn.DwTextButton;
import net.datenwerke.gxtdto.client.forms.simpleform.SimpleForm;
import net.datenwerke.gxtdto.client.forms.simpleform.SimpleFormFieldConfiguration;
import net.datenwerke.gxtdto.client.forms.simpleform.providers.configs.SFFCCustomComponent;
import net.datenwerke.gxtdto.client.forms.simpleform.providers.configs.SFFCReadOnly;
import net.datenwerke.gxtdto.client.forms.simpleform.providers.configs.SFFCStaticLabel;
import net.datenwerke.gxtdto.client.forms.simpleform.providers.dummy.CustomComponent;
import net.datenwerke.gxtdto.client.forms.simpleform.providers.dummy.Separator;
import net.datenwerke.gxtdto.client.forms.simpleform.providers.dummy.StaticLabel;
import net.datenwerke.gxtdto.client.locale.BaseMessages;
import net.datenwerke.gxtdto.client.servercommunication.callback.NotamCallback;
import net.datenwerke.gxtdto.client.utilityservices.submittracker.SubmitTrackerToken;
import net.datenwerke.rs.theme.client.icon.BaseIcon;
import net.datenwerke.rs.userprofile.client.userprofile.hooks.UserProfileCardProviderHookImpl;
import net.datenwerke.security.client.security.dto.WriteDto;
import net.datenwerke.security.client.usermanager.dto.UserDto;
import net.datenwerke.security.client.usermanager.dto.decorator.UserDtoDec;
import net.datenwerke.security.client.usermanager.dto.pa.UserDtoPA;
import net.datenwerke.security.ext.client.password.PasswordServiceDao;
import net.datenwerke.security.ext.client.password.ui.ChangePasswordDialog;
import net.datenwerke.security.ext.client.usermanager.UserManagerDao;
import net.datenwerke.security.ext.client.usermanager.locale.UsermanagerMessages;

import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;

public class UserProfileUserDataHooker extends UserProfileCardProviderHookImpl {

	private final LoginService loginService;
	private final UserManagerDao dao;
	private final PasswordServiceDao passwordService;
	
	private UserDto userData;

	
	@Inject
	public UserProfileUserDataHooker(
		LoginService loginService,
		UserManagerDao dao, 
		PasswordServiceDao passwordService
		){
		
		/* store object */
		this.loginService = loginService;
		this.dao = dao;
		this.passwordService = passwordService;
	}
	
	@Override
	public ImageResource getIcon() {
		return BaseIcon.USER_PROFILE.toImageResource(1);
	}

	@Override
	public String getName() {
		return UsermanagerMessages.INSTANCE.userProfileUserDataName();
	}

	@Override
	public Widget getCard() {
		UserDto user = loginService.getCurrentUser();
		
		/* create model */
		userData = new UserDtoDec();
		userData.setFirstname(user.getFirstname());
		userData.setLastname(user.getLastname());
		userData.setEmail(user.getEmail());
		
		SimpleForm form = SimpleForm.getInlineInstance();
		
		/* ReadOnly */
		SimpleFormFieldConfiguration[] configs;
		if(user.hasAccessRight(WriteDto.class)){
			configs = new SimpleFormFieldConfiguration[]{SFFCReadOnly.FALSE};
		}else{
			configs = new SimpleFormFieldConfiguration[]{SFFCReadOnly.TRUE};
		}
		
		form.beginRow();
		/* first name */
		form.addField(String.class, UserDtoPA.INSTANCE.firstname(), UsermanagerMessages.INSTANCE.firstname(), configs); //$NON-NLS-1$
		
		/* last name */
		form.addField(String.class, UserDtoPA.INSTANCE.lastname(), UsermanagerMessages.INSTANCE.lastname(), configs); //$NON-NLS-1$
		form.endRow();
		
		/* email */
		form.setFieldWidth(0.75);
		form.addField(String.class, UserDtoPA.INSTANCE.email(), UsermanagerMessages.INSTANCE.email(), configs); //$NON-NLS-1$
		
		form.setFieldWidth(1);
		
		form.addField(Separator.class);
		form.addField(StaticLabel.class, new SFFCStaticLabel(){
			@Override
			public String getLabel() {
				return UsermanagerMessages.INSTANCE.changePasswordDescription();
			}
		});
		
		form.setFieldWidth(150);
		form.addField(CustomComponent.class, new SFFCCustomComponent(){
			@Override
			public Widget getComponent() {
				DwTextButton changePasswordBtn = new DwTextButton(UsermanagerMessages.INSTANCE.changePassword());
				changePasswordBtn.setWidth(150);
				changePasswordBtn.addSelectHandler(new SelectHandler() {
					@Override
					public void onSelect(SelectEvent event) {
						final ChangePasswordDialog dialog = new ChangePasswordDialog(true);
						
						dialog.addSubmitHandler(new SelectHandler() {
							@Override
							public void onSelect(SelectEvent event) {
								passwordService.changePassword(dialog.getOldPassword(), dialog.getNewPassword(),
										new NotamCallback<Void>(UsermanagerMessages.INSTANCE.passwordChangeSuccess()){
									@Override
									public void doOnSuccess(Void result) {
										dialog.hide();
									}
								});
							}
						});
						
						dialog.show();
					}
				});
				return changePasswordBtn;
			}
			
		});
		
		form.bind(userData);
		
		return form;
	}
	
	@Override
	public int getHeight() {
		return 400;
	}


	@Override
	public void submitPressed(final SubmitTrackerToken token) {
		UserDto user = loginService.getCurrentUser();

		if(!user.hasAccessRight(WriteDto.class)){
			token.setCompleted();
			return;
		}
		
		user.setFirstname((String) userData.getFirstname());
		user.setLastname((String) userData.getLastname());
		user.setEmail((String) userData.getEmail());
		
		if(user.isFirstnameModified() || user.isLastnameModified() || user.isEmailModified()){
			dao.changeActiveUserData(user, new NotamCallback<UserDto>(BaseMessages.INSTANCE.changesApplied()){
				@Override
				public void doOnSuccess(UserDto result) {
					token.setCompleted();
				}
			});
		}else{
			token.setCompleted();
		}
		
		
	}



}
