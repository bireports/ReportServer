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
 
 
package net.datenwerke.rs.base.service.parameters.string;

import javax.inject.Provider;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import net.datenwerke.dtoservices.dtogenerator.annotations.ExposeToClient;
import net.datenwerke.dtoservices.dtogenerator.annotations.GenerateDto;
import net.datenwerke.rs.base.service.parameters.string.post.TextParameterInstancePost;
import net.datenwerke.rs.core.service.parameters.entities.ParameterInstance;
import net.datenwerke.rs.core.service.parameters.entities.ParameterInstanceForJuel;
import net.datenwerke.rs.core.service.reportmanager.parameters.ParameterSet;
import net.datenwerke.rs.utils.juel.SimpleJuel;
import net.datenwerke.rs.utils.juel.wrapper.TodayWrapper;
import net.datenwerke.security.service.usermanager.entities.User;

import org.hibernate.envers.Audited;

import com.google.inject.Inject;


/**
 * 
 *
 */
@Entity
@Table(name="TEXT_PARAM_INST")
@Audited
@GenerateDto(
	dtoPackage="net.datenwerke.rs.base.client.parameters.string.dto",
	dto2PosoPostProcessors=TextParameterInstancePost.class,
	poso2DtoPostProcessors=TextParameterInstancePost.class
)
public class TextParameterInstance extends ParameterInstance<TextParameterDefinition> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6923196515527111117L;
	
	@Inject @Transient
	private Provider<SimpleJuel> simpleJuelProvider;
	
	/**
	 * The actual value
	 */
	@ExposeToClient
	@Column(length=4000)
	private String value;
	
	public void setValue(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
	
	@Override
	protected ParameterInstanceForJuel createParameterInstanceForJuel() {
		return new TextParameterInstanceForJuel();
	}
	
	@Override
	protected void configureParameterInstanceForJuel(
			ParameterInstanceForJuel instance, Object value) {
		super.configureParameterInstanceForJuel(instance, value);
		
		TextParameterInstanceForJuel ins = (TextParameterInstanceForJuel) instance;
		if(value instanceof String)
			ins.setValue((String) value);
		else
			ins.setValue(getValue());
		ins.setReturnType(((TextParameterDefinition)getDefinition()).getReturnType());
	}
	
	@Override
	public Object getSelectedValue(User user) {
		String value = getValue();
		
		SimpleJuel juel = simpleJuelProvider.get();
		juel.addReplacement(TodayWrapper.TODAY, new TodayWrapper());
		
		String selectedValue = null == value ? null : juel.parse(value);
		
		return getCastedValue(selectedValue);
	}
	
	protected Object getCastedValue(String value){
		return getDefinition().getCastedValue(value);
	}
	
	@Override
	protected void doParseStringValue(String value){
		setValue(value);
	}

	@Override
	public Object getDefaultValue(User user, ParameterSet parameterSet) {
		String value = ((TextParameterDefinition)getDefinition()).getDefaultValue();
		return getCastedValue(value);
	}

	@Override
	protected Class<?> getType() {
		return ((TextParameterDefinition)getDefinition()).getType();
	}
	

}
