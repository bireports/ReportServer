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
 
 
package net.datenwerke.security.client.security.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.datenwerke.security.client.treedb.dto.SecuredAbstractNodeDto;

/**
 * Contains all the information needed to display the security view for an "ACE container".
 *
 */
public class SecurityViewInformation implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4590378711038525589L;

	private List<AceDto> aces = new ArrayList<AceDto>();
	private List<AceDto> inheritedAces = new ArrayList<AceDto>();
	private Map<AceDto,SecuredAbstractNodeDto> referencesToInheritedNodes = new HashMap<AceDto, SecuredAbstractNodeDto>();
	
	private Collection<SecureeDto> availableSecurees = new ArrayList<SecureeDto>();
	
	public void setAces(List<AceDto> aces) {
		this.aces = aces;
	}

	public List<AceDto> getAces() {
		return aces;
	}

	public void setAvailableSecurees(Collection<SecureeDto> availableSecurees) {
		this.availableSecurees = availableSecurees;
	}

	public Collection<SecureeDto> getAvailableSecurees() {
		return availableSecurees;
	}

	public void setInheritedAces(List<AceDto> inheritedAces) {
		this.inheritedAces = inheritedAces;
	}

	public List<AceDto> getInheritedAces() {
		return inheritedAces;
	}

	public void setReferencesToInheritedNodes(Map<AceDto,SecuredAbstractNodeDto> referencesToInheritedNodes) {
		this.referencesToInheritedNodes = referencesToInheritedNodes;
	}

	public Map<AceDto,SecuredAbstractNodeDto> getReferencesToInheritedNodes() {
		return referencesToInheritedNodes;
	}

}
