package fr.legrain.wsimportosc.contants;

import java.util.HashMap;
import java.util.Map;

public class ConstantWebClient {
	
	
	public static final String E1_FR = "é";
	public static final String E2_FR = "è";
	public static final String E3_FR = "ë";
	public static final String C_FR = "ç";
	public static final String I_FR = "î";
	
	
	public static final String INCONNU = "inconnu";
	public static final String MESSAGE_ERROR_LOGIN_OR_PASSWORD = "Login ou Password est incorrect !!";
	public static final String MESSAGE_NO_INFOS_IMPORTER = "Il y a rien de factures de importer !!";
	public static final String MESSAGE_SUCCESS_INFOS_IMPORTER = " IMPORTATION EST REUSSIE !!";
	public static final String MESSAGE_INFOS_NO_TIERS_IMPORTER = "  MAIS PAS DE NOUVELLES TIERS !!";
	public static final String MESSAGE_INFOS_NO_ARTICLES_IMPORTER = "  MAIS PAS DE NOUVELLES ARTICLES !!";
	public static final float  STOCK_MIN_ARTICLE = (float) 1.00;
	
	public static final int ID_T_ADR_B = 1;// ==> CODE_ADR = B
	public static final int ID_T_ADR_D = 2;// ==> CODE_ADR = D
	public static final int ID_T_ADR_FACT = 4;// ==> CODE_ADR = FACT
	public static final int ID_T_ADR_LIV = 5;// ==> CODE_ADR = LIV
	
	public static final float VALUE_TAUX_TVA_A3 =(float)19.6000;
	public static final String CODE_TVA_A3 = "A3";
	public static final String NUMCPT_TVA_A3 = "44566";
	public static final float VALUE_TAUX_TVA_A2 =(float)5.5000;
	public static final String CODE_TVA_A2 = "A2";
	public static final String NUMCPT_TVA_A2 = "44566";
	public static final String CODE_T_TVA_E = "E";
	
	public static final int ID_T_LIGNE_H = 1;//hors tax
	
	public static final String FACTURE = "FACTURE_";
	public static final String LIBELLE_FACTURE = "FACTURE_OSC_";
	
	/** pour table TA_TIERS_OSC **/
	public static final String COMPTE_TA_TIERS_OSC = "411";
	public static final int ID_T_TIERS_TA_TIERS_OSC = 2;
	public static final String CODE_T_TIERS_OSC = "SARL";

	public static final String INSERT_TA_TIERS_OSC = "insert into TA_TIERS_OSC(ID_TIERS,CODE_TIERS,CODE_COMPTA,COMPTE,NOM_TIERS,PRENOM_TIERS," +
													 "ID_T_CIVILITE,ID_T_TIERS)values(?,?,?,?,?,?,?,?)";
	

	/** pour type telephone **/
	public static final String TYPE_TEL_BUREAU = "BUREAU";
	public static final String TYPE_TEL_FAX = "FAX";
	
	/** pour table TA_T_CIVILITE**/
	
	public static final String SELECT_TA_T_CIVILITE = "select * from TA_T_CIVILITE";
	public static final String CODE_T_CIVILITE = "CODE_T_CIVILITE";
	public static final String ID_T_CIVILITE = "ID_T_CIVILITE";
	public static final String INSERT_TA_T_CIVILITE = "insert into TA_T_CIVILITE(ID_T_CIVILITE,CODE_T_CIVILITE)values(?,?)";
	
	/** pour table TA_ARTICLE_OSC**/
	public static final String SELECT_TA_ARTICLE_OSC = "select * from TA_ARTICLE_OSC";
	public static final String INSERT_TA_ARTICLE_OSC = "insert into TA_ARTICLE_OSC(ID_ARTICLE,CODE_ARTICLE,LIBELLEC_ARTICLE," +
													   "STOCK_MIN_ARTICLE)values(?,?,?,?)";
	public static final String UPDATE_TA_ARTICLE_OSC = "update TA_ARTICLE_OSC set ID_ARTICLE_BDG=?,CODE_ARTICLE=? where ID_ARTICLE = ?";
	public static final String DROP_TA_ARTICLE_OSC = "delete from TA_ARTICLE_OSC";
	
	/** pour table TA_ARTICLE **/
	public static final String SELECT_TA_ARTICLE = "select * from TA_ARTICLE where CODE_ARTICLE = ?";
	
	/** pour table TA_FACTURE_OSC **/
	public static final String ID_FACTURE_OSC = "ID_FACTURE";
	public static final String SELECT_TA_FACTURE_OSC = "select * from TA_FACTURE_OSC";
	public static final String INSERT_TA_FACTURE_OSC = "insert into TA_FACTURE_OSC(ID_FACTURE,CODE_FACTURE,DATE_FACTURE," +
													   "DATE_LIV_FACTURE,LIBELLE_FACTURE,ID_ADRESSE,ID_ADRESSE_LIV,ID_TIERS)" +
													   "values(?,?,?,?,?,?,?,?)";
	
