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
 
 
package net.datenwerke.rs.tsreportarea.server.tsreportarea;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.NoResultException;

import org.plutext.jaxb.svg11.Vkern;

import net.datenwerke.gxtdto.client.dtomanager.Dto;
import net.datenwerke.gxtdto.client.dtomanager.Dto2PosoMapper;
import net.datenwerke.gxtdto.client.servercommunication.exceptions.ExpectedException;
import net.datenwerke.gxtdto.client.servercommunication.exceptions.ServerCallFailedException;
import net.datenwerke.gxtdto.client.servercommunication.exceptions.ViolatedSecurityExceptionDto;
import net.datenwerke.gxtdto.server.dtomanager.DtoService;
import net.datenwerke.rs.core.client.reportmanager.dto.reports.ReportDto;
import net.datenwerke.rs.core.service.reportmanager.ReportService;
import net.datenwerke.rs.core.service.reportmanager.entities.reports.Report;
import net.datenwerke.rs.core.service.reportmanager.interfaces.ReportVariant;
import net.datenwerke.rs.teamspace.client.teamspace.dto.TeamSpaceDto;
import net.datenwerke.rs.teamspace.service.teamspace.TeamSpaceService;
import net.datenwerke.rs.teamspace.service.teamspace.entities.TeamSpace;
import net.datenwerke.rs.teamspace.service.teamspace.entities.TeamSpaceApp;
import net.datenwerke.rs.tsreportarea.client.tsreportarea.dto.AbstractTsDiskNodeDto;
import net.datenwerke.rs.tsreportarea.client.tsreportarea.dto.TsDiskFolderDto;
import net.datenwerke.rs.tsreportarea.client.tsreportarea.dto.TsDiskReportReferenceDto;
import net.datenwerke.rs.tsreportarea.client.tsreportarea.dto.container.TsDiskItemList;
import net.datenwerke.rs.tsreportarea.client.tsreportarea.rpc.TsDiskRpcService;
import net.datenwerke.rs.tsreportarea.service.tsreportarea.TsDiskModule;
import net.datenwerke.rs.tsreportarea.service.tsreportarea.TsDiskService;
import net.datenwerke.rs.tsreportarea.service.tsreportarea.TsDiskTeamSpaceAppDefinition;
import net.datenwerke.rs.tsreportarea.service.tsreportarea.entities.AbstractTsDiskNode;
import net.datenwerke.rs.tsreportarea.service.tsreportarea.entities.TsDiskFolder;
import net.datenwerke.rs.tsreportarea.service.tsreportarea.entities.TsDiskReportReference;
import net.datenwerke.rs.tsreportarea.service.tsreportarea.entities.TsDiskRoot;
import net.datenwerke.rs.tsreportarea.service.tsreportarea.locale.TsDiskMessages;
import net.datenwerke.security.server.SecuredRemoteServiceServlet;
import net.datenwerke.security.service.authenticator.AuthenticatorService;
import net.datenwerke.security.service.security.SecurityService;
import net.datenwerke.security.service.security.SecurityServiceSecuree;
import net.datenwerke.security.service.security.SecurityTarget;
import net.datenwerke.security.service.security.annotation.ArgumentVerification;
import net.datenwerke.security.service.security.annotation.RightsVerification;
import net.datenwerke.security.service.security.annotation.SecurityChecked;
import net.datenwerke.security.service.security.exceptions.ViolatedSecurityException;
import net.datenwerke.security.service.security.rights.Execute;
import net.datenwerke.security.service.security.rights.Read;
import net.datenwerke.security.service.usermanager.UserManagerService;
import net.datenwerke.security.service.usermanager.UserPropertiesService;
import net.datenwerke.security.service.usermanager.entities.User;
import net.datenwerke.treedb.client.treedb.dto.AbstractNodeDto;
import net.datenwerke.treedb.client.treedb.dto.EntireTreeDTO;
import net.datenwerke.treedb.service.treedb.AbstractNode;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import com.google.inject.name.Named;
import com.google.inject.persist.Transactional;

/**
 * 
 *
 */
