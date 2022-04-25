package fr.legrain.lib.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.log4j.Logger;
import org.firebirdsql.gds.impl.GDSType;
import org.firebirdsql.management.FBMaintenanceManager;
import org.firebirdsql.management.FBManager;
import org.firebirdsql.management.FBBackupManager;

//jdbc driver class : org.firebirdsql.jdbc.FBDriver
//jdbc url : jdbc:firebirdsql:localhost/3050:c:/chemin/fichier.fdb
public class FireBirdManager {
	
	static Logger logger = Logger.getLogger(BdLgr.class.getName());
	
	private FBManager fbManager = new FBManager();
	private String DB_SERVER_URL = "localhost";
	private int DB_SERVER_PORT = 3050;
	private String DB_PATH = null;
	private String DB_USER = "admin";
	private String DB_PASSWORD = "###_PASSWORD_FB_BDG_###";//"masterkey";
	private String JDBC_DRIVER = "org.firebirdsql.jdbc.FBDriver";
	private String DB_NAME = "GEST_COM.FDB";
	private String DEBUT_URL_JDBC = "jdbc:firebirdsql";
	
	
	public void createDB(String dbPath, String dbName, String dbUser, String dbPass , String dbServerUrl) {
		createDB(dbPath, dbName, dbUser, dbPass , dbServerUrl, 3050);
	}
	


	
	public void CompactDB(String dbPath,String fileBase,String User,String Pass) throws SQLException {
		try {

			FBMaintenanceManager f = new FBMaintenanceManager(GDSType.getType("PURE_JAVA"));
				f.setHost(DB_SERVER_URL);
				f.setUser(User);
				f.setPassword(Pass);
				f.setDatabase(dbPath+"/"+fileBase);
				f.sweepDatabase();							
		}catch(Exception e) {

		}
	};
	
	public void sauvegardeDB(String dbPath,String fileBase,String fileBackup,String User,String Pass) throws SQLException {
		try {

			FBBackupManager backupManager =
				new FBBackupManager(GDSType.getType("PURE_JAVA"));			
				backupManager.setHost(DB_SERVER_URL);
				backupManager.setUser(User);
				backupManager.setPassword(Pass);
				backupManager.setDatabase(dbPath+"/"+fileBase);
				backupManager.setBackupPath(dbPath+"/"+fileBackup);
				backupManager.setVerbose(true);
				backupManager.backupDatabase();
		}catch(Exception e) {
			FBBackupManager backupManager =
				new FBBackupManager(GDSType.getType("PURE_JAVA"));			
				backupManager.setHost(DB_SERVER_URL);
				backupManager.setUser("SYSDBA");
				backupManager.setPassword("masterkey");
				backupManager.setDatabase(dbPath+"/"+fileBase);
				backupManager.setBackupPath(dbPath+"/"+fileBackup);
				backupManager.setVerbose(true);
				backupManager.backupDatabase();
		}
	};
	
	
	public void restaurationDB(String dbPath,String fileBase,String fileBackup,String User,String Pass) throws SQLException {
		try {
				FBBackupManager backupManager =
					new FBBackupManager(GDSType.getType("PURE_JAVA"));		
					backupManager.setHost(DB_SERVER_URL);
					backupManager.setUser(User);
					backupManager.setPassword(Pass);
					backupManager.setDatabase(dbPath+"/"+fileBase);
					backupManager.setBackupPath(dbPath+"/"+fileBackup);
					backupManager.setVerbose(true);
					backupManager.setRestoreReplace(true);
					backupManager.restoreDatabase();
		}catch(Exception e) {
			FBBackupManager backupManager =
				new FBBackupManager(GDSType.getType("PURE_JAVA"));		
				backupManager.setHost(DB_SERVER_URL);
				backupManager.setUser("SYSDBA");
				backupManager.setPassword("masterkey");
				backupManager.setDatabase(dbPath+"/"+fileBase);
				backupManager.setBackupPath(dbPath+"/"+fileBackup);
				backupManager.setVerbose(true);
				backupManager.setRestoreReplace(true);
				backupManager.restoreDatabase();
		}					
	};
	
	
	/**
	 * Création d'une base de données
	 * @param dbPath - chemin du répertoire contenant le fichier de base de données
	 * @param dbName - fichier de la base de données
	 * @param dbUser - nom d'utilisateur
	 * @param dbPass - mot de passe
	 * @param dbServerUrl - ex "localhost"
	 * @param dbPort - 3050 par defaut
	 */
	public void createDB(String dbPath, String dbName, String dbUser, String dbPass , String dbServerUrl, int dbPort) {
		try {
			fbManager.setServer(dbServerUrl);
			fbManager.setPort(dbPort);
			
			fbManager.start();
			
			if(fbManager.isDatabaseExists(dbPath + "/" + dbName, dbUser, dbPass)){
				fbManager.dropDatabase(dbPath + "/" + dbName, dbUser, dbPass);
			}
			
			fbManager.createDatabase(dbPath + "/" + dbName, dbUser, dbPass);
			
			Class.forName(JDBC_DRIVER);
			
			Connection connection = DriverManager.getConnection(
					DEBUT_URL_JDBC+":"+dbServerUrl+"/"+dbPort+":"+dbPath+"/"+dbName, 
					dbUser,
					dbPass);
			
			//After you have created the database you can set its default character set with a command like:
			connection.prepareStatement("UPDATE rdb$database SET rdb$character_set_name='ISO8859_1'").execute();
			Statement s = connection.createStatement();
			
		} catch (Exception e) {
			logger.error("",e);
		}
	}
	
