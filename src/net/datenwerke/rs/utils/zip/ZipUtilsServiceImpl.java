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

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import org.apache.commons.io.IOUtils;

import net.datenwerke.rs.fileserver.service.fileserver.entities.AbstractFileServerNode;
import net.datenwerke.rs.fileserver.service.fileserver.entities.FileServerFile;
import net.datenwerke.rs.fileserver.service.fileserver.entities.FileServerFolder;

/**
 * 
 *
 */
public class ZipUtilsServiceImpl implements ZipUtilsService{

	final int BUFFER = 2048;
	
	@Override
	public void createZip(byte[] content, OutputStream os) throws IOException {
		ZipOutputStream out = new ZipOutputStream(os); 
		
		out.putNextEntry(new ZipEntry("data"));
		
		ByteArrayInputStream in = new ByteArrayInputStream(content);
		if(null != in)
			IOUtils.copy(in, out);
		
		out.closeEntry(); 
		
		out.close();
	}
	
	@Override
	public void createZip(Map<String, ? extends Object> content, OutputStream os) throws IOException {
		ZipOutputStream out = new ZipOutputStream(os); 
		
		for (String name : content.keySet()) {
			if(DIRECTORY_MARKER == (content.get(name))){
				out.putNextEntry(new ZipEntry(name.endsWith("/") ? name : name + "/"));
			}else{
				out.putNextEntry(new ZipEntry(name));
			}
			
			ByteArrayInputStream in = null;
			if(content.get(name) instanceof String){
				String text = (String) content.get(name);
				in = new ByteArrayInputStream(text.getBytes());
			}else if(content.get(name) instanceof byte[]){
				in = new ByteArrayInputStream((byte[]) content.get(name));
			}
			
			if(null != in){
				IOUtils.copy(in, out);
			}
			
			out.closeEntry(); 
		}
		
		out.close();
	}
	
	public void zipDirectory(File dir, OutputStream os) throws IOException{
		ZipOutputStream out = new ZipOutputStream(os); 
		
		try{
			for(File f : getFilesRec(dir)){
				
				if(f.isDirectory()){
					String name = f.getPath().replace(dir.getPath(), "").replace("\\", "/").substring(1);
					name = name.endsWith("/") ? name : name + "/";
					out.putNextEntry(new ZipEntry(name));
					continue;
				}
				
				out.putNextEntry(new ZipEntry(f.getPath().replace(dir.getPath(), "").replace("\\", "/").substring(1)));
				
				InputStream in = new FileInputStream(f);
				byte[] buff = new byte[1024];
				int read = 0;
				while((read = in.read(buff)) > 0){
					out.write(buff, 0, read);
				}
				in.close();
			}
		} finally {
			out.finish();
			out.close();	
		}
	}
	
	private List<File> getFilesRec(File path){
		List<File> files = new ArrayList<File>();
		for(File f : path.listFiles()) {
			if(f.isDirectory())	{
				files.add(f);
				files.addAll(getFilesRec(f));
			}else{
				files.add(f);
			}
		}
		return files;
	}

	@Override
	public void extractZip(byte[] data, ZipExtractionConfig config) throws IOException{
		extractZip(new ByteArrayInputStream(data), config);
	}
	
	@Override
	public void extractZip(InputStream is, ZipExtractionConfig config) throws IOException{
		ZipInputStream zin = new ZipInputStream(new BufferedInputStream(is));
		ZipEntry entry;
		while((entry = zin.getNextEntry()) != null) {
			if(! config.isAllowedFile(entry))
				continue;
			
			int count;
            byte data[] = new byte[BUFFER];

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            
            while ((count = zin.read(data, 0, BUFFER)) != -1) {
            	out.write(data, 0, count);
            }
            
            config.processContent(entry, out.toByteArray());
		}
	}
	
	@Override
	public void createZip(FileServerFolder folder, OutputStream os) throws IOException {
		createZip(folder, os, new FileFilter() {
			@Override
			public boolean addNode(AbstractFileServerNode node) {
				return false;
			}
		});
	}
	
	@Override
	public void createZip(FileServerFolder folder, OutputStream os, FileFilter filter) throws IOException {
		HashMap<String, Object> zipObjects = new HashMap<String, Object>(); 
		addFolder(folder, folder, zipObjects, filter);
		createZip(zipObjects, os);
	}
	
	private void addFile(FileServerFile file, FileServerFolder baseFolder, Map<String, Object> zipObjects, FileFilter filter){
		if(filter.addNode(file))
			zipObjects.put(getRealtivePath(file, baseFolder), file.getData());
	}

	private void addFolder(FileServerFolder folder, FileServerFolder baseFolder, Map<String, Object> zipObjects, FileFilter filter){
		if(! filter.addNode(folder))
			return;
		
		zipObjects.put(getRealtivePath(folder, baseFolder), ZipUtilsService.DIRECTORY_MARKER);
		for(AbstractFileServerNode f : folder.getChildren()){
			if(f instanceof FileServerFolder){
				addFolder((FileServerFolder) f, baseFolder, zipObjects, filter);
			}else if(f instanceof FileServerFile){
				addFile((FileServerFile) f, baseFolder, zipObjects, filter);
			}
		}

	}

	private String getRealtivePath(AbstractFileServerNode node, AbstractFileServerNode root){
		if(node == root){
			return "";
		}else{
			String name = "";
			if(node instanceof FileServerFile)
				name = ((FileServerFile) node).getName();
			if(node instanceof FileServerFolder)
				name = ((FileServerFolder) node).getName();

			if(null != node.getParent()){
				String rp = getRealtivePath(node.getParent(), root);
				return ("".equals(rp)?name: rp + "/" + name);
			}else{
				return name;

			}
		}
	}
}
