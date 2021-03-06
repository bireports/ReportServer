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
 
 
package net.datenwerke.gf.client.localization.hooker;

import java.util.Map;

import net.datenwerke.gf.client.localization.LocalizationDao;
import net.datenwerke.gxtdto.client.baseex.widget.form.DwComboBox;
import net.datenwerke.gxtdto.client.model.KeyValueBaseModel;
import net.datenwerke.gxtdto.client.utils.modelkeyprovider.BasicObjectModelKeyProvider;
import net.datenwerke.gxtdto.client.xtemplates.NullSafeFormatter;
import net.datenwerke.rs.theme.client.icon.BaseIcon;
import net.datenwerke.security.client.login.hooks.AuthenticatorWindowExtraOptionHook;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.http.client.UrlBuilder;
import com.google.gwt.safecss.shared.SafeStyles;
import com.google.gwt.safecss.shared.SafeStylesBuilder;
import com.google.gwt.safecss.shared.SafeStylesUtils;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.safehtml.shared.SafeUri;
import com.google.gwt.safehtml.shared.UriUtils;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HTML;
import com.google.inject.Inject;
import com.sencha.gxt.cell.core.client.form.ComboBoxCell.TriggerAction;
import com.sencha.gxt.core.client.IdentityValueProvider;
import com.sencha.gxt.core.client.XTemplates;
import com.sencha.gxt.core.client.XTemplates.FormatterFactories;
import com.sencha.gxt.core.client.XTemplates.FormatterFactory;
import com.sencha.gxt.core.client.XTemplates.FormatterFactoryMethod;
import com.sencha.gxt.data.shared.LabelProvider;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.widget.core.client.ListView;
import com.sencha.gxt.widget.core.client.container.Container;
import com.sencha.gxt.widget.core.client.container.HBoxLayoutContainer;
import com.sencha.gxt.widget.core.client.form.ComboBox;

public class AuthenticatorWindowExtraOptionHooker implements AuthenticatorWindowExtraOptionHook {

	@FormatterFactories(@FormatterFactory(factory=NullSafeFormatter.class,methods=@FormatterFactoryMethod(name="nullsafe")))
	public interface LanguageTemplate extends XTemplates {
		@XTemplate("<div class=\"language-selector-item x-combo-list-item\">" +
			    "<span class=\"language-selector-title\" style=\"{iconUrl}\">{entry.value}</span>" + 
			    "</div>")
	    public SafeHtml render(KeyValueBaseModel<String> entry, SafeStyles iconUrl); 
	}
	
	private final LocalizationDao localizationDao;

	@Inject
	public AuthenticatorWindowExtraOptionHooker(LocalizationDao localizationDao) {
		this.localizationDao = localizationDao;
	}
	
	@Override
	public ExtraOptionPosition getPosition() {
		return ExtraOptionPosition.TOP;
	}

	@Override
	public void configure(Container panel) {
		final ListStore<KeyValueBaseModel<String>> store = new ListStore<KeyValueBaseModel<String>>(new BasicObjectModelKeyProvider<KeyValueBaseModel<String>>());
		
		ListView<KeyValueBaseModel<String>, KeyValueBaseModel<String>> view = new ListView<KeyValueBaseModel<String>, KeyValueBaseModel<String>>(store, new IdentityValueProvider<KeyValueBaseModel<String>>());
		final LanguageTemplate template = GWT.create(LanguageTemplate.class);
		view.setCell(new AbstractCell<KeyValueBaseModel<String>>() {
			@Override
			public void render(com.google.gwt.cell.client.Cell.Context context,
					KeyValueBaseModel<String> value, SafeHtmlBuilder sb) {
				if(null != value.getKey()){
					String icon = value.getKey();
					if("ga".equals(icon)){
						icon = "ie";
					}else if("ca".equals(icon)){
						icon = "catalonia";
					}else if("en".equals(icon)){
						icon = "gb";
					}else if("et".equals(icon)){
						icon = "ee";
					}else if("sv".equals(icon)){
							icon = "se";
					}else if("cs".equals(icon)){
						icon = "cz";
					}else if("sl".equals(icon)){
						icon = "si";
					}else if("zh-CN".equals(icon)){
						icon = "cn";
					}else if("zh-TW".equals(icon)){
						icon = "cn";
					} 
					
					SafeUri iconUri = UriUtils.fromSafeConstant("resources/icons/flags/" + icon + ".png");
					SafeStyles bgStyle = SafeStylesUtils.forBackgroundImage(iconUri);
					
					SafeStylesBuilder builder = new SafeStylesBuilder();
					builder.append(bgStyle);
					builder.append(SafeStylesUtils.forPaddingLeft(21, Unit.PX));
					builder.append(SafeStylesUtils.forMarginLeft(3, Unit.PX));
					builder.appendTrustedString("background-repeat:no-repeat;");
					builder.appendTrustedString("background-position:0px 3px;");
					
					sb.append(template.render(value, builder.toSafeStyles()));
				}
			}
		});
		
		final DwComboBox<KeyValueBaseModel<String>> cb = new DwComboBox<KeyValueBaseModel<String>>(store, new LabelProvider<KeyValueBaseModel<String>>() {
			@Override
			public String getLabel(KeyValueBaseModel<String> item) {
				return item.getValue();
			}
		}, view);
		cb.setWidth(220);
		
		localizationDao.getLanguageSelectorConfiguration(new AsyncCallback<Map<String,String>>() {

			@Override
			public void onSuccess(Map<String, String> result) {
				KeyValueBaseModel<String> currentModel = null;
				String currentKey = localizationDao.getCurrentClientLocale();
				
				for(String s : result.keySet()){
					KeyValueBaseModel<String> model = new KeyValueBaseModel<String>(s, result.get(s));
					store.add(model);
					
					if(s.equals(currentKey))
						currentModel = model;
				}
				
				if(null != currentModel)
					cb.setValue(currentModel);
				else
					if(store.size() > 0)
						cb.setValue(store.get(0));
			}

			@Override
			public void onFailure(Throwable caught) {
			}
		});

		cb.addSelectionHandler(new SelectionHandler<KeyValueBaseModel<String>>() {
			@Override
			public void onSelection(SelectionEvent<KeyValueBaseModel<String>> event) {
				String lang = event.getSelectedItem().getKey();
				
				UrlBuilder url = Window.Location.createUrlBuilder().setParameter("locale", lang);
				Window.Location.replace(url.buildString());
			}
		});
		
		cb.setTypeAhead(false);
		cb.setForceSelection(true);
		cb.setEditable(false);
		cb.setAllowBlank(false);
		cb.setTriggerAction(TriggerAction.ALL);
		cb.setStore(store);
		
		HTML flagIcon = new HTML(BaseIcon.FLAG.toSafeHtml());
		flagIcon.addStyleName("rs-login-flag-i");
		flagIcon.setWidth("20px");
		HBoxLayoutContainer uContainer = new HBoxLayoutContainer();
		uContainer.addStyleName("rs-login-flag");
		uContainer.add(flagIcon);
		uContainer.add(cb);
		panel.add(uContainer);
	}

}
