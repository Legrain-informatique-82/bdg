package fr.legrain.dossierIntelligent.dao;

// Generated Mar 24, 2009 4:44:04 PM by Hibernate Tools 3.2.0.CR1

import java.util.LinkedList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import fr.legrain.articles.dao.TaArticle;
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
public class TaTypeDonneeDAO extends AbstractApplicationDAO<TaTypeDonnee>{

	//private static final Log logger = LogFactory.getLog(TaAdresseDAO.class);
	static Logger logger = Logger.getLogger(TaTypeDonneeDAO.class);
	
	private String defaultJPQLQuery = "select a from TaTypeDonnee a";
	
	public TaTypeDonneeDAO(){
		this(null);
	}
	
	public TaTypeDonneeDAO(EntityManager em){
		if(em!=null) {
			setEm(em);
		}
//		champIdTable=ctrlGeneraux.getID_TABLE(TaTypeDonnee.class.getSimpleName());
		initChampId(TaTypeDonnee.class);
		setJPQLQuery(defaultJPQLQuery);
		setListeChampMaj(Const.C_FICHIER_INI_CHAMPMAJ);
		initNomTable(new TaTypeDonnee());
	}

	public TaTypeDonnee refresh(TaTypeDonnee detachedInstance) {
		logger.debug("refresh instance");
		try {
			org.hibernate.Session session = (org.hibernate.Session) getEntityManager().getDelegate();
		    session.evict(detachedInstance);
		    detachedInstance = getEntityManager().find(TaTypeDonnee.class, detachedInstance.getId());
		    return detachedInstance;
		} catch (RuntimeException re) {
			logger.error("refresh failed", re);
			throw re;
		}
	}
	public void persist(TaTypeDonnee transientInstance) {
		logger.debug("persisting TaTypeDonnee instance");
		try {
			getEntityManager().persist(transientInstance);
			logger.debug("persist successful");
		} catch (RuntimeException re) {
			logger.error("persist failed", re);
			throw re;
		}
	}

	public void remove(TaTypeDonnee persistentInstance) {
		logger.debug("removing TaTypeDonnee instance");
		try {
			getEntityManager().remove(persistentInstance);
			logger.debug("remove successful");
		} catch (RuntimeException re) {
			logger.error("remove failed", re);
			throw re;
		}
	}

	public TaTypeDonnee merge(TaTypeDonnee detachedInstance) {
		logger.debug("merging TaTypeDonnee instance");
		try {
			TaTypeDonnee result = getEntityManager().merge(detachedInstance);
			logger.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			logger.error("merge failed", re);
			throw re;
		}
	}

	public TaTypeDonnee findById(int id) {
		logger.debug("getting TaTypeDonnee instance with id: " + id);
		try {
			TaTypeDonnee instance = getEntityManager().find(TaTypeDonnee.class, id);
			logger.debug("get successful");
			return instance;
		} catch (RuntimeException re) {
			logger.error("get failed", re);
			throw re;
		}
	}

	public TaTypeDonnee findByCode(String code) {
		logger.debug("getting TaTypeDonnee instance with code: " + code);
		try {
			if(!code.equals("")){
			Query query = getEntityManager().createQuery("select a from TaTypeDonnee a where upper(a.typeDonnee)='"+code.toUpperCase()+"'");
			TaTypeDonnee instance = (TaTypeDonnee)query.getSingleResult();
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
	public List<TaTypeDonnee> selectAll() {
		// TODO Auto-generated method stub
		logger.debug("selectAll TaTypeDonnee");
		try {
			Query ejbQuery = getEntityManager().createQuery(JPQLQuery);
			List<TaTypeDonnee> l = ejbQuery.getResultList();
			logger.debug("selectAll successful");
			return l;
		} catch (RuntimeException re) {
			logger.error("selectAll failed", re);
			throw re;
		}
	}
	
	
	public void ctrlSaisieSpecifique(TaTypeDonnee entity,String field) throws ExceptLgr {	
		
	}

}
