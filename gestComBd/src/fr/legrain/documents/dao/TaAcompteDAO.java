package fr.legrain.documents.dao;

// Generated Apr 7, 2009 3:27:24 PM by Hibernate Tools 3.2.0.CR1

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TemporalType;

import org.apache.log4j.Logger;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ui.PlatformUI;

import fr.legrain.articles.dao.TaFamille;
import fr.legrain.articles.dao.TaPrix;
import fr.legrain.articles.dao.TaUnite;
import fr.legrain.gestCom.Appli.AbstractApplicationDAO;
import fr.legrain.gestCom.Appli.Const;
import fr.legrain.gestCom.Module_Document.IDocumentTiers;
import fr.legrain.lib.data.LibConversion;
import fr.legrain.tiers.dao.TaTiers;

/**
 * Home object for domain model class TaAcompte.
 * @see fr.legrain.documents.dao.TaAcompte_old
 * @author Hibernate Tools
 */
public class TaAcompteDAO extends AbstractApplicationDAO<TaAcompte> implements IDocumentTiersDAO<TaAcompte> {

//	private static final Log log = LogFactory.getLog(TaAcompteDAO.class);
	static Logger logger = Logger.getLogger(TaAcompteDAO.class);
	private String defaultJPQLQuery = "select a from TaAcompte a";
	
	public TaAcompteDAO(){
		this(null);
	}
	
	public TaAcompteDAO(EntityManager em){
		if(em!=null) {
			setEm(em);
		}
//		champIdTable=ctrlGeneraux.getID_TABLE(TaAcompte.class.getSimpleName());
		initChampId(TaAcompte.class);
		setJPQLQuery(defaultJPQLQuery);
		setListeChampMaj(Const.C_FICHIER_INI_CHAMPMAJ);
		initNomTable(new TaAcompte());
	}
	public TaAcompte refresh(TaAcompte detachedInstance) {
        logger.debug("refresh instance");
        try {
        	detachedInstance.setLegrain(false);
        	getEntityManager().refresh(detachedInstance);
//            detachedInstance.setLegrain(false);
//            org.hibernate.Session session = (org.hibernate.Session) getEntityManager().getDelegate();
//
//            for (TaRAcompte a : detachedInstance.getTaRAcomptes()) {
//
//                 if(a.getTaDevis()!=null) {
//                	 for (TaRAcompte b : a.getTaDevis().getTaRAcomptes()) {
//                		 session.evict(b.getTaAcompte());
//                		 session.evict(b);
//                	 }
//                	 session.evict(a.getTaDevis().getTaRAcomptes());
//                 }
//                 if(a.getTaBoncde()!=null) {
//                	 for (TaRAcompte b : a.getTaBoncde().getTaRAcomptes()) {
//                		 session.evict(b.getTaAcompte());
//                		 session.evict(b);
//                	 }
//                	 session.evict(a.getTaBoncde().getTaRAcomptes());
//                 }
//                 if(a.getTaProforma()!=null) {
//                	 for (TaRAcompte b : a.getTaProforma().getTaRAcomptes()) {
//                		 session.evict(b.getTaAcompte());
//                		 session.evict(b);
//                	 }
//                	 session.evict(a.getTaProforma().getTaRAcomptes());
//                 } 
//                 if(a.getTaFacture()!=null) {
//                	 for (TaRAcompte b : a.getTaFacture().getTaRAcomptes()) {
//                		 session.evict(b.getTaAcompte());
//                		 session.evict(b);
//                	 }
//                	 session.evict(a.getTaFacture().getTaRAcomptes());
//                 }
//                 if(a.getTaBonliv()!=null) {
//                	 for (TaRAcompte b : a.getTaBonliv().getTaRAcomptes()) {
//                		 session.evict(b.getTaAcompte());
//                		 session.evict(b);
//                	 }
//                	 session.evict(a.getTaBonliv().getTaRAcomptes());
//                 }
//                 if(a.getTaAvoir()!=null) {
//                	 for (TaRAcompte b : a.getTaAvoir().getTaRAcomptes()) {
//                		 session.evict(b.getTaAcompte());
//                		 session.evict(b);
//                	 }
//                	 session.evict(a.getTaAvoir().getTaRAcomptes());
//                 }
//                 if(a.getTaApporteur()!=null) {
//                	 for (TaRAcompte b : a.getTaApporteur().getTaRAcomptes()) {
//                		 session.evict(b.getTaAcompte());
//                		 session.evict(b);
//                	 }
//                	 session.evict(a.getTaApporteur().getTaRAcomptes());
//                 }
//                 if(a.getTaPrelevement()!=null) {
//                	 for (TaRAcompte b : a.getTaPrelevement().getTaRAcomptes()) {
//                		 session.evict(b.getTaAcompte());
//                		 session.evict(b);
//                	 }
//                	 session.evict(a.getTaPrelevement().getTaRAcomptes());
//                 }
//                 session.evict(a.getTaDevis());
//                 session.evict(a.getTaApporteur());
//                 session.evict(a.getTaAvoir());
//                 session.evict(a.getTaBoncde());
//                 session.evict(a.getTaBonliv());
//                 session.evict(a.getTaFacture());
//                 session.evict(a.getTaProforma());
//                 session.evict(a.getTaPrelevement());
//              	for (TaRAcompte c : a.getTaAcompte().getTaRAcomptes()) {
//            		session.evict(c);
//            	}
//            	session.evict(a.getTaAcompte().getTaRAcomptes());
//            	session.evict(a.getTaAcompte());
//                 
//                 session.evict(a);
//            }
//            session.evict(detachedInstance.getTaRAcomptes());
//            session.evict(detachedInstance);
//            
//            detachedInstance = getEntityManager().find(TaAcompte.class, detachedInstance.getIdDocument());
            detachedInstance.setLegrain(true);
            detachedInstance.calculLignesTva();
            return detachedInstance;
        } catch (RuntimeException re) {
            logger.error("refresh failed", re);
            throw re;
        }
    } 
	public void persist(TaAcompte transientInstance) {
		logger.debug("persisting TaAcompte instance");
		try {
			getEntityManager().persist(transientInstance);
			logger.debug("persist successful");
		} catch (RuntimeException re) {
			logger.error("persist failed", re);
			throw re;
		}
	}

