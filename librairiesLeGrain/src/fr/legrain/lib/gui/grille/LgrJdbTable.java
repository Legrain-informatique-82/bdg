package fr.legrain.lib.gui.grille;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.KeyStroke;
import javax.swing.event.EventListenerList;
import javax.swing.event.ListSelectionEvent;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;

import org.apache.log4j.Logger;

import com.borland.dbswing.CustomColumnsDescriptor;
import com.borland.dbswing.DBExceptionHandler;
import com.borland.dbswing.JdbTable;
import com.borland.dbswing.TableRowHeader;
import com.borland.dx.dataset.AccessEvent;
import com.borland.dx.dataset.DataChangeEvent;
import com.borland.dx.dataset.DataSet;
import com.borland.dx.dataset.DataSetException;
import com.borland.dx.dataset.NavigationEvent;
import com.borland.dx.dataset.StorageDataSet;
import com.borland.dx.sql.dataset.QueryDataSet;

import fr.legrain.lib.data.IBQuLgr;

/**
 * <p>Title: </p>
 * <p>Description: Extension de la grille du package dbswing de JBuilder.</p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: Le Grain SA</p>
 * @author nicolas
 * @see com.borland.dbswing.JdbTable
 * @version 1.0
 */
public class LgrJdbTable extends JdbTable {
	
	static Logger logger = Logger.getLogger(LgrJdbTable.class.getName());
	
	protected EventListenerList listenerList = new EventListenerList();
	
	public boolean error = false;
	private int currentRow = 0; // derniere ligne selectionnée et encore non validée
	private IBQuLgr iBQuLgr = null; //dataset relié à la table
	
	/**
	 * Lecture des nom des champs de la grille et affectation pour chaque colonne
	 * @param iBQuLgr - <code>IBQuLgr</code> qui contient le DataSet de la grille
	 */
	public void initNomColonne(IBQuLgr iBQuLgr) {
		initNomColonne(iBQuLgr.getFIBQuery());
	}
	
	public LgrJdbTable() {
		super();
	}
	
	public LgrJdbTable(TableModel model) {
		super(model);
	}
	
	public IBQuLgr getIBQuLgr() {
		return iBQuLgr;
	}

	public void setIBQuLgr(IBQuLgr quLgr) {
		iBQuLgr = quLgr;
		this.setDataSet(iBQuLgr.getFIBQuery());
		this.initNomColonne(quLgr);
	}
	
	@Override
	public void setDataSet(DataSet dataSet) {
		super.setDataSet(dataSet);
		if(dataSet instanceof QueryDataSet) {
			this.initNomColonne((QueryDataSet)dataSet);
		}
	}

	@Override
	public void navigated(NavigationEvent e) {
		// TODO Auto-generated method stub
		super.navigated(e);
		if(this.getDataSet()!=null) {
			this.initNomColonne((QueryDataSet)this.getDataSet());
		}
	}

	/**
	 * Lecture des nom des champs de la grille et affectation pour chaque colonne
	 * @param iBQuLgr - <code>IBQuLgr</code> qui contient le DataSet de la grille
	 */
	public void initNomColonne(QueryDataSet query) {
		try {
			
			for(int i =0; i<query.getColumnCount(); i++) {
				if(query.getColumn(i).getColumnName()==null)
					if(logger.isDebugEnabled())
						logger.debug("Il n'y a pas de titre pour la colonne "+query.getColumn(i).getColumnName()+" dans le fichier des titres");
				query.getColumn(i).setCaption(query.getColumn(i).getColumnName()
						);
			}
		} catch (Exception e) {
			logger.debug("",e);
		}
	}
	
	/**
	 * Affiche une valeur booleenne : entier (0 ou 1) sous la forme d'une JCheckBox
	 * @author nicolas
	 *
	 */
	class LgrTableCellBooleanRenderer extends JPanel implements javax.swing.table.TableCellRenderer {
		private JCheckBox cb = new JCheckBox();
		
