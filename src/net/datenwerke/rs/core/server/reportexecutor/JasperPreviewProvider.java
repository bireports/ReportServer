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
 
 
package net.datenwerke.rs.core.server.reportexecutor;

import java.awt.image.RenderedImage;
import java.io.IOException;
import java.io.OutputStream;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.datenwerke.security.server.SecuredHttpServlet;

import com.google.inject.Singleton;
import com.google.inject.persist.Transactional;

@Singleton
public class JasperPreviewProvider extends SecuredHttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5593388658395337773L;
	
	public static final String IMAGE_SESSION_KEY = "jasperPreview_image";
	
	@Override
	@Transactional(rollbackOn={Exception.class})
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		
		Object image = session.getAttribute(IMAGE_SESSION_KEY);

		if(null != image && image instanceof RenderedImage){
			resp.setContentType("image/png"); //$NON-NLS-1$
			OutputStream os = resp.getOutputStream();
			ImageIO.write((RenderedImage) image, "png", os);
			os.close();
		}
		
		session.setAttribute(IMAGE_SESSION_KEY, null);
	}
}
