package fr.legrain.departement.dao;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TemporalType;

import org.apache.log4j.Logger;

import fr.legrain.documents.dao.TaFacture;
import fr.legrain.gestCom.Appli.AbstractApplicationDAO;
import fr.legrain.gestCom.Appli.Const;
import fr.legrain.lib.data.ExceptLgr;


/**
 * Home object for domain model class TaDepartement.
 * @see fr.legrain.tiers.dao.TaDepartements
 * @author Hibernate Tools
 */
public class TaDepartementsDAO extends AbstractApplicationDAO<TaDepartements>{

	//private static final Log logger = LogFactory.getLog(TaDepartementDAO.class);
	static Logger logger = Logger.getLogger(TaDepartementsDAO.class);
	
	private String defaultJPQLQuery = "select a from TaDepartement a";
	
	public TaDepartementsDAO(){
		this(null);
	}
	
	public TaDepartementsDAO(EntityManager em){
		if(em!=null) {
			setEm(em);
		}
//		champIdTable=ctrlGeneraux.getID_TABLE(TaDepartement.class.getSimpleName());
		initChampId(TaDepartements.class);
		setJPQLQuery(defaultJPQLQuery);
		setListeChampMaj(Const.C_FICHIER_INI_CHAMPMAJ);
		initNomTable(new TaDepartements());
	}

	public TaDepartements refresh(TaDepartements detachedInstance) {
		logger.debug("refresh instance");
		try {
			org.hibernate.Session session = (org.hibernate.Session) getEntityManager().getDelegate();
		    session.evict(detachedInstance);
		    detachedInstance = getEntityManager().find(TaDepartements.class, detachedInstance.getNumDep());
		    return detachedInstance;
		} catch (RuntimeException re) {
			logger.error("refresh failed", re);
			throw re;
		}
	}
	public void persist(TaDepartements transientInstance) {
		logger.debug("persisting TaDepartement instance");
		try {
			getEntityManager().persist(transientInstance);
			logger.debug("persist successful");
		} catch (RuntimeException re) {
			logger.error("persist failed", re);
			throw re;
		}
	}

	public void remove(TaDepartements persistentInstance) {
		logger.debug("removing TaDepartement instance");
		try {
			getEntityManager().remove(persistentInstance);
			logger.debug("remove successful");
		} catch (RuntimeException re) {
			logger.error("remove failed", re);
			throw re;
		}
	}

	public TaDepartements merge(TaDepartements detachedInstance) {
		logger.debug("merging TaDepartement instance");
		try {
			TaDepartements result = getEntityManager().merge(detachedInstance);
			logger.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			logger.error("merge failed", re);
			throw re;
		}
	}

	public TaDepartements findById(int id) {
		logger.debug("getting TaDepartement instance with id: " + id);
		try {
			TaDepartements instance = getEntityManager().find(TaDepartements.class, id);
			logger.debug("get successful");
			return instance;
		} catch (RuntimeException re) {
			logger.error("get failed", re);
			throw re;
		}
	}
	


	@Override
	public List<TaDepartements> selectAll() {
		// TODO Auto-generated method stub
		logger.debug("selectAll TaDepartement");
		try {
			Query ejbQuery = getEntityManager().createQuery(JPQLQuery);
			List<TaDepartements> l = ejbQuery.getResultList();
			logger.debug("selectAll successful");
			return l;
		} catch (RuntimeException re) {
			logger.error("selectAll failed", re);
			throw re;
		}
	}
	
	
	public List<Object> chiffreDocParRegions(String typedoc,Date dateDeb, Date dateFin) {
		List<Object> result = new LinkedList<Object>();

		String requete=
			" select sum(f.mtHtCalc), "+
			" d.codeRegion, d.nomRegion"+
			" from "+typedoc+" f, TaDepartements d"+
			" left join f.taTiers t" +
			" left join t.taAdresse a" + 
			" where f.dateDocument between ? and ? "+	
			" and SUBSTR(a.codepostalAdresse,1,2) = d.numDep"+
			" group by d.codeRegion, d.nomRegion";
		Query query = getEntityManager().createQuery(requete);
		
		query.setParameter(1, dateDeb, TemporalType.DATE);
		query.setParameter(2, dateFin, TemporalType.DATE);
		
		result=query.getResultList();

	
		return result;
		
	}
	


}
