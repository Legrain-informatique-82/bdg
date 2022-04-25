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
 * Home object for domain model class TaRAcompte.
 * @see fr.legrain.documents.dao.TaRAcompte
 * @author Hibernate Tools
 */
public class TaRAcompteDAO extends AbstractApplicationDAO<TaRAcompte> {

//	private static final Log log = LogFactory.getLog(TaRAcompteDAO.class);
	static Logger logger = Logger.getLogger(TaRAcompteDAO.class);
	
	private String defaultJPQLQuery = "select a from TaRAcompte a";
	
	public TaRAcompteDAO(){
		this(null);
	}
	
	public TaRAcompteDAO(EntityManager em){
		if(em!=null) {
			setEm(em);
		}
//		champIdTable=ctrlGeneraux.getID_TABLE(TaRAcompte.class.getSimpleName());
		initChampId(TaRAcompte.class);
		setJPQLQuery(defaultJPQLQuery);
		setListeChampMaj(Const.C_FICHIER_INI_CHAMPMAJ);
		initNomTable(new TaRAcompte());
	}
	public TaRAcompte refresh(TaRAcompte detachedInstance) {
		logger.debug("refresh instance");
		try {
			getEntityManager().refresh(detachedInstance);
//			org.hibernate.Session session = (org.hibernate.Session) getEntityManager().getDelegate();
//		    session.evict(detachedInstance);
//		    detachedInstance = getEntityManager().find(TaRAcompte.class, detachedInstance.getId());
		    return detachedInstance;

		} catch (RuntimeException re) {
			logger.error("refresh failed", re);
			throw re;
		}
	}
	public void persist(TaRAcompte transientInstance) {
		logger.debug("persisting TaRAcompte instance");
		try {
			getEntityManager().persist(transientInstance);
			logger.debug("persist successful");
		} catch (RuntimeException re) {
			logger.error("persist failed", re);
			throw re;
		}
	}

	public void remove(TaRAcompte persistentInstance) {
		logger.debug("removing TaRAcompte instance");
		try {
			getEntityManager().remove(persistentInstance);
			logger.debug("remove successful");
		} catch (RuntimeException re) {
			logger.error("remove failed", re);
			throw re;
		}
	}

	public TaRAcompte merge(TaRAcompte detachedInstance) {
		logger.debug("merging TaRAcompte instance");
		try {
			TaRAcompte result = getEntityManager().merge(detachedInstance);
			logger.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			logger.error("merge failed", re);
			throw re;
		}
	}

	public TaRAcompte findById(int id) {
		logger.debug("getting TaRAcompte instance with id: " + id);
		try {
			TaRAcompte instance = getEntityManager().find(TaRAcompte.class, id);
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
	public List<TaRAcompte> selectAll(TaAcompte taAcompte) {
		logger.debug("selectAll TaRAcompte");
		try {
			if(taAcompte!=null){
			Query ejbQuery = getEntityManager().createQuery("select a from TaRAcompte a where a.taAcompte=?");
			ejbQuery.setParameter(1, taAcompte);
			List<TaRAcompte> l = ejbQuery.getResultList();
			logger.debug("selectAll successful");
			return l;
			}
			return null;
		} catch (RuntimeException re) {
			logger.error("selectAll failed", re);
			throw re;
		}
	}

	public List<TaRAcompte> selectSumAcompteDocument(TaAcompte taAcompte) {
		logger.debug("selectAll TaRAcompte");
		try {
			if(taAcompte!=null){
			Query ejbQuery = getEntityManager().createQuery("select a from TaRAcompte a where a.taAcompte=?");
			ejbQuery.setParameter(1, taAcompte);
			List<TaRAcompte> l = ejbQuery.getResultList();
			logger.debug("selectAll successful");
			return l;
			}
			return null;
		} catch (RuntimeException re) {
			logger.error("selectAll failed", re);
			throw re;
		}
	}
	
	@Override
	public List<TaRAcompte> selectAll() {
		// TODO Auto-generated method stub
		return null;
	}
}
