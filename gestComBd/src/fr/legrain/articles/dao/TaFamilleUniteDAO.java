package fr.legrain.articles.dao;

// Generated 11 juin 2009 10:56:24 by Hibernate Tools 3.2.4.GA

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import fr.legrain.gestCom.Appli.AbstractApplicationDAO;
import fr.legrain.gestCom.Appli.Const;

/**
 * Home object for domain model class TaFamilleUnite.
 * @see fr.legrain.articles.dao.TaFamilleUnite
 * @author Hibernate Tools
 */
public class TaFamilleUniteDAO extends AbstractApplicationDAO<TaFamilleUnite> {

	private static final Log logger = LogFactory.getLog(TaFamilleUniteDAO.class);

	private String defaultJPQLQuery = "select p from TaFamilleUnite p";
	
	public TaFamilleUniteDAO(){
		this(null);
	}

	public TaFamilleUniteDAO(EntityManager em){
		if(em!=null) {
			setEm(em);
		}
//		champIdTable=ctrlGeneraux.getID_TABLE(TaFamilleUnite.class.getSimpleName());
		initChampId(TaFamilleUnite.class);
		setJPQLQuery(defaultJPQLQuery);
		setListeChampMaj(Const.C_FICHIER_INI_CHAMPMAJ);
		initNomTable(new TaFamilleUnite());
	}

	public void persist(TaFamilleUnite transientInstance) {
		logger.debug("persisting TaFamilleUnite instance");
		try {
			 getEntityManager().persist(transientInstance);
			logger.debug("persist successful");
		} catch (RuntimeException re) {
			logger.error("persist failed", re);
			throw re;
		}
	}

	public TaFamilleUnite refresh(TaFamilleUnite detachedInstance) {
		logger.debug("refresh instance");
		try {
			org.hibernate.Session session = (org.hibernate.Session) getEntityManager().getDelegate();
		    session.evict(detachedInstance);
		    detachedInstance = getEntityManager().find(TaFamilleUnite.class, detachedInstance.getIdFamille());
		    return detachedInstance;

		} catch (RuntimeException re) {
			logger.error("refresh failed", re);
			throw re;
		}
	}
	
	public void remove(TaFamilleUnite persistentInstance) {
		logger.debug("removing TaFamilleUnite instance");
		try {
			 getEntityManager().remove(persistentInstance);
			logger.debug("remove successful");
		} catch (RuntimeException re) {
			logger.error("remove failed", re);
			throw re;
		}
	}

	public TaFamilleUnite merge(TaFamilleUnite detachedInstance) {
		logger.debug("merging TaFamilleUnite instance");
		try {
			TaFamilleUnite result =  getEntityManager().merge(detachedInstance);
			logger.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			logger.error("merge failed", re);
			throw re;
		}
	}

	public TaFamilleUnite findById(int id) {
		logger.debug("getting TaFamilleUnite instance with id: " + id);
		try {
			TaFamilleUnite instance = getEntityManager().find(TaFamilleUnite.class,
					id);
			logger.debug("get successful");
			return instance;
		} catch (RuntimeException re) {
			logger.error("get failed", re);
			throw re;
		}
	}
	public TaFamilleUnite findByCode(String code) {
		logger.debug("getting TaFamilleUnite instance with code: " + code);
		try {
			if(!code.equals("")){
			Query query = getEntityManager().createQuery("select f from TaFamilleUnite f where f.codeFamille='"+code+"'");
			TaFamilleUnite instance = (TaFamilleUnite)query.getSingleResult();
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
	public List<TaFamilleUnite> selectAll() {
		try {
			Query ejbQuery = getEntityManager().createQuery(JPQLQuery);
			List<TaFamilleUnite> l = ejbQuery.getResultList();
			logger.debug("selectAll successful");
			return l;
		} catch (RuntimeException re) {
			logger.error("selectAll failed", re);
			throw re;
		}
	}
}
