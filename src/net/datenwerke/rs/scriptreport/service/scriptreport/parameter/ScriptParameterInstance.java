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
 
 
package net.datenwerke.rs.scriptreport.service.scriptreport.parameter;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.VariableMapper;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Type;
import org.hibernate.envers.Audited;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import net.datenwerke.dtoservices.dtogenerator.annotations.ExposeToClient;
import net.datenwerke.dtoservices.dtogenerator.annotations.GenerateDto;
import net.datenwerke.rs.core.service.parameters.entities.ParameterDefinitionForJuel;
import net.datenwerke.rs.core.service.parameters.entities.ParameterInstance;
import net.datenwerke.rs.core.service.parameters.entities.ParameterInstanceForJuel;
import net.datenwerke.rs.core.service.reportmanager.parameters.ParameterSet;
import net.datenwerke.rs.core.service.reportmanager.parameters.ParameterValue;
import net.datenwerke.rs.core.service.reportmanager.parameters.ParameterValueImpl;
import net.datenwerke.security.service.usermanager.entities.User;

/**
 * 
 *
 */
@Entity
@Table(name="SCRIPT_PARAM_INST")
@Audited
@GenerateDto(
	dtoPackage="net.datenwerke.rs.scriptreport.client.scriptreport.parameters.dto"
)
public class ScriptParameterInstance extends ParameterInstance<ScriptParameterDefinition> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2332220199796956927L;

	@ExposeToClient(allowArbitraryLobSize=true,
			disableHtmlEncode=true)
	@Lob
	@Type(type = "net.datenwerke.rs.utils.hibernate.RsClobType")
	private String value;
	
	@Override
	public String getSelectedValue(User user) {
		return getValue();
	}

	@Override
	public String getDefaultValue(User user, ParameterSet parameterSet) {
		return ((ScriptParameterDefinition)getDefinition()).getDefaultValue();
	}

	@Override
	protected Class<?> getType() {
		return String.class;
	}
	
	public String getValue() {
		return value;
	}
	
	public void setValue(String value) {
		this.value = value;
	}
	
	@Transient @Override
	public void configureParameterMap(User user, Map<String, ParameterValue> parameterMap, ParameterSet parameterSet) {
		Object value;
		
		if(getDefinition().isEditable() && ! isStillDefault())
			value = this.getSelectedValue(user);
		else
			value = this.getDefaultValue(user, parameterSet);
		
		parameterMap.put(getKey(), new ParameterValueImpl(getKey(), (String)value, String.class));
		
		try{
			JSONObject json = new JSONObject((String)value);
			Iterator keys = json.keys();
			while(keys.hasNext()){
				String key = (String) keys.next();
				Object object = json.get(key);
				
				if(object instanceof JSONArray) {
					object = toList((JSONArray)object);
					value = object;
				}
				
				if(null != object)
					parameterMap.put(getKey() + "_" + key, new ParameterValueImpl(getKey() + "_" + key, value, object.getClass()));
				else
					parameterMap.put(getKey() + "_" + key, new ParameterValueImpl(getKey() + "_" + key, value, Object.class));
			}
		} catch(Exception ex) {
		}
	}
	
	@Override
	public void configureEL(User user, ExpressionFactory factory, ELContext context, ParameterSet parameterSet) {
		VariableMapper vm = context.getVariableMapper();
		
		/* store the objects main value */ 
		String value;
		if(getDefinition().isEditable() && ! isStillDefault())
			value = this.getSelectedValue(user);
		else
			value = this.getDefaultValue(user, parameterSet);
		
		/* provide access to the actual object */
		ParameterInstanceForJuel instanceForJuel = createParameterInstanceForJuel();
		configureParameterInstanceForJuel(instanceForJuel, value);
		vm.setVariable("_" + getKey(), factory.createValueExpression(instanceForJuel, ParameterInstanceForJuel.class)); //$NON-NLS-1$
		
		ParameterDefinitionForJuel definitionForJuel = getDefinition().createParameterDefinitionForJuel();
		getDefinition().configureParameterDefinitionForJuel(definitionForJuel);
		vm.setVariable("__" + getKey(), factory.createValueExpression(definitionForJuel, ParameterDefinitionForJuel.class)); //$NON-NLS-1$
		
		if(null != value)
			vm.setVariable(getKey(), factory.createValueExpression(value, String.class));
		else
			vm.setVariable(getKey(), factory.createValueExpression(null, Object.class));
		
		try{
			JSONObject json = new JSONObject(value);
			Iterator keys = json.keys();
			while(keys.hasNext()){
				String key = (String) keys.next();
				Object object = json.get(key);
				
				if(object instanceof JSONArray)
					object = toList((JSONArray)object);
				
				if(null != value)
					vm.setVariable(getKey() + "_" + key, factory.createValueExpression(object, object.getClass()));
				else
					vm.setVariable(getKey() + "_" + key, factory.createValueExpression(null, Object.class));
			}
		} catch(Exception ex) {
		}
	}
	
	private List toList(JSONArray jsonArray) {
		List list = new ArrayList();
		int len = jsonArray.length();
		for (int i=0;i<len;i++) {
			try {
				list.add(jsonArray.get(i));
			} catch (JSONException e) {
				throw new IllegalStateException("Could not parse script parameter result.", e);
			}
		}
		return list;
	}

}
