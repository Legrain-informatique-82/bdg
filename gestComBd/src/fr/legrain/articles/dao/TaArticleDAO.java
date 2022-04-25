package fr.legrain.articles.dao;

// Generated Aug 11, 2008 4:05:28 PM by Hibernate Tools 3.2.1.GA

import java.sql.CallableStatement;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import fr.legrain.documents.dao.TaSumTaLAvisEcheance;
import fr.legrain.gestCom.Appli.AbstractApplicationDAO;
import fr.legrain.gestCom.Appli.Const;
import fr.legrain.gestCom.global.dao.TaConst;
import fr.legrain.gestCom.global.dao.TaConstDAO;
import fr.legrain.lib.data.ExceptLgr;
import fr.legrain.lib.data.LibChaine;
import fr.legrain.tiers.dao.TaTiers;

/**
 * Home object for domain model class TaArticle.
 * @see fr.legrain.articles.dao.TaArticle
 * @author Hibernate Tools
 */
public class TaArticleDAO extends AbstractApplicationDAO<TaArticle> {

	//private static final Log logger = LogFactory.getLog(TaArticleDAO.class);
	static Logger logger = Logger.getLogger(TaArticleDAO.class);

	private String defaultJPQLQuery = "select a from TaArticle a ";
	
	private String defaultLightJPQLQueryEcran = TaArticle.QN.FIND_BY_ECRAN_LIGHT;
	
	public TaArticleDAO(){
		this(null);
	}
	
	public TaArticleDAO(EntityManager em){
		if(em!=null) {
			setEm(em);
		}
		initChampId(TaArticle.class);
//		champIdTable=ctrlGeneraux.getID_TABLE(TaArticle.class.getSimpleName());
		setJPQLQuery(defaultJPQLQuery);
		setJPQLQueryInitial(defaultJPQLQuery);
		setListeChampMaj(Const.C_FICHIER_INI_CHAMPMAJ);
		initNomTable(new TaArticle());
	}
	

	protected void persist(TaArticle transientInstance) {
		logger.debug("persisting TaArticle instance");
		try {
			getEntityManager().persist(transientInstance);
			logger.debug("persist successful");
		} catch (RuntimeException re) {
			logger.error("persist failed", re);
			throw re;
		}
	}

	protected void remove(TaArticle persistentInstance) {
		logger.debug("removing TaArticle instance");
		try {
			getEntityManager().remove(persistentInstance);
			logger.debug("remove successful");
		} catch (RuntimeException re) {
			logger.error("remove failed", re);
			throw re;
		}
	}

	protected TaArticle merge(TaArticle detachedInstance) {
		logger.debug("merging TaArticle instance");
		try {
			TaArticle result = getEntityManager().merge(detachedInstance);
			logger.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			logger.error("merge failed", re);
			throw re;
		}
	}

	public TaArticle findById(int id) {
		logger.debug("getting TaArticle instance with id: " + id);
		try {
			TaArticle instance = getEntityManager().find(TaArticle.class, id);
			logger.debug("get successful");
			return instance;
		} catch (RuntimeException re) {
			logger.error("get failed", re);
			throw re;
		}
	}
	
//	public TaArticle findByIdEM(int id) {
//		logger.debug("getting TaArticle instance with id: " + id);
//		try {
//			EntityManager em = new JPABdLgr().getEntityManager();
//			TaArticle instance = em.find(TaArticle.class, id);
////			em.close();
//			instance=getEntityManager().merge(instance);
//			logger.debug("get successful");
//			return instance;
//		} catch (RuntimeException re) {
//			logger.error("get failed", re);
//			throw re;
//		}
//	}
	
	public TaArticle findByCode(String code) {
		logger.debug("getting TaArticle instance with code: " + code);
		try {
			if(!code.equals("")){
			Query query = getEntityManager().createQuery("select a from TaArticle a where a.codeArticle='"+code+"'");
			TaArticle instance = (TaArticle)query.getSingleResult();
			logger.debug("get successful");
			return instance;
			}
			return null;
		} catch (RuntimeException re) {
			logger.error("get failed", re);
			throw re;
		}
	}	
	
	public List<TaArticle>  findByCodeTva(String codeTva) {
		logger.debug("findByCodeTva: " + codeTva);
		try {
			if(!codeTva.equals("")){
			Query query = getEntityManager().createQuery("select a from TaArticle a join a.taTva t where t.codeTva='"+codeTva+"'");
			List<TaArticle> temp =query.getResultList();
			logger.debug("get successful");
			return temp;
			}
			return null;
		} catch (RuntimeException re) {
			logger.error("get failed", re);
			throw re;
		}
	}
	
