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
 
 
package net.datenwerke.rs.tabletemplate.client.tabletemplate;

import net.datenwerke.gxtdto.client.xtemplates.NullSafeFormatter;
import net.datenwerke.rs.tabletemplate.client.tabletemplate.dto.TableReportTemplateDto;

import com.google.gwt.core.client.GWT;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.sencha.gxt.core.client.XTemplates;
import com.sencha.gxt.core.client.XTemplates.FormatterFactories;
import com.sencha.gxt.core.client.XTemplates.FormatterFactory;
import com.sencha.gxt.core.client.XTemplates.FormatterFactoryMethod;

public class TableTemplateUIServiceImpl implements TableTemplateUIService {

	@FormatterFactories(@FormatterFactory(factory=NullSafeFormatter.class,methods=@FormatterFactoryMethod(name="nullsafe")))
	public interface TableTemplateTemplates extends XTemplates {
		@XTemplate("<div class=\"rt-ticket-grid-desc\">" +
		    	"<div class=\"title\">{name:nullsafe}</div>" +
		    	"<div class=\"description\">{description:nullsafe}</div>" +
		    "</div>")
	    public SafeHtml descriptionTemplate(TableReportTemplateDto dto); 
	}

	@Override
	public TableTemplateTemplates getDescriptionTemplate() {
		return GWT.create(TableTemplateTemplates.class);
	}
	
}
