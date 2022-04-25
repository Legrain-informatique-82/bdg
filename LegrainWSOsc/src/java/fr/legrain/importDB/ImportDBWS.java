/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package fr.legrain.importDB;

import fr.legrain.classDB.WsTaNombreConnect;
import fr.legrain.connect.controlConnectOSC;
import fr.legrain.constant.constantProjetOSC;
import fr.legrain.fonctions.fonctionProjetOSC;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

/**
 *
 * @author lee
 */
@WebService()
public class ImportDBWS {

    /**
     * Web service operation
     */
    @WebMethod(operationName = "checkDBclient")
    public List<WsTaNombreConnect> checkDBclient(@WebParam(name = "login") String login,
                                 @WebParam(name = "password")String password,
                                 @WebParam(name = "dateStart") long dateStart,
                                 @WebParam(name = "dateFin") long dateFin) {
        boolean flag = false;
        List<WsTaNombreConnect> a = new ArrayList<WsTaNombreConnect>();
        //TODO write your implementation code here:
        Connection connection = controlConnectOSC.makeConnect(constantProjetOSC.MYSQL_DB, constantProjetOSC.NAME_DB_WS_GENBOUCOM,
                constantProjetOSC.LOGIN, constantProjetOSC.PASSWORD);
        fonctionProjetOSC objectFonction = new fonctionProjetOSC();
        flag = objectFonction.verificationClient(connection, login, password);
        if(flag && objectFonction.getNombreConnection()==0){
//             for (String nameTable : constantProjetOSC.ARRAY_CREATE_WS_TA) {
//                objectFonction.createTableWS(connection,nameTable);
//            }
            WsTaNombreConnect test = new WsTaNombreConnect(objectFonction.getId(),0, objectFonction.getLogin(), objectFonction.getPassword(),
                                                           new Date(objectFonction.getQuand_connect()));
            System.out.println(objectFonction.getId()+"--"+objectFonction.getLogin()+"--"+objectFonction.getPassword()+"--"+objectFonction.getQuand_connect()
                                        +"--");
            objectFonction.updateWsTaNombreConnect();
            a.add(test);
        }
        System.out.println("heffre");
        return a;
    }

}
