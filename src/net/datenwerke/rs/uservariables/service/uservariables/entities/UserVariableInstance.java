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
 
 
package net.datenwerke.rs.uservariables.service.uservariables.entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Version;

import net.datenwerke.dtoservices.dtogenerator.annotations.ExposeToClient;
import net.datenwerke.dtoservices.dtogenerator.annotations.GenerateDto;
import net.datenwerke.rs.core.service.parameters.entities.ParameterInstance;
import net.datenwerke.security.service.usermanager.entities.AbstractUserManagerNode;
import net.datenwerke.security.service.usermanager.entities.OrganisationalUnit;
import net.datenwerke.security.service.usermanager.entities.User;

import org.hibernate.envers.Audited;

/**
 * 
 *
 */
@Entity
@Table(name="USERVAR_INST")
@Audited
@Inheritance(strategy = InheritanceType.JOINED)
@GenerateDto(
	dtoPackage="net.datenwerke.rs.uservariables.client.uservariables.dto",
	abstractDto=true
)
abstract public class UserVariableInstance implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5940158658373392060L;

	@ExposeToClient
	@ManyToOne(targetEntity=UserVariableDefinition.class)
	private UserVariableDefinition definition;
	
	@ExposeToClient
	@ManyToOne
	private AbstractUserManagerNode folk;
	
	@Version
	private Long version;

	@ExposeToClient(id=true)
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}

	public void setDefinition(UserVariableDefinition definition) {
		this.definition = definition;
	}

	public UserVariableDefinition getDefinition() {
		return definition;
	}

	public final String getName() {
		return definition.getName();
	}
	
	public void setFolk(AbstractUserManagerNode folk) {
		if(! ((folk instanceof User) || (folk instanceof OrganisationalUnit)) )
			throw new IllegalArgumentException("User Variables may only be assigned to Users and OUs."); //$NON-NLS-1$
		
		this.folk = folk;
	}

	public AbstractUserManagerNode getFolk() {
		return folk;
	}
	
	public abstract Object getVariableValue();

	@Override
	public int hashCode() {
		if (null == id)
			return super.hashCode();
		return id.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (null == obj || !(obj instanceof ParameterInstance))
			return false;

		ParameterInstance p = (ParameterInstance) obj;
		if (null == p.getId())
			return getId() == null;

		return p.getId().equals(getId());
	}

}
