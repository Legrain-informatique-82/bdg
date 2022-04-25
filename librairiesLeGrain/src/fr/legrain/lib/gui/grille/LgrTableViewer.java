package fr.legrain.lib.gui.grille;

import java.io.File;
import java.io.FileInputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.log4j.Logger;
import org.eclipse.core.databinding.observable.list.WritableList;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.AbstractTableViewer;
import org.eclipse.jface.viewers.CellEditor.LayoutData;
import org.eclipse.jface.viewers.CellNavigationStrategy;
import org.eclipse.jface.viewers.CheckboxTableViewer;
import org.eclipse.jface.viewers.ColumnViewer;
import org.eclipse.jface.viewers.ColumnViewerEditor;
import org.eclipse.jface.viewers.ColumnViewerEditorActivationEvent;
import org.eclipse.jface.viewers.ColumnViewerEditorActivationStrategy;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.FocusCellOwnerDrawHighlighter;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.TableViewerFocusCellManager;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerCell;
import org.eclipse.jface.viewers.ViewerColumn;
import org.eclipse.jface.viewers.ViewerRow;
import org.eclipse.jface.viewers.ViewerSorter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.TableEditor;
import org.eclipse.swt.events.TraverseEvent;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Item;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.ui.PlatformUI;

import fr.legrain.lib.data.LibConversion;

public class LgrTableViewer<T> extends CheckboxTableViewer {

	static Logger logger = Logger.getLogger(LgrTableViewer.class.getName());
	
	static private PropertiesConfiguration listeChampGrille = new PropertiesConfiguration();
	private String[] champsObjetCompare;
	private String[] titresColonnes;
	private Class typeObjetCompare;
	private TableColumn column = null;
	
	private String[] listeTailleCol;
	
	/**
	 * 
	 */
	private String[] listeChamp = null;

	/**
	 * {@inheritDoc}
	 */
	public LgrTableViewer(Composite parent) {
		super(parent);
	}

	
	/**
	 * {@inheritDoc}
	 */
	public LgrTableViewer(Composite parent, int style) {
		super(parent, style);
	}

	public LgrTableViewer(Table table) {
		super(table);
    
	}

	/**
	 * Sélectionne l'objet à la position <code>row</code> dans la table et dans le viewer associé.
	 * Si <code>row</code> est invalide, la 1ère ligne est sélectionné
	 * @param row - position de la ligne à sélectionner
	 * @return vrai ssi une ligne a été sélectionné
	 */
	public boolean selectionGrille(int row) {
		boolean result = false;
		StructuredSelection s = null;
		if (row>0 && row<this.getTable().getItemCount()){
			s = new StructuredSelection(this.getElementAt(row));
		} else {
			if(this.getTable().getItemCount()>0)
				if (this.getElementAt(0)!=null)
					s = new StructuredSelection(this.getElementAt(0));
		}
		if(s!=null) {
			this.setSelection(s,true);
			this.setSelectionToWidget(s, true);
			result =true;
		}
		return result;
	}

	
	public int selectionGrille(Object row) {
		int rang = 0;
		if (row!=null ){
			this.findItem(row);
			rang = this.indexForElement(row)-1;
		}
		return rang;
	}
	
	public int findPositionColonne(String titreColonne){
		boolean trouve = false;
		int i = 0;
		while(!trouve && i<getTable().getColumns().length){
			if(getTable().getColumns()[i].getText().equals(titreColonne)) {
				trouve = true;
			}
			i++;
		}
		if(trouve)
			return i-1;
		else
			return -1;
	}

	public String[] getListeTailleCol() {
		return listeTailleCol;
	}


	public String findTitreColonne(String titreColonne){
		int pos = findPositionColonne(titreColonne);
		if(pos!=-1) {
			return getTable().getColumns()[pos].getText();
		} else {
			return null;
		}
	}

	public int findPositionNomChamp(String nomChamp){
		boolean trouve = false;
		int i = 0;
		if(listeChamp!=null) {
			while(!trouve && i<listeChamp.length){
				if(listeChamp[i].equalsIgnoreCase(nomChamp)) {
					trouve = true;
				}
				i++;
			}
		}
		if(trouve)
			return i-1;
		else
			return -1;
	}

	public String findNomChamp(String nomChamp){
		int pos = findPositionNomChamp(nomChamp);
		if(pos!=-1 && listeChamp!=null) {
			return listeChamp[pos];
		} else {
			return null;
		}
	}
	
	Listener listener = new Listener() {
		public void handleEvent(Event e) {
			System.out.println("Move "+e.widget);
		}
	};
	/**
	 * Permet de trier la grille à la création sur le champ <code>colonneTri</code> si celui-ci existe dans
	 * la liste des champs
	 * @param typeObjetCompare - Classe des objets contenus dans le viewer
	 * @param grille
	 * @param nomClasse - nom de la classe appelante (controller)
	 * @param nomFichierChamps - fichier properties
	 * @param colonneTri - numéro du champs à trier
	 */
	public void createTableCol(Class typeObjetCompare,Table grille, String nomClasse, String nomFichierChamps,int colonneTri ){
		try {
			champsObjetCompare = setListeChampGrille(nomClasse,nomFichierChamps);
			titresColonnes = setListeTitreChampGrille(nomClasse,nomFichierChamps);
			this.typeObjetCompare = typeObjetCompare;
		} catch (Exception e1) {
			logger.error(e1);
		}
		videTableViewer();
		grille.setHeaderVisible(true);
		grille.setLinesVisible(true);

		String[] listeTitreCol;
		String[] listeTailleCol;
		try {
			listeTitreCol = setListeTitreChampGrille(nomClasse,nomFichierChamps);
			listeTailleCol= setListeTailleChampGrille(nomClasse,nomFichierChamps);
			TableColumn col;
			for (int i = 0; i < listeTitreCol.length; i++) {
				col = new TableColumn(grille, SWT.NONE);		
				col.setWidth(LibConversion.stringToInteger(listeTailleCol[i]) );
				col.setText(listeTitreCol[i]);
				col.setMoveable(true);
				col.addListener(SWT.Move, listener);
				if (i==colonneTri)column = col;
			}
			
			modifCellEditors(grille);
//			navigationStrategy();
		} catch (Exception e) {
			logger.debug(e);
		}
		if(column!=null)
			setSorter(new LgrSorter(column,SWT.UP));
	}
	
