package fr.legrain.edition.actions;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.persistence.EntityManager;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.log4j.Logger;
import org.eclipse.birt.report.model.api.core.IModuleModel;
import org.eclipse.birt.report.model.api.core.UserPropertyDefn;
import org.eclipse.birt.report.model.api.elements.DesignChoiceConstants;
import org.eclipse.birt.report.viewer.ViewerPlugin;
import org.eclipse.birt.report.viewer.utilities.WebViewer;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.ExpandBar;
import org.eclipse.swt.widgets.ExpandItem;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.osgi.framework.Bundle;

import fr.legrain.dossier.dao.TaInfoEntreprise;
import fr.legrain.dossier.dao.TaInfoEntrepriseDAO;
import fr.legrain.edition.Activator;
import fr.legrain.edition.ImprimeObjet;
import fr.legrain.edition.dynamique.EditionAppContext;
import fr.legrain.edition.preferences.PropertiesFilePreference;
import fr.legrain.extensionprovider.Application;
import fr.legrain.gestCom.Appli.Const;
import fr.legrain.gestCom.librairiesEcran.LibrairiesEcranPlugin;
import fr.legrain.gestCom.librairiesEcran.swt.StringButtonFieldEditorLgr;
import fr.legrain.lib.data.JPABdLgr;
import fr.legrain.lib.gui.grille.LgrTableViewer;

public class ConstEdition {
	static Logger logger = Logger.getLogger(ConstEdition.class.getName());	
	
	static LinkedHashMap<String, String> nameFileReport = new LinkedHashMap<String, String>();
	static LinkedList<Button> listButton = new LinkedList<Button>();
	
	
	/**
	 * flagDocument pour vérifier quelle entity 
	 * si true ==> entity document EX: TaFacture TaDevis ...
	 * si false ==> EX : TaTiers TaArticle  
	 */
	private boolean flagEditionMultiple = false;
	
	/**
	 * nameTableEcran ==> les noms de champs EX : Code article, Libelle, Compte, Prix HT, Unite ..
	 * nameTableBDD ==> les attributes de JPA EX : codeArticle, libellecArticle, numcptArticle, prixPrix, codeUnite
	 */
	private ArrayList<String> nameTableEcran = new ArrayList<String>();
	private ArrayList<String> nameTableBDD = new ArrayList<String>();
	
	public static String SEPARATOR = "/";//File.separator;
	
	private SwtCompositeReport_new objetSwtCompositeReport=null;
	private SwtReportWithExpandbar swtReportWithExpandbar = null;
	private String pathFileReport=null;
	private String nomPlugin=null;
	private Shell shell = null;
	private File repertoireReport=null;
	private HashMap<String,String> reportParamLoc=null;
	public static String COMMENTAIRE_EDITION_DEFAUT = "Edition par défaut";
	public static final String PARAM_CHOIX_DEST="ParamChoix";
	public static final String NAME_MASTER_PAGE="Simple MasterPage";
	public static final int COUPURE_LIGNE=54;
	
	public static final String PARAM_ID_DOC="paramID_DOC";
	
	public static final String PARAM_CAPITAL="capital";
	public static final String PARAM_APE="ape";
	public static final String PARAM_SIRET="siret";
	public static final String PARAM_RCS="rcs";
	public static final String PARAM_NOM_ENTREPRISE="nomEntreprise";
	
	public static final String UNITS_PERCENTAGE = DesignChoiceConstants.UNITS_PERCENTAGE;
	public static final String UNITS_CM = DesignChoiceConstants.UNITS_CM;
	public static final String WIDTH = "width";
	public static final String TYPE = "type";
	public static final String UNITS_EURO ="EURO";
	public static final String UNITS_VIDE ="VIDE";
	public static final String SYMBOL_BIAS ="/";
	public static final String SYMBOL_SEMICOLON =";";
	public static final String SYMBOL_SPACE =" ";
	public static final String TOP_MARGIN ="topMargin";
	public static final String LEFT_MARGIN ="leftMargin";
	public static final String RIGHT_MARGIN ="rightMargin";
	public static final String BOTTOM_MARGIN ="bottomMargin";
	public static final String ODA_DRIVER_CLASS ="odaDriverClass";
	public static final String ODA_URL ="odaURL";
	public static final String ODA_USER ="odaUser";
	public static final String ODA_PASSWORD ="odaPassword";
	public static final String PART_WHERE ="WHERE";
	public static final String TYPE_FILE_REPORT =".rptdesign";
	
	public static File PATH_FICHE_FILE_REPORT =null;
	
	public static String NAME_FILE_EDITION = null;
	public static String LI_FICHER_EDITION = "li_FicheArticle";
	public static String UNE_FICHER_EDITION = "FicheArticle";
	public static String FOLDER_IMAGE = "icons"+SEPARATOR;
	public static String FOLDER_IMAGE_LOGO = "Images"+SEPARATOR;
	
	public static String NAME_FILE_EDITION_SPECIFIQUE_ONE = "Fiche_a.rptdesign";
	public static String IMAGE_LOGO_FOND = "logoFond.png";

	
	/** /report/defaut/TaTiers/FicheTiers.rptdesign **/
	public static String FICHE_FILE_REPORT_TIERS = SEPARATOR+"report"+SEPARATOR+"defaut"+SEPARATOR+"TaTiers"+SEPARATOR+"FicheTiers.rptdesign";
	/** /report/defaut/TaArticle/FicheArticle.rptdesign **/
	public static String FICHE_FILE_REPORT_ARTICLES = SEPARATOR+"report"+SEPARATOR+"defaut"+SEPARATOR+"TaArticle"+SEPARATOR+"FicheArticle.rptdesign";
	/** /report/defaut/TaFacture/FicheFacture.rptdesign **/
	public static String FICHE_FILE_REPORT_FACTURE = SEPARATOR+"report"+SEPARATOR+"defaut"+SEPARATOR+"TaFacture"+SEPARATOR+"FicheFacture.rptdesign";
	/** /report/defaut/TaTraite/Traite.rptdesign **/
	public static String FICHE_FILE_REPORT_TRAITE = SEPARATOR+"report"+SEPARATOR+"defaut"+SEPARATOR+"TaTraite"+SEPARATOR+"Traite.rptdesign";
	/** /report/EtatStocks.rptdesign **/
	public static String FICHE_FILE_REPORT_STOCKS = SEPARATOR+"report"+SEPARATOR+"EtatStocks.rptdesign";
	
	/** /report/EtatStocks.rptdesign **/
	public static String FICHE_FILE_REPORT_STOCKS_MOUVEMENTS_DATE = SEPARATOR+"report"+SEPARATOR+"EtatStocksMouvementsDate.rptdesign";
	/** /report/EtatStocks.rptdesign **/
	public static String FICHE_FILE_REPORT_STOCKS_MOUVEMENTS_TYPE = SEPARATOR+"report"+SEPARATOR+"EtatStocksMouvementsType.rptdesign";
	
	public static String FICHE_FILE_REPORT_STOCKS_CAPSULES = SEPARATOR+"report"+SEPARATOR+"EtatStocksCapsules.rptdesign";
	public static String FICHE_FILE_REPORT_STOCKS_CAPSULES_MOUVEMENTS_DATE = SEPARATOR+"report"+SEPARATOR+"EtatStocksCapsulesMouvementsDate.rptdesign";
	public static String FICHE_FILE_REPORT_STOCKS_CAPSULES_MOUVEMENTS_TYPE = SEPARATOR+"report"+SEPARATOR+"EtatStocksCapsulesMouvementsType.rptdesign";
	
	/** /report/EtatDms.rptdesign **/
	public static String FICHE_FILE_REPORT_DMS = SEPARATOR+"report"+SEPARATOR+"EtatDms.rptdesign";
	
	/** /report/EtatDms.rptdesign **/
	public static String FICHE_FILE_REPORT_SYNTHESEDMS = SEPARATOR+"report"+SEPARATOR+"EtatSyntheseDms.rptdesign";
	
	/** /report/EtatDms.rptdesign **/
	public static String FICHE_FILE_REPORT_ETATFAMILLE = SEPARATOR+"report"+SEPARATOR+"EtatFamille.rptdesign";
	
	/** /report/EtatDms.rptdesign **/
	public static String FICHE_FILE_REPORT_SYNTHESEETATFAMILLE = SEPARATOR+"report"+SEPARATOR+"EtatSyntheseFamille.rptdesign";
	
	/** /report/EtatDms.rptdesign **/
	public static String FICHE_FILE_REPORT_ETATARTICLE = SEPARATOR+"report"+SEPARATOR+"EtatArticle.rptdesign";
	
	/** /report/EtatDms.rptdesign **/
	public static String FICHE_FILE_REPORT_SYNTHESEETATARTICLE = SEPARATOR+"report"+SEPARATOR+"EtatSyntheseArticle.rptdesign";
	
	public static String FICHE_GENERATIONDOCUMENT_LGR = "";
	/** /report/defaut/TaDevis/Fiche_devis.rptdesign **/
	public static String FICHE_FILE_REPORT_DEVIS = SEPARATOR+"report"+SEPARATOR+"defaut"+SEPARATOR+"TaDevis"+SEPARATOR+"Fiche_devis.rptdesign";

	/** /report/defaut/TaAcompte/Fiche_acompte.rptdesign **/
	public static String FICHE_FILE_REPORT_ACOMPTE = SEPARATOR+"report"+SEPARATOR+"defaut"+SEPARATOR+"TaAcompte"+SEPARATOR+"Fiche_acompte.rptdesign";

	/** /report/defaut/TaProforma/Fiche_proforma.rptdesign **/
	public static String FICHE_FILE_REPORT_PROFORMA = SEPARATOR+"report"+SEPARATOR+"defaut"+SEPARATOR+"TaProforma"+SEPARATOR+"Fiche_proforma.rptdesign";
	/** /report/defaut/TaBoncde/Fiche_boncde.rptdesign **/
	public static String FICHE_FILE_REPORT_BONCDE = SEPARATOR+"report"+SEPARATOR+"defaut"+SEPARATOR+"TaBoncde"+SEPARATOR+"Fiche_boncde.rptdesign";
	/** /report/defaut/TaApporteur/Fiche_apporteur.rptdesign **/
	public static String FICHE_FILE_REPORT_APPORTEUR = SEPARATOR+"report"+SEPARATOR+"defaut"+SEPARATOR+"TaApporteur"+SEPARATOR+"Fiche_apporteur.rptdesign";
	/** /report/defaut/TaAvoir/Fiche_avoir.rptdesign **/
	public static String FICHE_FILE_REPORT_AVOIR = SEPARATOR+"report"+SEPARATOR+"defaut"+SEPARATOR+"TaAvoir"+SEPARATOR+"Fiche_avoir.rptdesign";
	/** /report/defaut/TaBonliv/Fiche_bonLiv.rptdesign **/
	public static String FICHE_FILE_REPORT_BONLIV = SEPARATOR+"report"+SEPARATOR+"defaut"+SEPARATOR+"TaBonliv"+SEPARATOR+"Fiche_bonLiv.rptdesign";	
	//public static String FICHE_FILE_REPORT_SAISIECAISSE = "/report/defaut/FicheSaisieCaisse.rptdesign";
	/** /report/EtatSaisieCaisseSynthese.rptdesign **/
	public static String FICHE_FILE_ETAT_SAISIECAISSE_SYNTHESE = SEPARATOR+"report"+SEPARATOR+"EtatSaisieCaisseSynthese.rptdesign";
	/** /report/EtatSaisieCaisseJour.rptdesign **/
	public static String FICHE_FILE_ETAT_SAISIECAISSE_JOUR = SEPARATOR+"report"+SEPARATOR+"EtatSaisieCaisseJour.rptdesign";
	/** /report/defaut/TaProforma/Fiche_prelevement.rptdesign **/
	public static String FICHE_FILE_REPORT_PRELEVEMENT = SEPARATOR+"report"+SEPARATOR+"defaut"+SEPARATOR+"TaPrelevement"+SEPARATOR+"Fiche_prelevement.rptdesign";
	
	/** /report/defaut/TaRemise/Fiche_Remise.rptdesign **/
	public static String FICHE_FILE_REPORT_REMISE = SEPARATOR+"report"+SEPARATOR+"defaut"+SEPARATOR+"TaRemise"+SEPARATOR+"Fiche_remise.rptdesign";
	
