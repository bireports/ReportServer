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
 
 
package net.datenwerke.gf.client.juel;

import net.datenwerke.gf.client.juel.dto.JuelResultDto;
import net.datenwerke.gxtdto.client.dtomanager.callback.RsAsyncCallback;

import com.google.inject.Inject;

public class JuelUiServiceImpl implements JuelUiService {

	private final JuelDao juelDao;

	@Inject
	public JuelUiServiceImpl(JuelDao juelDao) {
		super();
		this.juelDao = juelDao;
	}
	
	@Override
	public void evaluateExpression(String expression, RsAsyncCallback<JuelResultDto> callback){
		juelDao.evaluateExpression(expression, callback);
	}
}