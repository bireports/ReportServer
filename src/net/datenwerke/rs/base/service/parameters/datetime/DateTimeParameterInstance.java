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
 
 
package net.datenwerke.rs.base.service.parameters.datetime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.inject.Provider;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import net.datenwerke.dtoservices.dtogenerator.annotations.ExposeToClient;
import net.datenwerke.dtoservices.dtogenerator.annotations.GenerateDto;
import net.datenwerke.rs.base.service.parameters.datasource.DatasourceParameterServiceImpl;
import net.datenwerke.rs.base.service.parameters.datetime.dtogen.DateTimeParameterInstance2DtoPostProcessor;
import net.datenwerke.rs.base.service.parameters.datetime.dtogen.DateTimeParameterInstance2PosoPostProcessor;
import net.datenwerke.rs.base.service.parameters.locale.BaseParameterMessages;
import net.datenwerke.rs.core.service.parameters.entities.ParameterInstance;
import net.datenwerke.rs.core.service.parameters.entities.ParameterInstanceForJuel;
import net.datenwerke.rs.core.service.reportmanager.parameters.ParameterSet;
import net.datenwerke.rs.utils.config.ConfigService;
import net.datenwerke.rs.utils.juel.SimpleJuel;
import net.datenwerke.rs.utils.juel.wrapper.TodayWrapper;
import net.datenwerke.security.service.usermanager.entities.User;

import org.apache.commons.configuration.Configuration;
import org.codehaus.jackson.map.util.ISO8601DateFormat;
import org.hibernate.envers.Audited;

import com.google.inject.Inject;

/**
 * 
 *
 */
@Entity
@Table(name="DATETIME_PARAM_INST")
@Audited
@GenerateDto(
	dtoPackage="net.datenwerke.rs.base.client.parameters.datetime.dto",
	dto2PosoPostProcessors=DateTimeParameterInstance2PosoPostProcessor.class,
	poso2DtoPostProcessors=DateTimeParameterInstance2DtoPostProcessor.class,
	createDecorator=true
)

public class DateTimeParameterInstance extends ParameterInstance<DateTimeParameterDefinition> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6280772628417470631L;
	
	@Inject @Transient
	private Provider<SimpleJuel> simpleJuelProvider;
	
	@Inject @Transient
	private ConfigService configService;
	
	@ExposeToClient
	private Date value;
	
	@ExposeToClient @Transient
	private String strValue;
	
	@ExposeToClient
	private String formula;
	
	/**
	 * Sets the actual value of this parameter
	 * @param date
	 */
	public void setValue(Date date){
		this.value = date;
	}
	
	public Date getValue(){
		return value;
	}
	
	public void setFormula(String formula) {
		this.formula = formula;
	}

	public String getFormula() {
		return formula;
	}
	
	@Override
	public Object getSelectedValue(User user) {
		if(null != getFormula() && ! "".equals(getFormula().trim()))
			return parseFormula(getFormula());
		return getValue();
	}

	private Date parseFormula(String formula) {
		SimpleJuel juel = simpleJuelProvider.get();
		juel.addReplacement(TodayWrapper.TODAY, new TodayWrapper());
		Object obj = juel.parseAsObject(null != formula ? formula.trim() : "");
		if(obj instanceof Date)
			return (Date)obj;
		if(obj instanceof TodayWrapper)
			return ((TodayWrapper)obj).getDate();
		throw new IllegalArgumentException(BaseParameterMessages.INSTANCE.errorParseDateFormula(formula));
	}

	@Override
	protected void doParseStringValue(String value){
		Configuration cfg = configService.getConfigFailsafe(DatasourceParameterServiceImpl.CONFIG_FILE);
		String pattern = cfg.getString("parameter.date.urlpattern", null);
		try {
			Date dateVal;
			
			if(null != pattern && !pattern.isEmpty()){
				dateVal = new SimpleDateFormat(pattern).parse(value);
			}else{
				dateVal = new ISO8601DateFormat().parse(value);
			}
			
			setValue(dateVal);
		} catch (ParseException e) {
			IllegalArgumentException iae = new IllegalArgumentException("Could not parse date: " + value); //$NON-NLS-1$
			iae.initCause(e);
			throw iae;
		}
	}
	
	@Override
	protected ParameterInstanceForJuel createParameterInstanceForJuel() {
		return new DateTimeParameterInstanceForJuel();
	}
	
	@Override
	protected void configureParameterInstanceForJuel(
			ParameterInstanceForJuel instance, Object value) {
		super.configureParameterInstanceForJuel(instance, value);
		
		DateTimeParameterInstanceForJuel inst = (DateTimeParameterInstanceForJuel) instance;
		
		if(value instanceof Date)
			inst.setValue((Date)value);
		else
			inst.setValue(getValue());
	}

	@Override
	public Object getDefaultValue(User user, ParameterSet parameterSet) {
		DateTimeParameterDefinition definition = (DateTimeParameterDefinition) getDefinition();
		
		if(null != definition.getFormula() && ! "".equals(definition.getFormula().trim()))
			return parseFormula(definition.getFormula());

		
		if(definition.isUseNowAsDefault())
			return Calendar.getInstance().getTime();

		return definition.getDefaultValue();
	}

	@Override
	protected Class<?> getType() {
		return Date.class;
	}

	public String getStrValue() {
		return strValue;
	}

	public void setStrValue(String strValue) {
		this.strValue = strValue;
	}



	
}
