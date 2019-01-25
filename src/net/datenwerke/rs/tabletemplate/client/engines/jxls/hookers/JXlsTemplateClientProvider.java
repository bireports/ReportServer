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
 
 
package net.datenwerke.rs.tabletemplate.client.engines.jxls.hookers;

import net.datenwerke.gxtdto.client.resources.BaseResources;
import net.datenwerke.rs.tabletemplate.client.engines.jxls.JXlsTemplateUIModule;
import net.datenwerke.rs.tabletemplate.client.engines.jxls.locale.JXlsTemplateMessages;
import net.datenwerke.rs.tabletemplate.client.tabletemplate.hooks.TableTemplateClientProviderHook;
import net.datenwerke.rs.theme.client.icon.BaseIcon;

import com.google.gwt.resources.client.ImageResource;

public class JXlsTemplateClientProvider implements
		TableTemplateClientProviderHook {

	@Override
	public String getType() {
		return JXlsTemplateUIModule.TEMPLATE_TYPE;
	}

	@Override
	public String getName() {
		return JXlsTemplateMessages.INSTANCE.templateTypeName();
	}

	@Override
	public String getDescription() {
		return JXlsTemplateMessages.INSTANCE.templateTypeDescription();
	}

	@Override
	public ImageResource getIconLarge() {
		return BaseIcon.fromFileExtension("xls").toImageResource(1);
	}

	@Override
	public ImageResource getIconSmall() {
		return BaseIcon.fromFileExtension("xls").toImageResource();
	}
	
	@Override
	public ContentTypeConfig getContentTypeConfig() {
		return new ContentTypeConfig(){
			@Override
			public boolean isDisplay() {
				return false;
			}

			@Override
			public String getDefaultContentType() {
				return null;
			}

			@Override
			public String getDefaultFileExtension() {
				return null;
			}
		};
	}

}
