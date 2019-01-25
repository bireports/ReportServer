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
 
 
package net.datenwerke.rs.license.client.locale;

import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.Messages;

import net.datenwerke.gxtdto.client.forms.simpleform.SimpleFormFieldConfiguration;

public interface LicenseMessages extends Messages{

	public final LicenseMessages INSTANCE = GWT.create(LicenseMessages.class);
	
	public String viewNavigationTitle();
	
	public String dialogTitle();

	public String installationDateLabel();
	public String serverIdLabel();

	public String informationPanelHeader();

	public String currentLicenseLabel();

	public String licenseExpirationDate();

	public String noExpirationDateMsg();

	public String purchaseEnterpriseLabel();
	public String purchaseEnterpriseText();

	public String updateLicenseInfoBtnLabel();
	public String updateLicenseInfoFieldLabel();

	public String licenseeLabel();

	public String licenseExpiredLabel();

	public String upgradesAvailableUntilLabel();

	public String versionLabel();

	public String permissionModuleDescription();
	
}
