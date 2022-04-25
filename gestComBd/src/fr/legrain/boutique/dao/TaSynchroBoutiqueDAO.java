package fr.legrain.boutique.dao;

// Generated Aug 11, 2008 4:05:28 PM by Hibernate Tools 3.2.1.GA

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.hibernate.validator.ClassValidator;
import org.hibernate.validator.InvalidValue;

import fr.legrain.dossier.dao.TaInfoEntreprise;
import fr.legrain.gestCom.Appli.AbstractApplicationDAO;
import fr.legrain.gestCom.Appli.Const;
import fr.legrain.lib.data.ExceptLgr;

/**
 * Home object for domain model class TaUnite.
 * @see fr.legrain.articles.dao.TaUnite
 * @author Hibernate Tools
 */
public class TaSynchroBoutiqueDAO extends AbstractApplicationDAO<TaSynchroBoutique> {

	private static final Log logger = LogFactory.getLog(TaSynchroBoutiqueDAO.class);
	
	private String defaultJPQLQuery = "select u from TaSynchroBoutique u";
	
	public TaSynchroBoutiqueDAO(){
		this(null);
	}
	
	public TaSynchroBoutiqueDAO(EntityManager em){
		if(em!=null) {
			setEm(em);
		}
//		champIdTable=ctrlGeneraux.getID_TABLE(TaSynchroBoutique.class.getSimpleName());
		initChampId(TaSynchroBoutique.class);
		setJPQLQuery(defaultJPQLQuery);
		setListeChampMaj(Const.C_FICHIER_INI_CHAMPMAJ);
		initNomTable(new TaSynchroBoutique());
	}
	
	public IStatus validate(TaSynchroBoutique entity){
		ClassValidator<TaSynchroBoutique> uniteValidator = new ClassValidator<TaSynchroBoutique>( TaSynchroBoutique.class );
		InvalidValue[] iv = uniteValidator.getInvalidValues(entity);
		IStatus s = null;
		if(iv.length>0) {
			s = new Status(0,"",iv[0].getMessage());
		}
		return s;
	}
	

	/* (non-Javadoc)
	 * @see fr.legrain.articles.dao.ILgrDAO#persist(fr.legrain.articles.dao.TaSynchroBoutique)
	 */
	protected void persist(TaSynchroBoutique transientInstance) {
		logger.debug("persisting TaSynchroBoutique instance");
		try {
			getEntityManager().persist(transientInstance);
			logger.debug("persist successful");
		} catch (RuntimeException re) {
			logger.error("persist failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see fr.legrain.articles.dao.ILgrDAO#remove(fr.legrain.articles.dao.TaSynchroBoutique)
	 */
	protected void remove(TaSynchroBoutique persistentInstance) {
		logger.debug("removing TaSynchroBoutique instance");
		try {
			getEntityManager().remove(persistentInstance);
			logger.debug("remove successful");
		} catch (RuntimeException re) {
			logger.error("remove failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see fr.legrain.articles.dao.ILgrDAO#merge(fr.legrain.articles.dao.TaSynchroBoutique)
	 */
	protected TaSynchroBoutique merge(TaSynchroBoutique detachedInstance) {
		logger.debug("merging TaSynchroBoutique instance");
		try {
			TaSynchroBoutique result = getEntityManager().merge(detachedInstance);
			logger.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			logger.error("merge failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see fr.legrain.articles.dao.ILgrDAO#findById(int)
	 */
	public TaSynchroBoutique findById(int id) {
		logger.debug("getting TaSynchroBoutique instance with id: " + id);
		try {
			TaSynchroBoutique instance = getEntityManager().find(TaSynchroBoutique.class, id);
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
	public List<TaSynchroBoutique> selectAll() {
		logger.debug("selectAll TaSynchroBoutique");
		try {
			Query ejbQuery = getEntityManager().createQuery(JPQLQuery);
			List<TaSynchroBoutique> l = ejbQuery.getResultList();
			logger.debug("selectAll successful");
			return l;
		} catch (RuntimeException re) {
			logger.error("selectAll failed", re);
			throw re;
		}
	}
	
	public TaSynchroBoutique refresh(TaSynchroBoutique detachedInstance) {
		logger.debug("refresh instance");
		try {
			org.hibernate.Session session = (org.hibernate.Session) getEntityManager().getDelegate();
		    session.evict(detachedInstance);
		    detachedInstance = getEntityManager().find(TaSynchroBoutique.class, detachedInstance.getId());
		    return detachedInstance;
			
		} catch (RuntimeException re) {
			logger.error("refresh failed", re);
			throw re;
		}
	}
	
	/**
	 * @return - l'unique instance de taSynchroBoutique si elle existe, sinon retourne null.
	 */
	public TaSynchroBoutique findInstance() {
		int premierId = 1;
		logger.debug("getting TaSynchroBoutique instance with id: "+premierId);
		try {
			TaSynchroBoutique instance = findById(premierId);
			if(instance == null) {
				logger.debug("Aucun objet TaSynchroBoutique trouve, creation d'une nouvelle instance vide");
				instance = new TaSynchroBoutique();
				instance.setId(premierId);
			}
			logger.debug("get successful");
			return instance;
		} catch (RuntimeException re) {
			logger.error("get failed", re);
			throw re;
		}
	}
	
	/**
	 * 
	 * @return vrai s'il y a déjà eu au moins une synchronisation entre les articles du bdg et la boutique.
	 */
	public boolean dejaSynchro() {
		return findInstance().getDerniereSynchro()!=null;
	}
	
	public void ctrlSaisieSpecifique(TaSynchroBoutique entity,String field) throws ExceptLgr {	
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
