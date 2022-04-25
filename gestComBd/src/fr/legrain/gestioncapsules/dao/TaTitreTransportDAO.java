package fr.legrain.gestioncapsules.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import fr.legrain.articles.dao.TaArticle;
import fr.legrain.gestCom.Appli.AbstractApplicationDAO;
import fr.legrain.gestCom.Appli.Const;
import fr.legrain.lib.data.ExceptLgr;

/**
 * Home object for domain model class TaTNoteArticle.
 * @see fr.legrain.tiers.dao.TaTitreTransport
 * @author Hibernate Tools
 */
public class TaTitreTransportDAO extends AbstractApplicationDAO<TaTitreTransport>{

	static Logger logger = Logger.getLogger(TaTitreTransport.class);

	private String defaultJPQLQuery = "select a from TaTitreTransport a";
	
	public TaTitreTransportDAO() {
		this(null);
	}
	
	public TaTitreTransportDAO(EntityManager em){
		if(em!=null) {
			setEm(em);
		}
//		champIdTable=ctrlGeneraux.getID_TABLE(TaTNoteArticle.class.getSimpleName());
		initChampId(TaTitreTransport.class);
		setJPQLQuery(defaultJPQLQuery);
		setListeChampMaj(Const.C_FICHIER_INI_CHAMPMAJ);
		initNomTable(new TaTitreTransport());
	}
	public TaTitreTransport refresh(TaTitreTransport detachedInstance) {
		logger.debug("refresh instance");
		try {
			org.hibernate.Session session = (org.hibernate.Session) getEntityManager().getDelegate();
		    session.evict(detachedInstance);
		    detachedInstance = getEntityManager().find(TaTitreTransport.class, detachedInstance.getIdTitreTransport());
		    return detachedInstance;
		} catch (RuntimeException re) {
			logger.error("refresh failed", re);
			throw re;
		}
	}
	public void persist(TaTitreTransport transientInstance) {
		logger.debug("persisting TaTitreTransport instance");
		try {
			getEntityManager().persist(transientInstance);
			logger.debug("persist successful");
		} catch (RuntimeException re) {
			logger.error("persist failed", re);
			throw re;
		}
	}

	public void remove(TaTitreTransport persistentInstance) {
		logger.debug("removing TaTitreTransport instance");
		try {
			getEntityManager().remove(persistentInstance);
			logger.debug("remove successful");
		} catch (RuntimeException re) {
			logger.error("remove failed", re);
			throw re;
		}
	}

	public TaTitreTransport merge(TaTitreTransport detachedInstance) {
		logger.debug("merging TaTitreTransport instance");
		try {
			TaTitreTransport result = getEntityManager().merge(detachedInstance);
			logger.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			logger.error("merge failed", re);
			throw re;
		}
	}

	public TaTitreTransport findById(int id) {
		logger.debug("getting TaTitreTransport instance with id: " + id);
		try {
			TaTitreTransport instance = getEntityManager().find(TaTitreTransport.class, id);
			logger.debug("get successful");
			return instance;
		} catch (RuntimeException re) {
			logger.error("get failed", re);
			throw re;
		}
	}
	
	@Override
	public List<TaTitreTransport> selectAll() {
		// TODO Auto-generated method stub
		logger.debug("selectAll TaTitreTransport");
		try {
			Query ejbQuery = getEntityManager().createQuery(JPQLQuery);
			List<TaTitreTransport> l = ejbQuery.getResultList();
			logger.debug("selectAll successful");
			return l;
		} catch (RuntimeException re) {
			logger.error("selectAll failed", re);
			throw re;
		}
	}
	
	public TaTitreTransport findByCode(String code) {
		logger.debug("getting TaTitreTransport instance with code: " + code);
		try {
			if(!code.equals("")){
			Query query = getEntityManager().createQuery("select a from TaTitreTransport a where a.codeTitreTransport='"+code+"'");
			TaTitreTransport instance = (TaTitreTransport)query.getSingleResult();
			logger.debug("get successful");
			return instance;
			}
			return null;
		} catch (RuntimeException re) {
			logger.error("get failed", re);
			throw re;
		}
	}	
	
	public void ctrlSaisieSpecifique(TaTitreTransport entity,String field) throws ExceptLgr {	
		
	}
}
