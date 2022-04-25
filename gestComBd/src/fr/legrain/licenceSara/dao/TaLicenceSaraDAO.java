package fr.legrain.licenceSara.dao;

// Generated Mar 25, 2009 10:06:50 AM by Hibernate Tools 3.2.0.CR1

import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ui.PlatformUI;

import fr.legrain.SupportAbon.dao.TaSupportAbon;
import fr.legrain.abonnement.dao.TaAbonnement;
import fr.legrain.articles.dao.TaUnite;
import fr.legrain.documents.dao.TaLEcheance;
import fr.legrain.documents.dao.TaLEcheanceDAO;
import fr.legrain.gestCom.Appli.AbstractApplicationDAO;
import fr.legrain.gestCom.Appli.Const;
import fr.legrain.lib.data.ExceptLgr;
import fr.legrain.pointLgr.dao.TaComptePointDAO;

/**
 * Home object for domain model class TaCPaiement.
 * @see fr.legrain.tiers.dao.test.TaCPaiement
 * @author Hibernate Tools
 */
public   class TaLicenceSaraDAO extends AbstractApplicationDAO<TaLicenceSara>{

	static Logger logger = Logger.getLogger(TaLicenceSaraDAO.class);
	
	private String defaultJPQLQuery = "select a from TaLicenceSara a";
	
	public TaLicenceSaraDAO() {
		this(null);
	}
	
	public TaLicenceSaraDAO(EntityManager em){
		if(em!=null) {
			setEm(em);
		}
		initChampId(TaLicenceSara.class);
		setJPQLQuery(defaultJPQLQuery);
		setListeChampMaj(Const.C_FICHIER_INI_CHAMPMAJ);
		initNomTable(new TaLicenceSara());
		initNomTableMere(new TaSupportAbon());
	}
	
	public void persist(TaLicenceSara transientInstance) {
		logger.debug("persisting TaLicenceSara instance");
		try {
			getEntityManager().persist(transientInstance);
			logger.debug("persist successful");
		} catch (RuntimeException re) {
			logger.error("persist failed", re);
			throw re;
		}
	}
	
	public TaLicenceSara refresh(TaLicenceSara detachedInstance) {
		logger.debug("refresh instance");
		try {
			org.hibernate.Session session = (org.hibernate.Session) getEntityManager().getDelegate();
		    session.evict(detachedInstance);
		    detachedInstance = getEntityManager().find(TaLicenceSara.class, detachedInstance.getIdSupportAbon());
		    return detachedInstance;
		} catch (RuntimeException re) {
			logger.error("refresh failed", re);
			throw re;
		}
	}

	public void remove(TaLicenceSara persistentInstance) {
		logger.debug("removing TaLicenceSara instance");
		try {
			TaLEcheanceDAO daoLEcheance = new TaLEcheanceDAO(getEntityManager());
			List<TaLEcheance> listeEcheance=null;
			Boolean continuer=true;
			Iterator<TaAbonnement> iterator=persistentInstance.getTaAbonnements().iterator();
			while(continuer && iterator.hasNext()){
				listeEcheance=daoLEcheance.rechercheEcheanceLieAAbonnement(iterator.next()) ;
				continuer=listeEcheance==null || listeEcheance.size()<=0;
			};
			
			if(!continuer){
				MessageDialog.openWarning(PlatformUI.getWorkbench()
						.getActiveWorkbenchWindow().getShell(), "Suppression impossible", "Vous ne pouvez pas supprimer ce support.");
			}else
				getEntityManager().remove(persistentInstance);
			logger.debug("remove successful");
		} catch (RuntimeException re) {
			logger.error("remove failed", re);
			throw re;
		}
	}

	public TaLicenceSara merge(TaLicenceSara detachedInstance) {
		logger.debug("merging TaLicenceSara instance");
		try {
			TaLicenceSara result = getEntityManager().merge(detachedInstance);
			TaComptePointDAO daoComptePoint = new TaComptePointDAO(getEntityManager());
			daoComptePoint.calculPointTotalAnneeSupport(detachedInstance);
			logger.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			logger.error("merge failed", re);
			throw re;
		}
	}

	public TaLicenceSara findById(int id) {
		logger.debug("getting TaLicenceSara instance with id: " + id);
		try {
			TaLicenceSara instance = getEntityManager().find(TaLicenceSara.class, id);
			logger.debug("get successful");
			return instance;
		} catch (RuntimeException re) {
			logger.error("get failed", re);
			throw re;
		}
	}
	
	public TaLicenceSara findByCode(String code) {
		logger.debug("getting TaLicenceSara instance with code: " + code);
		try {
			if(!code.equals("")){
			Query query = getEntityManager().createQuery("select f from TaLicenceSara f where " +
					"f.codeSupportAbon='"+code+"'");
			TaLicenceSara instance = (TaLicenceSara)query.getSingleResult();
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
	public List<TaLicenceSara> selectAll() {
		// TODO Auto-generated method stub
		logger.debug("selectAll TaLicenceSara");
		try {
			Query ejbQuery = getEntityManager().createQuery(JPQLQuery);
			List<TaLicenceSara> l = ejbQuery.getResultList();
			logger.debug("selectAll successful");
			return l;
		} catch (RuntimeException re) {
			logger.error("selectAll failed", re);
			throw re;
		}
	}
	

}
