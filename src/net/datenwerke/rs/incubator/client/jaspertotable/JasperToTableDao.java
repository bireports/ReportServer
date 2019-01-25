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
 
 
package net.datenwerke.rs.incubator.client.jaspertotable;

import net.datenwerke.gxtdto.client.dtomanager.Dao;
import net.datenwerke.rs.base.client.reportengines.jasper.dto.JasperReportDto;
import net.datenwerke.rs.incubator.client.jaspertotable.dto.JasperToTableConfigDto;
import net.datenwerke.rs.incubator.client.jaspertotable.rpc.JasperToTableRpcServiceAsync;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;

public class JasperToTableDao extends Dao {

	final private JasperToTableRpcServiceAsync rpcService;
	
	@Inject
	public JasperToTableDao(JasperToTableRpcServiceAsync rpcService){
		
		/* store object */
		this.rpcService = rpcService;
	}
	
	public void getConfig(JasperReportDto report,
			AsyncCallback<JasperToTableConfigDto> callback){
		rpcService.getConfig(report, transformAndKeepCallback(callback));
	}
	
	public void setConfig(JasperReportDto report, JasperToTableConfigDto config,
			AsyncCallback<Void> callback){
		rpcService.setConfig(report, config, transformAndKeepCallback(callback));
	}
	
	
}
