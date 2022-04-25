package fr.legrain.lib.gui.grille;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.*;
//import org.jdesktop.swing.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: Le Grain SA</p>
 * @author nicolas
 * @version 1.0
 */
/*
public class LgrDateCellEditor extends AbstractCellEditor implements TableCellEditor {

  private JXDatePicker datePicker = new JXDatePicker();

  public LgrDateCellEditor() {
    datePicker = (JXDatePicker) getComponent();
//    datePicker.setDateFormatterFactory();
  }

  public Component getComponent() {
    return datePicker;
  }

  //Override to invoke setValue on the formatted text field.
  public Component getTableCellEditorComponent(JTable table,
                                               Object value, boolean isSelected,
                                               int row, int column) {
    return datePicker;
  }

  //Override to ensure that the value remains an Integer.
  public Object getCellEditorValue() {
    JXDatePicker dp = (JXDatePicker) getComponent();
    return dp.getDate();
  }

  //Override to check whether the edit is valid,
  //setting the value if it is and complaining if
  //it isn't.  If it's OK for the editor to go
  //away, we need to invoke the superclass's version
  //of this method so that everything gets cleaned up.
  public boolean stopCellEditing() {
    JXDatePicker dp = (JXDatePicker) getComponent();
    if (dp.isEditValid()) {
      try {
        dp.commitEdit();
      }
      catch (java.text.ParseException exc) {
        exc.printStackTrace();
      }
    }
    else { //text is invalid
      return true;
    }
    return super.stopCellEditing();
  }

}
*/