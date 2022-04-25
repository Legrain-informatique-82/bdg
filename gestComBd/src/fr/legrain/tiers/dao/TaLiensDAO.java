package fr.legrain.tiers.dao;

// Generated Mar 24, 2009 4:44:04 PM by Hibernate Tools 3.2.0.CR1

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import fr.legrain.articles.dao.TaUnite;
import fr.legrain.gestCom.Appli.AbstractApplicationDAO;
import fr.legrain.gestCom.Appli.Const;
import fr.legrain.lib.data.ExceptLgr;

/**
 * Home object for domain model class TaLiens.
 * @see fr.legrain.tiers.dao.TaLiens
 * @author Hibernate Tools
 */
public class TaLiensDAO extends AbstractApplicationDAO<TaLiens>{

	//private static final Log logger = LogFactory.getLog(TaLiensDAO.class);
	static Logger logger = Logger.getLogger(TaLiensDAO.class);

	private String defaultJPQLQuery = "select a from TaLiens a";
	
	public TaLiensDAO() {
		this(null);
	}

	public TaLiensDAO(EntityManager em){
		if(em!=null) {
			setEm(em);
		}
//		champIdTable=ctrlGeneraux.getID_TABLE(TaLiens.class.getSimpleName());
		initChampId(TaLiens.class);
		setJPQLQuery(defaultJPQLQuery);
		setListeChampMaj(Const.C_FICHIER_INI_CHAMPMAJ);
		initNomTable(new TaLiens());
	}

	public TaLiens refresh(TaLiens detachedInstance) {
		logger.debug("refresh instance");
		try {
			org.hibernate.Session session = (org.hibernate.Session) getEntityManager().getDelegate();
		    session.evict(detachedInstance);
		    detachedInstance = getEntityManager().find(TaLiens.class, detachedInstance.getIdLiens());
		    return detachedInstance;
		} catch (RuntimeException re) {
			logger.error("refresh failed", re);
			throw re;
		}
	}
	
	public void persist(TaLiens transientInstance) {
		logger.debug("persisting TaLiens instance");
		try {
			getEntityManager().persist(transientInstance);
			logger.debug("persist successful");
		} catch (RuntimeException re) {
			logger.error("persist failed", re);
			throw re;
		}
	}

	public void remove(TaLiens persistentInstance) {
		logger.debug("removing TaLiens instance");
		try {
			getEntityManager().remove(persistentInstance);
			logger.debug("remove successful");
		} catch (RuntimeException re) {
			logger.error("remove failed", re);
			throw re;
		}
	}

	public TaLiens merge(TaLiens detachedInstance) {
		logger.debug("merging TaLiens instance");
		try {
			TaLiens result = getEntityManager().merge(detachedInstance);
			logger.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			logger.error("merge failed", re);
			throw re;
		}
	}

	public TaLiens findById(int id) {
		logger.debug("getting TaLiens instance with id: " + id);
		try {
			TaLiens instance = getEntityManager().find(TaLiens.class, id);
			logger.debug("get successful");
			return instance;
		} catch (RuntimeException re) {
			logger.error("get failed", re);
			throw re;
		}
	}

	@Override
	public List<TaLiens> selectAll() {
		// TODO Auto-generated method stub
		logger.debug("selectAll TaLiens");
		try {
			Query ejbQuery = getEntityManager().createQuery(JPQLQuery);
			List<TaLiens> l = ejbQuery.getResultList();
			logger.debug("selectAll successful");
			return l;
		} catch (RuntimeException re) {
			logger.error("selectAll failed", re);
			throw re;
		}
	}
	
	/**
	 * Retourne pour un tiers données, un liens correspondant au type de liens passé en paramètre.
	 * Il ne doit existé qu'un seul liens de ce type pour ce tiers (0 ou 1)
	 * @param codeTiers
	 * @param codeTypeLiens
	 * @return
	 */
	public TaLiens findLiensTiers(String codeTiers, String codeTypeLiens) {
		logger.debug("getting TaLiens instance with codeTiers: " + codeTiers+" and codeTypeLiens: "+codeTypeLiens);
		try {
			if(!codeTiers.equals("")){
				Query query = getEntityManager().createQuery("select a from TaLiens a where a.taTiers.codeTiers='"+codeTiers+"' and a.taTLiens.codeTLiens='"+codeTypeLiens+"'");
				TaLiens instance = (TaLiens)query.getSingleResult();
				logger.debug("get successful");
				return instance;
			}
			return null;
		} catch (NoResultException re) {
			logger.debug("Pas de liens de type "+codeTypeLiens+" pour le tiers "+codeTiers);
			return null;
		} catch (RuntimeException re) {
			logger.error("get failed", re);
			throw re;
		}
	}
	
	public void ctrlSaisieSpecifique(TaLiens entity,String field) throws ExceptLgr {	
		
	}
}
