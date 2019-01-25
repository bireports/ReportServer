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
 
 
package net.datenwerke.rs.core.client.reportmanager.objectinfo;

import java.util.Date;

import net.datenwerke.gxtdto.client.objectinformation.hooks.ObjectInfoKeyInfoProviderImpl;
import net.datenwerke.hookhandler.shared.hookhandler.HookHandlerService;
import net.datenwerke.rs.core.client.reportmanager.dto.interfaces.ReportVariantDto;
import net.datenwerke.rs.core.client.reportmanager.dto.reports.ReportDto;
import net.datenwerke.rs.core.client.reportmanager.hooks.ReportTypeConfigHook;
import net.datenwerke.rs.core.client.reportmanager.locale.ReportmanagerMessages;

import com.google.gwt.resources.client.ImageResource;
import com.google.inject.Inject;

public final class ReportObjectInfo extends ObjectInfoKeyInfoProviderImpl<ReportDto> {

	private final HookHandlerService hookHandler;
	
	@Inject
	public ReportObjectInfo(HookHandlerService hookHandler) {
		this.hookHandler = hookHandler;
	}

	@Override
	protected String doGetName(ReportDto report) {
		return report.getName();
	}

	@Override
	protected String doGetDescription(ReportDto report) {
		return report.getDescription();
	}

	@Override
	protected Date doGetLastUpdatedOn(ReportDto object) {
		return object.getLastUpdated();
	}

	@Override
	protected Date doGetCreatedOn(ReportDto object) {
		return object.getCreatedOn();
	}

	@Override
	public boolean consumes(Object object) {
		return object instanceof ReportDto;
	}

	@Override
	protected String doGetType(ReportDto report) {
		return getConfigHook(report).getReportName() + ((report instanceof ReportVariantDto) ? " (" + ReportmanagerMessages.INSTANCE.variant() + ")": "");
	}

	@Override
	protected ImageResource doGetIconSmall(ReportDto report) {
		return (report instanceof ReportVariantDto) ?
				getConfigHook(report).getReportVariantIcon()
				: getConfigHook(report).getReportIcon();
	}

	protected ReportTypeConfigHook getConfigHook(ReportDto report){
		for(ReportTypeConfigHook hooker : hookHandler.getHookers(ReportTypeConfigHook.class))
			if(hooker.consumes(report))
				return hooker;
		throw new IllegalArgumentException("Could not find config provider for: " + report.getClass());
	}
	

}
