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
 
 
package net.datenwerke.rs.tsreportarea.client.tsreportarea.objectinfo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import net.datenwerke.gxtdto.client.objectinformation.ObjectPreviewTabPanel;
import net.datenwerke.gxtdto.client.objectinformation.hooks.ObjectPreviewTabKeyInfoProvider;
import net.datenwerke.gxtdto.client.objectinformation.hooks.ObjectPreviewTabProviderHook;
import net.datenwerke.rs.tsreportarea.client.tsreportarea.dto.TsDiskReportReferenceDto;

/**
 * 
 *
 */
public class TsFavoriteReferenceObjectInfo implements ObjectPreviewTabKeyInfoProvider {

	@Override
	public boolean consumes(Object object) {
		return object instanceof TsDiskReportReferenceDto;
	}

	@Override
	public Collection<?> getSubtypes(Object object) {
		return null;
	}

	@Override
	public void setInfoPanel(final ObjectPreviewTabPanel infoPanel, Object object) {
		TsDiskReportReferenceDto reference = (TsDiskReportReferenceDto) object;
		infoPanel.addInfoFor(reference.getReport());
	}

	@Override
	public List<PreviewComponent> getInfoComponents(Object object) {
		return new ArrayList<ObjectPreviewTabProviderHook.PreviewComponent>();
	}


}