	/** /report/defaut/TaAvisEcheance/Fiche_AvisEcheance.rptdesign **/
	public static String FICHE_FILE_REPORT_AVIS_ECHEANCE = SEPARATOR+"report"+SEPARATOR+"defaut"+SEPARATOR+"TaAvisEcheance"+SEPARATOR+"Fiche_AvisEcheance.rptdesign";


	/* * 
	 */
	public static String START_FICHER = "Fiche";
	public static String START_LI = "li_";
	public static String START_DIA = "Dia_";
	public static String START_V= "V_";
	
	private boolean flagButtonOneFiche = false;
	
	/**
	 * for format report
	 */
	public static final String FORMAT_PDF ="pdf";
	public static final String FORMAT_HTML ="html";
	public static final String FORMAT_EXTRACTION ="extraction";
	public static final String FORMAT_DOC ="doc";
	public static final String FORMAT_XLS ="xls";
	public static final String FORMAT_PPT ="ppt";
	public static final String FOLDER_REPORT_PLUGIN =SEPARATOR+"report"+SEPARATOR;
	public static final String TITLE_SHELL ="Choix de l'édition"; 
	
	public static final String AUTO_TEXT_PAGE_NUMBER = DesignChoiceConstants.AUTO_TEXT_PAGE_NUMBER;
	public static final String AUTO_TEXT_TOTAL_PAGE = DesignChoiceConstants.AUTO_TEXT_TOTAL_PAGE;
	public static final String TEXT_ALIGN_CENTER = DesignChoiceConstants.TEXT_ALIGN_CENTER;
	public static final String TEXT_ALIGN_RIGHT = DesignChoiceConstants.TEXT_ALIGN_RIGHT;
	public static final String TEXT_ALIGN_LEFT = DesignChoiceConstants.TEXT_ALIGN_LEFT;
	public static final String FONT_SIZE_MEDIUM = DesignChoiceConstants.FONT_SIZE_MEDIUM;
	public static final String FONT_SIZE_SMALL = DesignChoiceConstants.FONT_SIZE_SMALL;
	public static final String FONT_WEIGHT_BOLD = DesignChoiceConstants.FONT_WEIGHT_BOLD;
	public static final String FONT_WEIGHT_NORMAL = DesignChoiceConstants.FONT_WEIGHT_NORMAL;
	public static final String COLUMN_DATA_TYPE_STRING = DesignChoiceConstants.COLUMN_DATA_TYPE_STRING;
	public static final String IMAGE_TYPE_IMAGE_PNG = DesignChoiceConstants.IMAGE_TYPE_IMAGE_PNG;
	public static final String COLUMN_DATA_TYPE_DATE = DesignChoiceConstants.COLUMN_DATA_TYPE_DATE;
	public static final String COLUMN_DATA_TYPE_DECIMAL = DesignChoiceConstants.COLUMN_DATA_TYPE_DECIMAL;
	public static final String COLUMN_DATA_TYPE_INTEGER = DesignChoiceConstants.COLUMN_DATA_TYPE_INTEGER;
	public static final String COLUMN_DATA_TYPE_BOOLEAN = DesignChoiceConstants.COLUMN_DATA_TYPE_BOOLEAN;
	public static final String FONT_SIZE_X_LARGE = DesignChoiceConstants.FONT_SIZE_X_LARGE;
	public static final String FONT_SIZE_XX_LARGE = DesignChoiceConstants.FONT_SIZE_XX_LARGE;
	public static final String COLOR_GRAY = "gray";
	public static final String PAGE_ORIENTATION_LANDSCAPE = DesignChoiceConstants.PAGE_ORIENTATION_LANDSCAPE;
	public static final String PAGE_ORIENTATION_PORTRAIT = DesignChoiceConstants.PAGE_ORIENTATION_PORTRAIT;
	static String PROPERTY_WIDTH= null; 
	static String PAGE_ORIENTATION= null; 
	

	public static final String TEXT_PAGE_FOOTER 	= "Bureau de Gestion : ";
	public static final String EDITION 			= "Edition ";

	public static final String EDITION_VIDE 					= "Il n'y a rien dans cette edition !";
	public static final String TITRE_MESSAGE_EDITION_VIDE 	= "Bureau de Gestion";
	public static final String EDITION_NON_USABLE 			= "Cette edition n'est pas disponible!";
	
	public static final String FOLDER_EDITION_CLIENT = SEPARATOR+Const.FOLDER_EDITION_CLIENT+SEPARATOR;
	
	public static final String FOLDER_EDITION = SEPARATOR+Const.FOLDER_EDITION+SEPARATOR;

	public static final String SEPARATEUR_COMMA	= ".";
	public static final String INFOS_VIDE			= "Infos-vide";
	public static final String TIERS_ENTREPRISE	= "Entreprise";
	public static final String TIERS_BANQUE		= "Banque";
	public static final String TYPE_ENTREPRISE	= "Type_Entreprise";
	
	public static final String IMAGE_CROSS	= "cross.png";
	public static final String IMAGE_ACCEPT	= "accept.png";
	public static String PATH_EDITIONS_IMAGE 	= Const.C_RCP_INSTANCE_LOCATION+SEPARATOR+Const.C_REPERTOIRE_PROJET+FOLDER_EDITION+FOLDER_IMAGE;
	/**
	 * for Report avanced
	 **/
	public static final String CREATED_BY_PROP	= IModuleModel.CREATED_BY_PROP;
	public static final String COMMENTS			= "comments";
	
	public static String CONTENT_COMMENTS = "";
	/**
	 * par-dessous: les constants pour plugin ArchivageEpicea
	 */
	public static final String PATTERN_NUMBER = "\\d";
	
	public static final String COMMENT_LIST_EDITION_DEFAUT	 	= "Liste des fiches ";
	public static final String COMMENT_LIST_EDITION_DYNAMIQUE 	= "Liste des ";
	/**
	 * message edition
	 */
	public static final String MESSAGE_EDITION_PREFERENCE 		= "Le chemin d'edition n'est pas correct !! ";
	public static final String TITLE_MESSAGE_EDITION_PREFERENCE 	= "Erreur chemin edition";

	/*
	 * parametre of edition id
	 */
	public String paramId = null;
	
	private Button ficheTotal;
	private LinkedList<Integer> id;
	private LinkedList<Integer> idOne;

	private Collection collection = null;
	private String nomChampIdTable;
	private Object objectEntity;
	private String nameEntity;
	
	private InfosEmail infosEmail = null;
	private InfosFax infosFax = null;
	
	private String PARAM_ID_TABLE = null;
	
	public static final String NOMBRE_LINE_EDITION_DYNAMIQUE = "600";
	
	public static final String COMMENT_ONE_EDITION_ENTITY = "Fiche %s (pour l'élément selectionné)";
	

	public static final String TEXT_BUTTON_EDITION_DEFAUT = "Fiche ";
	
	private TaInfoEntreprise taInfoEntreprise ;
	

	public static final String[] arrayEntity = {"TaFacture","TaApporteur","TaBonliv","TaBoncde",
												"TaAvoir","TaDevis","TaProforma"};
	
	public List<String> listEntity = new ArrayList<String>();
	
	
	
	public Map<String, String> mapNameExpandbar  = new LinkedHashMap<String, String>(); 
	public Map<String, String> mapIconExpandItem = new LinkedHashMap<String, String>(); 
	public Map<String, ExpandItem> mapExpandItem = new HashMap<String, ExpandItem>();
	private Map<String, Composite>  mapExpandItemComposite = new HashMap<String, Composite>();
	
	private Map<String,String>  mapFileEditionandPlugin = new HashMap<String,String>();
	
	public static final String NAME_EXPANDITEM_EDITION_DEFAUT = "Editions";
	
	/**
	 * 16/12/2009 ajouter   
	 */
	/**
	 * pour pagePreference, si selected, afficher cette button
	 * 						si non selected , ne afficher pas cette button
	 */
//	static LinkedList<Button> listButtonCheckbox = new LinkedList<Button>();
	static LinkedHashMap<Button,String> mapButtonCheckbox = new LinkedHashMap<Button,String>();
	/**
	 * pour stocked quelles edition affichent
	 */
	public LinkedList<String> listButtonCheckboxPreferencePage = new LinkedList<String>();
	/** Pour stocker Quelles Button Radio Verrouillent ou non **/
	public LinkedHashMap<String,Boolean> mapButtonRadio = new LinkedHashMap<String,Boolean>();
	
	public static String nameFileEditionSelected = null;
	public static String pathFileEditionSelected = null;
	public static String nameButtonSelected = null;
	public List<ExpandItem> listExpandItem = new ArrayList<ExpandItem>(); 
	private  AffichageEdition affichageEdition = null;
	private boolean flagListFicheEdition = false;
	private boolean flagListFicheEditionDocument = false;
	private String commentEditionDynamique = "";
	
	public static String pathEditionDefautPreferencePage = null;
	public static String affichageEditionPreferencePage  = null;
	
	public String valuePropertieNamePlugin 			= "";
	public String valuePropertiePathEditionDefaut 	= "";
	public String valuePropertieChoixEditions		= "";
	public String valuePropertieCommentEditions 	= "";
	
	
	public static final String PROPERTIES_COMMENT_DEFAUT_EDITION = "commmentEdition";
	public static final String PROPERTIES_CHOIX_EDITION = "choixEdition";
	public static final String PROPERTIES_PATH_EDITIONDEFAUT = "defautPathEdition";
	public static final String PROPERTIES_PLUGIN_EDITIONDEFAUT = "pluginDefautEdition";
	
	public static final String PROPERTIES_OPTION_AFFICHAGE = "optionAffichage";
	
	public  String propertiesCommentDefautEdition = "";
	public  String propertiesChoixEdition = "";
	public  String propertiesDefautEditionPlugin = "";
	public  String propertiesPathEditiondefaut = "";
	
	private StringButtonFieldEditorLgr fieldPathEditionDefaut = null;
	/**
	 * pour savoir , combien de Button Radio une ExpandItem contien? 
	 */
	private LinkedHashMap<String, LinkedHashMap<String,String> > mapExpandItemButton = new LinkedHashMap<String, 
																LinkedHashMap<String,String>>(); 
	/**
	 * en fonction de cette variable, pour choisir quelle fonction pour immprimer edition 
	 * Ex : si  true , imprimer une entity de l'edition
	 * 		si  false,  imprimer tous les entitys de l'edition
	 */
	public static boolean choixOneFicherEdition = false;
	
	/** 29/01/2010 add pour resoudre vittess de programme (Eventuellement) **/
	private EntityManager entityManager = null;
	boolean flagentityManager = false;
	
	/** 03/02/2010 add pour update file de proprities preference page edition**/
	
	public static final String[] NAME_FOLDER_EDITION = {FOLDER_EDITION,FOLDER_EDITION_CLIENT,FOLDER_REPORT_PLUGIN};
	
	/**************************par-dessous: les fonctions********************************/
	public String propertyWidth(){
		String PROPERTY_WIDTH = UserPropertyDefn.STRUCTURE_NAME;
		PROPERTY_WIDTH = "width";
		return PROPERTY_WIDTH;
	}
	
	/**
	 * Get le nombre lignes sur l'interface
	 */
	public int nombreLineTable(LgrTableViewer tableViewer){

		return tableViewer.getTable().getItemCount();
	}

	public static final String FORMAT_TAUX_TVA = "@@@@";

	public ConstEdition(String width,String pageOrientation) {
		super();
		PROPERTY_WIDTH = UserPropertyDefn.STRUCTURE_NAME;
		this.PROPERTY_WIDTH = width;

		PAGE_ORIENTATION = UserPropertyDefn.STRUCTURE_NAME;
		this.PAGE_ORIENTATION = pageOrientation;
	}

	public ConstEdition(){}
	
	public ConstEdition(EntityManager entityManager){
		this.entityManager = entityManager;
	}
	
