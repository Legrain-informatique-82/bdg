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
 * Home object for domain model class TaInfosAvisEcheance.
 * @see fr.legrain.documents.dao.TaInfosAvisEcheance
 * @author Hibernate Tools
 */
public class TaInfosAvisEcheanceDAO extends AbstractApplicationDAO<TaInfosAvisEcheance> {

//	private static final Log log = LogFactory.getLog(TaInfosAvisEcheanceDAO.class);
	static Logger logger = Logger.getLogger(TaInfosAvisEcheanceDAO.class);
	
	private String defaultJPQLQuery = "select a from TaInfosAvisEcheance a";

	public TaInfosAvisEcheanceDAO(){
		this(null);
	}
	
	public TaInfosAvisEcheanceDAO(EntityManager em){
		if(em!=null) {
			setEm(em);
		}
//		champIdTable=ctrlGeneraux.getID_TABLE(TaInfosAvisEcheance.class.getSimpleName());
		initChampId(TaInfosAvisEcheance.class);
		setJPQLQuery(defaultJPQLQuery);
		setListeChampMaj(Const.C_FICHIER_INI_CHAMPMAJ);
		initNomTable(new TaInfosAvisEcheance());
	}
	public TaInfosAvisEcheance refresh(TaInfosAvisEcheance detachedInstance) {
		logger.debug("refresh instance");
		try {
			getEntityManager().refresh(detachedInstance);
//			org.hibernate.Session session = (org.hibernate.Session) getEntityManager().getDelegate();
//		    session.evict(detachedInstance);
//		    detachedInstance = getEntityManager().find(TaInfosAvisEcheance.class, detachedInstance.getIdInfosDocument());
		    return detachedInstance;
		} catch (RuntimeException re) {
			logger.error("refresh failed", re);
			throw re;
		}
	}
	public void persist(TaInfosAvisEcheance transientInstance) {
		logger.debug("persisting TaInfosAvisEcheance instance");
		try {
			getEntityManager().persist(transientInstance);
			logger.debug("persist successful");
		} catch (RuntimeException re) {
			logger.error("persist failed", re);
			throw re;
		}
	}

	public void remove(TaInfosAvisEcheance persistentInstance) {
		logger.debug("removing TaInfosAvisEcheance instance");
		try {
			getEntityManager().remove(persistentInstance);
			logger.debug("remove successful");
		} catch (RuntimeException re) {
			logger.error("remove failed", re);
			throw re;
		}
	}

	public TaInfosAvisEcheance merge(TaInfosAvisEcheance detachedInstance) {
		logger.debug("merging TaInfosAvisEcheance instance");
		try {
			TaInfosAvisEcheance result = getEntityManager().merge(detachedInstance);
			logger.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			logger.error("merge failed", re);
			throw re;
		}
	}

	public TaInfosAvisEcheance findById(int id) {
		logger.debug("getting TaInfosAvisEcheance instance with id: " + id);
		try {
			TaInfosAvisEcheance instance = getEntityManager().find(TaInfosAvisEcheance.class, id);
			logger.debug("get successful");
			return instance;
		} catch (RuntimeException re) {
			logger.error("get failed", re);
			throw re;
		}
	}
	public TaInfosAvisEcheance findByCodeAvisEcheance(String code) {
		logger.debug("getting TaInfosAvisEcheance instance with code document : " + code);
		try {
			if(!code.equals("")){
				Query query = getEntityManager().createQuery("select a from TaInfosAvisEcheance a where a.taDocument.codeDocument='"+code+"'");
				TaInfosAvisEcheance instance = (TaInfosAvisEcheance)query.getSingleResult();
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
	public List<TaInfosAvisEcheance> selectAll() {
		logger.debug("selectAll TaInfosAvisEcheance");
		try {
			Query ejbQuery = getEntityManager().createQuery(JPQLQuery);
			List<TaInfosAvisEcheance> l = ejbQuery.getResultList();
			logger.debug("selectAll successful");
			return l;
		} catch (RuntimeException re) {
			logger.error("selectAll failed", re);
			throw re;
		}
	}
}
