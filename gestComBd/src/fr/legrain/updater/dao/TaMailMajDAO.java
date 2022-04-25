package fr.legrain.updater.dao;

// Generated 14 mai 2009 14:36:23 by Hibernate Tools 3.2.4.GA

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import fr.legrain.articles.dao.TaArticleDAO;
import fr.legrain.gestCom.Appli.AbstractApplicationDAO;
import fr.legrain.gestCom.Appli.Const;

/**
 * Home object for domain model class TaMailMaj.
 * @see fr.legrain.updater.dao.TaMailMaj
 * @author Hibernate Tools
 */
public class TaMailMajDAO extends AbstractApplicationDAO<TaMailMaj> {

//	private static final Log log = LogFactory.getLog(TaMailMajDAO.class);
	static Logger logger = Logger.getLogger(TaArticleDAO.class);

	private String defaultJPQLQuery = "select a from TaMailMaj a";
	
	public TaMailMajDAO(){
		this(null);
	}
	
	public TaMailMajDAO(EntityManager em){
		if(em!=null) {
			setEm(em);
		}
//		champIdTable=ctrlGeneraux.getID_TABLE(TaMailMaj.class.getSimpleName());
		initChampId(TaMailMaj.class);
		setJPQLQuery(defaultJPQLQuery);
		setListeChampMaj(Const.C_FICHIER_INI_CHAMPMAJ);
		initNomTable(new TaMailMaj());
	}

	public void persist(TaMailMaj transientInstance) {
		logger.debug("persisting TaMailMaj instance");
		try {
			getEntityManager().persist(transientInstance);
			logger.debug("persist successful");
		} catch (RuntimeException re) {
			logger.error("persist failed", re);
			throw re;
		}
	}

	public void remove(TaMailMaj persistentInstance) {
		logger.debug("removing TaMailMaj instance");
		try {
			getEntityManager().remove(persistentInstance);
			logger.debug("remove successful");
		} catch (RuntimeException re) {
			logger.error("remove failed", re);
			throw re;
		}
	}

	public TaMailMaj merge(TaMailMaj detachedInstance) {
		logger.debug("merging TaMailMaj instance");
		try {
			TaMailMaj result = getEntityManager().merge(detachedInstance);
			logger.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			logger.error("merge failed", re);
			throw re;
		}
	}

	public TaMailMaj findById(int id) {
		logger.debug("getting TaMailMaj instance with id: " + id);
		try {
			TaMailMaj instance = getEntityManager().find(TaMailMaj.class, id);
			logger.debug("get successful");
			return instance;
		} catch (RuntimeException re) {
			logger.error("get failed", re);
			throw re;
		}
	}

	@Override
	public List<TaMailMaj> selectAll() {
		logger.debug("selectAll TaMailMaj");
		try {
			Query ejbQuery = getEntityManager().createQuery(JPQLQuery);
			List<TaMailMaj> l = ejbQuery.getResultList();
			logger.debug("selectAll successful");
			return l;
		} catch (RuntimeException re) {
			logger.error("selectAll failed", re);
			throw re;
		}
	}

	protected TaMailMaj refresh(TaMailMaj persistentInstance) {
		// TODO Auto-generated method stub
		return null;
	}
}
