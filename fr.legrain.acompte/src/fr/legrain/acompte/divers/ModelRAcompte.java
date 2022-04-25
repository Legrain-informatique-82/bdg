package fr.legrain.acompte.divers;

import java.util.LinkedList;

import javax.persistence.EntityManager;

import fr.legrain.document.divers.TypeDoc;
import fr.legrain.documents.dao.TaAcompte;
import fr.legrain.documents.dao.TaRAcompte;
import fr.legrain.gestCom.Module_Document.IHMRDocumentReduit;
import fr.legrain.gestCom.Module_Document.IHMRDocument;

public class ModelRAcompte {

	LinkedList<IHMRDocumentReduit> listeObjetReduit = new LinkedList<IHMRDocumentReduit>();
	LinkedList<IHMRDocument> listeObjet = new LinkedList<IHMRDocument>();
	TypeDoc typeDocPresent = TypeDoc.getInstance();
	
	public LinkedList<IHMRDocumentReduit> remplirListe(TaAcompte taAcompte,EntityManager em) {
		IHMRDocumentReduit ihmRAcompte = null;
		listeObjetReduit.clear();
		if(taAcompte != null) {
			for(TaRAcompte rAcompte : taAcompte.getTaRAcomptes()){
				if(rAcompte.getTaDevis()!=null && 
						typeDocPresent.getTypeDocPresentSelectionAcompte().containsKey(typeDocPresent.TYPE_DEVIS_BUNDLEID)){				
					ihmRAcompte = new IHMRDocumentReduit();
					ihmRAcompte.setIdDocument(rAcompte.getTaDevis().getIdDocument());
					ihmRAcompte.setCodeDocument(rAcompte.getTaDevis().getCodeDocument());
					ihmRAcompte.setLibelleDocument(rAcompte.getTaDevis().getLibelleDocument());
					ihmRAcompte.setTypeDocument(typeDocPresent.TYPE_DEVIS);
					ihmRAcompte.setId(rAcompte.getId());
					ihmRAcompte.setTaAcompte(rAcompte.getTaAcompte());
					listeObjetReduit.add(ihmRAcompte);
				}
				if(rAcompte.getTaBoncde()!=null && 
						typeDocPresent.getTypeDocPresentSelectionAcompte().containsKey(typeDocPresent.TYPE_BON_COMMANDE_BUNDLEID)){	
					ihmRAcompte = new IHMRDocumentReduit();
					ihmRAcompte.setIdDocument(rAcompte.getTaBoncde().getIdDocument());
					ihmRAcompte.setCodeDocument(rAcompte.getTaBoncde().getCodeDocument());
					ihmRAcompte.setLibelleDocument(rAcompte.getTaBoncde().getLibelleDocument());
					ihmRAcompte.setTypeDocument(typeDocPresent.TYPE_BON_COMMANDE);
					ihmRAcompte.setId(rAcompte.getId());
					ihmRAcompte.setTaAcompte(rAcompte.getTaAcompte());
					listeObjetReduit.add(ihmRAcompte);
				}
				if(rAcompte.getTaProforma()!=null && 
						typeDocPresent.getTypeDocPresentSelectionAcompte().containsKey(typeDocPresent.TYPE_PROFORMA_BUNDLEID)){	
					ihmRAcompte = new IHMRDocumentReduit();
					ihmRAcompte.setIdDocument(rAcompte.getTaProforma().getIdDocument());
					ihmRAcompte.setCodeDocument(rAcompte.getTaProforma().getCodeDocument());
					ihmRAcompte.setLibelleDocument(rAcompte.getTaProforma().getLibelleDocument());
					ihmRAcompte.setTypeDocument(typeDocPresent.TYPE_PROFORMA);
					ihmRAcompte.setId(rAcompte.getId());
					ihmRAcompte.setTaAcompte(rAcompte.getTaAcompte());
					listeObjetReduit.add(ihmRAcompte);
				}
				if(rAcompte.getTaFacture()!=null && 
						typeDocPresent.getTypeDocPresentSelectionAcompte().containsKey(typeDocPresent.TYPE_FACTURE_BUNDLEID)){	
					ihmRAcompte = new IHMRDocumentReduit();
					ihmRAcompte.setIdDocument(rAcompte.getTaFacture().getIdDocument());
					ihmRAcompte.setCodeDocument(rAcompte.getTaFacture().getCodeDocument());
					ihmRAcompte.setLibelleDocument(rAcompte.getTaFacture().getLibelleDocument());
					ihmRAcompte.setTypeDocument(typeDocPresent.TYPE_FACTURE);
					ihmRAcompte.setId(rAcompte.getId());
					ihmRAcompte.setTaAcompte(rAcompte.getTaAcompte());
					listeObjetReduit.add(ihmRAcompte);
				}
				if(rAcompte.getTaApporteur()!=null && 
						typeDocPresent.getTypeDocPresentSelectionAcompte().containsKey(typeDocPresent.TYPE_APPORTEUR_BUNDLEID)){	
					ihmRAcompte = new IHMRDocumentReduit();
					ihmRAcompte.setIdDocument(rAcompte.getTaApporteur().getIdDocument());
					ihmRAcompte.setCodeDocument(rAcompte.getTaApporteur().getCodeDocument());
					ihmRAcompte.setLibelleDocument(rAcompte.getTaApporteur().getLibelleDocument());
					ihmRAcompte.setTypeDocument(typeDocPresent.TYPE_APPORTEUR);
					ihmRAcompte.setId(rAcompte.getId());
					ihmRAcompte.setTaAcompte(rAcompte.getTaAcompte());
					listeObjetReduit.add(ihmRAcompte);
				}
				if(rAcompte.getTaAvoir()!=null && 
						typeDocPresent.getTypeDocPresentSelectionAcompte().containsKey(typeDocPresent.TYPE_AVOIR_BUNDLEID)){	
					ihmRAcompte = new IHMRDocumentReduit();
					ihmRAcompte.setIdDocument(rAcompte.getTaAvoir().getIdDocument());
					ihmRAcompte.setCodeDocument(rAcompte.getTaAvoir().getCodeDocument());
					ihmRAcompte.setLibelleDocument(rAcompte.getTaAvoir().getLibelleDocument());
					ihmRAcompte.setTypeDocument(typeDocPresent.TYPE_AVOIR);
					ihmRAcompte.setId(rAcompte.getId());
					ihmRAcompte.setTaAcompte(rAcompte.getTaAcompte());
					listeObjetReduit.add(ihmRAcompte);
				}
				if(rAcompte.getTaBonliv()!=null && 
						typeDocPresent.getTypeDocPresentSelectionAcompte().containsKey(typeDocPresent.TYPE_BON_LIVRAISON_BUNDLEID)){	
					ihmRAcompte = new IHMRDocumentReduit();
					ihmRAcompte.setIdDocument(rAcompte.getTaBonliv().getIdDocument());
					ihmRAcompte.setCodeDocument(rAcompte.getTaBonliv().getCodeDocument());
					ihmRAcompte.setLibelleDocument(rAcompte.getTaBonliv().getLibelleDocument());
					ihmRAcompte.setTypeDocument(typeDocPresent.TYPE_BON_LIVRAISON);
					ihmRAcompte.setId(rAcompte.getId());
					ihmRAcompte.setTaAcompte(rAcompte.getTaAcompte());
					listeObjetReduit.add(ihmRAcompte);
				}
				
			}
		}
		return listeObjetReduit;
	}
	
