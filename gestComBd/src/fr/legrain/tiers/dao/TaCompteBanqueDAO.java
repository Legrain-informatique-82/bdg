package fr.legrain.tiers.dao;

// Generated Mar 24, 2009 4:44:04 PM by Hibernate Tools 3.2.0.CR1

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import fr.legrain.articles.dao.TaUnite;
import fr.legrain.documents.dao.TaTPaiement;
import fr.legrain.gestCom.Appli.AbstractApplicationDAO;
import fr.legrain.gestCom.Appli.Const;
import fr.legrain.lib.data.ExceptLgr;


/**
 * Home object for domain model class TaCompteBanque.
 * @see fr.legrain.tiers.dao.TaCompteBanque
 * @author Hibernate Tools
 */
public class TaCompteBanqueDAO extends AbstractApplicationDAO<TaCompteBanque>{

	//private static final Log logger = LogFactory.getLog(TaCompteBanqueDAO.class);
	static Logger logger = Logger.getLogger(TaCompteBanqueDAO.class);

	private String defaultJPQLQuery = "select a from TaCompteBanque a";
	
	public TaCompteBanqueDAO() {
		this(null);
	}
	
	public TaCompteBanqueDAO(EntityManager em){
		if(em!=null) {
			setEm(em);
		}
//		champIdTable=ctrlGeneraux.getID_TABLE(TaCompteBanque.class.getSimpleName());
		initChampId(TaCompteBanque.class);
		setJPQLQuery(defaultJPQLQuery);
		setListeChampMaj(Const.C_FICHIER_INI_CHAMPMAJ);
		initNomTable(new TaCompteBanque());
	}

	public void persist(TaCompteBanque transientInstance) {
		logger.debug("persisting TaCompteBanque instance");
		try {
			getEntityManager().persist(transientInstance);
			logger.debug("persist successful");
		} catch (RuntimeException re) {
			logger.error("persist failed", re);
			throw re;
		}
	}
	
	public TaCompteBanque refresh(TaCompteBanque detachedInstance) {
		logger.debug("refresh instance");
		try {
			org.hibernate.Session session = (org.hibernate.Session) getEntityManager().getDelegate();
		    session.evict(detachedInstance);
		    detachedInstance = getEntityManager().find(TaCompteBanque.class, detachedInstance.getIdCompteBanque());
		    return detachedInstance;
		} catch (RuntimeException re) {
			logger.error("refresh failed", re);
			throw re;
		}
	}

	public void remove(TaCompteBanque persistentInstance) {
		logger.debug("removing TaCompteBanque instance");
		try {
			getEntityManager().remove(persistentInstance);
			logger.debug("remove successful");
		} catch (RuntimeException re) {
			logger.error("remove failed", re);
			throw re;
		}
	}

	public TaCompteBanque merge(TaCompteBanque detachedInstance) {
		logger.debug("merging TaCompteBanque instance");
		try {
			TaCompteBanque result = getEntityManager().merge(detachedInstance);
			logger.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			logger.error("merge failed", re);
			throw re;
		}
	}

	public TaCompteBanque findById(int id) {
		logger.debug("getting TaCompteBanque instance with id: " + id);
		try {
			TaCompteBanque instance = getEntityManager().find(TaCompteBanque.class,
					id);
			logger.debug("get successful");
			return instance;
		} catch (RuntimeException re) {
			logger.error("get failed", re);
			throw re;
		}
	}

	@Override
	public List<TaCompteBanque> selectAll() {
		// TODO Auto-generated method stub
		logger.debug("selectAll TaCompteBanque");
		try {
			Query ejbQuery = getEntityManager().createQuery(JPQLQuery);
			List<TaCompteBanque> l = ejbQuery.getResultList();
			logger.debug("selectAll successful");
			return l;
		} catch (RuntimeException re) {
			logger.error("selectAll failed", re);
			throw re;
		}
	}
	
	public List<TaCompteBanque> selectCompteEntreprise() {
		// TODO Auto-generated method stub
		logger.debug("selectCompteEntreprise TaCompteBanque");
		try {
			Query ejbQuery = getEntityManager().createQuery("select a from TaCompteBanque a where a.taTiers.idTiers=-1 order by a.idCompteBanque");
//			Query ejbQuery = getEntityManager().createQuery("select a from TaCompteBanque a where a.taTiers.idTiers=-1 ");
			List<TaCompteBanque> l = ejbQuery.getResultList();
			logger.debug("selectCompteEntreprise successful");
			return l;
		} catch (RuntimeException re) {
			logger.error("selectCompteEntreprise failed", re);
			throw re;
		}
	}
	
