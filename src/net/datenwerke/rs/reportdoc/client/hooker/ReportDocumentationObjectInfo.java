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
 
 
package net.datenwerke.rs.reportdoc.client.hooker;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Random;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

import net.datenwerke.gxtdto.client.baseex.widget.DwContentPanel;
import net.datenwerke.rs.core.client.urlview.hooks.UrlViewSpecialViewHandler;
import net.datenwerke.rs.reportdoc.client.ReportDocumentationUIModule;

/**
 * 
 *
 */
public class ReportDocumentationObjectInfo implements
	UrlViewSpecialViewHandler {

	
	@Inject
	public ReportDocumentationObjectInfo(
		){
		
		/* store objects */
	}
	
	@Override
	public boolean consumes(String url) {
		return null != url && url.startsWith("rs:reportdoc://") || url.startsWith("rs:revisions://");
	}

	@Override
	public Widget getWidget(String url) {
		final DwContentPanel wrapper = DwContentPanel.newInlineInstance();
		try{
			if(url.startsWith("rs:revisions://")){
				String reportId = url.substring("rs:revisions://".length());
				
				int nonce = Random.nextInt();
				String reportUrl = GWT.getModuleBaseURL() + ReportDocumentationUIModule.SERVLET + "?nonce=" + nonce + "&id=" + reportId + "&report=revisions" + "&format=HTML";
				
				wrapper.setUrl(reportUrl);
			} else {
				String strIds = url.substring("rs:reportdoc://".length());
				String reportId = strIds;
				String tsid = "";
				if(strIds.contains("/")){
					reportId = strIds.split("/")[0];
					tsid = strIds.split("/")[1];
				} 
				
				int nonce = Random.nextInt();
				String reportUrl = GWT.getModuleBaseURL() + ReportDocumentationUIModule.SERVLET + "?nonce=" + nonce + "&id=" + reportId + "&tsid=" + tsid + "&format=HTML";
				
				wrapper.setUrl(reportUrl);
			}
		} catch(Exception e){
		}

		return wrapper;
	}

	
}
