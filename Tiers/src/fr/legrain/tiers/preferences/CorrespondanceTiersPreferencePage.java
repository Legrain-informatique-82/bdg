package fr.legrain.tiers.preferences;

import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import fr.legrain.tiers.TiersPlugin;

public class CorrespondanceTiersPreferencePage
	extends FieldEditorPreferencePage
	implements IWorkbenchPreferencePage {

	public CorrespondanceTiersPreferencePage() {
		super(GRID);
		setPreferenceStore(TiersPlugin.getDefault().getPreferenceStore());
		setDescription("Utilisation par défaut des informations de correspondace");
	}
	
	/**
	 * Creates the field editors. Field editors are abstractions of
	 * the common GUI blocks needed to manipulate various types
	 * of preferences. Each field editor knows how to save and
	 * restore itself.
	 */
	public void createFieldEditors() {		
		addField(new BooleanFieldEditor(PreferenceConstants.CORRESPONDANCE_ADRESSE_COMMERCIAL_DEFAUT, "Utiliser les adresses pour les courriers commerciaux", getFieldEditorParent()));
		addField(new BooleanFieldEditor(PreferenceConstants.CORRESPONDANCE_ADRESSE_ADMINISTRATIF_DEFAUT, "Utiliser les adresses pour les courriers administratifs", getFieldEditorParent()));
		addField(new BooleanFieldEditor(PreferenceConstants.CORRESPONDANCE_EMAIL_COMMERCIAL_DEFAUT, "Utiliser les adresses email pour les emails commerciaux", getFieldEditorParent()));
		addField(new BooleanFieldEditor(PreferenceConstants.CORRESPONDANCE_EMAIL_ADMINISTRATIF_DEFAUT, "Utiliser les adresses email pour les emails administratifs", getFieldEditorParent()));
		addField(new BooleanFieldEditor(PreferenceConstants.CORRESPONDANCE_TELEPHONE_COMMERCIAL_DEFAUT, "Utiliser les numéros de téléphone pour les SMS ou fax commerciaux", getFieldEditorParent()));
		addField(new BooleanFieldEditor(PreferenceConstants.CORRESPONDANCE_TELEPHONE_ADMINISTRATIF_DEFAUT, "Utiliser les numéros de téléphone pour les SMS ou fax administratifs", getFieldEditorParent()));
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.IWorkbenchPreferencePage#init(org.eclipse.ui.IWorkbench)
	 */
	public void init(IWorkbench workbench) {
	}
	
}