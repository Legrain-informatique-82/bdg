package fr.legrain.devis.divers;

import java.util.LinkedList;

import fr.legrain.documents.dao.LigneTva;
import fr.legrain.documents.dao.TaDevis;

public class ModelLignesTVADevis {

	LinkedList<IHMLignesTVADevis> listeObjet = new LinkedList<IHMLignesTVADevis>();

	public LinkedList remplirListe(TaDevis taDevis) {
		IHMLignesTVADevis ihmLignesTVA = null;
		listeObjet.clear();
		if(taDevis != null) {
			for(LigneTva ligneTVA : taDevis.getLignesTVA()){
				ihmLignesTVA = new IHMLignesTVADevis();
				ihmLignesTVA.setIHMDevisLignesTVA(ligneTVA);
				listeObjet.add(ihmLignesTVA);
			}
		}
		return listeObjet;
	}
	
	public LinkedList<IHMLignesTVADevis> getListeObjet() {
		return listeObjet;
	}

}
