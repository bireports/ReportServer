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
 
 
package net.datenwerke.rs.teamspace.client.teamspace.helpers.simpleform;

import java.util.LinkedHashMap;
import java.util.Map;

import net.datenwerke.gxtdto.client.forms.simpleform.providers.configs.SFFCBaseModel;
import net.datenwerke.rs.teamspace.client.teamspace.dto.TeamSpaceDto;
import net.datenwerke.rs.teamspace.client.teamspace.dto.TeamSpaceMemberDto;
import net.datenwerke.security.client.usermanager.dto.UserDto;
import net.datenwerke.security.client.usermanager.dto.pa.UserDtoPA;
import net.datenwerke.security.ext.client.usermanager.locale.UsermanagerMessages;

import com.google.gwt.core.client.GWT;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.ListStore;

public class SFFCTeamSpaceMemberAsUser implements SFFCBaseModel<UserDto> {

	private static UserDtoPA userPa = GWT.create(UserDtoPA.class);
	
	private final TeamSpaceDto teamSpace;
	
	public SFFCTeamSpaceMemberAsUser(
		TeamSpaceDto teamSpace
		){
		
		this.teamSpace = teamSpace;
	}
	
	public ListStore<UserDto> getAllItemsStore(){
		ListStore<UserDto> store = new ListStore<UserDto>(userPa.dtoId());
		
		for(TeamSpaceMemberDto member : teamSpace.getMembers())
			if(member.getFolk() instanceof UserDto)
				store.add((UserDto) member.getFolk());
		
		return store;
	}
	
	public Map<ValueProvider<UserDto, String>, String> getDisplayProperties(){
		Map<ValueProvider<UserDto, String>, String> displayProperties = new LinkedHashMap<ValueProvider<UserDto, String>, String>();
		
		displayProperties.put(userPa.firstname(), UsermanagerMessages.INSTANCE.firstname());
		displayProperties.put(userPa.lastname(), UsermanagerMessages.INSTANCE.lastname());
		
		return displayProperties;
	}
	
	public boolean isMultiSelect(){
		return false;
	}
}
