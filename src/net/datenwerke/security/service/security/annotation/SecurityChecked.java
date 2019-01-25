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
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import net.datenwerke.rs.utils.interfaces.NullIndicatorInterface;

/**
 * 
 *
 */
@Retention(RetentionPolicy.RUNTIME) 
@Target({ElementType.METHOD,ElementType.TYPE})
@Inherited
public @interface SecurityChecked {

	public boolean superUserRequired() default false;
	
	public Class<?> location() default NullIndicatorInterface.class; 
	
	/**
	 * Sets whether a user has to be logged in to call the method
	 * @return
	 */
	public boolean loginRequired() default true;
	
	/**
	 * Defines whether the caller has to have authenticated himself with a certificate.
	 * @return
	 */
	public boolean certificateRequired() default true;
	
	/**
	 * An array of methods that are not affected by security checks (when used as class level annotation)
	 * @return
	 */
	public BypassMethod[] bypassMethods() default {};
	
	/**
	 * Defines whether the security checks should be disabled.
	 * @return
	 */
	public boolean bypass() default false;

	/**
	 * Defines whether inherited methods should not be checked.
	 * @return
	 */
	public boolean bypassInheritedMethods() default false;

	/**
	 * If this flag is set the interceptor returns true or false depending on the security check.
	 * @return
	 */
	public boolean returnBooleanOnCheck() default false;
	
	/**
	 * Tells the interceptor to return an instance of the methods return type on failure.
	 * 
	 * <p>
	 * Common interfaces such as Collection, List, Set, Map, will be automatically mapped.
	 * If you want to manually set the type to be instantiated use 
	 * 
	 * @return
	 */
	public boolean returnObjectInstanceOnFailure() default false;
	
	public boolean returnFalseOnFailure() default false;
	
	public boolean returnNullOnFailure() default false;
	
	/**
	 * 
	 * @see returnObjectInstanceOnFailure
	 * @return
	 */
	public Class<?> returnTypeOnFailue() default NullIndicatorInterface.class;
	
	/**
	 * Additional generic target verifications
	 * @return
	 */
	public GenericTargetVerification[] genericTargetVerification() default {};
	
	/**
	 * Request verification for parameters.
	 * @return
	 */
	public ArgumentVerification[] argumentVerification() default {};
	
	/**
	 * Requests verification for return 
	 * @return
	 */
	public ReturnObjectValidation[] returnObjectValidation() default {};

	
}
