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
 
 
package net.datenwerke.gf.service.tempfile;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import javax.inject.Provider;
import javax.inject.Singleton;

import org.apache.commons.io.FileUtils;

import net.datenwerke.gf.service.tempfile.annotations.TempDirLocation;
import net.datenwerke.security.service.usermanager.entities.User;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;

@Singleton
public class TempFileService {

	private final Logger logger = LoggerFactory.getLogger(getClass().getName());
	
	private Provider<String> tempDir;
	private Map<String, TempFile> webAccessibleTempFiles = Collections.synchronizedMap(new HashMap<String, TempFile>());
	
	private Random random = new Random();
	
	@Inject
	public TempFileService(
		@TempDirLocation Provider<String> tempDir
			){
		this.tempDir = tempDir;
		
	}
	
	public synchronized File createTempFile() throws IOException{
		File dir = new File(tempDir.get());
		if(! dir.exists() && ! dir.isDirectory()){
			logger.warn( "cannot access tempdir: " + tempDir);
			throw new IllegalStateException("cannot access tempdir: " + tempDir);
		}
		
		File file = null;
		do {
			String name = "rs-tmp-" + Calendar.getInstance().getTimeInMillis() + "-" + String.valueOf(random.nextInt(Integer.MAX_VALUE)) + ".tmp";
			file = new File(tempDir.get() + File.separator + name);
		} while (file.exists());
		
		file.createNewFile();
		return file;
	}
	
	public TempFile createWebAccessibleTempFile(String id, User... permittedUsers) throws IOException {
		TempFile tmpFile =  new TempFile(createTempFile(), id, Arrays.asList(permittedUsers));
		webAccessibleTempFiles.put(tmpFile.getWebId(), tmpFile);
		
		return tmpFile;
	}
	
	/**
	 * Creates a temporary file which can be retrieved by the returned 
	 * identifier using the TempFileServlet
	 * 
	 * @param permittedUsers
	 * @return
	 * @throws IOException 
	 */
	public TempFile createWebAccessibleTempFile(User... permittedUsers) throws IOException{
		return createWebAccessibleTempFile(UUID.randomUUID().toString(), permittedUsers);
	}
	
	public TempFile getTempFileById(String id){
		TempFile tempFile = webAccessibleTempFiles.get(id);
		
		if(null == tempFile)
			return null;
		
		tempFile.setLastAccessed();
		
		return tempFile;
	}

	public byte[] readTmpFileIntoByteArray(File location) throws IOException {
		return readTmpFileIntoByteArray(location, true);
	}

	public byte[] readTmpFileIntoByteArray(File location, boolean removeFile) throws IOException {
		/* ensure file is in right location */
		if(! isTmpFile(location))
			throw new IllegalArgumentException("Tried to read file: " + location);
			
		byte[] fileContents = FileUtils.readFileToByteArray(location);
		
		/* remove? */
		if(removeFile)
			location.delete();
		
		return fileContents;
	}

	public boolean isTmpFile(File file) {
		File dir = new File(tempDir.get());
		if(! dir.exists() && ! dir.isDirectory()){
			logger.warn("cannot access tempdir: " + tempDir);
			throw new IllegalStateException("cannot access tempdir: " + tempDir);
		}
		
		return isInDir(file, dir);
	}

	protected boolean isInDir(File file, File dir) {
		if(null == file)
			return false;
		
		if(file.equals(dir))
			return true;
		
		return isInDir(file.getParentFile(), dir);
	}
	
}
