package fr.legrain.documents.dao;

// Generated Apr 7, 2009 3:27:24 PM by Hibernate Tools 3.2.0.CR1

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TemporalType;

import org.apache.log4j.Logger;

import fr.legrain.articles.dao.TaPrix;
import fr.legrain.articles.dao.TaUnite;
import fr.legrain.gestCom.Appli.AbstractApplicationDAO;
import fr.legrain.gestCom.Appli.Const;
import fr.legrain.gestCom.Module_Document.IDocumentTiers;
import fr.legrain.lib.data.LibConversion;
import fr.legrain.tiers.dao.TaTiers;

/**
 * Home object for domain model class TaRAcompte.
 * @see fr.legrain.documents.dao.TaRAcompte
 * @author Hibernate Tools
 */
public class TaReglementDAO extends AbstractApplicationDAO<TaReglement> implements IDocumentTiersDAO<TaReglement> {

//	private static final Log log = LogFactory.getLog(TaRAcompteDAO.class);
	static Logger logger = Logger.getLogger(TaReglementDAO.class);
	
	private String defaultJPQLQuery = "select a from TaReglement a";
	
	public TaReglementDAO(){
		this(null);
	}
	
	public TaReglementDAO(EntityManager em){
		if(em!=null) {
			setEm(em);
		}
//		champIdTable=ctrlGeneraux.getID_TABLE(TaReglement.class.getSimpleName());
		initChampId(TaReglement.class);
		setJPQLQuery(defaultJPQLQuery);
		setListeChampMaj(Const.C_FICHIER_INI_CHAMPMAJ);
		initNomTable(new TaReglement());
	}
	public TaReglement refresh(TaReglement detachedInstance) {
		logger.debug("refresh instance");
		try {
			getEntityManager().refresh(detachedInstance);
//			org.hibernate.Session session = (org.hibernate.Session) getEntityManager().getDelegate();
//		    session.evict(detachedInstance);
//		    detachedInstance = getEntityManager().find(TaReglement.class, detachedInstance.getIdDocument());
		    return detachedInstance;

		} catch (RuntimeException re) {
			logger.error("refresh failed", re);
			throw re;
		}
	}
	public void persist(TaReglement transientInstance) {
		logger.debug("persisting TaReglement instance");
		try {
			getEntityManager().persist(transientInstance);
			logger.debug("persist successful");
		} catch (RuntimeException re) {
			logger.error("persist failed", re);
			throw re;
		}
	}

	public void remove(TaReglement persistentInstance) {
		logger.debug("removing TaReglement instance");
		try {
			getEntityManager().remove(persistentInstance);
			logger.debug("remove successful");
		} catch (RuntimeException re) {
			logger.error("remove failed", re);
			throw re;
		}
	}

	public TaReglement merge(TaReglement detachedInstance) {
		logger.debug("merging TaReglement instance");
		try {
			TaReglement result = getEntityManager().merge(detachedInstance);
			logger.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			logger.error("merge failed", re);
			throw re;
		}
	}

	public TaReglement findById(int id) {
		logger.debug("getting TaReglement instance with id: " + id);
		try {
			TaReglement instance = getEntityManager().find(TaReglement.class, id);
			logger.debug("get successful");
			return instance;
		} catch (RuntimeException re) {
			logger.error("get failed", re);
			throw re;
		}
	}
//	
//	/* (non-Javadoc)
//	 * @see fr.legrain.articles.dao.ILgrDAO#selectAll()
//	 */
//	public List<TaReglement> selectAll(TaFacture taFacture) {
//		logger.debug("selectAll TaReglement");
//		try {
//			if(taFacture!=null){
//			Query ejbQuery = getEntityManager().createQuery("select a from TaReglement a where a.taFacture=?");
//			ejbQuery.setParameter(1, taFacture);
//			List<TaReglement> l = ejbQuery.getResultList();
//			logger.debug("selectAll successful");
//			return l;
//			}
//			return null;
//		} catch (RuntimeException re) {
//			logger.error("selectAll failed", re);
//			throw re;
//		}
//	}

