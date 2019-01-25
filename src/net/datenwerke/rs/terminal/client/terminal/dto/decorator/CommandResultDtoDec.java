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

import net.datenwerke.rs.terminal.client.terminal.dto.CommandResultDto;
import net.datenwerke.rs.terminal.client.terminal.dto.CommandResultExtensionDto;
import net.datenwerke.rs.terminal.client.terminal.dto.CommandResultModifierDto;

/**
 * Dto Decorator for {@link CommandResultDto}
 *
 */
public class CommandResultDtoDec extends CommandResultDto {


	private static final long serialVersionUID = 1L;

	public CommandResultDtoDec() {
		super();
	}

	public boolean hasModifier(Class<? extends CommandResultModifierDto> type){
		for(CommandResultModifierDto modifier : getModifiers()){
			boolean found = testModifier(modifier.getClass(), type);
			if(found)
				return true;
		}
		return false;
	}

	private boolean testModifier(Class<?> toCheck, Class<? extends CommandResultModifierDto> toFind) {
		if(null == toCheck)
			return false;
		if(toCheck.equals(toFind))
			return true;
		
		return testModifier(toCheck.getSuperclass(), toFind);
		
	}

	public <E extends CommandResultExtensionDto> E getExtension(Class<E> type) {
		if(null == type)
			return null;
		if(null == getExtensions())
			return null;
		for(CommandResultExtensionDto ext : getExtensions())
			if(type.getClass().equals(ext.getClass()))
				return (E) ext;
		return null;
	}
}
