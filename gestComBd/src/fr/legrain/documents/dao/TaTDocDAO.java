package fr.legrain.documents.dao;

// Generated Apr 7, 2009 3:27:24 PM by Hibernate Tools 3.2.0.CR1

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import fr.legrain.gestCom.Appli.AbstractApplicationDAO;
import fr.legrain.gestCom.Appli.Const;

/**
 * Home object for domain model class TaTDoc.
 * @see fr.legrain.documents.dao.TaTDoc
 * @author Hibernate Tools
 */
public class TaTDocDAO extends AbstractApplicationDAO<TaTDoc> {

//	private static final Log log = LogFactory.getLog(TaTDocDAO.class);
	static Logger logger = Logger.getLogger(TaTDocDAO.class);
	
	private String defaultJPQLQuery = "select a from TaTDoc a";
	
	public TaTDocDAO(){
		this(null);
	}
	
	public TaTDocDAO(EntityManager em){
		if(em!=null) {
			setEm(em);
		}
//		champIdTable=ctrlGeneraux.getID_TABLE(TaTDoc.class.getSimpleName());
		initChampId(TaTDoc.class);
		setJPQLQuery(defaultJPQLQuery);
		setListeChampMaj(Const.C_FICHIER_INI_CHAMPMAJ);
		initNomTable(new TaTDoc());
	}
	public TaTDoc refresh(TaTDoc detachedInstance) {
		logger.debug("refresh instance");
		try {
			getEntityManager().refresh(detachedInstance);
//			org.hibernate.Session session = (org.hibernate.Session) getEntityManager().getDelegate();
//		    session.evict(detachedInstance);
//		    detachedInstance = getEntityManager().find(TaTDoc.class, detachedInstance.getIdTDoc());
		    return detachedInstance;
			
		} catch (RuntimeException re) {
			logger.error("refresh failed", re);
			throw re;
		}
	}
	public void persist(TaTDoc transientInstance) {
		logger.debug("persisting TaTDoc instance");
		try {
			getEntityManager().persist(transientInstance);
			logger.debug("persist successful");
		} catch (RuntimeException re) {
			logger.error("persist failed", re);
			throw re;
		}
	}

	public void remove(TaTDoc persistentInstance) {
		logger.debug("removing TaTDoc instance");
		try {
			getEntityManager().remove(persistentInstance);
			logger.debug("remove successful");
		} catch (RuntimeException re) {
			logger.error("remove failed", re);
			throw re;
		}
	}

	public TaTDoc merge(TaTDoc detachedInstance) {
		logger.debug("merging TaTDoc instance");
		try {
			TaTDoc result = getEntityManager().merge(detachedInstance);
			logger.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			logger.error("merge failed", re);
			throw re;
		}
	}

	public TaTDoc findById(int id) {
		logger.debug("getting TaTDoc instance with id: " + id);
		try {
			TaTDoc instance = getEntityManager().find(TaTDoc.class, id);
			logger.debug("get successful");
			return instance;
		} catch (RuntimeException re) {
			logger.error("get failed", re);
			throw re;
		}
	}
	
	public TaTDoc findByCode(String code) {
		logger.debug("getting TaTDoc instance with code: " + code);
		try {
			if(!code.equals("")){
			Query query = getEntityManager().createQuery("select f from TaTDoc f where f.codeTDoc='"+code+"'");
			TaTDoc instance = (TaTDoc)query.getSingleResult();
			logger.debug("get successful");
			return instance;
			}
			return null;
		} catch (RuntimeException re) {
			logger.error("get failed", re);
			throw re;
		}
	}
	
	/* (non-Javadoc)
	 * @see fr.legrain.articles.dao.ILgrDAO#selectAll()
	 */
	public List<TaTDoc> selectAll() {
		logger.debug("selectAll TaTDoc");
		try {
			Query ejbQuery = getEntityManager().createQuery(JPQLQuery);
			List<TaTDoc> l = ejbQuery.getResultList();
			logger.debug("selectAll successful");
			return l;
		} catch (RuntimeException re) {
			logger.error("selectAll failed", re);
			throw re;
		}
	}
}
