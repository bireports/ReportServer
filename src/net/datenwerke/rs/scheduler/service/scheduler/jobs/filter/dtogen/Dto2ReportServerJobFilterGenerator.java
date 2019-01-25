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
 
 
package net.datenwerke.rs.scheduler.service.scheduler.jobs.filter.dtogen;

import com.google.inject.Inject;
import com.google.inject.Provider;
import java.lang.Exception;
import java.lang.NullPointerException;
import java.lang.RuntimeException;
import java.lang.reflect.Field;
import java.util.Collection;
import net.datenwerke.dtoservices.dtogenerator.annotations.GeneratedType;
import net.datenwerke.dtoservices.dtogenerator.dto2posogenerator.interfaces.Dto2PosoGenerator;
import net.datenwerke.dtoservices.dtogenerator.dto2posogenerator.validator.DtoPropertyValidator;
import net.datenwerke.gxtdto.client.servercommunication.exceptions.ExpectedException;
import net.datenwerke.gxtdto.server.dtomanager.DtoMainService;
import net.datenwerke.gxtdto.server.dtomanager.DtoService;
import net.datenwerke.rs.scheduler.client.scheduler.dto.ReportServerJobFilterDto;
import net.datenwerke.rs.scheduler.service.scheduler.jobs.filter.ReportServerJobFilter;
import net.datenwerke.rs.scheduler.service.scheduler.jobs.filter.dtogen.Dto2ReportServerJobFilterGenerator;
import net.datenwerke.rs.utils.entitycloner.annotation.TransientID;
import net.datenwerke.rs.utils.reflection.ReflectionService;
import net.datenwerke.scheduler.client.scheduler.dto.JobExecutionStatusDto;
import net.datenwerke.scheduler.client.scheduler.dto.OutcomeDto;
import net.datenwerke.scheduler.client.scheduler.dto.filter.OrderDto;
import net.datenwerke.scheduler.service.scheduler.entities.JobExecutionStatus;
import net.datenwerke.scheduler.service.scheduler.entities.Outcome;
import net.datenwerke.scheduler.service.scheduler.stores.jpa.filter.Order;
import net.datenwerke.security.client.usermanager.dto.UserDto;
import net.datenwerke.security.service.usermanager.entities.User;

/**
 * Dto2PosoGenerator for ReportServerJobFilter
 *
 * This file was automatically created by DtoAnnotationProcessor, version 0.1
 */
@GeneratedType("net.datenwerke.dtoservices.dtogenerator.DtoAnnotationProcessor")
public class Dto2ReportServerJobFilterGenerator implements Dto2PosoGenerator<ReportServerJobFilterDto,ReportServerJobFilter> {

	private final Provider<DtoService> dtoServiceProvider;

	private final net.datenwerke.dtoservices.dtogenerator.dto2posogenerator.interfaces.Dto2PosoSupervisorDefaultImpl dto2PosoSupervisor;

	private final ReflectionService reflectionService;
	@Inject
	public Dto2ReportServerJobFilterGenerator(
		net.datenwerke.dtoservices.dtogenerator.dto2posogenerator.interfaces.Dto2PosoSupervisorDefaultImpl dto2PosoSupervisor,
		Provider<DtoService> dtoServiceProvider,
		ReflectionService reflectionService
	){
		this.dto2PosoSupervisor = dto2PosoSupervisor;
		this.dtoServiceProvider = dtoServiceProvider;
		this.reflectionService = reflectionService;
	}

	public ReportServerJobFilter loadPoso(ReportServerJobFilterDto dto)  {
		/* Poso is not an entity .. so what should I load? */
		return null;
	}

	public ReportServerJobFilter instantiatePoso()  {
		ReportServerJobFilter poso = new ReportServerJobFilter();
		return poso;
	}

	public ReportServerJobFilter createPoso(ReportServerJobFilterDto dto)  throws ExpectedException {
		ReportServerJobFilter poso = new ReportServerJobFilter();

		/* merge data */
		mergePoso(dto, poso);
		return poso;
	}

	public ReportServerJobFilter createUnmanagedPoso(ReportServerJobFilterDto dto)  throws ExpectedException {
		ReportServerJobFilter poso = new ReportServerJobFilter();

		mergePlainDto2UnmanagedPoso(dto,poso);

		return poso;
	}

