package fr.legrain.tiers.dao;

// Generated 11 juin 2009 10:56:24 by Hibernate Tools 3.2.4.GA

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import fr.legrain.articles.dao.TaUnite;
import fr.legrain.gestCom.Appli.AbstractApplicationDAO;
import fr.legrain.gestCom.Appli.Const;

/**
 * Home object for domain model class TaFamilleUnite.
 * @see fr.legrain.articles.dao.TaFamilleUnite
 * @author Hibernate Tools
 */
public class TaFamilleTiersDAO extends AbstractApplicationDAO<TaFamilleTiers> {

	private static final Log logger = LogFactory.getLog(TaFamilleTiersDAO.class);

	private String defaultJPQLQuery = "select p from TaFamilleTiers p";
	
	public TaFamilleTiersDAO(){
		this(null);
	}

	public TaFamilleTiersDAO(EntityManager em){
		if(em!=null) {
			setEm(em);
		}
//		champIdTable=ctrlGeneraux.getID_TABLE(TaFamilleTiers.class.getSimpleName());
		initChampId(TaFamilleTiers.class);
		setJPQLQuery(defaultJPQLQuery);
		setListeChampMaj(Const.C_FICHIER_INI_CHAMPMAJ);
		initNomTable(new TaFamilleTiers());
	}

	public void persist(TaFamilleTiers transientInstance) {
		logger.debug("persisting TaFamilleTiers instance");
		try {
			 getEntityManager().persist(transientInstance);
			logger.debug("persist successful");
		} catch (RuntimeException re) {
			logger.error("persist failed", re);
			throw re;
		}
	}

	public TaFamilleTiers refresh(TaFamilleTiers detachedInstance) {
		logger.debug("refresh instance");
		try {
			org.hibernate.Session session = (org.hibernate.Session) getEntityManager().getDelegate();
		    session.evict(detachedInstance);
		    detachedInstance = getEntityManager().find(TaFamilleTiers.class, detachedInstance.getIdFamille());
		    return detachedInstance;
		} catch (RuntimeException re) {
			logger.error("refresh failed", re);
			throw re;
		}
	}
	
	public void remove(TaFamilleTiers persistentInstance) {
		logger.debug("removing TaFamilleTiers instance");
		try {
			 getEntityManager().remove(persistentInstance);
			logger.debug("remove successful");
		} catch (RuntimeException re) {
			logger.error("remove failed", re);
			throw re;
		}
	}

	public TaFamilleTiers merge(TaFamilleTiers detachedInstance) {
		logger.debug("merging TaFamilleTiers instance");
		try {
			TaFamilleTiers result =  getEntityManager().merge(detachedInstance);
			logger.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			logger.error("merge failed", re);
			throw re;
		}
	}

	public TaFamilleTiers findById(int id) {
		logger.debug("getting TaFamilleTiers instance with id: " + id);
		try {
			TaFamilleTiers instance = getEntityManager().find(TaFamilleTiers.class,
					id);
			logger.debug("get successful");
			return instance;
		} catch (RuntimeException re) {
			logger.error("get failed", re);
			throw re;
		}
	}
	
	public TaFamilleTiers findByCode(String code) {
		logger.debug("getting TaFamilleTiers instance with code: " + code);
		try {
			if(!code.equals("")){
			Query query = getEntityManager().createQuery("select a from TaFamilleTiers a where a.codeFamille='"+code+"'");
			TaFamilleTiers instance = (TaFamilleTiers)query.getSingleResult();
			logger.debug("get successful");
			return instance;
			}
			return null;
		} catch (RuntimeException re) {
			logger.error("get failed", re);
			throw re;
		}
	}
	
	public TaFamilleTiers rechercheFamilleCogere() {
		logger.debug(" rechercheFamilleCogere" );
		try {
			Query query = getEntityManager().createQuery("select a from TaFamilleTiers a where a.codeFamille = 'COGERE'");
			TaFamilleTiers instance = (TaFamilleTiers)query.getSingleResult();
			logger.debug("get successful");
			return instance;
		} catch (RuntimeException re) {
			logger.error("get failed", re);
			throw re;
		}
	}

	public Boolean exist(String code) {
		logger.debug("exist with code: " + code);
		try {
			if(code!=null && !code.equals("")){
			Query query = getEntityManager().createQuery("select count(f) from TaFamilleTiers f where f.codeFamille='"+code+"'");
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

	
	

	@Override
	public List<TaFamilleTiers> selectAll() {
		try {
			Query ejbQuery = getEntityManager().createQuery(JPQLQuery);
			List<TaFamilleTiers> l = ejbQuery.getResultList();
			logger.debug("selectAll successful");
			return l;
		} catch (RuntimeException re) {
			logger.error("selectAll failed", re);
			throw re;
		}
	}
}
