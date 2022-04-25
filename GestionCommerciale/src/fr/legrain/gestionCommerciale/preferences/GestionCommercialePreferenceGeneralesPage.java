package fr.legrain.gestionCommerciale.preferences;

import org.eclipse.jface.preference.*;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.eclipse.ui.IWorkbench;

import fr.legrain.gestionCommerciale.GestionCommercialePlugin;

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

public class GestionCommercialePreferenceGeneralesPage
	extends FieldEditorPreferencePage
	implements IWorkbenchPreferencePage {

	public GestionCommercialePreferenceGeneralesPage() {
		super(GRID);
		setPreferenceStore(GestionCommercialePlugin.getDefault().getPreferenceStore());
		setDescription("Paramètres généraux.");
	}
	
	/**
	 * Creates the field editors. Field editors are abstractions of
	 * the common GUI blocks needed to manipulate various types
	 * of preferences. Each field editor knows how to save and
	 * restore itself.
	 */
	public void createFieldEditors() {
		addField(
				new BooleanFieldEditor(PreferenceConstants.AFFICHAGE_INTRO, "Afficher l'écran de bienvenue au démarrage.", getFieldEditorParent()));
		
//		addField(
//				new BooleanFieldEditor(PreferenceConstants.TYPE_AFFICHAGE_AIDE, "Affichage de l'aide (F1) pré-remplie."+"\n"+"Cela peut engendrer des ralentissements sur les dossiers volumineux.", getFieldEditorParent()));

		addField(
				new BooleanFieldEditor(PreferenceConstants.AFFICHAGE_CTRL_ESPACE, "Affichage du (Ctrl+Espace)."+"\n"+"Cela peut engendrer des ralentissements sur les dossiers volumineux.", getFieldEditorParent()));
		
		addField(
				new StringFieldEditor(PreferenceConstants.SYSTEM_UNIX_PASSWORD_SUDO, "Mot de passe UNIX", getFieldEditorParent()));

	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.IWorkbenchPreferencePage#init(org.eclipse.ui.IWorkbench)
	 */
	public void init(IWorkbench workbench) {
		setPreferenceStore(GestionCommercialePlugin.getDefault().getPreferenceStore());
	}
	
}