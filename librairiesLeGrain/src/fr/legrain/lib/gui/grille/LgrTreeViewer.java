package fr.legrain.lib.gui.grille;

import java.io.File;
import java.io.FileInputStream;
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
import org.eclipse.jface.viewers.CheckboxTreeViewer;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.TreeViewerColumn;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerSorter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeColumn;
import org.eclipse.ui.PlatformUI;

import fr.legrain.lib.data.LibConversion;

//public class LgrTreeViewer extends CheckboxTreeViewer{
//public class LgrTreeViewer extends TreeViewer{
public class LgrTreeViewer {

	static Logger logger = Logger.getLogger(LgrTreeViewer.class.getName());
	
	static private PropertiesConfiguration listeChampGrille = new PropertiesConfiguration();
	private String[] champsObjetCompare;
	private String[] titresColonnes;
	private Class typeObjetCompare;
	private TreeColumn column = null;
	//private TreeViewer viewer;
	private CheckboxTreeViewer viewer;
	
	/**
	 * 
	 */
	private String[] listeChamp = null;
	
	
//	public LgrTreeViewer(Composite parent, int style) {
//		super(parent, style);
//		this.viewer = new TreeViewer(this.getTree());
//		// TODO Auto-generated constructor stub
//	}
//
//	public LgrTreeViewer(Composite parent) {
//		super(parent);
//		this.viewer = new TreeViewer(this.getTree());
//		// TODO Auto-generated constructor stub
//	}
//
//	public LgrTreeViewer(Tree tree) {
//		super(tree);
//		this.viewer = new TreeViewer(this.getTree());
//		// TODO Auto-generated constructor stub
//	}

	public LgrTreeViewer(CheckboxTreeViewer treeViewer) {
		this.viewer = treeViewer;
	}

//	/**
//	 * {@inheritDoc}
//	 */
//	public LgrTreeViewer(Composite parent) {
//		super(parent);
//	}
//
//	
//	/**
//	 * {@inheritDoc}
//	 */
//	public LgrTreeViewer(Composite parent, int style) {
//		super(parent, style);
//	}
//
//	public LgrTreeViewer(Table table) {
//		super(table);
//	}
	
	/**
	 * Sélectionne l'objet à la position <code>row</code> dans la table et dans le viewer associé.
	 * Si <code>row</code> est invalide, la 1ère ligne est sélectionné
	 * @param row - position de la ligne à sélectionner
	 * @return vrai ssi une ligne a été sélectionné
	 */
	public boolean selectionGrille(int row) {
		boolean result = false;
//		StructuredSelection s = null;
//		if (row>0 && row<this.getTree().getItemCount()){
//			s = new StructuredSelection(this.getElementAt(row));
//		} else {
//			if(this.getTree().getItemCount()>0)
//				if (this.getElementAt(0)!=null)
//					s = new StructuredSelection(this.getElementAt(0));
//		}
//		if(s!=null) {
//			this.setSelection(s,true);
//			this.setSelectionToWidget(s, true);
//			result =true;
//		}
		return result;
	}

	
	public int selectionGrille(Object row) {
		int rang = 0;
//		if (row!=null ){
//			this.findItem(row);
//			rang = this.indexForElement(row)-1;
//		}
		return rang;
	}
	
	public int findPositionColonne(String titreColonne){
		boolean trouve = false;
		int i = 0;
		while(!trouve && i<viewer.getTree().getColumns().length){
			if(viewer.getTree().getColumns()[i].getText().equals(titreColonne)) {
				trouve = true;
			}
			i++;
		}
		if(trouve)
			return i-1;
		else
			return -1;
	}

