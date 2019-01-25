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
 
 
package net.datenwerke.gxtdto.client.eventbus.events;

import java.util.HashMap;
import java.util.Map;

import net.datenwerke.gxtdto.client.eventbus.handlers.SubmoduleDisplayRequestHandler;

import com.google.gwt.event.shared.GwtEvent;

public class SubmoduleDisplayRequest extends GwtEvent<SubmoduleDisplayRequestHandler> {
	
	public interface ParentDisplayedCallback{
		void notify(Object obj);
	}
	
	public static final Type<SubmoduleDisplayRequestHandler> TYPE = new Type<SubmoduleDisplayRequestHandler>();

	private Object submodule;
	private String parentId;
	private ParentDisplayedCallback callback = new ParentDisplayedCallback() {
		public void notify(Object obj){}
	};

	private Map<String, Object> parameters = new HashMap<String, Object>();
	
	public SubmoduleDisplayRequest(Object submodule, String parentId) {
		this.submodule = submodule;
		this.parentId = parentId;
	}
	
	public SubmoduleDisplayRequest(Object submodule, String parentId, Map<String, Object> parameters) {
		this.submodule = submodule;
		this.parentId = parentId;
		this.setParameters(parameters);
	}
	
	public String getParentId() {
		return parentId;
	}
	
	public Object getSubmodule() {
		return submodule;
	}
	
	public void setParameters(Map<String, Object> parameters) {
		this.parameters = parameters;
	}

	public Map<String, Object> getParameters() {
		return parameters;
	}
	
	public void setCallback(ParentDisplayedCallback callback){
		this.callback = callback;
	}
	
	public void notifyCallback(Object obj){
		callback.notify(obj);
	}

	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<SubmoduleDisplayRequestHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(SubmoduleDisplayRequestHandler handler) {
		handler.onSubmoduleDisplayRequest(this);
	}

	public void addParameter(String key, Object value) {
		parameters.put(key,value);
	}

	


}
