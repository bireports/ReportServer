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
 
 
package net.datenwerke.rs.birt.service.reportengine.output.generator;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;
import org.eclipse.birt.report.engine.api.HTMLImageHandler;
import org.eclipse.birt.report.engine.api.IImage;
import org.eclipse.birt.report.engine.api.impl.Image;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Base64ImageHandler extends HTMLImageHandler {

	private final Logger logger = LoggerFactory.getLogger(getClass().getName());
	
	private String handleImage( IImage image, Object context, String prefix, boolean needMap )	{
		try {
			byte[] imageBytes = image.getImageData();
			String base64String =new String(Base64.encodeBase64(imageBytes));
			
			String data = "data:" + image.getMimeType() + ";base64," + base64String;
			return data;
		} catch (Exception e) {
			logger.warn( "Could not create image for birt report", e);
		}
		return "failed-creating-tempfile";	
	}

	@Deprecated
	@Override
	public String onDesignImage( IImage image, Object context )	{
		return handleImage( image, context, "design", true ); //$NON-NLS-1$
	}

	@Deprecated
	@Override
	public String onDocImage( IImage image, Object context )	{
		return null;
	}

	@Deprecated
	@Override
	public String onURLImage( IImage image, Object context )	{
		assert ( image != null );
		String uri = image.getID( );
		if (uri.startsWith( "http:" ) || uri.startsWith( "https:" ))
		{
			return uri;
		}
		return handleImage( image, context, "uri", true ); //$NON-NLS-1$
	}

	@Deprecated
	@Override
	public String onCustomImage( IImage image, Object context )	{
		return handleImage( image, context, "custom", false ); //$NON-NLS-1$
	}

	@Deprecated
	@Override
	public String onFileImage( IImage image, Object context ) {
		if(image.getID().startsWith("rsfs://")){
			InputStream in = null;
			try {
				in = new URL(image.getID()).openStream();
				;
				Image img = new Image(IOUtils.toByteArray(in), image.getID());
				img.setMimeType(image.getMimeType());
				img.setImageMap(image.getImageMap());
				img.setRenderOption(image.getRenderOption());
				img.setReportRunnable(image.getReportRunnable());
				img.setSource(image.getSource());
				
				return handleImage( img, context, "file", true ); //$NON-NLS-1$
				
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if(null != in)
					IOUtils.closeQuietly(in);
			 }
		}
		return handleImage( image, context, "file", true ); //$NON-NLS-1$
	}


}
