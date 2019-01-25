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
 
 
package net.datenwerke.rs.fileserver.service.fileserver.terminal.commands.unzip;

import java.util.zip.ZipEntry;

import javax.inject.Inject;

import net.datenwerke.rs.fileserver.service.fileserver.FileServerService;
import net.datenwerke.rs.fileserver.service.fileserver.entities.AbstractFileServerNode;
import net.datenwerke.rs.fileserver.service.fileserver.entities.FileServerFile;
import net.datenwerke.rs.fileserver.service.fileserver.entities.FileServerFolder;
import net.datenwerke.rs.utils.zip.ZipExtractionConfig;

import com.google.inject.assistedinject.Assisted;

public class BasepathZipExtractConfig extends ZipExtractionConfig {

	private final FileServerFolder loc;
	private UnzipCommandHelper unzipHelper;
	private FileServerService fileServerService;

	@Inject
	public BasepathZipExtractConfig(@Assisted FileServerFolder parent, UnzipCommandHelper unzipHelper, FileServerService fileServerService) {
		this.loc = parent;
		this.unzipHelper = unzipHelper;
		this.fileServerService = fileServerService;
	}

	@Override
	public boolean isAllowedFile(ZipEntry entry) {
		return true;
	}

	@Override
	public void processContent(ZipEntry entry, byte[] content) {
		if(null == content || content.length==0 && entry.isDirectory() && "/".equals(entry.getName()))
			return;
		
		AbstractFileServerNode f = unzipHelper.createFileAndFolders(loc, entry.getName(), entry.isDirectory());
		if(f instanceof FileServerFile){
			((FileServerFile) f).setData(content);
			fileServerService.merge(f);
		}
	}

}
