package fr.legrain.articles.dao;

// Generated Aug 11, 2008 4:05:28 PM by Hibernate Tools 3.2.1.GA

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.Table;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

import fr.legrain.gestCom.Appli.AbstractApplicationDAO;
import fr.legrain.gestCom.Appli.Const;
import fr.legrain.gestCom.Appli.JPACtrl_Application;
import fr.legrain.gestCom.Appli.LgrHibernateValidated;
import fr.legrain.gestCom.gestComBd.gestComBdPlugin;
import fr.legrain.lib.data.ExceptLgr;
import fr.legrain.lib.data.MessCtrlLgr;

/**
 * Home object for domain model class TaTConditionnement.
 * @see fr.legrain.articles.dao.TaTConditionnement
 * @author Hibernate Tools
 */
public class TaTConditionnementDAO extends AbstractApplicationDAO<TaTConditionnement> {

	private static final Log logger = LogFactory.getLog(TaTConditionnementDAO.class);
	//static Logger logger = Logger.getLogger(TaTConditionnementDAO.class.getName());

	//@PersistenceContext
	//private EntityManager entityManager = getEntityManager();
	
	private String defaultJPQLQuery = "select t from TaTConditionnement t";
	
	public TaTConditionnementDAO(){
		this(null);
	}
	
	public TaTConditionnementDAO(EntityManager em){
		if(em!=null) {
			setEm(em);
		}
//		champIdTable=ctrlGeneraux.getID_TABLE(TaTConditionnement.class.getSimpleName());
		initChampId(TaTConditionnement.class);
		setJPQLQuery(defaultJPQLQuery);
		setListeChampMaj(Const.C_FICHIER_INI_CHAMPMAJ);
		initNomTable(new TaTConditionnement());
	}
	public TaTConditionnement refresh(TaTConditionnement detachedInstance) {
		logger.debug("refresh instance");
		try {
			org.hibernate.Session session = (org.hibernate.Session) getEntityManager().getDelegate();
		    session.evict(detachedInstance);
		    detachedInstance = getEntityManager().find(TaTConditionnement.class, detachedInstance.getId());
		    return detachedInstance;
			
		} catch (RuntimeException re) {
			logger.error("refresh failed", re);
			throw re;
		}
	}

	protected void persist(TaTConditionnement transientInstance) {
		logger.debug("persisting TaTConditionnement instance");
		try {
			getEntityManager().persist(transientInstance);
			logger.debug("persist successful");
		} catch (RuntimeException re) {
			logger.error("persist failed", re);
			throw re;
		}
	}

	protected void remove(TaTConditionnement persistentInstance) {
		logger.debug("removing TaTConditionnement instance");
		try {
			getEntityManager().remove(persistentInstance);
			logger.debug("remove successful");
		} catch (RuntimeException re) {
			logger.error("remove failed", re);
			throw re;
		}
	}

	protected TaTConditionnement merge(TaTConditionnement detachedInstance) {
		logger.debug("merging TaTConditionnement instance");
		try {
			TaTConditionnement result = getEntityManager().merge(detachedInstance);
			logger.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			logger.error("merge failed", re);
			throw re;
		}
	}

	public TaTConditionnement findById(int id) {
		logger.debug("getting TaTConditionnement instance with id: " + id);
		try {
			TaTConditionnement instance = getEntityManager().find(TaTConditionnement.class, id);
			logger.debug("get successful");
			return instance;
		} catch (RuntimeException re) {
			logger.error("get failed", re);
			throw re;
		}
	}
	
	public TaTConditionnement findByCode(String code) {
		logger.debug("getting TaTConditionnement instance with code: " + code);
		try {
			if(!code.equals("")){
			Query query = getEntityManager().createQuery("select f from TaTConditionnement f where f.codeTTva='"+code+"'");
			TaTConditionnement instance = (TaTConditionnement)query.getSingleResult();
			logger.debug("get successful");
			return instance;
			}
			return null;
		} catch (RuntimeException re) {
			logger.error("get failed", re);
			throw re;
		}
	}
	
	/* (non-Javadoc)
	 * @see fr.legrain.articles.dao.ILgrDAO#selectAll()
	 */
	public List<TaTConditionnement> selectAll() {
		logger.debug("selectAll TaArticle");
		try {
			Query ejbQuery = getEntityManager().createQuery(JPQLQuery);
			List<TaTConditionnement> l = ejbQuery.getResultList();
			logger.debug("selectAll successful");
			return l;
		} catch (RuntimeException re) {
			logger.error("selectAll failed", re);
			throw re;
		}
	}
	
	public void ctrlSaisieSpecifique(TaTConditionnement entity,String field) throws ExceptLgr {	
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
