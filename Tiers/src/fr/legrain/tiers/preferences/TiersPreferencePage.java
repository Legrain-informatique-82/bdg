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

public class TiersPreferencePage extends FieldEditorPreferencePageLGR{
	
	static Logger logger = Logger.getLogger(TiersPreferencePage.class.getName());
		
	public TiersPreferencePage() {
		super(GRID);
		String idPlugin = TiersPlugin.getDefault().getBundle().getSymbolicName();
		this.constEdition = new ConstEdition();
		this.setFolderEdition(constEdition.FOLDER_EDITION);
		this.setFolderEditionClients(constEdition.FOLDER_EDITION_CLIENT);
		this.setFolderEditionReportPlugin(constEdition.FICHE_FILE_REPORT_TIERS);
		this.setNamePlugin(idPlugin);
		
		reportPlugin =ConstEdition.pathRepertoireEditionsSpecifiques()+ConstEdition.SEPARATOR
		+idPlugin+ConstEdition.SEPARATOR+TaTiers.class.getSimpleName();
		
		reportPluginClients=ConstEdition.pathRepertoireEditionsSpecifiquesClient()+ConstEdition.SEPARATOR+
		idPlugin+ConstEdition.SEPARATOR+TaTiers.class.getSimpleName();
		
		reportEditionSupp = Const.PATH_FOLDER_EDITION_SUPP+ConstEdition.SEPARATOR+idPlugin+ConstEdition.SEPARATOR
	    +TaTiers.class.getSimpleName();
	    
		setPreferenceStore(TiersPlugin.getDefault().getPreferenceStore());
		setDescription("Paramètrage des codes tiers");
		
	}
	
	/**
	 * Creates the field editors. Field editors are abstractions of
	 * the common GUI blocks needed to manipulate various types
	 * of preferences. Each field editor knows how to save and
	 * restore itself.
	 */
	public void createFieldEditors() {
		addField(
				new StringFieldEditor(PreferenceConstants.FIXE, "fixe", getFieldEditorParent()));
//		addField(
//				new StringFieldEditor(PreferenceConstants.EXO, "exo", getFieldEditorParent()));
		addField(
				new StringFieldEditor(PreferenceConstants.COMPTEUR, "Compteur", getFieldEditorParent()));
		addField(
				new StringFieldEditor(PreferenceConstants.TADR_DEFAUT, "Type d'adresse par défaut", getFieldEditorParent()));
		addField(
				new StringFieldEditor(PreferenceConstants.TTEL_DEFAUT, "Type de téléphone par défaut", getFieldEditorParent()));
		addField(
				new StringFieldEditor(PreferenceConstants.TEMAIL_DEFAUT, "Type d'email par défaut", getFieldEditorParent()));
		addField(
				new StringFieldEditor(PreferenceConstants.TWEB_DEFAUT, "Type web par défaut", getFieldEditorParent()));
		
		addField(
				new StringFieldEditor(PreferenceConstants.TIERS_COMPTE_COMPTALE_DEFAUT, "Compte comptable par defaut", getFieldEditorParent()));
		addField(
				new BooleanFieldEditor(PreferenceConstants.TIERS_SAISIE_TTC_DEFAUT, "Saisie TTC", getFieldEditorParent()));
//		addField(
//				new StringFieldEditor(PreferenceConstants.TIERS_TYPE_REP_COURRIER, "Type liens répertoire courrier", getFieldEditorParent()));
		
		/** ajouter 28/12/2009 **/
		Bundle bundleCourant = TiersPlugin.getDefault().getBundle();
		String namePlugin = bundleCourant.getSymbolicName();
		
		String PathEditionDefaut = ConstEdition.pathFichierEditionsSpecifiques(ConstEdition.FICHE_FILE_REPORT_TIERS,
				   namePlugin);

		File filePathEditionDefaut = new File(PathEditionDefaut);
		File fileEditionSpecifiquesClient = new File(reportPluginClients);
		File fileEditionSpecifiques = new File(reportPlugin);
		
		File fileEditionsSuppTiers = constEdition.makeFolderEditions(reportEditionSupp);
		
		String editionDynamique = Const.C_RCP_INSTANCE_LOCATION+"/"+Const.C_NOM_PROJET_TMP+"/"+TaTiers.class.getSimpleName()
		  								+"/"+Const.C_NOM_VU_TIERS+".rptdesign";
		
		File fileReportDynamique = new File(editionDynamique);
		constEdition.setCommentEditionDynamique(ConstEditionTiers.COMMENTAIRE_EDITION_DEFAUT);
		
		constEdition.fillMapNameExpandbar(false);
		constEdition.valuePropertieNamePlugin = namePlugin;
		constEdition.valuePropertiePathEditionDefaut = ConstEdition.FICHE_FILE_REPORT_TIERS;
		constEdition.valuePropertieCommentEditions = String.format(ConstEdition.COMMENT_ONE_EDITION_ENTITY, 
				namePlugin,namePlugin);
		
		boolean flag = constEdition.getAllInfosEdition(filePathEditionDefaut,fileReportDynamique,namePlugin,
				  fileEditionSpecifiquesClient,fileEditionSpecifiques,TaTiers.class.getSimpleName(),
				  ConstEditionTiers.COMMENTAIRE_EDITION_DEFAUT,true,fileEditionsSuppTiers);

		/************************/	
		createFieldParamEdition();
		
		/** 08/02/2010 **/
		addField(new BooleanFieldEditor(PreferenceConstants.editionImprimerDirect,
				PreferenceConstants.EDITION_IMPRIMER_DIRECT,getFieldEditorParent()));
		addField(new BooleanFieldEditor(PreferenceConstants.afficheEditionImprimer,
				PreferenceConstants.AFFICHE_EDITION_IMPRIMER,getFieldEditorParent()));
	}


