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
 
 
package net.datenwerke.rs.base.service.reportengines.table.entities.format.enums;

import java.util.Currency;
import java.util.Locale;

import net.datenwerke.dtoservices.dtogenerator.annotations.EnumLabel;
import net.datenwerke.dtoservices.dtogenerator.annotations.GenerateDto;
import net.datenwerke.rs.base.client.reportengines.table.locale.EnumMessages;

@GenerateDto(
	dtoPackage="net.datenwerke.rs.base.client.reportengines.table.dto"
)
public enum CurrencyType {
	
	@EnumLabel(msg=EnumMessages.class,key="currencyEuro")
	EURO,
	
	@EnumLabel(msg=EnumMessages.class,key="currencyDollar")
	DOLLAR,
	
	@EnumLabel(msg=EnumMessages.class,key="currencyPound")
	BRITTISH_POUND,
	
	@EnumLabel(msg=EnumMessages.class,key="AED")
	AED,

	@EnumLabel(msg=EnumMessages.class,key="AFN")
	AFN,

	@EnumLabel(msg=EnumMessages.class,key="ALL")
	ALL,

	@EnumLabel(msg=EnumMessages.class,key="AMD")
	AMD,

	@EnumLabel(msg=EnumMessages.class,key="ANG")
	ANG,

	@EnumLabel(msg=EnumMessages.class,key="AOA")
	AOA,

	@EnumLabel(msg=EnumMessages.class,key="ARS")
	ARS,

	@EnumLabel(msg=EnumMessages.class,key="AUD")
	AUD,

	@EnumLabel(msg=EnumMessages.class,key="AWG")
	AWG,

	@EnumLabel(msg=EnumMessages.class,key="AZN")
	AZN,

	@EnumLabel(msg=EnumMessages.class,key="BAM")
	BAM,

	@EnumLabel(msg=EnumMessages.class,key="BBD")
	BBD,

	@EnumLabel(msg=EnumMessages.class,key="BDT")
	BDT,

	@EnumLabel(msg=EnumMessages.class,key="BGN")
	BGN,

	@EnumLabel(msg=EnumMessages.class,key="BHD")
	BHD,

	@EnumLabel(msg=EnumMessages.class,key="BIF")
	BIF,

	@EnumLabel(msg=EnumMessages.class,key="BMD")
	BMD,

	@EnumLabel(msg=EnumMessages.class,key="BND")
	BND,

	@EnumLabel(msg=EnumMessages.class,key="BOB")
	BOB,

	@EnumLabel(msg=EnumMessages.class,key="BRL")
	BRL,

	@EnumLabel(msg=EnumMessages.class,key="BSD")
	BSD,

	@EnumLabel(msg=EnumMessages.class,key="BTN")
	BTN,

	@EnumLabel(msg=EnumMessages.class,key="BWP")
	BWP,

	@EnumLabel(msg=EnumMessages.class,key="BYR")
	BYR,

	@EnumLabel(msg=EnumMessages.class,key="BZD")
	BZD,

	@EnumLabel(msg=EnumMessages.class,key="CAD")
	CAD,

	@EnumLabel(msg=EnumMessages.class,key="CDF")
	CDF,

	@EnumLabel(msg=EnumMessages.class,key="CHF")
	CHF,

	@EnumLabel(msg=EnumMessages.class,key="CLP")
	CLP,

	@EnumLabel(msg=EnumMessages.class,key="CNY")
	CNY,

	@EnumLabel(msg=EnumMessages.class,key="COP")
	COP,

	@EnumLabel(msg=EnumMessages.class,key="CRC")
	CRC,

	@EnumLabel(msg=EnumMessages.class,key="CUC")
	CUC,

	@EnumLabel(msg=EnumMessages.class,key="CUP")
	CUP,

	@EnumLabel(msg=EnumMessages.class,key="CVE")
	CVE,

	@EnumLabel(msg=EnumMessages.class,key="CZK")
	CZK,

	@EnumLabel(msg=EnumMessages.class,key="DJF")
	DJF,

	@EnumLabel(msg=EnumMessages.class,key="DKK")
	DKK,

	@EnumLabel(msg=EnumMessages.class,key="DOP")
	DOP,

	@EnumLabel(msg=EnumMessages.class,key="DZD")
	DZD,

	@EnumLabel(msg=EnumMessages.class,key="EGP")
	EGP,

	@EnumLabel(msg=EnumMessages.class,key="ERN")
	ERN,

	@EnumLabel(msg=EnumMessages.class,key="ETB")
	ETB,

	@EnumLabel(msg=EnumMessages.class,key="EUR")
	EUR,

	@EnumLabel(msg=EnumMessages.class,key="FJD")
	FJD,

	@EnumLabel(msg=EnumMessages.class,key="FKP")
	FKP,

	@EnumLabel(msg=EnumMessages.class,key="GBP")
	GBP,

	@EnumLabel(msg=EnumMessages.class,key="GEL")
	GEL,

	@EnumLabel(msg=EnumMessages.class,key="GGP")
	GGP,

