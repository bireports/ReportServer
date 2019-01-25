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
 
 
package net.datenwerke.security.ext.client.usermanager.helper.simpleform;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

import net.datenwerke.gxtdto.client.forms.binding.HasValueFieldBinding;
import net.datenwerke.gxtdto.client.forms.selection.SimpleGridSelectionPopup;
import net.datenwerke.gxtdto.client.forms.selection.SingleSelectionField;
import net.datenwerke.gxtdto.client.forms.simpleform.SimpleFormFieldConfiguration;
import net.datenwerke.gxtdto.client.forms.simpleform.hooks.FormFieldProviderHookImpl;
import net.datenwerke.gxtdto.client.utils.handlers.GenericStoreHandler;
import net.datenwerke.security.client.usermanager.dto.ie.StrippedDownUser;
import net.datenwerke.security.client.usermanager.dto.ie.StrippedDownUserPA;
import net.datenwerke.security.ext.client.usermanager.UserManagerUIService;
import net.datenwerke.security.ext.client.usermanager.locale.UsermanagerMessages;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.ListStore;

/**
 * 
 *
 */
public class StrippedDownUserProvider extends FormFieldProviderHookImpl {

	private static StrippedDownUserPA sduPa = GWT.create(StrippedDownUserPA.class);
	
	private final UserManagerUIService userManagerService;
	
	private ListStore<StrippedDownUser> selectedStrippedUserStore;
	
	@Inject
	public StrippedDownUserProvider(
		UserManagerUIService userManagerService	
		){
		
		/* store objects */
		this.userManagerService = userManagerService;
	}
	
	@Override
	public boolean doConsumes(Class<?> type, SimpleFormFieldConfiguration... configs) {
		return StrippedDownUser.class.equals(type);
	}

	@Override
	public Widget createFormField() {
		if(isMultiSelect())
			return createMultiSelect();
		else
			return createSingleSelect();
	}
	
	private Widget createSingleSelect() {
		/* create stores */
		ListStore<StrippedDownUser> strippedUserStore = userManagerService.getStrippedUserStore();
		
		Map<ValueProvider<StrippedDownUser, String>, String> displayProperties = new LinkedHashMap<ValueProvider<StrippedDownUser, String>, String>();
		displayProperties.put(sduPa.firstname(), UsermanagerMessages.INSTANCE.firstname());
		displayProperties.put(sduPa.lastname(), UsermanagerMessages.INSTANCE.lastname());
		displayProperties.put(sduPa.ou(), UsermanagerMessages.INSTANCE.ou());
		
		SingleSelectionField<StrippedDownUser> selectionPanel = new SingleSelectionField<StrippedDownUser>(strippedUserStore, displayProperties);
		selectionPanel.addValueChangeHandler(new ValueChangeHandler<StrippedDownUser>() {
			@Override
			public void onValueChange(ValueChangeEvent<StrippedDownUser> event) {
				ValueChangeEvent.fire(StrippedDownUserProvider.this, event.getValue());
			}
		});
		
		return selectionPanel;
	}

	private Widget createMultiSelect() {
		/* create stores */
		selectedStrippedUserStore = new ListStore<StrippedDownUser>(sduPa.dtoId());
		ListStore<StrippedDownUser> strippedUserStore = userManagerService.getStrippedUserStore();
		
		Map<ValueProvider<StrippedDownUser, String>, String> displayProperties = new LinkedHashMap<ValueProvider<StrippedDownUser, String>, String>();
		displayProperties.put(sduPa.firstname(), UsermanagerMessages.INSTANCE.firstname());
		displayProperties.put(sduPa.lastname(), UsermanagerMessages.INSTANCE.lastname());
		displayProperties.put(sduPa.ou(), UsermanagerMessages.INSTANCE.ou());
		final SimpleGridSelectionPopup<StrippedDownUser> userGrid = new SimpleGridSelectionPopup<StrippedDownUser>(displayProperties, 90, selectedStrippedUserStore, strippedUserStore);
		userGrid.setBorders(true);
		userGrid.setWidth(400);
		userGrid.setHeight(100);
		userGrid.getView().setShowDirtyCells(false);
		userGrid.getView().setEmptyText(UsermanagerMessages.INSTANCE.noRecipientSelected());
		
		selectedStrippedUserStore.addStoreHandlers(new GenericStoreHandler<StrippedDownUser>(){
			@Override
			protected void handleDataChangeEvent() {
				ValueChangeEvent.fire(StrippedDownUserProvider.this, selectedStrippedUserStore.getAll());
			}
		});
		
		return userGrid;
	}

	private boolean isMultiSelect() {
		if(configs.length > 0 && configs[0] instanceof SFFCUser)
			return ((SFFCUser)configs[0]).isMultiSelect();
		return true;
	}

	public Object getValue(Widget field){
		if(isMultiSelect())
			return new ArrayList(selectedStrippedUserStore.getAll());
		
		return ((SingleSelectionField<StrippedDownUser>)field).getValue();
	}
	
	@Override
	public void setValue(Widget field, Object object) {
		if(isMultiSelect()){
			selectedStrippedUserStore.clear();
			selectedStrippedUserStore.addAll((Collection) object);
		} else 
			((SingleSelectionField<StrippedDownUser>)field).setValue((StrippedDownUser) object);
	}

	@Override
	public void addFieldBindings(final Object model, ValueProvider vp, Widget field) {
		fieldBinding = new HasValueFieldBinding(this, model, vp);
	}

}
