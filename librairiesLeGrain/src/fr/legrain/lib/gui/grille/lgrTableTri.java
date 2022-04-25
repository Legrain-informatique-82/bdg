package fr.legrain.lib.gui.grille;

import java.math.BigDecimal;

import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.log4j.Logger;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerSorter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;

public class lgrTableTri extends LgrTableViewer {

	static Logger logger = Logger.getLogger(LgrTableViewer.class.getName());
	
	static private PropertiesConfiguration listeChampGrille = new PropertiesConfiguration();
	private String[] champsObjetCompare;
	private String[] titresColonnes;
	private Class typeObjetCompare;
	private TableColumn column = null;
	
	public lgrTableTri(Composite parent) {
		super(parent);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * {@inheritDoc}
	 */
	public lgrTableTri(Composite parent, int style) {
		super(parent, style);
	}

	public lgrTableTri(Table table) {
		super(table);
	}
	
	public void createTableCol(Class typeObjetCompare,Table grille, String nomClasse, String nomFichierChamps,int colonneTri ){
		super.createTableCol(typeObjetCompare, grille, nomClasse, nomFichierChamps, colonneTri);
	}
	
	public void createTableCol(Table grille, String nomClasse, String nomFichierChamps ){
		super.createTableCol(grille, nomClasse, nomFichierChamps);
	}

	
	private class LgrSorter extends ViewerSorter {
		private TableColumn column = null;
		private int dir = SWT.DOWN;
		private int i = -1;
		private boolean trouve = false;

		/**
		 * @param column - colonne sur laquelle porte le tri
		 * @param dir - sens du tri (SWT.UP/SWT.DOWN)
		 */
		public LgrSorter(TableColumn column, int dir) {
			super();
			this.column = column;
			this.dir = dir;

			//Recherche du nom du champs/proprietes de l'objet correspondant à la colonne
			i = -1;
			trouve = false;
			while(!trouve && i<titresColonnes.length) {
				i++;
				if(this.column.getText().equals(titresColonnes[i])){
					trouve = true;
				}
			}

		}

		/* (non-Javadoc)
		 * @see org.eclipse.jface.viewers.ViewerComparator#compare(org.eclipse.jface.viewers.Viewer, java.lang.Object, java.lang.Object)
		 */
		public int compare(Viewer viewer, Object e1, Object e2) {
			try {				
				int returnValue = 0;
						returnValue = ((String)e1).compareTo((String)e2);
					if (this.dir == SWT.DOWN) {
						returnValue = returnValue * -1;
					}
				return returnValue;
			} catch(Exception e) {
				logger.error("type d'objet non implémenté",e);
				return 0;
			}
		}
	}

}