	/** pour table TA_L_FACTURE_OSC**/
	public static final String SELECT_TA_L_FACTURE_OSC = "select * from TA_L_FACTURE_OSC";
	public static final String INSERT_TA_L_FACTURE_OSC = "insert into TA_L_FACTURE_OSC(ID_L_FACTURE,ID_FACTURE,ID_T_LIGNE,ID_ARTICLE," +
														 "NUM_LIGNE_L_FACTURE,LIB_L_FACTURE,QTE_L_FACTURE,PRIX_U_L_FACTURE,TAUX_TVA_L_FACTURE," +
														 "CODE_TVA_L_FACTURE,CODE_T_TVA_L_FACTURE,MT_HT_L_FACTURE,MT_TTC_L_FACTURE)" +
														 "values(?,?,?,?,?,?,?,?,?,?,?,?,?)";
	/** pour table TA_INFOS_FACTURE_OSC **/
	public static final String ID_INFOS_FACTURE_OSC  = "ID_INFOS_FACTURE";
	public static final String SELECT_TA_INFOS_FACTURE_OSC = "select * from TA_INFOS_FACTURE_OSC";
	public static final String INSERT_TA_INFOS_FACTURE_OSC = "insert into TA_INFOS_FACTURE_OSC(ID_INFOS_FACTURE,ID_FACTURE,ADRESSE1,ADRESSE2," +
													   "ADRESSE3,CODEPOSTAL,VILLE,PAYS,ADRESSE1_LIV,ADRESSE2_LIV,ADRESSE3_LIV,CODEPOSTAL_LIV," +
													   "VILLE_LIV,PAYS_LIV,CODE_COMPTA,COMPTE,NOM_TIERS,PRENOM_TIERS,CODE_T_CIVILITE,CODE_T_ENTITE)" +
													   "values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
	/** pour table TA_ADRESSE_OSC**/
	public static final String ID_ADDRESS_OSC = "ID_ADRESSE";
	public static final String INSERT_TA_ADRESSE_OSC = "insert into TA_ADRESSE_OSC(ID_ADRESSE,ADRESSE1_ADRESSE,ADRESSE2_ADRESSE," +
	   												   "ADRESSE3_ADRESSE,CODEPOSTAL_ADRESSE,VILLE_ADRESSE,PAYS_ADRESSE)" +
	                                                   "values(?,?,?,?,?,?,?)";
	
	public static final String SELECT_TA_ADRESSE_OSC = "select * from TA_ADRESSE_OSC";
	
	
	/** pour table TA_R_ADR_OSC **/
	public static final String ID_R_ADR_OSC = "ID_R_ADR";
	public static final String INSERT_TA_R_ADR_OSC = "insert into TA_R_ADR_OSC(ID_R_ADR,ID_TIERS,ID_ADRESSE)" +
	   												 "values(?,?,?)";
	public static final String SELECT_TA_R_ADR_OSC = "select * from TA_R_ADR_OSC";
	
	/** pour table TA_R_ADR_T_ADR_OSC **/
	public static final String ID_R_ADR_T_ADR_OSC = "ID_R_ADR_T_ADR";
	public static final String INSERT_TA_R_ADR_T_ADR_OSC = "insert into TA_R_ADR_T_ADR_OSC(ID_R_ADR_T_ADR,ID_T_ADR,ID_ADRESSE)" +
	   												 	   "values(?,?,?)";
	public static final String SELECT_TA_R_ADR_T_ADR_OSC = "select * from TA_R_ADR_T_ADR_OSC";
	
	/** pour table TA_EMAIL_OSC **/
	public static final String	ID_EMAIL_OSC = "ID_EMAIL";
	public static final String SELECT_TA_EMAIL_OSC = "select * from TA_EMAIL_OSC";
	public static final String INSERT_TA_EMAIL_OSC = "insert into TA_EMAIL_OSC(ID_EMAIL,ADRESSE_EMAIL)values(?,?)";
	/** pour table TA_R_EMAIL_OSC **/
	public static final String ID_R_EMAIL_OSC = "ID_R_EMAIL"; 
	public static final String SELECT_TA_R_EMAIL_OSC = "select * from TA_R_EMAIL_OSC";
	public static final String INSERT_TA_R_EMAIL_OSC = "insert into TA_R_EMAIL_OSC(ID_R_EMAIL,ID_TIERS,ID_EMAIL)values(?,?,?)";
	
	/** pour table TA_TELEPHONE_OSC **/
	public static final String	ID_TELEPHONE_OSC = "ID_TELEPHONE";
	public static final String SELECT_TA_TELEPHONE_OSC = "select * from TA_TELEPHONE_OSC";
	public static final String INSERT_TA_TELEPHONE_OSC = "insert into TA_TELEPHONE_OSC(ID_TELEPHONE,NUMERO_TELEPHONE)values(?,?)";
	
	/** pour table TA_R_TELEPHONE_OSC **/
	public static final String ID_R_TEL_OSC = "ID_R_TEL"; 
	public static final String SELECT_TA_R_TEL_OSC = "select * from TA_R_TEL_OSC";
	public static final String INSERT_TA_R_TEL_OSC = "insert into TA_R_TEL_OSC(ID_R_TEL,ID_TIERS,ID_TELEPHONE)values(?,?,?)";
	
	
	/** pour table TA_R_TEL_T_TEL_OSC **/
	public static final int ID_T_TEL_B = 1;// like Bureau
	public static final int ID_T_TEL_FAX = 4;// like Fax
	public static final String ID_R_TEL_T_TEL = "ID_R_TEL_T_TEL"; 
	public static final String SELECT_TA_R_TEL_T_TEL_OSC = "select * from TA_R_TEL_T_TEL_OSC";
	public static final String INSERT_TA_R_TEL_T_TEL_OSC = "insert into TA_R_TEL_T_TEL_OSC(ID_R_TEL_T_TEL,ID_TELEPHONE,ID_T_TEL)values(?,?,?)";
	
	/**test**/
	
	public static final String SELECT_TA_T_CIVILITE_TEST = "select * from TA_T_CIVILITE where ID_T_CIVILITE = 57";
}
