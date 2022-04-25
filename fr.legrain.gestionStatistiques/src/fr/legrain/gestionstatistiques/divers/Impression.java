package fr.legrain.gestionstatistiques.divers;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.birt.report.viewer.ViewerPlugin;
import org.eclipse.birt.report.viewer.utilities.WebViewer;

import fr.legrain.dms.dao.TaEtatMouvementDms;
import fr.legrain.edition.ImprimeObjet;
import fr.legrain.edition.actions.AffichageEdition;
import fr.legrain.edition.actions.ConstEdition;
import fr.legrain.edition.dynamique.EditionAppContext;
import fr.legrain.gestionstatistiques.Activator;
import fr.legrain.stocks.dao.TaEtatStock;

public class Impression {
	
	private List<TaEtatMouvementDms>  listeEtatStockEdition =new ArrayList<TaEtatMouvementDms>() ;
	private ConstEdition constEdition;

	public Impression(List<TaEtatMouvementDms> listeEtatStockEdition,ConstEdition constEdition) {
		super();
		this.listeEtatStockEdition = listeEtatStockEdition;
		this.constEdition = constEdition;
	}
	
	public void imprimer(String fileEditionDefaut,String params ){
		
		ViewerPlugin.getDefault().getPluginPreferences().setValue(WebViewer.APPCONTEXT_EXTENSION_KEY, EditionAppContext.APP_CONTEXT_NAME);
		String reportFileLocationDefaut = ConstEdition.pathFichierEditionsSpecifiques(fileEditionDefaut,Activator.PLUGIN_ID);
		if (reportFileLocationDefaut == null){
			reportFileLocationDefaut = fileEditionDefaut;
		}
		ImprimeObjet.l.clear();
		
		for (int i = 0; i < listeEtatStockEdition.size(); i++) {
			TaEtatMouvementDms taEtat = listeEtatStockEdition.get(i);
			ImprimeObjet.l.add(taEtat);
		}

		
		AffichageEdition affichageEdition = new AffichageEdition();
		
		affichageEdition.showEditionDynamiqueDefaut(reportFileLocationDefaut+params,fileEditionDefaut,
				ConstEdition.FORMAT_PDF,false);
		
	}
	

}
