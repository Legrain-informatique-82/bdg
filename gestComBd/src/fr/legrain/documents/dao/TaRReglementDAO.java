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
import fr.legrain.gestCom.Module_Document.IDocumentTiersComplet;
import fr.legrain.tiers.dao.TaTiers;

/**
 * Home object for domain model class TaRAcompte.
 * @see fr.legrain.documents.dao.TaRAcompte
 * @author Hibernate Tools
 */
public class TaRReglementDAO extends AbstractApplicationDAO<TaRReglement> {

//	private static final Log log = LogFactory.getLog(TaRAcompteDAO.class);
	static Logger logger = Logger.getLogger(TaRReglementDAO.class);
	
	private String defaultJPQLQuery = "select a from TaRReglement a";
	
	public TaRReglementDAO(){
		this(null);
	}
	
	public TaRReglementDAO(EntityManager em){
		if(em!=null) {
			setEm(em);
		}
//		champIdTable=ctrlGeneraux.getID_TABLE(TaRReglement.class.getSimpleName());
		initChampId(TaRReglement.class);
		setJPQLQuery(defaultJPQLQuery);
		setListeChampMaj(Const.C_FICHIER_INI_CHAMPMAJ);
		initNomTable(new TaRReglement());
	}
	public TaRReglement refresh(TaRReglement detachedInstance) {
		logger.debug("refresh instance");
		try {
			getEntityManager().refresh(detachedInstance);
//			org.hibernate.Session session = (org.hibernate.Session) getEntityManager().getDelegate();
//		    session.evict(detachedInstance);
//		    detachedInstance = getEntityManager().find(TaRReglement.class, detachedInstance.getId());
		    return detachedInstance;

		} catch (RuntimeException re) {
			logger.error("refresh failed", re);
			throw re;
		}
	}
	public void persist(TaRReglement transientInstance) {
		logger.debug("persisting TaRReglement instance");
		try {
			getEntityManager().persist(transientInstance);
			logger.debug("persist successful");
		} catch (RuntimeException re) {
			logger.error("persist failed", re);
			throw re;
		}
	}

	public void remove(TaRReglement persistentInstance) {
		logger.debug("removing TaRReglement instance");
		try {
			getEntityManager().remove(persistentInstance);
			logger.debug("remove successful");
		} catch (RuntimeException re) {
			logger.error("remove failed", re);
			throw re;
		}
	}

	public TaRReglement merge(TaRReglement detachedInstance) {
		logger.debug("merging TaRReglement instance");
		try {
			TaRReglement result = getEntityManager().merge(detachedInstance);
			logger.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			logger.error("merge failed", re);
			throw re;
		}
	}

	public TaRReglement findById(int id) {
		logger.debug("getting TaRReglement instance with id: " + id);
		try {
			TaRReglement instance = getEntityManager().find(TaRReglement.class, id);
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
	public List<TaRReglement> selectAll(IDocumentTiersComplet taFacture) {
		logger.debug("selectAll TaRReglement");
		try {
			if(taFacture!=null){
			Query ejbQuery = getEntityManager().createQuery("select a from TaRReglement a " +
					"where a.taFacture.codeDocument=?" +
					" or a.taAvoir.codeDocument=?");
			ejbQuery.setParameter(1, taFacture.getCodeDocument());
			ejbQuery.setParameter(2, taFacture.getCodeDocument());
			List<TaRReglement> l = ejbQuery.getResultList();
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
	public List<TaRReglement> selectAll(TaTiers taTiers,Date dateDeb,Date dateFin) {
		logger.debug("selectAll TaRReglement");
		try {
//			if(taTiers!=null){				
				if(dateDeb==null)dateDeb=new Date(0);
				if(dateFin==null)dateFin=new Date("01/01/3000");
				
				Query ejbQuery = null;
				if(taTiers!=null) {
					String requete="select a from TaRReglement a where a.taFacture.taTiers.codeTiers like ? " +
							"and a.taReglement.dateDocument between ? and ?";
					ejbQuery = getEntityManager().createQuery(requete);
					ejbQuery.setParameter(1, taTiers.getCodeTiers());
					ejbQuery.setParameter(2, dateDeb,TemporalType.DATE);
					ejbQuery.setParameter(3, dateFin,TemporalType.DATE);
				} else {
					String requete="select a from TaRReglement a where a.taReglement.dateDocument between ? and ?";
					ejbQuery = getEntityManager().createQuery(requete);
					ejbQuery.setParameter(1, dateDeb,TemporalType.DATE);
					ejbQuery.setParameter(2, dateFin,TemporalType.DATE);
				}
		
			
			List<TaRReglement> l = ejbQuery.getResultList();
			logger.debug("selectAll successful");
			return l;
//			}
//			return null;
		} catch (RuntimeException re) {
			logger.error("selectAll failed", re);
			throw re;
		}
	}
	
	public List<TaRReglement> selectSumReglementDocument(TaFacture taFacture) {
		logger.debug("selectAll TaRAcompte");
		try {
			if(taFacture!=null){
			Query ejbQuery = getEntityManager().createQuery("select a from TaRReglement a " +
					"where a.taFacture=?");
			ejbQuery.setParameter(1, taFacture);
			List<TaRReglement> l = ejbQuery.getResultList();
			logger.debug("selectAll successful");
			return l;
			}
			return null;
		} catch (RuntimeException re) {
			logger.error("selectAll failed", re);
			throw re;
		}
	}

	public List<TaRReglement> findByCode(String code) {
		logger.debug("getting TaRReglement instance with code: " + code);
		try {
			Query query = getEntityManager().createQuery("select a from TaRReglement a " +
					"where a.taReglement.codeDocument like ?");
			query.setParameter(1, code);
			List<TaRReglement> l = query.getResultList();
			return l;
		} catch (RuntimeException re) {
			throw re;
		} catch (Exception e) {
			return null;
		}
	}
	@Override
	public List<TaRReglement> selectAll() {
		// TODO Auto-generated method stub
		return null;
	}
}
