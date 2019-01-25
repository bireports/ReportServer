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
 
 
package net.datenwerke.scheduler.client.scheduler.dto.config.complex.pa;

import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.Editor.Path;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;
import java.lang.Integer;
import java.lang.Long;
import java.util.Date;
import net.datenwerke.dtoservices.dtogenerator.annotations.CorrespondingPoso;
import net.datenwerke.dtoservices.dtogenerator.annotations.GeneratedType;
import net.datenwerke.scheduler.client.scheduler.dto.config.complex.DailyRepeatTypeDto;
import net.datenwerke.scheduler.client.scheduler.dto.config.complex.DateTriggerConfigDto;
import net.datenwerke.scheduler.client.scheduler.dto.config.complex.EndTypesDto;
import net.datenwerke.scheduler.client.scheduler.dto.config.complex.TimeDto;
import net.datenwerke.scheduler.client.scheduler.dto.config.complex.TimeUnitsDto;

/**
 * This file was automatically created by DtoAnnotationProcessor, version 0.1
 */
@GeneratedType("net.datenwerke.dtoservices.dtogenerator.DtoAnnotationProcessor")
@CorrespondingPoso(net.datenwerke.scheduler.service.scheduler.triggers.complex.config.DateTriggerConfig.class)
public interface DateTriggerConfigDtoPA extends PropertyAccess<DateTriggerConfigDto> {


	public static final DateTriggerConfigDtoPA INSTANCE = GWT.create(DateTriggerConfigDtoPA.class);

	@Path("dtoId")
	public ModelKeyProvider<DateTriggerConfigDto> dtoId();

	/* Properties */
	public ValueProvider<DateTriggerConfigDto,TimeDto> atTime();
	public ValueProvider<DateTriggerConfigDto,DailyRepeatTypeDto> dailyRepeatType();
	public ValueProvider<DateTriggerConfigDto,EndTypesDto> endType();
	public ValueProvider<DateTriggerConfigDto,Date> firstExecution();
	public ValueProvider<DateTriggerConfigDto,Long> id();
	public ValueProvider<DateTriggerConfigDto,Date> lastExecution();
	public ValueProvider<DateTriggerConfigDto,Integer> numberOfExecutions();
	public ValueProvider<DateTriggerConfigDto,TimeDto> timeRangeEnd();
	public ValueProvider<DateTriggerConfigDto,Integer> timeRangeInterval();
	public ValueProvider<DateTriggerConfigDto,TimeDto> timeRangeStart();
	public ValueProvider<DateTriggerConfigDto,TimeUnitsDto> timeRangeUnit();


}