	public void mergePoso(ReportServerJobFilterDto dto, final ReportServerJobFilter poso)  throws ExpectedException {
		if(dto.isDtoProxy())
			mergeProxy2Poso(dto, poso);
		else
			mergePlainDto2Poso(dto, poso);
	}

	protected void mergePlainDto2Poso(ReportServerJobFilterDto dto, final ReportServerJobFilter poso)  throws ExpectedException {
		/*  set active */
		try{
			poso.setActive(dto.isActive() );
		} catch(NullPointerException e){
		}

		/*  set executionStatus */
		JobExecutionStatusDto tmpDto_executionStatus = dto.getExecutionStatus();
		poso.setExecutionStatus((JobExecutionStatus)dtoServiceProvider.get().createPoso(tmpDto_executionStatus));

		/*  set fromCurrentUser */
		try{
			poso.setFromCurrentUser(dto.isFromCurrentUser() );
		} catch(NullPointerException e){
		}

		/*  set fromUser */
		UserDto tmpDto_fromUser = dto.getFromUser();
		if(null != tmpDto_fromUser && null != tmpDto_fromUser.getId()){
			if(null != poso.getFromUser() && null != poso.getFromUser().getId() && ! poso.getFromUser().getId().equals(tmpDto_fromUser.getId())){
				User newPropertyValue = (User)dtoServiceProvider.get().loadPoso(tmpDto_fromUser);
				dto2PosoSupervisor.referencedObjectRemoved(dto, poso, poso.getFromUser(), newPropertyValue, "fromUser");
				poso.setFromUser(newPropertyValue);
			} else if(null == poso.getFromUser()){
				poso.setFromUser((User)dtoServiceProvider.get().loadPoso(tmpDto_fromUser));
			}
		} else if(null != tmpDto_fromUser && null == tmpDto_fromUser.getId()){
			((DtoMainService)dtoServiceProvider.get()).getCreationHelper().onPosoCreation(tmpDto_fromUser, new net.datenwerke.gxtdto.server.dtomanager.CallbackOnPosoCreation(){
				public void callback(Object refPoso){
					if(null == refPoso)
						throw new IllegalArgumentException("referenced dto should have an id (fromUser)");
					poso.setFromUser((User)refPoso);
				}
			});
		} else if(null == tmpDto_fromUser){
			dto2PosoSupervisor.referencedObjectRemoved(dto, poso, poso.getFromUser(), null, "fromUser");
			poso.setFromUser(null);
		}

		/*  set inActive */
		try{
			poso.setInActive(dto.isInActive() );
		} catch(NullPointerException e){
		}

		/*  set lastOutcome */
		OutcomeDto tmpDto_lastOutcome = dto.getLastOutcome();
		poso.setLastOutcome((Outcome)dtoServiceProvider.get().createPoso(tmpDto_lastOutcome));

		/*  set limit */
		try{
			poso.setLimit(dto.getLimit() );
		} catch(NullPointerException e){
		}

		/*  set offset */
		try{
			poso.setOffset(dto.getOffset() );
		} catch(NullPointerException e){
		}

		/*  set order */
		OrderDto tmpDto_order = dto.getOrder();
		poso.setOrder((Order)dtoServiceProvider.get().createPoso(tmpDto_order));

		/*  set reports */
		poso.setReports(dto.getReports() );

		/*  set sortField */
		poso.setSortField(dto.getSortField() );

		/*  set toCurrentUser */
		try{
			poso.setToCurrentUser(dto.isToCurrentUser() );
		} catch(NullPointerException e){
		}

		/*  set toUser */
		UserDto tmpDto_toUser = dto.getToUser();
		if(null != tmpDto_toUser && null != tmpDto_toUser.getId()){
			if(null != poso.getToUser() && null != poso.getToUser().getId() && ! poso.getToUser().getId().equals(tmpDto_toUser.getId())){
				User newPropertyValue = (User)dtoServiceProvider.get().loadPoso(tmpDto_toUser);
				dto2PosoSupervisor.referencedObjectRemoved(dto, poso, poso.getToUser(), newPropertyValue, "toUser");
				poso.setToUser(newPropertyValue);
			} else if(null == poso.getToUser()){
				poso.setToUser((User)dtoServiceProvider.get().loadPoso(tmpDto_toUser));
			}
		} else if(null != tmpDto_toUser && null == tmpDto_toUser.getId()){
			((DtoMainService)dtoServiceProvider.get()).getCreationHelper().onPosoCreation(tmpDto_toUser, new net.datenwerke.gxtdto.server.dtomanager.CallbackOnPosoCreation(){
				public void callback(Object refPoso){
					if(null == refPoso)
						throw new IllegalArgumentException("referenced dto should have an id (toUser)");
					poso.setToUser((User)refPoso);
				}
			});
		} else if(null == tmpDto_toUser){
			dto2PosoSupervisor.referencedObjectRemoved(dto, poso, poso.getToUser(), null, "toUser");
			poso.setToUser(null);
		}

	}

