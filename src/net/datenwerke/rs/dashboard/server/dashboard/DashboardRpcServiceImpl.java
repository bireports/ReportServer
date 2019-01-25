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
 
 
package net.datenwerke.rs.dashboard.server.dashboard;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.inject.Singleton;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Provider;
import com.google.inject.name.Named;
import com.google.inject.persist.Transactional;

import net.datenwerke.gxtdto.client.dtomanager.DtoView;
import net.datenwerke.gxtdto.client.servercommunication.exceptions.ExpectedException;
import net.datenwerke.gxtdto.client.servercommunication.exceptions.ServerCallFailedException;
import net.datenwerke.gxtdto.server.dtomanager.DtoService;
import net.datenwerke.rs.core.client.parameters.dto.ParameterInstanceDto;
import net.datenwerke.rs.core.service.parameters.entities.ParameterDefinition;
import net.datenwerke.rs.core.service.parameters.entities.ParameterInstance;
import net.datenwerke.rs.core.service.reportmanager.entities.reports.Report;
import net.datenwerke.rs.dashboard.client.dashboard.dto.DadgetDto;
import net.datenwerke.rs.dashboard.client.dashboard.dto.DashboardContainerDto;
import net.datenwerke.rs.dashboard.client.dashboard.dto.DashboardDto;
import net.datenwerke.rs.dashboard.client.dashboard.dto.DashboardNodeDto;
import net.datenwerke.rs.dashboard.client.dashboard.dto.DashboardReferenceDto;
import net.datenwerke.rs.dashboard.client.dashboard.dto.FavoriteListDto;
import net.datenwerke.rs.dashboard.client.dashboard.dto.ReportDadgetDto;
import net.datenwerke.rs.dashboard.client.dashboard.rpc.DashboardRpcService;
import net.datenwerke.rs.dashboard.service.dashboard.DadgetService;
import net.datenwerke.rs.dashboard.service.dashboard.DashboardService;
import net.datenwerke.rs.dashboard.service.dashboard.dagets.FavoriteList;
import net.datenwerke.rs.dashboard.service.dashboard.dagets.FavoriteListEntry;
import net.datenwerke.rs.dashboard.service.dashboard.dagets.ReportDadget;
import net.datenwerke.rs.dashboard.service.dashboard.entities.Dadget;
import net.datenwerke.rs.dashboard.service.dashboard.entities.Dashboard;
import net.datenwerke.rs.dashboard.service.dashboard.entities.DashboardContainer;
import net.datenwerke.rs.dashboard.service.dashboard.entities.DashboardNode;
import net.datenwerke.rs.dashboard.service.dashboard.entities.DashboardReference;
import net.datenwerke.rs.dashboard.service.dashboard.entities.LayoutType;
import net.datenwerke.rs.dashboard.service.dashboard.genrights.DashboardViewSecurityTarget;
import net.datenwerke.rs.teamspace.service.teamspace.TeamSpaceService;
import net.datenwerke.rs.teamspace.service.teamspace.entities.TeamSpace;
import net.datenwerke.rs.tsreportarea.client.tsreportarea.dto.AbstractTsDiskNodeDto;
import net.datenwerke.rs.tsreportarea.service.tsreportarea.TsDiskService;
import net.datenwerke.rs.tsreportarea.service.tsreportarea.entities.AbstractTsDiskNode;
import net.datenwerke.rs.utils.entitycloner.EntityClonerService;
import net.datenwerke.security.server.SecuredRemoteServiceServlet;
import net.datenwerke.security.service.authenticator.AuthenticatorService;
import net.datenwerke.security.service.security.SecurityService;
import net.datenwerke.security.service.security.annotation.ArgumentVerification;
import net.datenwerke.security.service.security.annotation.GenericTargetVerification;
import net.datenwerke.security.service.security.annotation.RightsVerification;
import net.datenwerke.security.service.security.annotation.SecurityChecked;
import net.datenwerke.security.service.security.exceptions.ViolatedSecurityException;
import net.datenwerke.security.service.security.rights.Read;
import net.datenwerke.security.service.security.rights.Write;
import net.datenwerke.security.service.usermanager.entities.User;

@Singleton
public class DashboardRpcServiceImpl extends SecuredRemoteServiceServlet implements DashboardRpcService {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2357811151987662453L;


