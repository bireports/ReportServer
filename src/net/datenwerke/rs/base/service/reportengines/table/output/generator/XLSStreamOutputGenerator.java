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
 
 
package net.datenwerke.rs.base.service.reportengines.table.output.generator;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import net.datenwerke.rs.base.service.reportengines.locale.ReportEnginesMessages;
import net.datenwerke.rs.base.service.reportengines.table.entities.Column;
import net.datenwerke.rs.base.service.reportengines.table.entities.Column.CellFormatter;
import net.datenwerke.rs.base.service.reportengines.table.entities.Column.ColumnFormatCellFormatter;
import net.datenwerke.rs.base.service.reportengines.table.entities.TableReport;
import net.datenwerke.rs.base.service.reportengines.table.entities.format.ColumnFormat;
import net.datenwerke.rs.base.service.reportengines.table.entities.format.ColumnFormatCurrency;
import net.datenwerke.rs.base.service.reportengines.table.entities.format.ColumnFormatDate;
import net.datenwerke.rs.base.service.reportengines.table.entities.format.ColumnFormatNumber;
import net.datenwerke.rs.base.service.reportengines.table.entities.format.ColumnFormatTemplate;
import net.datenwerke.rs.base.service.reportengines.table.entities.format.ColumnFormatText;
import net.datenwerke.rs.base.service.reportengines.table.output.object.CompiledXLSXTableReport;
import net.datenwerke.rs.base.service.reportengines.table.output.object.TableDefinition;
import net.datenwerke.rs.core.service.reportmanager.ReportExecutorService;
import net.datenwerke.rs.core.service.reportmanager.engine.CompiledReport;
import net.datenwerke.rs.core.service.reportmanager.engine.config.ReportExecutionConfig;
import net.datenwerke.rs.core.service.reportmanager.entities.reports.Report;
import net.datenwerke.rs.core.service.reportmanager.exceptions.ExcelExportException;
import net.datenwerke.rs.core.service.reportmanager.parameters.ParameterSet;
import net.datenwerke.rs.utils.localization.LocalizationServiceImpl;
import net.datenwerke.rs.utils.oracle.StupidOracleService;
import net.datenwerke.security.service.usermanager.entities.User;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import com.google.inject.Inject;
 
public class XLSStreamOutputGenerator extends TableOutputGeneratorImpl{

	private final ReportEnginesMessages messages = LocalizationServiceImpl.getMessages(ReportEnginesMessages.class);
	
	private CellStyle timeFormat;
	private CellStyle fullDateFormat;
	
	private int row = 0;
	private int column = 0;
	
	private SXSSFWorkbook workbook;

	private Sheet sheet;

	private CellStyle[] styles;

	private int[] columnWidths;

	private Object[] nullReplacemenets;

	private Row dataRow;

	private boolean dontStream = false;

	private final XLSOutputGenerator basicXlsGenerator;

	private final StupidOracleService sos;

	
	@Inject
	public XLSStreamOutputGenerator(
		XLSOutputGenerator basicXlsGenerator,
		StupidOracleService sos
		) {
		super();
		this.basicXlsGenerator = basicXlsGenerator;
		this.sos = sos;
	}
	
	@Override
	public String[] getFormats() {
		return new String[]{ReportExecutorService.OUTPUT_FORMAT_EXCEL};
	}
	
