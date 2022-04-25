/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package fr.legrain.test;

import fr.legrain.connect.controlConnectOSC;
import fr.legrain.constant.constantProjetOSC;
import fr.legrain.fonctions.fonctionProjetOSC;
import fr.legrain.importDB.ImportDBWS;
import java.util.Date;

/**
 *
 * @author lee
 */
public class testWSOsc {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        ImportDBWS testImportDBWS = new ImportDBWS();
        testImportDBWS.checkDBclient("genboucom","1234",new Date().getTime(),new Date().getTime());
//        boolean a = testImportDBWS.checkDBclient("genboucom","1234",new Date().getTime(),new Date().getTime());
//        if(a){
//           System.out.println("find");
//           //fonctionProjetOSC objet = new fonctionProjetOSC();
//
//
//        }else{
//            System.out.println("not find");
//        }
    }

}
