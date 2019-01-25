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
 
 
package net.datenwerke.rs.core.client.reportmanager.ui.forms;

import net.datenwerke.gf.client.managerhelper.mainpanel.SimpleFormView;
import net.datenwerke.gxtdto.client.dtomanager.callback.RsAsyncCallback;
import net.datenwerke.gxtdto.client.forms.simpleform.SimpleForm;
import net.datenwerke.gxtdto.client.forms.simpleform.SimpleFormSubmissionCallback;
import net.datenwerke.gxtdto.client.forms.simpleform.SimpleMultiForm;
import net.datenwerke.gxtdto.client.forms.simpleform.providers.configs.impl.SFFCTextAreaImpl;
import net.datenwerke.gxtdto.client.locale.BaseMessages;
import net.datenwerke.rs.core.client.reportmanager.ReportManagerTreeManagerDao;
import net.datenwerke.rs.core.client.reportmanager.dto.reports.ReportDto;
import net.datenwerke.rs.core.client.reportmanager.dto.reports.pa.ReportDtoPA;
import net.datenwerke.rs.core.client.reportmanager.locale.ReportmanagerMessages;
import net.datenwerke.treedb.client.treedb.dto.AbstractNodeDto;
import net.datenwerke.treedb.client.treedb.dto.decorator.AbstractNodeDtoDec;

import com.google.inject.Inject;

/**
 * 
 *
 */
public class BasicReportVariantForm extends SimpleFormView {

	private final ReportManagerTreeManagerDao dao;

	@Inject
	public BasicReportVariantForm(
		ReportManagerTreeManagerDao dao
		){
		this.dao = dao;
	}

	@Override
	public void configureSimpleForm(SimpleForm form) {
		form.setHeadingText(ReportmanagerMessages.INSTANCE.editReportVariant() + (getSelectedNode() == null ? "" : " (" + getSelectedNode().getId() + ")"));
		
		form.beginRow();
		form.addField(String.class, ReportDtoPA.INSTANCE.name(), BaseMessages.INSTANCE.name()); 
		form.addField(String.class, ReportDtoPA.INSTANCE.key(), ReportmanagerMessages.INSTANCE.key());
		form.endRow();
		
		form.addField(String.class, ReportDtoPA.INSTANCE.description(), BaseMessages.INSTANCE.propertyDescription(), new SFFCTextAreaImpl());
	}
	
	@Override
	protected void callbackAfterBinding(SimpleMultiForm form) {
		final SimpleForm xform = SimpleForm.getNewInstance();
		xform.getButtonBar().clear();
		final String wpKey = xform.addField(Boolean.class, ReportmanagerMessages.INSTANCE.writeProtect());
		
		xform.setValue(wpKey, ((ReportDto)getSelectedNode()).isWriteProtected());
		
		xform.loadFields();
		xform.addSubmissionCallback(new SimpleFormSubmissionCallback(form) {
			@Override
			public void formSubmitted() {
				dao.setFlag(getSelectedNode(), AbstractNodeDtoDec.FLAG_WRITE_PROTECT, (Boolean) xform.getValue(wpKey), new RsAsyncCallback<AbstractNodeDto>());
			}
		});
		form.addSubForm(xform);
	}


}
