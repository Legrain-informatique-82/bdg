package fr.legrain.saisieCaisse.dao;

// Generated 2 juin 2009 14:13:00 by Hibernate Tools 3.2.4.GA

import java.math.BigDecimal;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import fr.legrain.articles.dao.TaUnite;
import fr.legrain.documents.dao.TaInfosApporteur;
import fr.legrain.gestCom.Appli.AbstractApplicationDAO;
import fr.legrain.gestCom.Appli.Const;
import fr.legrain.lib.data.LibCalcul;
import fr.legrain.lib.data.LibConversion;
import fr.legrain.lib.data.LibDate;

/**
 * Home object for domain model class TaOperation.
 * @see fr.legrain.saisieCaisse.dao.TaOperation
 * @author Hibernate Tools
 */
public class TaOperationDAO extends AbstractApplicationDAO<TaOperation> {

	private static final Log logger = LogFactory.getLog(TaOperationDAO.class);
	private TaEtablissement masterEntity ;
	private String defaultJPQLQuery = "select u from TaOperation u ";
	
	public TaOperationDAO(){
		this(null);
	}
	
	public TaOperationDAO(EntityManager em){
		if(em!=null) {
			setEm(em);
		}
//		champIdTable=ctrlGeneraux.getID_TABLE(TaOperation.class.getSimpleName());
		initChampId(TaOperation.class);
		setJPQLQuery(defaultJPQLQuery);
		setListeChampMaj(Const.C_FICHIER_INI_CHAMPMAJ);
		initNomTable(new TaOperation());
	}
	
	public void persist(TaOperation transientInstance) {
		logger.debug("persisting TaOperation instance");
		try {
			getEntityManager().persist(transientInstance);
			logger.debug("persist successful");
		} catch (RuntimeException re) {
			logger.error("persist failed", re);
			throw re;
		}
	}

	public TaOperation refresh(TaOperation detachedInstance) {
		logger.debug("refresh instance");
		try {
////			getEntityManager().refresh(detachedInstance);
////			logger.debug("refresh successful");
//			org.hibernate.Session session = (org.hibernate.Session) getEntityManager().getDelegate();
//		    session.evict(detachedInstance);
//		    detachedInstance = getEntityManager().find(TaOperation.class, detachedInstance.getIdOperation());
		    return detachedInstance;
		} catch (RuntimeException re) {
			logger.error("refresh failed", re);
			throw re;
		}
	}
	
	public void remove(TaOperation persistentInstance) {
		logger.debug("removing TaOperation instance");
		try {
			getEntityManager().remove(persistentInstance);
			logger.debug("remove successful");
		} catch (RuntimeException re) {
			logger.error("remove failed", re);
			throw re;
		}
	}

	public TaOperation merge(TaOperation detachedInstance) {
		logger.debug("merging TaOperation instance");
		try {
			TaOperation result = getEntityManager().merge(detachedInstance);
			logger.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			logger.error("merge failed", re);
			throw re;
		}
	}

	public TaOperation findById(int id) {
		logger.debug("getting TaOperation instance with id: " + id);
		try {
			TaOperation instance = getEntityManager().find(TaOperation.class, id);
			logger.debug("get successful");
			return instance;
		} catch (RuntimeException re) {
			logger.error("get failed", re);
			throw re;
		}
	}

	@Override
	public List<TaOperation> selectAll() {
		logger.debug("TaOperation TaDepot");
		try {
			if(masterEntity!=null)
			  JPQLQuery = "select u from TaOperation u where u.taEtablissement.idEtablissement like "+getMasterEntity().getIdEtablissement();
			else
				JPQLQuery =defaultJPQLQuery;
			Query ejbQuery = getEntityManager().createQuery(JPQLQuery);
			List<TaOperation> l = ejbQuery.getResultList();
			logger.debug("selectAll successful");
			return l;
		} catch (RuntimeException re) {
			logger.error("selectAll failed", re);
			throw re;
		}
	}
	