		public LgrTableCellBooleanRenderer() {
			super();
			this.add(cb);
			this.setLayout(new FlowLayout(FlowLayout.CENTER,0,0));
		}
		
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
			if(isSelected) {
				this.setBackground(table.getSelectionBackground());
				cb.setBackground(table.getSelectionBackground());
			} else {
				this.setBackground(table.getBackground());
				cb.setBackground(table.getBackground());
			}
			if(value!=null && value.toString().equals("1"))
				cb.setSelected(true);
			else
				cb.setSelected(false);
			return this;
		}
	}
	
	/**
	 * 
	 * @param posColBooleen - position des colonnes contenant des valeurs booleennes
	 */
	public void initBooleanRenderer(int[] posColBooleen) {
		TableColumn[] colonnes = new TableColumn[posColBooleen.length];
		TableColumn c;
		int[] posColonne = new int[posColBooleen.length];
		for (int i=0;i<posColBooleen.length;i++) {
			c = new TableColumn();
			c.setCellRenderer(new LgrTableCellBooleanRenderer());
			c.setModelIndex(posColBooleen[i]);
			colonnes[i]=c;
			posColonne[i]=posColBooleen[i];
		}
		
		CustomColumnsDescriptor cd = new CustomColumnsDescriptor(
				posColonne,
				colonnes
		);
		
		this.setCustomColumns(cd);
	}
	
	public TableColumn[] initTailleColonne(int[][] tailles) {
		TableColumn[] colonnes = new TableColumn[tailles.length];
		TableColumn c;
		for (int i=0;i<tailles.length;i++) {
			c = new TableColumn();
			c.setMinWidth(tailles[i][0]);
			c.setMaxWidth(tailles[i][1]);
			c.setPreferredWidth(tailles[i][2]);
			colonnes[i]=c;
		}
		return colonnes;
	}
	
	public void initCustomColumns(int[][] tailles) {
		TableColumn[] colonnes = new TableColumn[tailles.length];
		TableColumn c;
		int[] posColonne = new int[tailles.length];
		for (int i=0;i<tailles.length;i++) {
			c = new TableColumn();
			c.setMinWidth(tailles[i][0]);
			c.setMaxWidth(tailles[i][1]);
			c.setPreferredWidth(tailles[i][2]);
			c.setModelIndex(i);
			colonnes[i]=c;
			posColonne[i]=i;
		}
		
		CustomColumnsDescriptor cd = new CustomColumnsDescriptor(
				posColonne,
				colonnes
		);
		
		this.setCustomColumns(cd);
	}
	
	public void addLgrJdbTableListener(LgrJdbTableListener l) {
		listenerList.add(LgrJdbTableListener.class, l);
	}
	
	public void removeLgrJdbTableListener(LgrJdbTableListener l) {
		listenerList.remove(LgrJdbTableListener.class, l);
	}
	
	protected void fireDerniereLigneAtteinte(LgrJdbTableEvent e) {
		Object[] listeners = listenerList.getListenerList();
		for (int i = listeners.length - 2; i >= 0; i -= 2) {
			if (listeners[i] == LgrJdbTableListener.class) {
				if (e == null)
					e = new LgrJdbTableEvent(this);
				( (LgrJdbTableListener) listeners[i + 1]).dernierLigneAtteinte(e);
			}
		}
	}
		
	@Override
	public void updateUI() {
		// TODO Raccord de méthode auto-généré
		super.updateUI();
		unregisterKeyboardAction(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0));
		unregisterKeyboardAction(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0));
		
		registerKeyboardAction(new EnterAction(),KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0),
				JComponent.WHEN_FOCUSED);
		registerKeyboardAction(new AppendRowAction(),KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0),
				JComponent.WHEN_FOCUSED);
	}
	
	private class EnterAction extends AbstractAction {
		
		public void actionPerformed(ActionEvent e) {		
			if (getThis().isEditable() &&
					getThis().getDataSet() != null && getThis().getDataSet().isEnableInsert() &&
					!getThis().getDataSet().isEditingNewRow() && getThis().getDataSet().isEditable()) {
				boolean readOnly = false;
				if (getThis().getDataSet() instanceof StorageDataSet) {
					readOnly = ((StorageDataSet) getThis().getDataSet()).isReadOnly();
				}
				if (!readOnly) {
					try {
						if (getThis().getDataSet().atLast()) {
							getThis().getDataSet().insertRow(false);
						}
						else {
							getThis().getActionMap().get("selectNextColumnCell").actionPerformed(e);
						}
					}
					catch (DataSetException ex) {
						DBExceptionHandler.handleException(getThis().getDataSet(), getThis(), ex);
					}
				}
			}
			else {
				getThis().getActionMap().get("selectNextColumnCell").actionPerformed(e);
			}
		}
	}
	
	private JdbTable getThis() {
		return this;
	}
	
	private class AppendRowAction extends AbstractAction {
		private AppendRowAction() {
			super("append-row");   
		}
		
		public void actionPerformed(ActionEvent e) {
			if (getThis().isEditable() &&
					getThis().getDataSet() != null && getThis().getDataSet().isEnableInsert() &&
					!getThis().getDataSet().isEditingNewRow() && getThis().getDataSet().isEditable()) {
				boolean readOnly = false;
				if (getThis().getDataSet() instanceof StorageDataSet) {
					readOnly = ((StorageDataSet) getThis().getDataSet()).isReadOnly();
				}
				if (!readOnly) {
					try {
						if (getThis().getDataSet().atLast()) {
							getThis().getDataSet().insertRow(false);
						}
						else {
							getThis().getDataSet().next();
						}
					}
					catch (DataSetException ex) {
						DBExceptionHandler.handleException(getThis().getDataSet(), getThis(), ex);
					}
				} else if (getThis().getDataSet().atLast()) {
					fireDerniereLigneAtteinte(null);
				}
			} else if (getThis().getDataSet().atLast()) {
				fireDerniereLigneAtteinte(null);
			}
			else {
				getThis().getDataSet().next();
			}
		}
	}
	
}
