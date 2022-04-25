package fr.legrain.apporteur.preferences;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.log4j.Logger;
import org.eclipse.core.databinding.observable.Realm;
import org.eclipse.jface.databinding.swt.SWTObservables;
import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.RadioGroupFieldEditor;
import org.eclipse.jface.preference.StringFieldEditor;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Control;
import org.osgi.framework.Bundle;

import fr.legrain.apporteur.apporteurPlugin;
import fr.legrain.articles.dao.TaArticle;
import fr.legrain.document.divers.ListEditorGestionTypeTiersDocument;
import fr.legrain.documents.dao.TaApporteur;
import fr.legrain.edition.actions.ConstEdition;
import fr.legrain.edition.divers.FieldEditorPreferencePageLGR;
import fr.legrain.gestCom.Appli.Const;
import fr.legrain.gestCom.librairiesEcran.swt.ListEditorAddRemoveEditLgr;
import fr.legrain.gestCom.librairiesEcran.swt.StringCommentaireEditor;

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

public class LgrApporteurPreferencePage
extends FieldEditorPreferencePageLGR{
	

//	public static final String TYPE_FILE_REPORT =".rptdesign";
	private RadioGroupFieldEditor radioGroupChoixDestinataire = null;
	
	private ListEditorAddRemoveEditLgr listeTypeTiers = null;
	
//	String reportPlugin =ConstEdition.pathRepertoireEditionsSpecifiques()+"\\"+apporteurPlugin.getDefault().getBundle().getSymbolicName();
//	String [][] liste=null;
	

	 @Override
	protected void performDefaults() {
		PreferenceInitializer.initDefautProperties();
		super.performDefaults();
	}

	static private PropertiesConfiguration listeGestCode = new PropertiesConfiguration();
	 static Logger logger = Logger.getLogger(LgrApporteurPreferencePage.class.getName());
	  
	public LgrApporteurPreferencePage() {
		super(GRID);
		String idPlugin = apporteurPlugin.getDefault().getBundle().getSymbolicName();
		this.constEdition = new ConstEdition();
		this.setFolderEdition(constEdition.FOLDER_EDITION);
		this.setFolderEditionClients(constEdition.FOLDER_EDITION_CLIENT);
		this.setFolderEditionReportPlugin(constEdition.FICHE_FILE_REPORT_APPORTEUR);
		this.setNamePlugin(idPlugin);
		
		reportPlugin = ConstEdition.pathRepertoireEditionsSpecifiques()+"//"+idPlugin+"/"+
		TaApporteur.class.getSimpleName();
		
		reportPluginClients= ConstEdition.pathRepertoireEditionsSpecifiquesClient()+"//"+idPlugin
						    +"/"+TaApporteur.class.getSimpleName();	
		
		setPreferenceStore(apporteurPlugin.getDefault().getPreferenceStore());
		setDescription("Paramètres de codification des factures apporteur");
		
		reportEditionSupp = Const.PATH_FOLDER_EDITION_SUPP+ConstEdition.SEPARATOR+idPlugin+ConstEdition.SEPARATOR
		+TaApporteur.class.getSimpleName();
	}
	
	/**
	 * Creates the field editors. Field editors are abstractions of
	 * the common GUI blocks needed to manipulate various types
	 * of preferences. Each field editor knows how to save and
	 * restore itself.
	 */
	public void createFieldEditors() {
		addField(
			new StringFieldEditor(PreferenceConstants.TA_DOC_FIXE_1, "fixe", getFieldEditorParent()));
		addField(
				new StringFieldEditor(PreferenceConstants.TA_DOC_EXO, "exo", getFieldEditorParent()));
		addField(
			new StringFieldEditor(PreferenceConstants.TA_DOC_COMPTEUR, "Compteur", getFieldEditorParent()));
		addField(
				new BooleanFieldEditor(PreferenceConstants.IMPRIMER_AUTO, "Impression automatique", getFieldEditorParent()));
		addField(
				new StringCommentaireEditor(PreferenceConstants.COMMENTAIRE, "Commentaire à insérer", getFieldEditorParent()));
		
		/** 05/01/2010 Add **/
		Bundle bundleCourant = apporteurPlugin.getDefault().getBundle();
		String namePlugin = bundleCourant.getSymbolicName();
		
		String PathEditionDefaut = ConstEdition.pathFichierEditionsSpecifiques(ConstEdition.FICHE_FILE_REPORT_APPORTEUR,
				   namePlugin);
		
		File filePathEditionDefaut = new File(PathEditionDefaut);
		File fileEditionSpecifiquesClient = new File(reportPluginClients);
		File fileEditionSpecifiques = new File(reportPlugin);
		
		constEdition.fillMapNameExpandbar(true);
		
		constEdition.valuePropertieNamePlugin = namePlugin;
		constEdition.valuePropertiePathEditionDefaut = ConstEdition.FICHE_FILE_REPORT_APPORTEUR;
		constEdition.valuePropertieCommentEditions = String.format(ConstEdition.COMMENT_ONE_EDITION_ENTITY, 
				namePlugin,namePlugin);
		
		File fileEditionsSuppApporteur = constEdition.makeFolderEditions(reportEditionSupp);
		
		boolean flag = constEdition.getAllInfosEdition(filePathEditionDefaut,null,namePlugin,
				  fileEditionSpecifiquesClient,fileEditionSpecifiques,TaApporteur.class.getSimpleName(),
				  null,false,fileEditionsSuppApporteur);
		
		/********************/
		createFieldParamEdition();
		createFieldChoixDestinataire();
		
		StringFieldEditor fieldTypeAdresse =new StringFieldEditor(PreferenceConstants.TYPE_ADRESSE_CORRESPONDANCE,
				"Type adresse de correspondance <?>",getFieldEditorParent());
		addField(fieldTypeAdresse);//

		String hint = "* Certain de vos clients font peut-être traiter leurs factures apporteur par des sociétés de services."
		+System.getProperty("line.separator")+
		"Pour envoyer une facture apporteur à une autre adresse que celle de votre client, vous pouvez créer un"
		+System.getProperty("line.separator")+
		"type d'adresse spécial qui sera imprimé sur un document en complément de la facture apporteur."
		+System.getProperty("line.separator")+
		"Exemple : type : Correspondance";
		fieldTypeAdresse.getTextControl(getFieldEditorParent()).setToolTipText(hint);
		fieldTypeAdresse.getLabelControl(getFieldEditorParent()).setToolTipText(hint);

		createListeTypeTiers();
		
		addField(new BooleanFieldEditor(PreferenceConstants.editionImprimerDirect,
				PreferenceConstants.EDITION_IMPRIMER_DIRECT,getFieldEditorParent()));
		
		addField(new BooleanFieldEditor(PreferenceConstants.afficheEditionImprimer,
				PreferenceConstants.AFFICHE_EDITION_IMPRIMER,getFieldEditorParent()));

	}


					
					
	@Override
	protected void performApply() {
		super.performApply();

		/** 05/01/2010 Add **/
		constEdition.saveProprietyPreferencPage();
		/********************/
		
		PropertiesConfiguration listeGestCode = new PropertiesConfiguration() ;
		//File fProp = new File(Const.C_FICHIER_GESTCODE) ;

		// Charge le contenu de ton fichier properties dans un objet Properties
		FileInputStream stream;
		try {
			stream = new FileInputStream(Const.C_FICHIER_GESTCODE);
			listeGestCode.load(stream) ;
			stream.close();
			String taNouvelleValeur=null;
			IPreferenceStore store = apporteurPlugin.getDefault().getPreferenceStore();

			// Change la valeur de la clé taCle dans l'objet Properties
			taNouvelleValeur=apporteurPlugin.getDefault().getPreferenceStore().getString(PreferenceConstants.TA_DOC_FIXE_1) ;
			listeGestCode.setProperty(PreferenceConstants.TA_DOC_FIXE_1,taNouvelleValeur) ;

			taNouvelleValeur=apporteurPlugin.getDefault().getPreferenceStore().getString(PreferenceConstants.TA_DOC_EXO) ;
			listeGestCode.setProperty(PreferenceConstants.TA_DOC_EXO,taNouvelleValeur) ;

			taNouvelleValeur=apporteurPlugin.getDefault().getPreferenceStore().getString(PreferenceConstants.TA_DOC_COMPTEUR) ;
			listeGestCode.setProperty(PreferenceConstants.TA_DOC_COMPTEUR,taNouvelleValeur) ;

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
	


	


	public void createFieldChoixDestinataire(){
		try {
			addField(
					new BooleanFieldEditor(PreferenceConstants.AFFICHER_SELECTION_EDITION_EDITION_EN_LISTE, "Afficher la sélection des editions en liste", getFieldEditorParent()));
			//création des boutons radios contenant les choix disponibles
			String[][]listeChoix = new String[][]{{"choix 1 : Civilité,Nom,Prénom","choix 1"},{"choix 2 : type entreprise,nom entreprise","choix 2"},{"choix 3 : les 2","choix 3"},};
			radioGroupChoixDestinataire =new RadioGroupFieldEditor(
					PreferenceConstants.LISTE_CHOIX, PreferenceConstants.LISTE_CHOIX, 1,
					listeChoix,				
					getFieldEditorParent(),
					true); 
			addField(radioGroupChoixDestinataire);




			Control[] controls =getRadioGroupChoixDestinataire().getRadioBoxControl(getFieldEditorParent()).getChildren();  
			for (int i = 0; i < controls.length; i++) {
				((org.eclipse.swt.widgets.Button)controls[i]).addSelectionListener(new SelectionListener(){
					public void widgetDefaultSelected(SelectionEvent e) {
						// TODO Auto-generated method stub
						widgetSelected(e);
					}
					public void widgetSelected(SelectionEvent e) {
//						// TODO Auto-generated method stub
//						if(((org.eclipse.swt.widgets.Button)e.getSource()).getSelection()){
//							String value ="";
//							for (int j = 0; j < liste.length; j++) {
//								if(liste[j][0].indexOf(((org.eclipse.swt.widgets.Button)e.getSource()).getText())!=-1)
//									value=liste[j][1];
//							}
//							fieldPathEditionDefaut.setStringValue(value);
//						}					
					}			
				});
			}
//			fieldPathEditionDefaut.setStringValue(getPreferenceStore().getString(PreferenceConstants.P_PATH_EDITION_DEFAUT));
			//selectButtonValue();
		} catch (Exception e1) {
			logger.error("", e1);
		}
		
	}
//public void createFieldParamEdition(){
//	URI uriReportFile;
//	try {
//		Bundle bundleCourant = apporteurPlugin.getDefault().getBundle();
//		URL urlReportFile = Platform.asLocalURL(bundleCourant
//				.getEntry(ConstEdition.FICHE_FILE_REPORT_APPORTEUR));
//		uriReportFile = new URI("file", urlReportFile.getAuthority(),
//				urlReportFile.getPath(), urlReportFile.getQuery(), null);
//		File reportFile = new File(uriReportFile);
//
//		//création du champ + bouton contenant le fichier d'édition choisi pour être celui par défaut
//		fieldPathEditionDefaut = new StringButtonFieldEditorLgr(PreferenceConstants.P_PATH_EDITION_DEFAUT, 
//				"Edition par défaut :", getFieldEditorParent());
//		addField(fieldPathEditionDefaut);
//		fieldPathEditionDefaut.setChangeButtonText("Réinitialiser");		
//		fieldPathEditionDefaut.setValeurParDefaut(reportFile.getAbsolutePath());
//		fieldPathEditionDefaut.getTextControl(getFieldEditorParent()).addModifyListener(new ModifyListener(){
//
//			public void modifyText(ModifyEvent e) {
//				selectButtonValue();
//			}
//			
//		});
//
//		addField(
//				new BooleanFieldEditor(PreferenceConstants.AFFICHER_SELECTION_EDITION, "Afficher la sélection des editions", getFieldEditorParent()));
//
//		//création des boutons radios contenant toutes les éditions du plugin disponibles
//		liste=listeEditions(new File(reportPlugin));
//		if(liste!=null){
//		radioGroupListeEditions =new RadioGroupFieldEditor(
//				PreferenceConstants.LISTE_EDITIONS, PreferenceConstants.LISTE_EDITIONS, 1,
//				liste,				
//				getFieldEditorParent(),
//				true); 
//		addField(radioGroupListeEditions);
//		
//
//
//		Control[] controls =getRadioGroupFieldEditor().getRadioBoxControl(getFieldEditorParent()).getChildren();  
//		for (int i = 0; i < controls.length; i++) {
//			((org.eclipse.swt.widgets.Button)controls[i]).addSelectionListener(new SelectionListener(){
//				public void widgetDefaultSelected(SelectionEvent e) {
//					// TODO Auto-generated method stub
//					widgetSelected(e);
//				}
//				public void widgetSelected(SelectionEvent e) {
//					// TODO Auto-generated method stub
//					if(((org.eclipse.swt.widgets.Button)e.getSource()).getSelection()){
//						String value ="";
//						for (int j = 0; j < liste.length; j++) {
//							if(liste[j][0].indexOf(((org.eclipse.swt.widgets.Button)e.getSource()).getText())!=-1)
//								value=liste[j][1];
//						}
//						fieldPathEditionDefaut.setStringValue(value);
//					}					
//				}			
//			});
//		}
//		}
//		fieldPathEditionDefaut.setStringValue(getPreferenceStore().getString(PreferenceConstants.P_PATH_EDITION_DEFAUT));
//		//selectButtonValue();
//	} catch (Exception e1) {
//		logger.error("", e1);
//	}
//	
//}

public RadioGroupFieldEditor getRadioGroupChoixDestinataire() {
	return radioGroupChoixDestinataire;
}

public void setRadioGroupChoixDestinataire(
		RadioGroupFieldEditor radioGroupChoixDestinataire) {
	this.radioGroupChoixDestinataire = radioGroupChoixDestinataire;
}
public void createListeTypeTiers(){
	try {//
		Realm realm = SWTObservables.getRealm(getFieldEditorParent().getDisplay());

		listeTypeTiers = new ListEditorGestionTypeTiersDocument(
				PreferenceConstants.TYPE_TIERS_DOCUMENT, "Liste des tiers à utiliser",
				"Ajouter","Supprimer",getFieldEditorParent(),realm);
		addField(listeTypeTiers);
	} catch (Exception e1) {
		logger.error("", e1);
	}	
}
}