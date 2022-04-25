package fr.legrain.documents.dao;

// Generated Mar 24, 2009 4:44:04 PM by Hibernate Tools 3.2.0.CR1

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import fr.legrain.articles.dao.TaPrix;
import fr.legrain.gestCom.Appli.AbstractApplicationDAO;
import fr.legrain.gestCom.Appli.Const;
import fr.legrain.lib.data.ExceptLgr;

/**
 * Home object for domain model class TaTLiens.
 * @see fr.legrain.tiers.dao.TaTLiens
 * @author Hibernate Tools
 */
public class TaLRemiseDAO extends AbstractApplicationDAO<TaLRemise>{

	static Logger logger = Logger.getLogger(TaLRemiseDAO.class);

	private String defaultJPQLQuery = "select a from TaLRemise a ";
	
	public TaLRemiseDAO() {
		this(null);
	}
	
	public TaLRemiseDAO(EntityManager em){
		if(em!=null) {
			setEm(em);
		}
//		champIdTable=ctrlGeneraux.getID_TABLE(TaLRemise.class.getSimpleName());
		initChampId(TaLRemise.class);
		setJPQLQuery(defaultJPQLQuery);
		setListeChampMaj(Const.C_FICHIER_INI_CHAMPMAJ);
		initNomTable(new TaLRemise());
	}
	public TaLRemise refresh(TaLRemise detachedInstance) {
		logger.debug("refresh instance");
		try {
			getEntityManager().refresh(detachedInstance);
//			org.hibernate.Session session = (org.hibernate.Session) getEntityManager().getDelegate();
//		    session.evict(detachedInstance);
//		    detachedInstance = getEntityManager().find(TaLRemise.class, detachedInstance.getIdLRemise());
		    return detachedInstance;
		} catch (RuntimeException re) {
			logger.error("refresh failed", re);
			throw re;
		}
	}
	public void persist(TaLRemise transientInstance) {
		logger.debug("persisting TaLRemise instance");
		try {
			getEntityManager().persist(transientInstance);
			logger.debug("persist successful");
		} catch (RuntimeException re) {
			logger.error("persist failed", re);
			throw re;
		}
	}

	public void remove(TaLRemise persistentInstance) {
		logger.debug("removing TaLRemise instance");
		try {
			getEntityManager().remove(persistentInstance);
			logger.debug("remove successful");
		} catch (RuntimeException re) {
			logger.error("remove failed", re);
			throw re;
		}
	}

	public TaLRemise merge(TaLRemise detachedInstance) {
		logger.debug("merging TaLRemise instance");
		try {
			TaLRemise result = getEntityManager().merge(detachedInstance);
			logger.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			logger.error("merge failed", re);
			throw re;
		}
	}

	public TaLRemise findById(int id) {
		logger.debug("getting TaLRemise instance with id: " + id);
		try {
			TaLRemise instance = getEntityManager().find(TaLRemise.class, id);
			logger.debug("get successful");
			return instance;
		} catch (RuntimeException re) {
			logger.error("get failed", re);
			throw re;
		}
	}
	
	@Override
	public List<TaLRemise> selectAll() {
		// TODO Auto-generated method stub
		logger.debug("selectAll TaLRemise");
		try {  
			Query ejbQuery = getEntityManager().createQuery(JPQLQuery);
			List<TaLRemise> l = ejbQuery.getResultList();
			logger.debug("selectAll successful");
			return l;
		} catch (RuntimeException re) {
			logger.error("selectAll failed", re);
			throw re;
		}
	}
	
	
	public void ctrlSaisieSpecifique(TaLRemise entity,String field) throws ExceptLgr {	
		
	}
}
