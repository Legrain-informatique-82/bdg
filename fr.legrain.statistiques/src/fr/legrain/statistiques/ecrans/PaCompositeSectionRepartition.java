/**
 * PaCompositeSectionRepartition.java
 */
package fr.legrain.statistiques.ecrans;

import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.forms.widgets.FormToolkit;

import fr.legrain.statistiques.GoogleMapChart;
import fr.legrain.statistiques.Outils;

/**
 * @author nicolas²
 *
 */
public class PaCompositeSectionRepartition {
	// Composite
	private Composite compo = null;
	private Label temporaire = null;
	private Browser mapBrowser = null;
	
	public PaCompositeSectionRepartition(Composite compo,FormToolkit toolkit) {
		this.compo = compo;
//		toolkit.paintBordersFor(compo);
		
		// Browser dans le composite
		mapBrowser = new Browser(compo,SWT.NONE);
		mapBrowser.setSize(300,300);
		
		
		compo.setLayout(new FillLayout());
		compo.layout();
		
		

	}

	public Composite getCompo() {
		return compo;
	}
	
	public Label getLabel() {
		return temporaire;
	}
	
	public Browser getMapBrowser() {
		return mapBrowser;
	}

	/**
	 * Méthode permettant l'affichage de la page html dans le naviguateur
	 * @param page
	 */
	public void setPage(String page) {
		mapBrowser.setText(page);
		//mapBrowser.setUrl("file:///home/nicolas/Desktop/test3.html");
	}
	
}
