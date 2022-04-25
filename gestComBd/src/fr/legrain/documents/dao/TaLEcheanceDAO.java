package fr.legrain.documents.dao;

// Generated Apr 7, 2009 3:27:24 PM by Hibernate Tools 3.2.0.CR1

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import fr.legrain.abonnement.dao.TaAbonnement;
import fr.legrain.articles.dao.TaPrix;
import fr.legrain.articles.dao.TaTAbonnement;
import fr.legrain.articles.dao.TaUnite;
import fr.legrain.gestCom.Appli.AbstractApplicationDAO;
import fr.legrain.gestCom.Appli.Const;
import fr.legrain.gestCom.Module_Document.IHMPeriode;

/**
 * Home object for domain model class TaLEcheance.
 * @see fr.legrain.documents.dao.TaLEcheance
 * @author Hibernate Tools
 */
public class TaLEcheanceDAO extends AbstractApplicationDAO<TaLEcheance> {

//	private static final Log log = LogFactory.getLog(TaLEcheanceDAO.class);
	static Logger logger = Logger.getLogger(TaLEcheanceDAO.class);
	
	private String defaultJPQLQuery = "select a from TaLEcheance a";
	
	public TaLEcheanceDAO(){
		this(null);
	}
	
	public TaLEcheanceDAO(EntityManager em){
		if(em!=null) {
			setEm(em);
		}
//		champIdTable=ctrlGeneraux.getID_TABLE(TaLEcheance.class.getSimpleName());
		initChampId(TaLEcheance.class);
		setJPQLQuery(defaultJPQLQuery);
		setListeChampMaj(Const.C_FICHIER_INI_CHAMPMAJ);
		initNomTable(new TaLEcheance());
	}
	public TaLEcheance refresh(TaLEcheance detachedInstance) {
		logger.debug("refresh instance");
		try {
			detachedInstance.setLegrain(false);
        	getEntityManager().refresh(detachedInstance);
//			org.hibernate.Session session = (org.hibernate.Session) getEntityManager().getDelegate();
//		    session.evict(detachedInstance);
//		    detachedInstance = getEntityManager().find(TaLEcheance.class, detachedInstance.getIdLDocument());
			detachedInstance.setLegrain(true);
		    return detachedInstance;
			
		} catch (RuntimeException re) {
			logger.error("refresh failed", re);
			throw re;
		}
	}
	public void persist(TaLEcheance transientInstance) {
		logger.debug("persisting TaLEcheance instance");
		try {
			getEntityManager().persist(transientInstance);
			logger.debug("persist successful");
		} catch (RuntimeException re) {
			logger.error("persist failed", re);
			throw re;
		}
	}

	public void remove(TaLEcheance persistentInstance) {
		logger.debug("removing TaLEcheance instance");
		try {
			getEntityManager().remove(persistentInstance);
			logger.debug("remove successful");
		} catch (RuntimeException re) {
			logger.error("remove failed", re);
			throw re;
		}
	}

	public TaLEcheance merge(TaLEcheance detachedInstance) {
		logger.debug("merging TaLEcheance instance");
		try {
			TaLEcheance result = getEntityManager().merge(detachedInstance);
			logger.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			logger.error("merge failed", re);
			throw re;
		}
	}

	public TaLEcheance findById(int id) {
		logger.debug("getting TaLEcheance instance with id: " + id);
		try {
			TaLEcheance instance = getEntityManager().find(TaLEcheance.class, id);
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
	public List<TaLEcheance> selectAll() {
		logger.debug("selectAll TaLEcheance");
		try {
			Query ejbQuery = getEntityManager().createQuery(JPQLQuery);
			List<TaLEcheance> l = ejbQuery.getResultList();
			logger.debug("selectAll successful");
			return l;
		} catch (RuntimeException re) {
			logger.error("selectAll failed", re);
			throw re;
		}
	}
	
	public List<TaLEcheance> rechercheEcheanceLieAAbonnement(TaAbonnement taAbonnement) {
		logger.debug(" rechercheEcheanceLieAAbonnement");
		try {
			Query ejbQuery = getEntityManager().createQuery("select e from TaLEcheance e where e.taAbonnement=?");
			ejbQuery.setParameter(1, taAbonnement);
			List<TaLEcheance> l = ejbQuery.getResultList();
			logger.debug("rechercheEcheanceLieAAbonnement successful");
			return l;
		} catch (RuntimeException re) {
			logger.error("rechercheEcheanceLieAAbonnement failed", re);
			throw re;
		}
	}
	

	public List<IHMPeriode> recherchePeriodeEcheance(String codeTAbonnement) {
		logger.debug(" recherchePeriodeEcheance");
		try {
			Query ejbQuery = getEntityManager().createQuery("select NEW fr.legrain.gestCom.Module_Document.IHMPeriode(e.debutPeriode,e.finPeriode)" +
					" from TaLEcheance e join e.taAbonnement a join a.taTAbonnement ta where ta.codeTAbonnement=? group by e.debutPeriode,e.finPeriode");
			ejbQuery.setParameter(1, codeTAbonnement);
			List<IHMPeriode> l = ejbQuery.getResultList();
			logger.debug("recherchePeriodeEcheance successful");
			return l;
		} catch (RuntimeException re) {
			logger.error("recherchePeriodeEcheance failed", re);
			throw re;
		}
	}	


	
	public void suppressionAbonnementDansLEcheance(TaAbonnement taAbonnement){
		List<TaLEcheance> liste=rechercheEcheanceLieAAbonnement(taAbonnement);
		if(!getEntityManager().getTransaction().isActive())getEntityManager().getTransaction().begin();
		if(liste!=null){
			for (TaLEcheance taLEcheance : liste) {				
				taLEcheance.setTaAbonnement(null);
				merge(taLEcheance);
			}
		}
		getEntityManager().getTransaction().commit();
	}
	
	public List<TaLEcheance> rechercheLigneRenouvellementAbonnement(Date dateDeb, Date datefin, String codeTAbonnement, String codeTiers,boolean existant) {
		List<TaLEcheance> result = null;
		Query query = null;
//		existant=false;
		String requete="select le from TaLEcheance le join le.taAbonnement ta join ta.taTAbonnement tat join ta.taTiers t join ta.taSupportAbon s where " +
				" (le.debutPeriode between ? and ? )and(le.finPeriode between ? and ? )" +
				" and tat.codeTAbonnement like ?" +
				" and t.codeTiers like ?  " ;
		if(!existant)requete+=" and not exists(select a from TaLAvisEcheance a where a.taLEcheance.idLEcheance=le.idLEcheance)" ;
		requete+=" and s.inactif<>1 and t.actifTiers=1 order by t.codeTiers,le.debAbonnement" ;		

		if(codeTAbonnement==null || codeTAbonnement.toLowerCase().equals("tous")) {
			query = getEntityManager().createQuery(requete);
			query.setParameter(1, dateDeb);
			query.setParameter(2, datefin);
			query.setParameter(3, dateDeb);
			query.setParameter(4, datefin);
			query.setParameter(5, "%");
			query.setParameter(6, codeTiers);
		}else if(codeTAbonnement!=null ) {
			query = getEntityManager().createQuery(requete);
			query.setParameter(1, dateDeb);
			query.setParameter(2, datefin);
			query.setParameter(3, dateDeb);
			query.setParameter(4, datefin);
			query.setParameter(5, codeTAbonnement);
			query.setParameter(6, codeTiers);
		}

		result = query.getResultList();


		return result;
	}

}
