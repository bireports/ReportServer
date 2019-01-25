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
 
 
package net.datenwerke.rs.utils.entitydiff.config;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StrictDiffConfig implements EntityDiffConfig {

	private Map<Class<?>, List<String>> fieldsToCompareWhiteList = new HashMap<Class<?>, List<String>>();
	private Map<Class<?>, List<String>> fieldsToCompareBlackList = new HashMap<Class<?>, List<String>>();
	
	@Override
	public boolean ignoreId() {
		return false;
	}

	@Override
	public boolean ignoreVersion() {
		return false;
	}

	@Override
	public Map<Class<?>, List<String>> getFieldsToCompareWhiteList() {
		return fieldsToCompareWhiteList;
	}
	
	public void setFieldsToCompareWhite(Map<Class<?>, List<String>> fieldsToCompare) {
		this.fieldsToCompareWhiteList = fieldsToCompare;
	}

	public void addFieldWhiteList(Class<?> type, List<String> fieldList){
		fieldsToCompareWhiteList.put(type, fieldList);
	}

	public Map<Class<?>, List<String>> getFieldsToCompareBlackList() {
		return fieldsToCompareBlackList;
	}

	public void setFieldsToCompareBlackList(
			Map<Class<?>, List<String>> fieldsToCompareBlackList) {
		this.fieldsToCompareBlackList = fieldsToCompareBlackList;
	}
	
	public void addFieldBlackList(Class<?> type, List<String> fieldList){
		fieldsToCompareBlackList.put(type, fieldList);
	}

	


}
