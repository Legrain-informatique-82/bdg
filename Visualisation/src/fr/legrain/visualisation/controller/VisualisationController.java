package fr.legrain.visualisation.controller;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import org.apache.log4j.Logger;
import org.eclipse.birt.report.viewer.ViewerPlugin;
import org.eclipse.birt.report.viewer.utilities.WebViewer;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.core.commands.NotEnabledException;
import org.eclipse.core.commands.NotHandledException;
import org.eclipse.core.commands.common.NotDefinedException;
import org.eclipse.core.databinding.observable.list.WritableList;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.AbstractTableViewer;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.CellNavigationStrategy;
import org.eclipse.jface.viewers.ColumnViewer;
import org.eclipse.jface.viewers.ColumnViewerEditor;
import org.eclipse.jface.viewers.ColumnViewerEditorActivationEvent;
import org.eclipse.jface.viewers.ColumnViewerEditorActivationListener;
import org.eclipse.jface.viewers.ColumnViewerEditorActivationStrategy;
import org.eclipse.jface.viewers.ColumnViewerEditorDeactivationEvent;
import org.eclipse.jface.viewers.ComboBoxCellEditor;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.FocusCellOwnerDrawHighlighter;
import org.eclipse.jface.viewers.ICellModifier;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerEditor;
import org.eclipse.jface.viewers.TableViewerFocusCellManager;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerCell;
import org.eclipse.jface.viewers.ViewerColumn;
import org.eclipse.jface.viewers.ViewerRow;
import org.eclipse.jface.viewers.CellEditor.LayoutData;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.TableEditor;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.events.TraverseEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Item;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.browser.IWorkbenchBrowserSupport;

import fr.legrain.dossier.dao.TaInfoEntreprise;
import fr.legrain.dossier.dao.TaInfoEntrepriseDAO;
import fr.legrain.edition.ImprimeObjet;
import fr.legrain.edition.actions.AttributElementResport;
import fr.legrain.edition.actions.ConstEdition;
import fr.legrain.edition.actions.MakeDynamiqueReport;
import fr.legrain.edition.dynamique.EditionAppContext;
import fr.legrain.edition.dynamique.FonctionGetInfosXmlAndProperties;
import fr.legrain.gestCom.Appli.Const;
import fr.legrain.gestCom.librairiesEcran.LibrairiesEcranPlugin;
import fr.legrain.gestCom.librairiesEcran.swt.BaseControllerSelection;
import fr.legrain.gestCom.librairiesEcran.swt.LgrAbstractHandler;
import fr.legrain.gestCom.librairiesEcran.swt.ParamAfficheVisualisation;
import fr.legrain.gestCom.librairiesEcran.workbench.LgrPartListener;
import fr.legrain.lib.data.ExceptLgr;
import fr.legrain.lib.data.LgrConstantes;
import fr.legrain.lib.data.LibChaine;
import fr.legrain.lib.data.LibChaineSQL;
import fr.legrain.lib.data.LibConversion;
import fr.legrain.lib.data.LibDate;
import fr.legrain.lib.gui.PaResultatVisu;
import fr.legrain.lib.gui.PaSelectionVisualisation;
import fr.legrain.lib.gui.ParamAffiche;
import fr.legrain.lib.gui.ResultAffiche;
import fr.legrain.lib.gui.RetourEcranEvent;
import fr.legrain.lib.gui.RetourEcranListener;
import fr.legrain.lib.gui.grille.LgrTableViewer;
import fr.legrain.libLgrBirt.WebViewerUtil;
import fr.legrain.visualisation.controller.OngletResultatControllerJPA.InfosPresentation;
import fr.legrain.visualisation.dao.TaVisualisation;
import fr.legrain.visualisation.dao.TaVisualisationDAO;

