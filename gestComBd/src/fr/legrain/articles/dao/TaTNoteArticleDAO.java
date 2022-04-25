package fr.legrain.articles.dao;

// Generated Mar 24, 2009 4:44:04 PM by Hibernate Tools 3.2.0.CR1

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import fr.legrain.gestCom.Appli.AbstractApplicationDAO;
import fr.legrain.gestCom.Appli.Const;
import fr.legrain.lib.data.ExceptLgr;

/**
 * Home object for domain model class TaTNoteArticle.
 * @see fr.legrain.tiers.dao.TaTitreTransport
 * @author Hibernate Tools
 */
public class TaTNoteArticleDAO extends AbstractApplicationDAO<TaTNoteArticle>{

	//private static final Log logger = LogFactory.getLog(TaTNoteArticle.class);
	static Logger logger = Logger.getLogger(TaTNoteArticle.class);

	private String defaultJPQLQuery = "select a from TaTNoteArticle a";
	
	public TaTNoteArticleDAO() {
		this(null);
	}
	
	public TaTNoteArticleDAO(EntityManager em){
		if(em!=null) {
			setEm(em);
		}
//		champIdTable=ctrlGeneraux.getID_TABLE(TaTNoteArticle.class.getSimpleName());
		initChampId(TaTNoteArticle.class);
		setJPQLQuery(defaultJPQLQuery);
		setListeChampMaj(Const.C_FICHIER_INI_CHAMPMAJ);
		initNomTable(new TaTNoteArticle());
	}
	public TaTNoteArticle refresh(TaTNoteArticle detachedInstance) {
		logger.debug("refresh instance");
		try {
			org.hibernate.Session session = (org.hibernate.Session) getEntityManager().getDelegate();
		    session.evict(detachedInstance);
		    detachedInstance = getEntityManager().find(TaTNoteArticle.class, detachedInstance.getIdTNoteArticle());
		    return detachedInstance;
		} catch (RuntimeException re) {
			logger.error("refresh failed", re);
			throw re;
		}
	}
	public void persist(TaTNoteArticle transientInstance) {
		logger.debug("persisting TaTNoteArticle instance");
		try {
			getEntityManager().persist(transientInstance);
			logger.debug("persist successful");
		} catch (RuntimeException re) {
			logger.error("persist failed", re);
			throw re;
		}
	}

	public void remove(TaTNoteArticle persistentInstance) {
		logger.debug("removing TaTNoteArticle instance");
		try {
			getEntityManager().remove(persistentInstance);
			logger.debug("remove successful");
		} catch (RuntimeException re) {
			logger.error("remove failed", re);
			throw re;
		}
	}

	public TaTNoteArticle merge(TaTNoteArticle detachedInstance) {
		logger.debug("merging TaTNoteArticle instance");
		try {
			TaTNoteArticle result = getEntityManager().merge(detachedInstance);
			logger.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			logger.error("merge failed", re);
			throw re;
		}
	}

	public TaTNoteArticle findById(int id) {
		logger.debug("getting TaTNoteArticle instance with id: " + id);
		try {
			TaTNoteArticle instance = getEntityManager().find(TaTNoteArticle.class, id);
			logger.debug("get successful");
			return instance;
		} catch (RuntimeException re) {
			logger.error("get failed", re);
			throw re;
		}
	}
	
	public TaTNoteArticle findByCode(String code) {
		logger.debug("getting TaTNoteArticle instance with code: " + code);
		try {
			if(!code.equals("")){
			Query query = getEntityManager().createQuery("select f from TaTNoteArticle f where f.codeTNoteArticle='"+code+"'");
			TaTNoteArticle instance = (TaTNoteArticle)query.getSingleResult();
			logger.debug("get successful");
			return instance;
			}
			return null;
		} catch (RuntimeException re) {
			logger.error("get failed", re);
			throw re;
		}
	}
	
	@Override
	public List<TaTNoteArticle> selectAll() {
		// TODO Auto-generated method stub
		logger.debug("selectAll TaLiens");
		try {
			Query ejbQuery = getEntityManager().createQuery(JPQLQuery);
			List<TaTNoteArticle> l = ejbQuery.getResultList();
			logger.debug("selectAll successful");
			return l;
		} catch (RuntimeException re) {
			logger.error("selectAll failed", re);
			throw re;
		}
	}
	public void ctrlSaisieSpecifique(TaTNoteArticle entity,String field) throws ExceptLgr {	
		
	}
}
