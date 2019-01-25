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
 
 
package net.datenwerke.rs.computedcolumns.service.computedcolumns.hookers;

import java.util.Iterator;
import java.util.List;

import net.datenwerke.hookhandler.shared.hookhandler.HookHandlerService;
import net.datenwerke.rs.base.service.dbhelper.hooks.ColumnReferenceSqlProvider;
import net.datenwerke.rs.base.service.dbhelper.querybuilder.QueryBuilder;
import net.datenwerke.rs.base.service.reportengines.table.entities.AdditionalColumnSpec;
import net.datenwerke.rs.computedcolumns.service.computedcolumns.entities.ComputedColumn;
import net.datenwerke.rs.computedcolumns.service.computedcolumns.hooks.FunctionProviderHook;
import net.datenwerke.rs.computedcolumns.service.computedcolumns.tokenizer.ExpressionToken;
import net.datenwerke.rs.computedcolumns.service.computedcolumns.tokenizer.ExpressionTokenizer;

import com.google.inject.Inject;
import com.google.inject.Provider;

public class ComputedColumnSqlProvider implements ColumnReferenceSqlProvider {

	private final Provider<ExpressionTokenizer> tokenizerProvider;
	
	private final HookHandlerService hookHandler;
	
	@Inject
	public ComputedColumnSqlProvider(
		Provider<ExpressionTokenizer> tokenizerProvider,
		HookHandlerService hookHandler
		) {
		
		this.tokenizerProvider = tokenizerProvider;
		this.hookHandler = hookHandler;
	}

	@Override
	public boolean consumes(AdditionalColumnSpec col, QueryBuilder queryBuilder) {
		return col instanceof ComputedColumn;
	}

	@Override
	public String getSelectSnipped(AdditionalColumnSpec col, QueryBuilder queryBuilder) {
		String expression = ((ComputedColumn)col).getExpression();

		if(null == expression || "".equals(expression))
			return "";
		
		ExpressionTokenizer tokenizer = tokenizerProvider.get();
		tokenizer.initFunctions(hookHandler.getHookers(FunctionProviderHook.class));
		tokenizer.initVariables(queryBuilder.getPlainColumnNames());
		
		List<ExpressionToken> tokenlist = tokenizer.tokenize(expression);
		
		StringBuilder snippet = new StringBuilder();
		
		Iterator<ExpressionToken> tokenIt = tokenlist.iterator();
		while(tokenIt.hasNext()){
			ExpressionToken token = tokenIt.next();
			for(ExpressionTokenToSqlHook toSqlHooker : hookHandler.getHookers(ExpressionTokenToSqlHook.class)){
				if(toSqlHooker.consumes(token))
					snippet.append(toSqlHooker.handleToken(token, tokenIt));
			}
		}
		
		return snippet.toString();
	}

}
