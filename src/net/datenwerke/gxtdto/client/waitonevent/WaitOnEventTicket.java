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
 
 
package net.datenwerke.gxtdto.client.waitonevent;

public class WaitOnEventTicket {

	private static int ticketCounter;
	
	private String uid;
	private String event;
	
	public WaitOnEventTicket(String event) {
		super();
		this.uid = "ticket_" + (ticketCounter++); //$NON-NLS-1$
		this.event = event;
	}


	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getEvent() {
		return event;
	}

	public void setEvent(String event) {
		this.event = event;
	}

	
	@Override
	public int hashCode() {
		return event.hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		if(! (obj instanceof WaitOnEventTicket))
			return false;
		
		WaitOnEventTicket ticket = (WaitOnEventTicket) obj;
		
		return ticket.getUid().equals(uid) && ticket.getEvent().equals(event);
	}
}
