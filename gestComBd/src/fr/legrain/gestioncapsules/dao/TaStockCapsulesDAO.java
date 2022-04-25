package fr.legrain.gestioncapsules.dao;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import fr.legrain.gestCom.Appli.AbstractApplicationDAO;
import fr.legrain.gestCom.Appli.Const;
import fr.legrain.lib.data.LibDate;

/**
 * Home object for domain model class TaStock.
 * @see fr.legrain.stocks.dao.TaStock
 * @author Hibernate Tools
 */
public class TaStockCapsulesDAO  extends AbstractApplicationDAO<TaStockCapsules> {

	private static final Log logger = LogFactory.getLog(TaStockCapsulesDAO.class);//

	private String defaultJPQLQuery = "select u from TaStockCapsules u";
	
//	private String JPQLQueryMouvEntrees="select  S.taArticle,S.mouvStock, "+
//    "S.dateStock, sum(S.qte1Stock), S.un1Stock , sum(S.qte2Stock), S.un2Stock "+
//    "from TaStock S where S.taArticle is not null and upper(S.mouvStock) = 'E' and (S.qte1Stock is not null or S.qte2Stock is not null) "
//    +"group by S.taArticle.codeArticle,S.mouvStock, S.dateStock,S.un1Stock ,S.un2Stock";
//	
//	private String JPQLQueryMouvSorties="select S.taArticle,S.mouvStock, "+
//    "S.dateStock, sum(-1*S.qte1Stock), S.un1Stock , sum(-1*S.qte2Stock), S.un2Stock "+
//    "from TaStock S where S.taArticle is not null and upper(S.mouvStock) = 'S' and (S.qte1Stock is not null or S.qte2Stock is not null) "
//    +"group by S.taArticle,S.mouvStock, S.dateStock,S.un1Stock ,S.un2Stock";
	
	public TaStockCapsulesDAO(){
		this(null);
	}
	
	public TaStockCapsulesDAO(EntityManager em){
		if(em!=null) {
			setEm(em);
		}
//		champIdTable=ctrlGeneraux.getID_TABLE(TaStock.class.getSimpleName());
		initChampId(TaStockCapsules.class);
		setJPQLQuery(defaultJPQLQuery);
		setListeChampMaj(Const.C_FICHIER_INI_CHAMPMAJ);
		initNomTable(new TaStockCapsules());
	}
	public void persist(TaStockCapsules transientInstance) {
		logger.debug("persisting TaStockCapsules instance");
		try {
			getEntityManager().persist(transientInstance);
			logger.debug("persist successful");
		} catch (RuntimeException re) {
			logger.error("persist failed", re);
			throw re;
		}
	}

	public void remove(TaStockCapsules persistentInstance) {
		logger.debug("removing TaStockCapsules instance");
		try {
			getEntityManager().remove(persistentInstance);
			logger.debug("remove successful");
		} catch (RuntimeException re) {
			logger.error("remove failed", re);
			throw re;
		}
	}

	public TaStockCapsules merge(TaStockCapsules detachedInstance) {
		logger.debug("merging TaStockCapsules instance");
		try {
			TaStockCapsules result = getEntityManager().merge(detachedInstance);
			logger.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			logger.error("merge failed", re);
			throw re;
		}
	}

	public TaStockCapsules findById(int id) {
		logger.debug("getting TaStockCapsules instance with id: " + id);
		try {
			TaStockCapsules instance = getEntityManager().find(TaStockCapsules.class, id);
			logger.debug("get successful");
			return instance;
		} catch (RuntimeException re) {
			logger.error("get failed", re);
			throw re;
		}
	}
	@Override
	public List<TaStockCapsules> selectAll() {
		logger.debug("selectAll TaStockCapsules");
		try {
			Query ejbQuery = getEntityManager().createQuery(JPQLQuery);
			List<TaStockCapsules> l = ejbQuery.getResultList();
			logger.debug("selectAll successful");
			return l;
		} catch (RuntimeException re) {
			logger.error("selectAll failed", re);
			throw re;
		}
	}
	
	public TaStockCapsules refresh(TaStockCapsules detachedInstance) {
		logger.debug("refresh instance");
		try {
////			getEntityManager().refresh(detachedInstance);
////			logger.debug("refresh successful");
//			org.hibernate.Session session = (org.hibernate.Session) getEntityManager().getDelegate();
//		    session.evict(detachedInstance);
//		    detachedInstance = getEntityManager().find(TaStockCapsules.class, detachedInstance.getIdStock());
		    return detachedInstance;
		} catch (RuntimeException re) {
			logger.error("refresh failed", re);
			throw re;
		}
	}
	
