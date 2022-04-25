package fr.legrain.documents.dao;

// Generated Apr 7, 2009 3:27:24 PM by Hibernate Tools 3.2.0.CR1

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import fr.legrain.articles.dao.TaPrix;
import fr.legrain.articles.dao.TaUnite;
import fr.legrain.gestCom.Appli.AbstractApplicationDAO;
import fr.legrain.gestCom.Appli.Const;

/**
 * Home object for domain model class TaLModele.
 * @see fr.legrain.documents.dao.TaLModele
 * @author Hibernate Tools
 */
public class TaLModeleDAO extends AbstractApplicationDAO<TaLModele> {

//	private static final Log log = LogFactory.getLog(TaLModeleDAO.class);
	static Logger logger = Logger.getLogger(TaLModeleDAO.class);
	
	private String defaultJPQLQuery = "select a from TaLModele a";
	
	public TaLModeleDAO(){
		this(null);
	}
	
	public TaLModeleDAO(EntityManager em){
		if(em!=null) {
			setEm(em);
		}
//		champIdTable=ctrlGeneraux.getID_TABLE(TaLModele.class.getSimpleName());
		initChampId(TaLModele.class);
		setJPQLQuery(defaultJPQLQuery);
		setListeChampMaj(Const.C_FICHIER_INI_CHAMPMAJ);
		initNomTable(new TaLModele());
	}
	public TaLModele refresh(TaLModele detachedInstance) {
		logger.debug("refresh instance");
		try {
			detachedInstance.setLegrain(false);
        	getEntityManager().refresh(detachedInstance);
//			org.hibernate.Session session = (org.hibernate.Session) getEntityManager().getDelegate();
//		    session.evict(detachedInstance);
//		    detachedInstance = getEntityManager().find(TaLModele.class, detachedInstance.getIdLModele());
			detachedInstance.setLegrain(true);
		    return detachedInstance;
			
		} catch (RuntimeException re) {
			logger.error("refresh failed", re);
			throw re;
		}
	}
	public void persist(TaLModele transientInstance) {
		logger.debug("persisting TaLModele instance");
		try {
			getEntityManager().persist(transientInstance);
			logger.debug("persist successful");
		} catch (RuntimeException re) {
			logger.error("persist failed", re);
			throw re;
		}
	}

	public void remove(TaLModele persistentInstance) {
		logger.debug("removing TaLModele instance");
		try {
			getEntityManager().remove(persistentInstance);
			logger.debug("remove successful");
		} catch (RuntimeException re) {
			logger.error("remove failed", re);
			throw re;
		}
	}

	public TaLModele merge(TaLModele detachedInstance) {
		logger.debug("merging TaLModele instance");
		try {
			TaLModele result = getEntityManager().merge(detachedInstance);
			logger.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			logger.error("merge failed", re);
			throw re;
		}
	}

	public TaLModele findById(int id) {
		logger.debug("getting TaLModele instance with id: " + id);
		try {
			TaLModele instance = getEntityManager().find(TaLModele.class, id);
			logger.debug("get successful");
			return instance;
		} catch (RuntimeException re) {
			logger.error("get failed", re);
			throw re;
		}
	}
	
	/* (non-Javadoc)
	 * @see fr.legrain.articles.dao.ILgrDAO#selectAll()
	 */
	public List<TaLModele> selectAll() {
		logger.debug("selectAll TaLModele");
		try {
			Query ejbQuery = getEntityManager().createQuery(JPQLQuery);
			List<TaLModele> l = ejbQuery.getResultList();
			logger.debug("selectAll successful");
			return l;
		} catch (RuntimeException re) {
			logger.error("selectAll failed", re);
			throw re;
		}
	}
}
