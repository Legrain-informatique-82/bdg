package fr.legrain.dossier.dao;

// Generated 14 mai 2009 11:08:14 by Hibernate Tools 3.2.4.GA

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import fr.legrain.articles.dao.TaPrix;
import fr.legrain.articles.dao.TaUnite;
import fr.legrain.documents.dao.TaInfosApporteur;
import fr.legrain.gestCom.Appli.AbstractApplicationDAO;
import fr.legrain.gestCom.Appli.Const;

/**
 * Home object for domain model class TaInfoEntreprise.
 * @see fr.legrain.dossier.dao.TaInfoEntreprise
 * @author Hibernate Tools
 */
public class TaInfoEntrepriseDAO extends AbstractApplicationDAO<TaInfoEntreprise> {

//	private static final Log log = LogFactory.getLog(TaInfoEntrepriseDAO.class);
	static Logger logger = Logger.getLogger(TaInfoEntrepriseDAO.class);
	
	private String defaultJPQLQuery = "select a from TaInfoEntreprise a";
	
	public TaInfoEntrepriseDAO(){
		this(null);
	}
	
	public TaInfoEntrepriseDAO(EntityManager em){
		if(em!=null) {
			setEm(em);
		}
//		champIdTable=ctrlGeneraux.getID_TABLE(TaInfoEntreprise.class.getSimpleName());
		initChampId(TaInfoEntreprise.class);
		setJPQLQuery(defaultJPQLQuery);
		setListeChampMaj(Const.C_FICHIER_INI_CHAMPMAJ);
		initNomTable(new TaInfoEntreprise());
	}
	public TaInfoEntreprise refresh(TaInfoEntreprise detachedInstance) {
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
	public void persist(TaInfoEntreprise transientInstance) {
		logger.debug("persisting TaInfoEntreprise instance");
		try {
			getEntityManager().persist(transientInstance);
			logger.debug("persist successful");
		} catch (RuntimeException re) {
			logger.error("persist failed", re);
			throw re;
		}
	}

	public void remove(TaInfoEntreprise persistentInstance) {
		logger.debug("removing TaInfoEntreprise instance");
		try {
			getEntityManager().remove(persistentInstance);
			logger.debug("remove successful");
		} catch (RuntimeException re) {
			logger.error("remove failed", re);
			throw re;
		}
	}

	public TaInfoEntreprise merge(TaInfoEntreprise detachedInstance) {
		logger.debug("merging TaInfoEntreprise instance");
		try {
			TaInfoEntreprise result = getEntityManager().merge(detachedInstance);
			logger.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			logger.error("merge failed", re);
			throw re;
		}
	}

	public TaInfoEntreprise findById(int id) {
		logger.debug("getting TaInfoEntreprise instance with id: " + id);
		try {
			TaInfoEntreprise instance = getEntityManager().find(
					TaInfoEntreprise.class, id);
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
	public TaInfoEntreprise findInstance() {
		int premierId = 1;
		logger.debug("getting TaInfoEntreprise instance with id: "+premierId);
		try {
			TaInfoEntreprise instance = findById(premierId);
			if(instance == null) {
				logger.debug("Aucun objet TaInfoEntreprise trouve, creation d'une nouvelle instance vide");
				instance = new TaInfoEntreprise();
			}
			logger.debug("get successful");
			return instance;
		} catch (RuntimeException re) {
			logger.error("get failed", re);
			throw re;
		}
	}

	@Override
	public List<TaInfoEntreprise> selectAll() {
		logger.debug("selectAll TaInfoEntreprise");
		try {
			Query ejbQuery = getEntityManager().createQuery(JPQLQuery);
			List<TaInfoEntreprise> l = ejbQuery.getResultList();
			logger.debug("selectAll successful");
			return l;
		} catch (RuntimeException re) {
			logger.error("selectAll failed", re);
			throw re;
		}
	}
	
	public int selectCount() {
		// dans ce cas on peu faire un selectAll car il ne doit y avoir qu'un seul enregistrement au maximum
		//return 1;
		return selectAll().size();
	}
}
