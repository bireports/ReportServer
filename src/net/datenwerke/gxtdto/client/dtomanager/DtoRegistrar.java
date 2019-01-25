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
 
 
package net.datenwerke.gxtdto.client.dtomanager;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Helper class to register Dtos in a dto container
 *
 */
public class DtoRegistrar {

	private final ClientDtoManagerService dtoManager;
	private Map<Dto, Dto> registeredDtos = new HashMap<Dto, Dto>();
	
	public DtoRegistrar(ClientDtoManagerService dtoManager){
		this.dtoManager = dtoManager;
	}
	
	/**
	 * Registers a dto and returns a proxied object.
	 * 
	 * @param dto
	 * @return
	 */
	public <X extends Dto> X registerDto(X dto){
		if(registeredDtos.containsKey(dto))
			return (X) registeredDtos.get(dto);
		
		X proxy = dtoManager.registerDto(dto);
		registeredDtos.put(dto, proxy);
		return proxy;
	}
	
	/**
	 * Registers a collections of dtos and returns proxied objects.
	 * 
	 * @param dtos
	 * @return
	 */
	public <X extends Dto> List<X> registerDtos(Collection<X> dtos){
		List<X> proxies = new ArrayList<X>();
		for(X dto : dtos)
			proxies.add(registerDto(dto));
		
		return proxies;
	}

}
