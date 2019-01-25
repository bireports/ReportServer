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
 
 
package net.datenwerke.rs.base.client.reportengines.table.dto.decorator;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.sencha.gxt.core.client.util.Util;

import net.datenwerke.rs.base.client.reportengines.table.dto.ColumnDto;
import net.datenwerke.rs.base.client.reportengines.table.dto.TableReportDto;
import net.datenwerke.rs.core.client.reportmanager.dto.reports.ReportPropertyDto;
import net.datenwerke.rs.core.client.reportmanager.dto.reports.ReportStringPropertyDto;


/**
 * Dto Decorator for {@link TableReportDto}
 *
 */
public class TableReportDtoDec extends TableReportDto {


	private static final String PROPERTY_PREVIEW_COLUMNWIDTH = "preview:columnwidth";
	private static final String PROPERTY_PREVIEW_COLUMNWIDTH_OPTIMAL = "optimal";
	
	
	private static final long serialVersionUID = 1L;

	public TableReportDtoDec() {
		super();
	}

	public ColumnDto getColumnByName(String columnName) {
		if(null == columnName)
			return null;
		for(ColumnDto column : getColumns()){
			if(!Util.isEmptyString(column.getAlias()) && columnName.equals(column.getAlias()))
			   return column;
			if(Util.isEmptyString(column.getAlias()) && ! Util.isEmptyString(column.getDefaultAlias()) && columnName.equals(column.getDefaultAlias()))
				return column;
			if(Util.isEmptyString(column.getAlias()) && Util.isEmptyString(column.getDefaultAlias()) && columnName.equals(column.getName()))
				return column;
		}
		return null;
	}

	public ColumnDto getVisibleColumnByPos(int pos) {
		int vis = 0;
		for(int i = 0; i < getColumns().size(); i++){
			ColumnDto col = getColumns().get(i);
			if(null == col.isHidden() || !col.isHidden()){
				if(vis == pos)
					return col;
				vis++;
			}
		}
		return null;
	}
	
	public int getVisibleColumnCount() {
		int vis = 0;
		for(int i = 0; i < getColumns().size(); i++){
			ColumnDto col = getColumns().get(i);
			if(null == col.isHidden() || !col.isHidden()){
				vis++;
			}
		}
		return vis;
	}
	
	

	public void moveColumnToPos(ColumnDto col, int index) {
		List<ColumnDto> cols = new ArrayList<ColumnDto>(getColumns());
		
		boolean found = false;
		int insertIndex = 0;
		for (Iterator iterator = cols.iterator(); iterator.hasNext();) {
			ColumnDto c = (ColumnDto) iterator.next();
			if(!found && c.equals(col)){
				iterator.remove();
				found = true;
			}
			
			if(index > 0){
				insertIndex++;
				if(null == c.isHidden() || !c.isHidden())
					index--;
			}
		}
		
		if(!found)
			return;
		
		if(insertIndex >= cols.size())
			cols.add(col);
		else
			cols.add(insertIndex, col);
		
		setColumns(cols);
	}


	public boolean hasAggregateColumn() {
		for(ColumnDto col : getColumns())
			if(null != col.getAggregateFunction())
				return true;
		return false;
	}

	public boolean hasSubtotalGroupColumn() {
		for(ColumnDto col : getColumns())
			if(col.isSubtotalGroup())
				return true;
		return false;
	}

	public boolean isBaseReportExecutable() {
		return false;
	}

	
	public Integer getColumnWidth(ColumnDto column){
		ReportPropertyDto property = getReportPropertyByName(PROPERTY_PREVIEW_COLUMNWIDTH);
		if(null == property || ! (property instanceof ReportStringPropertyDto) || isOptimalcolumnWidth())
			return null;
		
		String value = ((ReportStringPropertyDto)property).getStrValue();
		for(String e : value.split(";")){
			if(! e.contains(":"))
				continue;
			String[] split = e.split(":");
			String name = split[0];
			String w = split[1];
			
			if(column.getName().equals(name)){
				try{
					return Integer.parseInt(w);
				}catch(Exception ex){
					return null;
				}
			}
		}
		return null;
	}
	
