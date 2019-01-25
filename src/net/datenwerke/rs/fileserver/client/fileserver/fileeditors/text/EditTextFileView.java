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
 
 
package net.datenwerke.rs.fileserver.client.fileserver.fileeditors.text;

import net.datenwerke.gf.client.managerhelper.mainpanel.MainPanelView;
import net.datenwerke.rs.fileserver.client.fileserver.dto.FileServerFileDto;
import net.datenwerke.rs.fileserver.client.fileserver.dto.decorator.FileServerFileDtoDec;
import net.datenwerke.rs.fileserver.client.fileserver.hooks.EditFileServerFileHook;

import com.google.inject.Inject;
import com.google.inject.Provider;

public class EditTextFileView implements EditFileServerFileHook {

	@Inject
	private Provider<TextFileView> textFileViewProvider;
	
	@Override
	public boolean consumes(FileServerFileDto file) {
		if(null == file)
			return false;
		
		if(null == file.getContentType()){
			if(((FileServerFileDtoDec)file).getSize() < 1024*10)
				return true;
			return false;
		}
			
		if(null != file.getName() && file.getName().endsWith("rs"))
			return true;
		
		return (file.getContentType().startsWith("text") || file.getContentType().endsWith("xml") || file.getContentType().contains("groovy"));
	}

	@Override
	public MainPanelView getView(FileServerFileDto file) {
		return textFileViewProvider.get();
	}

	
	
}
