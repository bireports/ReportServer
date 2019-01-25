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
 
 
package net.datenwerke.rs.terminal.client.terminal.dto.decorator;

import net.datenwerke.rs.terminal.client.terminal.dto.AutocompleteResultDto;

/**
 * Dto Decorator for {@link AutocompleteResultDto}
 *
 */
public class AutocompleteResultDtoDec extends AutocompleteResultDto {


	private static final long serialVersionUID = 1L;

	public AutocompleteResultDtoDec() {
		super();
	}

	public int size() {
		return getEntries().getList().size() + getCmdEntries().getList().size() + getObjectEntries().getList().size();
	}


}
