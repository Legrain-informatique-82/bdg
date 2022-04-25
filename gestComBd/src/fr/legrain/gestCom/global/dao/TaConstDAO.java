package fr.legrain.gestCom.global.dao;

// Generated 14 mai 2009 11:08:14 by Hibernate Tools 3.2.4.GA

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import fr.legrain.articles.dao.TaArticle;
import fr.legrain.documents.dao.TaRemise;
import fr.legrain.gestCom.Appli.AbstractApplicationDAO;
import fr.legrain.gestCom.Appli.Const;

/**
 * Home object for domain model class TaInfoEntreprise.
 * @see fr.legrain.dossier.dao.TaInfoEntreprise
 * @author Hibernate Tools
 */
public class TaConstDAO extends AbstractApplicationDAO<TaConst> {

//	private static final Log log = LogFactory.getLog(TaInfoEntrepriseDAO.class);
	static Logger logger = Logger.getLogger(TaConstDAO.class);
	
	private String defaultJPQLQuery = "select a from TaConst a";
	
	public TaConstDAO(){
		this(null);
	}
	
	public TaConstDAO(EntityManager em){
		if(em!=null) {
			setEm(em);
		}
		//champIdTable=ctrlGeneraux.getID_TABLE(TaVersion.class.getSimpleName());
		setJPQLQuery(defaultJPQLQuery);
		setListeChampMaj(Const.C_FICHIER_INI_CHAMPMAJ);
		//initNomTable(new TaVersion());
	}
	public TaConst refresh(TaConst detachedInstance) {
		logger.debug("refresh instance");
		try {
////			getEntityManager().refresh(detachedInstance);
////			logger.debug("refresh successful");
//			org.hibernate.Session session = (org.hibernate.Session) getEntityManager().getDelegate();
//		    session.evict(detachedInstance);
//		    detachedInstance = getEntityManager().find(TaInfoEntreprise.class, detachedInstance.getIdInfoEntreprise());
		    return detachedInstance;
		} catch (RuntimeException re) {
			logger.error("refresh failed", re);
			throw re;
		}
	}
	public void persist(TaConst transientInstance) {
		logger.debug("persisting TaConst instance");
		try {
			getEntityManager().persist(transientInstance);
			logger.debug("persist successful");
		} catch (RuntimeException re) {
			logger.error("persist failed", re);
			throw re;
		}
	}

	public void remove(TaConst persistentInstance) {
		logger.debug("removing TaConst instance");
		try {
			getEntityManager().remove(persistentInstance);
			logger.debug("remove successful");
		} catch (RuntimeException re) {
			logger.error("remove failed", re);
			throw re;
		}
	}

	public TaConst merge(TaConst detachedInstance) {
		logger.debug("merging TaConst instance");
		try {
			TaConst result = getEntityManager().merge(detachedInstance);
			logger.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			logger.error("merge failed", re);
			throw re;
		}
	}

	public TaConst findById(int id) {
		logger.debug("getting TaConst instance with id: " + id);
		try {
			TaConst instance = getEntityManager().find(
					TaConst.class, id);
			logger.debug("get successful");
			return instance;
		} catch (RuntimeException re) {
			logger.error("get failed", re);
			throw re;
		}
	}
	


	@Override
	public List<TaConst> selectAll() {
		logger.debug("selectAll TaConst");
		try {
			Query ejbQuery = getEntityManager().createQuery(JPQLQuery);
			List<TaConst> l = ejbQuery.getResultList();
			logger.debug("selectAll successful");
			return l;
		} catch (RuntimeException re) {
			logger.error("selectAll failed", re);
			throw re;
		}
	}
	
	
	
	public TaConst findByNomConst(String nomConst) {
		logger.debug("getting TaConst instance with nomConst: " + nomConst);
		try {
			if(!nomConst.equals("")){
			Query query = getEntityManager().createQuery("select f from TaConst f where f.nomConst like'"+nomConst+"'");
			TaConst instance = (TaConst)query.getSingleResult();
			logger.debug("get successful");
			return instance;
			}
			return null;
		} catch (RuntimeException re) {
			logger.error("get failed", re);
			throw re;
		}
	}	

	public  BigDecimal remonteConversionPointBonus(){
		BigDecimal taux=BigDecimal.ZERO;
		try {
			TaConstDAO daoConst = new TaConstDAO();
			TaConst ligneConst=daoConst.findByNomConst("CONVERSION_POINT");
			if(ligneConst!=null)taux=ligneConst.getvNumericCourt();
			
			return taux;
		} catch (Exception e) {
			return null;
		}
			
		
	}

}
