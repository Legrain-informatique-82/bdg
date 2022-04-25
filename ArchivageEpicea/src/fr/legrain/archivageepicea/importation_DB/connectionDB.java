package fr.legrain.archivageepicea.importation_DB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class connectionDB {
	
	//private static Connection connection = null;
	private static String DB_SERVER_URL = "localhost";
	private static int DB_SERVER_PORT = 3050;
	private static String DB_USER = "admin";
	private static String DB_PASSWORD = "###_PASSWORD_FB_BDG_###";//"masterkey";
	private String JDBC_DRIVER = "org.firebirdsql.jdbc.FBDriver";
	private static String DEBUT_URL_JDBC = "jdbc:firebirdsql";
	protected Connection connDB;
	private static String keyMap = null;
	
	static private Map<String, Connection> mapConnect = new HashMap<String, Connection>(0); 
	
	
	static public String cheminBdd(String pathDB){
		//System.out.println(DEBUT_URL_JDBC+":"+DB_SERVER_URL+"/"+DB_SERVER_PORT+":"+pathDB);
		return  DEBUT_URL_JDBC+":"+DB_SERVER_URL+"/"+DB_SERVER_PORT+":"+pathDB;
	}
	static public Connection makeConnectSql(String pathDB)
	{
		//pathDB = pathDB.replaceFirst("/C:/", "C://");
		//String pathDB = DB_PATH +"/"+DB_NAME+".FDB";
		if(mapConnect.get(pathDB)==null){
		//if(connection==null){

			try {
				Connection connection = DriverManager.getConnection(
						DEBUT_URL_JDBC+":"+DB_SERVER_URL+"/"+DB_SERVER_PORT+":"+pathDB, 
						DB_USER,
						DB_PASSWORD);
				System.out.println(DEBUT_URL_JDBC+":"+DB_SERVER_URL+"/"+DB_SERVER_PORT+":"+pathDB);
				mapConnect.put(pathDB, connection);
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		keyMap = pathDB;
		return mapConnect.get(pathDB);
	}

	static public void Deconnection(String pathDB)
	{
		//if(connection!=null){
		if(mapConnect.get(pathDB)!=null){
			try {
				mapConnect.get(pathDB).close();//mapConnect.get(pathDB) synonym un objet qui correspond a cette key
				mapConnect.remove(pathDB);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}

	static public void DeconnectAll(){
		for (String nameDB : mapConnect.keySet()) {
			Deconnection(nameDB);
		}
	}
	public static Map<String, Connection> getMapConnect() {
		return mapConnect;
	}

	public static void setMapConnect(Map<String, Connection> mapConnect) {
		connectionDB.mapConnect = mapConnect;
	}

	public static String getKeyMap() {
		return keyMap;
	}
	
		
}
