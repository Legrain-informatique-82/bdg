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
import fr.legrain.gestCom.Module_Document.IDocumentTiers;
import fr.legrain.lib.data.LibDate;
import fr.legrain.tiers.dao.TaTiers;
import fr.legrain.tiers.dao.TaTiersDAO;

/**
 * Home object for domain model class TaBonliv.
 * @see fr.legrain.documents.dao.TaBonliv
 * @author Hibernate Tools
 */
public class TaBonlivDAO extends AbstractApplicationDAO<TaBonliv> implements IDocumentTiersDAO<TaBonliv>,IDocumentTiersStatistiquesDAO<TaBonliv> {

//	private static final Log log = LogFactory.getLog(TaBonlivDAO.class);
	static Logger logger = Logger.getLogger(TaBonlivDAO.class);
	
	private String defaultJPQLQuery = "select a from TaBonliv a";
	
	public TaBonlivDAO(){
		this(null);
	}
	
	public TaBonlivDAO(EntityManager em){
		if(em!=null) {
			setEm(em);
		}
//		champIdTable=ctrlGeneraux.getID_TABLE(TaBonliv.class.getSimpleName());
		initChampId(TaBonliv.class);
		setJPQLQuery(defaultJPQLQuery);
		setListeChampMaj(Const.C_FICHIER_INI_CHAMPMAJ);
		initNomTable(new TaBonliv());
	}
	
