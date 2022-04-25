package fr.legrain.prelevement.exportation.preferences;

import org.apache.log4j.Logger;
import org.eclipse.jface.preference.DirectoryFieldEditor;

import fr.legrain.edition.divers.FieldEditorPreferencePageLGR;
import fr.legrain.prelevement.exportation.pluginPrelevement;

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

public class PrelevementPreferencePage extends FieldEditorPreferencePageLGR{
	
	private String pathEditionFragement = null;
	private String pathEditionSpecifiquesClient = null;
	
	
	 @Override
	protected void performDefaults() {
		PreferenceInitializer.initDefautProperties();
		super.performDefaults();
	}

	 static Logger logger = Logger.getLogger(PrelevementPreferencePage.class.getName());
	  
	public PrelevementPreferencePage() {
		super(GRID);
		setPreferenceStore(pluginPrelevement.getDefault().getPreferenceStoreProject());
		setDescription("Paramètres d'exportation des prélèvements en banque");
	}
	
	/**
	 * Creates the field editors. Field editors are abstractions of
	 * the common GUI blocks needed to manipulate various types
	 * of preferences. Each field editor knows how to save and
	 * restore itself.
	 */
	public void createFieldEditors() {
		addField(
				new DirectoryFieldEditor(PreferenceConstants.P_PATH_EXPORTATION_BANQUE, "Répertoire d'exportation des prélèvements en banque", getFieldEditorParent()));

	}		
	@Override
	protected void performApply() {
		super.performApply();

	}




	

}