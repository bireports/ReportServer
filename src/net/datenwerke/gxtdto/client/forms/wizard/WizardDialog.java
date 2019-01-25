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
 
 
package net.datenwerke.gxtdto.client.forms.wizard;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.datenwerke.gxtdto.client.baseex.widget.DwWindow;
import net.datenwerke.gxtdto.client.baseex.widget.btn.DwTextButton;
import net.datenwerke.gxtdto.client.baseex.widget.mb.DwAlertMessageBox;
import net.datenwerke.gxtdto.client.forms.locale.FormsMessages;
import net.datenwerke.gxtdto.client.locale.BaseMessages;

import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.widget.core.client.container.CardLayoutContainer;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;

/**
 * A Dialog capable of navigating between multiple pages by means of prev/next/finish buttons
 * 
 *
 */
public abstract class WizardDialog extends DwWindow{
	
	private CardLayoutContainer cardLayoutContainer;
	private DwTextButton prevButton;
	private DwTextButton nextButton;
	private DwTextButton cancelButton;
	private DwTextButton finishButton;
	private List<Widget> pages;
	private int currentPage;
	
	public WizardDialog() {
		this(new Widget[]{});
	}
	
	public WizardDialog(Widget... pages) {
		super();
		cardLayoutContainer = new CardLayoutContainer();
		setWidget(cardLayoutContainer);
		setModal(true);
		
		this.pages = new ArrayList<Widget>();
		this.pages.addAll(Arrays.asList(pages));
		
		for(Widget page : pages)
			add(page);

		createButtons();
		showPage(0);
	}
	
	public int getPageCount(){
		return pages.size();
	}
	
	public void addPage(Widget page){
		pages.add(page);
		cardLayoutContainer.add(page);
		if(page instanceof WizardAware)
			((WizardAware)page).setWizard(this);
	}
	
	public void addPage(int index, Widget page){
		pages.add(index, page);
		cardLayoutContainer.add(page);
	}
	
	public Widget removePage(int index){
		return pages.remove(index);
	}
	
	public boolean removePage(Widget page){
		return pages.remove(page);
	}
	
	public void showPage(int pageNum){
		if(pageNum > pages.size() - 1)
			return;
		
		Widget page = pages.get(pageNum);
		cardLayoutContainer.setActiveWidget(page);
		
		prevButton.setEnabled(pageNum > 0);
		nextButton.setEnabled(pageNum < pages.size() - 1);
		
		finishButton.setEnabled(pageNum == pages.size() - 1);

		if(page instanceof WizardResizer)
			setHeight(((WizardResizer)page).getPageHeight());
		else
			setHeight(-1);
		
		currentPage = pageNum;
		
		forceLayout();
	}
	
	private void createButtons() {
		prevButton = new DwTextButton(BaseMessages.INSTANCE.prev()); 
		nextButton = new DwTextButton(BaseMessages.INSTANCE.next()); 
		cancelButton = new DwTextButton(BaseMessages.INSTANCE.cancel()); 
		finishButton = new DwTextButton( FormsMessages.INSTANCE.finish());
		
		finishButton.setEnabled(false);
		
		prevButton.addSelectHandler(new SelectHandler() {
			@Override
			public void onSelect(SelectEvent event) {
				showPage(currentPage - 1);
			}
		});

		nextButton.addSelectHandler(new SelectHandler() {
			@Override
			public void onSelect(SelectEvent event) {
				if(!currentPageValid())
					return;
				showPage(currentPage + 1);
			}
		});

		cancelButton.addSelectHandler(new SelectHandler() {
			@Override
			public void onSelect(SelectEvent event) {
				WizardDialog.this.hide();
			}
		});

		finishButton.addSelectHandler(new SelectHandler() {
			@Override
			public void onSelect(SelectEvent event) {
				if(!currentPageValid())
					return;
				onFinish();
			}
		});

		addButton(prevButton);
		addButton(nextButton);
		addButton(finishButton);

	}
	
	private boolean currentPageValid(){
		Widget current = cardLayoutContainer.getActiveWidget();
		if(current instanceof Validatable){
			if(!((Validatable) current).isValid()){
				new DwAlertMessageBox(FormsMessages.INSTANCE.invalidConfigTitle(), FormsMessages.INSTANCE.invalidConfigMessage()).show();
			    return false;
			}
		}
		return true;
	}

	public DwTextButton getCancelButton() {
		return cancelButton;
	}
	
	public DwTextButton getFinishButton() {
		return finishButton;
	}

	public abstract void onFinish();
}
