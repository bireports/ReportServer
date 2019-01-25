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
 
 
package net.datenwerke.rs.base.service.reportengines.jasper.util;

import java.io.Serializable;

import net.datenwerke.dtoservices.dtogenerator.annotations.ExposeToClient;
import net.datenwerke.dtoservices.dtogenerator.annotations.GenerateDto;
import net.datenwerke.rs.core.client.parameters.dto.ParameterProposalDto;
import net.datenwerke.rs.core.service.parameters.entities.ParameterDefinition;
import net.datenwerke.rs.utils.entitycloner.annotation.EnclosedEntity;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 * Represents a parameter in a jrxml:
 * 
 * <parameter name="P_DATUM_GENEHMIGUNG" isForPrompting="false" class="java.lang.String">
		<defaultValueExpression ><![CDATA["30.06.2008"]]></defaultValueExpression>
	</parameter>
	
 *
 */
@GenerateDto(
	dtoPackage="net.datenwerke.rs.base.client.jasperutils.dto",
	proxyableDto=false,
	dtoImplementInterfaces=ParameterProposalDto.class
)
public class JasperParameterProposal implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3234674530617332714L;
	
	public static final String NAME_ATTRIBUTE = "name";
	public static final String FOR_PROMPTING_ATTRIBUTE = "isForPrompting";
	public static final String CLASS_ATTRIBUTE = "class";
	
	@ExposeToClient(id=true)
	private String key;
	
	@ExposeToClient
	private String name;
	
	@ExposeToClient
	private boolean forPromting;
	
	@ExposeToClient
	private String type;
	
	@ExposeToClient
	private String defaultValueExpression;
	
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

	public boolean isForPromting() {
		return forPromting;
	}

	public void setForPromting(boolean forPromting) {
		this.forPromting = forPromting;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDefaultValueExpression() {
		return defaultValueExpression;
	}

	public void setDefaultValueExpression(String defaultValueExpression) {
		this.defaultValueExpression = defaultValueExpression;
	}
	
	public void setParameterProposal(ParameterDefinition parameterProposal) {
		this.parameterProposal = parameterProposal;
	}

	public ParameterDefinition getParameterProposal() {
		return parameterProposal;
	}

	public static JasperParameterProposal createFrom(Element parameterElement) {
		JasperParameterProposal parameter = new JasperParameterProposal();
		
		if(parameterElement.hasAttribute(NAME_ATTRIBUTE)){
			parameter.setName(parameterElement.getAttribute(NAME_ATTRIBUTE));
			parameter.setKey(parameterElement.getAttribute(NAME_ATTRIBUTE));
		}
		
		if(parameterElement.hasAttribute(FOR_PROMPTING_ATTRIBUTE))
			parameter.setForPromting("true".equals(parameterElement.getAttribute(FOR_PROMPTING_ATTRIBUTE)));
		
		if(parameterElement.hasAttribute(CLASS_ATTRIBUTE))
			parameter.setType(parameterElement.getAttribute(CLASS_ATTRIBUTE));
		
		NodeList defaultValueTag = parameterElement.getElementsByTagName("defaultValueExpression");
		if(null != defaultValueTag && defaultValueTag.getLength() > 0){
			Element defaultValue = (Element) defaultValueTag.item(0);
			parameter.setDefaultValueExpression(defaultValue.getTextContent());
		}
		
		
		return parameter;
	}

}
