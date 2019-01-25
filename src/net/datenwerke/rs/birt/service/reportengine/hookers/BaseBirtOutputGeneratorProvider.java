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
 
 
package net.datenwerke.rs.birt.service.reportengine.hookers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import net.datenwerke.rs.birt.service.reportengine.hooks.BirtOutputGeneratorProviderHook;
import net.datenwerke.rs.birt.service.reportengine.output.generator.BirtDOCOutputGenerator;
import net.datenwerke.rs.birt.service.reportengine.output.generator.BirtHTMLOutputGenerator;
import net.datenwerke.rs.birt.service.reportengine.output.generator.BirtOutputGenerator;
import net.datenwerke.rs.birt.service.reportengine.output.generator.BirtPDFOutputGenerator;
import net.datenwerke.rs.birt.service.reportengine.output.generator.BirtPNGOutputGenerator;
import net.datenwerke.rs.birt.service.reportengine.output.generator.BirtXLSOutputGenerator;

import com.google.inject.Inject;
import com.google.inject.Provider;

public class BaseBirtOutputGeneratorProvider implements BirtOutputGeneratorProviderHook{

	private final Provider<BirtHTMLOutputGenerator> html;
	private final Provider<BirtPDFOutputGenerator> pdf;
	private final Provider<BirtXLSOutputGenerator> xls;
	private final Provider<BirtPNGOutputGenerator> png;
	private final Provider<BirtDOCOutputGenerator> rtf;
	
	
	@Inject
	public BaseBirtOutputGeneratorProvider(
			Provider<BirtHTMLOutputGenerator> html,
			Provider<BirtPDFOutputGenerator> pdf,
			Provider<BirtXLSOutputGenerator> xls,
			Provider<BirtPNGOutputGenerator> png,
			Provider<BirtDOCOutputGenerator> rtf) {
		super();
		this.html = html;
		this.pdf = pdf;
		this.xls = xls;
		this.png = png;
		this.rtf = rtf;
	}



	@Override
	public Collection<BirtOutputGenerator> provideGenerators() {
		List<BirtOutputGenerator> generators = new ArrayList<BirtOutputGenerator>();
		
		generators.add(html.get());
		generators.add(pdf.get());
		generators.add(xls.get());
		generators.add(png.get());
		generators.add(rtf.get());
		
		return generators;
	}

}
