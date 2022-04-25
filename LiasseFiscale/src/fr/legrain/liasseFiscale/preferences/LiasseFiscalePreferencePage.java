package fr.legrain.liasseFiscale.preferences;

import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.jface.preference.DirectoryFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.FileFieldEditor;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.jboss.util.propertyeditor.BooleanEditor;

import fr.legrain.liasseFiscale.LiasseFiscalePlugin;

public class LiasseFiscalePreferencePage extends FieldEditorPreferencePage implements
		IWorkbenchPreferencePage {
	
	public LiasseFiscalePreferencePage() {
		super(GRID);
		setPreferenceStore(LiasseFiscalePlugin.getDefault().getPreferenceStore());
		setDescription("");
	}

	@Override
	protected void createFieldEditors() {
		
		addField(new DirectoryFieldEditor(PreferenceConstants.P_CHEMIN_COMPTA, "Chemin dossiers comptabilité : ", getFieldEditorParent()));
		addField(new FileFieldEditor(PreferenceConstants.P_CHEMIN_ADOBE_READER, "Chemin lecteur PDF (Adobe Acrobat) : ", getFieldEditorParent()));
		
		
		addField(new DirectoryFieldEditor(PreferenceConstants.P_DOSSIER_A_SAUVEGARDER, "Répertoire à sauvegarder : ", getFieldEditorParent()));
		addField(new DirectoryFieldEditor(PreferenceConstants.P_EMPLACEMENT_SAUVEGARDE, "Répertoire où mettre les sauvegardes : ", getFieldEditorParent()));

		
		addField(new DirectoryFieldEditor(PreferenceConstants.P_DOSSIER_A_EFFACER_RESTAURATION, "Répertoire à remplacer par la sauvegarde : ", getFieldEditorParent()));
		addField(new DirectoryFieldEditor(PreferenceConstants.P_REP_DEST_RESTAURATION, "Répertoire où restaurer : ", getFieldEditorParent()));
		
		addField(new BooleanFieldEditor(PreferenceConstants.P_ARRONDI, "Arrondir les valeurs à l'euro", getFieldEditorParent()));
		

	}

	public void init(IWorkbench workbench) {
		// TODO Auto-generated method stub
		
	}

	
}
