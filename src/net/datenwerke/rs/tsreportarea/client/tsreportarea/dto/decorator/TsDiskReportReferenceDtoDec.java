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
 
 
package net.datenwerke.rs.tsreportarea.client.tsreportarea.dto.decorator;

import net.datenwerke.rs.core.client.reportmanager.dto.interfaces.ReportContainerDto;
import net.datenwerke.rs.theme.client.icon.BaseIcon;
import net.datenwerke.rs.tsreportarea.client.tsreportarea.dto.TsDiskReportReferenceDto;

/**
 * Dto Decorator for {@link TsDiskReportReferenceDto}
 *
 */
public class TsDiskReportReferenceDtoDec extends TsDiskReportReferenceDto implements ReportContainerDto {


	private static final long serialVersionUID = 1L;

	public TsDiskReportReferenceDtoDec() {
		super();
	}
	
	@Override
	public String toTypeDescription() {
		if(null == getReport())
			return super.toTypeDescription();
		return getReport().toTypeDescription();
	}
	
	@Override
	public BaseIcon toIcon() {
		if(null == getReport())
			return super.toIcon();
		return getReport().toIcon();
	}

}
