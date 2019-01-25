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
 
 
/*
 *   Copyright 2012 OSBI Ltd
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 */
package net.datenwerke.rs.saiku.server.rest.resources;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

import org.saiku.service.PlatformUtilsService;
import org.saiku.service.util.dto.Plugin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.google.inject.Inject;
import com.qmino.miredot.annotations.ReturnType;

/**
 * Info Resource to get platform information.
 */
@Component
@Path("/saiku/info")
@XmlAccessorType( XmlAccessType.NONE)
public class InfoResource {

  private static final Logger log = LoggerFactory.getLogger( InfoResource.class );

  private PlatformUtilsService platformService;

  //@Autowired
//  public void setPlatformUtilsService(Object ps) {
////    this.platformService = ps;
//  }
  
  @Inject
  public InfoResource(PlatformUtilsService platformService){
	  this.platformService = platformService;
  }

  /**
   * Get a list of available plugins.
   * @summary Get plugins
   * @return A response containing a list of plugins.
   */
  @GET
  @Produces({"application/json" })
  @ReturnType("java.util.List<Plugin>")
  public Response getAvailablePlugins() {

    GenericEntity<List<Plugin>> entity =
         new GenericEntity<List<Plugin>>(platformService.getAvailablePlugins()){};
     return Response.ok(entity).build();
  }

}
