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
 
 
package net.datenwerke.rs.teamspace.client.teamspace.dto;

import com.google.gwt.core.client.GWT;
import java.lang.IllegalStateException;
import java.lang.Long;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import net.datenwerke.dtoservices.dtogenerator.annotations.GeneratedType;
import net.datenwerke.gf.base.client.dtogenerator.RsDto;
import net.datenwerke.gxtdto.client.dtomanager.Dto2PosoMapper;
import net.datenwerke.gxtdto.client.dtomanager.Dto;
import net.datenwerke.gxtdto.client.dtomanager.DtoView;
import net.datenwerke.gxtdto.client.dtomanager.IdedDto;
import net.datenwerke.gxtdto.client.dtomanager.PropertyAccessor;
import net.datenwerke.gxtdto.client.dtomanager.redoundo.ChangeTracker;
import net.datenwerke.gxtdto.client.eventbus.events.ObjectChangedEvent;
import net.datenwerke.gxtdto.client.eventbus.handlers.ObjectChangedEventHandler;
import net.datenwerke.gxtdto.client.eventbus.handlers.has.HasObjectChangedEventHandler;
import net.datenwerke.rs.teamspace.client.teamspace.dto.TeamSpaceRoleDto;
import net.datenwerke.rs.teamspace.client.teamspace.dto.pa.TeamSpaceMemberDtoPA;
import net.datenwerke.rs.teamspace.client.teamspace.dto.posomap.TeamSpaceMemberDto2PosoMap;
import net.datenwerke.rs.teamspace.service.teamspace.entities.TeamSpaceMember;
import net.datenwerke.security.client.usermanager.dto.UserDto;

/**
 * Dto for {@link TeamSpaceMember}
 *
 * This file was automatically created by DtoAnnotationProcessor, version 0.1
 */
@GeneratedType("net.datenwerke.dtoservices.dtogenerator.DtoAnnotationProcessor")
public class TeamSpaceMemberDto extends RsDto implements IdedDto {


	private static final long serialVersionUID = 1;

	private Long dtoId;


	/* Fields */
	private Long id;
	private  boolean id_m;
	public static final String PROPERTY_ID = "dpi-teamspacemember-id";

	private transient static PropertyAccessor<TeamSpaceMemberDto, Long> id_pa = new PropertyAccessor<TeamSpaceMemberDto, Long>() {
		@Override
		public void setValue(TeamSpaceMemberDto container, Long object) {
			// id field
		}

		@Override
		public Long getValue(TeamSpaceMemberDto container) {
			return container.getId();
		}

		@Override
		public Class<?> getType() {
			return Long.class;
		}

		@Override
		public String getPath() {
			return "id";
		}

		@Override
		public void setModified(TeamSpaceMemberDto container, boolean modified) {
			container.id_m = modified;
		}

		@Override
		public boolean isModified(TeamSpaceMemberDto container) {
			return container.isIdModified();
		}
	};

	private TeamSpaceRoleDto role;
	private  boolean role_m;
	public static final String PROPERTY_ROLE = "dpi-teamspacemember-role";

	private transient static PropertyAccessor<TeamSpaceMemberDto, TeamSpaceRoleDto> role_pa = new PropertyAccessor<TeamSpaceMemberDto, TeamSpaceRoleDto>() {
		@Override
		public void setValue(TeamSpaceMemberDto container, TeamSpaceRoleDto object) {
			container.setRole(object);
		}

		@Override
		public TeamSpaceRoleDto getValue(TeamSpaceMemberDto container) {
			return container.getRole();
		}

		@Override
		public Class<?> getType() {
			return TeamSpaceRoleDto.class;
		}

		@Override
		public String getPath() {
			return "role";
		}

		@Override
		public void setModified(TeamSpaceMemberDto container, boolean modified) {
			container.role_m = modified;
		}

		@Override
		public boolean isModified(TeamSpaceMemberDto container) {
			return container.isRoleModified();
		}
	};

	private UserDto user;
	private  boolean user_m;
	public static final String PROPERTY_USER = "dpi-teamspacemember-user";

	private transient static PropertyAccessor<TeamSpaceMemberDto, UserDto> user_pa = new PropertyAccessor<TeamSpaceMemberDto, UserDto>() {
		@Override
		public void setValue(TeamSpaceMemberDto container, UserDto object) {
			container.setUser(object);
		}

		@Override
		public UserDto getValue(TeamSpaceMemberDto container) {
			return container.getUser();
		}

		@Override
		public Class<?> getType() {
			return UserDto.class;
		}

		@Override
		public String getPath() {
			return "user";
		}

		@Override
		public void setModified(TeamSpaceMemberDto container, boolean modified) {
			container.user_m = modified;
		}

		@Override
		public boolean isModified(TeamSpaceMemberDto container) {
			return container.isUserModified();
		}
	};


	public TeamSpaceMemberDto() {
		super();
	}

	public final Long getId()  {
		return dtoId;
	}

	public final void setId(Long id)  {
		if (null != dtoId)
			throw new IllegalStateException("Id already set!");
		this.dtoId = id;
	}

	public boolean isIdModified()  {
		return id_m;
	}


	public static PropertyAccessor<TeamSpaceMemberDto, Long> getIdPropertyAccessor()  {
		return id_pa;
	}


