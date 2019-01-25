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
 
 
package net.datenwerke.security.service.security;

import java.util.ArrayList;
import java.util.List;

import net.datenwerke.dtoservices.dtogenerator.annotations.GenerateDto;
import net.datenwerke.rs.utils.localization.LocalizationServiceImpl;
import net.datenwerke.security.service.security.locale.SecurityMessages;
import net.datenwerke.security.service.security.rights.Delete;
import net.datenwerke.security.service.security.rights.Execute;
import net.datenwerke.security.service.security.rights.GrantAccess;
import net.datenwerke.security.service.security.rights.Read;
import net.datenwerke.security.service.security.rights.Right;
import net.datenwerke.security.service.security.rights.Write;

/**
 * 
 *
 */
@GenerateDto(
	dtoPackage="net.datenwerke.security.client.security.dto"
)
public class SecurityServiceSecuree implements Securee{

	private final SecurityMessages messages = LocalizationServiceImpl.getMessages(SecurityMessages.class);
	
	public static final String SECUREE_ID = "SecurityService_DefaultSecuree"; //$NON-NLS-1$

	@Override
	public List<Right> getRights() {	
		List<Right> rights = new ArrayList<Right>();
		
		rights.add(new Read());
		rights.add(new Write());
		rights.add(new Execute());
		rights.add(new Delete());
		rights.add(new GrantAccess());
		
		return rights;
	}

	@Override
	public String getSecureeId() {
		return SECUREE_ID;
	}
	
	@Override
	public String getName(){
		return messages.securitySecureeName();
	}
	

}
