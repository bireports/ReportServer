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
 
 
package net.datenwerke.scheduler.service.scheduler.locale;

import com.google.gwt.i18n.client.Messages;

public interface SchedulerMessages extends Messages {
	String once();
	String scheduleOnce();
	String recurring();
	String scheduleRecurring();
	String scheduleReportOnce(String reportName);
	String scheduleReportMulti(String reportName);
	String exportType();
	String date();
	String time();
	String subject();
	String message();
	String receipient();
	String cancel();
	String scheduleReport();
	String name();

	String monday();
	String tuesday();
	String wednesday();
	String thursday();
	String friday();
	String saturday();
	String sunday();
	String day();
	String workingday();
	String weekendday();
	
	String january();
	String february();
	String march();
	String april();
	String may();
	String june();
	String july();
	String august();
	String september();
	String october();
	String november();
	String december();
	
	String first();
	String second();
	String third();
	String fourth();
	String last();
	
	String hours();
	String minutes();
	String scheduler();
	String schedule();
	String repeatedly();
	String scheduled();
	String reload();
	String remove();
	String report();
	String owner();
	String triggercount();
	String lastexec();
	String nextExec();
	String schema();
	String schedulerAdminHeading();
	String schedulerAdminDescription();
	String and();
	String all();
	String at();
	String between();
	String All();
	String days();
	String every();
	String weeksAt();
	String At();
	String dayEvery();
	String month();
	String everyOf();
	String thMonth();
	String in();
	String daily();
	String weekly();
	String yearly();
	String monthly();
	String duration();
	String begin();
	String noEnd();
	String endsAfter();
	String dates();
	String endsAt();

	String propertiesHeading();
	String propertiesEmailGroup();
	String propertiesEmailSubject();
	String propertiesEmailText();
	String propertiesEmailAttachementName();
	String error();
	String recipients();
	String addressedToMeEntriesHeading();
	String createdByMeEntriesHeading();
	String formatConfig();
	String formatConfigError();
	

	
	String download();
	String downloadButtonTooltip();

}
