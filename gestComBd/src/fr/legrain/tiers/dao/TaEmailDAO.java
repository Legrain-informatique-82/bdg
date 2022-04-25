package fr.legrain.tiers.dao;

// Generated Mar 24, 2009 4:44:04 PM by Hibernate Tools 3.2.0.CR1

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import fr.legrain.articles.dao.TaUnite;
import fr.legrain.gestCom.Appli.AbstractApplicationDAO;
import fr.legrain.gestCom.Appli.Const;
import fr.legrain.lib.data.ExceptLgr;

/**
 * Home object for domain model class TaEmail.
 * @see fr.legrain.tiers.dao.TaEmail
 * @author Hibernate Tools
 */
public class TaEmailDAO extends AbstractApplicationDAO<TaEmail>{

	//private static final Log logger = LogFactory.getLog(TaEmailDAO.class);
	static final Log logger = LogFactory.getLog(TaEmailDAO.class);

	private String defaultJPQLQuery = "select a from TaEmail a";
	
	public TaEmailDAO() {
		this(null);
	}

	public TaEmailDAO(EntityManager em){
		if(em!=null) {
			setEm(em);
		}
//		champIdTable=ctrlGeneraux.getID_TABLE(TaEmail.class.getSimpleName());
		initChampId(TaEmail.class);
		setJPQLQuery(defaultJPQLQuery);
		setListeChampMaj(Const.C_FICHIER_INI_CHAMPMAJ);
		initNomTable(new TaEmail());
	}

//	public TaEmailDAO(String defaultJPQLQuery) {
//		champIdTable=ctrlGeneraux.getID_TABLE(TaEmail.class.getSimpleName());
//		setJPQLQuery(defaultJPQLQuery);
//		setListeChampMaj(Const.C_FICHIER_INI_CHAMPMAJ);
//		initNomTable(new TaEmail());
//	}

	public TaEmail refresh(TaEmail detachedInstance) {
		logger.debug("refresh instance");
		try {
			org.hibernate.Session session = (org.hibernate.Session) getEntityManager().getDelegate();
		    session.evict(detachedInstance);
		    detachedInstance = getEntityManager().find(TaEmail.class, detachedInstance.getIdEmail());
		    return detachedInstance;
		} catch (RuntimeException re) {
			logger.error("refresh failed", re);
			throw re;
		}
	}
	public void persist(TaEmail transientInstance) {
		logger.debug("persisting TaEmail instance");
		try {
			getEntityManager().persist(transientInstance);
			logger.debug("persist successful");
		} catch (RuntimeException re) {
			logger.error("persist failed", re);
			throw re;
		}
	}

	public void remove(TaEmail persistentInstance) {
		logger.debug("removing TaEmail instance");
		try {
			getEntityManager().remove(persistentInstance);
			logger.debug("remove successful");
		} catch (RuntimeException re) {
			logger.error("remove failed", re);
			throw re;
		}
	}

	public TaEmail merge(TaEmail detachedInstance) {
		logger.debug("merging TaEmail instance");
		try {
			TaEmail result = getEntityManager().merge(detachedInstance);
			logger.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			logger.error("merge failed", re);
			throw re;
		}
	}

	public TaEmail findById(int id) {
		logger.debug("getting TaEmail instance with id: " + id);
		try {
			TaEmail instance = getEntityManager().find(TaEmail.class, id);
			logger.debug("get successful");
			return instance;
		} catch (RuntimeException re) {
			logger.error("get failed", re);
			throw re;
		}
	}
	
	/**
	 * Recherche les emails qui ont été importés d'un autre programme
	 * @param origineImport
	 * @param idImport - id externe (dans le programme d'origine)
	 * @return
	 */
	public List<TaEmail> rechercheParImport(String origineImport, String idImport) {
		Query query = getEntityManager().createNamedQuery(TaEmail.QN.FIND_BY_IMPORT);
		query.setParameter(1, origineImport);
		query.setParameter(2, idImport);
		List<TaEmail> l = query.getResultList();

		return l;
	}

	@Override
	public List<TaEmail> selectAll() {
		// TODO Auto-generated method stub
		logger.debug("selectAll TaEmail");
		try {
			Query ejbQuery = getEntityManager().createQuery(JPQLQuery);
			List<TaEmail> l = ejbQuery.getResultList();
			logger.debug("selectAll successful");
			return l;
		} catch (RuntimeException re) {
			logger.error("selectAll failed", re);
			throw re;
		}
	}
	public void ctrlSaisieSpecifique(TaEmail entity,String field) throws ExceptLgr {	
		
	}
}
