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
 
 
package net.datenwerke.rs.theme.client.tree;

import net.datenwerke.rs.theme.client.icon.BaseIcon;
import net.datenwerke.rs.theme.client.icon.CssIconContainer;
import net.datenwerke.rs.theme.client.icon.CssIconImageResource;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.AbstractImagePrototype;
import com.sencha.gxt.core.client.GXT;
import com.sencha.gxt.core.client.dom.XDOM;
import com.sencha.gxt.core.client.dom.XElement;
import com.sencha.gxt.theme.neptune.client.base.tree.Css3TreeAppearance;
import com.sencha.gxt.widget.core.client.tree.Tree.CheckState;
import com.sencha.gxt.widget.core.client.tree.Tree.Joint;
import com.sencha.gxt.widget.core.client.tree.TreeStyle;
import com.sencha.gxt.widget.core.client.tree.TreeView.TreeViewRenderMode;

public class RsTreeAppearance extends Css3TreeAppearance {

	public static final String CSS_JOINT_NAME = "rs-t-j";
	public static final String CSS_ICON_NAME = "rs-t-i";
	public static final String CSS_TEXT_NAME = "rs-t-t";
	public static final String CSS_SELECTED_NAME = "rs-t-sel";
	public static final String CSS_OVER_NAME = "rs-t-over";

	private final Css3TreeResources resources;
	private final Css3TreeStyle style;

	public RsTreeAppearance() {
		this(GWT.<Css3TreeResources>create(Css3TreeResources.class));
	}

	public RsTreeAppearance(Css3TreeResources resources) {
		super(resources);

		this.resources = resources;
		this.style = resources.style();
	}

	@Override
	public void renderNode(SafeHtmlBuilder sb, String id, SafeHtml text, TreeStyle ts, ImageResource icon,
			boolean checkable, CheckState checked, Joint joint, int level, TreeViewRenderMode renderMode) {

		if (renderMode == TreeViewRenderMode.ALL || renderMode == TreeViewRenderMode.BUFFER_WRAP) {
			sb.appendHtmlConstant("<div id=\"" + SafeHtmlUtils.htmlEscape(id) + "\" class=\"" + style.node() + "\">");

			sb.appendHtmlConstant("<div class=\"" + style.element() + "\">");
		}

		if (renderMode == TreeViewRenderMode.ALL || renderMode == TreeViewRenderMode.BUFFER_BODY) {

			sb.appendHtmlConstant(getIndentMarkup(level));

			Element jointElement = null;
			switch (joint) {
			case COLLAPSED:
				jointElement = getImage(ts.getJointCloseIcon() == null ? resources.jointCollapsedIcon()
						: ts.getJointCloseIcon());
				break;
			case EXPANDED:
				jointElement = getImage(ts.getJointOpenIcon() == null ? resources.jointExpandedIcon() : ts.getJointOpenIcon());
				break;
			default:
				//empty
			}

			if (jointElement != null) {
				jointElement.addClassName(style.joint());
				jointElement.addClassName(CSS_JOINT_NAME);
			}

			sb.appendHtmlConstant(jointElement == null ? "<img src=\"" + GXT.getBlankImageUrl()
					+ "\" style=\"width: 14px\" class=\"" + style.joint() + " " + CSS_JOINT_NAME + "\" />" : jointElement.getString());

			// checkable
			if (checkable) {
				Element e = null;
				switch (checked) {
				case CHECKED:
					//e = getImage(resources.checked());
					e = BaseIcon.CHECK_SQUARE_O.toElement();
					break;
				case UNCHECKED:
					//					e = getImage(resources.unchecked());
					e = BaseIcon.SQUARE_O.toElement();
					break;
				case PARTIAL:
					e = getImage(resources.partialChecked());
					break;
				}
				e.addClassName("rs-tree-check");
				
				e.addClassName(style.check());
				sb.appendHtmlConstant(e.getString());
			} else {
				sb.appendHtmlConstant("<span class='" + style.check() + "'></span>");
			}

			if (icon != null) {
				Element e = getImage(icon);
				e.addClassName(style.icon());
				e.addClassName(CSS_ICON_NAME);
				sb.appendHtmlConstant(e.getString());
			} else {
				sb.appendHtmlConstant("<span class=\"" + style.icon() + " " + CSS_ICON_NAME + "\"></span>");
			}

			sb.appendHtmlConstant("<span class=\"" + style.text() + " " + CSS_TEXT_NAME +  "\">" + text.asString() + "</span>");
		}

		if (renderMode == TreeViewRenderMode.ALL || renderMode == TreeViewRenderMode.BUFFER_WRAP) {
			sb.appendHtmlConstant("</div>");
			sb.appendHtmlConstant("</div>");
		}

	}

	@Override
	public XElement onJointChange(XElement node, XElement jointElement, Joint joint, TreeStyle ts) {
		Element e;
		switch (joint) {
		case COLLAPSED:
			e = getImage(ts.getJointCloseIcon() == null ? resources.jointCollapsedIcon() : ts.getJointCloseIcon());
			break;
		case EXPANDED:
			e = getImage(ts.getJointOpenIcon() == null ? resources.jointExpandedIcon() : ts.getJointOpenIcon());
			break;
		default:
			e = XDOM.create("<img src=\"" + GXT.getBlankImageUrl() + "\" style=\"width: 14px\"  />");
		}

		e.addClassName(style.joint());
		e.addClassName(CSS_JOINT_NAME);
		e = (Element) node.getFirstChild().insertBefore(e, jointElement);
		jointElement.removeFromParent();
		return e.cast();
	}

	@Override
	public XElement onCheckChange(XElement node, XElement checkElement, boolean checkable, CheckState state) {
		Element e = null;
		if (checkable) {
			switch (state) {
			case CHECKED:
				//e = getImage(resources.checked());
				e = BaseIcon.CHECK_SQUARE_O.toElement();
				break;
			case UNCHECKED:
				//					e = getImage(resources.unchecked());
				e = BaseIcon.SQUARE_O.toElement();
				break;
			case PARTIAL:
				e = getImage(resources.partialChecked());
				break;
			}
			e.addClassName("rs-tree-check");
		} else {
			e = DOM.createSpan();
		}
		e.addClassName(style.check());
		e = (Element) node.getFirstChild().insertBefore(e, checkElement);
		checkElement.removeFromParent();
		return e.cast();
	}

	public String getIconStyle(){
		return style.icon() + " " + CSS_ICON_NAME;
	}

	@Override
	public ImageResource closeNodeIcon() {
		return new CssIconImageResource(BaseIcon.FOLDER_O);
	}

	@Override
	public ImageResource openNodeIcon() {
		return new CssIconImageResource(BaseIcon.FOLDER_OPEN_O);
	}

	@Override
	public void onSelect(XElement node, boolean select) {
		node.setClassName(style.selected() + " " + CSS_SELECTED_NAME,  select);
	}

	@Override
	public void onHover(XElement node, boolean over) {
		node.setClassName(style.over() + " " + CSS_OVER_NAME,  over);
	}

	private Element getImage(ImageResource ir) {
		if(ir instanceof CssIconContainer){
			Element e = ((CssIconContainer)ir).getCssElement();
			e.addClassName("fa-fw"); // fixed width 
			return e;
		}
		return AbstractImagePrototype.create(ir).createElement();
	}


}
