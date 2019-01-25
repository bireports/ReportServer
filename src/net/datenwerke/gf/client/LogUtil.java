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
 
 
package net.datenwerke.gf.client;

//credit Fred Sauer. The original code comes from gwt-log 
public class LogUtil {

    public static native void setJSNIErrorHandler()
        /*-{
          if ($wnd != window) {
            window.onerror =
@net.datenwerke.gf.client.LogUtil::handleOnError(Ljava/lang/String;Ljava/lang/String;I);
          }

          var oldOnError = $wnd.onerror;
          $wnd.onerror = function(msg, url, line) {
            var result, oldResult;
            try {
              result =
@net.datenwerke.gf.client.LogUtil::handleOnError(Ljava/lang/String;Ljava/lang/String;I)(msg, url, line);
            } finally {
              oldResult = oldOnError && oldOnError(msg, url, line);
            }
            if (result != null) return result;
            if (oldResult != null) return oldResult;
          };
        }-*/;

    private static native boolean handleOnError(String msg, String url, int line)
        /*-{
          @net.datenwerke.gf.client.LogUtil::fatal(Ljava/lang/String;)("Uncaught JavaScript exception [" + msg
+ "] in " + url + ", line " + line);
          return true;
        }-*/;

    private static void fatal(String message) {
        System.err.println(message);
    }
}