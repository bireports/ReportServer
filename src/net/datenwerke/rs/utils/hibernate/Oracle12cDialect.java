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
 
 
package net.datenwerke.rs.utils.hibernate;

import java.sql.Types;

import org.hibernate.id.enhanced.SequenceStyleGenerator;
import org.hibernate.type.descriptor.sql.ClobTypeDescriptor;
import org.hibernate.type.descriptor.sql.SqlTypeDescriptor;

public class Oracle12cDialect extends org.hibernate.dialect.Oracle12cDialect {

	public Oracle12cDialect() {
		super();
		registerColumnType( Types.LONGVARCHAR, "clob" );
	}
	
	@Override
	public boolean supportsSequences() {
		return false;
	}
	
	/* Hibernate 4 uses HiLo per default. To ensure that we get simulated sequences
	 * we now do the following.
	 */
	@Override
	public Class getNativeIdentifierGeneratorClass() {
		return SequenceStyleGenerator.class;
	}
	
	@Override
	public SqlTypeDescriptor remapSqlTypeDescriptor(SqlTypeDescriptor sqlTypeDescriptor) {
		if (sqlTypeDescriptor instanceof RsClobTypeDummyDescriptor) {
			return ClobTypeDescriptor.DEFAULT;
		}
		return super.remapSqlTypeDescriptor(sqlTypeDescriptor);
	}

}
