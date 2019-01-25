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
 
 
package net.datenwerke.treedb.ext.client.eximport.im.ui;

import net.datenwerke.gxtdto.client.baseex.widget.DwContentPanel;
import net.datenwerke.gxtdto.client.forms.simpleform.SimpleForm;
import net.datenwerke.rs.eximport.client.eximport.im.dto.ImportConfigDto;
import net.datenwerke.rs.eximport.client.eximport.im.exceptions.NotProperlyConfiguredException;

import com.sencha.gxt.core.client.dom.ScrollSupport.ScrollMode;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer.VerticalLayoutData;
import com.sencha.gxt.widget.core.client.form.FormPanel.LabelAlign;

/**
 * 
 *
 */
abstract public class ImporterMainPropertiesPanel<C extends ImportConfigDto> extends DwContentPanel {

	protected SimpleForm form;
	
	protected void initializeUI() {
		/* configure */
		setHeaderVisible(false);
		setBodyBorder(false);
		setBorders(false);
		
		/* create form */
		form = SimpleForm.getInlineInstance();
		form.setLabelAlign(LabelAlign.TOP);
		form.setFieldWidth(250);
		configureForm();
		form.loadFields();
		
		DwContentPanel wrapper = new DwContentPanel();
		wrapper.setLightDarkStyle();
		wrapper.setHeadingText(getHeadline());
		wrapper.setInfoText(getDescription());
		wrapper.add(form);
		
		VerticalLayoutContainer outerWrapper = new VerticalLayoutContainer();
		outerWrapper.setScrollMode(ScrollMode.AUTOY);
		outerWrapper.add(wrapper, new VerticalLayoutData(1,-1,new Margins(10)));
		
		add(outerWrapper);
	}

	abstract protected String getDescription();

	abstract protected String getHeadline();

	protected void configureForm() {

	}

	public void populateConfig(C config) throws NotProperlyConfiguredException {
		
	}

	public void validateConfig(C config) throws NotProperlyConfiguredException{
	}

	public void resetConfig() {
		form.getFormPanel().reset();
	}

	
}
