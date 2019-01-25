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
 
 
package net.datenwerke.rs.tsreportarea.client.tsreportarea.hookers;

import java.util.List;

import net.datenwerke.gf.client.managerhelper.hooks.TreeConfiguratorHook;
import net.datenwerke.gf.client.managerhelper.tree.ManagerHelperTree;
import net.datenwerke.gf.client.treedb.UITree;
import net.datenwerke.gf.client.treedb.helper.menu.TreeDBUIMenuProvider;
import net.datenwerke.gf.client.treedb.helper.menu.TreeMenuItem;
import net.datenwerke.gf.client.treedb.helper.menu.TreeMenuSelectionEvent;
import net.datenwerke.gf.client.treedb.icon.TreeDBUIIconProvider;
import net.datenwerke.gxtdto.client.baseex.widget.DwWindow;
import net.datenwerke.gxtdto.client.baseex.widget.btn.DwTextButton;
import net.datenwerke.gxtdto.client.dtomanager.callback.RsAsyncCallback;
import net.datenwerke.gxtdto.client.forms.simpleform.SimpleForm;
import net.datenwerke.gxtdto.client.forms.simpleform.providers.configs.SFFCTeamSpace;
import net.datenwerke.gxtdto.client.locale.BaseMessages;
import net.datenwerke.hookhandler.shared.hookhandler.HookHandlerService;
import net.datenwerke.rs.core.client.reportmanager.ReportManagerUIModule;
import net.datenwerke.rs.core.client.reportmanager.dto.interfaces.ReportVariantDto;
import net.datenwerke.rs.core.client.reportmanager.dto.reports.ReportDto;
import net.datenwerke.rs.core.client.reportmanager.hooks.ReportTypeConfigHook;
import net.datenwerke.rs.teamspace.client.teamspace.dto.TeamSpaceDto;
import net.datenwerke.rs.tsreportarea.client.tsreportarea.TsDiskDao;
import net.datenwerke.rs.tsreportarea.client.tsreportarea.dto.TsDiskReportReferenceDto;
import net.datenwerke.rs.tsreportarea.client.tsreportarea.locale.TsFavoriteMessages;
import net.datenwerke.treedb.client.treedb.dto.AbstractNodeDto;

import com.google.gwt.event.dom.client.DoubleClickEvent;
import com.google.inject.Inject;
import com.sencha.gxt.widget.core.client.container.MarginData;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.menu.Menu;
import net.datenwerke.gxtdto.client.baseex.widget.menu.DwMenu;

public class ReportVariantMenuHooker implements TreeConfiguratorHook {

	private final HookHandlerService hookHandler;
	private final TsDiskDao dao;
	
	@Inject
	public ReportVariantMenuHooker(
		HookHandlerService hookHandler,
		TsDiskDao dao) {
		this.hookHandler = hookHandler;
		this.dao = dao;
	}

	@Override
	public boolean consumes(ManagerHelperTree tree) {
		return ReportManagerUIModule.class.equals(tree.getGuarantor());
	}

	@Override
	public void configureTreeIcons(TreeDBUIIconProvider iconProvider) {
	}

	@Override
	public void configureTreeMenu(TreeDBUIMenuProvider menuProvider) {
		for(ReportTypeConfigHook config : hookHandler.getHookers(ReportTypeConfigHook.class)){
			Menu reportVariantMenu = menuProvider.createOrGetMenuFor(config.getReportVariantClass());
			
			TreeMenuItem item = new TreeMenuItem();
			item.setText(TsFavoriteMessages.INSTANCE.importVariantIntoTeamSpaceLabel());
			item.addMenuSelectionListener(new TreeMenuSelectionEvent() {
				@Override
				public void menuItemSelected(UITree tree, AbstractNodeDto node) {
					if(node instanceof ReportVariantDto)
						importVariant((ReportDto) node);
				}
			});
			reportVariantMenu.add(item);
		}
	}
	
	public void importVariant(final ReportDto report) {
		final DwWindow window = new DwWindow();
		window.setBodyBorder(false);
		window.setHeadingText(TsFavoriteMessages.INSTANCE.importVariantHookName());
		window.setSize(340, 200);
		
		final SimpleForm form = SimpleForm.getInlineInstance();
		
		final String teamSpaceKey = form.addField(
				TeamSpaceDto.class, TsFavoriteMessages.INSTANCE.selectTeamspaceLabel(),
				new SFFCTeamSpace(){
					@Override
					public boolean isMulti() {
						return false;
					}

					@Override
					public boolean isLoadAll() {
						return true;
					}
				});
		
		final String duplicateVariant = form.addField(Boolean.class, TsFavoriteMessages.INSTANCE.duplicateLabel());
				
		form.loadFields();
		
		window.add(form, new MarginData(10));
		
		DwTextButton submit = new DwTextButton(BaseMessages.INSTANCE.submit());
		window.addButton(submit);
		submit.addSelectHandler(new SelectHandler() {
			@Override
			public void onSelect(SelectEvent event) {
				TeamSpaceDto teamSpace = (TeamSpaceDto) form.getValue(teamSpaceKey);
				if(null == teamSpace)
					return;
				
				boolean duplicate = (Boolean) form.getValue(duplicateVariant);
				
				window.mask(BaseMessages.INSTANCE.storingMsg());
				dao.importReport(teamSpace, null, report, duplicate, false, new RsAsyncCallback<List<TsDiskReportReferenceDto>>(){
					@Override
					public void onSuccess(List<TsDiskReportReferenceDto> result) {
						window.hide();
					}
					@Override
					public void onFailure(Throwable caught) {
						window.unmask();
					}
				});
			}
		});
		
		window.show();
	}

	@Override
	public void onDoubleClick(AbstractNodeDto selectedItem, DoubleClickEvent event) {
		
	}	
	
	@Override
	public void configureFolderTypes(ManagerHelperTree tree) {
	}
	

}
