package fr.legrain.documents.dao;

// Generated Mar 24, 2009 4:44:04 PM by Hibernate Tools 3.2.0.CR1

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TemporalType;

import org.apache.log4j.Logger;

import fr.legrain.articles.dao.TaPrix;
import fr.legrain.gestCom.Appli.AbstractApplicationDAO;
import fr.legrain.gestCom.Appli.Const;
import fr.legrain.gestCom.Module_Document.IDocumentTiers;
import fr.legrain.lib.data.ExceptLgr;

/**
 * Home object for domain model class TaTLiens.
 * @see fr.legrain.tiers.dao.TaTLiens
 * @author Hibernate Tools
 */
public class TaRemiseDAO extends AbstractApplicationDAO<TaRemise>implements IDocumentTiersDAO<TaRemise>{

	//private static final Log logger = LogFactory.getLog(TaTLiensDAO.class);
	static Logger logger = Logger.getLogger(TaRemiseDAO.class);

	private String defaultJPQLQuery = "select a from TaRemise a ";
	
	public TaRemiseDAO() {
		this(null);
	}
	
	public TaRemiseDAO(EntityManager em){
		if(em!=null) {
			setEm(em);
		}
//		champIdTable=ctrlGeneraux.getID_TABLE(TaRemise.class.getSimpleName());
		initChampId(TaRemise.class);
		setJPQLQuery(defaultJPQLQuery);
		setListeChampMaj(Const.C_FICHIER_INI_CHAMPMAJ);
		initNomTable(new TaRemise());
	}
	public TaRemise refresh(TaRemise detachedInstance) {
		logger.debug("refresh instance");
		try {
			getEntityManager().refresh(detachedInstance);
//			org.hibernate.Session session = (org.hibernate.Session) getEntityManager().getDelegate();
//		    session.evict(detachedInstance);
//		    detachedInstance = getEntityManager().find(TaRemise.class, detachedInstance.getIdRemise());
		    return detachedInstance;
		} catch (RuntimeException re) {
			logger.error("refresh failed", re);
			throw re;
		}
	}
	public void persist(TaRemise transientInstance) {
		logger.debug("persisting TaRemise instance");
		try {
			getEntityManager().persist(transientInstance);
			logger.debug("persist successful");
		} catch (RuntimeException re) {
			logger.error("persist failed", re);
			throw re;
		}
	}

	public void remove(TaRemise persistentInstance) {
		logger.debug("removing TaRemise instance");
		try {
			getEntityManager().remove(persistentInstance);
			logger.debug("remove successful");
		} catch (RuntimeException re) {
			logger.error("remove failed", re);
			throw re;
		}
	}

	public TaRemise merge(TaRemise detachedInstance) {
		logger.debug("merging TaRemise instance");
		try {
			TaRemise result = getEntityManager().merge(detachedInstance);
			logger.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			logger.error("merge failed", re);
			throw re;
		}
	}

	public TaRemise findById(int id) {
		logger.debug("getting TaRemise instance with id: " + id);
		try {
			TaRemise instance = getEntityManager().find(TaRemise.class, id);
			logger.debug("get successful");
			return instance;
		} catch (RuntimeException re) {
			logger.error("get failed", re);
			throw re;
		}
	}
	
	public TaRemise findByCode(String code) {
		logger.debug("getting TaRemise instance with code: " + code);
		try {
			if(!code.equals("")){
			Query query = getEntityManager().createQuery("select f from TaRemise f where f.codeDocument='"+code+"'");
			TaRemise instance = (TaRemise)query.getSingleResult();
			logger.debug("get successful");
			return instance;
			}
			return null;
		} catch (RuntimeException re) {
			logger.error("get failed", re);
			throw re;
		}
	}	
	@Override
	public List<TaRemise> selectAll() {
		// TODO Auto-generated method stub
		logger.debug("selectAll TaRemise");
		try {  
			Query ejbQuery = getEntityManager().createQuery(JPQLQuery);
			List<TaRemise> l = ejbQuery.getResultList();
			logger.debug("selectAll successful");
			return l;
		} catch (RuntimeException re) {
			logger.error("selectAll failed", re);
			throw re;
		}
	}

	/**
	 * Recherche les remises entre 2 dates
	 * @param dateDeb - date de début
	 * @param dateFin - date de fin
	 * @return String[] - tableau contenant les IDs des remises entre ces 2 dates (null en cas d'erreur)
	 */
	public List<TaRemise> rechercheDocument(Date dateDeb, Date dateFin) {
		return rechercheDocument(dateDeb, dateFin, null, null);
	}
	
	/**
	 * Recherche les remises entre 2 dates ordonnées par date
	 * @param dateDeb - date de début
	 * @param dateFin - date de fin
	 * @return String[] - tableau contenant les IDs des remises entre ces 2 dates (null en cas d'erreur)
	 */
	public List<TaRemise> rechercheDocumentOrderByDate(Date dateDeb, Date dateFin) {
		List<TaRemise> result = null;
		Query query = getEntityManager().createNamedQuery(TaRemise.QN.FIND_BY_DATE_PARDATE);
		query.setParameter(1, dateDeb, TemporalType.DATE);
		query.setParameter(2, dateFin, TemporalType.DATE);
		result = query.getResultList();		
		return result;		
	}
	
