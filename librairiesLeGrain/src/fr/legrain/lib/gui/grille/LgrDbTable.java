package fr.legrain.lib.gui.grille;

import javax.swing.event.*;
import fr.legrain.lib.data.*;

/**
 * <p>Title: </p>
 * <p>Description: Table "de base" pouvant être reliée à une base de données.</p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: Le Grain SA</p>
 * @author nicolas
 * @version 1.0
 * @todo A FAIRE/FINIR
 */

public class LgrDbTable extends LgrTable {

  private IBQuLgr data;

  public LgrDbTable() {
  //  data.getFIBQuery().first();
  //  data.getQuery_Champ_Obj();
  }

  public LgrDbTable(IBQuLgr data) {
    this.data = data;
  }

  public void valueChanged(ListSelectionEvent e) {
    int lignePrecedente = ligneCourante;
    super.valueChanged(e);
    //System.out.println("lignePrecedente : "+lignePrecedente+" ligneCourante : "+ligneCourante);
//    if(lignePrecedente>ligneCourante) {
//      data.previous();
//    } else if (lignePrecedente<ligneCourante) {
//      data.next();
//    }
    //data.relative(ligneCourante);
  }

  public void setData(IBQuLgr data) {
    this.data = data;
  }

  public IBQuLgr getData() {
    return data;
  }

}
