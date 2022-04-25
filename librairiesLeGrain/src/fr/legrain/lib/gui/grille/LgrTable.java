package fr.legrain.lib.gui.grille;

import javax.swing.*;
import javax.swing.event.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: Le Grain SA</p>
 * @author nicolas
 * @version 1.0
 */

public class LgrTable extends JTable {

  protected EventListenerList listenerList = new EventListenerList();
  protected LgrTableEvent lgrTableEvent = null;

  protected boolean validationLigne = true;
  protected boolean validationCellule = true;
//  private LgrGrille laGrille = null;

  protected int ligneCourante = 0;

  public LgrTable() {

   // laGrille = this;
    this.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

///////////////////////////////////////////////////////////////////////////////
/*
   ListSelectionModel rowSM = this.getSelectionModel();
   rowSM.addListSelectionListener(new ListSelectionListener() {
     public void valueChanged(ListSelectionEvent e) {

       if (e.getValueIsAdjusting())return; //Ignore extra messages.

       ListSelectionModel lsm = (ListSelectionModel) e.getSource();
       if (lsm.isSelectionEmpty()) {
         System.out.println("Pas de ligne sélectionnée.");
       }
       else {
         int selectedRow = lsm.getMinSelectionIndex();
         System.out.println("Ligne sélectionnée : " + selectedRow);
         valideLigne();
         ligneCourante = laGrille.getSelectedRow();
       }
     }
   });
*/
////////////////////////////////////////////////////////////////////////////////
  }

  public void tableChanged(TableModelEvent e){
    super.tableChanged(e);
  //  System.out.println("tableChanged =>"
  //                     + "\n  type : "+e.getType()
  //                     + "\n  col : "+ e.getColumn()
  //                     + "\n  ligDeb : "+ e.getFirstRow()
  //                     + "\n  ligFin : "+ e.getFirstRow());
  }

  public void valueChanged(ListSelectionEvent e) {
    super.valueChanged(e);
    if (e.getValueIsAdjusting())return; //Ignore extra messages.

    ListSelectionModel lsm = (ListSelectionModel) e.getSource();
    if (lsm.isSelectionEmpty()) {
    //  System.out.println("Pas de ligne sélectionnée.");
    }
    else {
      fireSelectionChange();
      int selectedRow = lsm.getMinSelectionIndex();
      System.out.println("Ligne sélectionnée : " + selectedRow);
      if(validationLigne)
        validerLigne();
      //ligneCourante = laGrille.getSelectedRow();
      ligneCourante = this.getSelectedRow();
    }
  }

  //validation
  /**
   * @todo A finir
   * Le mode de sélection doit être sur <code>ListSelectionModel.SINGLE_SELECTION</code>
   */
  public void validerLigne() {
    //Ask to be notified of selection changes.
    //this.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    for(int i = 0; i < this.getColumnCount(); i++){
      this.getValueAt(ligneCourante,i);
    }
  }




  public void addLgrTableListener(LgrTableListener l) {
    listenerList.add(LgrTableListener.class, l);
  }

  public void removeLgrTableListener(LgrTableListener l) {
    listenerList.remove(LgrTableListener.class, l);
  }

// Notify all listeners that have registered interest for
// notification on this event type.  The event instance
// is lazily created using the parameters passed into
// the fire method.
  protected void fireSelectionChange() {
    // Guaranteed to return a non-null array
    Object[] listeners = listenerList.getListenerList();
    // Process the listeners last to first, notifying
    // those that are interested in this event
    for (int i = listeners.length - 2; i >= 0; i -= 2) {
      if (listeners[i] == LgrTableListener.class) {
        // Lazily create the event:
        if (lgrTableEvent == null)
          lgrTableEvent = new LgrTableEvent(this);
        ( (LgrTableListener) listeners[i + 1]).selectionChange(lgrTableEvent);
      }
    }
  }



}
