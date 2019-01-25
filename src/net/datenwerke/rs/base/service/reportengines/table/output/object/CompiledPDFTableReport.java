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
 
 
package net.datenwerke.rs.base.service.reportengines.table.output.object;

/**
 * 
 *
 */
public class CompiledPDFTableReport extends CompiledTableReport{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7175341527659298964L;
	private byte[] report;

	public CompiledPDFTableReport() {
	}

	public CompiledPDFTableReport(byte[] cReport) {
		setReport(cReport);
	}

	public byte[] getReport() {
		return report;
	}

	public void setReport(Object report) {
		try{
			this.report = (byte[]) report;
		} catch(ClassCastException e){
			IllegalArgumentException iae = new IllegalArgumentException("Expected byte array"); //$NON-NLS-1$
			iae.initCause(e);
			throw iae;
		}
	}
	
	public String getFileExtension() {
		return "pdf"; //$NON-NLS-1$
	}

	public String getMimeType() {
		return "application/pdf"; //$NON-NLS-1$
	}

	@Override
	public boolean isStringReport() {
		return false;
	}
	
}
