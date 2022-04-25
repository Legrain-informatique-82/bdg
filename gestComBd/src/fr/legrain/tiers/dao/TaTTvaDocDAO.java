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
 * Home object for domain model class TaTTiers.
 * @see fr.legrain.tiers.dao.TaTTiers
 * @author Hibernate Tools
 */
public class TaTTvaDocDAO extends AbstractApplicationDAO<TaTTvaDoc>{

	//private static final Log logger = LogFactory.getLog(TaLiensDAO.class);
	static Logger logger = Logger.getLogger(TaTTvaDocDAO.class);

	private String defaultJPQLQuery = "select a from TaTTvaDoc a ";
	
	public TaTTvaDocDAO() {
		this(null);
	}
	
	public TaTTvaDocDAO(EntityManager em){
		if(em!=null) {
			setEm(em);
		}
//		champIdTable=ctrlGeneraux.getID_TABLE(TaTTvaDoc.class.getSimpleName());
		initChampId(TaTTvaDoc.class);
		setJPQLQuery(defaultJPQLQuery);
		setListeChampMaj(Const.C_FICHIER_INI_CHAMPMAJ);
		initNomTable(new TaTTvaDoc());
	}
	public TaTTvaDoc refresh(TaTTvaDoc detachedInstance) {
		logger.debug("refresh instance");
		try {
			org.hibernate.Session session = (org.hibernate.Session) getEntityManager().getDelegate();
		    session.evict(detachedInstance);
		    detachedInstance = getEntityManager().find(TaTTvaDoc.class, detachedInstance.getIdTTvaDoc());
		    return detachedInstance;
		} catch (RuntimeException re) {
			logger.error("refresh failed", re);
			throw re;
		}
	}
	public void persist(TaTTvaDoc transientInstance) {
		logger.debug("persisting TaTTvaDoc instance");
		try {
			getEntityManager().persist(transientInstance);
			logger.debug("persist successful");
		} catch (RuntimeException re) {
			logger.error("persist failed", re);
			throw re;
		}
	}

	public void remove(TaTTvaDoc persistentInstance) {
		logger.debug("removing TaTTiers instance");
		try {
			getEntityManager().remove(persistentInstance);
			logger.debug("remove successful");
		} catch (RuntimeException re) {
			logger.error("remove failed", re);
			throw re;
		}
	}

	public TaTTvaDoc merge(TaTTvaDoc detachedInstance) {
		logger.debug("merging TaTTvaDoc instance");
		try {
			TaTTvaDoc result = getEntityManager().merge(detachedInstance);
			logger.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			logger.error("merge failed", re);
			throw re;
		}
	}

	public TaTTvaDoc findById(int id) {
		logger.debug("getting TaTTvaDoc instance with id: " + id);
		try {
			TaTTvaDoc instance = getEntityManager().find(TaTTvaDoc.class, id);
			logger.debug("get successful");
			return instance;
		} catch (RuntimeException re) {
			logger.error("get failed", re);
			throw re;
		}
	}
	
	public TaTTvaDoc findByCode(String code) {
		logger.debug("getting TaTTvaDoc instance with code: " + code);
		try {
			if(!code.equals("")){
			Query query = getEntityManager().createQuery("select f from TaTTvaDoc f where f.codeTTvaDoc='"+code+"'");
			TaTTvaDoc instance = (TaTTvaDoc)query.getSingleResult();
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
			Query query = getEntityManager().createQuery("select count(f) from TaTTvaDoc f where f.codeTTvaDoc='"+code+"'");
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
	public List<TaTTvaDoc> selectAll() {
		// TODO Auto-generated method stub
		logger.debug("selectAll TaTTvaDoc");
		try {
			Query ejbQuery = getEntityManager().createQuery(JPQLQuery);
			List<TaTTvaDoc> l = ejbQuery.getResultList();
			logger.debug("selectAll successful");
			return l;
		} catch (RuntimeException re) {
			logger.error("selectAll failed", re);
			throw re;
		}
	}
	public void ctrlSaisieSpecifique(TaTTvaDoc entity,String field) throws ExceptLgr {	
		
	}
}
