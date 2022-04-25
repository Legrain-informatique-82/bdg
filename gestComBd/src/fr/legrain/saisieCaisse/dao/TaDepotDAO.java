package fr.legrain.saisieCaisse.dao;

// Generated 2 juin 2009 14:13:00 by Hibernate Tools 3.2.4.GA

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import fr.legrain.gestCom.Appli.AbstractApplicationDAO;
import fr.legrain.gestCom.Appli.Const;

/**
 * Home object for domain model class TaDepot.
 * @see fr.legrain.saisieCaisse.dao.TaDepot
 * @author Hibernate Tools
 */
public class TaDepotDAO extends AbstractApplicationDAO<TaDepot> {

	private static final Log logger = LogFactory.getLog(TaDepotDAO.class);
	private TaEtablissement masterEntity ;
	private String defaultJPQLQuery = "select u from TaDepot u";
	
	public TaDepotDAO(){
		this(null);
	}
	
	public TaDepotDAO(EntityManager em){
		if(em!=null) {
			setEm(em);
		}
//		champIdTable=ctrlGeneraux.getID_TABLE(TaDepot.class.getSimpleName());
		initChampId(TaDepot.class);
		setJPQLQuery(defaultJPQLQuery);
		setListeChampMaj(Const.C_FICHIER_INI_CHAMPMAJ);
		initNomTable(new TaDepot());
	}
	public void persist(TaDepot transientInstance) {
		logger.debug("persisting TaDepot instance");
		try {
			getEntityManager().persist(transientInstance);
			logger.debug("persist successful");
		} catch (RuntimeException re) {
			logger.error("persist failed", re);
			throw re;
		}
	}

	public TaDepot refresh(TaDepot detachedInstance) {
		logger.debug("refresh instance");
		try {
////			getEntityManager().refresh(detachedInstance);
////			logger.debug("refresh successful");
//			org.hibernate.Session session = (org.hibernate.Session) getEntityManager().getDelegate();
//		    session.evict(detachedInstance);
//		    detachedInstance = getEntityManager().find(TaDepot.class, detachedInstance.getIdDepot());
		    return detachedInstance;
		} catch (RuntimeException re) {
			logger.error("refresh failed", re);
			throw re;
		}
	}
	
	public void remove(TaDepot persistentInstance) {
		logger.debug("removing TaDepot instance");
		try {
			getEntityManager().remove(persistentInstance);
			logger.debug("remove successful");
		} catch (RuntimeException re) {
			logger.error("remove failed", re);
			throw re;
		}
	}

	public TaDepot merge(TaDepot detachedInstance) {
		logger.debug("merging TaDepot instance");
		try {
			TaDepot result = getEntityManager().merge(detachedInstance);
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
	public TaDepot findById(int id) {
		logger.debug("getting TaUnite instance with id: " + id);
		try {
			TaDepot instance = getEntityManager().find(TaDepot.class, id);
			logger.debug("get successful");
			return instance;
		} catch (RuntimeException re) {
			logger.error("get failed", re);
			throw re;
		}
	}
	@Override
	public List<TaDepot> selectAll() {
		logger.debug("selectAll TaDepot");
		try {
			if(masterEntity!=null)
				  JPQLQuery = "select u from TaDepot u where u.taEtablissement.idEtablissement like "+getMasterEntity().getIdEtablissement();
				else
					JPQLQuery =defaultJPQLQuery;
			Query ejbQuery = getEntityManager().createQuery(JPQLQuery);
			List<TaDepot> l = ejbQuery.getResultList();
			logger.debug("selectAll successful");
			return l;
		} catch (RuntimeException re) {
			logger.error("selectAll failed", re);
			throw re;
		}
	}


	
	public List<TaDepot> selectDepot(Date dateDeb,Date dateFin,String journal,TaEtablissement taEtablissement) {
		try {
			String requete = " select u from TaDepot u " +
					" where u.taEtablissement.idEtablissement like '" +taEtablissement.getIdEtablissement()+"'"+
					" and u.taTPaiement.codeTPaiement like '"+journal+"'" +
					" and u.dateDepot between ? and ? " ;
			Query ejbQuery = getEntityManager().createQuery(requete);
			ejbQuery.setParameter(1,dateDeb);
			ejbQuery.setParameter(2,dateFin);
			List<TaDepot> l = ejbQuery.getResultList();
			return l;
		} catch (RuntimeException re) {
			logger.error("selectDepotJour failed", re);
			throw re;
		}
	}
	public TaEtablissement getMasterEntity() {
		return masterEntity;
	}
	public void setMasterEntity(TaEtablissement masterEntity) {
		this.masterEntity = masterEntity;
	}
}
