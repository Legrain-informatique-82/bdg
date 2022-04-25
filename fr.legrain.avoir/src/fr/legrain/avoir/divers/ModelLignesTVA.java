package fr.legrain.avoir.divers;

import java.util.LinkedList;

import fr.legrain.documents.dao.LigneTva;
import fr.legrain.documents.dao.TaAvoir;


public class ModelLignesTVA {

	LinkedList<IHMLignesTVA> listeObjet = new LinkedList<IHMLignesTVA>();

	public LinkedList<IHMLignesTVA> remplirListe(TaAvoir taAvoir) {
		IHMLignesTVA ihmLignesTVA = null;
		listeObjet.clear();
		if(taAvoir != null) {
			for(LigneTva ligneTVA : taAvoir.getLignesTVA()){
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
