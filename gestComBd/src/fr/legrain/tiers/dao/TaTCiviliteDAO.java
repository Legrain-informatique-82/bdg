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
 * Home object for domain model class TaTCivilite.
 * @see fr.legrain.tiers.dao.TaTCivilite
 * @author Hibernate Tools
 */
public class TaTCiviliteDAO extends AbstractApplicationDAO<TaTCivilite>{

	//private static final Log logger = LogFactory.getLog(TaTCiviliteDAO.class);
	static Logger logger = Logger.getLogger(TaTCiviliteDAO.class);

	private String defaultJPQLQuery = "select a from TaTCivilite a";
	
	public TaTCiviliteDAO() {
		this(null);
	}

	public TaTCiviliteDAO(EntityManager em){
		if(em!=null) {
			setEm(em);
		}
//		champIdTable=ctrlGeneraux.getID_TABLE(TaTCivilite.class.getSimpleName());
		initChampId(TaTCivilite.class);
		setJPQLQuery(defaultJPQLQuery);
		setListeChampMaj(Const.C_FICHIER_INI_CHAMPMAJ);
		initNomTable(new TaTCivilite());
	}
	public TaTCivilite refresh(TaTCivilite detachedInstance) {
		logger.debug("refresh instance");
		try {
			org.hibernate.Session session = (org.hibernate.Session) getEntityManager().getDelegate();
		    session.evict(detachedInstance);
		    detachedInstance = getEntityManager().find(TaTCivilite.class, detachedInstance.getIdTCivilite());
		    return detachedInstance;
		} catch (RuntimeException re) {
			logger.error("refresh failed", re);
			throw re;
		}
	}
	
	public Boolean exist(String code) {
		logger.debug("exist with code: " + code);
		try {
			if(code!=null && !code.equals("")){
			Query query = getEntityManager().createQuery("select count(f) from TaTCivilite f where f.codeTCivilite='"+code+"'");
			Long instance = (Long)query.getSingleResult();
			logger.debug("get successful");
			return instance!=0;
			}
			return false;
		} catch (RuntimeException re) {
			logger.error("get failed", re);
			throw re;
		}
	}

	public void persist(TaTCivilite transientInstance) {
		logger.debug("persisting TaTCivilite instance");
		try {
			getEntityManager().persist(transientInstance);
			logger.debug("persist successful");
		} catch (RuntimeException re) {
			logger.error("persist failed", re);
			throw re;
		}
	}

	public void remove(TaTCivilite persistentInstance) {
		logger.debug("removing TaTCivilite instance");
		try {
			getEntityManager().remove(persistentInstance);
			logger.debug("remove successful");
		} catch (RuntimeException re) {
			logger.error("remove failed", re);
			throw re;
		}
	}

	public TaTCivilite merge(TaTCivilite detachedInstance) {
		logger.debug("merging TaTCivilite instance");
		try {
			TaTCivilite result = getEntityManager().merge(detachedInstance);
			logger.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			logger.error("merge failed", re);
			throw re;
		}
	}

	public TaTCivilite findById(int id) {
		logger.debug("getting TaTCivilite instance with id: " + id);
		try {
			TaTCivilite instance = getEntityManager().find(TaTCivilite.class, id);
			logger.debug("get successful");
			return instance;
		} catch (RuntimeException re) {
			logger.error("get failed", re);
			throw re;
		}
	}
	
	public TaTCivilite findByCode(String code) {
		logger.debug("getting TaTCivilite instance with code: " + code);
		try {
			if(!code.equals("")){
			Query query = getEntityManager().createQuery("select f from TaTCivilite f where f.codeTCivilite='"+code+"'");
			TaTCivilite instance = (TaTCivilite)query.getSingleResult();
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
	public List<TaTCivilite> selectAll() {
		// TODO Auto-generated method stub
		logger.debug("selectAll TaCompteBanque");
		try {
			Query ejbQuery = getEntityManager().createQuery(JPQLQuery);
			List<TaTCivilite> l = ejbQuery.getResultList();
			logger.debug("selectAll successful");
			return l;
		} catch (RuntimeException re) {
			logger.error("selectAll failed", re);
			throw re;
		}
	}
	public void ctrlSaisieSpecifique(TaTCivilite entity,String field) throws ExceptLgr {	
		
	}
}
