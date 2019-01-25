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
 
 
package net.datenwerke.rs.tsreportarea.client.tsreportarea.hookers;

import net.datenwerke.rs.core.client.urlview.hooks.UrlViewObjectInfoPostProcessorHook;
import net.datenwerke.rs.tsreportarea.client.tsreportarea.dto.AbstractTsDiskNodeDto;
import net.datenwerke.rs.tsreportarea.client.tsreportarea.dto.TsDiskReportReferenceDto;

public class TsUrlViewObjectInfoPostProcessor implements
		UrlViewObjectInfoPostProcessorHook {

	@Override
	public String postProcess(String[] conf, String url, Object object) {
		if(object instanceof AbstractTsDiskNodeDto)
			url = url.replace("${tsObjectId}", String.valueOf(((AbstractTsDiskNodeDto)object).getId()));
		
		if(object instanceof TsDiskReportReferenceDto)
			return url.replace("${reportId}", String.valueOf(((TsDiskReportReferenceDto)object).getReport().getId()));

		return url;
	}

}