	private final Provider<AuthenticatorService> authenticatorProvider;
	private final DashboardService dashboardService;
	private final DtoService dtoService;
	private final DadgetService dadgetService;
	private final TeamSpaceService teamSpaceService;
	private final SecurityService securityService;
	private final Injector injector;
	private final EntityClonerService entityCloner;

	private TsDiskService diskService;

	@Inject
	public DashboardRpcServiceImpl(
			Provider<AuthenticatorService> authenticatorProvider,
			DashboardService dashboardService,
			DadgetService dadgetService,
			DtoService dtoService,
			TeamSpaceService teamSpaceService,
			TsDiskService diskService,
			SecurityService securityService,
			EntityClonerService entityCloner,
			Injector injector
			){
		this.authenticatorProvider = authenticatorProvider;
		this.dashboardService = dashboardService;
		this.dadgetService = dadgetService;
		this.dtoService = dtoService;
		this.teamSpaceService = teamSpaceService;
		this.diskService = diskService;
		this.securityService = securityService;
		this.entityCloner = entityCloner;
		this.injector = injector;

	}

	@SecurityChecked(
			genericTargetVerification = {
				@GenericTargetVerification(
					target=DashboardViewSecurityTarget.class,
					verify=@RightsVerification(rights=Read.class)
				)
			}
		)
	@Override
	@Transactional(rollbackOn=Exception.class)
	public DashboardContainerDto getDashboardForUser() throws ServerCallFailedException{
		User user = authenticatorProvider.get().getCurrentUser();
		if(null == user)
			return null;

		return (DashboardContainerDto) dtoService.createListDto(dashboardService.getDashboardFor(user));
	}

	@SecurityChecked(
			genericTargetVerification = {
				@GenericTargetVerification(
					target=DashboardViewSecurityTarget.class,
					verify=@RightsVerification(rights=Read.class)
				)
			}
		)
	@Override
	public DashboardDto reloadDashboard(DashboardDto dashboard) {
		return (DashboardDto) dtoService.createDto(dashboardService.getDashboardById(dashboard.getId()));
	}

	@SecurityChecked(
			genericTargetVerification = {
				@GenericTargetVerification(
					target=DashboardViewSecurityTarget.class,
					verify=@RightsVerification(rights=Write.class)
				)
			}
		)
	@Override
	@Transactional(rollbackOn=Exception.class)
	public DashboardDto createDashboardForUser() throws ServerCallFailedException{
		User user = authenticatorProvider.get().getCurrentUser();
		if(null == user)
			return null;

		DashboardContainer container = dashboardService.getDashboardFor(user);
		Dashboard dashboard = new Dashboard();
		dashboard.setName("Default");
		dashboardService.persist(dashboard);

		container.addDashboard(dashboard);

		dashboardService.merge(container);

		return (DashboardDto) dtoService.createDto(dashboard);
	}

	@SecurityChecked(
			genericTargetVerification = {
				@GenericTargetVerification(
					target=DashboardViewSecurityTarget.class,
					verify=@RightsVerification(rights=Write.class)
				)
			}
		)
	@Override
	@Transactional(rollbackOn=Exception.class)
	public void editDashboards(ArrayList<DashboardDto> dashboards)
			throws ServerCallFailedException {
		for(DashboardDto dashboard : dashboards)
			editDashboard(dashboard);
	}

	@Override
	@Transactional(rollbackOn=Exception.class)
	public void removeDashboard(DashboardDto dashboardDto) throws ServerCallFailedException{
		Dashboard dashboard = (Dashboard) dtoService.loadPoso(dashboardDto);
		DashboardContainer container = dashboardService.getDashboardContainerFor(dashboard);
		checkAccess(container);

		if(container.removeDashboard(dashboard)){
			dashboardService.remove(dashboard);
			dashboardService.merge(container);
		}
	}

