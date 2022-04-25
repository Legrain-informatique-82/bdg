package fr.legrain.documents.dao;

// Generated Apr 7, 2009 3:27:24 PM by Hibernate Tools 3.2.0.CR1

import java.math.BigDecimal;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TemporalType;

import org.apache.log4j.Logger;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ui.PlatformUI;

import fr.legrain.SupportAbon.dao.TaLicenceLogiciel;
import fr.legrain.SupportAbon.dao.TaSupportAbon;
import fr.legrain.SupportAbon.dao.TaSupportAbonDAO;
import fr.legrain.abonnement.dao.TaAbonnement;
import fr.legrain.articles.dao.TaRTSupport;
import fr.legrain.dossier.dao.TaInfoEntreprise;
import fr.legrain.dossier.dao.TaInfoEntrepriseDAO;
import fr.legrain.gestCom.Appli.AbstractApplicationDAO;
import fr.legrain.gestCom.Appli.Const;
import fr.legrain.gestCom.Module_Document.IDocumentTiers;
import fr.legrain.lib.data.LibConversion;
import fr.legrain.lib.data.LibDate;
import fr.legrain.licenceBdg.dao.TaLicenceBdg;
import fr.legrain.licenceEpicea.dao.TaLicenceEpicea;
import fr.legrain.licenceSara.dao.TaLicenceSara;
import fr.legrain.pointLgr.dao.TaComptePointDAO;
import fr.legrain.tiers.dao.TaEmail;
import fr.legrain.tiers.dao.TaFamilleTiers;
import fr.legrain.tiers.dao.TaFamilleTiersDAO;
import fr.legrain.tiers.dao.TaTiers;

/**
 * Home object for domain model class TaAvisEcheance.
 * @see fr.legrain.documents.dao.TaAvisEcheance
 * @author Hibernate Tools
 */
