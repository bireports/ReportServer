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
 
 
package net.datenwerke.rs.fileserver.client.fileserver.helper.fileselection;

import java.util.List;

import net.datenwerke.gf.client.download.dto.DownloadProperties;
import net.datenwerke.gf.client.fileselection.FileSelectionWidget;
import net.datenwerke.gf.client.fileselection.FileSelectorSourceImpl;
import net.datenwerke.gf.client.fileselection.dto.SelectedFileWrapper;
import net.datenwerke.gf.client.treedb.UITree;
import net.datenwerke.gf.client.treedb.selection.SelectionFilter;
import net.datenwerke.gf.client.treedb.selection.TreeSelectionPopup;
import net.datenwerke.gxtdto.client.baseex.widget.btn.DwTextButton;
import net.datenwerke.gxtdto.client.forms.selection.SelectionMode;
import net.datenwerke.rs.fileserver.client.fileserver.dto.AbstractFileServerNodeDto;
import net.datenwerke.rs.fileserver.client.fileserver.dto.FileServerFileDto;
import net.datenwerke.rs.fileserver.client.fileserver.dto.FileServerFolderDto;
import net.datenwerke.rs.fileserver.client.fileserver.locale.FileServerMessages;
import net.datenwerke.rs.fileserver.client.fileserver.provider.annotations.FileServerTreeBasic;
import net.datenwerke.rs.theme.client.icon.BaseIcon;
import net.datenwerke.treedb.client.treedb.dto.AbstractNodeDto;

import com.google.gwt.resources.client.ImageResource;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.grid.Grid;
import com.sencha.gxt.widget.core.client.toolbar.ToolBar;

public class FileServerFileSelectorSource extends FileSelectorSourceImpl {

	public static final String FILESERVER_FILE_TYPE = "fileserver_file_type";
	
	private boolean enableNameEditing = true;
	private boolean enableDownload = true;
	private SelectionFilter selectionFilter = SelectionFilter.DUMMY_FILTER;

	 
	private final Provider<UITree> fileTreeProvider;

	private UITree tree;	
	
	
	@Inject
	public FileServerFileSelectorSource(
		@FileServerTreeBasic Provider<UITree> fileTreeProvider
		) {
		this.fileTreeProvider = fileTreeProvider;
	}
	
	@Override
	public void configureToolbar(FileSelectionWidget fileSelectionWidget,
			ToolBar toolbar) {
		DwTextButton btn = new DwTextButton(FileServerMessages.INSTANCE.selectFileFromFileServerText(), BaseIcon.FLOPPY_O);
		toolbar.add(btn);
		btn.addSelectHandler(new SelectHandler() {
			@Override
			public void onSelect(SelectEvent event) {
				displayTeamSpaceFileSelection();
			}
		});
	}

	@Override
	public void configureGrid(FileSelectionWidget fileSelectionWidget,
			Grid<SelectedFileWrapper> grid) {
	}

	@Override
	public boolean consumes(SelectedFileWrapper value) {
		return null != value && value.getOriginalDto() instanceof AbstractFileServerNodeDto;
	}

	@Override
	public ImageResource getIconFor(SelectedFileWrapper value) {
		return BaseIcon.FILE.toImageResource();
	}

	@Override
	public boolean isEditNameEnabled(SelectedFileWrapper selectedItem) {
		return false;
	}

	@Override
	public boolean isDownloadEnabled(SelectedFileWrapper item) {
		return false;
	}

	@Override
	public DownloadProperties getDownloadPropertiesFor(
			SelectedFileWrapper selectedItem) {
		return null;
	}

	@Override
	public String getTypeDescription(SelectedFileWrapper value) {
		if(null != value.getOriginalDto()){
			if(value.getOriginalDto() instanceof FileServerFolderDto)
				return FileServerMessages.INSTANCE.folderDescription();
			if(value.getOriginalDto() instanceof FileServerFileDto)
				return FileServerMessages.INSTANCE.fileDescription();
		}
		return "";
	}

	public boolean isEnableNameEditing() {
		return enableNameEditing;
	}

	public void setEnableNameEditing(boolean enableNameEditing) {
		this.enableNameEditing = enableNameEditing;
	}

	public boolean isEnableDownload() {
		return enableDownload;
	}

	public void setEnableDownload(boolean enableDownload) {
		this.enableDownload = enableDownload;
	}

	protected void displayTeamSpaceFileSelection() {
		if(fileSelectionWidget.getRemainingFileSpace() < 1){
			fileSelectionWidget.displayMaxFilesReachedMessage();
			return;
		}
		
		if(null == tree)
			tree = fileTreeProvider.get();
		
		TreeSelectionPopup popup = new TreeSelectionPopup(tree){
			@Override
			protected void itemsSelected(List<AbstractNodeDto> selectedItems) {
				for(AbstractNodeDto item : selectedItems){
					FileServerFileSelectorSource.this.add((AbstractFileServerNodeDto)item);
				}
			}
			@Override
			protected boolean validateSelectedItems(
					List<AbstractNodeDto> selectedItems) {
				if(null == selectedItems || selectedItems.isEmpty())
					return true;
				if(fileSelectionWidget.getRemainingFileSpace()<selectedItems.size()){
					fileSelectionWidget.displayMaxFilesReachedMessage();
					return false;
				}
				return true;
			}
		};
		popup.setSelectionFilter(selectionFilter);
		popup.setSelectionMode(SelectionMode.MULTI);
		popup.show();
	}

	protected void add(AbstractFileServerNodeDto file) {
		SelectedFileWrapper sfw = new SelectedFileWrapper();
		sfw.setId(String.valueOf(file.getId()));
		sfw.setName(file.toDisplayTitle());
		sfw.setType(FILESERVER_FILE_TYPE);
		sfw.setOriginalDto(file);
		
		fileSelectionWidget.add(sfw);
	}
	
	public SelectionFilter getSelectionFilter() {
		return selectionFilter;
	}
	
	public void setSelectionFilter(SelectionFilter selectionFilter) {
		this.selectionFilter = selectionFilter;
	}
	
}
