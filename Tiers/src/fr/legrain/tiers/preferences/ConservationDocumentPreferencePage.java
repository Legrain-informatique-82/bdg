package fr.legrain.tiers.preferences;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.log4j.Logger;
import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.StringFieldEditor;
import org.osgi.framework.Bundle;

import fr.legrain.edition.actions.ConstEdition;
import fr.legrain.edition.divers.FieldEditorPreferencePageLGR;
import fr.legrain.gestCom.Appli.Const;
import fr.legrain.tiers.TiersPlugin;
import fr.legrain.tiers.dao.TaTiers;
import fr.legrain.tiers.ecran.ConstEditionTiers;

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

public class ConservationDocumentPreferencePage extends FieldEditorPreferencePageLGR{

	static Logger logger = Logger.getLogger(ConservationDocumentPreferencePage.class.getName());

	public ConservationDocumentPreferencePage() {
		super(GRID);
//		String idPlugin = TiersPlugin.getDefault().getBundle().getSymbolicName();
//		this.setNamePlugin(idPlugin);

		setPreferenceStore(TiersPlugin.getDefault().getPreferenceStore());
		setDescription("Règles de conservation des documents générés");
	}

	/**
	 * Creates the field editors. Field editors are abstractions of
	 * the common GUI blocks needed to manipulate various types
	 * of preferences. Each field editor knows how to save and
	 * restore itself.
	 */
	public void createFieldEditors() {
//		addField(
//				new BooleanFieldEditor(PreferenceConstants.CONSERVER_DOCUMENT_GENERE, "Conserver les documents générés", getFieldEditorParent()));
		addField(
				new BooleanFieldEditor(PreferenceConstants.CONSERVER_DOCUMENT_GENERE_A_PARTIR_TIERS, "Conserver les courriers généré à partir d'un tiers", getFieldEditorParent()));
		addField(
				new BooleanFieldEditor(PreferenceConstants.CONSERVER_DOCUMENT_GENERE_A_PARTIR_DOC, "Conserver les courriers généré à partir d'un document", getFieldEditorParent()));
//		addField(
//				new BooleanFieldEditor(PreferenceConstants.CONSERVER_DOCUMENT_GENERE_DOC, "Document", getFieldEditorParent()));
	}

	@Override
	protected void performDefaults() {
		PreferenceInitializer.initDefautProperties();
		super.performDefaults();
	}

}