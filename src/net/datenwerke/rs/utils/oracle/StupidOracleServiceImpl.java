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
 
 
package net.datenwerke.rs.utils.oracle;

import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.Date;
import java.sql.Timestamp;

public class StupidOracleServiceImpl implements StupidOracleService {

	@Override
	public boolean isOracleTimestamp(Object obj){
		if(null == obj)
			return false;
		try{
			Class<?> cls = Class.forName("oracle.sql.TIMESTAMP");
			if(null != cls && cls.isAssignableFrom(obj.getClass()))
				return true;
			cls = Class.forName("oracle.sql.TIMESTAMPLTZ");
			if(null != cls && cls.isAssignableFrom(obj.getClass()))
				return true;
			cls = Class.forName("oracle.sql.TIMESTAMPTZ");
			if(null != cls && cls.isAssignableFrom(obj.getClass()))
				return true;
			
		} catch(Exception e){
		}
		return false;
	}
	
	@Override
	public Timestamp getTimeStampFromOracleTimestamp(Object obj, Connection con){
		if(! isOracleTimestamp(obj))
			throw new IllegalArgumentException();
		try {
			Class<?> oraconClass = Class.forName("oracle.jdbc.OracleConnection");
			Class<?> tsClass = Class.forName("oracle.sql.TIMESTAMP");
			Class<?> ltzClass = Class.forName("oracle.sql.TIMESTAMPLTZ");
			Class<?> tzClass = Class.forName("oracle.sql.TIMESTAMPLTZ");
			
			Object tsValue = null;
			if(ltzClass.isAssignableFrom(obj.getClass())){
				Method tsValueMethod = obj.getClass().getMethod("timestampValue", Connection.class);
				tsValue = tsValueMethod.invoke(obj, con.unwrap(oraconClass) );
			}
			
			if(tzClass.isAssignableFrom(obj.getClass())){
				Method tsValueMethod = obj.getClass().getMethod("timestampValue", Connection.class);
				tsValue = tsValueMethod.invoke(obj, con.unwrap(oraconClass) );
			}
			
			if(tsClass.isAssignableFrom(obj.getClass())){
				Method tsValueMethod = obj.getClass().getMethod("timestampValue");
				tsValue = tsValueMethod.invoke(obj);
			}
			
			return (Timestamp) tsValue;
		} catch (Exception e) {
			throw new IllegalArgumentException(e);
		}
	}
	
	@Override
	public Date getDateFromOracleDatum(Object obj){
		if(! isOracleDatum(obj))
			throw new IllegalArgumentException();
		try {
			return (Date) obj.getClass().getMethod("dateValue").invoke(obj);
		} catch (Exception e) {
			throw new IllegalArgumentException(e);
		}
	}
	
	@Override
	public boolean isOracleDatum(Object obj){
		if(null == obj)
			return false;
		try{
			Class<?> cls = Class.forName("oracle.sql.Datum");
			if(null != cls && cls.isAssignableFrom(obj.getClass()))
				return true;
		} catch(Exception e){
		}
		return false;
	}
	
	
}
