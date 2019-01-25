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
 
 
package net.datenwerke.scheduler.client.scheduler.dto.config.complex.decorator;

import java.util.Date;

import net.datenwerke.scheduler.client.scheduler.dto.config.complex.TimeDto;

import com.google.gwt.i18n.shared.DateTimeFormat;
import com.sencha.gxt.core.client.util.DateWrapper;

/**
 * Dto Decorator for {@link TimeDto}
 *
 */
public class TimeDtoDec extends TimeDto {


	private static final long serialVersionUID = 1L;

	public TimeDtoDec() {
		super();
	}
	
	public TimeDtoDec(Date date){
		setHour(new DateWrapper(date).getHours());
		setMinutes(new DateWrapper(date).getMinutes());
	}

	public Date toTime(){
		return new DateWrapper(1970, 0, 1).clearTime().addHours(getHour()).addMinutes(getMinutes()).asDate();
	}

	@Override
	public String toDisplayTitle() {
		return DateTimeFormat.getFormat(DateTimeFormat.PredefinedFormat.HOUR_MINUTE).format(toTime());
	}
}
