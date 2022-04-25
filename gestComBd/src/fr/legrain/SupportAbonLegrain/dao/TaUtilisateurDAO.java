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
public class TaUtilisateurDAO extends AbstractApplicationDAO<TaUtilisateur>{

	static Logger logger = Logger.getLogger(TaUtilisateurDAO.class);
	
	private String defaultJPQLQuery = "select a from TaUtilisateur a";
	
	public TaUtilisateurDAO() {
		this(null);
	}
	
	public TaUtilisateurDAO(EntityManager em){
		if(em!=null) {
			setEm(em);
		}
		initChampId(TaUtilisateur.class);
		setJPQLQuery(defaultJPQLQuery);
		setListeChampMaj(Const.C_FICHIER_INI_CHAMPMAJ);
		initNomTable(new TaUtilisateur());
	}
	
	public void persist(TaUtilisateur transientInstance) {
		logger.debug("persisting TaUtilisateur instance");
		try {
			getEntityManager().persist(transientInstance);
			logger.debug("persist successful");
		} catch (RuntimeException re) {
			logger.error("persist failed", re);
			throw re;
		}
	}
	
	public TaUtilisateur refresh(TaUtilisateur detachedInstance) {
		logger.debug("refresh instance");
		try {
			org.hibernate.Session session = (org.hibernate.Session) getEntityManager().getDelegate();
		    session.evict(detachedInstance);
		    detachedInstance = getEntityManager().find(TaUtilisateur.class, detachedInstance.getIdUtilisateur());
		    return detachedInstance;
		} catch (RuntimeException re) {
			logger.error("refresh failed", re);
			throw re;
		}
	}

	public void remove(TaUtilisateur persistentInstance) {
		logger.debug("removing TaUtilisateur instance");
		try {
			getEntityManager().remove(persistentInstance);
			logger.debug("remove successful");
		} catch (RuntimeException re) {
			logger.error("remove failed", re);
			throw re;
		}
	}

	public TaUtilisateur merge(TaUtilisateur detachedInstance) {
		logger.debug("merging TaUtilisateur instance");
		try {
			TaUtilisateur result = getEntityManager().merge(detachedInstance);
			logger.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			logger.error("merge failed", re);
			throw re;
		}
	}

	public TaUtilisateur findById(int id) {
		logger.debug("getting TaUtilisateur instance with id: " + id);
		try {
			TaUtilisateur instance = getEntityManager().find(TaUtilisateur.class, id);
			logger.debug("get successful");
			return instance;
		} catch (RuntimeException re) {
			logger.error("get failed", re);
			throw re;
		}
	}
	
	public TaUtilisateur findByCode(String code) {
		logger.debug("getting TaUtilisateur instance with code: " + code);
		try {
			if(!code.equals("")){
			Query query = getEntityManager().createQuery("select f from TaUtilisateur f where f.nom='"+code+"'");
			TaUtilisateur instance = (TaUtilisateur)query.getSingleResult();
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
	public List<TaUtilisateur> selectAll() {
		// TODO Auto-generated method stub
		logger.debug("selectAll TaUtilisateur");
		try {
			Query ejbQuery = getEntityManager().createQuery(JPQLQuery);
			List<TaUtilisateur> l = ejbQuery.getResultList();
			logger.debug("selectAll successful");
			return l;
		} catch (RuntimeException re) {
			logger.error("selectAll failed", re);
			throw re;
		}
	}
	

}
