package fr.legrain.boutique.dao;

// Generated Aug 11, 2008 4:05:28 PM by Hibernate Tools 3.2.1.GA

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.hibernate.validator.ClassValidator;
import org.hibernate.validator.InvalidValue;

import fr.legrain.gestCom.Appli.AbstractApplicationDAO;
import fr.legrain.gestCom.Appli.Const;
import fr.legrain.lib.data.ExceptLgr;
import fr.legrain.tiers.dao.TaAdresse;

/**
 * Home object for domain model class TaUnite.
 * @see fr.legrain.articles.dao.TaUnite
 * @author Hibernate Tools
 */
public class TaCorrespondanceIDBoutiqueDAO extends AbstractApplicationDAO<TaCorrespondanceIDBoutique> {

	private static final Log logger = LogFactory.getLog(TaCorrespondanceIDBoutiqueDAO.class);
	
	public static final String TYPE_ARTICLE = "id_article";
	public static final String TYPE_CATEGORIE = "id_categorie";
	public static final String TYPE_COMMANDE = "id_commandes";
	public static final String TYPE_IMG_ARTICLE = "id_image_art";
	public static final String TYPE_LABEL = "id_label";
	public static final String TYPE_TIERS = "id_tiers";
	public static final String TYPE_TVA = "id_tva";
	
	private String defaultJPQLQuery = "select u from TaCorrespondanceIDBoutique u";
	
	public TaCorrespondanceIDBoutiqueDAO(){
		this(null);
	}
	
	public TaCorrespondanceIDBoutiqueDAO(EntityManager em){
		if(em!=null) {
			setEm(em);
		}
//		champIdTable=ctrlGeneraux.getID_TABLE(TaCorrespondanceIDBoutique.class.getSimpleName());
		initChampId(TaCorrespondanceIDBoutique.class);
		setJPQLQuery(defaultJPQLQuery);
		setListeChampMaj(Const.C_FICHIER_INI_CHAMPMAJ);
		initNomTable(new TaCorrespondanceIDBoutique());
	}
	
	public IStatus validate(TaCorrespondanceIDBoutique entity){
		ClassValidator<TaCorrespondanceIDBoutique> uniteValidator = new ClassValidator<TaCorrespondanceIDBoutique>( TaCorrespondanceIDBoutique.class );
		InvalidValue[] iv = uniteValidator.getInvalidValues(entity);
		IStatus s = null;
		if(iv.length>0) {
			s = new Status(0,"",iv[0].getMessage());
		}
		return s;
	}
	

	/* (non-Javadoc)
	 * @see fr.legrain.articles.dao.ILgrDAO#persist(fr.legrain.articles.dao.TaCorrespondanceIDBoutique)
	 */
	protected void persist(TaCorrespondanceIDBoutique transientInstance) {
		logger.debug("persisting TaCorrespondanceIDBoutique instance");
		try {
			getEntityManager().persist(transientInstance);
			logger.debug("persist successful");
		} catch (RuntimeException re) {
			logger.error("persist failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see fr.legrain.articles.dao.ILgrDAO#remove(fr.legrain.articles.dao.TaCorrespondanceIDBoutique)
	 */
	protected void remove(TaCorrespondanceIDBoutique persistentInstance) {
		logger.debug("removing TaCorrespondanceIDBoutique instance");
		try {
			getEntityManager().remove(persistentInstance);
			logger.debug("remove successful");
		} catch (RuntimeException re) {
			logger.error("remove failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see fr.legrain.articles.dao.ILgrDAO#merge(fr.legrain.articles.dao.TaCorrespondanceIDBoutique)
	 */
	protected TaCorrespondanceIDBoutique merge(TaCorrespondanceIDBoutique detachedInstance) {
		logger.debug("merging TaCorrespondanceIDBoutique instance");
		try {
			TaCorrespondanceIDBoutique result = getEntityManager().merge(detachedInstance);
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
	public TaCorrespondanceIDBoutique findById(int id) {
		logger.debug("getting TaCorrespondanceIDBoutique instance with id: " + id);
		try {
			TaCorrespondanceIDBoutique instance = getEntityManager().find(TaCorrespondanceIDBoutique.class, id);
			logger.debug("get successful");
			return instance;
		} catch (RuntimeException re) {
			logger.error("get failed", re);
			throw re;
		}
	}
	
	public List<TaCorrespondanceIDBoutique> findByTypeTable(String type) {
		logger.debug("selectAll TaCorrespondanceIDBoutique");
		try {
			Query query = getEntityManager().createNamedQuery(TaCorrespondanceIDBoutique.QN.FIND_BY_TYPE_TABLE);
			query.setParameter(1, type);
			List<TaCorrespondanceIDBoutique> l = query.getResultList();
			logger.debug("selectAll successful");
			return l;
		} catch (RuntimeException re) {
			logger.error("selectAll failed", re);
			throw re;
		}
	}
	
	public TaCorrespondanceIDBoutique findByIdBdg(String idBdg) {
		logger.debug("selectAll TaCorrespondanceIDBoutique");
		try {
			Query query = getEntityManager().createNamedQuery(TaCorrespondanceIDBoutique.QN.FIND_BY_ID_BDG);
			query.setParameter(1, idBdg);
			TaCorrespondanceIDBoutique result = (TaCorrespondanceIDBoutique) query.getSingleResult();
			logger.debug("selectAll successful");
			return result;
		} catch (RuntimeException re) {
			logger.error("selectAll failed", re);
			throw re;
		}
	}
	
	public TaCorrespondanceIDBoutique findByIdBoutique(String idBoutique) {
		logger.debug("selectAll TaCorrespondanceIDBoutique");
		try {
			Query query = getEntityManager().createNamedQuery(TaCorrespondanceIDBoutique.QN.FIND_BY_ID_BOUTIQUE);
			query.setParameter(1, idBoutique);
			TaCorrespondanceIDBoutique result = (TaCorrespondanceIDBoutique) query.getSingleResult();
			logger.debug("selectAll successful");
			return result;
		} catch (RuntimeException re) {
			logger.error("selectAll failed", re);
			throw re;
		}
	}
	
	/* (non-Javadoc)
	 * @see fr.legrain.articles.dao.ILgrDAO#selectAll()
	 */
	public List<TaCorrespondanceIDBoutique> selectAll() {
		logger.debug("selectAll TaCorrespondanceIDBoutique");
		try {
			Query ejbQuery = getEntityManager().createQuery(JPQLQuery);
			List<TaCorrespondanceIDBoutique> l = ejbQuery.getResultList();
			logger.debug("selectAll successful");
			return l;
		} catch (RuntimeException re) {
			logger.error("selectAll failed", re);
			throw re;
		}
	}
	
	public TaCorrespondanceIDBoutique refresh(TaCorrespondanceIDBoutique detachedInstance) {
		logger.debug("refresh instance");
		try {
			org.hibernate.Session session = (org.hibernate.Session) getEntityManager().getDelegate();
		    session.evict(detachedInstance);
		    detachedInstance = getEntityManager().find(TaCorrespondanceIDBoutique.class, detachedInstance.getId());
		    return detachedInstance;
			
		} catch (RuntimeException re) {
			logger.error("refresh failed", re);
			throw re;
		}
	}
	
	
	
	public void ctrlSaisieSpecifique(TaCorrespondanceIDBoutique entity,String field) throws ExceptLgr {	
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
