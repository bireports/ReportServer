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
 
 
package net.datenwerke.rs.base.client.reportengines.table.helpers.format;

import net.datenwerke.rs.base.client.reportengines.table.columnfilter.locale.FilterMessages;

public enum FormatType {

	DEFAULT {
		@Override
		public String toString() {
			return FilterMessages.INSTANCE.formatTypeDefault();
		}
	},
	NUMBER{
		@Override
		public String toString() {
			return FilterMessages.INSTANCE.formatTypeNumber();
		}
	},
	CURRENCY{
		@Override
		public String toString() {
			return FilterMessages.INSTANCE.formatTypeCurrency();
		}
	},
	DATE{
		@Override
		public String toString() {
			return FilterMessages.INSTANCE.formatTypeDate();
		}
	},
	PERCENT{
		@Override
		public String toString() {
			return FilterMessages.INSTANCE.formatTypePercent();
		}
	},
	SCIENTIFIC{
		@Override
		public String toString() {
			return FilterMessages.INSTANCE.formatTypeScientific();
		}
	},
	TEXT{
		@Override
		public String toString() {
			return FilterMessages.INSTANCE.formatTypeText();
		}
	},
	TEMPLATE{
		@Override
		public String toString() {
			return FilterMessages.INSTANCE.formatTypeTemplate();
		}
	}
}
