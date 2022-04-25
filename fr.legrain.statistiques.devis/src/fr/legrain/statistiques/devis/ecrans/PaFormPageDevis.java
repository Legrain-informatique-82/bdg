/**
 * PaFormPageOngletDevis.java						03/05/11
 * ( dernière revision : 19/04/11 )
 */

package fr.legrain.statistiques.devis.ecrans;

import org.eclipse.ui.forms.editor.FormEditor;

import fr.legrain.statistiques.ecrans.PaFormPage;

/**
 * Classe permettant l'affichage du tableau de bord
 * Chaque section et chaque composite sont déclarés de façon autonome
 * @author nicolas²
 *
 */

public class PaFormPageDevis extends PaFormPage {
	
	public static String id = "fr.legrain.statistiques.ecrans.PaFormPageOngletDevis";
	public static String title = "Devis";
	
	public PaFormPageDevis() {
		super(id, title);
	}

	public PaFormPageDevis(FormEditor editor, String id, String title) {
		super(editor, id, title);
	}


	@Override
	public void createSections() {
		/* Section paramètres : identique @super */
		createSectionParam(toolkit,form);
		
		/* Section montant */
		createSectionMontant(toolkit,form);
		sctnMontant.setText("Montant des devis HT");
		compositeSectionMontant.getInfolabel().setText("Montant des devis HT sur la période");
		
		/* Section documents */
		createSectionDoc(toolkit, form);
		sctnDocuments.setText("Transformation des Devis");
		compositeSectionDoc.getLblNew1().setText("En Factures: ");
		compositeSectionDoc.getLblNew2().setText("En Bons de Commandes : ");
		compositeSectionDoc.getLblNew3().setText("En Bons de Livraison : ");
		compositeSectionDoc.getLblNew4().setText("En Proformas : ");
		compositeSectionDoc.getLblNew5().setText("Devis non transformés : ");
		
		/* Section Jauge */
		createSectionJauge(toolkit, form);
		sctnJauge.setText("Suivi des Devis");
		compositeSectionJauge.getLblNew1().setText("Nombre de devis non transformés : ");
		compositeSectionJauge.getLblNew2().setText("Montant des devis non transformés : ");
		
		/* Section Graph */
		createSectionEvolution(toolkit, form);
		sctnEvolutionDuChiffre.setText("Evolution devis transformés et non transformés");
		
		/* Section Repartition */
		createSectionRepartition(toolkit, form);
		sctnRepartition.setText("Répartition des devis sur la période");
		
		/* Section Tableaux */
		createSectionTableauGauche(toolkit, form);
		createSectionTableauDroit(toolkit, form);
		sctnTableauGauche.setText("Devis transformés sur la période");
		sctnTableauDroit.setText("Devis non transformés sur la période");
		
		
	}
	
	




}
