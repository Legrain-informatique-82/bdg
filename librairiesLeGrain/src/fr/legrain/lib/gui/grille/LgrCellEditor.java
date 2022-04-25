package fr.legrain.lib.gui.grille;

import javax.swing.*;

/**
 * <p>Title: </p>
 * <p>Description: Editeur de cellule</p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: Le Grain SA</p>
 * @author nicolas
 * @version 1.0
 */

abstract public class LgrCellEditor extends DefaultCellEditor {

  public LgrCellEditor(JCheckBox checkBox) {
    super(checkBox);
  }

  public LgrCellEditor(JComboBox comboBox) {
    super(comboBox);
  }

  public LgrCellEditor(JTextField textField) {
    super(textField);
  }

  /**
   * Fonction de validation appellée avant de sortir de l'éditeur de la cellule
   * @return boolean - vrai ssi le contenu de la cellule est correct
   */
  abstract public boolean valider();

  public boolean stopCellEditing() {
    if (!valider()) {
      return false;
    }
    else {
      return super.stopCellEditing(); //return true;
    }
  }

}
