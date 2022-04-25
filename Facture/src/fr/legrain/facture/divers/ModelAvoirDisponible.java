package fr.legrain.facture.divers;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.EntityManager;

import fr.legrain.document.divers.TypeDoc;
import fr.legrain.documents.dao.TaAvoir;
import fr.legrain.documents.dao.TaAvoirDAO;
import fr.legrain.documents.dao.TaFacture;
import fr.legrain.documents.dao.TaRAvoir;
import fr.legrain.gestCom.Module_Document.IHMAideDocument;
import fr.legrain.gestCom.Module_Document.IHMEtat;

public class ModelAvoirDisponible {

	LinkedList<IHMAideDocument> listeObjet = new LinkedList<IHMAideDocument>();
	List<TaAvoir> listeEntity = null;

	TypeDoc typeDocPresent = TypeDoc.getInstance();
	
	public LinkedList<IHMAideDocument> remplirListe(TaFacture taDocument,EntityManager em) {
		TaAvoirDAO dao =new TaAvoirDAO(em);
		IHMAideDocument ihmAideDocument = null;
		listeObjet.clear();
//		if(taDocument!=null){
		listeEntity=dao.selectAllDisponible(taDocument);
		if(listeEntity != null) {
			for(TaAvoir document : listeEntity){
				if(!existDansMasterEntity(taDocument,document)){
					ihmAideDocument = new IHMAideDocument();
					ihmAideDocument.setIdDocument(document.getIdDocument());
					ihmAideDocument.setCodeDocument(document.getCodeDocument());
					ihmAideDocument.setLibelleDocument(document.getLibelleDocument());
					ihmAideDocument.setResteARegler(document.calculResteARegler());
					//ihmAideAcompte.setResteARegler(acompte.getResteARegler());
					ihmAideDocument.setNetTtcCalc(document.getNetTtcCalc());
					listeObjet.add(ihmAideDocument);
				}
			}
		}
		if(taDocument != null) {
			for(TaRAvoir rAvoir : taDocument.getTaRAvoirs()){
				if(rAvoir.getTaAvoir()!=null && (rAvoir.getEtat()&IHMEtat.suppression)!=0){
					ihmAideDocument = new IHMAideDocument();
					ihmAideDocument.setIdDocument(rAvoir.getTaAvoir().getIdDocument());
					ihmAideDocument.setCodeDocument(rAvoir.getTaAvoir().getCodeDocument());
					ihmAideDocument.setLibelleDocument(rAvoir.getTaAvoir().getLibelleDocument());
//					BigDecimal valeur =rAcompte.getTaAcompte().getNetTtcCalc().subtract(rAcompte.getTaAcompte().getRegleDocument()).subtract(rAcompte.getTaAcompte().calculSommeAcomptes());
//					ihmAideAcompte.setResteARegler(valeur.add(rAcompte.getTaAcompte().calculSommeAffectationAvecDocument(taDocument)));
					ihmAideDocument.setResteARegler(rAvoir.getTaAvoir().calculResteARegler());
							//.add(rAcompte.getAffectation()));
					ihmAideDocument.setNetTtcCalc(rAvoir.getTaAvoir().getNetTtcCalc());
					listeObjet.add(ihmAideDocument);
				}
			}
		}		
//		}
		return listeObjet;
	}
	

	public boolean existDansMasterEntity(TaFacture taDocument,TaAvoir avoir){
		if(taDocument !=null){//&& taRAcompte.getTaFacture()!=null 
			for (TaRAvoir taRAvoir : taDocument.getTaRAvoirs()) {
				if(taRAvoir.getTaAvoir()!=null 
						//&& !taRAcompte.isEtatDeSuppression() 
						&& taRAvoir.getTaAvoir().getIdDocument()==(avoir.getIdDocument())){
					return true;
				}
			}
		}
		return false;
	}
	
	public LinkedList<IHMAideDocument> getListeObjet() {
		return listeObjet;
	}

}
