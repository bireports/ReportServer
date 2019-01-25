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
 
 
package net.datenwerke.rs.core.client.i18tools.hookers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import net.datenwerke.gxtdto.client.dtomanager.callback.RsAsyncCallback;
import net.datenwerke.gxtdto.client.forms.simpleform.SimpleForm;
import net.datenwerke.gxtdto.client.forms.simpleform.providers.configs.impl.SFFCStaticDropdownList;
import net.datenwerke.gxtdto.client.utilityservices.submittracker.SubmitTrackerToken;
import net.datenwerke.rs.core.client.i18tools.I18nToolsDao;
import net.datenwerke.rs.core.client.reportexecutor.locale.ReportexecutorMessages;
import net.datenwerke.rs.core.client.userprofile.UserProfileViewContentHook;
import net.datenwerke.security.client.usermanager.dto.UserDto;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Widget;

public class LocalDecimalFormatUserProfileViewContentHooker implements UserProfileViewContentHook {

	private SimpleForm form;
	private String fKey;
	private I18nToolsDao i18nTools;
	
	@Inject
	public LocalDecimalFormatUserProfileViewContentHooker(I18nToolsDao i18nTools) {
		this.i18nTools = i18nTools;
	}

	@Override
	public void submitPressed(final SubmitTrackerToken token) {
		final RsAsyncCallback<Void> cb = new RsAsyncCallback<Void>(){
			@Override
			public void onSuccess(Void result) {
				token.setCompleted();
			}
			
			@Override
			public void onFailure(Throwable caught) {
				token.failure(caught);
			}
		};
		
		i18nTools.setDecimalSeparator((String) form.getValue(fKey), cb);
	}

	@Override
	public Widget getComponent(UserDto user) {
		form = SimpleForm.getInlineInstance();

		form.setFieldWidth(150);
		fKey = form.addField(List.class, ReportexecutorMessages.INSTANCE.decimalSeparator(), new SFFCStaticDropdownList<String>() {

			@Override
			public Map<String, String> getValues() {
				HashMap<String, String> map = new HashMap<String, String>();
				map.put(",", ",");
				map.put(".", ".");
				return map;
			}
		});


		i18nTools.getDecimalSeparator(new AsyncCallback<String>() {
			
			@Override
			public void onSuccess(String result) {
				form.setValue(fKey, result);
			}
			
			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				
			}
		});

		form.loadFields();

		return form;
	}

}
