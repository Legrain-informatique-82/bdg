package fr.legrain.pointLgr.dao;

// Generated Mar 24, 2009 4:44:04 PM by Hibernate Tools 3.2.0.CR1

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TemporalType;

import org.apache.log4j.Logger;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Scale;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

import fr.legrain.SupportAbon.dao.TaSupportAbon;
import fr.legrain.SupportAbon.dao.TaSupportAbonDAO;
import fr.legrain.abonnement.dao.TaAbonnement;
import fr.legrain.articles.dao.TaArticle;
import fr.legrain.articles.dao.TaArticleDAO;
import fr.legrain.articles.dao.TaFamilleDAO;
import fr.legrain.documents.dao.TaAvisEcheance;
import fr.legrain.documents.dao.TaFacture;
import fr.legrain.documents.dao.TaFactureDAO;
import fr.legrain.documents.dao.TaLAvisEcheance;
import fr.legrain.documents.dao.TaLFacture;
import fr.legrain.documents.dao.TaLFactureDAO;
import fr.legrain.documents.dao.TaReglement;
import fr.legrain.gestCom.Appli.AbstractApplicationDAO;
import fr.legrain.gestCom.Appli.Const;
import fr.legrain.gestCom.Module_Document.IDocumentTiers;
import fr.legrain.gestCom.Module_Document.ILigneDocumentTiers;
import fr.legrain.gestCom.global.dao.TaConst;
import fr.legrain.gestCom.global.dao.TaConstDAO;
import fr.legrain.lib.data.ExceptLgr;
import fr.legrain.lib.data.JPABdLgr;
import fr.legrain.lib.data.LibConversion;
import fr.legrain.lib.data.LibDate;
import fr.legrain.licenceBdg.dao.TaLicenceBdg;
import fr.legrain.licenceEpicea.dao.TaLicenceEpicea;
import fr.legrain.licenceSara.dao.TaLicenceSara;
import fr.legrain.tiers.dao.TaFamilleTiers;
import fr.legrain.tiers.dao.TaFamilleTiersDAO;
import fr.legrain.tiers.dao.TaTiers;
import fr.legrain.tiers.dao.TaTiersDAO;


/**
 * Home object for domain model class TaAdresse.
 * @see fr.legrain.tiers.dao.TaAdresse
 * @author Hibernate Tools
 */
public class TaComptePointDAO extends AbstractApplicationDAO<TaComptePoint>{

	//private static final Log logger = LogFactory.getLog(TaAdresseDAO.class);
	static Logger logger = Logger.getLogger(TaComptePointDAO.class);
	
	private BigDecimal taux_conversion=null;
	private String defaultJPQLQuery = "select a from TaComptePoint a";
	
	public TaComptePointDAO(){
		this(null);
		taux_conversion=new TaConstDAO(getEntityManager()).remonteConversionPointBonus();
	}
	
	public TaComptePointDAO(EntityManager em){
		if(em!=null) {
			setEm(em);
		}
//		champIdTable=ctrlGeneraux.getID_TABLE(TaTiersPoint.class.getSimpleName());
		initChampId(TaComptePoint.class);
		setJPQLQuery(defaultJPQLQuery);
		setListeChampMaj(Const.C_FICHIER_INI_CHAMPMAJ);
		initNomTable(new TaComptePoint());
		taux_conversion=new TaConstDAO(getEntityManager()).remonteConversionPointBonus();
	}

	public TaComptePoint refresh(TaComptePoint detachedInstance) {
		logger.debug("refresh instance");
		try {
			org.hibernate.Session session = (org.hibernate.Session) getEntityManager().getDelegate();
		    session.evict(detachedInstance);
		    detachedInstance = getEntityManager().find(TaComptePoint.class, detachedInstance.getIdPoint());
		    return detachedInstance;
		} catch (RuntimeException re) {
			logger.error("refresh failed", re);
			throw re;
		}
	}
	public void persist(TaComptePoint transientInstance) {
		logger.debug("persisting TaComptePoint instance");
		try {
			getEntityManager().persist(transientInstance);
			logger.debug("persist successful");
		} catch (RuntimeException re) {
			logger.error("persist failed", re);
			throw re;
		}
	}

	public void remove(TaComptePoint persistentInstance) {
		logger.debug("removing TaComptePoint instance");
		try {
			getEntityManager().remove(persistentInstance);
			logger.debug("remove successful");
		} catch (RuntimeException re) {
			logger.error("remove failed", re);
			throw re;
		}
	}

	public TaComptePoint merge(TaComptePoint detachedInstance) {
		logger.debug("merging TaComptePoint instance");
		try {
			TaComptePoint result = getEntityManager().merge(detachedInstance);
			logger.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			logger.error("merge failed", re);
			throw re;
		}
	}

	public TaComptePoint findById(int id) {
		logger.debug("getting TaComptePoint instance with id: " + id);
		try {
			TaComptePoint instance = getEntityManager().find(TaComptePoint.class, id);
			logger.debug("get successful");
			return instance;
		} catch (RuntimeException re) {
			logger.error("get failed", re);
			throw re;
		}
	}

	public Boolean ExistPointTiersTypeMouvDocument(TaAbonnement abonnement,Integer idTypeMouv) {
		logger.debug("getting ExistPointTiersTypeMouvDocument  with id: " + abonnement.getTaTiers().getIdTiers());
		try {
			Query query = getEntityManager().createQuery("select count(a) from TaComptePoint a join a.taTypeMouvPoint tm" +
					" join a.taTiers t where t.idTiers = ? " +
					" and tm.idTypeMouv = ? and a.codeDocument = ?");
			query.setParameter(1, abonnement.getTaTiers().getIdTiers());
			query.setParameter(2, idTypeMouv);
			query.setParameter(3, abonnement.getTaLDocument().getTaDocument().getCodeDocument());
			Long instance = (Long)query.getSingleResult();
			logger.debug("get successful");
			return instance!=0;
		} catch (RuntimeException re) {
			throw re;
		} catch (Exception e) {
			return false;
		}
	}
	
