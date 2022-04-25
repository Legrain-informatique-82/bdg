package fr.legrain.remisecheque.preferences;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.log4j.Logger;
import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.jface.preference.IPreferenceStore;
import org.osgi.framework.Bundle;

import fr.legrain.documents.dao.TaRemise;
import fr.legrain.edition.actions.ConstEdition;
import fr.legrain.edition.divers.FieldEditorPreferencePageLGR;
import fr.legrain.gestCom.Appli.Const;
import fr.legrain.remisecheque.pluginRemise;

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

public class RemisePreferencePage extends FieldEditorPreferencePageLGR{
	
	private String pathEditionFragement = null;
	private String pathEditionSpecifiquesClient = null;
	
	
	 @Override
	protected void performDefaults() {
		PreferenceInitializer.initDefautProperties();
		super.performDefaults();
	}

	 static Logger logger = Logger.getLogger(RemisePreferencePage.class.getName());
	  
	public RemisePreferencePage() {
		super(GRID);
		String idPlugin = pluginRemise.getDefault().getBundle().getSymbolicName();
		this.constEdition = new ConstEdition();
		this.setFolderEdition(constEdition.FOLDER_EDITION);
		this.setFolderEditionClients(constEdition.FOLDER_EDITION_CLIENT);
		this.setFolderEditionReportPlugin(constEdition.FICHE_FILE_REPORT_REMISE);
		this.setNamePlugin(idPlugin);
		/** fragement **/
		reportPlugin =ConstEdition.pathRepertoireEditionsSpecifiques()+ConstEdition.SEPARATOR+
					  idPlugin+ConstEdition.SEPARATOR+TaRemise.class.getSimpleName();
		/** plugin EditionsSpecifiques **/
		reportPluginClients=ConstEdition.pathRepertoireEditionsSpecifiquesClient()+ConstEdition.SEPARATOR+
							idPlugin+ConstEdition.SEPARATOR+TaRemise.class.getSimpleName();
		setPreferenceStore(pluginRemise.getDefault().getPreferenceStore());
		setDescription("Paramètres d'impression des remises");
		
		/** 23/02/2010 **/
		reportEditionSupp = Const.PATH_FOLDER_EDITION_SUPP+ConstEdition.SEPARATOR+idPlugin+ConstEdition.SEPARATOR
	    					+TaRemise.class.getSimpleName();
	}
	
	/**
	 * Creates the field editors. Field editors are abstractions of
	 * the common GUI blocks needed to manipulate various types
	 * of preferences. Each field editor knows how to save and
	 * restore itself.
	 */
	public void createFieldEditors() {
		addField(
				new BooleanFieldEditor(PreferenceConstants.IMPRIMER_AUTO, "Impression automatique", getFieldEditorParent()));
		
		/** 05/01/2010 Add **/
		Bundle bundleCourant = pluginRemise.getDefault().getBundle();
		String namePlugin = bundleCourant.getSymbolicName();
		
		String PathEditionDefaut = ConstEdition.pathFichierEditionsSpecifiques(ConstEdition.FICHE_FILE_REPORT_REMISE,
				   namePlugin);
		
		File filePathEditionDefaut = new File(PathEditionDefaut);
		File fileEditionSpecifiquesClient = new File(reportPluginClients);
		File fileEditionSpecifiques = new File(reportPlugin);
		
		constEdition.fillMapNameExpandbar(true);
		constEdition.valuePropertieNamePlugin = namePlugin;
		constEdition.valuePropertiePathEditionDefaut = ConstEdition.FICHE_FILE_REPORT_REMISE;
		constEdition.valuePropertieCommentEditions = String.format(ConstEdition.COMMENT_ONE_EDITION_ENTITY, 
				namePlugin,namePlugin);
		
		File fileEditionsSuppFacture = constEdition.makeFolderEditions(reportEditionSupp);
		
		boolean flag = constEdition.getAllInfosEdition(filePathEditionDefaut,null,namePlugin,
				  fileEditionSpecifiquesClient,fileEditionSpecifiques,TaRemise.class.getSimpleName(),
				  null,false,fileEditionsSuppFacture);
		
		/********************/
		createFieldParamEdition();
		
		
			
			/** 08/02/2010 **/
			addField(new BooleanFieldEditor(PreferenceConstants.editionImprimerDirect,
					PreferenceConstants.EDITION_IMPRIMER_DIRECT,getFieldEditorParent()));
			addField(new BooleanFieldEditor(PreferenceConstants.afficheEditionImprimer,
					PreferenceConstants.AFFICHE_EDITION_IMPRIMER,getFieldEditorParent()));
			addField(
					new BooleanFieldEditor(PreferenceConstants.AFFICHER_SELECTION_EDITION_EDITION_EN_LISTE,
							"Afficher la sélection des editions en liste", getFieldEditorParent()));

	}		
	@Override
	protected void performApply() {
		super.performApply();

		/** 05/01/2010 Add **/
		constEdition.saveProprietyPreferencPage();
	}


	public void createFieldChoixDestinataire(){
		try {
			addField(
					new BooleanFieldEditor(PreferenceConstants.AFFICHER_SELECTION_EDITION_EDITION_EN_LISTE, "Afficher la sélection des editions en liste", getFieldEditorParent()));
		} catch (Exception e1) {
			logger.error("", e1);
		}
		
	}

	

}