package fr.legrain.pointLgr.dao;

// Generated Mar 24, 2009 4:44:04 PM by Hibernate Tools 3.2.0.CR1

import java.util.LinkedList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import fr.legrain.documents.dao.TaReglement;
import fr.legrain.gestCom.Appli.AbstractApplicationDAO;
import fr.legrain.gestCom.Appli.Const;
import fr.legrain.lib.data.ExceptLgr;
import fr.legrain.tiers.dao.TaTCivilite;
import fr.legrain.tiers.dao.TaTiers;
import fr.legrain.tiers.dao.TaTiersDAO;


/**
 * Home object for domain model class TaAdresse.
 * @see fr.legrain.tiers.dao.TaAdresse
 * @author Hibernate Tools
 */
public class TaTypeMouvPointDAO extends AbstractApplicationDAO<TaTypeMouvPoint>{

	//private static final Log logger = LogFactory.getLog(TaAdresseDAO.class);
	static Logger logger = Logger.getLogger(TaTypeMouvPointDAO.class);
	
	private String defaultJPQLQuery = "select a from TaTypeMouvPoint a";
	
	public TaTypeMouvPointDAO(){
		this(null);
	}
	
	public TaTypeMouvPointDAO(EntityManager em){
		if(em!=null) {
			setEm(em);
		}
//		champIdTable=ctrlGeneraux.getID_TABLE(TaTiersPoint.class.getSimpleName());
		initChampId(TaTypeMouvPoint.class);
		setJPQLQuery(defaultJPQLQuery);
		setListeChampMaj(Const.C_FICHIER_INI_CHAMPMAJ);
		initNomTable(new TaTypeMouvPoint());
	}

	public TaTypeMouvPoint refresh(TaTypeMouvPoint detachedInstance) {
		logger.debug("refresh instance");
		try {
			org.hibernate.Session session = (org.hibernate.Session) getEntityManager().getDelegate();
		    session.evict(detachedInstance);
		    detachedInstance = getEntityManager().find(TaTypeMouvPoint.class, detachedInstance.getIdTypeMouv());
		    return detachedInstance;
		} catch (RuntimeException re) {
			logger.error("refresh failed", re);
			throw re;
		}
	}
	public void persist(TaTypeMouvPoint transientInstance) {
		logger.debug("persisting TaTypeMouvPoint instance");
		try {
			getEntityManager().persist(transientInstance);
			logger.debug("persist successful");
		} catch (RuntimeException re) {
			logger.error("persist failed", re);
			throw re;
		}
	}

	public void remove(TaTypeMouvPoint persistentInstance) {
		logger.debug("removing TaTypeMouvPoint instance");
		try {
			getEntityManager().remove(persistentInstance);
			logger.debug("remove successful");
		} catch (RuntimeException re) {
			logger.error("remove failed", re);
			throw re;
		}
	}

	public TaTypeMouvPoint merge(TaTypeMouvPoint detachedInstance) {
		logger.debug("merging TaTypeMouvPoint instance");
		try {
			TaTypeMouvPoint result = getEntityManager().merge(detachedInstance);
			logger.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			logger.error("merge failed", re);
			throw re;
		}
	}

	public TaTypeMouvPoint findById(int id) {
		logger.debug("getting TaTypeMouvPoint instance with id: " + id);
		try {
			TaTypeMouvPoint instance = getEntityManager().find(TaTypeMouvPoint.class, id);
			logger.debug("get successful");
			return instance;
		} catch (RuntimeException re) {
			logger.error("get failed", re);
			throw re;
		}
	}

	public TaTypeMouvPoint findByCode(String code) {
		logger.debug("getting TaTypeMouvPoint instance with code: " + code);
		try {
			if(!code.equals("")){
			Query query = getEntityManager().createQuery("select f from TaTypeMouvPoint f where f.typeMouv='"+code+"'");
			TaTypeMouvPoint instance = (TaTypeMouvPoint)query.getSingleResult();
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
	public List<TaTypeMouvPoint> selectAll() {
		// TODO Auto-generated method stub
		logger.debug("selectAll TaTypeMouvPoint");
		try {
			Query ejbQuery = getEntityManager().createQuery(JPQLQuery);
			List<TaTypeMouvPoint> l = ejbQuery.getResultList();
			logger.debug("selectAll successful");
			return l;
		} catch (RuntimeException re) {
			logger.error("selectAll failed", re);
			throw re;
		}
	}
	
	
	public void ctrlSaisieSpecifique(TaComptePoint entity,String field) throws ExceptLgr {	
		
	}

}
