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
 
 
package net.datenwerke.rs.authenticator.client.login.sessiontimeout;

import java.util.Date;

import net.datenwerke.gf.client.login.LoginService;
import net.datenwerke.gxtdto.client.baseex.widget.DwWindow;
import net.datenwerke.gxtdto.client.locale.BaseMessages;
import net.datenwerke.rs.authenticator.client.login.locale.LoginMessages;
import net.datenwerke.security.client.login.AuthenticateCallback;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Label;
import com.google.inject.Inject;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.widget.core.client.ProgressBar;
import com.sencha.gxt.widget.core.client.button.ButtonBar;
import net.datenwerke.gxtdto.client.baseex.widget.btn.DwTextButton;
import com.sencha.gxt.widget.core.client.container.MarginData;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer.VerticalLayoutData;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;

public class SessionTimeoutWarningDialog extends DwWindow {

	private ProgressBar progressBar;
	private Timer timer;

	@Inject
	private static LoginService loginService;

	private DwTextButton refreshSessionButton;

	public SessionTimeoutWarningDialog(final int timeout) {
		setModal(true);
		setSize(330, 180);
		setHeadingText(LoginMessages.INSTANCE.expirationWarningTitle());

		progressBar = new ProgressBar();

		timer = new Timer() {
			int i = 0;
			@Override
			public void run() {
				double percentage = (double)i / timeout;
				i++;

				if(percentage > 1){
					progressBar.updateText(LoginMessages.INSTANCE.loggingOut());
					this.cancel();
					forceLogout();
				}else{
					String s = DateTimeFormat.getFormat("mm:ss").format(new Date((long)(1000 * (timeout - percentage * timeout))));
					progressBar.updateProgress(percentage, s);
				}
			}
		};

		timer.scheduleRepeating(1000);

		VerticalLayoutContainer hlc = new VerticalLayoutContainer();
		add(hlc, new MarginData(10));
		
		Label text = new Label(LoginMessages.INSTANCE.expirationWarningMessage());
		hlc.add(text);
		hlc.add(new Label(" "));
		hlc.add(progressBar, new VerticalLayoutData(1,-1d, new Margins(5, 5, 5, 5)));

		ButtonBar bb = getButtonBar();
		refreshSessionButton = new DwTextButton(BaseMessages.INSTANCE.cancel());
		refreshSessionButton.addSelectHandler(new SelectHandler() {
			@Override
			public void onSelect(SelectEvent event) {
				keepSessionAlive();
				timer.cancel();
				hide();
			}
		});
		bb.add(refreshSessionButton);

		forceLayout();
	}

	private void forceLogout(){
		refreshSessionButton.disable();
		loginService.logoff();
	}

	private void keepSessionAlive(){
		loginService.tryReAuthenticate(new AuthenticateCallback() {
			@Override
			public void execute(boolean authenticateSucceeded) {
				
			}
		});
	}

}
