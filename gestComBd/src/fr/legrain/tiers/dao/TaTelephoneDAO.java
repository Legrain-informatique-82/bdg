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
 * Home object for domain model class TaTelephone.
 * @see fr.legrain.tiers.dao.TaTelephone
 * @author Hibernate Tools
 */
public class TaTelephoneDAO extends AbstractApplicationDAO<TaTelephone>{

	//private static final Log logger = LogFactory.getLog(TaTelephoneDAO.class);
	static Logger logger = Logger.getLogger(TaTelephoneDAO.class);

	private String defaultJPQLQuery = "select a from TaTelephone a";
	
	public TaTelephoneDAO() {
		this(null);
	}

	public TaTelephoneDAO(EntityManager em){
		if(em!=null) {
			setEm(em);
		}
//		champIdTable=ctrlGeneraux.getID_TABLE(TaTelephone.class.getSimpleName());
		initChampId(TaTelephone.class);
		setJPQLQuery(defaultJPQLQuery);
		setListeChampMaj(Const.C_FICHIER_INI_CHAMPMAJ);
		initNomTable(new TaTelephone());
	}
	public TaTelephone refresh(TaTelephone detachedInstance) {
		logger.debug("refresh instance");
		try {
			org.hibernate.Session session = (org.hibernate.Session) getEntityManager().getDelegate();
		    session.evict(detachedInstance);
		    detachedInstance = getEntityManager().find(TaTelephone.class, detachedInstance.getIdTelephone());
		    return detachedInstance;
		} catch (RuntimeException re) {
			logger.error("refresh failed", re);
			throw re;
		}
	}

	public void persist(TaTelephone transientInstance) {
		logger.debug("persisting TaTelephone instance");
		try {
			getEntityManager().persist(transientInstance);
			logger.debug("persist successful");
		} catch (RuntimeException re) {
			logger.error("persist failed", re);
			throw re;
		}
	}

	public void remove(TaTelephone persistentInstance) {
		logger.debug("removing TaTelephone instance");
		try {
			getEntityManager().remove(persistentInstance);
			logger.debug("remove successful");
		} catch (RuntimeException re) {
			logger.error("remove failed", re);
			throw re;
		}
	}

	public TaTelephone merge(TaTelephone detachedInstance) {
		logger.debug("merging TaTelephone instance");
		try {
			TaTelephone result = getEntityManager().merge(detachedInstance);
			logger.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			logger.error("merge failed", re);
			throw re;
		}
	}

	public TaTelephone findById(int id) {
		logger.debug("getting TaTelephone instance with id: " + id);
		try {
			TaTelephone instance = getEntityManager().find(TaTelephone.class, id);
			logger.debug("get successful");
			return instance;
		} catch (RuntimeException re) {
			logger.error("get failed", re);
			throw re;
		}
	}
	
	/**
	 * Recherche les telephones qui ont été importés d'un autre programme
	 * @param origineImport
	 * @param idImport - id externe (dans le programme d'origine)
	 * @return
	 */
	public List<TaTelephone> rechercheParImport(String origineImport, String idImport) {
		Query query = getEntityManager().createNamedQuery(TaTelephone.QN.FIND_BY_IMPORT);
		query.setParameter(1, origineImport);
		query.setParameter(2, idImport);
		List<TaTelephone> l = query.getResultList();

		return l;
	}

	@Override
	public List<TaTelephone> selectAll() {
		// TODO Auto-generated method stub
		logger.debug("selectAll TaTelephone");
		try {
			Query ejbQuery = getEntityManager().createQuery(JPQLQuery);
			List<TaTelephone> l = ejbQuery.getResultList();
			logger.debug("selectAll successful");
			return l;
		} catch (RuntimeException re) {
			logger.error("selectAll failed", re);
			throw re;
		}
	}
	public void ctrlSaisieSpecifique(TaTelephone entity,String field) throws ExceptLgr {	
		
	}
}
