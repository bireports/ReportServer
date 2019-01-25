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
 
 
package net.datenwerke.gf.client.uiutils.date;

import java.util.Date;

import com.google.gwt.event.logical.shared.HasAttachHandlers;
import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;

public class FormulaEvaluatedEvent extends GwtEvent<FormulaEvaluatedEvent.Handler> {

	static Type<FormulaEvaluatedEvent.Handler> TYPE;
	private Date date;

	public static interface Handler extends EventHandler {
		public void handleFormulaEvaluatedEvent(FormulaEvaluatedEvent formulaEvaluatedEvent);
	}

	
	public FormulaEvaluatedEvent(Date date) {
		this.date = date;
	}

	public static <S extends HasAttachHandlers> void fire(S source, Date date) {
		if (TYPE != null) {
			FormulaEvaluatedEvent event = new FormulaEvaluatedEvent(date);
			source.fireEvent(event);
		}
	}

	public static Type<FormulaEvaluatedEvent.Handler> getType() {
		if (TYPE == null) {
			TYPE = new Type<FormulaEvaluatedEvent.Handler>();
		}
		return TYPE;
	}

	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<Handler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(Handler handler) {
		handler.handleFormulaEvaluatedEvent(this);
	}

	public Date getDate() {
		return date;
	}

}
