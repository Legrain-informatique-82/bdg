package fr.legrain.documents.dao;

// Generated Apr 7, 2009 3:27:24 PM by Hibernate Tools 3.2.0.CR1

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.NamedQuery;
import javax.persistence.Query;
import javax.persistence.TemporalType;

import org.apache.log4j.Logger;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ui.PlatformUI;

import fr.legrain.SupportAbon.dao.TaSupportAbon;
import fr.legrain.SupportAbon.dao.TaSupportAbonDAO;
import fr.legrain.abonnement.dao.TaAbonnement;
import fr.legrain.abonnement.dao.TaAbonnementDAO;
import fr.legrain.articles.dao.TaPrix;
import fr.legrain.articles.dao.TaUnite;
import fr.legrain.gestCom.Appli.AbstractApplicationDAO;
import fr.legrain.gestCom.Appli.Const;
import fr.legrain.gestCom.Module_Document.IDocumentTiers;
import fr.legrain.gestCom.Module_Document.SWTDocument;
import fr.legrain.lib.data.LibConversion;
import fr.legrain.lib.data.LibDate;
import fr.legrain.pointLgr.dao.TaComptePoint;
import fr.legrain.pointLgr.dao.TaComptePointDAO;
import fr.legrain.tiers.dao.TaTiers;

/**
 * Home object for domain model class TaFacture.
 * @see fr.legrain.documents.dao.TaFacture
 * @author Hibernate Tools
 */