	public void addExpandbarListener(final SwtReportWithExpandbar swtReportWithExpandbar ){
		
		final ExpandBar expandBar = swtReportWithExpandbar.getExpandBarEdition();
		
		expandBar.addListener(SWT.Expand, new Listener() {
			 
            @Override
            public void handleEvent(Event e) {
            	for (int i = 0; i < expandBar.getItems().length; i++) {
					ExpandItem item = expandBar.getItems()[i];
					
					if (e.item == item) {
						Composite composite = (Composite)item.getControl();
						if(composite.getChildren().length != 0){
							Button button = (Button)composite.getChildren()[0];
							button.setSelection(true);
							swtReportWithExpandbar.getTextCommentsEdition().setText(button.getText());
							File fileEdition = new File(nameFileReport.get(button.getText()));
							nameFileEditionSelected = fileEdition.getName();
							pathFileEditionSelected = fileEdition.getAbsolutePath();
						}		
					} else {
						item.setExpanded(false);
						Composite composite = (Composite)item.getControl();
						for (int j = 0; j < composite.getChildren().length; j++) {
							Button button = (Button)composite.getChildren()[j];
							button.setSelection(false);
						}
					}
				} 
            	
            }
        });

	}
	
	public boolean fillMapExpandItemDefaut(File fileReportDefaut,File fileReportDynamique,String nomPlugin,
			/*AnalyseFileReport analyseFileReport*/String commentEditionDynamique,boolean flagButtonOneFiche,
			AnalyseFileReport analyseFileReport,File folderEditionSupp){
		
		boolean flag = false; 
		LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
		boolean flagFileExist = false;
		if(fileReportDefaut.exists()){
			flagFileExist = true;
			
			String textButtonFicher = String.format(ConstEdition.COMMENT_ONE_EDITION_ENTITY, nomPlugin,nomPlugin);
			map.put(textButtonFicher, fileReportDefaut.getPath());
			mapFileEditionandPlugin.put(textButtonFicher,nomPlugin);
			
			if(flagButtonOneFiche){
				String textEditionDefaut = ConstEdition.COMMENT_LIST_EDITION_DEFAUT + nomPlugin;
				map.put(textEditionDefaut, fileReportDefaut.getPath());
				mapFileEditionandPlugin.put(textEditionDefaut,nomPlugin);
			}
			
		}
		if(fileReportDynamique != null){
//			analyseFileReport.initializeBuildDesignReportConfig(fileReportDynamique.getAbsolutePath());
//			String CommentaireEdition = analyseFileReport.findCommentsReport();
			//map.put(commentEditionDynamique, fileReportDynamique.getAbsolutePath());
			map.put(commentEditionDynamique, fileReportDynamique.getPath());
			mapFileEditionandPlugin.put(commentEditionDynamique,"tmp");
			flag = true;
		}
		
		/** Edition supplimentaire 24/02/2010 **/
		File[] arrayFileEdition = arrayPathFileParamEtiquette(folderEditionSupp);
		if(arrayFileEdition.length != 0){
			flagFileExist = true;
			for (File fileEdition : arrayFileEdition) {
				analyseFileReport.initializeBuildDesignReportConfig(fileEdition.getPath());
				String commentEdition = analyseFileReport.findCommentsReport();
				String description = analyseFileReport.findDescription();
				map.put(commentEdition, fileEdition.getPath());
				mapFileEditionandPlugin.put(commentEdition,description);
			}
		}
		/***************************************/
		mapExpandItemButton.put(NAME_EXPANDITEM_EDITION_DEFAUT,map);
		
		return !flagFileExist && flag;
	}
	
	public boolean fillMapExpandItemSpecifique(File pathFolder,AnalyseFileReport analyseFileReport){
			
		boolean flag = false;
		int count = 0;
		if(pathFolder != null){
			String directoryToSearch = pathFolder.getAbsolutePath();  
			if(pathFolder.exists()){
				File[] listOfFiles = pathFolder.listFiles();
				for (File item : listOfFiles) {
					String nameFolder = item.getName();
					if(item.isDirectory() && mapNameExpandbar.containsKey(nameFolder)){
						LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
						File subFolder = new File(directoryToSearch+SEPARATOR+nameFolder);
						String nameExpandItem = mapNameExpandbar.get(nameFolder);
						File[] subListOfFiles = subFolder.listFiles();
						for (File subItemFile : subListOfFiles) {
							if(subItemFile.isFile()){
								analyseFileReport.initializeBuildDesignReportConfig(subItemFile.getAbsolutePath());
								String CommentaireEdition = analyseFileReport.findCommentsReport();
								String descriptionEdition = analyseFileReport.findDescription();
								//map.put(CommentaireEdition, subItemFile.getAbsolutePath());
								map.put(CommentaireEdition, subItemFile.getPath());
								mapFileEditionandPlugin.put(CommentaireEdition,descriptionEdition);
							}
						}
						count += map.size();
						if(mapExpandItemButton.containsKey(nameExpandItem)){
							LinkedHashMap<String, String> mapExist = mapExpandItemButton.get(nameExpandItem);
							for (String commantaire : map.keySet()) {
								mapExist.put(commantaire, map.get(commantaire));
							}
						}else{
							mapExpandItemButton.put(nameExpandItem, map);
						}
						
					}
				}
			}
		}
		if(count == 0){
			flag = true;
		}
		return flag;
	}
	
	public boolean checkEditionPayForExpandItem(AnalyseFileReport analyseFileReport,String nomPlugin,
											    String nameEntity){
		Application application = new Application();
		//application.askFonction(nomPlugin,objectEntity.getClass().getSimpleName(),analyseFileReport);
		application.askFonction(nomPlugin,nameEntity,analyseFileReport);
		
		application.getMapFileReport();
//		addBtEdition(application.getMapFileReport(), objetSwtCompositeReport);
		boolean isFlagEditionPay = true;
		if(application.getPathFolderEditionPay() != null){
			File fileEditionPay = new File(application.getPathFolderEditionPay());
			isFlagEditionPay = fillMapExpandItemSpecifique(fileEditionPay,analyseFileReport);
		}
		
		return isFlagEditionPay;
	}
	
	public void addExpandItemAndButtonEdition(SwtReportWithExpandbar swtReportWithExpandbar,
			LinkedHashMap<String,LinkedHashMap<String,String>> mapExpandItemButton){

		
		ExpandBar expandBar = swtReportWithExpandbar.getExpandBarEdition();
		
		for (String nameExpandItem : mapExpandItemButton.keySet()) {
			ExpandItem item = new ExpandItem(expandBar, SWT.PUSH);
			Composite composite = new Composite(expandBar,SWT.NONE);
			GridLayout thisLayout = new GridLayout();
			composite.setLayout(thisLayout);
			
			LinkedHashMap<String,String> map = mapExpandItemButton.get(nameExpandItem);
			boolean flag = false;
			for (String textButton : map.keySet()) {
				
//				if(listButtonCheckboxPreferencePage.size() != 0){
					if(listButtonCheckboxPreferencePage.contains(textButton)){
						Button buttonRadio = null;
						buttonRadio = new Button(composite,SWT.RADIO |SWT.LEFT);
						buttonRadio.setText(textButton);
						if(textButton.equals(nameButtonSelected)){
							buttonRadio.setSelection(true);
							flag = true;
						}
						showCommentsEditionAndactionButton(buttonRadio,swtReportWithExpandbar,textButton,
								new File(map.get(textButton)));
						nameFileReport.put(textButton,map.get(textButton));
						listButton.add(buttonRadio);
					}
//				}else{
//					buttonRadio = new Button(composite,SWT.RADIO |SWT.LEFT);
//					buttonRadio.setText(textButton);
//					ConstEdition.showCommentsEditionAndactionButton(buttonRadio,swtReportWithExpandbar,textButton,
//							new File(map.get(textButton)));
//					nameFileReport.put(textButton,map.get(textButton));
//					listButton.add(buttonRadio);
//				}
				
	
//				ConstEdition.showCommentsEditionAndactionButton(buttonRadio,swtReportWithExpandbar,textButton,
//						new File(map.get(textButton)));
//				nameFileReport.put(textButton,map.get(textButton));
//				listButton.add(buttonRadio);
				
			}
			item.setExpanded(flag);
			item.setText(nameExpandItem);
			item.setHeight(composite.computeSize(SWT.DEFAULT, SWT.DEFAULT).y);
			item.setControl(composite);
			String pathRelativeImage = mapIconExpandItem.get(nameExpandItem);
			Image image = Activator.getImageDescriptor(pathRelativeImage).createImage();
			item.setImage(image);
			
		}
	}

	/**
	 * pour update les values dans ficher (PreferencePage.properties) 
	 * @param namePlugin
	 * @param mapExpandItemButton
	 */
	public void getInfosPropertiesPreferencePage(String namePlugin,
			LinkedHashMap<String,LinkedHashMap<String,String>> mapExpandItemButton){
		
		listButtonCheckboxPreferencePage.clear();
		
		Properties props = PropertiesFilePreference.getProperties();
		InputStream in;
		FileOutputStream out;
		try {
			in = new BufferedInputStream (new FileInputStream(Const.C_FICHIER_PREFERENCE_PAGE));
			props.load(in);
			in.close();
			
			propertiesDefautEditionPlugin = PROPERTIES_PLUGIN_EDITIONDEFAUT+namePlugin;
			propertiesCommentDefautEdition = PROPERTIES_COMMENT_DEFAUT_EDITION+namePlugin;
			propertiesChoixEdition = PROPERTIES_CHOIX_EDITION+namePlugin;
			propertiesPathEditiondefaut = PROPERTIES_PATH_EDITIONDEFAUT+namePlugin;
			
			
			if(!props.containsKey(propertiesDefautEditionPlugin)){
				props.setProperty(propertiesDefautEditionPlugin,valuePropertieNamePlugin);				
				out = new FileOutputStream(Const.C_FICHIER_PREFERENCE_PAGE);
				props.store(out,null);
				out.close();
			}else{
				valuePropertieNamePlugin = props.getProperty(remplaceBackSlashAndSlash(propertiesDefautEditionPlugin));
			}
			
			if(!props.containsKey(propertiesCommentDefautEdition)){
				props.setProperty(propertiesCommentDefautEdition, valuePropertieCommentEditions);
				out = new FileOutputStream(Const.C_FICHIER_PREFERENCE_PAGE);
				props.store(out,null);
				out.close();
			}else{
				valuePropertieCommentEditions = props.getProperty(propertiesCommentDefautEdition);
			}
			
			
			if(!props.containsKey(propertiesChoixEdition)){
				int count = 0;
				for (String nameExpandItem : mapExpandItemButton.keySet()) {
					LinkedHashMap<String,String> map = mapExpandItemButton.get(nameExpandItem);
					for (String textButton : map.keySet()) {
						listButtonCheckboxPreferencePage.add(textButton);
						count ++;
					}
				}
				valuePropertieChoixEditions = makePropertiesValueChoixEdition(listButtonCheckboxPreferencePage);
				props.setProperty(propertiesChoixEdition, valuePropertieChoixEditions);
				
				out = new FileOutputStream(Const.C_FICHIER_PREFERENCE_PAGE);
				props.store(out,null);
				out.close();
			}else{
				valuePropertieChoixEditions = props.getProperty(propertiesChoixEdition);
				String[] arrayValue = valuePropertieChoixEditions.split(";");
				for (int i = 0; i < arrayValue.length; i++) {
					listButtonCheckboxPreferencePage.add(arrayValue[i]);
				}
			}

			/** 03/02/2010 **/
			if(!props.containsKey(propertiesPathEditiondefaut)){
				props.setProperty(propertiesPathEditiondefaut, valuePropertiePathEditionDefaut);
								
				out = new FileOutputStream(Const.C_FICHIER_PREFERENCE_PAGE);
				props.store(out,null);
				out.close();
			}else{
				valuePropertiePathEditionDefaut = props.getProperty(propertiesPathEditiondefaut);
			}
			
		} catch (Exception e) {
			logger.error("",e);
		}
		
	}
	
	/**
	 * Construire String contient les edition qui affichent.
	 * @param listString ==> quelle editions sont pris pour afficher dans preference page 
	 * @return
	 */
	public String makePropertiesValueChoixEdition(LinkedList<String> listString){
		String valueFinal = "";
		
		for (int i = 0; i < listButtonCheckboxPreferencePage.size(); i++) {

			String value = listButtonCheckboxPreferencePage.get(i);
			if(i != (listButtonCheckboxPreferencePage.size()-1)){
				value += ";";
			}
			valueFinal += value; 
		}
		
		return valueFinal;
	}
	
