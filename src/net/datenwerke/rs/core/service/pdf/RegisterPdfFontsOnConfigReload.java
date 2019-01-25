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
 
 
package net.datenwerke.rs.core.service.pdf;

import com.google.inject.Inject;

import net.datenwerke.rs.birt.service.reportengine.BirtReportService;
import net.datenwerke.rs.configservice.service.configservice.hooks.ReloadConfigNotificationHook;
import net.datenwerke.rs.utils.misc.PdfUtils;

public class RegisterPdfFontsOnConfigReload implements ReloadConfigNotificationHook {

	private final PdfUtils pdfUtils;
	private final BirtReportService birtService;

	@Inject
	public RegisterPdfFontsOnConfigReload(PdfUtils pdfUtils, BirtReportService birtService) {
		this.pdfUtils = pdfUtils;
		this.birtService = birtService;
	}
	
	@Override
	public void reloadConfig() {
		registerFonts();
	}

	@Override
	public void reloadConfig(String identifier) {
		if(PdfUtils.PDF_CONFIG_FILE.equals(identifier))
			registerFonts();
	}

	protected void registerFonts() {
		pdfUtils.registerPdfFonts();
		birtService.clearFontCache();
	}

}
