package fr.legrain.generationDocumentLGR.dao;

// Generated 11 août 2009 15:52:23 by Hibernate Tools 3.2.4.GA

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import fr.legrain.articles.dao.TaPrix;
import fr.legrain.articles.dao.TaUnite;
import fr.legrain.documents.dao.TaInfosApporteur;
import fr.legrain.gestCom.Appli.AbstractApplicationDAO;
import fr.legrain.gestCom.Appli.Const;

/**
 * Home object for domain model class TaWlgr.
 * @see fr.legrain.generationDocumentLGR.dao.TaWlgr
 * @author Hibernate Tools
 */
public class TaWlgrNMoins1DAO extends AbstractApplicationDAO<TaWlgrNMoins1> {


	private String defaultJPQLQuery = "select u from TaWlgrNMoins1 u";
	private static final Log logger = LogFactory.getLog(TaWlgrNMoins1DAO.class);

	public TaWlgrNMoins1DAO(){
		this(null);
	}
	
	public TaWlgrNMoins1DAO(EntityManager em){
		if(em!=null) {
			setEm(em);
		}
//		champIdTable=ctrlGeneraux.getID_TABLE(TaWlgr.class.getSimpleName());
		initChampId(TaWlgrNMoins1.class);
		setJPQLQuery(defaultJPQLQuery);
		setListeChampMaj(Const.C_FICHIER_INI_CHAMPMAJ);
		initNomTable(new TaWlgrNMoins1());
	}
	public TaWlgrNMoins1 refresh(TaWlgrNMoins1 detachedInstance) {
		logger.debug("refresh instance");
		try {
////			getEntityManager().refresh(detachedInstance);
////			logger.debug("refresh successful");
//			org.hibernate.Session session = (org.hibernate.Session) getEntityManager().getDelegate();
//		    session.evict(detachedInstance);
//		    detachedInstance = getEntityManager().find(TaWlgr.class, detachedInstance.getId());
		    return detachedInstance;
		} catch (RuntimeException re) {
			logger.error("refresh failed", re);
			throw re;
		}
	}
	public void persist(TaWlgrNMoins1 transientInstance) {
		logger.debug("persisting TaWlgrNMoins1 instance");
		try {
			getEntityManager().persist(transientInstance);
			logger.debug("persist successful");
		} catch (RuntimeException re) {
			logger.error("persist failed", re);
			throw re;
		}
	}

	public void remove(TaWlgrNMoins1 persistentInstance) {
		logger.debug("removing TaWlgrNMoins1 instance");
		try {
			getEntityManager().remove(persistentInstance);
			logger.debug("remove successful");
		} catch (RuntimeException re) {
			logger.error("remove failed", re);
			throw re;
		}
	}

	public TaWlgrNMoins1 merge(TaWlgrNMoins1 detachedInstance) {
		logger.debug("merging TaWlgrNMoins1 instance");
		try {
			TaWlgrNMoins1 result = getEntityManager().merge(detachedInstance);
			logger.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			logger.error("merge failed", re);
			throw re;
		}
	}

	public TaWlgrNMoins1 findById(int id) {
		logger.debug("getting TaWlgrNMoins1 instance with id: " + id);
		try {
			TaWlgrNMoins1 instance = getEntityManager().find(TaWlgrNMoins1.class, id);
			logger.debug("get successful");
			return instance;
		} catch (RuntimeException re) {
			logger.error("get failed", re);
			throw re;
		}
	}
	
	public TaWlgrNMoins1 findByCode(String codeClient) {
		logger.debug("getting TaWlgrNMoins1 instance with codeClient: " + codeClient);
		try {
			Query query = getEntityManager().createQuery("select a from TaWlgrNMoins1 a where " +
					"a.codeClient='"+codeClient+"'");
			TaWlgrNMoins1 instance = (TaWlgrNMoins1)query.getSingleResult();
			logger.debug("get successful");
			return instance;
		} catch (RuntimeException re) {
			logger.error("get failed", re);
			throw re;
		}
	}
	
