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
 * Home object for domain model class TaTWeb.
 * @see fr.legrain.tiers.dao.TaTWeb
 * @author Hibernate Tools
 */
public class TaTWebDAO extends AbstractApplicationDAO<TaTWeb>{

	//private static final Log logger = LogFactory.getLog(TaTWeb.class);
	static Logger logger = Logger.getLogger(TaTWeb.class);

	private String defaultJPQLQuery = "select a from TaTWeb a";
	
	public TaTWebDAO() {
		this(null);
	}
	
	public TaTWebDAO(EntityManager em){
		if(em!=null) {
			setEm(em);
		}
//		champIdTable=ctrlGeneraux.getID_TABLE(TaTWeb.class.getSimpleName());
		initChampId(TaTWeb.class);
		setJPQLQuery(defaultJPQLQuery);
		setListeChampMaj(Const.C_FICHIER_INI_CHAMPMAJ);
		initNomTable(new TaTWeb());
	}
	public TaTWeb refresh(TaTWeb detachedInstance) {
		logger.debug("refresh instance");
		try {
			org.hibernate.Session session = (org.hibernate.Session) getEntityManager().getDelegate();
		    session.evict(detachedInstance);
		    detachedInstance = getEntityManager().find(TaTWeb.class, detachedInstance.getIdTWeb());
		    return detachedInstance;
		} catch (RuntimeException re) {
			logger.error("refresh failed", re);
			throw re;
		}
	}
	public void persist(TaTWeb transientInstance) {
		logger.debug("persisting TaTWeb instance");
		try {
			getEntityManager().persist(transientInstance);
			logger.debug("persist successful");
		} catch (RuntimeException re) {
			logger.error("persist failed", re);
			throw re;
		}
	}

	public void remove(TaTWeb persistentInstance) {
		logger.debug("removing TaTWeb instance");
		try {
			getEntityManager().remove(persistentInstance);
			logger.debug("remove successful");
		} catch (RuntimeException re) {
			logger.error("remove failed", re);
			throw re;
		}
	}

	public TaTWeb merge(TaTWeb detachedInstance) {
		logger.debug("merging TaTWeb instance");
		try {
			TaTWeb result = getEntityManager().merge(detachedInstance);
			logger.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			logger.error("merge failed", re);
			throw re;
		}
	}

	public TaTWeb findById(int id) {
		logger.debug("getting TaTWeb instance with id: " + id);
		try {
			TaTWeb instance = getEntityManager().find(TaTWeb.class, id);
			logger.debug("get successful");
			return instance;
		} catch (RuntimeException re) {
			logger.error("get failed", re);
			throw re;
		}
	}
	
	public TaTWeb findByCode(String code) {
		logger.debug("getting TaTWeb instance with code: " + code);
		try {
			if(!code.equals("")){
			Query query = getEntityManager().createQuery("select f from TaTWeb f where f.codeTWeb='"+code+"'");
			TaTWeb instance = (TaTWeb)query.getSingleResult();
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
	public List<TaTWeb> selectAll() {
		// TODO Auto-generated method stub
		logger.debug("selectAll TaLiens");
		try {
			Query ejbQuery = getEntityManager().createQuery(JPQLQuery);
			List<TaTWeb> l = ejbQuery.getResultList();
			logger.debug("selectAll successful");
			return l;
		} catch (RuntimeException re) {
			logger.error("selectAll failed", re);
			throw re;
		}
	}
	public void ctrlSaisieSpecifique(TaTWeb entity,String field) throws ExceptLgr {	
		
	}
}
