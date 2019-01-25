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
 
 
package net.datenwerke.rs.globalconstants.client.globalconstants.locale;

import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.Messages;

public interface GlobalConstantsMessages extends Messages{

	public final GlobalConstantsMessages INSTANCE = GWT.create(GlobalConstantsMessages.class);
	
	public String viewNavigationTitle();
	
	public String dialogTitle();
	public String dialogDescription();
	
	public String securityTitle();
	public String securityDescription();
	
	public String propertyName();
	public String propertyValue();
	
	public String addConstantText();
	
	public String confirmRemoveAllTitle();
	public String confirmRemoveAllText();
	public String constantSuccessfullyUpdated();
	
}