	public boolean isOptimalcolumnWidth(){
		ReportPropertyDto property = getReportPropertyByName(PROPERTY_PREVIEW_COLUMNWIDTH);
		if(null == property || ! (property instanceof ReportStringPropertyDto))
			return false;
		return PROPERTY_PREVIEW_COLUMNWIDTH_OPTIMAL.equals(((ReportStringPropertyDto)property).getStrValue());
	}
	

	public void setOptimalPreviewColumnWidth() {
		ReportPropertyDto property = getReportPropertyByName(PROPERTY_PREVIEW_COLUMNWIDTH);
		if(null == property){
			property = new ReportStringPropertyDto();
			property.setName(PROPERTY_PREVIEW_COLUMNWIDTH);
		}
		
		((ReportStringPropertyDto)property).setStrValue(PROPERTY_PREVIEW_COLUMNWIDTH_OPTIMAL);
		Set<ReportPropertyDto> properties = getReportProperties();
		properties.add(property);
		setReportProperties(properties);
	}


	

	public void setPreviewColumnWidth(int cwidth) {
		ReportPropertyDto property = getReportPropertyByName(PROPERTY_PREVIEW_COLUMNWIDTH);
		if(null == property){
			property = new ReportStringPropertyDto();
			property.setName(PROPERTY_PREVIEW_COLUMNWIDTH);
		}

		StringBuilder value = new StringBuilder(";");
		for(ColumnDto col : getColumns()){
			value.append(col.getName()).append(":").append(cwidth).append(";");
		}
		
		((ReportStringPropertyDto)property).setStrValue(value.toString());
		Set<ReportPropertyDto> properties = getReportProperties();
		properties.add(property);
		setReportProperties(properties);
	}

	public void setClearColumnWidth() {
		ReportPropertyDto property = getReportPropertyByName(PROPERTY_PREVIEW_COLUMNWIDTH);
		if(null == property){
			property = new ReportStringPropertyDto();
			property.setName(PROPERTY_PREVIEW_COLUMNWIDTH);
		}
		
		property = getReportPropertyByName(PROPERTY_PREVIEW_COLUMNWIDTH);
		if(null == property || ! (property instanceof ReportStringPropertyDto))
			return;
		
		((ReportStringPropertyDto)property).setStrValue("");
		Set<ReportPropertyDto> properties = getReportProperties();
		properties.add(property);
		setReportProperties(properties);
	}
	

	public void setPreviewColumnWidth(ColumnDto col, int cwidth) {
		ReportPropertyDto property = getReportPropertyByName(PROPERTY_PREVIEW_COLUMNWIDTH);
		if(null == property){
			property = new ReportStringPropertyDto();
			property.setName(PROPERTY_PREVIEW_COLUMNWIDTH);
		}
		
		property = getReportPropertyByName(PROPERTY_PREVIEW_COLUMNWIDTH);
		if(null == property || ! (property instanceof ReportStringPropertyDto))
			return;
		
		String value = ((ReportStringPropertyDto)property).getStrValue();
		if(null == value || "".equals(value.trim()))
			value = ";";
		
		/* remove entry for column */
		if (value.contains(";" + col.getName() + ":")) 
			value = value.substring(0,value.indexOf(";" + col.getName() + ":")) + ";" + col.getName() + ":" + cwidth + ";" + value.substring(value.indexOf(";", value.indexOf(";" + col.getName() + ":")+1)+1);
		else
			value +=  col.getName() + ":" + cwidth + ";";
		
		((ReportStringPropertyDto)property).setStrValue(value.toString());
		Set<ReportPropertyDto> properties = getReportProperties();
		properties.add(property);
		setReportProperties(properties);
	}

	
}
