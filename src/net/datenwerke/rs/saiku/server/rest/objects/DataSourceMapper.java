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
 * Copyright 2014 OSBI Ltd
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.datenwerke.rs.saiku.server.rest.objects;

import org.saiku.datasources.datasource.SaikuDatasource;

import java.util.Properties;
import java.util.UUID;

/**
 * Map from SaikuDatasources to JSON variants.
 */
public class DataSourceMapper {

    private String connectionname;
    private String jdbcurl;
    private String schema;
    private String driver;
    private String username;
    private String password;
    private String connectiontype;
    private String id;
    private String path;
    private String advanced;
    private String security_type;
    private String propertyKey;

    public DataSourceMapper() {

    }

    public DataSourceMapper(SaikuDatasource ds) {
        if (!ds.getProperties().containsKey("advanced") || ds.getProperties().getProperty("advanced").equals("false")) {
            String location = ds.getProperties().getProperty("location");

            String[] loc = location.split(";");

            String[] url = loc[0].split("=");
            if (ds.getProperties().getProperty("driver").equals("mondrian.olap4j.MondrianOlap4jDriver")) {
                String[] cat = loc[1].split("=");
                String[] drv = loc[2].split("=");
                if(cat.length>1) {
                    this.schema = cat[1];
                }
                if(drv.length>1) {
                    this.driver = drv[1];
                }
                this.connectiontype = "MONDRIAN";
            } else {
                this.connectiontype = "XMLA";
            }
            this.connectionname = ds.getName();
            if(url.length>1) {
                this.jdbcurl = url[1];
            }
            this.username = ds.getProperties().getProperty("username");
            this.password = ds.getProperties().getProperty("password");
            this.path = ds.getProperties().getProperty("path");
            this.id = ds.getProperties().getProperty("id");
            if(ds.getProperties().containsKey("schema")){
                this.schema = ds.getProperties().getProperty("schema");
            }

            if(ds.getProperties().containsKey("security.type")){
                this.security_type = ds.getProperties().getProperty("security.type");
            }

            if(ds.getProperties().containsKey("propertykey")){
                this.propertyKey = ds.getProperties().getProperty("propertykey");
            }


        } else {
            this.advanced = "type=" + ds.getType().toString() + "\n";
            this.advanced += "name=" + ds.getName() + "\n";
            this.advanced += "driver=" + ds.getProperties().getProperty("driver") + "\n";
            this.advanced += "location=" + ds.getProperties().getProperty("location") + "\n";
            if (ds.getProperties().containsKey("username")) {
                this.advanced += "username=" + ds.getProperties().get("username") + "\n";
            }
            if (ds.getProperties().containsKey("password")) {
                this.advanced += "password=" + ds.getProperties().get("password") + "\n";
            }
            if (ds.getProperties().containsKey("security.enabled")) {
                this.advanced += "security.enabled=" + ds.getProperties().get("security.enabled") + "\n";
            }
            if (ds.getProperties().containsKey("security.type")) {
                this.advanced += "security.type=" + ds.getProperties().get("security.type") + "\n";
            }
            if (ds.getProperties().containsKey("security.mapping")) {
                this.advanced += "security.mapping=" + ds.getProperties().get("security.mapping") + "\n";
            }
            if (ds.getProperties().contains("encrypt.password")) {
                this.advanced += "encrypt.password=" + ds.getProperties().get("encrypt.password") + "\n";
            }
            this.connectionname = ds.getName();
            this.id = ds.getProperties().getProperty("id");
        }
    }

    public SaikuDatasource toSaikuDataSource() {
        Properties props = new Properties();
        if (advanced == null) {
            String location;
            if (connectiontype.equals("MONDRIAN")) {
                props.setProperty("driver", "mondrian.olap4j.MondrianOlap4jDriver");
                location = "jdbc:mondrian:Jdbc=" + jdbcurl + ";Catalog=mondrian://" + schema + ";JdbcDrivers=" + driver;
            } else {
                props.setProperty("driver", "org.olap4j.driver.xmla.XmlaOlap4jDriver");
                location = "jdbc:xmla:Server=" + jdbcurl;
            }


            props.setProperty("location", location);
            props.setProperty("username", this.username);
            props.setProperty("password", this.password);
            if(this.security_type!=null){
                props.setProperty("security.type", this.security_type);
            }
            if(this.schema!=null){
                props.setProperty("schema", this.schema);
            }
            if (this.path != null) {
                props.setProperty("path", this.path);
            }
            if (this.id != null) {
                props.setProperty("id", this.id);
            } else {
                props.setProperty("id", UUID.randomUUID().toString());
            }
            props.setProperty("advanced", "false");

            if(this.propertyKey != null){
                props.setProperty("propertyKey", this.propertyKey);
            }

            return new SaikuDatasource(this.getConnectionname(), SaikuDatasource.Type.OLAP, props);
        } else {
            String name = null;

            String[] lines = advanced.split("\\r?\\n");

            for (String row : lines) {
                if (row.startsWith("name=")) {
                    name = row.substring(5, row.length());
                }
                if (row.startsWith("driver=")) {
                    props.setProperty("driver", row.substring(7, row.length()));
                }
                if (row.startsWith("location=")) {
                    props.setProperty("location", row.substring(9, row.length()));
                }
                if (row.startsWith("username=")) {
                    if (row.length() > 9) {
                        props.setProperty("username", row.substring(9, row.length()));
                    } else {
                        props.setProperty("username", "");
                    }
                }
                if (row.startsWith("password=")) {
                    if (row.length() > 9) {
                        props.setProperty("password", row.substring(9, row.length()));
                    } else {
                        props.setProperty("password", "");
                    }
                }

                if (row.startsWith("security.type=")) {
                    props.setProperty("security.type", row.substring(14, row.length()));
                }
                if (row.startsWith("security.mapping=")) {
                    props.setProperty("security.mapping", row.substring(17, row.length()));
                }
                if (row.startsWith("security.enabled=")) {
                    props.setProperty("security.enabled", row.substring(17, row.length()));
                }
                if (row.startsWith("encrypt.password=")) {
                    props.setProperty("encrypt.password", row.substring(17, row.length()));
                }
                if (this.id != null) {
                    props.setProperty("id", this.id);
                } else {
                    props.setProperty("id", UUID.randomUUID().toString());
                }
                if(row.startsWith("propertyKey=")){
                    props.setProperty("propertyKey", row.substring(12, row.length()));
                }
            }

            props.setProperty("advanced", "true");

            return new SaikuDatasource(name, SaikuDatasource.Type.OLAP, props);
        }


    }

    public String getConnectionname() {
        return connectionname;
    }

    public void setConnectionname(String connectionname) {
        this.connectionname = connectionname;
    }

    public String getJdbcurl() {
        return jdbcurl;
    }

    public void setJdbcurl(String jdbcurl) {
        this.jdbcurl = jdbcurl;
    }

    public String getSchema() {
        return schema;
    }

    public void setSchema(String schema) {
        this.schema = schema;
    }

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConnectiontype() {
        return connectiontype;
    }

    public void setConnectiontype(String connectiontype) {
        this.connectiontype = connectiontype;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getAdvanced() {
        return advanced;
    }

    public void setAdvanced(String advanced) {
        this.advanced = advanced;
    }

    public void setSecurity_type(String security_type) {
        this.security_type = security_type;
    }

    public void setPropertyKey(String propertyKey) {
        this.propertyKey = propertyKey;
    }

    public String getPropertyKey() {
        return propertyKey;
    }

    public String getSecurity_type() {
        return security_type;
    }
}
