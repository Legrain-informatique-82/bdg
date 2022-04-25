package fr.legrain.apporteur.divers;

import java.util.LinkedList;

import fr.legrain.documents.dao.LigneTva;
import fr.legrain.documents.dao.TaApporteur;


public class ModelLignesTVA {

	LinkedList<IHMLignesTVA> listeObjet = new LinkedList<IHMLignesTVA>();

	public LinkedList<IHMLignesTVA> remplirListe(TaApporteur taApporteur) {
		IHMLignesTVA ihmLignesTVA = null;
		listeObjet.clear();
		if(taApporteur != null) {
			for(LigneTva ligneTVA : taApporteur.getLignesTVA()){
				ihmLignesTVA = new IHMLignesTVA();
				ihmLignesTVA.setIHMDocumentLignesTVA(ligneTVA);
				listeObjet.add(ihmLignesTVA);
			}
		}
		return listeObjet;
	}
	
	public LinkedList<IHMLignesTVA> getListeObjet() {
		return listeObjet;
	}

}
