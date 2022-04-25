/**
 * PaFormPageOngletDevis.java						03/05/11
 * ( dernière revision : 19/04/11 )
 */

package fr.legrain.statistiques.ecrans.a_supprimer;

import org.eclipse.ui.forms.editor.FormEditor;

import fr.legrain.statistiques.ecrans.PaFormPage;

/**
 * Classe permettant l'affichage du tableau de bord
 * Chaque section et chaque composite sont déclarés de façon autonome
 * @author nicolas²
 *
 */

public class PaFormPageLivraisons extends PaFormPage {
	
	public static String id = "fr.legrain.statistiques.ecrans.PaFormPageOngletLivraisons";
	public static String title = "Livraisons";

	public PaFormPageLivraisons(FormEditor editor, String id, String title) {
		super(editor, id, title);
	}


	@Override
	public void createSections() {
		/* Section paramètres : identique @super */
		createSectionParam(toolkit,form);
		
		/* Section montant */
		createSectionMontant(toolkit,form);
		sctnMontant.setText("Montant des Bons de Livraison HT");
		compositeSectionMontant.getInfolabel().setText("Montant des Bons de Livraison HT sur la période");
		
		/* Section documents */
		createSectionDoc(toolkit, form);
		sctnDocuments.setText("Facturation des Bons de Livraison");
		compositeSectionDoc.getLblNew1().setText("Bons de livraison facturés : ");
		compositeSectionDoc.getLblNew2().setText("");
		compositeSectionDoc.getLblNew3().setText("");
		
		/* Section Jauge */
		createSectionJauge(toolkit, form);
		sctnJauge.setText("Suivi des Bons de Livraison");
		compositeSectionJauge.getLblNew1().setText("Nombre de bons de livraison non facturés : ");
		compositeSectionJauge.getLblNew2().setText("Montant des bons de livraison non facturés : ");
		
		/* Section Graph */
		createSectionEvolution(toolkit, form);
		sctnEvolutionDuChiffre.setText("Evolution bons livraison facturés et non facturés");
		
		/* Section Repartition */
		createSectionRepartition(toolkit, form);
		sctnRepartition.setText("Répartition des bons de livraison sur la période");
		
		/* Section Tableaux */
		createSectionTableauGauche(toolkit, form);
		createSectionTableauDroit(toolkit, form);
		sctnTableauGauche.setText("Bons de livraison facturés sur la période");
		sctnTableauDroit.setText("Bons de livraison non facturés sur la période");
		
		
	}
	
	




}
