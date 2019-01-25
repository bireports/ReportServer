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
 
 
package net.datenwerke.rs.computedcolumns.service.computedcolumns.tokenizer.functions;

import java.util.List;

import net.datenwerke.rs.computedcolumns.service.computedcolumns.annotations.AllowedFunctions;
import net.datenwerke.rs.computedcolumns.service.computedcolumns.hooks.FunctionProviderHook;
import net.datenwerke.rs.computedcolumns.service.computedcolumns.tokenizer.ExpressionTokenizer;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;

public class BaseFunctionProvider implements FunctionProviderHook {
	
	private final Logger logger = LoggerFactory.getLogger(getClass().getName());

	@Inject @AllowedFunctions
	private List<String> functions;
	
	@Override
	public boolean consumes(String strToken, ExpressionTokenizer expressionTokenizer) {
		try {
			if(strToken.contains("(") && StringUtils.countMatches(strToken, ")") != StringUtils.countMatches(strToken, "("))
				return false;
			
			for(String func : functions){
				if(func.toLowerCase().equals(strToken.toLowerCase()))
					return true;
			}
			
		} catch(Exception e){
			logger.info( "Exception while processing funciton", e);
		}
		
		return false;
	}

}
