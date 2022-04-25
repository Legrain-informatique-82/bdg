package fr.legrain.gestCom.librairiesEcran.swt;


import java.io.File;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Types;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;
import org.eclipse.birt.report.viewer.utilities.WebappAccessor;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.core.commands.NotEnabledException;
import org.eclipse.core.commands.NotHandledException;
import org.eclipse.core.commands.common.NotDefinedException;
import org.eclipse.core.databinding.observable.list.WritableList;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ComboBoxCellEditor;
import org.eclipse.jface.viewers.ICellModifier;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.browser.IWorkbenchBrowserSupport;
import org.osgi.framework.Bundle;

import fr.legrain.gestCom.Appli.Const;
import fr.legrain.gestCom.Appli.IB_APPLICATION;
import fr.legrain.gestCom.librairiesEcran.LibrairiesEcranPlugin;
import fr.legrain.lib.data.ExceptLgr;
import fr.legrain.lib.data.LgrConstantes;
import fr.legrain.lib.data.LibDate;
import fr.legrain.lib.gui.PaResultatVisu;
import fr.legrain.lib.gui.PaSelectionVisualisation;
import fr.legrain.lib.gui.ParamAffiche;
import fr.legrain.lib.gui.ResultAffiche;
import fr.legrain.lib.gui.RetourEcranEvent;
import fr.legrain.lib.gui.RetourEcranListener;
import fr.legrain.lib.gui.grille.LgrTableViewer;
import fr.legrain.libLgrBirt.WebViewerUtil;