	public TaBonliv refresh(TaBonliv detachedInstance) {
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
//			detachedInstance = getEntityManager().find(TaBonliv.class, detachedInstance.getIdDocument());
			detachedInstance.setLegrain(true);
			detachedInstance.calculLignesTva();
			return detachedInstance;

		} catch (RuntimeException re) {
			logger.error("refresh failed", re);
			throw re;
		}
	}
	
	public void persist(TaBonliv transientInstance) {
		logger.debug("persisting TaBonliv instance");
		try {
			getEntityManager().persist(transientInstance);
			logger.debug("persist successful");
		} catch (RuntimeException re) {
			logger.error("persist failed", re);
			throw re;
		}
	}

	public void remove(TaBonliv persistentInstance) {
		logger.debug("removing TaBonliv instance");
		try {
			getEntityManager().remove(persistentInstance);
			logger.debug("remove successful");
		} catch (RuntimeException re) {
			logger.error("remove failed", re);
			throw re;
		}
	}

	public TaBonliv merge(TaBonliv detachedInstance) {
		logger.debug("merging TaBonliv instance");
		try {
			TaBonliv result = getEntityManager().merge(detachedInstance);
			logger.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			logger.error("merge failed", re);
			throw re;
		}
	}

	public TaBonliv findById(int id) {
		logger.debug("getting TaBonliv instance with id: " + id);
		try {
			TaBonliv instance = getEntityManager().find(TaBonliv.class, id);
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
			Query query = getEntityManager().createQuery("select count(f) from TaBonliv f where f.codeDocument='"+code+"'");
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
	
	public TaBonliv findByCode(String code) {
		logger.debug("getting TaBonliv instance with code: " + code);
		try {
			Query query = getEntityManager().createQuery("select a from TaBonliv a " +
					"  left join FETCH a.lignes l"+
					" where a.codeDocument='"+code+"' " +
							" order by l.numLigneLDocument");			
			TaBonliv instance = (TaBonliv)query.getSingleResult();
			instance.setLegrain(true);
			logger.debug("get successful");
			return instance;
		} catch (RuntimeException re) {
			logger.error("get failed", re);
			throw re;
		}
	}
	
	public List<TaBonliv> findByCodeTiersAndDate(String codeTiers, Date debut, Date fin) {
		logger.debug("getting TaBonliv instance with codeTiers: " + codeTiers);
		List<TaBonliv> result = null;
		try {
			Query query = getEntityManager().createNamedQuery(TaBonliv.QN.FIND_BY_TIERS_AND_DATE);
			if(codeTiers==null || codeTiers.equals(""))codeTiers="%";
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
	
	public List<IDocumentTiers> selectDocNonTransformeRequete(IDocumentTiers doc ,String codeTiers,String typeOrigine,String typeDest,Date debut,Date fin) {
		// TODO Auto-generated method stub
		logger.debug("selectDocNonTransformeRequete ");
		try {			
			String requete = "select a from Ta"+typeOrigine+" a join a.taTiers t where " ;
			if(codeTiers!=null ) //&& !codeTiers.equals("")
				requete+=" t.codeTiers like '"+codeTiers+"' and ";
			if(doc!=null)
				requete+=" a.idDocument = "+doc.getIdDocument()+" and ";
			requete+=" a.dateDocument between ? and ? and not exists(select r from TaRDocument r " +
					"join r.ta"+typeOrigine+" org " +
					"join r.ta"+typeDest+" dest " +
					" where org.idDocument" +
					" = a.idDocument and (dest.idDocument is not null or dest.idDocument!=0))";
			requete+=" ) order by a.codeDocument";
			Query ejbQuery = getEntityManager().createQuery(requete);
			ejbQuery.setParameter(1, debut);
			ejbQuery.setParameter(2, fin);
			List<IDocumentTiers> l = ejbQuery.getResultList();
			return l;
		} catch (RuntimeException re) {
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
		logger.debug("getting TaBonliv instance with codeTiers: " + codeTiers);
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
				+" from TaBonliv f "
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
	
	/* (non-Javadoc)
	 * @see fr.legrain.articles.dao.ILgrDAO#selectAll()
	 */
	public List<TaBonliv> selectAll() {
		logger.debug("selectAll TaBonliv");
		try {
			Query ejbQuery = getEntityManager().createQuery(JPQLQuery);
			List<TaBonliv> l = ejbQuery.getResultList();
			logger.debug("selectAll successful");
			return l;
		} catch (RuntimeException re) {
			logger.error("selectAll failed", re);
			throw re;
		}
	}
	
	/**
	 * Recherche les bon de livraison entre 2 dates
	 * @param dateDeb - date de début
	 * @param dateFin - date de fin
	 * @return String[] - tableau contenant les IDs des bon de livraison entre ces 2 dates (null en cas d'erreur)
	 */
	public List<TaBonliv> rechercheDocument(Date dateDeb, Date dateFin) {
		List<TaBonliv> result = null;
		Query query = getEntityManager().createNamedQuery(TaBonliv.QN.FIND_BY_DATE);
		query.setParameter(1,"%");
		query.setParameter(2, dateDeb, TemporalType.DATE);
		query.setParameter(3, dateFin, TemporalType.DATE);
		result = query.getResultList();
		return result;
	}
	
	
	/**
	 * Recherche les bon de livraison entre 2 dates ordonnées par date
	 * @param dateDeb - date de début
	 * @param dateFin - date de fin
	 * @return String[] - tableau contenant les IDs des bon de livraison entre ces 2 dates (null en cas d'erreur)
	 */
	public List<TaBonliv> rechercheDocumentOrderByDate(Date dateDeb, Date dateFin) {
		List<TaBonliv> result = null;
		Query query = getEntityManager().createNamedQuery(TaBonliv.QN.FIND_BY_DATE_PARDATE);
		query.setParameter(1,"%");
		query.setParameter(2, dateDeb, TemporalType.DATE);
		query.setParameter(3, dateFin, TemporalType.DATE);
		result = query.getResultList();
		return result;
	}
	/**
	 * Recherche les bon de livraison entre 2 codes bon de livraison
	 * @param codeDeb - code de début
	 * @param codeFin - code de fin
	 * @return String[] - tableau contenant les IDs des bon de livraison entre ces 2 codes (null en cas d'erreur)
	 */
	public List<TaBonliv> rechercheDocument(String codeDeb, String codeFin) {		
		List<TaBonliv> result = null;
		Query query = getEntityManager().createNamedQuery(TaBonliv.QN.FIND_BY_CODE);
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
	 * Classe permettant d'obtenir le ca généré par les bons de livraison sur une période donnée
	 * @param debut date de début des données
	 * @param fin date de fin des données
	 * @return La requête renvoi le montant des par les BL sur la période
	 */
	public List<Object> findCABLSurPeriode(Date debut, Date fin) {
		logger.debug("getting nombre ca des bc");
		List<Object> result = null;
		
		
		try {
			String requete = "";

			requete = "SELECT "
				+" sum(d.netHtCalc)"
				+" FROM TaBonliv d " 
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
	 * Classe permettant d'obtenir les livraisons non transformées
	 * @param debut date de début des données
	 * @param fin date de fin des données
	 * @return La requête renvoyée renvoi la liste des livraisons non transformées
	 */
	public List<TaBonliv> findLivraisonsNonTransfos(Date debut, Date fin) {
		logger.debug("getting nombre livraisons non transfos");
		List<TaBonliv> result = null;
		
		
		try {
			String requete = "";

			requete = "SELECT "
			+" d"
			+" FROM TaBonliv d " 
			+" where d.dateDocument between ? and ?"
			+" and not exists (select r " +
					"from TaRDocument r " +
					" where r.taBonliv = d" +
					" and ( taFacture IS NOT NULL))"
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
			+" FROM TaBonliv d " 
			+" where d.dateDocument between ? and ? "
			+" and exists (select r " 
			+" from TaRDocument r " 
			+" where r.taBonliv = d " 
			+" and ( r.taFacture IS NOT NULL)) "
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
	 * Classe permettant d'obtenir les livraisons  transformées
	 * @param debut date de début des données
	 * @param fin date de fin des données
	 * @return La requête renvoyée renvoi la liste des livraisons transformées
	 */
	public List<TaBonliv> findLivraisonsTransfos(Date debut, Date fin) {
		logger.debug("getting nombre livraisons transfos");
		List<TaBonliv> result = null;
		
		
		try {
			String requete = "";

			requete = "SELECT "
			+" d"
			+" FROM TaBonliv d " 
			+" where d.dateDocument between ? and ?"
			+" and exists (select r " +
					"from TaRDocument r " +
					" where r.taBonliv = d" +
					" and ( taFacture IS NOT NULL))"
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
	public List<TaBonliv> rechercheDocument(String codeDeb, String codeFin,
			String codeTiers) {
		return rechercheDocument(codeDeb, codeFin, codeTiers, null);
	}

	@Override
	public List<TaBonliv> rechercheDocument(Date dateDeb, Date dateFin,
			String codeTiers) {
		return rechercheDocument(dateDeb, dateFin, codeTiers, null);
	}

	@Override
	public List<TaBonliv> rechercheDocument(Date dateDeb, Date dateFin,
			String codeTiers, Boolean export) {
		List<TaBonliv> result = null;
		Query query = getEntityManager().createNamedQuery(TaBonliv.QN.FIND_BY_TIERS_AND_DATE);
		if(codeTiers==null || codeTiers.equals(""))codeTiers="%";
		query.setParameter(1, codeTiers);
		query.setParameter(2, dateDeb, TemporalType.DATE);
		query.setParameter(3, dateFin, TemporalType.DATE);
		result = query.getResultList();
		return result;
	}

	@Override
	public List<TaBonliv> rechercheDocument(String codeDeb, String codeFin,
			String codeTiers, Boolean export) {
		List<TaBonliv> result = null;
		Query query = getEntityManager().createNamedQuery(TaBonliv.QN.FIND_BY_TIERS_AND_CODE);
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
			query =getEntityManager().createNamedQuery(TaBonliv.QN.FIND_BY_DATE_LIGHT);
		else query = getEntityManager().createNamedQuery(TaBonliv.QN.FIND_BY_DATE);
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
		query = getEntityManager().createNamedQuery(TaBonliv.QN.FIND_BY_DATE_LIGHT);
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
			Query query = getEntityManager().createNamedQuery(TaBonliv.QN.FIND_BY_TIERS_AND_CODE_LIGHT);
			query.setParameter(1,"%");
			query.setParameter(2, codeDoc+"%");
			result = query.getResultList();
			return result;

		}

}

