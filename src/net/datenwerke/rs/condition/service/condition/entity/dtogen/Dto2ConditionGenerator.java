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
 
 
package net.datenwerke.rs.condition.service.condition.entity.dtogen;

import com.google.inject.Inject;
import com.google.inject.Provider;
import java.lang.Exception;
import java.lang.RuntimeException;
import java.lang.reflect.Field;
import java.util.Collection;
import javax.persistence.EntityManager;
import net.datenwerke.dtoservices.dtogenerator.annotations.GeneratedType;
import net.datenwerke.dtoservices.dtogenerator.dto2posogenerator.interfaces.Dto2PosoGenerator;
import net.datenwerke.dtoservices.dtogenerator.dto2posogenerator.validator.DtoPropertyValidator;
import net.datenwerke.gxtdto.client.servercommunication.exceptions.ExpectedException;
import net.datenwerke.gxtdto.server.dtomanager.DtoMainService;
import net.datenwerke.gxtdto.server.dtomanager.DtoService;
import net.datenwerke.rs.base.client.reportengines.table.dto.TableReportDto;
import net.datenwerke.rs.base.service.reportengines.table.entities.TableReport;
import net.datenwerke.rs.condition.client.condition.dto.ConditionDto;
import net.datenwerke.rs.condition.service.condition.entity.Condition;
import net.datenwerke.rs.condition.service.condition.entity.dtogen.Dto2ConditionGenerator;
import net.datenwerke.rs.utils.entitycloner.annotation.TransientID;
import net.datenwerke.rs.utils.reflection.ReflectionService;

/**
 * Dto2PosoGenerator for Condition
 *
 * This file was automatically created by DtoAnnotationProcessor, version 0.1
 */
@GeneratedType("net.datenwerke.dtoservices.dtogenerator.DtoAnnotationProcessor")
public class Dto2ConditionGenerator implements Dto2PosoGenerator<ConditionDto,Condition> {

	private final Provider<DtoService> dtoServiceProvider;

	private final Provider<EntityManager> entityManagerProvider;

	private final net.datenwerke.dtoservices.dtogenerator.dto2posogenerator.interfaces.Dto2PosoSupervisorDefaultImpl dto2PosoSupervisor;

	private final ReflectionService reflectionService;
	@Inject
	public Dto2ConditionGenerator(
		net.datenwerke.dtoservices.dtogenerator.dto2posogenerator.interfaces.Dto2PosoSupervisorDefaultImpl dto2PosoSupervisor,
		Provider<DtoService> dtoServiceProvider,
		Provider<EntityManager> entityManagerProvider,
		ReflectionService reflectionService
	){
		this.dto2PosoSupervisor = dto2PosoSupervisor;
		this.dtoServiceProvider = dtoServiceProvider;
		this.entityManagerProvider = entityManagerProvider;
		this.reflectionService = reflectionService;
	}

	public Condition loadPoso(ConditionDto dto)  {
		if(null == dto)
			return null;

		/* get id */
		Object id = dto.getId();
		if(null == id)
			return null;

		/* load poso from db */
		EntityManager entityManager = entityManagerProvider.get();
		Condition poso = entityManager.find(Condition.class, id);
		return poso;
	}

	public Condition instantiatePoso()  {
		Condition poso = new Condition();
		return poso;
	}

	public Condition createPoso(ConditionDto dto)  throws ExpectedException {
		Condition poso = new Condition();

		/* merge data */
		mergePoso(dto, poso);
		return poso;
	}

	public Condition createUnmanagedPoso(ConditionDto dto)  throws ExpectedException {
		Condition poso = new Condition();

		/* store old id */
		if(null != dto.getId()){
			Field transientIdField = reflectionService.getFieldByAnnotation(poso, TransientID.class);
			if(null != transientIdField){
				transientIdField.setAccessible(true);
				try{
					transientIdField.set(poso, dto.getId());
				} catch(Exception e){
				}
			}
		}

		mergePlainDto2UnmanagedPoso(dto,poso);

		return poso;
	}

	public void mergePoso(ConditionDto dto, final Condition poso)  throws ExpectedException {
		if(dto.isDtoProxy())
			mergeProxy2Poso(dto, poso);
		else
			mergePlainDto2Poso(dto, poso);
	}

