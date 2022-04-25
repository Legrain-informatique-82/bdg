package fr.legrain.document.divers;

import java.io.File;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;

import javax.persistence.EntityManager;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;
import org.osgi.framework.Bundle;


import fr.legrain.document.DocumentPlugin;
import fr.legrain.document.preferences.PreferenceConstants;
import fr.legrain.edition.Activator;
import fr.legrain.edition.ImprimeObjet;
import fr.legrain.edition.actions.AffichageEdition;
import fr.legrain.edition.actions.ConstEdition;
import fr.legrain.edition.actions.SwtCompositeReport_new;
import fr.legrain.edition.actions.SwtReportWithExpandbar;
import fr.legrain.edition.dynamique.EditionAppContext;

public class Impression {
	

	ConstEdition constEdition = null;
	/**
	 * object ==> ce que l'on a choisit.
	 * collection ==>  stocker tous les object JPA dans l'interface.
	 */
	Shell shellParent = null;
	private Collection collection;
	private String nomChampIdTable;
	private Object object = null;
	private Integer id;
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
	public Impression(ConstEdition constEdition,Object object,Collection collection,String nomChampIdTable,int id,EntityManager entityManager){
		this.constEdition = constEdition;
		this.object = object;
		this.collection = collection;
		this.nomChampIdTable = nomChampIdTable;
		this.id = Integer.valueOf(id);
		this.entityManager = entityManager;
	}
	public Impression(ConstEdition constEdition,Object object,Collection collection,String nomChampIdTable,int id){
		this(constEdition,object,collection,nomChampIdTable,id,null);
	}
	
	public void imprimer(boolean preview,String pathEditionDynamique,String fileEditionDefaut,String nameOnglet,String nomEntity,boolean flag) throws Exception{
		
		IPreferenceStore preferenceStore = DocumentPlugin.getDefault().getPreferenceStore(); 
		boolean imprimerDirect = preferenceStore.getBoolean(PreferenceConstants.editionImprimerDirect);
		boolean afficheEditionAImprimer = preferenceStore.getBoolean(PreferenceConstants.afficheEditionImprimer);
		IPreferenceStore preferenceStoreEdition = Activator.getDefault().getPreferenceStore();
		String pathFileAImprimer = preferenceStoreEdition.getString(fr.legrain.edition.preferences.PreferenceConstants.
				   PATH_SAVE_PDF);
		String pathAdobeReader = preferenceStoreEdition.getString(fr.legrain.edition.preferences.PreferenceConstants.
			     PATH_ACROBAT_READER);
		/*****************/
		String nomOnglet = ConstEdition.EDITION + nameOnglet;
		HashMap<String,String> reportParam = new HashMap<String,String>();
		
		String reportFileLocationDefaut = null;
		boolean affiche = preferenceStore.getBoolean(PreferenceConstants.AFF_EDITION);
		boolean buttonEditionDefaut = false;
		boolean flagMessageEditionPreference = false;
		boolean flagImprimeTypeEntity = false;
		
		LinkedList<Integer> idEntity = new LinkedList<Integer>();
		idEntity.add(id);
		
		constEdition.setIdOne(idEntity);
		constEdition.setObjectEntity(object);
		constEdition.setCollection(collection);
		constEdition.setNomChampIdTable(nomChampIdTable);
		
//		/** 05/01/2010 **/
//		constEdition.prepartionEditionBirt();
		ImprimeObjet.clearListAndMap();
		
		
			Bundle bundleCourant = DocumentPlugin.getDefault().getBundle();
			String namePlugin = bundleCourant.getSymbolicName();
			/**
			 * affiche is false ==> ne affiche pas les choix des edition.pour obtenir l'edition
			 * 						prendre la chemin de l'edition dans le preference  
			 */
			if(!flag){
				AffichageEdition affichageEdition = new AffichageEdition(this.entityManager);
				affichageEdition.setAppContextEdition(new EditionAppContext().getExtensionMap());
				constEdition.afficheEditionDynamiqueDirect(affichageEdition, pathEditionDynamique, nomOnglet);
			}else{
				try {
				if(!affiche){
//					if(flagImprimeTypeEntity){
//						constEdition.afficheEditionTypeEntityDirect(pathEditionDynamique, nomOnglet);
//					}
//					constEdition.editionDirect(namePlugin, nomOnglet, reportParam,false);
				}else{
					reportFileLocationDefaut = ConstEdition.pathFichierEditionsSpecifiques(fileEditionDefaut,
							DocumentPlugin.PLUGIN_ID);
					if(reportFileLocationDefaut!=null){
						File fileReportFileLocationDefaut = new File(reportFileLocationDefaut);
						if(fileReportFileLocationDefaut.exists()){
							buttonEditionDefaut = true;
						}
					}
					else{
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
						FloderEditionSpecifiquesClient = ConstEdition.pathRepertoireEditionsSpecifiquesClient()+
							"/"+namePlugin+"/"+nomEntity;
					File fileEditionSpecifiquesClient = new File(FloderEditionSpecifiquesClient);
					/**
					 * trouver path de l'edition pour plugin EditionSpecifique 
					 * EX : /home/lee/testJPA26052009/EditionsSpecifiques/Editions/...
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
					
//					SwtCompositeReport_new ecranDialogReport = new SwtCompositeReport_new(dialogShell,SWT.NULL);
					/**
					 * With Expandbar
					 */
					SwtReportWithExpandbar ecranDialogReport = new SwtReportWithExpandbar(dialogShell,SWT.NULL);
					
//					constEdition.openDialogChoixEdition_Defaut(ecranDialogReport, fileEditionSpecifiquesClient, 
//							reportFileLocationDefaut, namePlugin,nomOnglet,dialogShell,fileReportDynamique,affiche,
//							reportParam,fileEditionSpecifiques,buttonEditionDefaut);
					
					/**
					 * with Expandbar
					 */
					constEdition.fillMapNameExpandbar(true);
					
//					constEdition.openDialogChoixEditionDefaut(ecranDialogReport,fileEditionSpecifiquesClient, 
//					reportFileLocationDefaut, namePlugin,nomOnglet,dialogShell,null,affiche,
//					reportParam,fileEditionSpecifiques,buttonEditionDefaut);
					
					
				}
				
			} catch (Exception e) {
				// TODO: handle exception
			}
			}
		
	}
	
	public ConstEdition getConstEdition() {
		return constEdition;
	}
	public void setConstEdition(ConstEdition constEdition) {
		this.constEdition = constEdition;
	}
	public Collection getCollection() {
		return collection;
	}
	public void setCollection(Collection collection) {
		this.collection = collection;
	}
	public String getNomChampIdTable() {
		return nomChampIdTable;
	}
	public void setNomChampIdTable(String nomChampIdTable) {
		this.nomChampIdTable = nomChampIdTable;
	}
	public Object getObject() {
		return object;
	}
	public void setObject(Object object) {
		this.object = object;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
}
