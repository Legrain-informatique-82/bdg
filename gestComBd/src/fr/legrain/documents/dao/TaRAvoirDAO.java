package fr.legrain.documents.dao;

// Generated Apr 7, 2009 3:27:24 PM by Hibernate Tools 3.2.0.CR1

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TemporalType;

import org.apache.log4j.Logger;

import fr.legrain.articles.dao.TaPrix;
import fr.legrain.articles.dao.TaUnite;
import fr.legrain.gestCom.Appli.AbstractApplicationDAO;
import fr.legrain.gestCom.Appli.Const;
import fr.legrain.tiers.dao.TaTiers;

/**
 * Home object for domain model class TaRAcompte.
 * @see fr.legrain.documents.dao.TaRAcompte
 * @author Hibernate Tools
 */
public class TaRAvoirDAO extends AbstractApplicationDAO<TaRAvoir> {

//	private static final Log log = LogFactory.getLog(TaRAcompteDAO.class);
	static Logger logger = Logger.getLogger(TaRAvoirDAO.class);
	
	private String defaultJPQLQuery = "select a from TaRAvoir a";
	
	public TaRAvoirDAO(){
		this(null);
	}
	
	public TaRAvoirDAO(EntityManager em){
		if(em!=null) {
			setEm(em);
		}
//		champIdTable=ctrlGeneraux.getID_TABLE(TaRAcompte.class.getSimpleName());
		initChampId(TaRAcompte.class);
		setJPQLQuery(defaultJPQLQuery);
		setListeChampMaj(Const.C_FICHIER_INI_CHAMPMAJ);
		initNomTable(new TaRAvoir());
	}
	public TaRAvoir refresh(TaRAvoir detachedInstance) {
		logger.debug("refresh instance");
		try {
			getEntityManager().refresh(detachedInstance);
//			org.hibernate.Session session = (org.hibernate.Session) getEntityManager().getDelegate();
//		    session.evict(detachedInstance);
//		    detachedInstance = getEntityManager().find(TaRAvoir.class, detachedInstance.getId());
		    return detachedInstance;

		} catch (RuntimeException re) {
			logger.error("refresh failed", re);
			throw re;
		}
	}
	public void persist(TaRAvoir transientInstance) {
		logger.debug("persisting TaRAvoir instance");
		try {
			getEntityManager().persist(transientInstance);
			logger.debug("persist successful");
		} catch (RuntimeException re) {
			logger.error("persist failed", re);
			throw re;
		}
	}

	public void remove(TaRAvoir persistentInstance) {
		logger.debug("removing TaRAvoir instance");
		try {
			getEntityManager().remove(persistentInstance);
			logger.debug("remove successful");
		} catch (RuntimeException re) {
			logger.error("remove failed", re);
			throw re;
		}
	}

	public TaRAvoir merge(TaRAvoir detachedInstance) {
		logger.debug("merging TaRAvoir instance");
		try {
			TaRAvoir result = getEntityManager().merge(detachedInstance);
			logger.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			logger.error("merge failed", re);
			throw re;
		}
	}

	public TaRAvoir findById(int id) {
		logger.debug("getting TaRAvoir instance with id: " + id);
		try {
			TaRAvoir instance = getEntityManager().find(TaRAvoir.class, id);
			logger.debug("get successful");
			return instance;
		} catch (RuntimeException re) {
			logger.error("get failed", re);
			throw re;
		}
	}
	
	/* (non-Javadoc)
	 * @see fr.legrain.articles.dao.ILgrDAO#selectAll()
	 */
	public List<TaRAvoir> selectAll(TaAvoir taDoc) {
		logger.debug("selectAll TaRAvoir");
		try {
			if(taDoc!=null){
			Query ejbQuery = getEntityManager().createQuery("select a from TaRAvoir a where a.taAvoir=?");
			ejbQuery.setParameter(1, taDoc);
			List<TaRAvoir> l = ejbQuery.getResultList();
			logger.debug("selectAll successful");
			return l;
			}
			return null;
		} catch (RuntimeException re) {
			logger.error("selectAll failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see fr.legrain.articles.dao.ILgrDAO#selectAll()
	 */
	public List<TaRAvoir> selectAll(TaTiers taTiers,Date dateDeb,Date dateFin) {
		logger.debug("selectAll TaRAvoir");
		try {
//			if(taTiers!=null){				
				if(dateDeb==null)dateDeb=new Date(0);
				if(dateFin==null)dateFin=new Date("01/01/3000");
				
				Query ejbQuery = null;
				if(taTiers!=null){		
					String requete="select a from TaRAvoir a where a.taFacture.taTiers.codeTiers=? " +
							"and a.taAvoir.dateDocument between ? and ?";
					ejbQuery = getEntityManager().createQuery(requete);
					ejbQuery.setParameter(1, taTiers.getCodeTiers());
					ejbQuery.setParameter(2, dateDeb,TemporalType.DATE);
					ejbQuery.setParameter(3, dateFin,TemporalType.DATE);
				} else {
					String requete="select a from TaRAvoir a where a.taAvoir.dateDocument between ? and ?";
					ejbQuery = getEntityManager().createQuery(requete);
					ejbQuery.setParameter(1, dateDeb,TemporalType.DATE);
					ejbQuery.setParameter(2, dateFin,TemporalType.DATE);
				}
			
			List<TaRAvoir> l = ejbQuery.getResultList();
			logger.debug("selectAll successful");
			return l;
//			}
//			return null;
		} catch (RuntimeException re) {
			logger.error("selectAll failed", re);
			throw re;
		}
	}
	
	public List<TaRAvoir> selectSumAvoirDocument(TaAvoir taDoc) {
		logger.debug("selectAll TaRAvoir");
		try {
			if(taDoc!=null){
			Query ejbQuery = getEntityManager().createQuery("select a from TaRAvoir a where a.taAvoir=?");
			ejbQuery.setParameter(1, taDoc);
			List<TaRAvoir> l = ejbQuery.getResultList();
			logger.debug("selectAll successful");
			return l;
			}
			return null;
		} catch (RuntimeException re) {
			logger.error("selectAll failed", re);
			throw re;
		}
	}
	
	@Override
	public List<TaRAvoir> selectAll() {
		// TODO Auto-generated method stub
		return null;
	}
}
