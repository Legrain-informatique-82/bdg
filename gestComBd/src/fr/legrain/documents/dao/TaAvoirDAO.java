package fr.legrain.documents.dao;

// Generated Apr 7, 2009 3:27:24 PM by Hibernate Tools 3.2.0.CR1

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TemporalType;

import org.apache.log4j.Logger;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ui.PlatformUI;

import fr.legrain.articles.dao.TaPrix;
import fr.legrain.gestCom.Appli.AbstractApplicationDAO;
import fr.legrain.gestCom.Appli.Const;
import fr.legrain.gestCom.Module_Document.IDocumentTiers;
import fr.legrain.tiers.dao.TaTiers;

/**
 * Home object for domain model class TaAvoir.
 * @see fr.legrain.documents.dao.TaAvoir
 * @author Hibernate Tools
 */
public class TaAvoirDAO extends AbstractApplicationDAO<TaAvoir> implements IDocumentTiersDAO<TaAvoir>,IDocumentTiersStatistiquesDAO<TaAvoir> {

	//	private static final Log log = LogFactory.getLog(TaAvoirDAO.class);
	static Logger logger = Logger.getLogger(TaAvoirDAO.class);

	private String defaultJPQLQuery = "select a from TaAvoir a";

	public TaAvoirDAO(){
		this(null);
	}

	public TaAvoirDAO(EntityManager em){
		if(em!=null) {
			setEm(em);
		}
		//		champIdTable=ctrlGeneraux.getID_TABLE(TaAvoir.class.getSimpleName());
		initChampId(TaAvoir.class);
		setJPQLQuery(defaultJPQLQuery);
		setListeChampMaj(Const.C_FICHIER_INI_CHAMPMAJ);
		initNomTable(new TaAvoir());
	}

