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
 
 
package net.datenwerke.rs.core.client.reportmanager.ui.forms;

import java.util.ArrayList;
import java.util.List;

import net.datenwerke.gf.client.managerhelper.mainpanel.FormView;
import net.datenwerke.gxtdto.client.forms.binding.FormBinding;
import net.datenwerke.gxtdto.client.locale.BaseMessages;
import net.datenwerke.rs.core.client.datasourcemanager.DatasourceUIService;
import net.datenwerke.rs.core.client.datasourcemanager.dto.DatasourceContainerProviderDto;
import net.datenwerke.rs.core.client.datasourcemanager.helper.forms.DatasourceSelectionField;
import net.datenwerke.rs.core.client.reportmanager.dto.reports.pa.ReportDtoPA;
import net.datenwerke.rs.core.client.reportmanager.locale.ReportmanagerMessages;
import net.datenwerke.treedb.client.treedb.dto.AbstractNodeDto;

import com.google.gwt.editor.client.Editor;
import com.google.gwt.editor.client.EditorError;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.widget.core.client.container.BoxLayoutContainer.BoxLayoutData;
import com.sencha.gxt.widget.core.client.container.Container;
import com.sencha.gxt.widget.core.client.container.HBoxLayoutContainer;
import com.sencha.gxt.widget.core.client.container.HBoxLayoutContainer.HBoxLayoutAlign;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer.VerticalLayoutData;
import com.sencha.gxt.widget.core.client.form.FieldLabel;
import com.sencha.gxt.widget.core.client.form.FormPanel;
import com.sencha.gxt.widget.core.client.form.TextArea;
import com.sencha.gxt.widget.core.client.form.TextField;
import com.sencha.gxt.widget.core.client.form.Validator;
import com.sencha.gxt.widget.core.client.form.error.DefaultEditorError;

public abstract class AbstractReportForm extends FormView {

	protected final DatasourceUIService datasourceService;
	
	protected TextField nameField;
	protected TextField keyField;
	protected TextArea descriptionField;
	
	protected DatasourceSelectionField datasourceFieldCreator;

	public AbstractReportForm(
		DatasourceUIService datasourceService
		){
		
		/* store objects */
		this.datasourceService = datasourceService;
	}
	
	@Override
	protected void initializeForm(FormPanel form, VerticalLayoutContainer fieldWrapper){
		form.setLabelWidth(120);
		
		HBoxLayoutContainer firstRow = new HBoxLayoutContainer();
		firstRow.setHBoxLayoutAlign(HBoxLayoutAlign.STRETCHMAX);
		fieldWrapper.add(firstRow, new VerticalLayoutData(1, -1));
		
		/* name */
		nameField = new TextField();
		nameField.setWidth(-1);
		BoxLayoutData nameData = new BoxLayoutData(new Margins(0, 5, 0, 0));
		nameData.setFlex(2);
		firstRow.add(new FieldLabel(nameField, BaseMessages.INSTANCE.name()), nameData);
		
		/* key */
		keyField = new TextField();
		keyField.setWidth(-1);
		keyField.addValidator(new Validator<String>() {
			@Override
			public List<EditorError> validate(Editor<String> editor, String value) {
				if(null == value || value.matches("^[a-zA-Z0-9_\\-]*$"))
					return null;
				List<EditorError> errors = new ArrayList<EditorError>();
				errors.add(new DefaultEditorError(editor, BaseMessages.INSTANCE.alphaNumericErrorMsg(), value));
				return errors;
			}
		});
		BoxLayoutData keyData = new BoxLayoutData(new Margins(0, 0, 0, 5));
		keyData.setFlex(1);
		firstRow.add(new FieldLabel(keyField, ReportmanagerMessages.INSTANCE.key()), keyData);
		
		/* description */
		descriptionField = new TextArea();
		descriptionField.setWidth(-1);
		fieldWrapper.add(new FieldLabel(descriptionField,BaseMessages.INSTANCE.propertyDescription()), new VerticalLayoutData(1, 150));
		
		/* datasource */
		addDatasourceField(fieldWrapper, isDisplayConfigFieldsForDatasource());
	}
	
	protected boolean isDisplayConfigFieldsForDatasource() {
		return true;
	}

	@Override
	protected void doInitFormBinding(FormBinding binding, AbstractNodeDto model) {
		datasourceFieldCreator.initFormBinding((DatasourceContainerProviderDto) model);
		
		binding.addHtmlSafeStringBinding(nameField, model, ReportDtoPA.INSTANCE.name());
		binding.addHtmlSafeStringBinding(keyField, model, ReportDtoPA.INSTANCE.key());
		binding.addHtmlSafeStringBinding(descriptionField, model, ReportDtoPA.INSTANCE.description());
	}

	protected void addDatasourceField(Container container, boolean displayConfigFields) {
		datasourceFieldCreator = datasourceService.getSelectionField(container, displayConfigFields);
		datasourceFieldCreator.addSelectionField();
		datasourceFieldCreator.addDisplayDefaultButton();
	}

}
