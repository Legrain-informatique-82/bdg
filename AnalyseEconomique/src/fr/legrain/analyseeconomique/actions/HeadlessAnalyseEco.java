package fr.legrain.analyseeconomique.actions;

import java.io.File;

import javax.swing.event.EventListenerList;

import org.apache.log4j.Logger;

import fr.legrain.gestCom.librairiesEcran.swt.LgrWorkListener;
import fr.legrain.liasseFiscale.db.ConstLiasse;
import fr.legrain.liasseFiscale.wizards.Document;
import fr.legrain.liasseFiscale.wizards.EnumTypeDoc;
import fr.legrain.liasseFiscale.wizards.WizardDocumentFiscalModel;

public class HeadlessAnalyseEco {
	
	static Logger logger = Logger.getLogger(HeadlessAnalyseEco.class.getName());
	
	protected EventListenerList listenerList = new EventListenerList();
	
	public void addLgrWorkListener(LgrWorkListener l) {
		listenerList.add(LgrWorkListener.class, l);
	}
	
	public void removeLgrWorkListener(LgrWorkListener l) {
		listenerList.remove(LgrWorkListener.class, l);
	}
	
	public void traitementAnalyseEco(WizardDocumentFiscalModel m, String agence) {
		try {
			ModelAnalyseEconomique dataAE = new ModelAnalyseEconomique();
			
			//cet le modele qui declenche les evenement, il faut donc "re"bracher les listener sur le model
			//et les enleves apres les traitement
			for (int i = 0; i < listenerList.getListeners(LgrWorkListener.class).length; i++) {
				dataAE.addLgrWorkListener(listenerList.getListeners(LgrWorkListener.class)[i]);
			}
			
			if(estUneLiasse(m.getCheminDocument())) {
				dataAE.setNomDossier(m.getNomDossier());
				dataAE.setCheminDocument(m.getCheminDocument());
				
				dataAE.traitementAnalyseEco(agence);
			}
			
			for (int i = 0; i < listenerList.getListeners(LgrWorkListener.class).length; i++) {
				dataAE.removeLgrWorkListener(listenerList.getListeners(LgrWorkListener.class)[i]);
			}
		} catch(Exception e) {
			logger.error("",e);
		}
	}
	
	//copie a partir de WizardPageChoixLiasse
	public boolean estUneLiasse(String selection) {
		boolean result = false;
		Document d = new Document();
		if(new File(selection+"/"+ConstLiasse.C_FICHIER_ID_DOC).exists()) {
			d = d.lectureXML(selection+"/"+ConstLiasse.C_FICHIER_ID_DOC);

			if(d.getTypeDocument().compareTo(EnumTypeDoc.liasse)==0) {
				result = true;
			}
		}
		return result;
	}
}
