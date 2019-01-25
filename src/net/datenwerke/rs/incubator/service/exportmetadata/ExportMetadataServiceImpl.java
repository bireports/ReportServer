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
 
 
package net.datenwerke.rs.incubator.service.exportmetadata;

import net.datenwerke.rs.base.service.parameterreplacements.provider.ReportForJuel;
import net.datenwerke.rs.base.service.parameterreplacements.provider.UserForJuel;
import net.datenwerke.rs.core.service.reportmanager.entities.reports.Report;
import net.datenwerke.rs.incubator.service.exportmetadata.annotations.ExportMetadataModuleAuthor;
import net.datenwerke.rs.incubator.service.exportmetadata.annotations.ExportMetadataModuleCreator;
import net.datenwerke.rs.incubator.service.exportmetadata.annotations.ExportMetadataModuleTitle;
import net.datenwerke.rs.utils.juel.SimpleJuel;
import net.datenwerke.security.service.usermanager.entities.User;

import com.google.inject.Inject;
import com.google.inject.Provider;

public class ExportMetadataServiceImpl implements ExportMetadataService {

	private static final String REPORT_REPLACEMENT_KEY = "report";
	private static final String USER_REPLACEMENT_KEY = "user";
	
	private final Provider<String> authorProvider;
	private final Provider<String> titleProvider;
	private final Provider<String> creatorProvider;
	
	private final Provider<SimpleJuel> simpleJuelProvider;

	/**
	 * All three elements are configurable in the Export metadata configuration
	 * page.
	 * 
	 * @param author The author of the to be generated PDF file.
	 * @param title  The title of the to be generated PDF file.
	 * @param creator The creator of the to be generated PDF file.
	 */
	@Inject
	public ExportMetadataServiceImpl(
			@ExportMetadataModuleAuthor Provider<String> author,
			@ExportMetadataModuleTitle Provider<String> title,
			@ExportMetadataModuleCreator Provider<String> creator,
			Provider<SimpleJuel> simpleJuelProvider
			) {
		
		/* store objects */
		this.authorProvider = author;
		this.titleProvider = title;
		this.creatorProvider = creator;
		
		this.simpleJuelProvider = simpleJuelProvider;
	}
	
	
	@Override
	public String getAuthor(){
		return authorProvider.get();
	}
	
	@Override
	public String getCreator(){
		return creatorProvider.get();
	}

	@Override
	public String getTitle(){
		return titleProvider.get();
	}

	@Override
	public String getAuthor(Report report, User user){
		return parse(report, user, getAuthor());
	}
	
	@Override
	public String getCreator(Report report, User user){
		return parse(report, user, getCreator());
	}
	
	@Override
	public String getTitle(Report report, User user){
		return parse(report, user, getTitle());
	}


	private String parse(Report report, User user, String template) {
		if(null == template)
			return "";
		
		try{
			return getJuel(report, user).parse(template);
		} catch (Exception e) {
			return "Could not parse template: " + String.valueOf(template);
		}
	}


	private SimpleJuel getJuel(Report report, User user) {
		SimpleJuel juel = simpleJuelProvider.get();
		juel.addReplacement(REPORT_REPLACEMENT_KEY, new ReportForJuel(report));
		juel.addReplacement(USER_REPLACEMENT_KEY, UserForJuel.createInstance(user));
		
		return juel;
	}
	
}