	/**
	 *  Pour Preference Page 
	 * @param scrolledComposite 
	 * @param mapExpandItemButton
	 * @param namePlugin
	 */
	public void addExpandItemAndButtonEditionPreference(ScrolledComposite scrolledComposite,
			LinkedHashMap<String,LinkedHashMap<String,String>> mapExpandItemButton,
			String namePlugin){
		
		nameFileReport.clear();
		listButton.clear();
		mapButtonCheckbox.clear();
		
		getInfosPropertiesPreferencePage(namePlugin,mapExpandItemButton);
		
		ExpandBar expandBar = new ExpandBar(scrolledComposite, SWT.V_SCROLL);
		
		String commentEditionDefaut = String.format(ConstEdition.COMMENT_ONE_EDITION_ENTITY,namePlugin,namePlugin);
		
		for (String nameExpandItem : mapExpandItemButton.keySet()) {
			ExpandItem item = new ExpandItem(expandBar, SWT.PUSH);
			Composite composite = new Composite(expandBar,SWT.NONE);
			GridLayout thisLayout = new GridLayout(2, true);
			composite.setLayout(thisLayout);
			
			LinkedHashMap<String,String> map = mapExpandItemButton.get(nameExpandItem);
			
			for (String textButton : map.keySet()) {
				
				Button buttonRadio = new Button(composite,SWT.RADIO |SWT.LEFT);
				buttonRadio.setText(textButton);
				
				if(textButton.equals(valuePropertieCommentEditions)){
					buttonRadio.setSelection(true);
				}else{
					buttonRadio.setSelection(false);
				}
				
				if(!listButtonCheckboxPreferencePage.contains(textButton)){
					buttonRadio.setEnabled(false);
				}
				nameFileReport.put(textButton,map.get(textButton));
				listButton.add(buttonRadio);

				Button buttonCheckbox = new Button(composite,SWT.CHECK |SWT.RIGHT);
				buttonCheckbox.setText("Afficher");
				if(textButton.equals(commentEditionDefaut)){
					buttonCheckbox.setEnabled(false);
				}
				mapButtonCheckbox.put(buttonCheckbox, textButton);
				if(listButtonCheckboxPreferencePage.contains(textButton)){
					buttonCheckbox.setSelection(true);
				}else{
					buttonCheckbox.setSelection(false); 
				}
				
				addActionBtRadioAndBtCheckBoxPreferencePage(buttonRadio, buttonCheckbox);	
			}
			
			item.setText(nameExpandItem);
			item.setHeight(composite.computeSize(SWT.DEFAULT, SWT.DEFAULT).y);
			item.setControl(composite);
			String pathRelativeImage = mapIconExpandItem.get(nameExpandItem);
			Image image = Activator.getImageDescriptor(pathRelativeImage).createImage();
			item.setImage(image);
			
		}
		scrolledComposite.setContent(expandBar);
		scrolledComposite.setExpandHorizontal(true);
		scrolledComposite.setExpandVertical(true);
		scrolledComposite.setMinSize(expandBar.computeSize(SWT.DEFAULT, SWT.DEFAULT));
		
	}

	public void initialInfosEdition(String namePlugin,String pathEdition,File pathFileDynamique){
		
		String pathFilePreferencePage = new File(Const.C_FICHIER_PREFERENCE_PAGE).getPath();
		
		Properties props = new Properties();
		InputStream in;
		try {
			in = new BufferedInputStream (new FileInputStream(pathFilePreferencePage));
			props.load(in);
			in.close();
			String defautPluginEdition =  props.getProperty(PROPERTIES_PLUGIN_EDITIONDEFAUT+namePlugin);
			if(props.getProperty(PROPERTIES_PATH_EDITIONDEFAUT+namePlugin) != null){
				
				if(defautPluginEdition.equals("tmp")) {
					pathFileEditionSelected = remplaceBackSlashAndSlash(pathFileDynamique.getPath());
				} else if(defautPluginEdition.equals(Const.FOLDER_EDITION_SUPP)) {
					String folderEditionsSupp = new File(new File(Const.C_FICHIER_PREFERENCE_PAGE).getParent()).getParent();
					//pathFileEditionSelected = Const.C_REPERTOIRE_BASE+Const.C_REPERTOIRE_PROJET+
					pathFileEditionSelected = folderEditionsSupp+
											  //ConstEdition.SEPARATOR+
											  props.getProperty(PROPERTIES_PATH_EDITIONDEFAUT+namePlugin);
				} else {
					pathFileEditionSelected =  pathEditionAImprimer(props.getProperty(PROPERTIES_PATH_EDITIONDEFAUT+namePlugin),
							props.getProperty(PROPERTIES_PLUGIN_EDITIONDEFAUT+namePlugin));
				}
				
			} else {
				pathFileEditionSelected = pathEdition;
			}
			if(pathFileEditionSelected==null) pathFileEditionSelected = pathEdition;
			if(props.getProperty(PROPERTIES_COMMENT_DEFAUT_EDITION+namePlugin) != null) {
				nameButtonSelected = props.getProperty(PROPERTIES_COMMENT_DEFAUT_EDITION+namePlugin);
				if(nameButtonSelected.startsWith(COMMENT_LIST_EDITION_DEFAUT)){					
					flagListFicheEdition = true;
				} else {
					flagListFicheEdition = false;
				}
			} else {
				flagListFicheEdition = false;
				nameButtonSelected = String.format(ConstEdition.COMMENT_ONE_EDITION_ENTITY, namePlugin,namePlugin);
			}
			if(props.getProperty(PROPERTIES_CHOIX_EDITION+namePlugin) != null) {
				valuePropertieChoixEditions = props.getProperty(PROPERTIES_CHOIX_EDITION+namePlugin);
				String[] arrayValue = valuePropertieChoixEditions.split(";");
				for (int i = 0; i < arrayValue.length; i++) {
					listButtonCheckboxPreferencePage.add(arrayValue[i]);
				}
			} else {
				for (String nameExpandItem : mapExpandItemButton.keySet()) {
					LinkedHashMap<String,String> map = mapExpandItemButton.get(nameExpandItem);
					for (String textButton : map.keySet()) {
						listButtonCheckboxPreferencePage.add(textButton);
					}
				}
			}
			
			if(props.getProperty(PROPERTIES_COMMENT_DEFAUT_EDITION+namePlugin) != null) {
				valuePropertieCommentEditions = props.getProperty(PROPERTIES_COMMENT_DEFAUT_EDITION+namePlugin);
			} else {
				valuePropertieCommentEditions = String.format(ConstEdition.COMMENT_ONE_EDITION_ENTITY, 
						namePlugin,namePlugin);
			}
//			if(pathFileEditionSelected!=null)
				nameFileEditionSelected = new File(pathFileEditionSelected).getName();
		} catch (Exception e) {
			logger.error("",e);
		}
	}
		
	public void prepartionEditionBirt(EntityManager entityManager) {
		/**
		 * pour vider les objects dans List ou Map
		 * List ou Map pour les editions
		 */
		ImprimeObjet.clearListAndMap();
		/** Birt generer l'edition **/
		ViewerPlugin.getDefault().getPluginPreferences().setValue(WebViewer.APPCONTEXT_EXTENSION_KEY, EditionAppContext.APP_CONTEXT_NAME);
		
		if(entityManager != null ) {
			this.entityManager =  entityManager;
		} else {
			this.entityManager = new JPABdLgr().getEntityManager();
//			ImprimeObjet.listEntityManager.add(JPABdLgr.getEntityManager());
		}
		ImprimeObjet.listEntityManager.add(this.entityManager);
	}
	
	
	public File[] arrayPathFileParamEtiquette(File pathFolder){
		FilenameFilter filter = new TypeFileReportFilter();
		File[] arrayFileReport = pathFolder.listFiles(filter);
		return arrayFileReport;
	}
	
	private class TypeFileReportFilter implements FilenameFilter{
		@Override
		public boolean accept(File dir, String nameFile) {
			return nameFile.endsWith(ConstEdition.TYPE_FILE_REPORT);
		}
	}
	
	/**
	 * 05/10/2010 Add 
	 * Pour get l'infos of edition (les edition affichaent )
	 * @param fileEditionDefaut
	 * @param fileEditionDynamique
	 * @param namePlugin
	 * @param fileEditionSpecifiquesClient
	 * @param fileEditionSpecifiques
	 * @param nameEntity
	 * @param commentEditionDynamique
	 * @param flagButtonOneFiche
	 * @return
	 */
	public boolean getAllInfosEdition(File fileEditionDefaut,File fileEditionDynamique,String namePlugin,
									  File fileEditionSpecifiquesClient,File fileEditionSpecifiques,
									  String nameEntity,String commentEditionDynamique,
									  boolean flagButtonOneFiche,File fileEditionSupp){
		boolean flag = false;
		
		cleanMapAndList();
		AnalyseFileReport analyseFileReport = new AnalyseFileReport();
		
		boolean isFlagEditionDefautAndDynamique = fillMapExpandItemDefaut(fileEditionDefaut,
				fileEditionDynamique, namePlugin, commentEditionDynamique,
				flagButtonOneFiche,analyseFileReport,fileEditionSupp);;
		 					
		boolean isFlagEditionSpecifiquesClient = fillMapExpandItemSpecifique(fileEditionSpecifiquesClient, 
			     analyseFileReport);

		boolean isFlagEditionSpecifiques = fillMapExpandItemSpecifique(fileEditionSpecifiques, 
				  analyseFileReport);
	
		/** check out edition supplimentair **/
		boolean isFlagEditionSupp = fillMapExpandItemSpecifique(fileEditionSupp,analyseFileReport);
		
		/**
		 * methode extention point
		 */
		boolean isFlagEditionPay = checkEditionPayForExpandItem(analyseFileReport,namePlugin,nameEntity);
		
		flag = isFlagEditionSpecifiquesClient && isFlagEditionSpecifiques && isFlagEditionDefautAndDynamique && isFlagEditionPay
		       && isFlagEditionSupp;
		
		return flag;
	}
	
