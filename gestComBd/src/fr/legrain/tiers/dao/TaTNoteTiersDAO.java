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
 * Home object for domain model class TaTNoteTiers.
 * @see fr.legrain.tiers.dao.TaTNoteTiers
 * @author Hibernate Tools
 */
public class TaTNoteTiersDAO extends AbstractApplicationDAO<TaTNoteTiers>{

	//private static final Log logger = LogFactory.getLog(TaTNoteTiers.class);
	static Logger logger = Logger.getLogger(TaTNoteTiers.class);

	private String defaultJPQLQuery = "select a from TaTNoteTiers a";
	
	public TaTNoteTiersDAO() {
		this(null);
	}
	
	public TaTNoteTiersDAO(EntityManager em){
		if(em!=null) {
			setEm(em);
		}
//		champIdTable=ctrlGeneraux.getID_TABLE(TaTNoteTiers.class.getSimpleName());
		initChampId(TaTNoteTiers.class);
		setJPQLQuery(defaultJPQLQuery);
		setListeChampMaj(Const.C_FICHIER_INI_CHAMPMAJ);
		initNomTable(new TaTNoteTiers());
	}
	public TaTNoteTiers refresh(TaTNoteTiers detachedInstance) {
		logger.debug("refresh instance");
		try {
			org.hibernate.Session session = (org.hibernate.Session) getEntityManager().getDelegate();
		    session.evict(detachedInstance);
		    detachedInstance = getEntityManager().find(TaTNoteTiers.class, detachedInstance.getIdTNoteTiers());
		    return detachedInstance;
		} catch (RuntimeException re) {
			logger.error("refresh failed", re);
			throw re;
		}
	}
	public void persist(TaTNoteTiers transientInstance) {
		logger.debug("persisting TaTNoteTiers instance");
		try {
			getEntityManager().persist(transientInstance);
			logger.debug("persist successful");
		} catch (RuntimeException re) {
			logger.error("persist failed", re);
			throw re;
		}
	}

	public void remove(TaTNoteTiers persistentInstance) {
		logger.debug("removing TaTNoteTiers instance");
		try {
			getEntityManager().remove(persistentInstance);
			logger.debug("remove successful");
		} catch (RuntimeException re) {
			logger.error("remove failed", re);
			throw re;
		}
	}

	public TaTNoteTiers merge(TaTNoteTiers detachedInstance) {
		logger.debug("merging TaTNoteTiers instance");
		try {
			TaTNoteTiers result = getEntityManager().merge(detachedInstance);
			logger.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			logger.error("merge failed", re);
			throw re;
		}
	}

	public TaTNoteTiers findById(int id) {
		logger.debug("getting TaTNoteTiers instance with id: " + id);
		try {
			TaTNoteTiers instance = getEntityManager().find(TaTNoteTiers.class, id);
			logger.debug("get successful");
			return instance;
		} catch (RuntimeException re) {
			logger.error("get failed", re);
			throw re;
		}
	}
	
	public TaTNoteTiers findByCode(String code) {
		logger.debug("getting TaTNoteTiers instance with code: " + code);
		try {
			if(!code.equals("")){
			Query query = getEntityManager().createQuery("select f from TaTNoteTiers f where f.codeTNoteTiers='"+code+"'");
			TaTNoteTiers instance = (TaTNoteTiers)query.getSingleResult();
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
	public List<TaTNoteTiers> selectAll() {
		// TODO Auto-generated method stub
		logger.debug("selectAll TaLiens");
		try {
			Query ejbQuery = getEntityManager().createQuery(JPQLQuery);
			List<TaTNoteTiers> l = ejbQuery.getResultList();
			logger.debug("selectAll successful");
			return l;
		} catch (RuntimeException re) {
			logger.error("selectAll failed", re);
			throw re;
		}
	}
	public void ctrlSaisieSpecifique(TaTNoteTiers entity,String field) throws ExceptLgr {	
		
	}
}
