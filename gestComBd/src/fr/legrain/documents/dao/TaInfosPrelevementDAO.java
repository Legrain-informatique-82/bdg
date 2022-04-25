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
public class TaInfosPrelevementDAO extends AbstractApplicationDAO<TaInfosPrelevement> {

//	private static final Log log = LogFactory.getLog(TaInfosDevisDAO.class);
	static Logger logger = Logger.getLogger(TaInfosPrelevementDAO.class);
	
	private String defaultJPQLQuery = "select a from TaInfosPrelevement a";
	
	public TaInfosPrelevementDAO(){
		this(null);
	}
	
	public TaInfosPrelevementDAO(EntityManager em){
		if(em!=null) {
			setEm(em);
		}
//		champIdTable=ctrlGeneraux.getID_TABLE(TaInfosPrelevement.class.getSimpleName());
		initChampId(TaInfosPrelevement.class);
		setJPQLQuery(defaultJPQLQuery);
		setListeChampMaj(Const.C_FICHIER_INI_CHAMPMAJ);
		initNomTable(new TaInfosPrelevement());
	}
	public TaInfosPrelevement refresh(TaInfosPrelevement detachedInstance) {
		logger.debug("refresh instance");
		try {
			getEntityManager().refresh(detachedInstance);
//			org.hibernate.Session session = (org.hibernate.Session) getEntityManager().getDelegate();
//		    session.evict(detachedInstance);
//		    detachedInstance = getEntityManager().find(TaInfosPrelevement.class, detachedInstance.getIdInfosDocument());
		    return detachedInstance;
		} catch (RuntimeException re) {
			logger.error("refresh failed", re);
			throw re;
		}
	}
	public void persist(TaInfosPrelevement transientInstance) {
		logger.debug("persisting TaInfosPrelevement instance");
		try {
			getEntityManager().persist(transientInstance);
			logger.debug("persist successful");
		} catch (RuntimeException re) {
			logger.error("persist failed", re);
			throw re;
		}
	}

	public void remove(TaInfosPrelevement persistentInstance) {
		logger.debug("removing TaInfosPrelevement instance");
		try {
			getEntityManager().remove(persistentInstance);
			logger.debug("remove successful");
		} catch (RuntimeException re) {
			logger.error("remove failed", re);
			throw re;
		}
	}

	public TaInfosPrelevement merge(TaInfosPrelevement detachedInstance) {
		logger.debug("merging TaInfosPrelevement instance");
		try {
			TaInfosPrelevement result = getEntityManager().merge(detachedInstance);
			logger.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			logger.error("merge failed", re);
			throw re;
		}
	}

	public TaInfosPrelevement findById(int id) {
		logger.debug("getting TaInfosPrelevement instance with id: " + id);
		try {
			TaInfosPrelevement instance = getEntityManager().find(TaInfosPrelevement.class, id);
			logger.debug("get successful");
			return instance;
		} catch (RuntimeException re) {
			logger.error("get failed", re);
			throw re;
		}
	}
	public TaInfosPrelevement findByCodeProforma(String code) {
		logger.debug("getting TaInfosPrelevement instance with code InfosProforma : " + code);
		try {
			if(!code.equals("")){
				Query query = getEntityManager().createQuery("select a from TaInfosPrelevement a where a.taDocument.codeDocument='"+code+"'");
				TaInfosPrelevement instance = (TaInfosPrelevement)query.getSingleResult();
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
	public List<TaInfosPrelevement> selectAll() {
		logger.debug("selectAll TaInfosPrelevement");
		try {
			Query ejbQuery = getEntityManager().createQuery(JPQLQuery);
			List<TaInfosPrelevement> l = ejbQuery.getResultList();
			logger.debug("selectAll successful");
			return l;
		} catch (RuntimeException re) {
			logger.error("selectAll failed", re);
			throw re;
		}
	}
}
