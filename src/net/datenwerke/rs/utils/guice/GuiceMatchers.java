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
 
 
package net.datenwerke.rs.utils.guice;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import com.google.inject.matcher.AbstractMatcher;
import com.google.inject.matcher.Matcher;

public class GuiceMatchers {

	/**
	 * Matches only public methods
	 * @return
	 */
	public static Matcher<Object> publicMethod() {
        return PUBLIC;
    }

    private static final Matcher<Object> PUBLIC = new Public();
	
	private static class Public extends AbstractMatcher<Object> implements Serializable {
		public boolean matches(Object o) {
		    if(o instanceof Method){
		    	return Modifier.isPublic(((Method)o).getModifiers()); 
		    }
			return false;
		}
		
		@Override
		public String toString() {
		    return "publicMethod()";
		}
		
		public Object readResolve() {
		    return publicMethod();
		}
		
		private static final long serialVersionUID = 0;
	}
	
}
