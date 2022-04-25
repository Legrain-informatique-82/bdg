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
public class TaLPrelevementDAO extends AbstractApplicationDAO<TaLPrelevement> {

//	private static final Log log = LogFactory.getLog(TaLDevisDAO.class);
	static Logger logger = Logger.getLogger(TaLPrelevementDAO.class);
	
	private String defaultJPQLQuery = "select a from TaLPrelevement a";
	
	public TaLPrelevementDAO(){
		this(null);
	}
	
	public TaLPrelevementDAO(EntityManager em){
		if(em!=null) {
			setEm(em);
		}
//		champIdTable=ctrlGeneraux.getID_TABLE(TaLPrelevement.class.getSimpleName());
		initChampId(TaLPrelevement.class);
		setJPQLQuery(defaultJPQLQuery);
		setListeChampMaj(Const.C_FICHIER_INI_CHAMPMAJ);
		initNomTable(new TaLPrelevement());
	}
	public TaLPrelevement refresh(TaLPrelevement detachedInstance) {
		logger.debug("refresh instance");
		try {
			detachedInstance.setLegrain(false);
        	getEntityManager().refresh(detachedInstance);
//			org.hibernate.Session session = (org.hibernate.Session) getEntityManager().getDelegate();
//		    session.evict(detachedInstance);
//		    detachedInstance = getEntityManager().find(TaLPrelevement.class, detachedInstance.getIdLDocument());
			detachedInstance.setLegrain(true);
		    return detachedInstance;
			
		} catch (RuntimeException re) {
			logger.error("refresh failed", re);
			throw re;
		}
	}
	public void persist(TaLPrelevement transientInstance) {
		logger.debug("persisting TaLPrelevement instance");
		try {
			getEntityManager().persist(transientInstance);
			logger.debug("persist successful");
		} catch (RuntimeException re) {
			logger.error("persist failed", re);
			throw re;
		}
	}

	public void remove(TaLPrelevement persistentInstance) {
		logger.debug("removing TaLPrelevement instance");
		try {
			getEntityManager().remove(persistentInstance);
			logger.debug("remove successful");
		} catch (RuntimeException re) {
			logger.error("remove failed", re);
			throw re;
		}
	}

	public TaLPrelevement merge(TaLPrelevement detachedInstance) {
		logger.debug("merging TaLPrelevement instance");
		try {
			TaLPrelevement result = getEntityManager().merge(detachedInstance);
			logger.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			logger.error("merge failed", re);
			throw re;
		}
	}

	public TaLPrelevement findById(int id) {
		logger.debug("getting TaLPrelevement instance with id: " + id);
		try {
			TaLPrelevement instance = getEntityManager().find(TaLPrelevement.class, id);
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
	public List<TaLPrelevement> selectAll() {
		logger.debug("selectAll TaLPrelevement");
		try {
			Query ejbQuery = getEntityManager().createQuery(JPQLQuery);
			List<TaLPrelevement> l = ejbQuery.getResultList();
			logger.debug("selectAll successful");
			return l;
		} catch (RuntimeException re) {
			logger.error("selectAll failed", re);
			throw re;
		}
	}
}
