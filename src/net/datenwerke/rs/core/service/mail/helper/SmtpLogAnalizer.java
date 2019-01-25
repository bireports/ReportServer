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
 
 
package net.datenwerke.rs.core.service.mail.helper;

import java.util.Scanner;

public class SmtpLogAnalizer {

	private final String log;

	public SmtpLogAnalizer(String log){
		this.log = log;
	}
	
	public String getLogWithoutData(){
		StringBuilder builder = new StringBuilder();
		
		if(null == log)
			return "";
		
		Scanner scanner = new Scanner(log);
		boolean inDataSection = false;
		while(scanner.hasNextLine()){
			String line = scanner.nextLine().trim();
			
			if(! inDataSection && "DATA".equals(line)){
				inDataSection = true;
				continue;
			}
			if(inDataSection && ".".equals(line)){
				inDataSection = false;
				continue;
			}
				
			if(! inDataSection && ! line.startsWith("DEBUG"))
				builder.append(line).append("\n");
		}
		
		return builder.toString();
	}
	
	public String getLog(){
		return log;
	}
	
	public String getMessageHeader(){
		StringBuilder builder = new StringBuilder();
		
		if(null == log)
			return "";
		
		Scanner scanner = new Scanner(log);
		boolean inHeaderSection = false;
		while(scanner.hasNextLine()){
			String line = scanner.nextLine().trim();
			
			if(! inHeaderSection && "DATA".equals(line)){
				inHeaderSection = true;
				continue;
			}
			if(inHeaderSection && "".equals(line)){
				inHeaderSection = false;
				continue;
			}
				
			if(inHeaderSection && ! line.startsWith("DEBUG"))
				builder.append(line).append("\n");
		}
		
		return builder.toString();		
	}
}
