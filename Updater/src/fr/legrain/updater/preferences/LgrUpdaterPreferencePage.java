package fr.legrain.updater.preferences;

import org.eclipse.jface.preference.*;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.eclipse.ui.IWorkbench;
import fr.legrain.updater.UpdaterPlugin;

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

public class LgrUpdaterPreferencePage
	extends FieldEditorPreferencePage
	implements IWorkbenchPreferencePage {

	public LgrUpdaterPreferencePage() {
		super(GRID);
		setPreferenceStore(UpdaterPlugin.getDefault().getPreferenceStore());
		setDescription("Paramètres de mise à jour.");
	}
	
	/**
	 * Creates the field editors. Field editors are abstractions of
	 * the common GUI blocks needed to manipulate various types
	 * of preferences. Each field editor knows how to save and
	 * restore itself.
	 */
	public void createFieldEditors() {
		addField(
			new StringFieldEditor(PreferenceConstants.P_SERVEUR_POP, "Serveur POP:", getFieldEditorParent()));
		addField(
				new StringFieldEditor(PreferenceConstants.P_USER, "Nom d'utilisateur:", getFieldEditorParent()));
		
		StringFieldEditor passwordField = new StringFieldEditor(PreferenceConstants.P_PASS, "Mot de Passe:", getFieldEditorParent());
		passwordField.getTextControl(getFieldEditorParent()).setEchoChar('*');
		passwordField.setEmptyStringAllowed(false);
		addField(passwordField);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.IWorkbenchPreferencePage#init(org.eclipse.ui.IWorkbench)
	 */
	public void init(IWorkbench workbench) {
	}
	
}