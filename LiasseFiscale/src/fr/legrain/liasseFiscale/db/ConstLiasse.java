package fr.legrain.liasseFiscale.db;

import org.eclipse.core.runtime.Platform;

public class ConstLiasse {
	
	static final public String C_RCP_INSTANCE_LOCATION = Platform.getInstanceLocation().getURL().getFile(); //=Workspace
	static final public String C_RCP_INSTALL_LOCATION  = Platform.getInstallLocation().getURL().getFile();
	static final public String C_RCP_USER_LOCATION     = Platform.getUserLocation().getURL().getFile();
	
	public static boolean C_REPERTOIRE_PROJET_IN_WORKSPACE = true;
	public static String C_REPERTOIRE_PROJET = "";
	public static String C_REPERTOIRE_BASE   = C_RCP_INSTANCE_LOCATION;
	
	public static final String C_URL_BDD     = "jdbc:firebirdsql:";
	public static final String C_USER        = "admin";
	public static final String C_PASS        = "###_PASSWORD_FB_BDG_###";
	public static final String C_DRIVER_JDBC = "org.firebirdsql.jdbc.FBDriver";
	
	public static final String C_Debut_Requete = "select * from ";
	public static final String C_Ip_Acces      = "IP_ACCES";
	
	public static String C_FICHIER_BDD       = C_REPERTOIRE_BASE+C_REPERTOIRE_PROJET+"/Bd/liasse.fdb";
	
	public static String C_REPERTOIRE_BD = "Bd";
	
	public static String C_FICHIER_GESTCODE 	  = C_REPERTOIRE_BASE+C_REPERTOIRE_PROJET+"/Bd/GestCode.properties";
	public static String C_FICHIER_INI_CTRL       = C_REPERTOIRE_BASE+C_REPERTOIRE_PROJET+"/Bd/CtrlBD.ini";
	public static String C_FICHIER_INI_IDBD       = C_REPERTOIRE_BASE+C_REPERTOIRE_PROJET+"/Bd/IDBD.ini";
	//public static String C_FICHIER_LISTE_TITRE_BD = C_REPERTOIRE_BASE+C_REPERTOIRE_PROJET+"/Bd/TitreBD2.lst";
	//public static String C_FICHIER_CONF_LOG4J     = C_REPERTOIRE_BASE+C_REPERTOIRE_PROJET+"/Bd/log4j.properties"; //log
	public static String C_FICHIER_MODIF     	  = C_REPERTOIRE_BASE+C_REPERTOIRE_PROJET+"/Bd/Modif.properties"; //log
	//public static String C_FICHIER_INI_CHAMPMAJ   = C_REPERTOIRE_BASE+C_REPERTOIRE_PROJET+"/Bd/ChampMaj.ini";
	//public static String C_FICHIER_INI_TOUTVENANT   = C_REPERTOIRE_BASE+C_REPERTOIRE_PROJET+"/Bd/ToutVenant.ini";
	
	public static void updatePath() {
		if(C_REPERTOIRE_PROJET_IN_WORKSPACE) {
			C_FICHIER_BDD  = C_REPERTOIRE_BASE+C_REPERTOIRE_PROJET+"/Bd/liasse.fdb";
			C_FICHIER_INI_IDBD = C_REPERTOIRE_BASE+C_REPERTOIRE_PROJET+"/Bd/IDBD.ini";
			C_FICHIER_INI_CTRL = C_REPERTOIRE_BASE+C_REPERTOIRE_PROJET+"/Bd/CtrlBD.ini";
			C_FICHIER_MODIF    = C_REPERTOIRE_BASE+C_REPERTOIRE_PROJET+"/Bd/Modif.properties";
		} else {
			C_FICHIER_BDD = C_REPERTOIRE_PROJET+"/Bd/liasse.fdb";
			C_FICHIER_INI_IDBD = C_REPERTOIRE_PROJET+"/Bd/IDBD.ini";
			C_FICHIER_INI_CTRL = C_REPERTOIRE_PROJET+"/Bd/CtrlBD.ini";
			C_FICHIER_MODIF    = C_REPERTOIRE_PROJET+"/Bd/Modif.properties";
		}
	}
	
	public static final int C_LOCATE_OPTION_BORLAND_FIRST = 32;
	
	//fichier qui peuvent être présent dans un répertoire de document fiscal valide
	public static final String C_FICHIER_ID_DOC = "doc.xml";
	public static final String C_FICHIER_VERROU_DOC = "verrou.xml";
	public static final String C_FICHIER_COMPTA_LOCAL = "compta.txt";
	public static final String C_FICHIER_XML_COMPTA_INITIAL = "comptaInitial.xml";
	public static final String C_FICHIER_XML_COMPTA_FINAL = "comptaFinal.xml";
	public static final String C_FICHIER_XML_REPART_INITIAL = "repart.xml";
	public static final String C_FICHIER_XML_REPART_FINAL = "repartFinal.xml";
	public static final String C_FICHIER_XML_MODEL_DOC = "model.xml";
	
	//nom des fichiers PDF représentant les documents fiscaux
	public static final String C_PDF_LIASSE_AGRI = "LIASSE AGRICOLE REEL 2143.pdf";
	public static final String C_PDF_LIASSE_BIC  = "LIASSE BIC REEL 2050.pdf";
	
	//nom des "répertoires de base" pour chaque type de document
	public static final String C_REP_DOC_TYPE_LIASSE = "liasse fiscale";
	public static final String C_REP_DOC_TYPE_TVA = "tva";
	
	//TABLE FINAL
	public static final String C_NOM_TA_FINAL    = "FINAL";
	public static final String C_NOM_VU_FINAL    = "FINAL";
	public static final String C_FINAL_CLE       = "CLE";
	public static final String C_FINAL_VALEUR    = "VALEUR";
	public static final String C_FINAL_ID_DETAIL = "ID_DETAIL";
	
