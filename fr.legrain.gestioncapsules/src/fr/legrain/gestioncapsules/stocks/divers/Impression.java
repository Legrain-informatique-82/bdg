package fr.legrain.gestioncapsules.stocks.divers;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.birt.report.viewer.ViewerPlugin;
import org.eclipse.birt.report.viewer.utilities.WebViewer;

import fr.legrain.edition.ImprimeObjet;
import fr.legrain.edition.actions.AffichageEdition;
import fr.legrain.edition.actions.ConstEdition;
import fr.legrain.edition.dynamique.EditionAppContext;
import fr.legrain.gestioncapsules.Activator;
import fr.legrain.gestioncapsules.dao.TaEtatStockCapsules;

public class Impression {
	
	private List<TaEtatStockCapsules>  listeEtatStockEdition =new ArrayList<TaEtatStockCapsules>() ;
	private ConstEdition constEdition;

	public Impression(List<TaEtatStockCapsules> listeEtatStockEdition,ConstEdition constEdition) {
		super();
		this.listeEtatStockEdition = listeEtatStockEdition;
		this.constEdition = constEdition;
	}
	
	public void imprimer(String fileEditionDefaut ,String param){
		
		ViewerPlugin.getDefault().getPluginPreferences().setValue(WebViewer.APPCONTEXT_EXTENSION_KEY, EditionAppContext.APP_CONTEXT_NAME);
		String reportFileLocationDefaut = ConstEdition.pathFichierEditionsSpecifiques(fileEditionDefaut,Activator.PLUGIN_ID);
		if (reportFileLocationDefaut == null){
			reportFileLocationDefaut = fileEditionDefaut;
		}
		ImprimeObjet.l.clear();
		
		for (int i = 0; i < listeEtatStockEdition.size(); i++) {
			TaEtatStockCapsules taEtatStock = listeEtatStockEdition.get(i);
			ImprimeObjet.l.add(taEtatStock);
		}
		
		AffichageEdition affichageEdition = new AffichageEdition();
		
		affichageEdition.showEditionDynamiqueDefaut(reportFileLocationDefaut+param,fr.legrain.gestioncapsules.stocks.ecrans.ConstEditionStocksCapsules.TITLE_EDITION_STOCKS,ConstEdition.FORMAT_PDF,false);
//		affichageEdition.showEditionDynamiqueDefaut(reportFileLocationDefaut+param,fr.legrain.gestioncapsules.stocks.ecrans.ConstEditionStocksCapsules.TITLE_EDITION_STOCKS,ConstEdition.FORMAT_HTML,false);

		
	}
	

}
