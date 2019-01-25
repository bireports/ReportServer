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
 
 
package net.datenwerke.rs.dashboard.client.dashboard.locale;

import net.datenwerke.gxtdto.client.forms.simpleform.SimpleFormFieldConfiguration;

import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.Messages;

public interface DashboardMessages extends Messages {

	public static final DashboardMessages INSTANCE = GWT.create(DashboardMessages.class);

	String clientModuleName();
	
	String graphDadgetTitle();
	String graphDadgetDescription();

	String addDadget();

	String tools();

	String createDashboard();
	String removeDashboard();
	String editDashboard();

	String removeDashboardConfirmTitle();
	String removeDashboardConfirmMsg();

	String nameLabel();
	String descriptionLabel();
	String layoutLabel();

	String singleColLayout();
	String twoColLayut();
	String twoColLayout1to2();
	String towColLayout2to1();
	String threeColLayout();

	String singleColLayoutDesc();
	String twoColLayoutDesc();
	String twoColLayout1to2Desc();
	String twoColLayout2to1Desc();
	String threeColLayoutDesc();

	String urlDadgetTitle();
	String urlDadgetDescription();

	String reportDadgetTitle();
	String reportDadgetDescription();

	String urlDadgetHeader(String url);

	String removeDadgetConfirmTitle();
	String removeDadgetConfirmMsg();

	String configDadgetTitle();

	String urlLabel();

	String reportSelectionLocation();

	String reportTeamspaceLocation();
	String reportAdminLocation();

	String selectReportLabel();

	String selectTeamSpace();

	String reportDadgetFormatPreview();
	String reportDadgetFormatHtml();
	String reportDadgetFormatFull();

	String reportDadgetFormat();

	String reportSelection();

	String favoriteListTitle();
	String favoriteListDescription();

	String addToFavorites();

	String staticHtmlDadgetTitle();
	String staticHtmlDadgetDescription();

	String staticHtmlDadgetHtmlLabel();
	String staticHtmlDadgetTitleLabel();

	String securityTargetDescription();

	String mainPanelHeading();

	String dadget();

	String insert();

	String adminLabel();

	String editFolder();

	String dashboardAdminRightsLabel();

	String editDadgetNode();

	String libraryDadgetTitle();
	String libraryDadgetDescription();

	String editDashboardNode();

	String dashboard();

	String createReferenceDashboard();

	String editDashboardOrder();

	String reportDadgetFormatImage();

	String reloadIntervalLabel();

	String singlePageLabel();

	String heightLabel();

	String parameterDescription();

	String parameterTitle();

	String resetReferenceDashboard();

	String editParameterBtnLabel();


}