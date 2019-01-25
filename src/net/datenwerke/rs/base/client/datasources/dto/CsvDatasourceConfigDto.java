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
 
 
package net.datenwerke.rs.base.client.datasources.dto;

import com.google.gwt.core.client.GWT;
import java.lang.String;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import net.datenwerke.dtoservices.dtogenerator.annotations.GeneratedType;
import net.datenwerke.gxtdto.client.dtomanager.Dto2PosoMapper;
import net.datenwerke.gxtdto.client.dtomanager.DtoView;
import net.datenwerke.gxtdto.client.dtomanager.PropertyAccessor;
import net.datenwerke.gxtdto.client.dtomanager.redoundo.ChangeTracker;
import net.datenwerke.rs.base.client.datasources.dto.FormatBasedDatasourceConfigDto;
import net.datenwerke.rs.base.client.datasources.dto.pa.CsvDatasourceConfigDtoPA;
import net.datenwerke.rs.base.client.datasources.dto.posomap.CsvDatasourceConfigDto2PosoMap;
import net.datenwerke.rs.base.service.datasources.definitions.CsvDatasourceConfig;

/**
 * Dto for {@link CsvDatasourceConfig}
 *
 * This file was automatically created by DtoAnnotationProcessor, version 0.1
 */
@GeneratedType("net.datenwerke.dtoservices.dtogenerator.DtoAnnotationProcessor")
public class CsvDatasourceConfigDto extends FormatBasedDatasourceConfigDto {


	private static final long serialVersionUID = 1;


	/* Fields */
	private String queryWrapper;
	private  boolean queryWrapper_m;
	public static final String PROPERTY_QUERY_WRAPPER = "dpi-csvdatasourceconfig-querywrapper";

	private transient static PropertyAccessor<CsvDatasourceConfigDto, String> queryWrapper_pa = new PropertyAccessor<CsvDatasourceConfigDto, String>() {
		@Override
		public void setValue(CsvDatasourceConfigDto container, String object) {
			container.setQueryWrapper(object);
		}

		@Override
		public String getValue(CsvDatasourceConfigDto container) {
			return container.getQueryWrapper();
		}

		@Override
		public Class<?> getType() {
			return String.class;
		}

		@Override
		public String getPath() {
			return "queryWrapper";
		}

		@Override
		public void setModified(CsvDatasourceConfigDto container, boolean modified) {
			container.queryWrapper_m = modified;
		}

		@Override
		public boolean isModified(CsvDatasourceConfigDto container) {
			return container.isQueryWrapperModified();
		}
	};


	public CsvDatasourceConfigDto() {
		super();
	}

	public String getQueryWrapper()  {
		if(! isDtoProxy()){
			return this.queryWrapper;
		}

		if(isQueryWrapperModified())
			return this.queryWrapper;

		if(! GWT.isClient())
			return null;

		String _value = dtoManager.getProperty(this, instantiatePropertyAccess().queryWrapper());

		return _value;
	}


	public void setQueryWrapper(String queryWrapper)  {
		/* old value */
		String oldValue = null;
		if(GWT.isClient())
			oldValue = getQueryWrapper();

		/* set new value */
		this.queryWrapper = queryWrapper;

		if(! GWT.isClient())
			return;

		if(isTrackChanges())
			addChange(new ChangeTracker(queryWrapper_pa, oldValue, queryWrapper, this.queryWrapper_m));

		/* set indicator */
		this.queryWrapper_m = true;

		this.fireObjectChangedEvent(CsvDatasourceConfigDtoPA.INSTANCE.queryWrapper(), oldValue);
	}


	public boolean isQueryWrapperModified()  {
		return queryWrapper_m;
	}


	public static PropertyAccessor<CsvDatasourceConfigDto, String> getQueryWrapperPropertyAccessor()  {
		return queryWrapper_pa;
	}


	@Override
	public int hashCode()  {
		if(null == getId())
			return super.hashCode();
		return getId().hashCode();
	}

	@Override
	public boolean equals(Object obj)  {
		if(! (obj instanceof CsvDatasourceConfigDto))
			return false;

		if(null == getId())
			return super.equals(obj);
		return getId().equals(((CsvDatasourceConfigDto)obj).getId());
	}

	@Override
	public String toString()  {
		return getClass().getName() + ": " + getId();
	}

	public static Dto2PosoMapper newPosoMapper()  {
		return new CsvDatasourceConfigDto2PosoMap();
	}

	public CsvDatasourceConfigDtoPA instantiatePropertyAccess()  {
		return GWT.create(CsvDatasourceConfigDtoPA.class);
	}

	public void clearModified()  {
		this.queryWrapper = null;
		this.queryWrapper_m = false;
	}


	public boolean isModified()  {
		if(super.isModified())
			return true;
		if(queryWrapper_m)
			return true;
		return false;
	}


	public List<PropertyAccessor> getPropertyAccessors()  {
		List<PropertyAccessor> list = super.getPropertyAccessors();
		list.add(queryWrapper_pa);
		return list;
	}


	public List<PropertyAccessor> getModifiedPropertyAccessors()  {
		List<PropertyAccessor> list = super.getModifiedPropertyAccessors();
		if(queryWrapper_m)
			list.add(queryWrapper_pa);
		return list;
	}


	public List<PropertyAccessor> getPropertyAccessorsByView(net.datenwerke.gxtdto.client.dtomanager.DtoView view)  {
		List<PropertyAccessor> list = super.getPropertyAccessorsByView(view);
		if(view.compareTo(DtoView.NORMAL) >= 0){
			list.add(queryWrapper_pa);
		}
		return list;
	}


	public List<PropertyAccessor> getPropertyAccessorsForDtos()  {
		List<PropertyAccessor> list = super.getPropertyAccessorsForDtos();
		return list;
	}




}
