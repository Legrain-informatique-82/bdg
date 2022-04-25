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
import org.hibernate.validator.ClassValidator;
import org.hibernate.validator.InvalidValue;

import fr.legrain.gestCom.Appli.AbstractApplicationDAO;
import fr.legrain.gestCom.Appli.Const;
import fr.legrain.gestCom.Appli.JPACtrl_Application;
import fr.legrain.gestCom.Appli.LgrHibernateValidated;
import fr.legrain.gestCom.Appli.ModelGeneralObjet;
import fr.legrain.gestCom.Module_Articles.SWTUnite;
import fr.legrain.gestCom.gestComBd.gestComBdPlugin;
import fr.legrain.lib.data.ExceptLgr;
import fr.legrain.lib.data.MessCtrlLgr;

/**
 * Home object for domain model class TaUnite.
 * @see fr.legrain.articles.dao.TaUnite
 * @author Hibernate Tools
 */
public class TaUniteDAO extends AbstractApplicationDAO<TaUnite> {

	private static final Log logger = LogFactory.getLog(TaUniteDAO.class);
	//static Logger logger = Logger.getLogger(TaUniteDAO.class.getName());

	//@PersistenceContext
	//private EntityManager entityManager = getEntityManager();

	//	protected ClassValidator<TaUnite> uniteValidator = new ClassValidator<TaUnite>( TaUnite.class );
	
	private String defaultJPQLQuery = "select u from TaUnite u";
	//private String defaultJPQLQuery = "select u from TaUnite u where u.taArticle is null";
	
	public TaUniteDAO(){
		this(null);
	}
	
	public TaUniteDAO(EntityManager em){
		if(em!=null) {
			setEm(em);
		}
//		champIdTable=ctrlGeneraux.getID_TABLE(TaUnite.class.getSimpleName());
		initChampId(TaUnite.class);
		setJPQLQuery(defaultJPQLQuery);
		setListeChampMaj(Const.C_FICHIER_INI_CHAMPMAJ);
		initNomTable(new TaUnite());
	}
	
	public IStatus validate(TaUnite entity){
		ClassValidator<TaUnite> uniteValidator = new ClassValidator<TaUnite>( TaUnite.class );
		InvalidValue[] iv = uniteValidator.getInvalidValues(entity);
		IStatus s = null;
		if(iv.length>0) {
			s = new Status(0,"",iv[0].getMessage());
		}
		return s;
	}
	

