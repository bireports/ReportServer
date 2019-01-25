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
 
 
package net.datenwerke.gf.service.history;

import java.util.Random;

import net.datenwerke.dtoservices.dtogenerator.annotations.ExposeToClient;
import net.datenwerke.dtoservices.dtogenerator.annotations.GenerateDto;
import net.datenwerke.gf.client.history.HistoryLocation;

/**
 * 
 *
 */
@GenerateDto(
	dtoPackage="net.datenwerke.gf.client.history.dto"
)
public class HistoryLink {

	@ExposeToClient(disableHtmlEncode=true)
	private String historyToken;
	
	@ExposeToClient
	private String objectCaption;
	
	@ExposeToClient
	private String historyLinkBuilderId;
	
	@ExposeToClient
	private String historyLinkBuilderName;
	
	@ExposeToClient
	private String historyLinkBuilderIcon;
	
	public HistoryLink() {
	}

	public HistoryLink(String objectCaption, String historyToken,
			String historyLinkBuilderId) {
		super();
		this.objectCaption = objectCaption;
		this.historyToken = historyToken;
		this.historyLinkBuilderId = historyLinkBuilderId;
		this.historyLinkBuilderName = historyLinkBuilderId;
	}

	public String getHistoryToken() {
		return historyToken;
	}

	public void setHistoryToken(String historyToken) {
		this.historyToken = historyToken;
	}

	public String getObjectCaption() {
		return objectCaption;
	}

	public void setObjectCaption(String objectCaption) {
		this.objectCaption = objectCaption;
	}

	public String getHistoryLinkBuilderId() {
		return historyLinkBuilderId;
	}

	public void setHistoryLinkBuilderId(String historyLinkBuilderId) {
		this.historyLinkBuilderId = historyLinkBuilderId;
	}

	public String getLink() {
		if(null == historyToken)
			return null;
		if(historyToken.endsWith(HistoryLocation.SEP_LOC_PARAM))
			return historyToken + "nonce" + HistoryLocation.SEP_PARAM_KEY_VALUE + new Random().nextInt();
		return historyToken + HistoryLocation.SEP_PARAMS + "nonce" + HistoryLocation.SEP_PARAM_KEY_VALUE + new Random().nextInt();
	}

	public void setHistoryLinkBuilderName(String historyLinkBuilderName) {
		this.historyLinkBuilderName = historyLinkBuilderName;
	}

	public String getHistoryLinkBuilderName() {
		return historyLinkBuilderName;
	}

	public void setHistoryLinkBuilderIcon(String historyLinkBuilderIcon) {
		this.historyLinkBuilderIcon = historyLinkBuilderIcon;
	}

	public String getHistoryLinkBuilderIcon() {
		return historyLinkBuilderIcon;
	}
	
	
}
