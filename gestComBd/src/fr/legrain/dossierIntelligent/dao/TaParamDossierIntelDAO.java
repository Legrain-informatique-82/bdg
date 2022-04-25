package fr.legrain.dossierIntelligent.dao;

// Generated Mar 24, 2009 4:44:04 PM by Hibernate Tools 3.2.0.CR1

import java.util.LinkedList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import fr.legrain.articles.dao.TaPrix;
import fr.legrain.articles.dao.TaUnite;
import fr.legrain.documents.dao.TaInfosApporteur;
import fr.legrain.gestCom.Appli.AbstractApplicationDAO;
import fr.legrain.gestCom.Appli.Const;
import fr.legrain.lib.data.ExceptLgr;
import fr.legrain.tiers.dao.TaTiers;
import fr.legrain.tiers.dao.TaTiersDAO;


/**
 * Home object for domain model class TaAdresse.
 * @see fr.legrain.tiers.dao.TaAdresse
 * @author Hibernate Tools
 */
public class TaParamDossierIntelDAO extends AbstractApplicationDAO<TaParamDossierIntel>{

	//private static final Log logger = LogFactory.getLog(TaAdresseDAO.class);
	static Logger logger = Logger.getLogger(TaParamDossierIntelDAO.class);
	
	private String defaultJPQLQuery = "select a from TaParamDossierIntel a";
	
	public TaParamDossierIntelDAO(){
		this(null);
	}
	
	public TaParamDossierIntelDAO(EntityManager em){
		if(em!=null) {
			setEm(em);
		}
//		champIdTable=ctrlGeneraux.getID_TABLE(TaParamDossierIntel.class.getSimpleName());
		initChampId(TaParamDossierIntel.class);
		setJPQLQuery(defaultJPQLQuery);
		setListeChampMaj(Const.C_FICHIER_INI_CHAMPMAJ);
		initNomTable(new TaParamDossierIntel());
	}

	public TaParamDossierIntel refresh(TaParamDossierIntel detachedInstance) {
		logger.debug("refresh instance");
		try {
			org.hibernate.Session session = (org.hibernate.Session) getEntityManager().getDelegate();
		    session.evict(detachedInstance);
		    detachedInstance = getEntityManager().find(TaParamDossierIntel.class, detachedInstance.getId());
		    return detachedInstance;
		} catch (RuntimeException re) {
			logger.error("refresh failed", re);
			throw re;
		}
	}
	public void persist(TaParamDossierIntel transientInstance) {
		logger.debug("persisting TaParamDossierIntel instance");
		try {
			getEntityManager().persist(transientInstance);
			logger.debug("persist successful");
		} catch (RuntimeException re) {
			logger.error("persist failed", re);
			throw re;
		}
	}

	public void remove(TaParamDossierIntel persistentInstance) {
		logger.debug("removing TaParamDossierIntel instance");
		try {
			getEntityManager().remove(persistentInstance);
			logger.debug("remove successful");
		} catch (RuntimeException re) {
			logger.error("remove failed", re);
			throw re;
		}
	}

	public TaParamDossierIntel merge(TaParamDossierIntel detachedInstance) {
		logger.debug("merging TaParamDossierIntel instance");
		try {
			TaParamDossierIntel result = getEntityManager().merge(detachedInstance);
			logger.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			logger.error("merge failed", re);
			throw re;
		}
	}

	public TaParamDossierIntel findById(int id) {
		logger.debug("getting TaParamDossierIntel instance with id: " + id);
		try {
			TaParamDossierIntel instance = getEntityManager().find(TaParamDossierIntel.class, id);
			logger.debug("get successful");
			return instance;
		} catch (RuntimeException re) {
			logger.error("get failed", re);
			throw re;
		}
	}
	public TaParamDossierIntel findByCode(String code) {
		logger.debug("getting TaParamDossierIntel instance with code: " + code);
		try {
			if(!code.equals("")){
			Query query = getEntityManager().createQuery("select a from TaParamDossierIntel a where upper(a.mot)='"+code.toUpperCase().replace("'", "''")+"'");
			TaParamDossierIntel instance = (TaParamDossierIntel)query.getSingleResult();
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
	public List<TaParamDossierIntel> selectAll() {
		// TODO Auto-generated method stub
		logger.debug("selectAll TaParamDossierIntel");
		try {
			Query ejbQuery = getEntityManager().createQuery(JPQLQuery);
			List<TaParamDossierIntel> l = ejbQuery.getResultList();
			logger.debug("selectAll successful");
			return l;
		} catch (RuntimeException re) {
			logger.error("selectAll failed", re);
			throw re;
		}
	}
	
	
	public void ctrlSaisieSpecifique(TaParamDossierIntel entity,String field) throws ExceptLgr {	
		
	}

}