	@EnumLabel(msg=EnumMessages.class,key="GHS")
	GHS,

	@EnumLabel(msg=EnumMessages.class,key="GIP")
	GIP,

	@EnumLabel(msg=EnumMessages.class,key="GMD")
	GMD,

	@EnumLabel(msg=EnumMessages.class,key="GNF")
	GNF,

	@EnumLabel(msg=EnumMessages.class,key="GTQ")
	GTQ,

	@EnumLabel(msg=EnumMessages.class,key="GYD")
	GYD,

	@EnumLabel(msg=EnumMessages.class,key="HKD")
	HKD,

	@EnumLabel(msg=EnumMessages.class,key="HNL")
	HNL,

	@EnumLabel(msg=EnumMessages.class,key="HRK")
	HRK,

	@EnumLabel(msg=EnumMessages.class,key="HTG")
	HTG,

	@EnumLabel(msg=EnumMessages.class,key="HUF")
	HUF,

	@EnumLabel(msg=EnumMessages.class,key="IDR")
	IDR,

	@EnumLabel(msg=EnumMessages.class,key="ILS")
	ILS,

	@EnumLabel(msg=EnumMessages.class,key="IMP")
	IMP,

	@EnumLabel(msg=EnumMessages.class,key="INR")
	INR,

	@EnumLabel(msg=EnumMessages.class,key="IQD")
	IQD,

	@EnumLabel(msg=EnumMessages.class,key="IRR")
	IRR,

	@EnumLabel(msg=EnumMessages.class,key="ISK")
	ISK,

	@EnumLabel(msg=EnumMessages.class,key="JEP")
	JEP,

	@EnumLabel(msg=EnumMessages.class,key="JMD")
	JMD,

	@EnumLabel(msg=EnumMessages.class,key="JOD")
	JOD,

	@EnumLabel(msg=EnumMessages.class,key="JPY")
	JPY,

	@EnumLabel(msg=EnumMessages.class,key="KES")
	KES,

	@EnumLabel(msg=EnumMessages.class,key="KGS")
	KGS,

	@EnumLabel(msg=EnumMessages.class,key="KHR")
	KHR,

	@EnumLabel(msg=EnumMessages.class,key="KMF")
	KMF,

	@EnumLabel(msg=EnumMessages.class,key="KPW")
	KPW,

	@EnumLabel(msg=EnumMessages.class,key="KRW")
	KRW,

	@EnumLabel(msg=EnumMessages.class,key="KWD")
	KWD,

	@EnumLabel(msg=EnumMessages.class,key="KYD")
	KYD,

	@EnumLabel(msg=EnumMessages.class,key="KZT")
	KZT,

	@EnumLabel(msg=EnumMessages.class,key="LAK")
	LAK,

	@EnumLabel(msg=EnumMessages.class,key="LBP")
	LBP,

	@EnumLabel(msg=EnumMessages.class,key="LKR")
	LKR,

	@EnumLabel(msg=EnumMessages.class,key="LRD")
	LRD,

	@EnumLabel(msg=EnumMessages.class,key="LSL")
	LSL,

	@EnumLabel(msg=EnumMessages.class,key="LTL")
	LTL,

	@EnumLabel(msg=EnumMessages.class,key="LYD")
	LYD,

	@EnumLabel(msg=EnumMessages.class,key="MAD")
	MAD,

	@EnumLabel(msg=EnumMessages.class,key="MDL")
	MDL,

	@EnumLabel(msg=EnumMessages.class,key="MGA")
	MGA,

	@EnumLabel(msg=EnumMessages.class,key="MKD")
	MKD,

	@EnumLabel(msg=EnumMessages.class,key="MMK")
	MMK,

	@EnumLabel(msg=EnumMessages.class,key="MNT")
	MNT,

	@EnumLabel(msg=EnumMessages.class,key="MOP")
	MOP,

	@EnumLabel(msg=EnumMessages.class,key="MRO")
	MRO,

	@EnumLabel(msg=EnumMessages.class,key="MUR")
	MUR,

	@EnumLabel(msg=EnumMessages.class,key="MVR")
	MVR,

	@EnumLabel(msg=EnumMessages.class,key="MWK")
	MWK,

	@EnumLabel(msg=EnumMessages.class,key="MXN")
	MXN,

	@EnumLabel(msg=EnumMessages.class,key="MYR")
	MYR,

	@EnumLabel(msg=EnumMessages.class,key="MZN")
	MZN,

	@EnumLabel(msg=EnumMessages.class,key="NAD")
	NAD,

	@EnumLabel(msg=EnumMessages.class,key="NGN")
	NGN,

	@EnumLabel(msg=EnumMessages.class,key="NIO")
	NIO,

	@EnumLabel(msg=EnumMessages.class,key="NOK")
	NOK,

	@EnumLabel(msg=EnumMessages.class,key="NPR")
	NPR,

	@EnumLabel(msg=EnumMessages.class,key="NZD")
	NZD,

	@EnumLabel(msg=EnumMessages.class,key="OMR")
	OMR,

	@EnumLabel(msg=EnumMessages.class,key="PAB")
	PAB,