	/* (non-Javadoc)
	 * @see fr.legrain.articles.dao.ILgrDAO#persist(fr.legrain.articles.dao.TaUnite)
	 */
	protected void persist(TaUnite transientInstance) {
		logger.debug("persisting TaUnite instance");
		try {
			getEntityManager().persist(transientInstance);
			logger.debug("persist successful");
		} catch (RuntimeException re) {
			logger.error("persist failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see fr.legrain.articles.dao.ILgrDAO#remove(fr.legrain.articles.dao.TaUnite)
	 */
	protected void remove(TaUnite persistentInstance) {
		logger.debug("removing TaUnite instance");
		try {
			getEntityManager().remove(persistentInstance);
			logger.debug("remove successful");
		} catch (RuntimeException re) {
			logger.error("remove failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see fr.legrain.articles.dao.ILgrDAO#merge(fr.legrain.articles.dao.TaUnite)
	 */
	protected TaUnite merge(TaUnite detachedInstance) {
		logger.debug("merging TaUnite instance");
		try {
			TaUnite result = getEntityManager().merge(detachedInstance);
			logger.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			logger.error("merge failed", re);
			throw re;
		}
	}
	
	public TaUnite findByCode(String code) {
		logger.debug("getting TaUnite instance with code: " + code);
		try {
			if(!code.equals("")){
			Query query = getEntityManager().createQuery("select f from TaUnite f where f.codeUnite='"+code+"' and f.taArticle is null");
			TaUnite instance = (TaUnite)query.getSingleResult();
			logger.debug("get successful");
			return instance;
			}
			return null;
		} catch (RuntimeException re) {
			logger.error("get failed", re);
			throw re;
		}
	}
	
	public TaUnite findByCode(String code, String codeArticle) {
		logger.debug("getting TaUnite instance with code: " + code);
		try {
			if(!code.equals("")){
			Query query = getEntityManager().createQuery("select f from TaUnite f where f.codeUnite='"+code+"' and f.taArticle.codeArticle='"+codeArticle+"'");
			TaUnite instance = (TaUnite)query.getSingleResult();
			logger.debug("get successful");
			return instance;
			}
			return null;
		} catch (RuntimeException re) {
			logger.error("get failed", re);
			throw re;
		}
	}

	public Boolean exist(String code) {
		logger.debug("exist with code: " + code);
		try {
			if(code!=null && !code.equals("")){
			Query query = getEntityManager().createQuery("select count(f) from TaUnite f where f.codeUnite='"+code+"'");
			Long instance = (Long)query.getSingleResult();
			logger.debug("get successful");
			return instance!=0;
			}
			return false;
		} catch (RuntimeException re) {
			logger.error("get failed", re);
			throw re;
		}
	}

	
	
	/* (non-Javadoc)
	 * @see fr.legrain.articles.dao.ILgrDAO#findById(int)
	 */
	public TaUnite findById(int id) {
		logger.debug("getting TaUnite instance with id: " + id);
		try {
			TaUnite instance = getEntityManager().find(TaUnite.class, id);
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
	public List<TaUnite> selectAll() {
		logger.debug("selectAll TaUnite");
		try {
			Query ejbQuery = getEntityManager().createQuery(JPQLQuery);
			List<TaUnite> l = ejbQuery.getResultList();
			logger.debug("selectAll successful");
			return l;
		} catch (RuntimeException re) {
			logger.error("selectAll failed", re);
			throw re;
		}
	}
	
	public TaUnite refresh(TaUnite detachedInstance) {
		logger.debug("refresh instance");
		try {
			org.hibernate.Session session = (org.hibernate.Session) getEntityManager().getDelegate();
		    session.evict(detachedInstance);
		    detachedInstance = getEntityManager().find(TaUnite.class, detachedInstance.getIdUnite());
		    return detachedInstance;
			
		} catch (RuntimeException re) {
			logger.error("refresh failed", re);
			throw re;
		}
	}
	
	public List<TaUnite> findByArticle(String codeArticle) {
		logger.debug("getting TaUnite instance with codeArticle: " + codeArticle);
		try {
			Query query = getEntityManager().createNamedQuery(TaUnite.QN.FIND_BY_ARTICLE);
			query.setParameter(1, codeArticle);
			List<TaUnite> l = query.getResultList();;
			logger.debug("get successful");
			return l;

		} catch (RuntimeException re) {
			logger.error("get failed", re);
			throw re;
		}
	}
	
	public ModelGeneralObjet<SWTUnite,TaUniteDAO,TaUnite> modelObjetUniteArticle(String codeArticle) {
		return new ModelGeneralObjet<SWTUnite,TaUniteDAO,TaUnite>(findByArticle(codeArticle),SWTUnite.class,getEntityManager());
	}
	
	public void ctrlSaisieSpecifique(TaUnite entity,String field) throws ExceptLgr {	
		try {
//			if(field.equals(Const.C_CODE_TVA)){
//				if(!entity.getCodeTva().equals("")){
//				}					
//			}			
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
//	public void ctrlSaisieSpecifique(ValeurChamps valeurChamps) throws ExceptLgr {	
//		try{
//			switch (this.getModeObjet().getMode()) {
//            case C_MO_CONSULTATION: break;
//            
//            case C_MO_IMPORTATION:            				
//            case C_MO_INSERTION:
//            	TCtrlGeneraux.ctrlSaisie(ex);
//				break;            	
//            case C_MO_EDITION:	
//            	TCtrlGeneraux.ctrlSaisie(ex);
//				break;
//            case C_MO_SUPPRESSION: 
//            	TCtrlGeneraux.ctrlSaisie(ex);
//				break;
//			default:				
//				break;
//			}
//		}catch (Exception e){
//			logger.error("Erreur : ctrlSaisieSpecifique", e);
//			throw new ExceptLgr(e.getMessage());
//		}
//	}

}
