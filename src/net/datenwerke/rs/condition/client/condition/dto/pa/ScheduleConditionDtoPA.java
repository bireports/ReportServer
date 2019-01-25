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
 
 
package net.datenwerke.rs.condition.client.condition.dto.pa;

import net.datenwerke.rs.condition.client.condition.dto.ConditionDto;
import net.datenwerke.rs.condition.client.condition.dto.ScheduleConditionDto;

import com.google.gwt.editor.client.Editor.Path;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.PropertyAccess;

public interface ScheduleConditionDtoPA extends PropertyAccess<ScheduleConditionDto> {


	/* Properties */
	public ValueProvider<ScheduleConditionDto,String> expression();
	public ValueProvider<ScheduleConditionDto,ConditionDto> condition();
	@Path("condition.name")
	public ValueProvider<ScheduleConditionDto,String> name();

}
