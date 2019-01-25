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
 
 
package net.datenwerke.rs.saiku.client.saiku.ui;

import net.datenwerke.gxtdto.client.forms.binding.FormBinding;
import net.datenwerke.rs.core.client.datasourcemanager.DatasourceUIService;
import net.datenwerke.rs.core.client.reportmanager.ui.forms.AbstractReportForm;
import net.datenwerke.rs.saiku.client.datasource.dto.MondrianDatasourceDto;
import net.datenwerke.rs.saiku.client.saiku.dto.pa.SaikuReportDtoPA;
import net.datenwerke.rs.saiku.client.saiku.locale.SaikuMessages;
import net.datenwerke.treedb.client.treedb.dto.AbstractNodeDto;

import com.google.inject.Inject;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.widget.core.client.container.Container;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer.VerticalLayoutData;
import com.sencha.gxt.widget.core.client.form.CheckBox;
import com.sencha.gxt.widget.core.client.form.FieldLabel;
import com.sencha.gxt.widget.core.client.form.FormPanel;

public class SaikuReportForm extends AbstractReportForm {
	
	private CheckBox enableMdx;

	@Inject
	public SaikuReportForm(
		DatasourceUIService datasourceService
	) {
		super(datasourceService);
	}
	
	@Override
	protected void initializeForm(FormPanel form, VerticalLayoutContainer fieldWrapper) {
		super.initializeForm(form, fieldWrapper);
		
		/* cubify */
		enableMdx = new CheckBox();
		enableMdx.setBoxLabel(SaikuMessages.INSTANCE.enableMdx());
		fieldWrapper.add(new FieldLabel(enableMdx, SaikuMessages.INSTANCE.fieldLabelEnableMdx()), new VerticalLayoutData(-1, -1, new Margins(0, 0, 5, 0)));

	}
	
	@Override
	protected void doInitFormBinding(FormBinding binding, AbstractNodeDto model) {
		super.doInitFormBinding(binding, model);
		
		binding.addBinding(enableMdx, model, SaikuReportDtoPA.INSTANCE.allowMdx());
	}
	
	@Override
	protected String getHeader() {
		return SaikuMessages.INSTANCE.editReport()  + " ("+getSelectedNode().getId()+")"; //$NON-NLS-1$ //$NON-NLS-2$
	}
	
	
	@Override
	protected boolean isUploadForm() {
		return false;
	}
	
	@Override
	protected boolean isDisplayConfigFieldsForDatasource() {
		return true;
	}

	@Override
	protected void addDatasourceField(Container container, boolean displayConfigFields) {
		datasourceFieldCreator = datasourceService.getSelectionField(container, displayConfigFields, MondrianDatasourceDto.class);
		datasourceFieldCreator.addSelectionField();
		datasourceFieldCreator.addDisplayDefaultButton();
	}

}
