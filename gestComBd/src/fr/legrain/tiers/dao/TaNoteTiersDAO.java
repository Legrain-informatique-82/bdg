package fr.legrain.tiers.dao;

// Generated Mar 24, 2009 4:44:04 PM by Hibernate Tools 3.2.0.CR1

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import fr.legrain.gestCom.Appli.AbstractApplicationDAO;
import fr.legrain.gestCom.Appli.Const;
import fr.legrain.lib.data.ExceptLgr;

/**
 * Home object for domain model class TaNoteTiers.
 * @see fr.legrain.tiers.dao.TaNoteTiers
 * @author Hibernate Tools
 */
public class TaNoteTiersDAO extends AbstractApplicationDAO<TaNoteTiers> {

	private static final Log logger = LogFactory.getLog(TaNoteTiersDAO.class);
	//static Logger logger = Logger.getLogger(TaNoteDAO.class.getName());
	
	private String defaultJPQLQuery = "select u from TaNoteTiers u";
	
	public TaNoteTiersDAO(){
		this(null);
	}

	public TaNoteTiersDAO(EntityManager em){
		if(em!=null) {
			setEm(em);
		}
//		champIdTable=ctrlGeneraux.getID_TABLE(TaNoteTiers.class.getSimpleName());
		initChampId(TaNoteTiers.class);
		setJPQLQuery(defaultJPQLQuery);
		setListeChampMaj(Const.C_FICHIER_INI_CHAMPMAJ);
		initNomTable(new TaNoteTiers());
	}
	public TaNoteTiers refresh(TaNoteTiers detachedInstance) {
		logger.debug("refresh instance");
		try {
			org.hibernate.Session session = (org.hibernate.Session) getEntityManager().getDelegate();
		    session.evict(detachedInstance);
		    detachedInstance = getEntityManager().find(TaNoteTiers.class, detachedInstance.getIdNoteTiers());
		    return detachedInstance;
		} catch (RuntimeException re) {
			logger.error("refresh failed", re);
			throw re;
		}
	}
	public void persist(TaNoteTiers transientInstance) {
		logger.debug("persisting TaNoteTiers instance");
		try {
			getEntityManager().persist(transientInstance);
			logger.debug("persist successful");
		} catch (RuntimeException re) {
			logger.error("persist failed", re);
			throw re;
		}
	}

	public void remove(TaNoteTiers persistentInstance) {
		logger.debug("removing TaNoteTiers instance");
		try {
			getEntityManager().remove(persistentInstance);
			logger.debug("remove successful");
		} catch (RuntimeException re) {
			logger.error("remove failed", re);
			throw re;
		}
	}

	public TaNoteTiers merge(TaNoteTiers detachedInstance) {
		logger.debug("merging TaNoteTiers instance");
		try {
			TaNoteTiers result = getEntityManager().merge(detachedInstance);
			logger.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			logger.error("merge failed", re);
			throw re;
		}
	}

	public TaNoteTiers findById(int id) {
		logger.debug("getting TaNoteTiers instance with id: " + id);
		try {
			TaNoteTiers instance = getEntityManager().find(TaNoteTiers.class, id);
			logger.debug("get successful");
			return instance;
		} catch (RuntimeException re) {
			logger.error("get failed", re);
			throw re;
		}
	}
	
	@Override
	public List<TaNoteTiers> selectAll() {
		// TODO Auto-generated method stub
		logger.debug("selectAll TaNoteTiers");
		try {
			Query ejbQuery = getEntityManager().createQuery(JPQLQuery);
			List<TaNoteTiers> l = ejbQuery.getResultList();
			logger.debug("selectAll successful");
			return l;
		} catch (RuntimeException re) {
			logger.error("selectAll failed", re);
			throw re;
		}
	}
	public void ctrlSaisieSpecifique(TaNoteTiers entity,String field) throws ExceptLgr {	
		
	}
}
