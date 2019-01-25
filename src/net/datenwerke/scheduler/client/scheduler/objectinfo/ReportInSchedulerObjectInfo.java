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
 
 
package net.datenwerke.scheduler.client.scheduler.objectinfo;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.inject.Inject;

import net.datenwerke.gxtdto.client.baseex.widget.DwContentPanel;
import net.datenwerke.gxtdto.client.dtomanager.callback.RsAsyncCallback;
import net.datenwerke.gxtdto.client.objectinformation.hooks.ObjectInfoAdditionalInfoProvider;
import net.datenwerke.gxtdto.client.ui.helper.info.InfoWindow;
import net.datenwerke.rs.core.client.i18tools.FormatUiHelper;
import net.datenwerke.rs.core.client.reportmanager.dto.reports.ReportDto;
import net.datenwerke.rs.scheduler.client.scheduler.SchedulerDao;
import net.datenwerke.rs.scheduler.client.scheduler.locale.SchedulerMessages;
import net.datenwerke.rs.scheduler.client.scheduler.schedulereportlist.dto.ReportScheduleJobListInformation;

/**
 * 
 *
 */
public class ReportInSchedulerObjectInfo implements ObjectInfoAdditionalInfoProvider {

	private final SchedulerDao schedulerDao;
	private FormatUiHelper formatUiHelper;

	@Inject
	public ReportInSchedulerObjectInfo(SchedulerDao schedulerDao, FormatUiHelper formatUiHelper) {

		this.schedulerDao = schedulerDao;
		this.formatUiHelper = formatUiHelper;
	}

	@Override
	public boolean consumes(Object object) {
		return object instanceof ReportDto;
	}

	@Override
	public void addInfoFor(Object object, InfoWindow window) {
		final DwContentPanel panel = window.addDelayedSimpelInfoPanel(SchedulerMessages.INSTANCE.scheduler());

		schedulerDao.getReportJobList((ReportDto) object,
				new RsAsyncCallback<List<ReportScheduleJobListInformation>>() {
					@Override
					public void onSuccess(List<ReportScheduleJobListInformation> result) {
						panel.clear();
						panel.enableScrollContainer();

						if (result.isEmpty())
							panel.add(new Label(SchedulerMessages.INSTANCE.reportNotInJobMessages()));
						else {
							SafeHtmlBuilder builder = new SafeHtmlBuilder();
							builder.appendHtmlConstant("<div class=\"rs-infopanel-reportinscheduler\">");

							/* Active jobs */
							builder.appendHtmlConstant("<ul>");
							List<ReportScheduleJobListInformation> activeJobs = filterJobs(result, true);
							for (ReportScheduleJobListInformation info : activeJobs) {
								builder.appendHtmlConstant("<li>")
										.appendEscaped(info.getJobId() + " -> " + info.getReportId());
								String lastExecuted = (null != info.getLastScheduled() ? " - "
										+ SchedulerMessages.INSTANCE.lastexec() + ": "
										+ formatUiHelper.getShortDateTimeFormat().format(info.getLastScheduled()) : "");
								builder.appendHtmlConstant(lastExecuted);
								builder.appendHtmlConstant("</li>");
							}
							builder.appendHtmlConstant("</ul>");

							/* Inactive jobs */
							List<ReportScheduleJobListInformation> inactiveJobs = filterJobs(result, false);
							if (!inactiveJobs.isEmpty()) {

								builder.appendHtmlConstant("<span style=\"font-style:italic;\">")
										.appendEscaped(SchedulerMessages.INSTANCE.archive() + ":")
										.appendHtmlConstant("</span>");
								builder.appendHtmlConstant("<ul>");
								for (ReportScheduleJobListInformation info : inactiveJobs) {
									builder.appendHtmlConstant("<li>")
											.appendEscaped(info.getJobId() + " -> " + info.getReportId());
									String lastExecuted = (null != info.getLastScheduled() ? " - "
											+ SchedulerMessages.INSTANCE.lastexec() + ": "
											+ formatUiHelper.getShortDateTimeFormat().format(info.getLastScheduled())
											: "");
									builder.appendHtmlConstant(lastExecuted);
									builder.appendHtmlConstant("</li>");
								}
								builder.appendHtmlConstant("</ul>");
							}

							builder.appendHtmlConstant("</div>");

							panel.add(new HTML(builder.toSafeHtml()));
						}

						panel.forceLayout();
					}
				});
	}

	private List<ReportScheduleJobListInformation> filterJobs(List<ReportScheduleJobListInformation> jobs,
			boolean active) {
		List<ReportScheduleJobListInformation> filteredJobs = new ArrayList<>();

		for (ReportScheduleJobListInformation job : jobs) {
			if (active && job.isActive())
				filteredJobs.add(job);
			if (!active && !job.isActive())
				filteredJobs.add(job);
		}

		return filteredJobs;
	}

}
