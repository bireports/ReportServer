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
 
 
package net.datenwerke.rs.core.service.i18ntools;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Currency;
import java.util.Locale;

public class FormatHelper {

	private Locale locale;

	private String shortDatePattern;
	private String longDatePattern;
	private String shortTimePattern;
	private String longTimePattern;
	private String shortDateTimePattern;
	private String longDateTimePattern;

	private String numberPattern;
	private String currencyPattern;
	private String integerPattern;
	private String percentPattern;
	

	public FormatHelper(Locale locale) {
		this.locale = locale;
	}


	public Locale getLocale() {
		return locale;
	}

	public DateFormat getShortDateFormat() {
		SimpleDateFormat format = (SimpleDateFormat) SimpleDateFormat.getDateInstance(DateFormat.SHORT, locale);
		if(null != shortDatePattern)
			format.applyPattern(shortDatePattern);
			
		return format;
	}

	public DateFormat getLongDateFormat() {
		SimpleDateFormat format = (SimpleDateFormat) SimpleDateFormat.getDateInstance(DateFormat.LONG, locale);
		if(null != longDatePattern)
			format.applyPattern(longDatePattern);
			
		return format;
	}

	public DateFormat getShortTimeFormat() {
		SimpleDateFormat format = (SimpleDateFormat) SimpleDateFormat.getTimeInstance(DateFormat.SHORT, locale);
		if(null != shortTimePattern)
			format.applyPattern(shortTimePattern);
			
		return format;
	}

	public DateFormat getLongTimeFormat() {
		SimpleDateFormat format = (SimpleDateFormat) SimpleDateFormat.getTimeInstance(DateFormat.LONG, locale);
		if(null != longTimePattern)
			format.applyPattern(longTimePattern);
			
		return format;
	}

	public DateFormat getShortDateTimeFormat() {
		SimpleDateFormat format = (SimpleDateFormat) SimpleDateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT, locale);
		if(null != shortDateTimePattern)
			format.applyPattern(shortDateTimePattern);
			
		return format;
	}

	public DateFormat getLongDateTimeFormat() {
		SimpleDateFormat format = (SimpleDateFormat) SimpleDateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		if(null != longDateTimePattern)
			format.applyPattern(longDateTimePattern);
			
		return format;
	}

	public NumberFormat getNumberFormat() {
		DecimalFormat format = (DecimalFormat) NumberFormat.getNumberInstance(locale);
		if(null != numberPattern)
			format.applyPattern(numberPattern);
		
		return format;
	}

	public NumberFormat getCurrencyFormat(Currency currency) {
		DecimalFormat format = (DecimalFormat) NumberFormat.getCurrencyInstance(locale);
		if(null != currencyPattern)
			format.applyPattern(currencyPattern);
		
		return format;
	}

	public NumberFormat getIntegerFormat() {
		DecimalFormat format = (DecimalFormat) NumberFormat.getIntegerInstance(locale);
		if(null != integerPattern)
			format.applyPattern(integerPattern);
		
		return format;
	}
	
	public NumberFormat getPercentInstance(){
		DecimalFormat format = (DecimalFormat) NumberFormat.getPercentInstance(locale);
		if(null != percentPattern)
			format.applyPattern(percentPattern);
		
		return format;
	}

	
}
