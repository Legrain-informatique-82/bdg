package fr.legrain.lib.gui.grille;

import javax.swing.table.*;
import java.util.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: Le Grain SA</p>
 * @author nicolas
 * @version 1.0
 */

public class LgrTableModel extends DefaultTableModel {

  /**
   * Constructs a default DefaultTableModel which is a table of zero columns and zero rows.
   */
  public LgrTableModel(){
    super();
  }

  /**
   * Constructs a DefaultTableModel with rowCount and columnCount of null object values.
   * @param rowCount int
   * @param columnCount int
   */
   public LgrTableModel(int rowCount, int columnCount){
    super(rowCount,columnCount);
  }

  /**
   * Constructs a DefaultTableModel and initializes the table by passing data and columnNames to the setDataVector method.
   * @param data Object[][]
   * @param columnNames Object[]
   */
   public LgrTableModel(Object[][] data, Object[] columnNames){
    super(data,columnNames);
  }

  /**
   * Constructs a DefaultTableModel with as many columns as there are elements in columnNames and rowCount of null object values.
   * @param columnNames Object[]
   * @param rowCount int
   */
   public LgrTableModel(Object[] columnNames, int rowCount){
    super(columnNames,rowCount);
  }

  /**
   * Constructs a DefaultTableModel with as many columns as there are elements in columnNames and rowCount of null object values.
   * @param columnNames Vector
   * @param rowCount int
   */
   public LgrTableModel(Vector columnNames, int rowCount) {
    super(columnNames,rowCount);
  }

  /**
   * Constructs a DefaultTableModel and initializes the table by passing data and columnNames to the setDataVector method.
   * @param data Vector
   * @param columnNames Vector
   */
   public LgrTableModel(Vector data, Vector columnNames){
     super(data,columnNames);
   }

   /**
    * Détermine l'afficheur/éditeur pour chaque cellule/colonne.
    * Si cette méthode n'est pas surchargée, tous les afficheur/éditeur sont des label/textfield
    * @param c int - position de la colonne
    * @return Class - classe de l'objet dans la cellule
    */
   public Class getColumnClass(int c) {
     return getValueAt(0, c).getClass();
   }

}
