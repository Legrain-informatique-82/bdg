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
public class TaWlgrDAO extends AbstractApplicationDAO<TaWlgr> {


	private String defaultJPQLQuery = "select u from TaWlgr u";
	private static final Log logger = LogFactory.getLog(TaWlgrDAO.class);

	public TaWlgrDAO(){
		this(null);
	}
	
	public TaWlgrDAO(EntityManager em){
		if(em!=null) {
			setEm(em);
		}
//		champIdTable=ctrlGeneraux.getID_TABLE(TaWlgr.class.getSimpleName());
		initChampId(TaWlgr.class);
		setJPQLQuery(defaultJPQLQuery);
		setListeChampMaj(Const.C_FICHIER_INI_CHAMPMAJ);
		initNomTable(new TaWlgr());
	}
	public TaWlgr refresh(TaWlgr detachedInstance) {
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
	public void persist(TaWlgr transientInstance) {
		logger.debug("persisting TaWlgr instance");
		try {
			getEntityManager().persist(transientInstance);
			logger.debug("persist successful");
		} catch (RuntimeException re) {
			logger.error("persist failed", re);
			throw re;
		}
	}

	public void remove(TaWlgr persistentInstance) {
		logger.debug("removing TaWlgr instance");
		try {
			getEntityManager().remove(persistentInstance);
			logger.debug("remove successful");
		} catch (RuntimeException re) {
			logger.error("remove failed", re);
			throw re;
		}
	}

	public TaWlgr merge(TaWlgr detachedInstance) {
		logger.debug("merging TaWlgr instance");
		try {
			TaWlgr result = getEntityManager().merge(detachedInstance);
			logger.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			logger.error("merge failed", re);
			throw re;
		}
	}

	public TaWlgr findById(int id) {
		logger.debug("getting TaWlgr instance with id: " + id);
		try {
			TaWlgr instance = getEntityManager().find(TaWlgr.class, id);
			logger.debug("get successful");
			return instance;
		} catch (RuntimeException re) {
			logger.error("get failed", re);
			throw re;
		}
	}
	
	public TaWlgr findByCode(String codeClient) {
		logger.debug("getting TaWlgr instance with codeClient: " + codeClient);
		try {
			Query query = getEntityManager().createQuery("select a from TaWlgr a where " +
					"a.codeClient='"+codeClient+"'");
			TaWlgr instance = (TaWlgr)query.getSingleResult();
			logger.debug("get successful");
			return instance;
		} catch (RuntimeException re) {
			logger.error("get failed", re);
			throw re;
		}
	}
	

	public boolean clientExiste(String codeClient) {
		logger.debug("clientExiste TaWlgr");
		try {
			String requete = "select v.codeClient from TaWlgr v where " +
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
		logger.debug("selectSommeRestante TaWlgr");
		try {
			String requete = "select sum(ttc)as sommeRestante from TaWlgr where " +
				"traite = 0 and ttc > 0 and prel=0";
			Query ejbQuery = getEntityManager().createQuery(requete);
			BigDecimal l = (BigDecimal)ejbQuery.getSingleResult();
			logger.debug("selectSommeRestante successful");
			return l;
		} catch (RuntimeException re) {
			logger.error("selectSommeRestante failed", re);
			throw re;
		}
	}
	
	public BigDecimal selectSommeRestantePrelevement() {
		logger.debug("selectSommeRestante TaWlgr");
		try {
			String requete = "select sum(ttc)as sommeRestante from TaWlgr where " +
				"traite = 0 and ttc > 0 and prel=1";
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
		logger.debug("ClientPlusieursLicence TaWlgr");
		try {
			String requete = "select count(*)as nb from TaWlgr where " +
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
		logger.debug("ClientNonTraite TaWlgr");
		try {
			String requete = "select v.codeClient,v.traite from TaWlgr v where " +
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
	public List<TaWlgr> selectAll() {
		logger.debug("selectAll TaWlgr");
		try {
			Query ejbQuery = getEntityManager().createQuery(JPQLQuery);
			List<TaWlgr> l = ejbQuery.getResultList();
			logger.debug("selectAll successful");
			return l;
		} catch (RuntimeException re) {
			logger.error("selectAll failed", re);
			throw re;
		}
	}
	
	public List<TaWlgr> selectAllClientNonTraite() {
		logger.debug("selectAllClientNonTraite TaWlgr");
		try {
			String requete = "select v from TaWlgr v where " +
			" v.traite = 0 and ttc > 0 and v.prel=0)";
		Query ejbQuery = getEntityManager().createQuery(requete);
			List<TaWlgr> l = ejbQuery.getResultList();
			logger.debug("selectAll successful");
			return l;
		} catch (RuntimeException re) {
			logger.error("selectAll failed", re);
			throw re;
		}
	}
	
	public List<TaWlgr> selectAllClientNonTraiteAPrelever() {
		logger.debug("selectAllClientNonTraiteAPrelever TaWlgr");
		try {
			String requete = "select v from TaWlgr v where " +
			" v.traite = 0 and ttc > 0 and v.prel=1)";
		Query ejbQuery = getEntityManager().createQuery(requete);
			List<TaWlgr> l = ejbQuery.getResultList();
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
			" (l.artEpi) as articleE2,"+
			" l.reducE2,"+
			" cast(sum(l.dureeE2Retard+l.dureeE2) as integer)as dureeE2,"+
			" l.totalHtE2,l.participationCogere,"+
			" (l.artBdg) as articleF2,"+
			" l.reducF2,"+
			" cast(sum(l.dureeF2Retard+l.dureeF2)as integer)as dureeF2,"+
			" l.totalHtF2,"+
			" (l.artSara) as articleS2,"+
			" l.reducS2,"+
			" l.bonusHt,"+
			" cast(sum(l.dureeS2Retard+l.dureeS2)as integer)as dureeS2,"+
			" l.totalHtS2"+
			" ) from TaWlgr l where (l.totalHtE2<>0 or l.totalHtF2<>0 or l.totalHtS2<>0) " +
			" and l.codeClient like('"+codeTiers+"')"+
			" group by l.artEpi,l.artBdg,l.artSara,l.typeE2,l.optionsE2,l.reducE2,l.totalHtE2,l.optionsF2,"+
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
//			" when l.optionsE2='R' then 'HE12SA'"+
//			" when l.typeE2='M' then 'HE12MO'"+
//			" when l.typeE2='D' then 'HE12MO'"+
//			" when l.typeE2='G' then  'HE12GR'   end) as articleE2,"+
//			" l.reducE2,"+
//			" cast(sum(l.dureeE2Retard+l.dureeE2) as integer)as dureeE2,"+
//			" l.totalHtE2,l.participationCogere,"+
//			" (case"+
//			" when l.bdg=1 then 'BG1N12'"+			
//			" when l.optionsF2='G' then 'HF12MO'"+
//			" else 'HF12OP' end) as articleF2,"+
//			" l.reducF2,"+
//			" cast(sum(l.dureeF2Retard+l.dureeF2)as integer)as dureeF2,"+
//			" l.totalHtF2,"+
//			" ('HS12MO') as articleS2,"+
//			" l.reducS2,"+
//			" l.bonusHt,"+
//			" cast(sum(l.dureeS2Retard+l.dureeS2)as integer)as dureeS2,"+
//			" l.totalHtS2"+
//			" ) from TaWlgr l where (l.totalHtE2<>0 or l.totalHtF2<>0 or l.totalHtS2<>0) " +
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
