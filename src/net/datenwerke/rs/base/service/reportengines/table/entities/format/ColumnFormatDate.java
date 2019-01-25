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
 
 
package net.datenwerke.rs.base.service.reportengines.table.entities.format;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import javax.persistence.Transient;

import net.datenwerke.dtoservices.dtogenerator.annotations.ExposeToClient;
import net.datenwerke.dtoservices.dtogenerator.annotations.GenerateDto;
import net.datenwerke.rs.core.service.i18ntools.I18nToolsService;
import net.datenwerke.rs.utils.juel.SimpleJuel;
import net.datenwerke.rs.utils.localization.LocalizationServiceImpl;

import org.hibernate.envers.Audited;

import com.google.inject.Inject;
import com.google.inject.Provider;

@Entity
@Table(name="COLUMN_FORMAT_DATE")
@Inheritance(strategy=InheritanceType.JOINED)
@Audited
@GenerateDto(
	dtoPackage="net.datenwerke.rs.base.client.reportengines.table.dto",
	createDecorator=true
)
public class ColumnFormatDate extends ColumnFormat {


	/**
	 * 
	 */
	private static final long serialVersionUID = 265407007855094110L;

	@Transient @Inject
	protected static Provider<SimpleJuel> simpleJuelProvider;
	
	@Transient @Inject
	protected static I18nToolsService i18nTools;
	
	@ExposeToClient
	private Boolean rollOver = false;
	
	@ExposeToClient
	private Boolean replaceErrors = false;
	
	@ExposeToClient
	private String errorReplacement;
	
	@ExposeToClient
	private String baseFormat;
	
	@ExposeToClient
	private String targetFormat;
	
	public String getBaseFormat() {
		return null == baseFormat ? "yyyyMMdd" : baseFormat;
	}

	public void setBaseFormat(String baseFormat) {
		this.baseFormat = baseFormat;
	}

	public String getTargetFormat() {
		return null != targetFormat ? targetFormat : i18nTools.getDefaultDateFormat();
	}

	public void setTargetFormat(String targetFormat) {
		this.targetFormat = targetFormat;
	}
	
	public Boolean isRollOver() {
		return Boolean.TRUE.equals(rollOver);
	}

	public void setRollOver(Boolean rollOver) {
		if(null == rollOver)
			rollOver = false;
		this.rollOver = rollOver;
	}
	
	public Boolean isReplaceErrors() {
		return Boolean.TRUE.equals(replaceErrors);
	}

	public void setReplaceErrors(Boolean replaceErrors) {
		if(null == replaceErrors)
			replaceErrors = false;
		this.replaceErrors = replaceErrors;
	}

	public String getErrorReplacement() {
		return errorReplacement;
	}

	public void setErrorReplacement(String errorReplacement) {
		this.errorReplacement = errorReplacement;
	}

	
	public Date parse(String strValue) throws ParseException {
		SimpleDateFormat baseFormatter = new SimpleDateFormat(baseFormat, LocalizationServiceImpl.getLocale());
		Date date = baseFormatter.parse(strValue);
		
		/* if rollover is allowed or no rollover did occur*/
		if(rollOver || strValue.equals(baseFormatter.format(date))){
			return date;
		}else{
			throw new ParseException("Rollover occured while parsing " + strValue + " but was not allowed", 0);
		}
	}
		
	
	@Override
	public String format(Object value) {
		if(null == value)
			return null;
		
		try{
			SimpleDateFormat targetFormatter = new SimpleDateFormat(targetFormat, LocalizationServiceImpl.getLocale());
			
			if(value instanceof Date){
				Date date = (Date) value;
				return targetFormatter.format(date);
			} else {
				String strValue = String.valueOf(value);
				Date date = parse(strValue);
				return targetFormatter.format(date);
			}
		}catch(ParseException e){
			if(replaceErrors)
				value = replaceValue(value);
		}
		return String.valueOf(value);
	}

	protected String replaceValue(Object value) {
		if(null == errorReplacement)
			errorReplacement = "";
		
		SimpleJuel juel = simpleJuelProvider.get();
		if(null == value)
			juel.addReplacement("isNull", true);
		else
			juel.addReplacement("value", value);
		
		return juel.parse(errorReplacement);
	}


}
