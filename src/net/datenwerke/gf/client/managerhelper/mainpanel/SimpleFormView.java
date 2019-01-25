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
 
 
package net.datenwerke.gf.client.managerhelper.mainpanel;

import java.util.ArrayList;
import java.util.List;

import net.datenwerke.gf.client.managerhelper.hooks.SimpleFormViewHook;
import net.datenwerke.gf.client.managerhelper.locale.ManagerhelperMessages;
import net.datenwerke.gf.client.upload.FileUploadUIModule;
import net.datenwerke.gxtdto.client.baseex.widget.DwContentPanel;
import net.datenwerke.gxtdto.client.baseex.widget.layout.DwFlowContainer;
import net.datenwerke.gxtdto.client.dialog.error.DetailErrorDialog;
import net.datenwerke.gxtdto.client.forms.simpleform.SimpleForm;
import net.datenwerke.gxtdto.client.forms.simpleform.SimpleFormSubmissionCallback;
import net.datenwerke.gxtdto.client.forms.simpleform.SimpleMultiForm;
import net.datenwerke.gxtdto.client.locale.BaseMessages;
import net.datenwerke.gxtdto.client.servercommunication.callback.NotamCallback;
import net.datenwerke.hookhandler.shared.hookhandler.HookHandlerService;
import net.datenwerke.rs.theme.client.icon.BaseIcon;
import net.datenwerke.security.client.security.dto.WriteDto;
import net.datenwerke.security.client.treedb.dto.decorator.SecuredAbstractNodeDtoDec;
import net.datenwerke.treedb.client.treedb.dto.AbstractNodeDto;

import com.google.gwt.resources.client.ImageResource;
import com.google.inject.Inject;
import com.sencha.gxt.core.client.dom.ScrollSupport.ScrollMode;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.widget.core.client.Component;
import com.sencha.gxt.widget.core.client.container.FlowLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer.VerticalLayoutData;
import com.sencha.gxt.widget.core.client.event.SubmitCompleteEvent;
import com.sencha.gxt.widget.core.client.event.SubmitCompleteEvent.SubmitCompleteHandler;
import com.sencha.gxt.widget.core.client.form.FormPanel.Encoding;
import com.sencha.gxt.widget.core.client.form.FormPanel.Method;

/**
 * A {@link MainPanelView} that displays a widget.
 * 
 *
 */
public abstract class SimpleFormView extends MainPanelView {

	private SimpleMultiForm form;
	
	@Inject
	protected HookHandlerService hookHandlerService;

	private DwFlowContainer wrapper;

	private VerticalLayoutContainer formWrapper;
	
	@Override
	public String getComponentHeader(){
		return BaseMessages.INSTANCE.properties();
	}

	@Override
	public ImageResource getIcon() {
		return BaseIcon.LIST.toImageResource();
	}
	
	@Override
	public Component getViewComponent() {
		wrapper = new DwFlowContainer();
		wrapper.setScrollMode(ScrollMode.AUTOY);
		
		formWrapper = new VerticalLayoutContainer();
		wrapper.add(formWrapper);
		
		/* create form and configure submit event handler */ 
		form = new SimpleMultiForm();
		formWrapper.add(form, new VerticalLayoutData(1,-1, new Margins(10)));

		if(null != getFormWidth())
			form.setWidth(getFormWidth());

		form.addSubmissionCallback(new SimpleFormSubmissionCallback(form) {
			@Override
			public void formSubmitted() {
				onSubmit(this);
			}
		});

		/* is upload form */
		if(isUploadForm()){
			form.setMethod(Method.POST);
			form.setEncoding(Encoding.MULTIPART);
			
			form.getFormPanel().addSubmitCompleteHandler(new SubmitCompleteHandler() {
				
				@Override
				public void onSubmitComplete(SubmitCompleteEvent event) {
					if(!event.getResults().startsWith(FileUploadUIModule.UPLOAD_SUCCESSFUL_PREFIX))
						new DetailErrorDialog(BaseMessages.INSTANCE.error(), BaseMessages.INSTANCE.uploadError(), ManagerhelperMessages.INSTANCE.upoadError() + event.getResults()).show(); 
					
					/* reload selected node */
					reloadNodeAndView();
				}
			});
		}
		
		if(null != getFormAction())
			form.setAction(getFormAction());
		
		/* ask configurator for a configuration */
		configureSimpleForm(form);
		
		/* binding */
		form.bind(getBindingEntity());
		
		callbackAfterBinding(form);
		
		/* add external fields from hookers */
		List<SimpleFormViewHook> simpleFormViewHookers = getSimpleFormViewHookers(this.getClass());
		
		for(SimpleFormViewHook hooker : simpleFormViewHookers){
			SimpleForm xform = SimpleForm.getNewInstance();
			xform.getButtonBar().clear();
			hooker.configureSimpleForm(xform, getSelectedNode());
			form.addSubForm(xform);
		}
		
		/* buttons */
		if((getSelectedNode() instanceof SecuredAbstractNodeDtoDec) && ! ((SecuredAbstractNodeDtoDec)getSelectedNode()).hasAccessRight(WriteDto.class))
			form.getButtonBar().clear();
		
		return wrapper;
	}
	
	protected String getFormAction() {
		return null;
	}

	protected boolean isUploadForm() {
		return false;
	}

	protected VerticalLayoutContainer getFormWrapper() {
		return formWrapper;
	}
	
	protected String getFormWidth() {
		return null;
	}

	protected FlowLayoutContainer createFlowWrapper() {
		return new DwFlowContainer();
	}

	protected void callbackAfterBinding(SimpleMultiForm form) {
	}

	protected void onSubmit(final SimpleFormSubmissionCallback callback) {
		/* upload */
		if(isUploadForm())
			form.submit();	
		
		/* perform server call */
		treeManager.updateNode(getSelectedNode(), new NotamCallback<AbstractNodeDto>(ManagerhelperMessages.INSTANCE.updated()){
			@Override
			public void doOnSuccess(AbstractNodeDto result) {
				callback.cbSuccess();
				onSuccessfulSubmit();
			}
			
			@Override
			public void doOnFailure(Throwable caught) {
				callback.cbFailure(caught);
			}
		});
	}

	protected void onSuccessfulSubmit() {
		
	}

	protected Object getBindingEntity() {
		return getSelectedNode();
	}

	public void mask(String message){
		wrapper.mask(message);
	}
	
	public void unmask(){
		wrapper.unmask();
	}
	
	abstract protected void configureSimpleForm(SimpleForm form);
	

	protected <T extends SimpleFormView> List<SimpleFormViewHook> getSimpleFormViewHookers(Class<T> simpleFormViewClass){
		List<SimpleFormViewHook> hookers = hookHandlerService.getHookers(SimpleFormViewHook.class);
		List<SimpleFormViewHook> res = new ArrayList<SimpleFormViewHook>();
		
		for(SimpleFormViewHook h : hookers){
			if(h.appliesTo(simpleFormViewClass)){
				res.add(h);
			}
		}
		
		return res;
	}
	
}
