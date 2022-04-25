package fr.legrain.edition.divers;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.IntegerFieldEditor;
import org.eclipse.jface.preference.RadioGroupFieldEditor;
import org.eclipse.jface.preference.StringFieldEditor;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.osgi.framework.Bundle;

import fr.legrain.edition.actions.AnalyseFileReport;
import fr.legrain.edition.actions.ConstEdition;
import fr.legrain.edition.preferences.PropertiesFilePreference;
import fr.legrain.gestCom.Appli.Const;
import fr.legrain.gestCom.librairiesEcran.preferences.LgrPreferenceConstantsDocuments;
import fr.legrain.gestCom.librairiesEcran.swt.IStringButtonFieldEditorLgrListener;
import fr.legrain.gestCom.librairiesEcran.swt.StringButtonFieldEditorLgr;
import fr.legrain.gestCom.librairiesEcran.swt.StringButtonFieldEditorLgrEvent;

public class FieldEditorPreferencePageLGR extends FieldEditorPreferencePage implements IWorkbenchPreferencePage , IStringButtonFieldEditorLgrListener{
	 static Logger logger = Logger.getLogger(FieldEditorPreferencePageLGR.class.getName());

	private RadioGroupFieldEditor radioGroupFieldEditor = null;
	private StringButtonFieldEditorLgr fieldPathEditionDefaut = null;
	public static final String TYPE_FILE_REPORT =".rptdesign";
	protected String folderEdition=null;
	protected String folderEditionClients=null;
	protected String folderEditionReportPlugin=null;
	protected String namePlugin=null;

	protected String reportPlugin =null;
	protected String reportPluginClients=null;
	/** 23/02/2010 **/
	protected String reportEditionSupp=null;
	
	private String [][] liste=null;
	
	/** 29/11/2009 **/
	protected ConstEdition constEdition;
	
	public FieldEditorPreferencePageLGR(int grid) {
		super(GRID);
	}
	
	
	@Override
	protected void createFieldEditors() {
		// TODO Auto-generated method stub

	}
	private String recupRelatifPathEdition(String path){
		Path tmp = new Path(folderEdition);
		int rang = path.lastIndexOf(tmp.toOSString());
		if(rang!=-1)return path.substring(rang);
		
		tmp = new Path(folderEditionClients);
		rang = path.lastIndexOf(tmp.toOSString());
		if(rang!=-1)return path.substring(rang);

		tmp = new Path(folderEditionReportPlugin);
		rang = path.lastIndexOf(tmp.toOSString());
		if(rang!=-1)return path.substring(rang);
		return path;			
	}

