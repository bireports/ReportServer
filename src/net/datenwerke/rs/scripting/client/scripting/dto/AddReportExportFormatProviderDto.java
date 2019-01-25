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
 
 
package net.datenwerke.rs.scripting.client.scripting.dto;

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
import net.datenwerke.rs.core.client.reportmanager.dto.reports.ReportDto;
import net.datenwerke.rs.scripting.client.scripting.dto.pa.AddReportExportFormatProviderDtoPA;
import net.datenwerke.rs.scripting.client.scripting.dto.posomap.AddReportExportFormatProviderDto2PosoMap;
import net.datenwerke.rs.scripting.service.scripting.extensions.AddReportExportFormatProvider;
import net.datenwerke.rs.terminal.client.terminal.dto.CommandResultExtensionDto;

/**
 * Dto for {@link AddReportExportFormatProvider}
 *
 * This file was automatically created by DtoAnnotationProcessor, version 0.1
 */
@GeneratedType("net.datenwerke.dtoservices.dtogenerator.DtoAnnotationProcessor")
public class AddReportExportFormatProviderDto extends CommandResultExtensionDto {


	private static final long serialVersionUID = 1;


	/* Fields */
	private String description;
	private  boolean description_m;
	public static final String PROPERTY_DESCRIPTION = "dpi-addreportexportformatprovider-description";

	private transient static PropertyAccessor<AddReportExportFormatProviderDto, String> description_pa = new PropertyAccessor<AddReportExportFormatProviderDto, String>() {
		@Override
		public void setValue(AddReportExportFormatProviderDto container, String object) {
			container.setDescription(object);
		}

		@Override
		public String getValue(AddReportExportFormatProviderDto container) {
			return container.getDescription();
		}

		@Override
		public Class<?> getType() {
			return String.class;
		}

		@Override
		public String getPath() {
			return "description";
		}

		@Override
		public void setModified(AddReportExportFormatProviderDto container, boolean modified) {
			container.description_m = modified;
		}

		@Override
		public boolean isModified(AddReportExportFormatProviderDto container) {
			return container.isDescriptionModified();
		}
	};

	private String icon;
	private  boolean icon_m;
	public static final String PROPERTY_ICON = "dpi-addreportexportformatprovider-icon";

	private transient static PropertyAccessor<AddReportExportFormatProviderDto, String> icon_pa = new PropertyAccessor<AddReportExportFormatProviderDto, String>() {
		@Override
		public void setValue(AddReportExportFormatProviderDto container, String object) {
			container.setIcon(object);
		}

		@Override
		public String getValue(AddReportExportFormatProviderDto container) {
			return container.getIcon();
		}

		@Override
		public Class<?> getType() {
			return String.class;
		}

		@Override
		public String getPath() {
			return "icon";
		}

		@Override
		public void setModified(AddReportExportFormatProviderDto container, boolean modified) {
			container.icon_m = modified;
		}

		@Override
		public boolean isModified(AddReportExportFormatProviderDto container) {
			return container.isIconModified();
		}
	};

	private String outputFormat;
	private  boolean outputFormat_m;
	public static final String PROPERTY_OUTPUT_FORMAT = "dpi-addreportexportformatprovider-outputformat";

	private transient static PropertyAccessor<AddReportExportFormatProviderDto, String> outputFormat_pa = new PropertyAccessor<AddReportExportFormatProviderDto, String>() {
		@Override
		public void setValue(AddReportExportFormatProviderDto container, String object) {
			container.setOutputFormat(object);
		}

		@Override
		public String getValue(AddReportExportFormatProviderDto container) {
			return container.getOutputFormat();
		}

		@Override
		public Class<?> getType() {
			return String.class;
		}

		@Override
		public String getPath() {
			return "outputFormat";
		}

		@Override
		public void setModified(AddReportExportFormatProviderDto container, boolean modified) {
			container.outputFormat_m = modified;
		}

		@Override
		public boolean isModified(AddReportExportFormatProviderDto container) {
			return container.isOutputFormatModified();
		}
	};

	private List<Long> parentIdWhitelist;
	private  boolean parentIdWhitelist_m;
	public static final String PROPERTY_PARENT_ID_WHITELIST = "dpi-addreportexportformatprovider-parentidwhitelist";