	protected void mergeProxy2Poso(ReportServerJobFilterDto dto, final ReportServerJobFilter poso)  throws ExpectedException {
		/*  set active */
		if(dto.isActiveModified()){
			try{
				poso.setActive(dto.isActive() );
			} catch(NullPointerException e){
			}
		}

		/*  set executionStatus */
		if(dto.isExecutionStatusModified()){
			JobExecutionStatusDto tmpDto_executionStatus = dto.getExecutionStatus();
			poso.setExecutionStatus((JobExecutionStatus)dtoServiceProvider.get().createPoso(tmpDto_executionStatus));
		}

		/*  set fromCurrentUser */
		if(dto.isFromCurrentUserModified()){
			try{
				poso.setFromCurrentUser(dto.isFromCurrentUser() );
			} catch(NullPointerException e){
			}
		}

		/*  set fromUser */
		if(dto.isFromUserModified()){
			UserDto tmpDto_fromUser = dto.getFromUser();
			if(null != tmpDto_fromUser && null != tmpDto_fromUser.getId()){
				if(null != poso.getFromUser() && null != poso.getFromUser().getId() && ! poso.getFromUser().getId().equals(tmpDto_fromUser.getId())){
					User newPropertyValue = (User)dtoServiceProvider.get().loadPoso(tmpDto_fromUser);
					dto2PosoSupervisor.referencedObjectRemoved(dto, poso, poso.getFromUser(), newPropertyValue, "fromUser");
					poso.setFromUser(newPropertyValue);
				} else if(null == poso.getFromUser()){
					poso.setFromUser((User)dtoServiceProvider.get().loadPoso(tmpDto_fromUser));
				}
			} else if(null != tmpDto_fromUser && null == tmpDto_fromUser.getId()){
			((DtoMainService)dtoServiceProvider.get()).getCreationHelper().onPosoCreation(tmpDto_fromUser, new net.datenwerke.gxtdto.server.dtomanager.CallbackOnPosoCreation(){
					public void callback(Object refPoso){
						if(null == refPoso)
							throw new IllegalArgumentException("referenced dto should have an id (fromUser)");
						poso.setFromUser((User)refPoso);
					}
			});
			} else if(null == tmpDto_fromUser){
				dto2PosoSupervisor.referencedObjectRemoved(dto, poso, poso.getFromUser(), null, "fromUser");
				poso.setFromUser(null);
			}
		}

		/*  set inActive */
		if(dto.isInActiveModified()){
			try{
				poso.setInActive(dto.isInActive() );
			} catch(NullPointerException e){
			}
		}

		/*  set lastOutcome */
		if(dto.isLastOutcomeModified()){
			OutcomeDto tmpDto_lastOutcome = dto.getLastOutcome();
			poso.setLastOutcome((Outcome)dtoServiceProvider.get().createPoso(tmpDto_lastOutcome));
		}

		/*  set limit */
		if(dto.isLimitModified()){
			try{
				poso.setLimit(dto.getLimit() );
			} catch(NullPointerException e){
			}
		}

		/*  set offset */
		if(dto.isOffsetModified()){
			try{
				poso.setOffset(dto.getOffset() );
			} catch(NullPointerException e){
			}
		}

		/*  set order */
		if(dto.isOrderModified()){
			OrderDto tmpDto_order = dto.getOrder();
			poso.setOrder((Order)dtoServiceProvider.get().createPoso(tmpDto_order));
		}

		/*  set reports */
		if(dto.isReportsModified()){
			poso.setReports(dto.getReports() );
		}

		/*  set sortField */
		if(dto.isSortFieldModified()){
			poso.setSortField(dto.getSortField() );
		}

		/*  set toCurrentUser */
		if(dto.isToCurrentUserModified()){
			try{
				poso.setToCurrentUser(dto.isToCurrentUser() );
			} catch(NullPointerException e){
			}
		}

		/*  set toUser */
		if(dto.isToUserModified()){
			UserDto tmpDto_toUser = dto.getToUser();
			if(null != tmpDto_toUser && null != tmpDto_toUser.getId()){
				if(null != poso.getToUser() && null != poso.getToUser().getId() && ! poso.getToUser().getId().equals(tmpDto_toUser.getId())){
					User newPropertyValue = (User)dtoServiceProvider.get().loadPoso(tmpDto_toUser);
					dto2PosoSupervisor.referencedObjectRemoved(dto, poso, poso.getToUser(), newPropertyValue, "toUser");
					poso.setToUser(newPropertyValue);
				} else if(null == poso.getToUser()){
					poso.setToUser((User)dtoServiceProvider.get().loadPoso(tmpDto_toUser));
				}
			} else if(null != tmpDto_toUser && null == tmpDto_toUser.getId()){
			((DtoMainService)dtoServiceProvider.get()).getCreationHelper().onPosoCreation(tmpDto_toUser, new net.datenwerke.gxtdto.server.dtomanager.CallbackOnPosoCreation(){
					public void callback(Object refPoso){
						if(null == refPoso)
							throw new IllegalArgumentException("referenced dto should have an id (toUser)");
						poso.setToUser((User)refPoso);
					}
			});
			} else if(null == tmpDto_toUser){
				dto2PosoSupervisor.referencedObjectRemoved(dto, poso, poso.getToUser(), null, "toUser");
				poso.setToUser(null);
			}
		}

	}