	/**
	 * Recherche les remises entre 2 codes remises
	 * @param codeDeb - code de début
	 * @param codeFin - code de fin
	 * @return String[] - tableau contenant les IDs des remises entre ces 2 codes (null en cas d'erreur)
	 */
	public List<TaRemise> rechercheDocument(String codeDeb, String codeFin) {
		return rechercheDocument(codeDeb, codeFin, null, null);
	}
	
	/**
	 * Recherche les remises entre 2 dates et non exportees
	 * @param dateDeb - date de début
	 * @param dateFin - date de fin
	 * @return String[] - tableau contenant les IDs des remises entre ces 2 dates (null en cas d'erreur)
	 */
	public List<TaRemise> rechercheDocumentNonExporte(Date dateDeb, Date dateFin,boolean parDate) {
		List<TaRemise> result = null;
		Query query = null;
		if(parDate)query=getEntityManager().createNamedQuery(TaRemise.QN.FIND_BY_DATE_NON_EXPORT_PARDATE);
		else query=getEntityManager().createNamedQuery(TaRemise.QN.FIND_BY_DATE_NON_EXPORT);
		query.setParameter(1, dateDeb, TemporalType.DATE);
		query.setParameter(2, dateFin, TemporalType.DATE);
		result = query.getResultList();
		
		
		return result;
		
	}
	
	public List<TaRemise> findSiReglementDansRemise(String code) {
		try {
			Query query = getEntityManager().createQuery("select a.taDocument from TaLRemise a where a.taReglement.codeDocument like ?");
			query.setParameter(1, code);
			return query.getResultList();
			 
		} catch (RuntimeException re) {
			throw re;
		} catch (Exception e) {
			return null;
		}
	}
	
	public List<TaRemise> findSiAcompteDansRemise(String code) {
		try {
			Query query = getEntityManager().createQuery("select a.taDocument from TaLRemise a where a.taAcompte.codeDocument like ?");
			query.setParameter(1, code);
			return query.getResultList();
		} catch (RuntimeException re) {
			throw re;
		} catch (Exception e) {
			return null;
		}
	}
	
	public void ctrlSaisieSpecifique(TaRemise entity,String field) throws ExceptLgr {	
		
	}

	@Override
	public List<TaRemise> rechercheDocument(String codeDeb, String codeFin,
			String codeTiers) {
		return rechercheDocument(codeDeb,codeFin);
	}

	@Override
	public List<TaRemise> rechercheDocument(Date dateDeb, Date dateFin,
			String codeTiers) {
		return rechercheDocument(dateDeb,dateFin);
	}

	@Override
	public List<TaRemise> rechercheDocument(Date dateDeb, Date dateFin,
			String codeTiers, Boolean export) {
		// TODO Auto-generated method stub
		List<TaRemise> result = null;
		Query query = null;
		if(export!=null && export)query = getEntityManager().createNamedQuery(TaRemise.QN.FIND_BY_DATE_EXPORT);
		else if(export!=null && export==false)query = getEntityManager().createNamedQuery(TaRemise.QN.FIND_BY_DATE_NON_EXPORT);
		else
			query = getEntityManager().createNamedQuery(TaRemise.QN.FIND_BY_DATE);	
		query.setParameter(1, dateDeb, TemporalType.DATE);
		query.setParameter(2, dateFin, TemporalType.DATE);
		result = query.getResultList();		
		return result;
	}

	@Override
	public List<TaRemise> rechercheDocument(String codeDeb, String codeFin,
			String codeTiers, Boolean export) {
		List<TaRemise> result = null;
		Query query = null;
		if(export!=null && export)query = getEntityManager().createNamedQuery(TaRemise.QN.FIND_BY_CODE_EXPORT);
		else if(export!=null && export==false)query = getEntityManager().createNamedQuery(TaRemise.QN.FIND_BY_CODE_NON_EXPORT);
		else
		query = getEntityManager().createNamedQuery(TaRemise.QN.FIND_BY_CODE);
		query.setParameter(1, codeDeb);
		query.setParameter(2, codeFin);
		result = query.getResultList();
		return result;
	}

	@Override
	public List<Object[]> rechercheDocument(Date dateDeb, Date dateFin,
			Boolean light) {
		// TODO Stub de la méthode généré automatiquement
		return null;
	}

	@Override
	public List<Object[]> rechercheDocumentLight(Date dateDeb, Date dateFin,
			String codeTiers) {
		// TODO Stub de la méthode généré automatiquement
		return null;
	}

	@Override
	public List<Object[]> rechercheDocumentLight(String codeDoc, String codeTiers) {
		// TODO Stub de la méthode généré automatiquement
		return null;
	}


}