	public List<TaOperation> selectOperationDate(TaCriteresSaisieCaisse criteres,String fixeTAchat) {
		try {
			
			String queryOperationDate = "select o from TaOperation o  " ;
			queryOperationDate +=" where o.taTOperation.codeTOperation not like '"+fixeTAchat+"%'" +
					" and extract(year from o.dateOperation) = ? " +
					" and extract(Month from o.dateOperation) =  ?" +
					" and extract(day from o.dateOperation) = ?";

			if(criteres.getTaEtablissement()!=null )
				queryOperationDate+=" and o.taEtablissement.idEtablissement like '"+criteres.getTaEtablissement().getIdEtablissement()+"'";
			if(criteres.getCodeTOperation()!=null && !criteres.getCodeTOperation().equals(""))
				queryOperationDate+=" and o.taTOperation.codeTOperation like '"+criteres.getCodeTOperation()+"'";
			if(criteres.getCodeTPaiement()!=null && !criteres.getCodeTPaiement().equals(""))
				queryOperationDate+=" and o.taTPaiement.codeTPaiement like '"+criteres.getCodeTPaiement()+"'";
//		
			queryOperationDate+=" order by o.dateOperation,o.taTPaiement.codeTPaiement,o.taTOperation.codeTOperation";
			Query ejbQuery = getEntityManager().createQuery(queryOperationDate);
			ejbQuery.setParameter(1,LibConversion.stringToInteger(LibDate.getAnnee(criteres.getDateDeb())));
			ejbQuery.setParameter(2,LibConversion.stringToInteger(LibDate.getMois(criteres.getDateDeb())));
			ejbQuery.setParameter(3,LibConversion.stringToInteger(LibDate.getJour(criteres.getDateDeb())));
			List<TaOperation> l = ejbQuery.getResultList();
			return l;
		} catch (RuntimeException re) {
			logger.error("selectOperationDate failed", re);
			throw re;
		}
	}
	
	public List<TaOperation> selectOperationExportationDate(TaCriteresSaisieCaisse criteres,String fixeTAchat) {
		try {
			//selection des opérations "hors achats"
			String queryOperationDate = "select o from TaOperation o  " ;
			queryOperationDate +=" where o.taTOperation.codeTOperation not like '"+fixeTAchat+"%'" +
			" and o.dateOperation <= ?" ;

			if(criteres.getTaEtablissement()!=null )
				queryOperationDate+=" and o.taEtablissement.idEtablissement like '"+criteres.getTaEtablissement().getIdEtablissement()+"'";
			if(criteres.getCodeTOperation()!=null && !criteres.getCodeTOperation().equals(""))
				queryOperationDate+=" and o.taTOperation.codeTOperation like '"+criteres.getCodeTOperation()+"'";
			if(criteres.getCodeTPaiement()!=null && !criteres.getCodeTPaiement().equals(""))
				queryOperationDate+=" and o.taTPaiement.codeTPaiement like '"+criteres.getCodeTPaiement()+"'";
//		
			queryOperationDate+=" order by o.dateOperation,o.taTPaiement.codeTPaiement,o.taTOperation.codeTOperation";
			Query ejbQuery = getEntityManager().createQuery(queryOperationDate);
			ejbQuery.setParameter(1,criteres.getDateDeb());
			List<TaOperation> l = ejbQuery.getResultList();
			
			//selection des opérations "d'achat uniquement"
			queryOperationDate = "select o from TaOperation o  " ;
			queryOperationDate +=" where o.taTOperation.codeTOperation like '"+fixeTAchat+"%'" +
			" and o.dateOperation <= ?" ;

			if(criteres.getTaEtablissement()!=null )
				queryOperationDate+=" and o.taEtablissement.idEtablissement like '"+criteres.getTaEtablissement().getIdEtablissement()+"'";
			if(criteres.getCodeTOperation()!=null && !criteres.getCodeTOperation().equals(""))
				queryOperationDate+=" and o.taTOperation.codeTOperation like '"+criteres.getCodeTOperation()+"'";
			if(criteres.getCodeTPaiement()!=null && !criteres.getCodeTPaiement().equals(""))
				queryOperationDate+=" and o.taTPaiement.codeTPaiement like '"+criteres.getCodeTPaiement()+"'";
//		
			queryOperationDate+=" order by o.dateOperation,o.taTPaiement.codeTPaiement,o.taTOperation.codeTOperation";
			ejbQuery = getEntityManager().createQuery(queryOperationDate);
			ejbQuery.setParameter(1,criteres.getDateDeb());
			List<TaOperation> l2 = ejbQuery.getResultList();
			for (TaOperation taOperation : l2) {
				l.add(taOperation);
			}
			return l;
		} catch (RuntimeException re) {
			logger.error("selectOperationDate failed", re);
			throw re;
		}
	}
	
