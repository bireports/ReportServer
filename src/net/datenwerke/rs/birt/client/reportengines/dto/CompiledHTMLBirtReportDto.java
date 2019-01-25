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
 
 
package net.datenwerke.rs.birt.client.reportengines.dto;

import com.google.gwt.core.client.GWT;
import java.lang.String;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import net.datenwerke.dtoservices.dtogenerator.annotations.GeneratedType;
import net.datenwerke.gf.base.client.dtogenerator.RsDto;
import net.datenwerke.gxtdto.client.dtomanager.Dto2PosoMapper;
import net.datenwerke.gxtdto.client.dtomanager.DtoView;
import net.datenwerke.gxtdto.client.dtomanager.PropertyAccessor;
import net.datenwerke.gxtdto.client.dtomanager.redoundo.ChangeTracker;
import net.datenwerke.rs.birt.client.reportengines.dto.pa.CompiledHTMLBirtReportDtoPA;
import net.datenwerke.rs.birt.client.reportengines.dto.posomap.CompiledHTMLBirtReportDto2PosoMap;
import net.datenwerke.rs.birt.service.reportengine.output.object.CompiledHTMLBirtReport;
import net.datenwerke.rs.core.client.reportexecutor.dto.CompiledHtmlReportDto;

/**
 * Dto for {@link CompiledHTMLBirtReport}
 *
 * This file was automatically created by DtoAnnotationProcessor, version 0.1
 */
@GeneratedType("net.datenwerke.dtoservices.dtogenerator.DtoAnnotationProcessor")
public class CompiledHTMLBirtReportDto extends RsDto implements CompiledHtmlReportDto {


	private static final long serialVersionUID = 1;


	/* Fields */
	private String report;
	private  boolean report_m;
	public static final String PROPERTY_REPORT = "dpi-compiledhtmlbirtreport-report";

	private transient static PropertyAccessor<CompiledHTMLBirtReportDto, String> report_pa = new PropertyAccessor<CompiledHTMLBirtReportDto, String>() {
		@Override
		public void setValue(CompiledHTMLBirtReportDto container, String object) {
			container.setReport(object);
		}

		@Override
		public String getValue(CompiledHTMLBirtReportDto container) {
			return container.getReport();
		}

		@Override
		public Class<?> getType() {
			return String.class;
		}

		@Override
		public String getPath() {
			return "report";
		}

		@Override
		public void setModified(CompiledHTMLBirtReportDto container, boolean modified) {
			container.report_m = modified;
		}

		@Override
		public boolean isModified(CompiledHTMLBirtReportDto container) {
			return container.isReportModified();
		}
	};


	public CompiledHTMLBirtReportDto() {
		super();
	}

	public String getReport()  {
		if(! isDtoProxy()){
			return this.report;
		}

		if(isReportModified())
			return this.report;

		if(! GWT.isClient())
			return null;

		String _value = dtoManager.getProperty(this, instantiatePropertyAccess().report());

		return _value;
	}


	public void setReport(String report)  {
		/* old value */
		String oldValue = null;
		if(GWT.isClient())
			oldValue = getReport();

		/* set new value */
		this.report = report;

		if(! GWT.isClient())
			return;

		if(isTrackChanges())
			addChange(new ChangeTracker(report_pa, oldValue, report, this.report_m));

		/* set indicator */
		this.report_m = true;

		this.fireObjectChangedEvent(CompiledHTMLBirtReportDtoPA.INSTANCE.report(), oldValue);
	}


	public boolean isReportModified()  {
		return report_m;
	}


	public static PropertyAccessor<CompiledHTMLBirtReportDto, String> getReportPropertyAccessor()  {
		return report_pa;
	}


	@Override
	public String toString()  {
		return super.toString();
	}

	public static Dto2PosoMapper newPosoMapper()  {
		return new CompiledHTMLBirtReportDto2PosoMap();
	}

	public CompiledHTMLBirtReportDtoPA instantiatePropertyAccess()  {
		return GWT.create(CompiledHTMLBirtReportDtoPA.class);
	}

	public void clearModified()  {
		this.report = null;
		this.report_m = false;
	}


	public boolean isModified()  {
		if(super.isModified())
			return true;
		if(report_m)
			return true;
		return false;
	}


	public List<PropertyAccessor> getPropertyAccessors()  {
		List<PropertyAccessor> list = super.getPropertyAccessors();
		list.add(report_pa);
		return list;
	}


	public List<PropertyAccessor> getModifiedPropertyAccessors()  {
		List<PropertyAccessor> list = super.getModifiedPropertyAccessors();
		if(report_m)
			list.add(report_pa);
		return list;
	}


	public List<PropertyAccessor> getPropertyAccessorsByView(net.datenwerke.gxtdto.client.dtomanager.DtoView view)  {
		List<PropertyAccessor> list = super.getPropertyAccessorsByView(view);
		if(view.compareTo(DtoView.NORMAL) >= 0){
			list.add(report_pa);
		}
		return list;
	}


	public List<PropertyAccessor> getPropertyAccessorsForDtos()  {
		List<PropertyAccessor> list = super.getPropertyAccessorsForDtos();
		return list;
	}




}
