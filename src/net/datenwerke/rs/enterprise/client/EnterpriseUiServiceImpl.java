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
 
 
package net.datenwerke.rs.enterprise.client;

public class EnterpriseUiServiceImpl implements EnterpriseUiService {

	private boolean enterprise;
	private boolean evaluation;
	private boolean enterpriseJarAvailable;

	@Override
	public boolean isCommunity() {
		return ! isEnterprise();
	}
	
	@Override
	public boolean isEnterprise() {
		return enterprise;
	}

	void setEnterprise(boolean enterprise) {
		this.enterprise = enterprise;
	}
	
	@Override
	public boolean isEvaluation() {
		return evaluation;
	}
	
	void setEvaluation(boolean evaluation) {
		this.evaluation = evaluation;
	}
	
	@Override
	public boolean isEnterpriseJarAvailable() {
		return enterpriseJarAvailable;
	}
	
	void setEnterpriseJarAvailable(boolean enterpriseJarAvailable) {
		this.enterpriseJarAvailable = enterpriseJarAvailable;
	}
}