	@Override
	public void initialize(OutputStream os, TableDefinition table, boolean withSubtotals, 
			TableReport report, TableReport originalReport,
			CellFormatter[] cellFormatters, ParameterSet parameters, User user, ReportExecutionConfig... configs)
			throws IOException {
		super.initialize(os, table, withSubtotals, report, originalReport, cellFormatters, parameters, user, configs);
		if(withSubtotals){
			basicXlsGenerator.initialize(os, table, withSubtotals, report, originalReport, cellFormatters, parameters, user, configs);
			return;
		}
		
		int columnCount = td.getColumns().size();
		
		/* adjust stream */
		if(null == this.os){
			this.os = new ByteArrayOutputStream();
			dontStream = true;
		}
		
		/* create workbook */
		workbook = new SXSSFWorkbook(1000); // keep 1000 rows in memory, exceeding rows will be flushed to disk
		workbook.setCompressTempFiles(true);
        sheet = workbook.createSheet();

        /* load columns */
        List<Column> columns;
        if(report.isSelectAllColumns()){
        	columns = new ArrayList<Column>();
        	for(Object[] c: td.getColumns()){
	        	Column col = new Column();
				col.setName((String)c[0]);
				col.setType(((Integer)c[3]));
				columns.add(col);
			}
        }else{
        	columns = report.getVisibleColumns();
        }
		nullReplacemenets = new Object[columnCount];
		for(int i = 0; i < columnCount; i++)
			prepareNullFormat(cellFormatters[i], columns.get(i), i);
		
		/* prepare array for widths for columns */
		columnWidths = new int[columnCount];
		
		Row headerRow = sheet.createRow(0);
		
		/* prepare formats */
		styles = new CellStyle[columnCount];
		CreationHelper createHelper = workbook.getCreationHelper();
		for(int i = 0; i < columns.size(); i++){
			Column column = columns.get(i);
			if(null != column && null != column.getFormat()){
				ColumnFormat format = column.getFormat();
				if(format instanceof ColumnFormatCurrency){
					CellStyle style = workbook.createCellStyle();
					String pattern = ((ColumnFormatNumber)format).getPattern().replace("\u00A4", "\"" + ((ColumnFormatCurrency)format).getCurrencyType().getCurrency().getSymbol() + "\"");
					style.setDataFormat(createHelper.createDataFormat().getFormat(pattern));
					styles[i] = style;
				} else if(format instanceof ColumnFormatNumber){
					CellStyle style = workbook.createCellStyle();
					style.setDataFormat(createHelper.createDataFormat().getFormat(((ColumnFormatNumber)format).getPattern()));
					styles[i] = style;
				}else if(format instanceof ColumnFormatDate){
					CellStyle style = workbook.createCellStyle();
					style.setDataFormat(createHelper.createDataFormat().getFormat(((ColumnFormatDate)format).getTargetFormat()));
					styles[i] = style;
				}else if(format instanceof ColumnFormatText){
					CellStyle style = workbook.createCellStyle();
					style.setWrapText(true);
					styles[i] = style;
				}
			}
		}
		
		/* add header */
		int i = 0;
		for(String name : table.getColumnNames()){
			/* add name to cell */
			Cell cell = headerRow.createCell(i);
			cell.setCellValue(name);
			
			/* column type */
			Class<?> cType = table.getColumnType(i);
			
			/* adjust column width */
			if(cType.equals(Date.class) || cType.equals(Timestamp.class))
				columnWidths[i] = Math.max(Math.max(messages.dateFormat().length(), name.length()), 15);
			else if(cType.equals(Timestamp.class))
				columnWidths[i] = Math.max(Math.max(messages.timeFormat().length(), name.length()), 15);
			else
				columnWidths[i] = Math.max(name.length(), 10);
			
			/* increment counter */
			i++;
		}
		
		/* create first row */
		dataRow = sheet.createRow(row+1);
	}

	@Override
	public void addField(Object field, CellFormatter cellFormatter)
			throws IOException {
		if(withSubtotals){
			basicXlsGenerator.addField(field, cellFormatter);
			return;
		}
		
		Cell cell = dataRow.createCell(column);
			
		/* content */
		Object content = getValueOf(field);
		
		if(cellFormatter instanceof ColumnFormatCellFormatter){
			ColumnFormat columnFormat = ((ColumnFormatCellFormatter) cellFormatter).getColumnFormat();
			
			if(columnFormat instanceof ColumnFormatTemplate)
				content = cellFormatter.format(content);
			
			if(columnFormat instanceof ColumnFormatDate && content instanceof String){
				try {
					content = ((ColumnFormatDate) columnFormat).parse((String) content);
				} catch (ParseException e) {
				}
			}
		}

		/* if content is null then write NULL */
		if(null == content)
			content = nullReplacemenets[column];

		/* column type */
		Class<?> cType = content.getClass();
		
		/* add content to cell */
		try {
			addContentToCell(cType, content, workbook, sheet, column, cell, styles, cellFormatter);
		} catch (ExcelExportException e) {
			throw new RuntimeException(e);
		}
		
		column++;
	}
	