	public void openDialogChoixEditionDefaut(final SwtReportWithExpandbar objetSwtCompositeReport,
			final File fileEditionSpecifiquesClient,final String pathFileReportDefaut,final String nomPlugin,
			final String titleOngletEdition,final Shell shell,final File fileReportDynamique,
			boolean affiche,final HashMap<String,String> reportParam,File fileEditionSpecifiques,
			boolean buttonReportDefaut,boolean flagPrint,boolean afficheEditionAImprimer,
			//String pathFileAImprimer,String pathAdobeReader,File folderEditionSupp) {
			String pathFileAImprimer,String pathAdobeReader,String nameEntity) {
	
		/** 23/02/2010 **/
		String reportEditionSupp = Const.PATH_FOLDER_EDITION_SUPP+ConstEdition.SEPARATOR+nomPlugin+ConstEdition.SEPARATOR+nameEntity;
		File folderEditionSupp = makeFolderEditions(reportEditionSupp);
		/****************/
		
		setReportParamLoc(reportParam);
		
		File filePathFileReportDefaut = new File(pathFileReportDefaut);
		boolean flag = getAllInfosEdition(filePathFileReportDefaut,fileReportDynamique,nomPlugin,
				  fileEditionSpecifiquesClient,fileEditionSpecifiques,nameEntity,
				  commentEditionDynamique,flagButtonOneFiche,folderEditionSupp);
		
		initialInfosEdition(nomPlugin,pathFileReportDefaut,fileReportDynamique);
		
		addExpandItemAndButtonEdition(objetSwtCompositeReport,mapExpandItemButton);
			
		addExpandbarListener(objetSwtCompositeReport);
		
		/** ajouter les images pour button Annuler et button valider **/
		initImageBouton(objetSwtCompositeReport);

		initCommentaire(objetSwtCompositeReport);
				
		affichageEdition = new AffichageEdition(this.entityManager);
		affichageEdition.setAppContextEdition(new EditionAppContext().getExtensionMap());
		
		affichageEdition.setCheminFichierEditionBirt(pathFileReportDefaut);
		
		/** 09/02/2010 **/
		affichageEdition.setFlagPrint(flagPrint);
		affichageEdition.setAfficheEditionAImprimer(afficheEditionAImprimer);
		affichageEdition.setRepertoireStockagePDFGenere(pathFileAImprimer);
		affichageEdition.setPathAdobeReader(pathAdobeReader);
		
		/** 05/01/2010 add **/
		if(flag) {
			/** afficher l'edition , mais ne imprimer pas **/
			if(!flagPrint) {
				if(afficheEditionAImprimer) {
					afficheEditionDynamiqueDirect(affichageEdition,fileReportDynamique.getAbsolutePath(),titleOngletEdition);
				}
			} else {
				HashMap<String,String> reportParams = null;
				affichageEdition.setCheminFichierEditionBirt(fileReportDynamique.getAbsolutePath());
				reportParams = addMapParametre(reportParam,PARAM_ID_TABLE);
				
				Collection collectionEntity = new LinkedList();
				/** preparer edition dynamique **/
				AffichageEdition.listEditionDynamique.clear();
				Iterator it = collection.iterator(); 
				while (it.hasNext()) {
					Object object = it.next();
					AffichageEdition.listEditionDynamique.add(object);
				}
				collectionEntity.add(this.objectEntity);
				
				affichageEdition.imprimerThreadAllFiche(collectionEntity,true,reportParams,PARAM_ID_TABLE);
			}
			
		} else {
	
			ScrolledComposite scrolledComposite = objetSwtCompositeReport.getScrolledCompositeListReport();
			ExpandBar expandBar = objetSwtCompositeReport.getExpandBarEdition();
			scrolledComposite.setContent(expandBar);
			scrolledComposite.setExpandHorizontal(true);
			scrolledComposite.setExpandVertical(true);
			scrolledComposite.setMinSize(expandBar.computeSize(SWT.DEFAULT, SWT.DEFAULT));
			
			//Ajout du listener du bouton valider de la fenetre du choix d'edition => declenche reellement l'edition
			EcranChoixEditionSelectionListener ecranChoixEditionSelectionListener = new EcranChoixEditionSelectionListener(objetSwtCompositeReport, 
					pathFileReportDefaut,nomPlugin, shell, 
					fileEditionSpecifiques, titleOngletEdition); 
			objetSwtCompositeReport.getButtonValiderPrint().addSelectionListener(ecranChoixEditionSelectionListener);
			
			if(infosEmail!=null) {
				final Object finalObjectEntity = this.objectEntity;
				objetSwtCompositeReport.getButtonEmail().addSelectionListener(new SelectionListener() {
					public void widgetDefaultSelected(SelectionEvent e) {
						widgetSelected(e);
					}
					public void widgetSelected(SelectionEvent e) {
						affichageEdition.setCreationFichierPDF(true);

						///////////////////////////////////////////////////////////////		
						HashMap<String,String> reportParams = null;
						affichageEdition.setCheminFichierEditionBirt(pathFileEditionSelected);
						reportParams = addMapParametre(reportParam,PARAM_ID_TABLE);

						Collection collectionEntity = new LinkedList();
						//					/** preparer edition dynamique **/
						//					AffichageEdition.listEditionDynamique.clear();
						//					Iterator it = collection.iterator(); 
						//					while (it.hasNext()) {
						//						Object object = it.next();
						//						AffichageEdition.listEditionDynamique.add(object);
						//					}
						collectionEntity.add(finalObjectEntity);

						String cheminFichierPDFGenere = affichageEdition.imprimerThreadAllFiche(collectionEntity,true,reportParams,PARAM_ID_TABLE);	
						shell.close();
						/////////////////////////////////////////////////////////////////////////
						affichageEdition.genereEtEnvoiPDFparMail(cheminFichierPDFGenere,infosEmail);
					}
				});
			} else {
				objetSwtCompositeReport.getButtonEmail().setEnabled(false);
			}
			
			if(infosFax!=null) {
				final Object finalObjectEntity = this.objectEntity;
				objetSwtCompositeReport.getButtonFax().addSelectionListener(new SelectionListener() {
					public void widgetDefaultSelected(SelectionEvent e) {
						widgetSelected(e);
					}
					public void widgetSelected(SelectionEvent e) {
						affichageEdition.setCreationFichierPDF(true);

						///////////////////////////////////////////////////////////////		
						HashMap<String,String> reportParams = null;
						affichageEdition.setCheminFichierEditionBirt(pathFileEditionSelected);
						reportParams = addMapParametre(reportParam,PARAM_ID_TABLE);

						Collection collectionEntity = new LinkedList();
						collectionEntity.add(finalObjectEntity);

						String cheminFichierPDFGenere = affichageEdition.imprimerThreadAllFiche(collectionEntity,true,reportParams,PARAM_ID_TABLE);	
						shell.close();
						/////////////////////////////////////////////////////////////////////////
						affichageEdition.genereEtEnvoiPDFparFax(cheminFichierPDFGenere,infosFax);
					}
				});
			} else {
				objetSwtCompositeReport.getButtonFax().setEnabled(false);
			}

			//Ajout du listener du bouton annuler de la fenetre du choix d'edition => ferme la fenêtre
			objetSwtCompositeReport.getButtonAnnulerPrint().addSelectionListener(new SelectionListener() {
				public void widgetDefaultSelected(SelectionEvent e) {
					widgetSelected(e);
				}
				public void widgetSelected(SelectionEvent e) {
					shell.close();
				}
			});
			
			//ouverture de la fenêtre
			shell.open();
			objetSwtCompositeReport.getButtonValiderPrint().setFocus();
		}
	}
	

	/**
	 * pour obtenir une partie de path absolute file d'edition
	 * @param pathEdition  ==> path absolute file d'edition
	 */
	public String getPartiPathEdition(String pathEdition) {
	
		String value = null;

		String[] arrayValues = {"\\breport\\b","\\bEditionsClient\\b","\\bEditions\\b","\\btmp\\b","\\bEditionsSupp\\b"};
		for (int i = 0; i < arrayValues.length; i++) {
			Pattern pattern = Pattern.compile(arrayValues[i]);
			Matcher matcher = pattern.matcher(pathEdition);
			while(matcher.find()) {
				valuePropertiePathEditionDefaut = remplaceBackSlashAndSlash(SEPARATOR+pathEdition.substring(matcher.start()));
//				valuePropertieNamePlugin = remplaceBackSlashAndSlash(new File(pathEdition.substring(0,matcher.start())).getName());
				break;
			}
		}
		return value;
	}
	
	
	/** 
	 * Pour action Button <<Reinitialiser>> dans page Preference 
	 */
	public void updateValuePreferencePage(String pathDefaut) {

		String txBt = null;
		for (int i = 0; i < listButton.size(); i++) {
			Button button = listButton.get(i);
			if(i==0) {
				button.setSelection(true);
				txBt = button.getText() ;
			} else {
				button.setSelection(false);
			}
		}
		valuePropertieCommentEditions = txBt;
		getPartiPathEdition(nameFileReport.get(valuePropertieCommentEditions));
		
	}
	
