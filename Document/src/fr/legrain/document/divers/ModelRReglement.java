package fr.legrain.document.divers;

import java.math.BigDecimal;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.EntityManager;

import fr.legrain.document.divers.TypeDoc;
import fr.legrain.documents.dao.LigneTva;
import fr.legrain.documents.dao.TaAcompte;
import fr.legrain.documents.dao.TaFacture;
import fr.legrain.documents.dao.TaRAcompte;
import fr.legrain.documents.dao.TaRAvoir;
import fr.legrain.documents.dao.TaRAvoirDAO;
import fr.legrain.documents.dao.TaRReglement;
import fr.legrain.documents.dao.TaRReglementDAO;
import fr.legrain.documents.dao.TaReglement;
import fr.legrain.documents.dao.TaReglementDAO;
import fr.legrain.gestCom.Module_Document.IDocumentTiersComplet;
import fr.legrain.gestCom.Module_Document.IHMEtat;
import fr.legrain.gestCom.Module_Document.IHMReglement;
import fr.legrain.lib.data.JPABdLgr;
import fr.legrain.tiers.dao.TaTiers;

public class ModelRReglement {

	LinkedList<IHMReglement> listeObjet = new LinkedList<IHMReglement>();
	TypeDoc typeDocPresent = TypeDoc.getInstance();
	List<TaRReglement> listeEntity = null;
	List<TaRAvoir> listeEntityAvoir = null;
	
	public LinkedList<IHMReglement> remplirListeReglementsFactureIntegres(TaFacture taFacture,EntityManager em) {
		IHMReglement ihmReglement = null;
		listeObjet.clear();
		if(taFacture != null) {
			for(TaRReglement taRReglement : taFacture.getTaRReglements() ){
				if(taRReglement.getTaFacture()!=null && (taRReglement.getEtatDeSuppression()&IHMEtat.suppression)==0
						&& (taRReglement.getTaReglement().getEtat()&IHMEtat.integre)!=0){
					ihmReglement = new IHMReglement();
					ihmReglement.setId(taRReglement.getId());
					ihmReglement.setCodeDocument(taRReglement.getTaReglement().getCodeDocument());
					ihmReglement.setIdDocument(taRReglement.getTaReglement().getIdDocument());
					if(taRReglement.getTaFacture()!=null)
						ihmReglement.setCodeFacture(taRReglement.getTaFacture().getCodeDocument());
					ihmReglement.setTypeDocument(typeDocPresent.TYPE_FACTURE);
					ihmReglement.setDateDocument(taRReglement.getTaReglement().getDateDocument());
					ihmReglement.setDateLivDocument(taRReglement.getTaReglement().getDateLivDocument());
					if(taRReglement.getTaReglement().getTaCompteBanque()!=null){
						ihmReglement.setIdCompteBanque(taRReglement.getTaReglement().getTaCompteBanque().getIdCompteBanque());
						ihmReglement.setCodeBanque(taRReglement.getTaReglement().getTaCompteBanque().getCodeBanque());
						ihmReglement.setCompte(taRReglement.getTaReglement().getTaCompteBanque().getCompte());
						ihmReglement.setCodeGuichet(taRReglement.getTaReglement().getTaCompteBanque().getCodeGuichet());
						ihmReglement.setCleRib(taRReglement.getTaReglement().getTaCompteBanque().getCleRib());
					}
					if(taRReglement.getTaReglement().getTaTPaiement()!=null){
						ihmReglement.setIdTPaiement(taRReglement.getTaReglement().getTaTPaiement().getIdTPaiement());
						ihmReglement.setCodeTPaiement(taRReglement.getTaReglement().getTaTPaiement().getCodeTPaiement());
					}
					ihmReglement.setEtat(taRReglement.getTaReglement().getEtat());
					ihmReglement.setExport(taRReglement.getTaReglement().getExport());
					ihmReglement.setLibelleDocument(taRReglement.getTaReglement().getLibelleDocument());
					ihmReglement.setAffectation(taRReglement.getAffectation());
					ihmReglement.setNetTtcCalc(taRReglement.getTaReglement().getNetTtcCalc());
//					reglement.getTaReglement().remonteListReglements();
					ihmReglement.setMulti(taRReglement.getTaReglement().getTaRReglements().size()>1);
					ihmReglement.setResteAAffecter(taRReglement.getTaReglement().getResteAAffecter());
					listeObjet.add(ihmReglement);
				}
			}
		}else{
			
		}
			

		return listeObjet;
	}
	