	public List<TaCompteBanque> selectCompteTiers(TaTiers taTiers) {
		// TODO Auto-generated method stub
		logger.debug("selectCompteTiers TaCompteBanque");
		try {
			if(taTiers!=null){
			Query ejbQuery = getEntityManager().createQuery("select a from TaCompteBanque a where a.taTiers.idTiers=?");
			ejbQuery.setParameter(1, taTiers.getIdTiers());
			List<TaCompteBanque> l = ejbQuery.getResultList();
			logger.debug("selectCompteTiers successful");
			return l;
			}
			return null;
		} catch (RuntimeException re) {
			logger.error("selectCompteTiers failed", re);
			throw re;
		}
	}
	
	public TaCompteBanque findByTiersEntreprise(String code) {
		try {
			if(code!=null && code!=""){
				Query query = getEntityManager().createQuery("select a from TaCompteBanque a where a.taTiers.idTiers=-1" +
				" and a.compte like ?");
				query.setParameter(1, code);
				if(query.getSingleResult()!=null)
					return (TaCompteBanque) query.getSingleResult();
			}
			return null;
		} catch (RuntimeException re) {
			logger.error("get failed", re);
			throw re;
		}
	}
	
	
	public TaCompteBanque findByTiersEntreprise() {
		try {
			Query query =null;
					query = getEntityManager().createQuery("select a from TaCompteBanque a where a.taTiers.idTiers=-1 order by 1" );

				
//				Query query = getEntityManager().createQuery("select a from TaCompteBanque a where a.taTiers.idTiers=-1 " );
				List<TaCompteBanque> l =query.getResultList();
				if(l.size()>0){
					return l.get(0);
				}
//			return (TaCompteBanque) query.getSingleResult();
				return null;
		} catch (RuntimeException re) {
			logger.error("get failed", re);
			throw re;
		}
	}

	
	public TaCompteBanque findByTiersEntreprise(TaTPaiement tPaiement) {
		try {
			Query query =null;
				if(tPaiement!=null){
					query = getEntityManager().createQuery("select a from TaCompteBanque a where a.taTiers.idTiers=-1 and a.cptcomptable like ?" );
					query.setParameter(1, tPaiement.getCompte());
				}else{
					query = getEntityManager().createQuery("select a from TaCompteBanque a where a.taTiers.idTiers=-1 order by 1" );
				}
				
//				Query query = getEntityManager().createQuery("select a from TaCompteBanque a where a.taTiers.idTiers=-1 " );
				List<TaCompteBanque> l =query.getResultList();
				if(l.size()>0){
					return l.get(0);
				}else
					return findByTiersEntreprise();
//			return (TaCompteBanque) query.getSingleResult();
		} catch (RuntimeException re) {
			logger.error("get failed", re);
			throw re;
		}
	}
	
	public List<TaCompteBanque> findByTiersEntrepriseListeComptePrelevement(TaTPaiement tPaiement) {
		try {
			Query query =null;
//				if(tPaiement!=null){
//					query = getEntityManager().createQuery("select a from TaCompteBanque a where a.taTiers.idTiers=-1 and a.taTBanque.codeTBanque='PREL'" );
					query = getEntityManager().createQuery("select a from TaCompteBanque a where a.taTiers.idTiers=-1 " );
//					query.setParameter(1, tPaiement.getCompte());
//				}else{
//					query = getEntityManager().createQuery("select a from TaCompteBanque a where a.taTiers.idTiers=-1 order by 1" );
//				}
				
//				Query query = getEntityManager().createQuery("select a from TaCompteBanque a where a.taTiers.idTiers=-1 " );
				List<TaCompteBanque> l =query.getResultList();
				return l;
//			return (TaCompteBanque) query.getSingleResult();
		} catch (RuntimeException re) {
			logger.error("get failed", re);
			throw re;
		}
	}
	
	
	public void ctrlSaisieSpecifique(TaCompteBanque entity,String field) throws ExceptLgr {	
		
	}
}
