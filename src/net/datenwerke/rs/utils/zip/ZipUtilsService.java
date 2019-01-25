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
 
 
package net.datenwerke.rs.utils.zip;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;

import net.datenwerke.rs.fileserver.service.fileserver.entities.AbstractFileServerNode;
import net.datenwerke.rs.fileserver.service.fileserver.entities.FileServerFolder;
import net.datenwerke.rs.utils.zip.ZipUtilsService.FileFilter;

public interface ZipUtilsService {

	public interface FileFilter{
		public boolean addNode(AbstractFileServerNode node);
	}
	
	public static Object DIRECTORY_MARKER = new Object();
	
	public void createZip(Map<String, ? extends Object> content, OutputStream os) throws IOException;
	
	public void extractZip(byte[] data, ZipExtractionConfig config) throws IOException;
	
	public void extractZip(InputStream is, ZipExtractionConfig config) throws IOException;

	void createZip(byte[] content, OutputStream os) throws IOException;

	void createZip(FileServerFolder folder, OutputStream os) throws IOException;

	void createZip(FileServerFolder folder, OutputStream os, FileFilter filter) throws IOException;
}