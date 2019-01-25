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
 
 
package net.datenwerke.rs.base.service.reportengines.table.entities.format;

import java.text.DecimalFormat;
import java.text.NumberFormat;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

import net.datenwerke.dtoservices.dtogenerator.annotations.ExposeToClient;
import net.datenwerke.dtoservices.dtogenerator.annotations.GenerateDto;
import net.datenwerke.rs.base.service.reportengines.table.entities.format.enums.CurrencyType;
import net.datenwerke.rs.base.service.reportengines.table.entities.format.enums.NumberType;

import org.hibernate.envers.Audited;

@Entity
@Table(name="COLUMN_FORMAT_CURRENCY")
@Inheritance(strategy=InheritanceType.JOINED)
@Audited
@GenerateDto(
	dtoPackage="net.datenwerke.rs.base.client.reportengines.table.dto",
	createDecorator=true
)
public class ColumnFormatCurrency extends ColumnFormatNumber {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8924716725557026930L;

	@ExposeToClient
	private CurrencyType currencyType = CurrencyType.EURO;

	public void setCurrencyType(CurrencyType currencyType) {
		this.currencyType = currencyType;
	}

	public CurrencyType getCurrencyType() {
		return currencyType;
	}

	@Override
	public void setType(NumberType type) {
		/* do not allow to change the type from default to something else */
	}

	@Override
	public NumberFormat getNumberFormat() {
		NumberFormat format = super.getNumberFormat();
		((DecimalFormat)format).setCurrency(getCurrencyType().getCurrency());
		return format;
	}
	
	@Override
	public String getPattern() {
		return getDecimalPattern() + " \u00A4" ;
	}
}
