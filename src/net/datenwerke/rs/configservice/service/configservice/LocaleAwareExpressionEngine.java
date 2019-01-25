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
 
 
package net.datenwerke.rs.configservice.service.configservice;

import java.util.ArrayList;
import java.util.List;

import net.datenwerke.rs.utils.localization.LocalizationServiceImpl;

import org.apache.commons.configuration.tree.ConfigurationNode;
import org.apache.commons.configuration.tree.ExpressionEngine;
import org.apache.commons.configuration.tree.NodeAddData;

/**
 * Adds support for config files with per-locale properties e.g.
 *  <view>
 *     <types>net.datenwerke.rs.tsreportarea.client.tsreportarea.dto.TsDiskReportReferenceDto</types>
 *     <name>default name}</name>
 *     <name locale="en">english description</name>
 *     <name locale="de">deutsche bezeichnung</name>
 *  </view>
 * 
 */
public class LocaleAwareExpressionEngine implements ExpressionEngine {

	private static final String LOCALE_ATTRIBUTE = "locale";

	private ExpressionEngine originalExpressionEngine;

	private LocalizationServiceImpl localizationService;

	public LocaleAwareExpressionEngine(ExpressionEngine originalExpressionEngine) {
		this.originalExpressionEngine = originalExpressionEngine;
		this.localizationService = new LocalizationServiceImpl();
	}


	@Override
	public List<ConfigurationNode> query(ConfigurationNode root, String key) {
		List<ConfigurationNode> res = originalExpressionEngine.query(root, key);

		/* If there are multiple matching nodes and a node specific to the current locale 
		 * was found, only return this node. Otherwise the default mechanism (return all 
		 * nodes, use first) is applied. 
		 */
		if(res.size() > 1) {
			List<ConfigurationNode> matching = new ArrayList<ConfigurationNode>();
			for(ConfigurationNode node : res){
				if(matchesLocale(node)){
					matching.add(node);
				}
			}
			if(!matching.isEmpty())
				return matching;
		}

		return res;
	}

	private boolean matchesLocale(ConfigurationNode node){
		if(node.getAttributeCount(LOCALE_ATTRIBUTE) == 0)
			return false;
		
		String locale = localizationService.getLocale().getLanguage();
		if(null == locale)
			return false;
		
		for(ConfigurationNode cfgnode : node.getAttributes(LOCALE_ATTRIBUTE)){
			if(locale.equals(cfgnode.getValue()))
				return true;
		}
		return false;
	}

	@Override
	public String nodeKey(ConfigurationNode node, String parentKey) {
		return originalExpressionEngine.nodeKey(node, parentKey);
	}

	@Override
	public NodeAddData prepareAdd(ConfigurationNode root, String key) {
		return originalExpressionEngine.prepareAdd(root, key);
	}

}
