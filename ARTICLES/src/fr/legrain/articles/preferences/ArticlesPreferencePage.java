package fr.legrain.articles.preferences;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.log4j.Logger;
import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.jface.preference.FieldEditor;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.StringFieldEditor;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.hibernate.cfg.reveng.TableFilter;
import org.osgi.framework.Bundle;

import fr.legrain.articles.ArticlesPlugin;
import fr.legrain.articles.dao.TaArticle;
import fr.legrain.articles.ecran.ConstEditionArticle;
import fr.legrain.edition.actions.AnalyseFileReport;
import fr.legrain.edition.actions.ConstEdition;
import fr.legrain.edition.divers.FieldEditorPreferencePageLGR;
import fr.legrain.edition.divers.PreferencesConstantsEdition;
import fr.legrain.edition.divers.ScrolledCompositeFieldEditor;
import fr.legrain.gestCom.Appli.Const;
import fr.legrain.tiers.dao.TaTiers;


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

public class ArticlesPreferencePage	extends FieldEditorPreferencePageLGR
	implements IWorkbenchPreferencePage {
	
	static Logger logger = Logger.getLogger(ArticlesPreferencePage.class.getName());
		
	public ArticlesPreferencePage() {
		super(GRID);
		String idPlugin = ArticlesPlugin.getDefault().getBundle().getSymbolicName();
		this.constEdition = new ConstEdition();
		this.setFolderEdition(constEdition.FOLDER_EDITION);
		this.setFolderEditionClients(constEdition.FOLDER_EDITION_CLIENT);
		this.setFolderEditionReportPlugin(constEdition.FICHE_FILE_REPORT_ARTICLES);
		this.setNamePlugin(idPlugin);
		File fileReportPlugin = new File(ConstEdition.pathRepertoireEditionsSpecifiques()+"//"+
										 idPlugin+"/"+
										 TaArticle.class.getSimpleName());
		reportPlugin = constEdition.remplaceBackSlashAndSlash(fileReportPlugin.getPath());
		
		File fileReportPluginClients = new File(ConstEdition.pathRepertoireEditionsSpecifiquesClient()+"//"+
				idPlugin+"/"+TaArticle.class.getSimpleName());
		reportPluginClients = constEdition.remplaceBackSlashAndSlash(fileReportPluginClients.getPath());
		
		/** 23/02/2010 **/
		reportEditionSupp = Const.PATH_FOLDER_EDITION_SUPP+ConstEdition.SEPARATOR+idPlugin+ConstEdition.SEPARATOR
						    +TaArticle.class.getSimpleName();
		setPreferenceStore(ArticlesPlugin.getDefault().getPreferenceStore());
		setDescription("Paramètrage des codes articles");
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
		
		/** ajouter 28/12/2009 **/
		
		Bundle bundleCourant = ArticlesPlugin.getDefault().getBundle();
		String namePlugin = bundleCourant.getSymbolicName();
		
		String PathEditionDefaut = ConstEdition.pathFichierEditionsSpecifiques(ConstEdition.FICHE_FILE_REPORT_ARTICLES,
								   namePlugin);
		
		File filePathEditionDefaut = new File(PathEditionDefaut);
		File fileEditionSpecifiquesClient = new File(reportPluginClients);
		File fileEditionSpecifiques = new File(reportPlugin);
		/** 23/02/2010 **/
//		File fileEditionsSuppArticle = new File(reportEditionSupp);
//		if(!fileEditionsSuppArticle.exists()){
//			fileEditionsSuppArticle.mkdirs();
//		}
		File fileEditionsSuppArticle = constEdition.makeFolderEditions(reportEditionSupp);
		
		String editionDynamique = Const.C_RCP_INSTANCE_LOCATION+ConstEdition.SEPARATOR+Const.C_NOM_PROJET_TMP+
								  ConstEdition.SEPARATOR+TaArticle.class.getSimpleName()+
								  ConstEdition.SEPARATOR+Const.C_NOM_VU_ARTICLE+".rptdesign";
						
		File fileReportDynamique = new File(editionDynamique);
		constEdition.setCommentEditionDynamique(ConstEditionArticle.COMMENTAIRE_EDITION_DEFAUT);
		constEdition.fillMapNameExpandbar(false);
		
		boolean flag = constEdition.getAllInfosEdition(filePathEditionDefaut,fileReportDynamique,namePlugin,
				  fileEditionSpecifiquesClient,fileEditionSpecifiques,TaArticle.class.getSimpleName(),
				  ConstEditionArticle.COMMENTAIRE_EDITION_DEFAUT,true,fileEditionsSuppArticle);
				
		constEdition.valuePropertieNamePlugin = namePlugin;
		constEdition.valuePropertiePathEditionDefaut = ConstEdition.FICHE_FILE_REPORT_ARTICLES;
		constEdition.valuePropertieCommentEditions = String.format(ConstEdition.COMMENT_ONE_EDITION_ENTITY, 
				namePlugin,namePlugin);
		createFieldParamEdition();
		
		/** 08/02/2010 **/
		addField(new BooleanFieldEditor(PreferenceConstants.editionImprimerDirect,
				PreferenceConstants.EDITION_IMPRIMER_DIRECT,getFieldEditorParent()));
		
		addField(new BooleanFieldEditor(PreferenceConstants.afficheEditionImprimer,
				PreferenceConstants.AFFICHE_EDITION_IMPRIMER,getFieldEditorParent()));
		
		addField(new BooleanFieldEditor(PreferenceConstants.REGENERATION_URL_REWRITING_POUR_CATALOGUE_WEB_A_PARTIR_ECRAN_PRINCIPAL,
				"Regénération de l'URL rewriting pour le catalogue web à partir de la description principale",getFieldEditorParent()));
		
		addField(new BooleanFieldEditor(PreferenceConstants.REGENERATION_DESCRIPTION_POUR_CATALOGUE_WEB_A_PARTIR_ECRAN_PRINCIPAL,
				"Regénération de la description pour le catalogue web à partir de la description principale",getFieldEditorParent()));
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
			IPreferenceStore store = ArticlesPlugin.getDefault().getPreferenceStore();

			// Change la valeur de la clé taCle dans l'objet Properties
			taNouvelleValeur=ArticlesPlugin.getDefault().getPreferenceStore().getString(PreferenceConstants.FIXE) ;
			listeGestCode.setProperty(PreferenceConstants.FIXE,taNouvelleValeur) ;

//			taNouvelleValeur=TiersPlugin.getDefault().getPreferenceStore().getString(PreferenceConstants.EXO) ;
//			listeGestCode.setProperty(PreferenceConstants.EXO,taNouvelleValeur) ;

			taNouvelleValeur=ArticlesPlugin.getDefault().getPreferenceStore().getString(PreferenceConstants.COMPTEUR) ;
			listeGestCode.setProperty(PreferenceConstants.COMPTEUR,taNouvelleValeur) ;

//			 Charge le contenu de ton objet Properties dans ton fichier properties
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