package fr.legrain.lib.data;

import java.util.HashMap;

import org.apache.log4j.Logger;

import com.borland.dx.sql.dataset.Database;


public class OuvertureBase {
	
	static Logger logger = Logger.getLogger(OuvertureBase.class.getName());
	//static private Database database = null;
	static private HashMap<String,Database> listeConnection = new HashMap<String,Database>();
	
	
	private OuvertureBase() {
	}
	
	/**
	 * Si databaseId existe deja, la base de données correspondate est retournée, sinon une nouvelle base de 
	 * données est ajouté à la liste.
	 * @param databaseId - String qui permet de retrouver cette base de donnees
	 * @param url - url de la base de donnees
	 * @param user - nom d'utilisateur pour la conexion
	 * @param pass - mot de passe pour la connexion
	 * @param driver - classe du pilote JDBC a utiliser
	 * @return
	 */
	static public Database getDatabase(String databaseId,String url,String user,String pass,String driver) {
		try{
			if(logger.isDebugEnabled())
				logger.debug("getDatabase");
			if(listeConnection.keySet().contains(databaseId)){
				return listeConnection.get(databaseId);
			} else {
				Database db = new Database();
				connection(db,url,user,pass,driver);
				listeConnection.put(databaseId,db);
				if(logger.isDebugEnabled())
					logger.debug("Création base");
				return db;
			}				  
			
		}
		catch(Exception e){
			logger.error("Erreur : getDatabase", e);
			return null;
		}
	}
	
	static public boolean connection(Database database, String url, String user, String pass, String driver) {
		try{
			database.setConnection(new com.borland.dx.sql.dataset.ConnectionDescriptor(
					url,user,pass , false, driver));
			return true;
		}catch(Exception e){
			logger.error("Erreur : getDatabase", e);
			return false;
		}
	}

	public static HashMap<String, Database> getListeConnection() {
		return listeConnection;
	}

	public static void setListeConnection(HashMap<String, Database> listeConnection) {
		OuvertureBase.listeConnection = listeConnection;
	}
	
	static public Database getDatabase(String databaseId) {
		return listeConnection.get(databaseId);
	}
	
	public static void setDatabase(String databaseId, Database database) {
		if(database==null)listeConnection.remove(databaseId);
		else
			listeConnection.put(databaseId,database);
	}
	
//	static public Database getDatabase() {
//		return database;
//	}
//	
//	public static void setDatabase(Database database) {
//		OuvertureBase.database = database;
//	}
}	

