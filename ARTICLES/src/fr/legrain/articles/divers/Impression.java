package fr.legrain.articles.divers;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Properties;

import javax.persistence.EntityManager;
import javax.swing.text.StyledEditorKit.BoldAction;

import org.apache.log4j.Logger;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;
import org.osgi.framework.Bundle;



import fr.legrain.articles.ArticlesPlugin;
import fr.legrain.articles.dao.TaArticle;
import fr.legrain.articles.ecran.ConstEditionArticle;
import fr.legrain.articles.preferences.PreferenceConstants;
import fr.legrain.edition.Activator;
import fr.legrain.edition.ImprimeObjet;
import fr.legrain.edition.actions.AffichageEdition;
import fr.legrain.edition.actions.ConstEdition;
import fr.legrain.edition.actions.SwtCompositeReport_new;
import fr.legrain.edition.actions.SwtReportWithExpandbar;
import fr.legrain.edition.divers.PreferencesConstantsEdition;
import fr.legrain.edition.dynamique.EditionAppContext;
import fr.legrain.extensionprovider.Application;
import fr.legrain.gestCom.Appli.Const;
import fr.legrain.gestCom.Appli.ModelGeneralObjet;
import fr.legrain.lib.data.JPABdLgr;
import fr.legrain.lib.data.LibConversion;


public class Impression {

	static Logger logger = Logger.getLogger(Impression.class.getName());
	
	Shell shellParent = null;
	ConstEdition constEdition = null;

	/**
	 * taArticle ==> ce que l'on a choisit.
	 * collection ==>  stocker tous les object JPA dans l'interface.
	 */
	private Collection collection;
	private String nomChampIdTable;
	private Object object = null;
	private Integer id;
	private EntityManager entityManager;

//	public Impression(Shell s){
//		setShellParent(s);	
//	}
	public Impression(){	

	}
	public Impression(ConstEdition constEdition,Object object,Collection collection, String nomChampIdTable,int id){	

		this(constEdition,object,collection,nomChampIdTable,id,null);
	}
	
	public Impression(ConstEdition constEdition,Object object,Collection collection, String nomChampIdTable,int id,
			EntityManager entityManager){	
		this.constEdition = constEdition;
		this.object = object;
		this.collection = collection;
		this.nomChampIdTable = nomChampIdTable;
		this.id = Integer.valueOf(id);
		this.entityManager = entityManager;
	}
	
