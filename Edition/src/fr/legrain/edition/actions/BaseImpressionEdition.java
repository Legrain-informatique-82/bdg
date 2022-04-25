package fr.legrain.edition.actions;

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

import fr.legrain.edition.Activator;
import fr.legrain.edition.ImprimeObjet;
import fr.legrain.edition.dynamique.EditionAppContext;
import fr.legrain.edition.preferences.PreferenceConstants;
import fr.legrain.gestCom.librairiesEcran.preferences.LgrPreferenceConstantsDocuments;
import fr.legrain.lib.data.LibConversion;
import fr.legrain.silentPdfPrint.LgrSpooler;

public class BaseImpressionEdition {
	
	static Logger logger = Logger.getLogger(BaseImpressionEdition.class.getName());
			
	protected ConstEdition constEdition = null;
	protected Object object = null;
	protected EntityManager entityManager;
	protected Collection collection = null;
	private Integer idEntity = null;
	private String typeTraite = null;
	
	protected AffichageEdition affichageEdition;
	protected HashMap<String,String> reportParam = new HashMap<String,String>();
	protected Shell dialogShell = null;
	protected SwtReportWithExpandbar ecranDialogReport;
	
	protected InfosEmail infosEmail = null;
	protected InfosFax infosFax = null;
	
	
	public BaseImpressionEdition(ConstEdition constEdition,Object object, EntityManager entityManager,
								 Collection collection,int idEntity) {
		super();
		this.constEdition = constEdition;
		this.object = object;
		this.entityManager = entityManager;
		this.collection = collection;
		this.idEntity = idEntity;
	}
	
	public BaseImpressionEdition(Shell dialogShell,EntityManager entityManager){
		this.dialogShell = dialogShell;
		this.entityManager = entityManager;
	}
	
	public BaseImpressionEdition(Shell dialogShell,Collection collection,EntityManager entityManager){
		this.dialogShell = dialogShell;
		this.collection = collection;
		this.entityManager = entityManager;
	}
	
	/**
	 * pour les editions dynamiques 
	 * @param pathEditionDynamique ==> path le fichier d'edition dynamique
	 * @param name ==> nom d'onglet edition 
	 */
	public void impressionEditionTypeEntity(String pathEditionDynamique,String name){
		String nameOnglet = "Edition Type "+name;
		affichageEdition = new AffichageEdition(this.entityManager);
		affichageEdition.setAppContextEdition(new EditionAppContext().getExtensionMap());
		
		constEdition.setCollection(this.collection);
		constEdition.setObjectEntity(this.object);
		constEdition.afficheEditionDynamiqueDirect(affichageEdition,pathEditionDynamique,nameOnglet);
	}
	
