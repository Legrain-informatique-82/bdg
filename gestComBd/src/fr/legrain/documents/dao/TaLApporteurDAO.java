package fr.legrain.documents.dao;

// Generated Apr 7, 2009 3:27:24 PM by Hibernate Tools 3.2.0.CR1

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import fr.legrain.articles.dao.TaPrix;
import fr.legrain.articles.dao.TaUnite;
import fr.legrain.gestCom.Appli.AbstractApplicationDAO;
import fr.legrain.gestCom.Appli.Const;
import fr.legrain.lib.data.LibDate;
import fr.legrain.stocks.dao.TaEtatStock;

/**
 * Home object for domain model class TaLApporteur.
 * @see fr.legrain.documents.dao.TaLApporteur
 * @author Hibernate Tools
 */
public class TaLApporteurDAO extends AbstractApplicationDAO<TaLApporteur> {

//	private static final Log log = LogFactory.getLog(TaLApporteurDAO.class);
	static Logger logger = Logger.getLogger(TaLApporteurDAO.class);
	boolean  raffraichir=false;
	private String defaultJPQLQuery = "select a from TaLApporteur a";
	
	public TaLApporteurDAO(){
		this(null);
	}
	
	public TaLApporteurDAO(EntityManager em){
		if(em!=null) {
			setEm(em);
		}
//		champIdTable=ctrlGeneraux.getID_TABLE(TaLApporteur.class.getSimpleName());
		initChampId(TaLApporteur.class);
		setJPQLQuery(defaultJPQLQuery);
		setListeChampMaj(Const.C_FICHIER_INI_CHAMPMAJ);
		initNomTable(new TaLApporteur());
	}
	public TaLApporteur refresh(TaLApporteur detachedInstance) {
		logger.debug("refresh instance");
		try {
			detachedInstance.setLegrain(false);
        	getEntityManager().refresh(detachedInstance);
//			org.hibernate.Session session = (org.hibernate.Session) getEntityManager().getDelegate();
//		    session.evict(detachedInstance);
//		    detachedInstance = getEntityManager().find(TaLApporteur.class, detachedInstance.getIdLDocument());
			detachedInstance.setLegrain(true);
		    return detachedInstance;
			
		} catch (RuntimeException re) {
			logger.error("refresh failed", re);
			throw re;
		}
	}
	public void persist(TaLApporteur transientInstance) {
		logger.debug("persisting TaLApporteur instance");
		try {
			getEntityManager().persist(transientInstance);
			logger.debug("persist successful");
		} catch (RuntimeException re) {
			logger.error("persist failed", re);
			throw re;
		}
	}

	public void remove(TaLApporteur persistentInstance) {
		logger.debug("removing TaLApporteur instance");
		try {
			getEntityManager().remove(persistentInstance);
			logger.debug("remove successful");
		} catch (RuntimeException re) {
			logger.error("remove failed", re);
			throw re;
		}
	}

	public TaLApporteur merge(TaLApporteur detachedInstance) {
		logger.debug("merging TaLApporteur instance");
		try {
			TaLApporteur result = getEntityManager().merge(detachedInstance);
			logger.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			logger.error("merge failed", re);
			throw re;
		}
	}

	public TaLApporteur findById(int id) {
		logger.debug("getting TaLApporteur instance with id: " + id);
		try {
			TaLApporteur instance = getEntityManager().find(TaLApporteur.class, id);
			logger.debug("get successful");
			return instance;
		} catch (RuntimeException re) {
			logger.error("get failed", re);
			throw re;
		}
	}

	public void annuler(TaLApporteur persistentInstance) throws Exception{
		persistentInstance.setLegrain(false);
		super.annuler(persistentInstance);
	}
	
	/* (non-Javadoc)
	 * @see fr.legrain.articles.dao.ILgrDAO#selectAll()
	 */
	public List<TaLApporteur> selectAll() {
		logger.debug("selectAll TaLApporteur");
		try {
			Query ejbQuery = getEntityManager().createQuery(JPQLQuery);
			List<TaLApporteur> l = ejbQuery.getResultList();
			logger.debug("selectAll successful");
			return l;
		} catch (RuntimeException re) {
			logger.error("selectAll failed", re);
			throw re;
		}
	}