	public String findTitreColonne(String titreColonne){
		int pos = findPositionColonne(titreColonne);
		if(pos!=-1) {
			return viewer.getTree().getColumns()[pos].getText();
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
	
	
	/**
	 * Permet de trier la grille à la création sur le champ <code>colonneTri</code> si celui-ci existe dans
	 * la liste des champs
	 * @param typeObjetCompare - Classe des objets contenus dans le viewer
	 * @param tree
	 * @param nomClasse - nom de la classe appelante (controller)
	 * @param nomFichierChamps - fichier properties
	 * @param colonneTri - numéro du champs à trier
	 */
	public void createTableCol(Class typeObjetCompare,Tree tree, String nomClasse, String nomFichierChamps,int colonneTri ){
		try {
			champsObjetCompare = setListeChampGrille(nomClasse,nomFichierChamps);
			titresColonnes = setListeTitreChampGrille(nomClasse,nomFichierChamps);
			this.typeObjetCompare = typeObjetCompare;
		} catch (Exception e1) {
			logger.error(e1);
		}
		videTableViewer();
		tree.setHeaderVisible(true);
		tree.setLinesVisible(true);

		String[] listeTitreCol;
		String[] listeTailleCol;
		try {
			listeTitreCol = setListeTitreChampGrille(nomClasse,nomFichierChamps);
			listeTailleCol= setListeTailleChampGrille(nomClasse,nomFichierChamps);
			TreeColumn col;
//			TreeViewerColumn col;
			for (int i = 0; i < listeTitreCol.length; i++) {
				col = new TreeColumn(tree, SWT.NONE);		
				col.setWidth(LibConversion.stringToInteger(listeTailleCol[i]) );
				col.setText(listeTitreCol[i]);
				if (i==colonneTri)column = col;
//				col = new TreeViewerColumn(viewer, SWT.NONE);		
//				col.getColumn().setWidth(LibConversion.stringToInteger(listeTailleCol[i]) );
//				col.getColumn().setText(listeTitreCol[i]);
//				if (i==colonneTri)column = col.getColumn();
			}
			
			modifCellEditors(tree);
		} catch (Exception e) {
			logger.debug(e);
		}
		if(column!=null)
			viewer.setSorter(new LgrSorter(column,SWT.UP));
	}

	public void createTableCol(Tree tree, String[] titreColonnes, String[] tailleColonnes ){
		videTableViewer();
		tree.setHeaderVisible(true);
		tree.setLinesVisible(true);

		String[] listeTitreCol;
		String[] listeTailleCol;
		try {
			listeTitreCol = titreColonnes;
			titresColonnes = listeTitreCol;
			listeTailleCol= tailleColonnes;
			TreeColumn col;
   			for (int i = 0; i < listeTitreCol.length; i++) {
   				col = new TreeColumn(tree, SWT.NONE);		
				col.setWidth(LibConversion.stringToInteger(listeTailleCol[i]) );
				col.setText(listeTitreCol[i]);
			}
   			modifCellEditors(tree);
		} catch (Exception e) {
			logger.debug(e);
		}
	}
	
	public void videTableViewer(){
		viewer.getTree().removeAll();
		while(viewer.getTree().getColumnCount()>0){
			viewer.getTree().getColumn(viewer.getTree().getColumnCount()-1).setData(null);
			viewer.getTree().getColumn(viewer.getTree().getColumnCount()-1).dispose();
		}
	}

	public void createTableCol(Tree tree, String nomClasse, String nomFichierChamps ){
		videTableViewer();
		tree.setHeaderVisible(true);
		tree.setLinesVisible(true);

		String[] listeTitreCol;
		String[] listeTailleCol;
		try {
			listeTitreCol = setListeTitreChampGrille(nomClasse,nomFichierChamps);
			titresColonnes = listeTitreCol;
			listeTailleCol= setListeTailleChampGrille(nomClasse,nomFichierChamps);
			TreeColumn col;
   			for (int i = 0; i < listeTitreCol.length; i++) {
   				col = new TreeColumn(tree, SWT.NONE);		
				col.setWidth(LibConversion.stringToInteger(listeTailleCol[i]) );
				col.setText(listeTitreCol[i]);
			}
   			modifCellEditors(tree);
		} catch (Exception e) {
			logger.debug(e);
		}
	}
	
	//NOUVEAU
	/**
	 * table
	 * classe des objets
	 * titres des colonnes
	 * 
	 */
	public TreeViewerColumn[] createTableViewerCol(Tree grille, String nomClasse, String nomFichierChamps ){
		videTableViewer();
		grille.setHeaderVisible(true);
		grille.setLinesVisible(true);

		String[] listeTitreCol;
		String[] listeTailleCol;
		TreeViewerColumn[] liste = null;
		try {
			listeTitreCol = setListeTitreChampGrille(nomClasse,nomFichierChamps);
			listeTailleCol= setListeTailleChampGrille(nomClasse,nomFichierChamps);
			liste = new TreeViewerColumn[listeTitreCol.length];
			TreeViewerColumn col;
   			for (int i = 0; i < listeTitreCol.length; i++) {
				col = new TreeViewerColumn(viewer, SWT.NONE);		
				col.getColumn().setWidth(LibConversion.stringToInteger(listeTailleCol[i]) );
				col.getColumn().setText(listeTitreCol[i]);
				//col.setEditingSupport(editingSupport);
				liste[i]=col;
			}
   			modifCellEditors(grille);
   			return liste;
		} catch (Exception e) {
			logger.debug(e);
		}
		return liste;
	}

	
	
	private void modifCellEditors(Tree tree){// Create the cell editors
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
	
	/**
	 * Active le tri sur les colonnes
	 * @param typeObjetCompare - Classe des objets contenus dans le viewer
	 * @param nomClasse - nom de la classe appelante (controller)
	 * @param nomFichierChamps - fichier properties
	 */
	public void tri(Class typeObjetCompare, String nomClasse, String nomFichierChamps) {
		try {
//			 champsObjetCompare = setListeChampGrille(nomClasse,nomFichierChamps);
//			 titresColonnes = setListeTitreChampGrille(nomClasse,nomFichierChamps);
//			 this.typeObjetCompare = typeObjetCompare;
//
//			 Listener sortListener = new Listener() {
//				 public void handleEvent(Event e) {
//					 // determination de la colonne de tri et du sens
//					 TableColumn sortColumn = getTree().getSortColumn();
//					 TableColumn currentColumn = (TableColumn) e.widget;
//					 int dir = getTree().getSortDirection();
//					 if (sortColumn == currentColumn) {
//						 dir = dir == SWT.UP ? SWT.DOWN : SWT.UP;
//					 } else {
//						 getTree().setSortColumn(currentColumn);
//						 dir = SWT.UP;
//					 }
//					 getTree().setSortDirection(dir);
//					 setSorter(new LgrSorter(currentColumn,dir));
//				 }
//			 };
//			 
//			 //branchement du tri sur toutes les colonnes
//			 for (int i = 0; i<getTree().getColumns().length; i++) {
//				 getTree().getColumns()[i].addListener(SWT.Selection, sortListener);
//			 }
			 
		} catch(Exception e) {
			logger.error("",e);
		}
	}

	private class LgrSorter extends ViewerSorter {
		private TreeColumn column = null;
		private int dir = SWT.DOWN;
		private int i = -1;
		private boolean trouve = false;

		/**
		 * @param column - colonne sur laquelle porte le tri
		 * @param dir - sens du tri (SWT.UP/SWT.DOWN)
		 */
		public LgrSorter(TreeColumn column, int dir) {
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
				
				Object propertyE1 = null;
				Object propertyE2 = null;
				
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
	
	public void setInput(Object list){
		viewer.setInput(null);
		viewer.setInput(list);
		viewer.refresh();
	}

	public void setInput(WritableList list){
		viewer.setInput(null);
		viewer.setInput(list);
		viewer.refresh();
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

	public CheckboxTreeViewer getViewer() {
		return viewer;
	}


}