	private transient static PropertyAccessor<AddReportExportFormatProviderDto, List<Long>> parentIdWhitelist_pa = new PropertyAccessor<AddReportExportFormatProviderDto, List<Long>>() {
		@Override
		public void setValue(AddReportExportFormatProviderDto container, List<Long> object) {
			container.setParentIdWhitelist(object);
		}

		@Override
		public List<Long> getValue(AddReportExportFormatProviderDto container) {
			return container.getParentIdWhitelist();
		}

		@Override
		public Class<?> getType() {
			return List.class;
		}

		@Override
		public String getPath() {
			return "parentIdWhitelist";
		}

		@Override
		public void setModified(AddReportExportFormatProviderDto container, boolean modified) {
			container.parentIdWhitelist_m = modified;
		}

		@Override
		public boolean isModified(AddReportExportFormatProviderDto container) {
			return container.isParentIdWhitelistModified();
		}
	};

	private List<String> parentKeyWhitelist;
	private  boolean parentKeyWhitelist_m;
	public static final String PROPERTY_PARENT_KEY_WHITELIST = "dpi-addreportexportformatprovider-parentkeywhitelist";

	private transient static PropertyAccessor<AddReportExportFormatProviderDto, List<String>> parentKeyWhitelist_pa = new PropertyAccessor<AddReportExportFormatProviderDto, List<String>>() {
		@Override
		public void setValue(AddReportExportFormatProviderDto container, List<String> object) {
			container.setParentKeyWhitelist(object);
		}

		@Override
		public List<String> getValue(AddReportExportFormatProviderDto container) {
			return container.getParentKeyWhitelist();
		}

		@Override
		public Class<?> getType() {
			return List.class;
		}

		@Override
		public String getPath() {
			return "parentKeyWhitelist";
		}

		@Override
		public void setModified(AddReportExportFormatProviderDto container, boolean modified) {
			container.parentKeyWhitelist_m = modified;
		}

		@Override
		public boolean isModified(AddReportExportFormatProviderDto container) {
			return container.isParentKeyWhitelistModified();
		}
	};

	private List<Long> reportIdWhitelist;
	private  boolean reportIdWhitelist_m;
	public static final String PROPERTY_REPORT_ID_WHITELIST = "dpi-addreportexportformatprovider-reportidwhitelist";

	private transient static PropertyAccessor<AddReportExportFormatProviderDto, List<Long>> reportIdWhitelist_pa = new PropertyAccessor<AddReportExportFormatProviderDto, List<Long>>() {
		@Override
		public void setValue(AddReportExportFormatProviderDto container, List<Long> object) {
			container.setReportIdWhitelist(object);
		}

		@Override
		public List<Long> getValue(AddReportExportFormatProviderDto container) {
			return container.getReportIdWhitelist();
		}

		@Override
		public Class<?> getType() {
			return List.class;
		}

		@Override
		public String getPath() {
			return "reportIdWhitelist";
		}

		@Override
		public void setModified(AddReportExportFormatProviderDto container, boolean modified) {
			container.reportIdWhitelist_m = modified;
		}

		@Override
		public boolean isModified(AddReportExportFormatProviderDto container) {
			return container.isReportIdWhitelistModified();
		}
	};

	private List<String> reportKeyWhitelist;
	private  boolean reportKeyWhitelist_m;
	public static final String PROPERTY_REPORT_KEY_WHITELIST = "dpi-addreportexportformatprovider-reportkeywhitelist";

	private transient static PropertyAccessor<AddReportExportFormatProviderDto, List<String>> reportKeyWhitelist_pa = new PropertyAccessor<AddReportExportFormatProviderDto, List<String>>() {
		@Override
		public void setValue(AddReportExportFormatProviderDto container, List<String> object) {
			container.setReportKeyWhitelist(object);
		}

		@Override
		public List<String> getValue(AddReportExportFormatProviderDto container) {
			return container.getReportKeyWhitelist();
		}

		@Override
		public Class<?> getType() {
			return List.class;
		}

		@Override
		public String getPath() {
			return "reportKeyWhitelist";
		}

		@Override
		public void setModified(AddReportExportFormatProviderDto container, boolean modified) {
			container.reportKeyWhitelist_m = modified;
		}

		@Override
		public boolean isModified(AddReportExportFormatProviderDto container) {
			return container.isReportKeyWhitelistModified();
		}
	};

