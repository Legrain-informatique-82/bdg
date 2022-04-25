package fr.legrain.tiers.dao;

// Generated Mar 24, 2009 4:44:04 PM by Hibernate Tools 3.2.0.CR1

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import fr.legrain.articles.dao.TaUnite;
import fr.legrain.gestCom.Appli.AbstractApplicationDAO;
import fr.legrain.gestCom.Appli.Const;
import fr.legrain.lib.data.ExceptLgr;

/**
 * Home object for domain model class TaWeb.
 * @see fr.legrain.tiers.dao.TaWeb
 * @author Hibernate Tools
 */
public class TaWebDAO extends AbstractApplicationDAO<TaWeb> {

	private static final Log logger = LogFactory.getLog(TaWebDAO.class);
	//static Logger logger = Logger.getLogger(TaWebDAO.class.getName());
	
	private String defaultJPQLQuery = "select u from TaWeb u";
	
	public TaWebDAO(){
		this(null);
	}

	public TaWebDAO(EntityManager em){
		if(em!=null) {
			setEm(em);
		}
//		champIdTable=ctrlGeneraux.getID_TABLE(TaWeb.class.getSimpleName());
		initChampId(TaWeb.class);
		setJPQLQuery(defaultJPQLQuery);
		setListeChampMaj(Const.C_FICHIER_INI_CHAMPMAJ);
		initNomTable(new TaWeb());
	}
	public TaWeb refresh(TaWeb detachedInstance) {
		logger.debug("refresh instance");
		try {
			org.hibernate.Session session = (org.hibernate.Session) getEntityManager().getDelegate();
		    session.evict(detachedInstance);
		    detachedInstance = getEntityManager().find(TaWeb.class, detachedInstance.getIdWeb());
		    return detachedInstance;
		} catch (RuntimeException re) {
			logger.error("refresh failed", re);
			throw re;
		}
	}
	public void persist(TaWeb transientInstance) {
		logger.debug("persisting TaWeb instance");
		try {
			getEntityManager().persist(transientInstance);
			logger.debug("persist successful");
		} catch (RuntimeException re) {
			logger.error("persist failed", re);
			throw re;
		}
	}

	public void remove(TaWeb persistentInstance) {
		logger.debug("removing TaWeb instance");
		try {
			getEntityManager().remove(persistentInstance);
			logger.debug("remove successful");
		} catch (RuntimeException re) {
			logger.error("remove failed", re);
			throw re;
		}
	}

	public TaWeb merge(TaWeb detachedInstance) {
		logger.debug("merging TaWeb instance");
		try {
			TaWeb result = getEntityManager().merge(detachedInstance);
			logger.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			logger.error("merge failed", re);
			throw re;
		}
	}

	public TaWeb findById(int id) {
		logger.debug("getting TaWeb instance with id: " + id);
		try {
			TaWeb instance = getEntityManager().find(TaWeb.class, id);
			logger.debug("get successful");
			return instance;
		} catch (RuntimeException re) {
			logger.error("get failed", re);
			throw re;
		}
	}
	
	@Override
	public List<TaWeb> selectAll() {
		// TODO Auto-generated method stub
		logger.debug("selectAll TaWeb");
		try {
			Query ejbQuery = getEntityManager().createQuery(JPQLQuery);
			List<TaWeb> l = ejbQuery.getResultList();
			logger.debug("selectAll successful");
			return l;
		} catch (RuntimeException re) {
			logger.error("selectAll failed", re);
			throw re;
		}
	}
	public void ctrlSaisieSpecifique(TaWeb entity,String field) throws ExceptLgr {	
		
	}
}
