package fr.legrain.relancefacture.preferences;

import org.eclipse.core.databinding.observable.Realm;
import org.eclipse.jface.databinding.swt.SWTObservables;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.StringButtonFieldEditor;
import org.eclipse.jface.preference.StringFieldEditor;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import fr.legrain.gestCom.librairiesEcran.swt.StringButtonFieldEditorLgr;
import fr.legrain.relancefacture.Activator;





/**
 * This class represents a preference page that
 * is contributed to the Preferences dialog. By 
 * subclassing <samp>FieldEditorPreferencePage</samp>, we
 * can use the field support built into JFace that allows
 * us to create a page that is small and knows how to 
 * save, restore and apply itself.
 * <p>
 * This page is used to modify preferences only. They
 * are stored in the preference store that belongs to
 * the main plug-in class. That way, preferences can
 * be accessed directly via the preference store.
 */

public class RelanceFacturePreferencePage
	extends FieldEditorPreferencePage
	implements IWorkbenchPreferencePage {

	public RelanceFacturePreferencePage() {
		super(GRID);
		setPreferenceStore(Activator.getDefault().getPreferenceStore());
		setDescription("Paramètres de gestion des relances.");
	}
	
	/**
	 * Creates the field editors. Field editors are abstractions of
	 * the common GUI blocks needed to manipulate various types
	 * of preferences. Each field editor knows how to save and
	 * restore itself.
	 */
	public void createFieldEditors() {
		Realm realm = SWTObservables.getRealm(getFieldEditorParent().getDisplay());

//		addField(
//				new StringFieldEditor(PreferenceConstants.P_RELANCE1_OPEN, "Modèle 1 de relance type OpenOffice:", getFieldEditorParent()));
//		addField(
//				new StringFieldEditor(PreferenceConstants.P_RELANCE2_OPEN, "Modèle 2 de relance type OpenOffice:", getFieldEditorParent()));
//		addField(
//				new StringFieldEditor(PreferenceConstants.P_RELANCE3_OPEN, "Modèle 3 de relance type OpenOffice:", getFieldEditorParent()));
//		addField(
//				new StringFieldEditor(PreferenceConstants.P_RELANCE1_WORD, "Modèle 1 de relance type Word:", getFieldEditorParent()));
//		addField(
//				new StringFieldEditor(PreferenceConstants.P_RELANCE2_WORD, "Modèle 2 de relance type Word:", getFieldEditorParent()));
//		addField(
//				new StringFieldEditor(PreferenceConstants.P_RELANCE3_WORD, "Modèle 3 de relance type Word:", getFieldEditorParent()));
		StringButtonFieldEditor stringButtonFieldEditorLgr=new StringButtonFieldEditor("", "Réinitialisation des modèles par défaut", getFieldEditorParent()) {
			
			@Override
			protected String changePressed() {
				if(MessageDialog.openConfirm(getFieldEditorParent().getShell(), "Réinitialisation !", 
				"Etes-vous sûr de vouloir remplacer vos modèles de relance par les modèles de relance par défaut ?")) {
				performDefaults();
				}
				return null;
			}
		};
		addField(stringButtonFieldEditorLgr);
		stringButtonFieldEditorLgr.setChangeButtonText("Reinitialiser");
		stringButtonFieldEditorLgr.getTextControl(getFieldEditorParent()).setVisible(false);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.IWorkbenchPreferencePage#init(org.eclipse.ui.IWorkbench)
	 */
	public void init(IWorkbench workbench) {		
		setPreferenceStore(Activator.getDefault().getPreferenceStore());
	}

	 @Override
		protected void performDefaults() {
			PreferenceInitializer.initDefautProperties(true);
			super.performDefaults();
		}
}