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
 
 
package net.datenwerke.gxtdto.server.dtomanager;

import net.datenwerke.gxtdto.client.dtomanager.Dto;
import net.datenwerke.gxtdto.client.dtomanager.Dto2PosoMapper;
import net.datenwerke.gxtdto.client.dtomanager.DtoView;
import net.datenwerke.gxtdto.client.servercommunication.exceptions.ExpectedException;

public interface DtoService {

	public Object createDto(Object poso);

	public Object createDto(Object poso, DtoView here, DtoView referenced);

	public Object createListDto(Object poso);

	public Object createDtoFullAccess(Object poso);

	public Object instantiateDto(Object poso);

	public Object instantiateDto(Class<?> posoType);

	public Object loadPoso(Object dto);

	public Object instantiatePoso(Class<?> dtoClass);

	public Object createPoso(Object dto) throws ExpectedException;

	public Object createUnmanagedPoso(Object dto) throws ExpectedException;

	public void mergePoso(Object dto, Object poso) throws ExpectedException;

	public void mergeUnmanagedPoso(Object dto, Object poso) throws ExpectedException;

	public Object loadAndMergePoso(Object dto) throws ExpectedException;

	public boolean isAuthorityForPosoClass(Class<?> clazz);

	public boolean isAuthorityForDtoClass(Class<?> clazz);

	public boolean isAuthorityForPoso(Object object);

	public boolean isAuthorityForDto(Object object);

	public Class<?> getPosoFromDtoMapper(Dto2PosoMapper mapper);

	public String[] createFto(Object poso, DtoView here, DtoView referenced);

	String[] createFto(Object poso);

	String[] createListFto(Object poso);

	String[] createFtoFullAccess(Object poso);

	String[] dto2Fto(Dto dto);
}