public  class BaseControllerSelection extends JPABaseControllerSWTStandard  
implements  RetourEcranListener {
	private static final String PREDICAT_CONTAINING = " CONTAINING ";
	private static final String PREDICAT_START = " STARTING WITH ";
	private static final String PREDICAT_LIKE = " LIKE ";
	private static final String PREDICAT_BETWEEN = " BETWEEN ";
	private static final String PREDICAT_INF_EGALE = " <= ";
	private static final String PREDICAT_SUP_EGALE = " >= ";
	private static final String PREDICAT_EGALE = " = ";
	
	private static final String CHAMPS_PROPERTY = "champs";
	private static final String VALUE_DEBUT_PROPERTY = "valeur_deb";
	private static final String VALUE_FIN_PROPERTY = "valeur_fin";
	
	private static final String CODE_REQUETE_PROPERTY = "CODE_REQUETE";
	private static final String REQUETE_PROPERTY = "REQUETE";
	private static final String CHAMPSREQUETE_PROPERTY = "CHAMPS";
	private static final String TITRE_CHAMPS_PROPERTY = "TITRE_CHAMPS";
	private static final String TAILLE_CHAMPS_PROPERTY = "TAILLE_CHAMPS";
	private static final String MODULE_PROPERTY = "MODULE";
	private static final String IMPRESSION_PROPERTY = "IMPRESSION";
	private static final String IDENTIFIANT_PROPERTY = "identifiant";
	private static final String ID_EDITEUR_PROPERTY = "id_editeur";
	private static final String ID_PLUGIN_PROPERTY = "id_plugin";
	private static final String ID_GROUPBY_PROPERTY = "GROUPBY";
	private static final String PROCEDURE_PROPERTY = "PROCEDURE";
	private static final String PARAM_PROPERTY = "PARAM";
	
	private boolean extraction = false;
	
	private ArrayList<String> listeDBOOL;
	
	public static final String C_COMMAND_RECHERCHER_ID 	= "fr.legrain.gestionCommerciale.rechercher";
	public static final String C_COMMAND_REINITIALISER_ID 	= "fr.legrain.gestionCommerciale.reinitialiser";
	public static final String C_COMMAND_REINITIALISER_GLOBAL_ID 	= "fr.legrain.gestionCommerciale.reinitialiser.global";

	private PaSelectionVisualisation vue = null;
	private LgrTableViewer tableViewerSelection;
	private LgrTableViewer tableViewerTotaux;
	private LgrTableViewer tableViewerRequete;
	private String queryWhere = null;
	private String queryDeb = null;
	private String param_procedure = null;
	private String nomClassController = null;
	private String module = null;
	private String nomTable = null;

	private String valeur_Deb="";
	private String valeur_Fin="";
	private String col="";
	private Object ecranAppelant = null;
	private int numRecherche=0;

	protected ActionRechercher actionRechercher = new ActionRechercher(LgrConstantes.C_LIB_BTNRECHERCHER);
	protected HandlerRechercher handlerRechercher = new HandlerRechercher();
	protected ActionReinitialiser actionReinitialiser = new ActionReinitialiser(LgrConstantes.C_LIB_BTNRECHERCHER);
	protected HandlerReinitialiser handlerReinitialiser = new HandlerReinitialiser();
	protected HandlerReinitialiserGlobal handlerReinitialiserGlobal = new HandlerReinitialiserGlobal();


	protected String[] listeChampSelection; 
	protected String[] tailleChampSelection;
	protected String[] titreChampSelection;
	protected String impression;
	protected String identifiant;
	protected String idEditor;
	protected String idPlugin;
	protected String requete;
	protected String groubBy;
	protected String procedure;
	protected String param;
	protected String[] listeParam;

	public ComboBoxCellEditor combo = null;
	static Logger logger = Logger.getLogger(BaseControllerSelection.class.getName());

	public BaseControllerSelection(PaSelectionVisualisation vue) {
		super.setVue(vue);
		this.vue = vue;
	}

	public BaseControllerSelection() {
		super();
	}

	public BaseControllerSelection(PaSelectionVisualisation vue, String nomTable,String nomController) {
		super.setVue(vue);
		this.vue = vue;
		this.nomClassController = nomController;
		this.nomTable = nomTable;

		initController();
	}
	
	protected void initImageBouton() {
		super.initImageBouton();
//		String C_IMAGE_RECHERCHER = "/icons/magnifier.png";
//		String C_IMAGE_REINITIALISER = "/icons/arrow_refresh.png";
		if(vue instanceof PaSelectionVisualisation) {
			((PaSelectionVisualisation)vue).getPaBtnRecherche().getBtnImprimer().setImage(LibrairiesEcranPlugin.ir.get(LibrairiesEcranPlugin.IMAGE_RECHERCHER));
			((PaSelectionVisualisation)vue).getPaBtnRecherche().getBtnFermer().setImage(LibrairiesEcranPlugin.ir.get(LibrairiesEcranPlugin.IMAGE_REINITIALISER));
			((PaSelectionVisualisation)vue).getPaBtnImprimer().getBtnAnnuler().setImage(LibrairiesEcranPlugin.ir.get(LibrairiesEcranPlugin.IMAGE_REINITIALISER));
			((PaSelectionVisualisation)vue).getPaBtnImprimer().getBtnFermer().setImage(LibrairiesEcranPlugin.ir.get(LibrairiesEcranPlugin.IMAGE_FERMER));
			((PaSelectionVisualisation)vue).getPaBtnImprimer().getBtnImprimer().setImage(LibrairiesEcranPlugin.ir.get(LibrairiesEcranPlugin.IMAGE_IMPRIMER));
			vue.layout(true);
		}
	}
	
	protected void attachCellEditorsSelection(final TableViewer viewer, Composite parent) {
		viewer.setCellModifier(new ICellModifier() {
			public boolean canModify(Object element, String property) {
				if(procedure.equals("1") && ((CHAMPS_PROPERTY.equals(property)||(VALUE_FIN_PROPERTY.equals(property)))))
					return false;
					else	
				return true;
			}

			public Object getValue(Object element, String property) {
				if (CHAMPS_PROPERTY.equals(property))
					return ((EditableTableItem) element).champs;
				else
					if (VALUE_DEBUT_PROPERTY.equals(property))
						return ((EditableTableItem) element).valeur_Deb;
					else
						if (VALUE_FIN_PROPERTY.equals(property))
							return ((EditableTableItem) element).valeur_Fin;
//				else
//				if (BOOLEAN_PROPERTY.equals(property))
//				return ((EditableTableItem) element).bool;
////				return LibConversion.booleanToStringFrancais(((EditableTableItem) element).bool);
						else return null;
			}

			public void modify(Object element, String property, Object value) {
				TableItem tableItem = (TableItem) element;
				EditableTableItem data = (EditableTableItem) tableItem
				.getData();
				if (CHAMPS_PROPERTY.equals(property)){
					data.champs = (Integer) value;
					//data.champs = value.toString();
				}else
					if (VALUE_DEBUT_PROPERTY.equals(property))
						data.valeur_Deb = value.toString();
					else
						if (VALUE_FIN_PROPERTY.equals(property))
							data.valeur_Fin = value.toString();
//				else
//				if (BOOLEAN_PROPERTY.equals(property))
//				data.bool =   (Boolean)value;
				viewer.refresh(data);
			}
		});
        combo = new ComboBoxCellEditor(parent, titreChampSelection);
		viewer.setCellEditors(new CellEditor[] { combo, new TextCellEditor(parent), new TextCellEditor(parent)});
//		viewer.setCellEditors(new CellEditor[] { new TextCellEditor(parent),
//		new ComboBoxCellEditor(parent, listeChamp) });
		viewer
		.setColumnProperties(new String[] { CHAMPS_PROPERTY,VALUE_DEBUT_PROPERTY ,VALUE_FIN_PROPERTY });
	}


	protected void attachContentProviderSelection(TableViewer viewer) {
		viewer.setContentProvider(new IStructuredContentProvider() {
			public Object[] getElements(Object inputElement) {
				return ((List<EditableTableItem>)inputElement).toArray();
				// return (Object[]) inputElement;
			}

			public void dispose() {
			}

			public void inputChanged(Viewer viewer, Object oldInput,
					Object newInput) {
			}
		});
	}

	protected void attachLabelProviderSelection(TableViewer viewer) {
		viewer.setLabelProvider(new ITableLabelProvider() {
			public Image getColumnImage(Object element, int columnIndex) {
				return null;
			}

			public String getColumnText(Object element, int columnIndex) {
				switch (columnIndex) {
				case 0:
					Number index = ((EditableTableItem) element).champs;
					return titreChampSelection[index.intValue()];		          
					//return ((EditableTableItem) element).champs;
				case 1:
					return ((EditableTableItem) element).valeur_Deb;
				case 2:
					return ((EditableTableItem) element).valeur_Fin;
//					case 3:
//	//return LibConversion.booleanToStringFrancais(((EditableTableItem) element).bool);
//					return ((EditableTableItem) element).bool.toString();
				default:
					return "Invalid column: " + columnIndex;
				}
			}

			public void addListener(ILabelProviderListener listener) {
			}

			public void dispose() {
			}

			public boolean isLabelProperty(Object element, String property) {
				return false;
			}

			public void removeListener(ILabelProviderListener lpl) {
			}
		});
	}


	public String[] getListeChamp() {
		return listeChampSelection;
	}


	public void setListeChamp(String[] listeChamp) {
		this.listeChampSelection = listeChamp;
	}	

	public class EditableTableItem {
		public Integer champs;
		public String valeur_Deb;
		public String valeur_Fin;
//		public Boolean bool;

		public EditableTableItem(Integer champs, String valeur_Deb, String valeur_Fin /*, Boolean bool*/) {
			this.champs = champs;
			this.valeur_Deb = valeur_Deb;
			this.valeur_Fin = valeur_Fin;
//			this.bool = bool;
		}
	}

	protected class HandlerRechercher extends LgrAbstractHandler {
		public Object execute(ExecutionEvent event) throws ExecutionException{
			try {
				actRechercher();
			} catch (Exception e) {
				logger.error("",e);
			}
			return event;
		}
	}
	protected class HandlerReinitialiser extends LgrAbstractHandler {
		public Object execute(ExecutionEvent event) throws ExecutionException{
			try {
				actReinitialiser();
			} catch (Exception e) {
				logger.error("",e);
			}
			return event;
		}
	}
	
	protected class HandlerReinitialiserGlobal extends LgrAbstractHandler {
		public Object execute(ExecutionEvent event) throws ExecutionException{
			try {
				actReinitialiserGlobal();
			} catch (Exception e) {
				logger.error("",e);
			}
			return event;
		}
	}


	protected class ActionRechercher extends Action {
		public ActionRechercher(String name) {
			super(name);
		}

		@Override
		public void run() {
			super.run();
			try {
				handlerService.executeCommand(C_COMMAND_RECHERCHER_ID, null);
			} catch (ExecutionException e) {
				logger.error("",e);
			} catch (NotDefinedException e) {
				logger.error("",e);
			} catch (NotEnabledException e) {
				logger.error("",e);
			} catch (NotHandledException e) {
				logger.error("",e);
			}
		}

	}
	protected class ActionReinitialiser extends Action {
		public ActionReinitialiser(String name) {
			super(name);
		}

		@Override
		public void run() {
			super.run();
			try {
				handlerService.executeCommand(C_COMMAND_REINITIALISER_ID, null);
			} catch (ExecutionException e) {
				logger.error("",e);
			} catch (NotDefinedException e) {
				logger.error("",e);
			} catch (NotEnabledException e) {
				logger.error("",e);
			} catch (NotHandledException e) {
				logger.error("",e);
			}
		}

	}


	public LgrTableViewer getTableViewer() {
		return tableViewerSelection;
	}


	public void setTableViewer(LgrTableViewer tableViewer) {
		this.tableViewerSelection = tableViewer;
	}



	public LgrTableViewer getTableViewerTotaux() {
		return tableViewerTotaux;
	}


	public void setTableViewerTotaux(LgrTableViewer tableViewerTotaux) {
		this.tableViewerTotaux = tableViewerTotaux;
	}


	public Composite getVue() {
		return vue;
	}


	public void setVue(PaSelectionVisualisation vue) {
		super.setVue(vue);
		this.vue = vue;
	}


	public String getQuery() {
		return queryWhere;
	}


	public void setQuery(String query) {
		this.queryWhere = query;
	}
	
	private class RequeteEditableTableItem {
		public String CODE_REQUETE;
		public String REQUETE;
		public String CHAMPS;
		public String TITRE_CHAMPS;
		public String TAILLE_CHAMPS;
		public String MODULE;
		public String IMPRESSION;
		public String identifiant;
		public String id_editeur;
		public String id_plugin;
		public String GROUPBY;
		public String PROCEDURE;
		public String PARAM;


		
		public RequeteEditableTableItem(String code_requete, String requete,
				String champs, String titre_champs, String taille_champs,
				String module, String impression, String identifiant,
				String id_editeur, String idPlugin,String GROUPBY,String PROCEDURE,String PARAM) {
			super();
			CODE_REQUETE = code_requete;
			REQUETE = requete;
			CHAMPS = champs;
			TITRE_CHAMPS = titre_champs;
			TAILLE_CHAMPS = taille_champs;
			MODULE = module;
			IMPRESSION = impression;
			this.identifiant = identifiant;
			this.id_editeur = id_editeur;
			this.id_plugin = idPlugin;
			this.GROUPBY = GROUPBY;
			this.PROCEDURE = PROCEDURE;
			this.PARAM = PARAM;
		}

		public RequeteEditableTableItem() {
			super();
		}

		public void setCODE_REQUETE(String code_requete) {
			CODE_REQUETE = code_requete;
		}

		public void setREQUETE(String requete) {
			REQUETE = requete;
		}

		public void setTITRE_CHAMPS(String titre_champs) {
			TITRE_CHAMPS = titre_champs;
		}

		public void setTAILLE_CHAMPS(String taille_champs) {
			TAILLE_CHAMPS = taille_champs;
		}

		public void setIMPRESSION(String impression) {
			IMPRESSION = impression;
		}

		public void setIdentifiant(String identifiant) {
			this.identifiant = identifiant;
		}

		public void setId_editeur(String id_editeur) {
			this.id_editeur = id_editeur;
		}

		public void setCHAMPS(String champs) {
			CHAMPS = champs;
		}

		public void setMODULE(String module) {
			MODULE = module;
		}

		public String getId_plugin() {
			return id_plugin;
		}

		public void setId_plugin(String id_plugin) {
			this.id_plugin = id_plugin;
		}

		public String getGROUPBY() {
			return GROUPBY;
		}

		public void setGROUPBY(String id_groupby) {
			GROUPBY = id_groupby;
		}

		public String getPROCEDURE() {
			return PROCEDURE;
		}

		public void setPROCEDURE(String procedure) {
			PROCEDURE = procedure;
		}

		public String getPARAM() {
			return PARAM;
		}

		public void setPARAM(String param) {
			PARAM = param;
		}

	}

	public void bindRequete() {
		try {

			ResultSet rs = IB_APPLICATION.findDatabase().createStatement().
			executeQuery("SELECT  CODE_REQUETE, REQUETE, CHAMPS, TITRE_CHAMPS, TAILLE_CHAMPS, " +
					"MODULE, IMPRESSION, identifiant,id_editeur,id_plugin,groupby,PROC,PARAM FROM TA_VISUALISATION where upper(module) = '"+module.toUpperCase()+"'");

			tableViewerRequete = new LgrTableViewer(vue.getTableRequete());

			tableViewerRequete.createTableCol( vue.getTableRequete(),tableViewerRequete.setListeTitreChampGrille("SelectionRequetes",Const.C_FICHIER_LISTE_CHAMP_GRILLE),
					tableViewerRequete.setListeTailleChampGrille("SelectionRequetes",Const.C_FICHIER_LISTE_CHAMP_GRILLE));
			
			attachContentProviderRequete(tableViewerRequete);
			attachLabelProviderRequete(tableViewerRequete);
			attachCellEditorsRequete(tableViewerRequete, vue.getTableRequete());
			
			for (int i = 0; i < tableViewerRequete.getTable().getColumns().length; i++) {
				//if(i!=0)
					tableViewerRequete.getTable().getColumns()[i].setResizable(false);
			}
////

			LinkedList<RequeteEditableTableItem> listeInput = new LinkedList<RequeteEditableTableItem>();
			RequeteEditableTableItem item = null;
			int j = 1;
			while (rs.next()) {
				item = new RequeteEditableTableItem();
						
				//item = new TableItem(vue.getTableRequete(),j);
				item.setCODE_REQUETE(rs.getString("CODE_REQUETE"));
				item.setId_editeur(rs.getString("id_editeur"));
				item.setIdentifiant(rs.getString("identifiant"));
				item.setMODULE(rs.getString("MODULE"));
				item.setIMPRESSION(rs.getString("IMPRESSION"));
				item.setREQUETE(rs.getString("REQUETE"));
				item.setCHAMPS(rs.getString("CHAMPS"));
				item.setTAILLE_CHAMPS(rs.getString("TAILLE_CHAMPS"));
				item.setTITRE_CHAMPS(rs.getString("TITRE_CHAMPS"));
				item.setId_plugin(rs.getString("ID_PLUGIN"));
				item.setGROUPBY(rs.getString("GROUPBY"));
				item.setPROCEDURE(rs.getString("PROC"));
				item.setPARAM(rs.getString("PARAM"));
				listeInput.add(item);
			} 
			

			WritableList wl = new WritableList(listeInput,RequeteEditableTableItem.class);
			tableViewerRequete.setInput(wl);
			
			selectionRequete(vue.getTableRequete().getItem(0));
			tableViewerRequete.selectionGrille(0);
			vue.getTableRequete().addSelectionListener(new SelectionListener(){

				public void widgetDefaultSelected(SelectionEvent e) {
					selectionRequete((TableItem)e.item);
					initGrilleSelection();
				}

				public void widgetSelected(SelectionEvent e) {
					selectionRequete((TableItem)e.item);
					initGrilleSelection();
				}

			});				
		} catch (Exception e) {
			logger.error("", e);
		}
	}


	public void selectionRequete(TableItem rang){
		TableItem selection=null;

		listeChampSelection=new String[]{};
		titreChampSelection=new String[]{};
		tailleChampSelection=new String[]{};
		impression="";

		if(rang!=null || vue.getTableRequete().getSelectionIndex()==-1 )
			selection =rang;
		else
			selection =vue.getTableRequete().getItem(vue.getTableRequete().getSelectionIndex());
		if(selection!=null){
			requete = selection.getText(1);
			listeChampSelection = selection.getText(2).split(";");
			titreChampSelection = selection.getText(3).split(";");
			tailleChampSelection = selection.getText(4).split(";");
			impression = selection.getText(6);
			identifiant = selection.getText(7);
			idEditor = selection.getText(8);
			idPlugin = selection.getText(9);
			groubBy = selection.getText(10);
			procedure = selection.getText(11);
			param = selection.getText(12);
		}
	}
	public void initGrilleSelection(){
		//liberation si déjà rempli
		vue.getGrilleSelection().removeAll();
		selectionRequete(null);
		LinkedList<EditableTableItem> listeInput = new LinkedList<EditableTableItem>();
		EditableTableItem item = null;
		for (int i = 0; i < titreChampSelection.length; i++) {
			item = new EditableTableItem(i,"","");
			listeInput.add(item);
			//tableViewer.add(item);
		}

		WritableList wl = new WritableList(listeInput,EditableTableItem.class);
		tableViewerSelection.setInput(wl);

		
	}
	public void bindSelection() {
		try {
			tableViewerSelection = new LgrTableViewer(vue.getGrilleSelection());
			tableViewerSelection.createTableCol( vue.getGrilleSelection(), new String[]{"CHAMPS REQUETE","VALEUR DEBUT","VALEUR FIN"}, new String[]{"200","100","100"});
			attachContentProviderSelection(tableViewerSelection);
			attachLabelProviderSelection(tableViewerSelection);
			attachCellEditorsSelection(tableViewerSelection, vue.getGrilleSelection());
		} catch (Exception e) {
			logger.error("", e);
		}
	}



	private String initRequete(String query,int typeColumn,String nameColumn){
		String predicat = "=";
//		private static final String PREDICAT_CONTAINING = " CONTAINING ";
//		private static final String PREDICAT_START = " STARTING WITH ";
//		private static final String PREDICAT_LIKE = " LIKE ";
//		private static final String PREDICAT_BETWEEN = " BETWEEN ";
//		private static final String PREDICAT_INF_EGALE = " <= ";
//		private static final String PREDICAT_SUP_EGALE = " >= ";
//		private static final String PREDICAT_EGALE = " = ";
		
//		if(rs.getMetaData().getColumnType(h)==Types.SMALLINT && 
//				isChampDbool(rs.getMetaData().getColumnName(h))){
//			if(rs.getBoolean(h))
//				valeur[h-1]=LibConversion.booleanToStringFrancais(rs.getBoolean(h));
//			}
		
		switch(typeColumn) {    //LibConversion.StringNotNull()
		case Types.INTEGER :
		case Types.SMALLINT :
		case Types.NUMERIC:
		case Types.FLOAT:
			if(typeColumn==Types.SMALLINT && isChampDbool(nameColumn)){
				valeur_Deb=valeur_Deb.toUpperCase().replace("FALSE", "0").replace("TRUE", "1").replace("FAUX", "0").replace("VRAI", "1");
				valeur_Fin=valeur_Fin.toUpperCase().replace("FALSE", "0").replace("TRUE", "1").replace("FAUX", "0").replace("VRAI", "1");
			}
			if (!valeur_Deb.equals("")&& !valeur_Fin.equals("")) {
				if (valeur_Deb.compareToIgnoreCase(valeur_Fin) <= 0) {
					predicat=PREDICAT_BETWEEN;
					query += " ("+col+") "+predicat+" '"
					+ valeur_Deb + " ' AND '"
					+ valeur_Fin + "'";
				}
			}
			if (valeur_Deb.equals("")&& !valeur_Fin.equals("")) {
				if(isChampDbool(nameColumn))predicat=PREDICAT_EGALE;
				else
				predicat=PREDICAT_INF_EGALE;
				query += " ("+col+") "+predicat+" '"
				+ valeur_Fin + "'";
			}
			if (valeur_Fin.equals("")&& !valeur_Deb.equals("")) {
				if(isChampDbool(nameColumn))predicat=PREDICAT_EGALE;
				else
				predicat=PREDICAT_SUP_EGALE;
				query += " ("+col+")  "+predicat+" '"
				+ valeur_Deb + "' ";
			}			
			break;
		case Types.VARCHAR :
		case Types.CHAR :
			valeur_Deb=valeur_Deb.replace("*", "%").replace("'", "''").trim();
			valeur_Fin=valeur_Fin.replace("*", "%").replace("'", "''").trim();
			String value=valeur_Deb;
			//si les 2 valeurs sont remplies
			if (!valeur_Deb.equals("")&& !valeur_Fin.equals("")) {
				//si valeurs égales et # de %
				if (valeur_Deb.compareToIgnoreCase(valeur_Fin) == 0 && !valeur_Deb.equals("%") ||
					// ou 
						//si valeur_Deb Inférieure à valeur_Fin 
					(valeur_Deb.compareToIgnoreCase(valeur_Fin) < 0)) {						
						query += " ("+col+") "+PREDICAT_BETWEEN+" '"
						+ value.toUpperCase()
						+"' AND '"	
						+ valeur_Fin.toUpperCase()
						+ "['";
					}
					else{
						//si valeur_Deb supérieur à valeur_Fin, on ignore valeur_Fin
						predicat=retournePredicat(valeur_Deb);
						if(predicat.equals(PREDICAT_CONTAINING)){
							value=valeur_Deb.replace("%", "");
						}
						query += " (("+col+") "+predicat+" '"
						+ value.toUpperCase()
						+ "' or "
						+ "("+col+") "+PREDICAT_SUP_EGALE+" '"
								+ valeur_Deb.toUpperCase()
						+ "')";	
					}
			}
			//si que valeur_Fin remplie
			if (valeur_Deb.equals("")&& !valeur_Fin.equals("")) {
				predicat=PREDICAT_INF_EGALE;
				query += " ("+col+") "+predicat+" '"
				+ valeur_Fin.toUpperCase() + "['";
			}
			//si que valeur_Deb remplie
			if (valeur_Fin.equals("")&& !valeur_Deb.equals("")) {
				predicat=retournePredicat(valeur_Deb);
				if(predicat.equals(PREDICAT_CONTAINING)){
					value=valeur_Deb.replace("%", "");
				}
				query += " (("+col+") "+predicat+" '"
				+ value.toUpperCase()
				+ "' or "
				+ "("+col+") "+PREDICAT_SUP_EGALE+" '"
						+ valeur_Deb.toUpperCase()
				+ "')";
			}
		break;
		case Types.DATE :
		case Types.TIMESTAMP :
			try {
				if(!valeur_Deb.equals(""))valeur_Deb=LibDate.dateToString(LibDate.stringToDate(valeur_Deb));
				if(!valeur_Fin.equals(""))valeur_Fin=LibDate.dateToString(LibDate.stringToDate(valeur_Fin));
				switch (LibDate.compareTo(valeur_Deb,valeur_Fin)) {
				case 1://dateDeb supérieure à dateFin, pas correct
					if (!valeur_Deb.equals("") && !valeur_Fin.equals("")) {
						predicat=PREDICAT_LIKE;
						query += " ("+col+")  "+predicat+" '%' ";
					}
					break;
				case 0:
				case -1:
					if (!valeur_Deb.equals("") && !valeur_Fin.equals("")) {
						predicat=PREDICAT_BETWEEN;
						query += " ("+col+") "+predicat + "'"
						+ valeur_Deb.replace("/" , ".")
						+ "' AND '"
						+ valeur_Fin.replace("/" , ".") + "'";
					}
					break;
				default:

					break;
				}
				if (valeur_Deb.equals("") && valeur_Fin.equals("")) {
					predicat=PREDICAT_LIKE;
					query += " ("+col+") "+predicat+" '%' ";
				}

				if (valeur_Fin.equals("") && !valeur_Deb.equals("")) {
					predicat=PREDICAT_SUP_EGALE;
					query += " ("+col+") "+predicat+" '"
					+ valeur_Deb.replace("/", ".") + "'";
				}
				if (valeur_Deb.equals("") && !valeur_Fin.equals("")) {
					predicat=PREDICAT_INF_EGALE;
					query += " ("+col+") "+predicat+" '"
					+ valeur_Fin.replace("/", ".") + "' ";
				}
			} catch (ParseException e) {
				MessageDialog.openWarning(vue.getShell(), "Valeur incorrect", "Cette date n'est pas correcte");
				logger.error("",e);
			}
			break;
		}
		if (query.trim().compareToIgnoreCase("Where")==0)return "";
		else
		return query;
	}

	private String initParametres(int typeColumn,String nameColumn){
		return initParametres(typeColumn,nameColumn,true);
	}

		
	private String initParametres(int typeColumn,String nameColumn,Boolean dateString){	
		switch(typeColumn) {    
		case Types.INTEGER :
		case Types.SMALLINT :
		case Types.NUMERIC:
		case Types.FLOAT:
			if(typeColumn==Types.SMALLINT && isChampDbool(nameColumn)){
				valeur_Deb=valeur_Deb.toUpperCase().replace("FALSE", "0").replace("TRUE", "1").replace("FAUX", "0").replace("VRAI", "1");
			}
			if (!valeur_Deb.equals("")) {
				return  valeur_Deb ;
			}else return "null";
			

		case Types.VARCHAR :
		case Types.CHAR :
			if (!valeur_Deb.equals("")) {
				return  "'"+valeur_Deb+"'" ;
			}else return "null";		

		case Types.DATE :
		case Types.TIMESTAMP :
			try {
				if(!valeur_Deb.equals(""))valeur_Deb=LibDate.dateToString(LibDate.stringToDate(valeur_Deb));				
				if (!valeur_Deb.equals("")) {
					if(dateString!=null && dateString)
					  return  "'"+valeur_Deb.replace("/" , ".")+"'" ;
					else return  valeur_Deb.replace("/" , ".") ;
				}else return "null";		
			} catch (Exception e) {
				MessageDialog.openWarning(vue.getShell(), "Valeur incorrect", "Cette date n'est pas correcte");
				logger.error("",e);
			}
			break;
			default : return null;
			}
		return null;
	}

	protected void actReinitialiser()throws Exception {
		bindSelection();
		initGrilleSelection();
	}
	
	protected void actReinitialiserGlobal()throws Exception {
		while (vue.getCTabFolderResultat().getItems().length>0) {
			((CTabItemResultatRecherche)vue.getCTabFolderResultat().getItems()[vue.getCTabFolderResultat().getItems().length-1]).getController().dispose();
			vue.getCTabFolderResultat().getItems()[vue.getCTabFolderResultat().getItems().length-1].dispose();
		} 
		actReinitialiser();
		tableViewerRequete.selectionGrille(0);
	}

	protected void actRechercher() throws Exception {
//		selectionRequete(tableViewerRequete.getTable().getSelection()[0]);
//
//		//Construire la requete
//		queryWhere="";
//		param_procedure="(";
//
//		queryDeb =requete;
//	
//		ResultSetMetaData rsMetadata = IB_APPLICATION.findDatabase().createPreparedStatement(queryDeb+" "+groubBy).getMetaData();
//		listeDBOOL=new ArrayList<String>();
//		//parcourir le metadata pour regarder s'il y a des champs DBOOL
//		for (int i = 1; i <= rsMetadata.getColumnCount(); i++) {
//			if(rsMetadata.getColumnType(i)==Types.SMALLINT){				
//				ResultSet rsTypes = IB_APPLICATION.findDatabase().
//				createStatement().executeQuery("select distinct(RDB$FIELD_SOURCE) from " +
//						"RDB$RELATION_FIELDS	where  RDB$FIELD_NAME = '"+rsMetadata.getColumnName(i)+"' " +
//								"and RDB$FIELD_SOURCE = 'DBOOL'");
//				if(rsTypes.next()){
//					listeDBOOL.add(rsMetadata.getColumnName(i));	
//				}
//			}
//		}
//		listeParam = param.split(";");
//		
//		ResultSet rs = IB_APPLICATION.findDatabase().createStatement().executeQuery(queryDeb+" "+groubBy);
//		if (rs.next()) {
//			//upper(a.code_article) between 'A%' and 'Z%'
//			for (int i = 0; i < tableViewerSelection.getTable().getItemCount(); i++) {
//				if(((EditableTableItem)tableViewerSelection.getTable().getItem(i).getData()).valeur_Deb!=null &&
//						!((EditableTableItem)tableViewerSelection.getTable().getItem(i).getData()).valeur_Deb.equals(""))
//					valeur_Deb =((EditableTableItem)tableViewerSelection.getTable().getItem(i).getData()).valeur_Deb; 
//				if(((EditableTableItem)tableViewerSelection.getTable().getItem(i).getData()).valeur_Fin!=null &&
//						!((EditableTableItem)tableViewerSelection.getTable().getItem(i).getData()).valeur_Fin.equals(""))
//					valeur_Fin =((EditableTableItem)tableViewerSelection.getTable().getItem(i).getData()).valeur_Fin; 
//				if(((EditableTableItem)tableViewerSelection.getTable().getItem(i).getData()).champs!=null &&
//						!((EditableTableItem)tableViewerSelection.getTable().getItem(i).getData()).champs.equals("")){
//					//transformer le titre en vrai champs de la table
//					col =getListeChamp()[((EditableTableItem)tableViewerSelection.getTable().getItem(i).getData()).champs]; 
//				}
//				if((!valeur_Deb.equals("")||!valeur_Fin.equals(""))&&
//						(col!=null&&!col.equals(""))){
//					if(! queryWhere.contains("where")){
//						queryWhere+=" where ";
//					}
//					else{
//						queryWhere+=" and ";
//					}
//					//suivant type de champ
//					int a = 1;
//					int pos = -1;
//					while(pos==-1 && a<=rs.getMetaData().getColumnCount()) {
//						if(rs.getMetaData().getColumnName(a).equals(col)){
//							pos = a;
//						} else {
//							a++;
//						}
//					}
//					if(pos!=-1){
//						queryWhere=initRequete(queryWhere,rs.getMetaData().getColumnType(pos),rs.getMetaData().getColumnName(pos));
//					}
//					if(!param_procedure.equals("")&& !param_procedure.equals("("))
//						param_procedure+=",";
//					param_procedure+=initParametres(rs.getMetaData().getColumnType(pos),rs.getMetaData().getColumnName(pos));
//					for (int j = 0; j < listeParam.length; j++) {
//						if(listeParam[j].toUpperCase().startsWith("&PARAM"+col.toUpperCase())){
//							String valeur=listeParam[j].substring(0,listeParam[j].lastIndexOf("=") );						  
//							listeParam[j]=valeur+"="+initParametres(rs.getMetaData().getColumnType(pos),rs.getMetaData().getColumnName(pos),false);	
//						}
//					}
//					
//				}else
//				{
//					if(!param_procedure.equals("")&& !param_procedure.equals("("))
//						param_procedure+=",";
//				   param_procedure+="null";
//					for (int j = 0; j < listeParam.length; j++) {
//						if(listeParam[j].toUpperCase().startsWith("&PARAM"+col.toUpperCase())){
//							String valeur=listeParam[j].substring(6,listeParam[j].lastIndexOf("=") );						  
//						  listeParam[j]="&__isnull=Param"+col.toUpperCase();	
//						}
//					}
//					
//				}
//				
//
//				valeur_Deb="";
//				valeur_Fin="";
//				col="";
//			}
//			param_procedure+=")";		
//			if(procedure.equals("1")){
//			  queryDeb=queryDeb.replace(queryDeb.substring(queryDeb.indexOf("("),queryDeb.indexOf(")")+1), "");
//			  queryWhere = ""; 
//			}else param_procedure="";
//			System.out.println(queryDeb+param_procedure+queryWhere+" "+groubBy);
//
//
//			
//			LinkedList<EditableTableItem> l = new LinkedList<EditableTableItem>();
//			EditableTableItem e = null;
//			for (Object editableTableItem : ((WritableList)tableViewerSelection.getInput()).subList(0, ((WritableList)tableViewerSelection.getInput()).size())) {
//				e = new EditableTableItem(((EditableTableItem)editableTableItem).champs,((EditableTableItem)editableTableItem).valeur_Deb,((EditableTableItem)editableTableItem).valeur_Fin);
//				l.add(e);
//			}
//			
//			WritableList wl = new WritableList(l,EditableTableItem.class);
//			
//			addOnglet(listeChampSelection,tailleChampSelection,titreChampSelection,queryDeb,param_procedure,queryWhere,groubBy,tableViewerRequete.getSelection(),wl,listeParam);
//		}
	}

	private void addOnglet(String[] listeChamp,String[] tailleChamp,String[] titreChamp,  String queryDeb,String paramProcedure,String queryWhere,String groupBy, ISelection selectionRqt,Object listeInput,String[] listeParam) {
//		PaResultatVisu vueOnglet = new PaResultatVisu(vue.getCTabFolderResultat(),SWT.NONE);
//		OngletResultatController controller = new OngletResultatController(vueOnglet,listeChamp,tailleChamp,titreChamp,queryDeb,paramProcedure,queryWhere,selectionRqt,identifiant,idEditor,listeInput,impression,idPlugin,groupBy,listeParam);
//		CTabItemResultatRecherche onglet = new CTabItemResultatRecherche(vue.getCTabFolderResultat(),SWT.CLOSE,vueOnglet,controller);
//		onglet.setText("Recherche "+(numRecherche+=1));
//		vue.getCTabFolderResultat().setSelection(onglet);
	}




	private void afficheHistorique(ISelection selection){

	}

	protected void initActions() {
		mapCommand = new HashMap<String, IHandler>();

		mapCommand.put(C_COMMAND_RECHERCHER_ID, handlerRechercher);
		mapCommand.put(C_COMMAND_GLOBAL_IMPRIMER_ID, handlerImprimer);
		mapCommand.put(C_COMMAND_GLOBAL_SUPPRIMER_ID, handlerSupprimer);
		mapCommand.put(C_COMMAND_GLOBAL_FERMER_ID, handlerFermer);
		mapCommand.put(C_COMMAND_GLOBAL_ANNULER_ID, handlerAnnuler);
		mapCommand.put(C_COMMAND_REINITIALISER_ID, handlerReinitialiser);
		mapCommand.put(C_COMMAND_REINITIALISER_GLOBAL_ID, handlerReinitialiserGlobal);

		initFocusCommand(String.valueOf(this.hashCode()),listeComposantFocusable,mapCommand);

		if (mapActions == null)
			mapActions = new LinkedHashMap<Button, Object>();

		mapActions.put(vue.getPaBtnRecherche().getBtnFermer(), C_COMMAND_REINITIALISER_ID);
		mapActions.put(vue.getPaBtnRecherche().getBtnImprimer(), C_COMMAND_RECHERCHER_ID);
		mapActions.put(vue.getPaBtnImprimer().getBtnSupprimer(), C_COMMAND_GLOBAL_SUPPRIMER_ID);
		mapActions.put(vue.getPaBtnImprimer().getBtnFermer(), C_COMMAND_GLOBAL_FERMER_ID);
		mapActions.put(vue.getPaBtnImprimer().getBtnImprimer(), C_COMMAND_GLOBAL_IMPRIMER_ID);
		mapActions.put(vue.getPaBtnImprimer().getBtnAnnuler(), C_COMMAND_REINITIALISER_GLOBAL_ID);


	}

	protected void initMapComposantChamps() {
		if (listeComposantFocusable == null)
			listeComposantFocusable = new ArrayList<Control>();
		listeComposantFocusable.add(vue.getTableRequete());
		listeComposantFocusable.add(vue.getGrilleSelection());
		listeComposantFocusable.add(vue.getPaBtnRecherche().getBtnFermer());
		listeComposantFocusable.add(vue.getPaBtnRecherche().getBtnImprimer());
//		listeComposantFocusable.add(vue.getGrilleResultat());
		listeComposantFocusable.add(vue.getPaBtnImprimer().getBtnSupprimer());
		listeComposantFocusable.add(vue.getPaBtnImprimer().getBtnAnnuler());
		listeComposantFocusable.add(vue.getPaBtnImprimer().getBtnImprimer());
		listeComposantFocusable.add(vue.getPaBtnImprimer().getBtnFermer());
//		listeComposantFocusable.add(vue.getGrilleHistorique());
	}
	private void initController() {
		try {
			setGrille(vue.getGrilleSelection());
			setDaoStandard(null);
			setTableViewerStandard(getTableViewer());

			initMapComposantChamps();
			initMapComposantDecoratedField();
			listeComponentFocusableSWT(listeComposantFocusable);
			initFocusOrder();
			initActions();
			initDeplacementSaisie(listeComposantFocusable);

			bindRequete();
			bindSelection();
			initGrilleSelection();
			branchementBouton();
			initEtatBouton();


			Menu popupMenuFormulaire = new Menu(getVue().getShell(), SWT.POP_UP);
			Menu popupMenuGrille = new Menu(getVue().getShell(), SWT.POP_UP);
			Menu[] tabPopups = new Menu[] { popupMenuFormulaire, popupMenuGrille };
			this.initPopupAndButtons(mapActions, tabPopups);
			vue.getPaSelection().setMenu(popupMenuFormulaire);
			vue.getPaBtnImprimer().setMenu(popupMenuFormulaire);
			vue.getCompositeForm().setMenu(popupMenuFormulaire);
			vue.getPaBtnRecherche().setMenu(popupMenuGrille);
			vue.getCTabFolderResultat().setMenu(popupMenuGrille);
			vue.getGrilleSelection().setMenu(popupMenuGrille);
			vue.getGroupResultats().setMenu(popupMenuGrille);
			vue.getGroupRqt().setMenu(popupMenuGrille);
//			vue.getGrilleHistorique().setMenu(popupMenuGrille);


			vue.getCTabFolderResultat().setSimple(false);
			vue.getCTabFolderResultat().addSelectionListener(new SelectionListener() {

				public void widgetDefaultSelected(SelectionEvent e) {
					widgetSelected(e);
				}

				public void widgetSelected(SelectionEvent e) {
					tableViewerRequete.setSelection(((CTabItemResultatRecherche)vue.getCTabFolderResultat().getSelection()).getController().getSelectionRqt(),true);
					selectionRequete(tableViewerRequete.getTable().getSelection()[0]);

					LinkedList<EditableTableItem> l = new LinkedList<EditableTableItem>();
					EditableTableItem elem = null;
					for (Object editableTableItem : ((WritableList)((CTabItemResultatRecherche)vue.getCTabFolderResultat().getSelection()).getController().getListeInput()).subList(0, ((WritableList)((CTabItemResultatRecherche)vue.getCTabFolderResultat().getSelection()).getController().getListeInput()).size())) {
						elem = new EditableTableItem(((EditableTableItem)editableTableItem).champs,((EditableTableItem)editableTableItem).valeur_Deb,((EditableTableItem)editableTableItem).valeur_Fin);
						l.add(elem);
					}
					
					WritableList wl = new WritableList(l,EditableTableItem.class);
					
					tableViewerSelection.setInput(wl);
				}

			});

		} catch (Exception e) {
			logger.error("Erreur : ControllerSelection", e);
		}
	}




	public String getNomTable() {
		return nomTable;
	}

	public void setNomTable(String nomTable) {
		this.nomTable = nomTable;
	}
	@Override
	protected void actAide() throws Exception {
		// TODO Auto-generated method stub

	}
	@Override
	protected void actAide(String message) throws Exception {
		// TODO Auto-generated method stub

	}
	@Override
	protected void actAnnuler() throws Exception {
		// TODO Auto-generated method stub

	}
	@Override
	protected void actEnregistrer() throws Exception {
		// TODO Auto-generated method stub

	}
	@Override
	protected void actFermer() throws Exception {
		// (controles de sortie et fermeture de la fenetre) => onClose()
		if(onClose())
			closeWorkbenchPart();
	}
	@Override
	protected void actImprimer() throws Exception {
		try {
			//setExtraction(true);
			String select = ((CTabItemResultatRecherche)vue.getCTabFolderResultat().getSelection()).getController().getQueryDeb();
			String param_Procedure = ((CTabItemResultatRecherche)vue.getCTabFolderResultat().getSelection()).getController().getParamProcedure();
			String groupBy = ((CTabItemResultatRecherche)vue.getCTabFolderResultat().getSelection()).getController().getGroupBy();
			//String[] listeParam = ((CTabItemResultatRecherche)vue.getCTabFolderResultat().getSelection()).getController().getParam().split(";");
		String where = ((CTabItemResultatRecherche)vue.getCTabFolderResultat().getSelection()).getController().getQueryWhere();
			String url = "http://" + WebappAccessor.getHost() + ":"+ WebappAccessor.getPort(WebViewerUtil.C_NOM_WEBAPP) + "/birt/";
//			System.setProperty("RUN_UNDER_ECLIPSE", "true");
			if (!isExtraction())
				url += "run?__report=";
			else
				url += "frameset?__report=";
		//	Bundle bundleCourant = LibrairiesEcranPlugin.getDefault().getBundle();
			
			String reportFileLocation = ((CTabItemResultatRecherche)vue.getCTabFolderResultat().getSelection()).getController().getImpression();
		//	URL urlReportFile = Platform.asLocalURL(bundleCourant.getEntry(reportFileLocation));
			
			String idPluginOnglet = ((CTabItemResultatRecherche)vue.getCTabFolderResultat().getSelection()).getController().getIdPlugin();
			URL urlReportFile = FileLocator.findEntries(Platform.getBundle(idPluginOnglet), new Path(reportFileLocation))[0];
			urlReportFile = FileLocator.toFileURL(urlReportFile);
			
			
			Bundle bundleCourant = Platform.getBundle(idPluginOnglet);
			//String reportFileLocation = "/report/facture.rptdesign";

			urlReportFile = FileLocator.toFileURL(bundleCourant.getEntry(reportFileLocation));
			URI uriReportFile = new URI("file", urlReportFile.getAuthority(),
					urlReportFile.getPath(), urlReportFile.getQuery(), null);
			File reportFile = new File(uriReportFile);
			
//			URI uriReportFile = new URI("file", urlReportFile.getAuthority(),
//					urlReportFile.getPath(), urlReportFile.getQuery(), null);
			//File reportFile = new File(urlReportFile.toURI().getPath());

			url += reportFile.getAbsolutePath();

			logger.debug("Report file asLocalURL URL  : " + urlReportFile);
			logger.debug("Report file asLocalURL File : "
					+ reportFile.getAbsolutePath());
			
			if(select == null 
					|| select.equals(""))
				url += "&__isnull=ParamSelect" ;
			else
				url += "&ParamSelect=" + URLEncoder.encode(select, "UTF-8") ;
			
			if(param_Procedure == null 
					|| param_Procedure.equals(""))
				url += "&__isnull=ParamProcedure" ;
			else
				url += "&ParamProcedure=" + param_Procedure ;
			
			if(where == null 
						|| where.equals(""))
					url += "&__isnull=ParamWhere" ;
				else
					url += "&ParamWhere=" + URLEncoder.encode(where, "UTF-8") ;
			
			if(groupBy == null 
					|| groupBy.equals(""))
				url += "&__isnull=ParamGroupBy" ;
			else
				url += "&ParamGroupBy=" + URLEncoder.encode(groupBy, "UTF-8") ;
			
			
			for (int i = 0; i < listeParam.length; i++) {
				if(listeParam[i].contains("=?"))listeParam[i]=listeParam[i].replace("=?", "").replace("&", "&__isnull=");
				url += listeParam[i];
			}
			
			url += "&paramUrlJDBC="
				+ IB_APPLICATION.findDatabase().getConnection()
				.getConnectionURL();
			url += "&paramUser="
				+ IB_APPLICATION.findDatabase().getConnection()
				.getUserName();
			url += "&paramDriverJDBC="
				+ IB_APPLICATION.findDatabase().getConnection()
				.getDriver();

			url += "&paramPassword="
				+ IB_APPLICATION.findDatabase().getConnection()
				.getPassword();//
			url += "&NomEntreprise= ";
			url += "&__document=doc"+new Date().getTime();
			if (!isExtraction())url += "&__format=pdf";
			logger.debug("URL edition: " + url);
			final String finalURL = url;
			PlatformUI.getWorkbench().getDisplay().asyncExec(new Runnable() {
				public void run() {
					try {
						PlatformUI.getWorkbench().getBrowserSupport()
						.createBrowser(
								IWorkbenchBrowserSupport.AS_EDITOR,
								"myId",
								"Prévisualisation",
						"Prévisualisation").openURL(
								new URL(finalURL));
					} catch (PartInitException e) {
						logger.error("", e);
					} catch (MalformedURLException e) {
						logger.error("", e);
					}
				}
			});
		} catch (Exception ex) {
			logger.error("",ex);
		}
		finally{
			setExtraction(false);
		}
	}

	@Override
	protected void actInserer() throws Exception {
		// TODO Auto-generated method stub

	}
	@Override
	protected void actModifier() throws Exception {
		// TODO Auto-generated method stub

	}
	@Override
	protected void actRefresh() throws Exception {


	}
	@Override
	protected void actSupprimer() throws Exception {
		setExtraction(true);
		actImprimer();
	}
	@Override
	protected void initComposantsVue() throws ExceptLgr {
		// TODO Auto-generated method stub

	}
	@Override
	public void initEtatComposant() {
		// TODO Auto-generated method stub

	}
	@Override
	protected void initMapComposantDecoratedField() {
		// TODO Auto-generated method stub

	}


	@Override
	protected void sortieChamps() {
		// TODO Auto-generated method stub

	}


	protected void initEtatBouton() {
		//super.initEtatBouton();
		enableActionAndHandler(C_COMMAND_RECHERCHER_ID,true);
		enableActionAndHandler(C_COMMAND_GLOBAL_SUPPRIMER_ID,true);
		enableActionAndHandler(C_COMMAND_GLOBAL_ANNULER_ID,true);
		enableActionAndHandler(C_COMMAND_GLOBAL_IMPRIMER_ID,true);
		enableActionAndHandler(C_COMMAND_GLOBAL_FERMER_ID,true);
	}	

	@Override
	public ResultAffiche configPanel(ParamAffiche param) {
		if (param != null) {
			if (((ParamAfficheVisualisation) param).getFocusDefautSWT() != null && !((ParamAfficheVisualisation) param).getFocusDefautSWT().isDisposed())
				((ParamAfficheVisualisation) param).getFocusDefautSWT().setFocus();
			else
				((ParamAfficheVisualisation) param).setFocusDefautSWT(vue.getGrilleSelection());
//			vue.getLaTitreHistorique().setText(
//			((ParamAfficheVisualisation) param).getTitreHistorique());
//			vue.getLaTitreSelection().setText(
//			((ParamAfficheVisualisation) param).getTitreSelection());
//			vue.getLaTitreResultat().setText(
//			((ParamAfficheVisualisation) param).getTitreResultat());
//			vue.getLaTitreFenetre().setText(
//			((ParamAfficheVisualisation) param).getTitreFenetre());

			if (param.getEcranAppelant() != null) {
				ecranAppelant = param.getEcranAppelant();
			}
			this.module =  ((ParamAfficheVisualisation) param).getModule();
			this.nomClassController = ((ParamAfficheVisualisation) param).getNomClassController();
			this.nomTable = ((ParamAfficheVisualisation) param).getNomRequete();
			initController();
		}
		return null;
	}


	public void retourEcran(final RetourEcranEvent evt) {
		
	}



	@Override
	public boolean onClose() throws ExceptLgr {
		// TODO Auto-generated method stub
		return true;
	}



	public String getModule() {
		return module;
	}



	public void setModule(String module) {
		this.module = module;
	}



	public LgrTableViewer getTableViewerRequete() {
		return tableViewerRequete;
	}



	public void setTableViewerRequete(LgrTableViewer tableViewerRequete) {
		this.tableViewerRequete = tableViewerRequete;
	}



	public String[] getTitreChamp() {
		return titreChampSelection;
	}



	public void setTitreChamp(String[] titreChamp) {
		this.titreChampSelection = titreChamp;
	}



	public String getImpression() {
		return impression;
	}



	public void setImpression(String impression) {
		this.impression = impression;
	}



	public String getQueryDeb() {
		return queryDeb;
	}



	public void setQueryDeb(String queryDeb) {
		this.queryDeb = queryDeb;
	}

	
	protected void attachCellEditorsRequete(final TableViewer viewer, Composite parent) {
		viewer.setCellModifier(new ICellModifier() {
			public boolean canModify(Object element, String property) {
				return true;
			}

			public Object getValue(Object element, String property) {
				if (CHAMPSREQUETE_PROPERTY.equals(property))
					return ((RequeteEditableTableItem) element).CHAMPS;
				else if (CODE_REQUETE_PROPERTY.equals(property))
					return ((RequeteEditableTableItem) element).CODE_REQUETE;
				else if (ID_EDITEUR_PROPERTY.equals(property))
					return ((RequeteEditableTableItem) element).id_editeur;
				else if (IDENTIFIANT_PROPERTY.equals(property))
					return ((RequeteEditableTableItem) element).identifiant;
				else if (IMPRESSION_PROPERTY.equals(property))
					return ((RequeteEditableTableItem) element).IMPRESSION;
				else if (MODULE_PROPERTY.equals(property))
					return ((RequeteEditableTableItem) element).MODULE;
				else if (REQUETE_PROPERTY.equals(property))
					return ((RequeteEditableTableItem) element).REQUETE;
				else if (TAILLE_CHAMPS_PROPERTY.equals(property))
					return ((RequeteEditableTableItem) element).TAILLE_CHAMPS;
				else if (TITRE_CHAMPS_PROPERTY.equals(property))
					return ((RequeteEditableTableItem) element).TITRE_CHAMPS;
				else if (ID_PLUGIN_PROPERTY.equals(property))
					return ((RequeteEditableTableItem) element).id_plugin;
				else if (ID_GROUPBY_PROPERTY.equals(property))
					return ((RequeteEditableTableItem) element).GROUPBY;
				else if (PROCEDURE_PROPERTY.equals(property))
					return ((RequeteEditableTableItem) element).PROCEDURE;
				else if (PARAM_PROPERTY.equals(property))
					return ((RequeteEditableTableItem) element).PARAM;				
						else return null;
			}

			public void modify(Object element, String property, Object value) {
				TableItem tableItem = (TableItem) element;
				RequeteEditableTableItem data = (RequeteEditableTableItem) tableItem
				.getData();
					if (CHAMPSREQUETE_PROPERTY.equals(property))
						data.CHAMPS = value.toString();
					else if (CODE_REQUETE_PROPERTY.equals(property))
						data.CODE_REQUETE = value.toString();
					else if (ID_EDITEUR_PROPERTY.equals(property))
						data.id_editeur = value.toString();
					else if (IDENTIFIANT_PROPERTY.equals(property))
						data.identifiant = value.toString();
					else if (IMPRESSION_PROPERTY.equals(property))
						data.IMPRESSION = value.toString();
					else if (MODULE_PROPERTY.equals(property))
						data.MODULE = value.toString();
					else if (REQUETE_PROPERTY.equals(property))
						data.REQUETE = value.toString();
					else if (TAILLE_CHAMPS_PROPERTY.equals(property))
						data.TAILLE_CHAMPS = value.toString();
					else if (TITRE_CHAMPS_PROPERTY.equals(property))
						data.TITRE_CHAMPS = value.toString();
					else if (ID_PLUGIN_PROPERTY.equals(property))
						data.id_plugin = value.toString();
					else if (ID_GROUPBY_PROPERTY.equals(property))
						data.GROUPBY = value.toString();
					else if (PROCEDURE_PROPERTY.equals(property))
						data.PROCEDURE = value.toString();
					else if (PARAM_PROPERTY.equals(property))
						data.PARAM = value.toString();
					
				viewer.refresh(data);
			}
		});

		viewer.setCellEditors(new CellEditor[] {
				null,
				new TextCellEditor(parent), 
				new TextCellEditor(parent),
				new TextCellEditor(parent),
				new TextCellEditor(parent),
				null,
				new TextCellEditor(parent),
				new TextCellEditor(parent),
				null,
				null,
				new TextCellEditor(parent),
				new TextCellEditor(parent),
				new TextCellEditor(parent)		
				});

		viewer
		.setColumnProperties(new String[] {
				CODE_REQUETE_PROPERTY ,
				REQUETE_PROPERTY,
				CHAMPSREQUETE_PROPERTY,
				TITRE_CHAMPS_PROPERTY,
				TAILLE_CHAMPS_PROPERTY,
				MODULE_PROPERTY,
				IMPRESSION_PROPERTY,
				IDENTIFIANT_PROPERTY,
				ID_EDITEUR_PROPERTY,	
				ID_PLUGIN_PROPERTY,
				ID_GROUPBY_PROPERTY,
				PROCEDURE_PROPERTY,
				PARAM_PROPERTY});
	}


	protected void attachContentProviderRequete(TableViewer viewer) {
		viewer.setContentProvider(new IStructuredContentProvider() {
			public Object[] getElements(Object inputElement) {
				return ((List<RequeteEditableTableItem>)inputElement).toArray();
				// return (Object[]) inputElement;
			}

			public void dispose() {
			}

			public void inputChanged(Viewer viewer, Object oldInput,
					Object newInput) {
			}
		});
	}

	protected void attachLabelProviderRequete(TableViewer viewer) {
		viewer.setLabelProvider(new ITableLabelProvider() {
			public Image getColumnImage(Object element, int columnIndex) {
				return null;
			}

			public String getColumnText(Object element, int columnIndex) {
				switch (columnIndex) {
				case 0:
					return ((RequeteEditableTableItem) element).CODE_REQUETE;		          
				case 1:
					return ((RequeteEditableTableItem) element).REQUETE;
				case 2:
					return ((RequeteEditableTableItem) element).CHAMPS;
				case 3:
					return ((RequeteEditableTableItem) element).TITRE_CHAMPS;
				case 4:
					return ((RequeteEditableTableItem) element).TAILLE_CHAMPS;
				case 5:
					return ((RequeteEditableTableItem) element).MODULE;
				case 6:
					return ((RequeteEditableTableItem) element).IMPRESSION;
				case 7:
					return ((RequeteEditableTableItem) element).identifiant;
				case 8:
					return ((RequeteEditableTableItem) element).id_editeur;
				case 9:
					return ((RequeteEditableTableItem) element).id_plugin;
				case 10:
					return ((RequeteEditableTableItem) element).GROUPBY;
				case 11:
					return ((RequeteEditableTableItem) element).PROCEDURE;
				case 12:
					return ((RequeteEditableTableItem) element).PARAM;
				default:
					return "Invalid column: " + columnIndex;
				}
			}

			public void addListener(ILabelProviderListener listener) {
			}

			public void dispose() {
			}

			public boolean isLabelProperty(Object element, String property) {
				return false;
			}

			public void removeListener(ILabelProviderListener lpl) {
			}
		});
	}
	
	public String findChampColonne(String titre){
		boolean trouve=false;
		int i=-1;
		while (!trouve && i<titreChampSelection.length){
			i++;
			trouve=titreChampSelection[i].equals(titre);
		}
		if(trouve)return listeChampSelection[i];
		else
			return null;
	}
	
	public String findTitreColonne(String champ){
		boolean trouve=false;
		int i=-1;
		while (!trouve && i<listeChampSelection.length){
			i++;
			trouve=listeChampSelection[i].equals(champ);
		}
		if(trouve)return titreChampSelection[i];
		else
			return null;
	}
	
	public String retournePredicat(String valeur){
		if(valeur.contains("%")){
			//si contient % au début (et/ou) à l'interieur de la valeur
			if((valeur.startsWith("%") && valeur.lastIndexOf("%")< valeur.length())
					||(valeur.lastIndexOf("%")< valeur.length()))
				return PREDICAT_LIKE;
			else 
				//si contient % que au début (dans ce cas, enlever le %)
				if(valeur.startsWith("%")) return PREDICAT_CONTAINING;				
		}
		else
			return PREDICAT_START;
		
		return valeur;
		
	}



	public boolean isExtraction() {
		return extraction;
	}



	public void setExtraction(boolean extraction) {
		this.extraction = extraction;
	}



	public String getRequete() {
		return requete;
	}



	public void setRequete(String requete) {
		this.requete = requete;
	}



	public String getGroubBy() {
		return groubBy;
	}



	public void setGroubBy(String groubBy) {
		this.groubBy = groubBy;
	}

	
	
	public boolean isChampDbool(String champ){
		if(listeDBOOL!=null){
			for (int i = 0; i < listeDBOOL.size(); i++) {
				if(listeDBOOL.get(i).equalsIgnoreCase(champ))
					return true;
			}
		}
		return false;
		
	}



	public String getProcedure() {
		return procedure;
	}



	public void setProcedure(String procedure) {
		this.procedure = procedure;
	}




	public String getParam() {
		return param;
	}



	public void setParam(String param) {
		this.param = param;
	}
}


