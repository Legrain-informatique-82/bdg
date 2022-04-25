package fr.legrain.documents.dao;

// Generated Mar 24, 2009 4:44:04 PM by Hibernate Tools 3.2.0.CR1

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import fr.legrain.gestCom.Appli.AbstractApplicationDAO;
import fr.legrain.gestCom.Appli.Const;
import fr.legrain.lib.data.ExceptLgr;
import fr.legrain.lib.data.LibConversion;

/**
 * Home object for domain model class TaTLiens.
 * @see fr.legrain.tiers.dao.TaTLiens
 * @author Hibernate Tools
 */
public class TaDocumentEditableDAO extends AbstractApplicationDAO<TaDocumentEditable>{

	static Logger logger = Logger.getLogger(TaDocumentEditableDAO.class);

	private String defaultJPQLQuery = "select a from TaDocumentEditable a" ;
			//"order by actif,ordreTRelance";
	
	public TaDocumentEditableDAO() {
		this(null);
	}
	
	public TaDocumentEditableDAO(EntityManager em){
		if(em!=null) {
			setEm(em);
		}
		initChampId(TaDocumentEditable.class);
		setJPQLQuery(defaultJPQLQuery);
		setListeChampMaj(Const.C_FICHIER_INI_CHAMPMAJ);
		initNomTable(new TaDocumentEditable());
	}
	public TaDocumentEditable refresh(TaDocumentEditable detachedInstance) {
		logger.debug("refresh instance");
		try {
			getEntityManager().refresh(detachedInstance);
//			org.hibernate.Session session = (org.hibernate.Session) getEntityManager().getDelegate();
//		    session.evict(detachedInstance);
//		    detachedInstance = getEntityManager().find(TaDocumentEditable.class, detachedInstance.getIdDocumentDoc());
		    return detachedInstance;
		} catch (RuntimeException re) {
			logger.error("refresh failed", re);
			throw re;
		}
	}
	public void persist(TaDocumentEditable transientInstance) {
		logger.debug("persisting TaDocumentDoc instance");
		try {
			getEntityManager().persist(transientInstance);
			logger.debug("persist successful");
		} catch (RuntimeException re) {
			logger.error("persist failed", re);
			throw re;
		}
	}

	public void remove(TaDocumentEditable persistentInstance) {
		logger.debug("removing TaDocumentDoc instance");
		try {
			getEntityManager().remove(persistentInstance);
			logger.debug("remove successful");
		} catch (RuntimeException re) {
			logger.error("remove failed", re);
			throw re;
		}
	}

	public TaDocumentEditable merge(TaDocumentEditable detachedInstance) {
		logger.debug("merging TaDocumentDoc instance");
		try {
			TaDocumentEditable result = getEntityManager().merge(detachedInstance);
			logger.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			logger.error("merge failed", re);
			throw re;
		}
	}

	public TaDocumentEditable findById(int id) {
		logger.debug("getting TaDocumentDoc instance with id: " + id);
		try {
			TaDocumentEditable instance = getEntityManager().find(TaDocumentEditable.class, id);
			logger.debug("get successful");
			return instance;
		} catch (RuntimeException re) {
			logger.error("get failed", re);
			throw re;
		}
	}
	
	public TaDocumentEditable findByCode(String code) {
		logger.debug("getting TaDocumentDoc instance with code: " + code);
		try {
			if(!code.equals("")){
				Query query = getEntityManager().createQuery("select f from TaDocumentEditable f where f.codeDocumentDoc='"+code+"'");
				TaDocumentEditable instance = (TaDocumentEditable)query.getSingleResult();
				logger.debug("get successful");
				return instance;
			}
			return null;
		} catch (RuntimeException re) {
			logger.error("get failed", re);
			throw re;
		}
	}
	
	public List<TaDocumentEditable> findByCodeTypeDoc(String codeTypeDoc) {
		logger.debug("getting TaDocumentDoc instance with code type doc: " + codeTypeDoc);
		try {
			if(!codeTypeDoc.equals("")){
				//Query query = getEntityManager().createQuery("select f from TaDocumentEditable f where f.taTDoc.codeTDoc='"+codeTypeDoc+"'");
				Query query = getEntityManager().createQuery("select f from TaDocumentEditable f, in(f.taTDoc) p where p.codeTDoc='"+codeTypeDoc+"'");
//				query.setParameter(1, new TaTDocDAO(getEntityManager()).findById(1));
				List<TaDocumentEditable> l = query.getResultList();
				logger.debug("get successful");
				return l;
			}
			return null;
		} catch (RuntimeException re) {
			logger.error("get failed", re);
			throw re;
		}
	}
	
	@Override
	public List<TaDocumentEditable> selectAll() {
		logger.debug("selectAll TaDocumentDoc");
		try {  //+" order by a.ordreTRelance"
			Query ejbQuery = getEntityManager().createQuery(JPQLQuery);
			List<TaDocumentEditable> l = ejbQuery.getResultList();
			logger.debug("selectAll successful");
			return l;
		} catch (RuntimeException re) {
			logger.error("selectAll failed", re);
			throw re;
		}
	}
	

	
	public void RAZCheminVersion(String typeLogiciel) {
		try {
			List<TaDocumentEditable> liste=selectAll();
			for (TaDocumentEditable TaDocumentDoc : liste) {
				getEntityManager().getTransaction().begin();
				TaDocumentDoc.setActif(LibConversion.booleanToInt(TaDocumentDoc.getTypeLogiciel().equals(typeLogiciel)));
//				TaDocumentDoc.setCheminCorrespRelance(null);
//				TaDocumentDoc.setCheminModelRelance(null);
				TaDocumentDoc=merge(TaDocumentDoc);
				getEntityManager().getTransaction().commit();
				TaDocumentDoc=refresh(TaDocumentDoc);
			}
		} catch (RuntimeException re) {
			logger.error("get failed", re);
			throw re;
		}
	}
	
	public void ctrlSaisieSpecifique(TaDocumentEditable entity,String field) throws ExceptLgr {	
		
	}
}
