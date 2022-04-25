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

public class ChampNumeroTel extends JFormattedTextField {
  private String masqueTel = "##-##-##-##-##";
  private char marquePlaceTel = '_';
  public ChampNumeroTel() {
    super();
    try {
      javax.swing.text.MaskFormatter dateMask = new javax.swing.text.MaskFormatter(masqueTel);
      dateMask.setPlaceholderCharacter(marquePlaceTel);
      dateMask.install(this);
    }
    catch (ParseException ex) {
      ex.printStackTrace();
    }
  }

}
