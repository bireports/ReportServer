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
 
 
package net.datenwerke.gf.server.history;

import java.util.ArrayList;
import java.util.List;

import net.datenwerke.gf.client.history.dto.HistoryLinkDto;
import net.datenwerke.gf.client.history.rpc.HistoryRpcService;
import net.datenwerke.gf.service.history.HistoryLink;
import net.datenwerke.gf.service.history.HistoryService;
import net.datenwerke.gxtdto.client.dtomanager.Dto;
import net.datenwerke.gxtdto.server.dtomanager.DtoService;
import net.datenwerke.security.server.SecuredRemoteServiceServlet;
import net.datenwerke.security.service.security.SecurityService;
import net.datenwerke.security.service.security.SecurityTarget;
import net.datenwerke.security.service.security.exceptions.ViolatedSecurityException;
import net.datenwerke.security.service.security.rights.Read;

import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class HistoryRpcServiceImpl extends SecuredRemoteServiceServlet
		implements HistoryRpcService {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4008561014565465375L;

	private final HistoryService historyService;
	private final DtoService dtoService;

	private final SecurityService securityService;

	
	@Inject
	public HistoryRpcServiceImpl(
		DtoService dtoService,
		HistoryService historyService,
		SecurityService securityService){
		this.dtoService = dtoService;
		this.historyService = historyService;
		this.securityService = securityService;
	}


	@Override
	public List<HistoryLinkDto> getLinksFor(Dto dto) {
		Object obj = dtoService.loadPoso(dto);
		
		if(obj instanceof SecurityTarget && ! securityService.checkRights((SecurityTarget)obj, Read.class))
			throw new ViolatedSecurityException();
		
		List<HistoryLinkDto> list = new ArrayList<HistoryLinkDto>();
		for(HistoryLink link : historyService.buildLinksFor(obj))
			list.add((HistoryLinkDto) dtoService.createDto(link));
		
		return list;
	}
	
}
