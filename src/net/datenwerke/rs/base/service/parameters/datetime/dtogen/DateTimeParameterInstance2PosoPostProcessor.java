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
 
 
package net.datenwerke.rs.base.service.parameters.datetime.dtogen;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.SimpleTimeZone;
import java.util.TimeZone;

import javax.inject.Inject;

import com.google.gwt.i18n.shared.DateTimeFormat;
import com.google.inject.Provider;

import net.datenwerke.dtoservices.dtogenerator.dto2posogenerator.interfaces.Dto2PosoPostProcessor;
import net.datenwerke.rs.base.client.parameters.datetime.dto.DateTimeParameterInstanceDto;
import net.datenwerke.rs.base.client.parameters.datetime.dto.decorator.DateTimeParameterInstanceDtoDec;
import net.datenwerke.rs.base.service.parameters.datetime.DateTimeParameterInstance;
import net.datenwerke.rs.utils.localization.LocalizationServiceImpl;
import net.datenwerke.rs.utils.localization.UserLocale;

public class DateTimeParameterInstance2PosoPostProcessor implements Dto2PosoPostProcessor<DateTimeParameterInstanceDto, DateTimeParameterInstance> {

	@Inject
	private static Provider<LocalizationServiceImpl> localizationService;
	
	@Override
	public void posoCreated(DateTimeParameterInstanceDto dto, DateTimeParameterInstance poso) {

		if(dto instanceof DateTimeParameterInstanceDtoDec) {
			DateTimeParameterInstanceDtoDec dec = (DateTimeParameterInstanceDtoDec) dto;
			String strval = dec.getStrValue();
			if(null != strval) {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
				try {
					Date date = sdf.parse(strval);
					poso.setValue(date);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
//		String timezone = localizationService.get().getUserTimezone();
//		TimeZone clientTZ = TimeZone.getTimeZone(timezone);
//		TimeZone serverTZ = TimeZone.getDefault();
//		
//		Date clientdate = poso.getValue();
//		
//		SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss Z");
//		
//		sdf.setTimeZone(clientTZ);
//		System.out.println(sdf.format(clientdate));
//		
//		int clientoffset = clientTZ.getRawOffset(); //clientTZ.getOffset(clientdate.getTime())/60000;
//		System.out.println("+ "  + clientoffset / 60000 / 60);
//		Date utc = new Date(clientdate.getTime() + clientoffset);
//		
//		sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
//		System.out.println(sdf.format(utc));
//		
//		int serveroffset = serverTZ.getOffset(utc.getTime());
//		System.out.println("- " + serveroffset / 60000 / 60);
//		Date serverdate = new Date(utc.getTime() - serveroffset);
//		
//		
//		sdf.setTimeZone(serverTZ);
//		System.out.println(sdf.format(serverdate));
//
//		poso.setValue(serverdate);
	}

	@Override
	public void posoCreatedUnmanaged(DateTimeParameterInstanceDto dto, DateTimeParameterInstance poso) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void posoInstantiated(DateTimeParameterInstance poso) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void posoMerged(DateTimeParameterInstanceDto dto, DateTimeParameterInstance poso) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void posoLoaded(DateTimeParameterInstanceDto dto, DateTimeParameterInstance poso) {
		// TODO Auto-generated method stub
		
	}




}
