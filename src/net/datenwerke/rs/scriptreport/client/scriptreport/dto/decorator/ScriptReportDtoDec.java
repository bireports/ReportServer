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
 
 
package net.datenwerke.rs.scriptreport.client.scriptreport.dto.decorator;

import java.util.ArrayList;
import java.util.List;

import net.datenwerke.gxtdto.client.dtomanager.Dto;
import net.datenwerke.gxtdto.client.dtomanager.PropertyAccessor;
import net.datenwerke.gxtdto.client.dtomanager.fto.FtoSupervisor;
import net.datenwerke.rs.scriptreport.client.scriptreport.dto.ScriptReportDto;

import com.sencha.gxt.core.client.ValueProvider;

/**
 * Dto Decorator for {@link ScriptReportDto}
 *
 */
public class ScriptReportDtoDec extends ScriptReportDto implements FtoSupervisor {


	private static final long serialVersionUID = 1L;

	public ScriptReportDtoDec() {
		super();
	}

	@Override
	public boolean consumes(ValueProvider vp) {
		return "exportFormats".equals(vp.getPath());
	}

	@Override
	public String adaptFtoGeneration(ValueProvider vp, Dto dto) {
		StringBuilder sb = new StringBuilder();
		
		List<String> l = (List<String>) vp.getValue(dto);
		if(null == l)
			return "";
		boolean first = true;
		for(String f : l){
			if(first)
				first=false;
			else
				sb.append(":");
			
			sb.append(f.replace(":", "\\:"));
		}
		return sb.toString();
	}

	@Override
	public void decodeFromFto(String val, PropertyAccessor pa, Dto dto) {
		if(null == val)
			return;
		val = val.replace("\\:", ":");
		
		List<String> values = new ArrayList<String>();
		for(String v : val.split(":"))
			values.add(v);
		pa.setValue(dto, values);
	}

}
