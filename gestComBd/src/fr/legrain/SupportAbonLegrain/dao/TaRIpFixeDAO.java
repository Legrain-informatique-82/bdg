package fr.legrain.SupportAbonLegrain.dao;

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
public   class TaRIpFixeDAO extends AbstractApplicationDAO<TaRIpFixe>{

	static Logger logger = Logger.getLogger(TaRIpFixeDAO.class);
	
	private String defaultJPQLQuery = "select a from TaRIpFixe a";
	
	public TaRIpFixeDAO() {
		this(null);
	}
	
	public TaRIpFixeDAO(EntityManager em){
		if(em!=null) {
			setEm(em);
		}
		initChampId(TaRIpFixe.class);
		setJPQLQuery(defaultJPQLQuery);
		setListeChampMaj(Const.C_FICHIER_INI_CHAMPMAJ);
		initNomTable(new TaRIpFixe());
	}
	
	public void persist(TaRIpFixe transientInstance) {
		logger.debug("persisting TaRIpFixe instance");
		try {
			getEntityManager().persist(transientInstance);
			logger.debug("persist successful");
		} catch (RuntimeException re) {
			logger.error("persist failed", re);
			throw re;
		}
	}
	
	public TaRIpFixe refresh(TaRIpFixe detachedInstance) {
		logger.debug("refresh instance");
		try {
			org.hibernate.Session session = (org.hibernate.Session) getEntityManager().getDelegate();
		    session.evict(detachedInstance);
		    detachedInstance = getEntityManager().find(TaRIpFixe.class, detachedInstance.getIdIpFixe());
		    return detachedInstance;
		} catch (RuntimeException re) {
			logger.error("refresh failed", re);
			throw re;
		}
	}

	public void remove(TaRIpFixe persistentInstance) {
		logger.debug("removing TaRIpFixe instance");
		try {
			getEntityManager().remove(persistentInstance);
			logger.debug("remove successful");
		} catch (RuntimeException re) {
			logger.error("remove failed", re);
			throw re;
		}
	}

	public TaRIpFixe merge(TaRIpFixe detachedInstance) {
		logger.debug("merging TaRIpFixe instance");
		try {
			TaRIpFixe result = getEntityManager().merge(detachedInstance);
			logger.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			logger.error("merge failed", re);
			throw re;
		}
	}

	public TaRIpFixe findById(int id) {
		logger.debug("getting TaRIpFixe instance with id: " + id);
		try {
			TaRIpFixe instance = getEntityManager().find(TaRIpFixe.class, id);
			logger.debug("get successful");
			return instance;
		} catch (RuntimeException re) {
			logger.error("get failed", re);
			throw re;
		}
	}
	
	public TaRIpFixe findByCode(String code) {
		logger.debug("getting TaRIpFixe instance with code: " + code);
		try {
			if(!code.equals("")){
			Query query = getEntityManager().createQuery("select f from TaRIpFixe f where" +
					"f.optionAbon='"+code+"'");
			TaRIpFixe instance = (TaRIpFixe)query.getSingleResult();
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
	public List<TaRIpFixe> selectAll() {
		// TODO Auto-generated method stub
		logger.debug("selectAll TaRIpFixe");
		try {
			Query ejbQuery = getEntityManager().createQuery(JPQLQuery);
			List<TaRIpFixe> l = ejbQuery.getResultList();
			logger.debug("selectAll successful");
			return l;
		} catch (RuntimeException re) {
			logger.error("selectAll failed", re);
			throw re;
		}
	}
	

}
