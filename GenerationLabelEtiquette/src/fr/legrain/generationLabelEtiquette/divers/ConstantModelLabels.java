package fr.legrain.generationLabelEtiquette.divers;

import org.eclipse.birt.report.model.api.elements.DesignChoiceConstants;

public class ConstantModelLabels {
	/*
	cm  x  0.39* = in
	in  x  2.54 = cm
	 */

	/**
	 * format of paper
	 */
	public static final String TYPE_PAPER_A4 = "a4";
	public static final String TYPE_PAPER_US_LEGAL= "us-legal";
	public static final String TYPE_PAPER_US_LETTER = "us-letter";
	public static final String TYPE_PAPER_CUSTOM = "custom";
	
	public static final String[] VALUES_COMBO_TYPE_PAPER = new String[]
	                                                       {"a4","us-legal","us-letter","custom"};
	
	public static Float ONE_INCH = new Float(2.54); 
	public static Float ONE_POINTS = new Float(28.3464567);
	public static final String MARGIN_DEFAUT = "1";
	public static final String MARGIN_LEFT = "MarginLeft";
	public static final String MARGIN_RIGHT = "MarginRight";
	public static final String MARGIN_TOP = "MarginTop";
	public static final String MARGIN_BUTTOM = "MarginButtom";
	public static final String A4_HEIGHT = "29.7";
	public static final String A4_WIDTH = "21";
	
	public static final String LARGEUR_PAPIER = "largeurPapier";
	public static final String HAUTEUR_PAPIER = "hauteurPapier";
	
	public static final String ROWS_LABLES = "rowLables";
	public static final String COLUMNS_LABLES = "columnsLables";
	
	public static final String US_LEGAL_HEIGHT = String.format("%.2f",11*ONE_INCH);
	public static final String US_LEGAL_WIDTH = String.format("%.2f",8.5*ONE_INCH);
	
	public static final String US_LETTER_HEIGHT = String.format("%.2f",14*ONE_INCH); 
	public static final String US_LETTER_WIDTH = String.format("%.2f",8.5*ONE_INCH);
	
	/**
	 * constant wizardPage
	 */
	public static final String NAME_WIZARD_MODEL_LABLES = "Assistant - Génération d'étiquettes";
	public static final String NAME_PAGE_MODEL_LABLES = "WizardPageChoiceDataLettre";
	public static final String TITLE_PAGE_MODEL_LABLES = "Régler les paramètres de l'étiquette";
	public static final String DESCRIPTION_PAGE_MODEL_LABLES = "Paramétrage du format et de la source de données.";
	
	public static final String NAME_PAGE_FORMAT_ETIQUETTE = "WizardPageFormatEtiquette";
	public static final String TITLE_PAGE_FORMAT_ETIQUETTE = "Régler la position de l'étiquette";
	public static final String DESCRIPTION_PAGE_FORMAT_ETIQUETTE = "Mise en page des étiquettes.";
	
	public static final String MESSAGE_ERROR_MODEL_LABLES_TEXT = "La valeur n'est pas correcte ou est vide !";
	public static final String MESSAGE_FOLDER_NO_EXIST = "Le répertoire n'existe pas !";
	public static final String MESSAGE_FILE_NO_EXIST ="Le fichier n'existe pas !";
	
	public static final String PATTERN_FLOAT = "[0-9]+(.[0-9]+)?";
	public static final String PATTERN_INTEGER = "[0-9]+";
	
	
	public static final String TYPE_ETIQUETTE_FILE_REPORT =".rptdesign";
	public static final String NAME_ONGLET ="Etiquettes";
	public static final String PAGE_ORIENTATION_LANDSCAPE = DesignChoiceConstants.PAGE_ORIENTATION_LANDSCAPE;

	public static final String PROPERTY_TYPE = "type";
	public static final String PROPERTY_HEIGHT = "height";
	public static final String PROPERTY_WIDTH = "width";
	public static final String UNITS_CM = DesignChoiceConstants.UNITS_CM;
	public static final String UNITS_PERCENTAGE = DesignChoiceConstants.UNITS_PERCENTAGE;
	public static final String PAGE_ORIENTATION = "orientation";
	
	public static final String TOP_MARGIN ="topMargin";
	public static final String LEFT_MARGIN ="leftMargin";
	public static final String RIGHT_MARGIN ="rightMargin";
	public static final String BOTTOM_MARGIN ="bottomMargin";
	
	public static final String LAYOUT_TEXT_ETIQUETTE = "width %scm!,height %scm!";
	
	public static final String SYMBOLE_CHANGER_LIGNE = "<BR>";
	
	public static final String WIZARDPAGE_ERROR_MESSAGE_FORMAT_ETIQUETTE = "les contenu du text ne peuvez pas modifier";
	
	public static final String MESSAGE_DIALOG_TITLE = "Confirmer la suppression ";
	public static final String MESSAGE_DIALOG_CONTENT = "Etes vous sur de vouloir supprimer! ";
	
	public static final String MESSAGE_DIALOG_INFOS = "Information ";
	public static final String MESSAGE_DIALOG_INFOS_CONTENT = "Il faut choisir un nom parametre! ";

	public static final String TYPE_FILE_XML = ".xml";
	public static final String TYPE_FILE_DAT = ".dat";
	
	public static final String[] VALUES_COMBO_TYPE_SEPARATEUR = new String[]{";",":",","};
	
	public static final String CHOIX_DEFAUT_CCOMB_PARAM_ETIQUETTE = "<nouveau>";
	public static final String CHOIX_AUCUN_CCOMB_PARAM_ETIQUETTE = "<aucun>";
	
	public static final String MARGIN_BOTTOM_GRID ="marginBottom"; 
	public static final String PAGE_BREAK_AFTER ="pageBreakAfter";
	
	
	public static final String MESSAGE_ERROR_TITLE_NAME_ETIQUETTE = "Problème dans le nom du ficher";
	
	public static final String MESSAGE_ERROR_INFOS_NAME_ETIQUETTE = "Le nom de l'étiquette ne doit pas contenir " +
													 "de caratères interdits!";
	
}