	private ReportDto reportType;
	private  boolean reportType_m;
	public static final String PROPERTY_REPORT_TYPE = "dpi-addreportexportformatprovider-reporttype";

	private transient static PropertyAccessor<AddReportExportFormatProviderDto, ReportDto> reportType_pa = new PropertyAccessor<AddReportExportFormatProviderDto, ReportDto>() {
		@Override
		public void setValue(AddReportExportFormatProviderDto container, ReportDto object) {
			container.setReportType(object);
		}

		@Override
		public ReportDto getValue(AddReportExportFormatProviderDto container) {
			return container.getReportType();
		}

		@Override
		public Class<?> getType() {
			return ReportDto.class;
		}

		@Override
		public String getPath() {
			return "reportType";
		}

		@Override
		public void setModified(AddReportExportFormatProviderDto container, boolean modified) {
			container.reportType_m = modified;
		}

		@Override
		public boolean isModified(AddReportExportFormatProviderDto container) {
			return container.isReportTypeModified();
		}
	};

	private String title;
	private  boolean title_m;
	public static final String PROPERTY_TITLE = "dpi-addreportexportformatprovider-title";

	private transient static PropertyAccessor<AddReportExportFormatProviderDto, String> title_pa = new PropertyAccessor<AddReportExportFormatProviderDto, String>() {
		@Override
		public void setValue(AddReportExportFormatProviderDto container, String object) {
			container.setTitle(object);
		}

		@Override
		public String getValue(AddReportExportFormatProviderDto container) {
			return container.getTitle();
		}

		@Override
		public Class<?> getType() {
			return String.class;
		}

		@Override
		public String getPath() {
			return "title";
		}

		@Override
		public void setModified(AddReportExportFormatProviderDto container, boolean modified) {
			container.title_m = modified;
		}

		@Override
		public boolean isModified(AddReportExportFormatProviderDto container) {
			return container.isTitleModified();
		}
	};


	public AddReportExportFormatProviderDto() {
		super();
	}

	public String getDescription()  {
		if(! isDtoProxy()){
			return this.description;
		}

		if(isDescriptionModified())
			return this.description;

		if(! GWT.isClient())
			return null;

		String _value = dtoManager.getProperty(this, instantiatePropertyAccess().description());

		return _value;
	}


	public void setDescription(String description)  {
		/* old value */
		String oldValue = null;
		if(GWT.isClient())
			oldValue = getDescription();

		/* set new value */
		this.description = description;

		if(! GWT.isClient())
			return;

		if(isTrackChanges())
			addChange(new ChangeTracker(description_pa, oldValue, description, this.description_m));

		/* set indicator */
		this.description_m = true;

		this.fireObjectChangedEvent(AddReportExportFormatProviderDtoPA.INSTANCE.description(), oldValue);
	}


	public boolean isDescriptionModified()  {
		return description_m;
	}


	public static PropertyAccessor<AddReportExportFormatProviderDto, String> getDescriptionPropertyAccessor()  {
		return description_pa;
	}


	public String getIcon()  {
		if(! isDtoProxy()){
			return this.icon;
		}

		if(isIconModified())
			return this.icon;

		if(! GWT.isClient())
			return null;

		String _value = dtoManager.getProperty(this, instantiatePropertyAccess().icon());

		return _value;
	}


	public void setIcon(String icon)  {
		/* old value */
		String oldValue = null;
		if(GWT.isClient())
			oldValue = getIcon();

		/* set new value */
		this.icon = icon;

		if(! GWT.isClient())
			return;

		if(isTrackChanges())
			addChange(new ChangeTracker(icon_pa, oldValue, icon, this.icon_m));

		/* set indicator */
		this.icon_m = true;

		this.fireObjectChangedEvent(AddReportExportFormatProviderDtoPA.INSTANCE.icon(), oldValue);
	}


	public boolean isIconModified()  {
		return icon_m;
	}


	public static PropertyAccessor<AddReportExportFormatProviderDto, String> getIconPropertyAccessor()  {
		return icon_pa;
	}


	public String getOutputFormat()  {
		if(! isDtoProxy()){
			return this.outputFormat;
		}

		if(isOutputFormatModified())
			return this.outputFormat;

		if(! GWT.isClient())
			return null;

		String _value = dtoManager.getProperty(this, instantiatePropertyAccess().outputFormat());

		return _value;
	}


