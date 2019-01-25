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
 
 
package net.datenwerke.rs.core.service.mail;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import net.datenwerke.rs.utils.juel.SimpleJuel;

public class MailTemplate {
	
	private String subjectTemplate;
	private String messageTemplate;
	private Map<String, Object> dataMap;
	
	private boolean html = false;
	
	public MailTemplate() {
		this.dataMap = new HashMap<String, Object>();
	}
	
	public MailTemplate(String subjectTemplate, String messageTemplate, Map<String, Object> dataMap) {
		this.subjectTemplate = subjectTemplate;
		this.messageTemplate = messageTemplate;
		this.dataMap = dataMap;
	}

	public void addReplacement(String key, Object value){
		dataMap.put(key, value);
	}
	
	public String getSubjectTemplate() {
		return subjectTemplate;
	}

	public void setSubjectTemplate(String subjectTemplate) {
		this.subjectTemplate = subjectTemplate;
	}

	public String getMessageTemplate() {
		return messageTemplate;
	}

	public void setMessageTemplate(String messageTemplate) {
		this.messageTemplate = messageTemplate;
	}

	public Map<String, Object> getDataMap() {
		return dataMap;
	}

	public void setDataMap(Map<String, Object> dataMap) {
		this.dataMap = dataMap;
	}
	
	public void setHtml(boolean html) {
		this.html = html;
	}

	public boolean isHtml() {
		return html;
	}
	

	public void configureMail(SimpleMail mail, SimpleJuel juel,
			MailServiceImpl mailServiceImpl) {
		for(Entry<String, Object> e : getDataMap().entrySet()){
			juel.addReplacement(e.getKey(), e.getValue());
		}
		
		if(isHtml())
			mail.setHtml(juel.parse(getMessageTemplate()));
		else
			mail.setText(juel.parse(getMessageTemplate()));

		mail.setSubject(juel.parse(getSubjectTemplate()));

	}


}
