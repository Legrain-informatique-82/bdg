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
 * Home object for domain model class TaLDevis.
 * @see fr.legrain.documents.dao.TaLDevis
 * @author Hibernate Tools
 */
public class TaLDevisDAO extends AbstractApplicationDAO<TaLDevis> {

//	private static final Log log = LogFactory.getLog(TaLDevisDAO.class);
	static Logger logger = Logger.getLogger(TaLDevisDAO.class);
	
	private String defaultJPQLQuery = "select a from TaLDevis a";
	
	public TaLDevisDAO(){
		this(null);
	}
	
	public TaLDevisDAO(EntityManager em){
		if(em!=null) {
			setEm(em);
		}
//		champIdTable=ctrlGeneraux.getID_TABLE(TaLDevis.class.getSimpleName());
		initChampId(TaLDevis.class);
		setJPQLQuery(defaultJPQLQuery);
		setListeChampMaj(Const.C_FICHIER_INI_CHAMPMAJ);
		initNomTable(new TaLDevis());
	}
	public TaLDevis refresh(TaLDevis detachedInstance) {
		logger.debug("refresh instance");
		try {
			detachedInstance.setLegrain(false);
        	getEntityManager().refresh(detachedInstance);
//			org.hibernate.Session session = (org.hibernate.Session) getEntityManager().getDelegate();
//		    session.evict(detachedInstance);
//		    detachedInstance = getEntityManager().find(TaLDevis.class, detachedInstance.getIdLDocument());
			detachedInstance.setLegrain(true);
		    return detachedInstance;
			
		} catch (RuntimeException re) {
			logger.error("refresh failed", re);
			throw re;
		}
	}
	public void persist(TaLDevis transientInstance) {
		logger.debug("persisting TaLDevis instance");
		try {
			getEntityManager().persist(transientInstance);
			logger.debug("persist successful");
		} catch (RuntimeException re) {
			logger.error("persist failed", re);
			throw re;
		}
	}

	public void remove(TaLDevis persistentInstance) {
		logger.debug("removing TaLDevis instance");
		try {
			getEntityManager().remove(persistentInstance);
			logger.debug("remove successful");
		} catch (RuntimeException re) {
			logger.error("remove failed", re);
			throw re;
		}
	}

	public TaLDevis merge(TaLDevis detachedInstance) {
		logger.debug("merging TaLDevis instance");
		try {
			TaLDevis result = getEntityManager().merge(detachedInstance);
			logger.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			logger.error("merge failed", re);
			throw re;
		}
	}

	public TaLDevis findById(int id) {
		logger.debug("getting TaLDevis instance with id: " + id);
		try {
			TaLDevis instance = getEntityManager().find(TaLDevis.class, id);
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
	public List<TaLDevis> selectAll() {
		logger.debug("selectAll TaLDevis");
		try {
			Query ejbQuery = getEntityManager().createQuery(JPQLQuery);
			List<TaLDevis> l = ejbQuery.getResultList();
			logger.debug("selectAll successful");
			return l;
		} catch (RuntimeException re) {
			logger.error("selectAll failed", re);
			throw re;
		}
	}
}
