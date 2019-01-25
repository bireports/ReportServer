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
 
 
package net.datenwerke.rs.base.service.reportengines.jasper.output.object;

import net.datenwerke.rs.core.service.reportmanager.engine.CompiledReport;
import net.datenwerke.rs.core.service.reportmanager.engine.basereports.HasPages;
import net.sf.jasperreports.engine.JasperPrint;

/**
 * Simple wrapper object that stores a completed JasperReport along with parameters of interest.
 * 
 *
 */
abstract public class CompiledRSJasperReport implements CompiledReport, HasPages {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7282903771389742735L;
	
	private int pageWidth;
	private int pageHeight;
	private int pages;
	
	abstract public void setReport(Object report);
	
	/**
	 * Extracts all information from the JasperPrint object.
	 * @param jasperPrint
	 */
	public void setData(JasperPrint jasperPrint) {
		setPageHeight(jasperPrint.getPageHeight());
		setPageWidth(jasperPrint.getPageWidth());
		setPages(jasperPrint.getPages().size());
	}
	
	private void setPages(int size) {
		this.pages = size;
	}
	
	public int getPages() {
		return pages;
	}

	public int getPageWidth() {
		return pageWidth;
	}
	public void setPageWidth(int pagewidth) {
		this.pageWidth = pagewidth;
	}
	public int getPageHeight() {
		return pageHeight;
	}
	public void setPageHeight(int pageheight) {
		this.pageHeight = pageheight;
	}
	
	@Override
	public boolean hasData() {
		return true;
	}
	
	@Override
	public boolean isStringReport() {
		return false;
	}
}