	public void remove(TaAcompte persistentInstance) {
		logger.debug("removing TaAcompte instance");
		try {
			getEntityManager().remove(persistentInstance);
			logger.debug("remove successful");
		} catch (RuntimeException re) {
			logger.error("remove failed", re);
			throw re;
		}
	}
	
	public void annuler(TaAcompte persistentInstance) throws Exception{
		persistentInstance.setLegrain(false);
		List<TaLAcompte> listTemp=new ArrayList<TaLAcompte>(0);
		for (Object ligne : persistentInstance.getLignes()) {
			if(((TaLAcompte)ligne).getIdLDocument()==0)
				listTemp.add((TaLAcompte)ligne);				
		}
		for (TaLAcompte taLAcompte : listTemp) {
			persistentInstance.getLignes().remove(taLAcompte);
		}
		super.annuler(persistentInstance);
	}
	

	public TaAcompte merge(TaAcompte detachedInstance) {
		logger.debug("merging TaAcompte instance");
		try {
			TaAcompte result = getEntityManager().merge(detachedInstance);
			logger.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			logger.error("merge failed", re);
			throw re;
		}
	}

	public TaAcompte findById(int id) {
		logger.debug("getting TaAcompte instance with id: " + id);
		try {
			TaAcompte instance = getEntityManager().find(TaAcompte.class, id);
			logger.debug("get successful");
			return instance;
		} catch (RuntimeException re) {
			logger.error("get failed", re);
			throw re;
		}
	}
	
