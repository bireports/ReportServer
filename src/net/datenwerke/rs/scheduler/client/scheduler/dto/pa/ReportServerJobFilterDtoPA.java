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
 
 
package net.datenwerke.rs.scheduler.client.scheduler.dto.pa;

import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.Editor.Path;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;
import java.util.Set;
import net.datenwerke.dtoservices.dtogenerator.annotations.CorrespondingPoso;
import net.datenwerke.dtoservices.dtogenerator.annotations.GeneratedType;
import net.datenwerke.rs.scheduler.client.scheduler.dto.ReportServerJobFilterDto;
import net.datenwerke.scheduler.client.scheduler.dto.filter.pa.JobFilterConfigurationDtoPA;
import net.datenwerke.security.client.usermanager.dto.UserDto;

/**
 * This file was automatically created by DtoAnnotationProcessor, version 0.1
 */
@GeneratedType("net.datenwerke.dtoservices.dtogenerator.DtoAnnotationProcessor")
@CorrespondingPoso(net.datenwerke.rs.scheduler.service.scheduler.jobs.filter.ReportServerJobFilter.class)
public interface ReportServerJobFilterDtoPA extends JobFilterConfigurationDtoPA {


	public static final ReportServerJobFilterDtoPA INSTANCE = GWT.create(ReportServerJobFilterDtoPA.class);


	/* Properties */
	public ValueProvider<ReportServerJobFilterDto,Boolean> fromCurrentUser();
	public ValueProvider<ReportServerJobFilterDto,UserDto> fromUser();
	public ValueProvider<ReportServerJobFilterDto,Set<Long>> reports();
	public ValueProvider<ReportServerJobFilterDto,Boolean> toCurrentUser();
	public ValueProvider<ReportServerJobFilterDto,UserDto> toUser();


}
