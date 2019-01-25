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
 
 
package net.datenwerke.rs.saiku.server.rest.resources;

import javax.ws.rs.Path;

@Path("/saiku/{username}/datasources")
public class DataSourceResource {

//    DatasourceService datasourceService;
//    
//    private static final Logger log = LoggerFactory.getLogger(DataSourceResource.class);
//    
//    public void setDatasourceService(DatasourceService ds) {
//    	datasourceService = ds;
//    }
//    
//    /**
//     * Get Data Sources.
//     * @return A Collection of SaikuDatasource's.
//     */
//    @GET
//    @Produces({"application/json" })
//     public Collection<SaikuDatasource> getDatasources() {
//    	try {
//			return datasourceService.getDatasources().values();
//		} catch (SaikuServiceException e) {
//			log.error(this.getClass().getName(),e);
//			return new ArrayList<SaikuDatasource>();
//		}
//    }
//    
//    /**
//     * Delete Data Source.
//     * @param datasourceName - The name of the data source.
//     * @return A GONE Status.
//     */
//    @DELETE
//	@Path("/{datasource}")
//	public Status deleteDatasource(@PathParam("datasource") String datasourceName){
//    	datasourceService.removeDatasource(datasourceName);
//		return(Status.GONE);
//    }
//    
//    /**
//     * Get Data Source.
//     * @param datasourceName.
//     * @return A Saiku Datasource.
//     */
//    @GET
//    @Produces({"application/json" })
//	@Path("/{datasource}")
//	public SaikuDatasource getDatasource(@PathParam("datasource") String datasourceName){
//    	return datasourceService.getDatasource(datasourceName);
//    }
//
//    @POST
//    @Consumes({"application/json" })
//	@Path("/{datasource}")
//	public Status addDatasource(@PathParam("datasource") String datasourceName , @Context SaikuDatasource ds){
//    	System.out.println("ds not null:" + (ds != null));
//    	System.out.println("ds name:"+ds.getName());
//    	datasourceService.addDatasource(ds);
//    	return Status.OK;
//    }

}
