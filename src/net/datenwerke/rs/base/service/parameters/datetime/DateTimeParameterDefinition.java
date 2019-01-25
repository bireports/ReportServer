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
 
 
package net.datenwerke.rs.base.service.parameters.datetime;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

import net.datenwerke.dtoservices.dtogenerator.annotations.ExposeToClient;
import net.datenwerke.dtoservices.dtogenerator.annotations.GenerateDto;
import net.datenwerke.rs.base.client.parameters.locale.RsMessages;
import net.datenwerke.rs.core.service.parameters.entities.ParameterDefinition;
import net.datenwerke.rs.core.service.parameters.entities.ParameterDefinitionForJuel;

import org.hibernate.envers.Audited;

/**
 * 
 * <p>
 *  Properties:
 *  <dl>
 *   <dd>value</dd>
 *   <dt>The actual date/time</dt>
 *   <dd>mode</dd>
 *   <dt>
 *    <ul>
 *     <li>1 : only ask for date</li>
 *     <li>2 : only ask for time</li>
 *     <li>3 : ask for date and time</li>
 *    </ul>
 *   </dt>
 *   <dd>useNowAsDefault</dd>
 *   <dt>use the current time as default</dt>
 *  </dl>
 * </p>
 *
 */
@Entity
@Table(name="DATETIME_PARAM_DEF")
@Audited
@GenerateDto(
	dtoPackage="net.datenwerke.rs.base.client.parameters.datetime.dto",
	displayTitle="RsMessages.INSTANCE.dateTimeParameterText()",
	additionalImports=RsMessages.class,
	createDecorator=true
)
public class DateTimeParameterDefinition extends ParameterDefinition<DateTimeParameterInstance> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7342720295847247699L;

	@ExposeToClient
	private String formula;
	
	@ExposeToClient
	private Mode mode = Mode.Date;
	
	@ExposeToClient
	private Boolean useNowAsDefault = true;
	
	@ExposeToClient
	private Date defaultValue;
	
	public void setUseNowAsDefault(Boolean useNow){
		if(null == useNow)
			useNow = true;
		this.useNowAsDefault = useNow;
	}
	
	public Boolean isUseNowAsDefault(){
		return useNowAsDefault;
	}
	
	public void setMode(Mode mode){
		if(null == mode)
			mode = Mode.Date;
		this.mode = mode;
	}
	
	public Mode getMode(){
		return mode;
	}
	
	public Date getDefaultValue() {
		return defaultValue;
	}

	
	public void setDefaultValue(Date defaultValue) {
		this.defaultValue = defaultValue;
	}

	@Override
	public void initWithDefaultValues(){
		super.initWithDefaultValues();
		useNowAsDefault = true;
	}
	
	@Override
	protected DateTimeParameterInstance doCreateParameterInstance(){
		return new DateTimeParameterInstance();
	}
	
	@Override
	public ParameterDefinitionForJuel createParameterDefinitionForJuel() {
		return new DateTimeParameterDefinitionForJuel();
	}
	
	@Override
	public void configureParameterDefinitionForJuel(
			ParameterDefinitionForJuel definition) {
		super.configureParameterDefinitionForJuel(definition);
		
		DateTimeParameterDefinitionForJuel def = (DateTimeParameterDefinitionForJuel) definition;
		
		def.setDefaultValue(defaultValue);
		def.setMode(mode);
		def.setUseNowAsDefault(useNowAsDefault);
	}

	public void setFormula(String formula) {
		this.formula = formula;
	}

	public String getFormula() {
		return formula;
	}

	
}
