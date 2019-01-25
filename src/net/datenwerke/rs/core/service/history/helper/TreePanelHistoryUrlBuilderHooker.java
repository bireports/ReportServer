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
 
 
package net.datenwerke.rs.core.service.history.helper;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.datenwerke.gf.client.history.HistoryLocation;
import net.datenwerke.gf.client.treedb.TreeDBHistoryCallback;
import net.datenwerke.gf.service.history.HistoryLink;
import net.datenwerke.gf.service.history.hooks.HistoryUrlBuilderHook;
import net.datenwerke.treedb.service.treedb.AbstractNode;

public abstract class TreePanelHistoryUrlBuilderHooker implements
		HistoryUrlBuilderHook {

	@Override
	public List<HistoryLink> buildLinksFor(Object o) {
		if(!consumes(o))
			return new ArrayList<HistoryLink>();
		
		HistoryLink lnk = new HistoryLink();
		AbstractNode n = (AbstractNode) o;
		lnk.setObjectCaption(assembleReadablePathFor(n));
		
		lnk.setHistoryLinkBuilderId(getBuilderId());
		lnk.setHistoryLinkBuilderName(getNameFor(o));
		lnk.setHistoryLinkBuilderIcon(getIconFor(o));

		String path = assemblePathFor((AbstractNode<?>) o); 
		HistoryLocation loc = new HistoryLocation(getTokenName(), HistoryLocation.makeParameter(TreeDBHistoryCallback.HISTORY_PARAMETER_TREE_PATH, path));
		adjustLocation(o, loc);
		lnk.setHistoryToken(loc.asString());
		
		return Arrays.asList(lnk);
	}

	protected void adjustLocation(Object o, HistoryLocation location) {
	}

	protected abstract String getTokenName();

	protected abstract String getBuilderId();
	
	protected abstract String getNameFor(Object o);
	
	protected abstract String getIconFor(Object o);

	protected String assemblePathFor(AbstractNode<?> o) {
		StringBuilder builder = new StringBuilder();
		builder.append(o.getId());
		AbstractNode<?> parent = o.getParent();
		while(null != parent){
			builder.insert(0, parent.getId() + ".");
			parent = parent.getParent();
		}
		
		return builder.toString();
	}
	
	protected String assembleReadablePathFor(AbstractNode<?> o) {
		StringBuilder builder = new StringBuilder();
		builder.append( getName(o));
		AbstractNode<?> parent = o.getParent();
		while(null != parent){
			builder.insert(0, getName(parent) + "/");
			parent = parent.getParent();
		}
		
		return builder.toString();
	}
	
	protected String getName(AbstractNode<?> o){
		Method method = null;
		try{
			method = o.getClass().getMethod("getName", null);
			Object invoke = method.invoke(o, null);
			return String.valueOf(invoke);
		} catch(NoSuchMethodException m){
		} catch (IllegalArgumentException e) {
		} catch (IllegalAccessException e) {
		} catch (InvocationTargetException e) {
		}
		
		return "id:"+o.getClass().getSimpleName()+":"+o.getId();
		
	}
}
