package fr.legrain.SupportAbon.dao;

// Generated Mar 25, 2009 10:06:50 AM by Hibernate Tools 3.2.0.CR1

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import fr.legrain.abonnement.dao.TaAbonnement;
import fr.legrain.abonnement.dao.TaAbonnementDAO;
import fr.legrain.articles.dao.TaUnite;
import fr.legrain.documents.dao.TaFacture;
import fr.legrain.gestCom.Appli.AbstractApplicationDAO;
import fr.legrain.gestCom.Appli.Const;
import fr.legrain.lib.data.ExceptLgr;
import fr.legrain.lib.data.LibDate;

/**
 * Home object for domain model class TaCPaiement.
 * @see fr.legrain.tiers.dao.test.TaCPaiement
 * @author Hibernate Tools
 */
public   class TaSupportAbonDAO extends AbstractApplicationDAO<TaSupportAbon>{

	static Logger logger = Logger.getLogger(TaSupportAbonDAO.class);
	
	private String defaultJPQLQuery = "select a from TaSupportAbon a";
	
	public TaSupportAbonDAO() {
		this(null);
	}
	
	public TaSupportAbonDAO(EntityManager em){
		if(em!=null) {
			setEm(em);
		}
		initChampId(TaSupportAbon.class);
		setJPQLQuery(defaultJPQLQuery);
		setListeChampMaj(Const.C_FICHIER_INI_CHAMPMAJ);
		initNomTable(new TaSupportAbon());
	}
	
	public void persist(TaSupportAbon transientInstance) {
		logger.debug("persisting TaSupportAbon instance");
		try {
			getEntityManager().persist(transientInstance);
			logger.debug("persist successful");
		} catch (RuntimeException re) {
			logger.error("persist failed", re);
			throw re;
		}
	}
	
	public TaSupportAbon refresh(TaSupportAbon detachedInstance) {
		logger.debug("refresh instance");
		try {
			org.hibernate.Session session = (org.hibernate.Session) getEntityManager().getDelegate();
		    session.evict(detachedInstance);
		    detachedInstance = getEntityManager().find(TaSupportAbon.class, detachedInstance.getIdSupportAbon());
		    return detachedInstance;
		} catch (RuntimeException re) {
			logger.error("refresh failed", re);
			throw re;
		}
	}

	public void remove(TaSupportAbon persistentInstance) {
		logger.debug("removing TaSupportAbon instance");
		try {
			getEntityManager().remove(persistentInstance);
			logger.debug("remove successful");
		} catch (RuntimeException re) {
			logger.error("remove failed", re);
			throw re;
		}
	}

	public TaSupportAbon merge(TaSupportAbon detachedInstance) {
		logger.debug("merging TaSupportAbon instance");
		try {
			TaSupportAbon result = getEntityManager().merge(detachedInstance);
			logger.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			logger.error("merge failed", re);
			throw re;
		}
	}

	public TaSupportAbon findById(int id) {
		logger.debug("getting TaSupportAbon instance with id: " + id);
		try {
			TaSupportAbon instance = getEntityManager().find(TaSupportAbon.class, id);
			logger.debug("get successful");
			return instance;
		} catch (RuntimeException re) {
			logger.error("get failed", re);
			throw re;
		}
	}
	
	public TaSupportAbon findByCode(String code) {
		logger.debug("getting TaSupportAbon instance with code: " + code);
		try {
			if(!code.equals("")){
			Query query = getEntityManager().createQuery("select f from TaSupportAbon f where " +
					"f.codeSupportAbon='"+code+"'");
			TaSupportAbon instance = (TaSupportAbon)query.getSingleResult();
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
	public List<TaSupportAbon> selectAll() {
		// TODO Auto-generated method stub
		logger.debug("selectAll TaSupportAbon");
		try {
			Query ejbQuery = getEntityManager().createQuery(JPQLQuery);
			List<TaSupportAbon> l = ejbQuery.getResultList();
			logger.debug("selectAll successful");
			return l;
		} catch (RuntimeException re) {
			logger.error("selectAll failed", re);
			throw re;
		}
	}
	

	
	public List<TaSupportAbon> selectTaSupportAbonFacture(TaFacture taFacture) {
		// TODO Auto-generated method stub
		List<TaSupportAbon> l=null;
		logger.debug("selectTaSupportAbonFacture");
		try {
			if(taFacture!=null && taFacture.getIdDocument()!=0){
			Query query = getEntityManager().createQuery("select f from TaSupportAbon f " +
					" join f.taLDocument l join l.taDocument d where d.idDocument="+taFacture.getIdDocument() );
			l = query.getResultList();
			}
			logger.debug("selectTaSupportAbonFacture successful");
			return l;
		} catch (RuntimeException re) {
			logger.error("selectTaSupportAbonFacture failed", re);
			throw re;
		}
	}


	public List<TaSupportAbon> selectSupportPourMiseAJourCompteLegrainFr() {
		// TODO Auto-generated method stub
		List<TaSupportAbon> l=null;
		
		TaAbonnementDAO daoAbonnement = new TaAbonnementDAO(getEntityManager());
		logger.debug("selectSupportPourMiseAJourCompteLegrainFr");
		try {

			Query query = getEntityManager().createQuery("select f from TaSupportAbon f " +
					" join f.taTiers t  where t.actifTiers = 1 " );
			l = query.getResultList();
			
			logger.debug("selectSupportPourMiseAJourCompteLegrainFr successful");
			return l;
		} catch (RuntimeException re) {
			logger.error("selectSupportPourMiseAJourCompteLegrainFr failed", re);
			throw re;
		}
	}
}
