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
 
 
package net.datenwerke.rs.base.service.datasources.definitions;

import java.io.IOException;
import java.io.InputStream;

import javax.persistence.CascadeType;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToOne;

import net.datenwerke.dtoservices.dtogenerator.annotations.ExposeToClient;
import net.datenwerke.dtoservices.dtogenerator.annotations.GenerateDto;
import net.datenwerke.gxtdto.client.dtomanager.DtoView;
import net.datenwerke.rs.base.service.datasources.connectors.DatasourceConnector;
import net.datenwerke.rs.core.service.datasourcemanager.entities.DatasourceDefinition;
import net.datenwerke.rs.utils.entitycloner.annotation.EnclosedEntity;

import org.hibernate.envers.Audited;

@GenerateDto(
	dtoPackage="net.datenwerke.rs.base.client.datasources.dto",
	abstractDto=true
)
@MappedSuperclass
public abstract class FormatBasedDatasourceDefinition extends DatasourceDefinition {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1595308818017167937L;

	@EnclosedEntity
	@OneToOne(cascade=CascadeType.ALL)
	@ExposeToClient(view=DtoView.MINIMAL)
	@Audited
	private DatasourceConnector connector;

	public void setConnector(DatasourceConnector connector) {
		this.connector = connector;
	}

	public DatasourceConnector getConnector() {
		return connector;
	}

	public InputStream getDataStream(FormatBasedDatasourceConfig dsConfig) throws IOException{
		return getConnector().getDataStream(dsConfig);
	}
}
