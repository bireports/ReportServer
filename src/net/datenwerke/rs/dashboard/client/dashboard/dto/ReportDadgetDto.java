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
 
 
package net.datenwerke.rs.dashboard.client.dashboard.dto;

import com.google.gwt.core.client.GWT;
import java.lang.String;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import net.datenwerke.dtoservices.dtogenerator.annotations.GeneratedType;
import net.datenwerke.gxtdto.client.dtomanager.Dto2PosoMapper;
import net.datenwerke.gxtdto.client.dtomanager.Dto;
import net.datenwerke.gxtdto.client.dtomanager.DtoView;
import net.datenwerke.gxtdto.client.dtomanager.PropertyAccessor;
import net.datenwerke.gxtdto.client.dtomanager.dtomod.collections.ChangeMonitoredSet;
import net.datenwerke.gxtdto.client.dtomanager.dtomod.collections.MonitoredCollection;
import net.datenwerke.gxtdto.client.dtomanager.redoundo.ChangeTracker;
import net.datenwerke.gxtdto.client.eventbus.events.ObjectChangedEvent;
import net.datenwerke.gxtdto.client.eventbus.handlers.ObjectChangedEventHandler;
import net.datenwerke.gxtdto.client.eventbus.handlers.has.HasObjectChangedEventHandler;
import net.datenwerke.rs.core.client.parameters.dto.ParameterInstanceDto;
import net.datenwerke.rs.core.client.reportmanager.dto.reports.ReportDto;
import net.datenwerke.rs.dashboard.client.dashboard.dto.decorator.DadgetDtoDec;
import net.datenwerke.rs.dashboard.client.dashboard.dto.pa.ReportDadgetDtoPA;
import net.datenwerke.rs.dashboard.client.dashboard.dto.posomap.ReportDadgetDto2PosoMap;
import net.datenwerke.rs.dashboard.service.dashboard.dagets.ReportDadget;
import net.datenwerke.rs.tsreportarea.client.tsreportarea.dto.TsDiskReportReferenceDto;

/**
 * Dto for {@link ReportDadget}
 *
 * This file was automatically created by DtoAnnotationProcessor, version 0.1
 */
@GeneratedType("net.datenwerke.dtoservices.dtogenerator.DtoAnnotationProcessor")
abstract public class ReportDadgetDto extends DadgetDtoDec {


	private static final long serialVersionUID = 1;


	/* Fields */
	private String config;
	private  boolean config_m;
	public static final String PROPERTY_CONFIG = "dpi-reportdadget-config";

	private transient static PropertyAccessor<ReportDadgetDto, String> config_pa = new PropertyAccessor<ReportDadgetDto, String>() {
		@Override
		public void setValue(ReportDadgetDto container, String object) {
			container.setConfig(object);
		}

		@Override
		public String getValue(ReportDadgetDto container) {
			return container.getConfig();
		}

		@Override
		public Class<?> getType() {
			return String.class;
		}

		@Override
		public String getPath() {
			return "config";
		}

		@Override
		public void setModified(ReportDadgetDto container, boolean modified) {
			container.config_m = modified;
		}

		@Override
		public boolean isModified(ReportDadgetDto container) {
			return container.isConfigModified();
		}
	};

	private Set<ParameterInstanceDto> parameterInstances;
	private  boolean parameterInstances_m;
	public static final String PROPERTY_PARAMETER_INSTANCES = "dpi-reportdadget-parameterinstances";

	private transient static PropertyAccessor<ReportDadgetDto, Set<ParameterInstanceDto>> parameterInstances_pa = new PropertyAccessor<ReportDadgetDto, Set<ParameterInstanceDto>>() {
		@Override
		public void setValue(ReportDadgetDto container, Set<ParameterInstanceDto> object) {
			container.setParameterInstances(object);
		}

		@Override
		public Set<ParameterInstanceDto> getValue(ReportDadgetDto container) {
			return container.getParameterInstances();
		}

		@Override
		public Class<?> getType() {
			return Set.class;
		}

		@Override
		public String getPath() {
			return "parameterInstances";
		}

		@Override
		public void setModified(ReportDadgetDto container, boolean modified) {
			container.parameterInstances_m = modified;
		}

		@Override
		public boolean isModified(ReportDadgetDto container) {
			return container.isParameterInstancesModified();
		}
	};