	public void mergeUnmanagedPoso(ReportServerJobFilterDto dto, final ReportServerJobFilter poso)  throws ExpectedException {
		if(dto.isDtoProxy())
			mergeProxy2UnmanagedPoso(dto, poso);
		else
			mergePlainDto2UnmanagedPoso(dto, poso);
	}

	protected void mergePlainDto2UnmanagedPoso(ReportServerJobFilterDto dto, final ReportServerJobFilter poso)  throws ExpectedException {
		/*  set active */
		try{
			poso.setActive(dto.isActive() );
		} catch(NullPointerException e){
		}

		/*  set executionStatus */
		JobExecutionStatusDto tmpDto_executionStatus = dto.getExecutionStatus();
		poso.setExecutionStatus((JobExecutionStatus)dtoServiceProvider.get().createPoso(tmpDto_executionStatus));

		/*  set fromCurrentUser */
		try{
			poso.setFromCurrentUser(dto.isFromCurrentUser() );
		} catch(NullPointerException e){
		}

		/*  set fromUser */
		UserDto tmpDto_fromUser = dto.getFromUser();
		if(null != tmpDto_fromUser && null != tmpDto_fromUser.getId()){
			User newPropertyValue = (User)dtoServiceProvider.get().loadPoso(tmpDto_fromUser);
			poso.setFromUser(newPropertyValue);
			((DtoMainService)dtoServiceProvider.get()).getCreationHelper().onPosoCreation(tmpDto_fromUser, new net.datenwerke.gxtdto.server.dtomanager.CallbackOnPosoCreation(){
				public void callback(Object refPoso){
					if(null != refPoso)
						poso.setFromUser((User)refPoso);
				}
			});
		} else if(null != tmpDto_fromUser && null == tmpDto_fromUser.getId()){
			final UserDto tmpDto_fromUser_final = tmpDto_fromUser;
			((DtoMainService)dtoServiceProvider.get()).getCreationHelper().onPosoCreation(tmpDto_fromUser, new net.datenwerke.gxtdto.server.dtomanager.CallbackOnPosoCreation(){
				public void callback(Object refPoso){
					if(null == refPoso){
						try{
							poso.setFromUser((User)dtoServiceProvider.get().createUnmanagedPoso(tmpDto_fromUser_final));
						} catch(ExpectedException e){
							throw new RuntimeException(e);
						}
					} else {
						poso.setFromUser((User)refPoso);
					}
				}
			});
		} else if(null == tmpDto_fromUser){
			poso.setFromUser(null);
		}

		/*  set inActive */
		try{
			poso.setInActive(dto.isInActive() );
		} catch(NullPointerException e){
		}

		/*  set lastOutcome */
		OutcomeDto tmpDto_lastOutcome = dto.getLastOutcome();
		poso.setLastOutcome((Outcome)dtoServiceProvider.get().createPoso(tmpDto_lastOutcome));

		/*  set limit */
		try{
			poso.setLimit(dto.getLimit() );
		} catch(NullPointerException e){
		}

		/*  set offset */
		try{
			poso.setOffset(dto.getOffset() );
		} catch(NullPointerException e){
		}

		/*  set order */
		OrderDto tmpDto_order = dto.getOrder();
		poso.setOrder((Order)dtoServiceProvider.get().createPoso(tmpDto_order));

		/*  set reports */
		poso.setReports(dto.getReports() );

		/*  set sortField */
		poso.setSortField(dto.getSortField() );

		/*  set toCurrentUser */
		try{
			poso.setToCurrentUser(dto.isToCurrentUser() );
		} catch(NullPointerException e){
		}

		/*  set toUser */
		UserDto tmpDto_toUser = dto.getToUser();
		if(null != tmpDto_toUser && null != tmpDto_toUser.getId()){
			User newPropertyValue = (User)dtoServiceProvider.get().loadPoso(tmpDto_toUser);
			poso.setToUser(newPropertyValue);
			((DtoMainService)dtoServiceProvider.get()).getCreationHelper().onPosoCreation(tmpDto_toUser, new net.datenwerke.gxtdto.server.dtomanager.CallbackOnPosoCreation(){
				public void callback(Object refPoso){
					if(null != refPoso)
						poso.setToUser((User)refPoso);
				}
			});
		} else if(null != tmpDto_toUser && null == tmpDto_toUser.getId()){
			final UserDto tmpDto_toUser_final = tmpDto_toUser;
			((DtoMainService)dtoServiceProvider.get()).getCreationHelper().onPosoCreation(tmpDto_toUser, new net.datenwerke.gxtdto.server.dtomanager.CallbackOnPosoCreation(){
				public void callback(Object refPoso){
					if(null == refPoso){
						try{
							poso.setToUser((User)dtoServiceProvider.get().createUnmanagedPoso(tmpDto_toUser_final));
						} catch(ExpectedException e){
							throw new RuntimeException(e);
						}
					} else {
						poso.setToUser((User)refPoso);
					}
				}
			});
		} else if(null == tmpDto_toUser){
			poso.setToUser(null);
		}

	}

