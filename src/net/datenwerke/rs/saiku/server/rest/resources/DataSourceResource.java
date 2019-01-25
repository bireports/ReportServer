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

import javax.ws.rs.Path;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Data Source Manipulation Utility Endpoints
 */
@Component
@Path("/saiku/{username}/org.saiku.datasources")
public class DataSourceResource {

    private static final Logger log = LoggerFactory.getLogger(DataSourceResource.class);
//    private DatasourceService datasourceService;
//
//    public void setDatasourceService(DatasourceService ds) {
//        datasourceService = ds;
//    }
//
//    /**
//     * Get Data Sources available on the server.
//     *
//     * @return A Collection of SaikuDatasource's.
//     * @summary Get Data Sources
//     */
//    @GET
//    @Produces({"application/json"})
//    public Collection<SaikuDatasource> getDatasources() {
//        //TODO: admin security?
//        try {
//            return datasourceService.getDatasources().values();
//        } catch (SaikuServiceException e) {
//            log.error(this.getClass().getName(), e);
//            return new ArrayList<>();
//        }
//    }
//
//    /**
//     * Delete available data source from the server.
//     *
//     * @param datasourceName - The name of the data source.
//     * @return A GONE Status.
//     * @summary Delete data source
//     */
//    @DELETE
//    @Path("/{datasource}")
//    public Status deleteDatasource(@PathParam("datasource") String datasourceName) {
//        datasourceService.removeDatasource(datasourceName);
//        return (Status.GONE);
//    }
//
//    /**
//     * Get a specific data source from the server by ID.
//     *
//     * @param id The data source id.
//     * @return A Saiku Datasource.
//     * @summary Get Data Source.
//     */
//    @GET
//    @Produces({"application/json"})
//    @Path("/{id}")
//    @ReturnType("org.saiku.web.rest.objects.DataSourceMapper")
//    public Response getDatasourceById(@PathParam("id") String id) {
//        try {
//            SaikuDatasource saikuDatasource = null;
//            Map<String, SaikuDatasource> datasources = datasourceService.getDatasources();
//            for (SaikuDatasource currentDatasource : datasources.values()) {
//                if (currentDatasource.getProperties().getProperty("id").equals(id)) {
//                    saikuDatasource = currentDatasource;
//                    break;
//                }
//            }
//            return Response.ok().type("application/json").entity(new DataSourceMapper(saikuDatasource)).build();
//        } catch (Exception e) {
//            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getLocalizedMessage())
//                .type("text/plain").build();
//        }
//    }
//
//    @PUT
//    @Produces({"application/json"})
//    @Consumes({"application/json"})
//    @Path("/{id}")
//    @ReturnType("org.saiku.web.rest.objects.DataSourceMapper")
//    public Response updateDatasourceLocale(String locale, @PathParam("id") String id) {
//        boolean overwrite = true;
//        try {
//            SaikuDatasource saikuDatasource = null;
//            Map<String, SaikuDatasource> datasources = datasourceService.getDatasources();
//            for (SaikuDatasource currentDatasource : datasources.values()) {
//                if (currentDatasource.getProperties().getProperty("id").equals(id)) {
//                    saikuDatasource = currentDatasource;
//                    changeLocale(saikuDatasource, locale);
//                    datasourceService.addDatasource(saikuDatasource, overwrite);
//                    break;
//                }
//            }
//            return Response.ok().type("application/json").entity(saikuDatasource).build();
//        } catch (Exception e) {
//            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getLocalizedMessage())
//                .type("text/plain").build();
//        }
//    }
//
//    private void changeLocale(SaikuDatasource saikuDatasource, String newLocale) {
//        String location = saikuDatasource.getProperties().getProperty("location");
//        String oldLocale = getOldLocale(location);
//        String newLocation = location.replace(oldLocale, newLocale);
//        saikuDatasource.getProperties().setProperty("location", newLocation);
//    }
//
//    private String getOldLocale(String location) {
//        String referenceText = "locale=";
//        int start = location.toLowerCase().indexOf(referenceText);
//        if (start == -1) {
//            // warn user
//            return "no locale!";
//        } else {
//            start += referenceText.length();
//            int end = location.indexOf(";", start);
//            return location.substring(start, end);
//        }
//    }

}
