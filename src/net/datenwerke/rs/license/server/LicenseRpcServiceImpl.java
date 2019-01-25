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
 
 
package net.datenwerke.rs.license.server;

import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.persist.Transactional;

import net.datenwerke.gxtdto.client.servercommunication.exceptions.ServerCallFailedException;
import net.datenwerke.rs.license.client.dto.LicenseInformationDto;
import net.datenwerke.rs.license.client.rpc.LicenseRpcService;
import net.datenwerke.rs.license.service.LicenseService;
import net.datenwerke.rs.license.service.exceptions.LicenseValidationFailedException;
import net.datenwerke.rs.license.service.genrights.LicenseSecurityTarget;
import net.datenwerke.security.server.SecuredRemoteServiceServlet;
import net.datenwerke.security.service.security.annotation.GenericTargetVerification;
import net.datenwerke.security.service.security.annotation.RightsVerification;
import net.datenwerke.security.service.security.annotation.SecurityChecked;
import net.datenwerke.security.service.security.rights.Execute;

@Singleton
public class LicenseRpcServiceImpl extends SecuredRemoteServiceServlet implements LicenseRpcService {

	private static final long serialVersionUID = 1L;
	private final Logger logger = LoggerFactory.getLogger(getClass().getName());

	private LicenseService licenseService;
	
	@Inject
	public LicenseRpcServiceImpl(
		LicenseService licenseService
		) {
		this.licenseService = licenseService;
	}

	@Override
	public LicenseInformationDto loadLicenseInformation() {
		LicenseInformationDto info = new LicenseInformationDto();
		
		info.setRsVersion(getRsVersion());
		info.setInstallationDate(licenseService.getInstallationDate());
		info.setServerId(licenseService.getServerId());
		info.setLicenseType(licenseService.getLicenseType());
		if(licenseService.hasNonCommunityLicense()){
			info.setLicenseIssueDate(licenseService.getLicenseIssueDate());
			info.setUpgradesUntil(licenseService.getUpgradesUntil());
			info.setExpirationDate(licenseService.getLicenseExpirationDate());
			info.setName(licenseService.getLicenseName());
			info.setAdditionalLicenseProperties(licenseService.getAdditionalLicenseProperties());
		}
		
		return info;
	}

	private String getRsVersion(){
		try {
			InputStream propfile = getClass().getClassLoader().getResourceAsStream("rsversion.properties");
			Properties p = new Properties();
			p.load(propfile);
			String date = p.getProperty("buildDate");
			return p.getProperty("version") + " (" + date.substring(date.indexOf("-") + 1) + ")";
		} catch (Exception e) {
			logger.debug("Could not read rsversion.properties");
		}
		return "unknown";
	}
	
	@Override
	@Transactional(rollbackOn={Exception.class})
	@SecurityChecked(
		genericTargetVerification = @GenericTargetVerification(
			target=LicenseSecurityTarget.class,
			verify=@RightsVerification(rights=Execute.class)
		)
	)
	public void updateLicense(String license) throws ServerCallFailedException {
		try {
			licenseService.updateLicense(license);
		} catch(LicenseValidationFailedException e){
			throw new ServerCallFailedException(e);
		}
	}

}
