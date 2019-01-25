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
 
 
package net.datenwerke.rs.tabletemplate.service.tabletemplate.config;

import net.datenwerke.dtoservices.dtogenerator.annotations.ExposeToClient;
import net.datenwerke.dtoservices.dtogenerator.annotations.GenerateDto;
import net.datenwerke.rs.core.service.reportmanager.engine.config.ReportExecutionConfig;

@GenerateDto(
	dtoPackage="net.datenwerke.rs.tabletemplate.client.tabletemplate.dto"
)
public class RECTableTemplate implements ReportExecutionConfig {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2846255412147622569L;

	@ExposeToClient
	private Long templateId;
	
	@ExposeToClient
	private Long temporaryId;
	
	@ExposeToClient
	private String templateKey;

	public Long getTemplateId() {
		return templateId;
	}

	public void setTemplateId(Long templateId) {
		this.templateId = templateId;
	}

	public Long getTemporaryId() {
		return temporaryId;
	}

	public void setTemporaryId(Long temporaryId) {
		this.temporaryId = temporaryId;
	}

	public void setTemplateKey(String templateKey) {
		this.templateKey = templateKey;
	}

	public String getTemplateKey() {
		return templateKey;
	}


	
	
}
