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
 
 
package net.datenwerke.security.ext.client.usermanager.ui.forms;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.datenwerke.gf.client.managerhelper.mainpanel.SimpleFormView;
import net.datenwerke.gxtdto.client.forms.simpleform.SimpleForm;
import net.datenwerke.gxtdto.client.forms.simpleform.providers.configs.SFFCAllowBlank;
import net.datenwerke.gxtdto.client.forms.simpleform.providers.configs.SFFCPasswordField;
import net.datenwerke.gxtdto.client.forms.simpleform.providers.configs.impl.SFFCStaticDropdownList;
import net.datenwerke.security.client.usermanager.dto.SexDto;
import net.datenwerke.security.client.usermanager.dto.UserDto;
import net.datenwerke.security.client.usermanager.dto.pa.UserDtoPA;
import net.datenwerke.security.ext.client.usermanager.locale.UsermanagerMessages;

import com.google.inject.Inject;
import com.sencha.gxt.widget.core.client.container.MarginData;

public class UserForm extends SimpleFormView {

	@Inject
	public UserForm(){
		super();
	}
	
	public void configureSimpleForm(SimpleForm form) {
		/* build form */
		form.setHeadingText(UsermanagerMessages.INSTANCE.editUser() + (getSelectedNode() == null ? "" : " (" + getSelectedNode().getId() + ")"));

		form.beginRow();
		
		/* personal fields */
		form.beginColumn(new MarginData(0,5,0,0));
		
		/* sex */
		form.setFieldWidth(200);
		form.addField(
				List.class, UserDtoPA.INSTANCE.sex(), UsermanagerMessages.INSTANCE.gender(), //$NON-NLS-1$
				new SFFCStaticDropdownList<SexDto>() {
					public Map<String, SexDto> getValues() {
						Map<String, SexDto> map = new HashMap<String, SexDto>();
						
						map.put(UsermanagerMessages.INSTANCE.genderMale(), SexDto.Male);
						map.put(UsermanagerMessages.INSTANCE.genderFemale(), SexDto.Female);
						
						return map;
					}
					@Override
					public boolean allowBlank() {
						return true;
					}
			});
		form.setFieldWidth(1);
	
		/* first name */
		form.addField(String.class, UserDtoPA.INSTANCE.firstname(), UsermanagerMessages.INSTANCE.firstname()); //$NON-NLS-1$
		
		/* last name */
		form.addField(String.class, UserDtoPA.INSTANCE.lastname(), UsermanagerMessages.INSTANCE.lastname(), new SFFCAllowBlank() {
			
			@Override
			public boolean allowBlank() {
				return false;
			}
		}); //$NON-NLS-1$
		
		/* end personal */
		form.endColumn();
		
		/* security */
		form.beginColumn();
		
		/* user name */
		form.addField(String.class, UserDtoPA.INSTANCE.username(), UsermanagerMessages.INSTANCE.username(),  new SFFCAllowBlank() {
			
			@Override
			public boolean allowBlank() {
				return false;
			}
		}); //$NON-NLS-1$
		
		/* password field */
		form.addField(String.class, UserDtoPA.INSTANCE.password(), UsermanagerMessages.INSTANCE.password(), new SFFCPasswordField() {
			@Override
			public Boolean isPasswordSet() {
				return ((UserDto)getSelectedNode()).isHasPassword();
			}
		}); //$NON-NLS-1$
		
		/* email */
		form.addField(String.class, UserDtoPA.INSTANCE.email(), UsermanagerMessages.INSTANCE.email()); //$NON-NLS-1$
		
		form.endColumn();
		
		form.endRow();
		
		form.setValidateOnSubmit(true);
	}

}
