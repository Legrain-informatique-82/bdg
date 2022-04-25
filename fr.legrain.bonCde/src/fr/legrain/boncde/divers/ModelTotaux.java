package fr.legrain.boncde.divers;

import java.util.LinkedList;

import fr.legrain.gestCom.Module_Document.IHMTotauxBoncde;

public class ModelTotaux {

	LinkedList<IHMTotauxBoncde> listeObjet = new LinkedList<IHMTotauxBoncde>();

//	public LinkedList remplirListe(SWTFacture swtFacture) {
//		IHMTotauxFacture ihmTotauxFacture = null;
//		listeObjet.clear();
//		if(swtFacture != null) {
//			ihmTotauxFacture = new IHMTotauxFacture();
//			ihmTotauxFacture.setIHMTotauxFacture(((SWTEnteteFacture)swtFacture.getEntete()));
//			listeObjet.add(ihmTotauxFacture);	
//		}
//		return listeObjet;
//	}
	
	public LinkedList<IHMTotauxBoncde> getListeObjet() {
		return listeObjet;
	}

}
