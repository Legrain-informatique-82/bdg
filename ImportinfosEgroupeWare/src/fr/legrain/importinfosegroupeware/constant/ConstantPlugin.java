package fr.legrain.importinfosegroupeware.constant;

public class ConstantPlugin {
	
	
	
	public static final String JDBC = "jdbc:";
	
	/***** POUR DB MYSQL****/
	public static final String DB_EGROUPWARE = "egroupware";
//	public static final String DB_EGROUPWARE = "egroupware_test";
	public static final String LOGIN_MYSQL = "root";
	public static final String PASSWORD_MYSQL = "pwd04sql82";
	public static final String MYSQL_DB = "mysql";
	public static final String JDBC_DRIVER_MYSQL = "com.mysql.jdbc.Driver";
	public static final String PATH_DB = "lgrser.lgr:";//192.168.1.11:
	public static final String PORT_DB_MYSQL = "3306" ;
	
	/** POUR DB DE FIREBIRD**/
	public static final String PATH_LOCALHOST = "localhost";
	public static final String LOGIN_FIREBIRD = "admin";
	public static final String PASSWORD_FIREBIRD = "###_PASSWORD_FB_BDG_###";
	public static final String SYSTEME_PROPERTY_JDBC_FIREBIRD = "jdbc.drivers";
	public static final String FIREBIRD_DB = "firebirdsql";
	public static final String JDBC_DRIVER_FIREBIRD = "org.firebirdsql.jdbc.FBDriver";
	public static final String PORT_DB_FIREBIRD = "3050" ;
	
	/***query sql**/
	public static final String SELECT_EGW_ADDRESSBOOK = "select * from egw_addressbook where contact_id = ?";
	public static final String SELECT_EGW_ADDRESSBOOK_EXTRA = "select * from egw_addressbook_extra where contact_id = ?";
	public static final String SELECT_EGW_ADDRESSBOOK_EXTRA_NEW = "select * from egw_addressbook_extra where contact_id = ?" +
																  " and contact_name=?";
	public static final String SELECT_COUNT_EGW_ADDRESSBOOK = "select count(*) from egw_addressbook where contact_id = ?";
	
	public static final String SELECT_ID_TIERS_TA_TIERS = "select * from TA_TIERS where CODE_TIERS=?";
	public static final String SELECT_ID_ENTREPRISE_TA_ENTREPRISE = "select * from TA_ENTREPRISE where " +
																	   "CODE_ENTREPRISE=?";
	public static final String SELECT_TA_ENTREPRISE = "select * from TA_ENTREPRISE";
	public static final String UPDATE_EGW_ADDRESSBOOK_EXTRA = "update egw_addressbook_extra set " +
							   "contact_value=? where contact_id = ?";
	public static final String INSERT_EGW_ADDRESSBOOK_EXTRA = "insert into egw_addressbook_extra(" +
			   				   "contact_id,contact_owner,contact_name,contact_value) values(" +
			   				   "?,?,?,?)";
	
	public static final String PATH_DOSSIER_CLIENT ="http://lgrser.lgr/lgr/tiers/client_test/";
	public static final String PATH_FTP ="http://lgrser.lgr/lgr/tiers/";
	
	
	
	/****/
	public static final String LIEN_LGRSER = "lien_lgrser";
	public static final String PATH_TIERS = "/home/comm/tiers/";
	/** pour tester**/
//	public static final String PROSPECT = "/prospect_test/";
//	public static final String CLIENT = "client_test/";
	
	public static final String PROSPECT = "/prospect/";
	public static final String CLIENT = "client/";
	
	public static final String CODE_CLI = "CODE_CLI";
	public static final String MESSAGE_IMPORT = "CE NUMERO DE CONTACT EST REUSSIT IMPORTE !!";
	public static final String MESSAGE_EXISTER = "CE NUMERO DE CONTACT NE EXISTE PAS !!";
	public static final String MESSAGE_IMPORT_ERROR = "CE NUMERO DE CONTACT EST FAUTE !!";
	public static final String MESSAGE_IMPORT_ERROR_NOM_TIERS = "NOM DE TIERS EST VIDE !! NE POUVEZ PAS IMPORTER !!";
	
	/** constant server ftp**/
	public static final String SERVER_FTP = "lgrser.lgr";
//	public static final String USER_FTP = "zhaolin";
//	public static final String PASSWORD_FTP = "lgr009";
	public static final String USER_FTP = "cecile";
	public static final String PASSWORD_FTP = "lgr007";
	public static final int PORT_FTP = 21;
	
	
	/********new********/
	public static final String PATH_FOLDER_CLIENT ="%slgrser/lgr/tiers/";
//	public static final String HEAD_HTTP = "http://";
	public static final String HEAD_SERVER_LINUX = "smb://";
	public static final String HEAD_SERVER_WIN = "\\";
	
	public static final String VALUE_INCONNU = "INCONNU";
	/*****************************/
	/****constant pour test****/
	public static final String sqlfirebird = "select * from TA_TIERS where ID_TIERS = 1";
}
