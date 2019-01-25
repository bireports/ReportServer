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
 
 
package net.datenwerke.rs.core.service.reportmanager.output;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import net.datenwerke.hookhandler.shared.hookhandler.HookHandlerService;

/**
 * 
 *
 */
abstract public class AbstractReportOutputGeneratorManager<G extends ReportOutputGenerator> {

	protected final HookHandlerService hookHandler;
	protected final Class<? extends ReportOutputGeneratorProvider<G>> providerType;
	
	
	public AbstractReportOutputGeneratorManager(
		HookHandlerService hookHandler,
		Class<? extends ReportOutputGeneratorProvider<G>> providerType
		){
		this.hookHandler = hookHandler;
		this.providerType = providerType;
	}
	
	/**
	 * Gets a specific output generator that generates the specified format.
	 * 
	 * @param format
	 * @return The corresponding output generator
	 */
	public G getOutputGenerator(String format){
		if(null == format)
			throw new IllegalArgumentException("No format specified"); //$NON-NLS-1$
		
		G catchAll = null;
		for(G g : getRegisteredOutputGenerators()){
			if(g.isCatchAll() && null == catchAll)
				catchAll = g;
			for(String f: g.getFormats())
				if(format.equals(f))
					return g;
		}
		
		if(null == catchAll)
			throw new IllegalArgumentException("Could not find generator for format " + format); //$NON-NLS-1$
		
		return catchAll;
	}
	
	/**
	 * Returns all registered generators
	 * @return
	 */
	public List<G> getRegisteredOutputGenerators(){
		List<G> generators = new ArrayList<G>();
		for(ReportOutputGeneratorProvider<G> provider : hookHandler.getHookers(providerType)){
			Collection<G> genList = provider.provideGenerators();
			if(null != genList)
				generators.addAll(genList);
		}
		return generators;
	}
	
	/**
	 * Returns an array with all registered output formats
	 * 
	 * @return
	 */
	public String[] getRegisteredOutputFormats(){
		Set<String> formats = new HashSet<String>();

		for(G g : getRegisteredOutputGenerators())
			for(String format : g.getFormats())
				formats.add(format);
		
		return formats.toArray(new String[]{});
	}

	public boolean hasCatchAllOutputGen() {
		for(G g : getRegisteredOutputGenerators())
			if(g.isCatchAll())
				return true;
		return false;
	}
}
