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
 
 
package net.datenwerke.treedb.client.treedb.dto.decorator;

import net.datenwerke.gxtdto.client.dtomanager.stores.TreeDto;
import net.datenwerke.gxtdto.client.locale.BaseMessages;
import net.datenwerke.treedb.client.treedb.dto.AbstractNodeDto;

/**
 * Dto Decorator for {@link AbstractNodeDto}
 *
 */
abstract public class AbstractNodeDtoDec extends AbstractNodeDto implements TreeDto {

	public static final long FLAG_WRITE_PROTECT = 1;
	
	private static final long serialVersionUID = 1L;

	public AbstractNodeDtoDec() {
		super();
	}

	@Override
	public boolean hasChildren() {
		return isHasChildren();
	}
	
	public boolean testFlags(long flag){
		return ((long)getFlags() & flag) == flag;
	}
	
	public boolean isWriteProtected(){
		return testFlags(FLAG_WRITE_PROTECT);
	}
	
	public String getDescription(){
		return "";
	}
	
}