	public List<TaComptePoint> selectAllByTiersCompte(Integer idTiers) {
		logger.debug("getting selectAllByTiersCompte  with id: " + idTiers);
		try {
			Query query = getEntityManager().createQuery("select a from TaComptePoint a join a.taTiers t where t.idTiers = ?");
			query.setParameter(1, idTiers);
			List<TaComptePoint> l = query.getResultList();
			return l;
		} catch (RuntimeException re) {
			throw re;
		} catch (Exception e) {
			return null;
		}
	}
	

	
	public void calculPointUtilise(Integer idTiers, Date dateDeb) {
//		List<TaTypeMouvPoint>listeType=null;
//		List<TaLAvisEcheance> l = null;
//		List<TaLFacture> f = null;
//		TaComptePoint comptePoint=null;
//		List<String> listeCodeArticlePoint=new LinkedList<String>();
//		TaTypeMouvPoint typeEu= new TaTypeMouvPointDAO().findByCode("EU");
//		if(dateDeb==null)dateDeb=new Date(0);
//		logger.debug("calculPointUtilise  with id: " + idTiers);
//		try {
//			Query query = null;
//			getEntityManager().getTransaction().begin();
//			query = getEntityManager().createQuery("delete from TaComptePoint  where taTiers.idTiers =? "+
//					" and dateUtilisation is not null and dateUtilisation>=? and taTypeMouvPoint=? ");
//			query.setParameter(1, idTiers);
//			query.setParameter(2, dateDeb,TemporalType.DATE);	
//			query.setParameter(3, typeEu);
//			query.executeUpdate();
//			getEntityManager().getTransaction().commit();
//			query=null;
//			query = getEntityManager().createQuery("select a from TaTypeMouvPoint a ");
//			listeType=query.getResultList();
//
////
//			for (TaTypeMouvPoint taTypeMouvPoint : listeType) {
//				if(!listeCodeArticlePoint.contains(taTypeMouvPoint.getTaArticle().getCodeArticle()))
//					listeCodeArticlePoint.add(taTypeMouvPoint.getTaArticle().getCodeArticle());
//			}
//			
//			for (String codeArticle : listeCodeArticlePoint) {				
//			
//				query = getEntityManager().createQuery("select a from TaLAvisEcheance a join a.taDocument d join d.taTiers t join a.taArticle ar where t.idTiers = ? " +
//						" and ar.codeArticle=? and d.dateDocument>=? and not exists(select rd from TaRDocument rd join rd.taAvisEcheance av " +
//						" where av.idDocument=d.idDocument and rd.taFacture is not null) ");// +
////						" union " +
////						"select lf from TaLFacture lf join lf.taDocument d join d.taTiers t join lf.taArticle ar where t.idTiers = ? " +
////						" and ar.codeArticle=? and d.dateDocument>=? ");
//				
//				query.setParameter(1, idTiers);
//				query.setParameter(2, codeArticle);
//				query.setParameter(3, dateDeb,TemporalType.DATE);
//				l=query.getResultList();
//				TaTypeMouvPoint typePointUtilise=new TaTypeMouvPointDAO().findByCode("EU");
//				for (TaLAvisEcheance taLAvisEcheance : l) {
//					getEntityManager().getTransaction().begin();
//					comptePoint=new TaComptePoint(0);
//					comptePoint.setTaTiers(taLAvisEcheance.getTaDocument().getTaTiers());
//					comptePoint.setCodeDocument(taLAvisEcheance.getTaDocument().getCodeDocument());
//					comptePoint.setDateUtilisation(taLAvisEcheance.getTaDocument().getDateDocument());
//					comptePoint.setPoint(taLAvisEcheance.getMtTtcLDocument().multiply(BigDecimal.valueOf(10)));
//					comptePoint.setTaTypeMouvPoint(typePointUtilise);
//					getEntityManager().merge(comptePoint);
//					getEntityManager().getTransaction().commit();
//				}
//				
//				query = getEntityManager().createQuery("select lf from TaLFacture lf join lf.taDocument d join d.taTiers t join lf.taArticle ar where t.idTiers = ? " +
//						" and ar.codeArticle=? and d.dateDocument>=? ");
//				
//				query.setParameter(1, idTiers);
//				query.setParameter(2, codeArticle);
//				query.setParameter(3, dateDeb,TemporalType.DATE);
//				f=query.getResultList();
//				for (TaLFacture taLAvisEcheance : f) {
//					getEntityManager().getTransaction().begin();
//					comptePoint=new TaComptePoint(0);
//					comptePoint.setTaTiers(taLAvisEcheance.getTaDocument().getTaTiers());
//					comptePoint.setCodeDocument(taLAvisEcheance.getTaDocument().getCodeDocument());
//					comptePoint.setDateUtilisation(taLAvisEcheance.getTaDocument().getDateDocument());
//					comptePoint.setPoint(taLAvisEcheance.getMtTtcLApresRemiseGlobaleDocument().multiply(BigDecimal.valueOf(10)));
//					comptePoint.setTaTypeMouvPoint(typePointUtilise);
//					getEntityManager().merge(comptePoint);
//					getEntityManager().getTransaction().commit();
//				}				
//			}
//			
//
//			
//
//		} catch (RuntimeException re) {
//			if(getEntityManager().getTransaction().isActive())getEntityManager().getTransaction().rollback();
//			throw re;
//		} catch (Exception e) {
//		}
	}
	
