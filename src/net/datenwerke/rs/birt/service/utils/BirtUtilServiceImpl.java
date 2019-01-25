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
 
 
package net.datenwerke.rs.birt.service.utils;

import java.io.ByteArrayInputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import net.datenwerke.rs.birt.service.reportengine.BirtHelper;
import net.datenwerke.rs.birt.service.reportengine.entities.BirtReportFile;

import org.apache.commons.lang.StringUtils;
import org.eclipse.birt.core.exception.BirtException;
import org.eclipse.birt.report.engine.api.EngineException;
import org.eclipse.birt.report.engine.api.IParameterDefn;

import com.google.inject.Provider;

public class BirtUtilServiceImpl implements BirtUtilService {

	private Provider<BirtHelper> birtHelperProvider;

	@Inject
	public BirtUtilServiceImpl(Provider<BirtHelper> birtHelperProvider) {
		this.birtHelperProvider = birtHelperProvider;
	}

	@Override
	public List<BirtParameterProposal> extractParameters(BirtReportFile reportFile) {
		List<BirtParameterProposal> proposals = new ArrayList<BirtParameterProposal>();
		
		BirtHelper birtHelper = birtHelperProvider.get();
		try {
			birtHelper.loadReportDesign(new ByteArrayInputStream(reportFile.getContent().getBytes()));
			
			for(IParameterDefn pd : birtHelper.getParameterDefinitions()){
				BirtParameterProposal bpp = new BirtParameterProposal();
				bpp.setKey(pd.getName());
				bpp.setName(StringUtils.isEmpty(pd.getDisplayName()) ? pd.getName() : pd.getDisplayName());
		
				switch(pd.getDataType()){
				case IParameterDefn.TYPE_STRING:
					bpp.setType(String.class.getName());
					break;
				case IParameterDefn.TYPE_BOOLEAN:
					bpp.setType(Boolean.class.getName());
					break;
				case IParameterDefn.TYPE_DATE:
					bpp.setType(Date.class.getName());
					break;
				case IParameterDefn.TYPE_DECIMAL:
					bpp.setType(BigDecimal.class.getName());
					break;
				case IParameterDefn.TYPE_FLOAT:
					bpp.setType(Double.class.getName());
					break;
				case IParameterDefn.TYPE_INTEGER:
					bpp.setType(Integer.class.getName());
					break;
				}

				proposals.add(bpp);
			}
		} catch (EngineException e) {
			throw new RuntimeException(e);
		} catch (BirtException e) {
			throw new RuntimeException(e);
		} finally {
			birtHelper.cleanup();
		}

		return proposals;
	}

}
