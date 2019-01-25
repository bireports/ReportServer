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
 
 
package net.datenwerke.treedb.ext.client.eximport.im.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.sencha.gxt.data.shared.TreeStore.TreeNode;


public class ImportTreeModel implements Serializable, TreeNode<ImportTreeModel> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -455692463561631529L;

	private String id;
	private String name;
	private String parentId;
	private String type;

	private List<ImportTreeModel> children = new ArrayList<ImportTreeModel>();
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getParentId() {
		return parentId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public void setChildren(List<ImportTreeModel> children) {
		if(null == children)
			children = new ArrayList<ImportTreeModel>();
		this.children = children;
	}
	@Override
	public List<ImportTreeModel> getChildren() {
		return children;
	}
	public void addChild(ImportTreeModel model) {
		children.add(model);
	}
	@Override
	public ImportTreeModel getData() {
		return this;
	}
	
	
}
