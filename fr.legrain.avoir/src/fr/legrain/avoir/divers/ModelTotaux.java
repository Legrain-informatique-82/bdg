package fr.legrain.avoir.divers;

import java.util.LinkedList;

import fr.legrain.gestCom.Module_Document.IHMTotauxAvoir;

public class ModelTotaux {

	LinkedList<IHMTotauxAvoir> listeObjet = new LinkedList<IHMTotauxAvoir>();

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
	
	public LinkedList<IHMTotauxAvoir> getListeObjet() {
		return listeObjet;
	}

}