	/* (non-Javadoc)
	 * @see fr.legrain.articles.dao.ILgrDAO#selectAll()
	 */
	public List<TaReglement> selectAll(IDocumentTiers taDocument,Date dateDeb,Date dateFin) {
		TaTiers taTiersLoc=null;
		if(taDocument!=null)taTiersLoc=taDocument.getTaTiers();
		logger.debug("selectAll TaReglement");
		try {
			if(taTiersLoc!=null){				
				if(dateDeb==null)dateDeb=new Date(0);
				if(dateFin==null)dateFin=new Date("01/01/3000");
//				String requete="select a from TaReglement a where " +
//						" not exists(select r from TaRReglement r where r.taReglement.idDocument=a.id " +
////						" and (r.taFacture.codeDocument=?))" +
//						" and (r.taFacture=?))" +
//						" and exists(select r from TaRReglement r where r.taReglement.idDocument=a.id " +
//						" and r.taFacture.taTiers=?) " +
////						" or " +
////						" exists(select r from TaRReglement r where r.taReglement.idDocument=a.id " +
////						" and r.taAvoir.taTiers=?) " +
//						" and a.dateDocument between ? and ?";
				
				String requete="select a from TaReglement a where (" +
				" not exists(select r from TaRReglement r where r.taReglement.idDocument=a.id " +
				" and (r.taFacture=?))" +
//				" and exists(select r from TaRReglement r where r.taReglement.idDocument<>a.id " +
//				" and r.taFacture.taTiers=?) " +
				" ) and a.taTiers =?"+
				" and a.dateDocument between ? and ?";
				
				Query ejbQuery = getEntityManager().createQuery(requete);
				ejbQuery.setParameter(1, taDocument);
				ejbQuery.setParameter(2, taTiersLoc);
//				ejbQuery.setParameter(3, taTiersLoc);
				ejbQuery.setParameter(3, dateDeb,TemporalType.DATE);
				ejbQuery.setParameter(4, dateFin,TemporalType.DATE);
			
			List<TaReglement> l = ejbQuery.getResultList();
			logger.debug("selectAll successful");
			return l;
			}
			return null;
		} catch (RuntimeException re) {
			logger.error("selectAll failed", re);
			throw re;
		}
	}
	

	
//	public List<TaReglement> selectSumReglementDocument(TaFacture taFacture) {
//		logger.debug("selectAll TaRAcompte");
//		try {
//			if(taFacture!=null){
//			Query ejbQuery = getEntityManager().createQuery("select a from TaReglement a where a.taFacture=?");
//			ejbQuery.setParameter(1, taFacture);
//			List<TaReglement> l = ejbQuery.getResultList();
//			logger.debug("selectAll successful");
//			return l;
//			}
//			return null;
//		} catch (RuntimeException re) {
//			logger.error("selectAll failed", re);
//			throw re;
//		}
//	}

	public TaReglement findByCode(String code) {
		logger.debug("getting TaReglement instance with code: " + code);
		try {
			Query query = getEntityManager().createQuery("select a from TaReglement a where codeDocument like ?");
			query.setParameter(1, code);
			TaReglement instance = (TaReglement)query.getSingleResult();
			return instance;
		} catch (RuntimeException re) {
			throw re;
		} catch (Exception e) {
			return null;
		}
	}
	

	
//	select r.codeDocument,t.codeTiers,r.dateDocument,r.libelleDocument,r.netTtcCalc,rr.affectation,
//	tp.codeTPaiement,f.codeDocument
//	from taReglement r
//	join r.taRReglement rr
//	join rr.taFacture f 
//	join r.taTiers t 
//	left join r.taTPaiement tp 
	
	
	/**
	 * Recherche les avoirs entre 2 dates
	 * @param dateDeb - date de début
	 * @param dateFin - date de fin
	 * @return String[] - tableau contenant les IDs des avoirs entre ces 2 dates (null en cas d'erreur)
	 */
	public List<TaReglement> rechercheDocument(Date dateDeb, Date dateFin) {
		Query query = getEntityManager().createNamedQuery(TaReglement.QN.FIND_BY_DATE);
		query.setParameter(1,"%");
		query.setParameter(2, dateDeb, TemporalType.DATE);
		query.setParameter(3, dateFin, TemporalType.DATE);
		List<TaReglement> l = query.getResultList();		
		return l;
	}
	
	/**
	 * Recherche les factures entre 2 dates ordonnées par date
	 * @param dateDeb - date de début
	 * @param dateFin - date de fin
	 * @return String[] - tableau contenant les IDs des avoirs entre ces 2 dates (null en cas d'erreur)
	 */
	public List<TaReglement> rechercheDocumentOrderByDate(Date dateDeb, Date dateFin) {
		Query query = getEntityManager().createNamedQuery(TaReglement.QN.FIND_BY_DATE_PARDATE);
		query.setParameter(1,"%");
		query.setParameter(2, dateDeb, TemporalType.DATE);
		query.setParameter(3, dateFin, TemporalType.DATE);
		List<TaReglement> l = query.getResultList();		
		return l;
	}
	/**
	 * Recherche les avoirs entre 2 codes avoirs
	 * @param codeDeb - code de début
	 * @param codeFin - code de fin
	 * @return String[] - tableau contenant les IDs des avoirs entre ces 2 codes (null en cas d'erreur)
	 */
	//public String[] rechercheAvoir(String codeDeb, String codeFin) {
	public List<TaReglement> rechercheDocument(String codeDeb, String codeFin) {
		//String[] result = null;
		List<TaReglement> result = null;
		Query query = getEntityManager().createNamedQuery(TaReglement.QN.FIND_BY_CODE);
		query.setParameter(1,"%");
		query.setParameter(2, codeDeb);
		query.setParameter(3, codeFin);
		List<TaReglement> l = query.getResultList();
		return l;

	}
	
