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
 
 
package net.datenwerke.rs.core.client.sendto.rpc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import net.datenwerke.rs.core.client.helper.simpleform.ExportTypeSelection;
import net.datenwerke.rs.core.client.reportexporter.dto.ReportExecutionConfigDto;
import net.datenwerke.rs.core.client.reportmanager.dto.reports.ReportDto;
import net.datenwerke.rs.core.client.sendto.SendToClientConfig;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface SendToRpcServiceAsync {

	void loadClientConfigsFor(ReportDto report,
			AsyncCallback<ArrayList<SendToClientConfig>> callback);

	void sendTo(ReportDto report, String executorToken, String id, String format, List<ReportExecutionConfigDto> formatConfig, HashMap<String, String> values, AsyncCallback<String> callback);

}
