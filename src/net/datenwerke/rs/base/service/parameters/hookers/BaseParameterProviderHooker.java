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
 
 
package net.datenwerke.rs.base.service.parameters.hookers;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import net.datenwerke.rs.base.service.parameters.blatext.BlatextParameterDefinition;
import net.datenwerke.rs.base.service.parameters.datasource.DatasourceParameterDefinition;
import net.datenwerke.rs.base.service.parameters.datetime.DateTimeParameterDefinition;
import net.datenwerke.rs.base.service.parameters.headline.HeadlineParameterDefinition;
import net.datenwerke.rs.base.service.parameters.separator.SeparatorParameterDefinition;
import net.datenwerke.rs.base.service.parameters.string.TextParameterDefinition;
import net.datenwerke.rs.core.service.parameters.entities.ParameterDefinition;
import net.datenwerke.rs.core.service.reportmanager.hooks.ParameterProviderHook;

public class BaseParameterProviderHooker implements ParameterProviderHook {


	@Override
	public Collection<? extends Class<? extends ParameterDefinition>> getParameterDefinitions() {
		Set<Class<? extends ParameterDefinition>> definitions = new HashSet<Class<? extends ParameterDefinition>>();
		
		definitions.add(BlatextParameterDefinition.class);
		definitions.add(TextParameterDefinition.class);
		definitions.add(DateTimeParameterDefinition.class);
		definitions.add(DatasourceParameterDefinition.class);
		definitions.add(HeadlineParameterDefinition.class);
		definitions.add(SeparatorParameterDefinition.class);
		
		return definitions;
	}

}
