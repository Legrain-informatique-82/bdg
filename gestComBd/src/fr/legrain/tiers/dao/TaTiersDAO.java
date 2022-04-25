package fr.legrain.tiers.dao;

// Generated Mar 25, 2009 10:06:50 AM by Hibernate Tools 3.2.0.CR1

import java.math.BigDecimal;
import java.sql.Time;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TemporalType;

import org.apache.log4j.Logger;

import com.sun.jmx.snmp.Timestamp;

import fr.legrain.articles.dao.TaArticle;
import fr.legrain.documents.dao.TaAcompte;
import fr.legrain.documents.dao.TaAvoir;
import fr.legrain.documents.dao.TaBonliv;
import fr.legrain.documents.dao.TaFacture;
import fr.legrain.gestCom.Appli.AbstractApplicationDAO;
import fr.legrain.gestCom.Appli.Const;
import fr.legrain.gestCom.Module_Document.IDocumentTiers;
import fr.legrain.lib.data.ExceptLgr;
import fr.legrain.lib.data.LibConversion;
import fr.legrain.lib.data.LibDate;

/**
 * Home object for domain model class TaTiers.
 * @see fr.legrain.tiers.dao.test.TaTiers
 * @author Hibernate Tools
 */
public class TaTiersDAO extends AbstractApplicationDAO<TaTiers>{

	//private static final Log logger = LogFactory.getLog(TaTiersDAO.class);
	static Logger logger = Logger.getLogger(TaTiersDAO.class);

	private String defaultJPQLQuery = "select a from TaTiers a where idTiers > 0";
	private String defaultJPQLQueryIdentiteEntrepise = "select a from TaTiers a where idTiers = "+C_ID_IDENTITE_ENTREPRISE_STR;
	private String tiersActif = "select a from TaTiers a where actifTiers =1 and idTiers > 0";
	private String tiersActiflight = "select a.codeTiers,a.nomTiers,a.codeCompta,a.compte,ttiers.libelleTTiers from TaTiers a join a.taTTiers ttiers where a.actifTiers =1 and a.idTiers > 0";
	private String orderByCodeTiers = " order by a.codeTiers";
	private String preferences = null;
	
	private String defaultLightJPQLQuery = "select a.idTiers, a.codeTiers, a.nomTiers, a.prenomTiers, a.dateAnniv,  "
			+ "adresse.villeAdresse, adresse.codepostalAdresse, "
			+ "email.adresseEmail, tel.numeroTelephone, entreprise.nomEntreprise, "
			+ "a.actifTiers "
			+ "from TaTiers a left outer join a.taAdresse adresse "
			+ "left outer join a.taEmail email "
			+ "left outer join a.taTelephone tel "
			+ "left outer join a.taEntreprise entreprise "
			+ "where a.idTiers > 0";
	
	static public final int C_ID_IDENTITE_ENTREPRISE_INT = -1;
	static public final String C_ID_IDENTITE_ENTREPRISE_STR = "-1";
	
	public TaTiersDAO() {
		this(null);
	}
	
	public TaTiersDAO(EntityManager em){
		if(em!=null) {
			setEm(em);
		}
//		champIdTable=ctrlGeneraux.getID_TABLE(TaTiers.class.getSimpleName());
		initChampId(TaTiers.class);
		setJPQLQuery(defaultJPQLQuery);
		setListeChampMaj(Const.C_FICHIER_INI_CHAMPMAJ);
		initNomTable(new TaTiers());
	}
	
	public String getDefaultJPQLQueryOrderByCodeTiers() {
		return this.defaultJPQLQuery+" "+orderByCodeTiers;
	}
	
	public String getDefaultJPQLQuery() {
		return this.defaultJPQLQuery;
	}
	public String getDefaultJPQLQueryIdentiteEntrepise() {
		return this.defaultJPQLQueryIdentiteEntrepise;
	}


	public void persist(TaTiers transientInstance) {
		logger.debug("persisting TaTiers instance");
		try {
			getEntityManager().persist(transientInstance);
			logger.debug("persist successful");
		} catch (RuntimeException re) {
			logger.error("persist failed", re);
			throw re;
		}
	}

	public void remove(TaTiers persistentInstance) {
		logger.debug("removing TaTiers instance");
		try {
			getEntityManager().remove(persistentInstance);
			logger.debug("remove successful");
		} catch (RuntimeException re) {
			logger.error("remove failed", re);
			throw re;
		}
	}

	public TaTiers merge(TaTiers detachedInstance) {
		logger.debug("merging TaTiers instance");
		try {
			TaTiers result = getEntityManager().merge(detachedInstance);
			logger.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			logger.error("merge failed", re);
			throw re;
		}
	}
	
	/**
	 * Pour insertion batch
	 * http://docs.jboss.org/hibernate/core/3.3/reference/en/html/batch.html
	 * @param detachedInstance
	 */
	public void mergeFlush(TaTiers detachedInstance) {
		logger.debug("refresh instance");
		try {			
			merge(detachedInstance);
			org.hibernate.Session session = (org.hibernate.Session) getEntityManager().getDelegate();
		    session.flush();
		    session.clear();		    
		} catch (RuntimeException re) {
			logger.error("refresh failed", re);
			throw re;
		}
	}