	public TaAvoir refresh(TaAvoir detachedInstance) {
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
			//			}
			//			
			//			for (TaRAvoir a : detachedInstance.getTaRAvoirs()) {
			//				session.evict(a.getTaFacture().getTaRAvoirs());
			//				session.evict(a.getTaFacture());
			//			}
			//			session.evict(detachedInstance.getTaRAcomptes());
			//			session.evict(detachedInstance.getTaRAvoirs());
			//			session.evict(detachedInstance);
			//
			//			detachedInstance = getEntityManager().find(TaAvoir.class, detachedInstance.getIdDocument());
			detachedInstance.setLegrain(true);
			detachedInstance.calculLignesTva();
			return detachedInstance;

		} catch (RuntimeException re) {
			logger.error("refresh failed", re);
			throw re;
		}
	}

	public void persist(TaAvoir transientInstance) {
		logger.debug("persisting TaAvoir instance");
		try {
			getEntityManager().persist(transientInstance);
			logger.debug("persist successful");
		} catch (RuntimeException re) {
			logger.error("persist failed", re);
			throw re;
		}
	}

	public void remove(TaAvoir persistentInstance) {
		logger.debug("removing TaAvoir instance");
		try {
			getEntityManager().remove(persistentInstance);
			logger.debug("remove successful");
		} catch (RuntimeException re) {
			logger.error("remove failed", re);
			throw re;
		}
	}

	public TaAvoir merge(TaAvoir detachedInstance) {
		logger.debug("merging TaAvoir instance");
		try {
			TaAvoir result = getEntityManager().merge(detachedInstance);
			logger.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			logger.error("merge failed", re);
			throw re;
		}
	}

	public TaAvoir findById(int id) {
		logger.debug("getting TaAvoir instance with id: " + id);
		try {
			TaAvoir instance = getEntityManager().find(TaAvoir.class, id);
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
			Query query = getEntityManager().createQuery("select count(f) from TaAvoir f where f.codeDocument='"+code+"'");
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
	
	
	public TaAvoir findByCode(String code) {
		logger.debug("getting TaAvoir instance with code: " + code);
		try {
			Query query = getEntityManager().createQuery("select a from TaAvoir a " +
					"  left join FETCH a.lignes l"+
					" where a.codeDocument='"+code+"' " +
			" order by l.numLigneLDocument");
			TaAvoir instance = (TaAvoir)query.getSingleResult();
			instance.setLegrain(true);
			logger.debug("get successful");
			return instance;
		} catch (RuntimeException re) {
			logger.error("get failed", re);
			throw re;
		}
	}

	public List<TaAvoir> findByCodeTiersAndDate(String codeTiers, Date debut, Date fin) {
		logger.debug("getting TaAvoir instance with codeTiers: " + codeTiers);
		List<TaAvoir> result = null;
		try {
			Query query = getEntityManager().createNamedQuery(TaAvoir.QN.FIND_BY_TIERS_AND_DATE);
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
		logger.debug("getting TaAvoir instance with codeTiers: " + codeTiers);
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
				+" from TaAvoir f "
				+"left join f.taTiers t "
				+"where  t.codeTiers = ? and f.dateDocument between ? and ? "
				+"group by "+groupBy+",t.codeTiers"
				;

			//			jpql = "select  "
			//			+"cast(extract(year from f.dateDocument)as string), t.codeTiers,"
			//			+"case when extract ( year from f.dateDocument) <= 2001 then  sum(f.netHtCalc/6.55957) else sum(f.netHtCalc) end, "
			//			+"case when extract ( year from f.dateDocument) <= 2001 then  sum(f.netTvaCalc/6.55957) else sum(f.netTvaCalc) end, "
			//			+"case when extract ( year from f.dateDocument) <= 2001 then  sum(f.netTtcCalc/6.55957 ) else sum(f.netTtcCalc) end "
			//			+" from TaAvoir f "
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
	 * Recherche les avoirs entre 2 dates
	 * @param dateDeb - date de début
	 * @param dateFin - date de fin
	 * @return String[] - tableau contenant les IDs des avoirs entre ces 2 dates (null en cas d'erreur)
	 */
	public List<TaAvoir> rechercheDocument(Date dateDeb, Date dateFin) {
		Query query = getEntityManager().createNamedQuery(TaAvoir.QN.FIND_BY_DATE);
		query.setParameter(1,"%");
		query.setParameter(2, dateDeb, TemporalType.DATE);
		query.setParameter(3, dateFin, TemporalType.DATE);
		List<TaAvoir> l = query.getResultList();
		return l;
	}

	
	/**
	 * Recherche les avoirs entre 2 dates ordonnées par date
	 * @param dateDeb - date de début
	 * @param dateFin - date de fin
	 * @return String[] - tableau contenant les IDs des avoirs entre ces 2 dates (null en cas d'erreur)
	 */
	public List<TaAvoir> rechercheDocumentOrderByDate(Date dateDeb, Date dateFin) {
		Query query = getEntityManager().createNamedQuery(TaAvoir.QN.FIND_BY_DATE_PARDATE);
		query.setParameter(1,"%");
		query.setParameter(2, dateDeb, TemporalType.DATE);
		query.setParameter(3, dateFin, TemporalType.DATE);
		List<TaAvoir> l = query.getResultList();
		return l;
	}
	
	/**
	 * Recherche les avoirs entre 2 codes avoirs
	 * @param codeDeb - code de début
	 * @param codeFin - code de fin
	 * @return String[] - tableau contenant les IDs des avoirs entre ces 2 codes (null en cas d'erreur)
	 */
	//public String[] rechercheAvoir(String codeDeb, String codeFin) {
	public List<TaAvoir> rechercheDocument(String codeDeb, String codeFin) {
		//String[] result = null;
		List<TaAvoir> result = null;
		Query query = getEntityManager().createNamedQuery(TaAvoir.QN.FIND_BY_CODE);
		query.setParameter(1,"%");
		query.setParameter(2, codeDeb);
		query.setParameter(3, codeFin);
		List<TaAvoir> l = query.getResultList();
		return l;

	}

	/* (non-Javadoc)
	 * @see fr.legrain.articles.dao.ILgrDAO#selectAll()
	 */
	public List<TaAvoir> selectAll() {
		logger.debug("selectAll TaAvoir");
		try {
			Query ejbQuery = getEntityManager().createQuery(JPQLQuery);
			List<TaAvoir> l = ejbQuery.getResultList();
			logger.debug("selectAll successful");
			return l;
		} catch (RuntimeException re) {
			logger.error("selectAll failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see fr.legrain.articles.dao.ILgrDAO#selectAll()
	 */
	public List<TaAvoir> selectAll(IDocumentTiers taDocument,Date dateDeb,Date dateFin) {
		TaTiers taTiersLoc=null;
		if(taDocument!=null)taTiersLoc=taDocument.getTaTiers();
		logger.debug("selectAll TaAvoir");
		try {
			if(taTiersLoc!=null){				
				if(dateDeb==null)dateDeb=new Date(0);
				if(dateFin==null)dateFin=new Date("01/01/3000");
				
				String requete="select a from TaAvoir a where (" +
				" not exists(select r from TaRAvoir r where r.taAvoir.idDocument=a.id " +
				" and (r.taFacture=?))" +
//				" and exists(select r from TaRAvoir r where r.taAvoir.idDocument<>a.id " +
//				" and r.taFacture.taTiers=?) " +
				" ) and a.taTiers =?"+
				" and a.dateDocument between ? and ?";
				
				Query ejbQuery = getEntityManager().createQuery(requete);
				ejbQuery.setParameter(1, taDocument);
				ejbQuery.setParameter(2, taTiersLoc);
//				ejbQuery.setParameter(3, taTiersLoc);
				ejbQuery.setParameter(3, dateDeb,TemporalType.DATE);
				ejbQuery.setParameter(4, dateFin,TemporalType.DATE);

				List<TaAvoir> l = ejbQuery.getResultList();
				logger.debug("selectAll successful");
				return l;
			}
			return null;
		} catch (RuntimeException re) {
			logger.error("selectAll failed", re);
			throw re;
		}
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

	/* (non-Javadoc)
	 * @see fr.legrain.articles.dao.ILgrDAO#selectAll()
	 */
	public List<TaAvoir> selectAllDisponible(TaFacture taFacture) {
		logger.debug("TaAvoir selectAllDisponible");
		try {
			String requete ="";
			Query ejbQuery =null;
			if(taFacture!=null ){
				requete ="select a from TaAvoir  a " +
				" where a.taTiers=? and (" +
				" not exists(select ra from TaRAvoir ra " +
				"where  ra.taAvoir=a and ra.taFacture is not null) or" +
				" (a.netTtcCalc > (select sum(ra.affectation) from TaRAvoir ra " +
				"where  ra.taAvoir=a and ra.taFacture is not null))) " ;
				ejbQuery = getEntityManager().createQuery(requete);
				ejbQuery.setParameter(1, taFacture.getTaTiers());
			}

			if(taFacture!=null ){
				List<TaAvoir> l = ejbQuery.getResultList();
				logger.debug("selectAllDisponible successful");
				return l;
			}
			return null;
		} catch (RuntimeException re) {
			logger.error("selectAllDisponible failed", re);
			throw re;
		}
	}


	public void messageNonAutoriseModificationRelation(TaAvoir avoir) throws Exception{
		if(!dataSetEnModif()) {
			String message="l'avoir "+avoir.getCodeDocument()+" a été modifié par ailleurs !!!" +
			"\r\nIl doit être rechargé pour y " +
			"apporter de nouvelles modifications.";
			MessageDialog.openWarning(PlatformUI.getWorkbench()
					.getActiveWorkbenchWindow().getShell(),"Attention",
					message);
			throw new Exception();
		}
	}
	
	/**
	 * Classe permettant d'obtenir le ca généré par les avoirs sur une période donnée
	 * @param debut date de début des données
	 * @param fin date de fin des données
	 * @return La requête renvoi le montant des par les avoirs sur la période
	 */
	public List<Object> findCASurPeriode(Date debut, Date fin) {
		logger.debug("getting nombre ca des avoirs");
		List<Object> result = null;
		
		
		try {
			String requete = "";

			requete = "SELECT "
				+" sum(d.netHtCalc)"
				+" FROM TaAvoir d " 
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
	
	/**
	 * Méthode permettant de retourner le chiffre d'affaire total généré
	 * @param debut
	 * @param fin
	 * @param precision
	 * @return
	 */
	public List<Object> findChiffreAffaireTotal(Date debut, Date fin, int precision) {
		logger.debug("getting ChiffreAffaire total");
		List<Object> result = null;
		try {
			String requete = "";
			String groupBy = "";
			String select = "";
			
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
			
			requete = "SELECT "+select+ ", "
			+" case when extract ( year from f.dateDocument) <= 2001 then  sum(f.netHtCalc/6.55957) else sum(f.netHtCalc) end, "
			+" case when extract ( year from f.dateDocument) <= 2001 then  sum(f.netTvaCalc/6.55957) else sum(f.netTvaCalc) end, "
			+" case when extract ( year from f.dateDocument) <= 2001 then  sum(f.netTtcCalc/6.55957 ) else sum(f.netTtcCalc) end "
			+" FROM TaAvoir f " 
			+" where f.dateDocument between ? and ?"
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
	 * Classe permettant d'obtenir de la base de données X clients ordonnés par chiffre d'affaires
	 * @param debut date de début des données
	 * @param fin date de fin des données
	 * @param leNbResult le nombre de résultats à sortir de la BD
	 * Si cet int est positif, il sort les leNbResult meilleurs
	 * Si cet int est négatif, il sort les leNbResult moins bons
	 * @return La requête renvoyée renvoi le CA, le codeTiers, le nom et le CP
	 */
	public List<Object> findMeilleursClientsParCA(Date debut, Date fin, int leNbResult) {
		logger.debug("getting ChiffreAffaire total ordo tiers");
		
		int nbResult = leNbResult; // nombre de résultats à afficher
		String ordre = "DESC";     // Ordre d'affichage
		
		if (nbResult<0){ // On passe en paramètres une valeur négative : on souhaite les x derniers
			nbResult = Math.abs(leNbResult); // On prend la valeur absolue du nombre désiré
			ordre = "ASC";					   // On inverse l'ordre d'affichage
			
		}
		List<Object> result = null;
		try {
			String requete = "";

			
			requete = "SELECT "
			+" sum(f.mtHtCalc)"
			+", t.codeTiers"
			+", t.nomTiers"
			+", a.codepostalAdresse"
			+" FROM TaAvoir f " 
			+" left join f.taTiers t"
			+" left join t.taAdresse a"
			+" where f.dateDocument between ? and ?"
			+" group by t.codeTiers, t.nomTiers, a.codepostalAdresse"
			+" order by sum(f.mtHtCalc)"+ordre;
			Query query = getEntityManager().createQuery(requete);
			query.setParameter(1, debut);
			query.setParameter(2, fin);
			query.setMaxResults(nbResult);
			result = query.getResultList();
			return result;
		} catch (RuntimeException re) {
			logger.error("get failed", re);
			throw re;
		}
	}
	
	
	/**
	 * Classe permettant d'obtenir de la base de données X articles ordonnés par chiffre d'affaires
	 * @param debut date de début des données
	 * @param fin date de fin des données
	 * @param leNbResult le nombre de résultats à sortir de la BD
	 * Si cet int est positif, il sort les leNbResult meilleurs
	 * Si cet int est négatif, il sort les leNbResult moins bons
	 * @return La requête renvoyée renvoi le CA, le codeTiers, le nom et le CP
	 */
	public List<Object> findMeilleursArticlesParCA(Date debut, Date fin, int leNbResult) {
		logger.debug("getting ChiffreAffaire total ordo articles");
		
		int nbResult = leNbResult; // nombre de résultats à afficher
		String ordre = "DESC";     // Ordre d'affichage
		
		if (nbResult<0){ // On passe en paramètres une valeur négative : on souhaite les x derniers
			nbResult = Math.abs(leNbResult); // On prend la valeur absolue du nombre désiré
			ordre = "ASC";					   // On inverse l'ordre d'affichage
			
		}
		List<Object> result = null;
		try {
			String requete = "";

			
			requete = "SELECT a.codeArticle"
			+", a.libellecArticle"
			+", p.prixPrix"
			+", sum(l.qteLDocument)"
			+", sum(l.mtHtLDocument)"
			+" FROM TaLAvoir l " 
			+" left join l.taDocument f"
			+" left join l.taArticle a"
			+" left join a.taPrix p"
			+" where f.dateDocument between ? and ?"
			+" group by a.codeArticle, a.libellecArticle, p.prixPrix"
			+" order by sum(l.mtHtLDocument)"+ordre;
			Query query = getEntityManager().createQuery(requete);
			query.setParameter(1, debut);
			query.setParameter(2, fin);
			query.setMaxResults(nbResult);
			result = query.getResultList();
			return result;
		} catch (RuntimeException re) {
			logger.error("get failed", re);
			throw re;
		}
	}
	
	/**
	 * Recherche les avoirs entre 2 dates
	 * @param dateDeb - date de début
	 * @param dateFin - date de fin
	 * @return String[] - tableau contenant les IDs des avoirs entre ces 2 dates (null en cas d'erreur)
	 */
	public List<TaAvoir> rechercheDocumentNonExporte(Date dateDeb, Date dateFin,boolean parDate) {
		List<TaAvoir> result = null;
		Query query = null;
		if(parDate)query = getEntityManager().createNamedQuery(TaAvoir.QN.FIND_BY_DATE_NON_EXPORT_PARDATE);
		else
		query = getEntityManager().createNamedQuery(TaAvoir.QN.FIND_BY_DATE_NON_EXPORT);
		query.setParameter(1,"%");
		query.setParameter(2, dateDeb, TemporalType.DATE);
		query.setParameter(3, dateFin, TemporalType.DATE);
		result = query.getResultList();
		
		
		return result;
		
	}
	
	/**
	 * Recupere la liste des avoirs exportees ou non exportees
	 * @param export - vrai pour l'avoir deja exportees, faux pour celles non exportees
	 * @return
	 */
	public List<TaAvoir> findByExport(boolean export) {
		try {
			Query query = getEntityManager().createQuery("select a from TaAvoir a " +
					"where a.export=?");
			if(export)
				query.setParameter(1, 1);
			else 
				query.setParameter(1, 0);
			List<TaAvoir> l = query.getResultList();
			return l;
		} catch (RuntimeException re) {
			logger.error("findByExport failed", re);
			throw re;
		}
	}

	@Override
	public List<TaAvoir> rechercheDocument(String codeDeb, String codeFin,
			String codeTiers) {
		return rechercheDocument(codeDeb, codeFin, codeTiers,null);
	}

	@Override
	public List<TaAvoir> rechercheDocument(Date dateDeb, Date dateFin,
			String codeTiers) {
		return rechercheDocument(dateDeb, dateFin, codeTiers,null);
		
	}

	@Override
	public List<TaAvoir> rechercheDocument(Date dateDeb, Date dateFin,
			String codeTiers, Boolean export) {
		List<TaAvoir> result = null;
		Query query = null;
		if(export!=null && export)query = getEntityManager().createNamedQuery(TaAvoir.QN.FIND_BY_DATE_EXPORT);
		else if(export!=null && export==false)query = getEntityManager().createNamedQuery(TaAvoir.QN.FIND_BY_DATE_NON_EXPORT);
		else query = getEntityManager().createNamedQuery(TaAvoir.QN.FIND_BY_TIERS_AND_DATE);
		if(codeTiers==null || codeTiers.equals(""))codeTiers="%";
		query.setParameter(1, codeTiers);
		query.setParameter(2, dateDeb, TemporalType.DATE);
		query.setParameter(3, dateFin, TemporalType.DATE);
		result = query.getResultList();
		return result;
	}

	@Override
	public List<TaAvoir> rechercheDocument(String codeDeb, String codeFin,
			String codeTiers, Boolean export) {
		List<TaAvoir> result = null;
		Query query = null;
		if(export!=null && export)query = getEntityManager().createNamedQuery(TaAvoir.QN.FIND_BY_CODE_EXPORT);
		else if(export!=null && export==false)query = getEntityManager().createNamedQuery(TaAvoir.QN.FIND_BY_CODE_NON_EXPORT);
		else query = getEntityManager().createNamedQuery(TaAvoir.QN.FIND_BY_TIERS_AND_CODE);
		if(codeTiers==null || codeTiers.equals(""))codeTiers="%";
		query.setParameter(1, codeTiers);
		query.setParameter(2, codeDeb);
		query.setParameter(3, codeFin);
		result = query.getResultList();
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
			query =getEntityManager().createNamedQuery(TaAvoir.QN.FIND_BY_DATE_LIGHT);
		else query = getEntityManager().createNamedQuery(TaAvoir.QN.FIND_BY_DATE);
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
		query = getEntityManager().createNamedQuery(TaAvoir.QN.FIND_BY_DATE_LIGHT);
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
			Query query = getEntityManager().createNamedQuery(TaAvoir.QN.FIND_BY_TIERS_AND_CODE_LIGHT);
			query.setParameter(1,"%");
			query.setParameter(2, codeDoc+"%");
			result = query.getResultList();
			return result;

		}

}