	@SecurityChecked(
			argumentVerification = {
					@ArgumentVerification(
							name = "node",
							isDto = true,
							verify = @RightsVerification(rights=Read.class)
							)
			}
			)
	@Override
	@Transactional(rollbackOn=Exception.class)
	public DashboardDto importReferencedDashboardForUser(@Named("node")DashboardNodeDto dashboardNodeDto) throws ServerCallFailedException {
		DashboardNode node = (DashboardNode) dtoService.loadPoso(dashboardNodeDto);

		User user = authenticatorProvider.get().getCurrentUser();
		if(null == user)
			return null;

		DashboardContainer container = dashboardService.getDashboardFor(user);
		DashboardReference dashboardReference = new DashboardReference();
		dashboardReference.setDashboardNode(node);
		
		/* copy dashboard */
		resetReferenceDashboard(dashboardReference, node);
		
		dashboardService.persist(dashboardReference);

		container.addDashboard(dashboardReference);

		dashboardService.merge(container);

		return (DashboardDto) dtoService.createDto(dashboardReference);
	}
	
	@Override
	@Transactional(rollbackOn=Exception.class)
	public DashboardDto resetReferencedDashboard(DashboardReferenceDto dashboardDto){
		DashboardReference dashboardReference = (DashboardReference) dtoService.loadPoso(dashboardDto);
		DashboardNode node = dashboardReference.getDashboardNode();
		
		if(null == node)
			throw new IllegalStateException("Corresponding dashboard node not found.");
		
		User user = authenticatorProvider.get().getCurrentUser();
		if(null == user)
			return null;
		
		DashboardContainer container = dashboardService.getDashboardContainerFor(dashboardReference);
		checkAccess(container);
		
		if(! securityService.checkRights(node, Read.class))
			throw new IllegalArgumentException("Insufficient rights to read dashboard");
		
		for(Dadget d : new ArrayList<>(dashboardReference.getDadgets())){
			dashboardReference.removeDadget(d);
			dashboardService.remove(d);
		}
		
		resetReferenceDashboard(dashboardReference, node);
		
		Dashboard dashboard = dashboardService.merge(dashboardReference);	
		
		return (DashboardDto) dtoService.createDto(dashboard);
	}
	
	@Override
	@Transactional(rollbackOn=Exception.class)
	public DashboardDto loadDashboardForDisplay(DashboardDto dashboardDto) {
		Dashboard dashboard = (Dashboard) dtoService.loadPoso(dashboardDto);
		DashboardContainer container = dashboardService.getDashboardContainerFor(dashboard);
		checkAccess(container);
		
		if(dashboard instanceof DashboardReference && dashboard.getDadgets().isEmpty()){
			DashboardNode node = ((DashboardReference)dashboard).getDashboardNode();
			
			if(securityService.checkRights(node, Read.class)){
				resetReferenceDashboard((DashboardReference) dashboard, node);
				dashboardService.merge(dashboard);
			}
		}
		
		return (DashboardDto) dtoService.createDto(dashboard);
	}
	
	protected void resetReferenceDashboard(DashboardReference dashboardReference, DashboardNode node){
		/* copy dashboard */
		dashboardReference.setName(node.getName());
		dashboardReference.setDescription(node.getDescription());
		dashboardReference.setLayout(node.getDashboard().getLayout());
		dashboardReference.setSinglePage(node.getDashboard().isSinglePage());
		
		for(Dadget d : node.getDashboard().getDadgets()){
			Dadget dClone = entityCloner.cloneEntity(d);
			dashboardService.persist(dClone);
			dashboardReference.addDadgetPlain(dClone);
		}
	}

	@SecurityChecked(
			genericTargetVerification = {
				@GenericTargetVerification(
					target=DashboardViewSecurityTarget.class,
					verify=@RightsVerification(rights=Write.class)
				)
			}
		)
	@Override
	@Transactional(rollbackOn=Exception.class)
	public DashboardDto editDashboard(DashboardDto dashboardDto) throws ServerCallFailedException {
		Dashboard dashboard = (Dashboard) dtoService.loadPoso(dashboardDto);

		DashboardContainer container = dashboardService.getDashboardContainerFor(dashboard);
		checkAccess(container);

		if(! container.contains(dashboard))
			throw new ViolatedSecurityException();

		dashboard.setN(dashboardDto.getN());

		dashboard.setName(dashboardDto.getName());
		dashboard.setDescription(dashboardDto.getDescription());
		dashboard.setSinglePage(dashboardDto.isSinglePage());

		LayoutType oldL = dashboard.getLayout();
		dashboard.setLayout((LayoutType) dtoService.loadPoso(dashboardDto.getLayout()));
		if(oldL.compareTo(dashboard.getLayout()) > 0){
			switch (dashboard.getLayout()) {
			case TWO_COLUMN_EQUI:
			case TWO_COLUMN_LEFT_LARGE:
			case TWO_COLUMN_RIGHT_LARGE:
				for(Dadget d : dashboard.getDadgetsInColumn(3)){
					d.setCol(2);
					dashboardService.merge(d);
				}
				break;
			case ONE_COLUMN:
				for(Dadget d : dashboard.getDadgets()){
					d.setCol(1);
					dashboardService.merge(d);
				}
				break;
			}
		}

		dashboard = dashboardService.merge(dashboard);

		return (DashboardDto) dtoService.createDto(dashboard);	
	}