	public void setOutputFormat(String outputFormat)  {
		/* old value */
		String oldValue = null;
		if(GWT.isClient())
			oldValue = getOutputFormat();

		/* set new value */
		this.outputFormat = outputFormat;

		if(! GWT.isClient())
			return;

		if(isTrackChanges())
			addChange(new ChangeTracker(outputFormat_pa, oldValue, outputFormat, this.outputFormat_m));

		/* set indicator */
		this.outputFormat_m = true;

		this.fireObjectChangedEvent(AddReportExportFormatProviderDtoPA.INSTANCE.outputFormat(), oldValue);
	}


	public boolean isOutputFormatModified()  {
		return outputFormat_m;
	}


	public static PropertyAccessor<AddReportExportFormatProviderDto, String> getOutputFormatPropertyAccessor()  {
		return outputFormat_pa;
	}


	public List<Long> getParentIdWhitelist()  {
		if(! isDtoProxy()){
			List<Long> _currentValue = this.parentIdWhitelist;
			if(null == _currentValue)
				this.parentIdWhitelist = new ArrayList<Long>();

			return this.parentIdWhitelist;
		}

		if(isParentIdWhitelistModified())
			return this.parentIdWhitelist;

		if(! GWT.isClient())
			return null;

		List<Long> _value = dtoManager.getProperty(this, instantiatePropertyAccess().parentIdWhitelist());

		return _value;
	}


	public void setParentIdWhitelist(List<Long> parentIdWhitelist)  {
		/* old value */
		List<Long> oldValue = null;
		if(GWT.isClient())
			oldValue = getParentIdWhitelist();

		/* set new value */
		this.parentIdWhitelist = parentIdWhitelist;

		if(! GWT.isClient())
			return;

		if(isTrackChanges())
			addChange(new ChangeTracker(parentIdWhitelist_pa, oldValue, parentIdWhitelist, this.parentIdWhitelist_m));

		/* set indicator */
		this.parentIdWhitelist_m = true;

		this.fireObjectChangedEvent(AddReportExportFormatProviderDtoPA.INSTANCE.parentIdWhitelist(), oldValue);
	}


	public boolean isParentIdWhitelistModified()  {
		return parentIdWhitelist_m;
	}


	public static PropertyAccessor<AddReportExportFormatProviderDto, List<Long>> getParentIdWhitelistPropertyAccessor()  {
		return parentIdWhitelist_pa;
	}


	public List<String> getParentKeyWhitelist()  {
		if(! isDtoProxy()){
			List<String> _currentValue = this.parentKeyWhitelist;
			if(null == _currentValue)
				this.parentKeyWhitelist = new ArrayList<String>();

			return this.parentKeyWhitelist;
		}

		if(isParentKeyWhitelistModified())
			return this.parentKeyWhitelist;

		if(! GWT.isClient())
			return null;

		List<String> _value = dtoManager.getProperty(this, instantiatePropertyAccess().parentKeyWhitelist());

		return _value;
	}


	public void setParentKeyWhitelist(List<String> parentKeyWhitelist)  {
		/* old value */
		List<String> oldValue = null;
		if(GWT.isClient())
			oldValue = getParentKeyWhitelist();

		/* set new value */
		this.parentKeyWhitelist = parentKeyWhitelist;

		if(! GWT.isClient())
			return;

		if(isTrackChanges())
			addChange(new ChangeTracker(parentKeyWhitelist_pa, oldValue, parentKeyWhitelist, this.parentKeyWhitelist_m));

		/* set indicator */
		this.parentKeyWhitelist_m = true;

		this.fireObjectChangedEvent(AddReportExportFormatProviderDtoPA.INSTANCE.parentKeyWhitelist(), oldValue);
	}


	public boolean isParentKeyWhitelistModified()  {
		return parentKeyWhitelist_m;
	}


	public static PropertyAccessor<AddReportExportFormatProviderDto, List<String>> getParentKeyWhitelistPropertyAccessor()  {
		return parentKeyWhitelist_pa;
	}


	public List<Long> getReportIdWhitelist()  {
		if(! isDtoProxy()){
			List<Long> _currentValue = this.reportIdWhitelist;
			if(null == _currentValue)
				this.reportIdWhitelist = new ArrayList<Long>();

			return this.reportIdWhitelist;
		}

		if(isReportIdWhitelistModified())
			return this.reportIdWhitelist;

		if(! GWT.isClient())
			return null;

		List<Long> _value = dtoManager.getProperty(this, instantiatePropertyAccess().reportIdWhitelist());

		return _value;
	}


