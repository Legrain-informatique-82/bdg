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
public class TaRegistrarDAO extends AbstractApplicationDAO<TaServeur>{

	static Logger logger = Logger.getLogger(TaRegistrarDAO.class);
	
	private String defaultJPQLQuery = "select a from TaServeur a";
	
	public TaRegistrarDAO() {
		this(null);
	}
	
	public TaRegistrarDAO(EntityManager em){
		if(em!=null) {
			setEm(em);
		}
		initChampId(TaServeur.class);
		setJPQLQuery(defaultJPQLQuery);
		setListeChampMaj(Const.C_FICHIER_INI_CHAMPMAJ);
		initNomTable(new TaServeur());
	}
	
	public void persist(TaServeur transientInstance) {
		logger.debug("persisting TaServeur instance");
		try {
			getEntityManager().persist(transientInstance);
			logger.debug("persist successful");
		} catch (RuntimeException re) {
			logger.error("persist failed", re);
			throw re;
		}
	}
	
	public TaServeur refresh(TaServeur detachedInstance) {
		logger.debug("refresh instance");
		try {
			org.hibernate.Session session = (org.hibernate.Session) getEntityManager().getDelegate();
		    session.evict(detachedInstance);
		    detachedInstance = getEntityManager().find(TaServeur.class, detachedInstance.getIdServeur());
		    return detachedInstance;
		} catch (RuntimeException re) {
			logger.error("refresh failed", re);
			throw re;
		}
	}

	public void remove(TaServeur persistentInstance) {
		logger.debug("removing TaServeur instance");
		try {
			getEntityManager().remove(persistentInstance);
			logger.debug("remove successful");
		} catch (RuntimeException re) {
			logger.error("remove failed", re);
			throw re;
		}
	}

	public TaServeur merge(TaServeur detachedInstance) {
		logger.debug("merging TaServeur instance");
		try {
			TaServeur result = getEntityManager().merge(detachedInstance);
			logger.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			logger.error("merge failed", re);
			throw re;
		}
	}

	public TaServeur findById(int id) {
		logger.debug("getting TaServeur instance with id: " + id);
		try {
			TaServeur instance = getEntityManager().find(TaServeur.class, id);
			logger.debug("get successful");
			return instance;
		} catch (RuntimeException re) {
			logger.error("get failed", re);
			throw re;
		}
	}
	
	public TaServeur findByCode(String code) {
		logger.debug("getting TaServeur instance with code: " + code);
		try {
			if(!code.equals("")){
			Query query = getEntityManager().createQuery("select f from TaServeur f where f.serveur='"+code+"'");
			TaServeur instance = (TaServeur)query.getSingleResult();
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
	public List<TaServeur> selectAll() {
		// TODO Auto-generated method stub
		logger.debug("selectAll TaServeur");
		try {
			Query ejbQuery = getEntityManager().createQuery(JPQLQuery);
			List<TaServeur> l = ejbQuery.getResultList();
			logger.debug("selectAll successful");
			return l;
		} catch (RuntimeException re) {
			logger.error("selectAll failed", re);
			throw re;
		}
	}
	

}
