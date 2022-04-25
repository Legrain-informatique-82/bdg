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
 * Home object for domain model class TaInfosDevis.
 * @see fr.legrain.documents.dao.TaInfosDevis
 * @author Hibernate Tools
 */
public class TaInfosDevisDAO extends AbstractApplicationDAO<TaInfosDevis> {

//	private static final Log log = LogFactory.getLog(TaInfosDevisDAO.class);
	static Logger logger = Logger.getLogger(TaInfosDevisDAO.class);
	
	private String defaultJPQLQuery = "select a from TaInfosDevis a";

	public TaInfosDevisDAO(){
		this(null);
	}
	
	public TaInfosDevisDAO(EntityManager em){
		if(em!=null) {
			setEm(em);
		}
//		champIdTable=ctrlGeneraux.getID_TABLE(TaInfosDevis.class.getSimpleName());
		initChampId(TaInfosDevis.class);
		setJPQLQuery(defaultJPQLQuery);
		setListeChampMaj(Const.C_FICHIER_INI_CHAMPMAJ);
		initNomTable(new TaInfosDevis());
	}
	public TaInfosDevis refresh(TaInfosDevis detachedInstance) {
		logger.debug("refresh instance");
		try {
			getEntityManager().refresh(detachedInstance);
//			org.hibernate.Session session = (org.hibernate.Session) getEntityManager().getDelegate();
//		    session.evict(detachedInstance);
//		    detachedInstance = getEntityManager().find(TaInfosDevis.class, detachedInstance.getIdInfosDocument());
		    return detachedInstance;
		} catch (RuntimeException re) {
			logger.error("refresh failed", re);
			throw re;
		}
	}
	public void persist(TaInfosDevis transientInstance) {
		logger.debug("persisting TaInfosDevis instance");
		try {
			getEntityManager().persist(transientInstance);
			logger.debug("persist successful");
		} catch (RuntimeException re) {
			logger.error("persist failed", re);
			throw re;
		}
	}

	public void remove(TaInfosDevis persistentInstance) {
		logger.debug("removing TaInfosDevis instance");
		try {
			getEntityManager().remove(persistentInstance);
			logger.debug("remove successful");
		} catch (RuntimeException re) {
			logger.error("remove failed", re);
			throw re;
		}
	}

	public TaInfosDevis merge(TaInfosDevis detachedInstance) {
		logger.debug("merging TaInfosDevis instance");
		try {
			TaInfosDevis result = getEntityManager().merge(detachedInstance);
			logger.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			logger.error("merge failed", re);
			throw re;
		}
	}

	public TaInfosDevis findById(int id) {
		logger.debug("getting TaInfosDevis instance with id: " + id);
		try {
			TaInfosDevis instance = getEntityManager().find(TaInfosDevis.class, id);
			logger.debug("get successful");
			return instance;
		} catch (RuntimeException re) {
			logger.error("get failed", re);
			throw re;
		}
	}
	public TaInfosDevis findByCodeDevis(String code) {
		logger.debug("getting TaInfosDevis instance with code document : " + code);
		try {
			if(!code.equals("")){
				Query query = getEntityManager().createQuery("select a from TaInfosDevis a where a.taDocument.codeDocument='"+code+"'");
				TaInfosDevis instance = (TaInfosDevis)query.getSingleResult();
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
	public List<TaInfosDevis> selectAll() {
		logger.debug("selectAll TaInfosDevis");
		try {
			Query ejbQuery = getEntityManager().createQuery(JPQLQuery);
			List<TaInfosDevis> l = ejbQuery.getResultList();
			logger.debug("selectAll successful");
			return l;
		} catch (RuntimeException re) {
			logger.error("selectAll failed", re);
			throw re;
		}
	}
}