public class VisualisationController extends BaseControllerSelection  
implements  RetourEcranListener {
	private static final String PREDICAT_CONTAINING = " CONTAINING ";
	private static final String PREDICAT_START = " STARTING WITH ";
	private static final String PREDICAT_LIKE = " like ";
	private static final String PREDICAT_BETWEEN = " between ";
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
	private static final String WHERE_PROPERTY = "where";
	private static final String ID_GROUPBY_PROPERTY = "GROUPBY";
	private static final String HAVING_PROPERTY = "having";
	private static final String PROCEDURE_PROPERTY = "PROCEDURE";
	private static final String PARAM_PROPERTY = "PARAM";
	private static final String TYPES_RETOUR_PROPERTY = "TYPES_RETOUR";
	private static final String TOTAUX_PROPERTY = "TOTAUX";
	private static final String QUERY_LANG_PROPERTY = "QUERY_LANG";
	private static final String CLASSE_RECHERCHE_PROPERTY = "CLASSE_RECHERCHE";

	private boolean extraction = false;

	private ArrayList<String> listeDBOOL;

	public static final String C_COMMAND_RECHERCHER_ID 	= "fr.legrain.gestionCommerciale.rechercher";
	public static final String C_COMMAND_REINITIALISER_ID 	= "fr.legrain.gestionCommerciale.reinitialiser";
	public static final String C_COMMAND_REINITIALISER_GLOBAL_ID 	= "fr.legrain.gestionCommerciale.reinitialiser.global";
	public static final String C_COMMAND_EXTRACTION_ID 	= "fr.legrain.gestionCommerciale.extraction";

	private PaSelectionVisualisation vue = null;
	private LgrTableViewer tableViewerSelection;
	private LgrTableViewer tableViewerTotaux;
	private LgrTableViewer tableViewerRequete;
	private String queryWhere = null;
	private String queryHaving = null;
	private String queryOrderBy = null;
	private String queryDeb = null;
	private String param_procedure = null;
	private String nomClassController = null;
	private String module = null;
	private String nomTable = null;

	private String valeur_Deb="";
	private String valeur_Fin="";
	private String col="";
	private String colSurcheable="";
	private String colParticularite="";
	private Object ecranAppelant = null;
	private int numRecherche=0;

	protected ActionRechercher actionRechercher = new ActionRechercher(LgrConstantes.C_LIB_BTNRECHERCHER);
	protected HandlerRechercher handlerRechercher = new HandlerRechercher();
	protected ActionReinitialiser actionReinitialiser = new ActionReinitialiser(LgrConstantes.C_LIB_BTNRECHERCHER);
	protected HandlerReinitialiser handlerReinitialiser = new HandlerReinitialiser();
	protected HandlerReinitialiserGlobal handlerReinitialiserGlobal = new HandlerReinitialiserGlobal();
	protected HandlerExtraction handlerExtraction = new HandlerExtraction();


	protected String[] listeChampSelection; 
	protected String[] tailleChampSelection;
	protected String[] titreChampSelection;
	protected String impression;
	protected String identifiant;
	protected String idEditor;
	protected String idPlugin;
	protected String requete;
	protected String codeRequete;
	protected String where;
	protected String groubBy;
	protected String having;
	protected String procedure;
	protected String param;
	protected String[] totaux;
	protected String[] typesRetour;
	protected String[] listeParam;
	protected String queryLang;
	protected String classeRecherche;

	public ComboBoxCellEditor combo = null;
	static Logger logger = Logger.getLogger(VisualisationController.class.getName());
	
	public VisualisationController(PaSelectionVisualisation vue) {
		this(vue,null);
	}

	public VisualisationController(PaSelectionVisualisation vue,EntityManager em) {
		if(em!=null) {
			setEm(em);
		}
		setVue(vue);
		//this.vue = vue;
		//initController();
	}

	public VisualisationController() {
		super();
	}

	public VisualisationController(PaSelectionVisualisation vue, String nomTable,String nomController) {
		setVue(vue);
		//this.vue = vue;
		this.nomClassController = nomController;
		this.nomTable = nomTable;

	}

	protected void initImageBouton() {
		super.initImageBouton();

		if(vue instanceof PaSelectionVisualisation) {
			((PaSelectionVisualisation)vue).getPaBtnRecherche().getBtnImprimer().setImage(LibrairiesEcranPlugin.ir.get(LibrairiesEcranPlugin.IMAGE_RECHERCHER));
			((PaSelectionVisualisation)vue).getPaBtnRecherche().getBtnFermer().setImage(LibrairiesEcranPlugin.ir.get(LibrairiesEcranPlugin.IMAGE_REINITIALISER));
			((PaSelectionVisualisation)vue).getPaBtnImprimer().getBtnAnnuler().setImage(LibrairiesEcranPlugin.ir.get(LibrairiesEcranPlugin.IMAGE_REINITIALISER));
			((PaSelectionVisualisation)vue).getPaBtnImprimer().getBtnFermer().setImage(LibrairiesEcranPlugin.ir.get(LibrairiesEcranPlugin.IMAGE_FERMER));
			((PaSelectionVisualisation)vue).getPaBtnImprimer().getBtnImprimer().setImage(LibrairiesEcranPlugin.ir.get(LibrairiesEcranPlugin.IMAGE_IMPRIMER));
			((PaSelectionVisualisation)vue).getPaBtnImprimer().getBtnSupprimer().setImage(LibrairiesEcranPlugin.ir.get(LibrairiesEcranPlugin.IMAGE_IMPRIMER));
			vue.layout(true);
		}
	}

	protected void attachCellEditorsSelection(final TableViewer viewer, Composite parent) {
		viewer.setCellModifier(new ICellModifier() {
			public boolean canModify(Object element, String property) {
//				if(procedure.equals("1") && ((CHAMPS_PROPERTY.equals(property)||(VALUE_FIN_PROPERTY.equals(property)))))
//					return false;
//				else	
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
					if(index.intValue()>-1)
						return titreChampSelection[index.intValue()];
					return null;
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

	protected class HandlerExtraction extends LgrAbstractHandler {
		public Object execute(ExecutionEvent event) throws ExecutionException{
			try {
				actExtraction();
			} catch (Exception e) {
				logger.error("",e);
			}
			return event;
		}
	}


	protected void actExtraction() throws Exception {
//		setExtraction(true);
//		actImprimer();
//		setExtraction(false);
		exportCSV();
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
		public String where;
		public String GROUPBY;
		public String having;
		public String PROCEDURE;
		public String PARAM;
		public String TOTAUX;
		public String TYPES_RETOUR;
		public String QUERY_LANG;
		public String CLASSE_RECHERCHE;

		public RequeteEditableTableItem(String code_requete, String requete,
				String champs, String titre_champs, String taille_champs,
				String module, String impression, String identifiant,
				String id_editeur, String id_plugin, String where,
				String groupby, String having, String procedure, String param,
				String totaux, String typesRetour,String queryLang,String classeRecherche) {
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
			this.id_plugin = id_plugin;
			this.where = where;
			GROUPBY = groupby;
			this.having = having;
			PROCEDURE = procedure;
			PARAM = param;
			this.TOTAUX = totaux;
			this.TYPES_RETOUR = typesRetour;
			this.QUERY_LANG =  queryLang;
			this.CLASSE_RECHERCHE = classeRecherche;
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

		public String getCODE_REQUETE() {
			return CODE_REQUETE;
		}

		public String getREQUETE() {
			return REQUETE;
		}

		public String getCHAMPS() {
			return CHAMPS;
		}

		public String getTITRE_CHAMPS() {
			return TITRE_CHAMPS;
		}

		public String getTAILLE_CHAMPS() {
			return TAILLE_CHAMPS;
		}

		public String getMODULE() {
			return MODULE;
		}

		public String getIMPRESSION() {
			return IMPRESSION;
		}

		public String getIdentifiant() {
			return identifiant;
		}

		public String getId_editeur() {
			return id_editeur;
		}

		public String getWhere() {
			return where;
		}

		public String getHaving() {
			return having;
		}

		public String getTOTAUX() {
			return TOTAUX;
		}

		public String getTYPES_RETOUR() {
			return TYPES_RETOUR;
		}

		public void setWhere(String where) {
			this.where = where;
		}

		public void setHaving(String having) {
			this.having = having;
		}

		public void setTOTAUX(String totaux) {
			this.TOTAUX = totaux;
		}

		public void setTYPES_RETOUR(String booleen) {
			this.TYPES_RETOUR = booleen;
		}

		public String getQUERY_LANG() {
			return QUERY_LANG;
		}

		public void setQUERY_LANG(String query_lang) {
			QUERY_LANG = query_lang;
		}

		public String getCLASSE_RECHERCHE() {
			return CLASSE_RECHERCHE;
		}

		public void setCLASSE_RECHERCHE(String classe_recherche) {
			CLASSE_RECHERCHE = classe_recherche;
		}

	}

	public void bindRequete() {
		try {			  
			TaVisualisationDAO dao = new TaVisualisationDAO(getEm());
			List<TaVisualisation> listeVisualisation = dao.findByCodeModule(module);

			tableViewerRequete = new LgrTableViewer(vue.getTableRequete());

			tableViewerRequete.createTableCol( vue.getTableRequete(),tableViewerRequete.setListeTitreChampGrille("SelectionRequetes",Const.C_FICHIER_LISTE_CHAMP_GRILLE),
					tableViewerRequete.setListeTailleChampGrille("SelectionRequetes",Const.C_FICHIER_LISTE_CHAMP_GRILLE));

			attachContentProviderRequete(tableViewerRequete);
			attachLabelProviderRequete(tableViewerRequete);
			attachCellEditorsRequete(tableViewerRequete, vue.getTableRequete());
			for (int i = 0; i < tableViewerRequete.getTable().getColumns().length; i++) {
				tableViewerRequete.getTable().getColumns()[i].setResizable(
						tableViewerRequete.getTable().getColumn(i).getWidth()>0);
			}

			LinkedList<RequeteEditableTableItem> listeInput = new LinkedList<RequeteEditableTableItem>();
			RequeteEditableTableItem item = null;

			for (TaVisualisation visu : listeVisualisation) {
				item = new RequeteEditableTableItem();

				//item = new TableItem(vue.getTableRequete(),j);
				item.setCODE_REQUETE(LibChaineSQL.EnleveRetourChariot(visu.getCodeRequete()));
				item.setId_editeur(LibChaineSQL.EnleveRetourChariot(visu.getIdEditeur()));
				item.setIdentifiant(LibChaineSQL.EnleveRetourChariot(visu.getIdentifiant()));
				item.setMODULE(LibChaineSQL.EnleveRetourChariot(visu.getModule()));
				item.setIMPRESSION(LibChaineSQL.EnleveRetourChariot(visu.getImpression()));
				//pour celui la , je remplace les retours a  la ligne par des espaces pour ne pas fausser la requete
				item.setREQUETE(visu.getRequete().replaceAll(System.getProperty("line.separator"), " "));
				item.setCHAMPS(LibChaineSQL.EnleveRetourChariot(visu.getChamps()));
				item.setTAILLE_CHAMPS(LibChaineSQL.EnleveRetourChariot(visu.getTailleChamps()));
				item.setTITRE_CHAMPS(LibChaineSQL.EnleveRetourChariot(visu.getTitreChamps()));
				item.setId_plugin(LibChaineSQL.EnleveRetourChariot(visu.getIdPlugin()));

				item.setWhere(LibChaineSQL.EnleveRetourChariot(visu.getClauseWhere()));

				item.setGROUPBY(LibChaineSQL.EnleveRetourChariot(visu.getGroupby()));

				item.setHaving(LibChaineSQL.EnleveRetourChariot(visu.getClauseHaving()));

				item.setPROCEDURE(LibChaineSQL.EnleveRetourChariot(LibConversion.integerToString(visu.getProc())));
				item.setPARAM(LibChaineSQL.EnleveRetourChariot(visu.getParam()));

				item.setTYPES_RETOUR(LibChaineSQL.EnleveRetourChariot(visu.getTypesRetour()));
				item.setTOTAUX(LibChaineSQL.EnleveRetourChariot(visu.getTotaux()));
				item.setQUERY_LANG(LibChaineSQL.EnleveRetourChariot(visu.getQueryLang()));
				item.setCLASSE_RECHERCHE(LibChaineSQL.EnleveRetourChariot(visu.getClasseRecherche()));

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
			codeRequete = selection.getText(0);
			requete = selection.getText(1);
			listeChampSelection = selection.getText(2).trim().split(";");
			titreChampSelection = selection.getText(3).trim().split(";");
			tailleChampSelection = selection.getText(4).split(";");
			impression = selection.getText(6);
			identifiant = selection.getText(7);
			idEditor = selection.getText(8);
			idPlugin = selection.getText(9);
			where = selection.getText(10);
			groubBy = selection.getText(11);
			having = selection.getText(12);
			procedure = selection.getText(13);
			param = selection.getText(14);
			typesRetour = selection.getText(15).split(";");
			totaux = selection.getText(16).split(";");
			queryLang = selection.getText(17);
			classeRecherche = selection.getText(18);
		}
	}

	public void initGrilleSelection(){
		//liberation si deja  rempli
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
		tableViewerSelection.navigationStrategy();	 
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


	private String ajoutParamJPA(String query,String typeColumn,String nameColumn) {
		String predicat = "=";



		if(typeColumn.equals(ConstVisualisation.typeString)) {
			valeur_Deb=valeur_Deb.replace("*", "%").replace("'", "''").trim();
			valeur_Fin=valeur_Fin.replace("*", "%").replace("'", "''").trim();
			String value=valeur_Deb;
			//si les 2 valeurs sont remplies
			if (!valeur_Deb.equals("")&& !valeur_Fin.equals("")) {
				//si valeurs egales et != de %
				if (valeur_Deb.compareToIgnoreCase(valeur_Fin) == 0 && !valeur_Deb.equals("%") ||
						// ou 
						//si valeur_Deb Inferieure a valeur_Fin 
						(valeur_Deb.compareToIgnoreCase(valeur_Fin) < 0)) {						
					query += " ("+nameColumn+") "+PREDICAT_BETWEEN+" '"
					+ value.toUpperCase()
					+"' AND '"	
					+ valeur_Fin.toUpperCase()
					+ "['";
				}
				else{
					//si valeur_Deb superieur a valeur_Fin, on ignore valeur_Fin
					predicat=retournePredicat(valeur_Deb);
					if(!value.contains("%")){
						value+="%";
					}
					//					if(predicat.equals(PREDICAT_CONTAINING)){
					//						value=valeur_Deb.replace("%", "");
					//					}
					query += " (("+nameColumn+") "+predicat+" '"
					+ value.toUpperCase()
					+ "' or "
					+ "("+nameColumn+") "+PREDICAT_SUP_EGALE+" '"
					+ valeur_Deb.toUpperCase()
					+ "')";	
				}
			}
			//si que valeur_Fin remplie
			if (valeur_Deb.equals("")&& !valeur_Fin.equals("")) {
				predicat=PREDICAT_INF_EGALE;
				query += " ("+nameColumn+") "+predicat+" '"
				+ valeur_Fin.toUpperCase() + "['";
			}
			//si que valeur_Deb remplie
			if (valeur_Fin.equals("") && !valeur_Deb.equals("")) {
				predicat=retournePredicat(valeur_Deb);
				if(!value.contains("%")){
					value+="%";
				}
				//				if(predicat.equals(PREDICAT_CONTAINING)){
				//					value=valeur_Deb.replace("%", "");
				//				}
				query += " (("+nameColumn+") "+predicat+" '"
				+ value.toUpperCase()
				+ "' or "
				+ "("+nameColumn+") "+PREDICAT_SUP_EGALE+" '"
				+ valeur_Deb.toUpperCase()
				+ "')";
			}
		} else if(typeColumn.equals(ConstVisualisation.typeInteger) 
				|| typeColumn.equals(ConstVisualisation.typeFloat)
				||typeColumn.equals(ConstVisualisation.typeBooleen)) {
			if(typeColumn.equals(ConstVisualisation.typeBooleen)){
			valeur_Deb=valeur_Deb.toUpperCase().replace("FALSE", "0").replace("TRUE", "1").replace("FAUX", "0").replace("VRAI", "1").replace("F", "0").replace("V", "1");
			valeur_Fin=valeur_Fin.toUpperCase().replace("FALSE", "0").replace("TRUE", "1").replace("FAUX", "0").replace("VRAI", "1").replace("F", "0").replace("V", "1");;
			}
			if (!valeur_Deb.equals("")&& !valeur_Fin.equals("")) {
				if (valeur_Deb.compareToIgnoreCase(valeur_Fin) <= 0) {
					predicat=PREDICAT_BETWEEN;
					query += " ("+nameColumn+") "+predicat+" "
					+ valeur_Deb + "  AND "
					+ valeur_Fin + "";
				}
			}
			if (valeur_Deb.equals("")&& !valeur_Fin.equals("")) {
				if(isChampDbool(nameColumn))predicat=PREDICAT_EGALE;
				else
					predicat=PREDICAT_INF_EGALE;
				query += " ("+nameColumn+") "+predicat+" "
				+ valeur_Fin + "";
			}
			if (valeur_Fin.equals("")&& !valeur_Deb.equals("")) {
				if(isChampDbool(nameColumn))predicat=PREDICAT_EGALE;
				else
					predicat=PREDICAT_SUP_EGALE;
				query += " ("+nameColumn+")  "+predicat+" "
				+ valeur_Deb + " ";
			}			
		} else if(typeColumn.equals(ConstVisualisation.typeDate)) {
			try {
				if(!valeur_Deb.equals(""))valeur_Deb=LibDate.dateToString(LibDate.stringToDate(valeur_Deb));
				if(!valeur_Fin.equals(""))valeur_Fin=LibDate.dateToString(LibDate.stringToDate(valeur_Fin));
				switch (LibDate.compareTo(valeur_Deb,valeur_Fin)) {
				case 1://dateDeb superieure a dateFin, pas correct
					if (!valeur_Deb.equals("") && !valeur_Fin.equals("")) {
						predicat=PREDICAT_LIKE;
						query += " ("+nameColumn+")  "+predicat+" '%' ";
					}
					break;
				case 0:
				case -1:
					if (!valeur_Deb.equals("") && !valeur_Fin.equals("")) {
						predicat=PREDICAT_BETWEEN;
						query += " ("+nameColumn+") "+predicat + "'"
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
					query += " ("+nameColumn+") "+predicat+" '%' ";
				}

				if (valeur_Fin.equals("") && !valeur_Deb.equals("")) {
					predicat=PREDICAT_SUP_EGALE;
					query += " ("+nameColumn+") "+predicat+" '"
					+ valeur_Deb.replace("/", ".") + "'";
				}
				if (valeur_Deb.equals("") && !valeur_Fin.equals("")) {
					predicat=PREDICAT_INF_EGALE;
					query += " ("+nameColumn+") "+predicat+" '"
					+ valeur_Fin.replace("/", ".") + "' ";
				}
			} catch (ParseException e) {
				MessageDialog.openWarning(vue.getShell(), "Valeur incorrect", "Cette date n'est pas correcte");
				logger.error("",e);
			}
//		} else if(typeColumn.equals(ConstVisualisation.typeBooleen)) {
//			valeur_Deb=valeur_Deb.toUpperCase().replace("FALSE", "0").replace("TRUE", "1").replace("FAUX", "0").replace("VRAI", "1");
//			valeur_Fin=valeur_Fin.toUpperCase().replace("FALSE", "0").replace("TRUE", "1").replace("FAUX", "0").replace("VRAI", "1");
		}
		if (query.trim().compareToIgnoreCase("where")==0)
			return "";
		else
			return query;
	}

	protected void actReinitialiser()throws Exception {
		bindSelection();
		initGrilleSelection();
	}

	protected void actReinitialiserGlobal()throws Exception {
		while (vue.getCTabFolderResultat().getItems().length>0) {
			((CTabItemResultatRechercheJPA)vue.getCTabFolderResultat().getItems()[vue.getCTabFolderResultat().getItems().length-1]).getController().dispose();
			vue.getCTabFolderResultat().getItems()[vue.getCTabFolderResultat().getItems().length-1].dispose();
		} 
		actReinitialiser();
		tableViewerRequete.selectionGrille(0);
	}

	protected void rechercheJPA() {
		try {
			selectionRequete(tableViewerRequete.getTable().getSelection()[0]);
			//Query ejbQuery = JPABdLgr.getEntityManager().createQuery(requete);
			//List<Object> l = ejbQuery.getResultList();
			//ModelGeneralObjetVisualisation<Object> model = new ModelGeneralObjetVisualisation<Object>(l,Resultat.class);
			//model.remplirListe();

			queryDeb =requete;
			initRequeteJPA();

			LinkedList<EditableTableItem> l = new LinkedList<EditableTableItem>();
			EditableTableItem e = null;
			for (Object editableTableItem : ((WritableList)tableViewerSelection.getInput()).subList(0, ((WritableList)tableViewerSelection.getInput()).size())) {
				e = new EditableTableItem(((EditableTableItem)editableTableItem).champs,((EditableTableItem)editableTableItem).valeur_Deb,((EditableTableItem)editableTableItem).valeur_Fin);
				l.add(e);
			}

			WritableList wl = new WritableList(l,EditableTableItem.class);

			addOnglet(listeChampSelection,tailleChampSelection,titreChampSelection,queryDeb,param_procedure,queryWhere,queryHaving,groubBy,queryOrderBy, tableViewerRequete.getSelection(),wl,listeParam,codeRequete);
		} catch (Exception re) {
			logger.error("",re);
		}
	}

	public void initRequeteJPA() throws Exception {
		//Construire la requete
		//		queryWhere="";
		queryWhere=where;
//		param_procedure="(";

		queryDeb =requete;
		//		queryHaving="";
		queryHaving=having;
		queryOrderBy="";

		if(queryDeb.toLowerCase().contains("order by")){
			queryOrderBy=queryDeb.substring(queryDeb.toLowerCase().lastIndexOf("order by"),queryDeb.length());
			queryDeb=queryDeb.replaceFirst(queryOrderBy, "");
		}
		//ResultSetMetaData rsMetadata = IB_APPLICATION.findDatabase().createPreparedStatement(queryDeb+" "+groubBy).getMetaData();
		listeDBOOL=new ArrayList<String>();
		//parcourir le metadata pour regarder s'il y a des champs DBOOL
		//		for (int i = 1; i <= rsMetadata.getColumnCount(); i++) {
		//			if(rsMetadata.getColumnType(i)==Types.SMALLINT){				
		//				ResultSet rsTypes = IB_APPLICATION.findDatabase().
		//				createStatement().executeQuery("select distinct(RDB$FIELD_SOURCE) from " +
		//						"RDB$RELATION_FIELDS	where  RDB$FIELD_NAME = '"+rsMetadata.getColumnName(i)+"' " +
		//				"and RDB$FIELD_SOURCE = 'DBOOL'");
		//				if(rsTypes.next()){
		//					listeDBOOL.add(rsMetadata.getColumnName(i));	
		//				}
		//			}
		//		}
		listeParam = param.split(";");

		//ResultSet rs = IB_APPLICATION.findDatabase().createStatement().executeQuery(queryDeb+" "+groubBy);
		String[] cols=null;
		//if (rs.next()) {
		//upper(a.code_article) between 'A%' and 'Z%'
		for (int i = 0; i < tableViewerSelection.getTable().getItemCount(); i++) {
			valeur_Deb ="";
			valeur_Fin ="";
			col="";
			colSurcheable="";
			colParticularite="";
			if(((EditableTableItem)tableViewerSelection.getTable().getItem(i).getData()).valeur_Deb!=null &&
					!((EditableTableItem)tableViewerSelection.getTable().getItem(i).getData()).valeur_Deb.equals(""))
				valeur_Deb =((EditableTableItem)tableViewerSelection.getTable().getItem(i).getData()).valeur_Deb; 
			if(((EditableTableItem)tableViewerSelection.getTable().getItem(i).getData()).valeur_Fin!=null &&
					!((EditableTableItem)tableViewerSelection.getTable().getItem(i).getData()).valeur_Fin.equals(""))
				valeur_Fin =((EditableTableItem)tableViewerSelection.getTable().getItem(i).getData()).valeur_Fin; 
			if(((EditableTableItem)tableViewerSelection.getTable().getItem(i).getData()).champs!=null &&
					!((EditableTableItem)tableViewerSelection.getTable().getItem(i).getData()).champs.equals("")){
				//transformer le titre en vrai champs de la table
				cols=(getListeChamp()[((EditableTableItem)tableViewerSelection.getTable().getItem(i).getData()).champs]).split("!");
				//					col =cols[0].trim();
				//					if(cols.length>1)
				//						colSurcheable=cols[1];

				//					if(cols.length>2)
				//						colParticularite=cols[2];
			}
			//########## WHERE
			if(!valeur_Deb.equals("")||!valeur_Fin.equals("")) {
				if(!(cols.length>1)) {
					if(! queryWhere.contains("where")&& !queryDeb.contains("where") ){
						queryWhere+=" where ";
					}
					else{
						queryWhere+=" and ";
					}

					queryWhere = ajoutParamJPA(queryWhere,typesRetour[i],getListeChamp()[i]);
				}
			}
			//########## GROUP BY

			//########## HAVING
			if(!valeur_Deb.equals("")||!valeur_Fin.equals("")) {
				if(cols.length>1) {
					if(! queryHaving.contains("having")&& !queryDeb.contains("having") ){
						queryHaving+=" having ";
					}
					else{
						queryHaving+=" and ";
					}

					colSurcheable=cols[0];
					queryHaving = ajoutParamJPA(queryHaving,typesRetour[i],colSurcheable);
				}
			}
			//########## ORDER BY


			//				if((!valeur_Deb.equals("")||!valeur_Fin.equals(""))
			//						&&(col!=null&&!col.equals(""))){
			//					
			//					if(colParticularite.trim().equalsIgnoreCase("having")){
			//						if(! queryHaving.contains("having")){
			//							queryHaving+=" having ";
			//						}
			//						else{
			//							queryHaving+=" and ";
			//						}
			//						//suivant type de champ
			//						int a = 1;
			//						int pos = -1;
			////						while(pos==-1 && a<=rs.getMetaData().getColumnCount()) {
			////							if(rs.getMetaData().getColumnName(a).equalsIgnoreCase(col)){
			////								pos = a;
			////							} else {
			////								a++;
			////							}
			////						}
			////						if(pos!=-1){
			////							if(colSurcheable.equals(""))
			//								queryHaving=initRequete(queryHaving,rs.getMetaData().getColumnType(pos),rs.getMetaData().getColumnName(pos));
			////							else
			////								queryHaving=initRequete(queryHaving,rs.getMetaData().getColumnType(pos),colSurcheable);
			////						}
			//						if(!param_procedure.equals("")&& !param_procedure.equals("("))
			//							param_procedure+=",";
			////						param_procedure+=initParametres(rs.getMetaData().getColumnType(pos),rs.getMetaData().getColumnName(pos));
			////						for (int j = 0; j < listeParam.length; j++) {
			////							if(listeParam[j].toUpperCase().startsWith("&PARAM"+col.toUpperCase())){
			////								String valeur=listeParam[j].substring(0,listeParam[j].lastIndexOf("=") );						  
			////								listeParam[j]=valeur+"="+initParametres(rs.getMetaData().getColumnType(pos),rs.getMetaData().getColumnName(pos),false);	
			////							}
			////						}						
			//					}else{
			//						if(! queryWhere.contains("where")&& !queryDeb.contains("where") ){
			//							queryWhere+=" where ";
			//						}
			//						else{
			//							queryWhere+=" and ";
			//						}
			//						//suivant type de champ
			//						int a = 1;
			//						int pos = -1;
			////						while(pos==-1 && a<=rs.getMetaData().getColumnCount()) {
			////							if(rs.getMetaData().getColumnName(a).equalsIgnoreCase(col)){
			////								pos = a;
			////							} else {
			////								a++;
			////							}
			////						}
			////						if(pos!=-1){
			////							if(colSurcheable.equals(""))
			//								queryWhere=initRequete(queryWhere,rs.getMetaData().getColumnType(pos),rs.getMetaData().getColumnName(pos));
			////							else
			////								queryWhere=initRequete(queryWhere,rs.getMetaData().getColumnType(pos),colSurcheable);
			////						}
			//						
			////						if(!param_procedure.equals("")&& !param_procedure.equals("("))
			////							param_procedure+=",";
			////						param_procedure+=initParametres(rs.getMetaData().getColumnType(pos),rs.getMetaData().getColumnName(pos));
			//						
			////						for (int j = 0; j < listeParam.length; j++) {
			////							if(listeParam[j].toUpperCase().startsWith("&PARAM"+col.toUpperCase())){
			////								String valeur=listeParam[j].substring(0,listeParam[j].lastIndexOf("=") );						  
			////								listeParam[j]=valeur+"="+initParametres(rs.getMetaData().getColumnType(pos),rs.getMetaData().getColumnName(pos),false);	
			////							}
			////						}
			//					}
			//				}
			////				else {
			////					if(!param_procedure.equals("")&& !param_procedure.equals("("))
			////						param_procedure+=",";
			////					param_procedure+="null";
			//					
			////					for (int j = 0; j < listeParam.length; j++) {
			////						if(listeParam[j].toUpperCase().startsWith("&PARAM"+col.toUpperCase())){
			////							String valeur=listeParam[j].substring(6,listeParam[j].lastIndexOf("=") );						  
			////							listeParam[j]="&__isnull=Param"+col.toUpperCase();	
			////						}
			////					}
			//
			////				}
			//
			//
			//				valeur_Deb="";
			//				valeur_Fin="";
			//				col="";
			//		//	}
			//			param_procedure+=")";		
			if(procedure.equals("1")){
				String queryTmp="";
				if(queryDeb.indexOf("(")!=-1 && queryDeb.indexOf(")")!=-1)queryTmp=queryDeb.substring(queryDeb.indexOf("("),queryDeb.indexOf(")")+1);
				if(!queryTmp.equals(""))queryDeb=queryDeb.replace(queryTmp, "");
				//queryWhere = ""; 
			}else 
				param_procedure="";


			if(param_procedure == null)param_procedure="";

			logger.debug(queryDeb+" "+param_procedure+" "+queryWhere+" "+groubBy+" "+queryHaving+" "+queryOrderBy);
		}
		queryDeb = queryDeb+" "+param_procedure+" "+queryWhere+" "+groubBy+" "+queryHaving+" "+queryOrderBy;
	}

	protected void actRechercher() throws Exception {
		rechercheJPA();
	}


	private void addOnglet(String[] listeChamp,String[] tailleChamp,String[] titreChamp,  String queryDeb,String paramProcedure,String queryWhere,String queryHaving,String groupBy,String orderBy, ISelection selectionRqt,Object listeInput,String[] listeParam, String titre) {
		PaResultatVisu vueOnglet = new PaResultatVisu(vue.getCTabFolderResultat(),SWT.NONE);
		OngletResultatControllerJPA controller = new OngletResultatControllerJPA(vueOnglet,listeChamp,tailleChamp,titreChamp,queryDeb,paramProcedure,queryWhere,queryHaving,selectionRqt,identifiant,idEditor,listeInput,impression,idPlugin,groupBy,orderBy,listeParam,titre,totaux,typesRetour,queryLang,classeRecherche);
		CTabItemResultatRechercheJPA onglet = new CTabItemResultatRechercheJPA(vue.getCTabFolderResultat(),SWT.CLOSE,vueOnglet,controller);
		onglet.setText("Recherche "+(numRecherche+=1)+" : "+controller.getNbResultat()+" ligne(s)");
		vue.getCTabFolderResultat().setSelection(onglet);
	}

	protected void initActions() {
		mapCommand = new HashMap<String, IHandler>();

		mapCommand.put(C_COMMAND_RECHERCHER_ID, handlerRechercher);
		mapCommand.put(C_COMMAND_GLOBAL_IMPRIMER_ID, handlerImprimer);
		mapCommand.put(C_COMMAND_EXTRACTION_ID, handlerExtraction);
		mapCommand.put(C_COMMAND_GLOBAL_FERMER_ID, handlerFermer);
		mapCommand.put(C_COMMAND_GLOBAL_ANNULER_ID, handlerAnnuler);
		mapCommand.put(C_COMMAND_REINITIALISER_ID, handlerReinitialiser);
		mapCommand.put(C_COMMAND_REINITIALISER_GLOBAL_ID, handlerReinitialiserGlobal);

		initFocusCommand(String.valueOf(this.hashCode()),listeComposantFocusable,mapCommand);

		if (mapActions == null)
			mapActions = new LinkedHashMap<Button, Object>();

		mapActions.put(vue.getPaBtnRecherche().getBtnFermer(), C_COMMAND_REINITIALISER_ID);
		mapActions.put(vue.getPaBtnRecherche().getBtnImprimer(), C_COMMAND_RECHERCHER_ID);
		mapActions.put(vue.getPaBtnImprimer().getBtnSupprimer(), C_COMMAND_EXTRACTION_ID);
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

			if(module!=null) {
			bindRequete();
			bindSelection();
			initGrilleSelection();
			}
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
					tableViewerRequete.setSelection(((CTabItemResultatRechercheJPA)vue.getCTabFolderResultat().getSelection()).getController().getSelectionRqt(),true);
					selectionRequete(tableViewerRequete.getTable().getSelection()[0]);

					LinkedList<EditableTableItem> l = new LinkedList<EditableTableItem>();
					EditableTableItem elem = null;
					for (Object editableTableItem : ((WritableList)((CTabItemResultatRechercheJPA)vue.getCTabFolderResultat().getSelection()).getController().getListeInput()).subList(0, ((WritableList)((CTabItemResultatRechercheJPA)vue.getCTabFolderResultat().getSelection()).getController().getListeInput()).size())) {
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
	}

	@Override
	protected void actAide(String message) throws Exception {
	}

	@Override
	protected void actAnnuler() throws Exception {
	}

	@Override
	protected void actEnregistrer() throws Exception {
	}

	@Override
	protected void actFermer() throws Exception {
		// (controles de sortie et fermeture de la fenetre) => onClose()
		if(onClose())
			closeWorkbenchPart();
	}

	public void exportCSV() {
		try {
			FileDialog dd = new FileDialog(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), SWT.SAVE);
			// Configuration de la fenêtre
			dd.setText("Exporter en fichier csv");
			dd.setFileName("*.csv");
			dd.setFilterExtensions(new String[] { "*.csv" });
			dd.setOverwrite(true);

			// Récupération des informations
			String repDestination = "";
//			String repDestination = GestionDossierPlugin.getDefault().getPreferenceStore().getString("repertoire_w");
			if (repDestination.equals("")) {
				repDestination = Platform.getInstanceLocation().getURL().getFile();
			}
			dd.setFilterPath(LibChaine.pathCorrect(repDestination));
			String choix = dd.open();
			System.err.println(choix);

			// Si la fenetre de dialogue n'a pas été fermée volontairement
			String filecontent = "";
			if (choix != null) {
				PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell().setCursor(Display.getCurrent().getSystemCursor(SWT.CURSOR_WAIT));
				
				// Première ligne contenant les id des colonnes
				InfosPresentation[] infos = ((CTabItemResultatRechercheJPA)vue.getCTabFolderResultat().getSelection()).getController().getInfos();
				for (int i = 0; i < infos.length-1; i++) {
					filecontent += infos[i].getTitre() + ";";
				}
				filecontent += infos[infos.length - 1].getTitre();

				// Contenu du tableau
				Collection<Resultat> collectionResultat = ((CTabItemResultatRechercheJPA)vue.getCTabFolderResultat().getSelection()).getController().getModelImpression().remplirListe();
				for (Resultat resultat : collectionResultat) {
					filecontent += "\n";
					for (int i = 0; i < infos.length-1; i++) {
						filecontent += resultat.findValue(i+1) != null ? resultat.findValue(i+1)+ ";" : ";";
					}
					filecontent += resultat.findValue(infos.length) != null ? resultat.findValue(infos.length):"";
				}
			

				PrintStream out;
				out = new PrintStream(new FileOutputStream(choix), true, "UTF-8");
				out.print(filecontent);
				out.flush();
				out.close();
				out = null;
				PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell().setCursor(Display.getCurrent().getSystemCursor(SWT.CURSOR_ARROW));
				MessageDialog.openInformation(vue.getShell(), "Exportation",
						"Exportation terminée.");
			}
		} catch (UnsupportedEncodingException e1) {
			logger.error("",e1);
		} catch (FileNotFoundException e1) {
			logger.error("",e1);
		} finally {
			vue.setCursor(Display.getCurrent().getSystemCursor(SWT.CURSOR_ARROW));
		}


	}

	public void imprJPA() {
		TaInfoEntrepriseDAO taInfoEntrepriseDAO = new TaInfoEntrepriseDAO(getEm());
		TaInfoEntreprise infoEntreprise = taInfoEntrepriseDAO.findInstance();

		FonctionGetInfosXmlAndProperties fonctionGetInfosXmlAndProperties = new FonctionGetInfosXmlAndProperties();
		fonctionGetInfosXmlAndProperties.cleanValueMapAttributeTable();

		InfosPresentation[] infos = ((CTabItemResultatRechercheJPA)vue.getCTabFolderResultat().getSelection()).getController().getInfos();

		String valueMap = null;
		String[] valueAttributes = null;
		LinkedList<String> listeAttribut = new LinkedList<String>();
		String typeBirt = null;
		String alignement = null;

		String birtDataTypeFloat = "float";
		String birtDataTypeInteger = "integer";
		String birtDataTypeString = "string";
		String birtDataTypeDecimal = "decimal";
		String birtAlignRight = "right";
		String birtAlignLeft = "left";

		/*
		 * Generation des informations decrivant la structure/presentation de la table.
		 * Les lignes generees doivent etre au meme format que celle du fichier "AttributeTableEdition.properties"
		 */
		for (int i = 0; i < infos.length; i++) {
			try {
				//titre
				valueMap = infos[i].getTitre()+";"+infos[i].getTaille()+";center;medium;bold;%;string;vide;vide";
				valueAttributes = valueMap.split(";");
				fonctionGetInfosXmlAndProperties.getMapAttributeTablHead().put(Resultat.debutNomChamp+(i+1),new AttributElementResport(valueAttributes));

				//contenu
				if(infos[i].getTypeString()!=null) {
					if( infos[i].getTypeString().equals(ConstVisualisation.typeFloat)
							
							) {
						typeBirt = birtDataTypeFloat;
						alignement = birtAlignRight;
					}else					
						if(infos[i].getTypeString().equals(ConstVisualisation.typeBigDecimal)
							|| infos[i].getTypeString().equals(ConstVisualisation.typeDouble)
							) {
						typeBirt = birtDataTypeDecimal;
						alignement = birtAlignRight;
					}
						else if(infos[i].getTypeString().equals(ConstVisualisation.typeInteger)) {
						typeBirt = birtDataTypeInteger;
						alignement = birtAlignRight;
				//	}
				} else {
					typeBirt = birtDataTypeString;
					alignement = birtAlignLeft;
				} 
				}

				valueMap = Resultat.debutNomChamp+(i+1)+";"+infos[i].getTaille()+";"+alignement+";medium;bold;%;"+typeBirt+";vide;vide";
				valueAttributes = valueMap.split(";");
				fonctionGetInfosXmlAndProperties.getMapAttributeTablDetail().put(Resultat.debutNomChamp+(i+1),new AttributElementResport(valueAttributes));

				listeAttribut.add(Resultat.debutNomChamp+(i+1));
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		LinkedHashMap<String,AttributElementResport> mapAttributeTablHead = fonctionGetInfosXmlAndProperties.getMapAttributeTablHead(); 
		LinkedHashMap<String,AttributElementResport> mapAttributeTablDetail = fonctionGetInfosXmlAndProperties.getMapAttributeTablDetail();


		//Passage de contexte au Birt Viewer
		ViewerPlugin.getDefault().getPluginPreferences().setValue(WebViewer.APPCONTEXT_EXTENSION_KEY, EditionAppContext.APP_CONTEXT_NAME);

		//Recuperation des objets a imprimer
		Collection<Resultat> collectionResultat = ((CTabItemResultatRechercheJPA)vue.getCTabFolderResultat().getSelection()).getController().getModelImpression().remplirListe();
		
		//Initialisation des colonnes numérique sans valeur avec des zéros, sinon plante lors de l'extraction
		for (Resultat resultat : collectionResultat) {
			for (int i = 0; i < infos.length; i++) {
				if(
						(
						infos[i].getTypeString().equals(ConstVisualisation.typeBigDecimal)
						|| infos[i].getTypeString().equals(ConstVisualisation.typeFloat)
						|| infos[i].getTypeString().equals(ConstVisualisation.typeDouble)
						|| infos[i].getTypeString().equals(ConstVisualisation.typeInteger)
			) &&
						( resultat.findValue(i+1)==null || resultat.findValue(i+1).equals("")) ) {
					resultat.changeValue(i+1, "0");
				}
			}
		}

		/*
		 * ajout des lignes de totaux
		 */
		//ligne vide
		//collectionResultat.add(new Resultat("0"));
		//ligne titre
		//Resultat ligneTitreTotaux = new Resultat("0");
		//ligneTitreTotaux.setValeur1(ConstVisualisation.LIBELLE_TOTAUX);
		//collectionResultat.add(ligneTitreTotaux);
		//totaux
		Resultat totaux = new Resultat(((CTabItemResultatRechercheJPA)vue.getCTabFolderResultat().getSelection()).getController().getTotaux());
		totaux.setValeur1(ConstVisualisation.LIBELLE_TOTAUX);
		collectionResultat.add(totaux);

		ImprimeObjet.clearListAndMap();
		ImprimeObjet.l.addAll(collectionResultat);

		/************** pour préparer plusieurs objects construisirent l'edition ***************/
		ImprimeObjet.m.put(Resultat.class.getSimpleName(), ImprimeObjet.l);
		//ImprimeObjet.m.put(TaEntreprise.class.getSimpleName(), ImprimeObjet.l);
		/**************************************************************************************/

		/*
		 * La liste des attibuts de la classe qui sont utilise pour generer le script dans l'edition
		 * est en general retrouve a partir du mapping dozer et des champ affiches dans l'interface.
		 * Ici il n'y a pas de mapping dozer donc, il faut donner cette liste explicitement.
		 */
		fonctionGetInfosXmlAndProperties.getInfosObjetJPA(new Resultat(),listeAttribut);

		ConstEdition constEdition = new ConstEdition(); 
		String nomDossier=null;

		/**
		 * nombreLine ==> le nombre de ligne dans interface
		 */
		int nombreLine = collectionResultat.size();

		if(nombreLine==0){
			MessageDialog.openWarning(vue.getShell(), ConstEdition.TITRE_MESSAGE_EDITION_VIDE,
					ConstEdition.EDITION_VIDE);
		}else{
			if(infoEntreprise.getIdInfoEntreprise()==0){
				nomDossier = ConstEdition.INFOS_VIDE;
			}
			else{
				nomDossier = infoEntreprise.getNomInfoEntreprise();	
			}
			constEdition.addValueList(((CTabItemResultatRechercheJPA)vue.getCTabFolderResultat().getSelection()).getController().getTableViewerResultat(), nomClassController);

			/**
			 * pathFileReport ==> le path de ficher de edition dynamique
			 */
			String folderEditionDynamique = Const.C_RCP_INSTANCE_LOCATION+"/"+Const.C_NOM_PROJET_TMP+"/"+Resultat.class.getSimpleName();
			constEdition.makeFolderEditions(folderEditionDynamique);
			String nomVue = "visualisation";
			Path pathFileReport = new Path(folderEditionDynamique+"/"+nomVue+".rptdesign");
			final String pathFileReportDynamic = pathFileReport.toPortableString();

			MakeDynamiqueReport dynamiqueReport = new MakeDynamiqueReport(constEdition.getNameTableEcran(),
					constEdition.getNameTableBDD(),pathFileReportDynamic,nomVue,
					ConstEdition.PAGE_ORIENTATION_LANDSCAPE,nomDossier,infos.length); 

			/**************************************************************/
			dynamiqueReport.setFonctionGetInfosXml(fonctionGetInfosXmlAndProperties);
			dynamiqueReport.setNomObjet(Resultat.class.getSimpleName());
			dynamiqueReport.setSimpleNameEntity(Resultat.class.getSimpleName());
			/**************************************************************/
			Map<String, AttributElementResport> attribuGridHeader = new LinkedHashMap<String, AttributElementResport>();

			attribuGridHeader.put(((CTabItemResultatRechercheJPA)vue.getCTabFolderResultat().getSelection()).getController().getTitre(),
					new AttributElementResport("",ConstEdition.TEXT_ALIGN_CENTER,
							ConstEdition.FONT_SIZE_XX_LARGE,ConstEdition.FONT_WEIGHT_BOLD,"",
							ConstEdition.COLUMN_DATA_TYPE_STRING, ConstEdition.UNITS_VIDE
							,""));

			attribuGridHeader.put("", new AttributElementResport("",ConstEdition.TEXT_ALIGN_CENTER,
					ConstEdition.FONT_SIZE_X_LARGE,ConstEdition.FONT_WEIGHT_BOLD,"",
					ConstEdition.COLUMN_DATA_TYPE_STRING,ConstEdition.UNITS_VIDE,
					ConstEdition.COLOR_GRAY));

			//ConstEdition.CONTENT_COMMENTS = ConstEditionArticle.COMMENTAIRE_EDITION_DEFAUT;
			ConstEdition.CONTENT_COMMENTS = "COMMENTAIRE";
			dynamiqueReport.initializeBuildDesignReportConfig();
			dynamiqueReport.makePageMater("1", "1", "1", "1", "100");
			dynamiqueReport.makeReportHeaderGrid(3,5,100,ConstEdition.UNITS_PERCENTAGE,attribuGridHeader);

			dynamiqueReport.biuldTableReport("100", ConstEdition.UNITS_PERCENTAGE, 
					nomVue,1,1,2,"5000", mapAttributeTablHead, mapAttributeTablDetail);
			dynamiqueReport.savsAsDesignHandle();

			//impression.imprimer(idDoc,LibConversion.integerToString(oneIDArticle), true,pathFileReportDynamic);
			//#NICO			
			//			impression.imprimer(true,pathFileReportDynamic,ConstEdition.FICHE_FILE_REPORT_ARTICLES,"Article",Resultat.class.getSimpleName());
			String url = WebViewerUtil.debutURL();
//			System.setProperty("RUN_UNDER_ECLIPSE", "true");
			if (!isExtraction())
				url += "run?__report=";
			else
				url += "frameset?__report=";

			url +=pathFileReportDynamic;

			url += "&__document=doc"+new Date().getTime();			
			if (!isExtraction())url += "&__format=pdf";
			logger.debug("URL edition: " + url);	

			final String finalURL = url;
			PlatformUI.getWorkbench().getDisplay().asyncExec(new Runnable() {
				public void run() {
					try {
						LgrPartListener.getLgrPartListener().setLgrActivePart(null);
						PlatformUI.getWorkbench().getBrowserSupport()
						.createBrowser(
								IWorkbenchBrowserSupport.AS_EDITOR,
								"myId",
								"Visualisation"
								,""
						).openURL(new URL(finalURL));
					} catch (PartInitException e) {
						logger.error("", e);
					} catch (MalformedURLException e) {
						logger.error("", e);
					}
				}
			});
		}


	}

	@Override
	protected void actImprimer() throws Exception {
		try {
			imprJPA();
		} catch(Exception e) {
			logger.error("",e);
		}
	}



	@Override
	protected void actInserer() throws Exception {
	}

	@Override
	protected void actModifier() throws Exception {
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
	}

	@Override
	public void initEtatComposant() {
	}

	@Override
	protected void initMapComposantDecoratedField() {
	}

	@Override
	protected void sortieChamps() {
	}

	protected void initEtatBouton() {
		//super.initEtatBouton();
		enableActionAndHandler(C_COMMAND_RECHERCHER_ID,true);
		enableActionAndHandler(C_COMMAND_GLOBAL_SUPPRIMER_ID,true);
		enableActionAndHandler(C_COMMAND_GLOBAL_ANNULER_ID,true);
		enableActionAndHandler(C_COMMAND_GLOBAL_IMPRIMER_ID,true);
		enableActionAndHandler(C_COMMAND_GLOBAL_FERMER_ID,true);
		enableActionAndHandler(C_COMMAND_EXTRACTION_ID,true);
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
			vue.getLaTitreFenetre().setText("Recherches et visualisations générales");

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
				else if (WHERE_PROPERTY.equals(property))
					return ((RequeteEditableTableItem) element).where;
				else if (ID_GROUPBY_PROPERTY.equals(property))
					return ((RequeteEditableTableItem) element).GROUPBY;
				else if (HAVING_PROPERTY.equals(property))
					return ((RequeteEditableTableItem) element).having;
				else if (PROCEDURE_PROPERTY.equals(property))
					return ((RequeteEditableTableItem) element).PROCEDURE;
				else if (PARAM_PROPERTY.equals(property))
					return ((RequeteEditableTableItem) element).PARAM;	
				else if (TYPES_RETOUR_PROPERTY.equals(property))
					return ((RequeteEditableTableItem) element).TYPES_RETOUR;		
				else if (TOTAUX_PROPERTY.equals(property))
					return ((RequeteEditableTableItem) element).TOTAUX;
				else if (QUERY_LANG_PROPERTY.equals(property))
					return ((RequeteEditableTableItem) element).QUERY_LANG;
				else if (CLASSE_RECHERCHE_PROPERTY.equals(property))
					return ((RequeteEditableTableItem) element).CLASSE_RECHERCHE;	
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
				else if (WHERE_PROPERTY.equals(property))
					data.where = value.toString();
				else if (ID_GROUPBY_PROPERTY.equals(property))
					data.GROUPBY = value.toString();
				else if (HAVING_PROPERTY.equals(property))
					data.having = value.toString();
				else if (PROCEDURE_PROPERTY.equals(property))
					data.PROCEDURE = value.toString();
				else if (PARAM_PROPERTY.equals(property))
					data.PARAM = value.toString();
				else if (TYPES_RETOUR_PROPERTY.equals(property))
					data.TYPES_RETOUR = value.toString();
				else if (TOTAUX_PROPERTY.equals(property))
					data.TOTAUX = value.toString();
				else if (QUERY_LANG_PROPERTY.equals(property))
					data.QUERY_LANG = value.toString();
				else if (CLASSE_RECHERCHE_PROPERTY.equals(property))
					data.CLASSE_RECHERCHE = value.toString();

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
				WHERE_PROPERTY,
				ID_GROUPBY_PROPERTY,
				HAVING_PROPERTY,
				PROCEDURE_PROPERTY,
				PARAM_PROPERTY,
				TYPES_RETOUR_PROPERTY,
				TOTAUX_PROPERTY,
				QUERY_LANG_PROPERTY,
				CLASSE_RECHERCHE_PROPERTY});
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
					return ((RequeteEditableTableItem) element).where;
				case 11:
					return ((RequeteEditableTableItem) element).GROUPBY;
				case 12:
					return ((RequeteEditableTableItem) element).having;
				case 13:
					return ((RequeteEditableTableItem) element).PROCEDURE;
				case 14:
					return ((RequeteEditableTableItem) element).PARAM;
				case 15:
					return ((RequeteEditableTableItem) element).TYPES_RETOUR;
				case 16:
					return ((RequeteEditableTableItem) element).TOTAUX;
				case 17:
					return ((RequeteEditableTableItem) element).QUERY_LANG;
				case 18:
					return ((RequeteEditableTableItem) element).CLASSE_RECHERCHE;
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
		//		if(valeur.contains("%")){
		//			//si contient % au debut (et/ou) a l'interieur de la valeur
		//			if((valeur.startsWith("%") 
		//					&& valeur.lastIndexOf("%")< valeur.length())
		//					||(valeur.lastIndexOf("%")< valeur.length()))
		//				return PREDICAT_LIKE;
		//			else 
		//				//si contient % que au debut (dans ce cas, enlever le %)
		//				if(valeur.startsWith("%")) return PREDICAT_CONTAINING;				
		//		}
		//		else
		//			return PREDICAT_START;
		//		
		//		return valeur;
		return PREDICAT_LIKE;
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

	public String getQueryOrderBy() {
		return queryOrderBy;
	}

	public void setQueryOrderBy(String queryOrderBy) {
		this.queryOrderBy = queryOrderBy;
	}


	

}