	@Override
	protected void performApply() {
		super.performApply();

		/** 30/12/2009 add**/
		constEdition.saveProprietyPreferencPage();
		
		/*******************/
		
		PropertiesConfiguration listeGestCode = new PropertiesConfiguration() ;
		//File fProp = new File(Const.C_FICHIER_GESTCODE) ;

		// Charge le contenu de ton fichier properties dans un objet Properties
		FileInputStream stream;
		try {
			stream = new FileInputStream(Const.C_FICHIER_GESTCODE);
			listeGestCode.load(stream) ;
			stream.close();
			String taNouvelleValeur=null;
			IPreferenceStore store = TiersPlugin.getDefault().getPreferenceStore();

			// Change la valeur de la clé taCle dans l'objet Properties
			taNouvelleValeur=TiersPlugin.getDefault().getPreferenceStore().getString(PreferenceConstants.FIXE) ;
			listeGestCode.setProperty(PreferenceConstants.FIXE,taNouvelleValeur) ;

//			taNouvelleValeur=TiersPlugin.getDefault().getPreferenceStore().getString(PreferenceConstants.EXO) ;
//			listeGestCode.setProperty(PreferenceConstants.EXO,taNouvelleValeur) ;

			taNouvelleValeur=TiersPlugin.getDefault().getPreferenceStore().getString(PreferenceConstants.COMPTEUR) ;
			listeGestCode.setProperty(PreferenceConstants.COMPTEUR,taNouvelleValeur) ;

			// Charge le contenu de ton objet Properties dans ton fichier properties
			FileOutputStream oStream = new FileOutputStream(Const.C_FICHIER_GESTCODE);
			listeGestCode.save(oStream,null) ;
			oStream.close();

		} catch (ConfigurationException e) {
			logger.error("",e);
		
		} catch (IOException e) {
			logger.error("",e);
		}
	}	
	
	 @Override
		protected void performDefaults() {
			PreferenceInitializer.initDefautProperties();
			super.performDefaults();
		}
	 


}