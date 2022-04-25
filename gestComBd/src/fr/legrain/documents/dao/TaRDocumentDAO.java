package fr.legrain.documents.dao;

// Generated Apr 7, 2009 3:27:24 PM by Hibernate Tools 3.2.0.CR1

import java.beans.PropertyDescriptor;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.Id;
import javax.persistence.Query;
import javax.persistence.Table;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.log4j.Logger;

import fr.legrain.articles.dao.TaUnite;
import fr.legrain.gestCom.Appli.AbstractApplicationDAO;
import fr.legrain.gestCom.Appli.Const;
import fr.legrain.gestCom.Appli.LgrHibernateValidated;

/**
 * Home object for domain model class TaRDocument.
 * @see fr.legrain.documents.dao.TaRDocument
 * @author Hibernate Tools
 */
public class TaRDocumentDAO extends AbstractApplicationDAO<TaRDocument> {

//	private static final Log log = LogFactory.getLog(TaRDocumentDAO.class);
	static Logger logger = Logger.getLogger(TaRDocumentDAO.class);
	
	private String defaultJPQLQuery = "select a from TaRDocument a";
	
	public TaRDocumentDAO(){
		this(null);
	}
	
	public TaRDocumentDAO(EntityManager em){
		if(em!=null) {
			setEm(em);
		}					

		initChampId(TaRDocument.class);
		//champIdTable=	ctrlGeneraux.getID_TABLE(TaRDocument.class.getSimpleName());
		setJPQLQuery(defaultJPQLQuery);
		setListeChampMaj(Const.C_FICHIER_INI_CHAMPMAJ);
		initNomTable(new TaRDocument());
	}
	public TaRDocument refresh(TaRDocument detachedInstance) {
		logger.debug("refresh instance");
		try {
			getEntityManager().refresh(detachedInstance);
//			org.hibernate.Session session = (org.hibernate.Session) getEntityManager().getDelegate();
//		    session.evict(detachedInstance);
//		    
//            if(detachedInstance.getTaFacture()!=null)session.evict(detachedInstance.getTaFacture());
//            if(detachedInstance.getTaAcompte()!=null)session.evict(detachedInstance.getTaAcompte());
//            if(detachedInstance.getTaApporteur()!=null)session.evict(detachedInstance.getTaApporteur());
//            if(detachedInstance.getTaAvoir()!=null)session.evict(detachedInstance.getTaAvoir());
//            if(detachedInstance.getTaBoncde()!=null)session.evict(detachedInstance.getTaBoncde());
//            if(detachedInstance.getTaBonliv()!=null)session.evict(detachedInstance.getTaBonliv());
//            if(detachedInstance.getTaDevis()!=null)session.evict(detachedInstance.getTaDevis());
//            if(detachedInstance.getTaPrelevement()!=null)session.evict(detachedInstance.getTaPrelevement());
//            if(detachedInstance.getTaProforma()!=null)session.evict(detachedInstance.getTaProforma());
//
//		    detachedInstance = getEntityManager().find(TaRDocument.class, detachedInstance.getIdRDocument());
	           
		    return detachedInstance;

		} catch (RuntimeException re) {
			logger.error("refresh failed", re);
			throw re;
		}
	}
	public void persist(TaRDocument transientInstance) {
		logger.debug("persisting TaRDocument instance");
		try {
			getEntityManager().persist(transientInstance);
			logger.debug("persist successful");
		} catch (RuntimeException re) {
			logger.error("persist failed", re);
			throw re;
		}
	}

	public void remove(TaRDocument persistentInstance) {
		logger.debug("removing TaRDocument instance");
		try {
			persistentInstance.verifRemoveAfter(getEntityManager());
			getEntityManager().remove(persistentInstance);
			logger.debug("remove successful");
		} catch (RuntimeException re) {
			logger.error("remove failed", re);
			throw re;
		} catch (Exception e) {
			logger.error("remove failed", e);
		}
	}

	public TaRDocument merge(TaRDocument detachedInstance) {
		logger.debug("merging TaRDocument instance");
		try {
			TaRDocument result = getEntityManager().merge(detachedInstance);
			logger.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			logger.error("merge failed", re);
			throw re;
		}
	}

	public TaRDocument findById(int id) {
		logger.debug("getting TaRDocument instance with id: " + id);
		try {
			TaRDocument instance = getEntityManager().find(TaRDocument.class, id);
			logger.debug("get successful");
			return instance;
		} catch (RuntimeException re) {
			logger.error("get failed", re);
			throw re;
		}
	}
	
	public TaRDocument findByIdGenere(TaRDocument taDocumentGenere) {
		logger.debug("getting findByIdGenere instance with : " + taDocumentGenere);
		try {
			Query ejbQuery = getEntityManager().createQuery("select r from TaRDocument r where r.taDocumentGenere=?");
			ejbQuery.setParameter(1, taDocumentGenere);
			Object instance =  ejbQuery.getSingleResult();
			logger.debug("get successful");
			if(instance==null)return null;
			return (TaRDocument) instance;
		} catch (RuntimeException re) {
			logger.error("get failed", re);
			throw re;
		}
	}
	
	public Boolean exist(TaRDocument taDocumentGenere) {
		logger.debug("exist with taDocumentGenere: " + taDocumentGenere);
		try {
			if(taDocumentGenere!=null ){
			Query ejbQuery = getEntityManager().createQuery("select count(f) from TaRDocument f where f.taDocumentGenere= ?");
			ejbQuery.setParameter(1, taDocumentGenere);
			Long instance = (Long)ejbQuery.getSingleResult();
			logger.debug("get successful");
			return instance!=0;
			}
			return false;
		} catch (RuntimeException re) {
			logger.error("get failed", re);
			throw re;
		}
	}
	
