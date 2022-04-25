package fr.legrain.proforma.divers;

import java.util.LinkedList;

import fr.legrain.gestCom.Module_Document.IHMTotauxBoncde;
import fr.legrain.gestCom.Module_Document.IHMTotauxProforma;

public class ModelTotaux {

	LinkedList<IHMTotauxProforma> listeObjet = new LinkedList<IHMTotauxProforma>();

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
	
	public LinkedList<IHMTotauxProforma> getListeObjet() {
		return listeObjet;
	}

}