	public void calculPointUtiliseFacture(Integer idTiers, Date dateDeb) {
//		List<TaTypeMouvPoint>listeType=null;
//		List<TaLAvisEcheance> l = null;
//		List<TaLFacture> f = null;
//		TaComptePoint comptePoint=null;
//		List<String> listeCodeArticlePoint=new LinkedList<String>();
//		TaTypeMouvPoint typeEu= new TaTypeMouvPointDAO().findByCode("EU");
//		if(dateDeb==null)dateDeb=new Date(0);
//		logger.debug("calculPointUtilise  with id: " + idTiers);
//		try {
//			Query query = null;
//			getEntityManager().getTransaction().begin();
//			query = getEntityManager().createQuery("delete from TaComptePoint  where taTiers.idTiers =? "+
//					" and dateUtilisation is not null and dateUtilisation>=? and taTypeMouvPoint=? ");
//			query.setParameter(1, idTiers);
//			query.setParameter(2, dateDeb,TemporalType.DATE);	
//			query.setParameter(3, typeEu);
//			query.executeUpdate();
//			getEntityManager().getTransaction().commit();
//			query=null;
//			query = getEntityManager().createQuery("select a from TaTypeMouvPoint a ");
//			listeType=query.getResultList();
//
////
//			for (TaTypeMouvPoint taTypeMouvPoint : listeType) {
//				if(!listeCodeArticlePoint.contains(taTypeMouvPoint.getTaArticle().getCodeArticle()))
//					listeCodeArticlePoint.add(taTypeMouvPoint.getTaArticle().getCodeArticle());
//			}
//			
//			for (String codeArticle : listeCodeArticlePoint) {				
//				TaTypeMouvPoint typePointUtilise=new TaTypeMouvPointDAO().findByCode("EU");
//
//				query = getEntityManager().createQuery("select lf from TaLFacture lf join lf.taDocument d join d.taTiers t join lf.taArticle ar where t.idTiers = ? " +
//						" and ar.codeArticle=? and d.dateDocument>=? ");
//				
//				query.setParameter(1, idTiers);
//				query.setParameter(2, codeArticle);
//				query.setParameter(3, dateDeb,TemporalType.DATE);
//				f=query.getResultList();
//				for (TaLFacture taLAvisEcheance : f) {
//					getEntityManager().getTransaction().begin();
//					comptePoint=new TaComptePoint(0);
//					comptePoint.setTaTiers(taLAvisEcheance.getTaDocument().getTaTiers());
//					comptePoint.setCodeDocument(taLAvisEcheance.getTaDocument().getCodeDocument());
//					comptePoint.setDateUtilisation(taLAvisEcheance.getTaDocument().getDateDocument());
//					comptePoint.setPoint(taLAvisEcheance.getMtTtcLApresRemiseGlobaleDocument().multiply(BigDecimal.valueOf(10)));
//					comptePoint.setTaTypeMouvPoint(typePointUtilise);
//					getEntityManager().merge(comptePoint);
//					getEntityManager().getTransaction().commit();
//				}				
//			}
//			
//
//		} catch (RuntimeException re) {
//			if(getEntityManager().getTransaction().isActive())getEntityManager().getTransaction().rollback();
//			throw re;
//		} catch (Exception e) {
//		}
	}
	
	
	
//	public List<TaComptePoint> findByTiersAndDate(Integer idTiers,Date dateDeb,Date dateFin) {
//		List<TaComptePoint> listeTemp=new ArrayList<TaComptePoint>();
//		List<TaTypeMouvPoint>listeType=null;
//		List<TaComptePoint> l=null;
//		TaComptePoint taComptePointTemp=null;
//		TaTiersDAO daoTiers = new TaTiersDAO(getEntityManager()); 
//		TaTiers taTiers = daoTiers.findById(idTiers);
//		logger.debug("getting TaComptePoint  with id: " + idTiers);
//		if(dateDeb.before(new Date()))dateDeb=new Date();
//		
//		//*******************************
//		calculPointUtilise(idTiers,dateDeb);
//		//*******************************
//
//		try {
//			Query query = getEntityManager().createQuery("select a from TaTypeMouvPoint a ");	
//			listeType=query.getResultList();
//			//for (TaTypeMouvPoint taTypeMouvPoint : listeType) {
//			query=null; // and a.taTypeMouvPoint=?
//			query = getEntityManager().createQuery("select a from TaComptePoint a join a.taTiers t where t.idTiers = ? " +
//					" and(a.datePeremption is not null )" +
//					" and a.datePeremption >=?  and (a.dateAcquisition <= ? or a.dateAcquisition is null)" +
//					"  order by a.dateAcquisition,a.datePeremption"
//					);
//			query.setParameter(1, idTiers);
//			//query.setParameter(2, taTypeMouvPoint);
//			query.setParameter(2, dateDeb,TemporalType.DATE);
//			query.setParameter(3, dateFin,TemporalType.DATE);
//			l = query.getResultList();
//			if(l.size()>0){
//				taComptePointTemp=new TaComptePoint();
//				taComptePointTemp.setPoint(new BigDecimal(0));				
//				for (TaComptePoint taComptePoint : l) {
//					taComptePointTemp.setTaTiers(taComptePoint.getTaTiers());
//					taComptePointTemp.setPoint(taComptePointTemp.getPoint().add(taComptePoint.getPoint()));
//					taComptePointTemp.setTaTypeMouvPoint(taComptePoint.getTaTypeMouvPoint());
//				}
//			}
//			if(taComptePointTemp!=null){
//				TaComptePoint pointSolde= soldeReelTiers(taTiers, dateDeb, dateFin);
//				if(taComptePointTemp.getPoint().signum()>0){
//					if(pointSolde.getPoint()==null || pointSolde.getPoint().signum()<=0)taComptePointTemp=null;
//					else
//					if( pointSolde.getPoint().compareTo(taComptePointTemp.getPoint())<=0 )
//						taComptePointTemp.setPoint(pointSolde.getPoint());
//				}
//					
//				if(taComptePointTemp!=null)listeTemp.add(taComptePointTemp);
//			}
//			//}
//			return listeTemp;
//		} catch (RuntimeException re) {
//			throw re;
//		} catch (Exception e) {
//			return null;
//		}
//	}
	
	
	public List<TaComptePoint> findByTiersAndDate2(Integer idTiers,Date dateDeb,Date dateFin,boolean estUneFacture) {
		List<TaComptePoint> listeTemp=new ArrayList<TaComptePoint>();
		List<TaTypeMouvPoint>listeType=null;
		List<TaComptePoint> l=null;
		TaComptePoint taComptePointTemp=null;
		TaTiersDAO daoTiers = new TaTiersDAO(getEntityManager()); 
		TaTiers taTiers = daoTiers.findById(idTiers);
		logger.debug("getting TaComptePoint  with id: " + idTiers);
		if(dateDeb.before(new Date()))dateDeb=new Date();
		
		BigDecimal reste;
		
		//*******************************
		calculPointUtilise(idTiers,dateDeb);
		//*******************************

		try {
			Query query = getEntityManager().createQuery("select a from TaTypeMouvPoint a ");	
			listeType=query.getResultList();
			//for (TaTypeMouvPoint taTypeMouvPoint : listeType) {
			query=null; // and a.taTypeMouvPoint=?
			query = getEntityManager().createQuery("select a from TaComptePoint a join a.taTiers t where t.idTiers = ? " +
					" and (a.point-a.utilise_facture>0) "+
					" and(a.datePeremption is not null )" +
					" and a.datePeremption >=?  and (a.dateAcquisition <= ? or a.dateAcquisition is null)" +
					"  order by a.dateAcquisition,a.datePeremption"
					);
			query.setParameter(1, idTiers);
			//query.setParameter(2, taTypeMouvPoint);
			query.setParameter(2, dateDeb,TemporalType.DATE);
			query.setParameter(3, dateFin,TemporalType.DATE);
			l = query.getResultList();
			if(l.size()>0){
				taComptePointTemp=new TaComptePoint();
				taComptePointTemp.setPoint(new BigDecimal(0));				
				for (TaComptePoint taComptePoint : l) {
					taComptePointTemp.setTaTiers(taComptePoint.getTaTiers());
					if(estUneFacture){
						taComptePointTemp.setPoint(taComptePoint.getPoint().add(taComptePoint.getPoint().subtract(taComptePoint.getUtilise_facture())));
					}else{
						reste=taComptePoint.getPoint().subtract(taComptePoint.getUtilise_facture()).subtract(taComptePoint.getUtilise_avis());
						if(reste.signum()>0) taComptePointTemp.setPoint(taComptePointTemp.getPoint().add(reste));
					}					
					taComptePointTemp.setTaTypeMouvPoint(taComptePoint.getTaTypeMouvPoint());
				}
			}
			if(taComptePointTemp!=null && taComptePointTemp.getPoint().signum()>0){
//				TaComptePoint pointSolde= soldeReelTiers(taTiers, dateDeb, dateFin);
//				if(taComptePointTemp.getPoint().signum()>0){
//					if(pointSolde.getPoint()==null || pointSolde.getPoint().signum()<=0)taComptePointTemp=null;
//					else
//					if( pointSolde.getPoint().compareTo(taComptePointTemp.getPoint())<=0 )
//						taComptePointTemp.setPoint(pointSolde.getPoint());
//				}
					
				if(taComptePointTemp!=null)listeTemp.add(taComptePointTemp);
			}
			//}
			return listeTemp;
		} catch (RuntimeException re) {
			throw re;
		} catch (Exception e) {
			return null;
		}
	}
	
	
	public TaComptePoint soldeReelTiers(TaTiers taTiers,Date dateDeb,Date dateFin) {
//		List<TaComptePoint> listeTemp=new ArrayList<TaComptePoint>();
		List<TaTypeMouvPoint>listeType=null;
		TaComptePoint taComptePoint=null;
		BigDecimal point=null;
		TaComptePoint taComptePointTemp=null;
		logger.debug("getting soldeReelTiers  with id: " + taTiers.getIdTiers());

		//*******************************
		calculPointUtilise(taTiers.getIdTiers(),dateDeb);
		//*******************************

		try {
			Query query = getEntityManager().createQuery("select a from TaTypeMouvPoint a ");	
			listeType=query.getResultList();
			//for (TaTypeMouvPoint taTypeMouvPoint : listeType) {
			query=null; // and a.taTypeMouvPoint=?
			query = getEntityManager().createQuery("select sum(a.point) from TaComptePoint a join a.taTiers t where t.idTiers = ? "
					//" and ((a.datePeremption >= ? )or (a.datePeremption is null and a.dateUtilisation>=?))" +
//					"  order by a.dateUtilisation,a.datePeremption"
					);
			query.setParameter(1, taTiers.getIdTiers());
			//query.setParameter(2, taTypeMouvPoint);
//			query.setParameter(2, dateDeb,TemporalType.DATE);
//			query.setParameter(3, dateFin,TemporalType.DATE);
			taComptePoint=new TaComptePoint();
			point = (BigDecimal)query.getSingleResult();
			if(point.signum()!=0){
				taComptePoint.setTaTiers(taTiers);
				taComptePoint.setPoint(point);	
			}
			//}
			return taComptePoint;
		} catch (RuntimeException re) {
			throw re;
		} catch (Exception e) {
			return null;
		}
	}

	public TaComptePoint soldeReelPointTiers(Integer idTiers,Date dateDeb,Date dateFin) {
		List<TaComptePoint> listeTemp=new ArrayList<TaComptePoint>();
		List<TaTypeMouvPoint>listeType=null;
		List<TaComptePoint> l=null;
		TaComptePoint taComptePointTemp=null;
		logger.debug("getting TaComptePoint  with id: " + idTiers);
		
		//*******************************
		calculPointUtilise(idTiers,dateDeb);
		//*******************************
		
		try {
			Query query = getEntityManager().createQuery("select a from TaTypeMouvPoint a ");	
			listeType=query.getResultList();
			//for (TaTypeMouvPoint taTypeMouvPoint : listeType) {
				query=null; // and a.taTypeMouvPoint=?
				query = getEntityManager().createQuery("select a from TaComptePoint a join a.taTiers t where t.idTiers = ? " +
						" and ((a.datePeremption >= ? )or (a.datePeremption is null and a.dateUtilisation>=?))" +
						"  order by a.dateUtilisation,a.datePeremption"
						);
				query.setParameter(1, idTiers);
				//query.setParameter(2, taTypeMouvPoint);
				query.setParameter(2, dateDeb,TemporalType.DATE);
				query.setParameter(3, dateFin,TemporalType.DATE);
				l = query.getResultList();
				taComptePointTemp=new TaComptePoint();
				taComptePointTemp.setPoint(new BigDecimal(0));				
				for (TaComptePoint taComptePoint : l) {
					taComptePointTemp.setTaTiers(taComptePoint.getTaTiers());
					taComptePointTemp.setPoint(taComptePointTemp.getPoint().add(taComptePoint.getPoint()));
					taComptePointTemp.setTaTypeMouvPoint(taComptePoint.getTaTypeMouvPoint());
				}
				if(taComptePointTemp!=null)listeTemp.add(taComptePointTemp);
			//}
			return null;
		} catch (RuntimeException re) {
			throw re;
		} catch (Exception e) {
			return null;
		}
	}
	
	
	@Override
	public List<TaComptePoint> selectAll() {
		// TODO Auto-generated method stub
		logger.debug("selectAll TaComptePoint");
		try {
			Query ejbQuery = getEntityManager().createQuery(JPQLQuery);
			List<TaComptePoint> l = ejbQuery.getResultList();
			logger.debug("selectAll successful");
			return l;
		} catch (RuntimeException re) {
			logger.error("selectAll failed", re);
			throw re;
		}
	}
	