	@EnumLabel(msg=EnumMessages.class,key="PEN")
	PEN,

	@EnumLabel(msg=EnumMessages.class,key="PGK")
	PGK,

	@EnumLabel(msg=EnumMessages.class,key="PHP")
	PHP,

	@EnumLabel(msg=EnumMessages.class,key="PKR")
	PKR,

	@EnumLabel(msg=EnumMessages.class,key="PLN")
	PLN,

	@EnumLabel(msg=EnumMessages.class,key="PYG")
	PYG,

	@EnumLabel(msg=EnumMessages.class,key="QAR")
	QAR,

	@EnumLabel(msg=EnumMessages.class,key="RON")
	RON,

	@EnumLabel(msg=EnumMessages.class,key="RSD")
	RSD,

	@EnumLabel(msg=EnumMessages.class,key="RUB")
	RUB,

	@EnumLabel(msg=EnumMessages.class,key="RWF")
	RWF,

	@EnumLabel(msg=EnumMessages.class,key="SAR")
	SAR,

	@EnumLabel(msg=EnumMessages.class,key="SBD")
	SBD,

	@EnumLabel(msg=EnumMessages.class,key="SCR")
	SCR,

	@EnumLabel(msg=EnumMessages.class,key="SDG")
	SDG,

	@EnumLabel(msg=EnumMessages.class,key="SEK")
	SEK,

	@EnumLabel(msg=EnumMessages.class,key="SGD")
	SGD,

	@EnumLabel(msg=EnumMessages.class,key="SHP")
	SHP,

	@EnumLabel(msg=EnumMessages.class,key="SLL")
	SLL,

	@EnumLabel(msg=EnumMessages.class,key="SOS")
	SOS,

	@EnumLabel(msg=EnumMessages.class,key="SPL")
	SPL,

	@EnumLabel(msg=EnumMessages.class,key="SRD")
	SRD,

	@EnumLabel(msg=EnumMessages.class,key="STD")
	STD,

	@EnumLabel(msg=EnumMessages.class,key="SVC")
	SVC,

	@EnumLabel(msg=EnumMessages.class,key="SYP")
	SYP,

	@EnumLabel(msg=EnumMessages.class,key="SZL")
	SZL,

	@EnumLabel(msg=EnumMessages.class,key="THB")
	THB,

	@EnumLabel(msg=EnumMessages.class,key="TJS")
	TJS,

	@EnumLabel(msg=EnumMessages.class,key="TMT")
	TMT,

	@EnumLabel(msg=EnumMessages.class,key="TND")
	TND,

	@EnumLabel(msg=EnumMessages.class,key="TOP")
	TOP,

	@EnumLabel(msg=EnumMessages.class,key="TRY")
	TRY,

	@EnumLabel(msg=EnumMessages.class,key="TTD")
	TTD,

	@EnumLabel(msg=EnumMessages.class,key="TVD")
	TVD,

	@EnumLabel(msg=EnumMessages.class,key="TWD")
	TWD,

	@EnumLabel(msg=EnumMessages.class,key="TZS")
	TZS,

	@EnumLabel(msg=EnumMessages.class,key="UAH")
	UAH,

	@EnumLabel(msg=EnumMessages.class,key="UGX")
	UGX,

	@EnumLabel(msg=EnumMessages.class,key="USD")
	USD,

	@EnumLabel(msg=EnumMessages.class,key="UYU")
	UYU,

	@EnumLabel(msg=EnumMessages.class,key="UZS")
	UZS,

	@EnumLabel(msg=EnumMessages.class,key="VEF")
	VEF,

	@EnumLabel(msg=EnumMessages.class,key="VND")
	VND,

	@EnumLabel(msg=EnumMessages.class,key="VUV")
	VUV,

	@EnumLabel(msg=EnumMessages.class,key="WST")
	WST,

	@EnumLabel(msg=EnumMessages.class,key="XAF")
	XAF,

	@EnumLabel(msg=EnumMessages.class,key="XCD")
	XCD,

	@EnumLabel(msg=EnumMessages.class,key="XDR")
	XDR,

	@EnumLabel(msg=EnumMessages.class,key="XOF")
	XOF,

	@EnumLabel(msg=EnumMessages.class,key="XPF")
	XPF,

	@EnumLabel(msg=EnumMessages.class,key="YER")
	YER,

	@EnumLabel(msg=EnumMessages.class,key="ZAR")
	ZAR,

	@EnumLabel(msg=EnumMessages.class,key="ZMW")
	ZMW,

	@EnumLabel(msg=EnumMessages.class,key="ZWD")
	ZWD;
	
	
	public Currency getCurrency(){
		switch (this) {
		case EURO:	
			return Currency.getInstance(Locale.GERMANY);
		case DOLLAR:
			return Currency.getInstance(Locale.US);
		case BRITTISH_POUND:
			return Currency.getInstance(Locale.UK);
		default:
			try{
				String name = name();
				return Currency.getInstance(name);
			} catch(Exception e){
				return Currency.getInstance(Locale.US); 
			}
		}
	}

}
