package fr.legrain.tiers.dao;

// Generated Mar 25, 2009 10:06:50 AM by Hibernate Tools 3.2.0.CR1

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import fr.legrain.articles.dao.TaUnite;
import fr.legrain.gestCom.Appli.AbstractApplicationDAO;
import fr.legrain.gestCom.Appli.Const;
import fr.legrain.lib.data.ExceptLgr;

/**
 * Home object for domain model class TaCPaiement.
 * @see fr.legrain.tiers.dao.test.TaCPaiement
 * @author Hibernate Tools
 */
public class TaCPaiementDAO extends AbstractApplicationDAO<TaCPaiement>{

	//private static final Log logger = LogFactory.getLog(TaCPaiementDAO.class);
	static Logger logger = Logger.getLogger(TaCPaiementDAO.class);
	
	private String defaultJPQLQuery = "select a from TaCPaiement a";
	
	public TaCPaiementDAO() {
		this(null);
	}
	
	public TaCPaiementDAO(EntityManager em){
		if(em!=null) {
			setEm(em);
		}
//		champIdTable=ctrlGeneraux.getID_TABLE(TaCPaiement.class.getSimpleName());
		initChampId(TaCPaiement.class);
		setJPQLQuery(defaultJPQLQuery);
		setListeChampMaj(Const.C_FICHIER_INI_CHAMPMAJ);
		initNomTable(new TaCPaiement());
	}
	
	public void persist(TaCPaiement transientInstance) {
		logger.debug("persisting TaCPaiement instance");
		try {
			getEntityManager().persist(transientInstance);
			logger.debug("persist successful");
		} catch (RuntimeException re) {
			logger.error("persist failed", re);
			throw re;
		}
	}
	
	public TaCPaiement refresh(TaCPaiement detachedInstance) {
		logger.debug("refresh instance");
		try {
			org.hibernate.Session session = (org.hibernate.Session) getEntityManager().getDelegate();
		    session.evict(detachedInstance);
		    detachedInstance = getEntityManager().find(TaCPaiement.class, detachedInstance.getIdCPaiement());
		    return detachedInstance;
		} catch (RuntimeException re) {
			logger.error("refresh failed", re);
			throw re;
		}
	}

	public void remove(TaCPaiement persistentInstance) {
		logger.debug("removing TaCPaiement instance");
		try {
			getEntityManager().remove(persistentInstance);
			logger.debug("remove successful");
		} catch (RuntimeException re) {
			logger.error("remove failed", re);
			throw re;
		}
	}

	public TaCPaiement merge(TaCPaiement detachedInstance) {
		logger.debug("merging TaCPaiement instance");
		try {
			TaCPaiement result = getEntityManager().merge(detachedInstance);
			logger.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			logger.error("merge failed", re);
			throw re;
		}
	}

	public TaCPaiement findById(int id) {
		logger.debug("getting TaCPaiement instance with id: " + id);
		try {
			TaCPaiement instance = getEntityManager().find(TaCPaiement.class, id);
			logger.debug("get successful");
			return instance;
		} catch (RuntimeException re) {
			logger.error("get failed", re);
			throw re;
		}
	}
	
	public TaCPaiement findByCode(String code) {
		logger.debug("getting TaCPaiement instance with code: " + code);
		try {
			if(!code.equals("")){
			Query query = getEntityManager().createQuery("select f from TaCPaiement f where f.codeCPaiement='"+code+"'");
			TaCPaiement instance = (TaCPaiement)query.getSingleResult();
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
			Query query = getEntityManager().createQuery("select count(f) from TaCPaiement f where f.codeCPaiement='"+code+"'");
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
	public List<TaCPaiement> selectAll() {
		// TODO Auto-generated method stub
		logger.debug("selectAll TaCPaiement");
		try {
			Query ejbQuery = getEntityManager().createQuery(JPQLQuery);
			List<TaCPaiement> l = ejbQuery.getResultList();
			logger.debug("selectAll successful");
			return l;
		} catch (RuntimeException re) {
			logger.error("selectAll failed", re);
			throw re;
		}
	}
	
	/**
	 * Recherche les conditions de paiement d'un certain type
	 * @param codeType - le code du type
	 * @return
	 */
	public List<TaCPaiement> rechercheParType(String codeType) {
		Query query = getEntityManager().createNamedQuery(TaCPaiement.QN.FIND_BY_TYPE);
		query.setParameter(1, codeType);
		List<TaCPaiement> l = query.getResultList();

		return l;
	}
	
	public void ctrlSaisieSpecifique(TaCPaiement entity,String field) throws ExceptLgr {	
		
	}
}
