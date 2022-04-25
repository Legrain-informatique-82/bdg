package fr.legrain.articles.dao;

// Generated Aug 11, 2008 4:05:28 PM by Hibernate Tools 3.2.1.GA

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import fr.legrain.gestCom.Appli.AbstractApplicationDAO;
import fr.legrain.gestCom.Appli.Const;
import fr.legrain.gestCom.Appli.JPACtrl_Application;
import fr.legrain.lib.data.ExceptLgr;

/**
 * Home object for domain model class TaRefPrix.
 * @see fr.legrain.articles.dao.TaRefPrix
 * @author Hibernate Tools
 */
public class TaRefPrixDAO extends AbstractApplicationDAO<TaRefPrix> {

	private static final Log logger = LogFactory.getLog(TaRefPrixDAO.class);
	//static Logger logger = Logger.getLogger(TaRefPrixDAO.class.getName());

	//@PersistenceContext
	//private EntityManager entityManager = getEntityManager();
	
	private String defaultJPQLQuery = "select rp from TaRefPrix rp";
	
	public TaRefPrixDAO(){
		this(null);
	}
	
	public TaRefPrixDAO(EntityManager em){
		if(em!=null) {
			setEm(em);
		}
//		champIdTable=ctrlGeneraux.getID_TABLE(TaRefPrix.class.getSimpleName());
		initChampId(TaRefPrix.class);
		setJPQLQuery(defaultJPQLQuery);
		setListeChampMaj(Const.C_FICHIER_INI_CHAMPMAJ);
		initNomTable(new TaRefPrix());
	}

	public TaRefPrix refresh(TaRefPrix detachedInstance) {
		logger.debug("refresh instance");
		try {
			org.hibernate.Session session = (org.hibernate.Session) getEntityManager().getDelegate();
		    session.evict(detachedInstance);
		    detachedInstance = getEntityManager().find(TaRefPrix.class, detachedInstance.getIdRefPrix());
		    return detachedInstance;
			
		} catch (RuntimeException re) {
			logger.error("refresh failed", re);
			throw re;
		}
	}
	
	protected void persist(TaRefPrix transientInstance) {
		logger.debug("persisting TaRefPrix instance");
		try {
			getEntityManager().persist(transientInstance);
			logger.debug("persist successful");
		} catch (RuntimeException re) {
			logger.error("persist failed", re);
			throw re;
		}
	}

	protected void remove(TaRefPrix persistentInstance) {
		logger.debug("removing TaRefPrix instance");
		try {
			getEntityManager().remove(persistentInstance);
			logger.debug("remove successful");
		} catch (RuntimeException re) {
			logger.error("remove failed", re);
			throw re;
		}
	}

	protected TaRefPrix merge(TaRefPrix detachedInstance) {
		logger.debug("merging TaRefPrix instance");
		try {
			TaRefPrix result = getEntityManager().merge(detachedInstance);
			logger.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			logger.error("merge failed", re);
			throw re;
		}
	}

	public TaRefPrix findById(int id) {
		logger.debug("getting TaRefPrix instance with id: " + id);
		try {
			TaRefPrix instance = getEntityManager().find(TaRefPrix.class, id);
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
	public List<TaRefPrix> selectAll() {
		logger.debug("selectAll TaArticle");
		try {
			Query ejbQuery = getEntityManager().createQuery(JPQLQuery);
			List<TaRefPrix> l = ejbQuery.getResultList();
			logger.debug("selectAll successful");
			return l;
		} catch (RuntimeException re) {
			logger.error("selectAll failed", re);
			throw re;
		}
	}

	public void ctrlSaisieSpecifique(TaRefPrix entity,String field) throws ExceptLgr {	
		try {
//			if(field.equals(Const.C_CODE_TVA)){
//				if(!entity.getCodeTva().equals("")){
//				}					
//			}			
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
}
