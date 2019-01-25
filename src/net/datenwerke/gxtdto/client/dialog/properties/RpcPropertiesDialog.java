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
 
 
package net.datenwerke.gxtdto.client.dialog.properties;

import java.util.Iterator;

import net.datenwerke.gxtdto.client.utilityservices.submittracker.SubmitCompleteCallback;
import net.datenwerke.gxtdto.client.utilityservices.submittracker.SubmitTracker;
import net.datenwerke.gxtdto.client.utilityservices.submittracker.SubmitTrackerService;
import net.datenwerke.gxtdto.client.utilityservices.submittracker.SubmitTrackerToken;

import com.google.inject.Inject;

/**
 * 
 *
 */
public class RpcPropertiesDialog extends PropertiesDialog {

	protected final SubmitTrackerService submitTrackerService;

	protected String maskOnSubmit;
	protected boolean performSubmitsConsecutively = false;
	protected boolean continueOnFailure = false;
	
	protected SubmitCompleteCallback submitCompleteCallback = new SubmitCompleteCallback() {
		@Override
		public void onSuccess() {
		}
		
		@Override
		public void onFailure(Throwable t) {
		}
	};
	
	@Inject
	public RpcPropertiesDialog(
		SubmitTrackerService submitTrackerService
		){
		super();
		
		/* store objects */
		this.submitTrackerService = submitTrackerService;
		
		/* init */
		setCloseOnSubmit(false);
	}

	@Override
	protected void notifyCardsOfSubmit() {
		if(null != maskOnSubmit)
			mask(maskOnSubmit);
		
		if(isPerformSubmitsConsecutively())
			submitConsecutively();
		else
			submitNormal();
		
	}
	
	protected void submitConsecutively() {
		Iterator<PropertiesDialogCard> cardIterator = cards.iterator();
		
		if(cardIterator.hasNext())
			submitConsecutively(cardIterator.next(), cardIterator);
		else
			submitConsecutivelyDone();
	}

	protected void submitConsecutivelyDone() {
		submitCompleteCallback.onSuccess();
	}

	protected void submitConsecutively(PropertiesDialogCard card,
			final Iterator<PropertiesDialogCard> cardIterator) {
		if(card instanceof RpcPropertiesDialogCard) {
			SubmitTrackerToken token = submitTrackerService.createToken();
			token.setSubmitCompleteCallback(new SubmitCompleteCallback() {
				@Override
				public void onSuccess() {
					if(cardIterator.hasNext())
						submitConsecutively(cardIterator.next(), cardIterator);
					else
						submitConsecutivelyDone();
				}
				
				@Override
				public void onFailure(Throwable t) {
					submitCompleteCallback.onFailure(t);
					if(continueOnFailure)
						onSuccess();
				}
			});
			((RpcPropertiesDialogCard) card).submitPressed(token);
		} else {
			card.submitPressed();
			if(cardIterator.hasNext())
				submitConsecutively(cardIterator.next(), cardIterator);
			else
				submitConsecutivelyDone();
		}
	}

	protected void submitNormal() {
		SubmitTracker tracker = submitTrackerService.createTracker();
		for(PropertiesDialogCard card : cards)
			if(card instanceof RpcPropertiesDialogCard)
				((RpcPropertiesDialogCard) card).submitPressed(tracker.createToken());
			else
				card.submitPressed();
		
		tracker.beginTracking(submitCompleteCallback);
	}

	public void setSubmitCompleteCallback(SubmitCompleteCallback callback){
		this.submitCompleteCallback = callback;
	}
	
	public void setMaskOnSubmit(String maskOnSubmit) {
		this.maskOnSubmit = maskOnSubmit;
	}

	public boolean isPerformSubmitsConsecutively() {
		return performSubmitsConsecutively;
	}

	public void setPerformSubmitsConsecutively(boolean performSubmitsConsecutively) {
		this.performSubmitsConsecutively = performSubmitsConsecutively;
	}

	public void continueOnFailure(boolean b) {
		continueOnFailure = b;
	}
	
	
}
