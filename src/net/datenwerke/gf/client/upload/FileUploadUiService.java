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
 
 
package net.datenwerke.gf.client.upload;

import net.datenwerke.gf.client.upload.dto.FileToUpload;
import net.datenwerke.gf.client.upload.dto.UploadProperties;
import net.datenwerke.gf.client.upload.dto.UploadResponse;
import net.datenwerke.gxtdto.client.forms.simpleform.SimpleForm;

import com.google.gwt.http.client.Request;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.sencha.gxt.widget.core.client.Component;
import com.sencha.gxt.widget.core.client.container.Container;
import com.sencha.gxt.widget.core.client.form.FileUploadField;
import com.sencha.gxt.widget.core.client.form.FormPanel;

public interface FileUploadUiService {

	public interface UploadHandler {
		void onSuccess(JSONValue json);
		
		void onError();
	}
	
	FileUploadField addBaseUploadField(UploadProperties uploadProperties,
			Container fieldWrapper);

	String getFormAction();

	void handleUploadReturn(FormPanel form);
	
	void handleUploadReturn(FormPanel form, UploadHandler handler);
	
	void handleUploadReturn(SimpleForm form);
	
	void handleUploadReturn(SimpleForm form, UploadHandler handler);

	void prepareForUpload(SimpleForm form);
	
	void prepareForUpload(FormPanel form);

	void attachHtmlUploadTo(Component component,
			UploadProperties uploadProperties);
	
	Component addCombinedUploadField(UploadProperties properties);

	Request uploadInterimFile(FileToUpload uploadedFile,
			AsyncCallback<UploadResponse> rsAsyncCallback);

	String getValueOf(Component component);

	long getSize(FileUploadField field);


}
