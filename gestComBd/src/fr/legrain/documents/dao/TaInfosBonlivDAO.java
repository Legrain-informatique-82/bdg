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
 * Home object for domain model class TaInfosBonliv.
 * @see fr.legrain.documents.dao.TaInfosBonliv
 * @author Hibernate Tools
 */
public class TaInfosBonlivDAO extends AbstractApplicationDAO<TaInfosBonliv> {

//	private static final Log log = LogFactory.getLog(TaInfosBonlivDAO.class);
	static Logger logger = Logger.getLogger(TaInfosBonlivDAO.class);
	
	private String defaultJPQLQuery = "select a from TaInfosBonliv a";
	
	public TaInfosBonlivDAO(){
		this(null);
	}
	
	public TaInfosBonlivDAO(EntityManager em){
		if(em!=null) {
			setEm(em);
		}
//		champIdTable=ctrlGeneraux.getID_TABLE(TaInfosBonliv.class.getSimpleName());
		initChampId(TaInfosBonliv.class);
		setJPQLQuery(defaultJPQLQuery);
		setListeChampMaj(Const.C_FICHIER_INI_CHAMPMAJ);
		initNomTable(new TaInfosBonliv());
	}
	public TaInfosBonliv refresh(TaInfosBonliv detachedInstance) {
		logger.debug("refresh instance");
		try {
			getEntityManager().refresh(detachedInstance);
//			org.hibernate.Session session = (org.hibernate.Session) getEntityManager().getDelegate();
//		    session.evict(detachedInstance);
//		    detachedInstance = getEntityManager().find(TaInfosBonliv.class, detachedInstance.getIdInfosDocument());
		    return detachedInstance;
		} catch (RuntimeException re) {
			logger.error("refresh failed", re);
			throw re;
		}
	}
	public void persist(TaInfosBonliv transientInstance) {
		logger.debug("persisting TaInfosBonliv instance");
		try {
			getEntityManager().persist(transientInstance);
			logger.debug("persist successful");
		} catch (RuntimeException re) {
			logger.error("persist failed", re);
			throw re;
		}
	}

	public void remove(TaInfosBonliv persistentInstance) {
		logger.debug("removing TaInfosBonliv instance");
		try {
			getEntityManager().remove(persistentInstance);
			logger.debug("remove successful");
		} catch (RuntimeException re) {
			logger.error("remove failed", re);
			throw re;
		}
	}

	public TaInfosBonliv merge(TaInfosBonliv detachedInstance) {
		logger.debug("merging TaInfosBonliv instance");
		try {
			TaInfosBonliv result = getEntityManager().merge(detachedInstance);
			logger.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			logger.error("merge failed", re);
			throw re;
		}
	}

	public TaInfosBonliv findById(int id) {
		logger.debug("getting TaInfosBonliv instance with id: " + id);
		try {
			TaInfosBonliv instance = getEntityManager()
					.find(TaInfosBonliv.class, id);
			logger.debug("get successful");
			return instance;
		} catch (RuntimeException re) {
			logger.error("get failed", re);
			throw re;
		}
	}
	
	public TaInfosBonliv findByCodeBonliv(String code) {
		logger.debug("getting TaInfosBonliv instance with code InfosBonliv : " + code);
		try {
			if(!code.equals("")){
				Query query = getEntityManager().createQuery("select a from TaInfosBonliv a where a.taDocument.codeDocument='"+code+"'");
				TaInfosBonliv instance = (TaInfosBonliv)query.getSingleResult();
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
	public List<TaInfosBonliv> selectAll() {
		logger.debug("selectAll TaInfosBonliv");
		try {
			Query ejbQuery = getEntityManager().createQuery(JPQLQuery);
			List<TaInfosBonliv> l = ejbQuery.getResultList();
			logger.debug("selectAll successful");
			return l;
		} catch (RuntimeException re) {
			logger.error("selectAll failed", re);
			throw re;
		}
	}
}