	public void setReportIdWhitelist(List<Long> reportIdWhitelist)  {
		/* old value */
		List<Long> oldValue = null;
		if(GWT.isClient())
			oldValue = getReportIdWhitelist();

		/* set new value */
		this.reportIdWhitelist = reportIdWhitelist;

		if(! GWT.isClient())
			return;

		if(isTrackChanges())
			addChange(new ChangeTracker(reportIdWhitelist_pa, oldValue, reportIdWhitelist, this.reportIdWhitelist_m));

		/* set indicator */
		this.reportIdWhitelist_m = true;

		this.fireObjectChangedEvent(AddReportExportFormatProviderDtoPA.INSTANCE.reportIdWhitelist(), oldValue);
	}


	public boolean isReportIdWhitelistModified()  {
		return reportIdWhitelist_m;
	}


	public static PropertyAccessor<AddReportExportFormatProviderDto, List<Long>> getReportIdWhitelistPropertyAccessor()  {
		return reportIdWhitelist_pa;
	}


	public List<String> getReportKeyWhitelist()  {
		if(! isDtoProxy()){
			List<String> _currentValue = this.reportKeyWhitelist;
			if(null == _currentValue)
				this.reportKeyWhitelist = new ArrayList<String>();

			return this.reportKeyWhitelist;
		}

		if(isReportKeyWhitelistModified())
			return this.reportKeyWhitelist;

		if(! GWT.isClient())
			return null;

		List<String> _value = dtoManager.getProperty(this, instantiatePropertyAccess().reportKeyWhitelist());

		return _value;
	}


	public void setReportKeyWhitelist(List<String> reportKeyWhitelist)  {
		/* old value */
		List<String> oldValue = null;
		if(GWT.isClient())
			oldValue = getReportKeyWhitelist();

		/* set new value */
		this.reportKeyWhitelist = reportKeyWhitelist;

		if(! GWT.isClient())
			return;

		if(isTrackChanges())
			addChange(new ChangeTracker(reportKeyWhitelist_pa, oldValue, reportKeyWhitelist, this.reportKeyWhitelist_m));

		/* set indicator */
		this.reportKeyWhitelist_m = true;

		this.fireObjectChangedEvent(AddReportExportFormatProviderDtoPA.INSTANCE.reportKeyWhitelist(), oldValue);
	}


	public boolean isReportKeyWhitelistModified()  {
		return reportKeyWhitelist_m;
	}


	public static PropertyAccessor<AddReportExportFormatProviderDto, List<String>> getReportKeyWhitelistPropertyAccessor()  {
		return reportKeyWhitelist_pa;
	}


	public ReportDto getReportType()  {
		if(! isDtoProxy()){
			return this.reportType;
		}

		if(isReportTypeModified())
			return this.reportType;

		if(! GWT.isClient())
			return null;

		ReportDto _value = dtoManager.getProperty(this, instantiatePropertyAccess().reportType());

		return _value;
	}


	public void setReportType(ReportDto reportType)  {
		/* old value */
		ReportDto oldValue = null;
		if(GWT.isClient())
			oldValue = getReportType();

		/* set new value */
		this.reportType = reportType;

		if(! GWT.isClient())
			return;

		if(isTrackChanges())
			addChange(new ChangeTracker(reportType_pa, oldValue, reportType, this.reportType_m));

		/* set indicator */
		this.reportType_m = true;

		this.fireObjectChangedEvent(AddReportExportFormatProviderDtoPA.INSTANCE.reportType(), oldValue);
	}


	public boolean isReportTypeModified()  {
		return reportType_m;
	}


	public static PropertyAccessor<AddReportExportFormatProviderDto, ReportDto> getReportTypePropertyAccessor()  {
		return reportType_pa;
	}


	public String getTitle()  {
		if(! isDtoProxy()){
			return this.title;
		}

		if(isTitleModified())
			return this.title;

		if(! GWT.isClient())
			return null;

		String _value = dtoManager.getProperty(this, instantiatePropertyAccess().title());

		return _value;
	}


