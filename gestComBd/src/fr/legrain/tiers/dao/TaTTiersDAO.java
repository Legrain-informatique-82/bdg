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
 * Home object for domain model class TaTTiers.
 * @see fr.legrain.tiers.dao.TaTTiers
 * @author Hibernate Tools
 */
public class TaTTiersDAO extends AbstractApplicationDAO<TaTTiers>{

	//private static final Log logger = LogFactory.getLog(TaLiensDAO.class);
	static Logger logger = Logger.getLogger(TaTTiersDAO.class);

	private String defaultJPQLQuery = "select a from TaTTiers a where idTTiers > 0";
	
	public TaTTiersDAO() {
		this(null);
	}
	
	public TaTTiersDAO(EntityManager em){
		if(em!=null) {
			setEm(em);
		}
//		champIdTable=ctrlGeneraux.getID_TABLE(TaTTiers.class.getSimpleName());
		initChampId(TaTTiers.class);
		setJPQLQuery(defaultJPQLQuery);
		setListeChampMaj(Const.C_FICHIER_INI_CHAMPMAJ);
		initNomTable(new TaTTiers());
	}
	public TaTTiers refresh(TaTTiers detachedInstance) {
		logger.debug("refresh instance");
		try {
			org.hibernate.Session session = (org.hibernate.Session) getEntityManager().getDelegate();
		    session.evict(detachedInstance);
		    detachedInstance = getEntityManager().find(TaTTiers.class, detachedInstance.getIdTTiers());
		    return detachedInstance;
		} catch (RuntimeException re) {
			logger.error("refresh failed", re);
			throw re;
		}
	}
	public void persist(TaTTiers transientInstance) {
		logger.debug("persisting TaTTiers instance");
		try {
			getEntityManager().persist(transientInstance);
			logger.debug("persist successful");
		} catch (RuntimeException re) {
			logger.error("persist failed", re);
			throw re;
		}
	}

	public void remove(TaTTiers persistentInstance) {
		logger.debug("removing TaTTiers instance");
		try {
			getEntityManager().remove(persistentInstance);
			logger.debug("remove successful");
		} catch (RuntimeException re) {
			logger.error("remove failed", re);
			throw re;
		}
	}

	public TaTTiers merge(TaTTiers detachedInstance) {
		logger.debug("merging TaTTiers instance");
		try {
			TaTTiers result = getEntityManager().merge(detachedInstance);
			logger.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			logger.error("merge failed", re);
			throw re;
		}
	}

	public TaTTiers findById(int id) {
		logger.debug("getting TaTTiers instance with id: " + id);
		try {
			TaTTiers instance = getEntityManager().find(TaTTiers.class, id);
			logger.debug("get successful");
			return instance;
		} catch (RuntimeException re) {
			logger.error("get failed", re);
			throw re;
		}
	}
	
	public TaTTiers findByCode(String code) {
		logger.debug("getting TaTTiers instance with code: " + code);
		try {
			if(!code.equals("")){
				Query query = getEntityManager().createQuery("select f from TaTTiers f where f.codeTTiers='"+code+"'");
				TaTTiers instance = (TaTTiers)query.getSingleResult();
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
	public List<TaTTiers> selectAll() {
		// TODO Auto-generated method stub
		logger.debug("selectAll TaTTiers");
		try {
			Query ejbQuery = getEntityManager().createQuery(JPQLQuery);
			List<TaTTiers> l = ejbQuery.getResultList();
			logger.debug("selectAll successful");
			return l;
		} catch (RuntimeException re) {
			logger.error("selectAll failed", re);
			throw re;
		}
	}
	public void ctrlSaisieSpecifique(TaTTiers entity,String field) throws ExceptLgr {	
		
	}
}
