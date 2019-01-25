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
 
 
package net.datenwerke.scheduler.service.scheduler.stores.jpa.filter.dtogen;

import com.google.inject.Inject;
import com.google.inject.Provider;
import java.lang.IllegalArgumentException;
import net.datenwerke.dtoservices.dtogenerator.annotations.GeneratedType;
import net.datenwerke.dtoservices.dtogenerator.poso2dtogenerator.interfaces.Poso2DtoGenerator;
import net.datenwerke.gxtdto.client.dtomanager.DtoView;
import net.datenwerke.gxtdto.server.dtomanager.DtoMainService;
import net.datenwerke.gxtdto.server.dtomanager.DtoService;
import net.datenwerke.scheduler.client.scheduler.dto.filter.OrderDto;
import net.datenwerke.scheduler.service.scheduler.stores.jpa.filter.Order;
import net.datenwerke.scheduler.service.scheduler.stores.jpa.filter.dtogen.Order2DtoGenerator;

/**
 * Poso2DtoGenerator for Order
 *
 * This file was automatically created by DtoAnnotationProcessor, version 0.1
 */
@GeneratedType("net.datenwerke.dtoservices.dtogenerator.DtoAnnotationProcessor")
public class Order2DtoGenerator implements Poso2DtoGenerator<Order,OrderDto> {

	private final Provider<DtoService> dtoServiceProvider;

	@Inject
	public Order2DtoGenerator(
		Provider<DtoService> dtoServiceProvider	){
		this.dtoServiceProvider = dtoServiceProvider;
	}

	public OrderDto instantiateDto(Order poso)  {
		/* Simply return the first enum! */
		OrderDto dto = OrderDto.ASC;
		return dto;
	}

	public OrderDto createDto(Order poso, DtoView here, DtoView referenced)  {
		switch(poso){
			case ASC:
				return OrderDto.ASC;
			case DESC:
				return OrderDto.DESC;
		}
		throw new IllegalArgumentException("unknown enum type for: " + poso);
	}


}
