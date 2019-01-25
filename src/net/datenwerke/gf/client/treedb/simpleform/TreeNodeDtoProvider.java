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
 
 
package net.datenwerke.gf.client.treedb.simpleform;

import net.datenwerke.gf.client.treedb.selection.SingleTreeSelectionField;
import net.datenwerke.gxtdto.client.clipboard.ClipboardDtoItem;
import net.datenwerke.gxtdto.client.clipboard.ClipboardUiService;
import net.datenwerke.gxtdto.client.clipboard.processor.ClipboardDtoPasteProcessor;
import net.datenwerke.gxtdto.client.forms.simpleform.SimpleFormFieldConfiguration;
import net.datenwerke.gxtdto.client.forms.simpleform.hooks.FormFieldProviderHookImpl;
import net.datenwerke.treedb.client.treedb.dto.AbstractNodeDto;

import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

/**
 * 
 *
 */
public class TreeNodeDtoProvider extends FormFieldProviderHookImpl {

	private final ClipboardUiService clipboardService;

	@Inject
	public TreeNodeDtoProvider(ClipboardUiService clipboardService){
		this.clipboardService = clipboardService;
	}
			
	
	@Override
	public boolean doConsumes(Class<?> type, SimpleFormFieldConfiguration... configs) {
		if(configs.length == 0 || ! (configs[0] instanceof SFFCGenericTreeNode))
			return false;
		
		while(type != null){
			if(type.equals(AbstractNodeDto.class))
				return true;
			type = type.getSuperclass();
		}
		return false;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Widget createFormField() {
		SFFCGenericTreeNode config = (SFFCGenericTreeNode) configs[0];
		
		final SingleTreeSelectionField ssf = new SingleTreeSelectionField((Class<? extends AbstractNodeDto>)type);
		
		ssf.addValueChangeHandler(new ValueChangeHandler<AbstractNodeDto>() {
			@Override
			public void onValueChange(ValueChangeEvent<AbstractNodeDto> event) {
				ValueChangeEvent.fire(TreeNodeDtoProvider.this, event.getValue());
			}
		});
		
		/* clipboard */
		clipboardService.registerPasteHandler(ssf, new ClipboardDtoPasteProcessor(type) {
			@Override
			protected void doPaste(ClipboardDtoItem dtoItem) {
				ssf.setValue((AbstractNodeDto) dtoItem.getDto(), true);
			}
		});
		
		ssf.setName(name);
		ssf.setTreePanel(config.getTreeForPopup());
		return ssf;
	}
	
	
	public Object getValue(Widget field){
		return ((SingleTreeSelectionField)field).getValue();
	}

}
