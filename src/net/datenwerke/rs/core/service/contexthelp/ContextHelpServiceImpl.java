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
 
 
package net.datenwerke.rs.core.service.contexthelp;

import java.io.StringWriter;

import net.datenwerke.hookhandler.shared.hookhandler.HookHandlerService;
import net.datenwerke.rs.core.client.contexthelp.dto.ContextHelpInfo;
import net.datenwerke.rs.core.service.contexthelp.hooks.ContextHelpAdapterHook;
import net.datenwerke.rs.utils.man.ManPageService;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

import com.google.inject.Inject;

public class ContextHelpServiceImpl implements ContextHelpService {

	private final ManPageService manPageService;
	private final HookHandlerService hookHandler;
	
	@Inject
	public ContextHelpServiceImpl(
		ManPageService manPageService,
		HookHandlerService hookHandler) {
		super();
		
		this.manPageService = manPageService;
		this.hookHandler = hookHandler;
	}

	@Override
	public String getContextHelp(ContextHelpInfo info) {
		String manpage = manPageService.getManPageFailsafe("context/" + info.getId());
		
		VelocityContext context = new VelocityContext();
		
		StringWriter out = new StringWriter();
		Velocity.evaluate(context, out, "", manpage);
		
		
		String result = out.toString();
		
		for(ContextHelpAdapterHook adapter : hookHandler.getHookers(ContextHelpAdapterHook.class)){
			String adapted = adapter.adaptContextHelp(result, info);
			if(null != adapted)
				result = adapted;
		}
		
		return result;
	}

}
