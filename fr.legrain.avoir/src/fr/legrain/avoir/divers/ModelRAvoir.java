package fr.legrain.avoir.divers;

import java.util.LinkedList;

import javax.persistence.EntityManager;

import fr.legrain.document.divers.TypeDoc;
import fr.legrain.documents.dao.TaAcompte;
import fr.legrain.documents.dao.TaAvoir;
import fr.legrain.documents.dao.TaRAcompte;
import fr.legrain.documents.dao.TaRAvoir;
import fr.legrain.gestCom.Module_Document.IHMRDocumentReduit;
import fr.legrain.gestCom.Module_Document.IHMRDocument;

public class ModelRAvoir {

	LinkedList<IHMRDocumentReduit> listeObjetReduit = new LinkedList<IHMRDocumentReduit>();
	LinkedList<IHMRDocument> listeObjet = new LinkedList<IHMRDocument>();
	TypeDoc typeDocPresent = TypeDoc.getInstance();
	
	public LinkedList<IHMRDocumentReduit> remplirListe(TaAvoir taAvoir,EntityManager em) {
		IHMRDocumentReduit ihmRAcompte = null;
		listeObjetReduit.clear();
		if(taAvoir != null) {
			for(TaRAvoir rDocument : taAvoir.getTaRAvoirs()){
				if(rDocument.getTaFacture()!=null && 
						typeDocPresent.getTypeDocCompletPresent().containsKey(typeDocPresent.TYPE_FACTURE_BUNDLEID)){	
					ihmRAcompte = new IHMRDocumentReduit();
					ihmRAcompte.setIdDocument(rDocument.getTaFacture().getIdDocument());
					ihmRAcompte.setCodeDocument(rDocument.getTaFacture().getCodeDocument());
					ihmRAcompte.setLibelleDocument(rDocument.getTaFacture().getLibelleDocument());
					ihmRAcompte.setTypeDocument(typeDocPresent.TYPE_FACTURE);
					ihmRAcompte.setId(rDocument.getId());
					ihmRAcompte.setTaAvoir(rDocument.getTaAvoir());
					listeObjetReduit.add(ihmRAcompte);
				}
	
			}
		}
		return listeObjetReduit;
	}
	
	public LinkedList<IHMRDocument> remplirListeFactures(TaAvoir taAvoir,EntityManager em) {
		IHMRDocument ihmRDocument = null;
		listeObjet.clear();
		if(taAvoir != null) {
			for(TaRAvoir rAvoir : taAvoir.getTaRAvoirs()){
				if(rAvoir.getTaFacture()!=null){
					ihmRDocument = new IHMRDocument();
					ihmRDocument.setIdDocument(rAvoir.getTaFacture().getIdDocument());
					ihmRDocument.setCodeDocument(rAvoir.getTaFacture().getCodeDocument());
					ihmRDocument.setLibelleDocument(rAvoir.getTaFacture().getLibelleDocument());
					ihmRDocument.setTypeDocument(typeDocPresent.TYPE_FACTURE);
					ihmRDocument.setId(rAvoir.getId());
					ihmRDocument.setTaAvoir(rAvoir.getTaAvoir());
					ihmRDocument.setResteARegler(rAvoir.getTaFacture().getResteAReglerComplet());
					ihmRDocument.setAffectation(rAvoir.getAffectation());
					listeObjet.add(ihmRDocument);
				}
			}
		}
		return listeObjet;
	}

	public LinkedList<IHMRDocument> getListeObjet() {
		return listeObjet;
	}

	public LinkedList<IHMRDocumentReduit> getListeObjetReduit() {
		return listeObjetReduit;
	}

}