	public TaTiers refresh(TaTiers detachedInstance) {
		logger.debug("refresh TaTiers instance");
		try {
			org.hibernate.Session session = (org.hibernate.Session) getEntityManager().getDelegate();
		    session.evict(detachedInstance);
		    detachedInstance = getEntityManager().find(TaTiers.class, detachedInstance.getIdTiers());
		    return detachedInstance;
		} catch (RuntimeException re) {
			logger.error("refresh failed", re);
			throw re;
		}
	}
	public TaTiers findById(int id) {
		logger.debug("getting TaTiers instance with id: " + id);
		try {
			TaTiers instance = getEntityManager().find(TaTiers.class, id);
			logger.debug("get successful");
			return instance;
		} catch (RuntimeException re) {
			logger.error("get failed", re);
			throw re;
		}
	}
	
	public TaTiers findByCode(String code) {
		logger.debug("getting TaTiers instance with code: " + code);
		try {
			if(!code.equals("")){
			Query query = getEntityManager().createQuery("select a from TaTiers a where a.codeTiers='"+code+"'");
			TaTiers instance = (TaTiers)query.getSingleResult();
			logger.debug("get successful");
			return instance;
			}
			return null;
		} catch (RuntimeException re) {
			logger.error("get failed", re);
			throw re;
		}
	}	
	


