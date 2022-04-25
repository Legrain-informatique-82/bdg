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
 * Home object for domain model class TaInfosAcompte.
 * @see fr.legrain.documents.dao.TaInfosAcompte
 * @author Hibernate Tools
 */
public class TaInfosAcompteDAO extends AbstractApplicationDAO<TaInfosAcompte> {

//	private static final Log log = LogFactory.getLog(TaInfosAcompteDAO.class);
	static Logger logger = Logger.getLogger(TaInfosAcompteDAO.class);
	
	private String defaultJPQLQuery = "select a from TaInfosAcompte a";
	
	public TaInfosAcompteDAO(){
		this(null);
	}
	
	public TaInfosAcompteDAO(EntityManager em){
		if(em!=null) {
			setEm(em);
		}
//		champIdTable=ctrlGeneraux.getID_TABLE(TaInfosAcompte.class.getSimpleName());
		initChampId(TaInfosAcompte.class);
		setJPQLQuery(defaultJPQLQuery);
		setListeChampMaj(Const.C_FICHIER_INI_CHAMPMAJ);
		initNomTable(new TaInfosAcompte());
	}
	public TaInfosAcompte refresh(TaInfosAcompte detachedInstance) {
		logger.debug("refresh instance");
		try {
			getEntityManager().refresh(detachedInstance);
//			org.hibernate.Session session = (org.hibernate.Session) getEntityManager().getDelegate();
//		    session.evict(detachedInstance);
//		    detachedInstance = getEntityManager().find(TaInfosAcompte.class, detachedInstance.getIdInfosDocument());
		    return detachedInstance;
			
		} catch (RuntimeException re) {
			logger.error("refresh failed", re);
			throw re;
		}
	}
	public void persist(TaInfosAcompte transientInstance) {
		logger.debug("persisting TaInfosAcompte instance");
		try {
			getEntityManager().persist(transientInstance);
			logger.debug("persist successful");
		} catch (RuntimeException re) {
			logger.error("persist failed", re);
			throw re;
		}
	}

	public void remove(TaInfosAcompte persistentInstance) {
		logger.debug("removing TaInfosAcompte instance");
		try {
			getEntityManager().remove(persistentInstance);
			logger.debug("remove successful");
		} catch (RuntimeException re) {
			logger.error("remove failed", re);
			throw re;
		}
	}

	public TaInfosAcompte merge(TaInfosAcompte detachedInstance) {
		logger.debug("merging TaInfosAcompte instance");
		try {
			TaInfosAcompte result = getEntityManager().merge(detachedInstance);
			logger.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			logger.error("merge failed", re);
			throw re;
		}
	}

	public TaInfosAcompte findById(int id) {
		logger.debug("getting TaInfosAcompte instance with id: " + id);
		try {
			TaInfosAcompte instance = getEntityManager().find(TaInfosAcompte.class,
					id);
			logger.debug("get successful");
			return instance;
		} catch (RuntimeException re) {
			logger.error("get failed", re);
			throw re;
		}
	}
	
	public TaInfosAcompte findByCodeAcompte(String code) {
		logger.debug("getting TaInfosAcompte instance with code Document: " + code);
		try {
			if(!code.equals("")){
				Query query = getEntityManager().createQuery("select a from TaInfosAcompte " +
						"a where a.taDocument.codeDocument='"+code+"'");
				TaInfosAcompte instance = (TaInfosAcompte)query.getSingleResult();
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
	public List<TaInfosAcompte> selectAll() {
		logger.debug("selectAll TaInfosAcompte");
		try {
			Query ejbQuery = getEntityManager().createQuery(JPQLQuery);
			List<TaInfosAcompte> l = ejbQuery.getResultList();
			logger.debug("selectAll successful");
			return l;
		} catch (RuntimeException re) {
			logger.error("selectAll failed", re);
			throw re;
		}
	}

}
