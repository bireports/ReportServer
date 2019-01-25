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
 
 
package net.datenwerke.gf.base.client.dtogenerator;

import net.datenwerke.gxtdto.client.dtomanager.Dto;

/**
 * Dto base class for all ReportServer Dtos.
 *
 */
abstract public class RsDto extends Dto {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8159136471076859296L;

	public RsDto() {
		super();
	}
	
	@Override
	public boolean isModified() {
		return false;
	}
	
}
