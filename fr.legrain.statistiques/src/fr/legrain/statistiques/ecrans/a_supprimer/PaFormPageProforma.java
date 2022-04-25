/**
 * PaFormPageProforma.java						03/05/11
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

public class PaFormPageProforma extends PaFormPage {
	
	public static String id = "fr.legrain.statistiques.ecrans.PaFormPageOngletProforma";
	public static String title = "Proformas";

	public PaFormPageProforma(FormEditor editor, String id, String title) {
		super(editor, id, title);
	}


	@Override
	public void createSections() {
		/* Section paramètres : identique @super */
		createSectionParam(toolkit,form);
		
		/* Section montant */
		createSectionMontant(toolkit,form);
		sctnMontant.setText("Montant des proformas HT");
		compositeSectionMontant.getInfolabel().setText("Montant des proformas HT sur la période");
		
		/* Section documents */
		createSectionDoc(toolkit, form);
		sctnDocuments.setText("Transformation des Proformas");
		compositeSectionDoc.getLblNew1().setText("En Factures: ");
		compositeSectionDoc.getLblNew2().setText("En Bons de Commandes : ");
		compositeSectionDoc.getLblNew3().setText("En Bons de Livraison : ");
		compositeSectionDoc.getLblNew4().setText("Proforma non transformés : ");
		
		/* Section Jauge */
		createSectionJauge(toolkit, form);
		sctnJauge.setText("Suivi des Proformas");
		compositeSectionJauge.getLblNew1().setText("Nombre de proformas non transformés : ");
		compositeSectionJauge.getLblNew2().setText("Montant des proformas non transformés : ");
		
		/* Section Graph */
		createSectionEvolution(toolkit, form);
		sctnEvolutionDuChiffre.setText("Evolution proformas transformés et non transformés");
		
		/* Section Repartition */
		createSectionRepartition(toolkit, form);
		sctnRepartition.setText("Répartition des proformas sur la période");
		
		/* Section Tableaux */
		createSectionTableauGauche(toolkit, form);
		createSectionTableauDroit(toolkit, form);
		sctnTableauGauche.setText("Proformas transformés sur la période");
		sctnTableauDroit.setText("Proformas non transformés sur la période");
		
		
	}
	
	




}
