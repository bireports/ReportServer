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
 
 
package net.datenwerke.rs.scheduler.client.scheduler;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import net.datenwerke.rs.core.client.i18tools.FormatUiHelper;
import net.datenwerke.rs.scheduler.client.scheduler.locale.SchedulerMessages;
import net.datenwerke.rs.scheduler.client.scheduler.schedulereportlist.dto.ReportScheduleJobListInformation;
import net.datenwerke.rs.scheduler.client.scheduler.schedulereportlist.dto.ReportScheduleJobListInformationPA;
import net.datenwerke.scheduler.client.scheduler.dto.filter.JobFilterConfigurationDto;
import net.datenwerke.scheduler.client.scheduler.dto.filter.OrderDto;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.cell.client.DateCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.sencha.gxt.core.client.IdentityValueProvider;
import com.sencha.gxt.core.client.util.Format;
import com.sencha.gxt.data.shared.SortDir;
import com.sencha.gxt.data.shared.SortInfo;
import com.sencha.gxt.data.shared.loader.PagingLoadConfig;
import com.sencha.gxt.widget.core.client.grid.ColumnConfig;
import com.sencha.gxt.widget.core.client.grid.ColumnModel;

public class SchedulerUiServiceImpl implements SchedulerUiService {

	private static ReportScheduleJobListInformationPA scheduleInfoPa = GWT.create(ReportScheduleJobListInformationPA.class);
	private FormatUiHelper formatUiHelper;
	
	@Inject
	public SchedulerUiServiceImpl(FormatUiHelper formatUiHelper) {
		this.formatUiHelper = formatUiHelper;
	}
	
	@Override
	public ColumnModel<ReportScheduleJobListInformation> createReportScheduleListColumnModel() {
		return createReportScheduleListColumnModel(false, false);
	}
	
	@Override
	public ColumnModel<ReportScheduleJobListInformation> createReportScheduleListColumnModel(boolean displayJobId, boolean displayOwnerColumn) {
		List<ColumnConfig<ReportScheduleJobListInformation,?>> colConfig = new ArrayList<ColumnConfig<ReportScheduleJobListInformation,?>>();
		
		if(displayJobId){
			ColumnConfig<ReportScheduleJobListInformation,Long> idColumn = new ColumnConfig<ReportScheduleJobListInformation,Long>(scheduleInfoPa.jobId(), 80, SchedulerMessages.INSTANCE.gridJobIdLabel());
			idColumn.setMenuDisabled(true);
			colConfig.add(idColumn);
		}
		
		ColumnConfig<ReportScheduleJobListInformation,Long> idColumn = new ColumnConfig<ReportScheduleJobListInformation,Long>(scheduleInfoPa.reportId(), 80, SchedulerMessages.INSTANCE.gridIdLabel());
		idColumn.setMenuDisabled(true);
		colConfig.add(idColumn);
		
		ColumnConfig<ReportScheduleJobListInformation,ReportScheduleJobListInformation> descriptionConfig = new ColumnConfig<ReportScheduleJobListInformation,ReportScheduleJobListInformation>(new IdentityValueProvider<ReportScheduleJobListInformation>(scheduleInfoPa.reportName().getPath()), 150, SchedulerMessages.INSTANCE.gridTitleLabel());
		descriptionConfig.setCell(new AbstractCell<ReportScheduleJobListInformation>() {
			@Override
			public void render(com.google.gwt.cell.client.Cell.Context context,
					ReportScheduleJobListInformation value, SafeHtmlBuilder sb) {
				String header = value.isDeleted() ? SchedulerMessages.INSTANCE.deletedReport() : value.getReportName() == null ? SchedulerMessages.INSTANCE.noTitle() : value.getReportName();
				String description = null == value.getReportDescription() ? SchedulerMessages.INSTANCE.noDescription() : Format.ellipse(value.getReportDescription(), 150);
				sb.appendHtmlConstant("<div class=\"rs-list-name-desc\"><div class=\"title\">" + header + "</div><div class=\"description\">" + description + "</div></div>");
			}
		});
		descriptionConfig.setMenuDisabled(true);
		colConfig.add(descriptionConfig);
		
		if(displayOwnerColumn){
			ColumnConfig<ReportScheduleJobListInformation,ReportScheduleJobListInformation> ownerConfig = new ColumnConfig<ReportScheduleJobListInformation,ReportScheduleJobListInformation>(new IdentityValueProvider<ReportScheduleJobListInformation>(), 150, SchedulerMessages.INSTANCE.gridOwnerLabel());
			ownerConfig.setCell(new AbstractCell<ReportScheduleJobListInformation>() {
				@Override
				public void render(com.google.gwt.cell.client.Cell.Context context,
						ReportScheduleJobListInformation value, SafeHtmlBuilder sb) {
					String name = value.getOwnerName();
					if(null != name)
						sb.appendEscaped(name);
				}
			});
			ownerConfig.setMenuDisabled(true);
			colConfig.add(ownerConfig);
		}
		
		ColumnConfig<ReportScheduleJobListInformation,Date> lastColumn = new ColumnConfig<ReportScheduleJobListInformation,Date>(scheduleInfoPa.lastScheduled(), 128, SchedulerMessages.INSTANCE.gridLastScheduled());
		lastColumn.setCell(new DateCell(formatUiHelper.getShortDateTimeFormat()));
		lastColumn.setMenuDisabled(true);
		colConfig.add(lastColumn);
		
		ColumnConfig<ReportScheduleJobListInformation,Date> nextColumn = new ColumnConfig<ReportScheduleJobListInformation,Date>(scheduleInfoPa.nextScheduled(), 128, SchedulerMessages.INSTANCE.gridNextSchedule());
		nextColumn.setCell(new DateCell(formatUiHelper.getShortDateTimeFormat()));
		nextColumn.setMenuDisabled(true);
		colConfig.add(nextColumn);
		
		return new ColumnModel<ReportScheduleJobListInformation>(colConfig);
	}
	

	@Override
	public void transformLoadConfig(PagingLoadConfig plc, JobFilterConfigurationDto jobFilterConfig) {
		jobFilterConfig.setOffset(plc.getOffset());
		jobFilterConfig.setLimit(plc.getLimit());
		
		if(! plc.getSortInfo().isEmpty()){
			SortInfo info = plc.getSortInfo().get(0);
			
			if(SortDir.ASC == info.getSortDir())
				jobFilterConfig.setOrder(OrderDto.ASC);
			else if(SortDir.DESC == info.getSortDir())
				jobFilterConfig.setOrder(OrderDto.DESC);
			
			if(null != info.getSortField())
				jobFilterConfig.setSortField(info.getSortField());
		}
	}

}
