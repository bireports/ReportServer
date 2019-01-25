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
 
 
package net.datenwerke.rs.dashboard.client.dashboard.dto.decorator;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import net.datenwerke.gxtdto.client.dtomanager.IdedDto;
import net.datenwerke.rs.core.client.parameters.dto.ParameterInstanceDto;
import net.datenwerke.rs.dashboard.client.dashboard.dto.DadgetDto;
import net.datenwerke.rs.dashboard.client.dashboard.dto.DashboardDto;
import net.datenwerke.rs.dashboard.client.dashboard.dto.LayoutTypeDto;

/**
 * Dto Decorator for {@link DashboardDto}
 *
 */
public class DashboardDtoDec extends DashboardDto implements IdedDto {


	private static final long serialVersionUID = 1L;
	
	private Map<String, ParameterInstanceDto> temporaryInstanceMap;

	public DashboardDtoDec() {
		super();
		setLayout(LayoutTypeDto.TWO_COLUMN_EQUI);
	}

	public void updateDadget(DadgetDto old, DadgetDto newDadget) {
		List<DadgetDto> dadgets = getDadgets();
		Collections.replaceAll(dadgets, old, newDadget);
		setDadgets(dadgets);
	}

	public Map<String, ParameterInstanceDto> getTemporaryInstanceMap() {
		return temporaryInstanceMap;
	}

	public void setTemporaryInstanceMap(Map<String, ParameterInstanceDto> temporaryInstanceMap) {
		this.temporaryInstanceMap = temporaryInstanceMap;
	}


}
