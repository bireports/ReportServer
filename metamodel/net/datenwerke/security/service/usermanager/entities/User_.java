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
 
 
package net.datenwerke.security.service.usermanager.entities;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(User.class)
public abstract class User_ extends net.datenwerke.security.service.usermanager.entities.AbstractUserManagerNode_ {

	public static volatile SingularAttribute<User, String> firstname;
	public static volatile SingularAttribute<User, String> password;
	public static volatile SingularAttribute<User, Sex> sex;
	public static volatile SetAttribute<User, Group> groups;
	public static volatile SingularAttribute<User, String> title;
	public static volatile SetAttribute<User, UserProperty> properties;
	public static volatile SingularAttribute<User, String> email;
	public static volatile SingularAttribute<User, Boolean> superUser;
	public static volatile SingularAttribute<User, String> lastname;
	public static volatile SingularAttribute<User, String> username;

}