	public void imprimer(boolean preview,String pathEditionDynamique,String fileEditionDefaut,String namePlugin,String nomEntity) throws Exception{
		imprimer(preview, pathEditionDynamique, fileEditionDefaut, namePlugin, nomEntity,false);
	}
	//public void imprimer(String[] idFactureAImprimer,String oneIdAImprimer,boolean preview,String pathEditionDefaut) throws Exception{
	public void imprimer(boolean preview,String pathEditionDynamique,String fileEditionDefaut,String namePlugin,String nomEntity,
				boolean flag) throws Exception{

		String nomOnglet = ConstEdition.EDITION + namePlugin;

		HashMap<String,String> reportParam = new HashMap<String,String>();

		String reportFileLocationDefaut = null;
		
		IPreferenceStore preferenceStore = ArticlesPlugin.getDefault().getPreferenceStore();
		boolean affiche = preferenceStore.getBoolean(PreferenceConstants.AFFICHER_SELECTION_EDITION);
		
		/** 08/02/2010 **/
		IPreferenceStore preferenceStoreEdition = Activator.getDefault().getPreferenceStore();
		boolean imprimerDirect = preferenceStore.getBoolean(PreferenceConstants.editionImprimerDirect);
		boolean afficheEditionAImprimer = preferenceStore.getBoolean(PreferenceConstants.afficheEditionImprimer);
		
		String pathFileAImprimer = constEdition.remplaceBackSlashAndSlash(preferenceStoreEdition.getString(fr.legrain.edition.preferences.PreferenceConstants.
								   PATH_SAVE_PDF));
		String pathAdobeReader = constEdition.remplaceBackSlashAndSlash(preferenceStoreEdition.getString(fr.legrain.edition.preferences.PreferenceConstants.
				   			     PATH_ACROBAT_READER));
		
		/*****************/
		boolean buttonEditionDefaut = false;
//		boolean flagMessageEditionPreference = false;
//		boolean flagImprimeTypeEntity = false; 

		LinkedList<Integer> idEntity = new LinkedList<Integer>();
		idEntity.add(id);
		
		constEdition.setIdOne(idEntity);
		constEdition.setObjectEntity(object);
		constEdition.setCollection(collection);
		constEdition.setNomChampIdTable(nomChampIdTable);
		constEdition.setFlagButtonOneFiche(flag);
		constEdition.setNameEntity(nomEntity);

		/** 31/12/2009 **/
		constEdition.setPARAM_ID_TABLE(ConstEditionArticle.PARAM_REPORT_ID_ARTICLE);
		constEdition.paramId = ConstEditionArticle.PARAM_REPORT_ID_ARTICLE;
		
		ImprimeObjet.clearListAndMap();

		if(!flag){
			AffichageEdition affichageEdition = new AffichageEdition(this.entityManager);
			affichageEdition.setAppContextEdition(new EditionAppContext().getExtensionMap());
			constEdition.afficheEditionDynamiqueDirect(affichageEdition, pathEditionDynamique, nomOnglet);
		}else{
			try {
				/**
				 * affiche is false ==> ne affiche pas les choix des edition.pour obtenir l'edition
				 * 						prendre la chemin de l'edition dans le preference  
				 */
				if(!affiche){
					constEdition.editionDirect(namePlugin, nomOnglet, reportParam,false,imprimerDirect,
							afficheEditionAImprimer,pathFileAImprimer,pathAdobeReader,nomEntity,
							Const.C_NOM_VU_ARTICLE,false,fileEditionDefaut);

				}else{
					reportFileLocationDefaut = ConstEdition.pathFichierEditionsSpecifiques(fileEditionDefaut,namePlugin);
					if(reportFileLocationDefaut!=null){
						File fileReportFileLocationDefaut = new File(reportFileLocationDefaut);
						if(fileReportFileLocationDefaut.exists()){
							buttonEditionDefaut = true;
						}
					}else{
						reportFileLocationDefaut = fileEditionDefaut;
					}
					File fileReportDynamique = new File(pathEditionDynamique);

					ConstEdition.CONTENT_COMMENTS =ConstEdition.COMMENTAIRE_EDITION_DEFAUT;
					/**
					 * trouver path de l'edition pour un client spécific 
					 * EX : /home/lee/testJPA26052009/1715/EditionsClient/Articles
					 */
					String FloderEditionSpecifiquesClient="";                //pathRepertoireEditionsSpecifiques()
					if(ConstEdition.pathRepertoireEditionsSpecifiquesClient()!=null)
						FloderEditionSpecifiquesClient = ConstEdition.pathRepertoireEditionsSpecifiquesClient()+constEdition.SEPARATOR+
						namePlugin+ConstEdition.SEPARATOR+nomEntity;
					//					File fileEditionSpecifiquesClient = constEdition.makeFolderEditions(FloderEditionSpecifiquesClient);
					File fileEditionSpecifiquesClient = new File(FloderEditionSpecifiquesClient);
					/**
					 * trouver path de l'edition pour plugin EditionSpecifique 
					 * EX : /home/lee/testJPA26052009/EditionsSpecifiques/Editions/Articles/TaArticle
					 */
					String FloderEditionSpecifiques="";
					if(ConstEdition.pathRepertoireEditionsSpecifiques()!=null)
						FloderEditionSpecifiques = ConstEdition.pathRepertoireEditionsSpecifiques()+"/"+namePlugin+"/"+nomEntity;    
					File fileEditionSpecifiques = new File(FloderEditionSpecifiques);


					Shell dialogShell = new Shell(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),
							//SWT.DIALOG_TRIM |SWT.APPLICATION_MODAL);
							SWT.RESIZE | SWT.CLOSE | SWT.MAX | SWT.APPLICATION_MODAL);
					dialogShell.setText(ConstEdition.TITLE_SHELL);
					dialogShell.setLayout(new FillLayout());
				
					/**
					 * With Expandbar
					 */
					SwtReportWithExpandbar ecranDialogReport = new SwtReportWithExpandbar(dialogShell,SWT.NULL);
					constEdition.setCommentEditionDynamique(ConstEditionArticle.COMMENTAIRE_EDITION_DEFAUT);
					constEdition.fillMapNameExpandbar(false);
//					constEdition.addExpandItem(ecranDialogReport.getExpandBarEdition());

					
					constEdition.openDialogChoixEditionDefaut(ecranDialogReport,fileEditionSpecifiquesClient, 
							reportFileLocationDefaut,namePlugin,nomOnglet,dialogShell,fileReportDynamique,affiche,
							reportParam,fileEditionSpecifiques,buttonEditionDefaut,imprimerDirect,
							afficheEditionAImprimer,pathFileAImprimer,pathAdobeReader,nomEntity);
				}
			} catch (Exception ex) {
				logger.error(ex);
			}
		}	
	}
}
