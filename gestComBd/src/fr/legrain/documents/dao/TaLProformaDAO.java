package fr.legrain.documents.dao;

// Generated Apr 7, 2009 3:27:24 PM by Hibernate Tools 3.2.0.CR1

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import fr.legrain.articles.dao.TaPrix;
import fr.legrain.articles.dao.TaUnite;
import fr.legrain.gestCom.Appli.AbstractApplicationDAO;
import fr.legrain.gestCom.Appli.Const;

/**
 * Home object for domain model class TaLDevis.
 * @see fr.legrain.documents.dao.TaLDevis
 * @author Hibernate Tools
 */
public class TaLProformaDAO extends AbstractApplicationDAO<TaLProforma> {

//	private static final Log log = LogFactory.getLog(TaLDevisDAO.class);
	static Logger logger = Logger.getLogger(TaLProformaDAO.class);
	
	private String defaultJPQLQuery = "select a from TaLProforma a";
	
	public TaLProformaDAO(){
		this(null);
	}
	
	public TaLProformaDAO(EntityManager em){
		if(em!=null) {
			setEm(em);
		}
//		champIdTable=ctrlGeneraux.getID_TABLE(TaLProforma.class.getSimpleName());
		initChampId(TaLProforma.class);
		setJPQLQuery(defaultJPQLQuery);
		setListeChampMaj(Const.C_FICHIER_INI_CHAMPMAJ);
		initNomTable(new TaLProforma());
	}
	public TaLProforma refresh(TaLProforma detachedInstance) {
		logger.debug("refresh instance");
		try {
			detachedInstance.setLegrain(false);
        	getEntityManager().refresh(detachedInstance);
//			org.hibernate.Session session = (org.hibernate.Session) getEntityManager().getDelegate();
//		    session.evict(detachedInstance);
//		    detachedInstance = getEntityManager().find(TaLProforma.class, detachedInstance.getIdLDocument());
			detachedInstance.setLegrain(true);
		    return detachedInstance;
			
		} catch (RuntimeException re) {
			logger.error("refresh failed", re);
			throw re;
		}
	}
	public void persist(TaLProforma transientInstance) {
		logger.debug("persisting TaLProforma instance");
		try {
			getEntityManager().persist(transientInstance);
			logger.debug("persist successful");
		} catch (RuntimeException re) {
			logger.error("persist failed", re);
			throw re;
		}
	}

	public void remove(TaLProforma persistentInstance) {
		logger.debug("removing TaLProforma instance");
		try {
			getEntityManager().remove(persistentInstance);
			logger.debug("remove successful");
		} catch (RuntimeException re) {
			logger.error("remove failed", re);
			throw re;
		}
	}

	public TaLProforma merge(TaLProforma detachedInstance) {
		logger.debug("merging TaLProforma instance");
		try {
			TaLProforma result = getEntityManager().merge(detachedInstance);
			logger.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			logger.error("merge failed", re);
			throw re;
		}
	}

	public TaLProforma findById(int id) {
		logger.debug("getting TaLProforma instance with id: " + id);
		try {
			TaLProforma instance = getEntityManager().find(TaLProforma.class, id);
			logger.debug("get successful");
			return instance;
		} catch (RuntimeException re) {
			logger.error("get failed", re);
			throw re;
		}
	}
	
	/* (non-Javadoc)
	 * @see fr.legrain.articles.dao.ILgrDAO#selectAll()
	 */
	public List<TaLProforma> selectAll() {
		logger.debug("selectAll TaLProforma");
		try {
			Query ejbQuery = getEntityManager().createQuery(JPQLQuery);
			List<TaLProforma> l = ejbQuery.getResultList();
			logger.debug("selectAll successful");
			return l;
		} catch (RuntimeException re) {
			logger.error("selectAll failed", re);
			throw re;
		}
	}
}
