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
 
 
package net.datenwerke.rs.saiku.client.saiku.locale;

import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.Messages;

public interface SaikuNativeMessages extends Messages {

	public final static SaikuNativeMessages INSTANCE = GWT.create(SaikuNativeMessages.class);

	
	String About();
	String AddNewTagBySelectingCellsFromYourResult();
	String AddRolePermission();
	String Add();
	String Avg();
	String Area();
	String Ascending();
	String AscendingBreakingHierarchy();
	String AutomaticExecution();
	String AvailableMembers();
	String Average();
	String Bar();
	String Bar100();
	String BasicStatistics();
	String bCAUTIONbTheNumberOfRowsCanHaveASignificantImpactOnThePerformanceOfTheFollowingAction();
	String Bottom10();
	String Bottom10by();
	String Cancel();
	String CalculatedMeasure();
	String CalculatedMeasures();
	String CAUTION();
	String ChartMode();
	String ChartType();
	String ClearAxis();
	String ClearFilter();
	String ClearLimit();
	String ClearSort();
	String CloseOthers();
	String CloseThis();
	String Columns();
	String CouldNotReachServerPleaseTryAgainLater();
	String Cubes();
	String CurrentlyPrivateTo();
	String Custom();
	String CustomLimit();
	String DeleteQuery();
	String Descending();
	String DescendingBreakingHierarchy();
	String Dimensions();
	String Dot();
	String DrillThrough();
	String DrillThroughCurrentQuery();
	String DrillThroughOnCell();
	String Duration();
	String ExecutingDrillthrough();
	String EXPERIMENTALExportPDF();
	String Exclude();
	String Export();
	String ExportCSV();
	String ExportDrillThroughOnCellToCSV();
	String ExportDrillThroughResultOfCurrentQueryToCSV();
	String ExportXLS();
	String File();
	String Filter();
	String Filterspanspan();
	String Heatgrid();
	String HeatGrid();
	String HideParents();
	String Hr();
	String Hu();
	String ImproveThisTranslation();
	String IncludeLevel();
	String Include();
	String Info();
	String Items();
	String IssueTracker();
	String KeepandIncludeLevel();
	String KeepAndIncludeLevel();
	String KeepOnly();
	String KeepThisFilePrivateToMe();
	String Line();
	String Loading();
	String LoadingDatasources();
	String Locale();
	String Logout();
	String Lt();
	String Max();
	String Measure();
	String Measures();
	String Min();
	String Mode();
	String MultipleBar();
	String MultipleBarChart();
	String New();
	String NewQuery();
	String Nonempty();
	String No();
	String Open();
	String OpeningQuery();
	String OpenQuery();
	String Options();
	String Parameters();
	String Permissions();
	String Pie();
	String Pl();
	String PleaseSelectAFile();
	String Properties();
	String QueryScenario();
	String RefreshCubesClearCache();
	String ReloadCube();
	String RemoveLevel();
	String Roles();
	String RowLimit0None();
	String Rows();
	String Rowsspanspan();
	String RunningQuery();
	String RunQuery();
	String S();
	String SaveQuery();
	String Search();
	String Searchnbsp();
	String SelectACube();
	String SelectionsFor();
	String ShowChildren();
	String ShowExplainPlan();
	String ShowMDX();
	String ShowUniqueNames();
	String SparkBar();
	String SparkLine();
	String StackedBar();
	String Statistics();
	String StdDeviation();
	String Sum();
	String SwapAxis();
	String SwappingAxes();
	String SwitchToMDXMode();
	String Tags();
	String ToggleChart();
	String ToggleFields();
	String ToggleSidebar();
	String Top10();
	String Top10by();
	String Translator_name();
	String UnsavedQuery();
	String UpdatingChartData();
	String UsedMembers();
	String UseResult();
	String Waterfall();
	String Yes();
	String YouneedtoputatleastonelevelormeasureonColumnsandRowsforavalidquery();
	String YouNeedToPutAtLeastOneLevelOrMeasureOnColumnsAndRowsForAValidQuery();
	String DeepAvg();
}