	private ReportDto report;
	private  boolean report_m;
	public static final String PROPERTY_REPORT = "dpi-reportdadget-report";

	private transient static PropertyAccessor<ReportDadgetDto, ReportDto> report_pa = new PropertyAccessor<ReportDadgetDto, ReportDto>() {
		@Override
		public void setValue(ReportDadgetDto container, ReportDto object) {
			container.setReport(object);
		}

		@Override
		public ReportDto getValue(ReportDadgetDto container) {
			return container.getReport();
		}

		@Override
		public Class<?> getType() {
			return ReportDto.class;
		}

		@Override
		public String getPath() {
			return "report";
		}

		@Override
		public void setModified(ReportDadgetDto container, boolean modified) {
			container.report_m = modified;
		}

		@Override
		public boolean isModified(ReportDadgetDto container) {
			return container.isReportModified();
		}
	};

	private TsDiskReportReferenceDto reportReference;
	private  boolean reportReference_m;
	public static final String PROPERTY_REPORT_REFERENCE = "dpi-reportdadget-reportreference";

	private transient static PropertyAccessor<ReportDadgetDto, TsDiskReportReferenceDto> reportReference_pa = new PropertyAccessor<ReportDadgetDto, TsDiskReportReferenceDto>() {
		@Override
		public void setValue(ReportDadgetDto container, TsDiskReportReferenceDto object) {
			container.setReportReference(object);
		}

		@Override
		public TsDiskReportReferenceDto getValue(ReportDadgetDto container) {
			return container.getReportReference();
		}

		@Override
		public Class<?> getType() {
			return TsDiskReportReferenceDto.class;
		}

		@Override
		public String getPath() {
			return "reportReference";
		}

		@Override
		public void setModified(ReportDadgetDto container, boolean modified) {
			container.reportReference_m = modified;
		}

		@Override
		public boolean isModified(ReportDadgetDto container) {
			return container.isReportReferenceModified();
		}
	};


	public ReportDadgetDto() {
		super();
	}

	public String getConfig()  {
		if(! isDtoProxy()){
			return this.config;
		}

		if(isConfigModified())
			return this.config;

		if(! GWT.isClient())
			return null;

		String _value = dtoManager.getProperty(this, instantiatePropertyAccess().config());

		return _value;
	}


	public void setConfig(String config)  {
		/* old value */
		String oldValue = null;
		if(GWT.isClient())
			oldValue = getConfig();

		/* set new value */
		this.config = config;

		if(! GWT.isClient())
			return;

		if(isTrackChanges())
			addChange(new ChangeTracker(config_pa, oldValue, config, this.config_m));

		/* set indicator */
		this.config_m = true;

		this.fireObjectChangedEvent(ReportDadgetDtoPA.INSTANCE.config(), oldValue);
	}


	public boolean isConfigModified()  {
		return config_m;
	}


	public static PropertyAccessor<ReportDadgetDto, String> getConfigPropertyAccessor()  {
		return config_pa;
	}