	public void addActionBtRadioAndBtCheckBoxPreferencePage(final Button btRadioChoixEdition,final Button btCheckBox){
		/** pour Button Radio **/
		btRadioChoixEdition.addSelectionListener(new SelectionListener() {
					
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);
				
			}
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(btRadioChoixEdition.getSelection()) {
					String textButton = btRadioChoixEdition.getText();
					String valuePathEdition = remplaceBackSlashAndSlash(nameFileReport.get(textButton));
					for (int i = 0; i < listButton.size(); i++) {
						Button button = listButton.get(i);
						if(!btRadioChoixEdition.equals(button)) {
							button.setSelection(false);
						} else {
							valuePropertieCommentEditions = button.getText();
//							valuePropertieNamePlugin = mapFileEditionandPlugin.get(valuePropertieCommentEditions);
						}
					}
					getPartiPathEdition(valuePathEdition);
					
					fieldPathEditionDefaut.setStringValue(valuePropertiePathEditionDefaut);
				}
			}
		});
		
		/** pour Button Radio **/
		btCheckBox.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);
			}

			@Override
			public void widgetSelected(SelectionEvent e) {
				String valueTextBt = mapButtonCheckbox.get(btCheckBox);
				Button buttonRadio = null;
				boolean flag = false;
				for (int i = 0; i < listButton.size(); i++) {
					if(valueTextBt.equals(listButton.get(i).getText())) {
						buttonRadio = listButton.get(i);
						break;
					}
				}
				
				if(btCheckBox.getSelection()) {
					if(!listButtonCheckboxPreferencePage.contains(valueTextBt)){
						listButtonCheckboxPreferencePage.add(valueTextBt);
					}
					flag = true;
				} else {
					if(listButtonCheckboxPreferencePage.contains(valueTextBt)) {
						listButtonCheckboxPreferencePage.remove(valueTextBt);
					}	
					
					for (int i = 0; i < listButton.size(); i++) {
						Button btRadio = listButton.get(i);
						if(btRadio.getText().equals(valuePropertieCommentEditions)) {
							btRadio.setSelection(false);
							listButton.get(0).setSelection(true);
							valuePropertieCommentEditions = listButton.get(0).getText(); 
							
							getPartiPathEdition(nameFileReport.get(valuePropertieCommentEditions));
				
							fieldPathEditionDefaut.setStringValue(remplaceBackSlashAndSlash(
									new File(valuePropertiePathEditionDefaut).getPath()));
							break;
						}
					}
					flag = false;
				}
				buttonRadio.setEnabled(flag);
			}
			
		});
	}
	
	public void showCommentsEditionAndactionButton(final Button buttonChoixEdition,
					   final SwtReportWithExpandbar objetSwtCompositeReport,final String comments,
					   final File fileEdition){
		
		buttonChoixEdition.addSelectionListener(new SelectionListener(){

			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);
				
			}

			public void widgetSelected(SelectionEvent e) {
				
				actionBtRadionEdition(buttonChoixEdition,comments,objetSwtCompositeReport,
					   fileEdition);
			}
			
		});
	}
	
	public void actionBtRadionEdition(Button buttonChoixEdition,String comments,
			SwtReportWithExpandbar objetSwtCompositeReport,
			File fileEdition){
		
		if(buttonChoixEdition.getSelection()){
			objetSwtCompositeReport.getTextCommentsEdition().setText(comments);

			String nameBt = buttonChoixEdition.getText();
			if(nameBt.startsWith(COMMENT_LIST_EDITION_DEFAUT)) {
				flagListFicheEdition = true;
			} else {
				flagListFicheEdition = false;
			}
			nameFileEditionSelected = fileEdition.getName();
			pathFileEditionSelected = fileEdition.getAbsolutePath();
			nameButtonSelected = comments;
		}
	}
	
	protected void initImageBouton(SwtReportWithExpandbar dialog) {
		//TODO constante definie en double dans LibrairiesEcranPlugin, a changer
		final String C_IMAGE_IMPRIMER = SEPARATOR+"icons"+SEPARATOR+"printer.png";
		final String C_IMAGE_ANNULER = SEPARATOR+"icons"+SEPARATOR+"cancel.png";
		if(dialog instanceof SwtReportWithExpandbar) {
			dialog.getButtonAnnulerPrint().setImage(LibrairiesEcranPlugin.getImageDescriptor(C_IMAGE_ANNULER).createImage());
			dialog.getButtonValiderPrint().setImage(LibrairiesEcranPlugin.getImageDescriptor(C_IMAGE_IMPRIMER).createImage());
			dialog.getButtonEmail().setImage(LibrairiesEcranPlugin.ir.get(LibrairiesEcranPlugin.IMAGE_EMAIL));
			dialog.getButtonFax().setImage(LibrairiesEcranPlugin.ir.get(LibrairiesEcranPlugin.IMAGE_GENERE));
			dialog.layout(true);
		}
	}
	
	public void initCommentaire(SwtReportWithExpandbar dialog){
		dialog.getTextCommentsEdition().setText(valuePropertieCommentEditions);
	}
		
	/**
	 * Pour make folder for stocker l'edition dynamique
	 * @param folderEdition ==> path de folder for stocker l'edition dynamique 
	 * @return
	 */
	public File makeFolderEditions(String folderEdition){
		File edition = new File(folderEdition);
		try {
			
			if(!edition.exists()) {
				edition.mkdirs();
			}
			
		} catch (Exception e) {
			logger.error("",e);
		}
		return edition;
	}
	
	public String obtainSubString(String QueryStart,String QueryMiddle,
			String QueryEnd,String stringTotal) {
		
		String querySql = null;
		String UpperCaseStringTotal = stringTotal.toUpperCase();
		int indexString = UpperCaseStringTotal.indexOf(PART_WHERE);
		if(indexString !=0 ) {
			
			String QueryWhere = stringTotal.substring(indexString);
			querySql = QueryStart+QueryMiddle+QueryEnd+SYMBOL_SPACE+QueryWhere+SYMBOL_SEMICOLON;
		} else {
			querySql = QueryStart+QueryMiddle+QueryEnd+SYMBOL_SEMICOLON;
		}
		return querySql;
	}
	
	public File findPathReportPlugin(Bundle bundle,String reportPlugin,String sousReportPlugin){
		
		String reportFileLocation = reportPlugin;
		
		URL urlReportFile;
		URI uriReportFile = null;
		try {
			urlReportFile = FileLocator.find(bundle,new Path(reportFileLocation),null);
			urlReportFile = FileLocator.toFileURL(urlReportFile);
			//urlReportFile = Platform.asLocalURL(bundle.getEntry(reportFileLocation));
			try {
				uriReportFile = new URI("file", urlReportFile.getAuthority(),
						urlReportFile.getPath(), urlReportFile.getQuery(), null);
			} catch (URISyntaxException e) {
				logger.error("",e);
			}
		} catch (IOException e) {
			logger.error("",e);
		}
		File pathReport = new File(uriReportFile);
		String pathFileEdition = pathReport.toString()+sousReportPlugin;
		return new File(pathFileEdition);
	}
	
	public void addValues(LinkedList<Integer> idEntity,LinkedList<Integer> oneIDEntity){
		id = idEntity;
		idOne = oneIDEntity;
	}
	

	/**
	 * pour trouver des relations entre champs interface et attributes JPA
	 * 
	 * EX :
	 * nameTableEcran ==> les noms de champs EX : Code article, Libelle, Compte, Prix HT, Unite ..
	 * nameTableBDD ==> les attributes de JPA EX : codeArticle, libellecArticle, numcptArticle, prixPrix, codeUnite
	 */
	public String addValueList(LgrTableViewer tableViewer,String nameClass){
		//#JPA
		/*
		 * Le nom des champ dans le fichier ListeChampGrille.properties utilise pour JPA le nom des attributs des classes (minuscule)
		 * Les editions utilise encore le SQL, il faut donc pour un meme champ avec 2 cles, une classqie pour JPA et la meme avec cleChampSQL en plus pour les editions
		 * Exemple :
		 * #JPA
		 * SWTPaUniteController.codeUnite=Unite;100
		 * SWTPaUniteController.liblUnite=Libelle;200
		 * 
         * #SQL
		 * SWTPaUniteControllerEDITIONSQL.CODE_UNITE=Unite;100
		 * SWTPaUniteControllerEDITIONSQL.LIBL_UNITE=Libelle;200
		 */
//		String cleChampSQL="_EDITIONSQL";
		List<String> res =new ArrayList<String>();
		org.apache.commons.configuration.SubsetConfiguration propertie = null;
//		propertie = new org.apache.commons.configuration.SubsetConfiguration(
//				FonctionGetInfosXmlAndProperties.listeChampEditionDynamique,nameClass,ConstEdition.SEPARATEUR_COMMA);
		propertie = new org.apache.commons.configuration.SubsetConfiguration(
				LgrTableViewer.getListeChampGrille(),nameClass,ConstEdition.SEPARATEUR_COMMA);
		
		if (!propertie.isEmpty()) {
			for (Iterator iter = propertie.getKeys(); iter.hasNext();) {
				res.add(iter.next().toString());
			}
		}
		
		String[] retour = new String[res.size()];
		String sqlQueryMiddle = "";
		String valueKey = null;
		for (int i = 0; i < res.size(); i++) {
			retour[i]=res.get(i).toString();
			nameTableBDD.add(retour[i]);
			valueKey = LgrTableViewer.getListeChampGrille().getString(nameClass+ConstEdition.SEPARATEUR_COMMA+retour[i]);
//			valueKey = FonctionGetInfosXmlAndProperties.listeChampEditionDynamique.getString(nameClass+ConstEdition.SEPARATEUR_COMMA+retour[i]);
			valueKey = valueKey.substring(0,valueKey.indexOf(";"));
			nameTableEcran.add(valueKey);
		}
		return sqlQueryMiddle;
	}

	/**
	 * for find name of ExpandItem
	 * 
	 * flag ==> si false, entity n'est pas document.
	 * 			si true, entity est document.
	 */
	public void fillMapNameExpandbar(boolean flag) {
		mapNameExpandbar.clear();
		mapIconExpandItem.clear();
		
		mapIconExpandItem.put("Editions", "/icons/application.png");
	
		if(!flag){
			mapNameExpandbar.put("TaFacture", "Edition Facture");
			mapNameExpandbar.put("TaApporteur", "Edition Apporteur");
			mapNameExpandbar.put("TaBonliv", "Edition Bon de livraison");
			mapNameExpandbar.put("TaBoncde", "Edition Bon de Commande");
			mapNameExpandbar.put("TaAvoir", "Edition Avoir");
			mapNameExpandbar.put("TaDevis", "Edition Devis");
			mapNameExpandbar.put("TaProforma", "Edition Proforma");
			mapNameExpandbar.put("TaAcompte", "Edition Acompte");
			mapNameExpandbar.put("TaPrevelevement", "Edition Prelevement");
			
			mapIconExpandItem.put("Edition Facture", "/icons/table_add.png");
			mapIconExpandItem.put("Edition Apporteur", "/icons/creditcards.png");
			mapIconExpandItem.put("Edition Bon de livraison", "/icons/lorry.png");
			mapIconExpandItem.put("Edition Bon de Commande", "/icons/calculator_edit.png");
			mapIconExpandItem.put("Edition Avoir", "/icons/table.png");
			mapIconExpandItem.put("Edition Devis", "/icons/calculator_edit.png");
			mapIconExpandItem.put("Edition Proforma", "/icons/calculator_edit.png");
			mapIconExpandItem.put("Edition Acompte", "/icons/money.png");
			mapIconExpandItem.put("Edition Prelevement", "/icons/money.png");
			
		}else{
			mapNameExpandbar.put("Facture", "Edition Facture");
			mapIconExpandItem.put("Edition Facture", "/icons/table_add.png");
			mapNameExpandbar.put("BonLivraison", "Edition BonLiv");
			mapIconExpandItem.put("Edition BonLiv", "/icons/lorry.png");
			mapNameExpandbar.put("Devis", "Edition Devis");
			mapIconExpandItem.put("Edition Devis", "/icons/calculator_edit.png");
			mapNameExpandbar.put("Apporteur", "Edition Apporteur");
			mapIconExpandItem.put("Edition Apporteur", "/icons/creditcards.png");
			mapNameExpandbar.put("Avoir", "Edition Avoir");
			mapIconExpandItem.put("Edition Avoir", "/icons/table.png");
			mapNameExpandbar.put("Boncde", "Edition Bon de Commande");
			mapIconExpandItem.put("Edition Bon de Commande", "/icons/calculator_edit.png");
			mapNameExpandbar.put("Proforma", "Edition Proforma");
			mapIconExpandItem.put("Edition Proforma", "/icons/calculator_edit.png");
			mapNameExpandbar.put("Acompte", "Edition Acompte");
			mapIconExpandItem.put("Edition Acompte", "/icons/money.png");
			mapNameExpandbar.put("Prelevement", "Edition Prelevement");
			mapIconExpandItem.put("Edition Prelevement", "/icons/money.png");
		}
	}
	
	public File getPATH_FICHE_FILE_REPORT_ARTICLE() {
		return PATH_FICHE_FILE_REPORT;
	}

	public void setPATH_FICHE_FILE_REPORT_ARTICLE(
			File path_fiche_file_report_article) {
		PATH_FICHE_FILE_REPORT = path_fiche_file_report_article;
	}

	public Button getFicheTotal() {
		return ficheTotal;
	}

	public void setFicheTotal(Button ficheTotal) {
		this.ficheTotal = ficheTotal;
	}

	public LinkedList<Integer> getId() {
		return id;
	}

	public void setId(LinkedList<Integer> id) {
		this.id = id;
	}

	public String getPARAM_ID_TABLE() {
		return PARAM_ID_TABLE;
	}

	public void setPARAM_ID_TABLE(String param_id_table) {
		PARAM_ID_TABLE = param_id_table;
	}

	public ArrayList<String> getNameTableEcran() {
		return nameTableEcran;
	}

	public void setNameTableEcran(ArrayList<String> nameTableEcran) {
		this.nameTableEcran = nameTableEcran;
	}

	public ArrayList<String> getNameTableBDD() {
		return nameTableBDD;
	}

	public void setNameTableBDD(ArrayList<String> nameTableBDD) {
		this.nameTableBDD = nameTableBDD;
	}

