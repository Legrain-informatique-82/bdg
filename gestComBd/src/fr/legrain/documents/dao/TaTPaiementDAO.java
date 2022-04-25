package fr.legrain.documents.dao;

// Generated Apr 7, 2009 3:27:24 PM by Hibernate Tools 3.2.0.CR1

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import fr.legrain.gestCom.Appli.AbstractApplicationDAO;
import fr.legrain.gestCom.Appli.Const;

/**
 * Home object for domain model class TaTPaiement.
 * @see fr.legrain.documents.dao.TaTPaiement
 * @author Hibernate Tools
 */
public class TaTPaiementDAO extends AbstractApplicationDAO<TaTPaiement> {

//	private static final Log log = LogFactory.getLog(TaTPaiementDAO.class);
	static Logger logger = Logger.getLogger(TaTPaiementDAO.class);
	
	private String defaultJPQLQuery = "select a from TaTPaiement a";
	
	public TaTPaiementDAO(){
		this(null);
	}
	
	public TaTPaiementDAO(EntityManager em){
		if(em!=null) {
			setEm(em);
		}
//		champIdTable=ctrlGeneraux.getID_TABLE(TaTPaiement.class.getSimpleName());
		initChampId(TaTPaiement.class);
		setJPQLQuery(defaultJPQLQuery);
		setListeChampMaj(Const.C_FICHIER_INI_CHAMPMAJ);
		initNomTable(new TaTPaiement());
	}
	public TaTPaiement refresh(TaTPaiement detachedInstance) {
		logger.debug("refresh instance");
		try {
			getEntityManager().refresh(detachedInstance);
//			org.hibernate.Session session = (org.hibernate.Session) getEntityManager().getDelegate();
//		    session.evict(detachedInstance);
//		    detachedInstance = getEntityManager().find(TaTPaiement.class, detachedInstance.getIdTPaiement());
		    return detachedInstance;
			
		} catch (RuntimeException re) {
			logger.error("refresh failed", re);
			throw re;
		}
	}
	public void persist(TaTPaiement transientInstance) {
		logger.debug("persisting TaTPaiement instance");
		try {
			getEntityManager().persist(transientInstance);
			logger.debug("persist successful");
		} catch (RuntimeException re) {
			logger.error("persist failed", re);
			throw re;
		}
	}

	public void remove(TaTPaiement persistentInstance) {
		logger.debug("removing TaTPaiement instance");
		try {
			getEntityManager().remove(persistentInstance);
			logger.debug("remove successful");
		} catch (RuntimeException re) {
			logger.error("remove failed", re);
			throw re;
		}
	}

	public TaTPaiement merge(TaTPaiement detachedInstance) {
		logger.debug("merging TaTPaiement instance");
		try {
			TaTPaiement result = getEntityManager().merge(detachedInstance);
			logger.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			logger.error("merge failed", re);
			throw re;
		}
	}

	public TaTPaiement findById(int id) {
		logger.debug("getting TaTPaiement instance with id: " + id);
		try {
			TaTPaiement instance = getEntityManager().find(TaTPaiement.class, id);
			logger.debug("get successful");
			return instance;
		} catch (RuntimeException re) {
			logger.error("get failed", re);
			throw re;
		}
	}
	
	public TaTPaiement findByCode(String code) {
		logger.debug("getting TaTPaiement instance with code: " + code);
		try {
			if(!code.equals("")){
			Query query = getEntityManager().createQuery("select a from TaTPaiement a where a.codeTPaiement='"+code+"'");
			TaTPaiement instance = (TaTPaiement)query.getSingleResult();
			logger.debug("get successful");
			return instance;
			}
			return null;
		} catch (RuntimeException re) {
			logger.error("get failed", re);
			throw re;
		}
	}	
	
	
	public TaTPaiement findByCodeDefaut(String codeDefaut) {
		logger.debug("getting TaTPaiement instance with code: " + codeDefaut);
		try {
			TaTPaiement instance = null;
			if(!codeDefaut.equals("")){
			Query query = getEntityManager().createQuery("select a from TaTPaiement a where a.codeTPaiement='"+codeDefaut+"'");
			try {
				instance = (TaTPaiement)query.getSingleResult();
			} catch (Exception e) {
				List<TaTPaiement> liste =selectAll();
				if(liste.size()>0)	instance=liste.get(0);
			}
			
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
			Query query = getEntityManager().createQuery("select count(f) from TaTPaiement f where f.codeTPaiement='"+code+"'");
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

	
	
	/* (non-Javadoc)
	 * @see fr.legrain.articles.dao.ILgrDAO#selectAll()
	 */
	public List<TaTPaiement> selectAll() {
		logger.debug("selectAll TaTPaiement");
		try {
			Query ejbQuery = getEntityManager().createQuery(JPQLQuery);
			List<TaTPaiement> l = ejbQuery.getResultList();
			logger.debug("selectAll successful");
			return l;
		} catch (RuntimeException re) {
			logger.error("selectAll failed", re);
			throw re;
		}
	}
}
