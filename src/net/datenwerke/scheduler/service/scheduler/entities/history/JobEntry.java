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
 
 
package net.datenwerke.scheduler.service.scheduler.entities.history;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Version;

import net.datenwerke.dtoservices.dtogenerator.annotations.ExposeToClient;
import net.datenwerke.dtoservices.dtogenerator.annotations.GenerateDto;
import net.datenwerke.rs.utils.entitycloner.annotation.EnclosedEntity;
import net.datenwerke.scheduler.service.scheduler.entities.Outcome;

import org.hibernate.annotations.Type;

@GenerateDto(
	dtoPackage="net.datenwerke.scheduler.client.scheduler.dto.history"
)
@Entity
@Table(name="SCHED_HIST_JOB_ENTRY")
public class JobEntry {
	
	@ExposeToClient
	private Outcome outcome = Outcome.EXECUTING;
	
	@ExposeToClient
	@JoinTable(name="SCHED_JOB_ENT_2_PROP")
	@EnclosedEntity
	@OneToMany(cascade=CascadeType.ALL)
	private Set<HistoryEntryProperty> historyProperties = new HashSet<HistoryEntryProperty>();

	@ExposeToClient
	@Lob
	@Type(type = "net.datenwerke.rs.utils.hibernate.RsClobType")
	private String errorDescription;

	@Version
	private Long version;
	
	@ExposeToClient(id=true)
	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setOutcome(Outcome outcome) {
		this.outcome = outcome;
	}

	public Outcome getOutcome() {
		return outcome;
	}

	public void setHistoryProperties(Set<HistoryEntryProperty> historyProperties) {
		this.historyProperties = historyProperties;
	}

	public Set<HistoryEntryProperty> getHistoryProperties() {
		return historyProperties;
	}

	public void addHistoryProperty(String key, String value) {
		HistoryEntryProperty property = new HistoryEntryProperty(key, value);
		this.historyProperties.add(property);
	}
	
	public void addHistoryProperty(HistoryEntryProperty historyProperty) {
		this.historyProperties.add(historyProperty);
	}

	
	public void setErrorDescription(String description) {
		this.errorDescription = description;
	}

	public String getErrorDescription() {
		return errorDescription;
	}

	public void addHistoryProperty(String key, Long value) {
		addHistoryProperty(key, null == value ? "null" : value.toString());
	}
	
	
	
}
