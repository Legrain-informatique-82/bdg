package fr.legrain.stocks.divers;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.birt.report.viewer.ViewerPlugin;
import org.eclipse.birt.report.viewer.utilities.WebViewer;

import stocks.StocksPlugin;

import fr.legrain.articles.ArticlesPlugin;
import fr.legrain.edition.ImprimeObjet;
import fr.legrain.edition.actions.AffichageEdition;
import fr.legrain.edition.actions.ConstEdition;
import fr.legrain.edition.dynamique.EditionAppContext;
import fr.legrain.stocks.dao.TaEtatStock;
import fr.legrain.stocks.ecran.ConstEditionStocks;

public class Impression {
	
	private List<TaEtatStock>  listeEtatStockEdition =new ArrayList<TaEtatStock>() ;
	private ConstEdition constEdition;

	public Impression(List<TaEtatStock> listeEtatStockEdition,ConstEdition constEdition) {
		super();
		this.listeEtatStockEdition = listeEtatStockEdition;
		this.constEdition = constEdition;
	}
	
	public void imprimer(String fileEditionDefaut ,String param){
		
		ViewerPlugin.getDefault().getPluginPreferences().setValue(WebViewer.APPCONTEXT_EXTENSION_KEY, EditionAppContext.APP_CONTEXT_NAME);
		String reportFileLocationDefaut = ConstEdition.pathFichierEditionsSpecifiques(fileEditionDefaut,StocksPlugin.PLUGIN_ID);
		if (reportFileLocationDefaut == null){
			reportFileLocationDefaut = fileEditionDefaut;
		}
		ImprimeObjet.l.clear();
		
		for (int i = 0; i < listeEtatStockEdition.size(); i++) {
			TaEtatStock taEtatStock = listeEtatStockEdition.get(i);
			ImprimeObjet.l.add(taEtatStock);
		}
		
		AffichageEdition affichageEdition = new AffichageEdition();
		
		affichageEdition.showEditionDynamiqueDefaut(reportFileLocationDefaut+param,ConstEditionStocks.TITLE_EDITION_STOCKS,
				ConstEdition.FORMAT_PDF,false);
		
	}
	

}
