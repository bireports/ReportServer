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
 
 
package net.datenwerke.rs.saiku.client.saiku.locale;

import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.Messages;

public interface SaikuNativeMessages extends Messages {

	public final static SaikuNativeMessages INSTANCE = GWT.create(SaikuNativeMessages.class);

	String ClearAxis();
	String Rows();
	String Search();
	String Statistics();
	String ExportDrillThroughOnCellToCSV();
	String RunningQuery();
	String RunQuery();
	String AvailableMembers();
	String YouNeedToPutAtLeastOneLevelOrMeasureOnColumnsAndRowsForAValidQuery();
	String RowLimit0None();
	String QueryScenario();
	String ShowExplainPlan();
	String Nonempty();
	String SparkLine();
	String CouldNotReachServerPleaseTryAgainLater();
	String Filterspanspan();
	String UsedMembers();
	String RefreshCubesClearCache();
	String Lt();
	String SelectACube();
	String ReloadCube();
	String Mode();
	String Filter();
	String BasicStatistics();
	String CloseOthers();
	String ShowMDX();
	String ExportCSV();
	String Dimensions();
	String MultipleBar();
	String AutomaticExecution();
	String SwitchToMDXMode();
	String HideParents();
	String UpdatingChartData();
	String Loading();
	String Measures();
	String Heatgrid();
	String CurrentlyPrivateTo();
	String Properties();
	String IncludeLevel();
	String HeatGrid();
	String SaveQuery();
	String S();
	String Roles();
	String bCAUTIONbTheNumberOfRowsCanHaveASignificantImpactOnThePerformanceOfTheFollowingAction();
	String StackedBar();
	String EXPERIMENTALExportPDF();
	String Locale();
	String Options();
	String ExecutingDrillthrough();
	String Line();
	String Open();
	String Waterfall();
	String Area();
	String Logout();
	String Measure();
	String KeepThisFilePrivateToMe();
	String MultipleBarChart();
	String DrillThroughOnCell();
	String ToggleChart();
	String OpenQuery();
	String UnsavedQuery();
	String SwappingAxes();
	String Pie();
	String RemoveLevel();
	String About();
	String ExportDrillThroughResultOfCurrentQueryToCSV();
	String Min();
	String Searchnbsp();
	String UseResult();
	String AddNewTagBySelectingCellsFromYourResult();
	String Pl();
	String Hr();
	String Custom();
	String Hu();
	String Permissions();
	String Export();
	String Tags();
	String AddRolePermission();
	String SelectionsFor();
	String ShowUniqueNames();
	String Columns();
	String ToggleFields();
	String ImproveThisTranslation();
	String CloseThis();
	String DrillThroughCurrentQuery();
	String NewQuery();
	String Sum();
	String StdDeviation();
	String Duration();
	String SwapAxis();
	String Cancel();
	String Translator_name();
	String Max();
	String Bar();
	String Average();
	String ChartType();
	String KeepOnly();
	String IssueTracker();
	String SparkBar();
	String PleaseSelectAFile();
	String ToggleSidebar();
	String ExportXLS();
	String File();
	String New();
	String OpeningQuery();
	String Bar100();
	String Dot();
	String Rowsspanspan();
	String DeleteQuery();
	String KeepAndIncludeLevel();
	String DrillThrough();
	String Cubes();
	String Info();
	String LoadingDatasources();
	
	String ShowChildren();
	String KeepandIncludeLevel();
	String ClearFilter();
	String Top10();
	String Bottom10();
	String Top10by();
	String Bottom10by();
	String CustomLimit();
	String ClearLimit();
	String Ascending();
	String Descending();
	String AscendingBreakingHierarchy();
	String DescendingBreakingHierarchy();
	String ClearSort();
	String YouneedtoputatleastonelevelormeasureonColumnsandRowsforavalidquery();
}
