package fr.legrain.tiers.dao;

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
 * Home object for domain model class TaCompl.
 * @see fr.legrain.tiers.dao.test.TaCompl
 * @author Hibernate Tools
 */
public class TaComplDAO extends AbstractApplicationDAO<TaCompl>{

	//private static final Log logger = LogFactory.getLog(TaComplDAO.class);
	static Logger logger = Logger.getLogger(TaComplDAO.class);
	private String defaultJPQLQuery = "select a from TaCompl a";
	
	public TaComplDAO() {
		this(null);
	}
	
	public TaComplDAO(EntityManager em){
		if(em!=null) {
			setEm(em);
		}
//		champIdTable=ctrlGeneraux.getID_TABLE(TaCompl.class.getSimpleName());
		initChampId(TaCompl.class);
		setJPQLQuery(defaultJPQLQuery);
		setListeChampMaj(Const.C_FICHIER_INI_CHAMPMAJ);
		initNomTable(new TaCompl());
	}


	public void persist(TaCompl transientInstance) {
		logger.debug("persisting TaCompl instance");
		try {
			getEntityManager().persist(transientInstance);
			logger.debug("persist successful");
		} catch (RuntimeException re) {
			logger.error("persist failed", re);
			throw re;
		}
	}

	public TaCompl refresh(TaCompl detachedInstance) {
		logger.debug("refresh instance");
		try {
			org.hibernate.Session session = (org.hibernate.Session) getEntityManager().getDelegate();
		    session.evict(detachedInstance);
		    detachedInstance = getEntityManager().find(TaCompl.class, detachedInstance.getIdCompl());
		    return detachedInstance;
		} catch (RuntimeException re) {
			logger.error("refresh failed", re);
			throw re;
		}
	}
	
	public void remove(TaCompl persistentInstance) {
		logger.debug("removing TaCompl instance");
		try {
			getEntityManager().remove(persistentInstance);
			logger.debug("remove successful");
		} catch (RuntimeException re) {
			logger.error("remove failed", re);
			throw re;
		}
	}

	public TaCompl merge(TaCompl detachedInstance) {
		logger.debug("merging TaCompl instance");
		try {
			TaCompl result = getEntityManager().merge(detachedInstance);
			logger.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			logger.error("merge failed", re);
			throw re;
		}
	}

	public TaCompl findById(int id) {
		logger.debug("getting TaCompl instance with id: " + id);
		try {
			TaCompl instance = getEntityManager().find(TaCompl.class, id);
			logger.debug("get successful");
			return instance;
		} catch (RuntimeException re) {
			logger.error("get failed", re);
			throw re;
		}
	}

	@Override
	public List<TaCompl> selectAll() {
		// TODO Auto-generated method stub
		logger.debug("selectAll TaCompl");
		try {
			Query ejbQuery = getEntityManager().createQuery(JPQLQuery);
			List<TaCompl> l = ejbQuery.getResultList();
			logger.debug("selectAll successful");
			return l;
		} catch (RuntimeException re) {
			logger.error("selectAll failed", re);
			throw re;
		}
	}
	public void ctrlSaisieSpecifique(TaCompl entity,String field) throws ExceptLgr {	
		
	}
}

