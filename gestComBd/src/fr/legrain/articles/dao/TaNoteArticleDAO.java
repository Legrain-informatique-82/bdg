package fr.legrain.articles.dao;

// Generated Mar 24, 2009 4:44:04 PM by Hibernate Tools 3.2.0.CR1

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import fr.legrain.gestCom.Appli.AbstractApplicationDAO;
import fr.legrain.gestCom.Appli.Const;
import fr.legrain.lib.data.ExceptLgr;

/**
 * Home object for domain model class TaNoteArticle.
 * @see fr.legrain.tiers.dao.TaNoteArticle
 * @author Hibernate Tools
 */
public class TaNoteArticleDAO extends AbstractApplicationDAO<TaNoteArticle> {

	private static final Log logger = LogFactory.getLog(TaNoteArticleDAO.class);
	//static Logger logger = Logger.getLogger(TaNoteDAO.class.getName());
	
	private String defaultJPQLQuery = "select u from TaNoteArticle u";
	
	public TaNoteArticleDAO(){
		this(null);
	}

	public TaNoteArticleDAO(EntityManager em){
		if(em!=null) {
			setEm(em);
		}
//		champIdTable=ctrlGeneraux.getID_TABLE(TaNoteArticle.class.getSimpleName());
		initChampId(TaNoteArticle.class);
		setJPQLQuery(defaultJPQLQuery);
		setListeChampMaj(Const.C_FICHIER_INI_CHAMPMAJ);
		initNomTable(new TaNoteArticle());
	}
	public TaNoteArticle refresh(TaNoteArticle detachedInstance) {
		logger.debug("refresh instance");
		try {
			org.hibernate.Session session = (org.hibernate.Session) getEntityManager().getDelegate();
		    session.evict(detachedInstance);
		    detachedInstance = getEntityManager().find(TaNoteArticle.class, detachedInstance.getIdNoteArticle());
		    return detachedInstance;
		} catch (RuntimeException re) {
			logger.error("refresh failed", re);
			throw re;
		}
	}
	public void persist(TaNoteArticle transientInstance) {
		logger.debug("persisting TaNoteArticle instance");
		try {
			getEntityManager().persist(transientInstance);
			logger.debug("persist successful");
		} catch (RuntimeException re) {
			logger.error("persist failed", re);
			throw re;
		}
	}

	public void remove(TaNoteArticle persistentInstance) {
		logger.debug("removing TaNoteArticle instance");
		try {
			getEntityManager().remove(persistentInstance);
			logger.debug("remove successful");
		} catch (RuntimeException re) {
			logger.error("remove failed", re);
			throw re;
		}
	}

	public TaNoteArticle merge(TaNoteArticle detachedInstance) {
		logger.debug("merging TaNoteArticle instance");
		try {
			TaNoteArticle result = getEntityManager().merge(detachedInstance);
			logger.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			logger.error("merge failed", re);
			throw re;
		}
	}

	public TaNoteArticle findById(int id) {
		logger.debug("getting TaNoteArticle instance with id: " + id);
		try {
			TaNoteArticle instance = getEntityManager().find(TaNoteArticle.class, id);
			logger.debug("get successful");
			return instance;
		} catch (RuntimeException re) {
			logger.error("get failed", re);
			throw re;
		}
	}
	
	@Override
	public List<TaNoteArticle> selectAll() {
		// TODO Auto-generated method stub
		logger.debug("selectAll TaNoteArticle");
		try {
			Query ejbQuery = getEntityManager().createQuery(JPQLQuery);
			List<TaNoteArticle> l = ejbQuery.getResultList();
			logger.debug("selectAll successful");
			return l;
		} catch (RuntimeException re) {
			logger.error("selectAll failed", re);
			throw re;
		}
	}
	public void ctrlSaisieSpecifique(TaNoteArticle entity,String field) throws ExceptLgr {	
		
	}
}
