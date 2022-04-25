package fr.legrain.lib.gui.grille;

import java.text.*;
import java.awt.*;
import javax.swing.*;
import javax.swing.text.*;

/**
 * <p>Title: </p>
 * <p>Description: Editeur de cellule pour le format monétaire</p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: Le Grain SA</p>
 * @author nicolas
 * @version 1.0
 */

public class LgrCurrencyEditor extends DefaultCellEditor {

    JFormattedTextField ftf;
    NumberFormat currencyFormat;
    NumberFormat floatFormat;

    public LgrCurrencyEditor() {
        super(new JFormattedTextField());
        ftf = (JFormattedTextField)getComponent();

        //Set up the editor for the integer cells.
        currencyFormat = NumberFormat.getCurrencyInstance();
        NumberFormatter currencyFormatter = new NumberFormatter(currencyFormat);
        currencyFormatter.setFormat(currencyFormat);

        floatFormat = NumberFormat.getNumberInstance();
        floatFormat.setMaximumFractionDigits(2);
        floatFormat.setMinimumFractionDigits(2);
        NumberFormatter floatFormatter = new NumberFormatter(floatFormat);

        ftf.setFormatterFactory(new DefaultFormatterFactory(floatFormatter));

     //   ftf.setFormatterFactory(new DefaultFormatterFactory(intFormatter));
        ftf.setHorizontalAlignment(JTextField.TRAILING);
        ftf.setFocusLostBehavior(JFormattedTextField.PERSIST);

        //React when the user presses Enter while the editor is
        //active.  (Tab is handled as specified by
        //JFormattedTextField's focusLostBehavior property.)
   /*
        ftf.getInputMap().put(KeyStroke.getKeyStroke(
                                        KeyEvent.VK_ENTER, 0),
                                        "check");
        ftf.getActionMap().put("check", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                if (!ftf.isEditValid()) { //The text is invalid.
                    if (userSaysRevert()) { //reverted
                        ftf.postActionEvent(); //inform the editor
                    }
                } else try {              //The text is valid,
                    ftf.commitEdit();     //so use it.
                    ftf.postActionEvent(); //stop editing
                } catch (java.text.ParseException exc) { }
            }
        });
    */
    }

    //Override to invoke setValue on the formatted text field.
    public Component getTableCellEditorComponent(JTable table,
            Object value, boolean isSelected,
            int row, int column) {
        JFormattedTextField ftf =
            (JFormattedTextField)super.getTableCellEditorComponent(
                table, value, isSelected, row, column);
        ftf.setValue(value);
        return ftf;
    }

    //Override to ensure that the value remains an Integer.
    public Object getCellEditorValue() {
        JFormattedTextField ftf = (JFormattedTextField)getComponent();
        Object o = ftf.getValue();
       // if (o instanceof Integer) {
            return o;
      //  } else if (o instanceof Number) {
      //      return new Integer(((Number)o).intValue());
      //  } else {
      //      try {
      //          return currencyFormat.parseObject(o.toString());
      //      } catch (ParseException exc) {
      //          System.err.println("getCellEditorValue: can't parse o: " + o);
      //          return null;
      //      }
      //  }
    }

    //Override to check whether the edit is valid,
    //setting the value if it is and complaining if
    //it isn't.  If it's OK for the editor to go
    //away, we need to invoke the superclass's version
    //of this method so that everything gets cleaned up.
    public boolean stopCellEditing() {
        JFormattedTextField ftf = (JFormattedTextField)getComponent();

        //NumberFormatter floatFormatter = new NumberFormatter(floatFormat);
        //ftf.setFormatterFactory(new DefaultFormatterFactory(floatFormatter));

        if (ftf.isEditValid()) {
            try {
                ftf.commitEdit();
            } catch (java.text.ParseException exc) {exc.printStackTrace();}

        } else { //text is invalid
           // if (!userSaysRevert()) { //user wants to edit
                return false; //don't let the editor go away
           // }
        }
        return super.stopCellEditing();
    }
}
