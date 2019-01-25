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

import java.util.Date;

import net.datenwerke.gxtdto.client.baseex.widget.layout.DwFlowContainer;
import net.datenwerke.gxtdto.client.baseex.widget.menu.DwMenu;
import net.datenwerke.gxtdto.client.baseex.widget.menu.DwMenuItem;
import net.datenwerke.gxtdto.client.forms.binding.HasValueFieldBinding;
import net.datenwerke.gxtdto.client.forms.simpleform.SimpleFormFieldConfiguration;
import net.datenwerke.gxtdto.client.forms.simpleform.hooks.FormFieldProviderHookImpl;
import net.datenwerke.gxtdto.client.forms.simpleform.providers.configs.SFFCDate;
import net.datenwerke.gxtdto.client.forms.simpleform.providers.configs.SFFCDate.Mode;
import net.datenwerke.gxtdto.client.locale.BaseMessages;
import net.datenwerke.gxtdto.client.utilityservices.UtilsUIService;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.DateTimeFormat.PredefinedFormat;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.sencha.gxt.cell.core.client.form.ComboBoxCell.TriggerAction;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.core.client.util.DateWrapper;
import com.sencha.gxt.widget.core.client.container.Container;
import com.sencha.gxt.widget.core.client.form.DateField;
import com.sencha.gxt.widget.core.client.form.DateTimePropertyEditor;
import com.sencha.gxt.widget.core.client.form.Field;
import com.sencha.gxt.widget.core.client.form.TimeField;
import com.sencha.gxt.widget.core.client.menu.Item;
import com.sencha.gxt.widget.core.client.menu.Menu;
import com.sencha.gxt.widget.core.client.menu.MenuItem;

/**
 * 
 *
 */
public class DateProvider extends FormFieldProviderHookImpl {

	private final UtilsUIService utils;
	
	protected HasValueFieldBinding fieldBinding;
	protected HasValueFieldBinding fieldBindingTime;
	protected HasValueFieldBinding fieldBindingDate;
	
	@Inject
	public DateProvider(
		UtilsUIService utils 
		){
		super();
	
		/* store objects */
		this.utils = utils;
	}
	
	@Override
	public boolean doConsumes(Class<?> type, SimpleFormFieldConfiguration... configs) {
		return Date.class.equals(type);
	}
	
	protected void initContextMenu(final Field field) {
		Menu menu = new DwMenu();
		
		MenuItem delete = new DwMenuItem(BaseMessages.INSTANCE.remove());
		menu.add(delete);
		
		delete.addSelectionHandler(new SelectionHandler<Item>() {
			@Override
			public void onSelection(SelectionEvent<Item> event) {
				field.setValue(null, true);
			}
		});
		field.setContextMenu(menu);
	}

	public Widget createFormField() {
		SFFCDate dateConfig = getDateConfig();
		
		if(null == dateConfig)
			return createDateFormField(DateTimeFormat.getFormat(PredefinedFormat.DATE_SHORT).getPattern());
		else if(dateConfig.getMode().equals(Mode.Date))
			return createDateFormField(dateConfig.getDatePattern());
		else if(dateConfig.getMode().equals(Mode.DateTime))
			return createDateTimeFormField(dateConfig.getDatePattern(), dateConfig.getTimePattern());
		else
			return createTimeFormField(dateConfig.getTimePattern());
	}
	
	private Widget createDateTimeFormField(String datePattern, String timePattern) {
		if(null == datePattern)
			datePattern = DateTimeFormat.getFormat(DateTimeFormat.PredefinedFormat.DATE_SHORT).getPattern();
		
		if(null == timePattern)
			timePattern = DateTimeFormat.getFormat(DateTimeFormat.PredefinedFormat.TIME_SHORT).getPattern();
		
		DwFlowContainer flowWrap = new DwFlowContainer();
		
		flowWrap.add(createDateFormField(datePattern));
		flowWrap.add(createTimeFormField(timePattern));
		
		return flowWrap;
	}

