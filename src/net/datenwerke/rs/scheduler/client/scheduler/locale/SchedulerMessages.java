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
 
 
package net.datenwerke.rs.scheduler.client.scheduler.locale;

import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.Messages;

public interface SchedulerMessages extends Messages {
	
	public final static SchedulerMessages INSTANCE = GWT.create(SchedulerMessages.class);
	
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
	String scheduleReport();

	String monday();
	String tuesday();
	String wednesday();
	String thursday();
	String friday();
	String saturday();
	String sunday();
	String day();
	String workingday();
	
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
	String formatConfig();
	String formatConfigError();
	
	String listPanelHeading();
	String gridIdLabel();
	String gridTitleLabel();
	String gridLastScheduled();
	String gridNextSchedule();
	String detailPanelTitle();
	String noTitle();
	String noDescription();
	
	String propertyKeyLabel();
	String propertyValueLabel();
	
	String askSendEmail();
	
	String exportConfigHeadline();
	String exportConfigDescription();
	String showAdvancedOptions();
	String showAdvancedOptionsTooltip();

	String archiveScheduledJobLabel();
	
	String couldNotRemoveJob();
	
	String editScheduledJobLabel();
	
	String toMeSchedulerToolTip();
	
	String byMeSchedulerToolTip();
	
	String gridSchedule();
	
	String displayArchived();
	String jobId();
	
	String propertiesLabel();
	String scheduleListLabel();
	String gridInfoLabel();
	
	String easterEgg();
	
	String loadDetailsLabel();
	
	String detailDialogMainCardHeader();
	
	String nrOfSuccessfulExecutions();
	String nrOfFailedExecutions();
	String nrOfVetoedExecutions();
	
	String scheduleNowLabel();
	
	String outcomeLabel();
	
	String success();
	String failure();
	String veto();
	
	String actionName();
	String gridStarted();
	
	String started();
	String ended();
	
	String executionLogEntryLabel();
	String executionActionLogEntryLabel();
	String errorDescriptionLabel();
	String jobDataLabel();
	
	String allSchedulerToolTip();
	String vetoDescriptionLabel();
	
	String deletedReport();
	
	String schedulerBasicHeading();
	String schedulerBasicDescription();
	String scheduledBy();
	String gridJobIdLabel();
	String gridOwnerLabel();
	
	String fromUser();
	String toUser();
	String jobExecutionStatus();
	
	String firstName();
	String lastName();
	String ou();
	
	String noRecipientSelected();

	String archive();
	String myBtnLabel();
	String toMeBtnLabel();
	String scheduleEntryDetailHeader(String reportName, Long reportId,
			Long jobId);
	
	String reportChangedInfoHeader();
	String reportChangedInfoMessage();

	String executing();
	String filter();
	String status();
	String clearBadFailure();
	String lastOutcome();
	
	String labelReport();
	String labelMessage();
	String labelFilename();
	String labelDescription();
	String labelTeamspace();
	String labelFolder();
	String labelUser();
	String labelErrorMessage();
	String labelRecipient();
	String labelErrorDetails();

	String mailactionMsgText();

	String fileactionMsgSubject();
	String fileactionMsgText();

	String notificationMsgScheduledSubject();
	String notificationMsgScheduledText();
	String notificationMsgScheduledNextDates();

	String notificationMsgUnscheduledSubject();
	String notificationMsgUnscheduledText();

	String notificationMsgFailedSubject();
	String notificationMsgFailedText();

	
}
