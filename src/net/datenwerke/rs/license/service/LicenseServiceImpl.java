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
 
 
package net.datenwerke.rs.license.service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.UUID;

import org.apache.commons.codec.binary.Hex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;

import net.datenwerke.gf.service.properties.PropertiesService;
import net.datenwerke.rs.core.service.reportmanager.ReportService;
import net.datenwerke.rs.license.service.exceptions.LicenseValidationFailedException;
import net.datenwerke.security.service.crypto.pbe.PbeService;
import net.datenwerke.security.service.crypto.pbe.encrypt.EncryptionService;

public class LicenseServiceImpl implements LicenseService {

	protected static final String DATE_FORMAT = "yyyy-MM-dd";
	
	protected static final String INSTALLATION_DATE = "rs:installation:date";
	protected static final String LICENSE_SERVER_ID = "rs:license:server:id";
	protected static final String LICENSE = "rs:license";
	
	protected static final String DATE_PASS="PasswordToProtectTheInstallationDate";
	protected static final String SERVER_ID_PASS="PasswordToProtectTheServerId";
	protected static final String SALT="i solemnly swear that i am up to no good";

	public static final String COMMUNITY_LICENSE = "Community Edition";
	public static final String ENTERPRISE_LICENSE = "Enterprise Edition";
	public static final String EVALUATION_LICENSE = " (Evaluation)";
	
	private final Logger logger = LoggerFactory.getLogger(getClass().getName());
	
	protected final PropertiesService propertiesService;
	protected final PbeService pbeService;
	protected final ReportService reportService;

	private boolean initialized;
	
	@Inject
	public LicenseServiceImpl(
		PropertiesService propertiesService,
		ReportService reportService,
		PbeService pbeService
		) {
		this.propertiesService = propertiesService;
		this.reportService = reportService;
		this.pbeService = pbeService;
	}
	
	@Override
	public void checkInit() {
		setServerParametersIfNecessary();
		initialized = true;
	}
	
	@Override
	public boolean isInitialized() {
		return initialized;
	}

	protected void setServerParametersIfNecessary() {
		/* server id */
		if(! propertiesService.containsKey(LICENSE_SERVER_ID) ){
			/* generate server id */
			String id = UUID.randomUUID().toString();
			
			setEncryptedProperty(LICENSE_SERVER_ID, id, SERVER_ID_PASS, SALT);
		}

		/* installation date */
		if(! propertiesService.containsKey(INSTALLATION_DATE)){
			setEncryptedProperty(INSTALLATION_DATE, getDateFormat().format(new Date()), DATE_PASS, SALT);
		}
	}

	protected void setEncryptedProperty(String property, String value, String pass, String salt) {
		EncryptionService encService = pbeService.getEncryptionService(pass, salt);
		
		byte[] encValue = encService.encrypt(value);
		
		propertiesService.setProperty(property, new String(Hex.encodeHex(encValue)));
	}
	
	protected String getEncryptedProperty(String property, String pass, String salt) {
		if(! propertiesService.containsKey(property))
			return null;
		
		EncryptionService encryptionService = pbeService.getEncryptionService(pass, salt);
		String encValue = propertiesService.get(property);
		byte[] decrypted = encryptionService.decryptFromHex(encValue);
		
		return new String(decrypted);
	}

	protected DateFormat getDateFormat() {
		return new SimpleDateFormat(DATE_FORMAT);
	}
	
	@Override
	public Date getInstallationDate() {
		String dateStr = getEncryptedProperty(INSTALLATION_DATE, DATE_PASS, SALT);
		if(null == dateStr)
			return null;
		try {
			return getDateFormat().parse(dateStr);
		} catch (ParseException e) {
			logger.warn("Could not parse installation date.", e);
			return new Date();
		}
	}

	@Override
	public String getServerId() {
		return getEncryptedProperty(LICENSE_SERVER_ID, SERVER_ID_PASS, SALT);
	}
	
	@Override
	public String getLicenseType() {
		return COMMUNITY_LICENSE;
	}
	
	@Override
	public boolean isCustomLicenseType(){
		return !COMMUNITY_LICENSE.equals(getLicenseType());
	}
	
	@Override
	public String getLicenseName() {
		return null;
	}
	
	@Override
	public HashMap<String, String> getAdditionalLicenseProperties(){
		return null;
	}
	
	@Override
	public Date getLicenseExpirationDate(){
		return null;
	}
	
	@Override
	public Date getUpgradesUntil(){
		return null;
	}
	
	@Override
	public Date getLicenseIssueDate(){
		return null;
	}
	
	@Override
	public void updateLicense(String license) throws LicenseValidationFailedException {
		throw new LicenseValidationFailedException("Community Edition. Enterprise.jar needed.");
	}
	
	@Override
	public boolean isEnterprise() {
		return false;
	}
	
	@Override
	public boolean isEvaluation() {
		return false;
	}

	@Override
	public boolean hasNonCommunityLicense() {
		return false;
	}

}