	public LinkedList<IHMRDocument> remplirListeFactures(TaAcompte taAcompte,EntityManager em) {
//		TaRAcompteDAO dao =new TaRAcompteDAO(em);
		//		List<TaRAcompte> listRAcompte =dao.selectAll(taAcompte);
		IHMRDocument ihmRAcompte = null;
		listeObjet.clear();
		if(taAcompte != null) {
			for(TaRAcompte rAcompte : taAcompte.getTaRAcomptes()){
				if(rAcompte.getTaFacture()!=null){
					ihmRAcompte = new IHMRDocument();
					ihmRAcompte.setIdDocument(rAcompte.getTaFacture().getIdDocument());
					ihmRAcompte.setCodeDocument(rAcompte.getTaFacture().getCodeDocument());
					ihmRAcompte.setLibelleDocument(rAcompte.getTaFacture().getLibelleDocument());
					ihmRAcompte.setTypeDocument(typeDocPresent.TYPE_FACTURE);
					ihmRAcompte.setId(rAcompte.getId());
					ihmRAcompte.setTaAcompte(rAcompte.getTaAcompte());
					ihmRAcompte.setResteARegler(rAcompte.getTaFacture().getResteAReglerEcran());
					ihmRAcompte.setAffectation(rAcompte.getAffectation());
					listeObjet.add(ihmRAcompte);
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
