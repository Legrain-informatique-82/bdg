package fr.legrain.articles.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import fr.legrain.gestCom.Appli.AbstractApplicationDAO;
import fr.legrain.gestCom.Appli.Const;

/**
 * Home object for domain model class TaRTaTitreTransport.
 * @see fr.legrain.articles.dao.TaRTaTitreTransport
 * @author Hibernate Tools
 */
public class TaRTaTitreTransportDAO extends AbstractApplicationDAO<TaRTaTitreTransport> {

	private static final Log logger = LogFactory.getLog(TaRTaTitreTransportDAO.class);
	private String defaultJPQLQuery = "select p from TaRTaTitreTransport p";
	
	public TaRTaTitreTransportDAO(){
		this(null);
	}
	
	public TaRTaTitreTransportDAO(EntityManager em){
		if(em!=null) {
			setEm(em);
		}
//		champIdTable=ctrlGeneraux.getID_TABLE(TaRTaTitreTransport.class.getSimpleName());
		initChampId(TaRTaTitreTransport.class);
		setJPQLQuery(defaultJPQLQuery);
		setListeChampMaj(Const.C_FICHIER_INI_CHAMPMAJ);
		initNomTable(new TaRTaTitreTransport());
	}

	public void persist(TaRTaTitreTransport transientInstance) {
		logger.debug("persisting TaRTaTitreTransport instance");
		try {
			getEntityManager().persist(transientInstance);
			logger.debug("persist successful");
		} catch (RuntimeException re) {
			logger.error("persist failed", re);
			throw re;
		}
	}
	public TaRTaTitreTransport refresh(TaRTaTitreTransport detachedInstance) {
		logger.debug("refresh instance");
		try {
			org.hibernate.Session session = (org.hibernate.Session) getEntityManager().getDelegate();
		    session.evict(detachedInstance);
		    detachedInstance = getEntityManager().find(TaRTaTitreTransport.class, detachedInstance.getIdRTitreTransport());
		    return detachedInstance;
			
		} catch (RuntimeException re) {
			logger.error("refresh failed", re);
			throw re;
		}
	}

	public void remove(TaRTaTitreTransport persistentInstance) {
		logger.debug("removing TaRTaTitreTransport instance");
		try {
			getEntityManager().remove(persistentInstance);
			logger.debug("remove successful");
		} catch (RuntimeException re) {
			logger.error("remove failed", re);
			throw re;
		}
	}

	public TaRTaTitreTransport merge(TaRTaTitreTransport detachedInstance) {
		logger.debug("merging TaRTaTitreTransport instance");
		try {
			TaRTaTitreTransport result = getEntityManager().merge(detachedInstance);
			logger.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			logger.error("merge failed", re);
			throw re;
		}
	}

	public TaRTaTitreTransport findById(int id) {
		logger.debug("getting TaRTaTitreTransport instance with id: " + id);
		try {
			TaRTaTitreTransport instance = getEntityManager().find(TaRTaTitreTransport.class,
					id);
			logger.debug("get successful");
			return instance;
		} catch (RuntimeException re) {
			logger.error("get failed", re);
			throw re;
		}
	}
	
	public TaRTaTitreTransport findByIdArticle(int idArticle) {
		logger.debug("getting TaRTaTitreTransport instance with idArticle: " + idArticle);
		try {
			//Query ejbQuery = getEntityManager().createQuery("select p from TaRTaTitreTransport p.taArticle.idArticle = "+ idArticle);
			TaRTaTitreTransport instance = null;
			Query ejbQuery = getEntityManager().createQuery("select p from TaRTaTitreTransport p join p.taArticle a where a.idArticle = "+ idArticle);
			
			List<TaRTaTitreTransport> l = ejbQuery.getResultList();
			if(!l.isEmpty())
				return instance = l.get(0);
			
			//TaRTaTitreTransport instance = (TaRTaTitreTransport)ejbQuery.getSingleResult();
			logger.debug("get successful");
			return instance;
		} catch (RuntimeException re) {
			logger.error("get failed", re);
			throw re;
		}
	}

	@Override
	public List<TaRTaTitreTransport> selectAll() {
		try {
			Query ejbQuery = getEntityManager().createQuery(JPQLQuery);
			List<TaRTaTitreTransport> l = ejbQuery.getResultList();
			logger.debug("selectAll successful");
			return l;
		} catch (RuntimeException re) {
			logger.error("selectAll failed", re);
			throw re;
		}
	}
}