	private void prepareNullFormat(CellFormatter cellFormatter, Column column, int col) {
		if(null == cellFormatter){
			nullReplacemenets[col] = null != column.getNullReplacementFormat() ? column.getNullReplacementFormat() : "NULL";
			return;
		}
		
		try{
			if(Integer.class.equals(td.getColumnType(col)) ||
			   Long.class.equals(td.getColumnType(col)) ||
			   Byte.class.equals(td.getColumnType(col)) ||
			   Short.class.equals(td.getColumnType(col))){

			   nullReplacemenets[col] = NumberFormat.getIntegerInstance().parse((String)cellFormatter.format(null));
			} else if(Double.class.equals(td.getColumnType(col)) ||
					  Float.class.equals(td.getColumnType(col)) ||
					  BigDecimal.class.equals(td.getColumnType(col))){
			
				nullReplacemenets[col] = NumberFormat.getInstance().parse((String)cellFormatter.format(null));
			}
		} catch (ParseException e) {
			nullReplacemenets[col] = cellFormatter.format(null);
		}
		
		if(null == nullReplacemenets[col])
			nullReplacemenets[col] = null != column.getNullReplacementFormat() ? column.getNullReplacementFormat() : "NULL";
	}

	@Override
	public void addGroupRow(int[] subtotalIndices, Object[] subtotals,
			int[] subtotalGroupFieldIndices, Object[] subtotalGroupFieldValues,
			int rowSize, CellFormatter[] cellFormatters) throws IOException {
		basicXlsGenerator.addGroupRow(subtotalIndices, subtotals, subtotalGroupFieldIndices, subtotalGroupFieldValues, rowSize, cellFormatters);
	}

	@Override
	public void close() throws IOException {
		if(withSubtotals){
			basicXlsGenerator.close();
			return;
		}
		
		/* adjust column sizes */
		for(int column = 0; column < td.getColumnCount(); column++)
			sheet.setColumnWidth(column, 260 * Math.min(75, columnWidths[column]) + 2);

		workbook.write(os);
		os.close();
	}
	
	@Override
	public void nextRow() throws IOException {
		if(withSubtotals){
			basicXlsGenerator.nextRow();
			return;
		}
		
		row++;
		column = 0;
		
		/* create normal data row */
		dataRow = sheet.createRow(row+1);
	}
	
	@Override
	public CompiledReport getTableObject() {
		if(withSubtotals){
			return basicXlsGenerator.getTableObject();
		}
		
		return new CompiledXLSXTableReport(!dontStream ? null : ((ByteArrayOutputStream)os).toByteArray());
	}


	@Override
	public CompiledReport getFormatInfo() {
		if(withSubtotals){
			return basicXlsGenerator.getFormatInfo();
		}
		
		return new CompiledXLSXTableReport(null);
	}

	@Override
	protected boolean hasConfig(Class<? extends ReportExecutionConfig> type){
		if(withSubtotals)
			return basicXlsGenerator.hasConfig(type);
		return super.hasConfig(type);
	}
	
	@Override
	protected <R extends ReportExecutionConfig> R getConfig(Class<? extends R> type){
		if(withSubtotals)
			return basicXlsGenerator.getConfig(type);
		return super.getConfig(type);
	}

	@Override
	protected ReportExecutionConfig[] getConfigs() {
		if(withSubtotals)
			return basicXlsGenerator.getConfigs();
		return super.getConfigs();
	}

	@Override
	protected Report getReport() {
		if(withSubtotals)
			return basicXlsGenerator.getReport();
		return super.getReport();
	}

	@Override
	protected TableDefinition getTd() {
		if(withSubtotals)
			return basicXlsGenerator.getTd();
		return super.getTd();
	}
	
	@Override
	public boolean isCatchAll() {
		if(withSubtotals)
			return basicXlsGenerator.isCatchAll();
		return super.isCatchAll();
	}
	
	@Override
	protected Object getValueOf(Object field) {
		if(withSubtotals)
			return basicXlsGenerator.getValueOf(field);
		return super.getValueOf(field);
	}
	

