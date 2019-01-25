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
 
 
package net.datenwerke.rs.grideditor.client.grideditor.dto;

import com.google.gwt.core.client.GWT;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import net.datenwerke.dtoservices.dtogenerator.annotations.GeneratedType;
import net.datenwerke.gxtdto.client.dtomanager.Dto2PosoMapper;
import net.datenwerke.gxtdto.client.dtomanager.DtoView;
import net.datenwerke.gxtdto.client.dtomanager.PropertyAccessor;
import net.datenwerke.gxtdto.client.dtomanager.redoundo.ChangeTracker;
import net.datenwerke.rs.grideditor.client.grideditor.dto.decorator.ValidatorDtoDec;
import net.datenwerke.rs.grideditor.client.grideditor.dto.pa.MaxLengthValidatorDtoPA;
import net.datenwerke.rs.grideditor.client.grideditor.dto.posomap.MaxLengthValidatorDto2PosoMap;
import net.datenwerke.rs.grideditor.service.grideditor.definition.validator.MaxLengthValidator;

/**
 * Dto for {@link MaxLengthValidator}
 *
 * This file was automatically created by DtoAnnotationProcessor, version 0.1
 */
@GeneratedType("net.datenwerke.dtoservices.dtogenerator.DtoAnnotationProcessor")
abstract public class MaxLengthValidatorDto extends ValidatorDtoDec {


	private static final long serialVersionUID = 1;


	/* Fields */
	private int length;
	private  boolean length_m;
	public static final String PROPERTY_LENGTH = "dpi-maxlengthvalidator-length";

	private transient static PropertyAccessor<MaxLengthValidatorDto, Integer> length_pa = new PropertyAccessor<MaxLengthValidatorDto, Integer>() {
		@Override
		public void setValue(MaxLengthValidatorDto container, Integer object) {
			container.setLength(object);
		}

		@Override
		public Integer getValue(MaxLengthValidatorDto container) {
			return container.getLength();
		}

		@Override
		public Class<?> getType() {
			return Integer.class;
		}

		@Override
		public String getPath() {
			return "length";
		}

		@Override
		public void setModified(MaxLengthValidatorDto container, boolean modified) {
			container.length_m = modified;
		}

		@Override
		public boolean isModified(MaxLengthValidatorDto container) {
			return container.isLengthModified();
		}
	};


	public MaxLengthValidatorDto() {
		super();
	}

	public int getLength()  {
		if(! isDtoProxy()){
			return this.length;
		}

		if(isLengthModified())
			return this.length;

		if(! GWT.isClient())
			return 0;

		int _value = dtoManager.getProperty(this, instantiatePropertyAccess().length());

		return _value;
	}


	public void setLength(int length)  {
		/* old value */
		int oldValue = 0;
		if(GWT.isClient())
			oldValue = getLength();

		/* set new value */
		this.length = length;

		if(! GWT.isClient())
			return;

		if(isTrackChanges())
			addChange(new ChangeTracker(length_pa, oldValue, length, this.length_m));

		/* set indicator */
		this.length_m = true;

		this.fireObjectChangedEvent(MaxLengthValidatorDtoPA.INSTANCE.length(), oldValue);
	}


	public boolean isLengthModified()  {
		return length_m;
	}


	public static PropertyAccessor<MaxLengthValidatorDto, Integer> getLengthPropertyAccessor()  {
		return length_pa;
	}


	@Override
	public String toString()  {
		return super.toString();
	}

	public static Dto2PosoMapper newPosoMapper()  {
		return new MaxLengthValidatorDto2PosoMap();
	}

	public MaxLengthValidatorDtoPA instantiatePropertyAccess()  {
		return GWT.create(MaxLengthValidatorDtoPA.class);
	}

	public void clearModified()  {
		this.length = 0;
		this.length_m = false;
	}


	public boolean isModified()  {
		if(super.isModified())
			return true;
		if(length_m)
			return true;
		return false;
	}


	public List<PropertyAccessor> getPropertyAccessors()  {
		List<PropertyAccessor> list = super.getPropertyAccessors();
		list.add(length_pa);
		return list;
	}


	public List<PropertyAccessor> getModifiedPropertyAccessors()  {
		List<PropertyAccessor> list = super.getModifiedPropertyAccessors();
		if(length_m)
			list.add(length_pa);
		return list;
	}


	public List<PropertyAccessor> getPropertyAccessorsByView(net.datenwerke.gxtdto.client.dtomanager.DtoView view)  {
		List<PropertyAccessor> list = super.getPropertyAccessorsByView(view);
		if(view.compareTo(DtoView.NORMAL) >= 0){
			list.add(length_pa);
		}
		return list;
	}


	public List<PropertyAccessor> getPropertyAccessorsForDtos()  {
		List<PropertyAccessor> list = super.getPropertyAccessorsForDtos();
		return list;
	}




}