	public void reinitialise(Table grille) {
		if(titresColonnes!=null && listeTailleCol!=null) {
			TableColumn col;
			for (int i = 0; i < titresColonnes.length; i++) {
				//col = new TableColumn(grille, SWT.NONE);	
				col = grille.getColumn(i);
				col.setWidth(LibConversion.stringToInteger(listeTailleCol[i]) );
				col.setText(titresColonnes[i]);
				col.setResizable(true);
			}
		}
	}

	public void createTableCol(Table grille, String[] titreColonnes, String[] tailleColonnes,int colonneTri ){
		videTableViewer();
		grille.setHeaderVisible(true);
		grille.setLinesVisible(true);

		String[] listeTitreCol;
		String[] listeTailleCol;
		try {
			listeTitreCol = titreColonnes;
			titresColonnes = listeTitreCol;
			listeTailleCol= tailleColonnes;
			this.listeTailleCol = listeTailleCol;
			TableColumn col;
   			for (int i = 0; i < listeTitreCol.length; i++) {
   				col = new TableColumn(grille, SWT.NONE);		
				col.setWidth(LibConversion.stringToInteger(listeTailleCol[i]) );
				col.setText(listeTitreCol[i]);
				col.setMoveable(true);
				col.addListener(SWT.Move, listener);
				if (i==colonneTri)column = col;
			}
   			modifCellEditors(grille);
//   			navigationStrategy();
		} catch (Exception e) {
			logger.debug(e);
		}
		if(column!=null)
			setSorter(new LgrSorter(column,SWT.UP));

	}
	
	/**
	 * Constructeur prenant en compte l'alignement dans la colonne
	 * @param grille
	 * @param titreColonnes
	 * @param tailleColonnes
	 * @param colonneTri
	 * @param alignement
	 */
	public void createTableCol(Table grille, String[] titreColonnes, String[] tailleColonnes,int colonneTri, int[] alignement ){
		videTableViewer();
		grille.setHeaderVisible(true);
		grille.setLinesVisible(true);

		String[] listeTitreCol;
		String[] listeTailleCol;
		try {
			listeTitreCol = titreColonnes;
			titresColonnes = listeTitreCol;
			listeTailleCol= tailleColonnes;
			this.listeTailleCol = listeTailleCol;
			TableColumn col;
   			for (int i = 0; i < listeTitreCol.length; i++) {
   				col = new TableColumn(grille, alignement[i]);		
				col.setWidth(LibConversion.stringToInteger(listeTailleCol[i]) );
				col.setText(listeTitreCol[i]);
				col.setMoveable(true);
				col.addListener(SWT.Move, listener);
				if (i==colonneTri)column = col;
			}
   			modifCellEditors(grille);
//   			navigationStrategy();
		} catch (Exception e) {
			logger.debug(e);
		}
		if(column!=null)
			setSorter(new LgrSorter(column,SWT.UP));

	}
	
	public void createTableCol(Table grille, String[] titreColonnes, String[] tailleColonnes){
		createTableCol(grille,titreColonnes,tailleColonnes,0);
	}
	
	/**
	 * Supprime le contenu de la table et supprime les colonnes de la table
	 */
	public void videTableViewer(){
		getTable().removeAll();
		while(getTable().getColumnCount()>0){
			getTable().getColumn(getTable().getColumnCount()-1).setData(null);
			getTable().getColumn(getTable().getColumnCount()-1).dispose();
		}
	}
	
	public void createTableCol(Table grille, String nomClasse, String nomFichierChamps ){
		createTableCol(grille, nomClasse, nomFichierChamps,false );
	}

	/**
	 * 
	 * @param grille - la table
	 * @param nomClasse - Le nom de la classe servant de "clé" dans le fichier .properties
	 * @param nomFichierChamps - Le fichier .properties contenant la liste des colonnes ainsi que leur titre et leur taille
	 * @param add - vrai ssi on souhaite ajouter des colonnes à la fin d'une table
	 */
	public void createTableCol(Table grille, String nomClasse, String nomFichierChamps, boolean add){
		if(!add) {
			videTableViewer();
		}
		grille.setHeaderVisible(true);
		grille.setLinesVisible(true);

		String[] listeTitreCol;
		String[] listeTailleCol;
		try {
			listeTitreCol = setListeTitreChampGrille(nomClasse,nomFichierChamps);
			if(add) {
				String[] listeTitreColTmp = new String[titresColonnes.length + listeTitreCol.length];
				System.arraycopy(titresColonnes, 0, listeTitreColTmp, 0, titresColonnes.length);
				System.arraycopy(listeTitreCol, 0, listeTitreColTmp, listeTitreColTmp.length-listeTitreCol.length, listeTitreCol.length);
				titresColonnes = listeTitreColTmp;
			} else {
				titresColonnes = listeTitreCol;
			}
			
			listeTailleCol= setListeTailleChampGrille(nomClasse,nomFichierChamps);
			if(add) {
				String[] listeTailleColTmp = new String[this.listeTailleCol.length + listeTailleCol.length];
				System.arraycopy(this.listeTailleCol, 0, listeTailleColTmp, 0, this.listeTailleCol.length);
				System.arraycopy(listeTailleCol, 0, listeTailleColTmp, listeTailleColTmp.length-listeTitreCol.length, listeTailleCol.length);
				this.listeTailleCol = listeTailleColTmp;
			} else {
				this.listeTailleCol = listeTailleCol;
			}
			
			TableColumn col;
   			for (int i = 0; i < listeTitreCol.length; i++) {
   				col = new TableColumn(grille, SWT.NONE);		
				col.setWidth(LibConversion.stringToInteger(listeTailleCol[i]) );
				col.setText(listeTitreCol[i]);
				col.setMoveable(true);
				col.addListener(SWT.Move, listener);
			}
   			modifCellEditors(grille);
//   			navigationStrategy();
		} catch (Exception e) {
			logger.debug("",e);
		}
	}
	
