/**
 * TaRequeteDAO.java
 */
package fr.legrain.requete.dao;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TemporalType;

import org.apache.log4j.Logger;

import fr.legrain.gestCom.Appli.AbstractApplicationDAO;
import fr.legrain.gestCom.Appli.Const;

/**
 * @author Nicolas²
 *
 */
public class TaRequeteDAO extends AbstractApplicationDAO<TaRequete> {
	
	//private static final Log logger = LogFactory.getLog(TaRequeteDAO.class);
	static Logger logger = Logger.getLogger(TaRequeteDAO.class);
	
	private String defaultJPQLQuery = "select a from TaRequete a";
	
	public TaRequeteDAO(){
		this(null);
	}
	
	public TaRequeteDAO(EntityManager em){
		if(em!=null) {
			setEm(em);
		}
//		champIdTable=ctrlGeneraux.getID_TABLE(TaRequete.class.getSimpleName());
		initChampId(TaRequete.class);
		setJPQLQuery(defaultJPQLQuery);
		setListeChampMaj(Const.C_FICHIER_INI_CHAMPMAJ);
		initNomTable(new TaRequete());
	}

	public TaRequete refresh(TaRequete detachedInstance) {
		logger.debug("refresh instance");
		try {
			org.hibernate.Session session = (org.hibernate.Session) getEntityManager().getDelegate();
		    session.evict(detachedInstance);
		    detachedInstance = getEntityManager().find(TaRequete.class, detachedInstance.getIdRqt());
		    return detachedInstance;
		} catch (RuntimeException re) {
			logger.error("refresh failed", re);
			throw re;
		}
	}
	public void persist(TaRequete transientInstance) {
		logger.debug("persisting TaRequete instance");
		try {
			getEntityManager().persist(transientInstance);
			logger.debug("persist successful");
		} catch (RuntimeException re) {
			logger.error("persist failed", re);
			throw re;
		}
	}

	public void remove(TaRequete persistentInstance) {
		logger.debug("removing TaRequete instance");
		try {
			getEntityManager().remove(persistentInstance);
			logger.debug("remove successful");
		} catch (RuntimeException re) {
			logger.error("remove failed", re);
			throw re;
		}
	}

	public TaRequete merge(TaRequete detachedInstance) {
		logger.debug("merging TaRequete instance");
		try {
			TaRequete result = getEntityManager().merge(detachedInstance);
			logger.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			logger.error("merge failed", re);
			throw re;
		}
	}

	public TaRequete findById(int id) {
		logger.debug("getting TaRequete instance with id: " + id);
		try {
			TaRequete instance = getEntityManager().find(TaRequete.class, id);
			logger.debug("get successful");
			return instance;
		} catch (RuntimeException re) {
			logger.error("get failed", re);
			throw re;
		}
	}
	


	@Override
	public List<TaRequete> selectAll() {
		// TODO Auto-generated method stub
		logger.debug("selectAll TaRequete");
		try {
			Query ejbQuery = getEntityManager().createQuery(JPQLQuery);
			List<TaRequete> l = ejbQuery.getResultList();
			logger.debug("selectAll successful");
			return l;
		} catch (RuntimeException re) {
			logger.error("selectAll failed", re);
			throw re;
		}
	}
	
	/**
	 * Méthode permettant de vérifier qu'il n'existe pas déjà une requête
	 * avec le même nom
	 * @param libToCheck le libellé à tester
	 * @return un boolean a true si existe, sinon false
	 */
	public Boolean existThisLib(String libToCheck) {
		// TODO Auto-generated method stub
		logger.debug("existThisLib TaRequete");
		try {
			JPQLQuery = "select a from TaRequete a where a.libRqt='"+libToCheck+"'";
			Query ejbQuery = getEntityManager().createQuery(JPQLQuery);
			List<TaRequete> l = ejbQuery.getResultList();
			logger.debug("existThisLib successful");
			return l.size()!=0;
		} catch (RuntimeException re) {
			logger.error("existThisLib failed", re);
			throw re;
		}
	}
	
	/**
	 * Méthode permettant de retourner l'id d'une requête
	 * avec son nom passé en paramètre
	 * @param String nom, le nom de la requête
	 * @return l'id de la requete
	 */
	public int whatIsMyId(String nom) {
		// TODO Auto-generated method stub
		logger.debug("whatIsMyId TaRequete");
		try {
			int res = -1;
			JPQLQuery = "select a from TaRequete a where a.libRqt='"+nom+"'";
			Query ejbQuery = getEntityManager().createQuery(JPQLQuery);
			List<TaRequete> l = ejbQuery.getResultList();
			logger.debug("whatIsMyId successful");
			if(l.size()!=0){
				res = l.get(0).getIdRqt();
			}
			return res;
		} catch (RuntimeException re) {
			logger.error("whatIsMyId failed", re);
			throw re;
		}
	}
	
	/**
	 * Méthode permettant de retourner l'id d'une requête
	 * avec son nom passé en paramètre
	 * @param String nom, le nom de la requête
	 * @return l'id de la requete
	 */
	public  TaRequete whatIsMyIdFull(String nom) {
		// TODO Auto-generated method stub
		logger.debug("whatIsMyId TaRequete");
		try {
			TaRequete res = null;
			JPQLQuery = "select a from TaRequete a where a.libRqt='"+nom+"'";
			Query ejbQuery = getEntityManager().createQuery(JPQLQuery);
			List<TaRequete> l = ejbQuery.getResultList();
			logger.debug("whatIsMyId successful");
			if(l.size()!=0){
				res = l.get(0);
			}
			return res;
		} catch (RuntimeException re) {
			logger.error("whatIsMyId failed", re);
			throw re;
		}
	}
	
	
	
	
	

}
