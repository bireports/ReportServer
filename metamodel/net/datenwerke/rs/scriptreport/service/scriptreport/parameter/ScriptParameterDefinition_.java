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
 
 
package net.datenwerke.rs.scriptreport.service.scriptreport.parameter;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import net.datenwerke.rs.fileserver.service.fileserver.entities.FileServerFile;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(ScriptParameterDefinition.class)
public abstract class ScriptParameterDefinition_ extends net.datenwerke.rs.core.service.parameters.entities.ParameterDefinition_ {

	public static volatile SingularAttribute<ScriptParameterDefinition, String> defaultValue;
	public static volatile SingularAttribute<ScriptParameterDefinition, Integer> width;
	public static volatile SingularAttribute<ScriptParameterDefinition, String> arguments;
	public static volatile SingularAttribute<ScriptParameterDefinition, FileServerFile> script;
	public static volatile SingularAttribute<ScriptParameterDefinition, Integer> height;

}

