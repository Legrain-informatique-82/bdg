package fr.legrain.documents.dao;

// Generated Apr 7, 2009 3:27:24 PM by Hibernate Tools 3.2.0.CR1

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import fr.legrain.articles.dao.TaPrix;
import fr.legrain.articles.dao.TaUnite;
import fr.legrain.gestCom.Appli.AbstractApplicationDAO;
import fr.legrain.gestCom.Appli.Const;
import fr.legrain.gestioncapsules.dao.TaEtatStockCapsules;
import fr.legrain.lib.data.LibDate;
import fr.legrain.stocks.dao.TaEtatStock;

/**
 * Home object for domain model class TaLBonliv.
 * @see fr.legrain.documents.dao.TaLBonliv
 * @author Hibernate Tools
 */
public class TaLBonlivDAO extends AbstractApplicationDAO<TaLBonliv> {

//	private static final Log log = LogFactory.getLog(TaLBonlivDAO.class);
	static Logger logger = Logger.getLogger(TaLBonlivDAO.class);
//	private String JPQLQueryMouvDocumentSorties=
//	 "select L.taDocument.dateDocument,L.taArticle.codeArticle,sum(-1*L.qteLDocument),"+
//	 " L.u1LDocument,sum(-1*L.qte2LDocument),L.u2LDocument  "+
//	 "from TaLBonliv L "+
//	 " where L.taDocument.idDocument not in (select rdoc.taBonliv.idDocument from TaRDocument rdoc) "+
//	 " and (L.u1LDocument='' "+
//	 " or L.u1LDocument in (select u.codeUnite from TaUnite u)) "+
//	 " and L.taArticle.codeArticle is not null  "+ 
//	 " group by  L.taDocument.dateDocument,L.taArticle.codeArticle, L.u1LDocument, L.u2LDocument";

	private String defaultJPQLQuery = "select a from TaLBonliv a";
	
	public TaLBonlivDAO(){
		this(null);
	}
	
	public TaLBonlivDAO(EntityManager em){
		if(em!=null) {
			setEm(em);
		}
//		champIdTable=ctrlGeneraux.getID_TABLE(TaLBonliv.class.getSimpleName());
		initChampId(TaLBonliv.class);
		setJPQLQuery(defaultJPQLQuery);
		setListeChampMaj(Const.C_FICHIER_INI_CHAMPMAJ);
		initNomTable(new TaLBonliv());
	}
	public TaLBonliv refresh(TaLBonliv detachedInstance) {
		logger.debug("refresh instance");
		try {
			detachedInstance.setLegrain(false);
        	getEntityManager().refresh(detachedInstance);
//			org.hibernate.Session session = (org.hibernate.Session) getEntityManager().getDelegate();
//		    session.evict(detachedInstance);
//		    detachedInstance = getEntityManager().find(TaLBonliv.class, detachedInstance.getIdLDocument());
			detachedInstance.setLegrain(true);
		    return detachedInstance;
			
		} catch (RuntimeException re) {
			logger.error("refresh failed", re);
			throw re;
		}
	}
	public void persist(TaLBonliv transientInstance) {
		logger.debug("persisting TaLBonliv instance");
		try {
			getEntityManager().persist(transientInstance);
			logger.debug("persist successful");
		} catch (RuntimeException re) {
			logger.error("persist failed", re);
			throw re;
		}
	}

	public void remove(TaLBonliv persistentInstance) {
		logger.debug("removing TaLBonliv instance");
		try {
			getEntityManager().remove(persistentInstance);
			logger.debug("remove successful");
		} catch (RuntimeException re) {
			logger.error("remove failed", re);
			throw re;
		}
	}

	public TaLBonliv merge(TaLBonliv detachedInstance) {
		logger.debug("merging TaLBonliv instance");
		try {
			TaLBonliv result = getEntityManager().merge(detachedInstance);
			logger.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			logger.error("merge failed", re);
			throw re;
		}
	}