	public LinkedList<IHMReglement> remplirListeReglementsFactureTous(TaFacture taFacture) {
		IHMReglement ihmReglement = null;
		listeObjet.clear();
		//TaRReglementDAO taRReglementDAO = new TaRReglementDAO(em);
		if(taFacture != null) {
			//listeEntity=taRReglementDAO.selectAll(taFacture);
			for (TaRReglement rReglement : taFacture.getTaRReglements() ){
				if(rReglement.getTaFacture()!=null && (rReglement.getEtatDeSuppression()&IHMEtat.suppression)==0){
					ihmReglement = new IHMReglement();
					ihmReglement.setId(rReglement.getId());
					ihmReglement.setCodeDocument(rReglement.getTaReglement().getCodeDocument());
					ihmReglement.setIdDocument(rReglement.getTaReglement().getIdDocument());
					if(rReglement.getTaFacture()!=null)
						ihmReglement.setCodeFacture(rReglement.getTaFacture().getCodeDocument());
					ihmReglement.setTypeDocument(typeDocPresent.TYPE_FACTURE);
					ihmReglement.setDateDocument(rReglement.getTaReglement().getDateDocument());
					ihmReglement.setDateLivDocument(rReglement.getTaReglement().getDateLivDocument());
					if(rReglement.getTaReglement().getTaCompteBanque()!=null){
						ihmReglement.setIdCompteBanque(rReglement.getTaReglement().getTaCompteBanque().getIdCompteBanque());
						ihmReglement.setCodeBanque(rReglement.getTaReglement().getTaCompteBanque().getCodeBanque());
						ihmReglement.setCompte(rReglement.getTaReglement().getTaCompteBanque().getCompte());
						ihmReglement.setCodeGuichet(rReglement.getTaReglement().getTaCompteBanque().getCodeGuichet());
						ihmReglement.setCleRib(rReglement.getTaReglement().getTaCompteBanque().getCleRib());
					}
					if(rReglement.getTaReglement().getTaTPaiement()!=null){
						ihmReglement.setIdTPaiement(rReglement.getTaReglement().getTaTPaiement().getIdTPaiement());
						ihmReglement.setCodeTPaiement(rReglement.getTaReglement().getTaTPaiement().getCodeTPaiement());
					}
					ihmReglement.setEtat(rReglement.getTaReglement().getEtat());
					ihmReglement.setExport(rReglement.getTaReglement().getExport());
					ihmReglement.setLibelleDocument(rReglement.getTaReglement().getLibelleDocument());
					ihmReglement.setAffectation(rReglement.getAffectation());
					ihmReglement.setNetTtcCalc(rReglement.getTaReglement().getNetTtcCalc());
//					reglement.getTaReglement().remonteListReglements();
					ihmReglement.setMulti(rReglement.getTaReglement().getTaRReglements().size()>1);
					ihmReglement.setResteAAffecter(rReglement.getTaReglement().getResteAAffecter());
					listeObjet.add(ihmReglement);
				}
			}
			for (TaRAvoir rAvoir : taFacture.getTaRAvoirs() ){
				if(rAvoir.getTaFacture()!=null && (rAvoir.getEtat()&IHMEtat.suppression)==0){
					ihmReglement = new IHMReglement();
					ihmReglement.setId(rAvoir.getId());
					ihmReglement.setCodeDocument(rAvoir.getTaAvoir().getCodeDocument());
					ihmReglement.setIdDocument(rAvoir.getTaAvoir().getIdDocument());
					if(rAvoir.getTaFacture()!=null)
						ihmReglement.setCodeFacture(rAvoir.getTaFacture().getCodeDocument());
					ihmReglement.setTypeDocument(typeDocPresent.TYPE_AVOIR);
					ihmReglement.setDateDocument(rAvoir.getTaAvoir().getDateDocument());
					ihmReglement.setDateLivDocument(rAvoir.getTaAvoir().getDateLivDocument());
					if(rAvoir.getTaAvoir().getTaTPaiement()!=null){
						ihmReglement.setIdTPaiement(rAvoir.getTaAvoir().getTaTPaiement().getIdTPaiement());
						ihmReglement.setCodeTPaiement(rAvoir.getTaAvoir().getTaTPaiement().getCodeTPaiement());
					}
					ihmReglement.setEtat(rAvoir.getTaAvoir().getEtatDeSuppression());
					ihmReglement.setExport(rAvoir.getTaAvoir().getExport());
					ihmReglement.setLibelleDocument(rAvoir.getTaAvoir().getLibelleDocument());
					ihmReglement.setAffectation(rAvoir.getAffectation());
					ihmReglement.setNetTtcCalc(rAvoir.getTaAvoir().getNetTtcCalc());
//					reglement.getTaReglement().remonteListReglements();
					ihmReglement.setMulti(rAvoir.getTaAvoir().getTaRAvoirs().size()>1);
					ihmReglement.setResteAAffecter(rAvoir.getTaAvoir().getResteAAffecter());
					listeObjet.add(ihmReglement);
				}
			}	
			for (TaRAcompte rAcompte : taFacture.getTaRAcomptes() ){ //&& (rAcompte.getEtat()&IHMEtat.suppression)==0
				if(rAcompte.getTaFacture()!=null){
					ihmReglement = new IHMReglement();
					ihmReglement.setId(rAcompte.getId());
					ihmReglement.setCodeDocument(rAcompte.getTaAcompte().getCodeDocument());
					ihmReglement.setIdDocument(rAcompte.getTaAcompte().getIdDocument());
					if(rAcompte.getTaFacture()!=null)
						ihmReglement.setCodeFacture(rAcompte.getTaFacture().getCodeDocument());
					ihmReglement.setTypeDocument(typeDocPresent.TYPE_ACOMPTE);
					ihmReglement.setDateDocument(rAcompte.getTaAcompte().getDateDocument());
					ihmReglement.setDateLivDocument(rAcompte.getTaAcompte().getDateLivDocument());
					if(rAcompte.getTaAcompte().getTaTPaiement()!=null){
						ihmReglement.setIdTPaiement(rAcompte.getTaAcompte().getTaTPaiement().getIdTPaiement());
						ihmReglement.setCodeTPaiement(rAcompte.getTaAcompte().getTaTPaiement().getCodeTPaiement());
					}
					//ihmReglement.setEtat(rAcompte.getTaAcompte().getEtatDeSuppression());
					ihmReglement.setExport(rAcompte.getTaAcompte().getExport());
					ihmReglement.setLibelleDocument(rAcompte.getTaAcompte().getLibelleDocument());
					ihmReglement.setAffectation(rAcompte.getAffectation());
					ihmReglement.setNetTtcCalc(rAcompte.getTaAcompte().getNetTtcCalc());
//					reglement.getTaReglement().remonteListReglements();
					ihmReglement.setMulti(rAcompte.getTaAcompte().getTaRAcomptes().size()>1);
					ihmReglement.setResteAAffecter(rAcompte.getTaAcompte().getResteARegler());
					listeObjet.add(ihmReglement);
				}
			}
		}else{
			
		}
			

		return listeObjet;
	}

//	public LinkedList<IHMReglement> remplirListeReglementsFactureTous(IDocumentTiersComplet taFacture,EntityManager em) {
//		IHMReglement ihmReglement = null;
//		listeObjet.clear();
//		TaRReglementDAO taRReglementDAO = new TaRReglementDAO(em);
//		if(taFacture != null) {
//			listeEntity=taRReglementDAO.selectAll(taFacture);
//			for (TaRReglement reglement : listeEntity) {
//				if(reglement.getTaFacture()!=null && (reglement.getEtatDeSuppression()&IHMEtat.suppression)==0){
//					ihmReglement = new IHMReglement();
//					ihmReglement.setId(reglement.getId());
//					ihmReglement.setCodeReglement(reglement.getTaReglement().getCodeReglement());
//					ihmReglement.setIdDocument(reglement.getTaFacture().getIdDocument());
//					ihmReglement.setCodeDocument(reglement.getTaFacture().getCodeDocument());
//					ihmReglement.setTypeDocument(typeDocPresent.TYPE_FACTURE);
//					ihmReglement.setDateReglement(reglement.getTaReglement().getDateReglement());
//					ihmReglement.setDateEncaissement(reglement.getTaReglement().getDateEncaissement());
//					if(reglement.getTaReglement().getTaCompteBanque()!=null){
//						ihmReglement.setIdCompteBanque(reglement.getTaReglement().getTaCompteBanque().getIdCompteBanque());
//						ihmReglement.setCodeBanque(reglement.getTaReglement().getTaCompteBanque().getCodeBanque());
//						ihmReglement.setCompte(reglement.getTaReglement().getTaCompteBanque().getCompte());
//						ihmReglement.setCodeGuichet(reglement.getTaReglement().getTaCompteBanque().getCodeGuichet());
//						ihmReglement.setCleRib(reglement.getTaReglement().getTaCompteBanque().getCleRib());
//					}
//					if(reglement.getTaReglement().getTaTPaiement()!=null){
//						ihmReglement.setIdTPaiement(reglement.getTaReglement().getTaTPaiement().getIdTPaiement());
//						ihmReglement.setCodeTPaiement(reglement.getTaReglement().getTaTPaiement().getCodeTPaiement());
//					}
//					ihmReglement.setEtat(reglement.getTaReglement().getEtat());
//					ihmReglement.setExport(reglement.getTaReglement().getExport());
//					ihmReglement.setLibellePaiement(reglement.getTaReglement().getLibellePaiement());
//					ihmReglement.setAffectation(reglement.getAffectation());
//					ihmReglement.setMontantReglement(reglement.getTaReglement().getMontantReglement());
////					reglement.getTaReglement().remonteListReglements();
//					ihmReglement.setMulti(reglement.getTaReglement().getTaRReglements().size()>1);
//					ihmReglement.setResteAAffecter(reglement.getTaReglement().getResteAAffecter());
//					listeObjet.add(ihmReglement);
//				}
//			}
//		}else{
//			
//		}
//			
//
//		return listeObjet;
//	}