	public void createFieldParamEdition(){
		
		if(constEdition==null) {
			constEdition = new ConstEdition();
		}
		
		Properties props = PropertiesFilePreference.getProperties();
		InputStream in;
		
		try {
			in = new BufferedInputStream(new FileInputStream(Const.C_FICHIER_PREFERENCE_PAGE));
			props.load(in);
			in.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
			logger.error("", e);
		}
	

		
		String pathFileDefautReportEdition = props.getProperty(ConstEdition.PROPERTIES_PATH_EDITIONDEFAUT+namePlugin);
		if(pathFileDefautReportEdition != null){
			constEdition.valuePropertiePathEditionDefaut = pathFileDefautReportEdition;
		}
		String commentDefautReportEdition = props.getProperty(ConstEdition.PROPERTIES_COMMENT_DEFAUT_EDITION+namePlugin);
		if(commentDefautReportEdition != null){
			constEdition.valuePropertieCommentEditions = commentDefautReportEdition;
		}
		String namePluginReportEdition = props.getProperty(ConstEdition.PROPERTIES_PLUGIN_EDITIONDEFAUT+namePlugin);
		if(namePluginReportEdition != null){
			constEdition.valuePropertieNamePlugin = namePluginReportEdition;
		}
		URI uriReportFile;
		try {
			Bundle bundleCourant = Platform.getBundle(namePlugin);
			URL urlReportFile = Platform.asLocalURL(bundleCourant
					.getEntry(folderEditionReportPlugin));
			uriReportFile = new URI("file", urlReportFile.getAuthority(),
					urlReportFile.getPath(), urlReportFile.getQuery(), null);
			File reportFile = new File(uriReportFile);

			//création du champ + bouton contenant le fichier d'édition choisi pour être celui par défaut
			fieldPathEditionDefaut = new StringButtonFieldEditorLgr(PreferencesConstantsEdition.P_PATH_EDITION_DEFAUT, 
					"Edition par défaut :", getFieldEditorParent());
			addField(fieldPathEditionDefaut);
			fieldPathEditionDefaut.setChangeButtonText("Réinitialiser");		
//			fieldPathEditionDefaut.setValeurParDefaut(
//					constEdition.remplaceBackSlashAndSlash(recupRelatifPathEdition(reportFile.getAbsolutePath())));
			
			fieldPathEditionDefaut.setValeurParDefaut(constEdition.valuePropertiePathEditionDefaut);
			
			/** 05/01/2010 add **/
			fieldPathEditionDefaut.addPagePreferencePageListener(this);
			
//			fieldPathEditionDefaut.getTextControl(getFieldEditorParent()).addModifyListener(new ModifyListener(){
//				public void modifyText(ModifyEvent e) {
//					selectButtonValue();
//				}
//			});

			addField(new BooleanFieldEditor(PreferencesConstantsEdition.AFFICHER_SELECTION_EDITION, 
					"Afficher la sélection des editions", getFieldEditorParent()));

			addField(new IntegerFieldEditor(LgrPreferenceConstantsDocuments.COUPURE_LIGNE_EDITION, "Coupure des lignes dans les éditions",
					getFieldEditorParent()));
			
			addField(new IntegerFieldEditor(LgrPreferenceConstantsDocuments.PAGE_BREAK_MAXI, "Nombre de lignes maxi dans une page (hors dernière) dans les éditions",
					getFieldEditorParent()));
			
			addField(new IntegerFieldEditor(LgrPreferenceConstantsDocuments.PAGE_BREAK_TOTAUX, "Nombre de lignes dans dernière page dans les éditions",
					getFieldEditorParent()));
			
			

			/** codes isabelle **/
//			addField(new BooleanFieldEditor(PreferencesConstantsEdition.editionImprimerDirect, 
//					PreferencesConstantsEdition.EDITION_IMPRIMER_DIRECT,getFieldEditorParent()));
			
//			création des boutons radios contenant toutes les éditions du plugin disponibles
//			String[][]listeCommune=listeEditions(new File(reportPlugin));
//			String[][]listeClient=listeEditions(new File(reportPluginClients));
//			int nb=listeCommune.length+listeClient.length;
//			liste= new String[listeCommune.length+listeClient.length+1][2];
//			int j=0;
//			for (int i = 0; i < listeCommune.length; i++) {
//				liste[j][0]=listeCommune[i][0];
//				liste[j][1]=listeCommune[i][1];
//				j++;
//			}
//			for (int i = 0; i < listeClient.length; i++) {
//				liste[j][0]=listeClient[i][0];
//				liste[j][1]=listeClient[i][1];
//				j++;
//			}	
//			liste[j][0]=PreferencesConstantsEdition.COMMENTAIRE_EDITION_DEFAUT;
//			liste[j][1]=fieldPathEditionDefaut.getValeurParDefaut();				
//
//			radioGroupFieldEditor =new RadioGroupFieldEditor(
//					PreferencesConstantsEdition.LISTE_EDITIONS, PreferencesConstantsEdition.LISTE_EDITIONS, 1,
//					liste,				
//					getFieldEditorParent(),
//					true); 
//			addField(radioGroupFieldEditor);
//
//			Control[] controls =getRadioGroupFieldEditor().getRadioBoxControl(getFieldEditorParent()).getChildren();  
//			for (int i = 0; i < controls.length; i++) {
//				((org.eclipse.swt.widgets.Button)controls[i]).addSelectionListener(new SelectionListener(){
//					public void widgetDefaultSelected(SelectionEvent e) {
//						// TODO Auto-generated method stub
//						widgetSelected(e);
//					}
//					public void widgetSelected(SelectionEvent e) {
//						// TODO Auto-generated method stub
//						if(((org.eclipse.swt.widgets.Button)e.getSource()).getSelection()){
//							String value ="";
//							for (int j = 0; j < liste.length; j++) {
//								if(liste[j][0].indexOf(((org.eclipse.swt.widgets.Button)e.getSource()).getText())!=-1)
//									value=liste[j][1];
//							}
//							//ConstEdition.FOLDER_EDITION_CLIENT;
//
//							
//							fieldPathEditionDefaut.setStringValue(value);
//						}					
//					}			
//				});
//			}
			/** 29/12/2009 **/
			
			constEdition.setFieldPathEditionDefaut(fieldPathEditionDefaut);
			
			ScrolledCompositeFieldEditor scrolledCompositeFieldEditor = new ScrolledCompositeFieldEditor(
					getFieldEditorParent(),"scrolledCompositeFieldEditor","Liste des éditions disponibles",
					constEdition,namePlugin);	
			addField(scrolledCompositeFieldEditor);
			
			fieldPathEditionDefaut.setStringValue(constEdition.valuePropertiePathEditionDefaut);
			//selectButtonValue();
		} catch (Exception e1) {
			logger.error("", e1);
		}
		
	}
	public void selectButtonValue(){
		for (int j = 0; j < liste.length; j++) {
			if(liste[j][1].indexOf(fieldPathEditionDefaut.getStringValue())!=-1){
				deselectAllButton();
				((org.eclipse.swt.widgets.Button)radioGroupFieldEditor.
						getRadioBoxControl(getFieldEditorParent()).getChildren()[j]).setSelection(true);							
			}							
		}
	}
		
