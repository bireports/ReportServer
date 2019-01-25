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
 
 
package net.datenwerke.rs.dashboard.service.dashboard.entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import javax.persistence.Version;

import net.datenwerke.dtoservices.dtogenerator.annotations.ExposeToClient;
import net.datenwerke.dtoservices.dtogenerator.annotations.GenerateDto;
import net.datenwerke.rs.dashboard.client.dashboard.dto.DadgetDto;

import org.hibernate.envers.Audited;

import com.google.inject.Injector;

@Audited
@Entity
@Table(name="DADGET")
@Inheritance(strategy=InheritanceType.JOINED)
@GenerateDto(
	dtoPackage="net.datenwerke.rs.dashboard.client.dashboard.dto",
	createDecorator=true,
	abstractDto=true
)
public abstract class Dadget implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9148252532248599659L;
	
	@ExposeToClient
	private int n = 1;
	
	@ExposeToClient
	private int col = 1;
	
	@ExposeToClient
	private int height = 250;
	
	@ExposeToClient
	private long reloadInterval = -1; 

	@ExposeToClient
	private DadgetContainer container = DadgetContainer.CENTER;
	
	@Version
	private Long version;
	
	@ExposeToClient(id=true)
	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id){
		this.id = id;
	}
	
    public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}
	
	final public void setN(int n) {
		this.n = n;
	}

	final public int getN() {
		return n;
	}

	public void setCol(int column) {
		this.col = column;
	}

	public int getCol() {
		return col;
	}
	
	public long getReloadInterval() {
		return reloadInterval;
	}
	
	public void setReloadInterval(long reloadInterval) {
		this.reloadInterval = reloadInterval;
	}

	public void init() {
		
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getHeight() {
		return height;
	}
	
	public DadgetContainer getContainer() {
		return container;
	}
	
	public void setContainer(DadgetContainer container) {
		this.container = container;
	}

}
