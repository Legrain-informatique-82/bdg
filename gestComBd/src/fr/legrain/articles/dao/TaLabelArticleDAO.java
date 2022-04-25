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
 * Home object for domain model class TaLabelArticle.
 * @see fr.legrain.articles.dao.TaLabelArticle
 * @author Hibernate Tools
 */
public class TaLabelArticleDAO extends AbstractApplicationDAO<TaLabelArticle> {

	private static final Log logger = LogFactory.getLog(TaLabelArticleDAO.class);

	private String defaultJPQLQuery = "select p from TaLabelArticle p";
	
	public TaLabelArticleDAO(){
		this(null);
	}

	public TaLabelArticleDAO(EntityManager em){
		if(em!=null) {
			setEm(em);
		}
		initChampId(TaLabelArticle.class);
		setJPQLQuery(defaultJPQLQuery);
		setListeChampMaj(Const.C_FICHIER_INI_CHAMPMAJ);
		initNomTable(new TaLabelArticle());
	}


	protected void persist(TaLabelArticle transientInstance) {
		logger.debug("persisting TaLabelArticle instance");
		try {
			getEntityManager().persist(transientInstance);
			logger.debug("persist successful");
		} catch (RuntimeException re) {
			logger.error("persist failed", re);
			throw re;
		}
	}
	public TaLabelArticle refresh(TaLabelArticle detachedInstance) {
		logger.debug("refresh instance");
		try {
			org.hibernate.Session session = (org.hibernate.Session) getEntityManager().getDelegate();
		    session.evict(detachedInstance);
		    detachedInstance = getEntityManager().find(TaLabelArticle.class, detachedInstance.getIdLabelArticle());
		    return detachedInstance;
			
		} catch (RuntimeException re) {
			logger.error("refresh failed", re);
			throw re;
		}
	}

	protected void remove(TaLabelArticle persistentInstance) {
		logger.debug("removing TaLabelArticle instance");
		try {
			getEntityManager().remove(persistentInstance);
			logger.debug("remove successful");
		} catch (RuntimeException re) {
			logger.error("remove failed", re);
			throw re;
		}
	}

	protected TaLabelArticle merge(TaLabelArticle detachedInstance) {
		logger.debug("merging TaLabelArticle instance");
		try {
			TaLabelArticle result = getEntityManager().merge(detachedInstance);
			logger.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			logger.error("merge failed", re);
			throw re;
		}
	}

	public TaLabelArticle findById(int id) {
		logger.debug("getting TaLabelArticle instance with id: " + id);
		try {
			TaLabelArticle instance = getEntityManager().find(TaLabelArticle.class, id);
			logger.debug("get successful");
			return instance;
		} catch (RuntimeException re) {
			logger.error("get failed", re);
			throw re;
		}
	}
	
	public TaLabelArticle findByCode(String code) {
		logger.debug("getting TaLabelArticle instance with code: " + code);
		try {
			if(!code.equals("")){
			Query query = getEntityManager().createQuery("select a from TaLabelArticle a where a.codeLabelArticle='"+code+"'");
			TaLabelArticle instance = (TaLabelArticle)query.getSingleResult();
			logger.debug("get successful");
			return instance;
			}
			return null;
		} catch (RuntimeException re) {
			logger.error("get failed", re);
			throw re;
		}
	}	
	
	/**
	 * Liste des labels créé ou modifié par partir de la date passée en paramètre.
	 * @param d
	 * @return
	 */
	public List<TaLabelArticle> findByNewOrUpdatedAfter(Date d) {
		logger.debug("getting TaArticle instance with quandCreeLabelArticle or quandModifLabelArticle >= " + d);
		try {
			List<TaLabelArticle> l = null;
			if(d!=null) {
				Query query = getEntityManager().createNamedQuery(TaLabelArticle.QN.FIND_BY_NEW_OR_UPDATED_AFTER);
				query.setParameter(1, d);
				query.setParameter(2, d);
				l = query.getResultList();;
				logger.debug("get successful");
			}
			return l;

		} catch (RuntimeException re) {
			logger.error("get failed", re);
			throw re;
		}
	}
	
	/* (non-Javadoc)
	 * @see fr.legrain.articles.dao.ILgrDAO#selectAll()
	 */
	public List<TaLabelArticle> selectAll() {
		logger.debug("selectAll TaArticle");
		try {
			Query ejbQuery = getEntityManager().createQuery(JPQLQuery);
			List<TaLabelArticle> l = ejbQuery.getResultList();
			logger.debug("selectAll successful");
			return l;
		} catch (RuntimeException re) {
			logger.error("selectAll failed", re);
			throw re;
		}
	}

	public void ctrlSaisieSpecifique(TaLabelArticle entity,String field) throws ExceptLgr {	
		try {
			
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
}