	@SecurityChecked(
			genericTargetVerification = {
				@GenericTargetVerification(
					target=DashboardViewSecurityTarget.class,
					verify=@RightsVerification(rights=Write.class)
				)
			}
		)
	@Override
	@Transactional(rollbackOn=Exception.class)
	public DashboardDto addDadgetToDashboard(DashboardDto dashboardDto,
			DadgetDto dadgetDto) throws ServerCallFailedException {
		Dashboard dashboard = (Dashboard) dtoService.loadPoso(dashboardDto);
		DashboardContainer container = dashboardService.getDashboardContainerFor(dashboard);
		checkAccess(container);

		if(! container.contains(dashboard))
			throw new ViolatedSecurityException();

		Dadget dadget = (Dadget) dtoService.createPoso(dadgetDto);
		dadget.init();
		dashboardService.persist(dadget);

		dashboard.addDadget(dadget);
		dashboard = dashboardService.merge(dashboard);

		return (DashboardDto) dtoService.createDto(dashboard);	
	}

	@SecurityChecked(
			genericTargetVerification = {
				@GenericTargetVerification(
					target=DashboardViewSecurityTarget.class,
					verify=@RightsVerification(rights=Write.class)
				)
			}
		)
	@Override
	@Transactional(rollbackOn=Exception.class)
	public DashboardDto removeDadgetFromDashboard(DashboardDto dashboardDto,
			DadgetDto dadgetDto) throws ServerCallFailedException {
		Dashboard dashboard = (Dashboard) dtoService.loadPoso(dashboardDto);
		DashboardContainer container = dashboardService.getDashboardContainerFor(dashboard);
		checkAccess(container);

		if(! container.contains(dashboard))
			throw new ViolatedSecurityException();

		Dadget dadget = (Dadget) dtoService.loadPoso(dadgetDto);
		if(! dashboard.contains(dadget))
			throw new ViolatedSecurityException();

		if(null != dadget && dashboard.removeDadget(dadget)){
			dashboardService.remove(dadget);
			dashboard = dashboardService.merge(dashboard);	
		}

		return (DashboardDto) dtoService.createDto(dashboard);	
	}

	@SecurityChecked(
			genericTargetVerification = {
				@GenericTargetVerification(
					target=DashboardViewSecurityTarget.class,
					verify=@RightsVerification(rights=Write.class)
				)
			}
		)
	@Override
	@Transactional(rollbackOn=Exception.class)
	public DashboardDto editDadgetToDashboard(DashboardDto dashboardDto, DadgetDto dadgetDto)
			throws ServerCallFailedException {
		Dashboard dashboard = (Dashboard) dtoService.loadPoso(dashboardDto);
		DashboardContainer container = dashboardService.getDashboardContainerFor(dashboard); 
		checkAccess(container);

		if(! container.contains(dashboard))
			throw new ViolatedSecurityException();

		Dadget dadget = (Dadget) dtoService.loadPoso(dadgetDto);

		int oldN = dadget.getN();
		int oldC = dadget.getCol();
		dadget = (Dadget) dtoService.loadAndMergePoso(dadgetDto);
		if(oldN != dadget.getN() || oldC != dadget.getCol()){
			List<Dadget> dadgetsInCol = dashboard.getDadgetsInColumn(dadget.getCol());

			dadgetsInCol.remove(dadget);
			if(dadget.getN() > dadgetsInCol.size() || dadget.getN() < 0)
				dadgetsInCol.add(dadget);
			else
				dadgetsInCol.add(dadget.getN(), dadget);
			int c = 0;
			for(Dadget d : dadgetsInCol){
				d.setN(c++);
				dashboardService.merge(d);
			}

			/* sort */
			Collections.sort(dashboard.getDadgets(), new Comparator<Dadget>() {
				@Override
				public int compare(Dadget o1, Dadget o2) {
					return ((Integer)o1.getN()).compareTo(o2.getN());
				}
			});
			dashboardService.merge(dashboard);
		}

		dashboardService.merge(dadget);

		DashboardDto dash = (DashboardDto) dtoService.createDto(dashboard);
		return dash;
	}