	protected void mergePlainDto2Poso(ConditionDto dto, final Condition poso)  throws ExpectedException {
		/*  set description */
		poso.setDescription(dto.getDescription() );

		/*  set key */
		poso.setKey(dto.getKey() );

		/*  set name */
		poso.setName(dto.getName() );

		/*  set report */
		TableReportDto tmpDto_report = dto.getReport();
		if(null != tmpDto_report && null != tmpDto_report.getId()){
			if(null != poso.getReport() && null != poso.getReport().getId() && ! poso.getReport().getId().equals(tmpDto_report.getId())){
				TableReport newPropertyValue = (TableReport)dtoServiceProvider.get().loadPoso(tmpDto_report);
				dto2PosoSupervisor.referencedObjectRemoved(dto, poso, poso.getReport(), newPropertyValue, "report");
				poso.setReport(newPropertyValue);
			} else if(null == poso.getReport()){
				poso.setReport((TableReport)dtoServiceProvider.get().loadPoso(tmpDto_report));
			}
		} else if(null != tmpDto_report && null == tmpDto_report.getId()){
			((DtoMainService)dtoServiceProvider.get()).getCreationHelper().onPosoCreation(tmpDto_report, new net.datenwerke.gxtdto.server.dtomanager.CallbackOnPosoCreation(){
				public void callback(Object refPoso){
					if(null == refPoso)
						throw new IllegalArgumentException("referenced dto should have an id (report)");
					poso.setReport((TableReport)refPoso);
				}
			});
		} else if(null == tmpDto_report){
			dto2PosoSupervisor.referencedObjectRemoved(dto, poso, poso.getReport(), null, "report");
			poso.setReport(null);
		}

	}

	protected void mergeProxy2Poso(ConditionDto dto, final Condition poso)  throws ExpectedException {
		/*  set description */
		if(dto.isDescriptionModified()){
			poso.setDescription(dto.getDescription() );
		}

		/*  set key */
		if(dto.isKeyModified()){
			poso.setKey(dto.getKey() );
		}

		/*  set name */
		if(dto.isNameModified()){
			poso.setName(dto.getName() );
		}

		/*  set report */
		if(dto.isReportModified()){
			TableReportDto tmpDto_report = dto.getReport();
			if(null != tmpDto_report && null != tmpDto_report.getId()){
				if(null != poso.getReport() && null != poso.getReport().getId() && ! poso.getReport().getId().equals(tmpDto_report.getId())){
					TableReport newPropertyValue = (TableReport)dtoServiceProvider.get().loadPoso(tmpDto_report);
					dto2PosoSupervisor.referencedObjectRemoved(dto, poso, poso.getReport(), newPropertyValue, "report");
					poso.setReport(newPropertyValue);
				} else if(null == poso.getReport()){
					poso.setReport((TableReport)dtoServiceProvider.get().loadPoso(tmpDto_report));
				}
			} else if(null != tmpDto_report && null == tmpDto_report.getId()){
			((DtoMainService)dtoServiceProvider.get()).getCreationHelper().onPosoCreation(tmpDto_report, new net.datenwerke.gxtdto.server.dtomanager.CallbackOnPosoCreation(){
					public void callback(Object refPoso){
						if(null == refPoso)
							throw new IllegalArgumentException("referenced dto should have an id (report)");
						poso.setReport((TableReport)refPoso);
					}
			});
			} else if(null == tmpDto_report){
				dto2PosoSupervisor.referencedObjectRemoved(dto, poso, poso.getReport(), null, "report");
				poso.setReport(null);
			}
		}

	}

	public void mergeUnmanagedPoso(ConditionDto dto, final Condition poso)  throws ExpectedException {
		if(dto.isDtoProxy())
			mergeProxy2UnmanagedPoso(dto, poso);
		else
			mergePlainDto2UnmanagedPoso(dto, poso);
	}

