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
 
 
package net.datenwerke.security.service.security.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Version;

import net.datenwerke.rs.utils.entitycloner.annotation.EnclosedEntity;

import org.hibernate.envers.Audited;

/**
 * 
 *
 */
@Table(name="ACL")
@Inheritance(strategy=InheritanceType.JOINED)
@Entity
@Audited
public class Acl implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7837175609268593363L;

	@Id @GeneratedValue
    private Long id;
	
	@Version
    private Integer version;

	@OneToMany(cascade={CascadeType.ALL}, mappedBy="acl")
	@OrderBy("n")
	@EnclosedEntity
	private List<Ace> aces = new ArrayList<Ace>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    
    public Integer getVersion() {
        return version;
    }

    private void setVersion(Integer version) {
        this.version = version;
    }

	public List<Ace> getAces() {
		return aces;
	}

	private void setAces(List<Ace> aces) {
		this.aces = aces;
	}
	
	public void addAce(Ace ace){
		aces.add(ace);
		ace.setN(aces.size());
		ace.setAcl(this);
	}
	
	public void addAce(Ace ace, int position){
		if(aces.size() < position)
			addAce(ace);
		else {
			aces.add(position, ace);
			int n = 0;
			for(Ace oACE : aces)
				oACE.setN(n++);
			ace.setAcl(this);
		}
	}
	

	public void removeACE(Ace ace) {
		aces.remove(ace);
		int n = 0;
		for(Ace oACE : aces)
			oACE.setN(n++);
	}

	@Override
	public boolean equals(Object obj) {
		/* returns true if objects are in the same tree and have the same id */
    	if(! (obj instanceof Acl))
    		return false;
    	
    	/* cast object */
    	Acl acl = null;
    	try {
    		acl = (Acl) obj;
    	} catch(ClassCastException e){
    		return false;
    	}
    	
    	/* test id */
    	if(null == getId() && null != acl.getId())
    		return false;
    	if(null != getId() && ! getId().equals(acl.getId()))
    		return false;
    	
    	return true;
	}
	
    @Override
    public int hashCode() {
    	if(null != getId())
    		return getId().hashCode();
    	
    	return super.hashCode();
    }

    
}
