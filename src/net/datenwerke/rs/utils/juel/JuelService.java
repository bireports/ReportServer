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
 
 
package net.datenwerke.rs.utils.juel;

import java.util.Map;

import javax.el.ELContext;
import javax.el.ExpressionFactory;

/**
 * 
 *
 */
public interface JuelService {

	public static final String JUEL_SANDBOX = "JUEL";
	
	/**
	 * Provides a new {@link ExpressionFactory} with the following properties:
	 * <ul>
	 * <li>javax.el.methodInvocations: true</li>
	 * <li>javax.el.nullProperties: false</li>
	 * </ul>
	 * 
	 * @return A new {@link ExpressionFactory}
	 */
	public ExpressionFactory provideBasicExpressionFactory();
	
	/**
	 * Provides a new {@link ELContext} with some default values set. The {@link ContextConfig} gets newly created.
	 * 
	 * @param factory The {@link ExpressionFactory} to create expressions with.
	 * @return A new {@link ELContext}
	 */
	public ELContext provideBasicContext(ExpressionFactory factory);
	
	public boolean isValidExpression(String expression);

	public Object evaluate(ExpressionFactory factory, ELContext context,
			String text);

	public Object evaluate(Map<String, VariableAssignment> replacementMap, String template);

}
