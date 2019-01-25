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
 
 
package net.datenwerke.rs.scriptreport.client.scriptreport.hookers;

import java.util.Collection;
import java.util.Collections;

import net.datenwerke.gf.client.managerhelper.mainpanel.MainPanelView;
import net.datenwerke.rs.core.client.reportmanager.dto.reports.ReportDto;
import net.datenwerke.rs.core.client.reportmanager.hooks.ReportTypeConfigHook;
import net.datenwerke.rs.enterprise.client.EnterpriseUiService;
import net.datenwerke.rs.scriptreport.client.scriptreport.dto.ScriptReportDto;
import net.datenwerke.rs.scriptreport.client.scriptreport.dto.ScriptReportVariantDto;
import net.datenwerke.rs.scriptreport.client.scriptreport.dto.decorator.ScriptReportDtoDec;
import net.datenwerke.rs.scriptreport.client.scriptreport.dto.decorator.ScriptReportVariantDtoDec;
import net.datenwerke.rs.scriptreport.client.scriptreport.locale.ScriptReportMessages;
import net.datenwerke.rs.scriptreport.client.scriptreport.ui.ScriptReportForm;
import net.datenwerke.rs.theme.client.icon.BaseIcon;

import com.google.gwt.resources.client.ImageResource;
import com.google.inject.Inject;
import com.google.inject.Provider;

public class ScriptReportConfigHooker implements ReportTypeConfigHook {
	
	private final Provider<ScriptReportForm> adminFormProvider;
	private final EnterpriseUiService enterpriseService;
	
	@Inject
	public ScriptReportConfigHooker(
			Provider<ScriptReportForm> adminFormProvider,
			EnterpriseUiService enterpriseService
	) {
		this.adminFormProvider = adminFormProvider;
		this.enterpriseService = enterpriseService;

	}

	@Override
	public boolean consumes(ReportDto report) {
		return report instanceof ScriptReportDto;
	}

	@Override
	public Collection<? extends MainPanelView> getAdminViews(ReportDto report) {
		return Collections.singleton(adminFormProvider.get());
	}

	@Override
	public Class<? extends ReportDto> getReportClass() {
		return ScriptReportDto.class;
	}

	@Override
	public ImageResource getReportIcon() {
		return BaseIcon.SCRIPT.toImageResource();
	}

	@Override
	public ImageResource getReportIconLarge() {
		return BaseIcon.SCRIPT.toImageResource(1);
	}

	@Override
	public ImageResource getReportLinkIcon() {
		return BaseIcon.SCRIPT_LINK.toImageResource();
	}

	@Override
	public ImageResource getReportLinkIconLarge() {
		return BaseIcon.SCRIPT_LINK.toImageResource(1);
	}

	@Override
	public String getReportName() {
		return ScriptReportMessages.INSTANCE.reportTypeName();
	}

	@Override
	public Class<? extends ReportDto> getReportVariantClass() {
		return ScriptReportVariantDto.class;
	}

	@Override
	public ImageResource getReportVariantIcon() {
		return BaseIcon.REPORT_USER.toImageResource();
	}

	@Override
	public ImageResource getReportVariantIconLarge() {
		return BaseIcon.REPORT_USER.toImageResource(1);
	}

	@Override
	public ReportDto instantiateReport() {
		return new ScriptReportDtoDec();
	}

	@Override
	public ReportDto instantiateReportVariant() {
		return new ScriptReportVariantDtoDec();
	}
	
	@Override
	public boolean isAvailable() {
		return enterpriseService.isEnterprise();
	}
}
