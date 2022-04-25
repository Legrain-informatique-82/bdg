package fr.legrain.documents.dao;

// Generated Apr 7, 2009 3:27:24 PM by Hibernate Tools 3.2.0.CR1

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TemporalType;

import org.apache.log4j.Logger;

import fr.legrain.gestCom.Appli.AbstractApplicationDAO;
import fr.legrain.gestCom.Appli.Const;
import fr.legrain.tiers.dao.TaTiers;

/**
 * Home object for domain model class TaDevis.
 * @see fr.legrain.documents.dao.TaDevis
 * @author Hibernate Tools
 */
public class TaPrelevementDAO extends AbstractApplicationDAO<TaPrelevement> 
implements IDocumentTiersDAO<TaPrelevement>,IDocumentTiersStatistiquesDAO<TaPrelevement>,IDocumentTiersEtatDAO<TaPrelevement>  {

//	private static final Log log = LogFactory.getLog(TaDevisDAO.class);
	static Logger logger = Logger.getLogger(TaPrelevementDAO.class);
	
	private String defaultJPQLQuery = "select a from TaPrelevement a";
	
	public TaPrelevementDAO(){
		this(null);
	}
	
	public TaPrelevementDAO(EntityManager em){
		if(em!=null) {
			setEm(em);
		}
//		champIdTable=ctrlGeneraux.getID_TABLE(TaProforma.class.getSimpleName());
		initChampId(TaPrelevement.class);
		setJPQLQuery(defaultJPQLQuery);
		setListeChampMaj(Const.C_FICHIER_INI_CHAMPMAJ);
		initNomTable(new TaPrelevement());
	}
	
	public TaPrelevement refresh(TaPrelevement detachedInstance) {
		logger.debug("refresh instance");
		try {
			detachedInstance.setLegrain(false);
        	getEntityManager().refresh(detachedInstance);
//			org.hibernate.Session session = (org.hibernate.Session) getEntityManager().getDelegate();
//
//			for (TaRAcompte a : detachedInstance.getTaRAcomptes()) {
//				session.evict(a.getTaAcompte().getTaRAcomptes());
//				session.evict(a.getTaAcompte());
//			}
//			session.evict(detachedInstance.getTaRAcomptes());
//			session.evict(detachedInstance);
//
//			detachedInstance = getEntityManager().find(TaPrelevement.class, detachedInstance.getIdDocument());
			detachedInstance.setLegrain(true);
			detachedInstance.calculLignesTva();
			return detachedInstance;

		} catch (RuntimeException re) {
			logger.error("refresh failed", re);
			throw re;
		}
	}
	
	public void persist(TaPrelevement transientInstance) {
		logger.debug("persisting TaPrelevement instance");
		try {
			getEntityManager().persist(transientInstance);
			logger.debug("persist successful");
		} catch (RuntimeException re) {
			logger.error("persist failed", re);
			throw re;
		}
	}

	public void remove(TaPrelevement persistentInstance) {
		logger.debug("removing TaPrelevement instance");
		try {
			for (TaRDocument rdoc : persistentInstance.getTaRDocuments()) {
				rdoc.verifRemoveAfter(getEntityManager());
			}
			getEntityManager().remove(persistentInstance);
			logger.debug("remove successful");
		} catch (RuntimeException re) {
			logger.error("remove failed", re);
			throw re;
		}
	}

	public TaPrelevement merge(TaPrelevement detachedInstance) {
		logger.debug("merging TaPrelevement instance");
		try {
			TaPrelevement result = getEntityManager().merge(detachedInstance);
			logger.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			logger.error("merge failed", re);
			throw re;
		}
	}

	public TaPrelevement findById(int id) {
		logger.debug("getting TaPrelevement instance with id: " + id);
		try {
			TaPrelevement instance = getEntityManager().find(TaPrelevement.class, id);
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
			Query query = getEntityManager().createQuery("select count(f) from TaPrelevement f where f.codeDocument='"+code+"'");
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
	
	
	public TaPrelevement findByCode(String code) {
		logger.debug("getting TaPrelevement instance with code: " + code);
		try {
			Query query = getEntityManager().createQuery("select a from TaPrelevement a " +
					"  left join FETCH a.lignes l"+
					" where a.codeDocument='"+code+"' " +
							" order by l.numLigneLDocument");
			TaPrelevement instance = (TaPrelevement)query.getSingleResult();
			instance.setLegrain(true);
			logger.debug("get successful");
			return instance;
		} catch (RuntimeException re) {
			logger.error("get failed", re);
			throw re;
		}
	}
	
	public List<TaPrelevement> findByCodeTiersAndDate(String codeTiers, Date debut, Date fin) {
		logger.debug("getting TaPrelevement instance with codeTiers: " + codeTiers);
		List<TaPrelevement> result = null;
		try {
			Query query = getEntityManager().createNamedQuery(TaPrelevement.QN.FIND_BY_TIERS_AND_DATE);
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
		logger.debug("getting TaPrelevement instance with codeTiers: " + codeTiers);
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
				+" from TaPrelevement f "
				+"left join f.taTiers t "
				+"where  t.codeTiers = ? and f.dateDocument between ? and ? "
				+"group by "+groupBy+",t.codeTiers"
				;

//			jpql = "select  "
//			+"cast(extract(year from f.dateDocument)as string), t.codeTiers,"
//			+"case when extract ( year from f.dateDocument) <= 2001 then  sum(f.netHtCalc/6.55957) else sum(f.netHtCalc) end, "
//			+"case when extract ( year from f.dateDocument) <= 2001 then  sum(f.netTvaCalc/6.55957) else sum(f.netTvaCalc) end, "
//			+"case when extract ( year from f.dateDocument) <= 2001 then  sum(f.netTtcCalc/6.55957 ) else sum(f.netTtcCalc) end "
//			+" from TaProforma f "
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
	public List<TaPrelevement> selectAll() {
		logger.debug("TaPrelevement selectAll");
		try {
			Query ejbQuery = getEntityManager().createQuery(JPQLQuery);
			List<TaPrelevement> l = ejbQuery.getResultList();
			logger.debug("selectAll successful");
			return l;
		} catch (RuntimeException re) {
			logger.error("selectAll failed", re);
			throw re;
		}
	}
	
	/**
	 * Recherche les proforma entre 2 dates
	 * @param dateDeb - date de début
	 * @param dateFin - date de fin
	 * @return String[] - tableau contenant les IDs des proforma entre ces 2 dates (null en cas d'erreur)
	 */
	public List<TaPrelevement> rechercheDocument(Date dateDeb, Date dateFin) {

		List<TaPrelevement> result = null;
		Query query = getEntityManager().createNamedQuery(TaPrelevement.QN.FIND_BY_DATE);
		query.setParameter(1,"%");
		query.setParameter(2, dateDeb, TemporalType.DATE);
		query.setParameter(3, dateFin, TemporalType.DATE);
		result = query.getResultList();
		return result;
	}
	
	
	/**
	 * Recherche les proforma entre 2 dates ordonnées par date
	 * @param dateDeb - date de début
	 * @param dateFin - date de fin
	 * @return String[] - tableau contenant les IDs des proforma entre ces 2 dates (null en cas d'erreur)
	 */
	public List<TaPrelevement> rechercheDocumentOrderByDate(Date dateDeb, Date dateFin) {

		List<TaPrelevement> result = null;
		Query query = getEntityManager().createNamedQuery(TaPrelevement.QN.FIND_BY_DATE_PARDATE);
		query.setParameter(1,"%");
		query.setParameter(2, dateDeb, TemporalType.DATE);
		query.setParameter(3, dateFin, TemporalType.DATE);
		result = query.getResultList();
		return result;
	}
	/**
	 * Recherche les proforma entre 2 codes proforma
	 * @param codeDeb - code de début
	 * @param codeFin - code de fin
	 * @return String[] - tableau contenant les IDs des proforma entre ces 2 codes (null en cas d'erreur)
	 */
	public List<TaPrelevement> rechercheDocument(String codeDeb, String codeFin) {		
		List<TaPrelevement> result = null;
		Query query = getEntityManager().createNamedQuery(TaPrelevement.QN.FIND_BY_CODE);
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
	
	
	@Override
	public List<TaPrelevement> rechercheDocument(String codeDeb, String codeFin,
			String codeTiers) {
		return rechercheDocument(codeDeb, codeFin, codeTiers, null);
	}

	@Override
	public List<TaPrelevement> rechercheDocument(Date dateDeb, Date dateFin,
			String codeTiers) {
		return rechercheDocument(dateDeb, dateFin, codeTiers, null);
	}

	@Override
	public List<TaPrelevement> rechercheDocument(Date dateDeb, Date dateFin,
			String codeTiers, Boolean export) {
		List<TaPrelevement> result = null;
		Query query = getEntityManager().createNamedQuery(TaPrelevement.QN.FIND_BY_TIERS_AND_DATE);
		if(codeTiers==null || codeTiers.equals(""))codeTiers="%";
		query.setParameter(1, codeTiers);
		query.setParameter(2, dateDeb, TemporalType.DATE);
		query.setParameter(3, dateFin, TemporalType.DATE);
		result = query.getResultList();
		return result;
	}
	
	
	public List<TaPrelevement> recherchePrelevement(Date dateDeb, Date dateFin,
			String codeDocument, Integer export, String etat) { /**requete JPA (voir example dans TaPrelevement.java) <-- **/ 
		List<TaPrelevement> result = null;
		TaEtat taEtat=null;
		if(etat!=null && !etat.equals("TOUS")){
			TaEtatDAO daoEtat=new TaEtatDAO();
			taEtat=daoEtat.findByCode(etat);
		}
		Query query = null;
		if (export == null){
			if(taEtat==null){
				if(codeDocument==null || codeDocument.equals(""))codeDocument="%";
				query = getEntityManager().createQuery("select a from TaPrelevement a where a.codeDocument like ? and a.dateDocument between ? and ? and a.mtTtcCalc > 0 " +
						" and not exists(select rd from TaRDocument rd join rd.taDocumentGenere Dg join rd.taPrelevement p where p.idDocument=a.idDocument and dg is not null and rd.taFacture is not null)" +
						" and not exists(select rd from TaRDocument rd join rd.taDocumentGenere Dg join rd.taPrelevement p where p.idDocument=a.idDocument ) order by a.codeDocument");
				query.setParameter(1, codeDocument);
				query.setParameter(2, dateDeb, TemporalType.DATE);
				query.setParameter(3, dateFin, TemporalType.DATE);
				result = query.getResultList();				
			}else{
				if(codeDocument==null || codeDocument.equals(""))codeDocument="%";
				query = getEntityManager().createQuery("select a from TaPrelevement a where a.codeDocument like ? and a.dateDocument between ? and ? and a.mtTtcCalc > 0 and (a.taEtat.codeEtat=?) " +
						" and not exists(select rd from TaRDocument rd join rd.taDocumentGenere Dg join rd.taPrelevement p where p.idDocument=a.idDocument and dg is not null and rd.taFacture is not null)" +
						" and not exists(select rd from TaRDocument rd join rd.taDocumentGenere Dg join rd.taPrelevement p where p.idDocument=a.idDocument )  order by a.codeDocument");
				query.setParameter(1, codeDocument);
				query.setParameter(2, dateDeb, TemporalType.DATE);
				query.setParameter(3, dateFin, TemporalType.DATE);
				query.setParameter(4, taEtat.getCodeEtat());
				result = query.getResultList();
			}
			result = query.getResultList();
		}
		else {
			if(taEtat==null){
				query = getEntityManager().createQuery("select a from TaPrelevement a where a.codeDocument like ? and a.dateDocument between ? and ? and a.mtTtcCalc > 0 and a.exportBanque=?  " +
						" and not exists(select rd from TaRDocument rd join rd.taDocumentGenere Dg join rd.taPrelevement p where p.idDocument=a.idDocument and dg is not null and rd.taFacture is not null)" +
						" and not exists(select rd from TaRDocument rd join rd.taDocumentGenere Dg join rd.taPrelevement p where p.idDocument=a.idDocument )  order by a.codeDocument"); 
				if(codeDocument==null || codeDocument.equals(""))codeDocument="%";
				query.setParameter(1, codeDocument);
				query.setParameter(2, dateDeb, TemporalType.DATE);
				query.setParameter(3, dateFin, TemporalType.DATE);
				query.setParameter(4, export);
			}else{
				query = getEntityManager().createQuery("select a from TaPrelevement a where a.codeDocument like ? and a.dateDocument between ? and ? and a.mtTtcCalc > 0 and a.exportBanque=? and a.taEtat.codeEtat=? " +
						" and not exists(select rd from TaRDocument rd join rd.taDocumentGenere Dg join rd.taPrelevement p where p.idDocument=a.idDocument and dg is not null and rd.taFacture is not null)" +
						" and not exists(select rd from TaRDocument rd join rd.taDocumentGenere Dg join rd.taPrelevement p where p.idDocument=a.idDocument )  order by a.codeDocument"); 
				if(codeDocument==null || codeDocument.equals(""))codeDocument="%";
				query.setParameter(1, codeDocument);
				query.setParameter(2, dateDeb, TemporalType.DATE);
				query.setParameter(3, dateFin, TemporalType.DATE);
				query.setParameter(4, export);
				query.setParameter(5, taEtat.getCodeEtat());			
			}
			result = query.getResultList();
		}

		return result;
	}

	public List<TaPrelevement> recherchePrelevementTiers(Date dateDeb, Date dateFin,
			String codeTiers, Integer export, String etat) { /**requete JPA (voir example dans TaPrelevement.java) <-- **/ 
		List<TaPrelevement> result = null;
		TaEtat taEtat=null;
		if(etat!=null && !etat.equals("TOUS")){
			TaEtatDAO daoEtat=new TaEtatDAO();
			taEtat=daoEtat.findByCode(etat);
		}
		Query query = null;
		if (export == null){
			if(taEtat==null){
				if(codeTiers==null || codeTiers.equals(""))codeTiers="%";
				query = getEntityManager().createQuery("select a from TaPrelevement a join a.taTiers t where t.codeTiers like ? and a.dateDocument between ? and ? and a.mtTtcCalc > 0  " +
						" and not exists(select rd from TaRDocument rd join rd.taDocumentGenere Dg join rd.taPrelevement p where p.idDocument=a.idDocument and dg is not null and rd.taFacture is not null)" +
						" and not exists(select rd from TaRDocument rd join rd.taDocumentGenere Dg join rd.taPrelevement p where p.idDocument=a.idDocument )   order by a.codeDocument");
				query.setParameter(1, codeTiers);
				query.setParameter(2, dateDeb, TemporalType.DATE);
				query.setParameter(3, dateFin, TemporalType.DATE);
				result = query.getResultList();				
			}else{
				if(codeTiers==null || codeTiers.equals(""))codeTiers="%";
				query = getEntityManager().createQuery("select a from TaPrelevement a join a.taTiers t where t.codeTiers like ? and a.dateDocument between ? and ? and a.mtTtcCalc > 0 and (a.taEtat.codeEtat=?) " +
						" and not exists(select rd from TaRDocument rd join rd.taDocumentGenere Dg join rd.taPrelevement p where p.idDocument=a.idDocument and dg is not null and rd.taFacture is not null)" +
						" and not exists(select rd from TaRDocument rd join rd.taDocumentGenere Dg join rd.taPrelevement p where p.idDocument=a.idDocument )   order by a.codeDocument");
				query.setParameter(1, codeTiers);
				query.setParameter(2, dateDeb, TemporalType.DATE);
				query.setParameter(3, dateFin, TemporalType.DATE);
				query.setParameter(4, taEtat.getCodeEtat());
				result = query.getResultList();
			}
			result = query.getResultList();
		}
		else {
			if(taEtat==null){
				query = getEntityManager().createQuery("select a from TaPrelevement a join a.taTiers t where t.codeTiers like ? and a.dateDocument between ? and ? and a.mtTtcCalc > 0 and a.exportBanque=?  " +
						" and not exists(select rd from TaRDocument rd join rd.taDocumentGenere Dg join rd.taPrelevement p where p.idDocument=a.idDocument and dg is not null and rd.taFacture is not null)" +
						" and not exists(select rd from TaRDocument rd join rd.taDocumentGenere Dg join rd.taPrelevement p where p.idDocument=a.idDocument )   order by a.codeDocument"); 
				if(codeTiers==null || codeTiers.equals(""))codeTiers="%";
				query.setParameter(1, codeTiers);
				query.setParameter(2, dateDeb, TemporalType.DATE);
				query.setParameter(3, dateFin, TemporalType.DATE);
				query.setParameter(4, export);
			}else{
				query = getEntityManager().createQuery("select a from TaPrelevement a join a.taTiers t where t.codeTiers like ? and a.dateDocument between ? and ? and a.mtTtcCalc > 0 and a.exportBanque=? and a.taEtat.codeEtat=? " +
						" and not exists(select rd from TaRDocument rd join rd.taDocumentGenere Dg join rd.taPrelevement p where p.idDocument=a.idDocument and dg is not null and rd.taFacture is not null)" +
						" and not exists(select rd from TaRDocument rd join rd.taDocumentGenere Dg join rd.taPrelevement p where p.idDocument=a.idDocument )   order by a.codeDocument"); 
				if(codeTiers==null || codeTiers.equals(""))codeTiers="%";
				query.setParameter(1, codeTiers);
				query.setParameter(2, dateDeb, TemporalType.DATE);
				query.setParameter(3, dateFin, TemporalType.DATE);
				query.setParameter(4, export);
				query.setParameter(5, taEtat.getCodeEtat());			
			}
			result = query.getResultList();
		}

		return result;
	}

	
	@Override
	public List<TaPrelevement> rechercheDocument(String codeDeb,
			String codeFin, String codeTiers, Boolean export) {
		List<TaPrelevement> result = null;
		Query query = getEntityManager().createNamedQuery(TaPrelevement.QN.FIND_BY_TIERS_AND_CODE);
		if(codeTiers==null || codeTiers.equals(""))codeTiers="%";
		query.setParameter(1, codeTiers);
		query.setParameter(2, codeDeb);
		query.setParameter(3, codeFin);
		result = query.getResultList();
		return result;
	}
	
	@Override
	public List<TaPrelevement> rechercheDocumentEtat(Date dateDeb, Date datefin, String codeEtat) {
		List<TaPrelevement> result = null;
		Query query = null;
		if(codeEtat!=null) {
			query = getEntityManager().createNamedQuery(TaPrelevement.QN.FIND_BY_ETAT_DATE);
			query.setParameter(1, dateDeb);
			query.setParameter(2, datefin);
			query.setParameter(3, codeEtat);
		} else {
			query = getEntityManager().createNamedQuery(TaPrelevement.QN.FIND_BY_ETAT_ENCOURS_DATE);
			query.setParameter(1, datefin);
		}
		
		result = query.getResultList();
		return result;
	}

	@Override
	public List<TaPrelevement> rechercheDocumentEtat(Date dateDeb, Date datefin,
			String codeEtat, String codeTiers) {
		List<TaPrelevement> result = null;
		Query query = null;
		if(codeEtat!=null) {
			query = getEntityManager().createNamedQuery(TaPrelevement.QN.FIND_BY_ETAT_TIERS_DATE);
			query.setParameter(1, codeTiers);
			query.setParameter(2, dateDeb);
			query.setParameter(3, datefin);
			query.setParameter(4, codeEtat);		
		} else {
			query = getEntityManager().createNamedQuery(TaPrelevement.QN.FIND_BY_ETAT_TIERS_ENCOURS_DATE);
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
			query =getEntityManager().createNamedQuery(TaPrelevement.QN.FIND_BY_DATE_LIGHT);
		else query = getEntityManager().createNamedQuery(TaPrelevement.QN.FIND_BY_DATE);
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
		query = getEntityManager().createNamedQuery(TaPrelevement.QN.FIND_BY_DATE_LIGHT);
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
			Query query = getEntityManager().createNamedQuery(TaPrelevement.QN.FIND_BY_TIERS_AND_CODE_LIGHT);
			query.setParameter(1,"%");
			query.setParameter(2, codeDoc+"%");
			result = query.getResultList();
			return result;

		}

	
}

