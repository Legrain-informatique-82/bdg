package fr.legrain.facture.divers;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.EntityManager;

import fr.legrain.document.divers.TypeDoc;
import fr.legrain.documents.dao.TaAcompte;
import fr.legrain.documents.dao.TaAcompteDAO;
import fr.legrain.documents.dao.TaFacture;
import fr.legrain.documents.dao.TaRAcompte;
import fr.legrain.gestCom.Module_Document.IHMAideAcompte;

public class ModelAcompteDisponible {

	LinkedList<IHMAideAcompte> listeObjet = new LinkedList<IHMAideAcompte>();
	List<TaAcompte> listeEntity = null;

	TypeDoc typeDocPresent = TypeDoc.getInstance();
	
	public LinkedList<IHMAideAcompte> remplirListe(TaFacture taDocument,EntityManager em) {
		TaAcompteDAO dao =new TaAcompteDAO(em);
		IHMAideAcompte ihmAideAcompte = null;
		listeObjet.clear();
//		if(taDocument!=null){
		listeEntity=dao.selectAllDisponible(taDocument);
		if(listeEntity != null) {
			for(TaAcompte acompte : listeEntity){
				if(!existDansMasterEntity(taDocument,acompte)){
					ihmAideAcompte = new IHMAideAcompte();
					ihmAideAcompte.setIdDocument(acompte.getIdDocument());
					ihmAideAcompte.setCodeDocument(acompte.getCodeDocument());
					ihmAideAcompte.setLibelleDocument(acompte.getLibelleDocument());
					ihmAideAcompte.setResteAReglerComplet(acompte.calculResteARegler());
					//ihmAideAcompte.setResteARegler(acompte.getResteARegler());
					ihmAideAcompte.setNetTtcCalc(acompte.getNetTtcCalc());
					listeObjet.add(ihmAideAcompte);
				}
			}
		}
		if(taDocument != null) {
			for(TaRAcompte rAcompte : taDocument.getTaRAcomptes()){
				if(rAcompte.getTaAcompte()!=null && rAcompte.isEtatDeSuppression()){
					ihmAideAcompte = new IHMAideAcompte();
					ihmAideAcompte.setIdDocument(rAcompte.getTaAcompte().getIdDocument());
					ihmAideAcompte.setCodeDocument(rAcompte.getTaAcompte().getCodeDocument());
					ihmAideAcompte.setLibelleDocument(rAcompte.getTaAcompte().getLibelleDocument());
//					BigDecimal valeur =rAcompte.getTaAcompte().getNetTtcCalc().subtract(rAcompte.getTaAcompte().getRegleDocument()).subtract(rAcompte.getTaAcompte().calculSommeAcomptes());
//					ihmAideAcompte.setResteARegler(valeur.add(rAcompte.getTaAcompte().calculSommeAffectationAvecDocument(taDocument)));
					ihmAideAcompte.setResteAReglerComplet(rAcompte.getTaAcompte().calculResteARegler());
							//.add(rAcompte.getAffectation()));
					ihmAideAcompte.setNetTtcCalc(rAcompte.getTaAcompte().getNetTtcCalc());
					listeObjet.add(ihmAideAcompte);
				}
			}
		}		
//		}
		return listeObjet;
	}
	

	public boolean existDansMasterEntity(TaFacture taDocument,TaAcompte acompte){
		if(taDocument !=null){//&& taRAcompte.getTaFacture()!=null 
			for (TaRAcompte taRAcompte : taDocument.getTaRAcomptes()) {
				if(taRAcompte.getTaAcompte()!=null 
						//&& !taRAcompte.isEtatDeSuppression() 
						&& taRAcompte.getTaAcompte().getIdDocument()==(acompte.getIdDocument())){
					return true;
				}
			}
		}
		return false;
	}
	
	public LinkedList<IHMAideAcompte> getListeObjet() {
		return listeObjet;
	}

}