	public List<TaSumOperation> selectSumOperationDate(TaCriteresSaisieCaisse criteres,String fixeTAchat) {
		try {			
			String queryOperationDate = "select NEW fr.legrain.saisieCaisse.dao.TaSumOperation(" +
					" o.dateOperation, "+
					" o.taTPaiement.codeTPaiement,o.taTOperation.codeTOperation," +
					" sum(o.montantOperation)) from TaOperation o  " ;
			queryOperationDate +=" where o.taEtablissement.idEtablissement like '"+criteres.getTaEtablissement().getIdEtablissement()+"'" +
					" and extract(year from o.dateOperation) = ? " +
					" and extract(Month from o.dateOperation) =  ?" +
					" and o.taTOperation.codeTOperation not like '"+fixeTAchat+"%'" ;
 
			if(criteres.getCodeTOperation()!=null && !criteres.getCodeTOperation().equals(""))
				queryOperationDate+=" and o.taTOperation.codeTOperation like '"+criteres.getCodeTOperation()+"'";
			if(criteres.getCodeTPaiement()!=null && !criteres.getCodeTPaiement().equals(""))
				queryOperationDate+=" and o.taTPaiement.codeTPaiement like '"+criteres.getCodeTPaiement()+"'";
			
			queryOperationDate+=" group by o.dateOperation,o.taTPaiement.codeTPaiement,o.taTOperation.codeTOperation";
			queryOperationDate+=" order by o.dateOperation,o.taTPaiement.codeTPaiement,o.taTOperation.codeTOperation";
			Query ejbQuery = getEntityManager().createQuery(queryOperationDate);
			ejbQuery.setParameter(1,LibConversion.stringToInteger(LibDate.getAnnee(criteres.getDateDeb())));
			ejbQuery.setParameter(2,LibConversion.stringToInteger(LibDate.getMois(criteres.getDateDeb())));
			List<TaSumOperation> l = ejbQuery.getResultList();
			return l;
		} catch (RuntimeException re) {
			logger.error("selectSumOperationDate failed", re);
			throw re;
		}
	}
	
	
	public LinkedList<TaAchatTTC> selectAchat(Date dateDeb,Date dateFin,String fixeTAchat,TaEtablissement taEtablissement) {
		try {
			String requete = " select new fr.legrain.saisieCaisse.dao.TaAchatTTC(" +
					" u.dateOperation,sum(u.montantOperation),sum(u.tva))" +
					" from TaOperation u " +
					" where u.taEtablissement.idEtablissement like '"+taEtablissement.getIdEtablissement()+"'" +
					" and u.taTOperation.codeTOperation like ? " +
					" and u.dateOperation between ? and ? " +
					"group by u.dateOperation" ;
			
			Query ejbQuery = getEntityManager().createQuery(requete);
			ejbQuery.setParameter(1, fixeTAchat+"%");
			ejbQuery.setParameter(2,dateDeb);
			ejbQuery.setParameter(3,dateFin);

			/**
			 * isabelle
			 */
			//List<TaAchatTTC> l = ejbQuery.getResultList();
			/**
			 * lee
			 */
			LinkedList<TaAchatTTC> l = new LinkedList<TaAchatTTC>();
			
			for (int i = 0; i < ejbQuery.getResultList().size(); i++) {
				TaAchatTTC taAchatTTC = new TaAchatTTC();
				taAchatTTC = (TaAchatTTC) ejbQuery.getResultList().get(i);
				l.add(taAchatTTC);
			}
			
			return l;
		} catch (RuntimeException re) {
			logger.error("selectAchatJour failed", re);
			throw re;
		}
	}
	
