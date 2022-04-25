package fr.legrain.documents.dao;

// Generated Apr 7, 2009 3:27:24 PM by Hibernate Tools 3.2.0.CR1

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TemporalType;

import org.apache.log4j.Logger;

import fr.legrain.articles.dao.TaPrix;
import fr.legrain.gestCom.Appli.AbstractApplicationDAO;
import fr.legrain.gestCom.Appli.Const;
import fr.legrain.tiers.dao.TaTiers;

/**
 * Home object for domain model class TaApporteur.
 * @see fr.legrain.documents.dao.TaApporteur
 * @author Hibernate Tools
 */
public class TaApporteurDAO extends AbstractApplicationDAO<TaApporteur> implements IDocumentTiersDAO<TaApporteur>,IDocumentTiersStatistiquesDAO<TaApporteur> {

//	private static final Log log = LogFactory.getLog(TaApporteurDAO.class);
	static Logger logger = Logger.getLogger(TaApporteurDAO.class);
	private String defaultJPQLQuery = "select a from TaApporteur a";
	
	public TaApporteurDAO(){
		this(null);
	}
	
	public TaApporteurDAO(EntityManager em){
		if(em!=null) {
			setEm(em);
		}
//		champIdTable=ctrlGeneraux.getID_TABLE(TaApporteur.class.getSimpleName());
		initChampId(TaApporteur.class);
		setJPQLQuery(defaultJPQLQuery);
		setListeChampMaj(Const.C_FICHIER_INI_CHAMPMAJ);
		initNomTable(new TaApporteur());
	}
	
	public TaApporteur refresh(TaApporteur detachedInstance) {
		logger.debug("refresh instance");
		try {
			detachedInstance.setLegrain(false);
        	getEntityManager().refresh(detachedInstance);
//			detachedInstance.setLegrain(false);
//			org.hibernate.Session session = (org.hibernate.Session) getEntityManager().getDelegate();
//
//			for (TaRAcompte a : detachedInstance.getTaRAcomptes()) {
//				session.evict(a.getTaAcompte().getTaRAcomptes());
//				session.evict(a.getTaAcompte());
//			}
//			session.evict(detachedInstance.getTaRAcomptes());
//			session.evict(detachedInstance);
//
//			detachedInstance = getEntityManager().find(TaApporteur.class, detachedInstance.getIdDocument());
			detachedInstance.setLegrain(true);
			detachedInstance.calculLignesTva();
			return detachedInstance;
		} catch (RuntimeException re) {
			logger.error("refresh failed", re);
			throw re;
		}
	}
	
	public void persist(TaApporteur transientInstance) {
		logger.debug("persisting TaApporteur instance");
		try {
			getEntityManager().persist(transientInstance);
			logger.debug("persist successful");
		} catch (RuntimeException re) {
			logger.error("persist failed", re);
			throw re;
		}
	}

	public void remove(TaApporteur persistentInstance) {
		logger.debug("removing TaApporteur instance");
		try {
			getEntityManager().remove(persistentInstance);
			logger.debug("remove successful");
		} catch (RuntimeException re) {
			logger.error("remove failed", re);
			throw re;
		}
	}
	
	public void annuler(TaApporteur persistentInstance) throws Exception{
		persistentInstance.setLegrain(false);
		List<TaLApporteur> listTemp=new ArrayList<TaLApporteur>(0);
		for (Object ligne : persistentInstance.getLignes()) {
			if(((TaLApporteur)ligne).getIdLDocument()==0)
				listTemp.add((TaLApporteur)ligne);				
		}
		for (TaLApporteur taLApporteur : listTemp) {
			persistentInstance.getLignes().remove(taLApporteur);
		}
		super.annuler(persistentInstance);
	}
	

	public TaApporteur merge(TaApporteur detachedInstance) {
		logger.debug("merging TaApporteur instance");
		try {
			TaApporteur result = getEntityManager().merge(detachedInstance);
			logger.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			logger.error("merge failed", re);
			throw re;
		}
	}

