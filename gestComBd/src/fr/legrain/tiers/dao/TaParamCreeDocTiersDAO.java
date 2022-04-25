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
 * Home object for domain model class TaTAdr.
 * @see fr.legrain.tiers.dao.TaTAdr
 * @author Hibernate Tools
 */
public class TaParamCreeDocTiersDAO extends AbstractApplicationDAO<TaParamCreeDocTiers>{

	//private static final Log logger = LogFactory.getLog(TaTAdrDAO.class);
	static Logger logger = Logger.getLogger(TaParamCreeDocTiersDAO.class);

	private String defaultJPQLQuery = "select a from TaParamCreeDocTiers a";
	
	public TaParamCreeDocTiersDAO() {
		this(null);
	}
	
	public TaParamCreeDocTiersDAO(EntityManager em){
		if(em!=null) {
			setEm(em);
		}
//		champIdTable=ctrlGeneraux.getID_TABLE(TaTAdr.class.getSimpleName());
		initChampId(TaParamCreeDocTiers.class);
		setJPQLQuery(defaultJPQLQuery);
		setListeChampMaj(Const.C_FICHIER_INI_CHAMPMAJ);
		initNomTable(new TaParamCreeDocTiers());
	}
	public TaParamCreeDocTiers refresh(TaParamCreeDocTiers detachedInstance) {
		logger.debug("refresh instance");
		try {
			org.hibernate.Session session = (org.hibernate.Session) getEntityManager().getDelegate();
		    session.evict(detachedInstance);
		    detachedInstance = getEntityManager().find(TaParamCreeDocTiers.class, detachedInstance.getIdParamCreeDocTiers());
		    return detachedInstance;
		} catch (RuntimeException re) {
			logger.error("refresh failed", re);
			throw re;
		}
	}
	public void persist(TaParamCreeDocTiers transientInstance) {
		logger.debug("persisting TaParamCreeDocTiers instance");
		try {
			getEntityManager().persist(transientInstance);
			logger.debug("persist successful");
		} catch (RuntimeException re) {
			logger.error("persist failed", re);
			throw re;
		}
	}

	public void remove(TaParamCreeDocTiers persistentInstance) {
		logger.debug("removing TaParamCreeDocTiers instance");
		try {
			getEntityManager().remove(persistentInstance);
			logger.debug("remove successful");
		} catch (RuntimeException re) {
			logger.error("remove failed", re);
			throw re;
		}
	}

	public TaParamCreeDocTiers merge(TaParamCreeDocTiers detachedInstance) {
		logger.debug("merging TaParamCreeDocTiers instance");
		try {
			TaParamCreeDocTiers result = getEntityManager().merge(detachedInstance);
			logger.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			logger.error("merge failed", re);
			throw re;
		}
	}

	public TaParamCreeDocTiers findById(int id) {
		logger.debug("getting TaParamCreeDocTiers instance with id: " + id);
		try {
			TaParamCreeDocTiers instance = getEntityManager().find(TaParamCreeDocTiers.class, id);
			logger.debug("get successful");
			return instance;
		} catch (RuntimeException re) {
			logger.error("get failed", re);
			throw re;
		}
	}
	
	public List<TaParamCreeDocTiers> findByCode(String codeTiers) {
		logger.debug("getting TaParamCreeDocTiers instance with codeTiers: " + codeTiers);
		try {
			if(!codeTiers.equals("")){
			Query query = getEntityManager().createQuery("select f from TaParamCreeDocTiers f" +
					" join f.taTiers t  where t.codeTiers ='"+codeTiers+"'");
			List<TaParamCreeDocTiers> l =query.getResultList();
			logger.debug("get successful");
			return l;
			}
			return null;
		} catch (RuntimeException re) {
			logger.error("get failed", re);
			throw re;
		}
	}
	
	public List<TaParamCreeDocTiers> findByCodeTypeDoc(String codeTiers,String typeDoc) {
		logger.debug("getting TaParamCreeDocTiers instance with codeTiers: " + codeTiers);
		try {
			if(!codeTiers.equals("") && !typeDoc.equals("")){
			Query query = getEntityManager().createQuery("select f from TaParamCreeDocTiers f" +
					" join f.taTiers t  join f.taTDoc td where td.codeTDoc = '"+typeDoc+"' and t.codeTiers ='"+codeTiers+"'");
			List<TaParamCreeDocTiers> l = query.getResultList();
			logger.debug("get successful");
			return l;
			}
			return null;
		} catch (RuntimeException re) {
			logger.error("get failed", re);
			throw re;
		}
	}

	public List<TaParamCreeDocTiers> findByTypeDoc(String typeDoc) {
		logger.debug("getting TaParamCreeDocTiers instance with typeDoc: " + typeDoc);
		try {
			if(!typeDoc.equals("")){
			Query query = getEntityManager().createQuery("select f from TaParamCreeDocTiers f" +
					"  where f.typeDoc = "+typeDoc+"'");
			List<TaParamCreeDocTiers> l = query.getResultList();
			logger.debug("get successful");
			return l;
			}
			return null;
		} catch (RuntimeException re) {
			logger.error("get failed", re);
			throw re;
		}
	}
	
	@Override
	public List<TaParamCreeDocTiers> selectAll() {
		logger.debug("selectAll TaParamCreeDocTiers");
		try {
			Query ejbQuery = getEntityManager().createQuery(JPQLQuery);
			List<TaParamCreeDocTiers> l = ejbQuery.getResultList();
			logger.debug("selectAll successful");
			return l;
		} catch (RuntimeException re) {
			logger.error("selectAll failed", re);
			throw re;
		}
	}
	public void ctrlSaisieSpecifique(TaTAdr entity,String field) throws ExceptLgr {	
		
	}
}
