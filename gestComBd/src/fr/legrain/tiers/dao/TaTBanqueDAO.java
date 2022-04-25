package fr.legrain.tiers.dao;

// Generated Mar 25, 2009 10:06:50 AM by Hibernate Tools 3.2.0.CR1

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import fr.legrain.articles.dao.TaUnite;
import fr.legrain.gestCom.Appli.AbstractApplicationDAO;
import fr.legrain.gestCom.Appli.Const;
import fr.legrain.lib.data.ExceptLgr;

/**
 * Home object for domain model class TaTBanque.
 * @see fr.legrain.tiers.dao.test.TaTBanque
 * @author Hibernate Tools
 */
public class TaTBanqueDAO extends AbstractApplicationDAO<TaTBanque>{

	//private static final Log logger = LogFactory.getLog(TaTBanqueDAO.class);
	static final Log logger = LogFactory.getLog(TaTBanqueDAO.class);
	
	private String defaultJPQLQuery = "select a from TaTBanque a";
	
	public TaTBanqueDAO() {
		this(null);
	}

	public TaTBanqueDAO(EntityManager em){
		if(em!=null) {
			setEm(em);
		}
//		champIdTable=ctrlGeneraux.getID_TABLE(TaTBanque.class.getSimpleName());
		initChampId(TaTBanque.class);
		setJPQLQuery(defaultJPQLQuery);
		setListeChampMaj(Const.C_FICHIER_INI_CHAMPMAJ);
		initNomTable(new TaTBanque());
	}
	public TaTBanque refresh(TaTBanque detachedInstance) {
		logger.debug("refresh instance");
		try {
			org.hibernate.Session session = (org.hibernate.Session) getEntityManager().getDelegate();
		    session.evict(detachedInstance);
		    detachedInstance = getEntityManager().find(TaTBanque.class, detachedInstance.getIdTBanque());
		    return detachedInstance;
		} catch (RuntimeException re) {
			logger.error("refresh failed", re);
			throw re;
		}
	}

	public void persist(TaTBanque transientInstance) {
		logger.debug("persisting TaTBanque instance");
		try {
			getEntityManager().persist(transientInstance);
			logger.debug("persist successful");
		} catch (RuntimeException re) {
			logger.error("persist failed", re);
			throw re;
		}
	}

	public void remove(TaTBanque persistentInstance) {
		logger.debug("removing TaTBanque instance");
		try {
			getEntityManager().remove(persistentInstance);
			logger.debug("remove successful");
		} catch (RuntimeException re) {
			logger.error("remove failed", re);
			throw re;
		}
	}

	public TaTBanque merge(TaTBanque detachedInstance) {
		logger.debug("merging TaTBanque instance");
		try {
			TaTBanque result = getEntityManager().merge(detachedInstance);
			logger.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			logger.error("merge failed", re);
			throw re;
		}
	}

	public TaTBanque findById(int id) {
		logger.debug("getting TaTBanque instance with id: " + id);
		try {
			TaTBanque instance = getEntityManager().find(TaTBanque.class, id);
			logger.debug("get successful");
			return instance;
		} catch (RuntimeException re) {
			logger.error("get failed", re);
			throw re;
		}
	}
	public TaTBanque findByCode(String code) {
		logger.debug("getting TaTBanque instance with code: " + code);
		try {
			if(!code.equals("")){
			Query query = getEntityManager().createQuery("select a from TaTBanque a where a.codeTBanque='"+code+"'");
			TaTBanque instance = (TaTBanque)query.getSingleResult();
			logger.debug("get successful");
			return instance;
			}
			return null;
		} catch (RuntimeException re) {
			logger.error("get failed", re);
			throw re;
		}
	}
	@Override
	public List<TaTBanque> selectAll() {
		// TODO Auto-generated method stub
		logger.debug("selectAll TaTBanque");
		try {
			Query ejbQuery = getEntityManager().createQuery(JPQLQuery);
			List<TaTBanque> l = ejbQuery.getResultList();
			logger.debug("selectAll successful");
			return l;
		} catch (RuntimeException re) {
			logger.error("selectAll failed", re);
			throw re;
		}
	}
	public void ctrlSaisieSpecifique(TaTBanque entity,String field) throws ExceptLgr {	
		
	}
}
