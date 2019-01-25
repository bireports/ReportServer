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
 
 
package net.datenwerke.rs.teamspace.client.teamspace.hookers;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.sencha.gxt.core.client.IdentityValueProvider;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.widget.core.client.button.SplitButton;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer.VerticalLayoutData;
import com.sencha.gxt.widget.core.client.event.CompleteEditEvent;
import com.sencha.gxt.widget.core.client.event.CompleteEditEvent.CompleteEditHandler;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.form.SimpleComboBox;
import com.sencha.gxt.widget.core.client.grid.ColumnConfig;
import com.sencha.gxt.widget.core.client.grid.ColumnModel;
import com.sencha.gxt.widget.core.client.grid.Grid;
import com.sencha.gxt.widget.core.client.grid.GridSelectionModel;
import com.sencha.gxt.widget.core.client.grid.editing.GridEditing;
import com.sencha.gxt.widget.core.client.grid.editing.GridInlineEditing;
import com.sencha.gxt.widget.core.client.menu.Menu;
import com.sencha.gxt.widget.core.client.menu.MenuItem;
import com.sencha.gxt.widget.core.client.toolbar.ToolBar;

import net.datenwerke.gxtdto.client.baseex.widget.btn.DwSplitButton;
import net.datenwerke.gxtdto.client.baseex.widget.btn.DwTextButton;
import net.datenwerke.gxtdto.client.baseex.widget.menu.DwMenu;
import net.datenwerke.gxtdto.client.baseex.widget.menu.DwMenuItem;
import net.datenwerke.gxtdto.client.forms.selection.SelectionMode;
import net.datenwerke.gxtdto.client.forms.selection.SelectionPopup;
import net.datenwerke.gxtdto.client.servercommunication.callback.NotamCallback;
import net.datenwerke.gxtdto.client.utilityservices.grid.GridHelperService;
import net.datenwerke.gxtdto.client.utilityservices.grid.GridHelperService.CCContainer;
import net.datenwerke.gxtdto.client.utilityservices.submittracker.SubmitTrackerToken;
import net.datenwerke.gxtdto.client.utilityservices.toolbar.DwToolBar;
import net.datenwerke.gxtdto.client.utilityservices.toolbar.ToolbarService;
import net.datenwerke.rs.teamspace.client.teamspace.TeamSpaceDao;
import net.datenwerke.rs.teamspace.client.teamspace.TeamSpaceUIService;
import net.datenwerke.rs.teamspace.client.teamspace.dto.StrippedDownTeamSpaceMemberDto;
import net.datenwerke.rs.teamspace.client.teamspace.dto.TeamSpaceDto;
import net.datenwerke.rs.teamspace.client.teamspace.dto.TeamSpaceMemberDto;
import net.datenwerke.rs.teamspace.client.teamspace.dto.TeamSpaceRoleDto;
import net.datenwerke.rs.teamspace.client.teamspace.dto.pa.StrippedDownTeamSpaceMemberDtoPa;
import net.datenwerke.rs.teamspace.client.teamspace.hooks.TeamSpaceEditDialogHookImpl;
import net.datenwerke.rs.teamspace.client.teamspace.locale.TeamSpaceMessages;
import net.datenwerke.rs.theme.client.icon.BaseIcon;
import net.datenwerke.security.client.usermanager.dto.ie.StrippedDownUser;
import net.datenwerke.security.client.usermanager.dto.ie.StrippedDownUserPA;
import net.datenwerke.security.ext.client.usermanager.UserManagerUIService;

/**
 * 
 *
 */