	protected void checkAccess(DashboardContainer container) {
		User user = authenticatorProvider.get().getCurrentUser();
		if(null == user)
			throw new ViolatedSecurityException();

		DashboardContainer refcontainer = dashboardService.getDashboardFor(user);
		if(null == refcontainer || ! refcontainer.equals(container))
			throw new ViolatedSecurityException();
	}
	
	protected void checkAccess(Dashboard dashboard, DashboardContainer container) {
		if(null != container)
			checkAccess(container);
		else {
			DashboardNode node = dashboardService.getNodeFor(dashboard);
			if(! securityService.checkRights(node, Write.class))
				throw new ViolatedSecurityException("Insufficient rights to change dashboard");
		}
	}


	@Override
	@Transactional(rollbackOn=Exception.class)
	public void addToFavorites(AbstractTsDiskNodeDto nodeDto)
			throws ServerCallFailedException {
		AbstractTsDiskNode node = (AbstractTsDiskNode) dtoService.loadPoso(nodeDto);
		if(null == node)
			throw new ServerCallFailedException("could not load reference");

		TeamSpace ts = diskService.getTeamSpaceFor(node);
		if(null == ts || ! teamSpaceService.mayAccess(ts))
			throw new ViolatedSecurityException();

		FavoriteList list = dadgetService.loadFavoriteList();

		if(! list.containsEntry(node)){
			FavoriteListEntry entry = new FavoriteListEntry();
			entry.setReferenceEntry(node);
			list.addEntry(entry);
		}

		dadgetService.merge(list);
	}

	@Override
	@Transactional(rollbackOn=Exception.class)
	public void removeFromFavorites(AbstractTsDiskNodeDto nodeDto)
			throws ServerCallFailedException {
		AbstractTsDiskNode node = (AbstractTsDiskNode) dtoService.loadPoso(nodeDto);
		if(null == node)
			throw new ServerCallFailedException("could not load reference");

		TeamSpace ts = diskService.getTeamSpaceFor(node);
		if(null == ts || ! teamSpaceService.mayAccess(ts))
			throw new ViolatedSecurityException();

		FavoriteList list = dadgetService.loadFavoriteList();

		if(list.containsEntry(node)){
			FavoriteListEntry entry = list.getEntry(node);
			dadgetService.remove(list, entry);
			dadgetService.merge(list);
		}
	}


	@Override
	@Transactional(rollbackOn=Exception.class)
	public FavoriteListDto loadFavorites() throws ServerCallFailedException {
		FavoriteList list = dadgetService.loadFavoriteList();
		return (FavoriteListDto) dtoService.createDto(list);
	}

	@Override
	public Map<String, ParameterInstanceDto> getDashboardParameterInstances(DashboardDto dashboardDto) throws ServerCallFailedException {
		HashMap<String, ParameterInstanceDto> res = new HashMap<String, ParameterInstanceDto>();
		Dashboard dashboard = (Dashboard) dtoService.loadPoso(dashboardDto);
		
		/* security */
		DashboardContainer container = dashboardService.getDashboardContainerFor(dashboard);
		checkAccess(dashboard, container);
		
		for(Dadget dadget : dashboard.getDadgets()){
			if(dadget instanceof ReportDadget){
				if(null != ((ReportDadget) dadget).getReport()){
					Report report = ((ReportDadget) dadget).getReport();
					for(ParameterInstance instance: report.getParameterInstances()){
						res.put(instance.getDefinition().getKey(), (ParameterInstanceDto) dtoService.createDto(instance, DtoView.ALL, DtoView.ALL));
					}
					for(ParameterInstance instance: ((ReportDadget) dadget).getParameterInstances()){
						res.put(instance.getDefinition().getKey(), (ParameterInstanceDto) dtoService.createDto(instance, DtoView.ALL, DtoView.ALL));
					}
				}
			}
		}
		return res;
	}

