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
 
 
package net.datenwerke.rs.tsreportarea.service.tsreportarea.entities.post;

import net.datenwerke.dtoservices.dtogenerator.poso2dtogenerator.interfaces.Poso2DtoPostProcessor;
import net.datenwerke.rs.teamspace.service.teamspace.entities.TeamSpace;
import net.datenwerke.rs.tsreportarea.client.tsreportarea.dto.AbstractTsDiskNodeDto;
import net.datenwerke.rs.tsreportarea.client.tsreportarea.dto.decorator.AbstractTsDiskNodeDtoDec;
import net.datenwerke.rs.tsreportarea.service.tsreportarea.TsDiskService;
import net.datenwerke.rs.tsreportarea.service.tsreportarea.entities.AbstractTsDiskNode;

import com.google.inject.Inject;

public class AbstractTsDiskNode2DtoPost implements Poso2DtoPostProcessor<AbstractTsDiskNode, AbstractTsDiskNodeDto> {

	private final TsDiskService diskService;
	
	@Inject
	public AbstractTsDiskNode2DtoPost(TsDiskService diskService) {
		this.diskService = diskService;
	}

	@Override
	public void dtoCreated(AbstractTsDiskNode poso, AbstractTsDiskNodeDto dto) {
		TeamSpace ts = diskService.getTeamSpaceFor(poso);
		if(null != ts)
			((AbstractTsDiskNodeDtoDec)dto).setTeamSpaceId(ts.getId());
	}

	@Override
	public void dtoInstantiated(AbstractTsDiskNode arg0,
			AbstractTsDiskNodeDto arg1) {
		
	}

	


}