	public TaAcompte findByCode(String code) {
		logger.debug("getting TaAcompte instance with code: " + code);
		try {
			Query query = getEntityManager().createQuery("select a from TaAcompte a " +
					"  left join FETCH a.lignes l"+
//					"  left join FETCH a.taRAcomptesDevis RDevis"+
//					"  left join FETCH a.taRAcomptesBonCde RBonCde"+
//					"  left join FETCH a.taRAcomptesProforma RProforma"+
//					"  left join FETCH a.taRAcomptesFacture RFacture"+
					" where a.codeDocument='"+code+"' " +
							" order by l.numLigneLDocument");
			TaAcompte instance = (TaAcompte)query.getSingleResult();
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
	public List<TaAcompte> selectAll() {
		logger.debug("TaAcompte selectAll");
		try {
			Query ejbQuery = getEntityManager().createQuery(JPQLQuery);
			List<TaAcompte> l = ejbQuery.getResultList();
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
	public List<TaAcompte> selectAllDisponible(TaFacture taFacture) {
		logger.debug("TaAcompte selectAllDisponible");
		try {
			String requete ="";
			Query ejbQuery =null;
			if(taFacture!=null /*&& taFacture.getIdDocument()!=0*/){
			requete ="select a from TaAcompte  a " +
			" where a.taTiers=? and (" +
//			" not exists(select ra from TaRAcompte ra " +
//					"where  ra.taAcompte=a and ra.taFacture =?) and(  " +
			" not exists(select ra from TaRAcompte ra " +
					"where  ra.taAcompte=a and ra.taFacture is not null) or" +
			" (a.netTtcCalc > (select sum(ra.affectation) from TaRAcompte ra " +
					"where  ra.taAcompte=a and ra.taFacture is not null))) " ;
			ejbQuery = getEntityManager().createQuery(requete);
			ejbQuery.setParameter(1, taFacture.getTaTiers());
//			ejbQuery.setParameter(2, taFacture);
			}
//			else{
//				requete ="select a from TaAcompte  a " +
//				" where a.taTiers=? and" +
//				" not exists(select ra from TaRAcompte ra " +
//						"where  ra.taAcompte=a and ra.taFacture is not null) or" +
//				" (a.netTtcCalc > (select sum(ra.affectation) from TaRAcompte ra " +
//						"where  ra.taAcompte=a and ra.taFacture is not null)) " ;	
//				ejbQuery = getEntityManager().createQuery(requete);
//				ejbQuery.setParameter(1, taFacture.getTaTiers());
//			}
			if(taFacture!=null /*&& taFacture.getIdDocument()!=0*/){
				List<TaAcompte> l = ejbQuery.getResultList();
				logger.debug("selectAllDisponible successful");
				return l;
			}
			return null;
		} catch (RuntimeException re) {
			logger.error("selectAllDisponible failed", re);
			throw re;
		}
	}
	/**
	 * Recherche les factures apporteur entre 2 dates
	 * @param dateDeb - date de début
	 * @param dateFin - date de fin
	 * @return String[] - tableau contenant les IDs des factures apporteur entre ces 2 dates (null en cas d'erreur)
	 */
	public List<TaAcompte> rechercheDocument(Date dateDeb, Date dateFin) {
		List<TaAcompte> result = null;
		Query query = getEntityManager().createNamedQuery(TaAcompte.QN.FIND_BY_DATE);
		query.setParameter(1,"%");
		query.setParameter(2, dateDeb, TemporalType.DATE);
		query.setParameter(3, dateFin, TemporalType.DATE);
		result = query.getResultList();
		return result;
	}
	
	
	/**
	 * Recherche les factures apporteur entre 2 dates ordonnées par date
	 * @param dateDeb - date de début
	 * @param dateFin - date de fin
	 * @return String[] - tableau contenant les IDs des factures apporteur entre ces 2 dates (null en cas d'erreur)
	 */
	public List<TaAcompte> rechercheDocumentOrderByDate(Date dateDeb, Date dateFin) {
		List<TaAcompte> result = null;
		Query query = getEntityManager().createNamedQuery(TaAcompte.QN.FIND_BY_DATE_PARDATE);
		query.setParameter(1,"%");
		query.setParameter(2, dateDeb, TemporalType.DATE);
		query.setParameter(3, dateFin, TemporalType.DATE);
		result = query.getResultList();
		return result;
	}
	
	/**
	 * Recherche les factures apporteur entre 2 codes factures apporteur
	 * @param codeDeb - code de début
	 * @param codeFin - code de fin
	 * @return String[] - tableau contenant les IDs des factures apporteur entre ces 2 codes (null en cas d'erreur)
	 */
	public List<TaAcompte> rechercheDocument(String codeDeb, String codeFin) {		
		List<TaAcompte> result = null;
		Query query = getEntityManager().createNamedQuery(TaAcompte.QN.FIND_BY_CODE);
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
	
	public List<TaAcompte> findByCodeTiersAndDate(String codeTiers, Date debut, Date fin) {
		logger.debug("getting TaAcompte instance with codeTiers: " + codeTiers);
		List<TaAcompte> result = null;
		try {
			Query query = getEntityManager().createNamedQuery(TaAcompte.QN.FIND_BY_TIERS_AND_DATE);
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
	public void messageNonAutoriseModificationRelation(TaAcompte acompte) throws Exception{
			if(!dataSetEnModif()) {
					String message="l'acompte "+acompte.getCodeDocument()+" a été modifié par ailleurs !!!" +
					"\r\nIl doit être rechargé pour y " +
					"apporter de nouvelles modifications.";
					MessageDialog.openWarning(PlatformUI.getWorkbench()
							.getActiveWorkbenchWindow().getShell(),"Attention",
							message);
					throw new Exception();
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
		logger.debug("getting TaAcompte instance with codeTiers: " + codeTiers);
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
				+" from TaAcompte f "
				+"left join f.taTiers t "
				+"where  t.codeTiers = ? and f.dateDocument between ? and ? "
				+"group by "+groupBy+",t.codeTiers"
				;

//			jpql = "select  "
//			+"cast(extract(year from f.dateDocument)as string), t.codeTiers,"
//			+"case when extract ( year from f.dateDocument) <= 2001 then  sum(f.netHtCalc/6.55957) else sum(f.netHtCalc) end, "
//			+"case when extract ( year from f.dateDocument) <= 2001 then  sum(f.netTvaCalc/6.55957) else sum(f.netTvaCalc) end, "
//			+"case when extract ( year from f.dateDocument) <= 2001 then  sum(f.netTtcCalc/6.55957 ) else sum(f.netTtcCalc) end "
//			+" from TaBonliv f "
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
	 * Classe permettant d'obtenir le ca généré par les apporteur sur une période donnée
	 * @param debut date de début des données
	 * @param fin date de fin des données
	 * @return La requête renvoi le montant des par les apporteurs sur la période
	 */
	public List<Object> findCASurPeriode(Date debut, Date fin) {
		logger.debug("getting nombre ca des apporteurs");
		List<Object> result = null;
		
		
		try {
			String requete = "";

			requete = "SELECT "
				+" sum(d.netHtCalc)"
				+" FROM TaAcompte d " 
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
			+" FROM TaAcompte f " 
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
			+" FROM TaAcompte f " 
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
			+" FROM TaLAcompte l " 
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
	 * Recherche les acomptes entre 2 dates et non exportees
	 * @param dateDeb - date de début
	 * @param dateFin - date de fin
	 * @return String[] - tableau contenant les IDs des acomptes entre ces 2 dates (null en cas d'erreur)
	 */
	public List<TaAcompte> rechercheDocumentNonExporte(Date dateDeb, Date dateFin) {
		List<TaAcompte> result = null;
		Query query = getEntityManager().createNamedQuery(TaAcompte.QN.FIND_BY_DATE_NON_EXPORT);
		query.setParameter(1,"%");
		query.setParameter(2, dateDeb, TemporalType.DATE);
		query.setParameter(3, dateFin, TemporalType.DATE);
		result = query.getResultList();
		
		
		return result;
		
	}

	
	
	/**
	 * Recherche les acomptes non inclus dans des remises entre 2 dates
	 * @param codeTiers
	 * @param dateDeb
	 * @param DateFin
	 * @param export
	 * @param codeTPaiement
	 * @param compteBancaire
	 * @return String[] - tableau contenant les IDs des acomptes entre ces 2 dates (null en cas d'erreur)
	 */
	public List<TaAcompte> rechercheAcompteNonRemises(String codeTiers,Date dateDeb,Date DateFin,Boolean export,String codeTPaiement,String compteBancaire,boolean byDate) {
		String requete="select r from TaAcompte r where not exists" +
				"(select a from TaLRemise a where a.taAcompte.codeDocument=r.codeDocument)" +
				" and r.taTiers.codeTiers like ? " +
				" and r.dateDocument between ? and ?" +
				" and r.taTPaiement.codeTPaiement like ?"+
				" and r.taCompteBanque.codeBanque||r.taCompteBanque.codeGuichet||r.taCompteBanque.compte like ?";
		
		if(!export)requete+=" and r.export=? " ;
		if(byDate)requete+=" order by r.dateDocument";else requete+=" order by r.codeDocument";
		
		try {
			if(codeTiers==null ||codeTiers.equals(""))codeTiers="%";
			if(codeTPaiement==null ||codeTPaiement.equals(""))codeTPaiement="%";
			if(compteBancaire==null ||compteBancaire.equals(""))compteBancaire="%";
			Query ejbQuery = getEntityManager().createQuery(requete);
			ejbQuery.setParameter(1,codeTiers );
			ejbQuery.setParameter(2,dateDeb );
			ejbQuery.setParameter(3, DateFin);
			ejbQuery.setParameter(4, codeTPaiement);
			ejbQuery.setParameter(5, compteBancaire);
			
			if(!export)
				  ejbQuery.setParameter(6, LibConversion.booleanToInt(export));
			
			List<TaAcompte> l = ejbQuery.getResultList();
			logger.debug("rechercheAcompteNonRemises successful");
			return l;
		} catch (RuntimeException re) {
			logger.error("rechercheAcompteNonRemises failed", re);
			throw re;
		}
	}

	@Override
	public List<TaAcompte> rechercheDocument(String codeDeb, String codeFin,
			String codeTiers) {
		return rechercheDocument(codeDeb, codeFin, codeTiers,null);
	}

	@Override
	public List<TaAcompte> rechercheDocument(Date dateDeb, Date dateFin,
			String codeTiers) {
		return rechercheDocument(dateDeb, dateFin, codeTiers,null);
	}

	@Override
	public List<TaAcompte> rechercheDocument(Date dateDeb, Date dateFin,
			String codeTiers, Boolean export) {
		List<TaAcompte> result = null;
		Query query = null;
		if(export!=null && export)query = getEntityManager().createNamedQuery(TaAcompte.QN.FIND_BY_DATE_EXPORT);
		else if(export!=null && export==false)query = getEntityManager().createNamedQuery(TaAcompte.QN.FIND_BY_DATE_NON_EXPORT);
		else
		query = getEntityManager().createNamedQuery(TaAcompte.QN.FIND_BY_TIERS_AND_DATE);
		if(codeTiers==null || codeTiers.equals(""))codeTiers="%";
		query.setParameter(1, codeTiers);
		query.setParameter(2, dateDeb, TemporalType.DATE);
		query.setParameter(3, dateFin, TemporalType.DATE);
		result = query.getResultList();
		return result;
	}

	@Override
	public List<TaAcompte> rechercheDocument(String codeDeb, String codeFin,
			String codeTiers, Boolean export) {
		List<TaAcompte> result = null;
		Query query = null;
		if(export!=null && export)query = getEntityManager().createNamedQuery(TaAcompte.QN.FIND_BY_CODE_EXPORT);
		else if(export!=null && export==false)query = getEntityManager().createNamedQuery(TaAcompte.QN.FIND_BY_CODE_NON_EXPORT);
		else query = getEntityManager().createNamedQuery(TaAcompte.QN.FIND_BY_TIERS_AND_CODE);
		if(codeTiers==null || codeTiers.equals(""))codeTiers="%";
		query.setParameter(1, codeTiers);
		query.setParameter(2, codeDeb);
		query.setParameter(3, codeFin);
		result = query.getResultList();
		return result;
	}

	/* (non-Javadoc)
	 * @see fr.legrain.articles.dao.ILgrDAO#selectAll()
	 */
	public List<TaAcompte> selectAll(IDocumentTiers taDocument,Date dateDeb,Date dateFin) {
		TaTiers taTiersLoc=null;
		if(taDocument!=null)taTiersLoc=taDocument.getTaTiers();
		logger.debug("selectAll TaAcompte");
		try {
			if(taTiersLoc!=null){				
				if(dateDeb==null)dateDeb=new Date(0);
				if(dateFin==null)dateFin=new Date("01/01/3000");
				
				String requete="select a from TaAcompte a where (" +
				" not exists(select r from TaRAcompte r where r.taAcompte.idDocument=a.id " +
				" and (r.taFacture=?))" +
//				" and exists(select r from TaRAcompte r where r.taAcompte.idDocument<>a.id " +
//				" and r.taFacture.taTiers=?) " +
				" ) and a.taTiers =?"+
				" and a.dateDocument between ? and ?";
				
				Query ejbQuery = getEntityManager().createQuery(requete);
				ejbQuery.setParameter(1, taDocument);
				ejbQuery.setParameter(2, taTiersLoc);
//				ejbQuery.setParameter(3, taTiersLoc);
				ejbQuery.setParameter(3, dateDeb,TemporalType.DATE);
				ejbQuery.setParameter(4, dateFin,TemporalType.DATE);

				List<TaAcompte> l = ejbQuery.getResultList();
				logger.debug("selectAll successful");
				return l;
			}
			return null;
		} catch (RuntimeException re) {
			logger.error("selectAll failed", re);
			throw re;
		}
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
			query =getEntityManager().createNamedQuery(TaAcompte.QN.FIND_BY_DATE_LIGHT);
		else query = getEntityManager().createNamedQuery(TaAcompte.QN.FIND_BY_DATE);
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
		query = getEntityManager().createNamedQuery(TaAcompte.QN.FIND_BY_DATE_LIGHT);
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
			Query query = getEntityManager().createNamedQuery(TaAcompte.QN.FIND_BY_TIERS_AND_CODE_LIGHT);
			query.setParameter(1,"%");
			query.setParameter(2, codeDoc+"%");
			result = query.getResultList();
			return result;

		}

}
