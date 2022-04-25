package fr.legrain.gestCom.Appli;

import java.io.File;

import org.apache.log4j.Logger;

import com.borland.dx.sql.dataset.Database;

import fr.legrain.lib.data.BdLgr;
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
      fichierBdd = new File(Const.C_FICHIER_BDD).getCanonicalPath();
    }catch(Exception e){
      logger.error("Erreur : ", e);
    }
  }



  public Open_Base() {
    Database database = new Database();
    database.setConnection(new com.borland.dx.sql.dataset.ConnectionDescriptor(
      Const.C_URL_BDD+fichierBdd,Const.C_USER,Const.C_PASS , false, Const.C_DRIVER_JDBC));
    this.setFIBBase(database);
  }

}
