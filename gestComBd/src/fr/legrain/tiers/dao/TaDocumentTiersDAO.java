package fr.legrain.tiers.dao;

// Generated Mar 24, 2009 4:44:04 PM by Hibernate Tools 3.2.0.CR1

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import fr.legrain.articles.dao.TaPrix;
import fr.legrain.gestCom.Appli.AbstractApplicationDAO;
import fr.legrain.gestCom.Appli.Const;
import fr.legrain.lib.data.ExceptLgr;
import fr.legrain.lib.data.LibConversion;

/**
 * Home object for domain model class TaTLiens.
 * @see fr.legrain.tiers.dao.TaTLiens
 * @author Hibernate Tools
 */
public class TaDocumentTiersDAO extends AbstractApplicationDAO<TaDocumentTiers>{

	static Logger logger = Logger.getLogger(TaDocumentTiersDAO.class);

	private String defaultJPQLQuery = "select a from TaDocumentTiers a " ;
			//"order by actif,ordreTRelance";
	
	public TaDocumentTiersDAO() {
		this(null);
	}
	
	public TaDocumentTiersDAO(EntityManager em){
		if(em!=null) {
			setEm(em);
		}
		initChampId(TaDocumentTiers.class);
		setJPQLQuery(defaultJPQLQuery);
		setListeChampMaj(Const.C_FICHIER_INI_CHAMPMAJ);
		initNomTable(new TaDocumentTiers());
	}
	public TaDocumentTiers refresh(TaDocumentTiers detachedInstance) {
		logger.debug("refresh instance");
		try {
			org.hibernate.Session session = (org.hibernate.Session) getEntityManager().getDelegate();
		    session.evict(detachedInstance);
		    detachedInstance = getEntityManager().find(TaDocumentTiers.class, detachedInstance.getIdDocumentTiers());
		    return detachedInstance;
		} catch (RuntimeException re) {
			logger.error("refresh failed", re);
			throw re;
		}
	}
	public void persist(TaDocumentTiers transientInstance) {
		logger.debug("persisting TaDocumentTiers instance");
		try {
			getEntityManager().persist(transientInstance);
			logger.debug("persist successful");
		} catch (RuntimeException re) {
			logger.error("persist failed", re);
			throw re;
		}
	}

	public void remove(TaDocumentTiers persistentInstance) {
		logger.debug("removing TaDocumentTiers instance");
		try {
			getEntityManager().remove(persistentInstance);
			logger.debug("remove successful");
		} catch (RuntimeException re) {
			logger.error("remove failed", re);
			throw re;
		}
	}

	public TaDocumentTiers merge(TaDocumentTiers detachedInstance) {
		logger.debug("merging TaDocumentTiers instance");
		try {
			TaDocumentTiers result = getEntityManager().merge(detachedInstance);
			logger.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			logger.error("merge failed", re);
			throw re;
		}
	}

	public TaDocumentTiers findById(int id) {
		logger.debug("getting TaDocumentTiers instance with id: " + id);
		try {
			TaDocumentTiers instance = getEntityManager().find(TaDocumentTiers.class, id);
			logger.debug("get successful");
			return instance;
		} catch (RuntimeException re) {
			logger.error("get failed", re);
			throw re;
		}
	}
	
	public TaDocumentTiers findByCode(String code) {
		logger.debug("getting TaDocumentTiers instance with code: " + code);
		try {
			if(!code.equals("")){
			Query query = getEntityManager().createQuery("select f from TaDocumentTiers f where f.codeDocumentTiers='"+code+"'");
			TaDocumentTiers instance = (TaDocumentTiers)query.getSingleResult();
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
	public List<TaDocumentTiers> selectAll() {
		logger.debug("selectAll TaDocumentTiers");
		try {  //+" order by a.ordreTRelance"
			Query ejbQuery = getEntityManager().createQuery(JPQLQuery);
			List<TaDocumentTiers> l = ejbQuery.getResultList();
			logger.debug("selectAll successful");
			return l;
		} catch (RuntimeException re) {
			logger.error("selectAll failed", re);
			throw re;
		}
	}
	

	
	public void RAZCheminVersion(String typeLogiciel) {
		try {
			List<TaDocumentTiers> liste=selectAll();
			for (TaDocumentTiers TaDocumentTiers : liste) {
				getEntityManager().getTransaction().begin();
				TaDocumentTiers.setActif(LibConversion.booleanToInt(TaDocumentTiers.getTypeLogiciel().equals(typeLogiciel)));
//				TaDocumentTiers.setCheminCorrespRelance(null);
//				TaDocumentTiers.setCheminModelRelance(null);
				TaDocumentTiers=merge(TaDocumentTiers);
				getEntityManager().getTransaction().commit();
				TaDocumentTiers=refresh(TaDocumentTiers);
			}
		} catch (RuntimeException re) {
			logger.error("get failed", re);
			throw re;
		}
	}
	
	public void ctrlSaisieSpecifique(TaDocumentTiers entity,String field) throws ExceptLgr {	
		
	}
}
