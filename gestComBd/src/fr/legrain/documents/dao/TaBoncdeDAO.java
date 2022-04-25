package fr.legrain.documents.dao;

// Generated Apr 7, 2009 3:27:24 PM by Hibernate Tools 3.2.0.CR1

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
 * Home object for domain model class TaDevis.
 * @see fr.legrain.documents.dao.TaDevis
 * @author Hibernate Tools
 */
public class TaBoncdeDAO extends AbstractApplicationDAO<TaBoncde> 
implements IDocumentTiersDAO<TaBoncde>,IDocumentTiersStatistiquesDAO<TaBoncde>,IDocumentTiersEtatDAO<TaBoncde> {

//	private static final Log log = LogFactory.getLog(TaDevisDAO.class);
	static Logger logger = Logger.getLogger(TaBoncdeDAO.class);
	
	private String defaultJPQLQuery = "select a from TaBoncde a";
	
	public TaBoncdeDAO(){
		this(null);
	}
	
	public TaBoncdeDAO(EntityManager em){
		if(em!=null) {
			setEm(em);
		}
//		champIdTable=ctrlGeneraux.getID_TABLE(TaBoncde.class.getSimpleName());
		initChampId(TaBoncde.class);
		setJPQLQuery(defaultJPQLQuery);
		setListeChampMaj(Const.C_FICHIER_INI_CHAMPMAJ);
		initNomTable(new TaBoncde());
	}
	
	public TaBoncde refresh(TaBoncde detachedInstance) {
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
//			detachedInstance = getEntityManager().find(TaBoncde.class, detachedInstance.getIdDocument());
			detachedInstance.setLegrain(true);
			detachedInstance.calculLignesTva();
			return detachedInstance;

		} catch (RuntimeException re) {
			logger.error("refresh failed", re);
			throw re;
		}
	}
	
	public void persist(TaBoncde transientInstance) {
		logger.debug("persisting TaBoncde instance");
		try {
			getEntityManager().persist(transientInstance);
			logger.debug("persist successful");
		} catch (RuntimeException re) {
			logger.error("persist failed", re);
			throw re;
		}
	}

	public void remove(TaBoncde persistentInstance) {
		logger.debug("removing TaBoncde instance");
		try {
			getEntityManager().remove(persistentInstance);
			logger.debug("remove successful");
		} catch (RuntimeException re) {
			logger.error("remove failed", re);
			throw re;
		}
	}

	public TaBoncde merge(TaBoncde detachedInstance) {
		logger.debug("merging TaBoncde instance");
		try {
			TaBoncde result = getEntityManager().merge(detachedInstance);
			logger.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			logger.error("merge failed", re);
			throw re;
		}
	}

	public TaBoncde findById(int id) {
		logger.debug("getting TaBoncde instance with id: " + id);
		try {
			TaBoncde instance = getEntityManager().find(TaBoncde.class, id);
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
			Query query = getEntityManager().createQuery("select count(f) from TaBoncde f where f.codeDocument='"+code+"'");
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
	
	public TaBoncde findByCode(String code) {
		logger.debug("getting TaBoncde instance with code: " + code);
		try {
			Query query = getEntityManager().createQuery("select a from TaBoncde a " +
					"  left join FETCH a.lignes l"+
					" where a.codeDocument='"+code+"' " +
							" order by l.numLigneLDocument");
			TaBoncde instance = (TaBoncde)query.getSingleResult();
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
	public List<TaBoncde> selectAll() {
		logger.debug("TaBoncde selectAll");
		try {
			Query ejbQuery = getEntityManager().createQuery(JPQLQuery);
			List<TaBoncde> l = ejbQuery.getResultList();
			logger.debug("selectAll successful");
			return l;
		} catch (RuntimeException re) {
			logger.error("selectAll failed", re);
			throw re;
		}
	}
	
	public List<TaBoncde> findByCodeTiersAndDate(String codeTiers, Date debut, Date fin) {
		logger.debug("getting TaBoncde instance with codeTiers: " + codeTiers);
		List<TaBoncde> result = null;
		try {
			Query query = getEntityManager().createNamedQuery(TaBoncde.QN.FIND_BY_TIERS_AND_DATE);
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
		logger.debug("getting TaBoncde instance with codeTiers: " + codeTiers);
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
				+" from TaBoncde f "
				+"left join f.taTiers t "
				+"where  t.codeTiers = ? and f.dateDocument between ? and ? "
				+"group by "+groupBy+",t.codeTiers"
				;

//			jpql = "select  "
//			+"cast(extract(year from f.dateDocument)as string), t.codeTiers,"
//			+"case when extract ( year from f.dateDocument) <= 2001 then  sum(f.netHtCalc/6.55957) else sum(f.netHtCalc) end, "
//			+"case when extract ( year from f.dateDocument) <= 2001 then  sum(f.netTvaCalc/6.55957) else sum(f.netTvaCalc) end, "
//			+"case when extract ( year from f.dateDocument) <= 2001 then  sum(f.netTtcCalc/6.55957 ) else sum(f.netTtcCalc) end "
//			+" from TaBoncde f "
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
	 * Recherche les bon de commande entre 2 dates
	 * @param dateDeb - date de début
	 * @param dateFin - date de fin
	 * @return String[] - tableau contenant les IDs des bon de commande entre ces 2 dates (null en cas d'erreur)
	 */
	public List<TaBoncde> rechercheDocument(Date dateDeb, Date dateFin) {
		List<TaBoncde> result = null;
		Query query = getEntityManager().createNamedQuery(TaBoncde.QN.FIND_BY_DATE);
		query.setParameter(1,"%");
		query.setParameter(2, dateDeb, TemporalType.DATE);
		query.setParameter(3, dateFin, TemporalType.DATE);
		result = query.getResultList();
		return result;
	}
	
	
	/**
	 * Recherche les bon de commande entre 2 dates ordonnées par date
	 * @param dateDeb - date de début
	 * @param dateFin - date de fin
	 * @return String[] - tableau contenant les IDs des bon de commande entre ces 2 dates (null en cas d'erreur)
	 */
	public List<TaBoncde> rechercheDocumentOrderByDate(Date dateDeb, Date dateFin) {
		List<TaBoncde> result = null;
		Query query = getEntityManager().createNamedQuery(TaBoncde.QN.FIND_BY_DATE_PARDATE);
		query.setParameter(1,"%");
		query.setParameter(2, dateDeb, TemporalType.DATE);
		query.setParameter(3, dateFin, TemporalType.DATE);
		result = query.getResultList();
		return result;
	}
	/**
	 * Recherche les bon de commande entre 2 codes bon de commande
	 * @param codeDeb - code de début
	 * @param codeFin - code de fin
	 * @return String[] - tableau contenant les IDs des bon de commande entre ces 2 codes (null en cas d'erreur)
	 */
	public List<TaBoncde> rechercheDocument(String codeDeb, String codeFin) {		
		List<TaBoncde> result = null;
		Query query = getEntityManager().createNamedQuery(TaBoncde.QN.FIND_BY_CODE);
		query.setParameter(1,"%");
		query.setParameter(2, codeDeb);
		query.setParameter(3, codeFin);
		result = query.getResultList();
		return result;

	}
	
	/**
	 * Recherche les commandes qui ont été importés d'un autre programme
	 * @param origineImport
	 * @param idImport - id externe (dans le programme d'origine)
	 * @return
	 */
	public List<TaBoncde> rechercheParImport(String origineImport, String idImport) {
		Query query = getEntityManager().createNamedQuery(TaBoncde.QN.FIND_BY_IMPORT);
		query.setParameter(1, origineImport);
		query.setParameter(2, idImport);
		List<TaBoncde> l = query.getResultList();

		return l;
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
	 * Classe permettant d'obtenir le ca généré par les bons de commande sur une période donnée
	 * @param debut date de début des données
	 * @param fin date de fin des données
	 * @return La requête renvoi le montant des par les BC sur la période
	 */
	public List<Object> findCABCSurPeriode(Date debut, Date fin) {
		logger.debug("getting nombre ca des bc");
		List<Object> result = null;
		
		
		try {
			String requete = "";

			requete = "SELECT "
				+" sum(d.netHtCalc)"
				+" FROM TaBoncde d " 
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
			+" FROM TaBoncde d " 
			+" where d.dateDocument between ? and ? "
			+" and exists (select r " 
			+" from TaRDocument r " 
			+" where r.taBoncde = d " 
			+" and ( r.taFacture IS NOT NULL " 
			+" OR r.taBonliv IS NOT NULL )) "
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
	 * Classe permettant d'obtenir les commandes  transformées
	 * @param debut date de début des données
	 * @param fin date de fin des données
	 * @return La requête renvoyée renvoi la liste des commandes transformées
	 */
	public List<TaBoncde> findCommandesTransfos(Date debut, Date fin) {
		logger.debug("getting nombre commandes transfos");
		List<TaBoncde> result = null;
		
		
		try {
			String requete = "";

			requete = "SELECT "
			+" d"
			+" FROM TaBoncde d " 
			+" where d.dateDocument between ? and ?"
			+" and exists (select r " +
					"from TaRDocument r " +
					" where r.taBoncde = d" +
					" and ( taFacture IS NOT NULL" +
					" OR taBonliv IS NOT NULL ))"
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
	 * Classe permettant d'obtenir les commandes non transformées
	 * @param debut date de début des données
	 * @param fin date de fin des données
	 * @return La requête renvoyée renvoi la liste des commandes non transformées
	 */
	public List<TaBoncde> findCommandesNonTransfos(Date debut, Date fin) {
		logger.debug("getting nombre commandes non transfos");
		List<TaBoncde> result = null;
		
		
		try {
			String requete = "";

			requete = "SELECT "
			+" d"
			+" FROM TaBoncde d " 
			+" where d.dateDocument between ? and ?"
			+" and not exists (select r " +
					"from TaRDocument r " +
					" where r.taBoncde = d" +
					" and ( taFacture IS NOT NULL" +
					" OR taBonliv IS NOT NULL ))"
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

	@Override
	public List<TaBoncde> rechercheDocument(String codeDeb, String codeFin,
			String codeTiers) {
		return rechercheDocument(codeDeb, codeFin, codeTiers,null);
	}

	@Override
	public List<TaBoncde> rechercheDocument(Date dateDeb, Date dateFin,
			String codeTiers) {
		return rechercheDocument(dateDeb, dateFin, codeTiers,null);
	}

	@Override
	public List<TaBoncde> rechercheDocument(Date dateDeb, Date dateFin,
			String codeTiers, Boolean export) {
		List<TaBoncde> result = null;
		Query query = getEntityManager().createNamedQuery(TaBoncde.QN.FIND_BY_TIERS_AND_DATE);
		if(codeTiers==null || codeTiers.equals(""))codeTiers="%";
		query.setParameter(1, codeTiers);
		query.setParameter(2, dateDeb, TemporalType.DATE);
		query.setParameter(3, dateFin, TemporalType.DATE);
		result = query.getResultList();
		return result;
	}

	@Override
	public List<TaBoncde> rechercheDocument(String codeDeb, String codeFin,
			String codeTiers, Boolean export) {
		List<TaBoncde> result = null;
		Query query = getEntityManager().createNamedQuery(TaBoncde.QN.FIND_BY_TIERS_AND_CODE);
		if(codeTiers==null || codeTiers.equals(""))codeTiers="%";
		query.setParameter(1, codeTiers);
		query.setParameter(2, codeDeb);
		query.setParameter(3, codeFin);
		result = query.getResultList();
		return result;
	}
	
	@Override
	public List<TaBoncde> rechercheDocumentEtat(Date dateDeb, Date datefin, String codeEtat) {
		List<TaBoncde> result = null;
		Query query = null;
		if(codeEtat!=null) {
			query = getEntityManager().createNamedQuery(TaBoncde.QN.FIND_BY_ETAT_DATE);
			query.setParameter(1, dateDeb);
			query.setParameter(2, datefin);
			query.setParameter(3, codeEtat);
		} else {
			query = getEntityManager().createNamedQuery(TaBoncde.QN.FIND_BY_ETAT_ENCOURS_DATE);
			query.setParameter(1, datefin);
		}
		
		result = query.getResultList();
		return result;
	}

	@Override
	public List<TaBoncde> rechercheDocumentEtat(Date dateDeb, Date datefin,
			String codeEtat, String codeTiers) {
		List<TaBoncde> result = null;
		Query query = null;
		if(codeEtat!=null) {
			query = getEntityManager().createNamedQuery(TaBoncde.QN.FIND_BY_ETAT_TIERS_DATE);
			query.setParameter(1, codeTiers);
			query.setParameter(2, dateDeb);
			query.setParameter(3, datefin);
			query.setParameter(4, codeEtat);		
		} else {
			query = getEntityManager().createNamedQuery(TaBoncde.QN.FIND_BY_ETAT_TIERS_ENCOURS_DATE);
			query.setParameter(1, codeTiers);
			query.setParameter(2, datefin);
		}
		
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
			query =getEntityManager().createNamedQuery(TaBoncde.QN.FIND_BY_DATE_LIGHT);
		else query = getEntityManager().createNamedQuery(TaBoncde.QN.FIND_BY_DATE);
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
		query = getEntityManager().createNamedQuery(TaBoncde.QN.FIND_BY_DATE_LIGHT);
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
			Query query = getEntityManager().createNamedQuery(TaBoncde.QN.FIND_BY_TIERS_AND_CODE_LIGHT);
			query.setParameter(1,"%");
			query.setParameter(2, codeDoc+"%");
			result = query.getResultList();
			return result;

		}

}


