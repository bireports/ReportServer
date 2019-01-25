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
 
 
package net.datenwerke.treedb.ext.service.eximport;

import java.util.HashSet;
import java.util.Set;

import net.datenwerke.eximport.im.ImportItemConfig;
import net.datenwerke.eximport.im.ImportMode;
import net.datenwerke.treedb.service.treedb.AbstractNode;

/**
 * 
 *
 */
public class TreeNodeImportItemConfig extends ImportItemConfig {

	private AbstractNode<?> parent;
	
	private Set<String> ignoredFields = new HashSet<String>();
	
	public TreeNodeImportItemConfig(String id) {
		super(id);
	}
	
	public TreeNodeImportItemConfig(String id, ImportMode importMode){
		super(id, importMode);
	}
	
	public TreeNodeImportItemConfig(String id, ImportMode importMode, Object referenceObject) {
		super(id, importMode, referenceObject);
	}

	public void setParent(AbstractNode<?> parent){
		this.parent = parent;
	}

	public AbstractNode<?> getParent(){
		return parent;
	}

	public void setIgnoredFields(Set<String> ignoredFields) {
		this.ignoredFields = ignoredFields;
	}

	public Set<String> getIgnoredFields() {
		return ignoredFields;
	}
	
	public void addIgnoredField(String field){
		this.ignoredFields.add(field);
	}
}