	public BigDecimal calculRemisePointAvisEcheance(IDocumentTiers doc, Map<String,BigDecimal> listePointBonus) {
		BigDecimal remisePoint=new BigDecimal(0);
		BigDecimal totalTTCLigne=new BigDecimal(0);
		
		TaTypeMouvPointDAO daoTypePoint = new TaTypeMouvPointDAO();
		try {
			List<TaTypeMouvPoint>listeType= daoTypePoint.selectAll();
			List<String> listeArticleType=new LinkedList<String>();
			for (TaTypeMouvPoint type : listeType) {
				if(!type.getTypeMouv().equals("EF")&&!type.getTypeMouv().equals("EP")&&!type.getTypeMouv().equals("EG"))
					if(!listeArticleType.contains(type.getTaArticle().getCodeArticle()))listeArticleType.add(type.getTaArticle().getCodeArticle());
			}

			for (String codeArticlePoint : listePointBonus.keySet()) {
				if(codeArticlePoint!=null){
					if(listePointBonus.get(codeArticlePoint)!=null && listeArticleType.contains(codeArticlePoint))
						remisePoint=remisePoint.add(listePointBonus.get(codeArticlePoint));
				}
			}		
			if(remisePoint!=null){
				remisePoint=remisePoint.divide(taux_conversion).setScale(2, BigDecimal.ROUND_HALF_UP);
			}
			
			logger.debug("calculRemisePointFacture successful");
			return remisePoint;
		} catch (RuntimeException re) {
			logger.error("calculRemisePointFacture failed", re);
			throw re;
		}
	}
	
	public BigDecimal calculRemisePointFacture(IDocumentTiers doc) {
		BigDecimal remisePoint=new BigDecimal(0);

		TaTypeMouvPointDAO daoTypePoint = new TaTypeMouvPointDAO();
		try {
			List<TaTypeMouvPoint>listeType= daoTypePoint.selectAll();
			List<String> listeArticleType=new LinkedList<String>();
			for (TaTypeMouvPoint type : listeType) {
				if(type.getTaArticle()!=null && !listeArticleType.contains(type.getTaArticle().getCodeArticle()))
					listeArticleType.add(type.getTaArticle().getCodeArticle());
			}

			if(doc instanceof TaFacture){
				for (TaLFacture ligne : ((TaFacture)doc).getLignes()) {
					if(ligne.getTaArticle()!=null)logger.info(ligne.getTaArticle().getCodeArticle());
					if(ligne.getTaArticle()!=null && ligne.getTaArticle().getCodeArticle()!=null && listeArticleType.contains(ligne.getTaArticle().getCodeArticle())){
						remisePoint=ligne.getMtHtLApresRemiseGlobaleDocument().multiply(BigDecimal.valueOf(-1),MathContext.DECIMAL128);
						remisePoint=remisePoint.multiply(BigDecimal.valueOf(100)).divide(ligne.getTaDocument().getMtHtCalc().add(remisePoint),MathContext.DECIMAL128).
								setScale(2, BigDecimal.ROUND_HALF_UP);
					}
				}			
			}
			if(doc instanceof TaAvisEcheance){
				for (TaLAvisEcheance ligne : ((TaAvisEcheance)doc).getLignes()) {
					if(ligne.getTaArticle()!=null && ligne.getTaArticle().getCodeArticle()!=null && listeArticleType.contains(ligne.getTaArticle().getCodeArticle())){
						remisePoint=ligne.getMtHtLApresRemiseGlobaleDocument().multiply(BigDecimal.valueOf(-1),MathContext.DECIMAL128);
						remisePoint=remisePoint.multiply(BigDecimal.valueOf(100)).divide(ligne.getTaDocument().getMtHtCalc().add(remisePoint),MathContext.DECIMAL128).
								setScale(2, BigDecimal.ROUND_HALF_UP);
					}
				}			
			}			
			logger.debug("calculRemisePointFacture successful");
			return remisePoint;
		} catch (RuntimeException re) {
			logger.error("calculRemisePointFacture failed", re);
			throw re;
		}
	}
	public class PointDepart {
		BigDecimal pointAnnee=null;
		BigDecimal pointAttribue=null;
		
	}
	public BigDecimal calculPointTotalAnnee(EntityManager em, TaAbonnement taAbonnement , Boolean avecHistorique) {
		try {
			
			PointDepart pointAnnee=new PointDepart();
			BigDecimal pointPartenaire=null;
			BigDecimal pointTotalAnnee=null;			
			TaSupportAbonDAO daoSupportAbonDAO = new TaSupportAbonDAO(em);
			TaSupportAbon support = null;
			Date datePeremption=null;
			
			if(taAbonnement!=null && taAbonnement.getTaLDocument()!=null){
				datePeremption=taAbonnement.getTaLDocument().getTaDocument().getDateDocument();
				datePeremption=LibDate.incrementDate(datePeremption, 0, 18, 0);
				support=taAbonnement.getTaSupportAbon();
				pointAnnee=calculPointAnnee(taAbonnement);
//				pointPartenaire=calculPointPartenaire(taAbonnement);
				if(pointAnnee!=null && pointAnnee.pointAttribue!=null && pointAnnee.pointAttribue.signum()!=0 && avecHistorique){
					pointTotalAnnee=new BigDecimal(0);
					for (TaAbonnement abonnement : support.getTaAbonnements()) {
						pointTotalAnnee=pointTotalAnnee.add(calculPointHistorique(abonnement,pointAnnee.pointAnnee));
					}
					
					if(!support.getTaAbonnements().contains(taAbonnement)){
						pointTotalAnnee=pointTotalAnnee.add(calculPointHistorique(taAbonnement,pointAnnee.pointAnnee));
					}					
				}

//				if(!em.getTransaction().isActive())em.getTransaction().begin();
				TaComptePoint comptePoint=null;
				
//				if(pointAnnee!=null && pointAnnee.pointAttribue!=null && pointAnnee.pointAttribue.signum()!=0){
//					comptePoint = new TaComptePoint(0);
//				comptePoint.setCodeDocument(taAbonnement.getTaLDocument().getTaDocument().getCodeDocument());
//				comptePoint.setDateAcquisition(taAbonnement.getTaLDocument().getTaDocument().getDateDocument());
//				comptePoint.setDatePeremption(datePeremption);
//				comptePoint.setPoint(pointAnnee.pointAttribue);
//				comptePoint.setTaTiers(taAbonnement.getTaTiers());
//				comptePoint.setTaTypeMouvPoint(new TaTypeMouvPointDAO().findByCode("EP"));
//				merge(comptePoint);
//				}
				
				if(pointTotalAnnee!=null && pointTotalAnnee.signum()!=0){
				comptePoint = new TaComptePoint(0);
				comptePoint.setCodeDocument(taAbonnement.getTaLDocument().getTaDocument().getCodeDocument());
				comptePoint.setDateAcquisition(taAbonnement.getTaLDocument().getTaDocument().getDateDocument());
				comptePoint.setDatePeremption(datePeremption);
				comptePoint.setPoint(pointTotalAnnee);
				comptePoint.setTaTiers(taAbonnement.getTaTiers());
				comptePoint.setTaTypeMouvPoint(new TaTypeMouvPointDAO().findByCode("EF"));				
				merge(comptePoint);
				}
				
//				if(pointPartenaire!=null && pointPartenaire.signum()!=0){
//				comptePoint = new TaComptePoint(0);
//				comptePoint.setCodeDocument(taAbonnement.getTaLDocument().getTaDocument().getCodeDocument());
//				comptePoint.setDateAcquisition(taAbonnement.getTaLDocument().getTaDocument().getDateDocument());
//				comptePoint.setDatePeremption(datePeremption);
//				comptePoint.setPoint(pointPartenaire);
//				comptePoint.setTaTiers(taAbonnement.getTaTiers());
//				comptePoint.setTaTypeMouvPoint(new TaTypeMouvPointDAO().findByCode("EG"));				
//				merge(comptePoint);
//				}
				
//				em.getTransaction().commit();
			}
			logger.debug("calculPointTotalAnnee successful");
			return pointTotalAnnee;
		} catch (RuntimeException re) {
			logger.error("calculPointTotalAnnee failed", re);
			throw re;
		}
	}
	