	public Set<ParameterInstanceDto> getParameterInstances()  {
		if(! isDtoProxy()){
			Set<ParameterInstanceDto> _currentValue = this.parameterInstances;
			if(null == _currentValue)
				this.parameterInstances = new HashSet<ParameterInstanceDto>();

			return this.parameterInstances;
		}

		if(isParameterInstancesModified())
			return this.parameterInstances;

		if(! GWT.isClient())
			return null;

		Set<ParameterInstanceDto> _value = dtoManager.getProperty(this, instantiatePropertyAccess().parameterInstances());

		_value = new ChangeMonitoredSet<ParameterInstanceDto>(_value);
		if(_value instanceof HasObjectChangedEventHandler){
			((HasObjectChangedEventHandler)_value).addObjectChangedHandler(new net.datenwerke.gxtdto.client.eventbus.handlers.ObjectChangedEventHandler(){
				@Override
				public void onObjectChangedEvent(net.datenwerke.gxtdto.client.eventbus.events.ObjectChangedEvent event){
					if(! isParameterInstancesModified())
						setParameterInstances((Set<ParameterInstanceDto>) ((MonitoredCollection) event.getObject()).getUnderlyingCollection());
				}
			}
			);
		}
		return _value;
	}


	public void setParameterInstances(Set<ParameterInstanceDto> parameterInstances)  {
		/* old value */
		Set<ParameterInstanceDto> oldValue = null;
		if(GWT.isClient())
			oldValue = getParameterInstances();

		/* set new value */
		this.parameterInstances = parameterInstances;

		if(! GWT.isClient())
			return;

		if(isTrackChanges())
			addChange(new ChangeTracker(parameterInstances_pa, oldValue, parameterInstances, this.parameterInstances_m));

		/* set indicator */
		this.parameterInstances_m = true;

		this.fireObjectChangedEvent(ReportDadgetDtoPA.INSTANCE.parameterInstances(), oldValue);
	}


	public boolean isParameterInstancesModified()  {
		return parameterInstances_m;
	}


	public static PropertyAccessor<ReportDadgetDto, Set<ParameterInstanceDto>> getParameterInstancesPropertyAccessor()  {
		return parameterInstances_pa;
	}


	public ReportDto getReport()  {
		if(! isDtoProxy()){
			return this.report;
		}

		if(isReportModified())
			return this.report;

		if(! GWT.isClient())
			return null;

		ReportDto _value = dtoManager.getProperty(this, instantiatePropertyAccess().report());

		if(_value instanceof HasObjectChangedEventHandler){
			((HasObjectChangedEventHandler)_value).addObjectChangedHandler(new net.datenwerke.gxtdto.client.eventbus.handlers.ObjectChangedEventHandler(){
				@Override
				public void onObjectChangedEvent(net.datenwerke.gxtdto.client.eventbus.events.ObjectChangedEvent event){
					if(! isReportModified())
						setReport((ReportDto) event.getObject());
				}
			}
			);
		}
		return _value;
	}


	public void setReport(ReportDto report)  {
		/* old value */
		ReportDto oldValue = null;
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

		this.fireObjectChangedEvent(ReportDadgetDtoPA.INSTANCE.report(), oldValue);
	}


	public boolean isReportModified()  {
		return report_m;
	}


	public static PropertyAccessor<ReportDadgetDto, ReportDto> getReportPropertyAccessor()  {
		return report_pa;
	}


	public TsDiskReportReferenceDto getReportReference()  {
		if(! isDtoProxy()){
			return this.reportReference;
		}

		if(isReportReferenceModified())
			return this.reportReference;

		if(! GWT.isClient())
			return null;

		TsDiskReportReferenceDto _value = dtoManager.getProperty(this, instantiatePropertyAccess().reportReference());

		if(_value instanceof HasObjectChangedEventHandler){
			((HasObjectChangedEventHandler)_value).addObjectChangedHandler(new net.datenwerke.gxtdto.client.eventbus.handlers.ObjectChangedEventHandler(){
				@Override
				public void onObjectChangedEvent(net.datenwerke.gxtdto.client.eventbus.events.ObjectChangedEvent event){
					if(! isReportReferenceModified())
						setReportReference((TsDiskReportReferenceDto) event.getObject());
				}
			}
			);
		}
		return _value;
	}


