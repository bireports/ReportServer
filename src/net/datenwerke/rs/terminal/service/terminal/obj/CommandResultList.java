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
 
 
package net.datenwerke.rs.terminal.service.terminal.obj;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.datenwerke.dtoservices.dtogenerator.annotations.ExposeToClient;
import net.datenwerke.dtoservices.dtogenerator.annotations.GenerateDto;

@GenerateDto(
	dtoPackage="net.datenwerke.rs.terminal.client.terminal.dto",
	createDecorator=true
)
public class CommandResultList extends CommandResultEntry {
	
	@ExposeToClient
	private List<String> list = new ArrayList<String>();
	
	@ExposeToClient
	private boolean denyBreakUp = false;
	
	public CommandResultList() {
	}

	public CommandResultList(List<String> list) {
		this.setList(list);
	}

	public void setList(List<String> list) {
		this.list = list;
	}

	public List<String> getList() {
		return list;
	}
	
	public void addEntry(String entry){
		this.list.add(entry);
	}

	public int size() {
		return list.size();
	}

	public void addEntries(List<String> entries) {
		this.list.addAll(entries);
	}

	public void setDenyBreakUp(boolean denyBreakUp) {
		this.denyBreakUp = denyBreakUp;
	}

	public boolean isDenyBreakUp() {
		return denyBreakUp;
	}

	@Override
	public String toString() {
		if(list.isEmpty())
			return "";
		StringBuffer buf = new StringBuffer();
		
		for(String entry : list)
			buf.append(entry).append("\n");
		
		return buf.toString();
	}

	public void sort() {
		Collections.sort(list);
	}
	
}
