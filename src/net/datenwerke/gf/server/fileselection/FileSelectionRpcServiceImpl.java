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
 
 
package net.datenwerke.gf.server.fileselection;

import java.util.ArrayList;

import net.datenwerke.gf.client.fileselection.FileSelectionConfig;
import net.datenwerke.gf.client.fileselection.dto.SelectedFileWrapper;
import net.datenwerke.gf.client.fileselection.rpc.FileSelectionRpcService;
import net.datenwerke.gf.service.fileselection.hooks.FileSelectionHandlerHook;
import net.datenwerke.gxtdto.client.servercommunication.exceptions.ServerCallFailedException;
import net.datenwerke.hookhandler.shared.hookhandler.HookHandlerService;
import net.datenwerke.security.server.SecuredRemoteServiceServlet;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import com.google.inject.persist.Transactional;

@Singleton
public class FileSelectionRpcServiceImpl  extends SecuredRemoteServiceServlet implements FileSelectionRpcService {


	/**
	 * 
	 */
	private static final long serialVersionUID = 514135211083245835L;
	
	private final Provider<HookHandlerService> hookHandlerProvider;

	@Inject
	public FileSelectionRpcServiceImpl(
		Provider<HookHandlerService> hookHandlerProvider
		){
		this.hookHandlerProvider = hookHandlerProvider;
		
	}
	
	@Override
	@Transactional(rollbackOn={Exception.class})
	public void submit(ArrayList<SelectedFileWrapper> data, FileSelectionConfig config)
			throws ServerCallFailedException {
		/* hand call over to handler */
		for(FileSelectionHandlerHook hooker : hookHandlerProvider.get().getHookers(FileSelectionHandlerHook.class)){
			if(hooker.consumes(config.getHandler())){
				try{
					hooker.processData(data, config.getHandler(), config.getMetadata());
					return;
				} catch(RuntimeException e){
					throw new ServerCallFailedException(e);
				}
			}
		}
		throw new ServerCallFailedException("Could not find handler for: " + config.getHandler());		
	}

}