	public TaApporteur findById(int id) {
		logger.debug("getting TaApporteur instance with id: " + id);
		try {
			TaApporteur instance = getEntityManager().find(TaApporteur.class, id);
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
			Query query = getEntityManager().createQuery("select count(f) from TaApporteur f where f.codeDocument='"+code+"'");
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
	
	
	public TaApporteur findByCode(String code) {
		logger.debug("getting TaApporteur instance with code: " + code);
		try {
			Query query = getEntityManager().createQuery("select a from TaApporteur a " +
					"  left join FETCH a.lignes l"+
					" where a.codeDocument='"+code+"' " +
							" order by l.numLigneLDocument");
			TaApporteur instance = (TaApporteur)query.getSingleResult();
			instance.setLegrain(true);
			logger.debug("get successful");
			return instance;
		} catch (RuntimeException re) {
			logger.error("get failed", re);
			throw re;
		}
	}
	
	public List<TaApporteur> findByCodeTiersAndDate(String codeTiers, Date debut, Date fin) {
		logger.debug("getting TaApporteur instance with codeTiers: " + codeTiers);
		List<TaApporteur> result = null;
		try {
			Query query = getEntityManager().createNamedQuery(TaApporteur.QN.FIND_BY_TIERS_AND_DATE);
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
		logger.debug("getting TaApporteur instance with codeTiers: " + codeTiers);
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
				+" from TaApporteur f "
				+"left join f.taTiers t "
				+"where  t.codeTiers = ? and f.dateDocument between ? and ? "
				+"group by "+groupBy+",t.codeTiers"
				;

//			jpql = "select  "
//			+"cast(extract(year from f.dateDocument)as string), t.codeTiers,"
//			+"case when extract ( year from f.dateDocument) <= 2001 then  sum(f.netHtCalc/6.55957) else sum(f.netHtCalc) end, "
//			+"case when extract ( year from f.dateDocument) <= 2001 then  sum(f.netTvaCalc/6.55957) else sum(f.netTvaCalc) end, "
//			+"case when extract ( year from f.dateDocument) <= 2001 then  sum(f.netTtcCalc/6.55957 ) else sum(f.netTtcCalc) end "
//			+" from TaApporteur f "
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
	
	/* (non-Javadoc)
	 * @see fr.legrain.articles.dao.ILgrDAO#selectAll()
	 */
	public List<TaApporteur> selectAll() {
		logger.debug("TaApporteur TaArticle");
		try {
			Query ejbQuery = getEntityManager().createQuery(JPQLQuery);
			List<TaApporteur> l = ejbQuery.getResultList();
			logger.debug("selectAll successful");
			return l;
		} catch (RuntimeException re) {
			logger.error("selectAll failed", re);
			throw re;
		}
	}
	
	/**
	 * Recherche les factures apporteur entre 2 dates
	 * @param dateDeb - date de début
	 * @param dateFin - date de fin
	 * @return String[] - tableau contenant les IDs des factures apporteur entre ces 2 dates (null en cas d'erreur)
	 */
	public List<TaApporteur> rechercheDocument(Date dateDeb, Date dateFin) {
		List<TaApporteur> result = null;
		Query query = getEntityManager().createNamedQuery(TaApporteur.QN.FIND_BY_DATE);
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
	public List<TaApporteur> rechercheDocument(String codeDeb, String codeFin) {		
		List<TaApporteur> result = null;
		Query query = getEntityManager().createNamedQuery(TaApporteur.QN.FIND_BY_CODE);
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
	public List<TaTiers> findTiersByCodeArticleAndDate(String codeTiers,
			Date debut, Date fin) {
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
				+" FROM TaApporteur d " 
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
			+" FROM TaApporteur f " 
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
			+" FROM TaApporteur f " 
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
			+" FROM TaLApporteur l " 
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

	@Override
	public List<TaApporteur> rechercheDocument(String codeDeb, String codeFin,
			String codeTiers) {
		return rechercheDocument(codeDeb, codeFin, codeTiers,null);
		}

	@Override
	public List<TaApporteur> rechercheDocument(Date dateDeb, Date dateFin,
			String codeTiers) {
		return rechercheDocument(dateDeb, dateFin, codeTiers, null);
	}

	@Override
	public List<TaApporteur> rechercheDocument(Date dateDeb, Date dateFin,
			String codeTiers, Boolean export) {
		List<TaApporteur> result = null;
		Query query = null;
		query = getEntityManager().createNamedQuery(TaApporteur.QN.FIND_BY_TIERS_AND_DATE);
		if(codeTiers==null || codeTiers.equals(""))codeTiers="%";
		query.setParameter(1, codeTiers);
		query.setParameter(2, dateDeb, TemporalType.DATE);
		query.setParameter(3, dateFin, TemporalType.DATE);
		result = query.getResultList();
		return result;
	}

	@Override
	public List<TaApporteur> rechercheDocument(String codeDeb, String codeFin,
			String codeTiers, Boolean export) {
		List<TaApporteur> result = null;
		Query query = getEntityManager().createNamedQuery(TaApporteur.QN.FIND_BY_TIERS_AND_CODE);
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
			query =getEntityManager().createNamedQuery(TaApporteur.QN.FIND_BY_DATE_LIGHT);
		else query = getEntityManager().createNamedQuery(TaApporteur.QN.FIND_BY_DATE);
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
		query = getEntityManager().createNamedQuery(TaApporteur.QN.FIND_BY_DATE_LIGHT);
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
			Query query = getEntityManager().createNamedQuery(TaApporteur.QN.FIND_BY_TIERS_AND_CODE_LIGHT);
			query.setParameter(1,"%");
			query.setParameter(2, codeDoc+"%");
			result = query.getResultList();
			return result;

		}


	/**
	 * Recherche les apporteurs entre 2 dates
	 * @param dateDeb - date de début
	 * @param dateFin - date de fin
	 * @return String[] - tableau contenant les IDs des apporteurs entre ces 2 dates (null en cas d'erreur)
	 */
	public List<TaApporteur> rechercheDocumentNonExporte(Date dateDeb, Date dateFin,boolean parDate) {
		List<TaApporteur> result = null;
		Query query = null;
		if(parDate)query = getEntityManager().createNamedQuery(TaApporteur.QN.FIND_BY_DATE_NON_EXPORT_PARDATE);
		else
		query = getEntityManager().createNamedQuery(TaApporteur.QN.FIND_BY_DATE_NON_EXPORT);
		query.setParameter(1,"%");
		query.setParameter(2, dateDeb, TemporalType.DATE);
		query.setParameter(3, dateFin, TemporalType.DATE);
		result = query.getResultList();
		
		
		return result;
		
	}
	
	/**
	 * Recherche les apporteurs entre 2 dates ordonnées par date
	 * @param dateDeb - date de début
	 * @param dateFin - date de fin
	 * @return String[] - tableau contenant les IDs des apporteurs entre ces 2 dates (null en cas d'erreur)
	 */
	public List<TaApporteur> rechercheDocumentOrderByDate(Date dateDeb, Date dateFin) {
		Query query = getEntityManager().createNamedQuery(TaApporteur.QN.FIND_BY_DATE_PARDATE);
		query.setParameter(1,"%");
		query.setParameter(2, dateDeb, TemporalType.DATE);
		query.setParameter(3, dateFin, TemporalType.DATE);
		List<TaApporteur> l = query.getResultList();
		return l;
	}	
}

