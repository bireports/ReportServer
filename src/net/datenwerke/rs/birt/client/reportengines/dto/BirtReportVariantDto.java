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
 
 
package net.datenwerke.rs.birt.client.reportengines.dto;

import com.google.gwt.core.client.GWT;
import java.lang.NullPointerException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import net.datenwerke.dtoservices.dtogenerator.annotations.GeneratedType;
import net.datenwerke.gxtdto.client.dtomanager.Dto2PosoMapper;
import net.datenwerke.gxtdto.client.dtomanager.Dto;
import net.datenwerke.gxtdto.client.dtomanager.DtoView;
import net.datenwerke.gxtdto.client.dtomanager.PropertyAccessor;
import net.datenwerke.gxtdto.client.dtomanager.redoundo.ChangeTracker;
import net.datenwerke.gxtdto.client.eventbus.events.ObjectChangedEvent;
import net.datenwerke.gxtdto.client.eventbus.handlers.ObjectChangedEventHandler;
import net.datenwerke.gxtdto.client.eventbus.handlers.has.HasObjectChangedEventHandler;
import net.datenwerke.gxtdto.client.locale.BaseMessages;
import net.datenwerke.rs.birt.client.reportengines.dto.decorator.BirtReportDtoDec;
import net.datenwerke.rs.birt.client.reportengines.dto.pa.BirtReportVariantDtoPA;
import net.datenwerke.rs.birt.client.reportengines.dto.posomap.BirtReportVariantDto2PosoMap;
import net.datenwerke.rs.birt.service.reportengine.entities.BirtReportVariant;
import net.datenwerke.rs.core.client.reportmanager.dto.interfaces.ReportVariantDto;
import net.datenwerke.rs.core.client.reportmanager.dto.reports.ReportDto;

/**
 * Dto for {@link BirtReportVariant}
 *
 * This file was automatically created by DtoAnnotationProcessor, version 0.1
 */
@GeneratedType("net.datenwerke.dtoservices.dtogenerator.DtoAnnotationProcessor")
abstract public class BirtReportVariantDto extends BirtReportDtoDec implements ReportVariantDto {


	private static final long serialVersionUID = 1;


	/* Fields */
	private ReportDto baseReport;
	private  boolean baseReport_m;
	public static final String PROPERTY_BASE_REPORT = "dpi-birtreportvariant-basereport";

	private transient static PropertyAccessor<BirtReportVariantDto, ReportDto> baseReport_pa = new PropertyAccessor<BirtReportVariantDto, ReportDto>() {
		@Override
		public void setValue(BirtReportVariantDto container, ReportDto object) {
			container.setBaseReport(object);
		}

		@Override
		public ReportDto getValue(BirtReportVariantDto container) {
			return container.getBaseReport();
		}

		@Override
		public Class<?> getType() {
			return ReportDto.class;
		}

		@Override
		public String getPath() {
			return "baseReport";
		}

		@Override
		public void setModified(BirtReportVariantDto container, boolean modified) {
			container.baseReport_m = modified;
		}

		@Override
		public boolean isModified(BirtReportVariantDto container) {
			return container.isBaseReportModified();
		}
	};


	public BirtReportVariantDto() {
		super();
	}

	public void setBaseReport(ReportDto baseReport)  {
		/* old value */
		ReportDto oldValue = null;
		if(GWT.isClient())
			oldValue = getBaseReport();

		/* set new value */
		this.baseReport = baseReport;

		if(! GWT.isClient())
			return;

		if(isTrackChanges())
			addChange(new ChangeTracker(baseReport_pa, oldValue, baseReport, this.baseReport_m));

		/* set indicator */
		this.baseReport_m = true;

		this.fireObjectChangedEvent(BirtReportVariantDtoPA.INSTANCE.baseReport(), oldValue);
	}


	public ReportDto getBaseReport()  {
		if(! isDtoProxy()){
			return this.baseReport;
		}

		if(isBaseReportModified())
			return this.baseReport;

		if(! GWT.isClient())
			return null;

		ReportDto _value = dtoManager.getProperty(this, instantiatePropertyAccess().baseReport());

		if(_value instanceof HasObjectChangedEventHandler){
			((HasObjectChangedEventHandler)_value).addObjectChangedHandler(new net.datenwerke.gxtdto.client.eventbus.handlers.ObjectChangedEventHandler(){
				@Override
				public void onObjectChangedEvent(net.datenwerke.gxtdto.client.eventbus.events.ObjectChangedEvent event){
					if(! isBaseReportModified())
						setBaseReport((ReportDto) event.getObject());
				}
			}
			);
		}
		return _value;
	}


	public boolean isBaseReportModified()  {
		return baseReport_m;
	}


	public static PropertyAccessor<BirtReportVariantDto, ReportDto> getBaseReportPropertyAccessor()  {
		return baseReport_pa;
	}


	@Override
	public String toDisplayTitle()  {
		try{
			if(null == getName())
				return BaseMessages.INSTANCE.unnamed();
			return getName().toString();
		} catch(NullPointerException e){
			return BaseMessages.INSTANCE.unnamed();
		}
	}

	@Override
	public int hashCode()  {
		if(null == getId())
			return super.hashCode();
		return getId().hashCode();
	}

	@Override
	public boolean equals(Object obj)  {
		if(! (obj instanceof BirtReportVariantDto))
			return false;

		if(null == getId())
			return super.equals(obj);
		return getId().equals(((BirtReportVariantDto)obj).getId());
	}

	@Override
	public String toString()  {
		return getClass().getName() + ": " + getId();
	}

	public static Dto2PosoMapper newPosoMapper()  {
		return new BirtReportVariantDto2PosoMap();
	}

	public BirtReportVariantDtoPA instantiatePropertyAccess()  {
		return GWT.create(BirtReportVariantDtoPA.class);
	}

	public void clearModified()  {
		this.baseReport = null;
		this.baseReport_m = false;
	}


	public boolean isModified()  {
		if(super.isModified())
			return true;
		if(baseReport_m)
			return true;
		return false;
	}


	public List<PropertyAccessor> getPropertyAccessors()  {
		List<PropertyAccessor> list = super.getPropertyAccessors();
		list.add(baseReport_pa);
		return list;
	}


	public List<PropertyAccessor> getModifiedPropertyAccessors()  {
		List<PropertyAccessor> list = super.getModifiedPropertyAccessors();
		if(baseReport_m)
			list.add(baseReport_pa);
		return list;
	}


	public List<PropertyAccessor> getPropertyAccessorsByView(net.datenwerke.gxtdto.client.dtomanager.DtoView view)  {
		List<PropertyAccessor> list = super.getPropertyAccessorsByView(view);
		if(view.compareTo(DtoView.NORMAL) >= 0){
			list.add(baseReport_pa);
		}
		return list;
	}


	public List<PropertyAccessor> getPropertyAccessorsForDtos()  {
		List<PropertyAccessor> list = super.getPropertyAccessorsForDtos();
		list.add(baseReport_pa);
		return list;
	}



	net.datenwerke.rs.core.client.reportmanager.dto.reports.ReportDto wl_0;

}
