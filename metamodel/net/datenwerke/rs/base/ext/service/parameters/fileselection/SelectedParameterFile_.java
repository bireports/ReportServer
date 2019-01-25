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
 
 
package net.datenwerke.rs.base.ext.service.parameters.fileselection;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import net.datenwerke.rs.fileserver.service.fileserver.entities.AbstractFileServerNode;
import net.datenwerke.rs.tsreportarea.service.tsreportarea.entities.AbstractTsDiskNode;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(SelectedParameterFile.class)
public abstract class SelectedParameterFile_ {

	public static volatile SingularAttribute<SelectedParameterFile, AbstractFileServerNode> fileServerFile;
	public static volatile SingularAttribute<SelectedParameterFile, AbstractTsDiskNode> teamSpaceFile;
	public static volatile SingularAttribute<SelectedParameterFile, String> name;
	public static volatile SingularAttribute<SelectedParameterFile, Long> id;
	public static volatile SingularAttribute<SelectedParameterFile, Long> version;
	public static volatile SingularAttribute<SelectedParameterFile, UploadedParameterFile> uploadedFile;

}

