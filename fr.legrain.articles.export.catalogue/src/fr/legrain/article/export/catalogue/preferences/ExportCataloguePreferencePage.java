package fr.legrain.article.export.catalogue.preferences;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.jface.preference.FieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.RadioGroupFieldEditor;
import org.eclipse.jface.preference.StringFieldEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import fr.legrain.article.export.catalogue.Activator;
import fr.legrain.article.export.catalogue.handlers.BoutiqueInitializer;
import fr.legrain.boutique.dao.TaSynchroBoutiqueDAO;
import fr.legrain.gestCom.librairiesEcran.preferences.LgrBooleanFieldEditor;


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

public class ExportCataloguePreferencePage
extends FieldEditorPreferencePage
implements IWorkbenchPreferencePage {

	final private List<FieldEditor> listeCompoTout = new ArrayList<FieldEditor>();
	final private List<FieldEditor> listeCompoVerrouPremierEnvoi = new ArrayList<FieldEditor>();

	public ExportCataloguePreferencePage() {
		super(GRID);
		//setPreferenceStore(Activator.getDefault().getPreferenceStoreProject());
		//ScopedPreferenceStore preferences = null;
		//preferences = new ScopedPreferenceStore(new InstanceScope(),"");
		//preferences = new ScopedPreferenceStore(Const.getProjectScopeContext(),Const.getProjectName());
		//preferences.setSearchContexts(new IScopeContext[]{Const.getProjectScopeContext()});
		//setPreferenceStore(preferences);
		//setPreferenceStore(Const.getStore());
		//setPreferenceStore(Const.getStore(Activator.PLUGIN_ID));
		setPreferenceStore(Activator.getDefault().getPreferenceStoreProject());
		setDescription("Paramètres pour l'exportation du catalogue web");
	}
	
	private void initEtat(boolean b) {
		for (FieldEditor fieldEditor : listeCompoTout) {
			fieldEditor.setEnabled(b, getFieldEditorParent());
		}
		if(Activator.getDefault().getPreferenceStoreProject().getBoolean(PreferenceConstantsProject.UTILISE_BOUTIQUE)) {
			TaSynchroBoutiqueDAO dao = new TaSynchroBoutiqueDAO();
			initEtatVerrouPremierEnvoi(!dao.dejaSynchro());
		}
	}
	
	private void initEtatVerrouPremierEnvoi(boolean b) {
		for (FieldEditor fieldEditor : listeCompoVerrouPremierEnvoi) {
			fieldEditor.setEnabled(b, getFieldEditorParent());
		}
	}

	/**
	 * Creates the field editors. Field editors are abstractions of
	 * the common GUI blocks needed to manipulate various types
	 * of preferences. Each field editor knows how to save and
	 * restore itself.
	 */
	public void createFieldEditors() {
		
		final LgrBooleanFieldEditor booleanUtiliseBoutique = new LgrBooleanFieldEditor(PreferenceConstantsProject.UTILISE_BOUTIQUE, "Utilisation d'une boutique prestashop", getFieldEditorParent());
		addField(booleanUtiliseBoutique);
		
		booleanUtiliseBoutique.getCheckbox(getFieldEditorParent()).addSelectionListener(new SelectionListener() {

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				initEtat(booleanUtiliseBoutique.getBooleanValue());
			}

			@Override
			public void widgetSelected(SelectionEvent e) {
				widgetDefaultSelected(e);
			}
		});
		
		StringFieldEditor strHostWS = new StringFieldEditor(PreferenceConstantsProject.WEBSERVICE_HOST, "Hote WebService", getFieldEditorParent());
		addField(strHostWS);
		listeCompoTout.add(strHostWS);
		
		StringFieldEditor strLoginWS = new StringFieldEditor(PreferenceConstantsProject.WEBSERVICE_LOGIN, "Utilisateur WebService", getFieldEditorParent());
		addField(strLoginWS);
		listeCompoTout.add(strLoginWS);
		
		StringFieldEditor strPasswordWS = new StringFieldEditor(PreferenceConstantsProject.WEBSERVICE_PASSWORD, "Mot de passe WebService", getFieldEditorParent());
		addField(strPasswordWS);
		listeCompoTout.add(strPasswordWS);
		
		StringFieldEditor strBaseUriWS = new StringFieldEditor(PreferenceConstantsProject.WEBSERVICE_BASE_URI, "URL base WebService", getFieldEditorParent());
		addField(strBaseUriWS);
		listeCompoTout.add(strBaseUriWS);
				
//		StringFieldEditor strNomFichierExport = new StringFieldEditor(PreferenceConstantsProject.NOM_FICHIER_EXPORT, "Nom du fichier généré", getFieldEditorParent());
//		addField(strNomFichierExport);
//		listeCompoTout.add(strNomFichierExport);
//		
////		addField(
////				new DirectoryFieldEditor(PreferenceConstantsProject.REP_LOC_FICHIER_EXPORT, "Répertoire de stockage du fichier généré", getFieldEditorParent()));
//		
//		StringFieldEditor strHostnameFtpExport = new StringFieldEditor(PreferenceConstantsProject.HOSTNAME_FTP_EXPORT, "Serveur FTP", getFieldEditorParent());
//		addField(strHostnameFtpExport);
//		listeCompoTout.add(strHostnameFtpExport);
//		
//		StringFieldEditor strPortFtpExport = new StringFieldEditor(PreferenceConstantsProject.PORT_FTP_EXPORT, "Port du serveur FTP", getFieldEditorParent());
//		addField(strPortFtpExport);
//		listeCompoTout.add(strPortFtpExport);
//		
//		StringFieldEditor strLoginFtpExport = new StringFieldEditor(PreferenceConstantsProject.LOGIN_FTP_EXPORT, "Nom d'utilisateur FTP", getFieldEditorParent());
//		addField(strLoginFtpExport);
//		listeCompoTout.add(strLoginFtpExport);
//		
//		StringFieldEditor strPasswordFtpExport = new StringFieldEditor(PreferenceConstantsProject.PASSWORD_FTP_EXPORT, "Mot de passe FTP", getFieldEditorParent());
//		addField(strPasswordFtpExport);
//		listeCompoTout.add(strPasswordFtpExport);
//		
//		StringFieldEditor strRepFtpExportData = new StringFieldEditor(PreferenceConstantsProject.REP_FTP_EXPORT_DATA, "Répertoire FTP", getFieldEditorParent());
//		addField(strRepFtpExportData);
//		listeCompoTout.add(strRepFtpExportData);
		
//		Label space1 = new Label( getFieldEditorParent(), SWT.NONE );
//        space1.setText( "________________________________________________________" );
        GridData g = new GridData();
        g.horizontalSpan = 3;
//        space1.setLayoutData(g);
//
//        StringFieldEditor strHostnaleFtpInstallPresta =  new StringFieldEditor(PreferenceConstantsProject.HOSTNAME_FTP_INSTALL_PRESTA, "Serveur FTP Prestashop", getFieldEditorParent());
//        addField(strHostnaleFtpInstallPresta);
//        listeCompoTout.add(strHostnaleFtpInstallPresta);
//        
//		StringFieldEditor strPortFtpInstallPresta = new StringFieldEditor(PreferenceConstantsProject.PORT_FTP_INSTALL_PRESTA, "Port du serveur FTP Prestashop", getFieldEditorParent());
//		addField(strPortFtpInstallPresta);
//		listeCompoTout.add(strPortFtpInstallPresta);
//		
//		StringFieldEditor strLoginFtpInstallPresta = new StringFieldEditor(PreferenceConstantsProject.LOGIN_FTP_INSTALL_PRESTA, "Nom d'utilisateur FTP Prestashop", getFieldEditorParent());
//		addField(strLoginFtpInstallPresta);
//		listeCompoTout.add(strLoginFtpInstallPresta);
//		
//		StringFieldEditor strPrestaFtpInstallPresta = new StringFieldEditor(PreferenceConstantsProject.PASSWORD_FTP_INSTALL_PRESTA, "Mot de passe FTP Prestashop", getFieldEditorParent());
//		addField(strPrestaFtpInstallPresta);
//		listeCompoTout.add(strPrestaFtpInstallPresta);
//		
//		StringFieldEditor strRepFtpInstallPresta = new StringFieldEditor(PreferenceConstantsProject.REP_FTP_INSTALL_PRESTA, "Répertoire FTP installation Prestashop", getFieldEditorParent());
//		addField(strRepFtpInstallPresta);
//		listeCompoTout.add(strRepFtpInstallPresta);
		
		Label space2 = new Label( getFieldEditorParent(), SWT.NONE );
        space2.setText( "________________________________________________________" );
        space2.setLayoutData(g);
        
        RadioGroupFieldEditor radioModeAffichagePrix = new RadioGroupFieldEditor(PreferenceConstantsProject.MODE_AFFICHAGE_PRIX,
        		"Mode affichage du second prix",3,new String[][] {
        				{"Pas de second prix", PreferenceConstantsProject.VALEUR_MODE_AFFICHAGE_PRIX_CLASSIQUE},
        				{"Prix spéciaux", PreferenceConstantsProject.VALEUR_MODE_AFFICHAGE_PRIX_SPECIAL},
        				//{"Déclinaison", PreferenceConstantsProject.VALEUR_MODE_AFFICHAGE_PRIX_DECLINAISON}
        			},getFieldEditorParent());
        addField(radioModeAffichagePrix);
        listeCompoTout.add(radioModeAffichagePrix);
        listeCompoVerrouPremierEnvoi.add(radioModeAffichagePrix);
        
//        BooleanFieldEditor creationAutoSecondPrix = new BooleanFieldEditor(PreferenceConstantsProject.CREATION_AUTO_SECOND_PRIX, "Génération automatique du deuxième prix.", getFieldEditorParent());
//        addField(creationAutoSecondPrix);
//        listeCompoTout.add(creationAutoSecondPrix);
        
		Label space3 = new Label( getFieldEditorParent(), SWT.NONE );
        space3.setText( "________________________________________________________" );
        space3.setLayoutData(g);
        
        StringFieldEditor strUrlBoutique = new StringFieldEditor(PreferenceConstantsProject.URL_BOUTIQUE, "Adresse boutique Prestashop", getFieldEditorParent());
        addField(strUrlBoutique);
        listeCompoTout.add(strUrlBoutique);
        
    	Label space4 = new Label( getFieldEditorParent(), SWT.NONE );
        space4.setText( "________________________________________________________" );
        space4.setLayoutData(g);
        
        BooleanFieldEditor booleanMessUpdateBoutique = new BooleanFieldEditor(PreferenceConstantsProject.MESSAGE_CONFIRMATION_UPDATE_BOUTIQUE, "Afficher le message de confirmation avant mise à jour de la boutique.", getFieldEditorParent());
        addField(booleanMessUpdateBoutique);
        listeCompoTout.add(booleanMessUpdateBoutique);
        
        BooleanFieldEditor booleanMessUpdateBDG = new BooleanFieldEditor(PreferenceConstantsProject.MESSAGE_CONFIRMATION_UPDATE_BDG, "Afficher le message de confirmation avant mise à jour du Bureau de gestion.", getFieldEditorParent());
        addField(booleanMessUpdateBDG);
        listeCompoTout.add(booleanMessUpdateBDG);
        
		Label space5 = new Label( getFieldEditorParent(), SWT.NONE );
        space5.setText( "________________________________________________________" );
        space5.setLayoutData(g);
        
      StringFieldEditor strRemiseColisDefaut = new StringFieldEditor(PreferenceConstantsProject.RAPPORT_PRIX_U_COLIS,"Remise sur prix au colis par défaut", getFieldEditorParent());
      addField(strRemiseColisDefaut);
      listeCompoTout.add(strRemiseColisDefaut);
		
      StringFieldEditor strNbUniteDansColisDefaut = new StringFieldEditor(PreferenceConstantsProject.NB_UNITE_DANS_COLIS_DEFAUT,"Nombre d'unité dans un colis par défaut", getFieldEditorParent());
      addField(strNbUniteDansColisDefaut);
      listeCompoTout.add(strNbUniteDansColisDefaut);
		
      BooleanFieldEditor boolExportationPartielle = new BooleanFieldEditor(PreferenceConstantsProject.ACCEPTER_EXPORTATION_PARTIELLE_VERS_BOUTIQUE, "Exportation partielle vers la boutique (validations intermédiares) ", getFieldEditorParent());
      addField(boolExportationPartielle);
      listeCompoTout.add(boolExportationPartielle);
      
    StringFieldEditor strEBCategorieDefaut = new StringFieldEditor(PreferenceConstantsProject.EB_CATEGORIE_DEFAUT,"EB : Catégorie par défaut", getFieldEditorParent());
    addField(strEBCategorieDefaut);
    listeCompoTout.add(strEBCategorieDefaut);
		
      StringFieldEditor strEBImportEspace3000ArtExportDefaut = new StringFieldEditor(PreferenceConstantsProject.EB_IMPORTATION_ESPACE3000_ARTICLE_EXPORTABLE_DEFAUT,"EB : Les articles sont exportables vers la boutique par defaut", getFieldEditorParent());
      addField(strEBImportEspace3000ArtExportDefaut);
      listeCompoTout.add(strEBImportEspace3000ArtExportDefaut);
      
      StringFieldEditor strStockDefautExportBoutique = new StringFieldEditor(PreferenceConstantsProject.STOCK_DEFAUT_POUR_EXPORT_BOUTIQUE,"Stock defaut export boutique", getFieldEditorParent());
      addField(strStockDefautExportBoutique);
      listeCompoTout.add(strStockDefautExportBoutique);
      
      StringFieldEditor strEBCompteComptaDefaut = new StringFieldEditor(PreferenceConstantsProject.EB_COMPTE_COMPTA_DEFAUT,"EB : Compte compable par défaut", getFieldEditorParent());
      addField(strEBCompteComptaDefaut);
      listeCompoTout.add(strEBCompteComptaDefaut);
        
//        BooleanFieldEditor booleanSvgBoutique = new BooleanFieldEditor(PreferenceConstantsProject.SAUVEGARDE_AUTOMATIQUE_BOUTIQUE, "Sauvegarde automatique de la boutique.", getFieldEditorParent());
//        addField(booleanSvgBoutique);
//        listeCompoTout.add(booleanSvgBoutique);
//
//        StringFieldEditor strHostnameBddBoutique = new StringFieldEditor(PreferenceConstantsProject.HOSTNAME_BDD_BOUTIQUE,"Serveur de base de données de la boutique Prestashop", getFieldEditorParent());
//        addField(strHostnameBddBoutique);
//        listeCompoTout.add(strHostnameBddBoutique);
//        
//        StringFieldEditor strNameBddBoutique = new StringFieldEditor(PreferenceConstantsProject.NAME_BDD_BOUTIQUE,"Nom de la base données Prestashop", getFieldEditorParent());
//        addField(strNameBddBoutique);
//        listeCompoTout.add(strNameBddBoutique);
//        
//        StringFieldEditor strLoginBddBoutique = new StringFieldEditor(PreferenceConstantsProject.LOGIN_BDD_BOUTIQUE,"Nom d'utilisateur de la base de données Prestashop", getFieldEditorParent());
//        addField(strLoginBddBoutique);
//        listeCompoTout.add(strLoginBddBoutique);
//        
//        StringFieldEditor strPassBddBoutique = new StringFieldEditor(PreferenceConstantsProject.PASSWORD_BDD_BOUTIQUE,"Mot de passe de la base de données Prestashop", getFieldEditorParent());
//        addField(strPassBddBoutique);
//        listeCompoTout.add(strPassBddBoutique);

//		Label space = new Label( getFieldEditorParent(), SWT.NONE );
//        space.setText( "________________________________________________________" );
//        space.setLayoutData(g);
//	
//        FileFieldEditor fileCheminFichierParamExport = new FileFieldEditor(PreferenceConstantsProject.CHEMIN_FICHIER_PARAM_EXPORT, "Chemin du fichier de paramétrage", getFieldEditorParent());
//        addField(fileCheminFichierParamExport);	
//        listeCompoTout.add(fileCheminFichierParamExport);
        
        initEtat(Activator.getDefault().getPreferenceStoreProject().getBoolean(PreferenceConstantsProject.UTILISE_BOUTIQUE));
        
        if(Activator.getDefault().getPreferenceStoreProject().getBoolean(PreferenceConstantsProject.UTILISE_BOUTIQUE)) {
        	TaSynchroBoutiqueDAO dao = new TaSynchroBoutiqueDAO();
        	initEtatVerrouPremierEnvoi(!dao.dejaSynchro());
        }
		
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.IWorkbenchPreferencePage#init(org.eclipse.ui.IWorkbench)
	 */
	public void init(IWorkbench workbench) {
		//setPreferenceStore(Activator.getDefault().getPreferenceStoreProject());
		//setPreferenceStore(new ScopedPreferenceStore(Const.getProjectScopeContext(),Const.getProjectName()));
		//setPreferenceStore(Const.getStore());
		//setPreferenceStore(Const.getStore(Activator.PLUGIN_ID));
		setPreferenceStore(Activator.getDefault().getPreferenceStoreProject());
	}

	@Override
	public boolean performOk() {
		if(Activator.getDefault().getPreferenceStoreProject().getBoolean(PreferenceConstantsProject.UTILISE_BOUTIQUE)) {
			System.err.println("activation boutique");
			BoutiqueInitializer bi = new BoutiqueInitializer();
			bi.initialize();
		}
		return super.performOk();
	}
}