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
 
 
package net.datenwerke.security.service.security.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME) 
@Target(ElementType.TYPE)
public @interface ReturnObjectValidation {

	public enum Mode{
		THROW_EXCEPTION,
		FILTER
	}
	
	/**
	 * Specifys whether the parameter is a dto.
	 * @return
	 */
	public boolean isDto() default false;
	
	/**
	 * Defines whether the return value should be filtered or whether an exception should be thrown on violation. 
	 * 
	 * @return
	 */
	public Mode mode() default Mode.THROW_EXCEPTION;
	
	/**
	 * The list of rights to be verifyed.
	 * @return
	 */
	public RightsVerification[] verify() default {};
	
	public RightsVerification[] parentChecks() default {};
}
