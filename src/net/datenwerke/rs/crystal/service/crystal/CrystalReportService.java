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
 
 
package net.datenwerke.rs.crystal.service.crystal;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import net.datenwerke.rs.core.service.reportmanager.parameters.ParameterSet;
import net.datenwerke.rs.crystal.service.crystal.entities.CrystalReportFile;

import com.crystaldecisions.sdk.occa.report.application.ReportClientDocument;
import com.crystaldecisions.sdk.occa.report.lib.ReportSDKException;


public interface CrystalReportService {
	
	
	public void remove(CrystalReportFile file);

	public boolean isCrystalEnabled();

	public void replaceDatasourceWithJndi(ReportClientDocument reportClientDoc, String dsJndiName) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, ReportSDKException;

	public List<CrystalParameterProposal> extractParameters(CrystalReportFile reportFile) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException, InstantiationException, IOException, ReportSDKException;

	ReportClientDocument openReportClientDoc(CrystalReportFile crFile) throws IOException,
			IllegalArgumentException, IllegalAccessException,
			InvocationTargetException, InstantiationException, ReportSDKException;

	public void setParameters(ReportClientDocument reportClientDoc, ParameterSet parameters) throws ReportSDKException;

}
