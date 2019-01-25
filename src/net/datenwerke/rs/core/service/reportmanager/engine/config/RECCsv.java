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
 
 
package net.datenwerke.rs.core.service.reportmanager.engine.config;

import net.datenwerke.dtoservices.dtogenerator.annotations.ExposeToClient;
import net.datenwerke.dtoservices.dtogenerator.annotations.GenerateDto;

@GenerateDto(
	dtoPackage="net.datenwerke.rs.core.client.reportexporter.dto",
	createDecorator=true
)
public class RECCsv implements ReportExecutionConfig {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6096389901801569328L;

	@ExposeToClient
	private boolean printHeader = true;
	
	@ExposeToClient
	private String separator = ";";
	
	@ExposeToClient
	private String quote = "\"";
	
	public boolean isPrintHeader() {
		return printHeader;
	}
	public void setPrintHeader(boolean printHeader) {
		this.printHeader = printHeader;
	}
	public String getSeparator() {
		return separator;
	}
	public void setSeparator(String separator) {
		this.separator = separator;
	}
	public String getQuote() {
		return quote;
	}
	public void setQuote(String quote) {
		this.quote = quote;
	}
	
	@Override
	public int hashCode() {
		return separator.hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		if(null == obj || ! (obj instanceof RECCsv))
			return false;
		
		return printHeader == ((RECCsv)obj).printHeader && separator == ((RECCsv)obj).separator && quote == ((RECCsv)obj).quote;
	}
	
}
