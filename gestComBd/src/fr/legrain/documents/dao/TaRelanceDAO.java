package fr.legrain.documents.dao;

// Generated Mar 24, 2009 4:44:04 PM by Hibernate Tools 3.2.0.CR1

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import fr.legrain.articles.dao.TaPrix;
import fr.legrain.gestCom.Appli.AbstractApplicationDAO;
import fr.legrain.gestCom.Appli.Const;
import fr.legrain.gestCom.Module_Document.IDocumentTiers;
import fr.legrain.lib.data.ExceptLgr;

/**
 * Home object for domain model class TaTLiens.
 * @see fr.legrain.tiers.dao.TaTLiens
 * @author Hibernate Tools
 */
public class TaRelanceDAO extends AbstractApplicationDAO<TaRelance>{

	//private static final Log logger = LogFactory.getLog(TaTLiensDAO.class);
	static Logger logger = Logger.getLogger(TaRelanceDAO.class);

	private String defaultJPQLQuery = "select a from TaRelance a ";
	
	public TaRelanceDAO() {
		this(null);
	}
	
	public TaRelanceDAO(EntityManager em){
		if(em!=null) {
			setEm(em);
		}
//		champIdTable=ctrlGeneraux.getID_TABLE(TaRelance.class.getSimpleName());
		initChampId(TaRelance.class);
		setJPQLQuery(defaultJPQLQuery);
		setListeChampMaj(Const.C_FICHIER_INI_CHAMPMAJ);
		initNomTable(new TaRelance());
	}
	public TaRelance refresh(TaRelance detachedInstance) {
		logger.debug("refresh instance");
		try {
			getEntityManager().refresh(detachedInstance);
//			org.hibernate.Session session = (org.hibernate.Session) getEntityManager().getDelegate();
//		    session.evict(detachedInstance);
//		    detachedInstance = getEntityManager().find(TaRelance.class, detachedInstance.getIdRelance());
		    return detachedInstance;
		} catch (RuntimeException re) {
			logger.error("refresh failed", re);
			throw re;
		}
	}
	public void persist(TaRelance transientInstance) {
		logger.debug("persisting TaRelance instance");
		try {
			getEntityManager().persist(transientInstance);
			logger.debug("persist successful");
		} catch (RuntimeException re) {
			logger.error("persist failed", re);
			throw re;
		}
	}

	public void remove(TaRelance persistentInstance) {
		logger.debug("removing TaRelance instance");
		try {
			getEntityManager().remove(persistentInstance);
			logger.debug("remove successful");
		} catch (RuntimeException re) {
			logger.error("remove failed", re);
			throw re;
		}
	}

	public TaRelance merge(TaRelance detachedInstance) {
		logger.debug("merging TaRelance instance");
		try {
			TaRelance result = getEntityManager().merge(detachedInstance);
			logger.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			logger.error("merge failed", re);
			throw re;
		}
	}

	public TaRelance findById(int id) {
		logger.debug("getting TaRelance instance with id: " + id);
		try {
			TaRelance instance = getEntityManager().find(TaRelance.class, id);
			logger.debug("get successful");
			return instance;
		} catch (RuntimeException re) {
			logger.error("get failed", re);
			throw re;
		}
	}
	
	public TaRelance findByCode(String code) {
		logger.debug("getting TaRelance instance with code: " + code);
		try {
			if(!code.equals("")){
			Query query = getEntityManager().createQuery("select f from TaRelance f where f.codeRelance='"+code+"'");
			TaRelance instance = (TaRelance)query.getSingleResult();
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
	public List<TaRelance> selectAll() {
		// TODO Auto-generated method stub
		logger.debug("selectAll TaRelance");
		try {  
			Query ejbQuery = getEntityManager().createQuery(JPQLQuery);
			List<TaRelance> l = ejbQuery.getResultList();
			logger.debug("selectAll successful");
			return l;
		} catch (RuntimeException re) {
			logger.error("selectAll failed", re);
			throw re;
		}
	}
	/**
	 * Récupère le type de relance supérieur au type maxi déjà relancé
	 * s'il n'y en a pas au dessus, renvoie le max
	 * s'il n'y a pas de type de relance possible, renvoie null.
	 * @param taLRelance
	 * @return
	 */
	public TaTRelance maxTaTRelance(IDocumentTiers taDocument){
		try {  
			String requete="select max(rt.ordreTRelance) from TaRelance r " +
					" join r.taLRelances rl" +
					" join rl.taTRelance rt" +
					" where rl.codeDocument like ?";
			Integer l=null;
			Query ejbQuery = getEntityManager().createQuery(requete);
			ejbQuery.setParameter(1, taDocument.getCodeDocument());
			if(ejbQuery.getSingleResult()!=null){
				l = (Integer)ejbQuery.getSingleResult();
			}else{
				l = -1;
			}
			TaTRelanceDAO daoTRelance = new TaTRelanceDAO(getEntityManager());
			return daoTRelance.taTRelanceSuperieur(l);
		} catch (RuntimeException re) {
			logger.error("selectAll failed", re);
			throw re;
		}
	}
	
	public void ctrlSaisieSpecifique(TaRelance entity,String field) throws ExceptLgr {	
		
	}
}
