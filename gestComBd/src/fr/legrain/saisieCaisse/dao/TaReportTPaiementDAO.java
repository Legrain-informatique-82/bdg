package fr.legrain.saisieCaisse.dao;

// Generated 30 juin 2009 10:40:14 by Hibernate Tools 3.2.4.GA

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import fr.legrain.articles.dao.TaUnite;
import fr.legrain.documents.dao.TaInfosApporteur;
import fr.legrain.gestCom.Appli.AbstractApplicationDAO;
import fr.legrain.gestCom.Appli.Const;
import fr.legrain.stocks.dao.TaReportStock;

/**
 * Home object for domain model class TaReportTPaiement.
 * @see fr.legrain.saisieCaisse.dao.TaReportTPaiement
 * @author Hibernate Tools
 */
public class TaReportTPaiementDAO extends AbstractApplicationDAO<TaReportTPaiement> {

	private static final Log logger = LogFactory.getLog(TaReportTPaiementDAO.class);
	private String defaultJPQLQuery = "select u from TaReportTPaiement u";
	private String defaultJPQLQueryMaxReport = "select max(u.dateReportTPaiement) from TaReportTPaiement u";


	public TaReportTPaiementDAO(){
		this(null);
	}
	
	public TaReportTPaiementDAO(EntityManager em){
		if(em!=null) {
			setEm(em);
		}
//		champIdTable=ctrlGeneraux.getID_TABLE(TaReportTPaiement.class.getSimpleName());
		initChampId(TaReportTPaiement.class);
		setJPQLQuery(defaultJPQLQuery);
		setListeChampMaj(Const.C_FICHIER_INI_CHAMPMAJ);
		initNomTable(new TaReportTPaiement());
	}
	public void persist(TaReportTPaiement transientInstance) {
		logger.debug("persisting TaReportTPaiement instance");
		try {
			getEntityManager().persist(transientInstance);
			logger.debug("persist successful");
		} catch (RuntimeException re) {
			logger.error("persist failed", re);
			throw re;
		}
	}
	public TaReportTPaiement refresh(TaReportTPaiement detachedInstance) {
		logger.debug("refresh instance");
		try {
////			getEntityManager().refresh(detachedInstance);
////			logger.debug("refresh successful");
//			org.hibernate.Session session = (org.hibernate.Session) getEntityManager().getDelegate();
//		    session.evict(detachedInstance);
//		    detachedInstance = getEntityManager().find(TaReportTPaiement.class, detachedInstance.getIdReportTPaiement());
		    return detachedInstance;
		} catch (RuntimeException re) {
			logger.error("refresh failed", re);
			throw re;
		}
	}

	public void remove(TaReportTPaiement persistentInstance) {
		logger.debug("removing TaReportTPaiement instance");
		try {
			getEntityManager().remove(persistentInstance);
			logger.debug("remove successful");
		} catch (RuntimeException re) {
			logger.error("remove failed", re);
			throw re;
		}
	}

	public TaReportTPaiement merge(TaReportTPaiement detachedInstance) {
		logger.debug("merging TaReportTPaiement instance");
		try {
			TaReportTPaiement result = getEntityManager().merge(detachedInstance);
			logger.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			logger.error("merge failed", re);
			throw re;
		}
	}

	public TaReportTPaiement findById(int id) {
		logger.debug("getting TaReportTPaiement instance with id: " + id);
		try {
			TaReportTPaiement instance = getEntityManager().find(
					TaReportTPaiement.class, id);
			logger.debug("get successful");
			return instance;
		} catch (RuntimeException re) {
			logger.error("get failed", re);
			throw re;
		}
	}
	
	public java.util.Date recupDerniereDateReportSaisieCaisse() {
		logger.debug("recupDerniereDateReportSaisieCaisse");
		Date dateDeb=new Date(0);
		try {
			Query ejbQuery = getEntityManager().createQuery(defaultJPQLQueryMaxReport);
			List<java.util.Date> l = ejbQuery.getResultList();
			logger.debug("recupDerniereDateReportSaisieCaisse successful");			
			if(l!=null && l.size()>0)
				dateDeb= l.get(0);
//			if (dateDeb==null)dateDeb = new Date();
			return dateDeb;
		} catch (RuntimeException re) {
			logger.error("recupDerniereDateReportSaisieCaisse failed", re);
			throw re;
		}
	}
	
	public void deleteAll() {
		logger.debug("deleteAll");
		try {
			List<TaReportTPaiement> liste =selectAll();
			for (TaReportTPaiement taReportTPaiement : liste) {
				if(taReportTPaiement!=null)
					remove(taReportTPaiement);
			}
			logger.debug("deleteAll successful");			
		} catch (RuntimeException re) {
			logger.error("deleteAll failed", re);
			throw re;
		}
	}
	
	@Override
	public List<TaReportTPaiement> selectAll() {
		try {
			Query ejbQuery = getEntityManager().createQuery(JPQLQuery);
			List<TaReportTPaiement> l = ejbQuery.getResultList();
			logger.debug("selectAll successful");
			return l;
		} catch (RuntimeException re) {
			logger.error("selectAll failed", re);
			throw re;
		}
	}
}
