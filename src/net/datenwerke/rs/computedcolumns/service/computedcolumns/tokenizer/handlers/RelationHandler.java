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
 
 
package net.datenwerke.rs.computedcolumns.service.computedcolumns.tokenizer.handlers;

import net.datenwerke.rs.computedcolumns.service.computedcolumns.tokenizer.ExpressionToken;
import net.datenwerke.rs.computedcolumns.service.computedcolumns.tokenizer.ExpressionTokenizer;
import net.datenwerke.rs.computedcolumns.service.computedcolumns.tokenizer.handlers.tokens.RelationExpressionToken;
import net.datenwerke.rs.computedcolumns.service.computedcolumns.tokenizer.handlers.tokens.RelationExpressionToken.RelationType;
import net.datenwerke.rs.computedcolumns.service.computedcolumns.tokenizer.hooks.ExpressionTokenHandlerHook;

public class RelationHandler implements ExpressionTokenHandlerHook {

	@Override
	public ExpressionToken generateToken(String strToken, ExpressionTokenizer expressionTokenizer, String lookaheadChar) {
		boolean greedy = null == lookaheadChar || "".equals(lookaheadChar.trim());
		
		strToken = strToken.toLowerCase().trim();
		
		return "<".equals(strToken) ? new RelationExpressionToken(RelationType.LESS, greedy) :
			   "<=".equals(strToken) ? new RelationExpressionToken(RelationType.LESS_OR_EQUAL, greedy) :
			   ">=".equals(strToken) ? new RelationExpressionToken(RelationType.GREATER_OR_EQUAL, greedy) :
			   ">".equals(strToken) ? new RelationExpressionToken(RelationType.GREATER, greedy) :
			   "=".equals(strToken) ? new RelationExpressionToken(RelationType.EQUAL, greedy) :
			   "!=".equals(strToken) ? new RelationExpressionToken(RelationType.NOTEQUAL, greedy) :
			   "<>".equals(strToken) ? new RelationExpressionToken(RelationType.NOTEQUAL, greedy) :
			   "like".equals(strToken) ? new RelationExpressionToken(RelationType.LIKE, greedy) :
			   "between".equals(strToken) ? new RelationExpressionToken(RelationType.BETWEEN, greedy) :
			   "is null".equals(strToken) ? new RelationExpressionToken(RelationType.IS_NULL, greedy) :
			   "is not null".equals(strToken) ? new RelationExpressionToken(RelationType.IS_NOT_NULL, greedy) :
			   null;
	}

}
