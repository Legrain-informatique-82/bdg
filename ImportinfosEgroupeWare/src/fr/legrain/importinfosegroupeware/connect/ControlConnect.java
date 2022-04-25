package fr.legrain.importinfosegroupeware.connect;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;



import fr.legrain.importinfosegroupeware.constant.ConstantPlugin;



public class ControlConnect {

	private static String MapKey = null;
	private static Map<String, Connection> MapConnection = new HashMap<String, Connection>(0);
	
	static Logger logger = Logger.getLogger(ControlConnect.class.getName());
	
	public Connection makeConnect(String TYPE_DB,String NAME_DB){
		
		
		if(TYPE_DB.equalsIgnoreCase(ConstantPlugin.MYSQL_DB)){
			try {
				Class.forName(ConstantPlugin.JDBC_DRIVER_MYSQL);
				//String url ="jdbc:mysql://localhost:3306/plugin_feature";
				String urlMysql = ConstantPlugin.JDBC+ConstantPlugin.MYSQL_DB+ "://" +ConstantPlugin.PATH_DB+
				ConstantPlugin.PORT_DB_MYSQL+ "/"+NAME_DB;
				//System.out.println(urlMysql);
				Connection connection = DriverManager.getConnection(urlMysql, ConstantPlugin.LOGIN_MYSQL,
						ConstantPlugin.PASSWORD_MYSQL);
				if(!MapConnection.containsKey(NAME_DB)){
					MapConnection.put(NAME_DB, connection);
				}
				
			}catch (Exception e) {
				// TODO Auto-generated catch block
//				e.printStackTrace();
				logger.error("", e);
			}
			
		}
		
		if(TYPE_DB.equalsIgnoreCase(ConstantPlugin.FIREBIRD_DB)){
			
			try {
				Class.forName(ConstantPlugin.JDBC_DRIVER_FIREBIRD);
				/**jdbc:firebirdsql:localhost/3050:/home/lee/runtime-GestionCommercialeComplet.product/dossier/Bd/GEST_COM.FDB*/
				String urlFirebird = ConstantPlugin.JDBC+ConstantPlugin.FIREBIRD_DB+":"+ConstantPlugin.PATH_LOCALHOST
									+"/"+3050+":"+NAME_DB;
				System.out.println(urlFirebird);
				Connection connection = DriverManager.getConnection(urlFirebird, ConstantPlugin.LOGIN_FIREBIRD, 
						ConstantPlugin.PASSWORD_FIREBIRD);
				if(!MapConnection.containsKey(NAME_DB)){
					MapConnection.put(NAME_DB, connection);
				}
			}catch (Exception e) {
				// TODO Auto-generated catch block
//				e.printStackTrace();
				logger.error("", e);
			}
		}
		
		MapKey = NAME_DB;
		return MapConnection.get(MapKey);
	}
	
	public void Deconnection(String NAME_DB){
		if(MapConnection.get(NAME_DB)!=null){
			try {
				MapConnection.get(NAME_DB).close();
				MapConnection.remove(NAME_DB);
			} catch (Exception e) {
				// TODO: handle exception
				logger.error("", e);
			}
		}		
	}
	public void Deconnection_All(){
		for (String NameDB : MapConnection.keySet()) {
			Deconnection(NameDB);
		}
	}
}