	public void setTitle(String title)  {
		/* old value */
		String oldValue = null;
		if(GWT.isClient())
			oldValue = getTitle();

		/* set new value */
		this.title = title;

		if(! GWT.isClient())
			return;

		if(isTrackChanges())
			addChange(new ChangeTracker(title_pa, oldValue, title, this.title_m));

		/* set indicator */
		this.title_m = true;

		this.fireObjectChangedEvent(AddReportExportFormatProviderDtoPA.INSTANCE.title(), oldValue);
	}


	public boolean isTitleModified()  {
		return title_m;
	}


	public static PropertyAccessor<AddReportExportFormatProviderDto, String> getTitlePropertyAccessor()  {
		return title_pa;
	}


	@Override
	public String toString()  {
		return super.toString();
	}

	public static Dto2PosoMapper newPosoMapper()  {
		return new AddReportExportFormatProviderDto2PosoMap();
	}

	public AddReportExportFormatProviderDtoPA instantiatePropertyAccess()  {
		return GWT.create(AddReportExportFormatProviderDtoPA.class);
	}

	public void clearModified()  {
		this.description = null;
		this.description_m = false;
		this.icon = null;
		this.icon_m = false;
		this.outputFormat = null;
		this.outputFormat_m = false;
		this.parentIdWhitelist = null;
		this.parentIdWhitelist_m = false;
		this.parentKeyWhitelist = null;
		this.parentKeyWhitelist_m = false;
		this.reportIdWhitelist = null;
		this.reportIdWhitelist_m = false;
		this.reportKeyWhitelist = null;
		this.reportKeyWhitelist_m = false;
		this.reportType = null;
		this.reportType_m = false;
		this.title = null;
		this.title_m = false;
	}


	public boolean isModified()  {
		if(super.isModified())
			return true;
		if(description_m)
			return true;
		if(icon_m)
			return true;
		if(outputFormat_m)
			return true;
		if(parentIdWhitelist_m)
			return true;
		if(parentKeyWhitelist_m)
			return true;
		if(reportIdWhitelist_m)
			return true;
		if(reportKeyWhitelist_m)
			return true;
		if(reportType_m)
			return true;
		if(title_m)
			return true;
		return false;
	}


	public List<PropertyAccessor> getPropertyAccessors()  {
		List<PropertyAccessor> list = super.getPropertyAccessors();
		list.add(description_pa);
		list.add(icon_pa);
		list.add(outputFormat_pa);
		list.add(parentIdWhitelist_pa);
		list.add(parentKeyWhitelist_pa);
		list.add(reportIdWhitelist_pa);
		list.add(reportKeyWhitelist_pa);
		list.add(reportType_pa);
		list.add(title_pa);
		return list;
	}


	public List<PropertyAccessor> getModifiedPropertyAccessors()  {
		List<PropertyAccessor> list = super.getModifiedPropertyAccessors();
		if(description_m)
			list.add(description_pa);
		if(icon_m)
			list.add(icon_pa);
		if(outputFormat_m)
			list.add(outputFormat_pa);
		if(parentIdWhitelist_m)
			list.add(parentIdWhitelist_pa);
		if(parentKeyWhitelist_m)
			list.add(parentKeyWhitelist_pa);
		if(reportIdWhitelist_m)
			list.add(reportIdWhitelist_pa);
		if(reportKeyWhitelist_m)
			list.add(reportKeyWhitelist_pa);
		if(reportType_m)
			list.add(reportType_pa);
		if(title_m)
			list.add(title_pa);
		return list;
	}


	public List<PropertyAccessor> getPropertyAccessorsByView(net.datenwerke.gxtdto.client.dtomanager.DtoView view)  {
		List<PropertyAccessor> list = super.getPropertyAccessorsByView(view);
		if(view.compareTo(DtoView.NORMAL) >= 0){
			list.add(description_pa);
			list.add(icon_pa);
			list.add(outputFormat_pa);
			list.add(parentIdWhitelist_pa);
			list.add(parentKeyWhitelist_pa);
			list.add(reportIdWhitelist_pa);
			list.add(reportKeyWhitelist_pa);
			list.add(reportType_pa);
			list.add(title_pa);
		}
		return list;
	}


	public List<PropertyAccessor> getPropertyAccessorsForDtos()  {
		List<PropertyAccessor> list = super.getPropertyAccessorsForDtos();
		return list;
	}




}
