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

import fr.legrain.gestCom.Appli.AbstractApplicationDAO;
import fr.legrain.gestCom.Appli.Const;
import fr.legrain.gestCom.Module_Document.IDocumentTiers;
import fr.legrain.lib.data.LibDate;
import fr.legrain.tiers.dao.TaTiers;

/**
 * Home object for domain model class TaDevis.
 * @see fr.legrain.documents.dao.TaDevis
 * @author Hibernate Tools
 */
public class TaDevisDAO extends AbstractApplicationDAO<TaDevis> 
implements IDocumentTiersDAO<TaDevis>,IDocumentTiersStatistiquesDAO<TaDevis>,IDocumentTiersEtatDAO<TaDevis> {

//	private static final Log log = LogFactory.getLog(TaDevisDAO.class);
	static Logger logger = Logger.getLogger(TaDevisDAO.class);
	
	private String defaultJPQLQuery = "select a from TaDevis a";
	
	public TaDevisDAO(){
		this(null);
	}
	
	public TaDevisDAO(EntityManager em){
		if(em!=null) {
			setEm(em);
		}
//		champIdTable=ctrlGeneraux.getID_TABLE(TaDevis.class.getSimpleName());
		initChampId(TaDevis.class);
		setJPQLQuery(defaultJPQLQuery);
		setListeChampMaj(Const.C_FICHIER_INI_CHAMPMAJ);
		initNomTable(new TaDevis());
	}
	
	public TaDevis refresh(TaDevis detachedInstance) {
		logger.debug("refresh instance");
		try {
			Map<IDocumentTiers,Boolean> listeEtatLegrain=new LinkedHashMap<IDocumentTiers, Boolean>();
			detachedInstance.setLegrain(false);
			
        	for (TaRAcompte obj : detachedInstance.getTaRAcomptes()) {
        		listeEtatLegrain.put(obj.getTaAcompte(),obj.getTaAcompte().isLegrain());
        		obj.getTaAcompte().setLegrain(false);
			}
        	
        	getEntityManager().refresh(detachedInstance);
        	
        	for (TaRAcompte obj : detachedInstance.getTaRAcomptes()) {        		
        		obj.getTaAcompte().setLegrain(retourneEtatLegrain(listeEtatLegrain,obj.getTaAcompte()));
			}
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
//			detachedInstance = getEntityManager().find(TaDevis.class, detachedInstance.getIdDocument());
			detachedInstance.setLegrain(true);
			detachedInstance.calculLignesTva();
			return detachedInstance;

		} catch (RuntimeException re) {
			logger.error("refresh failed", re);
			throw re;
		}
	}
	
	public void persist(TaDevis transientInstance) {
		logger.debug("persisting TaDevis instance");
		try {
			getEntityManager().persist(transientInstance);
			logger.debug("persist successful");
		} catch (RuntimeException re) {
			logger.error("persist failed", re);
			throw re;
		}
	}

	public void remove(TaDevis persistentInstance) {
		logger.debug("removing TaDevis instance");
		try {
			getEntityManager().remove(persistentInstance);
			logger.debug("remove successful");
		} catch (RuntimeException re) {
			logger.error("remove failed", re);
			throw re;
		}
	}

	public TaDevis merge(TaDevis detachedInstance) {
		logger.debug("merging TaDevis instance");
		try {
			TaDevis result = getEntityManager().merge(detachedInstance);
			logger.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			logger.error("merge failed", re);
			throw re;
		}
	}

	public TaDevis findById(int id) {
		logger.debug("getting TaDevis instance with id: " + id);
		try {
			TaDevis instance = getEntityManager().find(TaDevis.class, id);
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
			Query query = getEntityManager().createQuery("select count(f) from TaDevis f where f.codeDocument='"+code+"'");
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
	
	public TaDevis findByCode(String code) {
		logger.debug("getting TaDevis instance with code: " + code);
		try {
			Query query = getEntityManager().createQuery("select a from TaDevis a " +
					" left join FETCH a.lignes l"+
					" where a.codeDocument='"+code+"' " +
							" order by l.numLigneLDocument");
			TaDevis instance = (TaDevis)query.getSingleResult();
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
	public List<TaDevis> selectAll() {
		logger.debug("selectAll TaDevis");
		try {
			Query ejbQuery = getEntityManager().createQuery(JPQLQuery);
			List<TaDevis> l = ejbQuery.getResultList();
			logger.debug("selectAll successful");
			return l;
		} catch (RuntimeException re) {
			logger.error("selectAll failed", re);
			throw re;
		}
	}
	
	public List<TaDevis> findByCodeTiers(String codeTiers) {
		logger.debug("getting TaDevis instance with codeTiers: " + codeTiers);
		List<TaDevis> result = null;
		try {
			Query query = getEntityManager().createNamedQuery(TaDevis.QN.FIND_BY_TIERS);
			query.setParameter(1, codeTiers);
			result = query.getResultList();
			return result;
		} catch (RuntimeException re) {
			logger.error("get failed", re);
			throw re;
		}
	}
	
	public List<TaDevis> findByCodeTiersAndDate(String codeTiers, Date debut, Date fin) {
		logger.debug("getting TaDevis instance with codeTiers: " + codeTiers);
		List<TaDevis> result = null;
		try {
			Query query = getEntityManager().createNamedQuery(TaDevis.QN.FIND_BY_TIERS_AND_DATE);
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
		logger.debug("getting TaDevis instance with codeTiers: " + codeTiers);
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
				+" from TaDevis f "
				+"left join f.taTiers t "
				+"where  t.codeTiers = ? and f.dateDocument between ? and ? "
				+"group by "+groupBy+",t.codeTiers"
				;

//			jpql = "select  "
//			+"cast(extract(year from f.dateDocument)as string), t.codeTiers,"
//			+"case when extract ( year from f.dateDocument) <= 2001 then  sum(f.netHtCalc/6.55957) else sum(f.netHtCalc) end, "
//			+"case when extract ( year from f.dateDocument) <= 2001 then  sum(f.netTvaCalc/6.55957) else sum(f.netTvaCalc) end, "
//			+"case when extract ( year from f.dateDocument) <= 2001 then  sum(f.netTtcCalc/6.55957 ) else sum(f.netTtcCalc) end "
//			+" from TaDevis f "
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
	public List<TaDevis> rechercheDocument(Date dateDeb, Date dateFin) {

		List<TaDevis> result = null;
		Query query = getEntityManager().createNamedQuery(TaDevis.QN.FIND_BY_DATE);
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
	public List<TaDevis> rechercheDocumentOrderByDate(Date dateDeb, Date dateFin) {

		List<TaDevis> result = null;
		Query query = getEntityManager().createNamedQuery(TaDevis.QN.FIND_BY_DATE_PARDATE);
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
	public List<TaDevis> rechercheDocument(String codeDeb, String codeFin) {		
		List<TaDevis> result = null;
		Query query = getEntityManager().createNamedQuery(TaDevis.QN.FIND_BY_CODE);
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
	public List<Object> findCADevisSurPeriode(Date debut, Date fin) {
		logger.debug("getting nombre ca des devis");
		List<Object> result = null;
		
		
		try {
			String requete = "";

			requete = "SELECT "
				+" sum(d.netHtCalc)"
				+" FROM TaDevis d " 
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
			+" FROM TaDevis d " 
			+" where d.dateDocument between ? and ? "
			+" and exists (select r " 
			+" from TaRDocument r " 
			+" where r.taDevis = d " 
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
	public List<TaDevis> findDevisTransfos(Date debut, Date fin) {
		logger.debug("getting nombre devis non transfos");
		List<TaDevis> result = null;
		
		
		try {
			String requete = "";

			requete = "SELECT "
				+" d"
				+" FROM TaDevis d " 
				+" where d.dateDocument between ? and ?"
				+" and  exists (select r " +
						"from TaRDocument r " +
						" where r.taDevis = d" +
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
	public List<TaDevis> findDevisNonTransfos(Date debut, Date fin) {
		logger.debug("getting nombre devis non transfos");
		List<TaDevis> result = null;
		
		
		try {
			String requete = "";

			requete = "SELECT "
				+" d"
				+" FROM TaDevis d " 
				+" where d.dateDocument between ? and ?"
				+" and not exists (select r " +
						"from TaRDocument r " +
						" where r.taDevis = d" +
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
	public List<TaDevis> rechercheDocument(String codeDeb, String codeFin,
			String codeTiers) {
		return rechercheDocument(codeDeb, codeFin, codeTiers, null);
	}

	@Override
	public List<TaDevis> rechercheDocument(Date dateDeb, Date dateFin,
			String codeTiers) {
		return rechercheDocument(dateDeb, dateFin, codeTiers, null);
	}

	@Override
	public List<TaDevis> rechercheDocument(Date dateDeb, Date dateFin,
			String codeTiers, Boolean export) {
		List<TaDevis> result = null;
		Query query = getEntityManager().createNamedQuery(TaDevis.QN.FIND_BY_TIERS_AND_DATE);
		if(codeTiers==null || codeTiers.equals(""))codeTiers="%";
		query.setParameter(1, codeTiers);
		query.setParameter(2, dateDeb, TemporalType.DATE);
		query.setParameter(3, dateFin, TemporalType.DATE);
		result = query.getResultList();
		return result;
	}

	@Override
	public List<TaDevis> rechercheDocument(String codeDeb, String codeFin,
			String codeTiers, Boolean export) {
		List<TaDevis> result = null;
		Query query = getEntityManager().createNamedQuery(TaDevis.QN.FIND_BY_TIERS_AND_CODE);
		if(codeTiers==null || codeTiers.equals(""))codeTiers="%";
		query.setParameter(1, codeTiers);
		query.setParameter(2, codeDeb);
		query.setParameter(3, codeFin);
		result = query.getResultList();
		return result;
	}	
	
	@Override
	public List<TaDevis> rechercheDocumentEtat(Date dateDeb, Date datefin, String codeEtat) {
		List<TaDevis> result = null;
		Query query = null;
		if(codeEtat!=null) {
			query = getEntityManager().createNamedQuery(TaDevis.QN.FIND_BY_ETAT_DATE);
			query.setParameter(1, dateDeb);
			query.setParameter(2, datefin);
			query.setParameter(3, codeEtat);
		} else {
			query = getEntityManager().createNamedQuery(TaDevis.QN.FIND_BY_ETAT_ENCOURS_DATE);
			query.setParameter(1, datefin);
		}
		
		result = query.getResultList();
		return result;
	}

	@Override
	public List<TaDevis> rechercheDocumentEtat(Date dateDeb, Date datefin,
			String codeEtat, String codeTiers) {
		List<TaDevis> result = null;
		Query query = null;
		if(codeEtat!=null) {
			query = getEntityManager().createNamedQuery(TaDevis.QN.FIND_BY_ETAT_TIERS_DATE);
			query.setParameter(1, codeTiers);
			query.setParameter(2, dateDeb);
			query.setParameter(3, datefin);
			query.setParameter(4, codeEtat);		
		} else {
			query = getEntityManager().createNamedQuery(TaDevis.QN.FIND_BY_ETAT_TIERS_ENCOURS_DATE);
			query.setParameter(1, codeTiers);
			query.setParameter(2, datefin);
		}
		
		result = query.getResultList();
		return result;
	}

	/**
	 * Recherche les devis entre 2 dates light
	 * @param dateDeb - date de début
	 * @param dateFin - date de fin
	 * @return String[] - tableau contenant les IDs des factures entre ces 2 dates (null en cas d'erreur)
	 */
	//public String[] rechercheFacture(Date dateDeb, Date dateFin) {
	public List<Object[]> rechercheDocument(Date dateDeb, Date dateFin,Boolean light) {
		List<Object[]> result = null;
		Query query = null;
		if(light)
			query =getEntityManager().createNamedQuery(TaDevis.QN.FIND_BY_DATE_LIGHT);
		else query = getEntityManager().createNamedQuery(TaDevis.QN.FIND_BY_DATE);
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
		query = getEntityManager().createNamedQuery(TaDevis.QN.FIND_BY_DATE_LIGHT);
		if(codeTiers==null || codeTiers.equals(""))codeTiers="%";
		query.setParameter(1, codeTiers);
		query.setParameter(2, dateDeb, TemporalType.DATE);
		query.setParameter(3, dateFin, TemporalType.DATE);
		result = query.getResultList();
		return result;
	}
	@Override
	public List<Object[]> rechercheDocumentLight(String codeDoc,String codeTiers) {
			List<Object[]> result = null;
			Query query = getEntityManager().createNamedQuery(TaDevis.QN.FIND_BY_TIERS_AND_CODE_LIGHT);
			query.setParameter(1,"%");
			query.setParameter(2, codeDoc+"%");
			result = query.getResultList();
			return result;

		}

}

