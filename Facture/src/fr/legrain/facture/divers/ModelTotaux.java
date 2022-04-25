package fr.legrain.facture.divers;

import java.util.LinkedList;

import fr.legrain.gestCom.Module_Document.IHMTotauxDocument;

public class ModelTotaux {

	LinkedList<IHMTotauxDocument> listeObjet = new LinkedList<IHMTotauxDocument>();

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
	
	public LinkedList<IHMTotauxDocument> getListeObjet() {
		return listeObjet;
	}

}
