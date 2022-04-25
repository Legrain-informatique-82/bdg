package fr.legrain.articles.dao;

//Generated Aug 11, 2008 4:05:28 PM by Hibernate Tools 3.2.1.GA

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import fr.legrain.gestCom.Appli.AbstractApplicationDAO;
import fr.legrain.gestCom.Appli.Const;
import fr.legrain.lib.data.ExceptLgr;

/**
 * Home object for domain model class TaCategorieArticle.
 * @see fr.legrain.articles.dao.TaCategorieArticle
 * @author Hibernate Tools
 */
public class TaCategorieArticleDAO extends AbstractApplicationDAO<TaCategorieArticle> {

	private static final Log logger = LogFactory.getLog(TaCategorieArticleDAO.class);

	private String defaultJPQLQuery = "select p from TaCategorieArticle p";
	
	public TaCategorieArticleDAO(){
		this(null);
	}

	public TaCategorieArticleDAO(EntityManager em){
		if(em!=null) {
			setEm(em);
		}
		initChampId(TaCategorieArticle.class);
		setJPQLQuery(defaultJPQLQuery);
		setListeChampMaj(Const.C_FICHIER_INI_CHAMPMAJ);
		initNomTable(new TaCategorieArticle());
	}


	protected void persist(TaCategorieArticle transientInstance) {
		logger.debug("persisting TaCategorieArticle instance");
		try {
			getEntityManager().persist(transientInstance);
			logger.debug("persist successful");
		} catch (RuntimeException re) {
			logger.error("persist failed", re);
			throw re;
		}
	}
	public TaCategorieArticle refresh(TaCategorieArticle detachedInstance) {
		logger.debug("refresh instance");
		try {
			org.hibernate.Session session = (org.hibernate.Session) getEntityManager().getDelegate();
		    session.evict(detachedInstance);
		    detachedInstance = getEntityManager().find(TaCategorieArticle.class, detachedInstance.getIdCategorieArticle());
		    return detachedInstance;
			
		} catch (RuntimeException re) {
			logger.error("refresh failed", re);
			throw re;
		}
	}

	protected void remove(TaCategorieArticle persistentInstance) {
		logger.debug("removing TaCategorieArticle instance");
		try {
			getEntityManager().remove(persistentInstance);
			logger.debug("remove successful");
		} catch (RuntimeException re) {
			logger.error("remove failed", re);
			throw re;
		}
	}

	protected TaCategorieArticle merge(TaCategorieArticle detachedInstance) {
		logger.debug("merging TaCategorieArticle instance");
		try {
			TaCategorieArticle result = getEntityManager().merge(detachedInstance);
			logger.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			logger.error("merge failed", re);
			throw re;
		}
	}

	public TaCategorieArticle findById(int id) {
		logger.debug("getting TaCategorieArticle instance with id: " + id);
		try {
			TaCategorieArticle instance = getEntityManager().find(TaCategorieArticle.class, id);
			logger.debug("get successful");
			return instance;
		} catch (RuntimeException re) {
			logger.error("get failed", re);
			throw re;
		}
	}
	
	public TaCategorieArticle findByCode(String code) {
		logger.debug("getting TaCategorieArticle instance with code: " + code);
		try {
			if(!code.equals("")){
			Query query = getEntityManager().createQuery("select a from TaCategorieArticle a where a.codeCategorieArticle='"+code+"'");
			TaCategorieArticle instance = (TaCategorieArticle)query.getSingleResult();
			logger.debug("get successful");
			return instance;
			}
			return null;
		} catch (RuntimeException re) {
			logger.error("get failed", re);
			throw re;
		}
	}	
	
	/* (non-Javadoc)
	 * @see fr.legrain.articles.dao.ILgrDAO#selectAll()
	 */
	public List<TaCategorieArticle> selectAll() {
		logger.debug("selectAll TaArticle");
		try {
			Query ejbQuery = getEntityManager().createQuery(JPQLQuery);
			List<TaCategorieArticle> l = ejbQuery.getResultList();
			logger.debug("selectAll successful");
			return l;
		} catch (RuntimeException re) {
			logger.error("selectAll failed", re);
			throw re;
		}
	}
	
	public List<TaCategorieArticle> findByCodeCategorieParent(String codeCategorieParent) {
		logger.debug("getting TaCategorieArticle instance with codeCategorieParent: " + codeCategorieParent);
		List<TaCategorieArticle> result = null;
		try {
			Query query = getEntityManager().createNamedQuery(TaCategorieArticle.QN.FIND_BY_PARENT);
			query.setParameter(1, codeCategorieParent);
			result = query.getResultList();
			return result;
		} catch (RuntimeException re) {
			logger.error("get failed", re);
			throw re;
		}
	}
	
	public List<TaCategorieArticle> findCategorieMere() {
		logger.debug("getting TaCategorieArticle instance");
		List<TaCategorieArticle> result = null;
		try {
			Query query = getEntityManager().createNamedQuery(TaCategorieArticle.QN.FIND_CATEGORIE_MERE);
			result = query.getResultList();
			return result;
		} catch (RuntimeException re) {
			logger.error("get failed", re);
			throw re;
		}
	}
	
	/**
	 * Liste des categories d'article créé ou modifié par partir de la date passée en paramètre.
	 * @param d
	 * @return
	 */
	public List<TaCategorieArticle> findByNewOrUpdatedAfter(Date d) {
		logger.debug("getting TaArticle instance with quandCreeCategorieArticle or quandModifCategorieArticle >= " + d);
		try {
			List<TaCategorieArticle> l = null;
			if(d!=null) {
				Query query = getEntityManager().createNamedQuery(TaCategorieArticle.QN.FIND_BY_NEW_OR_UPDATED_AFTER);
				query.setParameter(1, d);
				query.setParameter(2, d);
				l = query.getResultList();
				logger.debug("get successful");
			}
			return l;

		} catch (RuntimeException re) {
			logger.error("get failed", re);
			throw re;
		}
	}

	public void ctrlSaisieSpecifique(TaCategorieArticle entity,String field) throws ExceptLgr {	
		try {
			
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
}

