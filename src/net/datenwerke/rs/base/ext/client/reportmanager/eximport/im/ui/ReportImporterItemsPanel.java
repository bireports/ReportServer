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
 
 
package net.datenwerke.rs.base.ext.client.reportmanager.eximport.im.ui;

import java.util.List;

import com.google.gwt.resources.client.ImageResource;
import com.google.inject.Inject;
import com.sencha.gxt.data.shared.IconProvider;

import net.datenwerke.gxtdto.client.dtomanager.callback.RsAsyncCallback;
import net.datenwerke.rs.base.client.reportengines.jasper.dto.JasperReportDto;
import net.datenwerke.rs.base.client.reportengines.jasper.dto.JasperReportVariantDto;
import net.datenwerke.rs.base.client.reportengines.table.dto.TableReportDto;
import net.datenwerke.rs.base.client.reportengines.table.dto.TableReportVariantDto;
import net.datenwerke.rs.base.ext.client.reportmanager.eximport.im.ReportManagerImportDao;
import net.datenwerke.rs.base.ext.client.reportmanager.eximport.im.dto.ReportManagerImportConfigDto;
import net.datenwerke.rs.birt.client.reportengines.dto.BirtReportDto;
import net.datenwerke.rs.birt.client.reportengines.dto.BirtReportVariantDto;
import net.datenwerke.rs.core.client.reportmanager.dto.ReportFolderDto;
import net.datenwerke.rs.crystal.client.crystal.dto.CrystalReportDto;
import net.datenwerke.rs.crystal.client.crystal.dto.CrystalReportVariantDto;
import net.datenwerke.rs.grideditor.client.grideditor.dto.GridEditorReportDto;
import net.datenwerke.rs.grideditor.client.grideditor.dto.GridEditorReportVariantDto;
import net.datenwerke.rs.jxlsreport.client.jxlsreport.dto.JxlsReportDto;
import net.datenwerke.rs.jxlsreport.client.jxlsreport.dto.JxlsReportVariantDto;
import net.datenwerke.rs.scriptreport.client.scriptreport.dto.ScriptReportDto;
import net.datenwerke.rs.scriptreport.client.scriptreport.dto.ScriptReportVariantDto;
import net.datenwerke.rs.theme.client.icon.BaseIcon;
import net.datenwerke.treedb.ext.client.eximport.im.dto.ImportTreeModel;
import net.datenwerke.treedb.ext.client.eximport.im.ui.ImporterItemsPanel;

public class ReportImporterItemsPanel extends ImporterItemsPanel<ReportManagerImportConfigDto> {

	private final ReportManagerImportDao rmImportDao;
	
	@Inject
	public ReportImporterItemsPanel(ReportManagerImportDao rmImportDao) {
		super();
		
		/* store objects */
		this.rmImportDao = rmImportDao;
		
		/* load data */
		loadData();
	}


	private void loadData() {
		rmImportDao.loadTree(new RsAsyncCallback<List<ImportTreeModel>>(){
			@Override
			public void onSuccess(List<ImportTreeModel> roots) {
				buildTree(roots);
			}
		});
	}
	
	protected void configureTree() {
		super.configureTree();
		
		tree.setIconProvider(new IconProvider<ImportTreeModel>() {
			@Override
			public ImageResource getIcon(ImportTreeModel model) {
				if(TableReportDto.class.getName().equals(model.getType()))
					return BaseIcon.REPORT_DL.toImageResource();
				if(TableReportVariantDto.class.getName().equals(model.getType()))
					return BaseIcon.REPORT_DL.toImageResource();
				if(JasperReportDto.class.getName().equals(model.getType()))
					return BaseIcon.REPORT_JASPER.toImageResource();
				if(JasperReportVariantDto.class.getName().equals(model.getType()))
					return BaseIcon.REPORT_JASPER.toImageResource();
				if(BirtReportDto.class.getName().equals(model.getType()))
					return BaseIcon.REPORT_BIRT.toImageResource();
				if(BirtReportVariantDto.class.getName().equals(model.getType()))
					return BaseIcon.REPORT_BIRT.toImageResource();
				if(CrystalReportDto.class.getName().equals(model.getType()))
					return BaseIcon.REPORT_CRYSTAL.toImageResource();
				if(CrystalReportVariantDto.class.getName().equals(model.getType()))
					return BaseIcon.REPORT_CRYSTAL.toImageResource();
				if(GridEditorReportDto.class.getName().equals(model.getType()))
					return BaseIcon.REPORT_GE.toImageResource();
				if(GridEditorReportVariantDto.class.getName().equals(model.getType()))
					return BaseIcon.REPORT_GE.toImageResource();
				if(JxlsReportDto.class.getName().equals(model.getType()))
					return BaseIcon.REPORT_JXLS.toImageResource();
				if(JxlsReportVariantDto.class.getName().equals(model.getType()))
					return BaseIcon.REPORT_JXLS.toImageResource();
				if(ScriptReportDto.class.getName().equals(model.getType()))
					return BaseIcon.SCRIPT.toImageResource();
				if(ScriptReportVariantDto.class.getName().equals(model.getType()))
					return BaseIcon.SCRIPT.toImageResource();				
				if(ReportFolderDto.class.getName().equals(model.getType()))
					return BaseIcon.FOLDER_O.toImageResource();
				return null;
			}
		});
	}
}
