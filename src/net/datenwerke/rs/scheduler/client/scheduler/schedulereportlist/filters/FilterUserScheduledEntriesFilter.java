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
 
 
package net.datenwerke.rs.scheduler.client.scheduler.schedulereportlist.filters;

import java.util.ArrayList;
import java.util.List;

import net.datenwerke.gf.client.treedb.UITree;
import net.datenwerke.gf.client.treedb.simpleform.SFFCGenericTreeNode;
import net.datenwerke.gxtdto.client.forms.simpleform.SimpleForm;
import net.datenwerke.gxtdto.client.forms.simpleform.hooks.FormFieldProviderHook;
import net.datenwerke.rs.scheduler.client.scheduler.dto.ReportServerJobFilterDto;
import net.datenwerke.rs.scheduler.client.scheduler.locale.SchedulerMessages;
import net.datenwerke.rs.scheduler.client.scheduler.schedulereportlist.ScheduledReportListPanel;
import net.datenwerke.rs.scheduler.client.scheduler.schedulereportlist.SchedulerAdminModule;
import net.datenwerke.rs.scheduler.client.scheduler.schedulereportlist.hooks.ScheduledReportListFilter;
import net.datenwerke.scheduler.client.scheduler.dto.filter.JobFilterConfigurationDto;
import net.datenwerke.scheduler.client.scheduler.dto.filter.JobFilterCriteriaDto;
import net.datenwerke.security.client.usermanager.dto.UserDto;
import net.datenwerke.security.ext.client.usermanager.provider.annotations.UserManagerTreeBasicSingleton;

import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.sencha.gxt.widget.core.client.form.FieldLabel;

public class FilterUserScheduledEntriesFilter implements ScheduledReportListFilter {

	private final UITree tree;

	private FormFieldProviderHook fromUserHook;
	private Widget fromUserField;
	private FormFieldProviderHook toUserHook;
	private Widget toUserField;
	
	@Inject
	public FilterUserScheduledEntriesFilter(
		@UserManagerTreeBasicSingleton UITree tree	
		) {
		this.tree = tree;
	}
	
	public Iterable<Widget> getFilter(final ScheduledReportListPanel scheduledReportListPanel){
		fromUserHook = SimpleForm.getResponsibleHooker(UserDto.class, new SFFCGenericTreeNode() {
			@Override
			public UITree getTreeForPopup() {
				return tree;
			}
		});
		fromUserField = fromUserHook.createFormField();
		
		fromUserHook.addValueChangeHandler(new ValueChangeHandler() {
			@Override
			public void onValueChange(ValueChangeEvent event) {
				scheduledReportListPanel.reload();
			}
		});
		
		FieldLabel fromUser = new FieldLabel(fromUserField, SchedulerMessages.INSTANCE.fromUser());
		fromUser.setLabelWidth(120);
		
		toUserHook = SimpleForm.getResponsibleHooker(UserDto.class, new SFFCGenericTreeNode() {
			@Override
			public UITree getTreeForPopup() {
				return tree;
			}
		});
		toUserField = toUserHook.createFormField();
		
		toUserHook.addValueChangeHandler(new ValueChangeHandler() {
			@Override
			public void onValueChange(ValueChangeEvent event) {
				scheduledReportListPanel.reload();
			}
		});
		
		FieldLabel toUser = new FieldLabel(toUserField, SchedulerMessages.INSTANCE.toUser());
		toUser.setLabelWidth(120);
		
		ArrayList<Widget> widgets = new ArrayList<Widget>();
		widgets.add(fromUser);
		widgets.add(toUser);
		
		return widgets;
	}
	
	public void configure(ScheduledReportListPanel scheduledReportListPanel, JobFilterConfigurationDto config, List<JobFilterCriteriaDto> addCriterions){
		if(config instanceof ReportServerJobFilterDto){
			((ReportServerJobFilterDto)config).setFromUser(null);
			((ReportServerJobFilterDto)config).setToUser(null);
			
			if(null != fromUserField && null != toUserField){
				UserDto fromUser = (UserDto) fromUserHook.getValue(fromUserField);
				UserDto toUser = (UserDto) toUserHook.getValue(toUserField);
				
				if( null != fromUser)
					((ReportServerJobFilterDto)config).setFromUser(fromUser);
				if(null != toUser)
					((ReportServerJobFilterDto)config).setToUser(toUser);
			}
		}
	}

	@Override
	public boolean appliesTo(String panelName) {
		return SchedulerAdminModule.ADMIN_FILTER_PANEL.equals(panelName);
	}

	
	
	
}
