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

import net.datenwerke.gxtdto.client.forms.simpleform.SimpleFormFieldConfiguration;
import net.datenwerke.gxtdto.client.forms.simpleform.providers.DtoModelProvider;
import net.datenwerke.gxtdto.client.forms.simpleform.providers.configs.SFFCBaseModel;
import net.datenwerke.gxtdto.client.forms.simpleform.providers.configs.SFFCTeamSpace;
import net.datenwerke.gxtdto.client.locale.BaseMessages;
import net.datenwerke.rs.teamspace.client.teamspace.TeamSpaceUIService;
import net.datenwerke.rs.teamspace.client.teamspace.dto.TeamSpaceDto;
import net.datenwerke.rs.teamspace.client.teamspace.dto.pa.TeamSpaceDtoPA;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.ListStore;

public class TeamSpaceProvider extends DtoModelProvider{

	private static TeamSpaceDtoPA tsPa = GWT.create(TeamSpaceDtoPA.class);
	
	private final TeamSpaceUIService teamSpaceService;
	
	@Inject
	public TeamSpaceProvider(
		TeamSpaceUIService teamSpaceService	
		){
		
		/* store objects */
		this.teamSpaceService = teamSpaceService;
	}
	
	@Override
	public boolean doConsumes(Class<?> type,
			SimpleFormFieldConfiguration... configs) {
		return type.equals(TeamSpaceDto.class);
	}
	
	@Override
	public Widget createFormField() {
		final Map<ValueProvider<TeamSpaceDto, String>, String> displayProperties = new LinkedHashMap<ValueProvider<TeamSpaceDto, String>, String>();
		displayProperties.put(tsPa.name(), BaseMessages.INSTANCE.name());
		displayProperties.put(new ValueProvider<TeamSpaceDto, String>() {
			
			@Override
			public void setValue(TeamSpaceDto object, String value) {
			}
			
			@Override
			public String getValue(TeamSpaceDto object) {
				return String.valueOf(object.getId());
			}
			
			@Override
			public String getPath() {
				return "";
			}
		}, BaseMessages.INSTANCE.id());
		
		final SFFCTeamSpace teamSpaceConfig = getTeamSpaceConfig();
		
		configs = new SimpleFormFieldConfiguration[]{
			new SFFCBaseModel<TeamSpaceDto>() {
				@Override
				public ListStore<TeamSpaceDto> getAllItemsStore() {
					if(teamSpaceConfig.isLoadAll())
						return TeamSpaceProvider.this.teamSpaceService.getAllTeamSpacesStore();
					return TeamSpaceProvider.this.teamSpaceService.getTeamSpacesStore();
				}

				@Override
				public Map<ValueProvider<TeamSpaceDto, String>, String> getDisplayProperties() {
					return displayProperties;
				}

				@Override
				public boolean isMultiSelect() {
					return teamSpaceConfig.isMulti();
				}
				
			}
		};
		
		return super.createFormField();
	}

	private SFFCTeamSpace getTeamSpaceConfig() {
		for(SimpleFormFieldConfiguration config : configs)
			if(config instanceof SFFCTeamSpace)
				return (SFFCTeamSpace) config;
		return new SFFCTeamSpace() {
			@Override
			public boolean isMulti() {
				return false;
			}

			@Override
			public boolean isLoadAll() {
				return false;
			}
		};
	}
}