	@Override
	public Map<String, ParameterInstanceDto> getDadgetParameterInstances(ReportDadgetDto dadgetDto) throws ServerCallFailedException {
		ReportDadget dadget = (ReportDadget) dtoService.loadPoso(dadgetDto);

		/* security */
		Dashboard dashboard = dashboardService.getDashboardFor(dadget);
		DashboardContainer container = dashboardService.getDashboardContainerFor(dashboard);
		checkAccess(dashboard, container);
		
		HashMap<String, ParameterInstanceDto> res = new HashMap<String, ParameterInstanceDto>();
		if(null != dadget && null != ((ReportDadget) dadget).getReport()){
			Report report = ((ReportDadget) dadget).getReport();
			for(ParameterInstance instance: report.getParameterInstances())
				res.put(instance.getDefinition().getKey(), (ParameterInstanceDto) dtoService.createDto(instance, DtoView.ALL, DtoView.ALL));
			for(ParameterInstance instance: ((ReportDadget) dadget).getParameterInstances())
				res.put(instance.getDefinition().getKey(), (ParameterInstanceDto) dtoService.createDto(instance, DtoView.ALL, DtoView.ALL));
		}
		return res;
	}

	@Override
	@Transactional(rollbackOn=Exception.class)
	public List<DadgetDto> setDashboardParameterInstances(DashboardDto dashboardDto, Set<ParameterInstanceDto> parameterInstances) throws ServerCallFailedException {
		ArrayList<DadgetDto> res = new ArrayList<>();
		Dashboard dashboard = (Dashboard) dtoService.loadPoso(dashboardDto);
		
		/* security */
		DashboardContainer container = dashboardService.getDashboardContainerFor(dashboard);
		checkAccess(dashboard, container);
		
		for(Dadget dadget : dashboard.getDadgets()){
			if(dadget instanceof ReportDadget){
				Report report = ((ReportDadget) dadget).getReport();
				if(null != report){
					Set<ParameterInstance> instancesForReport = getInstancesForReport(report, parameterInstances);
					((ReportDadget) dadget).getParameterInstances().clear();
					((ReportDadget) dadget).getParameterInstances().addAll(instancesForReport);

					if(!instancesForReport.isEmpty()){
						dashboardService.merge(dadget);
						res.add((DadgetDto) dtoService.createDto(dadget));
					}
				}
			}
		}
		return res;
	}

	@Override
	@Transactional(rollbackOn=Exception.class)
	public DadgetDto setDadgetParameterInstances(ReportDadgetDto dadgetDto, Set<ParameterInstanceDto> parameterInstances) throws ServerCallFailedException {
		ReportDadget dadget= (ReportDadget) dtoService.loadPoso(dadgetDto);

		/* security */
		Dashboard dashboard = dashboardService.getDashboardFor(dadget);
		DashboardContainer container = dashboardService.getDashboardContainerFor(dashboard);
		checkAccess(dashboard, container);
		
		Report report = ((ReportDadget) dadget).getReport();
		if(null != report){
			Set<ParameterInstance> instancesForReport = getInstancesForReport(report, parameterInstances);
			((ReportDadget) dadget).getParameterInstances().clear();
			((ReportDadget) dadget).getParameterInstances().addAll(instancesForReport);

			dashboardService.merge(dadget);
			return ((DadgetDto) dtoService.createDto(dadget));
		}
		return dadgetDto;
	}

	private Set<ParameterInstance> getInstancesForReport(Report report, Set<ParameterInstanceDto> instances) throws ExpectedException{
		HashMap<String, ParameterDefinition> keys = new HashMap<>();
		HashSet<ParameterInstance> res = new HashSet<>();

		for(ParameterDefinition pd : report.getParameterDefinitions()){
			keys.put(pd.getKey(), pd);
		}

		for(ParameterInstanceDto pidto : instances){
			if(keys.containsKey(pidto.getDefinition().getKey())){
				ParameterInstance pi = (ParameterInstance) dtoService.createUnmanagedPoso(pidto);
				pi.setDefinition(keys.get(pidto.getDefinition().getKey()));
				res.add(pi);
			}
		}

		return res;
	}
}