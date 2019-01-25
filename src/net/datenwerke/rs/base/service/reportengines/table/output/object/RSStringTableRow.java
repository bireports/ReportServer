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
 
 
package net.datenwerke.rs.base.service.reportengines.table.output.object;

import java.util.ArrayList;
import java.util.List;

import net.datenwerke.dtoservices.dtogenerator.annotations.ExposeToClient;
import net.datenwerke.dtoservices.dtogenerator.annotations.GenerateDto;

@GenerateDto(
	dtoPackage="net.datenwerke.rs.base.client.reportengines.table.dto",
	generateDto2Poso=false
)
public class RSStringTableRow extends RSTableRow {

	/**
	 * 
	 */
	private static final long serialVersionUID = 121693743242964517L;
	
	@ExposeToClient(mergeDtoValueBack=false)
	protected List<String> stringRow = new ArrayList<String>();

	public RSStringTableRow(){
		super();
	}
	
	public RSStringTableRow(String... row){
		super();
		
		setRow(row);
	}
	
	public RSStringTableRow(List<String> row){
		super();
		
		setRow(row.toArray());
	}
	
	public RSStringTableRow(RSTableRow row) {
		for(Object cell : row.getRow())
			stringRow.add(null == cell ? null : cell.toString());
	}

	@Override
	public void setRow(Object[] row) {
		this.row = row;
		
		for(Object o : row){
			if(null != o)
				stringRow.add(o.toString());
		}
	}

	public List<String> getStringRow(){
		return stringRow;
	}
		
}
