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
 
 
package net.datenwerke.rs.birt.client.reportengines.locale;

import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.Messages;

public interface BirtMessages extends Messages{

	public final static BirtMessages INSTANCE = GWT.create(BirtMessages.class);
	
	String reportTypeName();

	String editBirtReport();

	String rptdesign();

	String fileName();

	String birtDownloadToolbarHeading();

	String birtDownloadToolbarButtonText();

	String birtReportDatasourceTypeName();

	String parameterProposalBtn();

	String noProposalsFoundTitle();

	String noProposalsFoundText();

	String datasourceTarget();

	String datasourceReport();

	String datasourceType();

	String queryWrapper();

	String fileMustBeRptDesign();

}
