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
 
 
package net.datenwerke.rs.fileserver.client.fileserver.ui.forms;

import java.util.List;

import com.google.inject.Inject;
import com.sencha.gxt.widget.core.client.Dialog.PredefinedButton;
import com.sencha.gxt.widget.core.client.box.MessageBox;
import com.sencha.gxt.widget.core.client.event.DialogHideEvent;
import com.sencha.gxt.widget.core.client.event.DialogHideEvent.DialogHideHandler;

import net.datenwerke.gf.client.managerhelper.mainpanel.SimpleFormView;
import net.datenwerke.gf.client.upload.dto.FileToUpload;
import net.datenwerke.gf.client.upload.simpleform.FileUpload;
import net.datenwerke.gf.client.upload.simpleform.SFFCDnDFileUpload;
import net.datenwerke.gxtdto.client.baseex.widget.mb.DwMessageBox;
import net.datenwerke.gxtdto.client.dtomanager.callback.RsAsyncCallback;
import net.datenwerke.gxtdto.client.forms.simpleform.SimpleForm;
import net.datenwerke.gxtdto.client.forms.simpleform.providers.configs.impl.SFFCTextAreaImpl;
import net.datenwerke.gxtdto.client.locale.BaseMessages;
import net.datenwerke.rs.fileserver.client.fileserver.FileServerDao;
import net.datenwerke.rs.fileserver.client.fileserver.dto.AbstractFileServerNodeDto;
import net.datenwerke.rs.fileserver.client.fileserver.dto.FileServerFileDto;
import net.datenwerke.rs.fileserver.client.fileserver.dto.FileServerFolderDto;
import net.datenwerke.rs.fileserver.client.fileserver.dto.pa.FileServerFolderDtoPA;
import net.datenwerke.rs.fileserver.client.fileserver.locale.FileServerMessages;

/**
 * 
 *
 */
public class FolderForm extends SimpleFormView {

	private final FileServerDao fileServerDao;

	@Inject
	public FolderForm(FileServerDao fileServerDao) {
		this.fileServerDao = fileServerDao;
	}

	@Override
	public void configureSimpleForm(SimpleForm form) {
		form.setHeadingText(FileServerMessages.INSTANCE.editFolder() + (getSelectedNode() == null ? "" : " (" + getSelectedNode().getId() + ")"));

		form.addField(String.class, FileServerFolderDtoPA.INSTANCE.name(), BaseMessages.INSTANCE.propertyName()); 

		form.addField(String.class, FileServerFolderDtoPA.INSTANCE.description(), BaseMessages.INSTANCE.propertyDescription(), new SFFCTextAreaImpl());

		form.addField(Boolean.class, FileServerFolderDtoPA.INSTANCE.publiclyAccessible(), FileServerMessages.INSTANCE.publiclyAccessibleLabel());

		form.addField(FileUpload.class, FileServerMessages.INSTANCE.fileUploadLabel(), new SFFCDnDFileUpload() {
			@Override
			public void filesUploaded(final List<FileToUpload> files) {
				if(null != getSelectedNode() && getSelectedNode() instanceof FileServerFolderDto){
					if(null != files && files.size() == 1  ){
						String name = files.get(0).getName();
						if(null != name && name.endsWith(".zip")){
							MessageBox mb = new DwMessageBox(FileServerMessages.INSTANCE.zipUploadedTitle(), FileServerMessages.INSTANCE.zipUploadedMsg());
							mb.setPredefinedButtons(PredefinedButton.NO, PredefinedButton.YES);
							mb.addDialogHideHandler(new DialogHideHandler() {
								@Override
								public void onDialogHide(DialogHideEvent event) {
									switch (event.getHideButton()) {
									case YES:
										uploadAndExtract(files.get(0));
										break;
									default:
										upload(files);
									}
								}
							});
							mb.show();
						} else
							upload(files);
					} else
						upload(files);
				}
			}

			private void upload(List<FileToUpload> files) {
				mask(BaseMessages.INSTANCE.storingMsg());
				fileServerDao.uploadFiles((FileServerFolderDto) getSelectedNode(), files, new RsAsyncCallback<List<FileServerFileDto>>(){
					@Override
					public void onSuccess(List<FileServerFileDto> result) {
						super.onSuccess(result);
						unmask();
					}
					@Override
					public void onFailure(Throwable caught) {
						super.onFailure(caught);
						unmask();
					}
				});				
			}
			
			private void uploadAndExtract(FileToUpload fileToUpload) {
				mask(BaseMessages.INSTANCE.storingMsg());
				fileServerDao.uploadAndExtract((FileServerFolderDto) getSelectedNode(), fileToUpload, new RsAsyncCallback<List<AbstractFileServerNodeDto>>(){
					@Override
					public void onSuccess(List<AbstractFileServerNodeDto> result) {
						super.onSuccess(result);
						unmask();
					}
					@Override
					public void onFailure(Throwable caught) {
						super.onFailure(caught);
						unmask();
					}
				});				
			}
		});
	}



}