	@Override
	public List<TaReglement> selectAll() {
		logger.debug("selectAll TaReglement");
		try {
			Query ejbQuery = getEntityManager().createQuery(defaultJPQLQuery);
			List<TaReglement> l = ejbQuery.getResultList();
			logger.debug("selectAll successful");
			return l;
		} catch (RuntimeException re) {
			logger.error("selectAll failed", re);
			throw re;
		}
	}
	
	/**
	 * Recherche les reglements non inclus dans des remises entre 2 dates
	 * @param codeTiers
	 * @param dateDeb
	 * @param DateFin
	 * @param export
	 * @param codeTPaiement
	 * @param compteBancaire
	 * @return String[] - tableau contenant les IDs des reglements entre ces 2 dates (null en cas d'erreur)
	 *  */
	public List<TaReglement> rechercheReglementNonRemises(String codeTiers,Date dateDeb,Date DateFin,Boolean export,String codeTPaiement,String compteBancaire,boolean byDate) {
		String requete="select r from TaReglement r where not exists" +
				"(select a from TaLRemise a where a.taReglement.codeDocument=r.codeDocument)" +
				" and r.taTiers.codeTiers like ? " +
				" and r.dateDocument between ? and ?" +
				" and r.taTPaiement.codeTPaiement like ?"+
				" and r.taCompteBanque.codeBanque||r.taCompteBanque.codeGuichet||r.taCompteBanque.compte like ?" ;
				
				
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
			
			
			List<TaReglement> l = ejbQuery.getResultList();
			logger.debug("rechercheReglementNonRemises successful");
			return l;
		} catch (RuntimeException re) {
			logger.error("rechercheReglementNonRemises failed", re);
			throw re;
		}
	}
	
	/**
	 * Recherche les reglements entre 2 dates et non exportees
	 * @param dateDeb - date de début
	 * @param dateFin - date de fin
	 * @return String[] - tableau contenant les IDs des reglements entre ces 2 dates (null en cas d'erreur)
	 */
	public List<TaReglement> rechercheDocumentNonExporte(Date dateDeb, Date dateFin) {
		List<TaReglement> result = null;
		Query query = getEntityManager().createNamedQuery(TaReglement.QN.FIND_BY_DATE_NON_EXPORT);
		query.setParameter(1,"%");
		query.setParameter(2, dateDeb, TemporalType.DATE);
		query.setParameter(3, dateFin, TemporalType.DATE);
		result = query.getResultList();
		
		
		return result;
		
	}
	
	
	@Override
	public List<TaReglement> rechercheDocument(String codeDeb, String codeFin,
			String codeTiers) {
		return rechercheDocument(codeDeb, codeFin, codeTiers, null);
	}

	@Override
	public List<TaReglement> rechercheDocument(Date dateDeb, Date dateFin,
			String codeTiers) {
		return rechercheDocument(dateDeb, dateFin, codeTiers, null);
	}

	@Override
	public List<TaReglement> rechercheDocument(Date dateDeb, Date dateFin,
			String codeTiers, Boolean export) {
		List<TaReglement> result = null;
		Query query = null;
		if(export!=null && export)query = getEntityManager().createNamedQuery(TaReglement.QN.FIND_BY_DATE_EXPORT);
		else if(export!=null && export==false)query = getEntityManager().createNamedQuery(TaReglement.QN.FIND_BY_DATE_NON_EXPORT);
		else query = getEntityManager().createNamedQuery(TaReglement.QN.FIND_BY_TIERS_AND_DATE);
		if(codeTiers==null || codeTiers.equals(""))codeTiers="%";
		query.setParameter(1, codeTiers);
		query.setParameter(2, dateDeb, TemporalType.DATE);
		query.setParameter(3, dateFin, TemporalType.DATE);
		result = query.getResultList();
		return result;
	}

	@Override
	public List<TaReglement> rechercheDocument(String codeDeb, String codeFin,
			String codeTiers, Boolean export) {
		List<TaReglement> result = null;
		Query query = null;
		if(export!=null && export)query = getEntityManager().createNamedQuery(TaReglement.QN.FIND_BY_CODE_EXPORT);
		else if(export!=null && export==false)query = getEntityManager().createNamedQuery(TaReglement.QN.FIND_BY_CODE_NON_EXPORT);
		else query = getEntityManager().createNamedQuery(TaReglement.QN.FIND_BY_TIERS_AND_CODE);
		if(codeTiers==null || codeTiers.equals(""))codeTiers="%";
		query.setParameter(1, codeTiers);
		query.setParameter(2, codeDeb);
		query.setParameter(3, codeFin);
		result = query.getResultList();
		return result;
	}

	@Override
	public List<Object[]> rechercheDocument(Date dateDeb, Date dateFin,
			Boolean light) {
		// TODO Stub de la méthode généré automatiquement
		return null;
	}

	@Override
	public List<Object[]> rechercheDocumentLight(Date dateDeb, Date dateFin,
			String codeTiers) {
		// TODO Stub de la méthode généré automatiquement
		return null;
	}

	@Override
	public List<Object[]> rechercheDocumentLight(String codeDoc, String codeTiers) {
		// TODO Stub de la méthode généré automatiquement
		return null;
	}
	
	
}
