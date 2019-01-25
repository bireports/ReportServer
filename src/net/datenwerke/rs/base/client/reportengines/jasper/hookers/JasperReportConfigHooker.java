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
 
 
package net.datenwerke.rs.base.client.reportengines.jasper.hookers;

import java.util.Collection;
import java.util.Collections;

import net.datenwerke.gf.client.managerhelper.mainpanel.MainPanelView;
import net.datenwerke.gxtdto.client.resources.BaseResources;
import net.datenwerke.rs.base.client.reportengines.jasper.dto.JasperReportDto;
import net.datenwerke.rs.base.client.reportengines.jasper.dto.JasperReportVariantDto;
import net.datenwerke.rs.base.client.reportengines.jasper.dto.decorator.JasperReportDtoDec;
import net.datenwerke.rs.base.client.reportengines.jasper.dto.decorator.JasperReportVariantDtoDec;
import net.datenwerke.rs.base.client.reportengines.jasper.locale.JasperMessages;
import net.datenwerke.rs.base.client.reportengines.jasper.ui.JasperReportForm;
import net.datenwerke.rs.core.client.reportmanager.dto.reports.ReportDto;
import net.datenwerke.rs.core.client.reportmanager.hooks.ReportTypeConfigHook;
import net.datenwerke.rs.theme.client.icon.BaseIcon;

import com.google.gwt.resources.client.ImageResource;
import com.google.inject.Inject;
import com.google.inject.Provider;

public class JasperReportConfigHooker implements ReportTypeConfigHook {

	private final Provider<JasperReportForm> adminViewProvider;
	
	@Inject
	public JasperReportConfigHooker(
		Provider<JasperReportForm> adminViewProvider
		) {

		/* store objects */
		this.adminViewProvider = adminViewProvider;
	}
	
	@Override
	public boolean consumes(ReportDto report) {
		return report instanceof JasperReportDto;
	}

	@Override
	public Class<? extends ReportDto> getReportClass() {
		return JasperReportDto.class;
	}

	@Override
	public Class<? extends ReportDto> getReportVariantClass() {
		return JasperReportVariantDto.class;
	}

	@Override
	public ImageResource getReportIcon() {
		return BaseIcon.REPORT_JASPER.toImageResource();
	}

	@Override
	public ImageResource getReportVariantIcon() {
		return BaseIcon.REPORT_JASPER.toImageResource();
	}

	@Override
	public ImageResource getReportIconLarge() {
		return BaseIcon.REPORT_JASPER.toImageResource(1);
	}

	@Override
	public ImageResource getReportVariantIconLarge() {
		return BaseIcon.REPORT_JASPER.toImageResource(1);
	}

	@Override
	public ImageResource getReportLinkIcon() {
		return BaseIcon.REPORT_JASPER_LINK.toImageResource();
	}

	@Override
	public ImageResource getReportLinkIconLarge() {
		return BaseIcon.REPORT_JASPER_LINK.toImageResource(1);
	}

	@Override
	public ReportDto instantiateReport() {
		return new JasperReportDtoDec();
	}
	
	@Override
	public ReportDto instantiateReportVariant() {
		return new JasperReportVariantDtoDec();
	}

	@Override
	public String getReportName() {
		return JasperMessages.INSTANCE.reportTypeName();
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