	public List<TaSumDepot> selectReportJournaux(Date dateReport,Date jour,String journalCaisse,
			TaEtablissement taEtablissement,String fixeAchat) {
		try {
			TaReportTPaiementDAO daoReport = new TaReportTPaiementDAO();			
			Date dateDeb =dateReport ;
			if(dateDeb==null)dateDeb=new Date(0);
			String requete;
			Query ejbQuery;
			List<TaSumDepot> l= new LinkedList<TaSumDepot>();
			List<TaReportTPaiement> listTaReportTPaiement = new LinkedList<TaReportTPaiement>();
			List<TaSumDepot> listeReport = new LinkedList<TaSumDepot>();
			List<TaSumDepot> listDepot = new LinkedList<TaSumDepot>();
			List<TaSumDepot> listOperation = new LinkedList<TaSumDepot>();
			List<TaSumDepot> listAchats = new LinkedList<TaSumDepot>();
			
			listTaReportTPaiement = daoReport.selectAll();
			for (TaReportTPaiement v : listTaReportTPaiement) {
				TaSumDepot sumDepot =new TaSumDepot();
				sumDepot.setCodeTPaiement(v.getTaTPaiement().getCodeTPaiement());
				sumDepot.setLiblTPaiement(v.getTaTPaiement().getLibTPaiement());
				sumDepot.setMontantOperation(v.getMontantReport());
				listeReport.add(sumDepot);
			}
			
			requete = "select DISTINCT  NEW fr.legrain.saisieCaisse.dao.TaSumDepot(" +
			" o.taTPaiement.codeTPaiement,o.taTPaiement.libTPaiement," +
			" sum(o.montantOperation)) " +
			" from TaOperation o " +
			" where  o.taEtablissement.idEtablissement like " +taEtablissement.getIdEtablissement()+
			" and o.taTOperation.codeTOperation not like '"+fixeAchat+"%'" +
			" and o.taTPaiement.codeTPaiement like ('"+journalCaisse+"')" +
			" and o.dateOperation between ? and ? " +
			" group by o.taTPaiement.codeTPaiement,o.taTPaiement.libTPaiement"  ;

			ejbQuery = getEntityManager().createQuery(requete);
			ejbQuery.setParameter(1, dateDeb);
			ejbQuery.setParameter(2, jour);
			listOperation = ejbQuery.getResultList();
			
			for (TaSumDepot v1 : listOperation) {
				int i=0;
				boolean trouve=false;
				while (!trouve && i<listeReport.size()){
					TaSumDepot v2 = listeReport.get(i);
					if(v2.getCodeTPaiement().equals(v1.getCodeTPaiement())){
						BigDecimal value1=BigDecimal.valueOf(0);
						if(v1.getMontantOperation()!=null)
							value1=v1.getMontantOperation();
						BigDecimal value2=BigDecimal.valueOf(0);
						if(v2.getMontantOperation()!=null)
							value2=v2.getMontantOperation();
						v1.setMontantOperation(value1.add(value2));
						trouve=true;
						if(!l.contains(v1))
							l.add(v1);
						listeReport.remove(v2);
					}
					i++;
				}
				if(!trouve && !l.contains(v1)) 
					l.add(v1);
			}
			for (TaSumDepot v2 : listeReport) {
				l.add(v2);
				}

			requete = " select DISTINCT  NEW fr.legrain.saisieCaisse.dao.TaSumDepot(" +
			" o.taTPaiement.codeTPaiement,o.taTPaiement.libTPaiement," +
			" - sum(o.montantOperation)) " +
			" from TaOperation o " +
			" where  o.taEtablissement.idEtablissement like " +taEtablissement.getIdEtablissement()+
			" and o.taTOperation.codeTOperation like '"+fixeAchat+"%'" +
			" and o.taTPaiement.codeTPaiement like ('"+journalCaisse+"')" +
			" and o.dateOperation between ? and ? " +
			" group by o.taTPaiement.codeTPaiement,o.taTPaiement.libTPaiement"  ;


			ejbQuery = getEntityManager().createQuery(requete);
			ejbQuery.setParameter(1, dateDeb);
			ejbQuery.setParameter(2, jour);
			listAchats = ejbQuery.getResultList();
			
			for (TaSumDepot v1 : listOperation) {
				int i=0;
				boolean trouve=false;
				while (!trouve && i<listAchats.size()){
					TaSumDepot v2 = listAchats.get(i);
					if(v2.getCodeTPaiement().equals(v1.getCodeTPaiement())){
						BigDecimal value1=BigDecimal.valueOf(0);
						if(v1.getMontantOperation()!=null)
							value1=v1.getMontantOperation();
						BigDecimal value2=BigDecimal.valueOf(0);
						if(v2.getMontantOperation()!=null)
							value2=v2.getMontantOperation();
						v1.setMontantOperation(value1.add(value2));
						trouve=true;
						if(!l.contains(v1))
							l.add(v1);
						listAchats.remove(v2);
					}
					i++;
				}
				if(!trouve && !l.contains(v1)) 
					l.add(v1);
			}
			for (TaSumDepot v2 : listAchats) {
				l.add(v2);
				}
			
			requete = " select DISTINCT  NEW fr.legrain.saisieCaisse.dao.TaSumDepot(" +
			" d.taTPaiement.codeTPaiement,d.taTPaiement.libTPaiement," +
			" sum(d.montantDepot)) " +
			" from TaDepot d " +
			" where d.taEtablissement.idEtablissement like " +taEtablissement.getIdEtablissement()+
			" and d.taTPaiement.codeTPaiement like ('"+journalCaisse+"')" +
			" and d.dateDepot between ? and ? " +
			" group by d.taTPaiement.codeTPaiement,d.taTPaiement.libTPaiement" ;	
			
			
			ejbQuery = getEntityManager().createQuery(requete);
			ejbQuery.setParameter(1, dateDeb);
			ejbQuery.setParameter(2, jour);
			listDepot = ejbQuery.getResultList();

			
			for (TaSumDepot v1 : listOperation) {
				int i=0;
				boolean trouve=false;
				while (!trouve && i<listDepot.size()){
					TaSumDepot v2 = listDepot.get(i);
					if(v2.getCodeTPaiement().equals(v1.getCodeTPaiement())){
						BigDecimal value1=BigDecimal.valueOf(0);
						if(v1.getMontantOperation()!=null)
							value1=v1.getMontantOperation();
						BigDecimal value2=BigDecimal.valueOf(0);
						if(v2.getMontantOperation()!=null)
							value2=v2.getMontantOperation();
						v1.setMontantOperation(value1.subtract(value2));
						trouve=true;
						if(!l.contains(v1))
							l.add(v1);
						listDepot.remove(v2);
					}
					i++;
				}
				if(!trouve && !l.contains(v1)) 
					l.add(v1);
			}
			for (TaSumDepot v2 : listDepot) {
				l.add(v2);
				}
			
			return l;
		} catch (RuntimeException re) {
			logger.error("selectReportFond failed", re);
			throw re;
		}
	}
	
