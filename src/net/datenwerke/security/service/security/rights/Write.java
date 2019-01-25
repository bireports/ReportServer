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
 
 
package net.datenwerke.security.service.security.rights;

import net.datenwerke.dtoservices.dtogenerator.annotations.GenerateDto;
import net.datenwerke.rs.utils.localization.LocalizationServiceImpl;
import net.datenwerke.security.service.security.locale.SecurityMessages;

/**
 * 
 *
 */
@GenerateDto(
	dtoPackage="net.datenwerke.security.client.security.dto"
)
public class Write implements Right {

	private final SecurityMessages messages = LocalizationServiceImpl.getMessages(SecurityMessages.class);
	
	@Override
	public String getDescription() {
		return messages.writeRightName();
	}

	@Override
	public long getBitField() {
		return SecurityServiceRightsDefinition.RIGHT_WRITE;
	}

	@Override
	public String getAbbreviation() {
		return messages.writeRightAbbreviation();
	}
}
