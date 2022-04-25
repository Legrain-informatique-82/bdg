package fr.legrain.facture.divers;

import java.util.LinkedList;

import fr.legrain.document.divers.TypeDoc;
import fr.legrain.documents.dao.TaFacture;
import fr.legrain.documents.dao.TaRAcompte;
import fr.legrain.documents.dao.TaRAvoir;
import fr.legrain.gestCom.Module_Document.IHMEtat;
import fr.legrain.gestCom.Module_Document.IHMRDocument;

public class ModelRDocument {

	LinkedList<IHMRDocument> listeObjet = new LinkedList<IHMRDocument>();
	TypeDoc typeDocPresent = TypeDoc.getInstance();
	
	public LinkedList<IHMRDocument> remplirListeRAcompte(TaFacture taFacture) {
		IHMRDocument ihmRAcompte = null;
		listeObjet.clear();
		if(taFacture != null) {
			for(TaRAcompte rAcompte : taFacture.getTaRAcomptes()){
				if(rAcompte.getTaAcompte()!=null && !rAcompte.isEtatDeSuppression()){
					ihmRAcompte = new IHMRDocument();
					ihmRAcompte.setIdDocument(rAcompte.getTaAcompte().getIdDocument());
					ihmRAcompte.setCodeDocument(rAcompte.getTaAcompte().getCodeDocument());
					ihmRAcompte.setLibelleDocument(rAcompte.getTaAcompte().getLibelleDocument());
					ihmRAcompte.setTypeDocument(typeDocPresent.TYPE_ACOMPTE);
					ihmRAcompte.setId(rAcompte.getId());
//					ihmRAcompte.setResteARegler(rAcompte.getTaAcompte().getResteARegler());
					ihmRAcompte.setResteARegler(rAcompte.getTaAcompte().calculResteARegler().
							subtract(rAcompte.calculAffectationEnCours(taFacture)));
//					ihmRAcompte.setResteARegler(rAcompte.getTaAcompte().calculResteARegler());					
					ihmRAcompte.setAffectation(rAcompte.getAffectation());
					listeObjet.add(ihmRAcompte);
				}
			}
		}

		return listeObjet;
	}
	
	public LinkedList<IHMRDocument> remplirListeRAvoir(TaFacture taFacture) {
		IHMRDocument ihmRAvoir = null;
		listeObjet.clear();
		if(taFacture != null) {
			for(TaRAvoir rAvoir : taFacture.getTaRAvoirs()){
				if(rAvoir.getTaAvoir()!=null && (rAvoir.getEtat()&IHMEtat.suppression)==0
						&& (rAvoir.getEtat()&IHMEtat.integre)!=0){
					ihmRAvoir = new IHMRDocument();
					ihmRAvoir.setIdDocument(rAvoir.getTaAvoir().getIdDocument());
					ihmRAvoir.setCodeDocument(rAvoir.getTaAvoir().getCodeDocument());
					ihmRAvoir.setLibelleDocument(rAvoir.getTaAvoir().getLibelleDocument());
					ihmRAvoir.setTypeDocument(typeDocPresent.TYPE_AVOIR);
					ihmRAvoir.setId(rAvoir.getId());
//					ihmRAcompte.setResteARegler(rAcompte.getTaAcompte().getResteARegler());
					ihmRAvoir.setResteARegler(rAvoir.getTaAvoir().calculResteARegler().
							subtract(rAvoir.calculAffectationEnCours(taFacture)));
//					ihmRAcompte.setResteARegler(rAcompte.getTaAcompte().calculResteARegler());					
					ihmRAvoir.setAffectation(rAvoir.getAffectation());
					listeObjet.add(ihmRAvoir);
				}
			}
		}

		return listeObjet;
	}


	public LinkedList<IHMRDocument> getListeObjet() {
		return listeObjet;
	}

}
