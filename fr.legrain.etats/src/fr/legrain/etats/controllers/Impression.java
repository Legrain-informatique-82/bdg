package fr.legrain.etats.controllers;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.birt.report.viewer.ViewerPlugin;
import org.eclipse.birt.report.viewer.utilities.WebViewer;

import fr.legrain.edition.ImprimeObjet;
import fr.legrain.edition.actions.AffichageEdition;
import fr.legrain.edition.actions.ConstEdition;
import fr.legrain.edition.dynamique.EditionAppContext;
import fr.legrain.etats.Activator;

public class Impression {
	private List<Object>  listeObjetEdition = new ArrayList<Object>() ;
	private ConstEdition constEdition;
	private boolean extraction = false;


	public Impression(List<Object> listeObjetEdition,ConstEdition constEdition) {
		super();
		this.listeObjetEdition = listeObjetEdition;
		this.constEdition = constEdition;
	}
	
	public void imprimer(String fileEditionDefaut, String name, String params ){
		ViewerPlugin.getDefault().getPluginPreferences().setValue(WebViewer.APPCONTEXT_EXTENSION_KEY, EditionAppContext.APP_CONTEXT_NAME);
		
		String reportFileLocationDefaut = ConstEdition.pathFichierEditionsSpecifiques(fileEditionDefaut,Activator.PLUGIN_ID);
		if (reportFileLocationDefaut == null){
			reportFileLocationDefaut = fileEditionDefaut;
		}
		
		ImprimeObjet.l.clear();
		
		if(listeObjetEdition!=null){
			for (int i = 0; i < listeObjetEdition.size(); i++) {
				Object o = listeObjetEdition.get(i);
				ImprimeObjet.l.add(o);
			}
		}
		AffichageEdition affichageEdition = new AffichageEdition();
//		affichageEdition.showEditionDynamiqueDefaut(reportFileLocationDefaut+params,name,ConstEdition.FORMAT_HTML,false);
		affichageEdition.showEditionDynamiqueDefaut(reportFileLocationDefaut+params,name,ConstEdition.FORMAT_PDF,isExtraction());
		
	}
	
	public boolean isExtraction() {
		return extraction;
	}

	public void setExtraction(boolean extraction) {
		this.extraction = extraction;
	}
}