	public List<TaEtatStockCapsules> selectSumMouvEntrees(TaEtatStockCapsules criteres,Date dateDeb,boolean strict) {
		try {																						//S.mouvStock
			String JPQLQueryMouvEntrees="select NEW fr.legrain.gestioncapsules.dao.TaEtatStockCapsules(a.codeTitreTransport,a.libelleTitreTransport, S.mouvStock , "+
		    " S.dateStock, sum(S.qte1Stock), '',S.libelleStock,'Stock','2' )"+
		    " from TaStockCapsules S join S.taTitreTransport a where  " +
		    "(upper(S.mouvStock) = 'E')  " ;//+
//		    " or ((S.qte1Stock is not null and S.qte1Stock >0 )or( S.qte2Stock is not null and S.qte2Stock >0))" ;
			
//			if(criteres.getExclusionQteSsUnite()){
//				 JPQLQueryMouvEntrees+=" and (S.un1Stock in (select u1.codeUnite from TaUnite u1) ";
//				 		//+"or " +
//				 		//" S.un2Stock in (select u2.codeUnite from TaUnite u2))";
//				}
			
			if(dateDeb!=null) {
				String signe = ">";
				if(!strict) signe=">=";
				JPQLQueryMouvEntrees+=" and S.dateStock "+signe+" '"+LibDate.dateToStringSql(dateDeb)+"'";
			}
			
			//clause where
			if(criteres.getCodeTitreTransport()!=null && !criteres.getCodeTitreTransport().equals(""))
		    	JPQLQueryMouvEntrees+=" and a.codeTitreTransport like '"+criteres.getCodeTitreTransport()+"'";
		    if(criteres.getDateStock()!=null)
		    	JPQLQueryMouvEntrees+=" and S.dateStock <= '"+LibDate.dateToStringSql(criteres.getDateStock())+"'";
//		    if(libelleStock!=null && !libelleStock.equals(""))
//		    	JPQLQueryMouvEntrees+=" and S.taArticle.codeArticle like "+codeArticle;
//		    if(criteres.getUn1Stock()!=null && !criteres.getUn1Stock().equals(""))
//		    	JPQLQueryMouvEntrees+=" and S.un1Stock like '"+criteres.getUn1Stock()+"'";
//		    if(criteres.getUn2Stock()!=null && !criteres.getUn2Stock().equals(""))
//		    	JPQLQueryMouvEntrees+=" and S.un2Stock like '"+criteres.getUn2Stock()+"'";	
		       	
			
		    
		    //clause group by
		    JPQLQueryMouvEntrees+=" group by a.codeTitreTransport,a.libelleTitreTransport,S.mouvStock, S.dateStock,S.libelleStock";			
			
		    //clause having
//		    if(criteres.getQte1Stock()!=null && !criteres.getQte1Stock().equals("")){
//		    	if(!JPQLQueryMouvEntrees.contains(" having "))
//		    		JPQLQueryMouvEntrees+=" having sum(S.qte1Stock) >= "+criteres.getQte1Stock();
//		    	else
//		    	JPQLQueryMouvEntrees+=" and sum(S.qte1Stock) >= "+criteres.getQte1Stock();
//		    }
//		    if(criteres.getQte2Stock()!=null && !criteres.getQte2Stock().equals("")){
//		    	if(!JPQLQueryMouvEntrees.contains(" having "))
//		    		JPQLQueryMouvEntrees+=" having sum(S.qte2Stock) >= "+criteres.getQte2Stock();	
//		    	else
//		    	JPQLQueryMouvEntrees+=" and sum(S.qte2Stock) >= "+criteres.getQte2Stock();		    	
//		    } 
		    
		    Query ejbQuery = getEntityManager().createQuery(JPQLQueryMouvEntrees);
		    List<TaEtatStockCapsules> l = ejbQuery.getResultList();
//		    for (TaEtatStockCapsules taEtatStock : l) {
//				if(taEtatStock.getQte1Stock()!=null && taEtatStock.getQte1Stock().signum()==0)taEtatStock.setUn1Stock(null);
////				if(taEtatStock.getQte2Stock()!=null && taEtatStock.getQte2Stock().signum()==0)taEtatStock.setUn2Stock(null);
//			}
			return l;
		} catch (RuntimeException re) {
			logger.error("selectSumMouvEntrees failed", re);
			throw re;
		}
	}
	public List<TaEtatStockCapsules> selectSumMouvSorties(TaEtatStockCapsules criteres,Date dateDeb, boolean strict) {
		try {																				//S.mouvStock
			String JPQLQueryMouvSorties="select NEW fr.legrain.gestioncapsules.dao.TaEtatStockCapsules(a.codeTitreTransport,a.libelleTitreTransport,S.mouvStock, "+
		    " S.dateStock, sum(-1*S.qte1Stock), '',S.libelleStock,'Stock','3' )"+
		    " from TaStockCapsules S join S.taTitreTransport a where  " +
		    "(upper(S.mouvStock) = 'S')  " ;//+
//		    " or ((-1*S.qte1Stock is not null and (-1*S.qte1Stock) >0 )or( -1*S.qte2Stock is not null and (-1*S.qte2Stock) >0))" ;
			
//			if(criteres.getExclusionQteSsUnite()){
//				JPQLQueryMouvSorties+=" and (S.un1Stock in (select u1.codeUnite from TaUnite u1) ";//or " +
//				 		//" S.un2Stock in (select u2.codeUnite from TaUnite u2))";
//				}
			
			if(dateDeb!=null) {
				String signe = ">";
				if(!strict) signe=">=";
				JPQLQueryMouvSorties+=" and S.dateStock "+signe+" '"+LibDate.dateToStringSql(dateDeb)+"'";
			}
		    
			//clause where
			if(criteres.getCodeTitreTransport()!=null && !criteres.getCodeTitreTransport().equals(""))
				JPQLQueryMouvSorties+=" and a.codeTitreTransport like '"+criteres.getCodeTitreTransport()+"'";
		    if(criteres.getDateStock()!=null)
		    	JPQLQueryMouvSorties+=" and S.dateStock <= '"+LibDate.dateToStringSql(criteres.getDateStock())+"'";
//		    if(libelleStock!=null && !libelleStock.equals(""))
//		    	JPQLQueryMouvSorties+=" and S.taArticle.codeArticle like "+codeArticle;
//		    if(criteres.getUn1Stock()!=null && !criteres.getUn1Stock().equals(""))
//		    	JPQLQueryMouvSorties+=" and S.un1Stock like '"+criteres.getUn1Stock()+"'";
//		    if(criteres.getUn2Stock()!=null && !criteres.getUn2Stock().equals(""))
//		    	JPQLQueryMouvSorties+=" and S.un2Stock like '"+criteres.getUn2Stock()+"'";	
		       	
		    //clause group by
		    JPQLQueryMouvSorties+=" group by a.codeTitreTransport,a.libelleTitreTransport,S.mouvStock, S.dateStock,S.libelleStock";						
			
		    //clause having
//		    if(criteres.getQte1Stock()!=null && !criteres.getQte1Stock().equals("")){
//		    	if(!JPQLQueryMouvSorties.contains(" having "))
//		    		JPQLQueryMouvSorties+=" having sum(-1*S.qte1Stock) >= "+criteres.getQte1Stock();
//		    	else
//		    	JPQLQueryMouvSorties+=" and sum(-1*S.qte1Stock) >= "+criteres.getQte1Stock();
//		    }
//		    if(criteres.getQte2Stock()!=null && !criteres.getQte2Stock().equals("")){
//		    	if(!JPQLQueryMouvSorties.contains(" having "))
//		    		JPQLQueryMouvSorties+=" having sum(-1*S.qte2Stock) >= "+criteres.getQte2Stock();
//		    	else JPQLQueryMouvSorties+=" and sum(-1*S.qte2Stock) >= "+criteres.getQte2Stock();
//		    }
			Query ejbQuery = getEntityManager().createQuery(JPQLQueryMouvSorties);
		    List<TaEtatStockCapsules> l = ejbQuery.getResultList();
//		    for (TaEtatStockCapsules taEtatStock : l) {
//				if(taEtatStock.getQte1Stock()!=null && taEtatStock.getQte1Stock().signum()==0)taEtatStock.setUn1Stock(null);
////				if(taEtatStock.getQte2Stock()!=null && taEtatStock.getQte2Stock().signum()==0)taEtatStock.setUn2Stock(null);
//			}
			return l;
		} catch (RuntimeException re) {
			logger.error("selectSumMouvSorties failed", re);
			throw re;
		}
	}

}
