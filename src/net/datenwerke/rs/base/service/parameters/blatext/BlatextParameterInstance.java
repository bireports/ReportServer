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
 
 
package net.datenwerke.rs.base.service.parameters.blatext;

import javax.persistence.Entity;
import javax.persistence.Table;

import net.datenwerke.dtoservices.dtogenerator.annotations.GenerateDto;
import net.datenwerke.rs.core.service.parameters.entities.ParameterInstance;
import net.datenwerke.rs.core.service.reportmanager.parameters.ParameterSet;
import net.datenwerke.security.service.usermanager.entities.User;

import org.hibernate.envers.Audited;

/**
 * 
 *
 */
@Entity
@Table(name="BLATEXT_PARAM_INST")
@Audited
@GenerateDto(
	dtoPackage="net.datenwerke.rs.base.client.parameters.blatext.dto"
)
public class BlatextParameterInstance extends ParameterInstance<BlatextParameterDefinition> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2332220199796956927L;

	@Override
	public Object getSelectedValue(User user) {
		return ((BlatextParameterDefinition)getDefinition()).getValue();
	}

	@Override
	public Object getDefaultValue(User user, ParameterSet parameterSet) {
		return getSelectedValue(user);
	}

	@Override
	protected Class<?> getType() {
		return String.class;
	}

}