	//NOUVEAU
	/**
	 * table
	 * classe des objets
	 * titres des colonnes
	 * 
	 */
	public TableViewerColumn[] createTableViewerCol(Table grille, String nomClasse, String nomFichierChamps ){
		videTableViewer();
		grille.setHeaderVisible(true);
		grille.setLinesVisible(true);

		String[] listeTitreCol;
		String[] listeTailleCol;
		TableViewerColumn[] liste = null;
		try {
			listeTitreCol = setListeTitreChampGrille(nomClasse,nomFichierChamps);
			listeTailleCol= setListeTailleChampGrille(nomClasse,nomFichierChamps);
			liste = new TableViewerColumn[listeTitreCol.length];
			TableViewerColumn col;
   			for (int i = 0; i < listeTitreCol.length; i++) {
				col = new TableViewerColumn(this, SWT.NONE);		
				col.getColumn().setWidth(LibConversion.stringToInteger(listeTailleCol[i]) );
				col.getColumn().setText(listeTitreCol[i]);
				col.getColumn().setMoveable(true);
				col.getColumn().addListener(SWT.Move, listener);
				//col.setEditingSupport(editingSupport);
				liste[i]=col;
			}
   			modifCellEditors(grille);
//   			navigationStrategy();
   			return liste;
		} catch (Exception e) {
			logger.debug(e);
		}
		return liste;
	}

	
	
	private void modifCellEditors(Table grille){// Create the cell editors
//		//cf doc de setCellEditor()
//		CellEditor[] editors = new CellEditor[grille.getColumnCount()];
//
//		// Column 1 : Completed (Checkbox)
//		editors[0] = new TextCellEditor(grille);
//		editors[1] = new TextCellEditor(grille);
//		editors[2] = new TextCellEditor(grille);
//		editors[3] = new CheckboxCellEditor(grille);
//		editors[4] = new CheckboxCellEditor(grille);
//		editors[5] = new CheckboxCellEditor(grille);
//		editors[6] = new TextCellEditor(grille);
//		grille.setEnabled(true);
//
//		this.setCellEditors(editors);
	}
	
	/**
	 * @return liste des champsObjetCompare/colonnes à afficher pour la grille ainsi que leur titre.
	 */
	public static PropertiesConfiguration getListeChampGrille() {
		return listeChampGrille;
	}
	
	/**
	 * Récupére le contenu de la liste <code>listeChampGrille</code> à partir du fichier properties
	 * <br>
	 * Format des lignes du fichier properties : NomDelaClasse.NOM_CHAMPS=TITRE CHAMPS
	 * <br>
	 * Exemple de fichier properties :
	 * <p>
	 * <code>SWTPaTiersController.COMPTE=COMPTE
	 * SWTPaTiersController.CODE_COMPTA=CODE COMPTA
     * SWTPaTiersController.CODE_TIERS=CODE TIERS 
     * SWTPaTiersController.NOM_TIERS=NOM TIERS
     * </code>
	 * </p>
	 * @param fileName - nom du fichier properties
	 */
	public static void setListeChampGrille(String fileName){
		try {
			if(listeChampGrille.isEmpty()){
				if (!new File(fileName).exists()) {
					MessageDialog.openError(
							PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),
							"ERREUR",
							"Le fichier .properties "+ fileName + " est inexistant");
				} else {
					FileInputStream file = new FileInputStream(fileName);
					listeChampGrille.load(file);
					file.close();
				}
			}
		}
		catch (Exception e) {
			logger.error("Erreur : setListeChampGrille", e);
		}
	}

	/**
	 * Récupère une liste de champsObjetCompare pour une section/écran donnée.
	 * @param section - nom de la classe appelante (controller)
	 * @param fileName - fichier properties
	 * @return - liste de champsObjetCompare
	 * @throws Exception - si fileName n'xiste pas.
	 * @see #setListeChampGrille
	 */
	public String[] setListeChampGrille(String section, String fileName) throws Exception {
		setListeChampGrille(fileName);
		List<String> res =new ArrayList<String>();
		org.apache.commons.configuration.SubsetConfiguration propertie = null;
		propertie = new org.apache.commons.configuration.SubsetConfiguration(getListeChampGrille(),section,".");
		if (!propertie.isEmpty()){
			for (Iterator iter = propertie.getKeys(); iter.hasNext();) {
				res.add(iter.next().toString());
			}
		}
		String[] retour = new String[res.size()];
		for (int i = 0; i < res.size(); i++) {
			retour[i]=res.get(i).toString();
		}
		listeChamp = retour;
		return retour;
	}
	
	/**
	 * Récupère une liste de titresColonnes pour une section/écran donnée.
	 * @param section - nom de la classe appelante (controller)
	 * @param fileName - fichier properties
	 * @return - liste de champsObjetCompare
	 * @throws Exception - si fileName n'xiste pas.
	 * @see #setListeChampGrille
	 */
	public String[] setListeTitreChampGrille( String section,String fileName) throws Exception{
		setListeChampGrille(fileName);
		List<String> res =new ArrayList<String>();
		org.apache.commons.configuration.SubsetConfiguration propertie = null;
		propertie = new org.apache.commons.configuration.SubsetConfiguration(getListeChampGrille(),section,".");
		if (!propertie.isEmpty()){			
			for (Iterator iter = propertie.getKeys(); iter.hasNext();) {
				res.add(propertie.getString(iter.next().toString()));
			}
		}
		String[] retour = new String[res.size()];
		for (int i = 0; i < res.size(); i++) {
			retour[i]=res.get(i).toString().split(";")[0];
		}
		return retour;
	}

	/**
	 * Récupère une liste de titresColonnes pour une section/écran donnée.
	 * @param section - nom de la classe appelante (controller)
	 * @param fileName - fichier properties
	 * @return - liste de champsObjetCompare
	 * @throws Exception - si fileName n'xiste pas.
	 * @see #setListeChampGrille
	 */
	public String[] setListeTailleChampGrille( String section,String fileName) throws Exception{
		setListeChampGrille(fileName);
		List<String> res =new ArrayList<String>();
		org.apache.commons.configuration.SubsetConfiguration propertie = null;
		propertie = new org.apache.commons.configuration.SubsetConfiguration(getListeChampGrille(),section,".");
		if (!propertie.isEmpty()){			
			for (Iterator iter = propertie.getKeys(); iter.hasNext();) {
				res.add(propertie.getString(iter.next().toString()));
			}
		}
		String[] retour = new String[res.size()];
		for (int i = 0; i < res.size(); i++) {
			retour[i]=res.get(i).toString().split(";")[1];
		}
		return retour;
	}
	
	/**
	 * Renvoie le nom d'un champ de la grille en fonction de son <code>titre</code>
	 * @param titreChampGrille titre du champ
	 * @return nom du champ
	 */	
	public String champGrille(String titreChampGrille,String section,String fileName)	throws Exception{
			setListeChampGrille(fileName);
			String res =null;
			org.apache.commons.configuration.SubsetConfiguration propertie = null;
			propertie = new org.apache.commons.configuration.SubsetConfiguration(getListeChampGrille(),section,".");
			if (!propertie.isEmpty()){
				Iterator iter = propertie.getKeys();
				while(res==null && iter.hasNext()){
					String valeur = iter.next().toString();
					if(propertie.getString(valeur).split(";")[0].equals(titreChampGrille))
						res=valeur;
				}
			}
				return res;
		}
	
	/**
	 * Renvoie le titre d'un champ de la grille en fonction du <code>Champ</code>
	 * @param ChampGrille nom du champ
	 * @return titre du champ
	 */	
	public String titreChampGrille(String ChampGrille,String section,String fileName){
		setListeChampGrille(fileName);
		String res =null;
		org.apache.commons.configuration.SubsetConfiguration propertie = null;
		propertie = new org.apache.commons.configuration.SubsetConfiguration(getListeChampGrille(),section,".");
		if (!propertie.isEmpty()){			
			Iterator iter = propertie.getKeys();
			while(res==null && iter.hasNext()){
				String valeur = iter.next().toString();
				if(valeur.equals(ChampGrille))
					res=propertie.getString(valeur).split(";")[0].toString();;
			}
		}
			return res;
	}			
	
	/**
	 * Renvoie le titre d'un champ de la grille en fonction du <code>Champ</code>
	 * @param ChampGrille nom du champ
	 * @return taille du champ
	 */	
	public String tailleChampGrille(String ChampGrille,String section,String fileName){
		setListeChampGrille(fileName);
		String res =null;
		org.apache.commons.configuration.SubsetConfiguration propertie = null;
		propertie = new org.apache.commons.configuration.SubsetConfiguration(getListeChampGrille(),section,".");
		if (!propertie.isEmpty()){			
			Iterator iter = propertie.getKeys();
			while(res==null && iter.hasNext()){
				String valeur = iter.next().toString();
				if(valeur.equals(ChampGrille))
					res=propertie.getString(valeur).split(";")[1].toString();;
			}
		}
			return res;
	}			



	
