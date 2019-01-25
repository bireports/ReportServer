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

import net.datenwerke.dtoservices.dtogenerator.annotations.ExposeToClient;
import net.datenwerke.dtoservices.dtogenerator.annotations.GenerateDto;
import net.datenwerke.rs.terminal.service.terminal.obj.CommandResultExtension;

@GenerateDto(
	dtoPackage="net.datenwerke.rs.scripting.client.scripting.dto"
)
public class AddStatusbBarLabelExtension extends CommandResultExtension {

	@ExposeToClient
	private boolean left = false;
	
	@ExposeToClient
	private String label;
	
	@ExposeToClient
	private String icon;
	
	@ExposeToClient
	private boolean clear = false;
	

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

	public void setLeft(boolean left) {
		this.left = left;
	}

	public boolean isLeft() {
		return left;
	}
	
	public boolean isClear() {
		return clear;
	}
	
	public void setClear(boolean clear) {
		this.clear = clear;
	}

}