	protected void mergeProxy2UnmanagedPoso(ReportServerJobFilterDto dto, final ReportServerJobFilter poso)  throws ExpectedException {
		/*  set active */
		if(dto.isActiveModified()){
			try{
				poso.setActive(dto.isActive() );
			} catch(NullPointerException e){
			}
		}

		/*  set executionStatus */
		if(dto.isExecutionStatusModified()){
			JobExecutionStatusDto tmpDto_executionStatus = dto.getExecutionStatus();
			poso.setExecutionStatus((JobExecutionStatus)dtoServiceProvider.get().createPoso(tmpDto_executionStatus));
		}

		/*  set fromCurrentUser */
		if(dto.isFromCurrentUserModified()){
			try{
				poso.setFromCurrentUser(dto.isFromCurrentUser() );
			} catch(NullPointerException e){
			}
		}

		/*  set fromUser */
		if(dto.isFromUserModified()){
			UserDto tmpDto_fromUser = dto.getFromUser();
			if(null != tmpDto_fromUser && null != tmpDto_fromUser.getId()){
				User newPropertyValue = (User)dtoServiceProvider.get().loadPoso(tmpDto_fromUser);
				poso.setFromUser(newPropertyValue);
			((DtoMainService)dtoServiceProvider.get()).getCreationHelper().onPosoCreation(tmpDto_fromUser, new net.datenwerke.gxtdto.server.dtomanager.CallbackOnPosoCreation(){
					public void callback(Object refPoso){
						if(null != refPoso)
							poso.setFromUser((User)refPoso);
					}
			});
			} else if(null != tmpDto_fromUser && null == tmpDto_fromUser.getId()){
				final UserDto tmpDto_fromUser_final = tmpDto_fromUser;
			((DtoMainService)dtoServiceProvider.get()).getCreationHelper().onPosoCreation(tmpDto_fromUser, new net.datenwerke.gxtdto.server.dtomanager.CallbackOnPosoCreation(){
					public void callback(Object refPoso){
						if(null == refPoso){
							try{
								poso.setFromUser((User)dtoServiceProvider.get().createUnmanagedPoso(tmpDto_fromUser_final));
							} catch(ExpectedException e){
								throw new RuntimeException(e);
							}
						} else {
							poso.setFromUser((User)refPoso);
						}
					}
			});
			} else if(null == tmpDto_fromUser){
				poso.setFromUser(null);
			}
		}

		/*  set inActive */
		if(dto.isInActiveModified()){
			try{
				poso.setInActive(dto.isInActive() );
			} catch(NullPointerException e){
			}
		}

		/*  set lastOutcome */
		if(dto.isLastOutcomeModified()){
			OutcomeDto tmpDto_lastOutcome = dto.getLastOutcome();
			poso.setLastOutcome((Outcome)dtoServiceProvider.get().createPoso(tmpDto_lastOutcome));
		}

		/*  set limit */
		if(dto.isLimitModified()){
			try{
				poso.setLimit(dto.getLimit() );
			} catch(NullPointerException e){
			}
		}

		/*  set offset */
		if(dto.isOffsetModified()){
			try{
				poso.setOffset(dto.getOffset() );
			} catch(NullPointerException e){
			}
		}

		/*  set order */
		if(dto.isOrderModified()){
			OrderDto tmpDto_order = dto.getOrder();
			poso.setOrder((Order)dtoServiceProvider.get().createPoso(tmpDto_order));
		}

		/*  set reports */
		if(dto.isReportsModified()){
			poso.setReports(dto.getReports() );
		}

		/*  set sortField */
		if(dto.isSortFieldModified()){
			poso.setSortField(dto.getSortField() );
		}

		/*  set toCurrentUser */
		if(dto.isToCurrentUserModified()){
			try{
				poso.setToCurrentUser(dto.isToCurrentUser() );
			} catch(NullPointerException e){
			}
		}

		/*  set toUser */
		if(dto.isToUserModified()){
			UserDto tmpDto_toUser = dto.getToUser();
			if(null != tmpDto_toUser && null != tmpDto_toUser.getId()){
				User newPropertyValue = (User)dtoServiceProvider.get().loadPoso(tmpDto_toUser);
				poso.setToUser(newPropertyValue);
			((DtoMainService)dtoServiceProvider.get()).getCreationHelper().onPosoCreation(tmpDto_toUser, new net.datenwerke.gxtdto.server.dtomanager.CallbackOnPosoCreation(){
					public void callback(Object refPoso){
						if(null != refPoso)
							poso.setToUser((User)refPoso);
					}
			});
			} else if(null != tmpDto_toUser && null == tmpDto_toUser.getId()){
				final UserDto tmpDto_toUser_final = tmpDto_toUser;
			((DtoMainService)dtoServiceProvider.get()).getCreationHelper().onPosoCreation(tmpDto_toUser, new net.datenwerke.gxtdto.server.dtomanager.CallbackOnPosoCreation(){
					public void callback(Object refPoso){
						if(null == refPoso){
							try{
								poso.setToUser((User)dtoServiceProvider.get().createUnmanagedPoso(tmpDto_toUser_final));
							} catch(ExpectedException e){
								throw new RuntimeException(e);
							}
						} else {
							poso.setToUser((User)refPoso);
						}
					}
			});
			} else if(null == tmpDto_toUser){
				poso.setToUser(null);
			}
		}

	}

	public ReportServerJobFilter loadAndMergePoso(ReportServerJobFilterDto dto)  throws ExpectedException {
		ReportServerJobFilter poso = loadPoso(dto);
		if(null != poso){
			mergePoso(dto, poso);
			return poso;
		}
		return createPoso(dto);
	}

	public void postProcessCreate(ReportServerJobFilterDto dto, ReportServerJobFilter poso)  {
	}


	public void postProcessCreateUnmanaged(ReportServerJobFilterDto dto, ReportServerJobFilter poso)  {
	}


	public void postProcessLoad(ReportServerJobFilterDto dto, ReportServerJobFilter poso)  {
	}


	public void postProcessMerge(ReportServerJobFilterDto dto, ReportServerJobFilter poso)  {
	}


	public void postProcessInstantiate(ReportServerJobFilter poso)  {
	}



}
