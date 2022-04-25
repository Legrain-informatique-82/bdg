package fr.legrain.documents.dao;

// Generated Apr 7, 2009 3:27:24 PM by Hibernate Tools 3.2.0.CR1

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import fr.legrain.articles.dao.TaPrix;
import fr.legrain.articles.dao.TaUnite;
import fr.legrain.gestCom.Appli.AbstractApplicationDAO;
import fr.legrain.gestCom.Appli.Const;

/**
 * Home object for domain model class TaInfosAvoir2.
 * @see fr.legrain.documents.dao.TaInfosAvoir2
 * @author Hibernate Tools
 */
public class TaInfosAvoirDAO extends AbstractApplicationDAO<TaInfosAvoir> {

//	private static final Log log = LogFactory.getLog(TaInfosAvoirDAO.class);
	static Logger logger = Logger.getLogger(TaInfosAvoirDAO.class);
	
	private String defaultJPQLQuery = "select a from TaInfosAvoir a";
	
	public TaInfosAvoirDAO(){
		this(null);
	}
	
	public TaInfosAvoirDAO(EntityManager em){
		if(em!=null) {
			setEm(em);
		}
//		champIdTable=ctrlGeneraux.getID_TABLE(TaInfosAvoir.class.getSimpleName());
		initChampId(TaInfosAvoir.class);
		setJPQLQuery(defaultJPQLQuery);
		setListeChampMaj(Const.C_FICHIER_INI_CHAMPMAJ);
		initNomTable(new TaInfosAvoir());
	}
	public TaInfosAvoir refresh(TaInfosAvoir detachedInstance) {
		logger.debug("refresh instance");
		try {
			getEntityManager().refresh(detachedInstance);
//			org.hibernate.Session session = (org.hibernate.Session) getEntityManager().getDelegate();
//		    session.evict(detachedInstance);
//		    detachedInstance = getEntityManager().find(TaInfosAvoir.class, detachedInstance.getIdInfosDocument());
		    return detachedInstance;
			
		} catch (RuntimeException re) {
			logger.error("refresh failed", re);
			throw re;
		}
	}
	public void persist(TaInfosAvoir transientInstance) {
		logger.debug("persisting TaInfosAvoir instance");
		try {
			getEntityManager().persist(transientInstance);
			logger.debug("persist successful");
		} catch (RuntimeException re) {
			logger.error("persist failed", re);
			throw re;
		}
	}

	public void remove(TaInfosAvoir persistentInstance) {
		logger.debug("removing TaInfosAvoir instance");
		try {
			getEntityManager().remove(persistentInstance);
			logger.debug("remove successful");
		} catch (RuntimeException re) {
			logger.error("remove failed", re);
			throw re;
		}
	}

	public TaInfosAvoir merge(TaInfosAvoir detachedInstance) {
		logger.debug("merging TaInfosAvoir instance");
		try {
			TaInfosAvoir result = getEntityManager().merge(detachedInstance);
			logger.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			logger.error("merge failed", re);
			throw re;
		}
	}

	public TaInfosAvoir findById(int id) {
		logger.debug("getting TaInfosAvoir instance with id: " + id);
		try {
			TaInfosAvoir instance = getEntityManager().find(TaInfosAvoir.class, id);
			logger.debug("get successful");
			return instance;
		} catch (RuntimeException re) {
			logger.error("get failed", re);
			throw re;
		}
	}
	
	public TaInfosAvoir findByCodeAvoir(String code) {
		logger.debug("getting TaInfosAvoir instance with code Document : " + code);
		try {
			if(!code.equals("")){
				Query query = getEntityManager().createQuery("select a from TaInfosAvoir a " +
						"where a.taDocument.codeDocument='"+code+"'");
				TaInfosAvoir instance = (TaInfosAvoir)query.getSingleResult();
				logger.debug("get successful");
				return instance;
			}
			return null;
		} catch (NoResultException re) {
			return null;
		} catch (RuntimeException re) {
			logger.error("get failed", re);
			throw re;
		}
	}	
	
	/* (non-Javadoc)
	 * @see fr.legrain.articles.dao.ILgrDAO#selectAll()
	 */
	public List<TaInfosAvoir> selectAll() {
		logger.debug("selectAll TaInfosAvoir");
		try {
			Query ejbQuery = getEntityManager().createQuery(JPQLQuery);
			List<TaInfosAvoir> l = ejbQuery.getResultList();
			logger.debug("selectAll successful");
			return l;
		} catch (RuntimeException re) {
			logger.error("selectAll failed", re);
			throw re;
		}
	}
}
