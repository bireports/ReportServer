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
 
 
package net.datenwerke.rs.utils.misc;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.grapher.GrapherModule;
import com.google.inject.grapher.InjectorGrapher;
import com.google.inject.grapher.graphviz.GraphvizModule;
import com.google.inject.grapher.graphviz.GraphvizRenderer;

public class Grapher {
  public void graph(String filename, Injector demoInjector) throws IOException {
    PrintWriter out = new PrintWriter(new File(filename), "UTF-8"); //$NON-NLS-1$

    Injector injector = Guice.createInjector(new GrapherModule(), new GraphvizModule());
    GraphvizRenderer renderer = injector.getInstance(GraphvizRenderer.class);
    renderer.setOut(out).setRankdir("TB"); //$NON-NLS-1$

    injector.getInstance(InjectorGrapher.class)
        .of(demoInjector)
        .graph();
  }
}