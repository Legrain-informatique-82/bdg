package fr.legrain.lib.gui.grille;

import javax.swing.table.*;
import java.text.*;
import javax.swing.text.*;

/**
 * <p>Title: </p>
 * <p>Description: Afficheur de cellule pour le format monétaire</p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: Le Grain SA</p>
 * @author nicolas
 * @version 1.0
 */

public class LgrCurrencyRenderer extends DefaultTableCellRenderer {
  public LgrCurrencyRenderer() {
  }

  protected void setValue(Object value) {
    NumberFormat currencyFormat = NumberFormat.getCurrencyInstance();
        NumberFormatter intFormatter = new NumberFormatter(currencyFormat);
        intFormatter.setFormat(currencyFormat);
       // new DefaultFormatterFactory(intFormatter.).

      //  ftf.setFormatterFactory(new DefaultFormatterFactory(intFormatter));
        this.setHorizontalAlignment(this.RIGHT);


   // setText( (value == null) ? "" : value.toString());
    try {
      //setText(currencyFormat.format(value.toString()));
      setText(intFormatter.valueToString(value));
    }
    catch (ParseException ex) {
      ex.printStackTrace();
    }
  }

}