	private void addContentToCell(Class<?> cType, Object content, Workbook workbook, Sheet sheet, int column, Cell cell, CellStyle[] styles, CellFormatter cellFormatter) throws ExcelExportException {
		/* string content */
		String sContent = String.valueOf(content);
		if(null != sContent)
			columnWidths[column] = Math.max(sContent.length(), columnWidths[column]);
		
		/* date */
		try{
			if(cType.equals(java.util.Date.class)){ 
				addDate((java.util.Date) content, workbook, sheet, column, cell, styles);
			} else if(cType.equals(Time.class)){
				addTime((Time) content, workbook, sheet, column, cell, styles);
			} else if(cType.equals(Timestamp.class)){
				/* special oracle handling */
				if(sos.isOracleTimestamp(content)){
					java.util.Date d = sos.getDateFromOracleDatum(content);
					addDate(d, workbook, sheet, column, cell, styles);
				} else {
					Timestamp ts = (Timestamp)content;
					long time = ts.getTime() + (ts.getNanos() / 1000000);

					java.util.Date d = new java.util.Date(time);
					addDate(d, workbook, sheet, column, cell, styles);
				}
			} //continues
			
			/* binary data */
			else if(cType.equals(Byte[].class)){
				cell.setCellValue( messages.rsTableToXLSBinaryData());
			} // continues
			
			/* integer */
			else if(cType.equals(Integer.class)){
				addInteger((Integer)content, sheet, column, cell, styles);
			} else if(cType.equals(Long.class)){
				/* excel does not support long */
				addDouble(Double.valueOf((Long)content), sheet, column, cell, styles);
			} else if(cType.equals(Byte.class)){
				addInteger(((Byte)content).intValue(), sheet, column, cell, styles);
			} else if(cType.equals(Short.class)){
				addInteger(((Short)content).intValue(), sheet, column, cell, styles);
			} // continues
			
			/* double */
			else if(cType.equals(Double.class)){
				addDouble((Double)content, sheet, column, cell, styles);
			} else if(cType.equals(Float.class)){
				addDouble(((Float)content).doubleValue(), sheet, column, cell, styles);
			} else if(cType.equals(BigDecimal.class)){
				addDouble(((BigDecimal)content).doubleValue(), sheet, column, cell, styles);
			} // continues
			
			/* strings */
			else {
				addString(sContent, sheet, column, cell, styles);
			}
		} catch(NullPointerException e){
			cell.setCellValue(cellFormatter.format(null));
		}
	}

	private void addTime(Time content, Workbook workbook, Sheet sheet, int column, Cell cell, CellStyle[] styles) {
		CellStyle dateFormat = styles[column] == null ? getTimeFormat(workbook) : styles[column];
		cell.setCellStyle(dateFormat);
		cell.setCellValue(content);
	}
	
	private void addDate(java.util.Date content, Workbook workbook, Sheet sheet, int column, Cell cell, CellStyle[] styles) {
		CellStyle dateFormat = styles[column] == null ? getFullDateFormat(workbook) : styles[column];
		cell.setCellStyle(dateFormat);
		cell.setCellValue(content);
	}
	
	private void addString(String content, Sheet sheet, int column, Cell cell,
			CellStyle[] styles) {
		if(null == styles[column])
			cell.setCellValue(content);
		else {
			CellStyle format = styles[column];
			cell.setCellStyle(format);
			cell.setCellValue(content);
		}
		cell.setCellType(Cell.CELL_TYPE_STRING);
	}


	private void addDouble(Double content, Sheet sheet, int column, Cell cell, CellStyle[] styles) {
		if(null == styles[column])
			cell.setCellValue(content);
		else {
			CellStyle format = styles[column];
			cell.setCellStyle(format);
			cell.setCellValue(content);
		}
		cell.setCellType(Cell.CELL_TYPE_NUMERIC);
	}

	private void addInteger(int content, Sheet sheet, int column, Cell cell, CellStyle[] styles) {
		if(null == styles[column])
			cell.setCellValue(content);
		else {
			CellStyle format = styles[column];
			cell.setCellStyle(format);
			cell.setCellValue(content);
		}
		cell.setCellType(Cell.CELL_TYPE_NUMERIC);
	}
	
	private CellStyle getTimeFormat(Workbook workbook){
		if(null == timeFormat){
			CreationHelper createHelper = workbook.getCreationHelper();
			timeFormat = workbook.createCellStyle();
			timeFormat.setDataFormat(createHelper.createDataFormat().getFormat(messages.timeFormat()));
		}
		return timeFormat;
	}
	
	private CellStyle getFullDateFormat(Workbook workbook){
		if(null == fullDateFormat){
			CreationHelper createHelper = workbook.getCreationHelper();
			fullDateFormat = workbook.createCellStyle();
			fullDateFormat.setDataFormat(createHelper.createDataFormat().getFormat(messages.dateFormat()));
		}
		return fullDateFormat;
	}
}