	/**
	 * Pour servir les editions 
	 * @param preferenceStore ==> pour obtenir les valeur de preference
	 * @param nameEntity ==> 
	 * @param flagDynamique
	 * @param filePathEditionDynamique
	 * @param namePlugin
	 * @param fileEditionDefaut
	 * @param flagExpanbarDocument
	 * @param flagListEdition
	 * @param flagDocument
	 * @param flagImpressionListDocuemnt
	 * @param preview
	 * @param printDirect - impression direct, sans prévisualisation à l'écran
	 */
	public void impressionEdition(IPreferenceStore preferenceStore,String nameEntity,/*boolean flagDynamique,*/
			File filePathEditionDynamique,String namePlugin,String fileEditionDefaut,
			boolean flagExpanbarDocument,boolean flagListEdition,boolean flagDocument,
			boolean flagImpressionListDocuemnt,boolean preview,boolean printDirect,
			String paramEditionId) {

		constEdition.setPARAM_ID_TABLE(paramEditionId);
		constEdition.paramId = paramEditionId;
		
		constEdition.setInfosEmail(this.infosEmail);
		constEdition.setInfosFax(this.infosFax);

		boolean imprimerDirect = preferenceStore.getBoolean(PreferenceConstants.editionImprimerDirect);
		boolean afficheEditionAImprimer = preferenceStore.getBoolean(PreferenceConstants.afficheEditionImprimer);

		boolean afficheFenetreChoixEdition = false;
		if(flagImpressionListDocuemnt) {
			afficheFenetreChoixEdition  = preferenceStore.getBoolean(PreferenceConstants.AFFICHER_SELECTION_EDITION_EDITION_EN_LISTE);
			imprimerDirect = printDirect;
			afficheEditionAImprimer = preview;
		} else {
			afficheFenetreChoixEdition = preferenceStore.getBoolean(PreferenceConstants.AFFICHER_SELECTION_EDITION);
		}
		HashMap<String,String> reportParam = new HashMap<String,String>();
		String choix = null;
		String typeAdresseCorrespondance = null;

		if(flagDocument){
			choix = preferenceStore.getString(PreferenceConstants.LISTE_CHOIX);
			if(choix == null || choix.equals("")) choix="choix 1";
			reportParam.put(PreferenceConstants.PARAM_CHOIX_DEST,choix);
			reportParam.put(PreferenceConstants.PARAM_TYPE_TRAITE,getTypeTraite());
			int coupure=preferenceStore.getInt(LgrPreferenceConstantsDocuments.COUPURE_LIGNE_EDITION);
			int pageBreakMaxi=preferenceStore.getInt(LgrPreferenceConstantsDocuments.PAGE_BREAK_MAXI);
			int pageBreakTotaux=preferenceStore.getInt(LgrPreferenceConstantsDocuments.PAGE_BREAK_TOTAUX);
			
			reportParam.put(LgrPreferenceConstantsDocuments.COUPURE_LIGNE_EDITION,LibConversion.integerToString(coupure));
			reportParam.put(LgrPreferenceConstantsDocuments.PAGE_BREAK_MAXI,LibConversion.integerToString(pageBreakMaxi));
			reportParam.put(LgrPreferenceConstantsDocuments.PAGE_BREAK_TOTAUX,LibConversion.integerToString(pageBreakTotaux));

			typeAdresseCorrespondance = preferenceStore.getString(PreferenceConstants.TYPE_ADRESSE_CORRESPONDANCE);
			if(typeAdresseCorrespondance !=null && !typeAdresseCorrespondance.equals("")){
				reportParam.put("ParamCorr",typeAdresseCorrespondance);
			}
			else{
				reportParam.put("ParamCorr",null);
			}
		}

		String reportFileLocationDefaut = ConstEdition.pathFichierEditionsSpecifiques(fileEditionDefaut, namePlugin);
		logger.debug("reportFileLocationDefaut : "+reportFileLocationDefaut);
		boolean buttonEditionDefaut = false;
		String nameOnglet = ConstEdition.EDITION + namePlugin;

		IPreferenceStore preferenceStoreEdition = Activator.getDefault().getPreferenceStore();
		String pathFileAImprimer = ConstEdition.remplaceBackSlashAndSlash(preferenceStoreEdition.getString(PreferenceConstants.PATH_SAVE_PDF));
		String pathAdobeReader = ConstEdition.remplaceBackSlashAndSlash(preferenceStoreEdition.getString(PreferenceConstants.PATH_ACROBAT_READER));

		if(idEntity != null){
			LinkedList<Integer> listIdEntity = new LinkedList<Integer>();
			listIdEntity.add(idEntity);
			constEdition.setIdOne(listIdEntity);
		}

		if(object != null){
			constEdition.setObjectEntity(object);
		}else{
			constEdition.setFlagListFicheEditionDocument(true);
		}
		constEdition.setCollection(collection);
		constEdition.setNameEntity(nameEntity);
		constEdition.setFlagButtonOneFiche(flagListEdition);

		String nameDynamiqueEdition = ""; 
		if(filePathEditionDynamique != null){
			nameDynamiqueEdition = filePathEditionDynamique.getName();
		}

		ImprimeObjet.clearListAndMap();
		
//		ImprimeObjet.listWidth.put(LgrPreferenceConstantsDocuments.EDITION_CHAMP_1,preferenceStore.getString(LgrPreferenceConstantsDocuments.EDITION_CHAMP_1));
//		ImprimeObjet.listWidth.put(LgrPreferenceConstantsDocuments.EDITION_CHAMP_2,preferenceStore.getString(LgrPreferenceConstantsDocuments.EDITION_CHAMP_2));
//		ImprimeObjet.listWidth.put(LgrPreferenceConstantsDocuments.EDITION_CHAMP_3,preferenceStore.getString(LgrPreferenceConstantsDocuments.EDITION_CHAMP_3));
//		ImprimeObjet.listWidth.put(LgrPreferenceConstantsDocuments.EDITION_CHAMP_4,preferenceStore.getString(LgrPreferenceConstantsDocuments.EDITION_CHAMP_4));
//		ImprimeObjet.listWidth.put(LgrPreferenceConstantsDocuments.EDITION_CHAMP_5,preferenceStore.getString(LgrPreferenceConstantsDocuments.EDITION_CHAMP_5));
//		ImprimeObjet.listWidth.put(LgrPreferenceConstantsDocuments.EDITION_CHAMP_6,preferenceStore.getString(LgrPreferenceConstantsDocuments.EDITION_CHAMP_6));
//		ImprimeObjet.listWidth.put(LgrPreferenceConstantsDocuments.EDITION_CHAMP_7,preferenceStore.getString(LgrPreferenceConstantsDocuments.EDITION_CHAMP_7));
//		ImprimeObjet.listWidth.put(LgrPreferenceConstantsDocuments.EDITION_CHAMP_8,preferenceStore.getString(LgrPreferenceConstantsDocuments.EDITION_CHAMP_8));


		if(!afficheFenetreChoixEdition) {
			//edition directe, sans fenêtre de sélection du modèle d'édition
			constEdition.editionDirect(namePlugin, nameOnglet, reportParam, false, imprimerDirect, afficheEditionAImprimer,
					pathFileAImprimer, pathAdobeReader, nameEntity, nameDynamiqueEdition, flagDocument, fileEditionDefaut);
		} else {
			//Préparation de l'affichage de la fenêtre de choix des éditions
			File fileReportFileLocationDefaut = null;
			if(reportFileLocationDefaut != null){
				fileReportFileLocationDefaut = new File(reportFileLocationDefaut);
				if(fileReportFileLocationDefaut.exists()){
					buttonEditionDefaut = true;
				}
			}else{
				reportFileLocationDefaut = fileEditionDefaut;
				//fileReportFileLocationDefaut = new File(fileEditionDefaut);
			}

			ConstEdition.CONTENT_COMMENTS = ConstEdition.COMMENTAIRE_EDITION_DEFAUT;

			String folderEditionSpecifiquesClient = ConstEdition.pathRepertoireEditionsSpecifiquesClient();
			File fileEditionSpecifiquesClient = null;
			if( folderEditionSpecifiquesClient != null){
				fileEditionSpecifiquesClient = new File(folderEditionSpecifiquesClient+ConstEdition.SEPARATOR+
						namePlugin+ConstEdition.SEPARATOR+nameEntity);
			}

			String folderEditionSpecifiques= ConstEdition.pathRepertoireEditionsSpecifiques();
			File fileEditionSpecifiques = null;
			if(folderEditionSpecifiques != null){
				fileEditionSpecifiques = new File(folderEditionSpecifiques+ConstEdition.SEPARATOR
						+namePlugin+ConstEdition.SEPARATOR+nameEntity);
			}

			dialogShell = new Shell(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),
					//SWT.DIALOG_TRIM |SWT.APPLICATION_MODAL);
					SWT.RESIZE | SWT.CLOSE | SWT.MAX | SWT.APPLICATION_MODAL);
			dialogShell.setText(ConstEdition.TITLE_SHELL);
			dialogShell.setLayout(new FillLayout());

			/** With Expandbar **/
			ecranDialogReport = new SwtReportWithExpandbar(dialogShell,SWT.NULL);

			if(!flagDocument) constEdition.setCommentEditionDynamique(ConstEdition.COMMENT_LIST_EDITION_DYNAMIQUE+namePlugin);
			constEdition.fillMapNameExpandbar(flagExpanbarDocument);

			//constEdition.setFlagListFicheEditionDocument(true);

			//Ouverture de la fenêtre de sélection des éditions
			constEdition.openDialogChoixEditionDefaut(ecranDialogReport,fileEditionSpecifiquesClient, 
					reportFileLocationDefaut,namePlugin,nameOnglet,dialogShell,filePathEditionDynamique,
					flagImpressionListDocuemnt,reportParam,fileEditionSpecifiques,buttonEditionDefaut,imprimerDirect,
					afficheEditionAImprimer,pathFileAImprimer,pathAdobeReader,nameEntity);
		}

	}

	public Collection getCollection() {
		return collection;
	}

	public void setCollection(Collection collection) {
		this.collection = collection;
	}

	public Shell getDialogShell() {
		return dialogShell;
	}

	public void setDialogShell(Shell dialogShell) {
		this.dialogShell = dialogShell;
	}

	public Object getObject() {
		return object;
	}

	public void setObject(Object object) {
		this.object = object;
	}

	public ConstEdition getConstEdition() {
		return constEdition;
	}

	public void setConstEdition(ConstEdition constEdition) {
		this.constEdition = constEdition;
	}

	public Integer getIdEntity() {
		return idEntity;
	}

	public void setIdEntity(Integer idEntity) {
		this.idEntity = idEntity;
	}

	public String getTypeTraite() {
		return typeTraite;
	}

	public void setTypeTraite(String typeTraite) {
		this.typeTraite = typeTraite;
	}

	public InfosEmail getInfosEmail() {
		return infosEmail;
	}

	public void setInfosEmail(InfosEmail infosEmail) {
		this.infosEmail = infosEmail;
	}

	public InfosFax getInfosFax() {
		return infosFax;
	}

	public void setInfosFax(InfosFax infosFax) {
		this.infosFax = infosFax;
	}
	
}
