package fr.legrain.abonnement.dao;

// Generated Mar 25, 2009 10:06:50 AM by Hibernate Tools 3.2.0.CR1

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import fr.legrain.articles.dao.TaUnite;
import fr.legrain.gestCom.Appli.AbstractApplicationDAO;
import fr.legrain.gestCom.Appli.Const;
import fr.legrain.lib.data.ExceptLgr;

/**
 * Home object for domain model class TaCPaiement.
 * @see fr.legrain.tiers.dao.test.TaCPaiement
 * @author Hibernate Tools
 */
public class TaRAbonnementDAO extends AbstractApplicationDAO<TaAbonnement>{

	static Logger logger = Logger.getLogger(TaRAbonnementDAO.class);
	
	private String defaultJPQLQuery = "select a from TaAbonnement a";
	
	public TaRAbonnementDAO() {
		this(null);
	}
	
	public TaRAbonnementDAO(EntityManager em){
		if(em!=null) {
			setEm(em);
		}
		initChampId(TaAbonnement.class);
		setJPQLQuery(defaultJPQLQuery);
		setListeChampMaj(Const.C_FICHIER_INI_CHAMPMAJ);
		initNomTable(new TaAbonnement());
	}
	
	public void persist(TaAbonnement transientInstance) {
		logger.debug("persisting TaAbonnement instance");
		try {
			getEntityManager().persist(transientInstance);
			logger.debug("persist successful");
		} catch (RuntimeException re) {
			logger.error("persist failed", re);
			throw re;
		}
	}
	
	public TaAbonnement refresh(TaAbonnement detachedInstance) {
		logger.debug("refresh instance");
		try {
			org.hibernate.Session session = (org.hibernate.Session) getEntityManager().getDelegate();
		    session.evict(detachedInstance);
		    detachedInstance = getEntityManager().find(TaAbonnement.class, detachedInstance.getIdAbonnement());
		    return detachedInstance;
		} catch (RuntimeException re) {
			logger.error("refresh failed", re);
			throw re;
		}
	}

	public void remove(TaAbonnement persistentInstance) {
		logger.debug("removing TaAbonnement instance");
		try {
			getEntityManager().remove(persistentInstance);
			logger.debug("remove successful");
		} catch (RuntimeException re) {
			logger.error("remove failed", re);
			throw re;
		}
	}

	public TaAbonnement merge(TaAbonnement detachedInstance) {
		logger.debug("merging TaAbonnement instance");
		try {
			TaAbonnement result = getEntityManager().merge(detachedInstance);
			logger.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			logger.error("merge failed", re);
			throw re;
		}
	}

	public TaAbonnement findById(int id) {
		logger.debug("getting TaAbonnement instance with id: " + id);
		try {
			TaAbonnement instance = getEntityManager().find(TaAbonnement.class, id);
			logger.debug("get successful");
			return instance;
		} catch (RuntimeException re) {
			logger.error("get failed", re);
			throw re;
		}
	}
	
	public TaAbonnement findByCode(String code) {
		logger.debug("getting TaAbonnement instance with code: " + code);
		try {
			if(!code.equals("")){
			Query query = getEntityManager().createQuery("select f from TaAbonnement f where f.codeAbonnement='"+code+"'");
			TaAbonnement instance = (TaAbonnement)query.getSingleResult();
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
	public List<TaAbonnement> selectAll() {
		// TODO Auto-generated method stub
		logger.debug("selectAll TaAbonnement");
		try {
			Query ejbQuery = getEntityManager().createQuery(JPQLQuery);
			List<TaAbonnement> l = ejbQuery.getResultList();
			logger.debug("selectAll successful");
			return l;
		} catch (RuntimeException re) {
			logger.error("selectAll failed", re);
			throw re;
		}
	}
	

}