	public List<TaEtatStock> selectSumMouvDocumentStocksEntrees(
			TaEtatStock criteres, Date dateDeb) {
		try {
			String est="";
			String JPQLQueryMouvDocument=
				 "select NEW fr.legrain.stocks.dao.TaEtatStock(a.codeArticle,'E',f.dateDocument,sum(L.qteLDocument),"+
				 " L.u1LDocument,sum(L.qte2LDocument),L.u2LDocument ,f.codeDocument,L.libLDocument,'Apporteur','10' ) "+
				 " from TaLApporteur L join L.taDocument f join L.taArticle a "+
				 " where  ";
			 if(criteres.getExclusionQteSsUnite()){
				 JPQLQueryMouvDocument+=est+" (L.u1LDocument in (select u.codeUnite from TaUnite u )or" +
			 		" L.u2LDocument in (select u.codeUnite from TaUnite u ))" ;
				 est="and";
			 }
			if(dateDeb!=null){
				JPQLQueryMouvDocument+=est+" f.dateDocument > '"+LibDate.dateToStringSql(dateDeb)+"'";
				est="and";
			}
			
			 JPQLQueryMouvDocument+=est+" ((L.qteLDocument is not null and L.qteLDocument >0)" +
		 		"or(L.qte2LDocument is not null and L.qte2LDocument >0)) "  ;
			 est="and";
			 
			//Clause where
			if(criteres.getCodeArticle()!=null && !criteres.getCodeArticle().equals(""))
				JPQLQueryMouvDocument+=" and a.codeArticle like '"+criteres.getCodeArticle()+"'";
			if(criteres.getDateStock()!=null)
				JPQLQueryMouvDocument+=" and f.dateDocument <= '"+LibDate.dateToStringSql(criteres.getDateStock())+"'";
			if(criteres.getUn1Stock()!=null && !criteres.getUn1Stock().equals(""))
				JPQLQueryMouvDocument+=" and L.u1LDocument like '"+criteres.getUn1Stock()+"'";
			 if(criteres.getUn2Stock()!=null && !criteres.getUn2Stock().equals(""))
				 JPQLQueryMouvDocument+=" and L.u2LDocument like '"+criteres.getUn2Stock()+"'";
			//Clause group by
			 JPQLQueryMouvDocument+=" group by  f.dateDocument,a.codeArticle, L.u1LDocument, L.u2LDocument,f.codeDocument,L.libLDocument";			
			
		    //clause having
		    if(criteres.getQte1Stock()!=null && !criteres.getQte1Stock().equals("")){
		    	if(!JPQLQueryMouvDocument.contains(" having "))
		    		JPQLQueryMouvDocument+=" having sum(L.qteLDocument) >= "+criteres.getQte1Stock();
		    	else
		    		JPQLQueryMouvDocument+=" and sum(L.qteLDocument) >= "+criteres.getQte1Stock();
		    }
		    if(criteres.getQte2Stock()!=null && !criteres.getQte2Stock().equals("")){
		    	if(!JPQLQueryMouvDocument.contains(" having "))
		    		JPQLQueryMouvDocument+=" having sum(L.qte2LDocument) >= "+criteres.getQte2Stock();
		    	else
		    		JPQLQueryMouvDocument+=" and sum(L.qte2LDocument) >= "+criteres.getQte2Stock();		    	
		    } 

			Query ejbQuery = getEntityManager().createQuery(JPQLQueryMouvDocument);
		    List<TaEtatStock> l = ejbQuery.getResultList();
		    for (TaEtatStock taEtatStock : l) {
				if(taEtatStock.getQte1Stock()!=null && taEtatStock.getQte1Stock().signum()==0)taEtatStock.setUn1Stock(null);
				if(taEtatStock.getQte2Stock()!=null && taEtatStock.getQte2Stock().signum()==0)taEtatStock.setUn2Stock(null);
			}
			return l;
		} catch (RuntimeException re) {
			logger.error("selectSumMouvDocumentEntrees failed", re);
			throw re;
		}
	}

