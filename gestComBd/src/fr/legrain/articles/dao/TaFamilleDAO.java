package fr.legrain.articles.dao;

// Generated Aug 11, 2008 4:57:14 PM by Hibernate Tools 3.2.1.GA

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

import sun.security.util.BigInt;

import fr.legrain.gestCom.Appli.AbstractApplicationDAO;
import fr.legrain.gestCom.Appli.Const;
import fr.legrain.gestCom.Appli.JPACtrl_Application;
import fr.legrain.gestCom.Appli.LgrHibernateValidated;
import fr.legrain.gestCom.gestComBd.gestComBdPlugin;
import fr.legrain.lib.data.ExceptLgr;
import fr.legrain.lib.data.LibConversion;
import fr.legrain.lib.data.MessCtrlLgr;

/**
 * Home object for domain model class TaFamille.
 * @see fr.legrain.articles.dao.TaFamille
 * @author Hibernate Tools
 */
public class TaFamilleDAO extends AbstractApplicationDAO<TaFamille> {

	private static final Log logger = LogFactory.getLog(TaFamilleDAO.class);
	//static Logger logger = Logger.getLogger(TaFamilleDAO.class.getName());

	//@PersistenceContext
	//private EntityManager entityManager = getEntityManager();
	
	private String defaultJPQLQuery = "select f from TaFamille f";
	
	public TaFamilleDAO(){
		this(null);
	}
	
	public TaFamilleDAO(EntityManager em){
		if(em!=null) {
			setEm(em);
		}
		initChampId(TaFamille.class);
		setJPQLQuery(defaultJPQLQuery);
		setListeChampMaj(Const.C_FICHIER_INI_CHAMPMAJ);
		initNomTable(new TaFamille());
	}
	
	public TaFamille refresh(TaFamille detachedInstance) {
		logger.debug("refresh instance");
		try {
			org.hibernate.Session session = (org.hibernate.Session) getEntityManager().getDelegate();
		    session.evict(detachedInstance);
		    detachedInstance = getEntityManager().find(TaFamille.class, detachedInstance.getIdFamille());
		    return detachedInstance;
				//getEntityManager().refresh(detachedInstance);
			//logger.debug("refresh successful");
		} catch (RuntimeException re) {
			logger.error("refresh failed", re);
			throw re;
		}
	}


	protected void persist(TaFamille transientInstance) {
		logger.debug("persisting TaFamille instance");
		try {
			getEntityManager().persist(transientInstance);
			logger.debug("persist successful");
		} catch (RuntimeException re) {
			logger.error("persist failed", re);
			throw re;
		}
	}

	protected void remove(TaFamille persistentInstance) {
		logger.debug("removing TaFamille instance");
		try {
			getEntityManager().remove(persistentInstance);
			logger.debug("remove successful");
		} catch (RuntimeException re) {
			logger.error("remove failed", re);
			throw re;
		}
	}

	protected TaFamille merge(TaFamille detachedInstance) {
		logger.debug("merging TaFamille instance");
		try {
			TaFamille result = getEntityManager().merge(detachedInstance);
			logger.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			logger.error("merge failed", re);
			throw re;
		}
	}
	
	public TaFamille findByCode(String code) {
		logger.debug("getting TaFamille instance with code: " + code);
		try {
			if(!code.equals("")){
			Query query = getEntityManager().createQuery("select f from TaFamille f where f.codeFamille='"+code+"'");
			TaFamille instance = (TaFamille)query.getSingleResult();
			logger.debug("get successful");
			return instance;
			}
			return null;
		} catch (RuntimeException re) {
			logger.error("get failed", re);
			throw re;
		}
	}

	public void razFamille() {
		try {
			Query query = getEntityManager().createQuery("delete from TaFamille ");
			query.executeUpdate();
		} catch (RuntimeException re) {
			logger.error("get failed", re);
			throw re;
		}
	}

	
	public Boolean exist(String code) {
		logger.debug("exist with code: " + code);
		try {
			if(code!=null && !code.equals("")){
			Query query = getEntityManager().createQuery("select count(f) from TaFamille f where f.codeFamille='"+code+"'");
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
	
	public TaFamille findById(int id) {
		logger.debug("getting TaFamille instance with id: " + id);
		try {
			TaFamille instance = getEntityManager().find(TaFamille.class, id);
			logger.debug("get successful");
			return instance;
		} catch (RuntimeException re) {
			logger.error("get failed", re);
			throw re;
		}
	}
	
	public List<TaFamille> selectAll() {
		logger.debug("selectAll TaFamille");
		try {
			Query ejbQuery = getEntityManager().createQuery(JPQLQuery);
			List<TaFamille> l = ejbQuery.getResultList();
			logger.debug("selectAll successful");
			return l;
		} catch (RuntimeException re) {
			logger.error("selectAll failed", re);
			throw re;
		}
	}

	public void ctrlSaisieSpecifique(TaFamille entity,String field) throws ExceptLgr {	
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
