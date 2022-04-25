package fr.legrain.stocks.dao;

// Generated 4 juin 2009 10:39:45 by Hibernate Tools 3.2.4.GA

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import fr.legrain.gestCom.Appli.AbstractApplicationDAO;
import fr.legrain.gestCom.Appli.Const;

/**
 * Home object for domain model class TaReportStock.
 * @see fr.legrain.stocks.dao.TaReportStock
 * @author Hibernate Tools
 */
public class TaReportStockDAO extends AbstractApplicationDAO<TaReportStock> {

	private static final Log logger = LogFactory.getLog(TaReportStockDAO.class);

	private String defaultJPQLQueryMaxReport = "select max(u.dateFinReportStock) from TaReportStock u";
	private String defaultJPQLQuery = "select u from TaReportStock u";
	
	private String JPQLQueryReportStock="select R.taArticle.codeArticle, "+
    " R.dateDebReportStock,R.dateFinReportStock, sum(R.qte1ReportStock), R.unite1ReportStock ," +
    " sum(R.qte2ReportStock), R.unite2ReportStock "+
    " from TaReportStock R where R.taArticle is not null  and " +
    "(R.qte1ReportStock is not null or R.qte2ReportStock is not null) "
    +" group by R.taArticle.codeArticle, R.dateDebReportStock,R.dateFinReportStock," +
    		"R.unite1ReportStock ,R.unite2ReportStock";


	public TaReportStockDAO(){
		this(null);
	}
	
	public TaReportStockDAO(EntityManager em){
		if(em!=null) {
			setEm(em);
		}
//		champIdTable=ctrlGeneraux.getID_TABLE(TaReportStock.class.getSimpleName());
		initChampId(TaReportStock.class);
		setJPQLQuery(defaultJPQLQuery);
		setListeChampMaj(Const.C_FICHIER_INI_CHAMPMAJ);
		initNomTable(new TaReportStock());
	}
	
	public void persist(TaReportStock transientInstance) {
		logger.debug("persisting TaReportStock instance");
		try {
			getEntityManager().persist(transientInstance);
			logger.debug("persist successful");
		} catch (RuntimeException re) {
			logger.error("persist failed", re);
			throw re;
		}
	}

	public void remove(TaReportStock persistentInstance) {
		logger.debug("removing TaReportStock instance");
		try {
			getEntityManager().remove(persistentInstance);
			logger.debug("remove successful");
		} catch (RuntimeException re) {
			logger.error("remove failed", re);
			throw re;
		}
	}

	public TaReportStock merge(TaReportStock detachedInstance) {
		logger.debug("merging TaReportStock instance");
		try {
			TaReportStock result = getEntityManager().merge(detachedInstance);
			logger.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			logger.error("merge failed", re);
			throw re;
		}
	}

	public TaReportStock findById(int id) {
		logger.debug("getting TaReportStock instance with id: " + id);
		try {
			TaReportStock instance = getEntityManager()
					.find(TaReportStock.class, id);
			logger.debug("get successful");
			return instance;
		} catch (RuntimeException re) {
			logger.error("get failed", re);
			throw re;
		}
	}

	@Override
	public List<TaReportStock> selectAll() {
		logger.debug("selectAll TaReportStock");
		try {
			Query ejbQuery = getEntityManager().createQuery(JPQLQuery);
			List<TaReportStock> l = ejbQuery.getResultList();
			logger.debug("selectAll successful");
			return l;
		} catch (RuntimeException re) {
			logger.error("selectAll failed", re);
			throw re;
		}
	}
	

	public java.util.Date recupDerniereDateReportStock() {
		logger.debug("recupDerniereDateReportStock");
		Date dateDeb=new Date(0);
		try {
			Query ejbQuery = getEntityManager().createQuery(defaultJPQLQueryMaxReport);
			List<java.util.Date> l = ejbQuery.getResultList();
			logger.debug("recupDerniereDateReportStock successful");			
			if(l!=null && l.size()>0)
				dateDeb= l.get(0);
			if (dateDeb==null)dateDeb = new Date(0);
			return dateDeb;
		} catch (RuntimeException re) {
			logger.error("recupDerniereDateReportStock failed", re);
			throw re;
		}
	}
	

	public void deleteAll() {
		logger.debug("deleteAll");
		try {
			List<TaReportStock> liste =selectAll();
			for (TaReportStock taReportStock : liste) {
				if(taReportStock!=null)
					remove(taReportStock);
			}
			logger.debug("deleteAll successful");			
		} catch (RuntimeException re) {
			logger.error("deleteAll failed", re);
			throw re;
		}
	}
	
