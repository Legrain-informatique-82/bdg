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
 * Home object for domain model class TaConditionnementArticle.
 * @see fr.legrain.articles.dao.TaConditionnementArticle
 * @author Hibernate Tools
 */
public class TaConditionnementArticleDAO extends AbstractApplicationDAO<TaConditionnementArticle> {

	private static final Log logger = LogFactory.getLog(TaConditionnementArticleDAO.class);
	//static Logger logger = Logger.getLogger(TaConditionnementArticleDAO.class.getName());

	//@PersistenceContext
	//private EntityManager entityManager = getEntityManager();
	
	private String defaultJPQLQuery = "select t from TaConditionnementArticle t";
	
	public TaConditionnementArticleDAO(){
		this(null);
	}
	
	public TaConditionnementArticleDAO(EntityManager em){
		if(em!=null) {
			setEm(em);
		}
//		champIdTable=ctrlGeneraux.getID_TABLE(TaConditionnementArticle.class.getSimpleName());
		initChampId(TaConditionnementArticle.class);
		setJPQLQuery(defaultJPQLQuery);
		setListeChampMaj(Const.C_FICHIER_INI_CHAMPMAJ);
		initNomTable(new TaConditionnementArticle());
	}
	public TaConditionnementArticle refresh(TaConditionnementArticle detachedInstance) {
		logger.debug("refresh instance");
		try {
			org.hibernate.Session session = (org.hibernate.Session) getEntityManager().getDelegate();
		    session.evict(detachedInstance);
		    detachedInstance = getEntityManager().find(TaConditionnementArticle.class, detachedInstance.getIdConditionnementArticle());
		    return detachedInstance;
			
		} catch (RuntimeException re) {
			logger.error("refresh failed", re);
			throw re;
		}
	}

	protected void persist(TaConditionnementArticle transientInstance) {
		logger.debug("persisting TaConditionnementArticle instance");
		try {
			getEntityManager().persist(transientInstance);
			logger.debug("persist successful");
		} catch (RuntimeException re) {
			logger.error("persist failed", re);
			throw re;
		}
	}

	protected void remove(TaConditionnementArticle persistentInstance) {
		logger.debug("removing TaConditionnementArticle instance");
		try {
			getEntityManager().remove(persistentInstance);
			logger.debug("remove successful");
		} catch (RuntimeException re) {
			logger.error("remove failed", re);
			throw re;
		}
	}

	protected TaConditionnementArticle merge(TaConditionnementArticle detachedInstance) {
		logger.debug("merging TaConditionnementArticle instance");
		try {
			TaConditionnementArticle result = getEntityManager().merge(detachedInstance);
			logger.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			logger.error("merge failed", re);
			throw re;
		}
	}

	public TaConditionnementArticle findById(int id) {
		logger.debug("getting TaConditionnementArticle instance with id: " + id);
		try {
			TaConditionnementArticle instance = getEntityManager().find(TaConditionnementArticle.class, id);
			logger.debug("get successful");
			return instance;
		} catch (RuntimeException re) {
			logger.error("get failed", re);
			throw re;
		}
	}
	
	public TaConditionnementArticle findByCode(String code) {
		logger.debug("getting TaConditionnementArticle instance with code: " + code);
		try {
			if(!code.equals("")){
			Query query = getEntityManager().createQuery("select f from TaConditionnementArticle f where f.codeTTva='"+code+"'");
			TaConditionnementArticle instance = (TaConditionnementArticle)query.getSingleResult();
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
	public List<TaConditionnementArticle> selectAll() {
		logger.debug("selectAll TaConditionnementArticle");
		try {
			Query ejbQuery = getEntityManager().createQuery(JPQLQuery);
			List<TaConditionnementArticle> l = ejbQuery.getResultList();
			logger.debug("selectAll successful");
			return l;
		} catch (RuntimeException re) {
			logger.error("selectAll failed", re);
			throw re;
		}
	}
	
	public void ctrlSaisieSpecifique(TaConditionnementArticle entity,String field) throws ExceptLgr {	
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
