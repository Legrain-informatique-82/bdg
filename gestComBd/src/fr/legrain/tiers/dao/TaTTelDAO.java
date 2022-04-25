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
 * Home object for domain model class TaTTel.
 * @see fr.legrain.tiers.dao.TaTTel
 * @author Hibernate Tools
 */
public class TaTTelDAO extends AbstractApplicationDAO<TaTTel>{

	//private static final Log logger = LogFactory.getLog(TaTTelDAO.class);
	static Logger logger = Logger.getLogger(TaTTelDAO.class);

	private String defaultJPQLQuery = "select a from TaTTel a";
	
	public TaTTelDAO() {
		this(null);
	}
	
	public TaTTelDAO(EntityManager em){
		if(em!=null) {
			setEm(em);
		}
//		champIdTable=ctrlGeneraux.getID_TABLE(TaTTel.class.getSimpleName());
		initChampId(TaTTel.class);
		setJPQLQuery(defaultJPQLQuery);
		setListeChampMaj(Const.C_FICHIER_INI_CHAMPMAJ);
		initNomTable(new TaTTel());
	}
	public TaTTel refresh(TaTTel detachedInstance) {
		logger.debug("refresh instance");
		try {
			org.hibernate.Session session = (org.hibernate.Session) getEntityManager().getDelegate();
		    session.evict(detachedInstance);
		    detachedInstance = getEntityManager().find(TaTTel.class, detachedInstance.getIdTTel());
		    return detachedInstance;
		} catch (RuntimeException re) {
			logger.error("refresh failed", re);
			throw re;
		}
	}
	public void persist(TaTTel transientInstance) {
		logger.debug("persisting TaTTel instance");
		try {
			getEntityManager().persist(transientInstance);
			logger.debug("persist successful");
		} catch (RuntimeException re) {
			logger.error("persist failed", re);
			throw re;
		}
	}

	public void remove(TaTTel persistentInstance) {
		logger.debug("removing TaTTel instance");
		try {
			getEntityManager().remove(persistentInstance);
			logger.debug("remove successful");
		} catch (RuntimeException re) {
			logger.error("remove failed", re);
			throw re;
		}
	}

	public TaTTel merge(TaTTel detachedInstance) {
		logger.debug("merging TaTTel instance");
		try {
			TaTTel result = getEntityManager().merge(detachedInstance);
			logger.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			logger.error("merge failed", re);
			throw re;
		}
	}

	public TaTTel findById(int id) {
		logger.debug("getting TaTTel instance with id: " + id);
		try {
			TaTTel instance = getEntityManager().find(TaTTel.class, id);
			logger.debug("get successful");
			return instance;
		} catch (RuntimeException re) {
			logger.error("get failed", re);
			throw re;
		}
	}
	
	public TaTTel findByCode(String code) {
		logger.debug("getting TaTTel instance with code: " + code);
		try {
			if(!code.equals("")){
			Query query = getEntityManager().createQuery("select f from TaTTel f where f.codeTTel='"+code+"'");
			TaTTel instance = (TaTTel)query.getSingleResult();
			logger.debug("get successful");
			return instance;
			}
			return null;
		} catch (RuntimeException re) {
			logger.error("get failed", re);
			throw re;
		}
	}
	

	public Boolean exist(String code) {
		logger.debug("exist with code: " + code);
		try {
			if(code!=null && !code.equals("")){
			Query query = getEntityManager().createQuery("select count(f) from TaTTel f where f.codeTTel='"+code+"'");
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
	
	
	@Override
	public List<TaTTel> selectAll() {
		// TODO Auto-generated method stub
		logger.debug("selectAll TaTTel");
		try {
			Query ejbQuery = getEntityManager().createQuery(JPQLQuery);
			List<TaTTel> l = ejbQuery.getResultList();
			logger.debug("selectAll successful");
			return l;
		} catch (RuntimeException re) {
			logger.error("selectAll failed", re);
			throw re;
		}
	}
	

	public void ctrlSaisieSpecifique(TaTTel entity,String field) throws ExceptLgr {	
		
	}
}
