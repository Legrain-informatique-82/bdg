package fr.legrain.articles.dao;

// Generated Mar 25, 2009 10:06:50 AM by Hibernate Tools 3.2.0.CR1

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import fr.legrain.gestCom.Appli.AbstractApplicationDAO;
import fr.legrain.gestCom.Appli.Const;
import fr.legrain.pointLgr.dao.TaComptePoint;
import fr.legrain.tiers.dao.TaTiers;

/**
 * Home object for domain model class TaCPaiement.
 * @see fr.legrain.tiers.dao.test.TaCPaiement
 * @author Hibernate Tools
 */
public class TaRTSupportDAO extends AbstractApplicationDAO<TaRTSupport>{

	static Logger logger = Logger.getLogger(TaRTSupportDAO.class);
	
	private String defaultJPQLQuery = "select a from TaRTSupport a";
	
	public TaRTSupportDAO() {
		this(null);
	}
	
	public TaRTSupportDAO(EntityManager em){
		if(em!=null) {
			setEm(em);
		}
		initChampId(TaRTSupport.class);
		setJPQLQuery(defaultJPQLQuery);
		setListeChampMaj(Const.C_FICHIER_INI_CHAMPMAJ);
		initNomTable(new TaRTSupport());
	}
	
	public void persist(TaRTSupport transientInstance) {
		logger.debug("persisting TaRTSupport instance");
		try {
			getEntityManager().persist(transientInstance);
			logger.debug("persist successful");
		} catch (RuntimeException re) {
			logger.error("persist failed", re);
			throw re;
		}
	}
	
	public TaRTSupport refresh(TaRTSupport detachedInstance) {
		logger.debug("refresh instance");
		try {
			org.hibernate.Session session = (org.hibernate.Session) getEntityManager().getDelegate();
		    session.evict(detachedInstance);
		    detachedInstance = getEntityManager().find(TaRTSupport.class, detachedInstance.getIdRTSupport());
		    return detachedInstance;
		} catch (RuntimeException re) {
			logger.error("refresh failed", re);
			throw re;
		}
	}

	public void remove(TaRTSupport persistentInstance) {
		logger.debug("removing TaRTSupport instance");
		try {
			getEntityManager().remove(persistentInstance);
			logger.debug("remove successful");
		} catch (RuntimeException re) {
			logger.error("remove failed", re);
			throw re;
		}
	}

	public TaRTSupport merge(TaRTSupport detachedInstance) {
		logger.debug("merging TaRTSupport instance");
		try {
			TaRTSupport result = getEntityManager().merge(detachedInstance);
			logger.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			logger.error("merge failed", re);
			throw re;
		}
	}

	public TaRTSupport findById(int id) {
		logger.debug("getting TaRTSupport instance with id: " + id);
		try {
			TaRTSupport instance = getEntityManager().find(TaRTSupport.class, id);
			logger.debug("get successful");
			return instance;
		} catch (RuntimeException re) {
			logger.error("get failed", re);
			throw re;
		}
	}
	
	
	@Override
	public List<TaRTSupport> selectAll() {
		// TODO Auto-generated method stub
		logger.debug("selectAll TaRTSupport");
		try {
			Query ejbQuery = getEntityManager().createQuery(JPQLQuery);
			List<TaRTSupport> l = ejbQuery.getResultList();
			logger.debug("selectAll successful");
			return l;
		} catch (RuntimeException re) {
			logger.error("selectAll failed", re);
			throw re;
		}
	}
	
	public List<TaRTSupport> selectAllByTiersRTSupport(Integer idArticle) {
		logger.debug("getting selectAllByTiersRTSupport  with id: " + idArticle);
		try {
			Query query = getEntityManager().createQuery("select rT from TaRTSupport rT join rT.taArticle t where t.idArticle = ?");
			query.setParameter(1, idArticle);
			List<TaRTSupport> l = query.getResultList();
			return l;
		} catch (RuntimeException re) {
			throw re;
		} catch (Exception e) {
			return null;
		}
	}
	
	public Boolean existByidArticleIdTSupport(Integer idArticle, Integer idTSupport) {
		logger.debug("getting TaRTSupport instance ");
		try {
			if(idArticle!=null && idTSupport!=null){
			Query query = getEntityManager().createQuery("select count(rT) from TaRTSupport rT join rT.taArticle a join rt.taTSupport ts  " +
					" where a.idArticle=? and ts.idTSupport=?");
			
			query.setParameter(1, idArticle);
			query.setParameter(2, idTSupport);
			
			Long instance = (Long)query.getSingleResult();
			logger.debug("get successful");
			return instance!=0;
			}
			return false;
			
		} catch (RuntimeException re) {
			logger.error("get failed", re);
			throw re;
		}
	}
	
	public Boolean exist(String code) {
		logger.debug("exist with code: " + code);
		try {
			if(code!=null && !code.equals("")){
			Query query = getEntityManager().createQuery("select count(f) from TaTva f where f.codeTva='"+code+"'");
			Long instance = (Long)query.getSingleResult();
			logger.debug("get successful");
			return instance!=0;
			}
			return false;
		} catch (RuntimeException re) {
			logger.error("get failed", re);
			throw re;
		}
	}
	
	public TaRTSupport findByidArticleIdTSupport(Integer idArticle, Integer idTSupport) {
		logger.debug("getting TaRTSupport instance ");
		try {
			if(idArticle!=null && idTSupport!=null){
			Query query = getEntityManager().createQuery("select rT from TaRTSupport rT join rT.taArticle a join rt.taTSupport ts  " +
					" where a.idArticle=? and ts.idTSupport=?");
			
			query.setParameter(1, idArticle);
			query.setParameter(2, idTSupport);
			
			TaRTSupport instance = (TaRTSupport)query.getSingleResult();
			logger.debug("get successful");
			return instance;
			}
			return null;
			
		} catch (RuntimeException re) {
			logger.error("get failed", re);
			throw re;
		}
	}
	
	
	public List<TaRTSupport> findByidArticleAbonnement(Integer idArticle) {
		logger.debug("getting TaRTSupport instance ");
		try {
			if(idArticle!=null ){
			Query query = getEntityManager().createQuery("select rT from TaRTSupport rT join rT.taArticleAbonnement a  " +
					" where a.idArticle=?");
			
			query.setParameter(1, idArticle);
			
			List<TaRTSupport> liste = query.getResultList();
			logger.debug("get successful");
			return liste;
			}
			return null;
			
		} catch (RuntimeException re) {
		logger.error(re.getMessage());
		}
		return null;
	}
}
