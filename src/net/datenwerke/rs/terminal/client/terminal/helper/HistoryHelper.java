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
 
 
package net.datenwerke.rs.terminal.client.terminal.helper;

import java.util.ArrayList;
import java.util.List;

public class HistoryHelper {

	private List<String> history = new ArrayList<String>();
	
	private int index = 0;
	
	public void addCommand(String command){
		history.add(command);
		index = history.size() - 1;
	}
	
	public boolean inHistory(){
		return index + 1 != history.size();
	}
	
	public String last(){
		try{
			return history.get(index--);
		} catch(IndexOutOfBoundsException e){
			index = 0;
			if(! history.isEmpty())
				return history.get(0);
			return "";
		}
	}
	
	public String next(){
		try{
			return history.get(index++);
		} catch(IndexOutOfBoundsException e){
			index = history.size() - 1;
			if(! history.isEmpty())
				return history.get(index);
			return "";
		}
	}

	public void addCurrent(String value) {
		if(null == value)
			return;
		if(history.isEmpty() || ! history.get(history.size()-1).equals(value))
			addCommand(value);
	}

	public String getLast() {
		if(! history.isEmpty())
			return history.get(history.size() - 1);
		return "";
	}

}
