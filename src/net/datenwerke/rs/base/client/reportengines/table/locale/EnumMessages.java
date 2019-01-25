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
 
 
package net.datenwerke.rs.base.client.reportengines.table.locale;

import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.Messages;

public interface EnumMessages extends Messages {

	public static final EnumMessages INSTANCE = GWT.create(EnumMessages.class);
	
	public String avg();
	public String count();
	public String max();
	public String min();
	public String sum();
	public String variance();
	public String countDistinct();

	public String asc();
	public String desc();
	
	public String nullInclude();
	public String nullExclude();
	
	public String numberDefault();
	public String numberPercent();
	public String numberScientific();
	
	public String currencyEuro();
	public String currencyDollar();
	public String currencyPound();
	
	public String AFN();

	public String ALL();

	public String DZD();

	public String AOA();

	public String ARS();

	public String AMD();

	public String AWG();

	public String AUD();

	public String AZN();

	public String BSD();

	public String BHD();

	public String BDT();

	public String BBD();

	public String BYR();

	public String BZD();

	public String BMD();

	public String BTN();

	public String BOB();

	public String BAM();

	public String BWP();

	public String BRL();

	public String BND();

	public String BGN();

	public String BIF();

	public String KHR();

	public String CAD();

	public String CVE();

	public String KYD();

	public String CLP();

	public String CNY();

	public String COP();

	public String XOF();

	public String XAF();

	public String KMF();

	public String XPF();

	public String CDF();

	public String CRC();

	public String HRK();

	public String CUC();

	public String CUP();

	public String CZK();

	public String DKK();

	public String DJF();

	public String DOP();

	public String XCD();

	public String EGP();

	public String SVC();

	public String ERN();

	public String ETB();

	public String EUR();

	public String FKP();

	public String FJD();

	public String GMD();

	public String GEL();

	public String GHS();

	public String GIP();

	public String GTQ();

	public String GGP();

	public String GNF();

	public String GYD();

	public String HTG();

	public String HNL();

	public String HKD();

	public String HUF();

	public String ISK();

	public String INR();

	public String IDR();

	public String XDR();

	public String IRR();

	public String IQD();

	public String IMP();

	public String ILS();

	public String JMD();

	public String JPY();

	public String JEP();

	public String JOD();

	public String KZT();

	public String KES();

	public String KPW();

	public String KRW();

	public String KWD();

	public String KGS();

	public String LAK();

	public String LBP();

	public String LSL();

	public String LRD();

	public String LYD();

	public String LTL();

	public String MOP();

	public String MKD();

	public String MGA();

	public String MWK();

	public String MYR();

	public String MVR();

	public String MRO();

	public String MUR();

	public String MXN();

	public String MDL();

	public String MNT();

	public String MAD();

	public String MZN();

	public String MMK();

	public String NAD();

	public String NPR();

	public String ANG();

	public String NZD();

	public String NIO();

	public String NGN();

	public String NOK();

	public String OMR();

	public String PKR();

	public String PAB();

	public String PGK();

	public String PYG();

	public String PEN();

	public String PHP();

	public String PLN();

	public String QAR();

	public String RON();

	public String RUB();

	public String RWF();

	public String SHP();

	public String WST();

	public String SAR();

	public String SPL();

	public String RSD();

	public String SCR();

	public String SLL();

	public String SGD();

	public String SBD();

	public String SOS();

	public String ZAR();

	public String LKR();

	public String SDG();

	public String SRD();

	public String SZL();

	public String SEK();

	public String CHF();

	public String SYP();

	public String STD();

	public String TWD();

	public String TJS();

	public String TZS();

	public String THB();

	public String TOP();

	public String TTD();

	public String TND();

	public String TRY();

	public String TMT();

	public String TVD();

	public String UGX();

	public String UAH();

	public String AED();

	public String GBP();

	public String USD();

	public String UYU();

	public String UZS();

	public String VUV();

	public String VEF();

	public String VND();

	public String YER();

	public String ZMW();

	public String ZWD();

}