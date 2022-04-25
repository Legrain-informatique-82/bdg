package fr.legrain.lib.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ConnectionJDBC {
	
	private static final Log logger = LogFactory.getLog(ConnectionJDBC.class);
	
	private static Connection cnx = null;
	
	private ConnectionJDBC(){}
	
	static public Connection getConnection(String url,String user,String password,String driver) {
		try {
			if(cnx==null) {
				Class.forName(driver);
				cnx = DriverManager.getConnection(url,user,password);
				cnx.setAutoCommit(false);
			}
			return cnx;
		 
		} catch (ClassNotFoundException e) {
			logger.error("",e);
		} catch (SQLException e) {
			logger.error("",e);
		}
		return null;
	}
	
	static public boolean closeConnection() {
		boolean retour = false;
		try {
			if(cnx!=null) {
				cnx.close();
				cnx=null;
				retour = true;
			}
			return retour;
		} catch (SQLException e) {
			logger.error("",e);
			return retour;
		}
	}


}
