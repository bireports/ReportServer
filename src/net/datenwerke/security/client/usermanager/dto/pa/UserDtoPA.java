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
 
 
package net.datenwerke.security.client.usermanager.dto.pa;

import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.Editor.Path;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;
import java.lang.Boolean;
import java.lang.String;
import java.util.Set;
import net.datenwerke.dtoservices.dtogenerator.annotations.CorrespondingPoso;
import net.datenwerke.dtoservices.dtogenerator.annotations.GeneratedType;
import net.datenwerke.security.client.usermanager.dto.GroupDto;
import net.datenwerke.security.client.usermanager.dto.SexDto;
import net.datenwerke.security.client.usermanager.dto.UserDto;
import net.datenwerke.security.client.usermanager.dto.UserPropertyDto;
import net.datenwerke.security.client.usermanager.dto.decorator.UserDtoDec;
import net.datenwerke.security.client.usermanager.dto.pa.AbstractUserManagerNodeDtoPA;

/**
 * This file was automatically created by DtoAnnotationProcessor, version 0.1
 */
@GeneratedType("net.datenwerke.dtoservices.dtogenerator.DtoAnnotationProcessor")
@CorrespondingPoso(net.datenwerke.security.service.usermanager.entities.User.class)
public interface UserDtoPA extends AbstractUserManagerNodeDtoPA {


	public static final UserDtoPA INSTANCE = GWT.create(UserDtoPA.class);


	/* Properties */
	public ValueProvider<UserDto,Boolean> active();
	public ValueProvider<UserDto,String> email();
	public ValueProvider<UserDto,String> firstname();
	public ValueProvider<UserDto,Set<GroupDto>> groups();
	public ValueProvider<UserDto,String> lastname();
	public ValueProvider<UserDto,String> password();
	public ValueProvider<UserDto,Set<UserPropertyDto>> properties();
	public ValueProvider<UserDto,SexDto> sex();
	public ValueProvider<UserDto,Boolean> superUser();
	public ValueProvider<UserDto,String> title();
	public ValueProvider<UserDto,String> username();
	public ValueProvider<UserDto,Boolean> hasPassword();


}
