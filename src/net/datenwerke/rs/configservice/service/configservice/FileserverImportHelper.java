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
 
 
package net.datenwerke.rs.configservice.service.configservice;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.regex.Pattern;

import javax.inject.Inject;

import net.datenwerke.rs.fileserver.service.fileserver.FileServerService;
import net.datenwerke.rs.fileserver.service.fileserver.entities.AbstractFileServerNode;
import net.datenwerke.rs.fileserver.service.fileserver.entities.FileServerFile;
import net.datenwerke.rs.fileserver.service.fileserver.entities.FileServerFolder;
import net.datenwerke.rs.fileserver.service.fileserver.terminal.commands.unzip.UnzipCommandHelper;
import net.datenwerke.rs.terminal.service.terminal.TerminalService;
import net.datenwerke.rs.terminal.service.terminal.objresolver.exceptions.ObjectResolverException;
import net.datenwerke.rs.utils.misc.MimeUtils;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;

public class FileserverImportHelper {

	private FileServerService fileServerService;
	private ConfigDirService configDirService;
	private TerminalService terminalService;
	private MimeUtils mimeUtils;

	@Inject
	public FileserverImportHelper(
			FileServerService fileServerService, 
			ConfigDirService configDirService, 
			TerminalService terminalService, 
			UnzipCommandHelper unzipHelper, 
			MimeUtils mimeUtils
			) {

		this.fileServerService = fileServerService;
		this.configDirService = configDirService;
		this.terminalService = terminalService;
		this.mimeUtils = mimeUtils;

	}

	public AbstractFileServerNode importFile(File f) throws IOException, ObjectResolverException{
		File importDir = new File(configDirService.getConfigDir(), ConfigStartup.FSIMPORT_DIR);
		String pathInImport = relativize(f.getCanonicalPath(), importDir.getCanonicalPath());
		String pathInFileserver =  pathInImport;

		AbstractFileServerNode node = createFileAndFolders(fileServerService.getRoots().get(0), pathInFileserver, f.isDirectory());
		if(node instanceof FileServerFile){
			((FileServerFile) node).setData(IOUtils.toByteArray(new FileInputStream(f)));
			((FileServerFile) node).setContentType(mimeUtils.getMimeTypeByExtension(f.getName()));

		}
		fileServerService.merge(node);

		return node;
	}

	private String relativize(String targetPath, String basePath){
		String pathSeparator = "/";
		// Normalize the paths
		String normalizedTargetPath = FilenameUtils.normalizeNoEndSeparator(targetPath);
		String normalizedBasePath = FilenameUtils.normalizeNoEndSeparator(basePath);

		// Undo the changes to the separators made by normalization
		if (pathSeparator.equals("/")) {
			normalizedTargetPath = FilenameUtils.separatorsToUnix(normalizedTargetPath);
			normalizedBasePath = FilenameUtils.separatorsToUnix(normalizedBasePath);

		} else if (pathSeparator.equals("\\")) {
			normalizedTargetPath = FilenameUtils.separatorsToWindows(normalizedTargetPath);
			normalizedBasePath = FilenameUtils.separatorsToWindows(normalizedBasePath);

		} else {
			throw new IllegalArgumentException("Unrecognised dir separator '" + pathSeparator + "'");
		}

		String[] base = normalizedBasePath.split(Pattern.quote(pathSeparator));
		String[] target = normalizedTargetPath.split(Pattern.quote(pathSeparator));

		// First get all the common elements. Store them as a string,
		// and also count how many of them there are.
		StringBuffer common = new StringBuffer();

		int commonIndex = 0;
		while (commonIndex < target.length && commonIndex < base.length
				&& target[commonIndex].equals(base[commonIndex])) {
			common.append(target[commonIndex] + pathSeparator);
			commonIndex++;
		}

		if (commonIndex == 0) {
			// No single common path element. This most
			// likely indicates differing drive letters, like C: and D:.
			// These paths cannot be relativized.
			throw new RuntimeException("No common path element found for '" + normalizedTargetPath + "' and '" + normalizedBasePath
					+ "'");
		}   

		// The number of directories we have to backtrack depends on whether the base is a file or a dir
		// For example, the relative path from
		//
		// /foo/bar/baz/gg/ff to /foo/bar/baz
		// 
		// ".." if ff is a file
		// "../.." if ff is a directory
		//
		// The following is a heuristic to figure out if the base refers to a file or dir. It's not perfect, because
		// the resource referred to by this path may not actually exist, but it's the best I can do
		boolean baseIsFile = true;

		File baseResource = new File(normalizedBasePath);

		if (baseResource.exists()) {
			baseIsFile = baseResource.isFile();

		} else if (basePath.endsWith(pathSeparator)) {
			baseIsFile = false;
		}

		StringBuffer relative = new StringBuffer();

		if (base.length != commonIndex) {
			int numDirsUp = baseIsFile ? base.length - commonIndex - 1 : base.length - commonIndex;

			for (int i = 0; i < numDirsUp; i++) {
				relative.append(".." + pathSeparator);
			}
		}
		relative.append(normalizedTargetPath.substring(common.length()));
		return relative.toString();
	}

	public AbstractFileServerNode createFileAndFolders(AbstractFileServerNode base, String filename, boolean isDirectory){
		String[] pathComponents = filename.split("/");
		Queue<String> q = new LinkedList<String>();
		for(String s : pathComponents){
			if(null != s && !s.isEmpty())
				q.add(s);
		}

		return createFileAndFolders(base, q, isDirectory);
	}

	private AbstractFileServerNode createFileAndFolders(AbstractFileServerNode base, Queue<String> filename, boolean isDirectory){
		/* Check if already exists */
		for(AbstractFileServerNode c : base.getChildren()){
			if(c instanceof FileServerFolder){
				if(null != ((FileServerFolder) c).getName() && ((FileServerFolder) c).getName().equals(filename.peek())){
					filename.poll();
					if(filename.size() > 0) {
						return createFileAndFolders(c, filename, isDirectory);
					} else {
						return c;
					}
				}
			}else if(!isDirectory && c instanceof FileServerFile){
				if(null != ((FileServerFile) c).getName() && ((FileServerFile) c).getName().equals(filename.peek())){
					filename.poll();
					if(filename.size() == 0) {
						return c;
					}
				}
			}
		}

		/* create */
		AbstractFileServerNode file;
		if(isDirectory || filename.size() > 1){
			file = new FileServerFolder();
			((FileServerFolder)file).setName(filename.poll());
		}else{
			FileServerFile fsFile = new FileServerFile();
			fsFile.setName(filename.poll());
			file = fsFile;
		}

		/* create folder */
		base.addChild(file);
		fileServerService.persist(file);
		fileServerService.merge(base);

		if(filename.size() > 0) {
			return createFileAndFolders(file, filename, isDirectory);
		} else {
			return file;
		}
	}
}