	/* (non-Javadoc)
	 * @see fr.legrain.articles.dao.ILgrDAO#selectAll()
	 */
	public List<TaRDocument> selectAll() {
		logger.debug("selectAll TaRDocument");
		try {
			Query ejbQuery = getEntityManager().createQuery(JPQLQuery);
			List<TaRDocument> l = ejbQuery.getResultList();
			logger.debug("selectAll successful");
			return l;
		} catch (RuntimeException re) {
			logger.error("selectAll failed", re);
			throw re;
		}
	}
	
	
	
	
	/**
	 * Classe permettant d'obtenir les Bons de Livraison non transformés
	 * @param debut date de début des données
	 * @param fin date de fin des données
	 * @return La requête renvoyée renvoi la liste des BL non transformés
	 */
	public List<TaBonliv> findBLNonTransfos(Date debut, Date fin) {
		logger.debug("getting nombre BL non transfos");
		List<TaBonliv> result = null;
		
		
		try {
			String requete = "";

			requete = "SELECT "
				+" d"
				+" FROM TaBonliv d " 
				+" where d.dateDocument between ? and ?"
				+" and not exists (select r " +
						"from TaRDocument r " +
						" where r.taBonliv = d" +
						" and taFacture IS NOT NULL)";
			Query query = getEntityManager().createQuery(requete);
			query.setParameter(1, debut);
			query.setParameter(2, fin);
			result = query.getResultList();
			return result;
		} catch (RuntimeException re) {
			logger.error("get failed", re);
			throw re;
		}
	}
	
	/**
	 * Classe permettant d'obtenir les docs transformés en document typedoc
	 * @param typedoc le type du document
	 * @param debut date de début des données
	 * @param fin date de fin des données
	 * @return La requête renvoyée renvoi la liste des devis non transformés
	 */
	public List<Object> findDocTransfosEn(String typeDocSourceMaj ,
			String typeDocSourceMin, String typedocfinal ,Date debut, Date fin) {
		logger.debug("getting doc non transfos");
		List<Object> result = null;
		
		
		try {
			String requete = "";

			requete = "SELECT "
				+" d"
				+" FROM "+typeDocSourceMaj+" d " 
				+" where d.dateDocument between ? and ?"
				+" and exists (select r " +
						"from TaRDocument r " +
						" where r."+typeDocSourceMin+" = d" +
						" and "+typedocfinal+" IS NOT NULL)";
			Query query = getEntityManager().createQuery(requete);
			query.setParameter(1, debut);
			query.setParameter(2, fin);
			result = query.getResultList();
			return result;
		} catch (RuntimeException re) {
			logger.error("get failed", re);
			throw re;
		}
	}
	
	/**
	 * Classe permettant d'obtenir les devis transformés en document typedoc
	 * @param typedoc le type du document
	 * @param debut date de début des données
	 * @param fin date de fin des données
	 * @return La requête renvoyée renvoi la liste des devis non transformés
	 */
	public List<Object> findDocTransPlusieursFois(String typedocMaj, String typedocMin,Date debut, Date fin) {
		logger.debug("getting nombre devis non transfos");
		List<Object> result = null;
		
		
		try {
			String requete = "";

			requete = "SELECT "
				+" d"
				+" FROM "+typedocMaj+" d " 
				+" where d.dateDocument between ? and ?"
				+" and (select count(r) " +
						"from TaRDocument r " +
						" where r."+typedocMin+" = d) > 1";
			Query query = getEntityManager().createQuery(requete);
			query.setParameter(1, debut);
			query.setParameter(2, fin);
			result = query.getResultList();
			return result;
		} catch (RuntimeException re) {
			logger.error("get failed", re);
			throw re;
		}
	}
	

	
	
	public List<Object> findChiffreAffaireTotalByTypeDoc(String typedoc,Date debut, Date fin, int precision) {
		logger.debug("getting ChiffreAffaire total");
		List<Object> result = null;
		try {
			String requete = "";
			String groupBy = "";
			String select = "";
			
			if(precision==0) {
				select = "'','',cast(extract(year from d.dateDocument)as string)";
				groupBy = "'','',extract(year from d.dateDocument)";
			} else if (precision==1){
				select = "'',cast(extract(month from d.dateDocument)as string),cast(extract(year from d.dateDocument)as string)";
				groupBy = "'',extract(month from d.dateDocument),extract(year from d.dateDocument)";
			} else {
				select = "cast(extract(day from d.dateDocument)as string),cast(extract(month from d.dateDocument)as string),cast(extract(year from d.dateDocument)as string)";
				groupBy = "extract(day from d.dateDocument),extract(month from d.dateDocument),extract(year from d.dateDocument)";
			}
			
			requete = "SELECT "+select+ ", "
			+" sum(d.netHtCalc), "
			+" sum(d.netTvaCalc), "
			+" sum(d.netTtcCalc) "
			+" FROM "+typedoc+" d " 
			+" where d.dateDocument between ? and ?"
			+" group by "+groupBy; 
			Query query = getEntityManager().createQuery(requete);
			query.setParameter(1, debut);
			query.setParameter(2, fin);
			result = query.getResultList();
			return result;
		} catch (RuntimeException re) {
			logger.error("get failed", re);
			throw re;
		}
	}
	
	
}
