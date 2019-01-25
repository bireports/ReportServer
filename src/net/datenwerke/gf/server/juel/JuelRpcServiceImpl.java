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
 
 
package net.datenwerke.gf.server.juel;

import javax.inject.Singleton;

import net.datenwerke.gf.client.juel.dto.JuelResultDto;
import net.datenwerke.gf.client.juel.rpc.JuelRpcService;
import net.datenwerke.gf.service.juel.JuelResult;
import net.datenwerke.gxtdto.client.servercommunication.exceptions.ServerCallFailedException;
import net.datenwerke.gxtdto.server.dtomanager.DtoService;
import net.datenwerke.rs.utils.juel.SimpleJuel;
import net.datenwerke.security.server.SecuredRemoteServiceServlet;

import com.google.inject.Inject;
import com.google.inject.Provider;

@Singleton
public class JuelRpcServiceImpl extends SecuredRemoteServiceServlet implements JuelRpcService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5137215532189202055L;
	
	private final Provider<SimpleJuel> juelServiceProvider;
	private final Provider<DtoService> dtoServiceProvider;
	
	@Inject
	public JuelRpcServiceImpl(
		Provider<SimpleJuel> juelServiceProvider,
		Provider<DtoService> dtoServiceProvider) {
		super();
		
		this.juelServiceProvider = juelServiceProvider;
		this.dtoServiceProvider = dtoServiceProvider;
	}



	@Override
	public JuelResultDto evaluateExpression(String expression) throws ServerCallFailedException {
		Object result = juelServiceProvider.get().parseAsObject(expression);
		
		try{
			return (JuelResultDto) dtoServiceProvider.get().createDto(new JuelResult(result));
		} catch(Exception e){
			throw new ServerCallFailedException(e);
		}
	}

}