////Test filtre		
////tableViewer.addFilter(new ViewerFilter() {
////@Override
////public boolean select(Viewer viewer, Object parentElement, Object element) {
////if(((SWTTiers)element).getNOM_TIERS().startsWith("B"))
////return true;
////else
////return false;
////}
////});
	
	public void tri(Class typeObjetCompare, String nomClasse, String nomFichierChamps) {
		try {
			champsObjetCompare = setListeChampGrille(nomClasse,nomFichierChamps);
			titresColonnes = setListeTitreChampGrille(nomClasse,nomFichierChamps);
			tri(typeObjetCompare,champsObjetCompare,titresColonnes);
		} catch(Exception e) {
			logger.error("",e);
		}
	}
	
	public void brancheComparateur(final ViewerSorter comparateur){
		if(comparateur!=null){
			setSorter(comparateur);
		}
	}
	/**
	 * Active le tri sur les colonnes
	 * @param typeObjetCompare - Classe des objets contenus dans le viewer
	 * @param nomClasse - nom de la classe appelante (controller)
	 * @param nomFichierChamps - fichier properties
	 */
	public void tri(Class typeObjetCompare, String[] champsObjetCompare, String[] titresColonnes) {
		try {
			this.champsObjetCompare = champsObjetCompare;
			this.titresColonnes = titresColonnes;
			this.typeObjetCompare = typeObjetCompare;
			
			 Listener sortListener = new Listener() {
				 public void handleEvent(Event e) {
					 // determination de la colonne de tri et du sens
					 TableColumn sortColumn = getTable().getSortColumn();
					 TableColumn currentColumn = (TableColumn) e.widget;
					 int dir = getTable().getSortDirection();
					 if (sortColumn == currentColumn) {
						 dir = dir == SWT.UP ? SWT.DOWN : SWT.UP;
					 } else {
						 getTable().setSortColumn(currentColumn);
						 dir = SWT.UP;
					 }
					 getTable().setSortDirection(dir);
					 setSorter(new LgrSorter(currentColumn,dir));
				 }
			 };
			 
			 //branchement du tri sur toutes les colonnes
			 for (int i = 0; i<getTable().getColumns().length; i++) {
				 getTable().getColumns()[i].addListener(SWT.Selection, sortListener);
			 }
			 
		} catch(Exception e) {
			logger.error("",e);
		}
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
		public int compare(Viewer viewer1, Object e1, Object e2) {
			try {				
				int returnValue = 0;
				
				Object propertyE1 = null;
				Object propertyE2 = null;
				if(champsObjetCompare!=null && i<champsObjetCompare.length){
				if(trouve && PropertyUtils.getReadMethod(PropertyUtils.getPropertyDescriptor(e1, champsObjetCompare[i]))!=null &&
						PropertyUtils.getReadMethod(PropertyUtils.getPropertyDescriptor(e2, champsObjetCompare[i]))!=null) {
					propertyE1 = PropertyUtils.getReadMethod(PropertyUtils.getPropertyDescriptor(e1, champsObjetCompare[i])).invoke(e1, new Object[0]);
					propertyE2 = PropertyUtils.getReadMethod(PropertyUtils.getPropertyDescriptor(e2, champsObjetCompare[i])).invoke(e2, new Object[0]);
					
					if(propertyE1 != null && propertyE2 != null) {
						if(propertyE1 instanceof Boolean)
							returnValue = ((Boolean)propertyE1).compareTo((Boolean)propertyE2);
						else	
							if(propertyE1 instanceof BigDecimal)
								returnValue = ((BigDecimal)propertyE1).compareTo((BigDecimal)propertyE2);
							else
								if(propertyE1 instanceof String)
									returnValue = ((String)propertyE1).compareTo((String)propertyE2);
								else
									if(propertyE1 instanceof Integer)
										returnValue = ((Integer)propertyE1).compareTo((Integer)propertyE2);
									else
										if(propertyE1 instanceof Date)
											returnValue = ((Date)propertyE1).compareTo((Date)propertyE2);
						//Date
					} 
					if (this.dir == SWT.DOWN) {
						returnValue = returnValue * -1;
					}
				}
				}
//AVANT JPA
//				if(trouve && typeObjetCompare.getMethod("get"+champsObjetCompare[i],new Class[0]).invoke(e1, new Object[0])!=null &&
//						typeObjetCompare.getMethod("get"+champsObjetCompare[i],new Class[0]).invoke(e2, new Object[0])!=null) {
//					Object typeObjet = (typeObjetCompare.getMethod("get"+champsObjetCompare[i].,new Class[0]).invoke(e1, new Object[0]));
//					if(typeObjet instanceof Boolean)
//						returnValue = ((Boolean)typeObjetCompare.getMethod("get"+champsObjetCompare[i],new Class[0]).invoke(e1, new Object[0])).
//						compareTo((Boolean)typeObjetCompare.getMethod("get"+champsObjetCompare[i],new Class[0]).invoke(e2, new Object[0]));
//					else	
//						if(typeObjet instanceof BigDecimal)
//							returnValue = ((BigDecimal)typeObjetCompare.getMethod("get"+champsObjetCompare[i],new Class[0]).invoke(e1, new Object[0])).
//							compareTo((BigDecimal)typeObjetCompare.getMethod("get"+champsObjetCompare[i],new Class[0]).invoke(e2, new Object[0]));							
//						else
//							if(typeObjet instanceof String)
//								returnValue = ((String)typeObjetCompare.getMethod("get"+champsObjetCompare[i],new Class[0]).invoke(e1, new Object[0])).
//								compareTo((String)typeObjetCompare.getMethod("get"+champsObjetCompare[i],new Class[0]).invoke(e2, new Object[0]));
//							else
//								if(typeObjet instanceof Integer)
//									returnValue = ((Integer)typeObjetCompare.getMethod("get"+champsObjetCompare[i],new Class[0]).invoke(e1, new Object[0])).
//									compareTo((Integer)typeObjetCompare.getMethod("get"+champsObjetCompare[i],new Class[0]).invoke(e2, new Object[0]));
//								else
//									if(typeObjet instanceof Date)
//										returnValue = ((Date)typeObjetCompare.getMethod("get"+champsObjetCompare[i],new Class[0]).invoke(e1, new Object[0])).
//										compareTo((Date)typeObjetCompare.getMethod("get"+champsObjetCompare[i],new Class[0]).invoke(e2, new Object[0]));
//					//Date
//
//					if (this.dir == SWT.DOWN) {
//						returnValue = returnValue * -1;
//					}
//				}
				return returnValue;
			} catch(Exception e) {
				logger.error("type d'objet non implémenté",e);
				return 0;
			}
		}
	}
	
	public void destroy(){
		//listeChampGrille = null; champ static il ne faut pas le destroy
		champsObjetCompare = null;
		titresColonnes = null;
		typeObjetCompare = null;
		column = null;

	}


	public void setInput(WritableList list){
		super.setInput(null);
		super.setInput(list);
		super.refresh();
	}
	public String[] getTitresColonnes() {
		return titresColonnes;
	}


	public String[] getListeChamp() {
		return listeChamp;
	}


	public void setListeChamp(String[] listeChamp) {
		this.listeChamp = listeChamp;
	}

    public void navigationStrategy(){
	CellNavigationStrategy naviStrat = new CellNavigationStrategy() {

		private ViewerCell internalFindSelectedCell(ColumnViewer viewer,
				ViewerCell currentSelectedCell, Event event) {
			switch (event.keyCode) {
			case SWT.ARROW_UP:
				if (currentSelectedCell != null) {
					return getNeighbor(currentSelectedCell,
							ViewerCell.ABOVE, false);
				}
				break;
			case SWT.ARROW_DOWN:
				if (currentSelectedCell != null) {
					return getNeighbor(currentSelectedCell,
							ViewerCell.BELOW, false);
				}
				break;
			case SWT.ARROW_LEFT:
				if (currentSelectedCell != null) {
					return getNeighbor(currentSelectedCell,
							ViewerCell.LEFT, true);
				}
				break;
			case SWT.ARROW_RIGHT:
				if (currentSelectedCell != null) {
					return getNeighbor(currentSelectedCell,
							ViewerCell.RIGHT, true);
				}
				break;
			}

			return null;
		}

		public ViewerCell findSelectedCell(ColumnViewer viewer,
				ViewerCell currentSelectedCell, Event event) {
			ViewerCell cell = internalFindSelectedCell(viewer,
					currentSelectedCell, event);

			if (cell != null) {
				TableColumn t = getTable().getColumn(
						cell.getColumnIndex());
				getTable().showColumn(t);
			}

			return cell;
		}

	};
	
	TableViewerFocusCellManager focusCellManager = new TableViewerFocusCellManager(
			this, new FocusCellOwnerDrawHighlighter(this));
	try {
		Field f = focusCellManager.getClass().getSuperclass()
				.getDeclaredField("navigationStrategy");
		f.setAccessible(true);
		f.set(focusCellManager, naviStrat);
	} catch (SecurityException e) {
		e.printStackTrace();
	} catch (NoSuchFieldException e) {
		e.printStackTrace();
	} catch (IllegalArgumentException e) {
		e.printStackTrace();
	} catch (IllegalAccessException e) {
		e.printStackTrace();
	}
//
	ColumnViewerEditorActivationStrategy actSupport = new ColumnViewerEditorActivationStrategy(
			this) {
		protected boolean isEditorActivationEvent(
				ColumnViewerEditorActivationEvent event) {
			return event.eventType == ColumnViewerEditorActivationEvent.TRAVERSAL
					|| event.eventType == ColumnViewerEditorActivationEvent.MOUSE_DOUBLE_CLICK_SELECTION
					|| (event.eventType == ColumnViewerEditorActivationEvent.KEY_PRESSED && event.keyCode == SWT.CR)
					|| (event.eventType == ColumnViewerEditorActivationEvent.KEY_PRESSED && event.keyCode == SWT.KEYPAD_CR)
					|| event.eventType == ColumnViewerEditorActivationEvent.PROGRAMMATIC
					|| event.eventType == ColumnViewerEditorActivationEvent.MOUSE_CLICK_SELECTION
					;
		}
	};
//
	TableViewerEditor.create(this, focusCellManager, actSupport,
			ColumnViewerEditor.TABBING_HORIZONTAL
					| ColumnViewerEditor.TABBING_MOVE_TO_ROW_NEIGHBOR
					| ColumnViewerEditor.TABBING_VERTICAL
					| ColumnViewerEditor.KEYBOARD_ACTIVATION);
//
//	tableViewerSelection.getColumnViewerEditor().addEditorActivationListener(
//			new ColumnViewerEditorActivationListener() {
//
//				public void afterEditorActivated(
//						ColumnViewerEditorActivationEvent event) {
//
//				}
//
//				public void afterEditorDeactivated(
//						ColumnViewerEditorDeactivationEvent event) {
//
//				}
//
//				public void beforeEditorActivated(
//						ColumnViewerEditorActivationEvent event) {
//					ViewerCell cell = (ViewerCell) event.getSource();
//					tableViewerSelection.getTable().showColumn(
//							tableViewerSelection.getTable().getColumn(cell.getColumnIndex()));
//				}
//
//				public void beforeEditorDeactivated(
//						ColumnViewerEditorDeactivationEvent event) {
//
//				}
//
//			});
    }


	public static class TableViewerEditor extends ColumnViewerEditor {
	/**
	 * This viewer's table editor.
	 */
	private TableEditor tableEditor;

	private TableViewerFocusCellManager focusCellManager;

	private int feature;

	/**
	 * @param viewer
	 *            the viewer the editor is attached to
	 * @param focusCellManager
	 *            the cell focus manager if one used or <code>null</code>
	 * @param editorActivationStrategy
	 *            the strategy used to decide about the editor activation
	 * @param feature
	 *            the feature mask
	 */
	TableViewerEditor(TableViewer viewer,
			TableViewerFocusCellManager focusCellManager,
			ColumnViewerEditorActivationStrategy editorActivationStrategy,
			int feature) {
		super(viewer, editorActivationStrategy, feature);
		this.feature = feature;
		tableEditor = new TableEditor(viewer.getTable());
		this.focusCellManager = focusCellManager;
	}

	/**
	 * Create a customized editor with focusable cells
	 *
	 * @param viewer
	 *            the viewer the editor is created for
	 * @param focusCellManager
	 *            the cell focus manager if one needed else
	 *            <code>null</code>
	 * @param editorActivationStrategy
	 *            activation strategy to control if an editor activated
	 * @param feature
	 *            bit mask controlling the editor
	 *            <ul>
	 *            <li>{@link ColumnViewerEditor#DEFAULT}</li>
	 *            <li>{@link ColumnViewerEditor#TABBING_CYCLE_IN_ROW}</li>
	 *            <li>{@link ColumnViewerEditor#TABBING_HORIZONTAL}</li>
	 *            <li>{@link ColumnViewerEditor#TABBING_MOVE_TO_ROW_NEIGHBOR}</li>
	 *            <li>{@link ColumnViewerEditor#TABBING_VERTICAL}</li>
	 *            </ul>
	 * @see #create(TableViewer, ColumnViewerEditorActivationStrategy, int)
	 */
	public static void create(TableViewer viewer,
			TableViewerFocusCellManager focusCellManager,
			ColumnViewerEditorActivationStrategy editorActivationStrategy,
			int feature) {
		TableViewerEditor editor = new TableViewerEditor(viewer,
				focusCellManager, editorActivationStrategy, feature);
		viewer.setColumnViewerEditor(editor);
		if (focusCellManager != null) {
			try {
				Method m = focusCellManager.getClass().getSuperclass()
						.getDeclaredMethod("init", null);
				m.setAccessible(true);
				m.invoke(focusCellManager, null);
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
			// focusCellManager.init();
		}
	}

	/**
	 * Create a customized editor whose activation process is customized
	 *
	 * @param viewer
	 *            the viewer the editor is created for
	 * @param editorActivationStrategy
	 *            activation strategy to control if an editor activated
	 * @param feature
	 *            bit mask controlling the editor
	 *            <ul>
	 *            <li>{@link ColumnViewerEditor#DEFAULT}</li>
	 *            <li>{@link ColumnViewerEditor#TABBING_CYCLE_IN_ROW}</li>
	 *            <li>{@link ColumnViewerEditor#TABBING_HORIZONTAL}</li>
	 *            <li>{@link ColumnViewerEditor#TABBING_MOVE_TO_ROW_NEIGHBOR}</li>
	 *            <li>{@link ColumnViewerEditor#TABBING_VERTICAL}</li>
	 *            </ul>
	 */
	public static void create(TableViewer viewer,
			ColumnViewerEditorActivationStrategy editorActivationStrategy,
			int feature) {
		create(viewer, null, editorActivationStrategy, feature);
	}

	protected void setEditor(Control w, Item item, int columnNumber) {
		tableEditor.setEditor(w, (TableItem) item, columnNumber);
	}

	protected void setLayoutData(LayoutData layoutData) {
		tableEditor.grabHorizontal = layoutData.grabHorizontal;
		tableEditor.horizontalAlignment = layoutData.horizontalAlignment;
		tableEditor.minimumWidth = layoutData.minimumWidth;
	}

	public ViewerCell getFocusCell() {
		if (focusCellManager != null) {
			return focusCellManager.getFocusCell();
		}

		return super.getFocusCell();
	}

	protected void updateFocusCell(ViewerCell focusCell,
			ColumnViewerEditorActivationEvent event) {
		// Update the focus cell when we activated the editor with these 2
		// events
		if (event.eventType == ColumnViewerEditorActivationEvent.PROGRAMMATIC
				|| event.eventType == ColumnViewerEditorActivationEvent.TRAVERSAL) {

			if (focusCellManager != null) {
				try {
					Method m = AbstractTableViewer.class.getDeclaredMethod(
							"getSelectionFromWidget", null);
					m.setAccessible(true);
					List l = (List) m.invoke(getViewer(), null);

					if (focusCellManager != null) {
						m = focusCellManager.getClass().getSuperclass()
								.getDeclaredMethod("setFocusCell",
										new Class[] { ViewerCell.class });
						m.setAccessible(true);
						m.invoke(focusCellManager,
								new Object[] { focusCell });
					}

					if (!l.contains(focusCell.getElement())) {
						getViewer().setSelection(
								new StructuredSelection(focusCell
										.getElement()));
					}

				} catch (SecurityException e) {
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (NoSuchMethodException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				}

			}
		}
	}

	protected void processTraverseEvent(int columnIndex, ViewerRow row,
			TraverseEvent event) {
		ViewerCell cell2edit = null;

		if (event.detail == SWT.TRAVERSE_TAB_PREVIOUS) {
			event.doit = false;

			if ((event.stateMask & SWT.CTRL) == SWT.CTRL
					&& (feature & TABBING_VERTICAL) == TABBING_VERTICAL) {
				cell2edit = searchCellAboveBelow(row, getViewer(), columnIndex, true);
			} else if ((feature & TABBING_HORIZONTAL) == TABBING_HORIZONTAL) {
				cell2edit = searchPreviousCell(row, row.getCell(columnIndex),
						row.getCell(columnIndex), getViewer());
			}
		} else if (event.detail == SWT.TRAVERSE_TAB_NEXT) {
			event.doit = false;

			if ((event.stateMask & SWT.CTRL) == SWT.CTRL
					&& (feature & TABBING_VERTICAL) == TABBING_VERTICAL) {
				cell2edit = searchCellAboveBelow(row, getViewer(), columnIndex,
						false);
			} else if ((feature & TABBING_HORIZONTAL) == TABBING_HORIZONTAL) {
				cell2edit = searchNextCell(row, row.getCell(columnIndex), row
						.getCell(columnIndex), getViewer());
			}
		}

//		System.err.println("NEXT CELL: " + cell2edit);

		if (cell2edit != null) {
			getViewer().getControl().setRedraw(false);
			ColumnViewerEditorActivationEvent acEvent = new ColumnViewerEditorActivationEvent(
					cell2edit, event);

			try {
				Method m = ColumnViewer.class.getDeclaredMethod("triggerEditorActivationEvent", new Class[] { ColumnViewerEditorActivationEvent.class });
				m.setAccessible(true);
				m.invoke(getViewer(), new Object[] {acEvent});
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}

			getViewer().getControl().setRedraw(true);
		}
	}

	private ViewerCell searchCellAboveBelow(ViewerRow row, ColumnViewer viewer,
			int columnIndex, boolean above) {
		ViewerCell rv = null;

		ViewerRow newRow = null;

		if (above) {
			newRow = row.getNeighbor(ViewerRow.ABOVE, false);
		} else {
			newRow = row.getNeighbor(ViewerRow.BELOW, false);
		}

		try {
		if (newRow != null) {
			Method m = ColumnViewer.class.getDeclaredMethod("getViewerColumn", new Class[] { int.class });
			m.setAccessible(true);
			ViewerColumn column = (ViewerColumn) m.invoke(viewer, new Object[] { new Integer(columnIndex) });
			m = ViewerColumn.class.getDeclaredMethod("getEditingSupport", null);
			m.setAccessible(true);

			EditingSupport es = (EditingSupport) m.invoke(column, null);

			if (column != null
					&& es != null) {
				m = EditingSupport.class.getDeclaredMethod("canEdit", new Class[] { Object.class });
				m.setAccessible(true);
				Boolean b = (Boolean) m.invoke(es, new Object[] {newRow.getItem().getData()});
				if( b.booleanValue() ) {
					rv = newRow.getCell(columnIndex);
				}

			} else {
				rv = searchCellAboveBelow(newRow, viewer, columnIndex, above);
			}
		}
		} catch( Exception e ) {
			e.printStackTrace();
		}

		return rv;
	}

	private ViewerCell searchPreviousCell(ViewerRow row,
			ViewerCell currentCell, ViewerCell originalCell, ColumnViewer viewer) {
		ViewerCell rv = null;
		ViewerCell previousCell;

		if (currentCell != null) {
			previousCell = getNeighbor(currentCell,ViewerCell.LEFT, true);
		} else {
			if (row.getColumnCount() != 0) {
				previousCell = row.getCell(getCreationIndex(row,row
						.getColumnCount() - 1));
			} else {
				previousCell = row.getCell(0);
			}

		}

		// No endless loop
		if (originalCell.equals(previousCell)) {
			return null;
		}

		if (previousCell != null) {
			if (isCellEditable(viewer, previousCell)) {
				rv = previousCell;
			} else {
				rv = searchPreviousCell(row, previousCell, originalCell, viewer);
			}
		} else {
			if ((feature & TABBING_CYCLE_IN_ROW) == TABBING_CYCLE_IN_ROW) {
				rv = searchPreviousCell(row, null, originalCell, viewer);
			} else if ((feature & TABBING_MOVE_TO_ROW_NEIGHBOR) == TABBING_MOVE_TO_ROW_NEIGHBOR) {
				ViewerRow rowAbove = row.getNeighbor(ViewerRow.ABOVE, false);
				if (rowAbove != null) {
					rv = searchPreviousCell(rowAbove, null, originalCell,
							viewer);
				}
			}
		}

		return rv;
	}

	private ViewerCell searchNextCell(ViewerRow row, ViewerCell currentCell,
			ViewerCell originalCell, ColumnViewer viewer) {
		ViewerCell rv = null;

		ViewerCell nextCell;

		if (currentCell != null) {
			nextCell = getNeighbor(currentCell,ViewerCell.RIGHT, true);
		} else {
			nextCell = row.getCell(getCreationIndex(row,0));
		}

		// No endless loop
		if (originalCell.equals(nextCell)) {
			return null;
		}

		if (nextCell != null) {
			if (isCellEditable(viewer, nextCell)) {
				rv = nextCell;
			} else {
				rv = searchNextCell(row, nextCell, originalCell, viewer);
			}
		} else {
			if ((feature & TABBING_CYCLE_IN_ROW) == TABBING_CYCLE_IN_ROW) {
				rv = searchNextCell(row, null, originalCell, viewer);
			} else if ((feature & TABBING_MOVE_TO_ROW_NEIGHBOR) == TABBING_MOVE_TO_ROW_NEIGHBOR) {
				ViewerRow rowBelow = row.getNeighbor(ViewerRow.BELOW, false);
				if (rowBelow != null) {
					rv = searchNextCell(rowBelow, null, originalCell, viewer);
				}
			}
		}

		return rv;
	}

	private boolean isCellEditable(ColumnViewer viewer, ViewerCell cell) {
		try {
			Method m = ColumnViewer.class.getDeclaredMethod("getViewerColumn", new Class[] { int.class });
			m.setAccessible(true);
			ViewerColumn column = (ViewerColumn) m.invoke(viewer, new Object[] { new Integer(cell.getColumnIndex()) });
			m = ViewerColumn.class.getDeclaredMethod("getEditingSupport", null);
			m.setAccessible(true);

			EditingSupport es = (EditingSupport) m.invoke(column, null);

			if( column != null && es != null ) {
				m = EditingSupport.class.getDeclaredMethod("canEdit", new Class[] { Object.class });
				m.setAccessible(true);
//				return true;
				Boolean b = (Boolean) m.invoke(es, new Object[] {cell.getElement()});
				return b.booleanValue();
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		return false;
	}
}

private static ViewerCell getNeighbor(ViewerCell currentCell,
		int directionMask, boolean sameLevel) {
	ViewerRow row;

	if ((directionMask & ViewerCell.ABOVE) == ViewerCell.ABOVE) {
		row = currentCell.getViewerRow().getNeighbor(
				ViewerRow.ABOVE, sameLevel);
	} else if ((directionMask & ViewerCell.BELOW) == ViewerCell.BELOW) {
		row = currentCell.getViewerRow().getNeighbor(
				ViewerRow.BELOW, sameLevel);
	} else {
		row = currentCell.getViewerRow();
	}

	if (row != null) {
		int columnIndex;
		columnIndex = getVisualIndex(row, currentCell
				.getColumnIndex());

		int modifier = 0;

		if ((directionMask & ViewerCell.LEFT) == ViewerCell.LEFT) {
			modifier = -1;
		} else if ((directionMask & ViewerCell.RIGHT) == ViewerCell.RIGHT) {
			modifier = 1;
		}

		columnIndex += modifier;

		if (columnIndex >= 0 && columnIndex < row.getColumnCount()) {
			ViewerCell cell = getCellAtVisualIndex(row, columnIndex);
			if (cell != null) {
				while (cell != null
						&& columnIndex < row.getColumnCount() - 1
						&& columnIndex > 0) {
					if (isVisible(cell)) {
						break;
					}

					columnIndex += modifier;
					cell = getCellAtVisualIndex(row, columnIndex);
					if (cell == null) {
						break;
					}
				}
			}

			return cell;
		}
	}
	return null;
}
private static int getCreationIndex(ViewerRow row, int visualIndex) {
	TableItem item = (TableItem) row.getItem();
	if (item != null && !item.isDisposed() /*
											 * && hasColumns() &&
											 * isValidOrderIndex(visualIndex)
											 */) {
		return item.getParent().getColumnOrder()[visualIndex];
	}
	return visualIndex;
}		 

// Reimplementation of ViewerCell-Methods
private static int getVisualIndex(ViewerRow row, int creationIndex) {
	TableItem item = (TableItem) row.getItem();
	int[] order = item.getParent().getColumnOrder();

	for (int i = 0; i < order.length; i++) {
		if (order[i] == creationIndex) {
			return i;
		}
	}
	return creationIndex;
}
private static ViewerCell getCellAtVisualIndex(ViewerRow row,
		int visualIndex) {
	return getCell(row, getCreationIndex(row, visualIndex));
}

private static boolean isVisible(ViewerCell cell) {
	return getWidth(cell) > 0;
}

private static ViewerCell getCell(ViewerRow row, int index) {
	return row.getCell(index);
}
private static int getWidth(ViewerCell cell) {
	TableItem item = (TableItem) cell.getViewerRow().getItem();
	return item.getParent().getColumn(cell.getColumnIndex())
			.getWidth();
}

}
