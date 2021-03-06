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
 
 
package net.datenwerke.rs.scripting.service.scripting.extensions;

import java.util.ArrayList;
import java.util.List;

import net.datenwerke.dtoservices.dtogenerator.annotations.ExposeToClient;
import net.datenwerke.dtoservices.dtogenerator.annotations.GenerateDto;
import net.datenwerke.rs.terminal.service.terminal.obj.CommandResultExtension;
import net.datenwerke.rs.utils.entitycloner.annotation.EnclosedEntity;

@GenerateDto(
	dtoPackage="net.datenwerke.rs.scripting.client.scripting.dto"
)
public class AddMenuEntryExtension extends CommandResultExtension {

	@ExposeToClient
	private String menuName;
	
	@ExposeToClient
	private String label;
	
	@ExposeToClient
	private String icon;
	
	@ExposeToClient
	private String scriptLocation;
	
	@ExposeToClient
	private String arguments;
	
	@ExposeToClient(allowArbitraryLobSize=true,disableHtmlEncode=true)
	private String javaScript;
	
	@EnclosedEntity
	@ExposeToClient
	private List<AddMenuEntryExtension> subMenuEntries = new ArrayList<AddMenuEntryExtension>();
	
	@EnclosedEntity
	@ExposeToClient
	private List<DisplayCondition> displayConditions = new ArrayList<DisplayCondition>();

	public void setSubMenuEntries(List<AddMenuEntryExtension> subMenuEntries) {
		this.subMenuEntries = subMenuEntries;
	}

	public List<AddMenuEntryExtension> getSubMenuEntries() {
		return subMenuEntries;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getScriptLocation() {
		return scriptLocation;
	}

	public void setScriptLocation(String scriptLocation) {
		this.scriptLocation = scriptLocation;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public String getMenuName() {
		return menuName;
	}

	public String getArguments() {
		return arguments;
	}
	
	public void setArguments(String arguments) {
		this.arguments = arguments;
	}
	
	public void setDisplayConditions(List<DisplayCondition> displayConditions) {
		this.displayConditions = displayConditions;
	}
	
	public void addDisplayCondition(DisplayCondition displayCondition) {
		if(null == displayConditions)
			displayConditions = new ArrayList<DisplayCondition>();
		this.displayConditions.add(displayCondition);
	}

	public List<DisplayCondition> getDisplayConditions() {
		return displayConditions;
	}

	public String getJavaScript() {
		return javaScript;
	}

	public void setJavaScript(String javaScript) {
		this.javaScript = javaScript;
	}
}
