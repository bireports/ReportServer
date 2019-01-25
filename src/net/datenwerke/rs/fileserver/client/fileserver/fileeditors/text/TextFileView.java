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
 
 
package net.datenwerke.rs.fileserver.client.fileserver.fileeditors.text;

import net.datenwerke.gf.client.managerhelper.mainpanel.SimpleFormView;
import net.datenwerke.gxtdto.client.codemirror.CodeMirrorPanel.ToolBarEnhancer;
import net.datenwerke.gxtdto.client.dtomanager.callback.RsAsyncCallback;
import net.datenwerke.gxtdto.client.forms.simpleform.SimpleForm;
import net.datenwerke.gxtdto.client.forms.simpleform.SimpleFormSubmissionCallback;
import net.datenwerke.gxtdto.client.forms.simpleform.providers.configs.SFFCCodeMirror;
import net.datenwerke.gxtdto.client.locale.BaseMessages;
import net.datenwerke.gxtdto.client.model.StringBaseModel;
import net.datenwerke.gxtdto.client.model.pa.StringBaseModelPa;
import net.datenwerke.rs.fileserver.client.fileserver.FileServerDao;
import net.datenwerke.rs.fileserver.client.fileserver.dto.FileServerFileDto;
import net.datenwerke.rs.fileserver.client.fileserver.locale.FileServerMessages;

import com.google.inject.Inject;

public class TextFileView extends SimpleFormView{

	@Inject
	private FileServerDao fileServerDao;
	
	private StringBaseModel bindingEntity = new StringBaseModel();

	@Override
	protected void configureSimpleForm(final SimpleForm form) {
		form.setHeadingText(FileServerMessages.INSTANCE.fileViewHeader() + (getSelectedNode() == null ? "" : " (" + getSelectedNode().getId() + ")"));
		
		final String codeMirrorField = form.addField(String.class, StringBaseModelPa.INSTANCE.value(), new SFFCCodeMirror() {
			
			@Override
			public int getWidth() {
				return -1;
			}
			
			@Override
			public int getHeight() {
				return 400;
			}

			@Override
			public boolean lineNumbersVisible() {
				return true;
			}

			
			@Override
			public String getLanguage() {
				String ct = ((FileServerFileDto)getSelectedNode()).getContentType();
				String name = ((FileServerFileDto)getSelectedNode()).getName();
				name = null == name ? "" : name;
					
				if(null == ct)
					return "none";
				
				if(ct.contains("xml"))
					return "xml";
				
				if(ct.contains("groovy") || name.endsWith(".rs") || name.endsWith(".groovy"))
					return "text/x-groovy";
				
				return ct;
			}

			@Override
			public ToolBarEnhancer getEnhancer() {
				return null;
		 	}
		});
		
		mask(BaseMessages.INSTANCE.loadingMsg());
		fileServerDao.loadFileDataAsString((FileServerFileDto) getSelectedNode(), new RsAsyncCallback<String>(){
			@Override
			public void onSuccess(String result) {
				unmask();
				
				bindingEntity.setValue(result);
				form.setValue(codeMirrorField, result);
			}
			
			@Override
			public void onFailure(Throwable caught) {
				super.onFailure(caught);
				
				unmask();
			}
		});
	}
	
	@Override
	protected void onSubmit(SimpleFormSubmissionCallback callback) {
		mask(BaseMessages.INSTANCE.loadingMsg());
		fileServerDao.updateFile((FileServerFileDto) getSelectedNode(), bindingEntity.getValue(), new RsAsyncCallback<Void>(){
			@Override
			public void onSuccess(Void result) {
				unmask();
			}
			
			@Override
			public void onFailure(Throwable caught) {
				unmask();
			}
		});
	}
	
	
	@Override
	public String getComponentHeader() {
		return FileServerMessages.INSTANCE.fileViewHeader();
	}
	
	@Override
	protected Object getBindingEntity() {
		return bindingEntity;
	}
	
}
