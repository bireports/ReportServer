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
 
 
package net.datenwerke.rs.birt.service.utils;

import java.io.Serializable;

import net.datenwerke.dtoservices.dtogenerator.annotations.ExposeToClient;
import net.datenwerke.dtoservices.dtogenerator.annotations.GenerateDto;
import net.datenwerke.rs.core.client.parameters.dto.ParameterProposalDto;
import net.datenwerke.rs.core.service.parameters.entities.ParameterDefinition;
import net.datenwerke.rs.utils.entitycloner.annotation.EnclosedEntity;

@GenerateDto(
		dtoPackage="net.datenwerke.rs.birt.client.utils.dto",
		proxyableDto=false,
		dtoImplementInterfaces=ParameterProposalDto.class
	)
public class BirtParameterProposal implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4302541126311802177L;

	@ExposeToClient(id=true)
	private String key;
	
	@ExposeToClient
	private String name;
	
	@ExposeToClient
	private String type;

	@EnclosedEntity
	@ExposeToClient
	private ParameterDefinition parameterProposal;
	
	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	public ParameterDefinition getParameterProposal() {
		return parameterProposal;
	}
	
	public void setParameterProposal(ParameterDefinition parameterProposal) {
		this.parameterProposal = parameterProposal;
	}
	
}
