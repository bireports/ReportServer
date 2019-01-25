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
 
 
package net.datenwerke.rs.scripting.client.scripting.dto.pa;

import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.Editor.Path;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;
import java.lang.String;
import java.util.List;
import net.datenwerke.dtoservices.dtogenerator.annotations.CorrespondingPoso;
import net.datenwerke.dtoservices.dtogenerator.annotations.GeneratedType;
import net.datenwerke.rs.scripting.client.scripting.dto.AddMenuEntryExtensionDto;
import net.datenwerke.rs.scripting.client.scripting.dto.AddToolbarEntryExtensionDto;
import net.datenwerke.rs.scripting.client.scripting.dto.DisplayConditionDto;
import net.datenwerke.rs.terminal.client.terminal.dto.pa.CommandResultExtensionDtoPA;

/**
 * This file was automatically created by DtoAnnotationProcessor, version 0.1
 */
@GeneratedType("net.datenwerke.dtoservices.dtogenerator.DtoAnnotationProcessor")
@CorrespondingPoso(net.datenwerke.rs.scripting.service.scripting.extensions.AddToolbarEntryExtension.class)
public interface AddToolbarEntryExtensionDtoPA extends CommandResultExtensionDtoPA {


	public static final AddToolbarEntryExtensionDtoPA INSTANCE = GWT.create(AddToolbarEntryExtensionDtoPA.class);


	/* Properties */
	public ValueProvider<AddToolbarEntryExtensionDto,String> arguments();
	public ValueProvider<AddToolbarEntryExtensionDto,Integer> columns();
	public ValueProvider<AddToolbarEntryExtensionDto,List<DisplayConditionDto>> displayConditions();
	public ValueProvider<AddToolbarEntryExtensionDto,List<AddToolbarEntryExtensionDto>> groupEntries();
	public ValueProvider<AddToolbarEntryExtensionDto,String> icon();
	public ValueProvider<AddToolbarEntryExtensionDto,String> javaScript();
	public ValueProvider<AddToolbarEntryExtensionDto,String> label();
	public ValueProvider<AddToolbarEntryExtensionDto,Boolean> large();
	public ValueProvider<AddToolbarEntryExtensionDto,Boolean> left();
	public ValueProvider<AddToolbarEntryExtensionDto,String> scriptLocation();
	public ValueProvider<AddToolbarEntryExtensionDto,List<AddMenuEntryExtensionDto>> subMenuEntries();
	public ValueProvider<AddToolbarEntryExtensionDto,String> toolbarName();


}
