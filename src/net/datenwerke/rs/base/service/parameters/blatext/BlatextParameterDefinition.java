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
 
 
package net.datenwerke.rs.base.service.parameters.blatext;

import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;

import net.datenwerke.dtoservices.dtogenerator.annotations.ExposeToClient;
import net.datenwerke.dtoservices.dtogenerator.annotations.GenerateDto;
import net.datenwerke.rs.base.client.parameters.locale.RsMessages;
import net.datenwerke.rs.core.service.parameters.entities.ParameterDefinition;

import org.hibernate.annotations.Type;
import org.hibernate.envers.Audited;

/**
 * 
 *
 */
@Entity
@Table(name="BLATEXT_PARAM_DEF")
@Audited
@GenerateDto(
	dtoPackage="net.datenwerke.rs.base.client.parameters.blatext.dto",
	displayTitle="RsMessages.INSTANCE.blaTextParameterText()",
	additionalImports=RsMessages.class
)
public class BlatextParameterDefinition extends ParameterDefinition<BlatextParameterInstance> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -458837856638298648L;
	
	@ExposeToClient(allowArbitraryLobSize=true,
			disableHtmlEncode=true,
			enableSimpleHtmlPolicy=true)
	@Lob
	@Type(type = "net.datenwerke.rs.utils.hibernate.RsClobType")
	private String value = ""; //$NON-NLS-1$
	
 	public void setValue(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
	
	@Override
	protected BlatextParameterInstance doCreateParameterInstance() {
		return new BlatextParameterInstance();
	}

}
