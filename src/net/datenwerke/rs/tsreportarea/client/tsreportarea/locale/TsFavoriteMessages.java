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
 
 
package net.datenwerke.rs.tsreportarea.client.tsreportarea.locale;

import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.Messages;

public interface TsFavoriteMessages extends Messages {

	public final static TsFavoriteMessages INSTANCE = GWT.create(TsFavoriteMessages.class);
	
	String addFolderText();
	String appDescription();
	
	String appName();
	String catalog();

	String containerHeadline();
	
	String explorer();
	String exportLabel();
	
	String importPostProcessorDescription();
	
	String importPostProcessorHeadline();
	
	String importPostProcessorIntoTeamspace();
	String importPostProcessorName();
	
	String importReportText();
	
	String importVariantHookDescription();
	
	String importVariantHookName();
	
	String itemDeleted();
	String lastModified();
	
	String listPanelHeader(String folderName);
	
	String movedNode();
	String movedNodes();
	
	String newCopy();

	String newFolderDescriptionLabel();
	
	String newFolderNameLabel();
	String newReference();
	String noTeamSpaceSelectedMsg();
	String noTeamSpaceSelectedTitle();
	String objectInfoHeader(String displayTitle);
	
	String reportNotInTeamSpacesMessages();
	String rootFolderName();
	String selectTeamspaceLabel();
	
	String importVariantIntoTeamSpaceLabel();
	String duplicateLabel();
	
	String lastUpdatedFormat();
	
	String idLabel();
	
	String reportCouldNotBeLoaded();

	String violatedSecurityTitle();	
	String violatedSecurityMessage();
	
	String selectFromTeamSpaceText();
	String selectFileFromTeamSpace();
	
	String folderDescription();
	String reportDescription();
	String referenceDescription();
	
	String referencedBy();

}
