package ControleAction;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import Interface.ConstProjet;

public class ControlConnection {
	
	private static String MapKey = null;
	private static Map<String, Connection> MapConnection = new HashMap<String, Connection>(0);
	
	public static Connection makeConnection(String TYPE_DB,String NAME_DB){
		
		if(MapConnection.get(NAME_DB) == null){
			/**
			 * for connect mysql
			 */
			if(TYPE_DB.equalsIgnoreCase(ConstProjet.MYSQL_DB)){
				try {
					Class.forName(ConstProjet.DRIVER_JDBC_MYSQL);
					//String url ="jdbc:mysql://localhost:3306/plugin_feature";
					String URL = ConstProjet.JDBC+ConstProjet.MYSQL_DB+ "://" +ConstProjet.LOCALHOST+
					ConstProjet.PORT_DB_MYSQL+ "/" +NAME_DB;

					Connection connDB = DriverManager.getConnection(URL, ConstProjet.USERNAME, ConstProjet.PASSWORD);
					
					MapConnection.put(NAME_DB, connDB);
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		MapKey = NAME_DB;
		return MapConnection.get(MapKey);
	}
	public static void Deconnection(String NAME_DB){
		if(MapConnection.get(NAME_DB)!=null){
			try {
				MapConnection.get(NAME_DB).close();
				MapConnection.remove(NAME_DB);
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		
	}
	public static void Deconnection_All(){
		for (String NameDB : MapConnection.keySet()) {
			Deconnection(NameDB);
		}
	}

}
