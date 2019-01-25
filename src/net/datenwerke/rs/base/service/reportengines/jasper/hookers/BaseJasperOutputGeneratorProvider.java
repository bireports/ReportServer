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
 
 
package net.datenwerke.rs.base.service.reportengines.jasper.hookers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import net.datenwerke.rs.base.service.reportengines.jasper.hooks.JasperOutputGeneratorProviderHook;
import net.datenwerke.rs.base.service.reportengines.jasper.output.generator.JasperCsvOutputGenerator;
import net.datenwerke.rs.base.service.reportengines.jasper.output.generator.JasperHTMLOutputGenerator;
import net.datenwerke.rs.base.service.reportengines.jasper.output.generator.JasperOutputGenerator;
import net.datenwerke.rs.base.service.reportengines.jasper.output.generator.JasperPDFOutputGenerator;
import net.datenwerke.rs.base.service.reportengines.jasper.output.generator.JasperPNGOutputGenerator;
import net.datenwerke.rs.base.service.reportengines.jasper.output.generator.JasperRTFOutputGenerator;
import net.datenwerke.rs.base.service.reportengines.jasper.output.generator.JasperXLSOutputGenerator;

import com.google.inject.Inject;
import com.google.inject.Provider;

public class BaseJasperOutputGeneratorProvider implements JasperOutputGeneratorProviderHook {

	private final Provider<JasperHTMLOutputGenerator> html;
	private final Provider<JasperPDFOutputGenerator> pdf;
	private final Provider<JasperXLSOutputGenerator> xls;
	private final Provider<JasperPNGOutputGenerator> png;
	private final Provider<JasperRTFOutputGenerator> rtf; 
	private final Provider<JasperCsvOutputGenerator> csv;
	
	@Inject
	public BaseJasperOutputGeneratorProvider(
			Provider<JasperHTMLOutputGenerator> html,
			Provider<JasperPDFOutputGenerator> pdf,
			Provider<JasperXLSOutputGenerator> xls,
			Provider<JasperPNGOutputGenerator> png,
			Provider<JasperRTFOutputGenerator> rtf,
			Provider<JasperCsvOutputGenerator> csv) {
		super();
		this.html = html;
		this.pdf = pdf;
		this.xls = xls;
		this.png = png;
		this.rtf = rtf;
		this.csv = csv;
	}


	@Override
	public Collection<JasperOutputGenerator> provideGenerators() {
		List<JasperOutputGenerator> generators = new ArrayList<JasperOutputGenerator>();
		
		generators.add(html.get());
		generators.add(pdf.get());
		generators.add(xls.get());
		generators.add(png.get());
		generators.add(rtf.get());
		generators.add(csv.get());
		
		
		return generators;
	}

}