	//TABLE FINAL_DETAIL
	public static final String C_NOM_TA_FINAL_DETAIL    = "FINAL_DETAIL";
	public static final String C_NOM_VU_FINAL_DETAIL    = "FINAL_DETAIL";
	public static final String C_FINAL_DETAIL_ID_DETAIL = "ID_DETAIL";
	public static final String C_FINAL_DETAIL_NUM_CPT   = "NUM_CPT";
	public static final String C_FINAL_DETAIL_TYPE_TRAITEMENT = "TYPE_TRAITEMENT";

	//TABLE INITIAL
	public static final String C_NOM_TA_INITIAL        = "INITIAL";
	public static final String C_NOM_VU_INITIAL        = "INITIAL";
	public static final String C_INITIAL_NUM_CPT       = "NUM_CPT";
	public static final String C_INITIAL_LIBELLE_CPT   = "LIBELLE_CPT";
	public static final String C_INITIAL_MT_DEBIT      = "MT_DEBIT";
	public static final String C_INITIAL_MT_CREDIT     = "MT_CREDIT";
	public static final String C_INITIAL_SOLDE         = "SOLDE";
	public static final String C_INITIAL_MT_DEBIT_2    = "MT_DEBIT_2";
	public static final String C_INITIAL_MT_CREDIT_2   = "MT_CREDIT_2";
	public static final String C_INITIAL_SOLDE_2       = "SOLDE_2";
	public static final String C_INITIAL_MT_DEBIT_3    = "MT_DEBIT_3";
	public static final String C_INITIAL_MT_CREDIT_3   = "MT_CREDIT_3";
	public static final String C_INITIAL_SOLDE_3       = "SOLDE_3";

	//TABLE INITIAL_COMPL
	public static final String C_NOM_TA_INITIAL_COMPL      = "INITIAL_COMPL";
	public static final String C_NOM_VU_INITIAL_COMPL      = "INITIAL_COMPL";
	public static final String C_INITIAL_COMPL_ID_COMPL    = "ID_COMPL";
	public static final String C_INITIAL_COMPL_VALEUR      = "VALEUR";
	public static final String C_INITIAL_COMPL_EMPLACEMENT = "EMPLACEMENT";

	//TABLE REPART
	public static final String C_NOM_TA_REPART    = "REPART";
	public static final String C_NOM_VU_REPART    = "REPART";
	public static final String C_REPART_NUM_CPT   = "NUM_CPT";
	public static final String C_REPART_MT_DEBIT  = "MT_DEBIT";
	public static final String C_REPART_MT_CREDIT = "MT_CREDIT";
	public static final String C_REPART_SOLDE     = "SOLDE";
	public static final String C_REPART_MT_DEBIT_2  = "MT_DEBIT_2";
	public static final String C_REPART_MT_CREDIT_2 = "MT_CREDIT_2";
	public static final String C_REPART_SOLDE_2     = "SOLDE_2";
	public static final String C_REPART_MT_DEBIT_3  = "MT_DEBIT_3";
	public static final String C_REPART_MT_CREDIT_3 = "MT_CREDIT_3";
	public static final String C_REPART_SOLDE_3     = "SOLDE_3";
	public static final String C_REPART_REGIME      = "REGIME";
	public static final String C_REPART_ORIGINE      = "ORIGINE";
	public static final String C_REPART_TYPE_TRAITEMENT      = "TYPE_TRAITEMENT";
	public static final String C_REPART_SOUS_TYPE_TRAITEMENT = "SOUS_TYPE_TRAITEMENT";
	public static final String C_REPART_ANNEE = "ANNEE";

	//TABLE TOTAUX
	public static final String C_NOM_TA_TOTAUX    = "TOTAUX";
	public static final String C_NOM_VU_TOTAUX    = "TOTAUX";
	public static final String C_TOTAUX_CLE       = "CLE";
	public static final String C_TOTAUX_LISTE_CLE = "LISTE_CLE";
	public static final String C_TOTAUX_REGIME    = "REGIME";
	public static final String C_TOTAUX_TYPE_TRAITEMENT      = "TYPE_TRAITEMENT";
	public static final String C_TOTAUX_CONDITION      = "CONDITION";
	public static final String C_TOTAUX_SOUS_TYPE_TRAITEMENT = "SOUS_TYPE_TRAITEMENT";
	public static final String C_TOTAUX_ANNEE 	  = "ANNEE";
	public static final String C_TOTAUX_ORDRE     = "ORDRE";
	
	//TABLE TOTAUX
	public static final String C_NOM_TA_TRAITEMENTS = "TRAITEMENTS";
	public static final String C_NOM_VU_TRAITEMENTS = "TRAITEMENTS";
	public static final String C_TRAITEMENTS_ID_TRAITEMENTS  = "ID_TRAITEMENTS";
	public static final String C_TRAITEMENTS_TYPE_TRAITEMENT = "TYPE_TRAITEMENT";
	public static final String C_TRAITEMENTS_LIBELLE_TYPE    = "LIBELLE_TYPE";
	public static final String C_TRAITEMENTS_SOUS_TYPE_TRAITEMENT         = "SOUS_TYPE_TRAITEMENT";
	public static final String C_TRAITEMENTS_LIBELLE_SOUS_TYPE_TRAITEMENT = "LIBELLE_SOUS_TYPE_TRAITEMENT";
	public static final String C_TRAITEMENTS_ORIGINES_DONNEES 	  = "ORIGINES_DONNEES";
	public static final String C_TRAITEMENTS_ANNEE   = "ANNEE";
	public static final String C_TRAITEMENTS_REGIME  = "REGIME";

}
