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
 
 
package net.datenwerke.rs.scheduleasfile.client.scheduleasfile.locale;

import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.Messages;

public interface ScheduleAsFileMessages extends Messages {

	public final static ScheduleAsFileMessages INSTANCE = GWT.create(ScheduleAsFileMessages.class);
	
	String askStoreAsFile();

	String folder();

	String teamspace();

	String executedReportNodeType();

	String exportToTeamSpaceLabel();
	String exportTypeLabel();
	String nameLabel();
	String descriptionLabel();
	
	String exportedIntoTeamSpace();

	String appName();
	String appDescription();

	String teamspaceSchedulerToolTip();

	String noFolderSelected();

	String executedReport();
}
