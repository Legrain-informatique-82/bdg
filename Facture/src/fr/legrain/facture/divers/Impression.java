package fr.legrain.facture.divers;

import java.io.File;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;

import javax.persistence.EntityManager;

import org.apache.log4j.Logger;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;
import org.osgi.framework.Bundle;

import fr.legrain.document.divers.IImpressionDocumentTiers;
import fr.legrain.edition.Activator;
import fr.legrain.edition.ImprimeObjet;
import fr.legrain.edition.actions.ConstEdition;
import fr.legrain.edition.actions.SwtReportWithExpandbar;
import fr.legrain.facture.FacturePlugin;
import fr.legrain.facture.preferences.PreferenceConstants;

public class Impression implements IImpressionDocumentTiers {
	Shell shellParent = null;

	private ConstEdition constEdition = null;
	private Object object = null;
	private Collection collection = null;
	private boolean afficheAutoEditionDocument;
	private EntityManager entityManager;
	public Impression() {
		super();
	}
	public Impression(Shell s){
		this(s, null);	
	}
	public Impression(Shell s,EntityManager entityManager){
		this.shellParent = s;
		this.entityManager = entityManager;
	}
	
	public Impression(ConstEdition constEdition,Object object,Collection collection){
		this.constEdition = constEdition;
		this.object = object;
		this.collection = collection;
	}
	static Logger logger = Logger.getLogger(Impression.class.getName());

