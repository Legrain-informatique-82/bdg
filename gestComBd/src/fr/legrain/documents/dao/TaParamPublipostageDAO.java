package fr.legrain.documents.dao;

// Generated Mar 24, 2009 4:44:04 PM by Hibernate Tools 3.2.0.CR1

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import fr.legrain.articles.dao.TaPrix;
import fr.legrain.dossier.dao.TaInfoEntreprise;
import fr.legrain.gestCom.Appli.AbstractApplicationDAO;
import fr.legrain.gestCom.Appli.Const;
import fr.legrain.lib.data.ExceptLgr;

/**
 * Home object for domain model class TaTLiens.
 * @see fr.legrain.tiers.dao.TaTLiens
 * @author Hibernate Tools
 */
public class TaParamPublipostageDAO extends AbstractApplicationDAO<TaParamPublipostage>{

	//private static final Log logger = LogFactory.getLog(TaTLiensDAO.class);
	static Logger logger = Logger.getLogger(TaParamPublipostageDAO.class);

	private String defaultJPQLQuery = "select a from TaParamPublipostage a ";
	
	public TaParamPublipostageDAO() {
		this(null);
	}
	
	public TaParamPublipostageDAO(EntityManager em){
		if(em!=null) {
			setEm(em);
		}
//		champIdTable=ctrlGeneraux.getID_TABLE(TaParamPublipostage.class.getSimpleName());
		initChampId(TaParamPublipostage.class);
		setJPQLQuery(defaultJPQLQuery);
		setListeChampMaj(Const.C_FICHIER_INI_CHAMPMAJ);
		initNomTable(new TaParamPublipostage());
	}
	public TaParamPublipostage refresh(TaParamPublipostage detachedInstance) {
		logger.debug("refresh instance");
		try {
			getEntityManager().refresh(detachedInstance);
//			org.hibernate.Session session = (org.hibernate.Session) getEntityManager().getDelegate();
//		    session.evict(detachedInstance);
//		    detachedInstance = getEntityManager().find(TaParamPublipostage.class, 
//		    		detachedInstance.getId());
		    return detachedInstance;
		} catch (RuntimeException re) {
			logger.error("refresh failed", re);
			throw re;
		}
	}
	public void persist(TaParamPublipostage transientInstance) {
		logger.debug("persisting TaParamPublipostage instance");
		try {
			getEntityManager().persist(transientInstance);
			logger.debug("persist successful");
		} catch (RuntimeException re) {
			logger.error("persist failed", re);
			throw re;
		}
	}

	public void remove(TaParamPublipostage persistentInstance) {
		logger.debug("removing TaParamPublipostage instance");
		try {
			getEntityManager().remove(persistentInstance);
			logger.debug("remove successful");
		} catch (RuntimeException re) {
			logger.error("remove failed", re);
			throw re;
		}
	}

	public TaParamPublipostage merge(TaParamPublipostage detachedInstance) {
		logger.debug("merging TaParamPublipostage instance");
		try {
			TaParamPublipostage result = getEntityManager().merge(detachedInstance);
			logger.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			logger.error("merge failed", re);
			throw re;
		}
	}

	public TaParamPublipostage findById(int id) {
		logger.debug("getting TaParamPublipostage instance with id: " + id);
		try {
			TaParamPublipostage instance = getEntityManager().find(TaParamPublipostage.class, id);
			logger.debug("get successful");
			return instance;
		} catch (RuntimeException re) {
			logger.error("get failed", re);
			throw re;
		}
	}
	
	/**
	 * @return - l'unique instance de TaParamPublipostage si elle existe, sinon retourne null.
	 */
	public TaParamPublipostage findInstance() {
		int premierId = 1;
		logger.debug("getting TaParamPublipostage instance with id: "+premierId);
		try {
			TaParamPublipostage instance = findById(premierId);
			if(instance == null) {
				logger.debug("Aucun objet TaParamPublipostage trouve, creation d'une nouvelle instance vide");
				instance = new TaParamPublipostage();
			}
			logger.debug("get successful");
			return instance;
		} catch (RuntimeException re) {
			logger.error("get failed", re);
			throw re;
		}
	}
	
	@Override
	public List<TaParamPublipostage> selectAll() {
		// TODO Auto-generated method stub
		logger.debug("selectAll TaParamPublipostage");
		try {  
			Query ejbQuery = getEntityManager().createQuery(JPQLQuery);
			List<TaParamPublipostage> l = ejbQuery.getResultList();
			logger.debug("selectAll successful");
			return l;
		} catch (RuntimeException re) {
			logger.error("selectAll failed", re);
			throw re;
		}
	}
	

	

}