	public TaLBonliv findById(int id) {
		logger.debug("getting TaLBonliv instance with id: " + id);
		try {
			TaLBonliv instance = getEntityManager().find(TaLBonliv.class, id);
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
	public List<TaLBonliv> selectAll() {
		logger.debug("selectAll TaLBonliv");
		try {
			Query ejbQuery = getEntityManager().createQuery(JPQLQuery);
			List<TaLBonliv> l = ejbQuery.getResultList();
			logger.debug("selectAll successful");
			return l;
		} catch (RuntimeException re) {
			logger.error("selectAll failed", re);
			throw re;
		}
	}
	public List<TaEtatStock> selectSumMouvDocumentSorties(TaEtatStock criteres,Date dateDeb) {
		try {
			String JPQLQueryMouvDocument=
				 "select NEW fr.legrain.stocks.dao.TaEtatStock(a.codeArticle,'S',f.dateDocument,sum(-1*L.qteLDocument),"+
				 " L.u1LDocument,sum(-1*L.qte2LDocument),L.u2LDocument,f.codeDocument,L.libLDocument,'BonLiv' ,'8') "+
				 " from TaLBonliv L join L.taDocument f join L.taArticle a "+
				 " where f.idDocument not in " +
				 " (select rdoc.taFacture.idDocument from TaRDocument rdoc where id_bonliv is not null) ";
			 if(criteres.getExclusionQteSsUnite())
				 JPQLQueryMouvDocument+=" and  (L.u1LDocument in (select u.codeUnite from TaUnite u )or" +
			 		" L.u2LDocument in (select u.codeUnite from TaUnite u ))" ;
			 
			 JPQLQueryMouvDocument+=" and  ((L.qteLDocument is not null and L.qteLDocument >0)" +
		 		"or(L.qte2LDocument is not null and L.qte2LDocument >0)) "  ;
			 
			if(dateDeb!=null)
				JPQLQueryMouvDocument+=" and f.dateDocument > '"+LibDate.dateToStringSql(dateDeb)+"'";

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
			 JPQLQueryMouvDocument+=" group by f.dateDocument,a.codeArticle, L.u1LDocument, L.u2LDocument,f.codeDocument,L.libLDocument";				
			
		    //clause having
		    if(criteres.getQte1Stock()!=null && !criteres.getQte1Stock().equals("")){
		    	if(!JPQLQueryMouvDocument.contains(" having "))
		    		JPQLQueryMouvDocument+=" having sum(-1*L.qteLDocument) >= "+criteres.getQte1Stock();
		    	else
		    		JPQLQueryMouvDocument+=" and sum(-1*L.qteLDocument) >= "+criteres.getQte1Stock();
		    }
		    if(criteres.getQte2Stock()!=null && !criteres.getQte2Stock().equals("")){
		    	if(!JPQLQueryMouvDocument.contains(" having "))
		    		JPQLQueryMouvDocument+=" having sum(-1*L.qte2LDocument) >= "+criteres.getQte2Stock();
		    	else
		    		JPQLQueryMouvDocument+=" and sum(-1*L.qte2LDocument) >= "+criteres.getQte2Stock();		    	
		    } 

			Query ejbQuery = getEntityManager().createQuery(JPQLQueryMouvDocument);
		    List<TaEtatStock> l = ejbQuery.getResultList();
		    for (TaEtatStock taEtatStock : l) {
				if(taEtatStock.getQte1Stock()!=null && taEtatStock.getQte1Stock().signum()==0)taEtatStock.setUn1Stock(null);
				if(taEtatStock.getQte2Stock()!=null && taEtatStock.getQte2Stock().signum()==0)taEtatStock.setUn2Stock(null);
			}
			return l;
		} catch (RuntimeException re) {
			logger.error("selectSumMouvDocumentSorties failed", re);
			throw re;
		}
	}
	public List<TaEtatStock> selectSumMouvDocumentEntrees(TaEtatStock criteres,Date dateDeb) {
		try {
			String JPQLQueryMouvDocument=
				 "select NEW fr.legrain.stocks.dao.TaEtatStock(a.codeArticle,'E',f.dateDocument,sum(-1*L.qteLDocument),"+
				 " L.u1LDocument,sum(-1*L.qte2LDocument),L.u2LDocument,f.codeDocument,L.libLDocument,'BonLiv','5' ) "+
				 " from TaLBonliv L join L.taDocument f join L.taArticle a "+
				 " where f.idDocument not in " +
				 " (select rdoc.taFacture.idDocument from TaRDocument rdoc where id_bonliv is not null) ";
			 if(criteres.getExclusionQteSsUnite())
				 JPQLQueryMouvDocument+=" and  (L.u1LDocument in (select u.codeUnite from TaUnite u )or" +
			 		" L.u2LDocument in (select u.codeUnite from TaUnite u ))" ;
			 
			 JPQLQueryMouvDocument+=" and  ((L.qteLDocument is not null and L.qteLDocument <0)" +
		 		"or(L.qte2LDocument is not null and L.qte2LDocument <0)) "  ;
			 
			if(dateDeb!=null)
				JPQLQueryMouvDocument+=" and f.dateDocument > '"+LibDate.dateToStringSql(dateDeb)+"'";

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
			 JPQLQueryMouvDocument+=" group by f.dateDocument,a.codeArticle, L.u1LDocument, L.u2LDocument,f.codeDocument,L.libLDocument";				
			
		    //clause having
		    if(criteres.getQte1Stock()!=null && !criteres.getQte1Stock().equals("")){
		    	if(!JPQLQueryMouvDocument.contains(" having "))
		    		JPQLQueryMouvDocument+=" having sum(-1*L.qteLDocument) >= "+criteres.getQte1Stock();
		    	else
		    		JPQLQueryMouvDocument+=" and sum(-1*L.qteLDocument) >= "+criteres.getQte1Stock();
		    }
		    if(criteres.getQte2Stock()!=null && !criteres.getQte2Stock().equals("")){
		    	if(!JPQLQueryMouvDocument.contains(" having "))
		    		JPQLQueryMouvDocument+=" having sum(-1*L.qte2LDocument) >= "+criteres.getQte2Stock();
		    	else
		    		JPQLQueryMouvDocument+=" and sum(-1*L.qte2LDocument) >= "+criteres.getQte2Stock();		    	
		    } 

			Query ejbQuery = getEntityManager().createQuery(JPQLQueryMouvDocument);
		    List<TaEtatStock> l = ejbQuery.getResultList();
		    for (TaEtatStock taEtatStock : l) {
				if(taEtatStock.getQte1Stock()!=null && taEtatStock.getQte1Stock().signum()==0)taEtatStock.setUn1Stock(null);
				if(taEtatStock.getQte2Stock()!=null && taEtatStock.getQte2Stock().signum()==0)taEtatStock.setUn2Stock(null);
			}
			return l;
		} catch (RuntimeException re) {
			logger.error("selectSumMouvDocumentSorties failed", re);
			throw re;
		}
	}
	
//	public List<TaEtatStockCapsules> selectSumMouvDocumentCapsulesSorties(TaEtatStockCapsules criteres,Date dateDeb) {
//		try {
//			String JPQLQueryMouvDocument=
//				 "select NEW fr.legrain.gestioncapsules.dao.TaEtatStockCapsules(L.codeTitreTransport,'S',f.dateDocument,sum(-1*L.qteLDocument),"+
//				 " L.u1LDocument,sum(-1*L.qte2LDocument),L.u2LDocument,f.codeDocument,L.libLDocument,'BonLiv' ,'8') "+
//				 " from TaLBonliv L join L.taDocument f "+
//				 " where f.idDocument not in " +
//				 " (select rdoc.taFacture.idDocument from TaRDocument rdoc where id_bonliv is not null) ";
//			 if(criteres.getExclusionQteSsUnite())
//				 JPQLQueryMouvDocument+=" and  (L.u1LDocument in (select u.codeUnite from TaUnite u )or" +
//			 		" L.u2LDocument in (select u.codeUnite from TaUnite u ))" ;
//			 
//			 JPQLQueryMouvDocument+=" and  ((L.qteLDocument is not null and L.qteLDocument >0)" +
//		 		"or(L.qte2LDocument is not null and L.qte2LDocument >0)) "  ;
//			 
//			if(dateDeb!=null)
//				JPQLQueryMouvDocument+=" and f.dateDocument > '"+LibDate.dateToStringSql(dateDeb)+"'";
//
//			//Clause where
//			if(criteres.getCodeTitreTransport()!=null && !criteres.getCodeTitreTransport().equals(""))
//				JPQLQueryMouvDocument+=" and L.codeTitreTransport like '"+criteres.getCodeTitreTransport()+"'";
//			if(criteres.getDateStock()!=null)
//				JPQLQueryMouvDocument+=" and f.dateDocument <= '"+LibDate.dateToStringSql(criteres.getDateStock())+"'";
//			if(criteres.getUn1Stock()!=null && !criteres.getUn1Stock().equals(""))
//				JPQLQueryMouvDocument+=" and L.u1LDocument like '"+criteres.getUn1Stock()+"'";
//			 if(criteres.getUn2Stock()!=null && !criteres.getUn2Stock().equals(""))
//				 JPQLQueryMouvDocument+=" and L.u2LDocument like '"+criteres.getUn2Stock()+"'";
//			//Clause group by
//			 JPQLQueryMouvDocument+=" group by f.dateDocument,L.codeTitreTransport, L.u1LDocument, L.u2LDocument,f.codeDocument,L.libLDocument";				
//			
//		    //clause having
//		    if(criteres.getQte1Stock()!=null && !criteres.getQte1Stock().equals("")){
//		    	if(!JPQLQueryMouvDocument.contains(" having "))
//		    		JPQLQueryMouvDocument+=" having sum(-1*L.qteLDocument) >= "+criteres.getQte1Stock();
//		    	else
//		    		JPQLQueryMouvDocument+=" and sum(-1*L.qteLDocument) >= "+criteres.getQte1Stock();
//		    }
//		    if(criteres.getQte2Stock()!=null && !criteres.getQte2Stock().equals("")){
//		    	if(!JPQLQueryMouvDocument.contains(" having "))
//		    		JPQLQueryMouvDocument+=" having sum(-1*L.qte2LDocument) >= "+criteres.getQte2Stock();
//		    	else
//		    		JPQLQueryMouvDocument+=" and sum(-1*L.qte2LDocument) >= "+criteres.getQte2Stock();		    	
//		    } 
//
//			Query ejbQuery = getEntityManager().createQuery(JPQLQueryMouvDocument);
//		    List<TaEtatStockCapsules> l = ejbQuery.getResultList();
//		    for (TaEtatStockCapsules taEtatStock : l) {
//				if(taEtatStock.getQte1Stock()!=null && taEtatStock.getQte1Stock().signum()==0)taEtatStock.setUn1Stock(null);
//				if(taEtatStock.getQte2Stock()!=null && taEtatStock.getQte2Stock().signum()==0)taEtatStock.setUn2Stock(null);
//			}
//			return l;
//		} catch (RuntimeException re) {
//			logger.error("selectSumMouvDocumentSorties failed", re);
//			throw re;
//		}
//	}
	
//	public List<TaEtatStockCapsules> selectSumMouvDocumentCapsulesEntrees(TaEtatStockCapsules criteres,Date dateDeb) {
//		try {
//			String JPQLQueryMouvDocument=
//				 "select NEW fr.legrain.gestioncapsules.dao.TaEtatStockCapsules(L.codeTitreTransport,'E',f.dateDocument,sum(-1*L.qteLDocument),"+
//				 " L.u1LDocument,sum(-1*L.qte2LDocument),L.u2LDocument,f.codeDocument,L.libLDocument,'BonLiv','5' ) "+
//				 " from TaLBonliv L join L.taDocument f "+
//				 " where f.idDocument not in " +
//				 " (select rdoc.taFacture.idDocument from TaRDocument rdoc where id_bonliv is not null) ";
//			 if(criteres.getExclusionQteSsUnite())
//				 JPQLQueryMouvDocument+=" and  (L.u1LDocument in (select u.codeUnite from TaUnite u )or" +
//			 		" L.u2LDocument in (select u.codeUnite from TaUnite u ))" ;
//			 
//			 JPQLQueryMouvDocument+=" and  ((L.qteLDocument is not null and L.qteLDocument <0)" +
//		 		"or(L.qte2LDocument is not null and L.qte2LDocument <0)) "  ;
//			 
//			if(dateDeb!=null)
//				JPQLQueryMouvDocument+=" and f.dateDocument > '"+LibDate.dateToStringSql(dateDeb)+"'";
//
//			//Clause where
//			if(criteres.getCodeTitreTransport()!=null && !criteres.getCodeTitreTransport().equals(""))
//				JPQLQueryMouvDocument+=" and L.codeTitreTransport like '"+criteres.getCodeTitreTransport()+"'";
//			if(criteres.getDateStock()!=null)
//				JPQLQueryMouvDocument+=" and f.dateDocument <= '"+LibDate.dateToStringSql(criteres.getDateStock())+"'";
//			if(criteres.getUn1Stock()!=null && !criteres.getUn1Stock().equals(""))
//				JPQLQueryMouvDocument+=" and L.u1LDocument like '"+criteres.getUn1Stock()+"'";
//			 if(criteres.getUn2Stock()!=null && !criteres.getUn2Stock().equals(""))
//				 JPQLQueryMouvDocument+=" and L.u2LDocument like '"+criteres.getUn2Stock()+"'";
//			//Clause group by
//			 JPQLQueryMouvDocument+=" group by f.dateDocument,L.codeTitreTransport, L.u1LDocument, L.u2LDocument,f.codeDocument,L.libLDocument";				
//			
//		    //clause having
//		    if(criteres.getQte1Stock()!=null && !criteres.getQte1Stock().equals("")){
//		    	if(!JPQLQueryMouvDocument.contains(" having "))
//		    		JPQLQueryMouvDocument+=" having sum(-1*L.qteLDocument) >= "+criteres.getQte1Stock();
//		    	else
//		    		JPQLQueryMouvDocument+=" and sum(-1*L.qteLDocument) >= "+criteres.getQte1Stock();
//		    }
//		    if(criteres.getQte2Stock()!=null && !criteres.getQte2Stock().equals("")){
//		    	if(!JPQLQueryMouvDocument.contains(" having "))
//		    		JPQLQueryMouvDocument+=" having sum(-1*L.qte2LDocument) >= "+criteres.getQte2Stock();
//		    	else
//		    		JPQLQueryMouvDocument+=" and sum(-1*L.qte2LDocument) >= "+criteres.getQte2Stock();		    	
//		    } 
//
//			Query ejbQuery = getEntityManager().createQuery(JPQLQueryMouvDocument);
//		    List<TaEtatStockCapsules> l = ejbQuery.getResultList();
//		    for (TaEtatStockCapsules taEtatStock : l) {
//				if(taEtatStock.getQte1Stock()!=null && taEtatStock.getQte1Stock().signum()==0)taEtatStock.setUn1Stock(null);
//				if(taEtatStock.getQte2Stock()!=null && taEtatStock.getQte2Stock().signum()==0)taEtatStock.setUn2Stock(null);
//			}
//			return l;
//		} catch (RuntimeException re) {
//			logger.error("selectSumMouvDocumentSorties failed", re);
//			throw re;
//		}
//	}
}
