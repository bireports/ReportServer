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
 
 
package net.datenwerke.rs.incubator.service.misc.terminal.exportcontentprovider;

import net.datenwerke.rs.terminal.service.terminal.vfs.VFSLocation;
import net.datenwerke.rs.terminal.service.terminal.vfs.VFSLocationInfo;
import net.datenwerke.rs.terminal.service.terminal.vfs.VFSObjectInfo;
import net.datenwerke.rs.terminal.service.terminal.vfs.exceptions.VFSException;
import net.datenwerke.rs.terminal.service.terminal.vfs.hooks.TreeBasedVirtualFileSystem;
import net.datenwerke.rs.terminal.service.terminal.vfs.hooks.VirtualContentProviderImpl;
import net.datenwerke.security.service.security.SecurityService;
import net.datenwerke.treedb.ext.service.eximport.helper.TreeNodeExportHelperServiceImpl;
import net.datenwerke.treedb.service.treedb.AbstractNode;

import com.google.inject.Inject;

public class TerminalExportContentProvider extends
		VirtualContentProviderImpl {

	public static final String VIRTUAL_NAME = "export";
	public static final String VIRTUAL_FILE_NAME = "export.xml";

	private final TreeNodeExportHelperServiceImpl exportService;
	
	@Inject
	public TerminalExportContentProvider(
		TreeNodeExportHelperServiceImpl exportService,
		SecurityService securityService
		) {
		super(securityService);
		
		this.exportService = exportService;
	}

	@Override
	public String getName() {
		return VIRTUAL_NAME;
	}

	@Override
	protected void addVirtualChildInfos(VFSLocationInfo info) {
		info.addChildInfo(new VFSObjectInfo(getClass(), VIRTUAL_FILE_NAME, VIRTUAL_FILE_NAME, false));
	}

	@Override
	protected boolean doHasContent(VFSLocation vfsLocation) {
		return true;
	}

	@Override
	protected byte[] doGetContent(VFSLocation vfsLocation) throws VFSException {
		VFSLocation parent = vfsLocation.getVirtualParentLocation();
		
		AbstractNode node = (AbstractNode) parent.getObject();
		
		return exportService.export(node, false, "export").getBytes();
	}

	@Override
	protected void doSetContent(VFSLocation vfsLocation, byte[] content) {
	}

	@Override
	protected String doGetContentType(VFSLocation vfsLocation) {
		return "application/xml";
	}

	@Override
	public boolean enhanceNonVirtual(VFSLocation location) {
		return null != location.getFilesystemManager() && location.getFilesystemManager() instanceof TreeBasedVirtualFileSystem;
	}


}
