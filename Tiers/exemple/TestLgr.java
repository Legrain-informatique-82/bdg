package fr.legrain.tiers.actions;

import com.borland.dx.sql.dataset.*;

import fr.legrain.lib.data.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: Le Grain SA</p>
 * @author Le Grain SA
 * @version 1.0
 */

public class TestLgr extends IBQuLgr {
  public TestLgr() {
  }

  public void test() {
    try{
      Database d = new Database();
      d.setConnection(new com.borland.dx.sql.dataset.ConnectionDescriptor(
          "jdbc:firebirdsql://localhost/C:/Projet/Java/PrototypeFormulaire/Bd/bdtiers.gdb",
          "SYSDBA", "###_PASSWORD_FB_BDG_###", false, "org.firebirdsql.jdbc.FBDriver"));
      this.setFIBBase(d);
      this.fIBQuery.setQuery(new QueryDescriptor(d,"select * from tiers",null,true,Load.ASYNCHRONOUS));
      this.fIBQuery.open();
    }
    catch(Exception e) {
      e.printStackTrace();
    }
  }
  
  public QueryDataSet getQuery() {
	  return this.fIBQuery;
  }

  /**
   * init
   * @param objetAppelant Object
   * @param param ParamInitIBQuLgr
   * @return ResultInitIBQuLgr
   */
  public ResultInitIBQuLgr init(Object objetAppelant, ParamInitIBQuLgr param) {
    return null;
  }
}
