package fr.legrain.saisieCaisse.dao;

// Generated 2 juin 2009 14:13:00 by Hibernate Tools 3.2.4.GA

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import fr.legrain.articles.dao.TaArticle;
import fr.legrain.articles.dao.TaUnite;
import fr.legrain.documents.dao.TaInfosApporteur;
import fr.legrain.gestCom.Appli.AbstractApplicationDAO;
import fr.legrain.gestCom.Appli.Const;

/**
 * Home object for domain model class TaTOperation.
 * @see fr.legrain.saisieCaisse.dao.TaTOperation
 * @author Hibernate Tools
 */
public class TaTOperationDAO extends AbstractApplicationDAO<TaTOperation> {

	private static final Log logger = LogFactory.getLog(TaTOperationDAO.class);
	private String defaultJPQLQuery = "select u from TaTOperation u";

	public TaTOperationDAO(){
		this(null);
	}
	
	public TaTOperationDAO(EntityManager em){
		if(em!=null) {
			setEm(em);
		}
//		champIdTable=ctrlGeneraux.getID_TABLE(TaTOperation.class.getSimpleName());
		initChampId(TaTOperation.class);
		setJPQLQuery(defaultJPQLQuery);
		setListeChampMaj(Const.C_FICHIER_INI_CHAMPMAJ);
		initNomTable(new TaTOperation());
	}
	
	public void persist(TaTOperation transientInstance) {
		logger.debug("persisting TaTOperation instance");
		try {
			getEntityManager().persist(transientInstance);
			logger.debug("persist successful");
		} catch (RuntimeException re) {
			logger.error("persist failed", re);
			throw re;
		}
	}

	public TaTOperation refresh(TaTOperation detachedInstance) {
		logger.debug("refresh instance");
		try {
////			getEntityManager().refresh(detachedInstance);
////			logger.debug("refresh successful");
//			org.hibernate.Session session = (org.hibernate.Session) getEntityManager().getDelegate();
//		    session.evict(detachedInstance);
//		    detachedInstance = getEntityManager().find(TaTOperation.class, detachedInstance.getIdTOperation());
		    return detachedInstance;
		} catch (RuntimeException re) {
			logger.error("refresh failed", re);
			throw re;
		}
	}
	
	public void remove(TaTOperation persistentInstance) {
		logger.debug("removing TaTOperation instance");
		try {
			getEntityManager().remove(persistentInstance);
			logger.debug("remove successful");
		} catch (RuntimeException re) {
			logger.error("remove failed", re);
			throw re;
		}
	}

	public TaTOperation merge(TaTOperation detachedInstance) {
		logger.debug("merging TaTOperation instance");
		try {
			TaTOperation result = getEntityManager().merge(detachedInstance);
			logger.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			logger.error("merge failed", re);
			throw re;
		}
	}

	public TaTOperation findById(int id) {
		logger.debug("getting TaTOperation instance with id: " + id);
		try {
			TaTOperation instance = getEntityManager().find(TaTOperation.class, id);
			logger.debug("get successful");
			return instance;
		} catch (RuntimeException re) {
			logger.error("get failed", re);
			throw re;
		}
	}

	@Override
	public List<TaTOperation> selectAll() {
		logger.debug("TaTOperation TaDepot");
		try {
			Query ejbQuery = getEntityManager().createQuery(JPQLQuery);
			List<TaTOperation> l = ejbQuery.getResultList();
			logger.debug("selectAll successful");
			return l;
		} catch (RuntimeException re) {
			logger.error("selectAll failed", re);
			throw re;
		}
	}
	
	public TaTOperation findByCode(String code) {
		logger.debug("getting TaTOperation instance with code: " + code);
		try {
			if(!code.equals("")){
			Query query = getEntityManager().createQuery("select a from TaTOperation a " +
					"where a.codeTOperation='"+code+"'");
			TaTOperation instance = (TaTOperation)query.getSingleResult();
			logger.debug("get successful");
			return instance;
			}
			return null;
		} catch (RuntimeException re) {
			logger.error("get failed", re);
			throw re;
		}
	}
}
