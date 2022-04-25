package fr.legrain.licenceBdg.dao;

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
public   class TaLicenceBdgDAO extends AbstractApplicationDAO<TaLicenceBdg>{

	static Logger logger = Logger.getLogger(TaLicenceBdgDAO.class);
	
	private String defaultJPQLQuery = "select a from TaLicenceBdg a";
	
	public TaLicenceBdgDAO() {
		this(null);
	}
	
	public TaLicenceBdgDAO(EntityManager em){
		if(em!=null) {
			setEm(em);
		}
		initChampId(TaLicenceBdg.class);
		setJPQLQuery(defaultJPQLQuery);
		setListeChampMaj(Const.C_FICHIER_INI_CHAMPMAJ);
		initNomTable(new TaLicenceBdg());
		initNomTableMere(new TaSupportAbon());
	}
	
	public void persist(TaLicenceBdg transientInstance) {
		logger.debug("persisting TaLicenceBdg instance");
		try {
			getEntityManager().persist(transientInstance);
			logger.debug("persist successful");
		} catch (RuntimeException re) {
			logger.error("persist failed", re);
			throw re;
		}
	}
	
	public TaLicenceBdg refresh(TaLicenceBdg detachedInstance) {
		logger.debug("refresh instance");
		try {
			org.hibernate.Session session = (org.hibernate.Session) getEntityManager().getDelegate();
		    session.evict(detachedInstance);
		    detachedInstance = getEntityManager().find(TaLicenceBdg.class, detachedInstance.getIdSupportAbon());
		    return detachedInstance;
		} catch (RuntimeException re) {
			logger.error("refresh failed", re);
			throw re;
		}
	}

	public void remove(TaLicenceBdg persistentInstance) {
		logger.debug("removing TaLicenceBdg instance");
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

	public TaLicenceBdg merge(TaLicenceBdg detachedInstance) {
		logger.debug("merging TaLicenceBdg instance");
		try {
			TaLicenceBdg result = getEntityManager().merge(detachedInstance);
			TaComptePointDAO daoComptePoint = new TaComptePointDAO(getEntityManager());
			daoComptePoint.calculPointTotalAnneeSupport(detachedInstance);
			logger.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			logger.error("merge failed", re);
			throw re;
		}
	}

	public TaLicenceBdg findById(int id) {
		logger.debug("getting TaLicenceBdg instance with id: " + id);
		try {
			TaLicenceBdg instance = getEntityManager().find(TaLicenceBdg.class, id);
			logger.debug("get successful");
			return instance;
		} catch (RuntimeException re) {
			logger.error("get failed", re);
			throw re;
		}
	}
	
	public TaLicenceBdg findByCode(String code) {
		logger.debug("getting TaLicenceBdg instance with code: " + code);
		try {
			if(!code.equals("")){
			Query query = getEntityManager().createQuery("select f from TaLicenceBdg f where " +
					"f.codeSupportAbon='"+code+"'");
			TaLicenceBdg instance = (TaLicenceBdg)query.getSingleResult();
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
	public List<TaLicenceBdg> selectAll() {
		// TODO Auto-generated method stub
		logger.debug("selectAll TaLicenceBdg");
		try {
			Query ejbQuery = getEntityManager().createQuery(JPQLQuery);
			List<TaLicenceBdg> l = ejbQuery.getResultList();
			logger.debug("selectAll successful");
			return l;
		} catch (RuntimeException re) {
			logger.error("selectAll failed", re);
			throw re;
		}
	}
	

}
