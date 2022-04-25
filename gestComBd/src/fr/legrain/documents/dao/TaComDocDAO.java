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
 * Home object for domain model class TaComDoc.
 * @see fr.legrain.documents.dao.TaComDoc
 * @author Hibernate Tools
 */
public class TaComDocDAO extends AbstractApplicationDAO<TaComDoc> {

//	private static final Log log = LogFactory.getLog(TaComDocDAO.class);
	static Logger logger = Logger.getLogger(TaComDocDAO.class);
	
	private String defaultJPQLQuery = "select a from TaComDoc a";
	
	public TaComDocDAO(){
		this(null);
	}
	
	public TaComDocDAO(EntityManager em){
		if(em!=null) {
			setEm(em);
		}
//		champIdTable=ctrlGeneraux.getID_TABLE(TaComDoc.class.getSimpleName());
		initChampId(TaComDoc.class);
		setJPQLQuery(defaultJPQLQuery);
		setListeChampMaj(Const.C_FICHIER_INI_CHAMPMAJ);
		initNomTable(new TaComDoc());
	}
	public TaComDoc refresh(TaComDoc detachedInstance) {
		logger.debug("refresh instance");
		try {
			getEntityManager().refresh(detachedInstance);
//			org.hibernate.Session session = (org.hibernate.Session) getEntityManager().getDelegate();
//		    session.evict(detachedInstance);
//		    detachedInstance = getEntityManager().find(TaComDoc.class, detachedInstance.getIdDocument());
		    return detachedInstance;

		} catch (RuntimeException re) {
			logger.error("refresh failed", re);
			throw re;
		}
	}
	public void persist(TaComDoc transientInstance) {
		logger.debug("persisting TaComDoc instance");
		try {
			getEntityManager().persist(transientInstance);
			logger.debug("persist successful");
		} catch (RuntimeException re) {
			logger.error("persist failed", re);
			throw re;
		}
	}

	public void remove(TaComDoc persistentInstance) {
		logger.debug("removing TaComDoc instance");
		try {
			getEntityManager().remove(persistentInstance);
			logger.debug("remove successful");
		} catch (RuntimeException re) {
			logger.error("remove failed", re);
			throw re;
		}
	}

	public TaComDoc merge(TaComDoc detachedInstance) {
		logger.debug("merging TaComDoc instance");
		try {
			TaComDoc result = getEntityManager().merge(detachedInstance);
			logger.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			logger.error("merge failed", re);
			throw re;
		}
	}

	public TaComDoc findById(int id) {
		logger.debug("getting TaComDoc instance with id: " + id);
		try {
			TaComDoc instance = getEntityManager().find(TaComDoc.class, id);
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
	public List<TaComDoc> selectAll() {
		logger.debug("selectAll TaComDoc");
		try {
			Query ejbQuery = getEntityManager().createQuery(JPQLQuery);
			List<TaComDoc> l = ejbQuery.getResultList();
			logger.debug("selectAll successful");
			return l;
		} catch (RuntimeException re) {
			logger.error("selectAll failed", re);
			throw re;
		}
	}
}
