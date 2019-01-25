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
 
 
package net.datenwerke.rs.utils.entitydiff.result;

import java.util.ArrayList;
import java.util.List;

public class EntityDiffResult {

	private final Object a;
	private final Object b;
	
	private List<FieldDiffResult> fieldResults = new ArrayList<FieldDiffResult>();
	
	
	public EntityDiffResult(Object a, Object b) {
		super();
		this.a = a;
		this.b = b;
	}


	public Object getA() {
		return a;
	}

	public Object getB() {
		return b;
	}

	public List<FieldDiffResult> getFieldResults(){
		return fieldResults;
	}
	
	public void addFieldResult(FieldDiffResult fieldResult) {
		fieldResults.add(fieldResult);
	}
	
	public boolean isEqual(){
		if(null == a && null == b)
			return true;
		if(null != a && null == b)
			return false;
		if(null == a && null != b)
			return false;
		
		for(FieldDiffResult fieldResult : fieldResults)
			if(! fieldResult.isEqual())
				return false;

		return true;
	}

}