	public TaReportStock refresh(TaReportStock detachedInstance) {
		logger.debug("refresh instance");
		try {
////			getEntityManager().refresh(detachedInstance);
////			logger.debug("refresh successful");
//			org.hibernate.Session session = (org.hibernate.Session) getEntityManager().getDelegate();
//		    session.evict(detachedInstance);
//		    detachedInstance = getEntityManager().find(TaReportStock.class, detachedInstance.getIdReportStock());
		    return detachedInstance;
		} catch (RuntimeException re) {
			logger.error("refresh failed", re);
			throw re;
		}
	}
	public String getDefaultJPQLQueryMaxReport() {
		return defaultJPQLQueryMaxReport;
	}
	
	public List<TaEtatStock> selectReportStocks(TaEtatStock criteres) {
		try {
			if(criteres.getDateStock()!=null && 
					criteres.getDateStock().before(recupDerniereDateReportStock()))
				return null;
			else
			JPQLQueryReportStock="select NEW fr.legrain.stocks.dao.TaEtatStock(a.codeArticle,'R', "+
		    " R.dateFinReportStock, sum(R.qte1ReportStock), R.unite1ReportStock ," +
		    " sum(R.qte2ReportStock), R.unite2ReportStock ,'','Report','Report','1') "+
		    " from TaReportStock R join R.taArticle a where  " +
		    " (R.qte1ReportStock is not null or R.qte2ReportStock is not null) ";
			
			if(criteres.getExclusionQteSsUnite()){
				JPQLQueryReportStock+=" and (R.unite1ReportStock in (select u1.codeUnite from TaUnite u1) or " +
				 		" R.unite2ReportStock in (select u2.codeUnite from TaUnite u2))";
				}
			
			//Clause where
			if(criteres.getCodeArticle()!=null && !criteres.getCodeArticle().equals(""))
				JPQLQueryReportStock+=" and a.codeArticle like '"+criteres.getCodeArticle()+"'";
//			if(criteres.getDateStock()!=null)
//				JPQLQueryReportStock+=" and R.dateFinReportStock >= '"+LibDate.dateToStringSql(criteres.getDateStock())+"'";
			if(criteres.getUn1Stock()!=null && !criteres.getUn1Stock().equals(""))
				JPQLQueryReportStock+=" and R.unite1ReportStock like '"+criteres.getUn1Stock()+"'";
			 if(criteres.getUn2Stock()!=null && !criteres.getUn2Stock().equals(""))
				 JPQLQueryReportStock+=" and R.unite2ReportStock like '"+criteres.getUn2Stock()+"'";
			 
			//Clause group by
			 JPQLQueryReportStock+=" group by a.codeArticle, R.dateFinReportStock,R.unite1ReportStock ,R.unite2ReportStock";	
			
		    //clause having
		    if(criteres.getQte1Stock()!=null && !criteres.getQte1Stock().equals("")){
		    	if(!JPQLQueryReportStock.contains(" having "))
		    		JPQLQueryReportStock+=" having sum(R.qte1ReportStock) >= "+criteres.getQte1Stock();
		    	else
		    	JPQLQueryReportStock+=" and sum(R.qte1ReportStock) >= "+criteres.getQte1Stock();
		    }
		    if(criteres.getQte2Stock()!=null && !criteres.getQte2Stock().equals("")){
		    	if(!JPQLQueryReportStock.contains(" having "))
		    		JPQLQueryReportStock+=" having sum(R.qte2ReportStock) >= "+criteres.getQte2Stock();
		    	else
		    	JPQLQueryReportStock+=" and sum(R.qte2ReportStock) >= "+criteres.getQte2Stock();		    	
		    } 
			
			Query ejbQuery = getEntityManager().createQuery(JPQLQueryReportStock);
		    List<TaEtatStock> l = ejbQuery.getResultList();
//		    for (TaEtatStock taEtatStock : l) {
//				if(taEtatStock.getQte1Stock()!=null && taEtatStock.getQte1Stock().signum()==0)taEtatStock.setUn1Stock(null);
//				if(taEtatStock.getQte2Stock()!=null && taEtatStock.getQte2Stock().signum()==0)taEtatStock.setUn2Stock(null);
//			}
			return l;
		} catch (RuntimeException re) {
			logger.error("selectReportStocks failed", re);
			throw re;
		}
	}

}
