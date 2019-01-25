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
 
 
package net.datenwerke.rs.core.service.i18ntools;

import java.io.PrintWriter;
import java.util.ArrayList;

import net.datenwerke.gxtdto.client.i18n.remotemessages.DwRemoteMessage;

import org.apache.commons.lang.StringUtils;

import com.google.gwt.core.ext.Generator;
import com.google.gwt.core.ext.GeneratorContext;
import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.core.ext.UnableToCompleteException;
import com.google.gwt.core.ext.typeinfo.JClassType;
import com.google.gwt.core.ext.typeinfo.JMethod;
import com.google.gwt.core.ext.typeinfo.JParameter;
import com.google.gwt.core.ext.typeinfo.NotFoundException;
import com.google.gwt.dev.javac.GeneratedUnit;
import com.google.gwt.dev.javac.StandardGeneratorContext;
import com.google.gwt.i18n.rebind.LocalizableGenerator;
import com.google.gwt.user.rebind.ClassSourceFileComposerFactory;
import com.google.gwt.user.rebind.SourceWriter;

public class DwMessageClassGenerator extends Generator {

	private static final String GENERATED_CLASS_SUFFIX = "RemoteImpl";
	private static final Class SUPERCLASS = DwRemoteMessage.class;

	public DwMessageClassGenerator() {

	}

	@Override
	public String generate(TreeLogger logger, GeneratorContext context, String typeName) throws UnableToCompleteException {
		try {
			JClassType classType = context.getTypeOracle().getType(typeName);
			SourceWriter src = getSourceWriter(classType, context, logger);

			if(null != src){
				for(JMethod m : classType.getMethods()){ 
					if(m.isAbstract()){
						ArrayList<String> paramStr = new ArrayList<>();
						for(JParameter p : m.getParameters()){
							paramStr.add(p.getType().getQualifiedSourceName() + " " + p.getName());
						}
						src.println("public %s %s (%s) {", m.getReturnType().getQualifiedSourceName(), m.getName(), StringUtils.join(paramStr, ", "));
						ArrayList<String> argsstr = new ArrayList<>();
						for(JParameter p : m.getParameters()){
							argsstr.add(p.getName());
						}

						src.indentln("return getMessage(\"%s\", \"%s\", %s);", typeName, m.getName(), (argsstr.size() == 0)?"null":StringUtils.join(argsstr, ", "));
						src.println("}");
						src.println();
					}
				}

				src.commit(logger);
			}
			
			String generated = typeName + GENERATED_CLASS_SUFFIX;
			return generated;
		} catch (NotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

	public SourceWriter getSourceWriter(JClassType classType, GeneratorContext context, TreeLogger logger) {
		String packageName = classType.getPackage().getName();
		String simpleName = classType.getSimpleSourceName() + GENERATED_CLASS_SUFFIX;
		ClassSourceFileComposerFactory composer = new ClassSourceFileComposerFactory(packageName, simpleName);
		composer.setSuperclass(SUPERCLASS.getName());		
		composer.addImplementedInterface(classType.getName());
		
		PrintWriter printWriter = context.tryCreate(logger, packageName, simpleName);
		if (printWriter == null) {
			return null;
		} else {
			SourceWriter sw = composer.createSourceWriter(context, printWriter);
			return sw;
		}
	}

	private String getGeneratedSource(GeneratorContext context, Object generated){
		GeneratedUnit unit = ((StandardGeneratorContext)context).getGeneratedUnitMap().get(generated);
		String source = unit.getSource();
		
		return source;
	}
	
	private String deferToDefaultGenerator(TreeLogger logger, GeneratorContext context, String typeName) throws UnableToCompleteException{
		LocalizableGenerator gwtDefaultGenerator = new LocalizableGenerator();
		return gwtDefaultGenerator.generate(logger, context, typeName);
	}



}