	public Boolean exist(String code) {
		logger.debug("exist with code: " + code);
		try {
			if(code!=null && !code.equals("")){
			Query query = getEntityManager().createQuery("select count(f) from TaTiers f where f.codeTiers='"+code+"'");
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

	
	
	/**
	 * Recherche les tiers qui ont été importés d'un autre programme
	 * @param origineImport
	 * @param idImport - id externe (dans le programme d'origine)
	 * @return
	 */
	public List<TaTiers> rechercheParImport(String origineImport, String idImport) {
		Query query = getEntityManager().createNamedQuery(TaTiers.QN.FIND_BY_IMPORT);
		query.setParameter(1, origineImport);
		query.setParameter(2, idImport);
		List<TaTiers> l = query.getResultList();

		return l;
	}
	
	/**
	 * Recherche les tiers d'un certain type
	 * @param codeType - le code du type
	 * @return String[] - tableau contenant les IDs des factures entre ces 2 codes (null en cas d'erreur)
	 */
	public List<TaTiers> rechercheParType(String codeType) {
		Query query = getEntityManager().createNamedQuery(TaTiers.QN.FIND_BY_TYPE);
		query.setParameter(1, codeType);
		List<TaTiers> l = query.getResultList();

		return l;
	}

	@Override
	public List<TaTiers> selectAll() {
		// TODO Auto-generated method stub
		logger.debug("selectAll TaTiers");
		try {
			logger.debug(new Timestamp(0));;
			Query ejbQuery = getEntityManager().createQuery(JPQLQuery + orderByCodeTiers);
			List<TaTiers> l = ejbQuery.getResultList();
			logger.debug("selectAll successful");
			logger.debug(new Timestamp(0));
			return l;
		} catch (RuntimeException re) {
			logger.error("selectAll failed", re);
			throw re;
		}
	}
	
	public List<TaTiers> selectTiersSurTypeTiers() {
		// TODO Auto-generated method stub
		logger.debug("selectTiersSurTypeTiers ");
		try {
			String requete = tiersActif + clauseWhereTypeTiers("");
			requete+= orderByCodeTiers;
			Query ejbQuery = getEntityManager().createQuery(requete);
			List<TaTiers> l = ejbQuery.getResultList();
			logger.debug("selectTiersSurTypeTiers successful");
			return l;
		} catch (RuntimeException re) {
			throw re;
		}
	}
	
	public List<Object[]> selectTiersSurTypeTiersLight() {
		// TODO Auto-generated method stub
		logger.debug("selectTiersSurTypeTiersLight ");
		try {
			String requete = tiersActiflight + clauseWhereTypeTiers("");
			requete+= orderByCodeTiers;
			Query ejbQuery = getEntityManager().createQuery(requete);
			List<Object[]> l = ejbQuery.getResultList();
			logger.debug("selectTiersSurTypeTiersLight successful");
			return l;
		} catch (RuntimeException re) {
			throw re;
		}
	}
	
	public List<TaTiers> selectTiersDocNonTransforme(String typeOrigine,String typeDest,Date debut,Date fin) {
		// TODO Auto-generated method stub
		logger.debug("selectTiersBonLivNonTransforme ");
		try {
			
			String requete = selectTiersDocNonTransformeRequete(typeOrigine,typeDest,debut,fin);
			//requete+= orderByCodeTiers;
			Query ejbQuery = getEntityManager().createQuery(requete);
//			ejbQuery.setParameter(1, debut, TemporalType.DATE);
//			ejbQuery.setParameter(2, fin, TemporalType.DATE);
			List<TaTiers> l = ejbQuery.getResultList();
			logger.debug("selectTiersSurTypeTiers successful");
			return l;
		} catch (RuntimeException re) {
			throw re;
		}
	}
	
	public List<TaTiers> selectTiersTypeDoc(String partieRequeteTiers,IDocumentTiers doc,String typeOrigine,String typeDest,Date debut,Date fin) {
		// TODO Auto-generated method stub
		logger.debug("selectTiersTypeDoc ");
		try {
			
			String requete = selectTiersTypeDocRequete( partieRequeteTiers,doc,typeOrigine,typeDest,debut,fin);
			//requete+= orderByCodeTiers;
			Query ejbQuery = getEntityManager().createQuery(requete);
//			ejbQuery.setParameter(1, debut, TemporalType.DATE);
//			ejbQuery.setParameter(2, fin, TemporalType.DATE);
			List<TaTiers> l = ejbQuery.getResultList();
			logger.debug("selectTiersTypeDoc successful");
			return l;
		} catch (RuntimeException re) {
			throw re;
		}
	}
	
	public String selectTiersDocNonTransformeRequete(String typeOrigine,String typeDest,Date debut,Date fin) {
		// TODO Auto-generated method stub
		logger.debug("selectTiersBonLivNonTransforme ");
		try {
			
			String requete = "select t from TaTiers t where exists (" ;
			requete=requete+" select doc from Ta"+typeOrigine+" doc join doc.taTiers t2 where t2.idTiers = t.idTiers" +
					" and doc.dateDocument between cast('"+LibDate.dateToStringSql(debut)+"'as date) and cast('"+LibDate.dateToStringSql(fin)+"'as date)" ;
			requete=requete+" and not exists(select r from TaRDocument r join r.ta"+typeOrigine+" org " +
					" join r.ta"+typeDest+" dest where org.idDocument" +
					" = doc.idDocument and (dest.idDocument is not null or dest.idDocument!=0))";
			requete=requete+" ) ";

			return requete;
		} catch (RuntimeException re) {
			throw re;
		}
	}

	public String selectTiersTypeDocRequete(String partieRequeteTiers,IDocumentTiers doc,String typeOrigine,String typeDest,Date debut,Date fin) {
		// TODO Auto-generated method stub
		logger.debug("selectTiersTypeDocRequete ");
		try {
			
			String requete = "select t from TaTiers t where " +
					"t in("+partieRequeteTiers+")"+
					//"t." +nomCritere+" "+comparateur+" '"+critere+"'"+
					" and exists (" ;
			requete=requete+" select doc from Ta"+typeOrigine+" doc join doc.taTiers t2 where " ;
			if(doc!=null)requete=requete+" doc.idDocument = "+doc.getIdDocument()+" and " ;
			requete=requete+" t2.idTiers = t.idTiers and " ;
			requete=requete+"  doc.dateDocument between cast('"+LibDate.dateToStringSql(debut)+"'as date) and cast('"+LibDate.dateToStringSql(fin)+"'as date)" ;
			requete=requete+" and not exists(select r from TaRDocument r join r.ta"+typeOrigine+" org join r.ta"+typeDest+" dest " +
					" where org.idDocument" +
					" = doc.idDocument and (dest.idDocument is not null or dest.idDocument!=0))";
			requete=requete+"  ) order by t.codeTiers   ";

			return requete;
		} catch (RuntimeException re) {
			throw re;
		}
	}
	
	public void ctrlSaisieSpecifique(TaTiers entity,String field) throws ExceptLgr {	
		
	}
	
	public String clauseWhereTypeTiers(String where){
		//String preferences =BonLivraisonPlugin.getDefault().getPreferenceStore().getString(PreferenceConstants.TYPE_TIERS_DOCUMENT);
		if (preferences!=null && !preferences.equals("")){
		String debut ="";
		String fin ="";
		String suite ="";
		if(where.equals("")) where=" where (";
		else debut=" and (";
			String[]typeTiers = preferences.split(";"); 
			for (int i = 0; i < typeTiers.length; i++) {
				if(suite.equals(""))
					suite+=" a.taTTiers.codeTTiers = '"+typeTiers[i].split(",")[0]+"'";
				else
					suite+=" or a.taTTiers.codeTTiers = '"+typeTiers[i].split(",")[0]+"'";
			}
			fin+=")";
			where+=debut+suite+fin;
		}
		return where;
	}


	public String getPreferences() {
		return preferences;
	}


	public void setPreferences(String preferences) {
		this.preferences = preferences;
	}

	public String getTiersActif() {
		return tiersActif;
	}

	public void setTiersActif(String tiersActif) {
		this.tiersActif = tiersActif;
	}
	
	public String getDefaultLightJPQLQueryOrderByCodeTiers() {
		return this.defaultLightJPQLQuery + orderByCodeTiers;
	}
	
}