//	public LinkedList<Integer> findIdTable(LinkedList<ModelObject> SWTtable){
//		LinkedList<Integer> idTable=null;
//		for (Object objectSWTtable : SWTtable) {
//			idTable.add(objectSWTtable.)
//		}
//		return idTable;
//	}
	
	public String pathEditionAImprimer(String path,String namePlugin){
		String pathEdition = null;
		Bundle bundleEditions = Platform.getBundle(namePlugin);
		
		URL urlReportFile;
		urlReportFile = FileLocator.find(bundleEditions,new Path(path),null);
		if(urlReportFile!=null){
			try {
				urlReportFile = FileLocator.toFileURL(urlReportFile);
				
				URI uriReportFile = new URI("file", urlReportFile.getAuthority(),
						urlReportFile.getPath(), urlReportFile.getQuery(), null);
				File reportFileEdition = new File(uriReportFile);
				
				pathEdition = reportFileEdition.getAbsolutePath();
			} catch (Exception e) {
				logger.error("", e);
			}
		}
		return pathEdition;
	}
	
	public static String pathFichierEditionsSpecifiques(String path,String namePlugin){
		String tmp = new Path(ConstEdition.FOLDER_REPORT_PLUGIN).toOSString();
		Bundle bundleEditions=null;
		if(new Path(path).toOSString().contains(tmp))
			bundleEditions = Platform.getBundle(namePlugin);
		else	
			bundleEditions = Platform.getBundle(Const.PLUGIN_EDITIONSPECIFIQUES);
		if(bundleEditions==null)return null;
		URL urlReportFile;
		try {
			
		urlReportFile = FileLocator.find(bundleEditions,new Path(path),null);
		if(urlReportFile!=null){
			urlReportFile = FileLocator.toFileURL(urlReportFile);

			URI uriReportFile = new URI("file", urlReportFile.getAuthority(),
					urlReportFile.getPath(), urlReportFile.getQuery(), null);
			File reportFileEdition = new File(uriReportFile);
			return reportFileEdition.getAbsolutePath();
		}else{
			return null;
		}
	
		} catch (Exception e) {
			logger.error("",e);
			return null;
		}		
	}
	
	public static String pathRepertoireEditionsSpecifiquesClient(){
		Bundle bundleEditions = Platform.getBundle("EditionsSpecifiques");
		if(bundleEditions==null)return null;
		URL urlReportFile;
		try {

			urlReportFile = FileLocator.find(bundleEditions,new Path(FOLDER_EDITION_CLIENT),null);
			if(urlReportFile!=null) {
				urlReportFile = FileLocator.toFileURL(urlReportFile);

				URI uriReportFile = new URI("file", urlReportFile.getAuthority(),
						urlReportFile.getPath(), urlReportFile.getQuery(), null);
				File reportFileEdition = new File(uriReportFile);
				return reportFileEdition.getAbsolutePath();
			} else {
				/*
				 * il n'a y pas fragement pour client 
				 */
				return null;
			}
		} catch (Exception e) {
			logger.error("",e);
			return null;
		}		
	}	
	
	public static String pathRepertoireEditionsSpecifiques(){
		Bundle bundleEditions = Platform.getBundle("EditionsSpecifiques");
		if(bundleEditions==null)return null;
		URL urlReportFile;
		try {
		urlReportFile = FileLocator.find(bundleEditions,new Path(FOLDER_EDITION),null);
		urlReportFile = FileLocator.toFileURL(urlReportFile);
		
		URI uriReportFile = new URI("file", urlReportFile.getAuthority(),
				urlReportFile.getPath(), urlReportFile.getQuery(), null);
		File reportFileEdition = new File(uriReportFile);
		return reportFileEdition.getAbsolutePath();
		} catch (Exception e) {
			logger.error("",e);
			return null;
		}		
	}
	
	/**
	 * Utiliser pour l'édition des "Types" (type article, famille, ...)
	 * Dans ce cas, on ne propose jamais la fenetre pour choisir l'edition.
	 * Seule l'édition par defaut/dynamique est disponible
	 * @param pathFileEdition - chemin ou sera stockée l'édition dynamique
	 * @param nameOnglet
	 */
	public void afficheEditionDynamiqueDirect(AffichageEdition affichageEdition,String pathFileEdition,String nameOnglet){
		
//		ImprimeObjet.clearListAndMap();
//		ViewerPlugin.getDefault().getPluginPreferences().setValue(WebViewer.APPCONTEXT_EXTENSION_KEY, EditionAppContext.APP_CONTEXT_NAME);
		
		String fromatFileReport = FORMAT_PDF;
		Boolean extraction = false;
		
		List<Object> list = new LinkedList<Object>();
		Iterator it = collection.iterator(); 
		while (it.hasNext()) {
			Object object = it.next();
			ImprimeObjet.l.add(object);
			list.add(object);
		}
		
		ImprimeObjet.m.put(objectEntity.getClass().getSimpleName(), list);
				
		affichageEdition.showEditionDynamiqueDefautThread(pathFileEdition,nameOnglet,
				fromatFileReport,extraction);
	}
	
	/**
	 * @param pathFileReport
	 * @param titleOngletEdition
	 * @param reportParam
	 */
	public void afficheEditionDirect(final String pathFileReport,String titleOngletEdition,HashMap<String,String> reportParam,AffichageEdition affichageEdition){
		
		if(affichageEdition !=null){
//			ViewerPlugin.getDefault().getPluginPreferences().setValue(WebViewer.APPCONTEXT_EXTENSION_KEY, EditionAppContext.APP_CONTEXT_NAME);
			String fromatFileReport = FORMAT_PDF;
			Boolean extraction = false;
	
			affichageEdition.setCheminFichierEditionBirt(pathFileReport);
			
			List<Object> list = new LinkedList<Object>();
			list.add(objectEntity);
			ImprimeObjet.m.put(objectEntity.getClass().getSimpleName(), list);
			ImprimeObjet.l.add(objectEntity);

			HashMap<String,String> reportParams = addMapParametre(reportParam,PARAM_ID_TABLE);

			String param =makeStringEditionParamtre(reportParams);

			affichageEdition.newImprimerThreadFicheEditon(pathFileReport, titleOngletEdition, 
					fromatFileReport, extraction, param);
		}
	}
	
	/**
	 * Declenchement de l'edition (BIRT) en fonction des choix fait dans la fenetre
	 * @param objetSwtCompositeReport - ecran de selection de l'edition
	 * @param nomPlugin 
	 * @param titleOngletEdition
	 * @param shell
	 * @param repertoireReport
	 * @param reportParam
	 */
	public void commandImpressionExpandbar(final SwtReportWithExpandbar objetSwtCompositeReport,
			final String nomPlugin,String titleOngletEdition,
			final Shell shell,File repertoireReport,HashMap<String,String> reportParam){
		
		String Title = titleOngletEdition;
		String formatFileReport = null;
		Boolean extraction = false;

		if(objetSwtCompositeReport.getButtonRadioPDF().getSelection()){
			formatFileReport = FORMAT_PDF;
		}else if (objetSwtCompositeReport.getButtonRadioHTML().getSelection()) {
			formatFileReport = FORMAT_HTML;
		}else if(objetSwtCompositeReport.getButtonRadioDOC().getSelection()){
			formatFileReport = FORMAT_DOC;
		}else if(objetSwtCompositeReport.getButtonRadioXLS().getSelection()){
			formatFileReport = FORMAT_XLS;
		}else{
			formatFileReport = FORMAT_EXTRACTION;
			extraction = true;
		}

		String fileEdition = null;
		//String Title = "Edition "+nomPlugin;
//		AffichageEdition affichageEdition = new AffichageEdition();
//		affichageEdition.setAppContextEdition(new EditionAppContext().getExtensionMap());

		affichageEdition.setCheminFichierEditionBirt(pathFileEditionSelected);
		

		//ImprimeObjet.m.put("TaInfoEntreprise",taInfoEntreprise);
		
		/**
		 * Button list defaut edition 
		 */
		List<Object> listEntity = new LinkedList<Object>();// stocker tous les objects pour l'edition 
		fileEdition = pathFileEditionSelected;
		//ImprimeObjet.m.put(objectEntity.getClass().getSimpleName(), listEntity);
		ImprimeObjet.m.put(nameEntity, listEntity);
		
		HashMap<String,String> reportParams = addMapParametre(reportParam,PARAM_ID_TABLE);
		
		if(affichageEdition.isFlagPrint()){
			//impression directe
			Collection collectionEntity = new LinkedList();
			
			String commentaireListEdition = ConstEdition.COMMENT_LIST_EDITION_DEFAUT+nomPlugin;
			if(nameButtonSelected.equals(commentaireListEdition) || flagListFicheEdition || flagListFicheEditionDocument) {
				collectionEntity = collection;
			
			} else if(new File(pathFileEditionSelected).getName().startsWith(ConstEdition.START_V)) {
				AffichageEdition.listEditionDynamique.clear();
				Iterator it = collection.iterator(); 
				while (it.hasNext()) {
					Object object = it.next();
					AffichageEdition.listEditionDynamique.add(object);
				}
			} else {
				collectionEntity.add(this.objectEntity);
			}
			affichageEdition.imprimerThreadAllFiche(collectionEntity,true,reportParams,PARAM_ID_TABLE);
			
		}else{
			if(nameFileEditionSelected.startsWith(ConstEdition.START_V)){
				//edition dynamique d'une liste d'élémnent
				Iterator it = collection.iterator(); 
				while (it.hasNext()) {
					Object object = it.next();
					ImprimeObjet.l.add(object);
					listEntity.add(object);
				}
				affichageEdition.showEditionDynamiqueDefautThread(fileEdition,Title,formatFileReport,extraction);
			}else{
				if(flagListFicheEdition || flagListFicheEditionDocument){
					affichageEdition.imprimerThreadAllFiche(collection,true,reportParams,PARAM_ID_TABLE);
				}else{
					
					listEntity.add(objectEntity);
					ImprimeObjet.l.add(objectEntity);
					affichageEdition.setParametreEditionIdDocument(PARAM_ID_TABLE);
					affichageEdition.setIdDocument(idOne.get(0));
					String param =makeStringEditionParamtre(reportParams);

					affichageEdition.newImprimerThreadFicheEditon(fileEdition,Title,formatFileReport,
							extraction,param);
				}
			}
		}
		shell.close();

	}
	
	public  HashMap<String,String> addMapParametre(HashMap<String,String> reportParam,String PARAM_ID_TABLE){
		//HashMap<String, String> mapReportParam = new HashMap<String, String>();
		
		TaInfoEntrepriseDAO taInfoEntrepriseDAO = new TaInfoEntrepriseDAO();
		TaInfoEntreprise taInfoEntreprise = taInfoEntrepriseDAO.findInstance();
		//reportParam.put("paramUrlJDBC",IB_APPLICATION.findDatabase().getConnection().getConnectionURL());
		reportParam.put("capital",taInfoEntreprise.getCapitalInfoEntreprise());
		reportParam.put("ape",taInfoEntreprise.getApeInfoEntreprise());
		reportParam.put("siret",taInfoEntreprise.getSiretInfoEntreprise());
		reportParam.put("rcs",taInfoEntreprise.getRcsInfoEntreprise());
		reportParam.put("nomEntreprise",taInfoEntreprise.getNomInfoEntreprise());
//		reportParam.put("paramID_DOC",PARAM_ID_TABLE);
		
//		return mapReportParam;
		return reportParam;
	}
	
	/**
	 * Genere a partir des parametres une chaine qui pourra etre utilise dans l'url BIRT de l'editon. <br>
	 * Ex : &param1=aaa&param2=bbb&param3=ccc
	 * @param reportParam ==> les parametres de l'edition
	 * @return
	 */
	public String makeStringEditionParamtre(HashMap<String,String> reportParam){
		String param = "";
		if(reportParam!=null) {
			for (Iterator<String> iterator = reportParam.keySet().iterator(); iterator.hasNext();) {
				String key = (String) iterator.next();
				param=param.concat("&"+key+"="+reportParam.get(key));
			}
		}
		return param;
	}
		
	/**
	 * Listener bouton OK, fenetre selection des editions
	 */
	public class  EcranChoixEditionSelectionListener implements SelectionListener{
		
		private String Title = null;
		
		public EcranChoixEditionSelectionListener(SwtReportWithExpandbar reportWithExpandbar
		,String pathFileReport, String nomPlugin, Shell shell,File repertoireReport,String nomOnglet ){
			
			setShell(shell);
			setSwtReportWithExpandbar(reportWithExpandbar);
			setNomPlugin(nomPlugin);
			setPathFileReport(pathFileReport);
			setRepertoireReport(repertoireReport);
			this.Title = nomOnglet;
		}

		public void widgetDefaultSelected(SelectionEvent e) {
			widgetSelected(e);
		}

		public void widgetSelected(SelectionEvent e) {
			commandImpressionExpandbar(swtReportWithExpandbar, getNomPlugin(),Title,
					getShell(), getRepertoireReport(),getReportParamLoc());
		}

	}
	
	public static void addCommentToEditionDefaut(Button buttonEditionDefaut,boolean flag,String partiValue1,
			String partiValue2){
		if(flag){
			buttonEditionDefaut.setText(partiValue1+partiValue2);
		}
	}
	
	/**
	 * 
	 * @param nameEntity ==> type de Entity EX : UNITE, TYPE ADRESSE  
	 * @return
	 */
	public static boolean findElement(String nameEntity,String[] typeEntity){
		boolean flag = false;
		for (int i = 0; i < typeEntity .length; i++) {
			if(typeEntity[i].equalsIgnoreCase(nameEntity)){
				flag = true;
				break;
			}
		}
		return flag;
	}
	
	public SwtCompositeReport_new getObjetSwtCompositeReport() {
		return objetSwtCompositeReport;
	}

	public void setObjetSwtCompositeReport(
			SwtCompositeReport_new objetSwtCompositeReport) {
		this.objetSwtCompositeReport = objetSwtCompositeReport;
	}

	public String getPathFileReport() {
		return pathFileReport;
	}

	public void setPathFileReport(String pathFileReport) {
		this.pathFileReport = pathFileReport;
	}

	public String getNomPlugin() {
		return nomPlugin;
	}

	public void setNomPlugin(String nomPlugin) {
		this.nomPlugin = nomPlugin;
	}

	public Shell getShell() {
		return shell;
	}

	public void setShell(Shell shell) {
		this.shell = shell;
	}

	public File getRepertoireReport() {
		return repertoireReport;
	}

	public void setRepertoireReport(File repertoireReport) {
		this.repertoireReport = repertoireReport;
	}

	public HashMap<String, String> getReportParamLoc() {
		return reportParamLoc;
	}

	public void setReportParamLoc(HashMap<String, String> reportParamLoc) {
		this.reportParamLoc = reportParamLoc;
	}


	public void setCollection(Collection collection) {
		this.collection = collection;
	}


	public void setIdOne(LinkedList<Integer> idOne) {
		this.idOne = idOne;
	}


	public void setNomChampIdTable(String nomChampIdTable) {
		this.nomChampIdTable = nomChampIdTable;
	}


	public void setObjectEntity(Object objectEntity) {
		this.objectEntity = objectEntity;
	}


	public void setFlagEditionMultiple(boolean flagDocument) {
		this.flagEditionMultiple = flagDocument;
	}


	public boolean isFlagButtonOneFiche() {
		return flagButtonOneFiche;
	}


	public void setFlagButtonOneFiche(boolean flagButtonOneFiche) {
		this.flagButtonOneFiche = flagButtonOneFiche;
	}


	public TaInfoEntreprise getTaInfoEntreprise() {
		return taInfoEntreprise;
	}


	public void setTaInfoEntreprise(TaInfoEntreprise taInfoEntreprise) {
		this.taInfoEntreprise = taInfoEntreprise;
	}


	public String getNameEntity() {
		return nameEntity;
	}


	public void setNameEntity(String nameEntity) {
		this.nameEntity = nameEntity;
	}


	public Map<String, ExpandItem> getMapExpandItem() {
		return mapExpandItem;
	}


	public void setMapExpandItem(Map<String, ExpandItem> mapExpandItem) {
		this.mapExpandItem = mapExpandItem;
	}


	public SwtReportWithExpandbar getSwtReportWithExpandbar() {
		return swtReportWithExpandbar;
	}


	public void setSwtReportWithExpandbar(
			SwtReportWithExpandbar swtReportWithExpandbar) {
		this.swtReportWithExpandbar = swtReportWithExpandbar;
	}


	public String getNameFileEditionSelected() {
		return nameFileEditionSelected;
	}


	public void setNameFileEditionSelected(String nameFileEditionSelected) {
		this.nameFileEditionSelected = nameFileEditionSelected;
	}


	public String getPathFileEditionSelected() {
		return pathFileEditionSelected;
	}


	public void setPathFileEditionSelected(String pathFileEditionSelected) {
		this.pathFileEditionSelected = pathFileEditionSelected;
	}


	public AffichageEdition getAffichageEdition() {
		return affichageEdition;
	}


	public void setAffichageEdition(AffichageEdition affichageEdition) {
		this.affichageEdition = affichageEdition;
	}


	public LinkedHashMap<String, LinkedHashMap<String, String>> getMapExpandItemButton() {
		return mapExpandItemButton;
	}


	public void setMapExpandItemButton(
			LinkedHashMap<String, LinkedHashMap<String, String>> mapExpandItemButton) {
		this.mapExpandItemButton = mapExpandItemButton;
	}
	
