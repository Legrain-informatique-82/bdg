package fr.legrain.documents.dao;

// Generated Apr 7, 2009 3:27:24 PM by Hibernate Tools 3.2.0.CR1

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import fr.legrain.articles.dao.TaPrix;
import fr.legrain.articles.dao.TaUnite;
import fr.legrain.gestCom.Appli.AbstractApplicationDAO;
import fr.legrain.gestCom.Appli.Const;

/**
 * Home object for domain model class TaInfosApporteur.
 * @see fr.legrain.documents.dao.TaInfosApporteur
 * @author Hibernate Tools
 */
public class TaInfosApporteurDAO extends AbstractApplicationDAO<TaInfosApporteur> {

//	private static final Log log = LogFactory.getLog(TaInfosApporteurDAO.class);
	static Logger logger = Logger.getLogger(TaInfosApporteurDAO.class);
	
	private String defaultJPQLQuery = "select a from TaInfosApporteur a";
	
	public TaInfosApporteurDAO(){
		this(null);
	}
	
	public TaInfosApporteurDAO(EntityManager em){
		if(em!=null) {
			setEm(em);
		}
//		champIdTable=ctrlGeneraux.getID_TABLE(TaInfosApporteur.class.getSimpleName());
		initChampId(TaInfosApporteur.class);
		setJPQLQuery(defaultJPQLQuery);
		setListeChampMaj(Const.C_FICHIER_INI_CHAMPMAJ);
		initNomTable(new TaInfosApporteur());
	}
	public TaInfosApporteur refresh(TaInfosApporteur detachedInstance) {
		logger.debug("refresh instance");
		try {
			getEntityManager().refresh(detachedInstance);
//			org.hibernate.Session session = (org.hibernate.Session) getEntityManager().getDelegate();
//		    session.evict(detachedInstance);
//		    detachedInstance = getEntityManager().find(TaInfosApporteur.class, detachedInstance.getIdInfosDocument());
		    return detachedInstance;
			
		} catch (RuntimeException re) {
			logger.error("refresh failed", re);
			throw re;
		}
	}
	public void persist(TaInfosApporteur transientInstance) {
		logger.debug("persisting TaInfosApporteur instance");
		try {
			getEntityManager().persist(transientInstance);
			logger.debug("persist successful");
		} catch (RuntimeException re) {
			logger.error("persist failed", re);
			throw re;
		}
	}

	public void remove(TaInfosApporteur persistentInstance) {
		logger.debug("removing TaInfosApporteur instance");
		try {
			getEntityManager().remove(persistentInstance);
			logger.debug("remove successful");
		} catch (RuntimeException re) {
			logger.error("remove failed", re);
			throw re;
		}
	}

	public TaInfosApporteur merge(TaInfosApporteur detachedInstance) {
		logger.debug("merging TaInfosApporteur instance");
		try {
			TaInfosApporteur result = getEntityManager().merge(detachedInstance);
			logger.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			logger.error("merge failed", re);
			throw re;
		}
	}

	public TaInfosApporteur findById(int id) {
		logger.debug("getting TaInfosApporteur instance with id: " + id);
		try {
			TaInfosApporteur instance = getEntityManager().find(TaInfosApporteur.class,
					id);
			logger.debug("get successful");
			return instance;
		} catch (RuntimeException re) {
			logger.error("get failed", re);
			throw re;
		}
	}
	
	public TaInfosApporteur findByCodeApporteur(String code) {
		logger.debug("getting TaInfosApporteur instance with code Document: " + code);
		try {
			if(!code.equals("")){
				Query query = getEntityManager().createQuery("select a from TaInfosApporteur " +
						"a where a.taDocument.codeDocument='"+code+"'");
				TaInfosApporteur instance = (TaInfosApporteur)query.getSingleResult();
				logger.debug("get successful");
				return instance;
			}
			return null;
		} catch (NoResultException re) {
			return null;
		} catch (RuntimeException re) {
			logger.error("get failed", re);
			throw re;
		}
	}	
	
	/* (non-Javadoc)
	 * @see fr.legrain.articles.dao.ILgrDAO#selectAll()
	 */
	public List<TaInfosApporteur> selectAll() {
		logger.debug("selectAll TaInfosApporteur");
		try {
			Query ejbQuery = getEntityManager().createQuery(JPQLQuery);
			List<TaInfosApporteur> l = ejbQuery.getResultList();
			logger.debug("selectAll successful");
			return l;
		} catch (RuntimeException re) {
			logger.error("selectAll failed", re);
			throw re;
		}
	}

}
