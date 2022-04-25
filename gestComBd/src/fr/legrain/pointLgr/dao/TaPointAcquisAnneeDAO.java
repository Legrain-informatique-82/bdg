package fr.legrain.pointLgr.dao;

// Generated Mar 24, 2009 4:44:04 PM by Hibernate Tools 3.2.0.CR1

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


/**
 * Home object for domain model class TaAdresse.
 * @see fr.legrain.tiers.dao.TaAdresse
 * @author Hibernate Tools
 */
public class TaPointAcquisAnneeDAO extends AbstractApplicationDAO<TaPointAcquisAnnee>{

	//private static final Log logger = LogFactory.getLog(TaAdresseDAO.class);
	static Logger logger = Logger.getLogger(TaPointAcquisAnneeDAO.class);
	
	private String defaultJPQLQuery = "select a from TaPointAcquisAnnee a";
	
	public TaPointAcquisAnneeDAO(){
		this(null);
	}
	
	public TaPointAcquisAnneeDAO(EntityManager em){
		if(em!=null) {
			setEm(em);
		}
//		champIdTable=ctrlGeneraux.getID_TABLE(TaTiersPoint.class.getSimpleName());
		initChampId(TaPointAcquisAnnee.class);
		setJPQLQuery(defaultJPQLQuery);
		setListeChampMaj(Const.C_FICHIER_INI_CHAMPMAJ);
		initNomTable(new TaPointAcquisAnnee());
	}

	public TaPointAcquisAnnee refresh(TaPointAcquisAnnee detachedInstance) {
		logger.debug("refresh instance");
		try {
			org.hibernate.Session session = (org.hibernate.Session) getEntityManager().getDelegate();
		    session.evict(detachedInstance);
		    detachedInstance = getEntityManager().find(TaPointAcquisAnnee.class, detachedInstance.getIdPoint());
		    return detachedInstance;
		} catch (RuntimeException re) {
			logger.error("refresh failed", re);
			throw re;
		}
	}
	public void persist(TaPointAcquisAnnee transientInstance) {
		logger.debug("persisting TaPointAcquisAnnee instance");
		try {
			getEntityManager().persist(transientInstance);
			logger.debug("persist successful");
		} catch (RuntimeException re) {
			logger.error("persist failed", re);
			throw re;
		}
	}

	public void remove(TaPointAcquisAnnee persistentInstance) {
		logger.debug("removing TaPointAcquisAnnee instance");
		try {
			getEntityManager().remove(persistentInstance);
			logger.debug("remove successful");
		} catch (RuntimeException re) {
			logger.error("remove failed", re);
			throw re;
		}
	}

	public TaPointAcquisAnnee merge(TaPointAcquisAnnee detachedInstance) {
		logger.debug("merging TaPointAcquisAnnee instance");
		try {
			TaPointAcquisAnnee result = getEntityManager().merge(detachedInstance);
			logger.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			logger.error("merge failed", re);
			throw re;
		}
	}

	public TaPointAcquisAnnee findById(int id) {
		logger.debug("getting TaPointAcquisAnnee instance with id: " + id);
		try {
			TaPointAcquisAnnee instance = getEntityManager().find(TaPointAcquisAnnee.class, id);
			logger.debug("get successful");
			return instance;
		} catch (RuntimeException re) {
			logger.error("get failed", re);
			throw re;
		}
	}

	@Override
	public List<TaPointAcquisAnnee> selectAll() {
		// TODO Auto-generated method stub
		logger.debug("selectAll TaPointAcquisAnnee");
		try {
			Query ejbQuery = getEntityManager().createQuery(JPQLQuery);
			List<TaPointAcquisAnnee> l = ejbQuery.getResultList();
			logger.debug("selectAll successful");
			return l;
		} catch (RuntimeException re) {
			logger.error("selectAll failed", re);
			throw re;
		}
	}
	
	public void ctrlSaisieSpecifique(TaPointAcquisAnnee entity,String field) throws ExceptLgr {	
		
	}

}
