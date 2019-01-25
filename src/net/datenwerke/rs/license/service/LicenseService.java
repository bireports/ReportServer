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
 
 
package net.datenwerke.rs.license.service;

import java.util.Date;
import java.util.HashMap;

import net.datenwerke.rs.license.service.exceptions.LicenseValidationFailedException;

public interface LicenseService {

	void checkInit();
	
	Date getInstallationDate();

	String getServerId();
	
	void updateLicense(String license) throws LicenseValidationFailedException;
	
	String getLicenseType();

	String getLicenseName();
	
	Date getUpgradesUntil();

	Date getLicenseIssueDate();
	
	Date getLicenseExpirationDate();

	HashMap<String, String> getAdditionalLicenseProperties();

	boolean isEnterprise();
	
	boolean isEvaluation();

	boolean hasNonCommunityLicense();

	boolean isInitialized();

	boolean isCustomLicenseType();
}
