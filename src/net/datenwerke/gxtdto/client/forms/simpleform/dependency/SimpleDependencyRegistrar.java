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
 
 
package net.datenwerke.gxtdto.client.forms.simpleform.dependency;

import net.datenwerke.gxtdto.client.forms.simpleform.SimpleForm;
import net.datenwerke.gxtdto.client.forms.simpleform.hooks.FormFieldProviderHook;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;

public class SimpleDependencyRegistrar implements DependencyRegistrar {

	private final String dependant;
	private final String dependsOn;
	private final SimpleForm form;
	
	private boolean reloading = false;
	
	public SimpleDependencyRegistrar(String dependant, String dependsOn,
			SimpleForm form) {
		super();
		this.dependant = dependant;
		this.dependsOn = dependsOn;
		this.form = form;
	}



	@SuppressWarnings("unchecked")
	public void registerDependency() {
		final FormFieldProviderHook responsibleHook = form.getResponsibleHook(dependsOn);
		
		responsibleHook.addValueChangeHandler(new ValueChangeHandler() {
			@Override
			public void onValueChange(ValueChangeEvent event) {
				/* test that we only reload once */
				if(reloading)
					return;
				reloading = true;
				
				Scheduler.get().scheduleDeferred(new ScheduledCommand() {
					@Override
					public void execute() {
						reloading = false;
						form.reloadField(dependant);	
					}
				});
			}
		});
	}

}
