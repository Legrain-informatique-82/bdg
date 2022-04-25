package fr.legrain.documents.dao;

// Generated Apr 7, 2009 3:27:24 PM by Hibernate Tools 3.2.0.CR1

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import fr.legrain.articles.dao.TaArticle;
import fr.legrain.articles.dao.TaPrix;
import fr.legrain.articles.dao.TaUnite;
import fr.legrain.gestCom.Appli.AbstractApplicationDAO;
import fr.legrain.gestCom.Appli.Const;

/**
 * Home object for domain model class TaInfosFacture.
 * @see fr.legrain.documents.dao.TaInfosFacture
 * @author Hibernate Tools
 */
public class TaInfosFactureDAO extends AbstractApplicationDAO<TaInfosFacture> {

//	private static final Log log = LogFactory.getLog(TaInfosFactureDAO.class);
	static Logger logger = Logger.getLogger(TaInfosFactureDAO.class);
	
	private String defaultJPQLQuery = "select a from TaInfosFacture a";
	
	public TaInfosFactureDAO(){
		this(null);
	}
	
	public TaInfosFactureDAO(EntityManager em){
		if(em!=null) {
			setEm(em);
		}
//		champIdTable=ctrlGeneraux.getID_TABLE(TaInfosFacture.class.getSimpleName());
		initChampId(TaInfosFacture.class);
		setJPQLQuery(defaultJPQLQuery);
		setListeChampMaj(Const.C_FICHIER_INI_CHAMPMAJ);
		initNomTable(new TaInfosFacture());
	}
	public TaInfosFacture refresh(TaInfosFacture detachedInstance) {
		logger.debug("refresh instance");
		try {
			getEntityManager().refresh(detachedInstance);
//			org.hibernate.Session session = (org.hibernate.Session) getEntityManager().getDelegate();
//		    session.evict(detachedInstance);
//		    detachedInstance = getEntityManager().find(TaInfosFacture.class, detachedInstance.getIdInfosDocument());
		    return detachedInstance;
		} catch (RuntimeException re) {
			logger.error("refresh failed", re);
			throw re;
		}
	}
	public void persist(TaInfosFacture transientInstance) {
		logger.debug("persisting TaInfosFacture instance");
		try {
			getEntityManager().persist(transientInstance);
			logger.debug("persist successful");
		} catch (RuntimeException re) {
			logger.error("persist failed", re);
			throw re;
		}
	}

	public void remove(TaInfosFacture persistentInstance) {
		logger.debug("removing TaInfosFacture instance");
		try {
			getEntityManager().remove(persistentInstance);
			logger.debug("remove successful");
		} catch (RuntimeException re) {
			logger.error("remove failed", re);
			throw re;
		}
	}

	public TaInfosFacture merge(TaInfosFacture detachedInstance) {
		logger.debug("merging TaInfosFacture instance");
		try {
			TaInfosFacture result = getEntityManager().merge(detachedInstance);
			logger.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			logger.error("merge failed", re);
			throw re;
		}
	}

	public TaInfosFacture findById(int id) {
		logger.debug("getting TaInfosFacture instance with id: " + id);
		try {
			TaInfosFacture instance = getEntityManager().find(TaInfosFacture.class,
					id);
			logger.debug("get successful");
			return instance;
		} catch (RuntimeException re) {
			logger.error("get failed", re);
			throw re;
		}
	}
	
	public TaInfosFacture findByCodeFacture(String code) {
		logger.debug("getting TaInfosFacture instance with code Document : " + code);
		try {
			if(!code.equals("")){
				Query query = getEntityManager().createQuery("select a from TaInfosFacture a where a.taDocument.codeDocument='"+code+"'");
				TaInfosFacture instance = (TaInfosFacture)query.getSingleResult();
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
	public List<TaInfosFacture> selectAll() {
		logger.debug("selectAll TaInfosFacture");
		try {
			Query ejbQuery = getEntityManager().createQuery(JPQLQuery);
			List<TaInfosFacture> l = ejbQuery.getResultList();
			logger.debug("selectAll successful");
			return l;
		} catch (RuntimeException re) {
			logger.error("selectAll failed", re);
			throw re;
		}
	}

}
