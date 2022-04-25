package fr.legrain.tiers.dao;

// Generated Mar 24, 2009 4:44:04 PM by Hibernate Tools 3.2.0.CR1

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import fr.legrain.gestCom.Appli.AbstractApplicationDAO;
import fr.legrain.gestCom.Appli.Const;
import fr.legrain.lib.data.ExceptLgr;


/**
 * Home object for domain model class TaAdresse.
 * @see fr.legrain.tiers.dao.TaAdresse
 * @author Hibernate Tools
 */
public class TaAdresseDAO extends AbstractApplicationDAO<TaAdresse>{

	//private static final Log logger = LogFactory.getLog(TaAdresseDAO.class);
	static Logger logger = Logger.getLogger(TaAdresseDAO.class);
	
	private String defaultJPQLQuery = "select a from TaAdresse a";
	
	public TaAdresseDAO(){
		this(null);
	}
	
	public TaAdresseDAO(EntityManager em){
		if(em!=null) {
			setEm(em);
		}
//		champIdTable=ctrlGeneraux.getID_TABLE(TaAdresse.class.getSimpleName());
		initChampId(TaAdresse.class);
		setJPQLQuery(defaultJPQLQuery);
		setListeChampMaj(Const.C_FICHIER_INI_CHAMPMAJ);
		initNomTable(new TaAdresse());
	}

	public TaAdresse refresh(TaAdresse detachedInstance) {
		logger.debug("refresh instance");
		try {
			org.hibernate.Session session = (org.hibernate.Session) getEntityManager().getDelegate();
		    session.evict(detachedInstance);
		    detachedInstance = getEntityManager().find(TaAdresse.class, detachedInstance.getIdAdresse());
		    return detachedInstance;
		} catch (RuntimeException re) {
			logger.error("refresh failed", re);
			throw re;
		}
	}
	public void persist(TaAdresse transientInstance) {
		logger.debug("persisting TaAdresse instance");
		try {
			getEntityManager().persist(transientInstance);
			logger.debug("persist successful");
		} catch (RuntimeException re) {
			logger.error("persist failed", re);
			throw re;
		}
	}

	public void remove(TaAdresse persistentInstance) {
		logger.debug("removing TaAdresse instance");
		try {
			getEntityManager().remove(persistentInstance);
			logger.debug("remove successful");
		} catch (RuntimeException re) {
			logger.error("remove failed", re);
			throw re;
		}
	}

	public TaAdresse merge(TaAdresse detachedInstance) {
		logger.debug("merging TaAdresse instance");
		try {
			TaAdresse result = getEntityManager().merge(detachedInstance);
			logger.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			logger.error("merge failed", re);
			throw re;
		}
	}

	public TaAdresse findById(int id) {
		logger.debug("getting TaAdresse instance with id: " + id);
		try {
			TaAdresse instance = getEntityManager().find(TaAdresse.class, id);
			logger.debug("get successful");
			return instance;
		} catch (RuntimeException re) {
			logger.error("get failed", re);
			throw re;
		}
	}
	
	/**
	 * Recherche les adresses qui ont été importés d'un autre programme
	 * @param origineImport
	 * @param idImport - id externe (dans le programme d'origine)
	 * @return
	 */
	public List<TaAdresse> rechercheParImport(String origineImport, String idImport) {
		Query query = getEntityManager().createNamedQuery(TaAdresse.QN.FIND_BY_IMPORT);
		query.setParameter(1, origineImport);
		query.setParameter(2, idImport);
		List<TaAdresse> l = query.getResultList();

		return l;
	}

	@Override
	public List<TaAdresse> selectAll() {
		// TODO Auto-generated method stub
		logger.debug("selectAll TaAdresse");
		try {
			Query ejbQuery = getEntityManager().createQuery(JPQLQuery);
			List<TaAdresse> l = ejbQuery.getResultList();
			logger.debug("selectAll successful");
			return l;
		} catch (RuntimeException re) {
			logger.error("selectAll failed", re);
			throw re;
		}
	}
	
	public void ctrlSaisieSpecifique(TaAdresse entity,String field) throws ExceptLgr {	
		
	}

}
