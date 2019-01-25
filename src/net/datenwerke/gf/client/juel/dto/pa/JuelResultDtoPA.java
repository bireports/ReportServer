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
 
 
package net.datenwerke.gf.client.juel.dto.pa;

import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.Editor.Path;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;
import java.lang.Boolean;
import java.lang.Double;
import java.lang.Float;
import java.lang.Integer;
import java.lang.Long;
import java.lang.String;
import java.math.BigDecimal;
import java.util.Date;
import net.datenwerke.dtoservices.dtogenerator.annotations.CorrespondingPoso;
import net.datenwerke.dtoservices.dtogenerator.annotations.GeneratedType;
import net.datenwerke.gf.client.juel.dto.JuelResultDto;
import net.datenwerke.gf.client.juel.dto.JuelResultTypeDto;
import net.datenwerke.gf.client.juel.dto.decorator.JuelResultDtoDec;

/**
 * This file was automatically created by DtoAnnotationProcessor, version 0.1
 */
@GeneratedType("net.datenwerke.dtoservices.dtogenerator.DtoAnnotationProcessor")
@CorrespondingPoso(net.datenwerke.gf.service.juel.JuelResult.class)
public interface JuelResultDtoPA extends PropertyAccess<JuelResultDto> {


	public static final JuelResultDtoPA INSTANCE = GWT.create(JuelResultDtoPA.class);


	/* Properties */
	public ValueProvider<JuelResultDto,Boolean> booleanValue();
	public ValueProvider<JuelResultDto,Date> dateValue();
	public ValueProvider<JuelResultDto,BigDecimal> decimalValue();
	public ValueProvider<JuelResultDto,Double> doubleValue();
	public ValueProvider<JuelResultDto,Boolean> entryNull();
	public ValueProvider<JuelResultDto,Float> floatValue();
	public ValueProvider<JuelResultDto,Integer> intValue();
	public ValueProvider<JuelResultDto,Long> longValue();
	public ValueProvider<JuelResultDto,String> stringValue();
	public ValueProvider<JuelResultDto,JuelResultTypeDto> type();


}
