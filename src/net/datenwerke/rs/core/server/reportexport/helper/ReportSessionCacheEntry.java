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
 
 
package net.datenwerke.rs.core.server.reportexport.helper;

import java.awt.image.BufferedImage;
import java.io.Serializable;

import net.datenwerke.rs.core.service.reportmanager.engine.config.ReportExecutionConfig;
import net.datenwerke.rs.core.service.reportmanager.entities.reports.Report;

public class ReportSessionCacheEntry implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private long id;
	private String outputFormat;
	private Report adjustedReport;
	private ReportExecutionConfig[] executorConfigs;
	private Throwable error;
	private BufferedImage image;
	private boolean cached;
	
	public ReportSessionCacheEntry() {
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getOutputFormat() {
		return outputFormat;
	}

	public void setOutputFormat(String outputFormat) {
		this.outputFormat = outputFormat;
	}

	public Report getAdjustedReport() {
		return adjustedReport;
	}

	public void setAdjustedReport(Report adjustedReport) {
		this.adjustedReport = adjustedReport;
	}

	public ReportExecutionConfig[] getExecutorConfigs() {
		return executorConfigs;
	}
	
	public void setExecutorConfigs(ReportExecutionConfig[] executorConfigs) {
		this.executorConfigs = executorConfigs;
	}

	public void setError(Throwable error) {
		this.error = error;
	}
	
	public Throwable getError() {
		return error;
	}
	
	public BufferedImage getImage() {
		return image;
	}
	
	public void setImage(BufferedImage image) {
		this.image = image;
	}
	
	public boolean isCached() {
		return cached;
	}
	
	public void setCached(boolean cached) {
		this.cached = cached;
	}
}
