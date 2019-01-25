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
 
 
package net.datenwerke.gxtdto.client.forms.simpleform.providers;

import net.datenwerke.gxtdto.client.codemirror.CodeMirrorConfig;
import net.datenwerke.gxtdto.client.codemirror.CodeMirrorPanel;
import net.datenwerke.gxtdto.client.forms.binding.HasValueFieldBinding;
import net.datenwerke.gxtdto.client.forms.simpleform.SimpleFormFieldConfiguration;
import net.datenwerke.gxtdto.client.forms.simpleform.hooks.FormFieldProviderHookImpl;
import net.datenwerke.gxtdto.client.forms.simpleform.json.SimpleFormFieldJson;
import net.datenwerke.gxtdto.client.forms.simpleform.providers.configs.SFFCAllowBlank;
import net.datenwerke.gxtdto.client.forms.simpleform.providers.configs.SFFCCodeMirror;
import net.datenwerke.gxtdto.client.forms.simpleform.providers.configs.SFFCNoHtmlDecode;
import net.datenwerke.gxtdto.client.forms.simpleform.providers.configs.SFFCPasswordField;
import net.datenwerke.gxtdto.client.forms.simpleform.providers.configs.SFFCReadOnly;
import net.datenwerke.gxtdto.client.forms.simpleform.providers.configs.SFFCRichTextEditor;
import net.datenwerke.gxtdto.client.forms.simpleform.providers.configs.SFFCStringMaxLength;
import net.datenwerke.gxtdto.client.forms.simpleform.providers.configs.SFFCStringNoHtmlDecode;
import net.datenwerke.gxtdto.client.forms.simpleform.providers.configs.SFFCStringValidator;
import net.datenwerke.gxtdto.client.forms.simpleform.providers.configs.SFFCTextArea;
import net.datenwerke.gxtdto.client.utils.StringEscapeUtils;

import com.google.gwt.event.logical.shared.HasValueChangeHandlers;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.core.client.GXT;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.widget.core.client.Component;
import com.sencha.gxt.widget.core.client.form.Field;
import com.sencha.gxt.widget.core.client.form.HtmlEditor;
import com.sencha.gxt.widget.core.client.form.PasswordField;
import com.sencha.gxt.widget.core.client.form.TextArea;
import com.sencha.gxt.widget.core.client.form.TextField;
import com.sencha.gxt.widget.core.client.form.ValueBaseField;
import com.sencha.gxt.widget.core.client.form.validator.MaxLengthValidator;

/**
 * 
 *
 */
public class StringProvider extends FormFieldProviderHookImpl {

	@Override
	public boolean doConsumes(Class<?> type, SimpleFormFieldConfiguration... configs) {
		return type.equals(String.class);
	}
	
	@Override
	public boolean doConsumes(String type, SimpleFormFieldJson config) {
		return type.equals("string");
	}

	@Override
	public Widget createFormField() {
		if(json)
			return createFormFieldFromJson();
		
		SFFCTextArea textAreaConfig = getTextareaConfig();
		
		Widget field = null;
		if(null != textAreaConfig && textAreaConfig.getHeight() > 1){
			SFFCCodeMirror codeMirrorConfig = GXT.isIE() ? null : getCodeMirrorConfig();
			if(null != codeMirrorConfig){
				field = new CodeMirrorPanel(new CodeMirrorConfig(codeMirrorConfig.getLanguage(), codeMirrorConfig.lineNumbersVisible()), codeMirrorConfig.getEnhancer());
			} else if(null != getRichTextEditorConfig()){
				field = new HtmlEditor();		
				((HtmlEditor)field).setEnableFont(false);
			} else  {
				field = new TextArea();
			}
			
			((Component)field).setWidth(textAreaConfig.getWidth());
			((Component)field).setHeight(textAreaConfig.getHeight());
		} else {
			if(null != getPasswordConfig() ){
				field = new PasswordField();
				SFFCPasswordField passwordConfig = getPasswordConfig();
				if(Boolean.TRUE.equals(passwordConfig.isPasswordSet())){
					((PasswordField)field).setEmptyText("****");
				}
			} else 
				field = new TextField();
			((ValueBaseField)field).setWidth(null);
			if(null != textAreaConfig)
				((ValueBaseField)field).setWidth(textAreaConfig.getWidth());
		}
		
		if(field instanceof ValueBaseField){
			/* validator */
			SFFCStringValidator validatorConfig = getStringValidatorConfig();
			if(null != validatorConfig)
				((Field)field).addValidator(validatorConfig.getValidator());
			
			/* max length */
			SFFCStringMaxLength maxLengthConfig = getMaxLengthConfig();
			if(null != maxLengthConfig)
				((Field)field).addValidator(new MaxLengthValidator(maxLengthConfig.maxLength()));
			
			/* blank */
			SFFCAllowBlank allowBlank = getAllowBlankConfig();
			if(null != allowBlank)
				((ValueBaseField)field).setAllowBlank(allowBlank.allowBlank());
			
			/* readOnly */
			SFFCReadOnly readOnly = getReadOnlyConfig();
			if(null != readOnly)
				((ValueBaseField)field).setReadOnly(readOnly.isReadOnly());
		}
		
		/* add listener for change events */
		addChangeListener(field);
		
		return field;
	}
	
