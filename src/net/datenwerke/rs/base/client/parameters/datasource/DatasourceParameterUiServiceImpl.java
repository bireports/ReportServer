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
 
 
package net.datenwerke.rs.base.client.parameters.datasource;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import net.datenwerke.rs.enterprise.client.EnterpriseUiService;

@Singleton
public class DatasourceParameterUiServiceImpl implements DatasourceParameterUiService {

	private final EnterpriseUiService enterpriseService;

	private boolean allowPostProcessing;
	
	@Inject
	public DatasourceParameterUiServiceImpl(EnterpriseUiService enterpriseService) {
		this.enterpriseService = enterpriseService;
	}

	@Override
	public void setAllowPostProcessing(boolean b) {
		this.allowPostProcessing = b;
	}
	
	@Override
	public boolean isAllowPostProcessing() {
		return allowPostProcessing && enterpriseService.isEnterprise();
	}

}
