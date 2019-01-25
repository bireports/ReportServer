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
 
 
package net.datenwerke.rs.base.client.reportengines.table.hookers;

import java.util.Collection;
import java.util.Collections;

import net.datenwerke.gf.client.managerhelper.mainpanel.MainPanelView;
import net.datenwerke.gxtdto.client.resources.BaseResources;
import net.datenwerke.rs.base.client.reportengines.table.dto.TableReportDto;
import net.datenwerke.rs.base.client.reportengines.table.dto.TableReportVariantDto;
import net.datenwerke.rs.base.client.reportengines.table.dto.decorator.TableReportDtoDec;
import net.datenwerke.rs.base.client.reportengines.table.dto.decorator.TableReportVariantDtoDec;
import net.datenwerke.rs.base.client.reportengines.table.locale.TableMessages;
import net.datenwerke.rs.base.client.reportengines.table.ui.TableReportForm;
import net.datenwerke.rs.core.client.reportmanager.dto.reports.ReportDto;
import net.datenwerke.rs.core.client.reportmanager.hooks.ReportTypeConfigHook;
import net.datenwerke.rs.theme.client.icon.BaseIcon;

import com.google.gwt.resources.client.ImageResource;
import com.google.inject.Inject;
import com.google.inject.Provider;

public class TableReportConfigHooker implements ReportTypeConfigHook {

	private final Provider<TableReportForm> adminViewProvider;
	
	@Inject
	public TableReportConfigHooker(
		Provider<TableReportForm> adminViewProvider
		) {

		/* store objects */
		this.adminViewProvider = adminViewProvider;
	}

	@Override
	public boolean consumes(ReportDto report) {
		return report instanceof TableReportDto;
	}
	
	@Override
	public Class<? extends ReportDto> getReportClass() {
		return TableReportDto.class;
	}

	@Override
	public Class<? extends ReportDto> getReportVariantClass() {
		return TableReportVariantDto.class;
	}

	@Override
	public ImageResource getReportIcon() {
		return BaseIcon.REPORT_DL.toImageResource();
	}

	@Override
	public ImageResource getReportVariantIcon() {
		return BaseIcon.REPORT_DL.toImageResource();
	}

	@Override
	public ImageResource getReportIconLarge() {
		return BaseIcon.REPORT_DL.toImageResource(1);
	}

	@Override
	public ImageResource getReportVariantIconLarge() {
		return BaseIcon.REPORT_DL.toImageResource(1);
	}

	@Override
	public ImageResource getReportLinkIcon() {
		return BaseIcon.REPORT_DL_LINK.toImageResource();
	}

	@Override
	public ImageResource getReportLinkIconLarge() {
		return BaseIcon.REPORT_DL_LINK.toImageResource(1);
	}
	
	@Override
	public ReportDto instantiateReport() {
		return new TableReportDtoDec();
	}
	
	@Override
	public ReportDto instantiateReportVariant() {
		return new TableReportVariantDtoDec();
	}

	@Override
	public String getReportName() {
		return TableMessages.INSTANCE.reportTypeName();
	}

	@Override
	public Collection<? extends MainPanelView> getAdminViews(ReportDto report) {
		return Collections.singleton(adminViewProvider.get());
	}

	@Override
	public boolean isAvailable() {
		return true;
	}
}
