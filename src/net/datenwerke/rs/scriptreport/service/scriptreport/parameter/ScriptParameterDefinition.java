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

import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import net.datenwerke.dtoservices.dtogenerator.annotations.ExposeToClient;
import net.datenwerke.dtoservices.dtogenerator.annotations.GenerateDto;
import net.datenwerke.rs.core.service.parameters.entities.ParameterDefinition;
import net.datenwerke.rs.fileserver.service.fileserver.entities.FileServerFile;
import net.datenwerke.rs.scriptreport.client.scriptreport.locale.ScriptReportMessages;

import org.hibernate.annotations.Type;
import org.hibernate.envers.Audited;

/**
 * 
 *
 */
@Entity
@Table(name="SCRIPT_PARAM_DEF")
@Audited
@GenerateDto(
	dtoPackage="net.datenwerke.rs.scriptreport.client.scriptreport.parameters.dto",
	displayTitle="ScriptReportMessages.INSTANCE.scriptParameterText()",
	additionalImports=ScriptReportMessages.class
)
public class ScriptParameterDefinition extends ParameterDefinition<ScriptParameterInstance> {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5286168287308916541L;
	
	@ExposeToClient
	@ManyToOne
	private FileServerFile script;
	
	@ExposeToClient
	private String arguments;
	
	@ExposeToClient(allowArbitraryLobSize=true,
			disableHtmlEncode=true)
	@Lob
	@Type(type = "net.datenwerke.rs.utils.hibernate.RsClobType")
	private String defaultValue;
	
	@ExposeToClient
	private Integer width = 640;
	
	@ExposeToClient
	private Integer height = 480;

	@Override
	protected ScriptParameterInstance doCreateParameterInstance() {
		return new ScriptParameterInstance();
	}
	
	public String getDefaultValue() {
		return defaultValue;
	}
	
	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}
	
	public FileServerFile getScript() {
		return script;
	}
	
	public void setScript(FileServerFile script) {
		this.script = script;
	}

	public String getArguments() {
		return arguments;
	}
	
	public void setArguments(String arguments) {
		this.arguments = arguments;
	}
	
	public Integer getWidth() {
		return width;
	}
	
	public void setWidth(Integer width) {
		this.width = width;
	}
	
	public Integer getHeight() {
		return height;
	}
	
	public void setHeight(Integer height) {
		this.height = height;
	}
}
