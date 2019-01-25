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
 
 
package net.datenwerke.rs.core.service.reportmanager.metadata;

import net.datenwerke.rs.core.service.parameters.entities.ParameterDefinition;
import net.datenwerke.rs.core.service.parameters.entities.ParameterInstance;
import net.datenwerke.rs.core.service.reportmanager.ReportExecutorService;
import net.datenwerke.rs.core.service.reportmanager.engine.basereports.CompiledReportMetadata;
import net.datenwerke.rs.core.service.reportmanager.entities.reports.Report;
import net.datenwerke.security.service.usermanager.entities.User;

abstract public class AbstractPlainMetadataExporter implements ReportMetadataExporter {

	protected static final String SEPARATOR = "-----------------------------------\n";
	
	protected final StringBuilder dataBuilder;
	
	public AbstractPlainMetadataExporter(){
		dataBuilder = new StringBuilder();
		init();
	}
	
	protected void init() {
		dataBuilder.append(SEPARATOR);
	}

	@Override
	public String[] getFormats() {
		return new String[]{ReportExecutorService.METADATA_FORMAT_PLAIN};
	}

	@Override
	public CompiledReportMetadata getMetadata() {
		return new PlainMetadata(dataBuilder.toString());
	}

	protected void beginSection(String header){
		dataBuilder.append('\n')
				.append(SEPARATOR)
				.append("++ ").append(header.toUpperCase()).append(" ++\n");
	
	}
	
	@Override
	public void beginParameterSection() {
		beginSection("Parameter");
	}
	
	@Override
	public void visitParameter(ParameterInstance instance, ParameterDefinition definition, User user) {
		if(definition.isHidden())
			return;
		
		dataBuilder.append(definition.getName())
					.append(instance.toInformationString(user))
				   .append('\n');
	}

	@Override
	public void visitUser(User user) {
	}
	
	@Override
	public void visitReport(Report report) {
		dataBuilder.append("Berichtsname: " + report.getName() + "\n");
		dataBuilder.append("Beschreibung: " + report.getDescription() + "\n");
	}
	
	@Override
	public void cleanUp(){}

}