	public List<TaEtatStock> selectSumMouvDocumentStocksSorties(
			TaEtatStock criteres, Date dateDeb) {
		try {
			String est="";
			String JPQLQueryMouvDocument=
				 "select NEW fr.legrain.stocks.dao.TaEtatStock(a.codeArticle,'S',f.dateDocument,sum(L.qteLDocument),"+
				 " L.u1LDocument,sum(L.qte2LDocument),L.u2LDocument ,f.codeDocument,L.libLDocument,'Apporteur' ,'11') "+
				 " from TaLApporteur L join L.taDocument f join L.taArticle a "+
				 " where  ";
			 if(criteres.getExclusionQteSsUnite()){
				 JPQLQueryMouvDocument+=est+" (L.u1LDocument in (select u.codeUnite from TaUnite u )or" +
			 		" L.u2LDocument in (select u.codeUnite from TaUnite u ))" ;
				 est="and";
			 }
			if(dateDeb!=null){
				JPQLQueryMouvDocument+=est+" f.dateDocument > '"+LibDate.dateToStringSql(dateDeb)+"'";
				est="and";
			}
			
			 JPQLQueryMouvDocument+=est+"  ((L.qteLDocument is not null and L.qteLDocument <0)" +
		 		"or(L.qte2LDocument is not null and L.qte2LDocument <0)) "  ;
			 est="and";
			 
			//Clause where
			if(criteres.getCodeArticle()!=null && !criteres.getCodeArticle().equals(""))
				JPQLQueryMouvDocument+=" and a.codeArticle like '"+criteres.getCodeArticle()+"'";
			if(criteres.getDateStock()!=null)
				JPQLQueryMouvDocument+=" and f.dateDocument <= '"+LibDate.dateToStringSql(criteres.getDateStock())+"'";
			if(criteres.getUn1Stock()!=null && !criteres.getUn1Stock().equals(""))
				JPQLQueryMouvDocument+=" and L.u1LDocument like '"+criteres.getUn1Stock()+"'";
			 if(criteres.getUn2Stock()!=null && !criteres.getUn2Stock().equals(""))
				 JPQLQueryMouvDocument+=" and L.u2LDocument like '"+criteres.getUn2Stock()+"'";
			//Clause group by
			 JPQLQueryMouvDocument+=" group by  f.dateDocument,a.codeArticle, L.u1LDocument, L.u2LDocument,f.codeDocument,L.libLDocument";			
			
		    //clause having
		    if(criteres.getQte1Stock()!=null && !criteres.getQte1Stock().equals("")){
		    	if(!JPQLQueryMouvDocument.contains(" having "))
		    		JPQLQueryMouvDocument+=" having sum(L.qteLDocument) >= "+criteres.getQte1Stock();
		    	else
		    		JPQLQueryMouvDocument+=" and sum(L.qteLDocument) >= "+criteres.getQte1Stock();
		    }
		    if(criteres.getQte2Stock()!=null && !criteres.getQte2Stock().equals("")){
		    	if(!JPQLQueryMouvDocument.contains(" having "))
		    		JPQLQueryMouvDocument+=" having sum(L.qte2LDocument) >= "+criteres.getQte2Stock();
		    	else
		    		JPQLQueryMouvDocument+=" and sum(L.qte2LDocument) >= "+criteres.getQte2Stock();		    	
		    } 

			Query ejbQuery = getEntityManager().createQuery(JPQLQueryMouvDocument);
		    List<TaEtatStock> l = ejbQuery.getResultList();
		    for (TaEtatStock taEtatStock : l) {
				if(taEtatStock.getQte1Stock()!=null && taEtatStock.getQte1Stock().signum()==0)taEtatStock.setUn1Stock(null);
				if(taEtatStock.getQte2Stock()!=null && taEtatStock.getQte2Stock().signum()==0)taEtatStock.setUn2Stock(null);
			}
			return l;
		} catch (RuntimeException re) {
			logger.error("selectSumMouvDocumentEntrees failed", re);
			throw re;
		}
	}
}
