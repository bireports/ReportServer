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
 
 
package net.datenwerke.gxtdto.client.utilityservices.submittracker;

import java.util.Arrays;
import java.util.Collection;

import com.google.gwt.user.client.Timer;

/**
 * 
 *
 */
public class SubmitTrackerServiceImpl implements SubmitTrackerService {

	public static final int RUNS_UNTIL_FAILURE = 120;
	private long ticketNr = 1;
	
	public SubmitTrackerServiceImpl(){
		
	}

	@Override
	public SubmitTracker createTracker(){
		return new SubmitTracker(this);
	}
	
	public synchronized SubmitTrackerToken createToken(){
		return new SubmitTrackerToken(++ticketNr);
	}
	
	@Override
	public void trackSubmit(SubmitCompleteCallback callback, SubmitTrackerToken... trackers){
		trackSubmit(callback, Arrays.asList(trackers));
	}
	
	@Override
	public void trackSubmit(final SubmitCompleteCallback callback, final Collection<SubmitTrackerToken> trackers){
		Timer timer = new Timer() {
			private int run = 0;
			
			@Override
			public void run() {
				if(++run > RUNS_UNTIL_FAILURE){
					this.cancel();
					callback.onFailure(new RuntimeException("timeout"));
				}
				
				for(SubmitTrackerToken tracker : trackers)
					if(! tracker.isRequestComplete())
						return;
				this.cancel();
				callback.onSuccess();
			}
		};
		
		timer.scheduleRepeating(500);
	}
	


}