	public void setReportReference(TsDiskReportReferenceDto reportReference)  {
		/* old value */
		TsDiskReportReferenceDto oldValue = null;
		if(GWT.isClient())
			oldValue = getReportReference();

		/* set new value */
		this.reportReference = reportReference;

		if(! GWT.isClient())
			return;

		if(isTrackChanges())
			addChange(new ChangeTracker(reportReference_pa, oldValue, reportReference, this.reportReference_m));

		/* set indicator */
		this.reportReference_m = true;

		this.fireObjectChangedEvent(ReportDadgetDtoPA.INSTANCE.reportReference(), oldValue);
	}


	public boolean isReportReferenceModified()  {
		return reportReference_m;
	}


	public static PropertyAccessor<ReportDadgetDto, TsDiskReportReferenceDto> getReportReferencePropertyAccessor()  {
		return reportReference_pa;
	}


	@Override
	public int hashCode()  {
		if(null == getId())
			return super.hashCode();
		return getId().hashCode();
	}

	@Override
	public boolean equals(Object obj)  {
		if(! (obj instanceof ReportDadgetDto))
			return false;

		if(null == getId())
			return super.equals(obj);
		return getId().equals(((ReportDadgetDto)obj).getId());
	}

	@Override
	public String toString()  {
		return getClass().getName() + ": " + getId();
	}

	public static Dto2PosoMapper newPosoMapper()  {
		return new ReportDadgetDto2PosoMap();
	}

	public ReportDadgetDtoPA instantiatePropertyAccess()  {
		return GWT.create(ReportDadgetDtoPA.class);
	}

	public void clearModified()  {
		this.config = null;
		this.config_m = false;
		this.parameterInstances = null;
		this.parameterInstances_m = false;
		this.report = null;
		this.report_m = false;
		this.reportReference = null;
		this.reportReference_m = false;
	}


	public boolean isModified()  {
		if(super.isModified())
			return true;
		if(config_m)
			return true;
		if(parameterInstances_m)
			return true;
		if(report_m)
			return true;
		if(reportReference_m)
			return true;
		return false;
	}


	public List<PropertyAccessor> getPropertyAccessors()  {
		List<PropertyAccessor> list = super.getPropertyAccessors();
		list.add(config_pa);
		list.add(parameterInstances_pa);
		list.add(report_pa);
		list.add(reportReference_pa);
		return list;
	}


	public List<PropertyAccessor> getModifiedPropertyAccessors()  {
		List<PropertyAccessor> list = super.getModifiedPropertyAccessors();
		if(config_m)
			list.add(config_pa);
		if(parameterInstances_m)
			list.add(parameterInstances_pa);
		if(report_m)
			list.add(report_pa);
		if(reportReference_m)
			list.add(reportReference_pa);
		return list;
	}


	public List<PropertyAccessor> getPropertyAccessorsByView(net.datenwerke.gxtdto.client.dtomanager.DtoView view)  {
		List<PropertyAccessor> list = super.getPropertyAccessorsByView(view);
		if(view.compareTo(DtoView.MINIMAL) >= 0){
			list.add(config_pa);
		}
		if(view.compareTo(DtoView.NORMAL) >= 0){
			list.add(parameterInstances_pa);
			list.add(report_pa);
			list.add(reportReference_pa);
		}
		return list;
	}


	public List<PropertyAccessor> getPropertyAccessorsForDtos()  {
		List<PropertyAccessor> list = super.getPropertyAccessorsForDtos();
		list.add(parameterInstances_pa);
		list.add(report_pa);
		list.add(reportReference_pa);
		return list;
	}



	net.datenwerke.rs.core.client.reportmanager.dto.reports.ReportDto wl_0;
	net.datenwerke.rs.core.client.parameters.dto.ParameterInstanceDto wl_1;
	net.datenwerke.rs.tsreportarea.client.tsreportarea.dto.TsDiskReportReferenceDto wl_2;

}
