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
public class TaTAbonnementDAO extends AbstractApplicationDAO<TaTAbonnement>{

	//private static final Log logger = LogFactory.getLog(TaTNoteArticle.class);
	static Logger logger = Logger.getLogger(TaTAbonnementDAO.class);

	private String defaultJPQLQuery = "select a from TaTAbonnement a";
	
	public TaTAbonnementDAO() {
		this(null);
	}
	
	public TaTAbonnementDAO(EntityManager em){
		if(em!=null) {
			setEm(em);
		}
//		champIdTable=ctrlGeneraux.getID_TABLE(TaTNoteArticle.class.getSimpleName());
		initChampId(TaTAbonnement.class);
		setJPQLQuery(defaultJPQLQuery);
		setListeChampMaj(Const.C_FICHIER_INI_CHAMPMAJ);
		initNomTable(new TaTAbonnement());
	}
	public TaTAbonnement refresh(TaTAbonnement detachedInstance) {
		logger.debug("refresh instance");
		try {
			org.hibernate.Session session = (org.hibernate.Session) getEntityManager().getDelegate();
		    session.evict(detachedInstance);
		    detachedInstance = getEntityManager().find(TaTAbonnement.class, detachedInstance.getIdTAbonnement());
		    return detachedInstance;
		} catch (RuntimeException re) {
			logger.error("refresh failed", re);
			throw re;
		}
	}
	public void persist(TaTAbonnement transientInstance) {
		logger.debug("persisting TaTAbonnement instance");
		try {
			getEntityManager().persist(transientInstance);
			logger.debug("persist successful");
		} catch (RuntimeException re) {
			logger.error("persist failed", re);
			throw re;
		}
	}

	public void remove(TaTAbonnement persistentInstance) {
		logger.debug("removing TaTAbonnement instance");
		try {
			getEntityManager().remove(persistentInstance);
			logger.debug("remove successful");
		} catch (RuntimeException re) {
			logger.error("remove failed", re);
			throw re;
		}
	}

	public TaTAbonnement merge(TaTAbonnement detachedInstance) {
		logger.debug("merging TaTAbonnement instance");
		try {
			TaTAbonnement result = getEntityManager().merge(detachedInstance);
			logger.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			logger.error("merge failed", re);
			throw re;
		}
	}

	public TaTAbonnement findById(int id) {
		logger.debug("getting TaTAbonnement instance with id: " + id);
		try {
			TaTAbonnement instance = getEntityManager().find(TaTAbonnement.class, id);
			logger.debug("get successful");
			return instance;
		} catch (RuntimeException re) {
			logger.error("get failed", re);
			throw re;
		}
	}
	
	public TaTAbonnement findByCode(String code) {
		logger.debug("getting TaTAbonnement instance with code: " + code);
		try {
			if(!code.equals("")){
			Query query = getEntityManager().createQuery("select f from TaTAbonnement f where f.codeTAbonnement='"+code+"'");
			TaTAbonnement instance = (TaTAbonnement)query.getSingleResult();
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
	public List<TaTAbonnement> selectAll() {
		// TODO Auto-generated method stub
		logger.debug("selectAll TaTAbonnement");
		try {
			Query ejbQuery = getEntityManager().createQuery(JPQLQuery);
			List<TaTAbonnement> l = ejbQuery.getResultList();
			logger.debug("selectAll successful");
			return l;
		} catch (RuntimeException re) {
			logger.error("selectAll failed", re);
			throw re;
		}
	}

}
