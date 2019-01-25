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
 
 
package net.datenwerke.treedb.client.treedb.dto.pa;

import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.Editor.Path;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;
import java.lang.Boolean;
import java.lang.Long;
import java.lang.String;
import java.util.ArrayList;
import java.util.Date;
import net.datenwerke.dtoservices.dtogenerator.annotations.CorrespondingPoso;
import net.datenwerke.dtoservices.dtogenerator.annotations.GeneratedType;
import net.datenwerke.treedb.client.treedb.dto.AbstractNodeDto;
import net.datenwerke.treedb.client.treedb.dto.decorator.AbstractNodeDtoDec;

/**
 * This file was automatically created by DtoAnnotationProcessor, version 0.1
 */
@GeneratedType("net.datenwerke.dtoservices.dtogenerator.DtoAnnotationProcessor")
@CorrespondingPoso(net.datenwerke.treedb.service.treedb.AbstractNode.class)
public interface AbstractNodeDtoPA extends PropertyAccess<AbstractNodeDto> {


	public static final AbstractNodeDtoPA INSTANCE = GWT.create(AbstractNodeDtoPA.class);

	@Path("dtoId")
	public ModelKeyProvider<AbstractNodeDto> dtoId();

	/* Properties */
	public ValueProvider<AbstractNodeDto,Date> createdOn();
	public ValueProvider<AbstractNodeDto,Long> flags();
	public ValueProvider<AbstractNodeDto,Long> id();
	public ValueProvider<AbstractNodeDto,Date> lastUpdated();
	public ValueProvider<AbstractNodeDto,Integer> position();
	public ValueProvider<AbstractNodeDto,Boolean> hasChildren();
	public ValueProvider<AbstractNodeDto,Long> parentNodeId();
	public ValueProvider<AbstractNodeDto,String> parentNodeType();
	public ValueProvider<AbstractNodeDto,ArrayList<Long>> rootPath();
	public ValueProvider<AbstractNodeDto,String> rootName();


}