	public List<TaArticle>  findByFamilleAndSuffixe(String codeFamille, String codeArticle, String suffixe) {
		logger.debug("findByFamilleAndSuffixe: " + codeFamille+'/'+suffixe);
		try {
			if(suffixe==null)suffixe="%";else suffixe="%"+suffixe;
//			if(codeArticle==null)codeArticle="%";
			if(!codeFamille.equals("")){
				Query query = null;
			if(codeArticle==null)
				query = getEntityManager().createQuery("select a from TaArticle a join a.taFamille f where f.codeFamille='"+codeFamille+"'" +
					" and a.codeArticle like ('"+suffixe.toUpperCase()+"') ");
			else				
				query = getEntityManager().createQuery("select a from TaArticle a join a.taFamille f where f.codeFamille='"+codeFamille+"'" +
						"  and a.codeArticle like ('"+codeArticle.toUpperCase()+"'))");			
			
			List<TaArticle> temp =query.getResultList();
			logger.debug("get successful");
			return temp;
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
			Query query = getEntityManager().createQuery("select count(f) from TaArticle f where f.codeArticle='"+code+"'");
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

	
	
	public List<TaArticle> findByActif(boolean actif) {
		logger.debug("getting TaArticle instance with actif: " + actif);
		try {
			int param = 0;
			if(actif){
				param = 1;
			}
			Query query = getEntityManager().createNamedQuery(TaArticle.QN.FIND_BY_ACTIF);
			query.setParameter(1, param);
			List<TaArticle> l = query.getResultList();;
			logger.debug("get successful");
			return l;

		} catch (RuntimeException re) {
			logger.error("get failed", re);
			throw re;
		}
	}
	
	
	public List<Object[]> findByActifLight(boolean actif) {
		logger.debug("getting TaArticle instance with actif: " + actif);
		try {
			int param = 0;
			if(actif){
				param = 1;
			}
			Query query = getEntityManager().createNamedQuery(TaArticle.QN.FIND_BY_ACTIF_LIGHT);
			query.setParameter(1, param);
			List<Object[]> l = query.getResultList();;
			logger.debug("get successful");
			return l;

		} catch (RuntimeException re) {
			logger.error("get failed", re);
			throw re;
		}
	}
	
	public List<Object[]> findByEcranLight() {
		logger.debug("getting TaArticle instance");
		try {

			Query query = getEntityManager().createNamedQuery(TaArticle.QN.FIND_BY_ECRAN_LIGHT);

			List<Object[]> l = query.getResultList();;
			logger.debug("get successful");
			return l;

		} catch (RuntimeException re) {
			logger.error("get failed", re);
			throw re;
		}
	}
	
	/**
	 * Liste des articles créé ou modifié par partir de la date passée en paramètre.
	 * @param d
	 * @return
	 */
	public List<TaArticle> findByNewOrUpdatedAfter(Date d) {
		logger.debug("getting TaArticle instance with quandCreeArticle or quandModifArticle >= " + d);
		try {
			List<TaArticle> l = null;
			if(d!=null) {
				Query query = getEntityManager().createNamedQuery(TaArticle.QN.FIND_BY_NEW_OR_UPDATED_AFTER);
				//query.setParameter(1, d);
				//query.setParameter(2, d);
				query.setParameter("dateLimite", d);
				l = query.getResultList();;
				logger.debug("get successful");
			}
			return l;

		} catch (RuntimeException re) {
			logger.error("get failed", re);
			throw re;
		}
	}
	
	/**
	 * Recherche les articles qui ont été importés d'un autre programme
	 * @param origineImport
	 * @param idImport - id externe (dans le programme d'origine)
	 * @return
	 */
	public List<TaArticle> rechercheParImport(String origineImport, String idImport) {
		Query query = getEntityManager().createNamedQuery(TaArticle.QN.FIND_BY_IMPORT);
		query.setParameter(1, origineImport);
		query.setParameter(2, idImport);
		List<TaArticle> l = query.getResultList();

		return l;
	}
	
	/* (non-Javadoc)
	 * @see fr.legrain.articles.dao.ILgrDAO#selectAll()
	 */
	public List<TaArticle> selectAll() {
		logger.debug("selectAll TaArticle");
		try {
			Query ejbQuery = getEntityManager().createQuery(JPQLQuery + "order by a.codeArticle");
			List<TaArticle> l = ejbQuery.getResultList();
			logger.debug("selectAll successful");
			return l;
		} catch (RuntimeException re) {
			logger.error("selectAll failed", re);
			throw re;
		}
	}
	
	public TaArticle refresh(TaArticle detachedInstance) {
		logger.debug("refresh instance");
		try {			
			org.hibernate.Session session = (org.hibernate.Session) getEntityManager().getDelegate();
		    session.evict(detachedInstance);
		    detachedInstance = getEntityManager().find(TaArticle.class, detachedInstance.getIdArticle());
		    return detachedInstance;		    
		} catch (RuntimeException re) {
			logger.error("refresh failed", re);
			throw re;
		}
	}
	
	/**
	 * Pour insertion batch
	 * http://docs.jboss.org/hibernate/core/3.3/reference/en/html/batch.html
	 * @param detachedInstance
	 */
	public void mergeFlush(TaArticle detachedInstance) {
		logger.debug("refresh instance");
		try {			
			merge(detachedInstance);
			getEntityManager().flush();
			getEntityManager().clear();
//			org.hibernate.Session session = (org.hibernate.Session) getEntityManager().getDelegate();
//		    session.flush();
//		    session.clear();		    
		} catch (RuntimeException re) {
			logger.error("refresh failed", re);
			throw re;
		}
	}
	

	public void initCodeTTva(TaArticle entity){
		if (entity.getTaTva()!=null && !LibChaine.empty(entity.getTaTva().getCodeTva())){
			if( entity.getTaTTva()==null)entity.setTaTTva(new TaTTva());  
				if(LibChaine.empty(entity.getTaTTva().getCodeTTva()))
					entity.getTaTTva().setCodeTTva("D");
		}else
			if(entity.getTaTva()==null || LibChaine.empty(entity.getTaTva().getCodeTva()))
				entity.setTaTTva(null);	
	}
	
	public void initPrixTTC(TaArticle entity,boolean changement){
//		boolean res = true;
//		CallableStatement cs;
//		try {
//			cs = cnx.prepareCall("{call " + "RECALCULPRIXTTC" +"(?,?)}");			
//			cs.setInt(1,entity.getIdArticle());
//			cs.setString(2,entity.getTaTva().getCodeTva());
//			cs.execute();
//			cnx.commit();
//		} catch (SQLException e) {
//			logger.error("", e);
//		}
		if(changement){
			
		}
	}
	
	public void ctrlSaisieSpecifique(TaArticle entity,String field) throws ExceptLgr {	
		try {
			if(field.equals(
					Const.C_CODE_UNITE)){	
			}
			if(field.equals(Const.C_CODE_TVA )||field.equals(Const.C_CODE_T_TVA )){
				initCodeTTva(entity);
				if(field.equals(Const.C_CODE_TVA ))
					initPrixTTC(entity,true);
			}
		} catch (Exception e) {
			logger.error("", e);
		}
	}

	public TaArticle remonteArticlePointBonus(){
		try {
			TaConstDAO daoConst = new TaConstDAO();
			TaConst ligneConst=daoConst.findByNomConst("Article_point_bonus");
			TaArticle ArticlePoint=findById(ligneConst.getvInteger());
			
			
			return ArticlePoint;
		} catch (Exception e) {
			return null;
		}
			
		
	}
//	public Date remonteDateFin(Integer idDocument){
//		try {
//			//Date dateFin=null;
//			String jpql = "select cast(min(lec.finAbonnement)as date) from  TaLEcheance lec "+ 
//					 " where exists (select tlf  from TaLAvisEcheance tlf join tlf.taLEcheance le join le.taAbonnement ta join ta.taTAbonnement  where le.idLEcheance=lec.idLEcheance"+ 
//					 " and tlf.taDocument.idDocument = "+idDocument+" )";
//			Query query = getEntityManager().createQuery(jpql);			
//			Date objet = (Date) query.getSingleResult();
//			
//			return objet;
//		} catch (Exception e) {
//			return null;
//		}
//			
//		
//	}

	public String getDefaultJPQLQuery() {
		return defaultJPQLQuery;
	}

	public void setDefaultJPQLQuery(String defaultJPQLQuery) {
		this.defaultJPQLQuery = defaultJPQLQuery;
	}

	public String getDefaultLightJPQLQueryEcran() {
		return defaultLightJPQLQueryEcran;
	}

	public void setDefaultLightJPQLQueryEcran(String defaultLightJPQLQueryEcran) {
		this.defaultLightJPQLQueryEcran = defaultLightJPQLQueryEcran;
	}
}