	public TeamSpaceRoleDto getRole()  {
		if(! isDtoProxy()){
			return this.role;
		}

		if(isRoleModified())
			return this.role;

		if(! GWT.isClient())
			return null;

		TeamSpaceRoleDto _value = dtoManager.getProperty(this, instantiatePropertyAccess().role());

		return _value;
	}


	public void setRole(TeamSpaceRoleDto role)  {
		/* old value */
		TeamSpaceRoleDto oldValue = null;
		if(GWT.isClient())
			oldValue = getRole();

		/* set new value */
		this.role = role;

		if(! GWT.isClient())
			return;

		if(isTrackChanges())
			addChange(new ChangeTracker(role_pa, oldValue, role, this.role_m));

		/* set indicator */
		this.role_m = true;

		this.fireObjectChangedEvent(TeamSpaceMemberDtoPA.INSTANCE.role(), oldValue);
	}


	public boolean isRoleModified()  {
		return role_m;
	}


	public static PropertyAccessor<TeamSpaceMemberDto, TeamSpaceRoleDto> getRolePropertyAccessor()  {
		return role_pa;
	}


	public UserDto getUser()  {
		if(! isDtoProxy()){
			return this.user;
		}

		if(isUserModified())
			return this.user;

		if(! GWT.isClient())
			return null;

		UserDto _value = dtoManager.getProperty(this, instantiatePropertyAccess().user());

		if(_value instanceof HasObjectChangedEventHandler){
			((HasObjectChangedEventHandler)_value).addObjectChangedHandler(new net.datenwerke.gxtdto.client.eventbus.handlers.ObjectChangedEventHandler(){
				@Override
				public void onObjectChangedEvent(net.datenwerke.gxtdto.client.eventbus.events.ObjectChangedEvent event){
					if(! isUserModified())
						setUser((UserDto) event.getObject());
				}
			}
			);
		}
		return _value;
	}


	public void setUser(UserDto user)  {
		/* old value */
		UserDto oldValue = null;
		if(GWT.isClient())
			oldValue = getUser();

		/* set new value */
		this.user = user;

		if(! GWT.isClient())
			return;

		if(isTrackChanges())
			addChange(new ChangeTracker(user_pa, oldValue, user, this.user_m));

		/* set indicator */
		this.user_m = true;

		this.fireObjectChangedEvent(TeamSpaceMemberDtoPA.INSTANCE.user(), oldValue);
	}


	public boolean isUserModified()  {
		return user_m;
	}


	public static PropertyAccessor<TeamSpaceMemberDto, UserDto> getUserPropertyAccessor()  {
		return user_pa;
	}


	@Override
	public void setDtoId(Object id)  {
		setId((Long) id);
	}

	@Override
	public Object getDtoId()  {
		return getId();
	}

	@Override
	public int hashCode()  {
		if(null == getId())
			return super.hashCode();
		return getId().hashCode();
	}

	@Override
	public boolean equals(Object obj)  {
		if(! (obj instanceof TeamSpaceMemberDto))
			return false;

		if(null == getId())
			return super.equals(obj);
		return getId().equals(((TeamSpaceMemberDto)obj).getId());
	}

	@Override
	public String toString()  {
		return getClass().getName() + ": " + getId();
	}

	public static Dto2PosoMapper newPosoMapper()  {
		return new TeamSpaceMemberDto2PosoMap();
	}

	public TeamSpaceMemberDtoPA instantiatePropertyAccess()  {
		return GWT.create(TeamSpaceMemberDtoPA.class);
	}

	public void clearModified()  {
		this.id = null;
		this.id_m = false;
		this.role = null;
		this.role_m = false;
		this.user = null;
		this.user_m = false;
	}


	public boolean isModified()  {
		if(super.isModified())
			return true;
		if(id_m)
			return true;
		if(role_m)
			return true;
		if(user_m)
			return true;
		return false;
	}


	public List<PropertyAccessor> getPropertyAccessors()  {
		List<PropertyAccessor> list = super.getPropertyAccessors();
		list.add(id_pa);
		list.add(role_pa);
		list.add(user_pa);
		return list;
	}


	public List<PropertyAccessor> getModifiedPropertyAccessors()  {
		List<PropertyAccessor> list = super.getModifiedPropertyAccessors();
		if(id_m)
			list.add(id_pa);
		if(role_m)
			list.add(role_pa);
		if(user_m)
			list.add(user_pa);
		return list;
	}


	public List<PropertyAccessor> getPropertyAccessorsByView(net.datenwerke.gxtdto.client.dtomanager.DtoView view)  {
		List<PropertyAccessor> list = super.getPropertyAccessorsByView(view);
		if(view.compareTo(DtoView.MINIMAL) >= 0){
			list.add(id_pa);
		}
		if(view.compareTo(DtoView.LIST) >= 0){
			list.add(user_pa);
		}
		if(view.compareTo(DtoView.NORMAL) >= 0){
			list.add(role_pa);
		}
		return list;
	}


	public List<PropertyAccessor> getPropertyAccessorsForDtos()  {
		List<PropertyAccessor> list = super.getPropertyAccessorsForDtos();
		list.add(user_pa);
		return list;
	}



	net.datenwerke.security.client.usermanager.dto.UserDto wl_0;
	net.datenwerke.rs.teamspace.client.teamspace.dto.TeamSpaceRoleDto wl_1;

}
