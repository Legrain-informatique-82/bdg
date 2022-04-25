package fr.legrain.pointLgr.dao;

// Generated Mar 24, 2009 4:44:04 PM by Hibernate Tools 3.2.0.CR1

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import fr.legrain.articles.dao.TaUnite;
import fr.legrain.documents.dao.TaInfosApporteur;
import fr.legrain.gestCom.Appli.AbstractApplicationDAO;
import fr.legrain.gestCom.Appli.Const;
import fr.legrain.lib.data.ExceptLgr;


/**
 * Home object for domain model class TaAdresse.
 * @see fr.legrain.tiers.dao.TaAdresse
 * @author Hibernate Tools
 */
public class TaArticlePointDAO extends AbstractApplicationDAO<TaArticlePoint>{

	//private static final Log logger = LogFactory.getLog(TaAdresseDAO.class);
	static Logger logger = Logger.getLogger(TaArticlePointDAO.class);
	
	private String defaultJPQLQuery = "select a from TaArticlePoint a";
	
	public TaArticlePointDAO(){
		this(null);
	}
	
	public TaArticlePointDAO(EntityManager em){
		if(em!=null) {
			setEm(em);
		}
//		champIdTable=ctrlGeneraux.getID_TABLE(TaTiersPoint.class.getSimpleName());
		initChampId(TaComptePoint.class);
		setJPQLQuery(defaultJPQLQuery);
		setListeChampMaj(Const.C_FICHIER_INI_CHAMPMAJ);
		initNomTable(new TaArticlePoint());
	}

	public TaArticlePoint refresh(TaArticlePoint detachedInstance) {
		logger.debug("refresh instance");
		try {
			org.hibernate.Session session = (org.hibernate.Session) getEntityManager().getDelegate();
		    session.evict(detachedInstance);
		    detachedInstance = getEntityManager().find(TaArticlePoint.class, detachedInstance.getIdArticlePoint());
		    return detachedInstance;
		} catch (RuntimeException re) {
			logger.error("refresh failed", re);
			throw re;
		}
	}
	public void persist(TaArticlePoint transientInstance) {
		logger.debug("persisting TaArticlePoint instance");
		try {
			getEntityManager().persist(transientInstance);
			logger.debug("persist successful");
		} catch (RuntimeException re) {
			logger.error("persist failed", re);
			throw re;
		}
	}

	public void remove(TaArticlePoint persistentInstance) {
		logger.debug("removing TaArticlePoint instance");
		try {
			getEntityManager().remove(persistentInstance);
			logger.debug("remove successful");
		} catch (RuntimeException re) {
			logger.error("remove failed", re);
			throw re;
		}
	}

	public TaArticlePoint merge(TaArticlePoint detachedInstance) {
		logger.debug("merging TaArticlePoint instance");
		try {
			TaArticlePoint result = getEntityManager().merge(detachedInstance);
			logger.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			logger.error("merge failed", re);
			throw re;
		}
	}

	public TaArticlePoint findById(int id) {
		logger.debug("getting TaArticlePoint instance with id: " + id);
		try {
			TaArticlePoint instance = getEntityManager().find(TaArticlePoint.class, id);
			logger.debug("get successful");
			return instance;
		} catch (RuntimeException re) {
			logger.error("get failed", re);
			throw re;
		}
	}

	@Override
	public List<TaArticlePoint> selectAll() {
		// TODO Auto-generated method stub
		logger.debug("selectAll TaArticlePoint");
		try {
			Query ejbQuery = getEntityManager().createQuery(JPQLQuery);
			List<TaArticlePoint> l = ejbQuery.getResultList();
			logger.debug("selectAll successful");
			return l;
		} catch (RuntimeException re) {
			logger.error("selectAll failed", re);
			throw re;
		}
	}
	

	public TaArticlePoint findByArticle(Integer idArticle) {
		logger.debug("getting TaArticlePoint  with id: " + idArticle);
		try {
			TaArticlePoint instance=null;
			Query query = getEntityManager().createQuery("select count(a) from TaArticlePoint a join a.taArticle t where t.idArticle = ?");
			query.setParameter(1, idArticle);
			Long count= (Long) query.getSingleResult();
			if(count>0){
				query = getEntityManager().createQuery("select a from TaArticlePoint a join a.taArticle t where t.idArticle = ?");
				query.setParameter(1, idArticle);
				instance= (TaArticlePoint) query.getSingleResult();
			}
			return instance;
		} catch (RuntimeException re) {
			throw re;
		} catch (Exception e) {
			return null;
		}
	}
	
	public void ctrlSaisieSpecifique(TaArticlePoint entity,String field) throws ExceptLgr {	
		
	}

}