	public void deselectAllButton(){
		for (int i = 0; i < radioGroupFieldEditor.getRadioBoxControl(getFieldEditorParent()).
		getChildren().length; i++) {
			((org.eclipse.swt.widgets.Button)radioGroupFieldEditor.getRadioBoxControl(getFieldEditorParent()).
					getChildren()[i]).setSelection(false);
		}
	}
	
	protected String[][] listeEditions(File fichierEdition){
		Boolean  fileEditionExist = false;
		String[][] resultat = new String[0][0];
		if(fichierEdition.exists()){
			File[] ListFileEdition = fichierEdition.listFiles(new FileFilter(){
				public boolean accept(File pathname) {
					return (pathname.getPath().endsWith(TYPE_FILE_REPORT));					
				}
				
			});
			resultat= new String[ListFileEdition.length][2];
			if(ListFileEdition.length!=0){
				int i=0;
				for (File fileEdition : ListFileEdition) {
					if(fileEdition.isFile()&& fileEdition.getName().endsWith(TYPE_FILE_REPORT)){
						fileEditionExist = true;
						AnalyseFileReport dd = new AnalyseFileReport();
						dd.initializeBuildDesignReportConfig(fileEdition.toString());
						resultat[i][0]=dd.findCommentsReport();
						resultat[i][1]=recupRelatifPathEdition(fileEdition.getAbsolutePath());
						//resultat[i][1]=fileEdition.getAbsolutePath();				
					i++;
					}
				}
//				resultat[i][0]=ConstEdition.COMMENTAIRE_EDITION_DEFAUT;
//				resultat[i][1]=fieldPathEditionDefaut.getValeurParDefaut();				
			}
		}
		return resultat;
	}

	
	public RadioGroupFieldEditor getRadioGroupFieldEditor() {
		return radioGroupFieldEditor;
	}
	public void setRadioGroupFieldEditor(RadioGroupFieldEditor radioGroupFieldEditor) {
		this.radioGroupFieldEditor = radioGroupFieldEditor;
	}
	public StringButtonFieldEditorLgr getFieldPathEditionDefaut() {
		return fieldPathEditionDefaut;
	}
	public void setFieldPathEditionDefaut(
			StringButtonFieldEditorLgr fieldPathEditionDefaut) {
		this.fieldPathEditionDefaut = fieldPathEditionDefaut;
	}
	public String getFolderEdition() {
		return folderEdition;
	}
	public void setFolderEdition(String folderEdition) {
		this.folderEdition = folderEdition;
	}
	public String getFolderEditionClients() {
		return folderEditionClients;
	}
	public void setFolderEditionClients(String folderEditionClients) {
		this.folderEditionClients = folderEditionClients;
	}
	public String getFolderEditionReportPlugin() {
		return folderEditionReportPlugin;
	}
	public void setFolderEditionReportPlugin(String folderEditionReportPlugin) {
		this.folderEditionReportPlugin = folderEditionReportPlugin;
	}
	public String getNamePlugin() {
		return namePlugin;
	}
	public void setNamePlugin(String namePlugin) {
		this.namePlugin = namePlugin;
	}
	public void init(IWorkbench workbench) {
		// TODO Auto-generated method stub
		
	}

	/** 05/01/2010 add **/
	@Override
	public void declencheBtReinitiale(StringButtonFieldEditorLgrEvent evt) {
		// TODO Auto-generated method stub
		constEdition.updateValuePreferencePage(namePlugin);		
	}

}
