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
 
 
package net.datenwerke.rs.terminal.service.terminal.obj;

import java.util.ArrayList;
import java.util.List;

import net.datenwerke.dtoservices.dtogenerator.annotations.ExposeToClient;
import net.datenwerke.dtoservices.dtogenerator.annotations.GenerateDto;
import net.datenwerke.rs.utils.entitycloner.annotation.EnclosedEntity;

@GenerateDto(
	dtoPackage="net.datenwerke.rs.terminal.client.terminal.dto",
	createDecorator=true
)
public class AutocompleteResult {

	@ExposeToClient(mergeDtoValueBack=false)
	@EnclosedEntity
	private final CommandResultList cmdEntries = new CommandResultList();
	
	@ExposeToClient(mergeDtoValueBack=false)
	@EnclosedEntity
	private final CommandResultList objectEntries = new CommandResultList();
	
	@ExposeToClient(mergeDtoValueBack=false)
	@EnclosedEntity
	private final CommandResultList entries = new CommandResultList();

	@ExposeToClient
	private String completeStump;
	
	public CommandResultList getEntries() {
		return entries;
	}
	
	public void addEntry(String entry){
		this.entries.addEntry(entry);
	}

	public void addEntries(List<String> list) {
		entries.addEntries(list);
	}
	
	public CommandResultList getCmdEntries() {
		return cmdEntries;
	}
	
	public void addCmdEntry(String entry){
		this.cmdEntries.addEntry(entry);
	}

	public void addCmdEntries(List<String> list) {
		cmdEntries.addEntries(list);
	}
	
	public CommandResultList getObjectEntries() {
		return objectEntries;
	}
	
	public void addObjectEntry(String entry){
		this.objectEntries.addEntry(entry);
	}

	public void addObjectEntries(List<String> list) {
		objectEntries.addEntries(list);
	}
	
	public int size(){
		return entries.size() + objectEntries.size() + cmdEntries.size();
	}

	public void setCompleteStump(String completeStump) {
		this.completeStump = completeStump;
	}

	public String getCompleteStump() {
		return completeStump;
	}
	
	public void processCompleteStump(String cmd){
		if(null == cmd)
			return;
		try{
			if(1 == size()){
				completeStump = getAll().get(0).substring(cmd.length());
			} else if(size() > 1){
				completeStump = findCommonPrefix(cmd);
			}
		}catch(Exception e){}
	}

	public List<String> getAll() {
		List<String> list = new ArrayList<String>(getEntries().getList());
		list.addAll(getCmdEntries().getList());
		list.addAll(getObjectEntries().getList());
		return list;
	}

	private String findCommonPrefix(String cmd) {
		if(null == cmd || "".equals(cmd))
			return null;
		
		boolean completedRound = false;
		String prefix = "";
		
		int prefixLength = 1;
		while(true){
			if(prefixLength  > 100)
				break;
			boolean error = false;

			try{
				String firstEntry = getAll().get(0);
				prefix = firstEntry.substring(0, cmd.length() + prefixLength++);
				
				for(String entry : getAll()){
					if(prefix.length() > entry.length() || ! entry.startsWith(prefix)){
						prefix = prefix.substring(0, prefix.length() - 1);
						error = true;
						break;
					}
				}
			} catch(StringIndexOutOfBoundsException e){
				error = true;
			}
			
			if(error)
				break;

			completedRound = true;
		}
		
		if(completedRound)
			return prefix.substring(cmd.length());
		return null;
	}

	public void sort() {
		getEntries().sort();
		getCmdEntries().sort();
		getObjectEntries().sort();
	}
	
}
