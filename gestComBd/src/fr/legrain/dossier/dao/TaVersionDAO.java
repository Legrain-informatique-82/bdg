package fr.legrain.dossier.dao;

// Generated 14 mai 2009 11:08:14 by Hibernate Tools 3.2.4.GA

import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import fr.legrain.gestCom.Appli.AbstractApplicationDAO;
import fr.legrain.gestCom.Appli.Const;

/**
 * Home object for domain model class TaInfoEntreprise.
 * @see fr.legrain.dossier.dao.TaInfoEntreprise
 * @author Hibernate Tools
 */
public class TaVersionDAO extends AbstractApplicationDAO<TaVersion> {

//	private static final Log log = LogFactory.getLog(TaInfoEntrepriseDAO.class);
	static Logger logger = Logger.getLogger(TaVersionDAO.class);
	
	private String defaultJPQLQuery = "select a from TaVersion a";
	
	public TaVersionDAO(){
		this(null);
	}
	
	public TaVersionDAO(EntityManager em){
		if(em!=null) {
			setEm(em);
		}
		//champIdTable=ctrlGeneraux.getID_TABLE(TaVersion.class.getSimpleName());
		setJPQLQuery(defaultJPQLQuery);
		setListeChampMaj(Const.C_FICHIER_INI_CHAMPMAJ);
		//initNomTable(new TaVersion());
	}
	public TaVersion refresh(TaVersion detachedInstance) {
		logger.debug("refresh instance");
		try {
////			getEntityManager().refresh(detachedInstance);
////			logger.debug("refresh successful");
//			org.hibernate.Session session = (org.hibernate.Session) getEntityManager().getDelegate();
//		    session.evict(detachedInstance);
//		    detachedInstance = getEntityManager().find(TaInfoEntreprise.class, detachedInstance.getIdInfoEntreprise());
		    return detachedInstance;
		} catch (RuntimeException re) {
			logger.error("refresh failed", re);
			throw re;
		}
	}
	public void persist(TaVersion transientInstance) {
		logger.debug("persisting TaVersion instance");
		try {
			getEntityManager().persist(transientInstance);
			logger.debug("persist successful");
		} catch (RuntimeException re) {
			logger.error("persist failed", re);
			throw re;
		}
	}

	public void remove(TaVersion persistentInstance) {
		logger.debug("removing TaVersion instance");
		try {
			getEntityManager().remove(persistentInstance);
			logger.debug("remove successful");
		} catch (RuntimeException re) {
			logger.error("remove failed", re);
			throw re;
		}
	}

	public TaVersion merge(TaVersion detachedInstance) {
		logger.debug("merging TaVersion instance");
		try {
			TaVersion result = getEntityManager().merge(detachedInstance);
			logger.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			logger.error("merge failed", re);
			throw re;
		}
	}

	public TaVersion findById(int id) {
		logger.debug("getting TaVersion instance with id: " + id);
		try {
			TaVersion instance = getEntityManager().find(
					TaVersion.class, id);
			logger.debug("get successful");
			return instance;
		} catch (RuntimeException re) {
			logger.error("get failed", re);
			throw re;
		}
	}
	
	/**
	 * @return - l'unique instance de taInfoEntreprise si elle existe, sinon retourne null.
	 */
	public TaVersion findInstance() {		
		Integer premierId = selectFirstIndex();
		logger.debug("getting TaVersion instance with id: "+premierId);
		try {
			TaVersion instance =null; 
			if(premierId!=null)instance=findById(premierId);
			if(instance == null) {
				logger.debug("Aucun objet TaVersion trouve, creation d'une nouvelle instance vide");
				instance = new TaVersion();
			}
			logger.debug("get successful");
			return instance;
		} catch (RuntimeException re) {
			logger.error("get failed", re);
			throw re;
		}
	}

	@Override
	public List<TaVersion> selectAll() {
		logger.debug("selectAll TaVersion");
		try {
			Query ejbQuery = getEntityManager().createQuery(JPQLQuery);
			List<TaVersion> l = ejbQuery.getResultList();
			logger.debug("selectAll successful");
			return l;
		} catch (RuntimeException re) {
			logger.error("selectAll failed", re);
			throw re;
		}
	}
	
	
	public Integer selectFirstIndex() {
		try {
			Query ejbQuery = getEntityManager().createQuery(JPQLQuery);
			for (Iterator iterator = ejbQuery.getResultList().listIterator(); iterator.hasNext();) {
				TaVersion type = (TaVersion) iterator.next();
				return type.getIdVersion();
			}
			return null;
		} catch (RuntimeException re) {
			logger.error("selectFirstIndex failed", re);
			throw re;
		}
	}
	
	public int selectCount() {
		// dans ce cas on peu faire un selectAll car il ne doit y avoir qu'un seul enregistrement au maximum
		//return 1;
		return selectAll().size();
	}
}