	public boolean clientExiste(String codeClient) {
		logger.debug("clientExiste TaWlgrNMoins1");
		try {
			String requete = "select v.codeClient from TaWlgrNMoins1 v where " +
				"codeClient like('"+codeClient+"')";
			Query ejbQuery = getEntityManager().createQuery(requete);
			Object l = ejbQuery.getSingleResult();
			logger.debug("clientExiste successful");
			return l!=null;
		} catch (RuntimeException re) {
			logger.error("clientExiste failed", re);
			return false;
		}
	}
	public BigDecimal selectSommeRestante() {
		logger.debug("selectSommeRestante TaWlgrNMoins1");
		try {
			String requete = "select sum(ttc)as sommeRestante from TaWlgrNMoins1 where " +
				"traite = 0 and ttc > 0 ";
			Query ejbQuery = getEntityManager().createQuery(requete);
			BigDecimal l = (BigDecimal)ejbQuery.getSingleResult();
			logger.debug("selectSommeRestante successful");
			return l;
		} catch (RuntimeException re) {
			logger.error("selectSommeRestante failed", re);
			throw re;
		}
	}
	public boolean ClientPlusieursLicence(String codeClient) {
		logger.debug("ClientPlusieursLicence TaWlgrNMoins1");
		try {
			String requete = "select count(*)as nb from TaWlgrNMoins1 where " +
				"codeClient like('"+codeClient+"')";
			Query ejbQuery = getEntityManager().createQuery(requete);
			Object l = ejbQuery.getSingleResult();
			logger.debug("ClientPlusieursLicence successful");
			return (l!=null && ((Long)l)>1);
		} catch (RuntimeException re) {
			logger.error("ClientPlusieursLicence failed", re);
			throw re;
		}
	}
	public boolean clientNonTraite(String codeClient) {
		logger.debug("ClientNonTraite TaWlgrNMoins1");
		try {
			String requete = "select v.codeClient,v.traite from TaWlgrNMoins1 v where " +
				"codeClient like('"+codeClient+"') and traite=0";
			Query ejbQuery = getEntityManager().createQuery(requete);
			Object l = ejbQuery.getSingleResult();
			logger.debug("ClientNonTraite successful");
			return (l!=null );
		} catch (RuntimeException re) {
			logger.error("ClientNonTraite failed", re);
			throw re;
		}
	}
	
	

	@Override
	public List<TaWlgrNMoins1> selectAll() {
		logger.debug("selectAll TaWlgrNMoins1");
		try {
			Query ejbQuery = getEntityManager().createQuery(JPQLQuery);
			List<TaWlgrNMoins1> l = ejbQuery.getResultList();
			logger.debug("selectAll successful");
			return l;
		} catch (RuntimeException re) {
			logger.error("selectAll failed", re);
			throw re;
		}
	}
	
	public List<TaWlgrNMoins1> selectAllClientNonTraite() {
		logger.debug("selectAll TaWlgrNMoins1");
		try {
			String requete = "select v from TaWlgrNMoins1 v where " +
			" v.traite = 0 and ttc > 0)";
		Query ejbQuery = getEntityManager().createQuery(requete);
			List<TaWlgrNMoins1> l = ejbQuery.getResultList();
			logger.debug("selectAll successful");
			return l;
		} catch (RuntimeException re) {
			logger.error("selectAll failed", re);
			throw re;
		}
	}
	
