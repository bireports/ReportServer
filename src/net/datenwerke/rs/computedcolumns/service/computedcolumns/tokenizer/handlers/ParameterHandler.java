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
 
 
package net.datenwerke.rs.computedcolumns.service.computedcolumns.tokenizer.handlers;

import javax.el.ExpressionFactory;

import com.google.inject.Inject;

import de.odysseus.el.ExpressionFactoryImpl;
import de.odysseus.el.util.SimpleContext;
import de.odysseus.el.util.SimpleResolver;
import net.datenwerke.rs.computedcolumns.service.computedcolumns.tokenizer.ExpressionToken;
import net.datenwerke.rs.computedcolumns.service.computedcolumns.tokenizer.ExpressionTokenizer;
import net.datenwerke.rs.computedcolumns.service.computedcolumns.tokenizer.handlers.tokens.ParameterExpressionToken;
import net.datenwerke.rs.computedcolumns.service.computedcolumns.tokenizer.hooks.ExpressionTokenHandlerHook;
import net.datenwerke.rs.utils.juel.JuelService;

public class ParameterHandler implements ExpressionTokenHandlerHook {

	@Inject
	private JuelService juelService;
	
	ExpressionFactory factory = new ExpressionFactoryImpl();
	SimpleContext context = new SimpleContext(new SimpleResolver());
	
	@Override
	public ExpressionToken generateToken(String strToken, ExpressionTokenizer expressionTokenizer,
			String lookaheadChar) {
		if(strToken.matches("\\$\\{.*\\}") && juelService.isValidExpression(strToken)){
			try{
				factory.createValueExpression(context, strToken, Object.class);
				return new ParameterExpressionToken(strToken.substring(2,strToken.length()-1)); 
			}catch(Exception e){
				return null;
			}
		}
		
		return null;
	}

}