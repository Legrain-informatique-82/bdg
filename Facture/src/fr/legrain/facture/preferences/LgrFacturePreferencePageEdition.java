package fr.legrain.facture.preferences;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.log4j.Logger;
import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.jface.preference.RadioGroupFieldEditor;
import org.eclipse.jface.preference.StringFieldEditor;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.IWorkbench;
import org.osgi.framework.Bundle;

import fr.legrain.documents.dao.TaFacture;
import fr.legrain.edition.actions.ConstEdition;
import fr.legrain.edition.divers.FieldEditorPreferencePageLGR;
import fr.legrain.facture.FacturePlugin;
import fr.legrain.gestCom.Appli.Const;

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

public class LgrFacturePreferencePageEdition extends FieldEditorPreferencePageLGR{
	
	private RadioGroupFieldEditor radioGroupChoixDestinataire = null;
//	private String pathEditionFragement = null;
//	private String pathEditionSpecifiquesClient = null;
//	
//	private ListEditorAddRemoveEditLgr listeTypeTiers = null;
	
	 @Override
	protected void performDefaults() {
		PreferenceInitializerProject.initDefautProperties();
		super.performDefaults();
	}

//	static private PropertiesConfiguration listeGestCode = new PropertiesConfiguration();
	 static Logger logger = Logger.getLogger(LgrFacturePreferencePageEdition.class.getName());
	  
	public LgrFacturePreferencePageEdition() {
		super(GRID);
		String idPlugin = FacturePlugin.getDefault().getBundle().getSymbolicName();
		this.constEdition = new ConstEdition();
		this.setFolderEdition(constEdition.FOLDER_EDITION);
		this.setFolderEditionClients(constEdition.FOLDER_EDITION_CLIENT);
		this.setFolderEditionReportPlugin(constEdition.FICHE_FILE_REPORT_FACTURE);
		this.setNamePlugin(idPlugin);
		/** fragement **/
		reportPlugin =ConstEdition.pathRepertoireEditionsSpecifiques()+ConstEdition.SEPARATOR+
					  idPlugin+ConstEdition.SEPARATOR+TaFacture.class.getSimpleName();
		/** plugin EditionsSpecifiques **/
		reportPluginClients=ConstEdition.pathRepertoireEditionsSpecifiquesClient()+ConstEdition.SEPARATOR+
							idPlugin+ConstEdition.SEPARATOR+TaFacture.class.getSimpleName();
		setPreferenceStore(FacturePlugin.getDefault().getPreferenceStore());
		setDescription("Paramètrage des Editions des factures");
		
		/** 23/02/2010 **/
		reportEditionSupp = Const.PATH_FOLDER_EDITION_SUPP+ConstEdition.SEPARATOR+idPlugin+ConstEdition.SEPARATOR
	    					+TaFacture.class.getSimpleName();
	}
	
	/**
	 * Creates the field editors. Field editors are abstractions of
	 * the common GUI blocks needed to manipulate various types
	 * of preferences. Each field editor knows how to save and
	 * restore itself.
	 */
	public void createFieldEditors() {
		/** 05/01/2010 Add **/
		Bundle bundleCourant = FacturePlugin.getDefault().getBundle();
		String namePlugin = bundleCourant.getSymbolicName();
		
		String PathEditionDefaut = ConstEdition.pathFichierEditionsSpecifiques(ConstEdition.FICHE_FILE_REPORT_FACTURE,
				   namePlugin);
		
		File filePathEditionDefaut = new File(PathEditionDefaut);
		File fileEditionSpecifiquesClient = new File(reportPluginClients);
		File fileEditionSpecifiques = new File(reportPlugin);
		
		constEdition.fillMapNameExpandbar(true);
		constEdition.valuePropertieNamePlugin = namePlugin;
		constEdition.valuePropertiePathEditionDefaut = ConstEdition.FICHE_FILE_REPORT_FACTURE;
		constEdition.valuePropertieCommentEditions = String.format(ConstEdition.COMMENT_ONE_EDITION_ENTITY, 
				namePlugin,namePlugin);
		
		File fileEditionsSuppFacture = constEdition.makeFolderEditions(reportEditionSupp);
		
		boolean flag = constEdition.getAllInfosEdition(filePathEditionDefaut,null,namePlugin,
				  fileEditionSpecifiquesClient,fileEditionSpecifiques,TaFacture.class.getSimpleName(),
				  null,false,fileEditionsSuppFacture);
		
		/********************/
		createFieldParamEdition();
		
		createFieldChoixDestinataire();
		
			StringFieldEditor fieldTypeAdresse =new StringFieldEditor(PreferenceConstants.TYPE_ADRESSE_CORRESPONDANCE,
					"Type adresse de correspondance <?>",getFieldEditorParent());
			addField(fieldTypeAdresse);//

			String hint = "* Certain de vos clients font peut-être traiter leurs factures par des sociétés de services."
			+System.getProperty("line.separator")+
			"Pour envoyer une facture à une autre adresse que celle de votre client, vous pouvez créer un"
			+System.getProperty("line.separator")+
			"type d'adresse spécial qui sera imprimé sur un document en complément de la facture."
			+System.getProperty("line.separator")+
			"Exemple : type : Correspondance";
			fieldTypeAdresse.getTextControl(getFieldEditorParent()).setToolTipText(hint);
			fieldTypeAdresse.getLabelControl(getFieldEditorParent()).setToolTipText(hint);
			
			
			/** 08/02/2010 **/
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

public RadioGroupFieldEditor getRadioGroupChoixDestinataire() {
	return radioGroupChoixDestinataire;
}

public void setRadioGroupChoixDestinataire(
		RadioGroupFieldEditor radioGroupChoixDestinataire) {
	this.radioGroupChoixDestinataire = radioGroupChoixDestinataire;
}
public void init(IWorkbench workbench) {
	// TODO Auto-generated method stub
	setPreferenceStore(FacturePlugin.getDefault().getPreferenceStore()); 
}

}