public class TaAvisEcheanceDAO extends AbstractApplicationDAO<TaAvisEcheance> 
implements IDocumentTiersDAO<TaAvisEcheance>,IDocumentTiersStatistiquesDAO<TaAvisEcheance>,IDocumentTiersEtatDAO<TaAvisEcheance> {

//	private static final Log log = LogFactory.getLog(TaAvisEcheanceDAO.class);
	static Logger logger = Logger.getLogger(TaAvisEcheanceDAO.class);
	
	private String defaultJPQLQuery = "select a from TaAvisEcheance a";
	
	public TaAvisEcheanceDAO(){
		this(null);
	}
	
	public TaAvisEcheanceDAO(EntityManager em){
		if(em!=null) {
			setEm(em);
		}
//		champIdTable=ctrlGeneraux.getID_TABLE(TaAvisEcheance.class.getSimpleName());
		initChampId(TaAvisEcheance.class);
		setJPQLQuery(defaultJPQLQuery);
		setListeChampMaj(Const.C_FICHIER_INI_CHAMPMAJ);
		initNomTable(new TaAvisEcheance());
	}
	
	public TaAvisEcheance refresh(TaAvisEcheance detachedInstance) {
		logger.debug("refresh instance");
		try {
			Map<IDocumentTiers,Boolean> listeEtatLegrain=new LinkedHashMap<IDocumentTiers, Boolean>();
			detachedInstance.setLegrain(false);
			
        	
        	getEntityManager().refresh(detachedInstance);
        	
//			detachedInstance.setLegrain(false);
//			org.hibernate.Session session = (org.hibernate.Session) getEntityManager().getDelegate();
//
//			for (TaRAcompte a : detachedInstance.getTaRAcomptes()) {
//				session.evict(a.getTaAcompte().getTaRAcomptes());
//				session.evict(a.getTaAcompte());
//				session.evict(a);
//			}
//			session.evict(detachedInstance.getTaRAcomptes());
//			session.evict(detachedInstance);
//
//			detachedInstance = getEntityManager().find(TaAvisEcheance.class, detachedInstance.getIdDocument());
			detachedInstance.setLegrain(true);
			detachedInstance.calculLignesTva();
			return detachedInstance;

		} catch (RuntimeException re) {
			logger.error("refresh failed", re);
			throw re;
		}
	}
	
	public void persist(TaAvisEcheance transientInstance) {
		logger.debug("persisting TaAvisEcheance instance");
		try {
			getEntityManager().persist(transientInstance);
			logger.debug("persist successful");
		} catch (RuntimeException re) {
			logger.error("persist failed", re);
			throw re;
		}
	}

	public void remove(TaAvisEcheance persistentInstance) {
		logger.debug("removing TaAvisEcheance instance");
		try {
			
			TaInfoEntrepriseDAO taInfoEntrepriseDAO = new TaInfoEntrepriseDAO(getEntityManager());
			TaInfoEntreprise taInfoEntreprise = taInfoEntrepriseDAO.findInstance();
			Date dateDeb=taInfoEntreprise.getDatedebInfoEntreprise();
			if(dateDeb.compareTo(persistentInstance.getDateDocument())>0)dateDeb=persistentInstance.getDateDocument();
			TaFacture facture = null;
			for (TaRDocument rDocument : persistentInstance.getTaRDocuments()) {
				if(facture==null)facture=rDocument.getTaFacture();
			}
			if(facture==null){
				getEntityManager().remove(persistentInstance);
				new TaComptePointDAO().calculPointUtilise(persistentInstance.getTaTiers().getIdTiers(), dateDeb);
			}else{
				MessageDialog.openWarning(PlatformUI.getWorkbench()
						.getActiveWorkbenchWindow().getShell(), "Suppression impossible", "Vous ne pouvez pas supprimer ce document car il est lié à la facture "+facture.getCodeDocument());
				getEntityManager().remove(null);
			}

			logger.debug("remove successful");
		} catch (RuntimeException re) {
			logger.error("remove failed", re);
			throw re;
		}
	}

	public TaAvisEcheance merge(TaAvisEcheance detachedInstance) {
		logger.debug("merging TaAvisEcheance instance");
		try {
			TaAvisEcheance result = getEntityManager().merge(detachedInstance);
			logger.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			logger.error("merge failed", re);
			throw re;
		}
	}

	public TaAvisEcheance findById(int id) {
		logger.debug("getting TaAvisEcheance instance with id: " + id);
		try {
			TaAvisEcheance instance = getEntityManager().find(TaAvisEcheance.class, id);
			logger.debug("get successful");
			return instance;
		} catch (RuntimeException re) {
			logger.error("get failed", re);
			throw re;
		}
	}
	

	public Boolean exist(String code) {
		logger.debug("exist with code: " + code);
		try {
			if(code!=null && !code.equals("")){
			Query query = getEntityManager().createQuery("select count(f) from TaAvisEcheance f where f.codeDocument='"+code+"'");
			Long instance = (Long)query.getSingleResult();
			logger.debug("get successful");
			return instance!=0;
			}
			return false;
		} catch (RuntimeException re) {
			logger.error("get failed", re);
			throw re;
		}
	}
	
	
	public TaAvisEcheance findByCode(String code) {
		logger.debug("getting TaAvisEcheance instance with code: " + code);
		try {
			Query query = getEntityManager().createQuery("select a from TaAvisEcheance a " +
					" left join FETCH a.lignes l"+
					" where a.codeDocument='"+code+"' " +
							" order by l.numLigneLDocument");
			TaAvisEcheance instance = (TaAvisEcheance)query.getSingleResult();
			instance.setLegrain(true);
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
	public List<TaAvisEcheance> selectAll() {
		logger.debug("selectAll TaAvisEcheance");
		try {
			Query ejbQuery = getEntityManager().createQuery(JPQLQuery);
			List<TaAvisEcheance> l = ejbQuery.getResultList();
			logger.debug("selectAll successful");
			return l;
		} catch (RuntimeException re) {
			logger.error("selectAll failed", re);
			throw re;
		}
	}
	
	public List<TaAvisEcheance> findByCodeTiers(String codeTiers) {
		logger.debug("getting TaAvisEcheance instance with codeTiers: " + codeTiers);
		List<TaAvisEcheance> result = null;
		try {
			Query query = getEntityManager().createNamedQuery(TaAvisEcheance.QN.FIND_BY_TIERS);
			query.setParameter(1, codeTiers);
			result = query.getResultList();
			return result;
		} catch (RuntimeException re) {
			logger.error("get failed", re);
			throw re;
		}
	}
	
	public List<TaAvisEcheance> findByCodeTiersAndDate(String codeTiers, Date debut, Date fin) {
		logger.debug("getting TaAvisEcheance instance with codeTiers: " + codeTiers);
		List<TaAvisEcheance> result = null;
		try {
			Query query = getEntityManager().createNamedQuery(TaAvisEcheance.QN.FIND_BY_TIERS_AND_DATE);
			query.setParameter(1, codeTiers);
			query.setParameter(2, debut);
			query.setParameter(3, fin);
			result = query.getResultList();
			return result;
		} catch (RuntimeException re) {
			logger.error("get failed", re);
			throw re;
		}
	}
	
	/**
	 * |année|codeTiers|HT|TVA|TTC
	 * @param codeTiers
	 * @param debut
	 * @param fin
	 * @return
	 */
	public List<Object> findChiffreAffaireByCodeTiers(String codeTiers,Date debut, Date fin, int precision) {
		logger.debug("getting TaAvisEcheance instance with codeTiers: " + codeTiers);
		List<Object> result = null;
		try {
			String jpql = null;
//			precision = 0;
			String groupBy = null;
			String select = null;
			
			if(precision==0) {
				select = "'','',cast(extract(year from f.dateDocument)as string)";
				groupBy = "'','',extract(year from f.dateDocument)";
			} else if (precision==1){
				select = "'',cast(extract(month from f.dateDocument)as string),cast(extract(year from f.dateDocument)as string)";
				groupBy = "'',extract(month from f.dateDocument),extract(year from f.dateDocument)";
			} else {
				select = "cast(extract(day from f.dateDocument)as string),cast(extract(month from f.dateDocument)as string),cast(extract(year from f.dateDocument)as string)";
				groupBy = "extract(day from f.dateDocument),extract(month from f.dateDocument),extract(year from f.dateDocument)";
			}
			
			jpql = "select  "
				+select+", t.codeTiers,"
				+"case when extract ( year from f.dateDocument) <= 2001 then  sum(f.netHtCalc/6.55957) else sum(f.netHtCalc) end, "
				+"case when extract ( year from f.dateDocument) <= 2001 then  sum(f.netTvaCalc/6.55957) else sum(f.netTvaCalc) end, "
				+"case when extract ( year from f.dateDocument) <= 2001 then  sum(f.netTtcCalc/6.55957 ) else sum(f.netTtcCalc) end "
				+" from TaAvisEcheance f "
				+"left join f.taTiers t "
				+"where  t.codeTiers = ? and f.dateDocument between ? and ? "
				+"group by "+groupBy+",t.codeTiers"
				;

//			jpql = "select  "
//			+"cast(extract(year from f.dateDocument)as string), t.codeTiers,"
//			+"case when extract ( year from f.dateDocument) <= 2001 then  sum(f.netHtCalc/6.55957) else sum(f.netHtCalc) end, "
//			+"case when extract ( year from f.dateDocument) <= 2001 then  sum(f.netTvaCalc/6.55957) else sum(f.netTvaCalc) end, "
//			+"case when extract ( year from f.dateDocument) <= 2001 then  sum(f.netTtcCalc/6.55957 ) else sum(f.netTtcCalc) end "
//			+" from TaAvisEcheance f "
//			+"left join f.taTiers t "
//			+"where  t.codeTiers = ? and f.dateDocument between ? and ? "
//			+"group by extract(year from f.dateDocument),t.codeTiers"
//			;
			Query query = getEntityManager().createQuery(jpql);
			query.setParameter(1, codeTiers);
			query.setParameter(2, debut);
			query.setParameter(3, fin);
			result = query.getResultList();
			return result;
		} catch (RuntimeException re) {
			logger.error("get failed", re);
			throw re;
		}
	}
	
	/**
	 * Recherche les devis entre 2 dates
	 * @param dateDeb - date de début
	 * @param dateFin - date de fin
	 * @return String[] - tableau contenant les IDs des devis entre ces 2 dates (null en cas d'erreur)
	 */
	//public String[] rechercheFacture(Date dateDeb, Date dateFin) {
	public List<TaAvisEcheance> rechercheDocument(Date dateDeb, Date dateFin) {

		List<TaAvisEcheance> result = null;
		Query query = getEntityManager().createNamedQuery(TaAvisEcheance.QN.FIND_BY_DATE);
		query.setParameter(1,"%");
		query.setParameter(2, dateDeb, TemporalType.DATE);
		query.setParameter(3, dateFin, TemporalType.DATE);
		result = query.getResultList();
		
		return result;
	}
	
	
	/**
	 * Recherche les devis entre 2 dates ordonnées par date
	 * @param dateDeb - date de début
	 * @param dateFin - date de fin
	 * @return String[] - tableau contenant les IDs des devis entre ces 2 dates (null en cas d'erreur)
	 */
	//public String[] rechercheFacture(Date dateDeb, Date dateFin) {
	public List<TaAvisEcheance> rechercheDocumentOrderByDate(Date dateDeb, Date dateFin) {

		List<TaAvisEcheance> result = null;
		Query query = getEntityManager().createNamedQuery(TaAvisEcheance.QN.FIND_BY_DATE_PARDATE);
		query.setParameter(1,"%");
		query.setParameter(2, dateDeb, TemporalType.DATE);
		query.setParameter(3, dateFin, TemporalType.DATE);
		result = query.getResultList();
		
		return result;
	}
	/**
	 * Recherche les devis entre 2 codes devis
	 * @param codeDeb - code de début
	 * @param codeFin - code de fin
	 * @return String[] - tableau contenant les IDs des devis entre ces 2 codes (null en cas d'erreur)
	 */
	//public String[] rechercheFacture(String codeDeb, String codeFin) {
	public List<TaAvisEcheance> rechercheDocument(String codeDeb, String codeFin) {		
		List<TaAvisEcheance> result = null;
		Query query = getEntityManager().createNamedQuery(TaAvisEcheance.QN.FIND_BY_CODE);
		query.setParameter(1,"%");
		query.setParameter(2, codeDeb);
		query.setParameter(3, codeFin);
		result = query.getResultList();
		return result;

	}
	@Override
	public int selectCount() {
		// TODO Auto-generated method stub
		return 1;
	}

	@Override
	public List<TaTiers> findTiersByCodeArticleAndDate(String codeTiers, Date debut,
			Date fin) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Object> findChiffreAffaireByCodeArticle(String codeTiers,
			Date debut, Date fin, int precision) {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * Classe permettant d'obtenir le ca généré par les devis sur une période donnée
	 * @param debut date de début des données
	 * @param fin date de fin des données
	 * @return La requête renvoyée renvoi le montant des devis sur la période
	 */
	public List<Object> findCAAvisEcheanceSurPeriode(Date debut, Date fin) {
		logger.debug("getting nombre ca des devis");
		List<Object> result = null;
		
		
		try {
			String requete = "";

			requete = "SELECT "
				+" sum(d.netHtCalc)"
				+" FROM TaAvisEcheance d " 
				+" where d.dateDocument between ? and ?";
			Query query = getEntityManager().createQuery(requete);
			query.setParameter(1, debut);
			query.setParameter(2, fin);
			result = query.getResultList();
			return result;
		} catch (RuntimeException re) {
			logger.error("get failed", re);
			throw re;
		}
	}
	
	public List<TaSumTaLAvisEcheance> rechercheSommeAvisEcheance(TaAvisEcheance taDoc) {
		logger.debug(" rechercheSommeAvisEcheance");
		try {
			Query ejbQuery = getEntityManager().createQuery("select NEW fr.legrain.documents.dao.TaSumTaLAvisEcheance( sum(tlf.mtTtcLApresRemiseGlobaleDocument),ta.idTAbonnement,ta.codeTAbonnement,cast(count(tlf.idLDocument) as integer)) from TaLAvisEcheance tlf join tlf.taLEcheance le "+
					" join le.taAbonnement a join a.taTAbonnement ta"+  
					 " where tlf.taDocument.idDocument = "+taDoc.getIdDocument()+" group by ta.idTAbonnement,ta.codeTAbonnement");
			List<TaSumTaLAvisEcheance> l = ejbQuery.getResultList();
			logger.debug("rechercheSommeAvisEcheance successful");
			return l;
		} catch (RuntimeException re) {
			logger.error("rechercheSommeAvisEcheance failed", re);
			throw re;
		}
	}
	
	public List<Object> findChiffreAffaireTotalTransfo(Date debut, Date fin, int precision) {
		logger.debug("getting ChiffreAffaire total transfo");
		List<Object> result = null;
		try {
			String requete = "";
			String groupBy = "";
			String select = "";
			
			if(precision==0) {
				select = "'','',cast(extract(year from d.dateDocument)as string)";
				groupBy = "'','',extract(year from d.dateDocument)";
			} else if (precision==1){
				select = "'',cast(extract(month from d.dateDocument)as string),cast(extract(year from d.dateDocument)as string)";
				groupBy = "'',extract(month from d.dateDocument),extract(year from d.dateDocument)";
			} else {
				select = "cast(extract(day from d.dateDocument)as string),cast(extract(month from d.dateDocument)as string),cast(extract(year from d.dateDocument)as string)";
				groupBy = "extract(day from d.dateDocument),extract(month from d.dateDocument),extract(year from d.dateDocument)";
			}
			
			requete = "SELECT "+select+ ", "
			+" sum(d.netHtCalc), "
			+" sum(d.netTvaCalc), "
			+" sum(d.netTtcCalc) "
			+" FROM TaAvisEcheance d " 
			+" where d.dateDocument between ? and ? "
			+" and exists (select r " 
			+" from TaRDocument r " 
			+" where r.taAvisEcheance = d " 
			+" and ( r.taFacture IS NOT NULL " 
			+" OR r.taBonliv IS NOT NULL " 
			+" OR r.taBoncde IS NOT NULL " 
			+" OR r.taProforma IS NOT NULL )) "
			+" group by "+groupBy; 
			Query query = getEntityManager().createQuery(requete);
			query.setParameter(1, debut);
			query.setParameter(2, fin);
			result = query.getResultList();
			return result;
		} catch (RuntimeException re) {
			logger.error("get failed", re);
			throw re;
		}
	}
	
	/**
	 * Classe permettant d'obtenir les devis transformés
	 * @param debut date de début des données
	 * @param fin date de fin des données
	 * @return La requête renvoyée renvoi la liste des devis transformés
	 */
	public List<TaAvisEcheance> findAvisEcheanceTransfos(Date debut, Date fin) {
		logger.debug("getting nombre devis non transfos");
		List<TaAvisEcheance> result = null;
		
		
		try {
			String requete = "";

			requete = "SELECT "
				+" d"
				+" FROM TaAvisEcheance d " 
				+" where d.dateDocument between ? and ?"
				+" and  exists (select r " +
						"from TaRDocument r " +
						" where r.taAvisEcheance = d" +
						" and ( taFacture IS NOT NULL" +
						" OR taBonliv IS NOT NULL " +
						" OR taBoncde IS NOT NULL " +
						" OR taProforma IS NOT NULL))"
				+" order by d.mtHtCalc DESC";
			Query query = getEntityManager().createQuery(requete);
			query.setParameter(1, debut);
			query.setParameter(2, fin);
			result = query.getResultList();
			return result;
		} catch (RuntimeException re) {
			logger.error("get failed", re);
			throw re;
		}
	}
	
	/**
	 * Classe permettant d'obtenir les devis non transformés
	 * @param debut date de début des données
	 * @param fin date de fin des données
	 * @return La requête renvoyée renvoi la liste des devis non transformés
	 */
	public List<TaAvisEcheance> findAvisEcheanceNonTransfos(Date debut, Date fin) {
		logger.debug("getting nombre devis non transfos");
		List<TaAvisEcheance> result = null;
		
		
		try {
			String requete = "";

			requete = "SELECT "
				+" d"
				+" FROM TaAvisEcheance d " 
				+" where d.dateDocument between ? and ?"
				+" and not exists (select r " +
						"from TaRDocument r " +
						" where r.taAvisEcheance = d" +
						" and ( taFacture IS NOT NULL" +
						" OR taBonliv IS NOT NULL " +
						" OR taBoncde IS NOT NULL " +
						" OR taProforma IS NOT NULL))"
						+" order by d.mtHtCalc DESC";;
			Query query = getEntityManager().createQuery(requete);
			query.setParameter(1, debut);
			query.setParameter(2, fin);
			result = query.getResultList();
			return result;
		} catch (RuntimeException re) {
			logger.error("get failed", re);
			throw re;
		}
	}
	
	@Override
	public List<TaAvisEcheance> rechercheDocument(String codeDeb, String codeFin,
			String codeTiers) {
		return rechercheDocument(codeDeb, codeFin, codeTiers, null);
	}

	@Override
	public List<TaAvisEcheance> rechercheDocument(Date dateDeb, Date dateFin,
			String codeTiers) {
		return rechercheDocument(dateDeb, dateFin, codeTiers, null);
	}

	@Override
	public List<TaAvisEcheance> rechercheDocument(Date dateDeb, Date dateFin,
			String codeTiers, Boolean export) {
		List<TaAvisEcheance> result = null;
		Query query = getEntityManager().createNamedQuery(TaAvisEcheance.QN.FIND_BY_TIERS_AND_DATE);
		if(codeTiers==null || codeTiers.equals(""))codeTiers="%";
		query.setParameter(1, codeTiers);
		query.setParameter(2, dateDeb, TemporalType.DATE);
		query.setParameter(3, dateFin, TemporalType.DATE);
		result = query.getResultList();
		return result;
	}

	@Override
	public List<TaAvisEcheance> rechercheDocument(String codeDeb, String codeFin,
			String codeTiers, Boolean export) {
		List<TaAvisEcheance> result = null;
		Query query = getEntityManager().createNamedQuery(TaAvisEcheance.QN.FIND_BY_TIERS_AND_CODE);
		if(codeTiers==null || codeTiers.equals(""))codeTiers="%";
		query.setParameter(1, codeTiers);
		query.setParameter(2, codeDeb);
		query.setParameter(3, codeFin);
		result = query.getResultList();
		return result;
	}	
	
	@Override
	public List<TaAvisEcheance> rechercheDocumentEtat(Date dateDeb, Date datefin, String codeEtat) {
		List<TaAvisEcheance> result = null;
		Query query = null;
		if(codeEtat!=null) {
			query = getEntityManager().createNamedQuery(TaAvisEcheance.QN.FIND_BY_ETAT_DATE);
			query.setParameter(1, dateDeb);
			query.setParameter(2, datefin);
			query.setParameter(3, codeEtat);
		} else {
			query = getEntityManager().createNamedQuery(TaAvisEcheance.QN.FIND_BY_ETAT_ENCOURS_DATE);
			query.setParameter(1, datefin);
		}
		
		result = query.getResultList();
		return result;
	}

	@Override
	public List<TaAvisEcheance> rechercheDocumentEtat(Date dateDeb, Date datefin,
			String codeEtat, String codeTiers) {
		List<TaAvisEcheance> result = null;
		Query query = null;
		if(codeEtat!=null) {
			query = getEntityManager().createNamedQuery(TaAvisEcheance.QN.FIND_BY_ETAT_TIERS_DATE);
			query.setParameter(1, codeTiers);
			query.setParameter(2, dateDeb);
			query.setParameter(3, datefin);
			query.setParameter(4, codeEtat);		
		} else {
			query = getEntityManager().createNamedQuery(TaAvisEcheance.QN.FIND_BY_ETAT_TIERS_ENCOURS_DATE);
			query.setParameter(1, codeTiers);
			query.setParameter(2, datefin);
		}
		
		result = query.getResultList();
		return result;
	}
	
	public List<TaAvisEcheance> rechercheDocumentTAbonnement(Date dateDeb, Date datefin,
			String codeTAbonnement, String codeTiers , BigDecimal montantDepart, boolean nonTransforme) {
		boolean accept=true;
		List<TaAvisEcheance> result = new LinkedList<TaAvisEcheance>();
		List<TaAvisEcheance> temp = null;
		Query query = null;
		if(codeTAbonnement!=null)codeTAbonnement="%";
		if(codeTiers==null)codeTiers="%";            //*
		String querys="select a from TaAvisEcheance a  join a.lignes la  join la.taLEcheance ech " +
				" join a.taTiers t " +
				" where t.codeTiers like ? " +
				" and  exists(select le from TaLEcheance le join le.taAbonnement a join a.taTAbonnement ta  " +
				" join a.taSupportAbon sa where " +
				"(le.debutPeriode between ? and ? )and(le.finPeriode between ? and ? ) and le.idLEcheance=ech.idLEcheance" +
				" and ta.codeTAbonnement like ? and sa.inactif=0) and a.netHtCalc >=?  and t.actifTiers=1" +
				" order by a.codeDocument";
		
		// 
		
		
		//			query = getEntityManager().createNamedQuery(TaAvisEcheance.QN.FIND_BY_TABONNEMENT_TIERS_DATE);
		query = getEntityManager().createQuery(querys);
		query.setParameter(1, codeTiers);
		query.setParameter(2, dateDeb);
		query.setParameter(3, datefin);
		query.setParameter(4, dateDeb);
		query.setParameter(5, datefin);			
		query.setParameter(6, codeTAbonnement);	
		query.setParameter(7, montantDepart);	
		temp = query.getResultList();
		for (TaAvisEcheance taAvisEcheance : temp) {
			accept=true;
			if(nonTransforme){
				for (TaRDocument rdoc : taAvisEcheance.getTaRDocuments()) {
					if(accept)accept=rdoc.getTaFacture()==null;
					if(accept && rdoc.getTaPrelevement()!=null){
						TaPrelevement prel = rdoc.getTaPrelevement(); 
						if(prel!=null){
							for (TaRDocument rdocPrel : prel.getTaRDocuments()) {
								if(accept)accept=rdocPrel.getTaFacture()==null;
							}
						}
					}
				}
			}
			if(!result.contains(taAvisEcheance)&& accept)result.add(taAvisEcheance);
		}
		return result;
	}	


	
	public class InfosSupport {
		String email;
		String groupe;
		Integer telechargement;
		
		public InfosSupport() {
			super();
		}

		public String getEmail() {
			return email;
		}

		public void setEmail(String email) {
			this.email = email;
		}

		public String getGroupe() {
			return groupe;
		}

		public void setGroupe(String groupe) {
			this.groupe = groupe;
		}

		public Integer getTelechargement() {
			return telechargement;
		}

		public void setTelechargement(Integer telechargement) {
			this.telechargement = telechargement;
		}

		
	}
	
	




	public InfosSupport recupInfosSupport(Integer idDocument){
		InfosSupport infosSupport=new InfosSupport();
		String groupeCogere = null;
		TaSupportAbonDAO daoSupport=new TaSupportAbonDAO();
		TaSupportAbon support=null;
		LinkedList<String> listeEmail = new LinkedList<String>();
		String jpql = " select tlf from TaLAvisEcheance tlf join tlf.taLEcheance le  join le.taAbonnement a"+ 
				" join a.taSupportAbon ts  where tlf.taDocument.idDocument = "+idDocument ;
		Query query = null;		
		query = getEntityManager().createQuery(jpql);
		List<TaLAvisEcheance> result = new LinkedList<TaLAvisEcheance>();
		List<TaLAvisEcheance> temp = query.getResultList();
		for (TaLAvisEcheance taAvisLEcheance : temp) {

			support=daoSupport.findByCode(taAvisLEcheance.getTaLEcheance().getTaAbonnement().getTaSupportAbon().getCodeSupportAbon() );
			if(support instanceof TaLicenceEpicea ||
					support instanceof TaLicenceSara ||
					support instanceof TaLicenceBdg ){
				TaLicenceLogiciel logiciel =(TaLicenceLogiciel) support;
				
//					if(logiciel.getTaUtilisateur()!=null && 
//							logiciel.getTaUtilisateur().getEmail()!=null){
//					if(infosSupport.email=="" || infosSupport.email==null)listeEmail.add(logiciel.getTaUtilisateur().getEmail());
//					else 
//					if(infosSupport.email!=null && infosSupport.email!="" && !listeEmail.contains(logiciel.getTaUtilisateur().getEmail()))			
//						listeEmail.add(logiciel.getTaUtilisateur().getEmail());
//					}

				    TaTiers tiers = taAvisLEcheance.getTaLEcheance().getTaAbonnement().getTaSupportAbon().getTaTiers();
				   for (TaEmail email : tiers.getTaEmails()) {
					if(email.getTaTEmail()!=null && email.getTaTEmail().getIdTEmail()==2){
						infosSupport.email=email.getAdresseEmail();
					}
				}
				    if((logiciel.getGroupe()!=null && 
							logiciel.getGroupe().getCodeFamille()!=null)){			
							 if(infosSupport.groupe=="" || infosSupport.groupe==null){
								 infosSupport.groupe=logiciel.getGroupe().getCodeFamille();
							 	if(infosSupport.groupe!=null && infosSupport.groupe.length()>=6 &&
							 			infosSupport.groupe.toLowerCase().substring(0, 6).equals("cogere"))groupeCogere="cogere";
							 }
//							else if(infosSupport.groupe!=null && infosSupport.groupe!="" && groupeCogere==null && logiciel.
//							getGroupe().getCodeFamille()!=(infosSupport.groupe)){
//								infosSupport.groupe="*";
//								}
							 infosSupport.groupe=groupeCogere;
							}
//					if((logiciel.getTelechargement()!=null)){
//						if(infosSupport.telechargement==null || infosSupport.telechargement==0){
//							infosSupport.telechargement=logiciel.getTelechargement();
//							}
//						}			
				}
//			infosSupport.email="";	
//			for (String lemail : listeEmail) {
//				if (infosSupport.email!=null || infosSupport.email.equals(""))
//					infosSupport.email=infosSupport.email+lemail;
//					else infosSupport.email=infosSupport.email+";"+lemail;
//			}
			
			infosSupport.telechargement=LibConversion.booleanToInt(!(infosSupport.email==null || infosSupport.email.isEmpty()));

			}
	
	return infosSupport;		

	}


	public LinkedList<TaAvisEcheance> rechercheAvisTelechargement(Date dateDeb, Date dateFin,Boolean telechargement,String groupe,Boolean detail) {
		int download=LibConversion.booleanToInt(telechargement);
		LinkedList<TaAvisEcheance> result = new LinkedList<TaAvisEcheance>();
		List<TaAvisEcheance> tmp ; 

		Map<String,LinkedList<TaAvisEcheance>> listeFinale = new LinkedHashMap<String, LinkedList<TaAvisEcheance>>();

		TaFamilleTiersDAO daoFamilleTiers = new TaFamilleTiersDAO();
		TaFamilleTiers groupeCogere = daoFamilleTiers.rechercheFamilleCogere();

		List<TaAvisEcheance> listeTemp = new LinkedList<TaAvisEcheance>();
		String requete="";
		String comparaison ="like";
		String codeFamille="%";


		if(groupe != null && !groupe.equals("autres")){
			codeFamille=groupe;
			comparaison ="like";
		}
		else if(groupe != null && groupe.equals("autres")){
			comparaison ="<>";
			codeFamille=groupeCogere.getCodeFamille();
		}

		//		if(telechargement){
		if(groupe != null && !groupe.equals("autres"))
			requete=" select av from TaAvisEcheance av where   av.dateDocument between ? and ?  " +
					" and (exists(select l from TaLicenceEpicea l join l.groupe g where l.taTiers=av.taTiers  and  g.codeFamille "+comparaison+" '"+codeFamille+"')" +
					" or exists(select l from TaLicenceBdg l join l.groupe g where l.taTiers=av.taTiers  and  g.codeFamille "+comparaison+" '"+codeFamille+"')" +
					" or exists(select l from TaLicenceSara l join l.groupe g where l.taTiers=av.taTiers  and  g.codeFamille "+comparaison+" '"+codeFamille+"'))"  ;
		else
			requete=" select av from TaAvisEcheance av where   av.dateDocument between ? and ?  " +
					" and (exists(select l from TaLicenceEpicea l left join l.groupe g where l.taTiers=av.taTiers  and  ((g is null) or (g.codeFamille "+comparaison+" '"+codeFamille+"')))" +
					" or exists(select l from TaLicenceBdg l left join l.groupe g where l.taTiers=av.taTiers  and  ((g is null) or (g.codeFamille "+comparaison+" '"+codeFamille+"')))" +
					" or exists(select l from TaLicenceSara l left join l.groupe g where l.taTiers=av.taTiers  and  ((g is null) or( g.codeFamille "+comparaison+" '"+codeFamille+"'))))" ;
					
		
//		if(download==0){
//			requete+=" and(not exists(select l from TaLicenceEpicea l where l.telechargement=1)" +
//					 " and not exists(select l from TaLicenceBdg l where l.telechargement=1)" +
//					 " and not exists(select l from TaLicenceSara l where l.telechargement=1))";
//		}else{
//			requete+=" and(exists(select l from TaLicenceEpicea l where l.telechargement=1)" +
//					 " or exists(select l from TaLicenceBdg l where l.telechargement=1)" +
//					 " or exists(select l from TaLicenceSara l where l.telechargement=1))";
//		}

		requete+=" order by av.taTiers.codeTiers" ;

		Query query = getEntityManager().createQuery(requete);
		//		query.setParameter(1,"%");
		query.setParameter(1, dateDeb, TemporalType.DATE);
		query.setParameter(2, dateFin, TemporalType.DATE);

		tmp = query.getResultList();
		
		for (TaAvisEcheance taAvisEcheance : tmp) {
			InfosSupport infos= recupInfosSupport(taAvisEcheance.getIdDocument());
			if(infos.getTelechargement()==download)result.add(taAvisEcheance);
		}
//		if(download==0){
//			for (TaAvisEcheance taAvisEcheance : tmp) {
//				int T=0;
//				for (TaLAvisEcheance talAvisEcheance : taAvisEcheance.getLignes()) {
//					if(talAvisEcheance.getTaLEcheance()!=null && talAvisEcheance.getTaLEcheance().getTaAbonnement().getTaSupportAbon().getTaTSupport().getIdTSupport() ==1)
//						T=((TaLicenceEpicea)talAvisEcheance.getTaLEcheance().getTaAbonnement().getTaSupportAbon()).getTelechargement();
//					else if(talAvisEcheance.getTaLEcheance()!=null && talAvisEcheance.getTaLEcheance().getTaAbonnement().getTaSupportAbon().getTaTSupport().getIdTSupport() ==2)
//						T=((TaLicenceBdg)talAvisEcheance.getTaLEcheance().getTaAbonnement().getTaSupportAbon()).getTelechargement();
//					else if(talAvisEcheance.getTaLEcheance()!=null && talAvisEcheance.getTaLEcheance().getTaAbonnement().getTaSupportAbon().getTaTSupport().getIdTSupport() ==3)
//						T=((TaLicenceSara)talAvisEcheance.getTaLEcheance().getTaAbonnement().getTaSupportAbon()).getTelechargement(); 
//					if(T==1)break;
//				}
//				if(T==0)result.add(taAvisEcheance);
//			}
//		}else{
//			for (TaAvisEcheance taAvisEcheance : tmp) {
//				int T=1;
//				for (TaLAvisEcheance talAvisEcheance : taAvisEcheance.getLignes()) {
//					if(talAvisEcheance.getTaLEcheance()!=null && talAvisEcheance.getTaLEcheance().getTaAbonnement().getTaSupportAbon().getTaTSupport().getIdTSupport() ==1)
//						T=((TaLicenceEpicea)talAvisEcheance.getTaLEcheance().getTaAbonnement().getTaSupportAbon()).getTelechargement();
//					else if(talAvisEcheance.getTaLEcheance()!=null && talAvisEcheance.getTaLEcheance().getTaAbonnement().getTaSupportAbon().getTaTSupport().getIdTSupport() ==2)
//						T=((TaLicenceBdg)talAvisEcheance.getTaLEcheance().getTaAbonnement().getTaSupportAbon()).getTelechargement();
//					else if(talAvisEcheance.getTaLEcheance()!=null && talAvisEcheance.getTaLEcheance().getTaAbonnement().getTaSupportAbon().getTaTSupport().getIdTSupport() ==3)
//						T=((TaLicenceSara)talAvisEcheance.getTaLEcheance().getTaAbonnement().getTaSupportAbon()).getTelechargement(); 
//					if(T==0)break;
//				}
//				if(T==1)result.add(taAvisEcheance);
//			}
//		}
		
		if(detail){
			List<TaAvisEcheance> listeAvisVersion;
			String variableVersion="";
			String separateur="";
			for (TaAvisEcheance doc :result) {
				variableVersion="";
				separateur="";
				for (TaLAvisEcheance ligne : doc.getLignes()) {
					if(ligne.getTaArticle()!=null && ligne.getTaArticle().getTaFamille()!=null && 
							(ligne.getTaArticle().getTaFamille().getIdFamille()==21 || ligne.getTaArticle().getTaFamille().getIdFamille()==44)){
						if(!variableVersion.equals(""))separateur="||";
						variableVersion+=separateur+ligne.getTaArticle().getCodeArticle();
					}
				}
				//si variableVersion remplie, on la cherche dans la listeFinale ou on la rajoute
				if(!variableVersion.equals("")){
					if(!listeFinale.containsKey(variableVersion))listeFinale.put(variableVersion, new LinkedList<TaAvisEcheance>());
					//quand récupérer tous les codes articles de l'avis
					listeFinale.get(variableVersion).add(doc);
				}

			}
			result.clear();
			for (String version : listeFinale.keySet()) {
				if(listeFinale.get(version).size()>0 && listeFinale.get(version).get(0)!=null)
					for (TaAvisEcheance taAvisEcheance : listeFinale.get(version)) {
						System.out.println(LibConversion.booleanToInt(telechargement)+";"+groupe+";"+version+";"+taAvisEcheance.getTaTiers().getCodeTiers());
						result.add(taAvisEcheance);
					}
				logger.debug("Téléchargement : "+LibConversion.booleanToStringFrancais(telechargement)+" - Groupe : "+groupe+" - Version : "+version+" - Nb : "+listeFinale.get(version).size()+" Premier code tiers : "+listeFinale.get(version).get(0).getTaTiers().getCodeTiers());
			}
		}
//		for (TaAvisEcheance taAvisEcheance : result) {
//			System.out.println(taAvisEcheance.getTaTiers().getCodeTiers());
//		}
		return result;
	}
	



	/**
	 * Recherche les factures entre 2 dates light
	 * @param dateDeb - date de début
	 * @param dateFin - date de fin
	 * @return String[] - tableau contenant les IDs des factures entre ces 2 dates (null en cas d'erreur)
	 */
	//public String[] rechercheFacture(Date dateDeb, Date dateFin) {
	public List<Object[]> rechercheDocument(Date dateDeb, Date dateFin,Boolean light) {
		List<Object[]> result = null;
		Query query = null;
		if(light)
			query =getEntityManager().createNamedQuery(TaAvisEcheance.QN.FIND_BY_DATE_LIGHT);
		else query = getEntityManager().createNamedQuery(TaAvisEcheance.QN.FIND_BY_DATE);
		query.setParameter(1,"%");
		query.setParameter(2, dateDeb, TemporalType.DATE);
		query.setParameter(3, dateFin, TemporalType.DATE);
		result = query.getResultList();
		
		
		return result;
		
	}
	
	@Override
	public List<Object[]> rechercheDocumentLight(Date dateDeb, Date dateFin,String codeTiers) {
		List<Object[]> result = null;
		Query query = null;
		query = getEntityManager().createNamedQuery(TaAvisEcheance.QN.FIND_BY_DATE_LIGHT);
		if(codeTiers==null || codeTiers.equals(""))codeTiers="%";
		query.setParameter(1, codeTiers);
		query.setParameter(2, dateDeb, TemporalType.DATE);
		query.setParameter(3, dateFin, TemporalType.DATE);
		result = query.getResultList();
		return result;
	}
	@Override
	public List<Object[]> rechercheDocumentLight(String codeDoc, String codeTiers) {
			List<Object[]> result = null;
			Query query = getEntityManager().createNamedQuery(TaAvisEcheance.QN.FIND_BY_TIERS_AND_CODE_LIGHT);
			query.setParameter(1,"%");
			query.setParameter(2, codeDoc+"%");
			result = query.getResultList();
			return result;

		}

}

