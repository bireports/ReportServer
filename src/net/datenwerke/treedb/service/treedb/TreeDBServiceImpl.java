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
 
 
package net.datenwerke.treedb.service.treedb;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.FlushModeType;
import javax.persistence.Query;

import net.datenwerke.rs.utils.reflection.ProxyUtils;
import net.datenwerke.rs.utils.reflection.ReflectionService;
import net.datenwerke.treedb.service.treedb.annotation.TreeDBTree;
import net.datenwerke.treedb.service.treedb.exceptions.MultipleRootException;
import net.datenwerke.treedb.service.treedb.exceptions.TreeDBCorruptException;
import net.datenwerke.treedb.service.treedb.exceptions.UnsupportedRootTypeException;
import net.datenwerke.treedb.service.treedb.locale.TreeDbMessages;

import org.hibernate.proxy.HibernateProxy;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;

/**
 * 
 *
 */
@Singleton
public class TreeDBServiceImpl implements TreeDBService {

	private final Provider<EntityManager> enitityManagerProvider;
	private final ProxyUtils proxyUtils;
	private final ReflectionService reflectionService;
	
	@Inject
	public TreeDBServiceImpl(
		Provider<EntityManager> emp,
		ProxyUtils proxyUtils, 
		ReflectionService reflectionService
		){
		
		this.enitityManagerProvider = emp;
		this.proxyUtils = proxyUtils;
		this.reflectionService = reflectionService;
	}
	
	@Override
	public <N extends AbstractNode<N>> void persist(N node) {
		/* if node is to be a root, run tests */
		if(node.isRoot()){
			/* get root annotation */
			Class<?> base = node.getBaseType();
			TreeDBTree rootAnno = (TreeDBTree) base.getAnnotation(TreeDBTree.class);
	
			/* test if type is in array of allowed types */
			if(null == rootAnno)
				throw new UnsupportedRootTypeException(node.getClass());
			boolean found = false;
			for(Class<?> possibleTyoe : rootAnno.rootTypes()){
				if(possibleTyoe.isAssignableFrom(node.getClass())){
					found = true;
					break;
				}
			}
			if(! found)
				throw new UnsupportedRootTypeException(node.getClass());
			
			/* check for multiple roots */
			if(! rootAnno.multipleRoots()){
				EntityManager em = enitityManagerProvider.get();
				Query q = em.createQuery("SELECT p FROM " + base.getSimpleName() + " p WHERE p." + AbstractNode__.parent + " = null"); //$NON-NLS-1$ //$NON-NLS-2$
				
				/* set flush mode as to not end up in endless loop */
				q.setFlushMode(FlushModeType.COMMIT);
				
				List results = q.getResultList();
				if(results.size() > 1){
					MultipleRootException mre = new MultipleRootException();
					TreeDBCorruptException tce = new TreeDBCorruptException();
					tce.initCause(mre);
					throw tce;
				} else if(results.size() == 1){
					if(! ((AbstractNode)results.get(0)).getId().equals(node.getId())){
						throw new MultipleRootException(node.getBaseType());
					}
				}
			}
		}
		
		EntityManager em = enitityManagerProvider.get();
		em.persist(node);
	}
	
	@Override
	public <N extends AbstractNode<N>> N merge(N node) {
		/* test for write protection */
		EntityManager em = enitityManagerProvider.get();
		N pNode = (N) em.find(proxyUtils.getUnproxiedClass(node.getClass()), node.getId());
		if(null == node)
			throw new IllegalArgumentException();
		if(pNode.isWriteProtected() || pNode.isConfigurationProtected())
			throw new IllegalArgumentException(TreeDbMessages.INSTANCE.exceptionNodeIsWriteProtected());
		
		node = enitityManagerProvider.get().merge(node);

		return node;
	}
	
	@Override
	public  <N extends AbstractNode<N>> N updateFlags(N node, long flags) {
		EntityManager em = enitityManagerProvider.get();
		node = (N) em.find(proxyUtils.getUnproxiedClass(node.getClass()), node.getId());
		if(null == node)
			throw new IllegalArgumentException();
		
		node.clearFlags();
		node.setFlags(flags);
		
		node = enitityManagerProvider.get().merge(node);
		
		return node;
	}
	
	@Override
	public <N extends AbstractNode<N>> void remove(N node) {
		if(node instanceof HibernateProxy)
			node = (N) ((HibernateProxy)node).getHibernateLazyInitializer().getImplementation();
		
		EntityManager em = enitityManagerProvider.get();
		node = (N) em.find(node.getClass(), node.getId());
		if(null == node)
			return;
		if(node.isWriteProtected() || node.isConfigurationProtected())
			throw new IllegalArgumentException(TreeDbMessages.INSTANCE.exceptionNodeIsWriteProtected());
		
		while(node.hasChildren()){
			N child = node.getChildren().get(0);
			remove(child);
		}

		N parent = node.getParent();
		if(null != parent)
			parent.removeChild(node);
		
		em.remove(node);
	}
	
	@Override
	public <A extends AbstractNode<?>> Class<? extends TreeDBManager<? extends A>> getManagerClassForNode(Class<A> nodeType) {
		TreeDBTree annotationRecursive =  reflectionService.getAnnotationRecursive(TreeDBTree.class, nodeType);
		
		return (Class<? extends TreeDBManager<? extends A>>) annotationRecursive.manager();
	}

}
