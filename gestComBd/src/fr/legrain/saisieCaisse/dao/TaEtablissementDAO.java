package fr.legrain.saisieCaisse.dao;

// Generated Mar 24, 2009 4:44:04 PM by Hibernate Tools 3.2.0.CR1

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import fr.legrain.articles.dao.TaUnite;
import fr.legrain.documents.dao.TaInfosApporteur;
import fr.legrain.gestCom.Appli.AbstractApplicationDAO;
import fr.legrain.gestCom.Appli.Const;
import fr.legrain.lib.data.ExceptLgr;


/**
 * Home object for domain model class TaEntreprise.
 * @see fr.legrain.tiers.dao.TaEntreprise
 * @author Hibernate Tools
 */
public class TaEtablissementDAO extends AbstractApplicationDAO<TaEtablissement>{

	static Logger logger = Logger.getLogger(TaEtablissementDAO.class);

	private String defaultJPQLQuery = "select a from TaEtablissement a";
	
	public TaEtablissementDAO() {
		this(null);
	}
	
	public TaEtablissementDAO(EntityManager em){
		if(em!=null) {
			setEm(em);
		}
//		champIdTable=ctrlGeneraux.getID_TABLE(TaEtablissement.class.getSimpleName());
		initChampId(TaEtablissement.class);
		setJPQLQuery(defaultJPQLQuery);
		setListeChampMaj(Const.C_FICHIER_INI_CHAMPMAJ);
		initNomTable(new TaEtablissement());
	}

	public void persist(TaEtablissement transientInstance) {
		logger.debug("persisting TaEtablissement instance");
		try {
			getEntityManager().persist(transientInstance);
			logger.debug("persist successful");
		} catch (RuntimeException re) {
			logger.error("persist failed", re);
			throw re;
		}
	}
	
	public TaEtablissement refresh(TaEtablissement detachedInstance) {
		logger.debug("refresh instance");
		try {
////			getEntityManager().refresh(detachedInstance);
////			logger.debug("refresh successful");
//			org.hibernate.Session session = (org.hibernate.Session) getEntityManager().getDelegate();
//		    session.evict(detachedInstance);
//		    detachedInstance = getEntityManager().find(TaEtablissement.class, detachedInstance.getIdEtablissement());
		    return detachedInstance;
		} catch (RuntimeException re) {
			logger.error("refresh failed", re);
			throw re;
		}
	}

	public void remove(TaEtablissement persistentInstance) {
		logger.debug("removing TaEtablissement instance");
		try {
			getEntityManager().remove(persistentInstance);
			logger.debug("remove successful");
		} catch (RuntimeException re) {
			logger.error("remove failed", re);
			throw re;
		}
	}

	public TaEtablissement merge(TaEtablissement detachedInstance) {
		logger.debug("merging TaEtablissement instance");
		try {
			TaEtablissement result = getEntityManager().merge(detachedInstance);
			logger.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			logger.error("merge failed", re);
			throw re;
		}
	}

	public TaEtablissement findById(int id) {
		logger.debug("getting TaEtablissement instance with id: " + id);
		try {
			TaEtablissement instance = getEntityManager().find(TaEtablissement.class, id);
			logger.debug("get successful");
			return instance;
		} catch (RuntimeException re) {
			logger.error("get failed", re);
			throw re;
		}
	}

	public TaEtablissement findByCode(String code) {
		logger.debug("getting TaEtablissement instance with code: " + code);
		try {
			if(!code.equals("")){
			Query query = getEntityManager().createQuery("select a from TaEtablissement a " +
					"where a.nomEtablissement='"+code+"'");
			TaEtablissement instance = (TaEtablissement)query.getSingleResult();
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
	public List<TaEtablissement> selectAll() {
		// TODO Auto-generated method stub
		logger.debug("selectAll TaEtablissement");
		try {
			Query ejbQuery = getEntityManager().createQuery(JPQLQuery);
			List<TaEtablissement> l = ejbQuery.getResultList();
			logger.debug("selectAll successful");
			return l;
		} catch (RuntimeException re) {
			logger.error("selectAll failed", re);
			throw re;
		}
	}
	
	public void ctrlSaisieSpecifique(TaEtablissement entity,String field) throws ExceptLgr {	
		
	}
}