	public void enregistrePointBonusSurDocument(TaComptePoint comptePointTemp,IDocumentTiers doc) throws Exception{
		try {
			Date datePeremption=null;
			TaComptePoint comptePoint=null;
			
				datePeremption=doc.getDateDocument();
				datePeremption=LibDate.incrementDate(datePeremption, 0, 18, 0);
				
				if(!getEntityManager().getTransaction().isActive())getEntityManager().getTransaction().begin();
				//if(!em.getTransaction().isActive())em.getTransaction().begin();
				
				if(comptePointTemp.getPoint()!=null && comptePointTemp.getPoint().signum()!=0){
					comptePoint=new TaComptePoint();
				comptePoint.setCodeDocument(doc.getCodeDocument());
				comptePoint.setDateAcquisition(doc.getDateDocument());
				comptePoint.setDatePeremption(datePeremption);
				comptePoint.setPoint(comptePointTemp.getPoint());
				comptePoint.setTaTiers(doc.getTaTiers());
				comptePoint.setTaTypeMouvPoint(comptePointTemp.getTaTypeMouvPoint());
				merge(comptePoint);
				}			
				
				getEntityManager().getTransaction().commit();
			
			logger.debug("enregistrePointBonusSurDocument successful");
		} catch (Exception re) {
			logger.error("enregistrePointBonusSurDocument failed", re);
			throw re;
		}
	}
	
	public BigDecimal calculPointTotalAnneeSupport(TaSupportAbon taSupportAbon ) {
		try {
			BigDecimal pointAnnee=null;
			TaSupportAbonDAO daoSupportAbonDAO = new TaSupportAbonDAO();
			TaSupportAbon support = null;
			Date datePeremption=null;
			
			if(taSupportAbon!=null && taSupportAbon.getTaLDocument()!=null){
				datePeremption=taSupportAbon.getTaLDocument().getTaDocument().getDateDocument();
				datePeremption=LibDate.incrementDate(datePeremption, 0, 18, 0);
				pointAnnee=calculPointAchatSupport(taSupportAbon);

				if(!getEntityManager().getTransaction().isActive())getEntityManager().getTransaction().begin();
				TaComptePoint comptePoint=null;
				
				if(pointAnnee!=null && pointAnnee.signum()!=0){
					comptePoint = new TaComptePoint(0);
				comptePoint.setCodeDocument(taSupportAbon.getTaLDocument().getTaDocument().getCodeDocument());
				comptePoint.setDateAcquisition(taSupportAbon.getTaLDocument().getTaDocument().getDateDocument());
				comptePoint.setDatePeremption(datePeremption);
				comptePoint.setPoint(pointAnnee);
				comptePoint.setTaTiers(taSupportAbon.getTaTiers());
				comptePoint.setTaTypeMouvPoint(new TaTypeMouvPointDAO().findByCode("EP"));
				merge(comptePoint);
				}		
				getEntityManager().getTransaction().commit();
			}
			logger.debug("calculPointTotalAnneeSupport successful");
			return pointAnnee;
		} catch (RuntimeException re) {
			logger.error("calculPointTotalAnneeSupport failed", re);
			throw re;
		}
	}
	
	public PointDepart calculPointAnnee(TaAbonnement taAbonnement) {
		try {
			TaArticlePointDAO daoArticlePoint = new TaArticlePointDAO();
			TaArticlePoint articlePoint=null;
			BigDecimal remisePoint=null;
			PointDepart pointAnnee=new PointDepart();
			articlePoint=daoArticlePoint.findByArticle(taAbonnement.getTaArticle().getIdArticle());
			if(articlePoint!=null && articlePoint.getTaTypeMouvPoint()!=null /*&& articlePoint.getTaTypeMouvPoint().getTypeMouv().toUpperCase().equals("EP")*/){
				if(taAbonnement.getTaLDocument()!=null){
					pointAnnee.pointAnnee=articlePoint.getPoints();
					remisePoint=calculRemisePointFacture(taAbonnement.getTaLDocument().getTaDocument())	;
					if(articlePoint.getPrixReference()!=null && articlePoint.getPrixReference().signum()!=0)
					pointAnnee.pointAttribue=taAbonnement.getTaLDocument().getMtHtLApresRemiseGlobaleDocument().multiply(articlePoint.getPoints(),MathContext.DECIMAL128).
							divide(articlePoint.getPrixReference(),MathContext.DECIMAL128).setScale(2, BigDecimal.ROUND_HALF_UP);
					if(remisePoint.compareTo(BigDecimal.ZERO)!=0){
						pointAnnee.pointAttribue=pointAnnee.pointAttribue.subtract(pointAnnee.pointAttribue.multiply(remisePoint,MathContext.DECIMAL128).divide(BigDecimal.valueOf(100)).setScale(2, BigDecimal.ROUND_HALF_UP));
					}
				}
			}
//			if(pointAnnee!=null && !pointAnnee.equals(BigDecimal.ZERO))pointAnnee=(pointAnnee.divide(BigDecimal.valueOf(1).add((taAbonnement.getTaLDocument().getTauxTvaLDocument().divide(new BigDecimal(100),MathContext.DECIMAL128).setScale(3,BigDecimal.ROUND_HALF_UP)
//					)),MathContext.DECIMAL128).setScale(2,BigDecimal.ROUND_HALF_UP));
			logger.debug("calculPointAnnee successful");
			return pointAnnee;
		} catch (RuntimeException re) {
			logger.error("calculPointAnnee failed", re);
			throw re;
		}
	}
	
	public BigDecimal calculPointAchatSupport(TaSupportAbon taSupportAbon) {
		try {
			TaArticlePointDAO daoArticlePoint = new TaArticlePointDAO();
			TaArticlePoint articlePoint=null;
			BigDecimal remisePoint=null;
			BigDecimal pointAnnee=null;
			articlePoint=daoArticlePoint.findByArticle(taSupportAbon.getTaArticle().getIdArticle());
			if(articlePoint!=null && articlePoint.getTaTypeMouvPoint()!=null && articlePoint.getTaTypeMouvPoint().getTypeMouv().toUpperCase().equals("EP")){
				if(taSupportAbon.getTaLDocument()!=null){
					remisePoint=calculRemisePointFacture(taSupportAbon.getTaLDocument().getTaDocument())	;
					pointAnnee=taSupportAbon.getTaLDocument().getMtHtLApresRemiseGlobaleDocument().multiply(articlePoint.getPoints(),MathContext.DECIMAL128).
							divide(articlePoint.getPrixReference(),MathContext.DECIMAL128).setScale(2, BigDecimal.ROUND_HALF_UP);
					if(remisePoint.compareTo(BigDecimal.ZERO)!=0){
						pointAnnee=pointAnnee.subtract(pointAnnee.multiply(remisePoint,MathContext.DECIMAL128).divide(BigDecimal.valueOf(100)).setScale(2, BigDecimal.ROUND_HALF_UP));
					}
				}
			}
			logger.debug("calculPointAnnee successful");
			return pointAnnee;
		} catch (RuntimeException re) {
			logger.error("calculPointAnnee failed", re);
			throw re;
		}
	}
	public Boolean recupIndice(TaAbonnement taAbonnement) {
		try {
			TaArticlePointDAO daoArticlePoint = new TaArticlePointDAO();
			TaArticlePoint articlePoint=null;
			articlePoint=daoArticlePoint.findByArticle(taAbonnement.getTaArticle().getIdArticle());
			if(articlePoint!=null ){
				return LibConversion.intToBoolean(articlePoint.getIndice());				
			}
			logger.debug("recupIndice successful");
			return null;
		} catch (RuntimeException re) {
			logger.error("recupIndice failed", re);
			throw re;
		}
	}
	public BigDecimal calculPointIndice(TaAvisEcheance taAvisEcheance) {
		try {
			//si Qté <12, on ne traite pas
			// si codeProduit identique, on ne traite pas
			
			TaIndicePointDAO daoIndicePoint = new TaIndicePointDAO();
			TaIndicePoint indicePoint=null;
			BigDecimal point=null;
			BigDecimal tranche=BigDecimal.ZERO;
			LinkedList<TaLAvisEcheance> tabLigneCumule=new LinkedList<TaLAvisEcheance>();
			Boolean indice=true;
			Integer indiceTotal=0;
			List<TaArticle> listeProduit=new LinkedList<TaArticle>();
			listeProduit.clear();
			for (TaLAvisEcheance ligne : taAvisEcheance.getLignes()) {
				indice=null;
				if(ligne.getTaLEcheance()!=null && ligne.getTaLEcheance().getTaArticle()!=null 
						&& !partenaire100Pour100(ligne.getTaLEcheance().getTaAbonnement()))
					if(!listeProduit.contains(ligne.getTaLEcheance().getTaArticle())&& 
							ligne.getTaLEcheance().getQteLDocument().compareTo(BigDecimal.valueOf(12))>=0)
						indice=recupIndice(ligne.getTaLEcheance().getTaAbonnement());
					if(indice!=null && indice==true){
						listeProduit.add(ligne.getTaLEcheance().getTaArticle());
						tabLigneCumule.add(ligne);
						tranche=tranche.add(ligne.getTaLEcheance().getMtHtLApresRemiseGlobaleDocument());
					}
			}
			//avec le totalindice, récupérer le pourcentage correspondant
			if(tranche!=null && tranche.signum()>0 && listeProduit.size()>1)indicePoint=daoIndicePoint.RechercheMaxParTranche(tranche,listeProduit.size());
			if(indicePoint!=null && indicePoint.getPoints()!=null){
				return indicePoint.getPoints();
//				for (TaLAvisEcheance taLAvisEcheance : tabLigneCumule) {
//					if(point==null)point=new BigDecimal(0);
//					point=point.add(taLAvisEcheance.getMtHtLApresRemiseGlobaleDocument().
//							multiply(indicePoint.getPoints(), MathContext.DECIMAL128).
//							divide(BigDecimal.valueOf(100),MathContext.DECIMAL128).setScale(2, RoundingMode.HALF_UP));
//				}
//				if(point!=null)point=point.multiply(BigDecimal.valueOf(10), MathContext.DECIMAL128);
//				if(point!=null && !point.equals(BigDecimal.ZERO))point=(point.divide(BigDecimal.valueOf(1).add((ligne.getTauxTvaLDocument().divide(new BigDecimal(100),MathContext.DECIMAL128).setScale(3,BigDecimal.ROUND_HALF_UP)
//						)),MathContext.DECIMAL128).setScale(2,BigDecimal.ROUND_HALF_UP));				
			}			
			logger.debug("calculPointIndice successful");
			return null;
		} catch (RuntimeException re) {
			logger.error("calculPointIndice failed", re);
			throw re;
		}
	}
	