//	public List<Object> getListObjetEdition(Collection<Object> collection){
//		List<Object> list = new LinkedList<Object>();
//		
//		Iterator it = collection.iterator(); 
//		while (it.hasNext()) {
//			ImprimeObjet.l.add(it.next());
//			list.add(it.next());
//		}
//		
//		return list;
//	}
	
	/** 28/12/2009 **/
	public void cleanMapAndList(){
	
		listButton.clear();
//		listButtonCheckbox.clear();
		nameFileReport.clear();
		mapExpandItemButton.clear();
		mapButtonCheckbox.clear();
		mapFileEditionandPlugin.clear();
	}
	
	/** 05/01/2010 add **/
	/**
	 * save, after update Propriety of PreferencePage, 
	 */
	public void saveProprietyPreferencPage(){
		valuePropertieChoixEditions = makePropertiesValueChoixEdition(listButtonCheckboxPreferencePage);
		valuePropertieNamePlugin = mapFileEditionandPlugin.get(valuePropertieCommentEditions);
//		Properties props = new Properties();
		Properties props = PropertiesFilePreference.getProperties();
//		InputStream in;
		FileOutputStream out;
		try {

			props.setProperty(propertiesChoixEdition,valuePropertieChoixEditions);
			props.setProperty(propertiesCommentDefautEdition,valuePropertieCommentEditions);
			props.setProperty(propertiesPathEditiondefaut,valuePropertiePathEditionDefaut);
			props.setProperty(propertiesDefautEditionPlugin,valuePropertieNamePlugin);
			
			out = new FileOutputStream(Const.C_FICHIER_PREFERENCE_PAGE);
			props.store(out,null);
			out.close();
//			ConstEdition.listButtonCheckboxPreferencePage.clear();
		} catch (Exception e) {
			logger.error("",e);
		}
	}
		
	/**
	 * afficher les editions 
	 * @param namePlugin
	 * @param nomOnglet
	 * @param reportParam
	 * @param flagListImpression ==> si true  : pour touts les documents
	 * 							 ==> si false : pour TaArticle, TaTiers   
	 */
	public void editionDirect(String namePlugin,String nomOnglet,HashMap<String,String> reportParam,
							  boolean flagListImpression,boolean flagPrint,boolean afficheEditionAImprimer,
							  String pathFileAImprimer,String pathAdobeReader,String nameEtnity,
							  String nameDynamiqueEdition,boolean flagListDocument,String editionDefaut){

		String pathFilePreferencePage = new File(Const.C_FICHIER_PREFERENCE_PAGE).getPath();
		logger.debug("pathFilePreferencePage :"+ pathFilePreferencePage);
		Properties props = PropertiesFilePreference.getProperties();
		InputStream in;
		try {
			in = new BufferedInputStream(new FileInputStream(pathFilePreferencePage));
			props.load(in);
			in.close();
				
			String commentaireListEdition = ConstEdition.COMMENT_LIST_EDITION_DEFAUT+namePlugin;
			String valuePropertiesCommentaireEdition = null;
			String defautPathFileReportEdition = props.getProperty(ConstEdition.PROPERTIES_PATH_EDITIONDEFAUT+namePlugin);
			String defautPluginEdition = props.getProperty(ConstEdition.PROPERTIES_PLUGIN_EDITIONDEFAUT+namePlugin);
			if(defautPathFileReportEdition == null){
				pathFileReport = pathFichierEditionsSpecifiques(editionDefaut,namePlugin);
			}else{
				File filePathFileReport = new File(defautPathFileReportEdition);
				String nameFile = filePathFileReport.getName();
				if(nameFile.startsWith(START_V)){
					pathFileReport = new File(Const.C_RCP_INSTANCE_LOCATION+SEPARATOR+Const.C_NOM_PROJET_TMP+SEPARATOR+nameEtnity+
								  //SEPARATOR+nameDynamiqueEdition+".rptdesign").getPath();
								  SEPARATOR+nameFile).getPath();
				}else if(defautPluginEdition.equals(Const.FOLDER_EDITION_SUPP)){
//					pathFileReport = Const.C_REPERTOIRE_BASE+Const.C_REPERTOIRE_PROJET+
//									ConstEdition.SEPARATOR+defautPathFileReportEdition;
					String folderEditionsSupp = new File(new File(Const.C_FICHIER_PREFERENCE_PAGE).
							getParent()).getParent();
					pathFileReport = folderEditionsSupp+props.getProperty(PROPERTIES_PATH_EDITIONDEFAUT+namePlugin);
				}else{
					pathFileReport = pathEditionAImprimer(defautPathFileReportEdition,
							defautPluginEdition);
				}
				valuePropertiesCommentaireEdition = props.getProperty(ConstEdition.PROPERTIES_COMMENT_DEFAUT_EDITION+
						namePlugin);
			}
			
//			String pathFileReport =  props.getProperty(ConstEdition.PROPERTIES_PATH_EDITION+namePlugin);
			logger.debug("pathFileReport :"+ pathFileReport);
			
			if(!(new File(pathFileReport).exists())){
				pathFileReport = pathFichierEditionsSpecifiques(editionDefaut,namePlugin);
			}
			String nameFile = new File(pathFileReport).getName();
					
			logger.debug("nameFile :"+ nameFile);
			
			AffichageEdition affichageEdition = new AffichageEdition(this.entityManager);
			HashMap<String,String> reportParams = null;
			
			/** 08/02/2010 **/
			affichageEdition.setFlagPrint(flagPrint);
			affichageEdition.setAfficheEditionAImprimer(afficheEditionAImprimer);
			affichageEdition.setRepertoireStockagePDFGenere(pathFileAImprimer);
			affichageEdition.setPathAdobeReader(pathAdobeReader);
			/** 17/03/2010 **/
			affichageEdition.setParametreEditionIdDocument(PARAM_ID_TABLE);
			if(idOne!=null)
				affichageEdition.setIdDocument(idOne.get(0));
			
			//if(flagListImpression){
			if(flagPrint){
				
				affichageEdition.setAppContextEdition(new EditionAppContext().getExtensionMap());
				affichageEdition.setCheminFichierEditionBirt(pathFileReport);
				reportParams = addMapParametre(reportParam,PARAM_ID_TABLE);
//				String PARAM_ID_TABLE = constEdition.getPARAM_ID_TABLE();

				Collection collectionEntity = new LinkedList();

				if(flagListDocument){
					collectionEntity = collection;
				}else{
					if(commentaireListEdition.equals(valuePropertiesCommentaireEdition)){
						collectionEntity = collection;
					}
					else{
						AffichageEdition.listEditionDynamique.clear();
						Iterator it = collection.iterator(); 
						while (it.hasNext()) {
							Object object = it.next();
							AffichageEdition.listEditionDynamique.add(object);
						}
						collectionEntity.add(this.objectEntity);
					}
				}

				affichageEdition.imprimerThreadAllFiche(collectionEntity,true,reportParams,PARAM_ID_TABLE);
				
			}else{
				if(commentaireListEdition.equals(valuePropertiesCommentaireEdition) || 
						flagListFicheEditionDocument){
					
					affichageEdition.setAppContextEdition(new EditionAppContext().getExtensionMap());
					affichageEdition.setCheminFichierEditionBirt(pathFileReport);
//					String PARAM_ID_TABLE = constEdition.getPARAM_ID_TABLE();
					reportParams = addMapParametre(reportParam,PARAM_ID_TABLE);
					affichageEdition.imprimerThreadAllFiche(collection,true,reportParams,PARAM_ID_TABLE);
				}
				else if(nameFile.startsWith(ConstEdition.START_V)){
					afficheEditionDynamiqueDirect(affichageEdition,pathFileReport, nomOnglet);
				}
				else{
					reportParams = addMapParametre(reportParam,PARAM_ID_TABLE);
					afficheEditionDirect(pathFileReport, nomOnglet, reportParam,affichageEdition);
				}
			}
		} catch (Exception e) {
			logger.error("",e);
		}
	}
	
	/*****17/02/2010******/
	public static String remplaceBackSlashAndSlash(String path){
		return path.replaceAll("\\\\", "/");
	}
	
	public String getCommentEditionDynamique() {
		return commentEditionDynamique;
	}

	public void setCommentEditionDynamique(String commentEditionDynamique) {
		this.commentEditionDynamique = commentEditionDynamique;
	}


	public StringButtonFieldEditorLgr getFieldPathEditionDefaut() {
		return fieldPathEditionDefaut;
	}

	public void setFieldPathEditionDefaut(
			StringButtonFieldEditorLgr fieldPathEditionDefaut) {
		this.fieldPathEditionDefaut = fieldPathEditionDefaut;
	}

	public static LinkedList<Button> getListButton() {
		return listButton;
	}

	public static void setListButton(LinkedList<Button> listButton) {
		ConstEdition.listButton = listButton;
	}

	public boolean isFlagListFicheEdition() {
		return flagListFicheEdition;
	}

	public void setFlagListFicheEdition(boolean flagListFicheEdition) {
		this.flagListFicheEdition = flagListFicheEdition;
	}

	public boolean isFlagListFicheEditionDocument() {
		return flagListFicheEditionDocument;
	}

	public void setFlagListFicheEditionDocument(boolean flagListFicheEditionDocument) {
		this.flagListFicheEditionDocument = flagListFicheEditionDocument;
	}

	public Map<String, String> getMapFileEditionandPlugin() {
		return mapFileEditionandPlugin;
	}

	public void setMapFileEditionandPlugin(
			Map<String, String> mapFileEditionandPlugin) {
		this.mapFileEditionandPlugin = mapFileEditionandPlugin;
	}

	public Collection getCollection() {
		return collection;
	}

	public LinkedList<Integer> getIdOne() {
		return idOne;
	}
	
	/*** 09/06/2010 zhaolin ***/
	private Collection collectionEntity = new LinkedList();
	public Collection getCollectionEntity() {
		return collectionEntity;
	}
	
	public void getOrderEcran(Collection collection,LgrTableViewer tableViewer,String codeEntity){
		collectionEntity.clear();
		int countTableItem = tableViewer.getTable().getItemCount();
		List<String> listOrderTri = new LinkedList<String>();
//		List<TaArticle> listOrderTri = new LinkedList<TaArticle>();
		
		for (int i = 0; i < countTableItem; i++) {
			listOrderTri.add(tableViewer.getTable().getItem(i).getText()); 
			
		}
		
		for (String order : listOrderTri) {
			for (Object object : collection) {
				String orderTri = "";
				try {
					orderTri = (String) PropertyUtils.getSimpleProperty(object,codeEntity);
				} catch (Exception e) {
					logger.error("",e);
				}

				if(orderTri.equals(order)){
					collectionEntity.add(object);
				}
			}
		}

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