@Singleton
public class TsDiskRpcServiceImpl extends SecuredRemoteServiceServlet
		implements TsDiskRpcService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6148231216021358758L;

	private final Provider<AuthenticatorService> authenticatorServiceProvider;
	private final DtoService dtoService;
	private final TsDiskService favoriteService;
	private final ReportService reportService;
	private final SecurityService securityService;
	private final TeamSpaceService teamSpaceService;
	private final UserManagerService userManagerService;

	private final UserPropertiesService userPropertiesService;
	
	@Inject
	public TsDiskRpcServiceImpl(
		Provider<AuthenticatorService> authenticatorServiceProvider,
		DtoService dtoService,
		TsDiskService favoriteService,
		ReportService reportService,
		SecurityService securityService,
		TeamSpaceService teamSpaceService,
		UserManagerService userManagerService, 
		UserPropertiesService userPropertiesService
		){
		
		/* store objects */
		this.authenticatorServiceProvider = authenticatorServiceProvider;
		this.dtoService = dtoService;
		this.favoriteService = favoriteService;
		this.reportService = reportService;
		this.securityService = securityService;
		this.teamSpaceService = teamSpaceService;
		this.userManagerService = userManagerService;
		this.userPropertiesService = userPropertiesService;
	}

	@Override
	@Transactional(rollbackOn={Exception.class})
	public List<AbstractNodeDto> getRoot(Dto state)
			throws ServerCallFailedException {
		
		TsDiskRoot root = getRootNode(state);

		List<AbstractNodeDto> list = new ArrayList<AbstractNodeDto>();
		list.add((AbstractNodeDto) dtoService.createDto(root));
		
		return list;
	}

	
	private TsDiskRoot getRootNode(Dto state) throws ServerCallFailedException{
		/* load teamspace */
		TeamSpaceDto teamSpaceDto = getTeamSpace(state);
		TeamSpace teamSpace = (TeamSpace) dtoService.loadPoso(teamSpaceDto);
		
		/* check rights */
		if(! teamSpaceService.mayAccess(teamSpace))
			throw new ViolatedSecurityExceptionDto("insufficient rights");
		
		try{
			return favoriteService.getRoot(teamSpace);
		} catch(NoResultException e){
		}
		
		return null;
	}
	

	@Override
	@Transactional(rollbackOn={Exception.class})
	public AbstractNodeDto loadNodeById(Long id, Dto state) throws ServerCallFailedException{
		AbstractTsDiskNode realNode = favoriteService.getNodeById(id);
		if(realNode == null)
			return null;
		
		TeamSpace teamSpace = favoriteService.getTeamSpaceFor(realNode);
		
		/* check rights */
		if(! teamSpaceService.mayAccess(teamSpace))
			throw new ViolatedSecurityExceptionDto("insufficient rights");
		
		return (AbstractNodeDto) dtoService.createDto(realNode);
	}
	
	@Override
	@Transactional(rollbackOn={Exception.class})
	public List<AbstractNodeDto> getChildren(AbstractNodeDto node, Dto state) throws ServerCallFailedException {
		/* load teamspace */
		TeamSpaceDto teamSpaceDto = getTeamSpace(state);
		TeamSpace teamSpace = (TeamSpace) dtoService.loadPoso(teamSpaceDto);
		
		/* check rights */
		if(! teamSpaceService.mayAccess(teamSpace))
			throw new ViolatedSecurityExceptionDto("insufficient rights");

		// TODO we actually need to check whether node is below the teamspace root
		
		List<AbstractNodeDto> list = new ArrayList<AbstractNodeDto>();

		for(AbstractTsDiskNode child : (Collection<AbstractTsDiskNode>) favoriteService.getNodeById(node.getId()).getChildrenSorted()){
			if(child instanceof TsDiskFolder){
				AbstractNodeDto dto = (AbstractNodeDto)  dtoService.createListDto(child);
				list.add(dto);
			}
		}
		
		return list;
	}
	
	@Override
	@Transactional(rollbackOn={Exception.class})
	public TsDiskItemList getItemsIn(TeamSpaceDto teamSpaceDto,
			TsDiskFolderDto folderDto) throws ServerCallFailedException {
		/* test teamSpace */
		TeamSpace teamSpace = (TeamSpace) dtoService.loadPoso(teamSpaceDto);
		
		/* check rights */
		if(! teamSpaceService.mayAccess(teamSpace))
			throw new ViolatedSecurityExceptionDto("insufficient rights");
		
		/* get folder */
		AbstractTsDiskNode folder = getFolder(teamSpace, folderDto);
		
		/* create result list */
		List<AbstractTsDiskNodeDto> items = new ArrayList<AbstractTsDiskNodeDto>();

		for(AbstractTsDiskNode child : folder.getChildrenSorted()){
			AbstractTsDiskNodeDto dto = (AbstractTsDiskNodeDto)  dtoService.createListDto(child);
			items.add(dto);
		}
		
		/* create path */
		List<AbstractTsDiskNodeDto> path = new ArrayList<AbstractTsDiskNodeDto>();
		AbstractTsDiskNode parent = folder.getParent();
		while(null != parent){
			path.add((AbstractTsDiskNodeDto) dtoService.createDto(parent));
			parent = parent.getParent();
		}
		Collections.reverse(path);
		
		/* create container */
		TsDiskItemList container = new TsDiskItemList();
		container.setItems(items);
		container.setPath(path);
		
		return container;
	}

	@Override
	@Transactional(rollbackOn={Exception.class})
	public AbstractNodeDto moveNodeAppend(AbstractNodeDto node,
			AbstractNodeDto reference, Dto state)
			throws ServerCallFailedException {
		/* get teamspace */
		TeamSpaceDto teamSpaceDto = getTeamSpace(state);
		TeamSpace teamSpace = (TeamSpace) dtoService.loadPoso(teamSpaceDto);
		
		/* check rights */
		if(! teamSpaceService.isUser(teamSpace))
			throw new ViolatedSecurityExceptionDto("insufficient rights");
		
		/* get objects */
		AbstractTsDiskNode realNode = favoriteService.getNodeById(node.getId());
		AbstractTsDiskNode parent = favoriteService.getNodeById(reference.getId());
		
		/* check rights */
		if(! teamSpace.equals(favoriteService.getTeamSpaceFor(realNode)) || ! teamSpace.equals(favoriteService.getTeamSpaceFor(parent)))
			throw new ViolatedSecurityExceptionDto("insufficient rights");
		
		/* move node */
		favoriteService.move(realNode, parent);
		
		/* merge parent */
		favoriteService.merge(parent);
		
		return (AbstractTsDiskNodeDto) dtoService.createDto(realNode);
	}
	
	@Override
	@Transactional(rollbackOn={Exception.class})
	public List<AbstractNodeDto> moveNodesAppend(List<AbstractNodeDto> nodes, @Named("reference")AbstractNodeDto reference, Dto state)  throws ServerCallFailedException  {
		/* get teamspace */
		TeamSpaceDto teamSpaceDto = getTeamSpace(state);
		TeamSpace teamSpace = (TeamSpace) dtoService.loadPoso(teamSpaceDto);
		
		/* check rights */
		if(! teamSpaceService.isUser(teamSpace))
			throw new ViolatedSecurityExceptionDto("insufficient rights");
		
		/* get real nodes */
		List<AbstractTsDiskNode> realNodes = new ArrayList();
		for(AbstractNodeDto nodeDto : nodes){
			AbstractTsDiskNode node = favoriteService.getNodeById(nodeDto.getId());

			/* check rights */
			if(! teamSpace.equals(favoriteService.getTeamSpaceFor(node)))
				throw new ViolatedSecurityExceptionDto("insufficient rights");

			realNodes.add(node);
		}

		/* move */
		AbstractTsDiskNode parent = favoriteService.getNodeById(reference.getId());
		List<AbstractNodeDto> resultList = new ArrayList<AbstractNodeDto>();

		/* check rights */
		if(! teamSpace.equals(favoriteService.getTeamSpaceFor(parent)))
			throw new ViolatedSecurityExceptionDto("insufficient rights");
		
		for(AbstractTsDiskNode node: realNodes){
			/* move node */
			AbstractTsDiskNode oldParent = (AbstractTsDiskNode) node.getParent();
			favoriteService.move(node, parent);

			resultList.add((AbstractNodeDto) dtoService.createDto(node));
		}

		/* merge parent */
		favoriteService.merge(parent);

		return resultList;
	}
	
	@Override
	@Transactional(rollbackOn={Exception.class})
	public void deleteNodes(List<AbstractNodeDto> nodes, Dto state)
			throws ServerCallFailedException {
		deleteNodes(nodes, state, false);
	} 
	
	@Override
	@Transactional(rollbackOn={Exception.class})
	public void deleteNodesWithForce(List<AbstractNodeDto> nodes, Dto state)
			throws ServerCallFailedException {
		deleteNodes(nodes, state, true);
	} 

	@Override
	@Transactional(rollbackOn={Exception.class})
	public void deleteNode(AbstractNodeDto node, Dto state)
			throws ServerCallFailedException {
		deleteNode(node, state, false);
	} 
	
	@Override
	@Transactional(rollbackOn={Exception.class})
	public void deleteNodeWithForce(AbstractNodeDto node, Dto state)
			throws ServerCallFailedException {
		deleteNode(node, state, true);
	} 
	
	protected Boolean deleteNodes(List<AbstractNodeDto> nodes, Dto state, boolean force) throws ServerCallFailedException{
		if(null == nodes || nodes.isEmpty())
			return true;
		
		for(AbstractNodeDto node : nodes)
			deleteNode(node, state, force);
		
		return true;
	}
	
	protected Boolean deleteNode(AbstractNodeDto node, Dto state, boolean force)
			throws ServerCallFailedException {
		TeamSpaceDto teamSpaceDto = getTeamSpace(state);
		TeamSpace teamSpace = (TeamSpace) dtoService.loadPoso(teamSpaceDto);

		/* check rights */
		if(! teamSpaceService.isUser(teamSpace))
			throw new ViolatedSecurityExceptionDto("insufficient rights");
		
		AbstractTsDiskNode uNode = favoriteService.getNodeById(node.getId());

		/* do not allow the deletion of root nodes */
		if(uNode.isRoot())
			throw new ExpectedException("Root cannot be deleted.");
		
		if(force)
			favoriteService.forceRemove(uNode);
		else
			favoriteService.remove(uNode);
		
		return true;
	}
	
	@Override
	@Transactional(rollbackOn={Exception.class})
	public TsDiskFolderDto createFolder(TeamSpaceDto teamSpaceDto,
			TsDiskFolderDto parentDto, TsDiskFolderDto dummy)
			throws ServerCallFailedException {
		/* test teamSpace */
		TeamSpace teamSpace = (TeamSpace) dtoService.loadPoso(teamSpaceDto);
		
		/* check rights */
		if(! teamSpaceService.isUser(teamSpace))
			throw new ViolatedSecurityExceptionDto("insufficient rights");
		
		/* get folder */
		AbstractTsDiskNode parent = getFolder(teamSpace, parentDto);
		
		/* create Folder */
		TsDiskFolder folder = new TsDiskFolder();
		folder.setName(dummy.getName());
		folder.setDescription(dummy.getDescription());
		parent.addChild(folder);
		
		favoriteService.persist(folder);
		favoriteService.merge(parent);
	
		return (TsDiskFolderDto) dtoService.createDto(folder);
	}
	
	private TeamSpaceDto getTeamSpace(Dto state) {
		if(null == state || ! (state instanceof TeamSpaceDto))
			throw new IllegalArgumentException("Illegal state");
		
		return (TeamSpaceDto) state;
	}

	private AbstractTsDiskNode getFolder(TeamSpace teamSpace,
			TsDiskFolderDto folderDto) {
		if(null == folderDto){
			try{
				return favoriteService.getRoot(teamSpace);
			} catch(NoResultException e){
				return null;
			}
		} else
			return (TsDiskFolder) dtoService.loadPoso(folderDto);
	}

	@Override
	@Transactional(rollbackOn={Exception.class})
	public List<TsDiskReportReferenceDto> importReports(
			TeamSpaceDto teamSpaceDto, TsDiskFolderDto folderDto, 
			List<ReportDto> reports, boolean copyReport, boolean isReference) throws ServerCallFailedException, ExpectedException {
		/* load team space */
		TeamSpace teamSpace = (TeamSpace) dtoService.loadPoso(teamSpaceDto);
		
		/* check rights */
		if(! teamSpaceService.isUser(teamSpace))
			throw new ViolatedSecurityExceptionDto("insufficient rights");
		
		/* get parent */
		AbstractTsDiskNode parent = getFolder(teamSpace, folderDto);
		
		/* prepare dto list */
		List<TsDiskReportReferenceDto> referenceDtos = new ArrayList<TsDiskReportReferenceDto>();
		
		/* import reports */
		for(ReportDto reportDto : reports){
			Report report = (Report) dtoService.loadPoso(reportDto);
			
			if( copyReport && report instanceof ReportVariant && ! teamSpaceService.isManager(teamSpace))
				throw new ViolatedSecurityExceptionDto("insufficient rights");
			if(!(report instanceof ReportVariant) && ! teamSpaceService.isManager(teamSpace))
				throw new ViolatedSecurityExceptionDto("insufficient rights");

			if(! copyReport && ! favoriteService.getTeamSpacesThatLinkTo(report).isEmpty() && !isReference)
				throw new ExpectedException(TsDiskMessages.INSTANCE.errorVariantNeedsToBeDuplicated());
			
			/* do import */
			TsDiskReportReference reference = favoriteService.importReport(report, parent, copyReport, isReference);
			
			/* create dto */
			referenceDtos.add((TsDiskReportReferenceDto) dtoService.createDto(reference));
		}
		
		/* merge */
		favoriteService.merge(parent);
		
		return referenceDtos;
	}
	

	

	@Override
	@Transactional(rollbackOn={Exception.class})
	public List<TeamSpaceDto> getTeamSpacesWithTsDiskApp()
			throws ServerCallFailedException {
		List<TeamSpaceDto> teamSpaces = new ArrayList<TeamSpaceDto>();
		
		/* get team spaces for current user and test if favorite app is installed */
		for(TeamSpace ts : teamSpaceService.getTeamSpaces()){
			TeamSpaceApp app = ts.getAppByType(TsDiskTeamSpaceAppDefinition.APP_ID);
			if(null != app && app.isInstalled())
				teamSpaces.add((TeamSpaceDto) dtoService.createDto(ts));
		}
		 
		return teamSpaces;
	}
	
	/**
	 * Returns a list of all reports the current user is allowed to see
	 */
	@Override
	@Transactional(rollbackOn={Exception.class})
	public List<ReportDto> getReportsInCatalog()
			throws ServerCallFailedException {
		List<ReportDto> reports = new ArrayList<ReportDto>();
		
		for(Report report : reportService.getAllReports()){
			/* we don't care about variants */
			if(report instanceof ReportVariant)
				continue;
			
			/* check rights */
			if(! securityService.checkRights(report, SecurityServiceSecuree.class, Read.class))
				continue;
			
			reports.add((ReportDto) dtoService.createListDto(report));
		}
		
		return reports;
	}
	
	@Override
	@Transactional(rollbackOn={Exception.class})
	public List<TsDiskReportReferenceDto> getReferencesInApp(
			TeamSpaceDto teamSpaceDto, TsDiskFolderDto folderDto) throws ServerCallFailedException {
		TeamSpace teamSpace = (TeamSpace) dtoService.loadPoso(teamSpaceDto);

		/* check rights */
		if(! teamSpaceService.mayAccess(teamSpace))
			throw new ViolatedSecurityExceptionDto("insufficient rights");
		
		List<TsDiskReportReference> references;
		
		if(null != folderDto){
			AbstractTsDiskNode folder = (AbstractTsDiskNode) dtoService.loadPoso(folderDto);
			
			if(!teamSpace.equals(favoriteService.getTeamSpaceFor(folder)))
				throw new ViolatedSecurityExceptionDto("insufficient rights");
			if(!(folder instanceof TsDiskFolder))
				throw new ServerCallFailedException("expected folder");
			
			references = favoriteService.getReferencesIn(folder);
		} else 
			references = favoriteService.getReferencesFor(teamSpace);
		
		List<TsDiskReportReferenceDto> dtos = new ArrayList<TsDiskReportReferenceDto>();
		for(TsDiskReportReference reference : references)
			dtos.add((TsDiskReportReferenceDto) dtoService.createListDto(reference));
		
		return dtos;
	}
	
	@Transactional(rollbackOn={Exception.class})
	protected AbstractNodeDto updateNode(AbstractNodeDto nodeDto) throws ServerCallFailedException {
		return updateNode(nodeDto, null);
	}
	
	@Override
	@Transactional(rollbackOn={Exception.class})
	public AbstractNodeDto updateNode(AbstractNodeDto nodeDto, Dto state) throws ServerCallFailedException {
		AbstractTsDiskNode node = (AbstractTsDiskNode) dtoService.loadPoso(nodeDto);
		TeamSpace teamSpace = favoriteService.getTeamSpaceFor(node);
		
		if(! teamSpaceService.isUser(teamSpace))
			throw new ViolatedSecurityException();
		
		/* merge node */
		dtoService.mergePoso(nodeDto, node);
		favoriteService.merge(node);
		
		return (AbstractNodeDto) dtoService.createDtoFullAccess(node);
	}

	@Override
	@Transactional(rollbackOn={Exception.class})
	public AbstractNodeDto updateNode(AbstractNodeDto nodeDto, boolean changeUnderlyingReport, String name, String description, Dto state)
			throws ServerCallFailedException {
		AbstractTsDiskNode node = (AbstractTsDiskNode) dtoService.loadPoso(nodeDto);
		TeamSpace teamSpace = favoriteService.getTeamSpaceFor(node);
		
		if(! teamSpaceService.isUser(teamSpace))
			throw new ViolatedSecurityException();
		
		/* merge node */
		node.setName(name);
		node.setDescription(description);
		favoriteService.merge(node);
		
		if(changeUnderlyingReport && node instanceof TsDiskReportReference){
			TsDiskReportReference reference = (TsDiskReportReference) node;
			if(reference.isHardlink() && null != reference.getReport() && ! reference.getReport().isWriteProtected()){
				Report report = reference.getReport();
				if(null != report){
					report.setName(reference.getName());
					report.setDescription(reference.getDescription());
					reportService.merge(report);
				}
			}
		}
		
		return (AbstractNodeDto) dtoService.createDtoFullAccess(node);
	}
	
	@Override
	public AbstractNodeDto insertNode(AbstractNodeDto objectTypeToInsert,
			AbstractNodeDto node, Dto state)
			throws ServerCallFailedException {
		throw new ServerCallFailedException("not implemented"); //$NON-NLS-1$
	}


	@Override
	public AbstractNodeDto refreshNode(AbstractNodeDto node, Dto state)
			throws ServerCallFailedException {
		throw new ServerCallFailedException("not implemented"); //$NON-NLS-1$
	}

	@Override
	public EntireTreeDTO loadAll(Dto state) throws ServerCallFailedException {
		EntireTreeDTO treeDto = new EntireTreeDTO();
		TsDiskRoot root = getRootNode(state);
		
		List<AbstractNodeDto> list = new ArrayList<AbstractNodeDto>();
		for(AbstractNodeDto rootDto : getRoot(state)){
			treeDto.addRoot(rootDto);
			addChildren(treeDto, rootDto, root);
		}
	
		return treeDto;
	}
	
	private void addChildren(EntireTreeDTO treeDto, AbstractNodeDto parentDto, AbstractNode parent) {
		for(AbstractNode child : (List<AbstractTsDiskNode>) parent.getChildrenSorted()){
			AbstractNodeDto childDto = (AbstractNodeDto) dtoService.createListDto(child);
			treeDto.addChild(parentDto, childDto);
			addChildren(treeDto, childDto, child);
		}
	}
	

	private void addChildren(EntireTreeDTO treeDto, AbstractNodeDto parentDto,
			AbstractNode parent, Set<Class<?>> wlFilterList,
			Set<Class<?>> blFilterList) {
		for(AbstractNode child : (List<AbstractTsDiskNode>) parent.getChildrenSorted()){
			boolean found = true;
			if(null != wlFilterList){
				found = false;
				for(Class<?> filter : wlFilterList){
					if(filter.isAssignableFrom(child.getClass())){
						found = true;
						break;
					}
				}
			}
			if(! found)
				continue;
			if(null != blFilterList){
				for(Class<?> filter : blFilterList){
					if(filter.isAssignableFrom(child.getClass())){
						found = false;
						break;
					}
				}
				if(! found)
					continue;
			}
			
			AbstractNodeDto childDto = (AbstractNodeDto) dtoService.createListDto(child);
			treeDto.addChild(parentDto, childDto);
			addChildren(treeDto, childDto, child, wlFilterList, blFilterList);
		}
	}

	@Override
	public AbstractNodeDto loadFullViewNode(AbstractNodeDto nodeDto, Dto state) throws ServerCallFailedException {
		AbstractTsDiskNode node = (AbstractTsDiskNode) dtoService.loadPoso(nodeDto);
		
		TeamSpace teamSpace = favoriteService.getTeamSpaceFor(node);
		if(! teamSpaceService.mayAccess(teamSpace))
			throw new ViolatedSecurityExceptionDto();
		
		return (AbstractNodeDto) dtoService.createDto(node);
	}

	@Override
	@Transactional(rollbackOn={Exception.class})
	public AbstractNodeDto moveNodeInsert(AbstractNodeDto node,
			AbstractNodeDto reference, int index, Dto state)
			throws ServerCallFailedException {
		throw new ServerCallFailedException("not implemented"); //$NON-NLS-1$
	}
	

	@Override
	public AbstractNodeDto duplicateNode(AbstractNodeDto toDuplicate,
			Dto state) throws ServerCallFailedException {
		throw new ServerCallFailedException("not implemented"); //$NON-NLS-1$
	}

	@Override
	@Transactional(rollbackOn={Exception.class})
	public void sendUserViewChangedNotice(String viewId)
			throws ServerCallFailedException {
		User currentUser = authenticatorServiceProvider.get().getCurrentUser();
		
		if(null != viewId && (viewId.length() > 64 || ! viewId.matches("^[a-zA-Z0-9]*$")))
			throw new ServerCallFailedException("unsuppoted id");
		
		if(null != viewId)
			userPropertiesService.setPropertyValue(currentUser, TsDiskModule.USER_PROPERTY_VIEW_VIEW_ID, viewId);
		
		userManagerService.merge(currentUser);
	}

	@SecurityChecked(
		argumentVerification = {
			@ArgumentVerification(
				name = "report",
				isDto = true,
				verify = @RightsVerification(rights=Read.class)
			)
		}
	)
	@Override
	@Transactional(rollbackOn={Exception.class})
	public List<TeamSpaceDto> getTeamSpacesWithReferenceTo(@Named("report") ReportDto reportDto)
			throws ServerCallFailedException {
		Report report = (Report) dtoService.loadPoso(reportDto);
		
		Set<TeamSpace> teamSpaces = favoriteService.getTeamSpacesThatLinkTo(report);
		
		List<TeamSpaceDto> resultList = new ArrayList<TeamSpaceDto>();
		for(TeamSpace ts : teamSpaces)
			resultList.add((TeamSpaceDto) dtoService.createListDto(ts));
		
		return resultList;
	}

	@Override
	public EntireTreeDTO loadAll(Dto state, Collection<Dto2PosoMapper> wlFilters, Collection<Dto2PosoMapper> blFilters)
			throws ServerCallFailedException {
		boolean filter = (null != wlFilters && ! wlFilters.isEmpty()) || (null != blFilters && ! blFilters.isEmpty());
		if(! filter)
			return loadAll(state);
		
		
		
		Set<Class<?>> wlFilterList = new HashSet<Class<?>>();
		Set<Class<?>> blFilterList = new HashSet<Class<?>>();
		if(null == wlFilters)
			wlFilters = new HashSet<Dto2PosoMapper>();
		if(null == blFilters)
			blFilters = new HashSet<Dto2PosoMapper>();

		for(Dto2PosoMapper filterDtoMapper : wlFilters)
			wlFilterList.add(dtoService.getPosoFromDtoMapper(filterDtoMapper));
		for(Dto2PosoMapper filterDtoMapper : blFilters)
			blFilterList.add(dtoService.getPosoFromDtoMapper(filterDtoMapper));
		
		/* load tree */ 
		EntireTreeDTO treeDto = new EntireTreeDTO();
		TsDiskRoot root = getRootNode(state);
		
		List<AbstractNodeDto> list = new ArrayList<AbstractNodeDto>();
		for(AbstractNodeDto rootDto : getRoot(state)){
			treeDto.addRoot(rootDto);
			addChildren(treeDto, rootDto, root, wlFilterList, blFilterList);
		}
	
		return treeDto;
	}

	

	@Override
	@Transactional(rollbackOn={Exception.class})
	public TsDiskReportReferenceDto createAndImportVariant(TeamSpaceDto teamSpaceDto, TsDiskFolderDto folderDto,
			@Named("report") ReportDto reportDto, String executeToken, String name, String desc) throws ServerCallFailedException {
		/* create report first */
		Report referenceReport = (Report) dtoService.loadPoso(reportDto);
		
		/* has permissions */
		if(! securityService.checkRights(referenceReport, Execute.class))
			throw new ViolatedSecurityException();
		
		/* create variant */
		Report adjustedReport = (Report) dtoService.createUnmanagedPoso(reportDto);
		Report variant = referenceReport.createNewVariant(adjustedReport);

		reportService.prepareVariantForStorage((ReportVariant) variant, executeToken);
		variant.setName(name);
		variant.setDescription(desc);
		
		/* persist variant */
		reportService.persist(variant);
		
		/* import into teamspace */
		
		/* load team space */
		TeamSpace teamSpace = (TeamSpace) dtoService.loadPoso(teamSpaceDto);
		
		/* check rights */
		if(! teamSpaceService.isUser(teamSpace))
			throw new ViolatedSecurityExceptionDto("insufficient rights");
		
		/* get parent */
		AbstractTsDiskNode parent = getFolder(teamSpace, folderDto);
				
		/* do import */
		TsDiskReportReference reference = favoriteService.importReport(variant, parent, false, false);
		
		/* create and return it */
		return (TsDiskReportReferenceDto) dtoService.createDto(reference);
	}

	@Override
	@Transactional(rollbackOn={Exception.class})
	public TsDiskReportReferenceDto updateReferenceAndReport(TsDiskReportReferenceDto referenceDto, @Named("report") ReportDto reportDto, String executeToken, String name, String description) throws ServerCallFailedException {
		/* load reference and check teamspace */
		AbstractTsDiskNode reference = (AbstractTsDiskNode) dtoService.loadPoso(referenceDto);
		TeamSpace teamSpace = favoriteService.getTeamSpaceFor(reference);
		
		if(! teamSpaceService.isUser(teamSpace))
			throw new ViolatedSecurityException();
		
		/* load report */
		Report report = (Report) dtoService.loadPoso(reportDto);
		if(! (report instanceof ReportVariant))
			throw new IllegalArgumentException();
		
		/* has permissions */
		if(! securityService.checkRights(report.getParent(), Execute.class))
			throw new ViolatedSecurityException();
		
		/* update report */
		dtoService.mergePoso(reportDto, report);
		reportService.prepareVariantForStorage((ReportVariant) report, executeToken);
		report.setName(name);
		report.setDescription(description);

		/* merge variant */
		reportService.merge(report);
		
		/* merge reference */
		return (TsDiskReportReferenceDto) updateNode(referenceDto);
	}

	@Override
	public AbstractNodeDto setFlag(AbstractNodeDto node, long flag, boolean set)
			throws ServerCallFailedException {
		return null;
	}

	@Override
	public String[][] loadAllAsFto(Dto state) throws ServerCallFailedException {
		return null;
	}



	@Override
	public String[][] getChildrenAsFto(AbstractNodeDto node, Dto state) throws ServerCallFailedException {
		return null;
	}



	
}
