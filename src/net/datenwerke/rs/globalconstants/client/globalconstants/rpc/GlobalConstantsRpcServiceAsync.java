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
 
 
package net.datenwerke.rs.globalconstants.client.globalconstants.rpc;

import java.util.Collection;
import java.util.List;

import net.datenwerke.rs.globalconstants.client.globalconstants.dto.GlobalConstantDto;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface GlobalConstantsRpcServiceAsync {

	void loadGlobalConstants(
			AsyncCallback<List<GlobalConstantDto>> callback);

	void addNewConstant(AsyncCallback<GlobalConstantDto> callback);

	void updateConstant(GlobalConstantDto constantDto,
			AsyncCallback<GlobalConstantDto> callback);

	void removeAllConstants(AsyncCallback<Void> callback);

	void removeConstants(Collection<GlobalConstantDto> constant,
			AsyncCallback<Void> callback);

}