	public LinkedList<IHMReglement> remplirListeReglements(TaTiers taTiers,Date dateDeb,Date dateFin, EntityManager em) {
		IHMReglement ihmReglement = null;
		TaRReglementDAO taRReglementDAO = new TaRReglementDAO(em);
		TaRAvoirDAO taRAvoirDao = new TaRAvoirDAO(em);
		listeObjet.clear();
		listeEntity=taRReglementDAO.selectAll(taTiers,dateDeb,dateFin);
		listeEntityAvoir=taRAvoirDao.selectAll(taTiers,dateDeb,dateFin);
		if(listeEntity!=null){
			for (TaRReglement reglement : listeEntity) {
				ihmReglement = new IHMReglement();
				ihmReglement.setId(reglement.getId());
				ihmReglement.setCodeDocument(reglement.getTaReglement().getCodeDocument());
				ihmReglement.setIdDocument(reglement.getTaReglement().getIdDocument());
				if(reglement.getTaFacture()!=null)
					ihmReglement.setCodeFacture(reglement.getTaFacture().getCodeDocument());
				ihmReglement.setTypeDocument(typeDocPresent.TYPE_FACTURE);
				ihmReglement.setDateDocument(reglement.getTaReglement().getDateDocument());
				ihmReglement.setDateLivDocument(reglement.getTaReglement().getDateLivDocument());
				if(reglement.getTaReglement().getTaCompteBanque()!=null){
					ihmReglement.setIdCompteBanque(reglement.getTaReglement().getTaCompteBanque().getIdCompteBanque());
					ihmReglement.setCodeBanque(reglement.getTaReglement().getTaCompteBanque().getCodeBanque());
					ihmReglement.setCompte(reglement.getTaReglement().getTaCompteBanque().getCompte());
					ihmReglement.setCodeGuichet(reglement.getTaReglement().getTaCompteBanque().getCodeGuichet());
					ihmReglement.setCleRib(reglement.getTaReglement().getTaCompteBanque().getCleRib());
				}
				if(reglement.getTaReglement().getTaTPaiement()!=null){
					ihmReglement.setIdTPaiement(reglement.getTaReglement().getTaTPaiement().getIdTPaiement());
					ihmReglement.setCodeTPaiement(reglement.getTaReglement().getTaTPaiement().getCodeTPaiement());
				}
				ihmReglement.setEtat(reglement.getTaReglement().getEtat());
				ihmReglement.setExport(reglement.getTaReglement().getExport());
				ihmReglement.setLibelleDocument(reglement.getTaReglement().getLibelleDocument());
				ihmReglement.setAffectation(reglement.getAffectation());
				ihmReglement.setNetTtcCalc(reglement.getTaReglement().getNetTtcCalc());
				//					reglement.getTaReglement().remonteListReglements();
				ihmReglement.setMulti(reglement.getTaReglement().getTaRReglements().size()>1);
				ihmReglement.setResteAAffecter(reglement.getTaReglement().getResteAAffecter());
				listeObjet.add(ihmReglement);
			}
			for (TaRAvoir reglement : listeEntityAvoir) {
				ihmReglement = new IHMReglement();
				ihmReglement.setId(reglement.getId());
				ihmReglement.setCodeDocument(reglement.getTaAvoir().getCodeDocument());
				ihmReglement.setIdDocument(reglement.getTaAvoir().getIdDocument());
				if(reglement.getTaFacture()!=null)
					ihmReglement.setCodeFacture(reglement.getTaFacture().getCodeDocument());
				ihmReglement.setTypeDocument(typeDocPresent.TYPE_AVOIR);
				ihmReglement.setDateDocument(reglement.getTaAvoir().getDateDocument());
				ihmReglement.setDateLivDocument(reglement.getTaAvoir().getDateLivDocument());
				if(reglement.getTaAvoir().getTaTPaiement()!=null){
					ihmReglement.setIdTPaiement(reglement.getTaAvoir().getTaTPaiement().getIdTPaiement());
					ihmReglement.setCodeTPaiement(reglement.getTaAvoir().getTaTPaiement().getCodeTPaiement());
				}
				ihmReglement.setEtat(reglement.getTaAvoir().getEtatDeSuppression());
				ihmReglement.setExport(reglement.getTaAvoir().getExport());
				ihmReglement.setLibelleDocument(reglement.getTaAvoir().getLibelleDocument());
				ihmReglement.setAffectation(reglement.getAffectation());
				ihmReglement.setNetTtcCalc(reglement.getTaAvoir().getNetTtcCalc());
				ihmReglement.setMulti(reglement.getTaAvoir().getTaRAvoirs().size()>1);
				ihmReglement.setResteAAffecter(reglement.getTaAvoir().getResteAAffecter());
				listeObjet.add(ihmReglement);
			}			
		}

		return listeObjet;
	}



	public LinkedList<IHMReglement> razListeReglements() {
		listeObjet.clear();
		return listeObjet;
	}


	public LinkedList<IHMReglement> getListeObjet() {
		return listeObjet;
	}

}
