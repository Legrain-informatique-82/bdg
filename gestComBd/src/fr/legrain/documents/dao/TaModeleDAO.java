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
 * Home object for domain model class TaModele.
 * @see fr.legrain.documents.dao.TaModele
 * @author Hibernate Tools
 */
public class TaModeleDAO extends AbstractApplicationDAO<TaModele> {

//	private static final Log log = LogFactory.getLog(TaModeleDAO.class);
	static Logger logger = Logger.getLogger(TaModeleDAO.class);
	
	private String defaultJPQLQuery = "select a from TaModele a";
	
	public TaModeleDAO(){
		this(null);
	}
	
	public TaModeleDAO(EntityManager em){
		if(em!=null) {
			setEm(em);
		}
//		champIdTable=ctrlGeneraux.getID_TABLE(TaModele.class.getSimpleName());
		initChampId(TaModele.class);
		setJPQLQuery(defaultJPQLQuery);
		setListeChampMaj(Const.C_FICHIER_INI_CHAMPMAJ);
		initNomTable(new TaModele());
	}
	public TaModele refresh(TaModele detachedInstance) {
		logger.debug("refresh instance");
		try {
			detachedInstance.setLegrain(false);
        	getEntityManager().refresh(detachedInstance);
//			org.hibernate.Session session = (org.hibernate.Session) getEntityManager().getDelegate();
//		    session.evict(detachedInstance);
//		    detachedInstance = getEntityManager().find(TaModele.class, detachedInstance.getIdModele());
			detachedInstance.setLegrain(true);
		    return detachedInstance;
			
		} catch (RuntimeException re) {
			logger.error("refresh failed", re);
			throw re;
		}
	}
	public void persist(TaModele transientInstance) {
		logger.debug("persisting TaModele instance");
		try {
			getEntityManager().persist(transientInstance);
			logger.debug("persist successful");
		} catch (RuntimeException re) {
			logger.error("persist failed", re);
			throw re;
		}
	}

	public void remove(TaModele persistentInstance) {
		logger.debug("removing TaModele instance");
		try {
			getEntityManager().remove(persistentInstance);
			logger.debug("remove successful");
		} catch (RuntimeException re) {
			logger.error("remove failed", re);
			throw re;
		}
	}

	public TaModele merge(TaModele detachedInstance) {
		logger.debug("merging TaModele instance");
		try {
			TaModele result = getEntityManager().merge(detachedInstance);
			logger.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			logger.error("merge failed", re);
			throw re;
		}
	}

	public TaModele findById(int id) {
		logger.debug("getting TaModele instance with id: " + id);
		try {
			TaModele instance = getEntityManager().find(TaModele.class, id);
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
	public List<TaModele> selectAll() {
		logger.debug("selectAll TaModele");
		try {
			Query ejbQuery = getEntityManager().createQuery(JPQLQuery);
			List<TaModele> l = ejbQuery.getResultList();
			logger.debug("selectAll successful");
			return l;
		} catch (RuntimeException re) {
			logger.error("selectAll failed", re);
			throw re;
		}
	}
}
