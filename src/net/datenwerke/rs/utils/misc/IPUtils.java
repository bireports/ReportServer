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

public class IPUtils {

	/**
	 * 
	 * @param ip an IP in format 1.2.3.4/32
	 * @param contained an IP in format 1.2.3.4.
	 * @return
	 */
	public boolean contains(String ip, String contained){
		validate(ip, true);
		validate(contained, false);
		
		if(! ip.contains("/"))
			return ip.equals(contained);
		else {
			/* find relevant bits */
			int bits = Integer.parseInt(ip.substring(ip.indexOf("/")+1));
			
			String[] ipGroups = ip.substring(0,ip.indexOf("/")).split("\\.");
			String[] containedGroups = contained.split("\\.");
			
			long ipValue = 0;
			long containedValue = 0;
			for(int i = 0 ; i <= 3; i++){
				ipValue += (long) (Integer.parseInt(ipGroups[i]) * Math.pow(255, 3-i));
				containedValue += (long) (Integer.parseInt(containedGroups[i]) * Math.pow(255, 3-i));
			}
			
			if( bits > 32 || bits < 0)
				throw new IllegalArgumentException("no valid bitmask");
			
			long bitmask = (long)(((long)Math.pow(2, bits)-1) << (32 - bits));
			
			ipValue &= bitmask;
			containedValue &= bitmask;
			
			return ipValue == containedValue;
		}
		
	}

	private void validate(String ip, boolean bitmask) {
		boolean valid = ip.matches("\\b\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\b");
		if(! valid &&  bitmask)
			valid = ip.matches("\\b\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\/\\d{1,2}\\b");
		
		if(! valid){
			throw new IllegalArgumentException("no valid ip address");
		}
	}
}
