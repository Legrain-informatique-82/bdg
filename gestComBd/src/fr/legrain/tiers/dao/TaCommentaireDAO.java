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
 * Home object for domain model class TaCommentaire.
 * @see fr.legrain.tiers.dao.TaCommentaire
 * @author Hibernate Tools
 */
public class TaCommentaireDAO extends AbstractApplicationDAO<TaCommentaire>{

	//private static final Log logger = LogFactory.getLog(TaCommentaireDAO.class);
	static Logger logger = Logger.getLogger(TaCommentaireDAO.class);

	private String defaultJPQLQuery = "select a from TaCommentaire a";
	
	public TaCommentaireDAO() {
		this(null);
	}

	public TaCommentaireDAO(EntityManager em){
		if(em!=null) {
			setEm(em);
		}
//		champIdTable=ctrlGeneraux.getID_TABLE(TaCommentaire.class.getSimpleName());
		initChampId(TaCommentaire.class);
		setJPQLQuery(defaultJPQLQuery);
		setListeChampMaj(Const.C_FICHIER_INI_CHAMPMAJ);
		initNomTable(new TaCommentaire());
	}
	
	public void persist(TaCommentaire transientInstance) {
		logger.debug("persisting TaCommentaire instance");
		try {
			getEntityManager().persist(transientInstance);
			logger.debug("persist successful");
		} catch (RuntimeException re) {
			logger.error("persist failed", re);
			throw re;
		}
	}
	
	public TaCommentaire refresh(TaCommentaire detachedInstance) {
		logger.debug("refresh instance");
		try {
			org.hibernate.Session session = (org.hibernate.Session) getEntityManager().getDelegate();
		    session.evict(detachedInstance);
		    detachedInstance = getEntityManager().find(TaCommentaire.class, detachedInstance.getIdCommentaire());
		    return detachedInstance;
		} catch (RuntimeException re) {
			logger.error("refresh failed", re);
			throw re;
		}
	}

	public void remove(TaCommentaire persistentInstance) {
		logger.debug("removing TaCommentaire instance");
		try {
			getEntityManager().remove(persistentInstance);
			logger.debug("remove successful");
		} catch (RuntimeException re) {
			logger.error("remove failed", re);
			throw re;
		}
	}

	public TaCommentaire merge(TaCommentaire detachedInstance) {
		logger.debug("merging TaCommentaire instance");
		try {
			TaCommentaire result = getEntityManager().merge(detachedInstance);
			logger.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			logger.error("merge failed", re);
			throw re;
		}
	}

	public TaCommentaire findById(int id) {
		logger.debug("getting TaCommentaire instance with id: " + id);
		try {
			TaCommentaire instance = getEntityManager().find(TaCommentaire.class, id);
			logger.debug("get successful");
			return instance;
		} catch (RuntimeException re) {
			logger.error("get failed", re);
			throw re;
		}
	}

	@Override
	public List<TaCommentaire> selectAll() {
		// TODO Auto-generated method stub
		logger.debug("selectAll TaCommentaire");
		try {
			Query ejbQuery = getEntityManager().createQuery(JPQLQuery);
			List<TaCommentaire> l = ejbQuery.getResultList();
			logger.debug("selectAll successful");
			return l;
		} catch (RuntimeException re) {
			logger.error("selectAll failed", re);
			throw re;
		}
	}
	
	public void ctrlSaisieSpecifique(TaCommentaire entity,String field) throws ExceptLgr {	
		
	}
}
