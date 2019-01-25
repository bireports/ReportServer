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
 
 
package net.datenwerke.rs.base.service.parameters.datetime;

import java.util.Date;

import net.datenwerke.rs.core.service.parameters.entities.ParameterDefinitionForJuel;

public class DateTimeParameterDefinitionForJuel extends
		ParameterDefinitionForJuel {

	private Mode mode = Mode.Date;
	private Boolean useNowAsDefault = true;
	private Date defaultValue;
	
	public Mode getMode() {
		return mode;
	}
	public void setMode(Mode mode) {
		this.mode = mode;
	}
	public Boolean getUseNowAsDefault() {
		return useNowAsDefault;
	}
	public void setUseNowAsDefault(Boolean useNowAsDefault) {
		this.useNowAsDefault = useNowAsDefault;
	}
	public Date getDefaultValue() {
		return defaultValue;
	}
	public void setDefaultValue(Date defaultValue) {
		this.defaultValue = defaultValue;
	}
	
	
}
