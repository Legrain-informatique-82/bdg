package fr.legrain.bonlivraison.divers;

import java.util.LinkedList;

import fr.legrain.documents.dao.LigneTva;
import fr.legrain.documents.dao.TaBonliv;


public class ModelLignesTVABL {

	LinkedList<IHMLignesTVABL> listeObjet = new LinkedList<IHMLignesTVABL>();

	public LinkedList remplirListe(TaBonliv taBonliv) {
		IHMLignesTVABL ihmLignesTVA = null;
		listeObjet.clear();
		if(taBonliv != null) {
			for(LigneTva ligneTVA : taBonliv.getLignesTVA()){
				ihmLignesTVA = new IHMLignesTVABL();
				ihmLignesTVA.setIHMDevisLignesTVA(ligneTVA);
				listeObjet.add(ihmLignesTVA);
			}
		}
		return listeObjet;
	}
	
	public LinkedList<IHMLignesTVABL> getListeObjet() {
		return listeObjet;
	}

}