	public List<TaOperation> selectOperationMois(TaCriteresSaisieCaisse criteres,String fixeTAchat) {
		try {
			
			String queryOperationDate = "select o from TaOperation o  " ;
			//(EXTRACT(MONTH FROM CMD_DATE) = 9)
			queryOperationDate +=" where o.taTOperation.codeTOperation not like '"+fixeTAchat+"%'" +
					" and EXTRACT(MONTH FROM o.dateOperation)= ? ";

			if(criteres.getTaEtablissement()!=null )
				queryOperationDate+=" and o.taEtablissement.idEtablissement like '"+criteres.getTaEtablissement().getIdEtablissement()+"'";
			if(criteres.getCodeTOperation()!=null && !criteres.getCodeTOperation().equals(""))
				queryOperationDate+=" and o.taTOperation.codeTOperation like '"+criteres.getCodeTOperation()+"'";
			if(criteres.getCodeTPaiement()!=null && !criteres.getCodeTPaiement().equals(""))
				queryOperationDate+=" and o.taTPaiement.codeTPaiement like '"+criteres.getCodeTPaiement()+"'";
//		
			queryOperationDate+=" order by o.dateOperation,o.taTOperation.codeTOperation,o.taTPaiement.codeTPaiement";
			Query ejbQuery = getEntityManager().createQuery(queryOperationDate);
			ejbQuery.setParameter(1, criteres.getMois());
			List<TaOperation> l = ejbQuery.getResultList();
			return l;
		} catch (RuntimeException re) {
			logger.error("selectOperationDate failed", re);
			throw re;
		}
	}

	public TaEtablissement getMasterEntity() {
		return masterEntity;
	}

	public void setMasterEntity(TaEtablissement masterEntity) {
		this.masterEntity = masterEntity;
	}
}
