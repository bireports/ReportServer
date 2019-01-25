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
 
 
package net.datenwerke.rs.base.service.dbhelper.dtogen.post;

import net.datenwerke.dtoservices.dtogenerator.poso2dtogenerator.interfaces.Poso2DtoPostProcessor;
import net.datenwerke.rs.base.client.dbhelper.dto.DatabaseHelperDto;
import net.datenwerke.rs.base.service.dbhelper.DatabaseHelper;

public class DatabaseHelper2DtoPostProcessor implements Poso2DtoPostProcessor<DatabaseHelper, DatabaseHelperDto> {

	@Override
	public void dtoCreated(DatabaseHelper poso, DatabaseHelperDto dto) {
		checkJdbcDriver(poso,dto);
	}

	@Override
	public void dtoInstantiated(DatabaseHelper poso, DatabaseHelperDto dto) {
		checkJdbcDriver(poso,dto);
	}

	protected void checkJdbcDriver(DatabaseHelper poso, DatabaseHelperDto dto) {
		try{
			Class.forName(poso.getDriver());
			dto.setJdbcDriverAvailable(true);
		} catch(Throwable e){
			dto.setJdbcDriverAvailable(false);
		}
	}

}
