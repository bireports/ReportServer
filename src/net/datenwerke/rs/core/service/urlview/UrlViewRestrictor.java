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
 
 
package net.datenwerke.rs.core.service.urlview;

import net.datenwerke.rs.utils.eventbus.EventBus;
import net.datenwerke.security.service.eventlogger.events.InvalidConfigEvent;
import net.datenwerke.security.service.usermanager.UserManagerService;
import net.datenwerke.security.service.usermanager.entities.AbstractUserManagerNode;
import net.datenwerke.security.service.usermanager.entities.Group;
import net.datenwerke.security.service.usermanager.entities.OrganisationalUnit;
import net.datenwerke.security.service.usermanager.entities.User;

import org.apache.commons.configuration.SubnodeConfiguration;

import com.google.inject.Inject;

public class UrlViewRestrictor {

	@Inject
	private static UserManagerService userService;
	
	@Inject
	private static EventBus eventBus;
	
	private static final String GROUP_RESTRICTION = "groups";
	private static final String OU_RESTRICTION = "ous";
	private static final String USER_RESTRICTION = "users";
	
	private static final String URLVIEW_ERROR_NAME = "urlview";
	
	private final User user;
	
	public UrlViewRestrictor(User user){
		this.user = user;
	}
	
	public boolean restrictionApplies(SubnodeConfiguration conf){
		if(null == conf)
			return false;
		
		/* check users */
	    String[] userList = conf.getString(USER_RESTRICTION).split(",");
	    if(null != userList){
	    	String userName = user.getName().trim();
	    	boolean found = false;
	    	for(String entry : userList){
	    		String trimmedEntry = entry.trim();
	    		if(trimmedEntry.equals(userName)){
	    			found = true;
	    			break;
	    		}
	    	}
	    	if(found)
	    		return false;
	    }
	    
	    /* check groups */
	    String[] groupList = conf.getString(GROUP_RESTRICTION).split(",");
	    if(null != groupList){
	    	boolean found = false;
	    	for(String entry : groupList){
	    		String trimmedEntry = entry.trim();
	    		try{
		    		Long id = Long.valueOf(trimmedEntry);
		    		AbstractUserManagerNode node = userService.getNodeById(id);
		    		Group group = (Group) node;
		    		if( userService.userInFolk(user, group) ){
		    			found = true;
		    			break;
		    		}
	    		} catch(Exception e){
	    			eventBus.fireEvent(new InvalidConfigEvent(URLVIEW_ERROR_NAME, "Could not find group for entry: " + entry));
	    		}
	    	}
	    	if(found)
	    		return false;
	    }
	    
	    /* check ous */
	    String[] ouList = conf.getString(OU_RESTRICTION).split(",");
	    if(null != ouList){
	    	boolean found = false;
	    	for(String entry : ouList){
	    		String trimmedEntry = entry.trim();
	    		try{
		    		Long id = Long.valueOf(trimmedEntry);
		    		AbstractUserManagerNode node = userService.getNodeById(id);
		    		OrganisationalUnit ou = (OrganisationalUnit) node;
		    		if( userService.userInFolk(user, ou) ){
		    			found = true;
		    			break;
		    		}
	    		} catch(Exception e){
	    			eventBus.fireEvent(new InvalidConfigEvent(URLVIEW_ERROR_NAME, "Could not find organisational unit for entry: " + entry));
	    		}
	    	}
	    	if(found)
	    		return false;
	    }
	    
		return true;
	}
}
