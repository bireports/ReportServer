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
 
 
package net.datenwerke.rs.authenticator.client.login.hookers;

import java.util.List;

import net.datenwerke.gxtdto.client.dialog.error.SimpleErrorDialog;
import net.datenwerke.rs.authenticator.client.login.PostAuthenticateClientHook;
import net.datenwerke.security.client.login.AuthenticateResultDto;
import net.datenwerke.security.client.login.AuthenticateResultInfo;
import net.datenwerke.security.client.login.resultinfos.AccountExpiredAuthenticateResultInfo;
import net.datenwerke.security.client.login.resultinfos.AccountInhibitionAuthenticateResultInfo;
import net.datenwerke.security.client.security.passwordpolicy.locale.PasswordPolicyMessages;

public class AccountInhibitionPostAuthenticateClientHook implements PostAuthenticateClientHook {
	
	@Override
	public void authenticated(final AuthenticateResultDto authRes, final List<PostAuthenticateClientHook> chain) {

		boolean autoProceed = true;

		if(null != authRes.getInfo()){
			for(final AuthenticateResultInfo info : authRes.getInfo()){
				if(info instanceof AccountExpiredAuthenticateResultInfo){
					autoProceed = false;
					new SimpleErrorDialog(PasswordPolicyMessages.INSTANCE.accountInhibitedHeading(), PasswordPolicyMessages.INSTANCE.accountInhibitedMessage()){
						@Override
						protected void onHide() {
							skipToEnd(authRes, chain);
						}
					}.show();
				} else if(info instanceof AccountInhibitionAuthenticateResultInfo){
					autoProceed = false;
					new SimpleErrorDialog(PasswordPolicyMessages.INSTANCE.accountInhibitedHeading(), PasswordPolicyMessages.INSTANCE.accountInhibitedMessage()) {
						@Override
						protected void onHide() {
							skipToEnd(authRes, chain);
						};
					}.show();
				} 
			}
		}

		if(autoProceed)
			proceed(authRes, chain);
	}
	
	protected void proceed(AuthenticateResultDto authRes, List<PostAuthenticateClientHook> chain){
		if(chain.size() > 0)
			chain.remove(0).authenticated(authRes, chain);
	}
	
	protected void skipToEnd(AuthenticateResultDto authRes, List<PostAuthenticateClientHook> chain){
		if(chain.size() > 0){
			PostAuthenticateClientHook last = chain.get(chain.size()-1);
			chain.clear();
			last.authenticated(authRes, chain);
		}
	}

}
