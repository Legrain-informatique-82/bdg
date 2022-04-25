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
public class TaInfosBoncdeDAO extends AbstractApplicationDAO<TaInfosBoncde> {

//	private static final Log log = LogFactory.getLog(TaInfosDevisDAO.class);
	static Logger logger = Logger.getLogger(TaInfosBoncdeDAO.class);
	
	private String defaultJPQLQuery = "select a from TaInfosBoncde a";
	
	public TaInfosBoncdeDAO(){
		this(null);
	}
	
	public TaInfosBoncdeDAO(EntityManager em){
		if(em!=null) {
			setEm(em);
		}
//		champIdTable=ctrlGeneraux.getID_TABLE(TaInfosBoncde.class.getSimpleName());
		initChampId(TaInfosBoncde.class);
		setJPQLQuery(defaultJPQLQuery);
		setListeChampMaj(Const.C_FICHIER_INI_CHAMPMAJ);
		initNomTable(new TaInfosBoncde());
	}
	public TaInfosBoncde refresh(TaInfosBoncde detachedInstance) {
		logger.debug("refresh instance");
		try {
			getEntityManager().refresh(detachedInstance);
//			org.hibernate.Session session = (org.hibernate.Session) getEntityManager().getDelegate();
//		    session.evict(detachedInstance);
//		    detachedInstance = getEntityManager().find(TaInfosBoncde.class, detachedInstance.getIdInfosDocument());
		    return detachedInstance;
		} catch (RuntimeException re) {
			logger.error("refresh failed", re);
			throw re;
		}
	}
	public void persist(TaInfosBoncde transientInstance) {
		logger.debug("persisting TaInfosBoncde instance");
		try {
			getEntityManager().persist(transientInstance);
			logger.debug("persist successful");
		} catch (RuntimeException re) {
			logger.error("persist failed", re);
			throw re;
		}
	}

	public void remove(TaInfosBoncde persistentInstance) {
		logger.debug("removing TaInfosBoncde instance");
		try {
			getEntityManager().remove(persistentInstance);
			logger.debug("remove successful");
		} catch (RuntimeException re) {
			logger.error("remove failed", re);
			throw re;
		}
	}

	public TaInfosBoncde merge(TaInfosBoncde detachedInstance) {
		logger.debug("merging TaInfosBoncde instance");
		try {
			TaInfosBoncde result = getEntityManager().merge(detachedInstance);
			logger.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			logger.error("merge failed", re);
			throw re;
		}
	}

	public TaInfosBoncde findById(int id) {
		logger.debug("getting TaInfosBoncde instance with id: " + id);
		try {
			TaInfosBoncde instance = getEntityManager().find(TaInfosBoncde.class, id);
			logger.debug("get successful");
			return instance;
		} catch (RuntimeException re) {
			logger.error("get failed", re);
			throw re;
		}
	}
	public TaInfosBoncde findByCodeBoncde(String code) {
		logger.debug("getting TaInfosBoncde instance with code InfosBoncde : " + code);
		try {
			if(!code.equals("")){
				Query query = getEntityManager().createQuery("select a from TaInfosBoncde a where a.taDocument.codeDocument='"+code+"'");
				TaInfosBoncde instance = (TaInfosBoncde)query.getSingleResult();
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
	public List<TaInfosBoncde> selectAll() {
		logger.debug("selectAll TaInfosBoncde");
		try {
			Query ejbQuery = getEntityManager().createQuery(JPQLQuery);
			List<TaInfosBoncde> l = ejbQuery.getResultList();
			logger.debug("selectAll successful");
			return l;
		} catch (RuntimeException re) {
			logger.error("selectAll failed", re);
			throw re;
		}
	}
}
