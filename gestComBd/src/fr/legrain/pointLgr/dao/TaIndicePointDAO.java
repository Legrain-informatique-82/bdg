package fr.legrain.pointLgr.dao;

// Generated Mar 24, 2009 4:44:04 PM by Hibernate Tools 3.2.0.CR1

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import fr.legrain.abonnement.dao.TaAbonnement;
import fr.legrain.articles.dao.TaUnite;
import fr.legrain.documents.dao.TaInfosApporteur;
import fr.legrain.gestCom.Appli.AbstractApplicationDAO;
import fr.legrain.gestCom.Appli.Const;
import fr.legrain.lib.data.ExceptLgr;


/**
 * Home object for domain model class TaAdresse.
 * @see fr.legrain.tiers.dao.TaAdresse
 * @author Hibernate Tools
 */
public class TaIndicePointDAO extends AbstractApplicationDAO<TaIndicePoint>{

	//private static final Log logger = LogFactory.getLog(TaAdresseDAO.class);
	static Logger logger = Logger.getLogger(TaIndicePointDAO.class);
	
	private String defaultJPQLQuery = "select a from TaIndicePoint a";
	
	public TaIndicePointDAO(){
		this(null);
	}
	
	public TaIndicePointDAO(EntityManager em){
		if(em!=null) {
			setEm(em);
		}
//		champIdTable=ctrlGeneraux.getID_TABLE(TaTiersPoint.class.getSimpleName());
		initChampId(TaIndicePoint.class);
		setJPQLQuery(defaultJPQLQuery);
		setListeChampMaj(Const.C_FICHIER_INI_CHAMPMAJ);
		initNomTable(new TaIndicePoint());
	}

	public TaIndicePoint refresh(TaIndicePoint detachedInstance) {
		logger.debug("refresh instance");
		try {
			org.hibernate.Session session = (org.hibernate.Session) getEntityManager().getDelegate();
		    session.evict(detachedInstance);
		    detachedInstance = getEntityManager().find(TaIndicePoint.class, detachedInstance.getIdIndicePoint());
		    return detachedInstance;
		} catch (RuntimeException re) {
			logger.error("refresh failed", re);
			throw re;
		}
	}
	public void persist(TaIndicePoint transientInstance) {
		logger.debug("persisting TaIndicePoint instance");
		try {
			getEntityManager().persist(transientInstance);
			logger.debug("persist successful");
		} catch (RuntimeException re) {
			logger.error("persist failed", re);
			throw re;
		}
	}

	public void remove(TaIndicePoint persistentInstance) {
		logger.debug("removing TaIndicePoint instance");
		try {
			getEntityManager().remove(persistentInstance);
			logger.debug("remove successful");
		} catch (RuntimeException re) {
			logger.error("remove failed", re);
			throw re;
		}
	}

	public TaIndicePoint merge(TaIndicePoint detachedInstance) {
		logger.debug("merging TaIndicePoint instance");
		try {
			TaIndicePoint result = getEntityManager().merge(detachedInstance);
			logger.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			logger.error("merge failed", re);
			throw re;
		}
	}

	public TaIndicePoint findById(int id) {
		logger.debug("getting TaIndicePoint instance with id: " + id);
		try {
			TaIndicePoint instance = getEntityManager().find(TaIndicePoint.class, id);
			logger.debug("get successful");
			return instance;
		} catch (RuntimeException re) {
			logger.error("get failed", re);
			throw re;
		}
	}

	@Override
	public List<TaIndicePoint> selectAll() {
		// TODO Auto-generated method stub
		logger.debug("selectAll TaIndicePoint");
		try {
			Query ejbQuery = getEntityManager().createQuery(JPQLQuery);
			List<TaIndicePoint> l = ejbQuery.getResultList();
			logger.debug("selectAll successful");
			return l;
		} catch (RuntimeException re) {
			logger.error("selectAll failed", re);
			throw re;
		}
	}
	


	
	public TaIndicePoint RechercheMaxParTranche(BigDecimal tranche,Integer nbProduit) {
		logger.debug("getting TaIndicePoint  with tranche : "+tranche);
		try {
			TaIndicePoint instance=null;
			Query query = getEntityManager().createQuery("select max(a.tranche) from TaIndicePoint a  where a.tranche <=  ? and a.nbProduit <= ?");
				query.setParameter(1, tranche);
				query.setParameter(2, nbProduit);
				BigDecimal trancheMax =(BigDecimal)query.getSingleResult();
				if(trancheMax!=null)instance=findByTrancheAndNbProduit(trancheMax,nbProduit);
//				instance= (TaIndicePoint) query.getSingleResult();
			
			return instance;
		} catch (Exception e) {
			return null;
		}
	}
	
	public TaIndicePoint findByTrancheAndNbProduit(BigDecimal tranche,Integer nbProduit) {
		logger.debug("getting findByTranche: " + tranche);
		try {
			if(!tranche.equals("")){
			Query query = getEntityManager().createQuery("select f from TaIndicePoint f where f.tranche= ?  and f.nbProduit <= ?");
			query.setParameter(1, tranche);
			query.setParameter(2, nbProduit);
			TaIndicePoint instance = (TaIndicePoint)query.getSingleResult();
			logger.debug("get successful");
			return instance;
			}
			return null;
		} catch (RuntimeException re) {
			logger.error("get failed", re);
			throw re;
		}
	}
	
	public void ctrlSaisieSpecifique(TaIndicePoint entity,String field) throws ExceptLgr {	
		
	}

}