	public void createDB(String dbPath) {
		//FBManager fbManager = new FBManager();
		createDB(dbPath, DB_NAME);
	}
	
	public void createDB(String dbPath, String dbName) {
		//FBManager fbManager = new FBManager();
		String DB_PATH = dbPath;
		String DB_NAME = dbName;
		
		fbManager.setServer(DB_SERVER_URL);
		fbManager.setPort(DB_SERVER_PORT);
		
		try {
			fbManager.start();
			fbManager.createDatabase(DB_PATH + "/" + DB_NAME, DB_USER, DB_PASSWORD);
			
			Class.forName(JDBC_DRIVER);
			
			Connection connection = DriverManager.getConnection(
					DEBUT_URL_JDBC+":"+DB_SERVER_URL+"/"+DB_SERVER_PORT+":"+DB_PATH+"/"+DB_NAME, 
					DB_USER,
					DB_PASSWORD);
			
			//After you have created the database you can set its default character set with a command like:
			//connection.prepareStatement("UPDATE rdb$database SET rdb$character_set_name='ISO8859_1'").execute();
			//Statement s = connection.createStatement();
				
		} catch (Exception e) {
			logger.error("",e);
		}
	}
	
	public static void main(String[] args) {
		FireBirdManager fbManager = new FireBirdManager();
		fbManager.createDB(args[0]);
	}

	public String getDB_NAME() {
		return DB_NAME;
	}

	public void setDB_NAME(String db_name) {
		DB_NAME = db_name;
	}

	public String getDB_PASSWORD() {
		return DB_PASSWORD;
	}

	public void setDB_PASSWORD(String db_password) {
		DB_PASSWORD = db_password;
	}

	public String getDB_PATH() {
		return DB_PATH;
	}

	public void setDB_PATH(String db_path) {
		DB_PATH = db_path;
	}

	public int getDB_SERVER_PORT() {
		return DB_SERVER_PORT;
	}

	public void setDB_SERVER_PORT(int db_server_port) {
		DB_SERVER_PORT = db_server_port;
	}

	public String getDB_SERVER_URL() {
		return DB_SERVER_URL;
	}

	public void setDB_SERVER_URL(String db_server_url) {
		DB_SERVER_URL = db_server_url;
	}

	public String getDB_USER() {
		return DB_USER;
	}

	public void setDB_USER(String db_user) {
		DB_USER = db_user;
	}

	public String getDEBUT_URL_JDBC() {
		return DEBUT_URL_JDBC;
	}

	public void setDEBUT_URL_JDBC(String debut_url_jdbc) {
		DEBUT_URL_JDBC = debut_url_jdbc;
	}

	public String getJDBC_DRIVER() {
		return JDBC_DRIVER;
	}

	public void setJDBC_DRIVER(String jdbc_driver) {
		JDBC_DRIVER = jdbc_driver;
	}
	
}