public class EditTeamSpaceMembersHooker extends
		TeamSpaceEditDialogHookImpl {

	private static StrippedDownUserPA userPa = GWT.create(StrippedDownUserPA.class);
	private static StrippedDownTeamSpaceMemberDtoPa memberPa = GWT.create(StrippedDownTeamSpaceMemberDtoPa.class);
	
	private final GridHelperService gridHelperService;
	private final ToolbarService toolbarService;
	private final UserManagerUIService userManagerService;
	private final TeamSpaceDao tsDao;
	private final TeamSpaceUIService teamSpaceService;
	
	private ListStore<StrippedDownUser> allUsersStore;
	private ListStore<StrippedDownTeamSpaceMemberDto> memberStore;
	private boolean changed = false;
	
	@Inject
	public EditTeamSpaceMembersHooker(
		GridHelperService gridHelperService,
		ToolbarService toolbarService,
		UserManagerUIService userManagerService,
		TeamSpaceDao tsDao,
		TeamSpaceUIService teamSpaceService
		){
		
		/* store objects */
		this.gridHelperService = gridHelperService;
		this.toolbarService = toolbarService;
		this.userManagerService = userManagerService;
		this.tsDao = tsDao;
		this.teamSpaceService = teamSpaceService;
	}
	
	@Override
	public ImageResource getIcon() {
		return BaseIcon.GROUP.toImageResource(1);
	}

	@Override
	public String getName() {
		return TeamSpaceMessages.INSTANCE.editTeamSpaceMembersName();
	}

	@Override
	public Widget getCard() {
		Widget editGrid = createEditComponent();
		
		return editGrid;
	}
	
	@Override
	public void setCurrentSpace(TeamSpaceDto teamSpace) {
		super.setCurrentSpace(teamSpace);
		
		/* create store */
		allUsersStore =  new ListStore<StrippedDownUser>(userPa.dtoId());
		memberStore = new ListStore<StrippedDownTeamSpaceMemberDto>(memberPa.dtoId());
		memberStore.setAutoCommit(true);
		
		/* fill member store */
		if(null != teamSpace.getOwner())
			memberStore.add(StrippedDownTeamSpaceMemberDto.createForOwner(teamSpace.getOwner()));
		
		for(TeamSpaceMemberDto member : teamSpace.getMembers())
			memberStore.add(StrippedDownTeamSpaceMemberDto.createFrom(member));
	}

	private Widget createEditComponent() {
		VerticalLayoutContainer nsContainer = new VerticalLayoutContainer();
		
		/* create toolbar */
		ToolBar toolbar = new DwToolBar();
		nsContainer.add(toolbar, new VerticalLayoutData(1,-1));
		
		/* create grid */
		final Grid<StrippedDownTeamSpaceMemberDto> userGrid = createMembersGrid();
		nsContainer.add(userGrid, new VerticalLayoutData(1,-1));
		
		
		/* add member */
		DwTextButton addMemberBtn = toolbarService.createSmallButtonLeft(TeamSpaceMessages.INSTANCE.editTeamSpaceMembersAddMember(), BaseIcon.USER_ADD);
		addMemberBtn.addSelectHandler(new SelectHandler() {
			
			@Override
			public void onSelect(SelectEvent event) {
				Map<ValueProvider<StrippedDownUser,String>, String> displayProperties = new LinkedHashMap<ValueProvider<StrippedDownUser,String>, String>();
				displayProperties.put(userPa.firstname(), TeamSpaceMessages.INSTANCE.firstname());
				displayProperties.put(userPa.lastname(), TeamSpaceMessages.INSTANCE.lastname());
				displayProperties.put(userPa.ou(), TeamSpaceMessages.INSTANCE.ou());

				// TODO: GXT3
				SelectionPopup<StrippedDownUser> selectionPanel = new SelectionPopup<StrippedDownUser>(allUsersStore, displayProperties){
					@Override
					protected void itemsSelected(
							List<StrippedDownUser> selectedItems) {
						/* remove members */
						for(StrippedDownTeamSpaceMemberDto model: memberStore.getAll()){
							boolean found = false;
							for(StrippedDownUser user : selectedItems){
								if(user.getId().equals(model.getUserId())){
									found = true;
									break;
								}
							}
							if(! found)
								memberStore.remove(model);
						}
						
						/* add members */
						List<StrippedDownTeamSpaceMemberDto> members = new ArrayList<StrippedDownTeamSpaceMemberDto>();
						for(StrippedDownUser user : selectedItems){
							boolean found = false;
							for(StrippedDownTeamSpaceMemberDto model: memberStore.getAll()){
								if(user.getId().equals(model.getUserId())){
									found = true;
									break;
								}
							}
								
							if(! found)
								members.add(StrippedDownTeamSpaceMemberDto.createFrom(user));
						}
							
						
						memberStore.addAll(members);
						
						/* set changed */
						changed = true;
					}
				};
				selectionPanel.setLoader(userManagerService.getStrippedUserLoader());
				selectionPanel.loadData();
				selectionPanel.setSelectionMode(SelectionMode.MULTI);
				List<StrippedDownUser> selectedUsers = new ArrayList<StrippedDownUser>();
				for(StrippedDownTeamSpaceMemberDto member : memberStore.getAll())
					selectedUsers.add(member.getStrippedUser());
				selectionPanel.setSelectedItems(selectedUsers);
				selectionPanel.show();	
			}
		});
		
		toolbar.add(addMemberBtn);
		
		/* remove members */
		SplitButton removeButton = toolbarService.configureButton(new DwSplitButton(), TeamSpaceMessages.INSTANCE.editTeamSpaceMembersRemoveMember(), BaseIcon.DELETE);
		toolbar.add(removeButton);
		
		removeButton.addSelectHandler(new SelectHandler() {
			@Override
			public void onSelect(SelectEvent event) {
				List<StrippedDownTeamSpaceMemberDto> items = userGrid.getSelectionModel().getSelectedItems();
				if(null != items && ! items.isEmpty())
					changed = true;
				for(StrippedDownTeamSpaceMemberDto model : items)
					memberStore.remove(model);
			}
		});
		
		/* remove all members */
		MenuItem removeAllMenuItem =  new DwMenuItem(TeamSpaceMessages.INSTANCE.editTeamSpaceMembersRemoveAllMembers(), BaseIcon.DELETE);
		removeAllMenuItem.addSelectionHandler(new SelectionHandler() {
			@Override
			public void onSelection(SelectionEvent event) {
				changed = true;
				memberStore.clear();
			}
		});
		
		Menu removeMenu = new DwMenu();
		removeMenu.add(removeAllMenuItem);
		removeButton.setMenu(removeMenu);
		
		nsContainer.setHeight(280);
		
		return nsContainer;
	}

	private Grid<StrippedDownTeamSpaceMemberDto> createMembersGrid() {
		/* create columns */
		List<ColumnConfig<StrippedDownTeamSpaceMemberDto,?>> configs = new ArrayList<ColumnConfig<StrippedDownTeamSpaceMemberDto,?>>();   
		
		
		/* add user column */
		ColumnConfig<StrippedDownTeamSpaceMemberDto,StrippedDownTeamSpaceMemberDto> ccOwner = new ColumnConfig<StrippedDownTeamSpaceMemberDto,StrippedDownTeamSpaceMemberDto>(new IdentityValueProvider<StrippedDownTeamSpaceMemberDto>(), 30);
		ccOwner.setCell(new AbstractCell<StrippedDownTeamSpaceMemberDto>() {
			@Override
			public void render(com.google.gwt.cell.client.Cell.Context context,
					StrippedDownTeamSpaceMemberDto value, SafeHtmlBuilder sb) {
				if(value.isOwner())
					 sb.append(BaseIcon.USER_MD.toSafeHtml());
			}
		});
		configs.add(ccOwner);
		
		ColumnConfig<StrippedDownTeamSpaceMemberDto,String> ccFirst = new ColumnConfig<StrippedDownTeamSpaceMemberDto,String>(memberPa.firstname(), 150,TeamSpaceMessages.INSTANCE.editTeamSpaceMembersGridFirstnameColumn());
		configs.add(ccFirst);
		ColumnConfig<StrippedDownTeamSpaceMemberDto,String> ccLast = new ColumnConfig<StrippedDownTeamSpaceMemberDto,String>(memberPa.lastname(), 150, TeamSpaceMessages.INSTANCE.editTeamSpaceMembersGridLastnameColumn());
		configs.add(ccLast);
		
		final CCContainer<StrippedDownTeamSpaceMemberDto, TeamSpaceRoleDto> cccRole = gridHelperService.createComboBoxColumnConfig(TeamSpaceRoleDto.values(), memberPa.role(), false, null, 150);
		ColumnConfig<StrippedDownTeamSpaceMemberDto,TeamSpaceRoleDto> roleConfig = cccRole.getConfig();
		roleConfig.setHeader( TeamSpaceMessages.INSTANCE.editTeamSpaceMembersGridRoleColumn());
		configs.add(roleConfig);
		
		/* create gride */
		final Grid<StrippedDownTeamSpaceMemberDto> grid = new Grid<StrippedDownTeamSpaceMemberDto>(memberStore, new ColumnModel<StrippedDownTeamSpaceMemberDto>(configs));
		
		/* create editing */
		final GridEditing<StrippedDownTeamSpaceMemberDto> editing = new GridInlineEditing<StrippedDownTeamSpaceMemberDto>(grid);
		
		
		if (teamSpaceService.isAdmin(teamSpace)) {
			editing.addEditor(cccRole.getConfig(), cccRole.getConverter(), cccRole.getCombo());
		} else {
			SimpleComboBox<Object> rolesComboBox = cccRole.getCombo();
			// only ADMIN users should see the ADMIN role in the combobox
			rolesComboBox.remove(TeamSpaceRoleDto.ADMIN);
			editing.addEditor(cccRole.getConfig(), cccRole.getConverter(), rolesComboBox);
		}
		
		editing.addCompleteEditHandler(new CompleteEditHandler<StrippedDownTeamSpaceMemberDto>() {
			@Override
			public void onCompleteEdit(
					CompleteEditEvent<StrippedDownTeamSpaceMemberDto> event) {
				changed = true;
			}
		});
		
		grid.setSelectionModel(new GridSelectionModel<StrippedDownTeamSpaceMemberDto>());
		grid.getView().setShowDirtyCells(false);
		grid.getSelectionModel().setSelectionMode(com.sencha.gxt.core.client.Style.SelectionMode.MULTI);

		return grid;
	}

	@Override
	public int getHeight() {
		return 460;
	}

	@Override
	public boolean applies(TeamSpaceDto teamSpace) {
		return true;
	}
	
	@Override
	public void submitPressed(final SubmitTrackerToken submitTrackerToken) {
		if(! changed){
			submitTrackerToken.setCompleted();
			return;
		}
		
		/* remove owner from member store */
		for(StrippedDownTeamSpaceMemberDto member : memberStore.getAll()){
			if(member.isOwner()){
				memberStore.remove(member);
				break;
			}
		}
		
		tsDao.setMembers(teamSpace, new ArrayList<StrippedDownTeamSpaceMemberDto>(memberStore.getAll()), new NotamCallback<TeamSpaceDto>(TeamSpaceMessages.INSTANCE.editTeamSpaceMembersSubmitted()){
			@Override
			public void doOnSuccess(TeamSpaceDto result) {
				submitTrackerToken.setCompleted();
			}
			@Override
			public void doOnFailure(Throwable caught) {
				submitTrackerToken.failure(caught);
			}
		});
	}


}