	public BigDecimal calculPointPartenaire(TaAbonnement taAbonnement,TaLAvisEcheance ligneAvisEcheance, Map<String,BigDecimal> listePointBonus) {
		try {
			BigDecimal pointPartenaire=new BigDecimal(0);
			BigDecimal remisePoint=null;
			//je récupère le % associé au groupe s'il y en a un
			TaFamilleTiersDAO daoFamille=new TaFamilleTiersDAO();
			TaPourcGroupe Pourcgroupe=null;
			TaPourcGroupeDAO daoPourcGroupe = new TaPourcGroupeDAO();
//			TaLFacture ligneFacture=null;
			Query ejbQuery = getEntityManager().createQuery("select s from TaSupportAbon s  where s.idSupportAbon="+taAbonnement.getTaSupportAbon().getIdSupportAbon());
			List<TaSupportAbon> l = ejbQuery.getResultList();
			for (TaSupportAbon taSupportAbon : l) {
				Pourcgroupe=null;
				BigDecimal valeur=null;
				
				if(taSupportAbon instanceof TaLicenceBdg){
					if(((TaLicenceBdg)taSupportAbon).getGroupe()!=null){
					if(taAbonnement.getTaTAbonnement()!=null)
						Pourcgroupe=daoPourcGroupe.findByTAbonnementFamille(((TaLicenceBdg)taSupportAbon).getGroupe().getIdFamille(),taAbonnement.getTaTAbonnement().getIdTAbonnement());
					}
				}
				if(taSupportAbon instanceof TaLicenceEpicea){
					if(((TaLicenceEpicea)taSupportAbon).getGroupe()!=null){
					if(taAbonnement.getTaTAbonnement()!=null && ((TaLicenceEpicea)taSupportAbon).getGroupe()!=null)
						Pourcgroupe=daoPourcGroupe.findByTAbonnementFamille(((TaLicenceEpicea)taSupportAbon).getGroupe().getIdFamille(),taAbonnement.getTaTAbonnement().getIdTAbonnement());
					}
				}
				if(taSupportAbon instanceof TaLicenceSara){
					if(((TaLicenceSara)taSupportAbon).getGroupe()!=null){
					if(taAbonnement.getTaTAbonnement()!=null && ((TaLicenceSara)taSupportAbon).getGroupe()!=null)
						Pourcgroupe=daoPourcGroupe.findByTAbonnementFamille(((TaLicenceSara)taSupportAbon).getGroupe().getIdFamille(),taAbonnement.getTaTAbonnement().getIdTAbonnement());
					}
				}
				
				if(Pourcgroupe!=null){
//					ligneFacture=taAbonnement.getTaLDocument();
					if(ligneAvisEcheance!=null){
						remisePoint=calculRemisePointAvisEcheance(ligneAvisEcheance.getTaDocument(),listePointBonus)	;
						valeur=ligneAvisEcheance.getMtTtcLApresRemiseGlobaleDocument().multiply(Pourcgroupe.getPourcentage(), MathContext.DECIMAL128).
						divide(BigDecimal.valueOf(100),MathContext.DECIMAL128).setScale(2,RoundingMode.HALF_UP);
						if(valeur!=null)valeur=valeur.multiply(taux_conversion,MathContext.DECIMAL128).setScale(2,RoundingMode.HALF_UP);
						pointPartenaire=pointPartenaire.add(valeur);
						if(remisePoint.compareTo(BigDecimal.ZERO)!=0){
							pointPartenaire=pointPartenaire.subtract(pointPartenaire.multiply(remisePoint,MathContext.DECIMAL128).divide(BigDecimal.valueOf(100)).setScale(2, BigDecimal.ROUND_HALF_UP));
						}
					}
				}				
			}		
//			if(pointPartenaire!=null && !pointPartenaire.equals(BigDecimal.ZERO))pointPartenaire=(pointPartenaire.divide(BigDecimal.valueOf(1).add((ligneFacture.getTauxTvaLDocument().divide(new BigDecimal(100),MathContext.DECIMAL128).setScale(3,BigDecimal.ROUND_HALF_UP)
//					)),MathContext.DECIMAL128).setScale(2,BigDecimal.ROUND_HALF_UP));
			logger.debug("calculPointPartenaire successful");
			return pointPartenaire;
		} catch (RuntimeException re) {
			logger.error("calculPointPartenaire failed", re);
			throw re;
		}
	}

