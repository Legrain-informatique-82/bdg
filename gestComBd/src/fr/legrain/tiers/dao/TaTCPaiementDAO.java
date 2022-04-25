package fr.legrain.tiers.dao;

// Generated Mar 24, 2009 4:44:04 PM by Hibernate Tools 3.2.0.CR1

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import fr.legrain.articles.dao.TaUnite;
import fr.legrain.gestCom.Appli.AbstractApplicationDAO;
import fr.legrain.gestCom.Appli.Const;
import fr.legrain.lib.data.ExceptLgr;

/**
 * Home object for domain model class TaTCPaiement.
 * @see fr.legrain.tiers.dao.TaTCPaiement
 * @author Hibernate Tools
 */
public class TaTCPaiementDAO extends AbstractApplicationDAO<TaTCPaiement>{

	//private static final Log logger = LogFactory.getLog(TaTCPaiementDAO.class);
	static Logger logger = Logger.getLogger(TaTCPaiementDAO.class);

	private String defaultJPQLQuery = "select a from TaTCPaiement a";
	
	public TaTCPaiementDAO() {
		this(null);
	}
	
	public TaTCPaiementDAO(EntityManager em){
		if(em!=null) {
			setEm(em);
		}
//		champIdTable=ctrlGeneraux.getID_TABLE(TaTCPaiement.class.getSimpleName());
		initChampId(TaTCPaiement.class);
		setJPQLQuery(defaultJPQLQuery);
		setListeChampMaj(Const.C_FICHIER_INI_CHAMPMAJ);
		initNomTable(new TaTCPaiement());
	}
	public TaTCPaiement refresh(TaTCPaiement detachedInstance) {
		logger.debug("refresh instance");
		try {
			org.hibernate.Session session = (org.hibernate.Session) getEntityManager().getDelegate();
		    session.evict(detachedInstance);
		    detachedInstance = getEntityManager().find(TaTCPaiement.class, detachedInstance.getIdTCPaiement());
		    return detachedInstance;
		} catch (RuntimeException re) {
			logger.error("refresh failed", re);
			throw re;
		}
	}
	public void persist(TaTCPaiement transientInstance) {
		logger.debug("persisting TaTCPaiement instance");
		try {
			getEntityManager().persist(transientInstance);
			logger.debug("persist successful");
		} catch (RuntimeException re) {
			logger.error("persist failed", re);
			throw re;
		}
	}

	public void remove(TaTCPaiement persistentInstance) {
		logger.debug("removing TaTCPaiement instance");
		try {
			getEntityManager().remove(persistentInstance);
			logger.debug("remove successful");
		} catch (RuntimeException re) {
			logger.error("remove failed", re);
			throw re;
		}
	}

	public TaTCPaiement merge(TaTCPaiement detachedInstance) {
		logger.debug("merging TaTCPaiement instance");
		try {
			TaTCPaiement result = getEntityManager().merge(detachedInstance);
			logger.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			logger.error("merge failed", re);
			throw re;
		}
	}

	public TaTCPaiement findById(int id) {
		logger.debug("getting TaTCPaiement instance with id: " + id);
		try {
			TaTCPaiement instance = getEntityManager().find(TaTCPaiement.class, id);
			logger.debug("get successful");
			return instance;
		} catch (RuntimeException re) {
			logger.error("get failed", re);
			throw re;
		}
	}
	
	public TaTCPaiement findByCode(String code) {
		logger.debug("getting TaTCPaiement instance with code: " + code);
		try {

			if(!code.equals("")){
			Query query = getEntityManager().createQuery("select f from TaTCPaiement f where f.codeTCPaiement='"+code+"'");
			TaTCPaiement instance = (TaTCPaiement)query.getSingleResult();
			logger.debug("get successful");
			return instance;
			}
			return null;
		} catch (RuntimeException re) {
			logger.error("get failed", re);
			throw re;
		}
	}
	
	public Boolean exist(String code) {
		logger.debug("exist with code: " + code);
		try {
			if(code!=null && !code.equals("")){
			Query query = getEntityManager().createQuery("select count(f) from TaTCPaiement f where f.codeTCPaiement='"+code+"'");
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
	public List<TaTCPaiement> selectAll() {
		// TODO Auto-generated method stub
		logger.debug("selectAll TaTCPaiement");
		try {
			Query ejbQuery = getEntityManager().createQuery(JPQLQuery);
			List<TaTCPaiement> l = ejbQuery.getResultList();
			logger.debug("selectAll successful");
			return l;
		} catch (RuntimeException re) {
			logger.error("selectAll failed", re);
			throw re;
		}
	}
	

	public void ctrlSaisieSpecifique(TaTCPaiement entity,String field) throws ExceptLgr {	
		
	}
}
