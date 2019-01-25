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

import java.util.Collection;

import net.datenwerke.gxtdto.client.dtomanager.events.DtoListener;

import com.sencha.gxt.core.client.ValueProvider;


/**
 * 
 *
 */
public interface ClientDtoManagerService {

	/**
	 * Registers a dto and returns a proxied object.
	 * 
	 * @param dto
	 * @return
	 */
	public <X extends Dto> X registerDto(X dto);
	
	/**
	 * Registers a collections of dtos and returns proxied objects.
	 * 
	 * @param dtos
	 * @return
	 */
	public <X extends Dto> Collection<X> registerDtos(Collection<X> dtos);

	/**
	 * Detaches Dtos from the manager.
	 * 
	 * <p>
	 * As a consequence all listeners on that particular dto are detached.
	 * </p>
	 * 
	 * <p>
	 * If properties for a specific dto are requested 
	 * 
	 * </p>
	 * @param <X>
	 * @param dto
	 */
	public <X extends Dto> void detachDto(X dto);
	
	/**
	 * Detaches Dtos from the manager.
	 * 
	 * @see #detachDto(Dto)
	 * @param <X>
	 * @param dtos
	 */
	public <X extends Dto> void detachDtos(Collection<X> dtos);
	
	public void registerDtoContainer(DtoContainer container);
	
	public <X> X getProperty(Dto proxy, ValueProvider valueProvider);
	
	public <X> void setProperty(Dto proxy, ValueProvider property, X value);

	public void registerDtoContainer(Collection<DtoContainer> result);
	
	public void onDtoChange(Dto dto, DtoListener listener);
	
	public void removeChangeListener(Dto dto, DtoListener listener);
	
	public void onDtoChange(DtoListener listener);
	
	public void removeDtoChangeListener(DtoListener listener);
	
	
	/**
	 * Can for example be used to "clone" dtos.
	 * @param <X>
	 * @param dto
	 * @return
	 */
	public <X extends Dto> X newProxy(X dto);
	
	/**
	 * Creates a propper clone.
	 * @param <X>
	 * @param dto
	 */
	public <X extends Dto> X deepClone(X dto);
	
	/**
	 * Turns a proxy back into a dto
	 * @param <X>
	 * @param dto
	 */
	public <X extends Dto> X unproxy(X dto);
	
	public <X extends Dto> Collection<X> unproxy(Collection<X> dtos);
	
}