	protected void mergePlainDto2UnmanagedPoso(ConditionDto dto, final Condition poso)  throws ExpectedException {
		/*  set description */
		poso.setDescription(dto.getDescription() );

		/*  set key */
		poso.setKey(dto.getKey() );

		/*  set name */
		poso.setName(dto.getName() );

		/*  set report */
		TableReportDto tmpDto_report = dto.getReport();
		if(null != tmpDto_report && null != tmpDto_report.getId()){
			TableReport newPropertyValue = (TableReport)dtoServiceProvider.get().loadPoso(tmpDto_report);
			poso.setReport(newPropertyValue);
			((DtoMainService)dtoServiceProvider.get()).getCreationHelper().onPosoCreation(tmpDto_report, new net.datenwerke.gxtdto.server.dtomanager.CallbackOnPosoCreation(){
				public void callback(Object refPoso){
					if(null != refPoso)
						poso.setReport((TableReport)refPoso);
				}
			});
		} else if(null != tmpDto_report && null == tmpDto_report.getId()){
			final TableReportDto tmpDto_report_final = tmpDto_report;
			((DtoMainService)dtoServiceProvider.get()).getCreationHelper().onPosoCreation(tmpDto_report, new net.datenwerke.gxtdto.server.dtomanager.CallbackOnPosoCreation(){
				public void callback(Object refPoso){
					if(null == refPoso){
						try{
							poso.setReport((TableReport)dtoServiceProvider.get().createUnmanagedPoso(tmpDto_report_final));
						} catch(ExpectedException e){
							throw new RuntimeException(e);
						}
					} else {
						poso.setReport((TableReport)refPoso);
					}
				}
			});
		} else if(null == tmpDto_report){
			poso.setReport(null);
		}

	}

	protected void mergeProxy2UnmanagedPoso(ConditionDto dto, final Condition poso)  throws ExpectedException {
		/*  set description */
		if(dto.isDescriptionModified()){
			poso.setDescription(dto.getDescription() );
		}

		/*  set key */
		if(dto.isKeyModified()){
			poso.setKey(dto.getKey() );
		}

		/*  set name */
		if(dto.isNameModified()){
			poso.setName(dto.getName() );
		}

		/*  set report */
		if(dto.isReportModified()){
			TableReportDto tmpDto_report = dto.getReport();
			if(null != tmpDto_report && null != tmpDto_report.getId()){
				TableReport newPropertyValue = (TableReport)dtoServiceProvider.get().loadPoso(tmpDto_report);
				poso.setReport(newPropertyValue);
			((DtoMainService)dtoServiceProvider.get()).getCreationHelper().onPosoCreation(tmpDto_report, new net.datenwerke.gxtdto.server.dtomanager.CallbackOnPosoCreation(){
					public void callback(Object refPoso){
						if(null != refPoso)
							poso.setReport((TableReport)refPoso);
					}
			});
			} else if(null != tmpDto_report && null == tmpDto_report.getId()){
				final TableReportDto tmpDto_report_final = tmpDto_report;
			((DtoMainService)dtoServiceProvider.get()).getCreationHelper().onPosoCreation(tmpDto_report, new net.datenwerke.gxtdto.server.dtomanager.CallbackOnPosoCreation(){
					public void callback(Object refPoso){
						if(null == refPoso){
							try{
								poso.setReport((TableReport)dtoServiceProvider.get().createUnmanagedPoso(tmpDto_report_final));
							} catch(ExpectedException e){
								throw new RuntimeException(e);
							}
						} else {
							poso.setReport((TableReport)refPoso);
						}
					}
			});
			} else if(null == tmpDto_report){
				poso.setReport(null);
			}
		}

	}

	public Condition loadAndMergePoso(ConditionDto dto)  throws ExpectedException {
		Condition poso = loadPoso(dto);
		if(null != poso){
			mergePoso(dto, poso);
			return poso;
		}
		return createPoso(dto);
	}

	public void postProcessCreate(ConditionDto dto, Condition poso)  {
	}


	public void postProcessCreateUnmanaged(ConditionDto dto, Condition poso)  {
	}


	public void postProcessLoad(ConditionDto dto, Condition poso)  {
	}


	public void postProcessMerge(ConditionDto dto, Condition poso)  {
	}


	public void postProcessInstantiate(Condition poso)  {
	}



}
