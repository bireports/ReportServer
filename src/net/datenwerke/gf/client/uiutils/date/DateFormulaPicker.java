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
 
 
package net.datenwerke.gf.client.uiutils.date;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.inject.Inject;
import com.sencha.gxt.widget.core.client.form.TriggerField;

import net.datenwerke.gf.client.juel.JuelUiService;
import net.datenwerke.gf.client.juel.dto.JuelResultDto;
import net.datenwerke.gf.client.uiutils.info.FieldErrorHandler;
import net.datenwerke.gf.client.uiutils.info.FieldInfoHandler;
import net.datenwerke.gf.client.uiutils.locale.UiUtilsMessages;
import net.datenwerke.gxtdto.client.dtomanager.callback.RsAsyncCallback;

public class DateFormulaPicker extends TriggerField<DateFormulaContainer> {

	@Inject
	private static JuelUiService juelService;
	private FieldInfoHandler fieldInfoHandler;
	
	public DateFormulaPicker(){
		this(null);
	}
	
	
	public DateFormulaPicker(DateTimeFormat dateFormat) {
		super(new DateFormulaPickerCell(), new DateFormulaPickerEditor(dateFormat));
		fieldInfoHandler = new FieldInfoHandler(this);
		
		fieldInfoHandler.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				final DateFormulaContainer value = getValue();
				if(null != value && null != value.getFormula()){
					((DateFormulaPickerCell)getCell()).startEditFormula(value.getFormula());
					focus();
				}
			}
		});
		
		setErrorSupport(new FieldErrorHandler(this));
	}


	@Override
	protected void onRedraw() {
		/* clear any marks */
		fieldInfoHandler.clear();
		clearInvalid();
		
		/* check value for formula */
		final DateFormulaContainer value = getValue();
		if(null != value && null != value.getFormula()){
			juelService.evaluateExpression(value.getFormula(), new RsAsyncCallback<JuelResultDto>(){
				@Override
				public void onSuccess(JuelResultDto result) {
					if(null == result.getDateValue()){
						markInvalid(UiUtilsMessages.INSTANCE.cannotParseDateFormula());
						return;
					}
					
					fieldInfoHandler.mark(UiUtilsMessages.INSTANCE.dateComputedBy(value.getFormula()));
					
					/* redraw */
					SafeHtmlBuilder sb = new SafeHtmlBuilder();
				    ((DateFormulaPickerCell)getCell()).render(createContext(), value, result, sb);
				    getElement().setInnerHTML(sb.toSafeHtml().asString());	
				}
				@Override
				public void onFailure(Throwable caught) {
					markInvalid(UiUtilsMessages.INSTANCE.cannotParseDateFormula());
				}
			});
		}
	}
	
	@Override
	public void setValue(DateFormulaContainer value, boolean fireEvents, boolean redraw) {
		if(null != fieldInfoHandler)
			fieldInfoHandler.clear();
		super.setValue(value, fireEvents, redraw || (null != value && null != value.getFormula()));
	}
	
	@Override
	public boolean isValid(boolean preventMark) {
		return isCurrentValid(preventMark);
	}

}
