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
 
 
package net.datenwerke.rs.passwordpolicy.client.accountinhibition;

import java.util.Date;

import net.datenwerke.gxtdto.client.model.DwModel;

public class AccountInhibitionConfiguration implements DwModel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1551519273324174420L;
	
	private boolean inhibitionState;
	private boolean blockedTemporarily;
	private Date expirationDate;
	private Long userId;
	
	public boolean isInhibitionState() {
		return inhibitionState;
	}
	public void setInhibitionState(boolean inhibitionState) {
		this.inhibitionState = inhibitionState;
	}
	public Date getExpirationDate() {
		return expirationDate;
	}
	public void setExpirationDate(Date expirationDate) {
		this.expirationDate = expirationDate;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public boolean isBlockedTemporarily() {
		return blockedTemporarily;
	}
	public void setBlockedTemporarily(boolean blockedTemporarily) {
		this.blockedTemporarily = blockedTemporarily;
	}
	

}