	public boolean partenaire100Pour100(TaAbonnement taAbonnement){
		try {		

		//je récupère le % associé au groupe s'il y en a un
		TaFamilleTiersDAO daoFamille=new TaFamilleTiersDAO();
		TaPourcGroupe Pourcgroupe=null;
		TaPourcGroupeDAO daoPourcGroupe = new TaPourcGroupeDAO();

		Query ejbQuery = getEntityManager().createQuery("select s from TaSupportAbon s  where s.idSupportAbon="+taAbonnement.getTaSupportAbon().getIdSupportAbon());
		TaSupportAbon taSupportAbon= (TaSupportAbon)ejbQuery.getSingleResult();
			Pourcgroupe=null;
			BigDecimal valeur=null;			

			if(taSupportAbon instanceof TaLicenceBdg){
				if(((TaLicenceBdg)taSupportAbon).getGroupe()!=null){
					if(taAbonnement.getTaTAbonnement()!=null)
						Pourcgroupe=daoPourcGroupe.findByTAbonnementFamille(((TaLicenceBdg)taSupportAbon).getGroupe().getIdFamille(),taAbonnement.getTaTAbonnement().getIdTAbonnement());
				}
			}
			if(taSupportAbon instanceof TaLicenceEpicea){
				if(((TaLicenceEpicea)taSupportAbon).getGroupe()!=null){
					if(taAbonnement.getTaTAbonnement()!=null && ((TaLicenceEpicea)taSupportAbon).getGroupe()!=null)
						Pourcgroupe=daoPourcGroupe.findByTAbonnementFamille(((TaLicenceEpicea)taSupportAbon).getGroupe().getIdFamille(),taAbonnement.getTaTAbonnement().getIdTAbonnement());
				}
			}
			if(taSupportAbon instanceof TaLicenceSara){
				if(((TaLicenceSara)taSupportAbon).getGroupe()!=null){
					if(taAbonnement.getTaTAbonnement()!=null && ((TaLicenceSara)taSupportAbon).getGroupe()!=null)
						Pourcgroupe=daoPourcGroupe.findByTAbonnementFamille(((TaLicenceSara)taSupportAbon).getGroupe().getIdFamille(),taAbonnement.getTaTAbonnement().getIdTAbonnement());
				}
			}

			return (Pourcgroupe!=null && Pourcgroupe.getPourcentage().compareTo(BigDecimal.valueOf(100))==0);
		} catch (Exception e) {
			logger.error("partenaire100Pour100 failed", e);
		}
		return false;
	}
	
	
	public BigDecimal calculPointCogere(TaAvisEcheance taAvisEcheance) {
		try {
			TaArticle ArticlePointBonus=new TaArticleDAO().remonteArticlePointBonus();
			BigDecimal tauxTvaPoint=BigDecimal.ZERO;
			if(ArticlePointBonus.getTaTva()!=null)
				tauxTvaPoint=ArticlePointBonus.getTaTva().getTauxTva();
			//point calculer en fonction du nombre d'année de maintenance payée sur Epicéa
			//ces points ne sont applicable que sur la maintenance Epicéa
			final String  libelleGroupeCogere="COGERE";
			BigDecimal pointCogere=new BigDecimal(0);
			JPABdLgr bdlgr=new JPABdLgr();
			TaAbonnement abonnement = null;
			LinkedHashMap<Integer, TaLAvisEcheance> tabIndice=new LinkedHashMap<Integer, TaLAvisEcheance>();
			Integer nbMois=0;
			Date dateMin=null;
			Date dateMax=null;
			BigDecimal moisConsommes=BigDecimal.ZERO;
			BigDecimal qteRestant=BigDecimal.ZERO;
			BigDecimal qtePlage=BigDecimal.ZERO;
			pointCogere=BigDecimal.ZERO;
			TaFamilleTiersDAO daoFamilleTiers=new TaFamilleTiersDAO();
			TaSupportAbonDAO daoSupport =new TaSupportAbonDAO();
			TaFamilleTiers groupeCogere=null;
			if(daoFamilleTiers.exist(libelleGroupeCogere)) groupeCogere=daoFamilleTiers.findByCode(libelleGroupeCogere);
			Boolean continuer=true;
			
			for (TaLAvisEcheance ligne : taAvisEcheance.getLignes()) {
				if(ligne.getTaLEcheance()!=null && ligne.getTaLEcheance().getTaAbonnement()!=null && 
						ligne.getTaLEcheance().getTaAbonnement().getTaSupportAbon()!=null && 
						ligne.getTaLEcheance().getTaAbonnement().getTaSupportAbon().getTaTSupport()!=null && 
						ligne.getTaLEcheance().getTaAbonnement().getTaSupportAbon().getTaTSupport().getIdTSupport()==1){ //si produit Epicéa
					TaSupportAbon support=daoSupport.findById(ligne.getTaLEcheance().getTaAbonnement().getTaSupportAbon().getIdSupportAbon());
					if(support!=null && support instanceof TaLicenceEpicea){
						continuer=((TaLicenceEpicea)support).getGroupe()!=null;
						if(groupeCogere == null) {
							MessageDialog.openError(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), "groupe "+libelleGroupeCogere+" " +
									" inéxistant", "Le groupe "+libelleGroupeCogere+" n'a pas été trouvé.");
							continuer=false;
						}
						if(continuer )continuer=(((TaLicenceEpicea)support).getGroupe().getIdFamille()==groupeCogere.getIdFamille());
					}
					if(continuer){	
						String sqlDocumentsE2="select dateDiff (month, min(DATE_DEBUT),max(DATE_FIN))as moisConsommes " +
								" from TA_ABONNEMENT a  where a.ID_SUPPORT_ABON="+ligne.getTaLEcheance().getTaAbonnement().getTaSupportAbon().getIdSupportAbon();
						PreparedStatement prep= bdlgr.getCnx().prepareStatement(sqlDocumentsE2);
						//					Query ejbQuery = getEntityManager().createQuery("select dateDiff (month, min(dateDebut),max(dateFin))as diff " +
						//							" from TaAbonnement a  where s.idSupportAbon="+ligne.getTaLEcheance().getTaAbonnement().getTaSupportAbon().getIdSupportAbon());
						//					List<Object> l = ejbQuery.getResultList();
						ResultSet l=prep.executeQuery(sqlDocumentsE2);
						while (l.next()) {
							moisConsommes = BigDecimal.valueOf(l.getInt("moisConsommes"));							
						}
						if(moisConsommes!=null && moisConsommes.compareTo(BigDecimal.valueOf(60))<0){
							BigDecimal qteAPendre=ligne.getTaLEcheance().getQteLDocument();
							BigDecimal qteP1=BigDecimal.ZERO;
							BigDecimal qteP2=BigDecimal.ZERO;
							BigDecimal qteP3=BigDecimal.ZERO;
							if(moisConsommes.compareTo(BigDecimal.valueOf(12))<0) {
								qteAPendre=qteAPendre.subtract(BigDecimal.valueOf(12).subtract(moisConsommes));
								moisConsommes=BigDecimal.valueOf(12);
							}else if(moisConsommes.add(ligne.getTaLEcheance().getQteLDocument()).compareTo(BigDecimal.valueOf(60))>0){
								qteAPendre=BigDecimal.valueOf(60).subtract(moisConsommes);
							}
							if(moisConsommes.compareTo(BigDecimal.valueOf(12))>=0 && moisConsommes.compareTo(BigDecimal.valueOf(36))<=0 && qteAPendre.signum()>0){
								//premiere tranche
								qteP1=BigDecimal.valueOf(36).subtract(moisConsommes);
								if(qteP1.compareTo(qteAPendre)>0)qteP1=qteAPendre;
								moisConsommes=moisConsommes.add(qteP1);
								pointCogere=pointCogere.add(qteP1.multiply(BigDecimal.valueOf(5)).multiply(taux_conversion));
								qteAPendre=qteAPendre.subtract(qteP1);
							}
							if(moisConsommes.compareTo(BigDecimal.valueOf(36))>=0 && moisConsommes.compareTo(BigDecimal.valueOf(48))<=0 && qteAPendre.signum()>0){
								//deuxième tranche
								qteP2=BigDecimal.valueOf(48).subtract(moisConsommes);
								if(qteP2.compareTo(qteAPendre)>0)qteP2=qteAPendre;
								moisConsommes=moisConsommes.add(qteP2);
								pointCogere=pointCogere.add(qteP2.multiply(BigDecimal.valueOf(3.33)).multiply(taux_conversion));
								qteAPendre=qteAPendre.subtract(qteP2);
							}
							if(moisConsommes.compareTo(BigDecimal.valueOf(48))>=0 && moisConsommes.compareTo(BigDecimal.valueOf(60))<=0 && qteAPendre.signum()>0){
								//deuxième tranche
								qteP3=BigDecimal.valueOf(60).subtract(moisConsommes);
								if(qteP3.compareTo(qteAPendre)>0)qteP3=qteAPendre;
								moisConsommes=moisConsommes.add(qteP3);
								pointCogere=pointCogere.add(qteP3.multiply(BigDecimal.valueOf(1.66)).multiply(taux_conversion));
							}
						}
//						if(moisConsommes!=null && moisConsommes<60){
//							BigDecimal plageRestante=BigDecimal.ZERO;
//							Integer plageCourante=0;
//							if(moisConsommes.compareTo(12)>=0 && moisConsommes.compareTo(36)<0){
//								plageCourante=24;
//								plageRestante=BigDecimal.valueOf(36).subtract(BigDecimal.valueOf(moisConsommes));
//								qteRestant=plageRestante.subtract(ligne.getTaLEcheance().getQteLDocument());
//								if(qteRestant.signum()==1)qtePlage=ligne.getTaLEcheance().getQteLDocument().subtract(qteRestant);
//								else {
//									qtePlage=plageRestante;
//									qteRestant=ligne.getTaLEcheance().getQteLDocument().subtract(plageRestante);
//								}
//								pointCogere=pointCogere.add(qtePlage.multiply(BigDecimal.valueOf(5)).multiply(BigDecimal.valueOf(10)));
//								qteRestant=qteRestant.abs();
//							}
//							qtePlage=BigDecimal.ZERO;
//							if((moisConsommes.compareTo(36)>=0 && moisConsommes.compareTo(48)<0) ||(qteRestant.signum()>0)){
//								plageCourante=12;
//								if(ligne.getTaLEcheance().getQteLDocument().compareTo(BigDecimal.valueOf(plageCourante))>0)
//									qtePlage=BigDecimal.valueOf(plageCourante);else	qtePlage=ligne.getTaLEcheance().getQteLDocument();
//								if(qteRestant.signum()>0){
//									if(qteRestant.compareTo(BigDecimal.valueOf(plageCourante))<=0)qtePlage=qteRestant;
//									else qtePlage=BigDecimal.valueOf(plageCourante);
//									qteRestant=qteRestant.subtract(qtePlage);
//								}else{
//									plageRestante=BigDecimal.valueOf(48).subtract(BigDecimal.valueOf(moisConsommes));
//									qteRestant=plageRestante.subtract(ligne.getTaLEcheance().getQteLDocument());
////									if(qteRestant.signum()==1)qtePlage=ligne.getTaLEcheance().getQteLDocument().subtract(qteRestant);
//									if(qteRestant.signum()!=0 && qteRestant.compareTo(ligne.getTaLEcheance().getQteLDocument())<0)qtePlage=qteRestant.abs();
//								}
//								pointCogere=pointCogere.add(qtePlage.multiply(BigDecimal.valueOf(3.33)).multiply(BigDecimal.valueOf(10)));
//								qteRestant=qteRestant.abs();
//							}
//							qtePlage=BigDecimal.ZERO;
//							if(moisConsommes.compareTo(48)>=0 && moisConsommes.compareTo(60)<0||(qteRestant.signum()>0)){
//								plageCourante=12;
//								if(ligne.getTaLEcheance().getQteLDocument().compareTo(BigDecimal.valueOf(plageCourante))>0)
//									qtePlage=BigDecimal.valueOf(plageCourante);else	qtePlage=ligne.getTaLEcheance().getQteLDocument();
//								if(qteRestant.signum()>0){
//									if(qteRestant.compareTo(BigDecimal.valueOf(plageCourante))<=0)qtePlage=qteRestant;
//									else qtePlage=BigDecimal.valueOf(plageCourante);
//									qteRestant=qteRestant.subtract(qtePlage);
//								}else{
//									plageRestante=BigDecimal.valueOf(60).subtract(BigDecimal.valueOf(moisConsommes));
//									qteRestant=plageRestante.subtract(ligne.getTaLEcheance().getQteLDocument());
////									if(qteRestant.signum()==1)qtePlage=ligne.getTaLEcheance().getQteLDocument().subtract(qteRestant);
//									if(qteRestant.signum()!=0 && qteRestant.compareTo(ligne.getTaLEcheance().getQteLDocument())<0)qtePlage=qteRestant.abs();
//								}
//								pointCogere=pointCogere.add(qtePlage.multiply(BigDecimal.valueOf(1.66)).multiply(BigDecimal.valueOf(10)));
//							}
//						}
					}
				}
				if(tauxTvaPoint.compareTo(BigDecimal.ZERO)!=0){
					BigDecimal tva=pointCogere.multiply(tauxTvaPoint).divide(new BigDecimal(100),MathContext.DECIMAL128).
							setScale(2,BigDecimal.ROUND_HALF_UP);
					pointCogere=pointCogere.add(tva);
				}
				logger.debug("calculPointCogere successful");
				return pointCogere;
			}
			return null;
		} catch (RuntimeException re) {
			logger.error("calculPointCogere failed", re);
			throw re;
		} catch (SQLException e) {
			logger.error("calculPointCogere failed", e);			
		}
		return null;
	}

	
	
	public BigDecimal calculPointHistorique(TaAbonnement taAbonnement, BigDecimal pointAnnee) {
		try {
			TaArticlePointDAO daoArticlePoint = new TaArticlePointDAO();
			TaArticlePoint articlePoint=null;
			BigDecimal remisePoint=null;
			BigDecimal pointHistorique=new BigDecimal(0);
			articlePoint=daoArticlePoint.findByArticle(taAbonnement.getTaArticle().getIdArticle());
			if(articlePoint!=null && articlePoint.getTaTypeMouvPoint()!=null && articlePoint.getTaTypeMouvPoint().getTypeMouv().toUpperCase().equals("EF")){
				if(taAbonnement.getTaLDocument()!=null){
					remisePoint=calculRemisePointFacture(taAbonnement.getTaLDocument().getTaDocument())	;
//					pointHistorique=taAbonnement.getTaLDocument().getMtHtLApresRemiseGlobaleDocument().multiply(articlePoint.getPoints(), MathContext.DECIMAL128).
//							divide(articlePoint.getPrixReference(), MathContext.DECIMAL128).setScale(2, BigDecimal.ROUND_HALF_UP);
					pointHistorique=taAbonnement.getTaLDocument().getMtHtLApresRemiseGlobaleDocument().multiply(pointAnnee, MathContext.DECIMAL128).
							divide(articlePoint.getPrixReference(), MathContext.DECIMAL128).setScale(2, BigDecimal.ROUND_HALF_UP);
					if(remisePoint.compareTo(BigDecimal.ZERO)!=0){
						pointHistorique=pointHistorique.subtract(pointHistorique.multiply(remisePoint, MathContext.DECIMAL128).divide(BigDecimal.valueOf(100), MathContext.DECIMAL128).
								setScale(2, BigDecimal.ROUND_HALF_UP));
					}
				}
			}

			logger.debug("calculPointHistorique successful");
			return pointHistorique;
		} catch (RuntimeException re) {
			logger.error("calculPointHistorique failed", re);
			throw re;
		}
	}
	
	
	public void ctrlSaisieSpecifique(TaComptePoint entity,String field) throws ExceptLgr {	
		
	}

	
	public List<TaComptePoint> selectComptePointBonusFacture(IDocumentTiers doc) {
		// TODO Auto-generated method stub
		List<TaComptePoint> l=null;
		logger.debug("selectComptePointBonusFacture");
		try {
			if(doc!=null && doc.getIdDocument()!=0){
			Query query = getEntityManager().createQuery("select f from TaComptePoint f " +
					" where f.codeDocument like '"+doc.getCodeDocument()+"'" );
			l = query.getResultList();
			}
			logger.debug("selectComptePointBonusFacture successful");
			return l;
		} catch (RuntimeException re) {
			logger.error("selectComptePointBonusFacture failed", re);
			throw re;
		}
	}
	
	public List<TaComptePoint> selectComptePointBonusFactureNew(IDocumentTiers doc,Integer ArticlePointBonus) {
		// TODO Auto-generated method stub
		List<TaComptePoint> l=new LinkedList<TaComptePoint>();
		logger.debug("selectComptePointBonusFacture");
		try {
			
			if(doc!=null && doc.getIdDocument()!=0){
				for (ILigneDocumentTiers ligne : doc.getLignesGeneral()) {
					if(ligne.getTaArticle()!=null && ligne.getTaArticle().getIdArticle()==ArticlePointBonus){
					TaComptePoint comptePoint = new TaComptePoint();
					comptePoint.setPoint(ligne.getMtTtcLApresRemiseGlobaleDocument().multiply(taux_conversion));
					l.add(comptePoint);
					}
				}
				
//			Query query = getEntityManager().createQuery("select f from TaComptePoint f " +
//					" where f.codeDocument like '"+doc.getCodeDocument()+"'" );
//			l = query.getResultList();
			}
			logger.debug("selectComptePointBonusFacture successful");
			return l;
		} catch (RuntimeException re) {
			logger.error("selectComptePointBonusFacture failed", re);
			throw re;
		}
	}
	public void removeTousLesPointsBonus(IDocumentTiers persistentInstance,boolean message) throws Exception {

		// ***TODO ISA **  a remettre après avoir trouver comment contourner le problème des liaison avec les points bonus pour autres utilisateur de BDG   ******
		try {	
			if(Platform.getBundle(TaComptePoint.TYPE_DOC)!=null){
				boolean continuer=!message;
				List<TaComptePoint> listeComptePoint=null;
				listeComptePoint=selectComptePointBonusFacture(persistentInstance);
				if(listeComptePoint!=null && listeComptePoint.size()>0){
					if(!continuer)continuer=MessageDialog.openQuestion(PlatformUI.getWorkbench().getDisplay().getActiveShell(), "document liée à points bonus", 
							"le document "+persistentInstance.getCodeDocument()+" est liée à "+listeComptePoint.size()+" ligne(s) de points bonus. Ils vont être supprimés. Voulez-vous continuer ?");
					if(continuer){
						for (TaComptePoint obj : listeComptePoint) {
							obj.setTaAvisEcheance(null);
							remove(obj);
						}
					}else throw new Exception();
				}
			}
		} catch (RuntimeException re) {
			logger.error("removeTousLesAbonnements failed", re);
			throw re;
		}		
	}
	
	
}
