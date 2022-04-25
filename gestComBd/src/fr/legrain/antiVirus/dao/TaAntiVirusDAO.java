package fr.legrain.antiVirus.dao;

// Generated Mar 25, 2009 10:06:50 AM by Hibernate Tools 3.2.0.CR1

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.SecondaryTable;
import javax.persistence.Table;

import org.apache.log4j.Logger;

import fr.legrain.SupportAbon.dao.TaSupportAbon;
import fr.legrain.articles.dao.TaUnite;
import fr.legrain.gestCom.Appli.AbstractApplicationDAO;
import fr.legrain.gestCom.Appli.Const;
import fr.legrain.lib.data.ExceptLgr;

/**
 * Home object for domain model class TaCPaiement.
 * @see fr.legrain.tiers.dao.test.TaCPaiement
 * @author Hibernate Tools
 */
public   class TaAntiVirusDAO extends AbstractApplicationDAO<TaAntiVirus>{

	static Logger logger = Logger.getLogger(TaAntiVirusDAO.class);
	
	private String defaultJPQLQuery = "select a from TaAntiVirus a";
	
	public TaAntiVirusDAO() {
		this(null);
	}
	
	public TaAntiVirusDAO(EntityManager em){
		if(em!=null) {
			setEm(em);
		}
		initChampId(TaAntiVirus.class);
		setJPQLQuery(defaultJPQLQuery);
		setListeChampMaj(Const.C_FICHIER_INI_CHAMPMAJ);
		initNomTable(new TaAntiVirus());
		initNomTableMere(new TaAntiVirus());
	}
	
	
	public void persist(TaAntiVirus transientInstance) {
		logger.debug("persisting TaAntiVirus instance");
		try {
			getEntityManager().persist(transientInstance);
			logger.debug("persist successful");
		} catch (RuntimeException re) {
			logger.error("persist failed", re);
			throw re;
		}
	}
	
	public TaAntiVirus refresh(TaAntiVirus detachedInstance) {
		logger.debug("refresh instance");
		try {
			org.hibernate.Session session = (org.hibernate.Session) getEntityManager().getDelegate();
		    session.evict(detachedInstance);
		    detachedInstance = getEntityManager().find(TaAntiVirus.class, detachedInstance.getIdSupportAbon());
		    return detachedInstance;
		} catch (RuntimeException re) {
			logger.error("refresh failed", re);
			throw re;
		}
	}

	public void remove(TaAntiVirus persistentInstance) {
		logger.debug("removing TaAntiVirus instance");
		try {
			getEntityManager().remove(persistentInstance);
			logger.debug("remove successful");
		} catch (RuntimeException re) {
			logger.error("remove failed", re);
			throw re;
		}
	}

	public TaAntiVirus merge(TaAntiVirus detachedInstance) {
		logger.debug("merging TaAntiVirus instance");
		try {
			TaAntiVirus result = getEntityManager().merge(detachedInstance);
			logger.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			logger.error("merge failed", re);
			throw re;
		}
	}

	public TaAntiVirus findById(int id) {
		logger.debug("getting TaAntiVirus instance with id: " + id);
		try {
			TaAntiVirus instance = getEntityManager().find(TaAntiVirus.class, id);
			logger.debug("get successful");
			return instance;
		} catch (RuntimeException re) {
			logger.error("get failed", re);
			throw re;
		}
	}
	
	public TaAntiVirus findByCode(String code) {
		logger.debug("getting TaAntiVirus instance with code: " + code);
		try {
			if(!code.equals("")){
			Query query = getEntityManager().createQuery("select f from TaAntiVirus f where " +
					"f.codeSupportAbon='"+code+"'");
			TaAntiVirus instance = (TaAntiVirus)query.getSingleResult();
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
	public List<TaAntiVirus> selectAll() {
		// TODO Auto-generated method stub
		logger.debug("selectAll TaAntiVirus");
		try {
			Query ejbQuery = getEntityManager().createQuery(JPQLQuery);
			List<TaAntiVirus> l = ejbQuery.getResultList();
			logger.debug("selectAll successful");
			return l;
		} catch (RuntimeException re) {
			logger.error("selectAll failed", re);
			throw re;
		}
	}
	

}
