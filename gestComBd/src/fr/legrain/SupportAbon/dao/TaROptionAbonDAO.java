package fr.legrain.SupportAbon.dao;

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
public   class TaROptionAbonDAO extends AbstractApplicationDAO<TaROptionAbon>{

	static Logger logger = Logger.getLogger(TaROptionAbonDAO.class);
	
	private String defaultJPQLQuery = "select a from TaRIpFixe a";
	
	public TaROptionAbonDAO() {
		this(null);
	}
	
	public TaROptionAbonDAO(EntityManager em){
		if(em!=null) {
			setEm(em);
		}
		initChampId(TaROptionAbon.class);
		setJPQLQuery(defaultJPQLQuery);
		setListeChampMaj(Const.C_FICHIER_INI_CHAMPMAJ);
		initNomTable(new TaROptionAbon());
	}
	
	public void persist(TaROptionAbon transientInstance) {
		logger.debug("persisting TaRIpFixe instance");
		try {
			getEntityManager().persist(transientInstance);
			logger.debug("persist successful");
		} catch (RuntimeException re) {
			logger.error("persist failed", re);
			throw re;
		}
	}
	
	public TaROptionAbon refresh(TaROptionAbon detachedInstance) {
		logger.debug("refresh instance");
		try {
			org.hibernate.Session session = (org.hibernate.Session) getEntityManager().getDelegate();
		    session.evict(detachedInstance);
		    detachedInstance = getEntityManager().find(TaROptionAbon.class, detachedInstance.getIdROptionAbon());
		    return detachedInstance;
		} catch (RuntimeException re) {
			logger.error("refresh failed", re);
			throw re;
		}
	}

	public void remove(TaROptionAbon persistentInstance) {
		logger.debug("removing TaRIpFixe instance");
		try {
			getEntityManager().remove(persistentInstance);
			logger.debug("remove successful");
		} catch (RuntimeException re) {
			logger.error("remove failed", re);
			throw re;
		}
	}

	public TaROptionAbon merge(TaROptionAbon detachedInstance) {
		logger.debug("merging TaRIpFixe instance");
		try {
			TaROptionAbon result = getEntityManager().merge(detachedInstance);
			logger.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			logger.error("merge failed", re);
			throw re;
		}
	}

	public TaROptionAbon findById(int id) {
		logger.debug("getting TaRIpFixe instance with id: " + id);
		try {
			TaROptionAbon instance = getEntityManager().find(TaROptionAbon.class, id);
			logger.debug("get successful");
			return instance;
		} catch (RuntimeException re) {
			logger.error("get failed", re);
			throw re;
		}
	}
	
	public TaROptionAbon findByCode(String code) {
		logger.debug("getting TaRIpFixe instance with code: " + code);
		try {
			if(!code.equals("")){
			Query query = getEntityManager().createQuery("select f from TaRIpFixe f where" +
					"f.optionAbon='"+code+"'");
			TaROptionAbon instance = (TaROptionAbon)query.getSingleResult();
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
	public List<TaROptionAbon> selectAll() {
		// TODO Auto-generated method stub
		logger.debug("selectAll TaRIpFixe");
		try {
			Query ejbQuery = getEntityManager().createQuery(JPQLQuery);
			List<TaROptionAbon> l = ejbQuery.getResultList();
			logger.debug("selectAll successful");
			return l;
		} catch (RuntimeException re) {
			logger.error("selectAll failed", re);
			throw re;
		}
	}
	

}
