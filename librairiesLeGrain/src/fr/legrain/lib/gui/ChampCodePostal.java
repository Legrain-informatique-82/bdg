package fr.legrain.lib.gui;

import javax.swing.JFormattedTextField;
import java.text.*;

/**
 * @todo FINIR
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: Le Grain SA</p>
 * @author nicolas
 * @version 1.0
 */

public class ChampCodePostal extends JFormattedTextField {
  private String masqueCP = "######";
  private char marquePlaceCP = '_';

  public ChampCodePostal() {
    try {
      javax.swing.text.MaskFormatter dateMask = new javax.swing.text.MaskFormatter(masqueCP);
      dateMask.setPlaceholderCharacter(marquePlaceCP);
      dateMask.install(this);
    }
    catch (ParseException ex) {
      ex.printStackTrace();
    }

  }

}
