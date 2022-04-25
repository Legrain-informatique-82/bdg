/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package fr.legrain.fonctions;

import fr.legrain.classDB.WsTaNombreConnect;
import fr.legrain.constant.constantProjetOSC;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author lee
 */
public class fonctionProjetOSC {

    private int id ;
    private String login = null;
    private String password = null;
    private int nombreConnection;
    private long quand_connect;

    public boolean verificationClient(Connection connection,String loginClient,String passwordClient){
        boolean flag = false;
        try {
            Statement statement = connection.createStatement();
            ResultSet resultset = statement.executeQuery(constantProjetOSC.SELECT_WS_TA_NOMBRE_CONNECT);
            while (resultset.next()) {
                login = resultset.getString(constantProjetOSC.COLUMN_NAME_TA_USER_LOGIN);
                password = resultset.getString(constantProjetOSC.COLUMN_NAME_TA_USER_PASSWORD);
                if(loginClient.equals(login) && passwordClient.equals(password)){
                    flag = true;
                    nombreConnection = resultset.getInt(constantProjetOSC.COLUMN_NAME_TA_NOMBRE_CONNECT);
                    id = resultset.getInt("id");
                    quand_connect = resultset.getDate("quand_connect").getTime();
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(fonctionProjetOSC.class.getName()).log(Level.SEVERE, null, ex);
        }
        return flag;
    }

   public void updateWsTaNombreConnect(){
       WsTaNombreConnect wsTaNombreConnect = new WsTaNombreConnect();
       wsTaNombreConnect.setLoginUser("lee");
       wsTaNombreConnect.setPasswordUser("4411757");
       wsTaNombreConnect.setNombreConnect(9);
       wsTaNombreConnect.setQuandConnect(new Date());

       EntityManagerFactory emf = Persistence.createEntityManagerFactory("LegrainWSOscPU");
       EntityManager em = emf.createEntityManager();
       em.getTransaction().begin();
       em.persist(wsTaNombreConnect);
       em.getTransaction().commit();
       em.close();
   }
   public void createTableWS(Connection connection,String sqlCreateTable){
        try {
            Statement statement = connection.createStatement();
            statement.execute(sqlCreateTable);
            statement.close();
        } catch (SQLException ex) {
            Logger.getLogger(fonctionProjetOSC.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        }
   }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long  getQuand_connect() {
        return quand_connect;
    }

    public void setQuand_connect(long quand_connect) {
        this.quand_connect = quand_connect;
    }

   
    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public int getNombreConnection() {
        return nombreConnection;
    }

    public void setNombreConnection(int nombreConnection) {
        this.nombreConnection = nombreConnection;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    

}