public class TaFactureDAO extends AbstractApplicationDAO<TaFacture> 
implements IDocumentTiersDAO<TaFacture>,IDocumentTiersStatistiquesDAO<TaFacture>,IDocumentTiersEtatDAO<TaFacture> {

//	private static final Log log = LogFactory.getLog(TaFactureDAO.class);
	static Logger logger = Logger.getLogger(TaFactureDAO.class);
	private String defaultJPQLQuery = "select a from TaFacture a ";
	private String supplementJPQLQuery = " order by codeDocument";
//	private String defaultJPQLQuery = "select a from TaFacture a";
	public static final String FIND_BY_DATE_MAINTENANCE = "select distinct a from TaFacture a  join a.lignes b where  a.codeDocument like('%') "+
	" and  b.taArticle.taFamille.idFamille between 21 and 21  and a.dateDocument between ? and ?  order by a.codeDocument"; 
	public static final String FIND_BY_CODE_MAINTENANCE = "select distinct a from TaFacture a  join a.lignes b where  a.codeDocument like('%') "+
	" and  (b.taArticle.taFamille.idFamille = 21 or b.taArticle.taFamille.idFamille= 44)  and a.codeDocument between ? and ?  order by a.codeDocument";
	public static final String FIND_BY_MAINTENANCE = "select distinct alias from TaFacture alias  join alias.lignes b where b.taArticle.taFamille.idFamille between 21 and 21 " +
	  " and (alias.dateDocument between (select datedebInfoEntreprise from TaInfoEntreprise ) and (select datefinInfoEntreprise from TaInfoEntreprise ))";
	
	public TaFactureDAO(){
		this(null);
	}
	
	public TaFactureDAO(EntityManager em){
		if(em!=null) {
			setEm(em);
		}
//		champIdTable=ctrlGeneraux.getID_TABLE(TaFacture.class.getSimpleName());
		initChampId(TaFacture.class);
		setJPQLQuery(defaultJPQLQuery);
		setListeChampMaj(Const.C_FICHIER_INI_CHAMPMAJ);
		initNomTable(new TaFacture());
	}
	
	public TaFacture refresh(TaFacture detachedInstance) {
        logger.debug("refresh instance");
        try {
        	Map<IDocumentTiers,Boolean> listeEtatLegrain=new LinkedHashMap<IDocumentTiers, Boolean>();
        	
        	detachedInstance.setLegrain(false);
        	List<TaRReglement>  listeTempReglement = new LinkedList<TaRReglement>();
        	for (TaRReglement rReglement : detachedInstance.getTaRReglements()) {
				if(rReglement.equals(detachedInstance.getTaRReglement()) && detachedInstance.getTaRReglement().getId()==0)
					listeTempReglement.add(rReglement);
			}

        	for (TaRAcompte obj : detachedInstance.getTaRAcomptes()) {
        		listeEtatLegrain.put(obj.getTaAcompte(),obj.getTaAcompte().isLegrain());
        		obj.getTaAcompte().setLegrain(false);
			}
        	for (TaRAvoir obj : detachedInstance.getTaRAvoirs()) {
        		listeEtatLegrain.put(obj.getTaAvoir(),obj.getTaAvoir().isLegrain());
        		obj.getTaAvoir().setLegrain(false);
			}
        	
			for (TaRReglement taRReglement : listeTempReglement) {
				detachedInstance.removeReglement(taRReglement);
			}
        	
			getEntityManager().refresh(detachedInstance);
        	
			for (TaRReglement taRReglement : listeTempReglement) {
				detachedInstance.addRReglement(taRReglement);
			}
			
        	for (TaRAcompte obj : detachedInstance.getTaRAcomptes()) {        		
        		obj.getTaAcompte().setLegrain(retourneEtatLegrain(listeEtatLegrain,obj.getTaAcompte()));
			}
        	for (TaRAvoir obj : detachedInstance.getTaRAvoirs()) {
        		obj.getTaAvoir().setLegrain(retourneEtatLegrain(listeEtatLegrain,obj.getTaAvoir()));
			}
//            org.hibernate.Session session = (org.hibernate.Session) getEntityManager().getDelegate();
//
//            for (TaRAcompte a : detachedInstance.getTaRAcomptes()) {
////            	new TaAcompteDAO(getEntityManager()).refresh(a.getTaAcompte());
//                 session.evict(a.getTaAcompte().getTaRAcomptes());
//                 session.evict(a.getTaAcompte());
//            }
//            
//            for (TaRAvoir a : detachedInstance.getTaRAvoirs()) {
////            	new TaAvoirDAO(getEntityManager()).refresh(a.getTaAvoir());
//                session.evict(a.getTaAvoir().getTaRAvoirs());
//                session.evict(a.getTaAvoir());
//           }
//            
//            for (TaRReglement a : detachedInstance.getTaRReglements()) {
//                session.evict(a.getTaReglement().getTaRReglements());
//                session.evict(a.getTaReglement());
//                session.evict(a.getTaFacture().getTaRReglement());
////                for (TaRReglement b : a.getTaFacture().getTaRReglements()) {
////                	session.evict(a.getTaReglement());
////				} 
//           }
//
//            session.evict(detachedInstance.getTaRReglement());
//            
//             
//            session.evict(detachedInstance.getTaRAcomptes());
//            session.evict(detachedInstance.getTaRAvoirs());
//            session.evict(detachedInstance.getTaRReglements());
//            session.evict(detachedInstance);
//            
//            detachedInstance = getEntityManager().find(TaFacture.class, detachedInstance.getIdDocument());
            detachedInstance.setLegrain(true);
            detachedInstance.calculLignesTva();
            detachedInstance.affecteReglementFacture(null);
            return detachedInstance;

        } catch (RuntimeException re) {
            logger.error("refresh failed", re);
            throw re;
        } catch (Exception e) {
        	 logger.error("refresh failed", e);
        	 return null;
		}
    } 
    
	public void persist(TaFacture transientInstance) {
		logger.debug("persisting TaFacture instance");
		try {
			getEntityManager().persist(transientInstance);
			logger.debug("persist successful");
		} catch (RuntimeException re) {
			logger.error("persist failed", re);
			throw re;
		}
	}

	public void remove(TaFacture persistentInstance) {
		logger.debug("removing TaFacture instance");
		try {
			getEntityManager().remove(persistentInstance);
			logger.debug("remove successful");
		} catch (RuntimeException re) {
			logger.error("remove failed", re);
			throw re;
		}
	}
	
//	public void annuler(TaFacture persistentInstance) throws Exception{
//		persistentInstance.setLegrain(false);
//		List<TaLFacture> listTemp=new ArrayList<TaLFacture>(0);
//		for (Object ligne : persistentInstance.getLignes()) {
//			if(((TaLFacture)ligne).getIdLDocument()==0)
//				listTemp.add((TaLFacture)ligne);				
//		}
//		for (TaLFacture taLFacture : listTemp) {
//			persistentInstance.getLignes().remove(taLFacture);
//		}
//		super.annuler(persistentInstance);
//	}
	

	public TaFacture merge(TaFacture detachedInstance) {
		logger.debug("merging TaFacture instance");
		try {
			TaFacture result = getEntityManager().merge(detachedInstance);
			logger.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			logger.error("merge failed", re);
			throw re;
		}
	}

	public TaFacture findById(int id) {
		logger.debug("getting TaFacture instance with id: " + id);
		try {
			TaFacture instance = getEntityManager().find(TaFacture.class, id);
			logger.debug("get successful");
			return instance;
		} catch (RuntimeException re) {
			logger.error("get failed", re);
			throw re;
		}
	}
	
	public List<TaFacture> findByChamp(String champ,Object value) {
		logger.debug("getting TaFacture instance with champ : " + champ);
		try {
			Query query = getEntityManager().createQuery("select a from TaFacture a " +
					"where "+champ+" = '"+value+"'");
			List<TaFacture> l = query.getResultList();
			return l;
		} catch (RuntimeException re) {
			throw re;
		}
	}
	
	public List<TaFacture> findByCodeTiers(String codeTiers) {
		logger.debug("getting TaFacture instance with codeTiers: " + codeTiers);
		List<TaFacture> result = null;
		try {
			Query query = getEntityManager().createNamedQuery(TaFacture.QN.FIND_BY_TIERS);
			query.setParameter(1, codeTiers);
			result = query.getResultList();
			return result;
		} catch (RuntimeException re) {
			logger.error("get failed", re);
			throw re;
		}
	}
	
	public List<TaFacture> findByCodeTiersAndDate(String codeTiers, Date debut, Date fin) {
		logger.debug("getting TaFacture instance with codeTiers: " + codeTiers);
		List<TaFacture> result = null;
		try {
			Query query = getEntityManager().createNamedQuery(TaFacture.QN.FIND_BY_TIERS_AND_DATE);
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
		logger.debug("getting TaFacture instance with codeTiers: " + codeTiers);
		List<Object> result = null;
		try {
			String jpql = null;
			String groupBy = null;
			String select = null;

//			jpql = "select  "
//			+"cast(extract(year from f.dateDocument)as string), t.codeTiers,"
//			+"case when extract ( year from f.dateDocument) <= 2001 then  sum(f.netHtCalc/6.55957) else sum(f.netHtCalc) end, "
//			+"case when extract ( year from f.dateDocument) <= 2001 then  sum(f.netTvaCalc/6.55957) else sum(f.netTvaCalc) end, "
//			+"case when extract ( year from f.dateDocument) <= 2001 then  sum(f.netTtcCalc/6.55957 ) else sum(f.netTtcCalc) end "
//			+" from TaFacture f "
//			+"left join f.taTiers t "
//			+"where  t.codeTiers = ? and f.dateDocument between ? and ? "
//			+"group by extract(year from f.dateDocument),t.codeTiers"
//			;
			
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
			+" from TaFacture f "
			+"left join f.taTiers t "
			+"where  t.codeTiers = ? and f.dateDocument between ? and ? "
			+"group by "+groupBy+",t.codeTiers"
			;
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

	public Boolean exist(String code) {
		logger.debug("exist with code: " + code);
		try {
			if(code!=null && !code.equals("")){
			Query query = getEntityManager().createQuery("select count(f) from TaFacture f where f.codeDocument='"+code+"'");
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
	
	public TaFacture findByCode(String code) {
		logger.debug("getting TaFacture instance with code: " + code);
		try {
			Query query = getEntityManager().createQuery("select a from TaFacture a " +
					" left join FETCH a.lignes l"+
					" where a.codeDocument='"+code+"' " +
							" order by l.numLigneLDocument");		
			TaFacture instance = (TaFacture)query.getSingleResult();
			instance.setLegrain(true);
			instance.affecteReglementFacture(null);
			logger.debug("get successful");
			return instance;
		} catch (RuntimeException re) {
			logger.error("get failed", re);
			throw re;
		} catch (Exception e) {
			return null;
		}
	}
	
	/* (non-Javadoc)
	 * @see fr.legrain.articles.dao.ILgrDAO#selectAll()
	 */
	public List<TaFacture> selectAll() {
		logger.debug("selectAll TaFacture");
		try {
			Query ejbQuery = getEntityManager().createQuery(JPQLQuery+supplementJPQLQuery);
			List<TaFacture> l = ejbQuery.getResultList();
			logger.debug("selectAll successful");
			return l;
		} catch (RuntimeException re) {
			logger.error("selectAll failed", re);
			throw re;
		}
	}
	

	
	/**
	 * Recherche les factures entre 2 codes factures
	 * @param codeDeb - code de début
	 * @param codeFin - code de fin
	 * @return String[] - tableau contenant les IDs des factures entre ces 2 codes (null en cas d'erreur)
	 */
	//public String[] rechercheFacture(String codeDeb, String codeFin) {
	public List<TaFacture> rechercheDocument(String codeDeb, String codeFin) {
		List<TaFacture> result = null;
		Query query = getEntityManager().createNamedQuery(TaFacture.QN.FIND_BY_CODE);
		query.setParameter(1,"%");
		query.setParameter(2, codeDeb);
		query.setParameter(3, codeFin);
		result = query.getResultList();
		return result;

	}
	
	//Fiche_CA_Articles_AnneeCourante_Tous.rptdesign
	//select a from TaFacture a where a.dateFacture between ? and ?
	/**
	 * select TA_FACTURE.CODE_FACTURE,TA_FACTURE.ID_TIERS,TA_FACTURE.LIBELLE_FACTURE,
	 * cast(TA_FACTURE.DATE_FACTURE as date),TA_ARTICLE.CODE_ARTICLE from TA_L_FACTURE  
	 * left join TA_FACTURE on (TA_FACTURE.ID_FACTURE = TA_L_FACTURE.ID_FACTURE )
	 * left join TA_ARTICLE on (TA_ARTICLE.ID_ARTICLE = TA_L_FACTURE.ID_ARTICLE ) 
	 * where TA_L_FACTURE.ID_ARTICLE=? And 
	 * cast(TA_FACTURE.DATE_FACTURE as date) 
	 * between ( select cast (TA_INFO_ENTREPRISE.DATEDEB_INFO_ENTREPRISE as date ) from TA_INFO_ENTREPRISE) 
	 * and ( select cast (TA_INFO_ENTREPRISE.DATEFIN_INFO_ENTREPRISE as date ) from TA_INFO_ENTREPRISE) 
	 * order by TA_FACUTRE.CODE_FACTURE,cast(TA_FACTURE.DATE_FACTURE as date)
	 */
	
	public List<Object> rechercheAllFactureBaseArticle(int idArticle,Date dateDeb, Date dateFin){
		
/***************** example entre sql et jpql******************/
//		select i
//		from Item i join i.bids b
//		where i.description like '%Foo%'
//		and b.amount > 100
//		
//		select i.DESCRIPTION, i.INITIAL_PRICE, ...
//		from ITEM i
//		inner join BID b on i.ITEM_ID = b.ITEM_ID
//		where i.DESCRIPTION like '%Foo%'
//		and b.AMOUNT > 100
/***********************************************************/
		//fr.legrain.lib.data.LibConversion.stringToInteger() as aa
		LibConversion libConversion = new LibConversion();
		//String jpql = "select tlf from TaLFacture tlf where tlf.taDocument.idDocument = 1"; 

		String jpql = "select taA from TaAdresse taA where taA.taTiers.idTiers=? and taA.taTAdr.codeTAdr=?";
		
		
//		Calendar calendarDateFin = Calendar.getInstance();
//		calendarDateFin.setTime(dateFin);
//		calendarDateFin.set(Calendar.YEAR, calendarDateFin.get(Calendar.YEAR)-1);
//		Date fin = calendarDateFin.getTime();
//		Timestamp timestampFin = new Timestamp(fin.getTime());
//		
//		Calendar calendarDateStart = Calendar.getInstance();
//		calendarDateStart.setTime(dateDeb);
//		calendarDateStart.set(Calendar.YEAR, calendarDateStart.get(Calendar.YEAR)-1);
//		Date deb = calendarDateStart.getTime();
//		Timestamp timestampDeb = new Timestamp(deb.getTime());
//		
//		String jpql = "select " +
//		   "tf.dateDocument " +
//		   "from TaLFacture tlf "+
//		   "left join tlf.taDocument tf ";
		   

		Query query = getEntityManager().createQuery(jpql);
		//Query query = getEntityManager().createNativeQuery(jpql);
		query.setParameter(1, 1);
		query.setParameter(2, "");
//		query.setParameter(3, dateFin);
		
//		query.setParameter(1, timestampDeb);
//		query.setParameter(2, timestampFin);
		
		
//		List<Object> listObjectQuery = new ArrayList<Object>();
//		for (int i = 0; i < query.getResultList().size(); i++) {
//			TaFacture taFacture = (TaFacture) query.getResultList().get(i);
//			taFacture.calculeTvaEtTotaux();
//			listObjectQuery.add(taFacture);
//		}
		System.out.println(jpql);
		List<Object> listObjectQuery = query.getResultList();
		return listObjectQuery;
	}
	
	
	
	/**
	 * Recherche les factures entre 2 dates
	 * @param dateDeb - date de début
	 * @param dateFin - date de fin
	 * @return String[] - tableau contenant les IDs des factures entre ces 2 dates (null en cas d'erreur)
	 */
	//public String[] rechercheFacture(Date dateDeb, Date dateFin) {
	public List<TaFacture> rechercheDocument(Date dateDeb, Date dateFin) {
		List<TaFacture> result = null;
		Query query = getEntityManager().createNamedQuery(TaFacture.QN.FIND_BY_DATE);
		query.setParameter(1,"%");
		query.setParameter(2, dateDeb, TemporalType.DATE);
		query.setParameter(3, dateFin, TemporalType.DATE);
		result = query.getResultList();
		
		
		return result;
		
	}
	
	
//	/**
//	 * Recherche les factures light entre 2 dates
//	 * @param dateDeb - date de début
//	 * @param dateFin - date de fin
//	 * @return String[] - tableau contenant les IDs des factures entre ces 2 dates (null en cas d'erreur)
//	 */
//	//public String[] rechercheFacture(Date dateDeb, Date dateFin) {
//	public List<Object[]> rechercheDocumentLight(Date dateDeb, Date dateFin) {
//		List<Object[]> result = null;
//		Query query = getEntityManager().createNamedQuery(TaFacture.QN.FIND_BY_DATE_LIGHT);
//		query.setParameter(1,"%");
//		query.setParameter(2, dateDeb, TemporalType.DATE);
//		query.setParameter(3, dateFin, TemporalType.DATE);
//		result = query.getResultList();
//		
//		
//		return result;
//		
//	}
	
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
			query =getEntityManager().createNamedQuery(TaFacture.QN.FIND_BY_DATE_LIGHT);
		else query = getEntityManager().createNamedQuery(TaFacture.QN.FIND_BY_DATE);
		query.setParameter(1,"%");
		query.setParameter(2, dateDeb, TemporalType.DATE);
		query.setParameter(3, dateFin, TemporalType.DATE);
		result = query.getResultList();
		
		
		return result;
		
	}	
	
	public List<Object[]> rechercheDocumentLight(Date dateDeb, Date dateFin,String codeTiers) {
		List<Object[]> result = null;
		Query query = null;
		query = getEntityManager().createNamedQuery(TaFacture.QN.FIND_BY_DATE_LIGHT);
		if(codeTiers==null || codeTiers.equals(""))codeTiers="%";
		query.setParameter(1, codeTiers);
		query.setParameter(2, dateDeb, TemporalType.DATE);
		query.setParameter(3, dateFin, TemporalType.DATE);
		result = query.getResultList();
		return result;
	}
	/**
	 * Recherche les factures entre 2 dates ordonnées par date
	 * @param dateDeb - date de début
	 * @param dateFin - date de fin
	 * @return String[] - tableau contenant les IDs des factures entre ces 2 dates (null en cas d'erreur)
	 */
	//public String[] rechercheFacture(Date dateDeb, Date dateFin) {
	public List<TaFacture> rechercheDocumentOrderByDate(Date dateDeb, Date dateFin) {
		List<TaFacture> result = null;
		Query query = getEntityManager().createNamedQuery(TaFacture.QN.FIND_BY_DATE_PARDATE);
		query.setParameter(1,"%");
		query.setParameter(2, dateDeb, TemporalType.DATE);
		query.setParameter(3, dateFin, TemporalType.DATE);
		result = query.getResultList();
		
		
		return result;
		
	}
	/**
	 * Recherche les factures entre 2 dates et non exportees
	 * @param dateDeb - date de début
	 * @param dateFin - date de fin
	 * @return String[] - tableau contenant les IDs des factures entre ces 2 dates (null en cas d'erreur)
	 */
	public List<TaFacture> rechercheDocumentNonExporte(Date dateDeb, Date dateFin,Boolean parDate) {
		List<TaFacture> result = null;
		Query query =null;
		if(parDate)query = getEntityManager().createNamedQuery(TaFacture.QN.FIND_BY_DATE_NON_EXPORT_PARDATE);
		else
		query = getEntityManager().createNamedQuery(TaFacture.QN.FIND_BY_DATE_NON_EXPORT);
		query.setParameter(1,"%");
		query.setParameter(2, dateDeb, TemporalType.DATE);
		query.setParameter(3, dateFin, TemporalType.DATE);
		result = query.getResultList();
		
		
		return result;
		
	}
	/**
	 * Recupere la liste des facture exportees ou non exportees
	 * @param export - vrai pour le facture deja exportees, faux pour celles non exportees
	 * @return
	 */
	public List<TaFacture> findByExport(boolean export) {
		try {
			Query query = getEntityManager().createQuery("select a from TaFacture a " +
					"where a.export=?");
			if(export)
				query.setParameter(1, 1);
			else 
				query.setParameter(1, 0);
			List<TaFacture> l = query.getResultList();
			return l;
		} catch (RuntimeException re) {
			logger.error("findByExport failed", re);
			throw re;
		}
	}
	
	public List<TaFacture> rechercheFactureMaintenance(String codeDeb, String codeFin) {
		
		List<TaFacture> result = null;
//		String requete ="select distinct a from TaFacture a " +
//		" join a.lignes b"+
//		" where  a.codeDocument like('%') "+
//		" and  b.taArticle.taFamille.idFamille between 21 and 21 "
//		+" and a.codeDocument between ? and ?  order by a.codeDocument";
//		Query query = getEntityManager().createQuery(requete);
		Query query =getEntityManager().createQuery(FIND_BY_CODE_MAINTENANCE);
		query.setParameter(1, codeDeb);
		query.setParameter(2, codeFin);
		result = query.getResultList();
		return result;

	}
	
	public List<TaFacture> rechercheFactureMaintenance(Date dateDeb, Date dateFin) {
		
		List<TaFacture> result = null;
//		String requete ="select distinct a from TaFacture a " +
//				" join a.lignes b"+
//		  " where  a.codeDocument like('%') "+
//		  " and  b.taArticle.taFamille.idFamille between 21 and 21 "
//		+" and a.dateDocument between ? and ?  order by a.codeDocument";
		
		Query query =getEntityManager().createQuery(FIND_BY_DATE_MAINTENANCE);
//		Query query = getEntityManager().createQuery(requete);
		query.setParameter(1, dateDeb);
		query.setParameter(2, dateFin);
		result = query.getResultList();
		return result;

	}

	@Override
	public int selectCount() {
		// TODO Auto-generated method stub
		return 1;
	}

	@Override
	public List<TaTiers> findTiersByCodeArticleAndDate(String codeArticle, Date debut, Date fin) {
		logger.debug("getting TaFacture instance with codeTiers: " + codeArticle);
		List<TaTiers> result = null;
		try {
			String jpql = null;
			jpql = "select distinct t "
				+" from TaFacture f "
				+"left join f.taTiers t "
				+"left join f.lignes lf "
				+"left join lf.taArticle art "
				+"where  art.codeArticle = ? and f.dateDocument between ? and ? "
				;
				Query query = getEntityManager().createQuery(jpql);
			query.setParameter(1, codeArticle);
			query.setParameter(2, debut);
			query.setParameter(3, fin);
			result = query.getResultList();
			return result;
		} catch (RuntimeException re) {
			logger.error("get failed", re);
			throw re;
		}
	}

	@Override
	public List<Object> findChiffreAffaireByCodeArticle(String codeArticle, Date debut, Date fin, int precision) {
		logger.debug("getting ChiffreAffaire instance with codeArticle: " + codeArticle);
		List<Object> result = null;
		try {
			String jpql = null;
			String groupBy = null;
			String select = null;
			/*
select  substring(a.codepostalAdresse,1,2) as departement,
 extract(year from f.dateDocument)as annee, 
t.codeTiers as codeTiersFacture, t.nomTiers as nomTiersFacture,
 case when extract ( year from f.dateDocument) <= 2001 
then  sum(lf.mtHtLApresRemiseGlobaleDocument/6.55957) else sum(lf.mtHtLApresRemiseGlobaleDocument) end,
 case when extract ( year from f.dateDocument) <= 2001 
then  sum(lf.mtTtcLApresRemiseGlobaleDocument-lf.mtHtLApresRemiseGlobaleDocument/6.55957) else sum(lf.mtTtcLApresRemiseGlobaleDocument-lf.mtHtLApresRemiseGlobaleDocument) end,
 case when extract ( year from f.dateDocument) <= 2001 
then  sum(lf.mtTtcLApresRemiseGlobaleDocument/6.55957 ) else sum(lf.mtTtcLApresRemiseGlobaleDocument) end,
 art.codeArticle,fa.codeFamille  
from TaFacture f 
left join f.taTiers t 
left join t.taAdresse a 
left join f.lignes lf 
left join lf.taArticle art 
left join art.taFamille fa 
where 
lf.taArticle is not null   
and  ((t.codeTiers)  like  '00001%' or (t.codeTiers)  >=  '00001') group by substring(a.codepostalAdresse,1,2),
extract(year from f.dateDocument),
t.codeTiers,
t.nomTiers,
art.codeArticle,
fa.codeFamille
			 */
			
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
			+select+", " +
					//"t.codeTiers,"
					"art.codeArticle,"
			+"case when extract ( year from f.dateDocument) <= 2001 then  sum(f.netHtCalc/6.55957) else sum(lf.mtHtLDocument) end, "
			+"case when extract ( year from f.dateDocument) <= 2001 then  sum(f.netTvaCalc/6.55957) else sum(lf.mtTtcLDocument-lf.mtHtLDocument) end, "
			+"case when extract ( year from f.dateDocument) <= 2001 then  sum(f.netTtcCalc/6.55957 ) else sum(lf.mtTtcLDocument) end "
			+" from TaFacture f "
			+"left join f.taTiers t "
			+"left join f.lignes lf "
			+"left join lf.taArticle art "
			+"where  art.codeArticle = ? and f.dateDocument between ? and ? "
			+"group by "+groupBy +
//					",t.codeTiers"
					",art.codeArticle"
			;
			Query query = getEntityManager().createQuery(jpql);
			query.setParameter(1, codeArticle);
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
			+" FROM TaFacture f " 
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
	 * @param limiter Si limiter est vrai, alors la liste retournée retourner un nombre de résultats égal à  nbResult
	 * Si cet int est positif, il sort les leNbResult meilleurs
	 * Si cet int est négatif, il sort les leNbResult moins bons
	 * @return La requête renvoyée renvoi le CA, le codeTiers, le nom et le CP
	 */
	public List<Object> findMeilleursClientsParCA(Date debut, Date fin, int leNbResult, boolean limiter) {
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
			+" FROM TaFacture f " 
			+" left join f.taTiers t"
			+" left join t.taAdresse a"
			+" where f.dateDocument between ? and ?"
			+" group by t.codeTiers, t.nomTiers, a.codepostalAdresse"
			+" order by sum(f.mtHtCalc)"+ordre;
			Query query = getEntityManager().createQuery(requete);
			query.setParameter(1, debut);
			query.setParameter(2, fin);
			if (limiter){
				query.setMaxResults(nbResult);
			}
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
	 * @param limiter Si limiter est vrai, alors la liste retournée retourner un nombre de résultats égal à  nbResult
	 * Si cet int est positif, il sort les leNbResult meilleurs
	 * Si cet int est négatif, il sort les leNbResult moins bons
	 * @return La requête renvoyée renvoi le CA, le codeTiers, le nom et le CP
	 */
	public List<Object> findMeilleursArticlesParCA(Date debut, Date fin, int leNbResult, boolean limiter) {
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
			+" FROM TaLFacture l " 
			+" left join l.taDocument f"
			+" left join l.taArticle a"
			+" left join a.taPrix p"
			+" where f.dateDocument between ? and ?"
			+" group by a.codeArticle, a.libellecArticle, p.prixPrix"
			+" order by sum(l.mtHtLDocument)"+ordre;
			Query query = getEntityManager().createQuery(requete);
			query.setParameter(1, debut);
			query.setParameter(2, fin);
			if (limiter){
				query.setMaxResults(nbResult);
			}
			result = query.getResultList();
			return result;
		} catch (RuntimeException re) {
			logger.error("get failed", re);
			throw re;
		}
	}
	
	
	/**
	 * 
	 * @param codeArticle
	 * @param debut
	 * @param fin
	 * @param qte1OuQte2 - 1 pour selectionner les quantités 1 et 2 pour selectionne les quantités 2 
	 * @return
	 */
	public List<Object> findQuantiteByCodeArticle(String codeArticle, Date debut, Date fin, int qte1OuQte2, int precision) {
		logger.debug("getting Quantite instance with codeArticle: " + codeArticle);
		List<Object> result = null;
		try {
			String jpql = null;
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
			+select+", " +
					"art.codeArticle,";
			
			if(qte1OuQte2==1) {
				jpql += "lf.u1LDocument,sum(lf.qteLDocument)";
			} else {
				jpql += "lf.u2LDocument,sum(lf.qte2LDocument)";
			}

			jpql += " from TaFacture f "
			+"left join f.taTiers t "
			+"left join f.lignes lf "
			+"left join lf.taArticle art "
			+"where  art.codeArticle = ? and f.dateDocument between ? and ? "
			+"group by "+groupBy +
					",art.codeArticle";
			if(qte1OuQte2==1) {
				jpql += ",lf.u1LDocument";
			} else {
				jpql += ",lf.u2LDocument";
			}
			
			Query query = getEntityManager().createQuery(jpql);
			query.setParameter(1, codeArticle);
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
	 * Recherche les factures non totalement réglées entre 2 dates
	 * @param dateDeb - date de début
	 * @param dateFin - date de fin
	 * @param tiers - tiers sur lequel porte la recherche, si null, alors on prend tous les tiers
	 * @return String[] - tableau contenant les IDs des factures entre ces 2 dates (null en cas d'erreur)
	 */
	public List<TaFacture> rechercheDocumentNonTotalementRegle(Date dateDeb, Date dateFin,String tiers,String document) {
		List<TaFacture> result = new LinkedList<TaFacture>();
		try {
			String[] lines = null;
			if(tiers!=null && !tiers.equals(""))
				 lines = tiers.split("\r\n|\r|\n");
		List<TaFacture> resultTemp = null;
		String codeTiers="%";
		String codeDocument="%";
		String requete="select a from TaFacture a where " ;
		if(lines==null || lines.length==0)
			requete+=" (a.taTiers.codeTiers like '%') and ";
		else{
			requete+="(";
		for (int i = 0; i < lines.length; i++) {
			if(i>0)requete+=" or ";
			requete+=" a.taTiers.codeTiers like upper('"+lines[i]+"') ";
		}
		requete+=") and";
		}
		//requete+=" a.taTiers.codeTiers like ? " ;
		requete+=" a.codeDocument like ?" +
				" and a.dateDocument between ? and ? " +
				//" and a.regleCompletDocument < a.netTtcCalc " +
				" order by a.codeDocument";
		Query query = getEntityManager().createQuery(requete);
		if(tiers != null && !tiers.equals(""))codeTiers=tiers.toUpperCase();
		if(dateDeb==null)dateDeb=new Date(0);
		if(dateFin==null)dateFin=new Date("01/01/3000");
		if(document!=null && !document.equals(""))codeDocument=document.toUpperCase();

//		query.setParameter(1, codeTiers);
		query.setParameter(1, codeDocument);
		query.setParameter(2, dateDeb, TemporalType.DATE);
		query.setParameter(3, dateFin, TemporalType.DATE);
		
		resultTemp=query.getResultList();;
		for (TaFacture taFacture : resultTemp) {
			taFacture.calculSommeAvoir();
			taFacture.calculSommeAcomptes();
			if(taFacture.calculRegleDocumentComplet().compareTo(taFacture.getNetTtcCalc())!=0)
				result.add(taFacture);
		}
		
	} catch (Exception e) {
		logger.error("", e);
	}
		return result;
		
	}
//	/**
//	 * Recherche les factures non totalement réglées dont la date d'échéance rentre dans l'intervalle
//	 * des 2 dates passées en paramètres
//	 * @param dateDeb - date de début
//	 * @param dateFin - date de fin
//	 * @param tiers - tiers sur lequel porte la recherche, si null, alors on prend tous les tiers
//	 * @return String[] - tableau contenant les IDs des factures entre ces 2 dates (null en cas d'erreur)
//	 */
//	public List<TaFacture> rechercheDocumentNonTotalementRegleAEcheance(Date dateDeb, Date dateFin,String tiers,String document,BigDecimal limite) {
//		if(limite==null)limite=BigDecimal.valueOf(0);
//		List<TaFacture> result = new LinkedList<TaFacture>();
//		List<TaFacture> resultTemp = null;
//		String codeTiers="%";
//		String codeDocument="%";
//		String requete="select f from TaFacture f " +
//				"where f.taTiers.codeTiers like ? " +
//				" and f.codeDocument like ?" +
//				" and f.dateEchDocument between ? and ? " +
//				//" and f.regleCompletDocument < f.netTtcCalc " +
//				" order by f.taTiers.codeTiers,f.codeDocument";
//		Query query = getEntityManager().createQuery(requete);
//		if(tiers != null && !tiers.equals(""))codeTiers=tiers.toUpperCase();
//		if(dateDeb==null)dateDeb=new Date(0);
//		if(dateFin==null)dateFin=new Date("01/01/3000");
//		if(document!=null && !document.equals(""))codeDocument=document.toUpperCase();
//
//		query.setParameter(1, codeTiers);
//		query.setParameter(2, codeDocument);
//		query.setParameter(3, dateDeb, TemporalType.DATE);
//		query.setParameter(4, dateFin, TemporalType.DATE);
//		
//		resultTemp=query.getResultList();;
//		for (TaFacture taFacture : resultTemp) {
//			taFacture.calculSommeAvoir();
//			taFacture.calculSommeAcomptes();
//			if(taFacture.calculRegleDocumentComplet().add(taFacture.getAcomptes()).add(taFacture.calculSommeAvoir()).compareTo(taFacture.getNetTtcCalc())<0)
//				result.add(taFacture);
//		}		
//		return result;
//		
//	}
	/**
	 * Recherche les factures non totalement réglées dont la date d'échéance rentre dans l'intervalle
	 * des 2 dates passées en paramètres
	 * @param dateDeb - date de début
	 * @param dateFin - date de fin
	 * @param tiers - tiers sur lequel porte la recherche, si null, alors on prend tous les tiers
	 * @return String[] - tableau contenant les IDs des factures entre ces 2 dates (null en cas d'erreur)
	 */
	public List<TaFacture> rechercheDocumentNonTotalementRegleAEcheance(Date dateDeb, Date dateFin,String tiers,String document,BigDecimal limite) {
		if(limite==null)limite=BigDecimal.valueOf(0);
		List<TaFacture> result = new LinkedList<TaFacture>();
		List<TaFacture> resultTemp = null;
		String codeTiers="%";
		String codeDocument="%";
		String requete="select f from TaFacture f " +
				"where f.taTiers.codeTiers like ? " +
				" and f.codeDocument like ?" +
				" and f.dateEchDocument between ? and ? " +
				//" and f.regleCompletDocument < f.netTtcCalc " +
				" order by f.taTiers.codeTiers,f.codeDocument";
		Query query = getEntityManager().createQuery(requete);
		if(tiers != null && !tiers.equals(""))codeTiers=tiers.toUpperCase();
		if(dateDeb==null)dateDeb=new Date(0);
		if(dateFin==null)dateFin=new Date("01/01/3000");
		if(document!=null && !document.equals(""))codeDocument=document.toUpperCase();

		query.setParameter(1, codeTiers);
		query.setParameter(2, codeDocument);
		query.setParameter(3, dateDeb, TemporalType.DATE);
		query.setParameter(4, dateFin, TemporalType.DATE);
		
		resultTemp=query.getResultList();;
		for (TaFacture taFacture : resultTemp) {
			taFacture.calculSommeAvoir();
			taFacture.calculSommeAcomptes();
			
			BigDecimal dif=taFacture.getNetTtcCalc().subtract(taFacture.calculRegleDocumentComplet().add(taFacture.getAcomptes()).add(taFacture.calculSommeAvoir()));
			if(dif.compareTo(limite)>0)
				result.add(taFacture);
		}		
		return result;
		
	}
	
	/**
	 * Recherche les factures non totalement réglées dont la date d'échéance rentre dans l'intervalle
	 * des 2 dates passées en paramètres
	 * @param dateDeb - date de début
	 * @param dateFin - date de fin
	 * @param tiers - tiers sur lequel porte la recherche, si null, alors on prend tous les tiers
	 * @return String[] - tableau contenant les IDs des factures entre ces 2 dates (null en cas d'erreur)
	 */
	public List<Object[]> rechercheDocumentNonTotalementRegleAEcheance2(Date dateDeb, Date dateFin,String tiers,String document) {
		List<Object[]> result = new LinkedList<Object[]>();
		List<Object[]> resultTemp = null;
		String codeTiers="%";
		String codeDocument="%";
		if(tiers != null && !tiers.equals(""))codeTiers=tiers.toUpperCase();
		if(dateDeb==null)dateDeb=new Date(0);
		if(dateFin==null)dateFin=new Date("01/01/3000");
		if(document!=null && !document.equals(""))codeDocument=document.toUpperCase();
		
//		String requete="EXEC DOCNONTOTALEMENTREGLEAECHEANCE('"+codeTiers+"','"+codeDocument+"',"+dateDeb+","+dateFin+") p " ;
		String requete="select * from DOCNONTOTALEMENTREGLEAECHEANCE(?,?,?,?) p " ;
//				"where a.taTiers.codeTiers like ? " +
//				" and a.codeDocument like ?" +
//				" and a.dateEchDocument between ? and ? " +
//				//" and a.regleCompletDocument < a.netTtcCalc " +
//				" order by f.taTiers.codeTiers,f.codeDocument";
		Query query = getEntityManager().createNativeQuery(requete);
		if(tiers != null && !tiers.equals(""))codeTiers=tiers.toUpperCase();
		if(dateDeb==null)dateDeb=new Date(0);
		if(dateFin==null)dateFin=new Date("01/01/3000");
		if(document!=null && !document.equals(""))codeDocument=document.toUpperCase();

		query.setParameter(1, codeTiers);
		query.setParameter(2, codeDocument);
		query.setParameter(3, dateDeb, TemporalType.DATE);
		query.setParameter(4, dateFin, TemporalType.DATE);
		
		resultTemp=query.getResultList();
		result=resultTemp;
//		for (TaFacture taFacture : resultTemp) {
//			if(taFacture.calculRegleDocumentComplet().add(taFacture.getAcomptes()).compareTo(taFacture.getNetTtcCalc())<0)
//				result.add(taFacture);
//		}		
		return result;
		
	}

	public List<TaFacture> rechercheDocumentRegle(Date dateDeb, Date dateFin,String tiers,String document) {
		List<TaFacture> result = new LinkedList<TaFacture>();
		List<TaFacture> resultTemp = null;
		String codeTiers="%";
		String codeDocument="%";
		String requete="select a from TaFacture a where a.taTiers.codeTiers like ? " +
				" and a.codeDocument like ?" +
				" and a.dateDocument between ? and ? " +
				//" and a.regleCompletDocument > 0 " +
				" order by a.codeDocument";
		Query query = getEntityManager().createQuery(requete);
		if(tiers != null && !tiers.equals(""))codeTiers=tiers.toUpperCase();
		if(dateDeb==null)dateDeb=new Date(0);
		if(dateFin==null)dateFin=new Date("01/01/3000");
		if(document!=null && !document.equals(""))codeDocument=document.toUpperCase();

		query.setParameter(1, codeTiers);
		query.setParameter(2, codeDocument);
		query.setParameter(3, dateDeb, TemporalType.DATE);
		query.setParameter(4, dateFin, TemporalType.DATE);
		
		resultTemp=query.getResultList();;
		for (TaFacture taFacture : resultTemp) {
			if(taFacture.calculRegleDocumentComplet().add(taFacture.getAcomptes()).add(taFacture.calculSommeAvoir()).compareTo(BigDecimal.valueOf(0))>0)
				result.add(taFacture);
		}		
		return result;
		
	}
	
	/**
	 * Recherche les factures non totalement réglées entre 2 dates (light version)
	 * Version allégée pour réduire le temps de calcul
	 * @param dateDeb - date de début
	 * @param dateFin - date de fin
	 * @return List<TaFacture> - tableau contenant les  factures entre ces 2 dates (null en cas d'erreur)
	 */
	public List<TaFacture> rechercheDocumentNonTotalementRegle(Date dateDeb, Date dateFin) {
		List<TaFacture> result = new LinkedList<TaFacture>();
		List<TaFacture> resultTemp = null;

		String requete="select a from TaFacture a" +
				" where a.dateDocument between ? and ? ";
		Query query = getEntityManager().createQuery(requete);
		
		query.setParameter(1, dateDeb, TemporalType.DATE);
		query.setParameter(2, dateFin, TemporalType.DATE);
		
		resultTemp=query.getResultList();;
		for (TaFacture taFacture : resultTemp) {
			if(taFacture.calculRegleDocumentComplet().compareTo(taFacture.getNetTtcCalc())<0)
				result.add(taFacture);
		}		
		return result;
		
	}
	
	/**
	 * Calcule le nombre de factures
	 * Version allégée pour réduire le temps de calcul
	 * @param dateDeb - date de début
	 * @param dateFin - date de fin
	 * @return List<TaFacture> - tableau contenant les  factures entre ces 2 dates (null en cas d'erreur)
	 */
	public List<TaFacture> toutesFacturesEntreDeuxDates(Date dateDeb, Date dateFin) {
		List<TaFacture> result = new LinkedList<TaFacture>();

		String requete="select a from TaFacture a" +
				" where a.dateDocument between ? and ? ";
		Query query = getEntityManager().createQuery(requete);
		
		query.setParameter(1, dateDeb, TemporalType.DATE);
		query.setParameter(2, dateFin, TemporalType.DATE);
		
		result=query.getResultList();;
	
		return result;
		
	}

	@Override
	public List<TaFacture> rechercheDocument(String codeDeb, String codeFin,
			String codeTiers) {
		return rechercheDocument(codeDeb, codeFin,codeTiers,null);
	}

	@Override
	public List<TaFacture> rechercheDocument(Date dateDeb, Date dateFin,
			String codeTiers) {
		return rechercheDocument(dateDeb,dateFin,codeTiers,null);
	}

	@Override
	public List<TaFacture> rechercheDocument(Date dateDeb, Date dateFin,
			String codeTiers, Boolean export) {
		List<TaFacture> result = null;
		Query query = null;
		if(export!=null && export)query = getEntityManager().createNamedQuery(TaFacture.QN.FIND_BY_DATE_EXPORT);
		else if(export!=null && export==false)query = getEntityManager().createNamedQuery(TaFacture.QN.FIND_BY_DATE_NON_EXPORT);
		else query = getEntityManager().createNamedQuery(TaFacture.QN.FIND_BY_TIERS_AND_DATE);
		if(codeTiers==null || codeTiers.equals(""))codeTiers="%";
		query.setParameter(1, codeTiers);
		query.setParameter(2, dateDeb, TemporalType.DATE);
		query.setParameter(3, dateFin, TemporalType.DATE);
		result = query.getResultList();
		return result;

	}

	@Override
	public List<TaFacture> rechercheDocument(String codeDeb, String codeFin,
			String codeTiers, Boolean export) {
		List<TaFacture> result = null;
		Query query = null;
		if(export!=null && export)query = getEntityManager().createNamedQuery(TaFacture.QN.FIND_BY_CODE_EXPORT);
		else if(export!=null && export==false)query = getEntityManager().createNamedQuery(TaFacture.QN.FIND_BY_CODE_NON_EXPORT);
		else query = getEntityManager().createNamedQuery(TaFacture.QN.FIND_BY_TIERS_AND_CODE);
		if(codeTiers==null || codeTiers.equals(""))codeTiers="%";
		query.setParameter(1, codeTiers);
		query.setParameter(2, codeDeb);
		query.setParameter(3, codeFin);
		result = query.getResultList();
		return result;
	}

	@Override
	public List<TaFacture> rechercheDocumentEtat(Date dateDeb, Date datefin, String codeEtat) {
		List<TaFacture> result = null;
		Query query = null;
//		Date date2 = LibDate.incrementDate(dateDeb, nbJours, 0, 0);
//		System.out.println("rechercheDocumentEtat => date2 : "+date2);
		query = getEntityManager().createNamedQuery(TaFacture.QN.FIND_BY_ETAT_DATE);
		query.setParameter(1, dateDeb);
		query.setParameter(2, datefin);
		query.setParameter(3, codeEtat);
		result = query.getResultList();
		return result;
	}

	@Override
	public List<TaFacture> rechercheDocumentEtat(Date dateDeb, Date datefin,
			String codeEtat, String codeTiers) {
		// TODO Auto-generated method stub
		return null;
	}

	
	public void removeTousLesAbonnements(TaFacture persistentInstance) throws Exception {
		try {
			if(Platform.getBundle(TaAbonnement.TYPE_DOC)!=null){
			TaAbonnementDAO daoAbonnement = new TaAbonnementDAO(getEntityManager());
			List<TaAbonnement> listeAbonnement=null;
			listeAbonnement=daoAbonnement.selectAbonnementFacture(persistentInstance);
			if(listeAbonnement!=null && listeAbonnement.size()>0){
				if(MessageDialog.openQuestion(PlatformUI.getWorkbench().getDisplay().getActiveShell(), "Facture liée à abonnement", 
						"Cette facture est liée à "+listeAbonnement.size()+" abonnement(s). Ils vont être supprimés. Voulez-vous continuer ?")){
					for (TaAbonnement taAbonnement : listeAbonnement) {
						daoAbonnement.remove(taAbonnement);
					}
				}else throw new Exception();
			}
			}
		} catch (RuntimeException re) {
			logger.error("removeTousLesAbonnements failed", re);
			throw re;
		}
	}
	public void removeTousLesSupportAbons(TaFacture persistentInstance) throws Exception {
		
		// ***TODO ISA***  a remettre après avoir trouver comment contourner le problème des liaison avec les points bonus pour autres utilisateur de BDG   ******
		
//		try {
		if(Platform.getBundle(TaSupportAbon.TYPE_DOC)!=null){
			TaSupportAbonDAO daoSupport = new TaSupportAbonDAO(getEntityManager());
			TaAbonnementDAO daoAbonnement = new TaAbonnementDAO(getEntityManager());
			List<TaSupportAbon> listeSupport=null;
			List<TaAbonnement> listeAbonnement=null;
			listeSupport=daoSupport.selectTaSupportAbonFacture(persistentInstance);
			
			if(listeSupport!=null && listeSupport.size()>0){
				if(MessageDialog.openQuestion(PlatformUI.getWorkbench().getDisplay().getActiveShell(), "Facture liée à support", 
						"Cette facture est liée à "+listeSupport.size()+" support d'abonnement(s). Ils vont être supprimés. Voulez-vous continuer ?")){
					for (TaSupportAbon taSupport : listeSupport) {
						//regarder si support lié à abonnement
						listeAbonnement=daoAbonnement.selectAbonnementSupport(taSupport);
						for (TaAbonnement taAbonnement : listeAbonnement) {
							daoAbonnement.remove(taAbonnement);
						}
						daoSupport.remove(taSupport);
					}
				}else throw new Exception();
			}
		}
//		} catch (RuntimeException re) {
//			logger.error("removeTousLesSupportAbons failed", re);
//			throw re;
//		}
	}

	@Override
	public List<Object[]> rechercheDocumentLight(String codeDoc, String codeTiers) {
			List<Object[]> result = null;
			Query query = getEntityManager().createNamedQuery(TaFacture.QN.FIND_BY_TIERS_AND_CODE_LIGHT);
			query.setParameter(1,"%");
			query.setParameter(2, codeDoc+"%");
			result = query.getResultList();
			return result;

		}
	

	
}
