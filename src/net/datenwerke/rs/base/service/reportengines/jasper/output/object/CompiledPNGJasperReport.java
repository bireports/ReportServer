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
 
 
package net.datenwerke.rs.base.service.reportengines.jasper.output.object;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

import javax.imageio.ImageIO;

import net.datenwerke.dtoservices.dtogenerator.annotations.GenerateDto;
import net.datenwerke.rs.core.service.reportmanager.engine.basereports.CompiledPngReport;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 *
 */
@GenerateDto(
	dtoPackage="net.datenwerke.rs.base.client.reportengines.jasper.dto"
)
public class CompiledPNGJasperReport extends CompiledRSJasperReport implements CompiledPngReport{

	private final Logger logger = LoggerFactory.getLogger(getClass().getName());
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5661102670095695972L;
	
	private BufferedImage[] report;

	public BufferedImage[] getReport() {
		return report;
	}
	
	public String getBase64Report() {
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ImageIO.write(report[0], "png", baos);
			byte[] bytesOut = baos.toByteArray();
			
			return new String(Base64.encodeBase64(bytesOut));
		} catch (IOException e) {
			logger.warn( "Failed getting report as base64", e);
		}
		
		return "";
		
	}
	
	public void setBase64Report(String report){
		// ignore
	}

	public void setReport(Object report) {
		try{
			this.report = (BufferedImage[]) report;
		} catch(ClassCastException e){
			IllegalArgumentException iae = new IllegalArgumentException("Expected BufferedImage array"); //$NON-NLS-1$
			iae.initCause(e);
			throw iae;
		}
	}

	public String getFileExtension() {
		return "png"; //$NON-NLS-1$
	}

	public String getMimeType() {
		return "image/png"; //$NON-NLS-1$
	}
	
	private void readObject(ObjectInputStream ois) throws ClassNotFoundException, IOException {
		
	}
	
}
