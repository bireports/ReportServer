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
 
 
package net.datenwerke.rs.teamspace.client.teamspace.dto;

import net.datenwerke.gf.base.client.dtogenerator.RsDto;
import net.datenwerke.security.client.usermanager.dto.UserDto;
import net.datenwerke.security.client.usermanager.dto.ie.StrippedDownUser;


public class StrippedDownTeamSpaceMemberDto extends RsDto {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4457293462541571533L;

	public static final String PROPERTY_USER_ID = "userID";
	public static final String PROPERTY_ROLE = "role";
	public static final String PROPERTY_FIRSTNAME = "firstname";
	public static final String PROPERTY_LASTNAME = "lastname";

	private Long userId;
	private TeamSpaceRoleDto role;
	private String firstname;
	private String lastname;
	private boolean owner;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public TeamSpaceRoleDto getRole() {
		return role;
	}

	public void setRole(TeamSpaceRoleDto role) {
		this.role = role;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	@Override
	public int hashCode() {
		return getUserId().hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if(! (obj instanceof StrippedDownTeamSpaceMemberDto))
			return false;

		return getUserId().equals(((StrippedDownTeamSpaceMemberDto)obj).getUserId());
	}

	public static StrippedDownTeamSpaceMemberDto createFrom(
			StrippedDownUser user) {
		StrippedDownTeamSpaceMemberDto member = new StrippedDownTeamSpaceMemberDto();

		member.setUserId(user.getId());
		member.setFirstname(user.getFirstname());
		member.setLastname(user.getLastname());
		member.setRole(TeamSpaceRoleDto.GUEST);

		return member;
	}

	public static StrippedDownTeamSpaceMemberDto createForOwner(UserDto user) {
		StrippedDownTeamSpaceMemberDto member = new StrippedDownTeamSpaceMemberDto();

		member.setUserId(user.getId());
		member.setFirstname(user.getFirstname());
		member.setLastname(user.getLastname());
		member.setRole(TeamSpaceRoleDto.ADMIN);
		member.setOwner(true);

		return member;
	}


	public static StrippedDownTeamSpaceMemberDto createFrom(TeamSpaceMemberDto member) {
		StrippedDownTeamSpaceMemberDto sMember = new StrippedDownTeamSpaceMemberDto();

		sMember.setUserId(member.getUser().getId());
		sMember.setFirstname(member.getUser().getFirstname());
		sMember.setLastname(member.getUser().getLastname());
		sMember.setRole(member.getRole());

		return sMember;
	}

	public StrippedDownUser getStrippedUser() {
		StrippedDownUser user = new StrippedDownUser();

		user.setId(getUserId());
		user.setFirstname(getFirstname());
		user.setLastname(getLastname());

		return user;
	}

	public boolean isOwner() {
		return owner;
	}

	public void setOwner(boolean owner) {
		this.owner = owner;
	}


}
