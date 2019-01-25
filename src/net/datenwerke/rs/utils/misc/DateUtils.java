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
 
 
package net.datenwerke.rs.utils.misc;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {
	
	private DateUtils(){
		//no instances
	}
	
	 /**
     * All minutes have this many milliseconds except the last minute of the day on a day defined with
     * a leap second.
     */
    public static final long MILLISECS_PER_MINUTE = 60*1000;
    
    /**
     * Number of milliseconds per hour, except when a leap second is inserted.
     */
    public static final long MILLISECS_PER_HOUR   = 60*MILLISECS_PER_MINUTE;
    
    /**
     * Number of leap seconds per day expect on 
     * <BR/>1. days when a leap second has been inserted, e.g. 1999 JAN  1.
     * <BR/>2. Daylight-savings "spring forward" or "fall back" days.
     */
    protected static final long MILLISECS_PER_DAY = 24*MILLISECS_PER_HOUR;
	
    /****
     * Value to add to the day number returned by this calendar to find the Julian Day number.
     * This is the Julian Day number for 1/1/1970.
     * Note: Since the unix Day number is the same from local midnight to local midnight adding
     * JULIAN_DAY_OFFSET to that value results in the chronologist, historians, or calenderists
     * Julian Day number.
     * @see http://www.hermetic.ch/cal_stud/jdn.htm
     */
    public static final long EPOCH_UNIX_ERA_DAY = 2440588L;
	
	
    /**
     * @return Day number where day 0 is 1/1/1970, as per the Unix/Java date/time epoch.
     */
    public static long getUnixDay(Calendar date) {
        long offset = date.get(Calendar.ZONE_OFFSET) + date.get(Calendar.DST_OFFSET);
        long day = (long)Math.floor( (double)(date.getTime().getTime() + offset ) / ((double)MILLISECS_PER_DAY) );
        return day;
    }
	
	/**
     * @return LOCAL Chronologists Julian day number each day starting from midnight LOCAL TIME.
     * @see http://tycho.usno.navy.mil/mjd.html for more information about local C-JDN
     */
    public static long getJulianDay(Calendar date) {
        return getUnixDay(date) + EPOCH_UNIX_ERA_DAY;
    }

    /**
     * 
     * @param d1
     * @param d2
     * @return d2 - d1
     */
    public static int getDeltaDays(Date d1, Date d2){
    	Calendar cal1 = Calendar.getInstance();
    	cal1.setTime(d1);
    	
    	Calendar cal2 = Calendar.getInstance();
    	cal2.setTime(d2);
    	
    	return (int) (getJulianDay(cal2) - getJulianDay(cal1));
    }
    
    public static String formatDate(Date date, String format){
    	return new SimpleDateFormat(format).format(date);
    }
}
