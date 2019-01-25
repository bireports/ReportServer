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
 
 
package net.datenwerke.rs.saiku.client.datasource.dto;

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
import net.datenwerke.rs.core.client.datasourcemanager.dto.DatasourceDefinitionConfigDto;
import net.datenwerke.rs.saiku.client.datasource.dto.pa.MondrianDatasourceConfigDtoPA;
import net.datenwerke.rs.saiku.client.datasource.dto.posomap.MondrianDatasourceConfigDto2PosoMap;
import net.datenwerke.rs.saiku.service.datasource.MondrianDatasourceConfig;

/**
 * Dto for {@link MondrianDatasourceConfig}
 *
 * This file was automatically created by DtoAnnotationProcessor, version 0.1
 */
@GeneratedType("net.datenwerke.dtoservices.dtogenerator.DtoAnnotationProcessor")
public class MondrianDatasourceConfigDto extends DatasourceDefinitionConfigDto {


	private static final long serialVersionUID = 1;


	/* Fields */
	private String cube;
	private  boolean cube_m;
	public static final String PROPERTY_CUBE = "dpi-mondriandatasourceconfig-cube";

	private transient static PropertyAccessor<MondrianDatasourceConfigDto, String> cube_pa = new PropertyAccessor<MondrianDatasourceConfigDto, String>() {
		@Override
		public void setValue(MondrianDatasourceConfigDto container, String object) {
			container.setCube(object);
		}

		@Override
		public String getValue(MondrianDatasourceConfigDto container) {
			return container.getCube();
		}

		@Override
		public Class<?> getType() {
			return String.class;
		}

		@Override
		public String getPath() {
			return "cube";
		}

		@Override
		public void setModified(MondrianDatasourceConfigDto container, boolean modified) {
			container.cube_m = modified;
		}

		@Override
		public boolean isModified(MondrianDatasourceConfigDto container) {
			return container.isCubeModified();
		}
	};


	public MondrianDatasourceConfigDto() {
		super();
	}

	public String getCube()  {
		if(! isDtoProxy()){
			return this.cube;
		}

		if(isCubeModified())
			return this.cube;

		if(! GWT.isClient())
			return null;

		String _value = dtoManager.getProperty(this, instantiatePropertyAccess().cube());

		return _value;
	}


	public void setCube(String cube)  {
		/* old value */
		String oldValue = null;
		if(GWT.isClient())
			oldValue = getCube();

		/* set new value */
		this.cube = cube;

		if(! GWT.isClient())
			return;

		if(isTrackChanges())
			addChange(new ChangeTracker(cube_pa, oldValue, cube, this.cube_m));

		/* set indicator */
		this.cube_m = true;

		this.fireObjectChangedEvent(MondrianDatasourceConfigDtoPA.INSTANCE.cube(), oldValue);
	}


	public boolean isCubeModified()  {
		return cube_m;
	}


	public static PropertyAccessor<MondrianDatasourceConfigDto, String> getCubePropertyAccessor()  {
		return cube_pa;
	}


	@Override
	public int hashCode()  {
		if(null == getId())
			return super.hashCode();
		return getId().hashCode();
	}

	@Override
	public boolean equals(Object obj)  {
		if(! (obj instanceof MondrianDatasourceConfigDto))
			return false;

		if(null == getId())
			return super.equals(obj);
		return getId().equals(((MondrianDatasourceConfigDto)obj).getId());
	}

	@Override
	public String toString()  {
		return getClass().getName() + ": " + getId();
	}

	public static Dto2PosoMapper newPosoMapper()  {
		return new MondrianDatasourceConfigDto2PosoMap();
	}

	public MondrianDatasourceConfigDtoPA instantiatePropertyAccess()  {
		return GWT.create(MondrianDatasourceConfigDtoPA.class);
	}

	public void clearModified()  {
		this.cube = null;
		this.cube_m = false;
	}


	public boolean isModified()  {
		if(super.isModified())
			return true;
		if(cube_m)
			return true;
		return false;
	}


	public List<PropertyAccessor> getPropertyAccessors()  {
		List<PropertyAccessor> list = super.getPropertyAccessors();
		list.add(cube_pa);
		return list;
	}


	public List<PropertyAccessor> getModifiedPropertyAccessors()  {
		List<PropertyAccessor> list = super.getModifiedPropertyAccessors();
		if(cube_m)
			list.add(cube_pa);
		return list;
	}


	public List<PropertyAccessor> getPropertyAccessorsByView(net.datenwerke.gxtdto.client.dtomanager.DtoView view)  {
		List<PropertyAccessor> list = super.getPropertyAccessorsByView(view);
		if(view.compareTo(DtoView.NORMAL) >= 0){
			list.add(cube_pa);
		}
		return list;
	}


	public List<PropertyAccessor> getPropertyAccessorsForDtos()  {
		List<PropertyAccessor> list = super.getPropertyAccessorsForDtos();
		return list;
	}




}