	protected Widget createFormFieldFromJson() {
		TextField field = new TextField();
		addChangeListener(field);
		return field;
	}

	
	protected void addChangeListener(Widget field) {
		if(field instanceof HasValueChangeHandlers){
			((HasValueChangeHandlers)field).addValueChangeHandler(new ValueChangeHandler() {
				@Override
				public void onValueChange(ValueChangeEvent event) {
					ValueChangeEvent.fire(StringProvider.this, event.getValue());
				}
			});
		}
	}


	@Override
	public void addFieldBindings(final Object model, final ValueProvider vp, Widget field) {
		if(field instanceof HasValueChangeHandlers){
			fieldBinding = new HasValueFieldBinding((HasValueChangeHandlers) field, model, vp){
				@Override
				protected Object convertModelValue(Object value) {
					if(isDecodeHtml() && value instanceof String)
						return StringEscapeUtils.unescapeHTML((String) value);
					return value;
				}
			};
		}
	}

	protected boolean isDecodeHtml(){
		for(SimpleFormFieldConfiguration config : configs)
			if(config instanceof SFFCNoHtmlDecode)
				return false;
		return true;
	}
	
	protected SFFCCodeMirror getCodeMirrorConfig(){
		for(SimpleFormFieldConfiguration config : configs)
			if(config instanceof SFFCCodeMirror)
				return (SFFCCodeMirror) config;
		return null;
	}
	
	protected SFFCRichTextEditor getRichTextEditorConfig(){
		for(SimpleFormFieldConfiguration config : configs)
			if(config instanceof SFFCRichTextEditor)
				return (SFFCRichTextEditor) config;
		return null;
	}
	
	protected boolean decodeHtml(){
		for(SimpleFormFieldConfiguration config : configs)
			if(config instanceof SFFCStringNoHtmlDecode)
				return false;
		return true;
	}
	
	
	protected SFFCStringValidator getStringValidatorConfig(){
		for(SimpleFormFieldConfiguration config : configs)
			if(config instanceof SFFCStringValidator)
				return (SFFCStringValidator) config;
		return null;
	}
	
	protected SFFCPasswordField getPasswordConfig(){
		for(SimpleFormFieldConfiguration config : configs)
			if(config instanceof SFFCPasswordField)
				return (SFFCPasswordField) config;
		return null;
	}
	
	protected SFFCTextArea getTextareaConfig(){
		for(SimpleFormFieldConfiguration config : configs)
			if(config instanceof SFFCTextArea)
				return (SFFCTextArea) config;
		return null;
	}
	
	protected SFFCStringMaxLength getMaxLengthConfig(){
		for(SimpleFormFieldConfiguration config : configs)
			if(config instanceof SFFCStringMaxLength)
				return (SFFCStringMaxLength) config;
		return null;
	}
	
	protected SFFCAllowBlank getAllowBlankConfig(){
		for(SimpleFormFieldConfiguration config : configs)
			if(config instanceof SFFCAllowBlank)
				return (SFFCAllowBlank) config;
		return null;
	}
	
	protected SFFCReadOnly getReadOnlyConfig(){
		for(SimpleFormFieldConfiguration config : configs)
			if(config instanceof SFFCReadOnly)
				return (SFFCReadOnly) config;
		return null;
	}

	@Override
	public Object getValue(Widget field) {
		if(field instanceof HtmlEditor){
			/* HtmlEditor does not implement HasValue */
			HtmlEditor editor = (HtmlEditor) field;
			return editor.getValue();
		}else{
			return super.getValue(field);
		}
	}
	
	@Override
	public void setValue(Widget field, Object object) {
		if(field instanceof HtmlEditor){
			/* HtmlEditor does not implement HasValue */
			HtmlEditor editor = (HtmlEditor) field;
			editor.setValue((String)object);
		}else
			super.setValue(field, object);
	}
	
	@Override
	protected void setStringValue(Widget field, String value) {
		((HasValue<String>) field).setValue(value);
	}

}
