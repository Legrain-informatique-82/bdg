package fr.legrain.documents.dao;

// Generated Apr 7, 2009 3:27:24 PM by Hibernate Tools 3.2.0.CR1

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import fr.legrain.articles.dao.TaPrix;
import fr.legrain.articles.dao.TaUnite;
import fr.legrain.gestCom.Appli.AbstractApplicationDAO;
import fr.legrain.gestCom.Appli.Const;

/**
 * Home object for domain model class TaInfosDevis.
 * @see fr.legrain.documents.dao.TaInfosDevis
 * @author Hibernate Tools
 */
public class TaInfosProformaDAO extends AbstractApplicationDAO<TaInfosProforma> {

//	private static final Log log = LogFactory.getLog(TaInfosDevisDAO.class);
	static Logger logger = Logger.getLogger(TaInfosProformaDAO.class);
	
	private String defaultJPQLQuery = "select a from TaInfosProforma a";
	
	public TaInfosProformaDAO(){
		this(null);
	}
	
	public TaInfosProformaDAO(EntityManager em){
		if(em!=null) {
			setEm(em);
		}
//		champIdTable=ctrlGeneraux.getID_TABLE(TaInfosProforma.class.getSimpleName());
		initChampId(TaInfosProforma.class);
		setJPQLQuery(defaultJPQLQuery);
		setListeChampMaj(Const.C_FICHIER_INI_CHAMPMAJ);
		initNomTable(new TaInfosProforma());
	}
	public TaInfosProforma refresh(TaInfosProforma detachedInstance) {
		logger.debug("refresh instance");
		try {
			getEntityManager().refresh(detachedInstance);
//			org.hibernate.Session session = (org.hibernate.Session) getEntityManager().getDelegate();
//		    session.evict(detachedInstance);
//		    detachedInstance = getEntityManager().find(TaInfosProforma.class, detachedInstance.getIdInfosDocument());
		    return detachedInstance;
		} catch (RuntimeException re) {
			logger.error("refresh failed", re);
			throw re;
		}
	}
	public void persist(TaInfosProforma transientInstance) {
		logger.debug("persisting TaInfosProforma instance");
		try {
			getEntityManager().persist(transientInstance);
			logger.debug("persist successful");
		} catch (RuntimeException re) {
			logger.error("persist failed", re);
			throw re;
		}
	}

	public void remove(TaInfosProforma persistentInstance) {
		logger.debug("removing TaInfosProforma instance");
		try {
			getEntityManager().remove(persistentInstance);
			logger.debug("remove successful");
		} catch (RuntimeException re) {
			logger.error("remove failed", re);
			throw re;
		}
	}

	public TaInfosProforma merge(TaInfosProforma detachedInstance) {
		logger.debug("merging TaInfosProforma instance");
		try {
			TaInfosProforma result = getEntityManager().merge(detachedInstance);
			logger.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			logger.error("merge failed", re);
			throw re;
		}
	}

	public TaInfosProforma findById(int id) {
		logger.debug("getting TaInfosProforma instance with id: " + id);
		try {
			TaInfosProforma instance = getEntityManager().find(TaInfosProforma.class, id);
			logger.debug("get successful");
			return instance;
		} catch (RuntimeException re) {
			logger.error("get failed", re);
			throw re;
		}
	}
	public TaInfosProforma findByCodeProforma(String code) {
		logger.debug("getting TaInfosProforma instance with code InfosProforma : " + code);
		try {
			if(!code.equals("")){
				Query query = getEntityManager().createQuery("select a from TaInfosProforma a where a.taDocument.codeDocument='"+code+"'");
				TaInfosProforma instance = (TaInfosProforma)query.getSingleResult();
				logger.debug("get successful");
				return instance;
			}
			return null;
		} catch (NoResultException re) {
			return null;
		} catch (RuntimeException re) {
			logger.error("get failed", re);
			throw re;
		}
	}	
	

	/* (non-Javadoc)
	 * @see fr.legrain.articles.dao.ILgrDAO#selectAll()
	 */
	public List<TaInfosProforma> selectAll() {
		logger.debug("selectAll TaInfosProforma");
		try {
			Query ejbQuery = getEntityManager().createQuery(JPQLQuery);
			List<TaInfosProforma> l = ejbQuery.getResultList();
			logger.debug("selectAll successful");
			return l;
		} catch (RuntimeException re) {
			logger.error("selectAll failed", re);
			throw re;
		}
	}
}
