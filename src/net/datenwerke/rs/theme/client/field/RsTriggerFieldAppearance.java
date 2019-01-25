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
 
 
package net.datenwerke.rs.theme.client.field;

import net.datenwerke.rs.theme.client.icon.BaseIcon;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.InputElement;
import com.google.gwt.safecss.shared.SafeStyles;
import com.google.gwt.safecss.shared.SafeStylesBuilder;
import com.google.gwt.safecss.shared.SafeStylesUtils;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.sencha.gxt.cell.core.client.form.FieldCell.FieldAppearanceOptions;
import com.sencha.gxt.core.client.GXT;
import com.sencha.gxt.core.client.dom.XElement;
import com.sencha.gxt.theme.neptune.client.FieldDetails;
import com.sencha.gxt.theme.neptune.client.base.field.Css3TriggerFieldAppearance;

public class RsTriggerFieldAppearance extends Css3TriggerFieldAppearance {

	protected final Css3TriggerFieldResources resources;
	protected final Css3TriggerFieldStyle style;
	protected boolean plainApperance;
	protected BaseIcon triggerIcon = BaseIcon.CHEVRON_DOWN;
	private boolean plainTriggerApperance;

	public RsTriggerFieldAppearance() {
		this(GWT.<Css3TriggerFieldResources>create(Css3TriggerFieldResources.class));
	}

	public RsTriggerFieldAppearance(Css3TriggerFieldResources resources) {
		super(resources);

		this.resources = resources;
		this.style = resources.style();
	}

	public void setPlainAppearance(boolean b) {
		plainApperance = true;
	}
	
	public void setTriggerPlainAppearance(boolean b) {
		plainTriggerApperance = true;
	}


	@Override
	public boolean triggerIsOrHasChild(XElement parent, Element target) {
		if (! parent.isOrHasChild(target)) 
			return false;

		XElement t = parent.selectNode(".rs-field-tf-t");

		return null != t && t.isOrHasChild(target);
	}

	@Override
	public void render(SafeHtmlBuilder sb, String value, FieldAppearanceOptions options) {
		int width = options.getWidth();
		boolean hideTrigger = options.isHideTrigger();

		if (width == -1) {
			width = 150;
		}

		SafeStylesBuilder inputStylesBuilder = new SafeStylesBuilder();
		if(!plainApperance)
			inputStylesBuilder.appendTrustedString("width:100%;");

		// outer div needed for widgets like comboBox that need the full width to set for listview width
		String className = plainApperance ? "rs-trigger-plain" : "rs-trigger";
		sb.appendHtmlConstant("<div class=\"" + className + "\" " + (plainApperance ? ">" : "style='width:" + width + "px;'>"));

		if (hideTrigger) {
			sb.appendHtmlConstant("<div class='" + style.wrap() + "'>");
			renderInput(sb, value, inputStylesBuilder.toSafeStyles(), options);
		} else {
			FieldDetails fieldDetails = getResources().theme().field();

			int rightPadding = fieldDetails.padding().right();
			sb.appendHtmlConstant("<div class='" + style.wrap() + "' " + (plainApperance ? "" : "style='padding-right:" + (getResources().triggerArrow().getWidth() + rightPadding) + "px;'") + ">");
			renderInput(sb, value, inputStylesBuilder.toSafeStyles(), options);


			int right = fieldDetails.borderWidth() + 1;
			int halfHeight = getResources().triggerArrow().getHeight() / 2;
			SafeStyles triggerStyle = SafeStylesUtils.fromTrustedString("margin-top:-" + halfHeight + "px; right:" + right + "px;");
			if(plainApperance)
				triggerStyle = SafeStylesUtils.fromTrustedString("margin-top:-" + halfHeight + "px;");
			sb.appendHtmlConstant("<div class='rs-field-tf-t " + getStyle().trigger() + (plainTriggerApperance ? " rs-field-tf-pt" : "") + 
					"' style='" + triggerStyle.asString() + "'>");
			sb.append(triggerIcon .toSafeHtml());
			sb.appendHtmlConstant("</div>");
		}

		sb.appendHtmlConstant("</div></div>");
	}

	protected void renderInput(SafeHtmlBuilder shb, String value, SafeStyles inputStyles, FieldAppearanceOptions options) {
		StringBuilder sb = new StringBuilder();
		sb.append("<input ");

		if (options.isDisabled()) {
			sb.append("disabled=true");
		}

		if (options.getName() != null) {
			sb.append("name='").append(SafeHtmlUtils.htmlEscape(options.getName())).append("' ");
		}

		if (options.isReadonly() || !options.isEditable()) {
			sb.append("readonly ");
		}

		if (inputStyles != null) {
			sb.append("style='").append(inputStyles.asString()).append("' ");
		}

		sb.append("class='").append(getStyle().field()).append(" ").append(getStyle().text());

		String placeholder = options.getEmptyText() != null ? " placeholder='" + SafeHtmlUtils.htmlEscape(options.getEmptyText()) + "' " : "";

		if ("".equals(value) && options.getEmptyText() != null) {
			sb.append(" ").append(getStyle().empty());
			if (GXT.isIE8() || GXT.isIE9()) {
				value = options.getEmptyText();
			}
		}

		if (!options.isEditable()) {
			sb.append(" ").append(getStyle().noedit());
		}

		sb.append("' ");
		sb.append(placeholder);

		sb.append("type='text' value='").append(SafeHtmlUtils.htmlEscape(value)).append("' ");

		sb.append("/>");

		if(plainApperance){
			sb.append("<span class=\"field-tf-t-plain\">").append(SafeHtmlUtils.htmlEscape(value)).append("</span>");
		}

		shb.append(SafeHtmlUtils.fromTrustedString(sb.toString()));
	}


	public void updatePlainValue(XElement parent) {
		InputElement el = getInputElement(parent).cast();
		String value = el.getValue();
		parent.selectNode(".field-tf-t-plain").setInnerHTML(SafeHtmlUtils.htmlEscape(value));
	}

	public void setTriggerIcon(BaseIcon icon) {
		this.triggerIcon = icon;
	}

	@Override
	public void onFocus(Element parent, boolean focus) {
		parent.<XElement>cast().setClassName(getResources().style().focus() + " .rs-focus", focus);
	}


}
