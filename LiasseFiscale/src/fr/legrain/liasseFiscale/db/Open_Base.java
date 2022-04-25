package fr.legrain.liasseFiscale.db;

import fr.legrain.lib.data.*;
import com.borland.dx.sql.dataset.*;
import java.io.*;
import org.apache.log4j.*;
/**
 * <p>Title: Gestion Commerciale</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: </p>
 * @author Le Grain A.A
 * @version 1.0
 */

public class Open_Base extends BdLgr {
  static Logger logger = Logger.getLogger(Open_Base.class.getName());
  public static String fichierBdd = null;


  static { /** @todo a modifier */
    try {
      fichierBdd = new File(ConstLiasse.C_FICHIER_BDD).getCanonicalPath();
    }catch(Exception e){
      logger.error("Erreur : ", e);
    }
  }



  public Open_Base() {
    Database database = new Database();
    database.setConnection(new com.borland.dx.sql.dataset.ConnectionDescriptor(
    		ConstLiasse.C_URL_BDD+fichierBdd,ConstLiasse.C_USER,ConstLiasse.C_PASS , false, ConstLiasse.C_DRIVER_JDBC));
    this.setFIBBase(database);
  }

}
