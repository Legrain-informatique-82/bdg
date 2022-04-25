package fr.legrain.proforma.divers;

import java.util.LinkedList;

import fr.legrain.documents.dao.LigneTva;
import fr.legrain.documents.dao.TaProforma;

public class ModelLignesTVAProforma {

	LinkedList<IHMLignesTVAProforma> listeObjet = new LinkedList<IHMLignesTVAProforma>();

	public LinkedList remplirListe(TaProforma taProforma) {
		IHMLignesTVAProforma ihmLignesTVA = null;
		listeObjet.clear();
		if(taProforma != null) {
			for(LigneTva ligneTVA : taProforma.getLignesTVA()){
				ihmLignesTVA = new IHMLignesTVAProforma();
				ihmLignesTVA.setIHMProformaLignesTVA(ligneTVA);
				listeObjet.add(ihmLignesTVA);
			}
		}
		return listeObjet;
	}
	
	public LinkedList<IHMLignesTVAProforma> getListeObjet() {
		return listeObjet;
	}

}
