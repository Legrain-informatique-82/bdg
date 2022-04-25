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
 * Home object for domain model class TaLAcompte.
 * @see fr.legrain.documents.dao.TaLAcompte
 * @author Hibernate Tools
 */
public class TaLAcompteDAO extends AbstractApplicationDAO<TaLAcompte> {

//	private static final Log log = LogFactory.getLog(TaLAcompteDAO.class);
	static Logger logger = Logger.getLogger(TaLAcompteDAO.class);
	boolean  raffraichir=false;
	private String defaultJPQLQuery = "select a from TaLAcompte a";
	
	public TaLAcompteDAO(){
		this(null);
	}
	
	public TaLAcompteDAO(EntityManager em){
		if(em!=null) {
			setEm(em);
		}
//		champIdTable=ctrlGeneraux.getID_TABLE(TaLAcompte.class.getSimpleName());
		initChampId(TaLAcompte.class);
		setJPQLQuery(defaultJPQLQuery);
		setListeChampMaj(Const.C_FICHIER_INI_CHAMPMAJ);
		initNomTable(new TaLAcompte());
	}
	public TaLAcompte refresh(TaLAcompte detachedInstance) {
		logger.debug("refresh instance");
		try {
			detachedInstance.setLegrain(false);
        	getEntityManager().refresh(detachedInstance);
//			org.hibernate.Session session = (org.hibernate.Session) getEntityManager().getDelegate();
//		    session.evict(detachedInstance);
//		    detachedInstance = getEntityManager().find(TaLAcompte.class, detachedInstance.getIdLDocument());
			detachedInstance.setLegrain(true);
		    return detachedInstance;
			
		} catch (RuntimeException re) {
			logger.error("refresh failed", re);
			throw re;
		}
	}
	public void persist(TaLAcompte transientInstance) {
		logger.debug("persisting TaLAcompte instance");
		try {
			getEntityManager().persist(transientInstance);
			logger.debug("persist successful");
		} catch (RuntimeException re) {
			logger.error("persist failed", re);
			throw re;
		}
	}

	public void remove(TaLAcompte persistentInstance) {
		logger.debug("removing TaLAcompte instance");
		try {
			getEntityManager().remove(persistentInstance);
			logger.debug("remove successful");
		} catch (RuntimeException re) {
			logger.error("remove failed", re);
			throw re;
		}
	}

	public TaLAcompte merge(TaLAcompte detachedInstance) {
		logger.debug("merging TaLAcompte instance");
		try {
			TaLAcompte result = getEntityManager().merge(detachedInstance);
			logger.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			logger.error("merge failed", re);
			throw re;
		}
	}

	public TaLAcompte findById(int id) {
		logger.debug("getting TaLAcompte instance with id: " + id);
		try {
			TaLAcompte instance = getEntityManager().find(TaLAcompte.class, id);
			logger.debug("get successful");
			return instance;
		} catch (RuntimeException re) {
			logger.error("get failed", re);
			throw re;
		}
	}

	public void annuler(TaLAcompte persistentInstance) throws Exception{
		persistentInstance.setLegrain(false);
		super.annuler(persistentInstance);
	}
	
	/* (non-Javadoc)
	 * @see fr.legrain.articles.dao.ILgrDAO#selectAll()
	 */
	public List<TaLAcompte> selectAll() {
		logger.debug("selectAll TaLAcompte");
		try {
			Query ejbQuery = getEntityManager().createQuery(JPQLQuery);
			List<TaLAcompte> l = ejbQuery.getResultList();
			logger.debug("selectAll successful");
			return l;
		} catch (RuntimeException re) {
			logger.error("selectAll failed", re);
			throw re;
		}
	}
}
