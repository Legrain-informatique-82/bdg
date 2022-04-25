package fr.legrain.articles.dao;

// Generated Mar 24, 2009 4:44:04 PM by Hibernate Tools 3.2.0.CR1

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import fr.legrain.gestCom.Appli.AbstractApplicationDAO;
import fr.legrain.gestCom.Appli.Const;

/**
 * Home object for domain model class TaTNoteArticle.
 * @see fr.legrain.tiers.dao.TaTitreTransport
 * @author Hibernate Tools
 */
public class TaTSupportDAO extends AbstractApplicationDAO<TaTSupport>{

	//private static final Log logger = LogFactory.getLog(TaTNoteArticle.class);
	static Logger logger = Logger.getLogger(TaTSupportDAO.class);

	private String defaultJPQLQuery = "select a from TaTSupport a";
	
	public TaTSupportDAO() {
		this(null);
	}
	
	public TaTSupportDAO(EntityManager em){
		if(em!=null) {
			setEm(em);
		}
//		champIdTable=ctrlGeneraux.getID_TABLE(TaTNoteArticle.class.getSimpleName());
		initChampId(TaTSupport.class);
		setJPQLQuery(defaultJPQLQuery);
		setListeChampMaj(Const.C_FICHIER_INI_CHAMPMAJ);
		initNomTable(new TaTSupport());
	}
	public TaTSupport refresh(TaTSupport detachedInstance) {
		logger.debug("refresh instance");
		try {
			org.hibernate.Session session = (org.hibernate.Session) getEntityManager().getDelegate();
		    session.evict(detachedInstance);
		    detachedInstance = getEntityManager().find(TaTSupport.class, detachedInstance.getIdTSupport());
		    return detachedInstance;
		} catch (RuntimeException re) {
			logger.error("refresh failed", re);
			throw re;
		}
	}
	public void persist(TaTSupport transientInstance) {
		logger.debug("persisting TaTSupport instance");
		try {
			getEntityManager().persist(transientInstance);
			logger.debug("persist successful");
		} catch (RuntimeException re) {
			logger.error("persist failed", re);
			throw re;
		}
	}

	public void remove(TaTSupport persistentInstance) {
		logger.debug("removing TaTSupport instance");
		try {
			getEntityManager().remove(persistentInstance);
			logger.debug("remove successful");
		} catch (RuntimeException re) {
			logger.error("remove failed", re);
			throw re;
		}
	}

	public TaTSupport merge(TaTSupport detachedInstance) {
		logger.debug("merging TaTSupport instance");
		try {
			TaTSupport result = getEntityManager().merge(detachedInstance);
			logger.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			logger.error("merge failed", re);
			throw re;
		}
	}

	public TaTSupport findById(int id) {
		logger.debug("getting TaTSupport instance with id: " + id);
		try {
			TaTSupport instance = getEntityManager().find(TaTSupport.class, id);
			logger.debug("get successful");
			return instance;
		} catch (RuntimeException re) {
			logger.error("get failed", re);
			throw re;
		}
	}
	
	public TaTSupport findByCode(String code) {
		logger.debug("getting TaTSupport instance with code: " + code);
		try {
			if(!code.equals("")){
			Query query = getEntityManager().createQuery("select f from TaTSupport f where f.codeTSupport='"+code+"'");
			TaTSupport instance = (TaTSupport)query.getSingleResult();
			logger.debug("get successful");
			return instance;
			}
			return null;
		} catch (RuntimeException re) {
			logger.error("get failed", re);
			throw re;
		}
	}
	
	@Override
	public List<TaTSupport> selectAll() {
		// TODO Auto-generated method stub
		logger.debug("selectAll TaTSupport");
		try {
			Query ejbQuery = getEntityManager().createQuery(JPQLQuery);
			List<TaTSupport> l = ejbQuery.getResultList();
			logger.debug("selectAll successful");
			return l;
		} catch (RuntimeException re) {
			logger.error("selectAll failed", re);
			throw re;
		}
	}

	public List<TaRTSupport> rechercheLigneAbonnementACreer(Date dateDeb, Date datefin, String codeTAbonnement, String codeTiers) {
		List<TaRTSupport> result = null;
		Query query = null;
//		String requete="select lf from TaLFacture lf join lf.taArticle a join lf.taDocument f join f.taTiers t  where f.dateDocument between ? and ?" +
//				" and t.codeTiers like ? and exists(" +
//				" select a1 from TaArticle a1 join a1.taTArticles ta where a1.codeArticle=a.codeArticle and upper(ta.types) ='A' and ta.codeTSupport like ?)" +
//				" and not exists(select s from TaAbonnement s where s.taLDocument.idLDocument=lf.idLDocument)" +
//				" order by f.codeDocument,a.codeArticle";
		String requete="select tr from TaRTSupport tr join tr.taTSupport ts join tr.taTAbonnement ta where ta.codeTAbonnement like ?" +
				" and t.codeTiers like ?  and not exists(select s from TaAbonnement s where s.taSupportAbon.idSupportAbon=lf.idLDocument)" ;		

		if(codeTAbonnement==null || codeTAbonnement.toLowerCase().equals("tous")) {
			query = getEntityManager().createQuery(requete);
			query.setParameter(1, dateDeb);
			query.setParameter(2, datefin);
			query.setParameter(3, codeTiers);
			query.setParameter(4, "%");
		}else if(codeTAbonnement!=null ) {
			query = getEntityManager().createQuery(requete);
			query.setParameter(1, dateDeb);
			query.setParameter(2, datefin);
			query.setParameter(3, codeTiers);
			query.setParameter(4, codeTAbonnement);
		}
		
		result = query.getResultList();
		for (TaRTSupport obj : result) {
			
		}
		return result;
	}
}
