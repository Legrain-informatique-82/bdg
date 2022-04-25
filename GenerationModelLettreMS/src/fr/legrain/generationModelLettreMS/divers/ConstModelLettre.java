package fr.legrain.generationModelLettreMS.divers;

public class ConstModelLettre {
	
	public static String FICHE_FILE_MODELE_LETTRE = "/ModelLettre/?/defautModelLettre.odt";
	public static String FOLDER_DEFAUT_MODELE_LETTRE_WO = "/ModelLettre/WO/";
	public static String NAME_FILE_DEFAUT_MODELE_LETTRE = "defautModelLettre";
	
	public static final String FILE_MODEL_LETTRE ="defautModelLettre.odt";
	public static final String FOLDER_MODEL_LETTRE ="/ModelLettre/";
	
	public static final String TYPE_FILE_MODEL_OO =".odt";
	public static final String TYPE_FILE_MODEL_WO =".doc";
	
	public static final String[] ARRAY_TYPE_FILE_MODEL_WO ={".doc",".docx"};
	
	/**
	 * contant Wizard
	 */
	public static final String NAME_WIZARD ="Assistant - Publipostage MS Office";
	
	public static final String NAME_PAGE_CHOICE_DATA_LETTER ="WizardPageChoiceDataLettre";
	public static final String TITLE_PAGE_CHOICE_DATA_LETTER ="Sélection des données";
	public static final String DESCRIPTION_PAGE_CHOICE_DATA_LETTER ="Choix du fichier contenant les données pour le publipostage.";
	
	public static final String NAME_PAGE_CHOICE_PLUGIN ="WizardPageChoicePlugin";
	public static final String TITLE_PAGE_CHOICE_PLUGIN ="Sélection de l'emplacement du modèle.";
	public static final String DESCRIPTION_PAGE_CHOICE_PLUGIN ="";
	
	
	public static final String NAME_PAGE_CHOICE_MODEL_LETTER ="WizardPageChoiceModelLettre";
	public static final String TITLE_PAGE_CHOICE_MODEL_LETTER ="Sélection du modèle";
	public static final String DESCRIPTION_PAGE_CHOICE_MODEL_LETTER ="";
	public static final String MESSAGE_MODEL_LETTER_MOT_CLE ="Le fichier de données ne corresponds pas au modèle de lettre.";
	
	public static final String FILE_MODEL ="file de modèle";
	public static final String FILE_EXTRCATION ="file de l'extraction";
	public static final String FILE_MOT_CLE ="file de mot de clé";
	
	

	public static final String[] TYPE_FILE_OFFICE_OO = {".odt"};
	public static final String[] TYPE_FILE_OFFICE_WS = {".doc",".docx"};
	
	public static final String MESSAGE_FILE_NO_EXIST ="Le fichier n'existe pas !";
	public static final String MESSAGE_FOLDER_NO_EXIST ="Le répertoire n'existe pas !";
	
	public static final String MESSAGE_FILE_TYPE_OFFICE ="Le type de fichier n'est pas correct !";
	
	public static final String WIN_LINUX_SOFFICE ="soffice";
	
	public static String PARAM_CONNECTION_OPEN_OFFICE = 
		"uno:socket,host=localhost,port=?;urp;StarOffice.ServiceManager";
	 
	
    public static String PARAM_START_SERVER_OPEN_OFFICE = 
				" -headless -nofirststartwizard -accept=socket,host=localhost,port=?;urp;StarOffice.Service";
    
    public static String PARAM_START_SERVER_OPEN_OFFICE_PARAM1 = 
    	" -accept=socket,host=localhost,port=?;urp";
    
    public static String PARAM_START_SERVER_OPEN_OFFICE_PARAM2 = 
    	" -accept=socket,host=localhost,port=?;urp";
    
    public static final String URI_FILE ="file:///";
    public static final String TYPE_OFFICE_WO = "WO";
    
    public static final String[] VALUES_COMBO_TYPE_SEPARATEUR = new String[]{";",":",","};
    
    public static final String MESSAGE_NAME_PUBLICPOSTAGE ="Nom de publicpostage n'est pas vide!";
    
	public static final String TYPE_FILE_XML = ".xml";
	
	public static final String MESSAGE_DIALOG_TITLE = "Confirmer Supprimer ";
	public static final String MESSAGE_DIALOG_CONTENT = "Vous êtes sûr supprimer ! ";
	
	
	public static final String MESSAGE_DIALOG_INFOS = "Information ";
	public static final String MESSAGE_DIALOG_INFOS_CONTENT = "il faut choisir un nom parametre! ";
	public static final String CHOIX_DEFAUT_CCOMB_PARAM_PUBLIPOSTAGE = "<vide>";
}
