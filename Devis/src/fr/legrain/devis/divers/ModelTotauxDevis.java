package fr.legrain.devis.divers;

import java.util.LinkedList;

import fr.legrain.gestCom.Module_Document.IHMTotauxDevis;
import fr.legrain.gestCom.Module_Document.IHMTotauxDocument;

public class ModelTotauxDevis {

	LinkedList<IHMTotauxDocument> listeObjet = new LinkedList<IHMTotauxDocument>();

//	public LinkedList remplirListe(SWTDevis swtDevis) {
//		IHMTotauxDevis ihmTotauxDevis = null;
//		listeObjet.clear();
//		if(swtDevis != null) {
//			ihmTotauxDevis = new IHMTotauxDevis();
//			ihmTotauxDevis.setIHMTotauxDEVIS(((SWTEnteteDevis)swtDevis.getEntete()));
//			listeObjet.add(ihmTotauxDevis);	
//		}
//		return listeObjet;
//	}
	
	public LinkedList<IHMTotauxDocument> getListeObjet() {
		return listeObjet;
	}

}
