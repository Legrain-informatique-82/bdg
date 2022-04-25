package fr.legrain.document.preferences;

import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.StringFieldEditor;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import fr.legrain.document.DocumentPlugin;
import fr.legrain.gestCom.librairiesEcran.swt.StringCommentaireEditor;

public class DocumentPreferencePage extends FieldEditorPreferencePage
implements IWorkbenchPreferencePage {

	public DocumentPreferencePage() {
		super(GRID);
		setPreferenceStore(DocumentPlugin.getDefault().getPreferenceStore());
		setDescription("Paramètres des documents.");
	}

	@Override
	protected void createFieldEditors() {
		addField(
				new BooleanFieldEditor(PreferenceConstants.P_ONGLETS_DOC, "Affichage sous forme d'onglets", getFieldEditorParent()));
		addField(
				new BooleanFieldEditor(PreferenceConstants.AFF_ADRESSE, "Affichage des adresses facturation", getFieldEditorParent()));
		addField(
				new BooleanFieldEditor(PreferenceConstants.AFF_ADRESSE_LIV, "Affichage des adresses livraison", getFieldEditorParent()));
		addField(
				new BooleanFieldEditor(PreferenceConstants.AFF_CPAIEMENT, "Affichage des conditions de paiement", getFieldEditorParent()));
		addField(
				new BooleanFieldEditor(PreferenceConstants.AFF_COMMENTAIRE, "Affichage des commentaires", getFieldEditorParent()));
		addField(
				new BooleanFieldEditor(PreferenceConstants.AFF_IDENTITE_TIERS, "Affichage de l'identité du tiers", getFieldEditorParent()));
		addField(new StringFieldEditor(PreferenceConstants.TYPE_ADRESSE_FACTURATION,
				"Type d'adresse facturation",getFieldEditorParent()));
		addField(new StringFieldEditor(PreferenceConstants.TYPE_ADRESSE_DEVIS,
				"Type d'adresse devis",getFieldEditorParent()));
		addField(new StringFieldEditor(PreferenceConstants.TYPE_ADRESSE_BONLIV,
				"Type d'adresse bon de livraison",getFieldEditorParent()));
		addField(
				new BooleanFieldEditor(PreferenceConstants.AFF_EDITION, PreferenceConstants.AFFICHER_SELECTION_EDITION, 
						getFieldEditorParent()));

		addField(new StringFieldEditor(PreferenceConstants.TYPE_PAIEMENT_DEFAUT,
				"Type de paiement par défaut",getFieldEditorParent()));
		addField(
				new BooleanFieldEditor(PreferenceConstants.MESSAGE_TIERS_DIFFERENT, "affichage d'un message si le nom du tiers diffère", getFieldEditorParent()));
		


		
		addField(
				new StringCommentaireEditor(PreferenceConstants.COMMENTAIRE_TRAITE, "Commentaire à insérer dans les traites", getFieldEditorParent()));
//		addField(
//				new BooleanFieldEditor(PreferenceConstants.TYPE_AFFICHAGE_AIDE, "Affichage de l'aide (F1) pré-remplie."+"\n"+"Cela peut engendrer des ralentissements sur les dossiers volumineux.", getFieldEditorParent()));
//		
		addField(
				new BooleanFieldEditor(PreferenceConstants.editionImprimerDirect, 
						PreferenceConstants.EDITION_IMPRIMER_DIRECT, getFieldEditorParent()));
		addField(
				new BooleanFieldEditor(PreferenceConstants.afficheEditionImprimer, 
						PreferenceConstants.AFFICHE_EDITION_IMPRIMER, getFieldEditorParent()));
	}

	public void init(IWorkbench workbench) {
		// TODO Auto-generated method stub
	}



}
