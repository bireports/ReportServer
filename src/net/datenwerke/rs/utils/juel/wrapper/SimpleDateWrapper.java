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
 
 
package net.datenwerke.rs.utils.juel.wrapper;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 
 *
 */
public class SimpleDateWrapper {

	protected final Date date;
	
	public SimpleDateWrapper(){
		date = Calendar.getInstance().getTime();
	}
	
	public SimpleDateWrapper(Date date){
		this.date = date;
	}
	
	public Date getDate(){
		return date;
	}
	
	public String format(){
		SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy hh:mm"); //$NON-NLS-1$
		return format.format(date);
	}
	
	public String format(String pattern){
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		return format.format(date);
	}
	
	@Override
	public String toString() {
		return format();
	}
}
