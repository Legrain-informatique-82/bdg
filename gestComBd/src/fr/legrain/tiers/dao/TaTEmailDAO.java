package fr.legrain.tiers.dao;

// Generated Mar 24, 2009 4:44:04 PM by Hibernate Tools 3.2.0.CR1

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import fr.legrain.articles.dao.TaUnite;
import fr.legrain.gestCom.Appli.AbstractApplicationDAO;
import fr.legrain.gestCom.Appli.Const;
import fr.legrain.lib.data.ExceptLgr;

/**
 * Home object for domain model class TaTEmail.
 * @see fr.legrain.tiers.dao.TaTEmail
 * @author Hibernate Tools
 */
public class TaTEmailDAO extends AbstractApplicationDAO<TaTEmail>{

	//private static final Log logger = LogFactory.getLog(TaTEmailDAO.class);
	static Logger logger = Logger.getLogger(TaTEmailDAO.class);

	private String defaultJPQLQuery = "select a from TaTEmail a";
	
	public TaTEmailDAO() {
		this(null);
	}
	
	public TaTEmailDAO(EntityManager em){
		if(em!=null) {
			setEm(em);
		}
//		champIdTable=ctrlGenerauxC.getID_TABLE(TaTEmail.class.getSimpleName());
		initChampId(TaTEmail.class);
		setJPQLQuery(defaultJPQLQuery);
		setListeChampMaj(Const.C_FICHIER_INI_CHAMPMAJ);
		initNomTable(new TaTEmail());
	}
	public TaTEmail refresh(TaTEmail detachedInstance) {
		logger.debug("refresh instance");
		try {
			org.hibernate.Session session = (org.hibernate.Session) getEntityManager().getDelegate();
		    session.evict(detachedInstance);
		    detachedInstance = getEntityManager().find(TaTEmail.class, detachedInstance.getIdTEmail());
		    return detachedInstance;
		} catch (RuntimeException re) {
			logger.error("refresh failed", re);
			throw re;
		}
	}
	public void persist(TaTEmail transientInstance) {
		logger.debug("persisting TaTEmail instance");
		try {
			getEntityManager().persist(transientInstance);
			logger.debug("persist successful");
		} catch (RuntimeException re) {
			logger.error("persist failed", re);
			throw re;
		}
	}

	public void remove(TaTEmail persistentInstance) {
		logger.debug("removing TaTEmail instance");
		try {
			getEntityManager().remove(persistentInstance);
			logger.debug("remove successful");
		} catch (RuntimeException re) {
			logger.error("remove failed", re);
			throw re;
		}
	}

	public TaTEmail merge(TaTEmail detachedInstance) {
		logger.debug("merging TaTEmail instance");
		try {
			TaTEmail result = getEntityManager().merge(detachedInstance);
			logger.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			logger.error("merge failed", re);
			throw re;
		}
	}

	public TaTEmail findById(int id) {
		logger.debug("getting TaTEmail instance with id: " + id);
		try {
			TaTEmail instance = getEntityManager().find(TaTEmail.class, id);
			logger.debug("get successful");
			return instance;
		} catch (RuntimeException re) {
			logger.error("get failed", re);
			throw re;
		}
	}
	
	public TaTEmail findByCode(String code) {
		logger.debug("getting TaTEmail instance with code: " + code);
		try {
			if(!code.equals("")){
			Query query = getEntityManager().createQuery("select f from TaTEmail f where f.codeTEmail='"+code+"'");
			TaTEmail instance = (TaTEmail)query.getSingleResult();
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
	public List<TaTEmail> selectAll() {
		// TODO Auto-generated method stub
		logger.debug("selectAll TaTEmail");
		try {
			Query ejbQuery = getEntityManager().createQuery(JPQLQuery);
			List<TaTEmail> l = ejbQuery.getResultList();
			logger.debug("selectAll successful");
			return l;
		} catch (RuntimeException re) {
			logger.error("selectAll failed", re);
			throw re;
		}
	}
	public void ctrlSaisieSpecifique(TaTEmail entity,String field) throws ExceptLgr {	
		
	}
}
