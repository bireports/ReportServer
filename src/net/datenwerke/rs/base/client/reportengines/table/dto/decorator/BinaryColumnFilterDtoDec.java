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

import net.datenwerke.rs.base.client.reportengines.table.dto.BinaryColumnFilterDto;
import net.datenwerke.rs.base.client.reportengines.table.dto.BinaryOperatorDto;
import net.datenwerke.rs.base.client.reportengines.table.dto.ColumnDto;
import net.datenwerke.rs.base.client.reportengines.table.dto.ColumnReferenceDto;
import net.datenwerke.rs.base.client.reportengines.table.dto.FilterSpecDto;
import net.datenwerke.rs.base.client.reportengines.table.dto.TableReportDto;

/**
 * Dto Decorator for {@link BinaryColumnFilterDto}
 *
 */
public class BinaryColumnFilterDtoDec extends BinaryColumnFilterDto {


	private static final long serialVersionUID = 1L;

	public BinaryColumnFilterDtoDec() {
		super();
	}

	@Override
	public String toDisplayTitle() {
		return "Filter: " + getColumnA().getName() + " " + BinaryColumnFilterDtoDec.getOperatorSymbol(getOperator()) + " " + getColumnB().getName();
	}

	public static String getOperatorSymbol(BinaryOperatorDto operator) {
		switch(operator){
		case EQUALS:
			return "=";
		case NOT_EQUALS:
			return "!=";
		case LESS:
			return "<";
		case LESS_OR_EQUALS:
			return "<=";
		case GREATER:
			return ">";
		case GREATER_OR_EQUALS:
			return ">=";
		default:
			return "";
		}
	}
	
	public static BinaryOperatorDto getOperatorForSymbol(String operator) {
		if("=".equals(operator))
			return BinaryOperatorDto.EQUALS;
		if("!=".equals(operator))
			return BinaryOperatorDto.NOT_EQUALS;
		if("<".equals(operator))
			return BinaryOperatorDto.LESS;
		if("<=".equals(operator))
			return BinaryOperatorDto.LESS_OR_EQUALS;
		if(">".equals(operator))
			return BinaryOperatorDto.GREATER;
		if(">=".equals(operator))
			return BinaryOperatorDto.GREATER_OR_EQUALS;
		
		return null;
	}

	@Override
	public boolean isStillValid(TableReportDto report) {
		ColumnDto a = getColumnA();
		ColumnDto b = getColumnB();
		
		if(null == a || null == b)
			return false;
		
		boolean foundA = ! (a instanceof ColumnReferenceDto);
		boolean foundB = ! (b instanceof ColumnReferenceDto);
		for(ColumnDto col : report.getAdditionalColumns()){
			if(a.getName().equals(col.getName()))
				foundA = true;
			if(b.getName().equals(col.getName()))
				foundB = true;
		}
		
		return foundA && foundB;
	}
	
	@Override
	public FilterSpecDto cloneFilter() {
		BinaryColumnFilterDtoDec clone = new BinaryColumnFilterDtoDec();
		
		if(null != getColumnA())
			clone.setColumnA(((ColumnDtoDec)getColumnA()).cloneColumnForSelection());
		if(null != getColumnB())
			clone.setColumnB(((ColumnDtoDec)getColumnB()).cloneColumnForSelection());
		if(null != getOperator())
			clone.setOperator(getOperator());
		
		return clone;
	}

}