	/**
	 * Imprime un seul document
	 * @param idDoc
	 * @param codeDoc
	 * @param preview
	 * @param fileEditionDefaut
	 * @param nameOnglet
	 * @param nomEntity
	 */
	public void imprimerSelection(int idDoc,final String codeDoc,boolean preview,String fileEditionDefaut,
			String namePlugin, String nomEntity){
		
		IPreferenceStore preferenceStore = FacturePlugin.getDefault().getPreferenceStore();
		/**
		 * afficheAutomatique si true  ==> quand on enregist une facture, ensuit affiche l'edition 
		 * 					  si false ==>
		 */
		boolean afficheAutomatique = preferenceStore.getBoolean(PreferenceConstants.IMPRIMER_AUTO);

		boolean affiche = preferenceStore.getBoolean(PreferenceConstants.AFFICHER_SELECTION_EDITION);
		
		HashMap<String,String> reportParam = new HashMap<String,String>();
		
		String choix= preferenceStore.getString(PreferenceConstants.LISTE_CHOIX);
		if(choix == null || choix.equals(""))choix="choix 1";
		reportParam.put(ConstEditionFacture.PARAM_CHOIX_DEST,choix);
		String typeAdresseCorrespondance = preferenceStore.getString(PreferenceConstants.TYPE_ADRESSE_CORRESPONDANCE);

		if(typeAdresseCorrespondance !=null &&!typeAdresseCorrespondance.equals("")){
			reportParam.put("ParamCorr",typeAdresseCorrespondance);
		}
		else{
			reportParam.put("ParamCorr",null);
		}
		
		String nomOnglet = ConstEdition.EDITION+namePlugin;
		
		String reportFileLocationDefaut = ConstEdition.pathFichierEditionsSpecifiques(fileEditionDefaut,namePlugin);
		boolean buttonEditionDefaut = false;
		boolean flagMessageEditionPreference = false;
		
		/** 08/02/2010 **/
		boolean imprimerDirect = preferenceStore.getBoolean(PreferenceConstants.editionImprimerDirect);
		boolean afficheEditionAImprimer = preferenceStore.getBoolean(PreferenceConstants.afficheEditionImprimer);
		
		IPreferenceStore preferenceStoreEdition = Activator.getDefault().getPreferenceStore();
		
		String pathFileAImprimer = preferenceStoreEdition.getString(fr.legrain.edition.preferences.PreferenceConstants.
				   PATH_SAVE_PDF);
		String pathAdobeReader = preferenceStoreEdition.getString(fr.legrain.edition.preferences.PreferenceConstants.
  			     PATH_ACROBAT_READER);
		/****************************/

		LinkedList<Integer> idEntity = new LinkedList<Integer>();
		idEntity.add(idDoc);
		constEdition.setIdOne(idEntity);
		constEdition.setObjectEntity(object);
//		constEdition.getCollection().clear();
		constEdition.setCollection(collection);
		constEdition.setNameEntity(nomEntity);

//		/** 05/01/2010 **/
//		constEdition.setPARAM_ID_TABLE(ConstEditionArticle.PARAM_REPORT_ID_ARTICLE);
//		constEdition.paramId = ConstEditionArticle.PARAM_REPORT_ID_ARTICLE;
		
//		constEdition.prepartionEditionBirt();
		ImprimeObjet.clearListAndMap();
		try {
//			Bundle bundleCourant = FacturePlugin.getDefault().getBundle();
//			String namePlugin = bundleCourant.getSymbolicName();
			/*
			 * affiche is false ==> ne affiche pas les choix des edition.pour obtenir l'edition
			 * 						prendre la chemin de l'edition dans le preference  
			 */			
			if(!affiche){
				constEdition.editionDirect(namePlugin, nomOnglet, reportParam,false,imprimerDirect,
						afficheEditionAImprimer,pathFileAImprimer,pathAdobeReader,nomEntity,
						null,false,fileEditionDefaut);
			}else{
				File fileReportFileLocationDefaut = new File(reportFileLocationDefaut);
				if(fileReportFileLocationDefaut.exists()){
					buttonEditionDefaut = true;
				}
				/********************************/
				/*
				 * Eventuellement, ces codes ne servit plus
				 */
				if (reportFileLocationDefaut == null){
					reportFileLocationDefaut = fileEditionDefaut;
				}
				/********************************/
				ConstEdition.CONTENT_COMMENTS =ConstEditionFacture.COMMENTAIRE_EDITION_DEFAUT;
				/*
				 * trouver path de l'edition pour un client spécific 
				 * EX : /home/lee/testJPA26052009/1715/EditionsClient/Facture
				 */
				String FloderEditionSpecifiquesClient="";                //pathRepertoireEditionsSpecifiques()
				if(ConstEdition.pathRepertoireEditionsSpecifiquesClient()!=null)
					FloderEditionSpecifiquesClient = ConstEdition.pathRepertoireEditionsSpecifiquesClient()+ConstEdition.SEPARATOR
					+namePlugin+ConstEdition.SEPARATOR+nomEntity;
				//File fileEditionSpecifiquesClient = constEdition.makeFolderEditions(FloderEditionSpecifiquesClient);
				File fileEditionSpecifiquesClient = new File(FloderEditionSpecifiquesClient);

				/**
				 * trouver path de l'edition pour plugin EditionSpecifique 
				 * EX : /home/lee/testJPA26052009/EditionsSpecifiques/Editions/Facture/TaFacture
				 */
				String FloderEditionSpecifiques="";
				if(ConstEdition.pathRepertoireEditionsSpecifiques()!=null)
					FloderEditionSpecifiques = ConstEdition.pathRepertoireEditionsSpecifiques()+ConstEdition.SEPARATOR
					+namePlugin+ConstEdition.SEPARATOR+nomEntity;    
				File fileEditionSpecifiques = new File(FloderEditionSpecifiques);

				//File FloderFileEditions = constEdition.makeFolderEditions(FloderEdition);	


				Shell dialogShell = new Shell(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),
						//SWT.DIALOG_TRIM |SWT.APPLICATION_MODAL);
						SWT.RESIZE | SWT.CLOSE | SWT.MAX | SWT.APPLICATION_MODAL);
				dialogShell.setText(ConstEdition.TITLE_SHELL);
				dialogShell.setLayout(new FillLayout());
				//dialogShell

//				SwtCompositeReport_new ecranDialogReport = new SwtCompositeReport_new(dialogShell,SWT.NULL);
//
//				ConstEdition.addCommentToEditionDefaut(ecranDialogReport.getRadioReportDefaut(), 
//						buttonEditionDefaut, ConstEdition.TEXT_BUTTON_EDITION_DEFAUT,nameOnglet);
				/**
				 * With Expandbar
				 */
				SwtReportWithExpandbar ecranDialogReport = new SwtReportWithExpandbar(dialogShell,SWT.NULL);

				constEdition.setPARAM_ID_TABLE(ConstEditionFacture.PARAM_ID_FACTURE);
				constEdition.paramId = ConstEditionFacture.PARAM_ID_FACTURE;
				/**
				 * with Expandbar
				 */
				constEdition.fillMapNameExpandbar(true);

				constEdition.openDialogChoixEditionDefaut(ecranDialogReport,fileEditionSpecifiquesClient, 
				reportFileLocationDefaut, namePlugin,nomOnglet,dialogShell,null,affiche,
				reportParam,fileEditionSpecifiques,buttonEditionDefaut,imprimerDirect,
				afficheEditionAImprimer,pathFileAImprimer,pathAdobeReader,nomEntity);
				
			}
		} catch (Exception ex) {
			logger.error(ex);
		}

	}

	/**
	 * Impression d'un serie de document
	 * @param fileEditionDefaut - chemin du fichier d'edition par defaut
	 * @param nameOnglet - libelle a afficher dans l'onglet de previsualisation - <b>Non utilise pour le moment</b>
	 * @param collection - Les documents a imprimer
	 */
	public void imprimerChoix(String fileEditionDefaut,String nameOnglet,Collection collection,String nomEntity,
			boolean flagPreview,boolean flagPrint) {
		
		IPreferenceStore preferenceStore = FacturePlugin.getDefault().getPreferenceStore();
		
		boolean affiche = preferenceStore.getBoolean(PreferenceConstants.AFFICHER_SELECTION_EDITION);
		boolean affiche_en_liste = preferenceStore.getBoolean(PreferenceConstants.AFFICHER_SELECTION_EDITION_EDITION_EN_LISTE);
		//ViewerPlugin.getDefault().getPluginPreferences().setValue(WebViewer.APPCONTEXT_EXTENSION_KEY, EditionAppContext.APP_CONTEXT_NAME);

		HashMap<String, String> reportParam = new HashMap<String, String>();
		
		String choix= preferenceStore.getString(PreferenceConstants.LISTE_CHOIX);
		if(choix == null || choix.equals(""))choix="choix 1";
		reportParam.put(ConstEditionFacture.PARAM_CHOIX_DEST,choix);
		String typeAdresseCorrespondance = preferenceStore.getString(PreferenceConstants.TYPE_ADRESSE_CORRESPONDANCE);

		if(typeAdresseCorrespondance !=null &&!typeAdresseCorrespondance.equals("")){
			reportParam.put("ParamCorr",typeAdresseCorrespondance);
		}
		else{
			reportParam.put("ParamCorr",null);
		}
		
		boolean flagMessageEditionpreference = false;
		
		Bundle bundleCourant = FacturePlugin.getDefault().getBundle();
		String namePlugin = bundleCourant.getSymbolicName();
		
//		ConstEdition constEdition = new ConstEdition(this.entityManager);
		
		constEdition = new ConstEdition(this.entityManager);
		constEdition.setNameEntity(nomEntity);
		constEdition.setFlagListFicheEditionDocument(true);
//		constEdition.getCollection().clear();
		constEdition.setCollection(collection);
		
		/** 08/02/2010 **/
//		boolean imprimerDirect = preferenceStore.getBoolean(PreferenceConstants.editionImprimerDirect);
//		boolean afficheEditionAImprimer = preferenceStore.getBoolean(PreferenceConstants.afficheEditionImprimer);
		boolean imprimerDirect = flagPrint;
		boolean afficheEditionAImprimer = flagPreview;
		IPreferenceStore preferenceStoreEdition = Activator.getDefault().getPreferenceStore();
		String pathFileAImprimer = preferenceStoreEdition.getString(fr.legrain.edition.preferences.PreferenceConstants.
				   PATH_SAVE_PDF);
		String pathAdobeReader = preferenceStoreEdition.getString(fr.legrain.edition.preferences.PreferenceConstants.
			     PATH_ACROBAT_READER);
		
		/****************/
		
		/** 05/01/2010 add **/
//		constEdition.prepartionEditionBirt();
		
//		AffichageEdition affichageChoixEdition = new AffichageEdition();
//		affichageChoixEdition.setAppContextEdition(new EditionAppContext().getExtensionMap());
		ImprimeObjet.clearListAndMap();
	
		String reportFileLocation = preferenceStore.getString(PreferenceConstants.P_PATH_EDITION_DEFAUT);
		if(!affiche_en_liste){
//			File reportFileDefautPreference = null;
//			if(reportFileLocation.equalsIgnoreCase(fileEditionDefaut)){
//				reportFileLocation = ConstEdition.pathFichierEditionsSpecifiques(reportFileLocation, FacturePlugin.PLUGIN_ID);
//				reportFileDefautPreference = new File(reportFileLocation);
//			}
//			else{
//				reportFileDefautPreference = new File(reportFileLocation);
//			}
//			flagMessageEditionpreference = reportFileDefautPreference.exists();
//			if(flagMessageEditionpreference){
////				constEdition.setPARAM_ID_TABLE(ConstEditionFacture.PARAM_ID_FACTURE);
////				constEdition.paramId = ConstEditionFacture.PARAM_ID_FACTURE;
//				affichageChoixEdition.setPathFileEdition(reportFileLocation);
//				affichageChoixEdition.imprimerThreadAllFiche(collection, true,reportParam, null);
//			}else{
//				Shell shell =  PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
//				MessageDialog.openError(shell, ConstEditionFacture.TITLE_MESSAGE_EDITION_PREFERENCE, ConstEditionFacture.MESSAGE_EDITION_PREFERENCE);
//			}
			
			constEdition.editionDirect(namePlugin, nameOnglet, reportParam,true,imprimerDirect,
					afficheEditionAImprimer,pathFileAImprimer,pathAdobeReader,nomEntity,
					null,true,fileEditionDefaut);
		}
		else{
			reportFileLocation = ConstEdition.pathFichierEditionsSpecifiques(reportFileLocation,FacturePlugin.PLUGIN_ID);
			File fileReportFileLocation = new File(reportFileLocation);
			boolean buttonEditionDefaut = false;

			if(fileReportFileLocation.exists()){
				buttonEditionDefaut = true;
			}
			
			File reportFile = constEdition.findPathReportPlugin(bundleCourant, 
					ConstEdition.SEPARATOR+"report"+ConstEdition.SEPARATOR+"defaut"+ConstEdition.SEPARATOR, "");

			ConstEdition.CONTENT_COMMENTS =ConstEdition.COMMENTAIRE_EDITION_DEFAUT;

			String FloderEdition="";
			if(ConstEdition.pathRepertoireEditionsSpecifiquesClient()!=null)	
				FloderEdition = ConstEdition.pathRepertoireEditionsSpecifiquesClient()+ConstEdition.SEPARATOR+
				namePlugin+ConstEdition.SEPARATOR+nomEntity;

		
			File FloderFileEditions = new File(FloderEdition);
			String nomOnglet = ConstEdition.EDITION+namePlugin;
			Shell dialogShell = new Shell(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),
					SWT.RESIZE | SWT.CLOSE | SWT.MAX | SWT.APPLICATION_MODAL);
			dialogShell.setText(ConstEdition.TITLE_SHELL);
			dialogShell.setLayout(new FillLayout());

			/**
			 * sans Expandbar
			 */
//			SwtCompositeReport_new dialogReport = new SwtCompositeReport_new(dialogShell,SWT.NULL);
//			
//			ConstEdition.addCommentToEditionDefaut(dialogReport.getRadioReportDefaut(), buttonEditionDefaut, 
//					ConstEdition.TEXT_BUTTON_EDITION_DEFAUT, nameOnglet);

			/**
			 * With Expandbar
			 */
			SwtReportWithExpandbar ecranDialogReport = new SwtReportWithExpandbar(dialogShell,SWT.NULL);
			
//			constEdition.setCollection(collection);
			
			

			String reportEditionsSpecifiques = ConstEdition.pathRepertoireEditionsSpecifiques()+ConstEdition.SEPARATOR
			+namePlugin+ConstEdition.SEPARATOR+nomEntity;
		
			constEdition.setFlagEditionMultiple(false);
//			
//			constEdition.openDialogChoixEdition_Defaut(dialogReport, FloderFileEditions, 
//					reportFileLocation, namePlugin,nomOnglet,dialogShell,reportFile,affiche,reportParam,new File(reportEditionsSpecifiques), true);
			/**
			 * with Expandbar
			 */
			constEdition.fillMapNameExpandbar(true);
			
			constEdition.openDialogChoixEditionDefaut(ecranDialogReport,FloderFileEditions, 
					reportFileLocation, namePlugin,nomOnglet,dialogShell,null,affiche,
					reportParam,new File(reportEditionsSpecifiques),buttonEditionDefaut,imprimerDirect,
					afficheEditionAImprimer,pathFileAImprimer,pathAdobeReader,nomEntity);
		}
	}
	
	
	public void imprimerChoix2(String fileEditionDefaut,String nameOnglet,Collection collection,String nomEntity) {

	}

	public Shell getShellParent() {
		return shellParent;
	}

	public void setShellParent(Shell shellParent) {
		this.shellParent = shellParent;
	}

	public void setConstEdition(ConstEdition constEdition) {
		this.constEdition = constEdition;
	}

	public void setObject(Object object) {
		this.object = object;
	}

	public void setCollection(Collection collection) {
		this.collection = collection;
	}

	public void setAfficheAutoEditionDocument(boolean afficheAutoEditionDocument) {
		this.afficheAutoEditionDocument = afficheAutoEditionDocument;
	}
	public EntityManager getEntityManager() {
		return entityManager;
	}
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	public Collection getCollection() {
		return collection;
	}
	@Override
	public String getPluginName() {
		// TODO Auto-generated method stub
		return FacturePlugin.getDefault().getBundle().getSymbolicName();
	}

	@Override
	public IPreferenceStore getPreferenceStore() {
		// TODO Auto-generated method stub
		return 	FacturePlugin.getDefault().getPreferenceStore();
	}


}
