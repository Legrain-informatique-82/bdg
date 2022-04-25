/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package fr.legrain.connect;

import fr.legrain.constant.constantProjetOSC;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author lee
 */
public class controlConnectOSC {

    private static String mapConnectKey = null;
    private static Map<String, Connection> mapConnect = new HashMap<String, Connection>(0);

    /**
     * @param typeDB type of database
     * @param nameDB name of database
     * @return java.sql.Connection
     */
    public static Connection makeConnect(String typeDB, String nameDB,String login,String password) {

        if(mapConnect.get(nameDB) == null){
            if(typeDB.equalsIgnoreCase(constantProjetOSC.MYSQL_DB)){
                try {
                    Class.forName(constantProjetOSC.DRIVER_JDBC_MYSQL);
                    String url = constantProjetOSC.JDBC + constantProjetOSC.MYSQL_DB + "://" +
                        constantProjetOSC.LOCALHOST + constantProjetOSC.PORT_DB_MYSQL + "/" + nameDB;
                    //like url ==> jdbc:mysql://localhost:3306/nameDB
                    Connection connDB = DriverManager.getConnection(url, login, password);
                    mapConnect.put(nameDB, connDB);
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(controlConnectOSC.class.getName()).log(Level.SEVERE, null, ex);
                } catch (SQLException ex) {
                    Logger.getLogger(controlConnectOSC.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        mapConnectKey = nameDB;
        return mapConnect.get(mapConnectKey);
    }

     /**
     *
     * @param nameDB is the name of database
     */
    public static void disconnect(String nameDB) {
        if (mapConnect.get(nameDB) != null) {
            try {
                mapConnect.get(nameDB).close();
                mapConnect.remove(nameDB);
            } catch (SQLException ex) {
                Logger.getLogger(controlConnectOSC.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