	public List<ArticlesMaintenance> selectArticleMaintenanceClient(String codeTiers) {
		logger.debug("select selectArticleMaintenanceClient");
		try {
			String requete = " select NEW fr.legrain.generationDocumentLGR.dao." +
					"ArticlesMaintenance(" + 
			" (case"+
			" when l.optionsE2='R' then 'HE12SA'"+
			" when l.typeE2='M' then 'HE12MO'"+
			" when l.typeE2='D' then 'HE12MO'"+
			" when l.typeE2='G' then  'HE12GR'   end) as articleE2,"+
			" l.reducE2,"+
			" cast(sum(l.dureeE2Retard+l.dureeE2) as integer)as dureeE2,"+
			" l.totalHtE2,l.participationCogere,"+
			" (case"+
			" when l.bdg=1 then 'BG1N12'"+			
			" when l.optionsF2='G' then 'HF12MO'"+
			" else 'HF12OP' end) as articleF2,"+
			" l.reducF2,"+
			" cast(sum(l.dureeF2Retard+l.dureeF2)as integer)as dureeF2,"+
			" l.totalHtF2,"+
			" ('HS12MO') as articleS2,"+
			" l.reducS2,"+
			" l.bonusHt,"+
			" cast(sum(l.dureeS2Retard+l.dureeS2)as integer)as dureeS2,"+
			" l.totalHtS2"+
			" ) from TaWlgrNMoins1 l where (l.totalHtE2<>0 or l.totalHtF2<>0 or l.totalHtS2<>0) " +
			" and l.codeClient like('"+codeTiers+"')"+
			" group by l.typeE2,l.optionsE2,l.reducE2,l.totalHtE2,l.optionsF2,"+
			" l.totalHtF2,l.reducF2,l.reducS2,l.totalHtS2,l.participationCogere,l.bdg,l.bonusHt";
			Query ejbQuery = getEntityManager().createQuery(requete);
			List<ArticlesMaintenance> l = ejbQuery.getResultList();
			logger.debug("select selectArticleMaintenanceClient");
			return l;
		} catch (RuntimeException re) {
			logger.error("select selectArticleMaintenanceClient", re);
			throw re;
		}
	}
//	public List<ArticlesMaintenance> selectArticleMaintenanceClient(String codeTiers) {
//		logger.debug("select selectArticleMaintenanceClient");
//		try {
//			String requete = " select NEW fr.legrain.generationDocumentLGR.dao." +
//					"ArticlesMaintenance(" + 
//			" (case"+
//			" when l.optionsE2='R' then 'HE11SA'"+
//			" when l.typeE2='M' then 'HE11MO'"+
//			" when l.typeE2='D' then 'HE11MO'"+
//			" when l.typeE2='G' then  'HE11GR'   end) as articleE2,"+
//			" l.reducE2,"+
//			" cast(sum(l.dureeE2Retard+l.dureeE2) as integer)as dureeE2,"+
//			" l.totalHtE2,l.participationCogere,"+
//			" (case"+
//			" when l.bdg=1 then 'BG1N11'"+			
//			" when l.optionsF2='G' then 'HF11MO'"+
//			" else 'HF11OP' end) as articleF2,"+
//			" l.reducF2,"+
//			" cast(sum(l.dureeF2Retard+l.dureeF2)as integer)as dureeF2,"+
//			" l.totalHtF2,"+
//			" ('HS11MO') as articleS2,"+
//			" l.reducS2,"+
//			" l.bonusHt,"+
//			" cast(sum(l.dureeS2Retard+l.dureeS2)as integer)as dureeS2,"+
//			" l.totalHtS2"+
//			" ) from TaWlgrNMoins1 l where (l.totalHtE2<>0 or l.totalHtF2<>0 or l.totalHtS2<>0) " +
//			" and l.codeClient like('"+codeTiers+"')"+
//			" group by l.typeE2,l.optionsE2,l.reducE2,l.totalHtE2,l.optionsF2,"+
//			" l.totalHtF2,l.reducF2,l.reducS2,l.totalHtS2,l.participationCogere,l.bdg,l.bonusHt";
//			Query ejbQuery = getEntityManager().createQuery(requete);
//			List<ArticlesMaintenance> l = ejbQuery.getResultList();
//			logger.debug("select selectArticleMaintenanceClient");
//			return l;
//		} catch (RuntimeException re) {
//			logger.error("select selectArticleMaintenanceClient", re);
//			throw re;
//		}
//	}
	
}
