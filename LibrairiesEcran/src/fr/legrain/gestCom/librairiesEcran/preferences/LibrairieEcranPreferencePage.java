package fr.legrain.gestCom.librairiesEcran.preferences;

import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import fr.legrain.gestCom.librairiesEcran.LibrairiesEcranPlugin;

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

public class LibrairieEcranPreferencePage
	extends FieldEditorPreferencePage
	implements IWorkbenchPreferencePage {

	public LibrairieEcranPreferencePage() {
		super(GRID);
		setPreferenceStore(LibrairiesEcranPlugin.getDefault().getPreferenceStore());
		setDescription("");
	}
	
	/**
	 * Creates the field editors. Field editors are abstractions of
	 * the common GUI blocks needed to manipulate various types
	 * of preferences. Each field editor knows how to save and
	 * restore itself.
	 */
	public void createFieldEditors() {
		addField(
				new BooleanFieldEditor(PreferenceConstants.AFFICHAGE_MESSAGE_EXTENSION_OPTIONNEL, "Afficher l'écran les messages d'information facultatifs.", getFieldEditorParent()));
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.IWorkbenchPreferencePage#init(org.eclipse.ui.IWorkbench)
	 */
	public void init(IWorkbench workbench) {
		setPreferenceStore(LibrairiesEcranPlugin.getDefault().getPreferenceStore());
	}
	
}