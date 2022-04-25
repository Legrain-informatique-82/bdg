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
 * Home object for domain model class TaTLiens.
 * @see fr.legrain.tiers.dao.TaTLiens
 * @author Hibernate Tools
 */
public class TaTLiensDAO extends AbstractApplicationDAO<TaTLiens>{

	//private static final Log logger = LogFactory.getLog(TaTLiensDAO.class);
	static Logger logger = Logger.getLogger(TaTLiensDAO.class);

	private String defaultJPQLQuery = "select a from TaTLiens a";
	
	public TaTLiensDAO() {
		this(null);
	}
	
	public TaTLiensDAO(EntityManager em){
		if(em!=null) {
			setEm(em);
		}
//		champIdTable=ctrlGeneraux.getID_TABLE(TaTLiens.class.getSimpleName());
		initChampId(TaTLiens.class);
		setJPQLQuery(defaultJPQLQuery);
		setListeChampMaj(Const.C_FICHIER_INI_CHAMPMAJ);
		initNomTable(new TaTLiens());
	}
	public TaTLiens refresh(TaTLiens detachedInstance) {
		logger.debug("refresh instance");
		try {
			org.hibernate.Session session = (org.hibernate.Session) getEntityManager().getDelegate();
		    session.evict(detachedInstance);
		    detachedInstance = getEntityManager().find(TaTLiens.class, detachedInstance.getIdTLiens());
		    return detachedInstance;
		} catch (RuntimeException re) {
			logger.error("refresh failed", re);
			throw re;
		}
	}
	public void persist(TaTLiens transientInstance) {
		logger.debug("persisting TaTLiens instance");
		try {
			getEntityManager().persist(transientInstance);
			logger.debug("persist successful");
		} catch (RuntimeException re) {
			logger.error("persist failed", re);
			throw re;
		}
	}

	public void remove(TaTLiens persistentInstance) {
		logger.debug("removing TaTLiens instance");
		try {
			getEntityManager().remove(persistentInstance);
			logger.debug("remove successful");
		} catch (RuntimeException re) {
			logger.error("remove failed", re);
			throw re;
		}
	}

	public TaTLiens merge(TaTLiens detachedInstance) {
		logger.debug("merging TaTLiens instance");
		try {
			TaTLiens result = getEntityManager().merge(detachedInstance);
			logger.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			logger.error("merge failed", re);
			throw re;
		}
	}

	public TaTLiens findById(int id) {
		logger.debug("getting TaTLiens instance with id: " + id);
		try {
			TaTLiens instance = getEntityManager().find(TaTLiens.class, id);
			logger.debug("get successful");
			return instance;
		} catch (RuntimeException re) {
			logger.error("get failed", re);
			throw re;
		}
	}
	
	public TaTLiens findByCode(String code) {
		logger.debug("getting TaTLiens instance with code: " + code);
		try {
			if(!code.equals("")){
			Query query = getEntityManager().createQuery("select f from TaTLiens f where f.codeTLiens='"+code+"'");
			TaTLiens instance = (TaTLiens)query.getSingleResult();
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
	public List<TaTLiens> selectAll() {
		// TODO Auto-generated method stub
		logger.debug("selectAll TaLiens");
		try {
			Query ejbQuery = getEntityManager().createQuery(JPQLQuery);
			List<TaTLiens> l = ejbQuery.getResultList();
			logger.debug("selectAll successful");
			return l;
		} catch (RuntimeException re) {
			logger.error("selectAll failed", re);
			throw re;
		}
	}
	public void ctrlSaisieSpecifique(TaTLiens entity,String field) throws ExceptLgr {	
		
	}
}
