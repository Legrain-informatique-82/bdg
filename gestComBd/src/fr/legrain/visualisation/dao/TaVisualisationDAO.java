package fr.legrain.visualisation.dao;

// Generated Mar 24, 2009 4:44:04 PM by Hibernate Tools 3.2.0.CR1

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import fr.legrain.gestCom.Appli.AbstractApplicationDAO;
import fr.legrain.gestCom.Appli.Const;
import fr.legrain.lib.data.ExceptLgr;


/**
 * Home object for domain model class TaAdresse.
 * @see fr.legrain.tiers.dao.TaAdresse
 * @author Hibernate Tools
 */
public class TaVisualisationDAO extends AbstractApplicationDAO<TaVisualisation>{

	//private static final Log logger = LogFactory.getLog(TaAdresseDAO.class);
	static Logger logger = Logger.getLogger(TaVisualisationDAO.class);
	
	private String defaultJPQLQuery = "select a from TaVisualisation a";
	
	public TaVisualisationDAO(){
		this(null);
	}
	
	public TaVisualisationDAO(EntityManager em){
		if(em!=null) {
			setEm(em);
		}
//		champIdTable=ctrlGeneraux.getID_TABLE(TaVisualisation.class.getSimpleName());
		initChampId(TaVisualisation.class);
		setJPQLQuery(defaultJPQLQuery);
		setListeChampMaj(Const.C_FICHIER_INI_CHAMPMAJ);
		initNomTable(new TaVisualisation());
	}

	
	public void persist(TaVisualisation transientInstance) {
		logger.debug("persisting TaVisualisation instance");
		try {
			getEntityManager().persist(transientInstance);
			logger.debug("persist successful");
		} catch (RuntimeException re) {
			logger.error("persist failed", re);
			throw re;
		}
	}

	public void remove(TaVisualisation persistentInstance) {
		logger.debug("removing TaVisualisation instance");
		try {
			getEntityManager().remove(persistentInstance);
			logger.debug("remove successful");
		} catch (RuntimeException re) {
			logger.error("remove failed", re);
			throw re;
		}
	}

	public TaVisualisation merge(TaVisualisation detachedInstance) {
		logger.debug("merging TaVisualisation instance");
		try {
			TaVisualisation result = getEntityManager().merge(detachedInstance);
			logger.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			logger.error("merge failed", re);
			throw re;
		}
	}

	public TaVisualisation findById(int id) {
		logger.debug("getting TaVisualisation instance with id: " + id);
		try {
			TaVisualisation instance = getEntityManager().find(TaVisualisation.class, id);
			logger.debug("get successful");
			return instance;
		} catch (RuntimeException re) {
			logger.error("get failed", re);
			throw re;
		}
	}
	
	public List<TaVisualisation> findByCodeModule(String module) {
		List<TaVisualisation> result = null;
		Query query = getEntityManager().createNamedQuery(TaVisualisation.QN.FIND_BY_MODULE);
		query.setParameter(1, module.toUpperCase());
		result = query.getResultList();
		return result;
	}

	@Override
	public List<TaVisualisation> selectAll() {
		// TODO Auto-generated method stub
		logger.debug("selectAll TaVisualisation");
		try {
			Query ejbQuery = getEntityManager().createQuery(JPQLQuery);
			List<TaVisualisation> l = ejbQuery.getResultList();
			logger.debug("selectAll successful");
			return l;
		} catch (RuntimeException re) {
			logger.error("selectAll failed", re);
			throw re;
		}
	}
	
	public void ctrlSaisieSpecifique(TaVisualisation entity,String field) throws ExceptLgr {	
		
	}

	protected TaVisualisation refresh(TaVisualisation persistentInstance) {
		// TODO Auto-generated method stub
		return null;
	}

}
