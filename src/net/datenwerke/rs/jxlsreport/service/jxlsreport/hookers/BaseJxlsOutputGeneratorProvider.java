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
 
 
package net.datenwerke.rs.jxlsreport.service.jxlsreport.hookers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import net.datenwerke.rs.jxlsreport.service.jxlsreport.reportengine.hooks.JxlsOutputGeneratorProviderHook;
import net.datenwerke.rs.jxlsreport.service.jxlsreport.reportengine.output.generator.JxlsHTMLOutputGenerator;
import net.datenwerke.rs.jxlsreport.service.jxlsreport.reportengine.output.generator.JxlsOutputGenerator;
import net.datenwerke.rs.jxlsreport.service.jxlsreport.reportengine.output.generator.JxlsOutputGeneratorImpl;

import com.google.inject.Inject;
import com.google.inject.Provider;

public class BaseJxlsOutputGeneratorProvider implements JxlsOutputGeneratorProviderHook{

	private final Provider<JxlsOutputGeneratorImpl> jxlsOutputGenerator;
	private final Provider<JxlsHTMLOutputGenerator> jxlsHtmlOutputGenerator;
	
	@Inject
	public BaseJxlsOutputGeneratorProvider(
			Provider<JxlsOutputGeneratorImpl> jxlsOutputGenerator,
			Provider<JxlsHTMLOutputGenerator> jxlsHtmlOutputGenerator) {
		super();
		this.jxlsOutputGenerator = jxlsOutputGenerator;
		this.jxlsHtmlOutputGenerator = jxlsHtmlOutputGenerator;
	}



	@Override
	public Collection<JxlsOutputGenerator> provideGenerators() {
		List<JxlsOutputGenerator> generators = new ArrayList<JxlsOutputGenerator>();
		
		generators.add(jxlsOutputGenerator.get());
		generators.add(jxlsHtmlOutputGenerator.get());
		
		return generators;
	}

}