	private Field createTimeFormField(String pattern) {
		if(null == pattern)
			pattern = DateTimeFormat.getFormat(DateTimeFormat.PredefinedFormat.TIME_SHORT).getPattern();
		
		final TimeField field = new TimeField();
		field.setFormat(DateTimeFormat.getFormat(pattern)); 
		field.setTriggerAction(TriggerAction.ALL);
		field.setName(name);
		initContextMenu(field);
		
		field.addChangeHandler(new ChangeHandler() {
			@Override
			public void onChange(ChangeEvent event) {
				ValueChangeEvent.fire(DateProvider.this, field.getValue());
			}
		});
		
		return field;
	}

	private Field createDateFormField(String pattern) {
		if(null == pattern)
			pattern = DateTimeFormat.getFormat(DateTimeFormat.PredefinedFormat.DATE_SHORT).getPattern();
		
		final DateField field = new DateField(new DateTimePropertyEditor(pattern));
		field.setName(name);
		field.setValidateOnBlur(true);
		initContextMenu(field);

		field.addValueChangeHandler(new ValueChangeHandler<Date>() {
			@Override
			public void onValueChange(ValueChangeEvent<Date> event) {
				ValueChangeEvent.fire(DateProvider.this, field.getValue());
			}
		});
		
		return field;
	}

	protected SFFCDate getDateConfig(){
		for(SimpleFormFieldConfiguration config : configs)
			if(config instanceof SFFCDate)
				return (SFFCDate) config;
		return null;
	}

	@Override
	public void addFieldBindings(Object model, ValueProvider vp, Widget field) {
		SFFCDate dateConfig = getDateConfig();
		
	    if(null != dateConfig && dateConfig.getMode().equals(Mode.DateTime)) {
	    	final DateField dateField = (DateField) ((Container)field).getWidget(0);
	    	final TimeField timeField = (TimeField) ((Container)field).getWidget(1);
	    	
			fieldBindingDate = new HasValueFieldBinding(dateField, model, vp){
				@Override
				protected Object convertFieldValue(Object value) {
					if(null == value || 
							! (value instanceof Date))
						return null;
					
					DateWrapper time = new DateWrapper(timeField.getValue());
					if(null != time){
						((Date)value).setMinutes(time.getMinutes());
						((Date)value).setHours(time.getHours());
					}
					return value;
				}
				
			};
			fieldBindingTime = new HasValueFieldBinding(timeField, model, vp){
				@Override
				protected Object convertFieldValue(Object value) {
					if(null == value || 
							! (value instanceof Date))
						return null;
					
					Date date = timeField.getValue();
					if(null != date){
						date.setMinutes(((Date)value).getMinutes());
						date.setHours(((Date)value).getHours());
						return date;
					}
					
					/* return value from field */
					return date;
				}
			};
	    } else {
			Field f = (Field) field;
			
			fieldBinding = new HasValueFieldBinding(f, model, vp);
		}
	}

	public Object getValue(Widget field) {
		SFFCDate dateConfig = getDateConfig();
		
	    if(null != dateConfig && dateConfig.getMode().equals(Mode.DateTime)) {
	    	Field dateField = (Field) ((DwFlowContainer)field).getWidget(0);
	    	Field timeField = (Field) ((DwFlowContainer)field).getWidget(1);
	    	
	    	Date date = (Date) dateField.getValue();
	    	Date time = (Date) timeField.getValue();
	    	if(null != date){
	    		date.setHours(time.getHours());
	    		date.setMinutes(time.getMinutes());
	    		return date;
	    	}
	    	return time;
	    } else {
	    	return ((Field) field).getValue();
	    }
	}

	public void removeFieldBindings(Object model, Widget field) {
		if(null != fieldBinding){
			fieldBinding.unbind();
			fieldBinding = null;
		}
		if(null != fieldBindingDate){
			fieldBindingDate.unbind();
			fieldBindingDate = null;
		}
		if(null != fieldBindingTime){
			fieldBindingTime.unbind();
			fieldBindingTime = null;
		}
	}

	@Override
	public Widget reload(Widget field) {
		return createFormField();
	}
}
