package fr.legrain.document.controller;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.log4j.Logger;
import org.eclipse.core.commands.IHandler;
import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.beans.BeanProperties;
import org.eclipse.core.databinding.observable.ChangeEvent;
import org.eclipse.core.databinding.observable.IChangeListener;
import org.eclipse.core.databinding.observable.Realm;
import org.eclipse.core.databinding.observable.list.WritableList;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.core.databinding.property.list.DelegatingListProperty;
import org.eclipse.core.databinding.property.list.IListProperty;
import org.eclipse.core.databinding.property.value.IValueProperty;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.ListenerList;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.databinding.swt.SWTObservables;
import org.eclipse.jface.databinding.viewers.ViewersObservables;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.fieldassist.DecoratedField;
import org.eclipse.jface.viewers.CellLabelProvider;
import org.eclipse.jface.viewers.CheckStateChangedEvent;
import org.eclipse.jface.viewers.CheckboxTreeViewer;
import org.eclipse.jface.viewers.ColumnViewerToolTipSupport;
import org.eclipse.jface.viewers.ICheckStateListener;
import org.eclipse.jface.viewers.ICheckStateProvider;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreePath;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.ViewerCell;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.PlatformUI;

import fr.legrain.document.ecran.PaDocumentEditable;
import fr.legrain.document.ecran.ParamAfficheDocumentEditable;
import fr.legrain.documents.dao.TaDocumentEditable;
import fr.legrain.documents.dao.TaDocumentEditableDAO;
import fr.legrain.documents.dao.TaParamPublipostage;
import fr.legrain.documents.dao.TaParamPublipostageDAO;
import fr.legrain.documents.dao.TaTDoc;
import fr.legrain.documents.dao.TaTDocDAO;
import fr.legrain.gestCom.Appli.Const;
import fr.legrain.gestCom.Appli.LgrDozerMapper;
import fr.legrain.gestCom.Appli.ModelGeneralObjet;
import fr.legrain.gestCom.Module_Document.SWTDocumentEditable;
import fr.legrain.gestCom.Module_Document.SWTTDoc;
import fr.legrain.gestCom.librairiesEcran.swt.JPABaseControllerSWTStandard;
import fr.legrain.gestCom.librairiesEcran.swt.SWTPaAideControllerSWT;
import fr.legrain.gestCom.librairiesEcran.workbench.ChangementDeSelectionEvent;
import fr.legrain.lib.data.ExceptLgr;
import fr.legrain.lib.data.InfosVerifSaisie;
import fr.legrain.lib.data.LibChaine;
import fr.legrain.lib.data.LibConversion;
import fr.legrain.lib.data.ModeObjet;
import fr.legrain.lib.data.VerrouInterface;
import fr.legrain.lib.data.ModeObjet.EnumModeObjet;
import fr.legrain.lib.gui.ParamAffiche;
import fr.legrain.lib.gui.ResultAffiche;
import fr.legrain.lib.gui.RetourEcranEvent;
import fr.legrain.lib.gui.RetourEcranListener;
import fr.legrain.lib.gui.aide.ResultAide;
import fr.legrain.lib.gui.grille.LgrTableViewer;
import fr.legrain.lib.gui.grille.LgrTreeViewer;
import fr.legrain.lib.gui.grille.LgrViewerSupport;
import fr.legrain.lib.windows.WinRegistry;
import fr.legrain.libMessageLGR.LgrMess;
import fr.legrain.publipostage.divers.TypeVersionPublipostage;
import fr.legrain.publipostage.msword.PublipostageMsWord;
import fr.legrain.publipostage.openoffice.AbstractPublipostageOOo;
import fr.legrain.publipostage.openoffice.PublipostageOOoFactory;

public class SWTPaDocumentEditableController extends JPABaseControllerSWTStandard
implements RetourEcranListener {

	static Logger logger = Logger.getLogger(SWTPaDocumentEditableController.class.getName());
	private PaDocumentEditable vue = null;
	private TaDocumentEditableDAO dao = null;
	private TaTDocDAO taTDocDAO = null;

	private Object ecranAppelant = null;
	private SWTDocumentEditable swtDocumentEditable;
	private SWTDocumentEditable swtOldDocumentEditable;
	private Realm realm;
	private DataBindingContext dbc;

	private LgrTreeViewer treeViewer;
	private LgrTableViewer tableTypeDocViewer;
	private WritableList writableList;
	private IObservableValue selectedDocumentEditable;
	private String[] listeChamp;
	private String nomClassController = this.getClass().getSimpleName();
	private MapChangeListener changeListener = new MapChangeListener();
	private Class classModel = SWTDocumentEditable.class;
	private Class classModelTypeDoc = SWTTDoc.class;
	private ModelGeneralObjet<SWTDocumentEditable,TaDocumentEditableDAO,TaDocumentEditable> modelDocumentEditable = null;	
	private ModelGeneralObjet<SWTTDoc,TaTDocDAO,TaTDoc> modelTypeDoc = null;
	private LgrDozerMapper<SWTDocumentEditable,TaDocumentEditable> mapperUIToModel  = new LgrDozerMapper<SWTDocumentEditable,TaDocumentEditable>();
	private TaDocumentEditable taDocumentEditable = null;

	private TaParamPublipostage paramPubli;
	private TypeVersionPublipostage typeVersion;

	public SWTPaDocumentEditableController(PaDocumentEditable vue) {
		this(vue,null);
	}

	public SWTPaDocumentEditableController(PaDocumentEditable vue,EntityManager em) {
		if(em!=null) {
			setEm(em);
		}
		dao = new TaDocumentEditableDAO(getEm());
		taTDocDAO = new TaTDocDAO(getEm());
		modelDocumentEditable = new ModelGeneralObjet<SWTDocumentEditable,TaDocumentEditableDAO,TaDocumentEditable>(dao,classModel);
		modelTypeDoc = new ModelGeneralObjet<SWTTDoc,TaTDocDAO,TaTDoc>(taTDocDAO,classModelTypeDoc);
		MapperDocumentEditable monMapper = new MapperDocumentEditable();
		monMapper.setEm(getEm());
		modelDocumentEditable.setLgrMapper(monMapper);
		setVue(vue);

		typeVersion = TypeVersionPublipostage.getInstance();
		paramPubli = new TaParamPublipostageDAO(getEm()).findInstance();

		// a faire avant l'initialisation du Binding
		vue.getTree().addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				setSwtOldDocumentEditable();
			}
		});
		vue.getShell().addShellListener(this);

		// Branchement action annuler : empeche la fermeture automatique de la
		// fenetre sur ESC
		vue.getShell().addTraverseListener(new Traverse());

		initController();
	}

	private void initController() {
		try {
			//setGrille(vue.getExpandBar());
			initSashFormWeight();
			setDaoStandard(dao);
			setTreeViewerStandard(treeViewer);
			setDbcStandard(dbc);

			initMapComposantChamps();
			initVerifySaisie();
			initMapComposantDecoratedField();
			listeComponentFocusableSWT(listeComposantFocusable);
			initFocusOrder();
			initActions();
			initDeplacementSaisie(listeComposantFocusable);

			branchementBouton();

			Menu popupMenuFormulaire = new Menu(vue.getShell(), SWT.POP_UP);
			Menu popupMenuGrille = new Menu(vue.getShell(), SWT.POP_UP);
			Menu[] tabPopups = new Menu[] { popupMenuFormulaire,
					popupMenuGrille };
			this.initPopupAndButtons(mapActions, tabPopups);
			vue.getPaCorpsFormulaire().setMenu(popupMenuFormulaire);
			vue.getPaGrille().setMenu(popupMenuGrille);

			initEtatBouton();

		} catch (Exception e) {
			logger.error("Erreur : PaTiersController", e);
			vue.getLaMessage().setText(e.getMessage());
		}
	}

	public void bind(PaDocumentEditable paAdresseSWT) {
		try {
			realm = SWTObservables.getRealm(vue.getParent().getDisplay());

			TreeViewer v = new TreeViewer(paAdresseSWT.getTree());
			treeViewer = new LgrTreeViewer(new CheckboxTreeViewer(paAdresseSWT.getTree()));

			treeViewer.createTableCol(classModel,paAdresseSWT.getTree(), nomClassController,
					Const.C_FICHIER_LISTE_CHAMP_GRILLE,-1);
			listeChamp = treeViewer.setListeChampGrille(nomClassController,
					Const.C_FICHIER_LISTE_CHAMP_GRILLE);

			/*
			 * http://www.developpez.net/forums/d959933-2/environnements-developpement/eclipse/eclipse-platform/debutant-treeviewer-jface-databinding/
			 * http://dev.eclipse.org/viewcvs/viewvc.cgi/org.eclipse.jface.examples.databinding/src/org/eclipse/jface/examples/databinding/snippets/Snippet019TreeViewerWithListFactory.java?revision=1.7&view=co
			 * http://dev.eclipse.org/mhonarc/newsLists/news.eclipse.platform.jface/msg00416.html
			 * http://dev.eclipse.org/viewcvs/viewvc.cgi/org.eclipse.jface.examples.databinding/src/org/eclipse/jface/examples/databinding/snippets/
			 */
			//			ObservableListContentProvider viewerContent = new ObservableListContentProvider();
			//			tableViewer.setContentProvider(viewerContent);
			//
			//			IObservableMap[] attributeMaps = BeansObservables.observeMaps(
			//					viewerContent.getKnownElements(), classModel,listeChamp);
			//
			//			tableViewer.setLabelProvider(new ObservableMapLabelProvider(attributeMaps));
			//			writableList = new WritableList(realm, modelCPaiement.remplirListe(), classModel);
			//			tableViewer.setInput(writableList);

			// Set up data binding.
			//			LgrViewerSupport.bind(treeViewer, 
			//					new WritableList(modelDocumentEditable.remplirListe(), classModel),
			//					BeanProperties.values(listeChamp)
			//					);

			SWTDocumentEditable root = refreshTreeModel();

			IListProperty childrenProp = new DelegatingListProperty() {
				//IListProperty inputChildren = BeanProperties.list(SWTDocumentEditable.class, "codeDocumentDoc");
				//IListProperty inputChildren = BeanProperties.list(ModelGeneralObjet.class, "listeObjet");
				//	IListProperty inputChildren = BeanProperties.list("listeObjet", ModelGeneralObjet.class);
				IListProperty inputChildren = BeanProperties.list("list", SWTDocumentEditable.class);
				//	IListProperty elementChildren = BeanProperties.list("taTDoc", SWTDocumentEditable.class);
				//IListProperty elementChildren = BeanProperties.list(SWTDocumentEditable.class, "codeDocumentDoc");
				protected IListProperty doGetDelegate(Object source) {
					//					if (source instanceof ModelGeneralObjet)
					if (source instanceof SWTDocumentEditable)
						return inputChildren;
					//					if (source instanceof SWTDocumentEditable)
					//						return elementChildren;
					return null;
				}
			};

			IValueProperty[] labelProps = BeanProperties.values(SWTDocumentEditable.class ,listeChamp);
			////IValueProperty[] labelProps = BeanProperties.values(listeChamp);

			//LgrViewerSupport.bind(treeViewer.getViewer(),
			ViewerSupportDocumentEditable.bind(treeViewer.getViewer(),
					//new WritableList(modelDocumentEditable.remplirListe(), classModel), 
					//modelDocumentEditable,
					//l,
					root,
					childrenProp,
					//BeanProperties.list("list", SWTDocumentEditable.class),
					//BeanProperties.value(SWTDocumentEditable.class, "codeDocumentDoc")
					labelProps
			);
			treeViewer.getViewer().refresh();

			//			selectedDocumentEditable = ViewersObservables.observeSingleSelection(treeViewer.getViewer());

			class LgrListener implements ISelectionChangedListener, ISelectionProvider {

				protected ListenerList listenerList = new ListenerList();

				@Override
				public void selectionChanged(SelectionChangedEvent event) {
					if(event.getSelection() instanceof TreeSelection) {
						Object o = ((TreeSelection)event.getSelection()).getFirstElement();
						if(o instanceof SWTDocumentEditable) {
							if(((SWTDocumentEditable)o).getLibelleDocumentDoc()!=null
									/*&& ((SWTDocumentEditable)o).getCodeDocumentDoc()!=null*/) {
								//setSelection(event.getSelection());
								fireChangementDeSelection(new SelectionChangedEvent(this, getSelection()));
							} else {
							}
						}
					}
				}

				@Override
				public void setSelection(ISelection selection) {
					treeViewer.getViewer().setSelection(selection);
					fireChangementDeSelection(new SelectionChangedEvent(this, getSelection()));
					initEtatBouton();
				}

				@Override
				public void removeSelectionChangedListener(ISelectionChangedListener listener) {
					listenerList.remove(listener);
				}

				@Override
				public ISelection getSelection() {
					if(treeViewer.getViewer().getSelection() instanceof TreeSelection) {
						Object o = ((TreeSelection)treeViewer.getViewer().getSelection()).getFirstElement();
						if(o instanceof SWTDocumentEditable) {
							if(((SWTDocumentEditable)o).getLibelleDocumentDoc()!=null
									/*&& ((SWTDocumentEditable)o).getCodeDocumentDoc()!=null*/) {
								//setSelection(event.getSelection());
								//fireChangementDeSelection(new SelectionChangedEvent(this, getSelection()));
								return treeViewer.getViewer().getSelection();
							} else {
								return null;
							}
						}
					}
					return treeViewer.getViewer().getSelection();
				}

				@Override
				public void addSelectionChangedListener(ISelectionChangedListener listener) {
					listenerList.add(listener);
				}

				protected void fireChangementDeSelection(SelectionChangedEvent e) {
					Object[] listeners = listenerList.getListeners();
					for (int i = listeners.length - 1; i >= 0; i -= 1) {
						if (listeners[i] == ISelectionChangedListener.class) {
							if (e == null)
								e = new SelectionChangedEvent(this,null);
							( (ISelectionChangedListener) listeners[i]).selectionChanged(e);
						}
					}
				}
			}

			LgrListener l = new LgrListener();
			//			treeViewer.getViewer().addSelectionChangedListener(l);
			selectedDocumentEditable = ViewersObservables.observeSingleSelection(l);
			treeViewer.selectionGrille(0);

			dbc = new DataBindingContext(realm);

			dbc.getValidationStatusMap().addMapChangeListener(changeListener);
			treeViewer.selectionGrille(0);

			setTreeViewerStandard(treeViewer);
			setDbcStandard(dbc);

			bindingFormMaitreDetail(dbc, realm, selectedDocumentEditable,classModel);
			changementDeSelection();
			selectedDocumentEditable.addChangeListener(new IChangeListener() {

				public void handleChange(ChangeEvent event) {
					changementDeSelection();
					if(tableTypeDocViewer!=null) {
						tableTypeDocViewer.setInput(new WritableList(modelTypeDoc.remplirListe(),classModelTypeDoc));
					}
				}

			});

			//refreshExpandBar();

			bindTableTypeDoc(vue);

		} catch (Exception e) {
			logger.error("", e);
			vue.getLaMessage().setText(e.getMessage());
		}
	}

	private SWTDocumentEditable refreshTreeModel() {
		return refreshTreeModel(null);
	}

	private SWTDocumentEditable refreshTreeModel(List<SWTDocumentEditable> list) {
		if(list == null) //on travaille à partir de la liste d'objet d'interface passée en paramètre (qui ne correspond pas forcement aux lignes en bdd)
			list = modelDocumentEditable.remplirListe();
		list = inverseStructureListe(list);
		SWTDocumentEditable root = new SWTDocumentEditable();
		root.setList(list);
		return root;
	}

	public List<SWTDocumentEditable> inverseStructureListe(List<SWTDocumentEditable> l) {
		List<SWTDocumentEditable> newList = new ArrayList<SWTDocumentEditable>();
		List<TaTDoc> ltdoc = taTDocDAO.selectAll();
		SWTDocumentEditable var = null;
		for (TaTDoc taTDoc : ltdoc) {
			//ajout type doc
			var = new SWTDocumentEditable();
			var.setCodeTypeDoc(taTDoc.getCodeTDoc());
			newList.add(var);
			//boucle ajout doc dans type
			for (SWTDocumentEditable doc : l) {
				if(doc.getTaTDoc()!=null) {
					for (SWTTDoc swtTdoc : doc.getTaTDoc()) {
						if(swtTdoc.getCodeTDoc().equals(taTDoc.getCodeTDoc())) {
							if(var.getList()==null)
								var.setList(new ArrayList<SWTDocumentEditable>());
							//doc.setCodeTypeDoc(swtTdoc.getCodeTDoc());
							//doc.setCheminCorrespDocumentDoc(swtTdoc.getCodeTDoc());
							var.getList().add(doc);
						}
					}
					//				} else { //nouveau doc qui n'appartient a aucun type
					//					if(!newList.contains(doc)) //ajout une seule fois
					//						newList.add(doc);
				}
			}
			var.setCodeDocumentDoc(taTDoc.getCodeTDoc()+" - "+taTDoc.getLibTDoc()+" ("+var.getList().size()+")");
		}
		for (SWTDocumentEditable doc : l) {
			if(doc.getTaTDoc()==null) {//nouveau doc qui n'appartient a aucun type
				doc.setLibelleDocumentDoc(""); //Problème d'affichage de l'arbre si tous les champs sont vides
				if(!newList.contains(doc)) //ajout une seule fois
					newList.add(doc);
			}
		}
		return newList;
	}

	public void bindTableTypeDoc(PaDocumentEditable vue) {
		try {
			realm = SWTObservables.getRealm(vue.getParent().getDisplay());
			String chaineTypeDoc = "TypeDoc";
			tableTypeDocViewer = new LgrTableViewer(vue.getTableTypeDoc());
			tableTypeDocViewer.createTableCol(classModelTypeDoc,vue.getTableTypeDoc(), nomClassController+chaineTypeDoc,
					Const.C_FICHIER_LISTE_CHAMP_GRILLE,0);
			String[] listeChamp = tableTypeDocViewer.setListeChampGrille(nomClassController+chaineTypeDoc,
					Const.C_FICHIER_LISTE_CHAMP_GRILLE);

			tableTypeDocViewer.addCheckStateListener(new ICheckStateListener() {

				@Override
				public void checkStateChanged(CheckStateChangedEvent event) {
					try {
						if(selectedDocumentEditable!=null
								&& selectedDocumentEditable.getValue()!=null) {

							if( ((SWTDocumentEditable)selectedDocumentEditable.getValue()).getTaTDoc()==null) { //a priori, insertion d'un nouveau document
								((SWTDocumentEditable)selectedDocumentEditable.getValue()).setTaTDoc(new ArrayList<SWTTDoc>());
							}

							modifMode();

							if(event.getChecked()) {
								((SWTDocumentEditable)selectedDocumentEditable.getValue()).getTaTDoc().add(((SWTTDoc)event.getElement()));
							} else {
								boolean trouve = false;
								SWTTDoc aSupprimer = null;
								List<SWTTDoc> l = ((SWTDocumentEditable)selectedDocumentEditable.getValue()).getTaTDoc();
								for (SWTTDoc swttDoc : l) {
									if(swttDoc.getCodeTDoc().equals(((SWTTDoc)event.getElement()).getCodeTDoc())) {
										trouve = true;
										aSupprimer = (SWTTDoc)event.getElement();
									}
								}
								if(trouve) {
									((SWTDocumentEditable)selectedDocumentEditable.getValue()).getTaTDoc().remove(aSupprimer);
								}
							}

						}

						//						String versionNew=vue.getCbListeVersion().getItem(vue.getCbListeVersion().getSelectionIndex());
						//						getTableViewerStandard().setSelection(new StructuredSelection(recherche(Const.C_CODE_DOCUMENT_TIERS
						//								, ((SWTDocumentTiers)event.getElement()).getCodeDocumentTiers())),true);
						//						if(selectedDocumentTiers.getValue()!=null){
						//							actModifier();
						//							if(!event.getChecked())
						//								((SWTDocumentTiers)event.getElement()).setActif(event.getChecked());
						//							else 					
						//								if(event.getChecked() && ((SWTDocumentTiers)event.getElement()).getTypeLogiciel().equals(typeVersion.getType().get(versionNew)))
						//									((SWTDocumentTiers)event.getElement()).setActif(event.getChecked());
						//								else
						//									event.getCheckable().setChecked(event.getElement(), false);
						//							validateUIField(Const.C_ACTIF_DOCUMENT_TIERS,LibConversion.booleanToInt(event.getChecked()));
						//							actEnregistrer();
						//						}
					} catch (Exception e) {
						logger.error("", e);
					}
				}
			});

			tableTypeDocViewer.setCheckStateProvider(new ICheckStateProvider() {

				@Override
				public boolean isGrayed(Object element) {
					//					// TODO Auto-generated method stub
					//					if(!((SWTDocumentTiers)element).getActif())
					//						return true;
					return false;
				}

				@Override
				public boolean isChecked(Object element) {
					if(selectedDocumentEditable!=null
							&& selectedDocumentEditable.getValue()!=null
							&& ((SWTDocumentEditable)selectedDocumentEditable.getValue()).getTaTDoc()!=null) {
						List<SWTTDoc> l = ((SWTDocumentEditable)selectedDocumentEditable.getValue()).getTaTDoc();
						for (SWTTDoc swttDoc : l) {
							if(swttDoc.getCodeTDoc().equals(((SWTTDoc)element).getCodeTDoc())) {
								return true;
							}
						}

					}
					return false;

					//					if(((SWTDocumentTiers)element).getActif())
					//						return true;
					//					return false;
				}
			});

			// Set up data binding.
			LgrViewerSupport.bind(tableTypeDocViewer, 
					//new WritableList(IHMmodel(), classModel),
					new WritableList(modelTypeDoc.remplirListe(), classModelTypeDoc),
					BeanProperties.values(listeChamp)
			);

			//			IObservableValue selectedTDoc;
			//
			//			selectedTDoc = ViewersObservables.observeSingleSelection(tableTypeDocViewer);
			//
			//			dbc = new DataBindingContext(realm);
			//
			//			dbc.getValidationStatusMap().addMapChangeListener(changeListener);
			//			tableTypeDocViewer.selectionGrille(0);
			//
			//			setTableViewerStandard(tableTypeDocViewer);
			//			setDbcStandard(dbc);
			//
			//			selectedTDoc.getValue();
			//			//bindingFormMaitreDetail(dbc, realm, selectedTDoc,classModelTypeDoc);
			//			changementDeSelection();
			//			selectedTDoc.addChangeListener(new IChangeListener() {
			//
			//				public void handleChange(ChangeEvent event) {
			//					changementDeSelection();
			//				}
			//
			//			});

		} catch (Exception e) {
			vue.getLaMessage().setText(e.getMessage());
			logger.error("", e);
		}
	}

	private void changementDeSelection() {
		if(selectedDocumentEditable!=null && selectedDocumentEditable.getValue()!=null) {
			if(((SWTDocumentEditable) selectedDocumentEditable.getValue()).getIdDocumentDoc()!=null) {
				taDocumentEditable = dao.findById(((SWTDocumentEditable) selectedDocumentEditable.getValue()).getIdDocumentDoc());
			}
			//null a tester ailleurs, car peut etre null en cas d'insertion
			fireChangementDeSelection(new ChangementDeSelectionEvent(SWTPaDocumentEditableController.this));
		}
	}

	public Composite getVue() {
		return vue;
	}

	public ResultAffiche configPanel(ParamAffiche param) {
		if (param != null) {
			Map<String,String[]> map = dao.getParamWhereSQL();
			//ibTaTable.ouvreDataset();
			if (((ParamAfficheDocumentEditable) param).getFocusDefautSWT() != null && !((ParamAfficheDocumentEditable) param).getFocusDefautSWT().isDisposed())
				((ParamAfficheDocumentEditable) param).getFocusDefautSWT().setFocus();
			else
				((ParamAfficheDocumentEditable) param).setFocusDefautSWT(vue.getTree());
			vue.getLaTitreFormulaire().setText(((ParamAfficheDocumentEditable) param).getTitreFormulaire());
			vue.getLaTitreGrille().setText(((ParamAfficheDocumentEditable) param).getTitreGrille());
			vue.getLaTitreFenetre().setText(((ParamAfficheDocumentEditable) param).getSousTitre());

			//			//if(((ParamAfficheDocumentEditable)param).getIdTiers()!=null) {
			//			String operateur = "=";
			//			this.conditionTiers = ((ParamAfficheDocumentEditable)param).isConditionTiers();
			//			initItemCombo();
			//			vue.getCbTypeCPaiement().select(0);
			//			if(conditionTiers){
			//				vue.getCbTypeCPaiement().setEnabled(false);
			//				vue.getCbTypeCPaiement().select(vue.getCbTypeCPaiement().indexOf(findComboItemDocumentCombo(TaTCPaiement.C_CODE_TYPE_TIERS)));
			//				vue.getCbCPaiementDocDefaut().setEnabled(false);
			//				vue.getCbCPaiementDocDefaut().setVisible(false);
			//				vue.getLaCPaiementDocDefaut().setVisible(false);
			//			} else {
			//				//condition documents
			//				operateur = "<>";
			//			}
			//			if(((ParamAfficheDocumentEditable)param).getIdTypeCond()!=null){
			//				this.idTypeConditionPaiement=((ParamAfficheDocumentEditable)param).getIdTypeCond().toString();
			//				if(map==null) map = new HashMap<String,String[]>();
			//				map.clear();
			//				map.put("a.taTCPaiement."+Const.C_ID_T_C_PAIEMENT,new String[]{operateur,idTypeConditionPaiement});
			//				dao.setParamWhereSQL(map);
			//			}
			//			dao.setParamWhereSQL(map);

			//}
			if (param.getEcranAppelant() != null) {
				ecranAppelant = param.getEcranAppelant();
			}

			if (param.getEcranAppelant() != null) {
				ecranAppelant = param.getEcranAppelant();
			}

			bind(vue);
			// permet de se positionner sur le 1er enregistrement et de remplir
			// le formulaire
			//treeViewer.selectionGrille(0);
			//treeViewer.tri(classModel, nomClassController,Const.C_FICHIER_LISTE_CHAMP_GRILLE);
			VerrouInterface.setVerrouille(false);
			setSwtOldDocumentEditable();

			//			filtreCombo();

			if (param.getModeEcran() != null
					&& param.getModeEcran().compareTo(EnumModeObjet.C_MO_INSERTION) == 0) {
				try {
					actInserer();
				} catch (Exception e) {
					vue.getLaMessage().setText(e.getMessage());
					logger.error("", e);
				}
			} 
		}
		return null;
	}

	protected void initVerifySaisie() {
		if (mapInfosVerifSaisie == null)
			mapInfosVerifSaisie = new HashMap<Control, InfosVerifSaisie>();

		mapInfosVerifSaisie.put(vue.getTfCode(), new InfosVerifSaisie(new TaDocumentEditable(),Const.C_CODE_DOCUMENT_DOC,null));
		mapInfosVerifSaisie.put(vue.getTfCheminCorresp(), new InfosVerifSaisie(new TaDocumentEditable(),Const.C_CHEMIN_CORRESP_DOCUMENT_DOC,null));
		mapInfosVerifSaisie.put(vue.getTfLibelle(), new InfosVerifSaisie(new TaDocumentEditable(),Const.C_LIBELLE_DOCUMENT_DOC,null));
		mapInfosVerifSaisie.put(vue.getTfCheminDocument(), new InfosVerifSaisie(new TaDocumentEditable(),Const.C_CHEMIN_MODEL_DOCUMENT_DOC,null));

		initVerifyListener(mapInfosVerifSaisie, dao);
	}

	protected void initComposantsVue() throws ExceptLgr {
	}

	protected void initMapComposantChamps() {
		if (mapComposantChamps == null)
			mapComposantChamps = new LinkedHashMap<Control, String>();

		if (listeComposantFocusable == null)
			listeComposantFocusable = new ArrayList<Control>();
		listeComposantFocusable.add(vue.getTree());

		vue.getTfCode().setToolTipText(Const.C_CODE_DOCUMENT_DOC);
		vue.getTfCheminCorresp().setToolTipText(Const.C_CHEMIN_CORRESP_DOCUMENT_DOC);
		vue.getTfLibelle().setToolTipText(Const.C_LIBELLE_DOCUMENT_DOC);
		vue.getTfCheminDocument().setToolTipText(Const.C_CHEMIN_MODEL_DOCUMENT_DOC);

		mapComposantChamps.put(vue.getTfCode(), Const.C_CODE_DOCUMENT_DOC);
		mapComposantChamps.put(vue.getTfLibelle(),Const.C_LIBELLE_DOCUMENT_DOC);
		mapComposantChamps.put(vue.getTfCheminDocument(),Const.C_CHEMIN_MODEL_DOCUMENT_DOC);
		mapComposantChamps.put(vue.getTfCheminCorresp(), Const.C_CHEMIN_CORRESP_DOCUMENT_DOC);
		mapComposantChamps.put(vue.getCbDefaut(), Const.C_DEFAUT_DOCUMENT_DOC);

		for (Control c : mapComposantChamps.keySet()) {
			listeComposantFocusable.add(c);
		}

		listeComposantFocusable.add(vue.getBtnEnregistrer());
		listeComposantFocusable.add(vue.getBtnInserer());
		listeComposantFocusable.add(vue.getBtnModifier());
		listeComposantFocusable.add(vue.getBtnSupprimer());
		listeComposantFocusable.add(vue.getBtnFermer());
		listeComposantFocusable.add(vue.getBtnAnnuler());
		listeComposantFocusable.add(vue.getBtnImprimer());

		if (mapInitFocus == null)
			mapInitFocus = new LinkedHashMap<ModeObjet.EnumModeObjet, Control>();
		mapInitFocus.put(ModeObjet.EnumModeObjet.C_MO_INSERTION, vue
				.getTfCode());
		mapInitFocus.put(ModeObjet.EnumModeObjet.C_MO_EDITION, vue
				.getTfCode());
		mapInitFocus.put(ModeObjet.EnumModeObjet.C_MO_CONSULTATION, vue
				.getTree());

		activeModifytListener();

		//		vue.getTfFIN_MOIS_C_PAIEMENT().addVerifyListener(queDesChiffres);
		//		vue.getTfREPORT_C_PAIEMENT().addVerifyListener(queDesChiffres);

		vue.getBtnChemin_Model().addMouseListener(new MouseAdapter() {

			@Override
			public void mouseDown(MouseEvent e) {
				super.mouseDown(e);

				FileDialog dd = new FileDialog(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell());
				dd.setFilterExtensions(new String[] {"*.*",paramPubli.getExtension()});
				dd.setFilterNames(new String[] {"Tous les fichiers","Modèle de lettre"});
				String repDestination = dd.getFilterPath(); 
				if(repDestination.equals(""))repDestination=Platform.getInstanceLocation().getURL().getFile();
				dd.setFilterPath(LibChaine.pathCorrect(repDestination));
				String choix = dd.open();
				System.out.println(choix);
				if(choix!=null){
					vue.getTfCheminDocument().setText(choix);
				}
				vue.getTfCheminDocument().forceFocus();
			}

		});

		vue.getBtnChemin_Corresp().addMouseListener(new MouseAdapter() {

			@Override
			public void mouseDown(MouseEvent e) {
				super.mouseDown(e);
				FileDialog dd = new FileDialog(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell());
				dd.setFilterExtensions(new String[] {TypeVersionPublipostage.EXTENSION_CORRESPONDANCE});
				dd.setFilterNames(new String[] {"Fichier de correspondance"});
				String repDestination = dd.getFilterPath(); 
				if(repDestination.equals(""))repDestination=Platform.getInstanceLocation().getURL().getFile();
				dd.setFilterPath(LibChaine.pathCorrect(repDestination));
				String choix = dd.open();
				System.out.println(choix);
				if(choix!=null){
					vue.getTfCheminCorresp().setText(choix);
				}
				vue.getTfCheminCorresp().forceFocus();
			}
		});

		vue.getBtnOuvrir().addMouseListener(new MouseAdapter() {

			@Override
			public void mouseDown(MouseEvent e) {
				super.mouseDown(e);
				String chemin = vue.getTfCheminDocument().getText();
				if(((SWTDocumentEditable) selectedDocumentEditable.getValue()).getDefaut()){
					File fileNew=new File(Const.C_REPERTOIRE_BASE+Const.C_REPERTOIRE_PROJET+"/ModelesRelance"+"/"+chemin);
					chemin=fileNew.getPath();
				}
				if(typeVersion.getType().get(paramPubli.getLogicielPublipostage()).
						equals(TypeVersionPublipostage.TYPE_OPENOFFICE)){

					//PublipostageOOoWin32 pub = new PublipostageOOoWin32(listeParamPubli);
					AbstractPublipostageOOo pub = PublipostageOOoFactory.createPublipostageOOo(null);
					String pathOpenOffice = "";

					try {
						if(Platform.getOS().equals(Platform.OS_WIN32)){
							pathOpenOffice = WinRegistry.readString(
									WinRegistry.HKEY_LOCAL_MACHINE,
									WinRegistry.KEY_REGISTR_WIN_OPENOFFICE,
							"");
						} else if(Platform.getOS().equals(Platform.OS_LINUX)){
							pathOpenOffice = "/usr/bin/soffice";
						}else if(Platform.getOS().equals(Platform.OS_MACOSX)) {}
					}
					catch (Exception e3) {
						logger.error("",e3);
					}    

					pub.setCheminOpenOffice(new File(pathOpenOffice).getPath());
					pub.setPortOpenOffice("8100");

					pub.setNomFichierFinalFusionne(new File(chemin).getPath());
					pub.demarrerServeur();
					pub.OuvreDocument("8100",new File(chemin).getPath());

				}else if(typeVersion.getType().get(paramPubli.getLogicielPublipostage()).
						equals(TypeVersionPublipostage.TYPE_MSWORD)){
					PublipostageMsWord pub = new PublipostageMsWord(null);
					pub.OuvreDocument(new File(chemin).getPath());
				}

			}

		});
	}

	@Override
	protected void initEtatBouton() {
		super.initEtatBouton();
		//		if(selectedDocumentEditable!=null && selectedDocumentEditable.getValue()!=null) {
		//			String codeTypeDocSelection = ((SWTDocumentEditable) selectedDocumentEditable.getValue()).getCodeTypeDoc();
		//			if(codeTypeDocSelection!=null || !codeTypeDocSelection.equals("")) {
		//				enableActionAndHandler(C_COMMAND_GLOBAL_INSERER_ID,false);
		//				enableActionAndHandler(C_COMMAND_GLOBAL_MODIFIER_ID,false);
		//				enableActionAndHandler(C_COMMAND_GLOBAL_ENREGISTRER_ID,false);
		//				enableActionAndHandler(C_COMMAND_GLOBAL_ANNULER_ID,false);
		//				enableActionAndHandler(C_COMMAND_GLOBAL_IMPRIMER_ID,false);
		//				enableActionAndHandler(C_COMMAND_GLOBAL_FERMER_ID,true);
		//				enableActionAndHandler(C_COMMAND_GLOBAL_SUPPRIMER_ID,false);
		//				enableActionAndHandler(C_COMMAND_GLOBAL_AIDE_ID,false);
		//				enableActionAndHandler(C_COMMAND_GLOBAL_REFRESH_ID,false);
		//				enableActionAndHandler(C_COMMAND_DOCUMENT_AFFICHER_TOUS_ID,false);
		//			} 
		//		}
	}

	protected void initActions() {
		mapCommand = new HashMap<String, IHandler>();

		mapCommand.put(C_COMMAND_GLOBAL_MODIFIER_ID, handlerModifier);
		mapCommand.put(C_COMMAND_GLOBAL_ANNULER_ID, handlerAnnuler);
		mapCommand.put(C_COMMAND_GLOBAL_ENREGISTRER_ID, handlerEnregistrer);
		mapCommand.put(C_COMMAND_GLOBAL_INSERER_ID, handlerInserer);
		mapCommand.put(C_COMMAND_GLOBAL_SUPPRIMER_ID, handlerSupprimer);
		mapCommand.put(C_COMMAND_GLOBAL_REFRESH_ID, handlerRefresh);
		mapCommand.put(C_COMMAND_GLOBAL_AIDE_ID, handlerAide);
		mapCommand.put(C_COMMAND_GLOBAL_FERMER_ID, handlerFermer);
		mapCommand.put(C_COMMAND_GLOBAL_IMPRIMER_ID, handlerImprimer);

		initFocusCommand(String.valueOf(this.hashCode()),listeComposantFocusable,mapCommand);

		if (mapActions == null)
			mapActions = new LinkedHashMap<Button, Object>();

		mapActions.put(vue.getBtnAnnuler(), C_COMMAND_GLOBAL_ANNULER_ID);
		mapActions.put(vue.getBtnEnregistrer(), C_COMMAND_GLOBAL_ENREGISTRER_ID);
		mapActions.put(vue.getBtnInserer(), C_COMMAND_GLOBAL_INSERER_ID);
		mapActions.put(vue.getBtnModifier(), C_COMMAND_GLOBAL_MODIFIER_ID);
		mapActions.put(vue.getBtnSupprimer(), C_COMMAND_GLOBAL_SUPPRIMER_ID);
		mapActions.put(vue.getBtnFermer(), C_COMMAND_GLOBAL_FERMER_ID);
		mapActions.put(vue.getBtnImprimer(), C_COMMAND_GLOBAL_IMPRIMER_ID);

		Object[] tabActionsAutres = new Object[] { C_COMMAND_GLOBAL_AIDE_ID, C_COMMAND_GLOBAL_REFRESH_ID };
		mapActions.put(null, tabActionsAutres);

	}

	public SWTPaDocumentEditableController getThis() {
		return this;
	}

	@Override
	public boolean onClose() throws ExceptLgr {
		boolean retour = true;
		VerrouInterface.setVerrouille(true);
		//switch (ibTaTable.getFModeObjet().getMode()) {
		switch (dao.getModeObjet().getMode()) {
		case C_MO_INSERTION:
		case C_MO_EDITION:
			if (MessageDialog.openQuestion(vue.getShell(), MessagesEcran
					.getString("Message.Attention"), MessagesEcran
					.getString("DocumentEditable.Message.Enregistrer"))) {

				try {
					actEnregistrer();
				} catch (Exception e) {
					vue.getLaMessage().setText(e.getMessage());
					logger.error("", e);
				}
			} else
				//ibTaTable.annuler();
				break;
		case C_MO_CONSULTATION:
			break;
		default:
			break;
		}

		if (retour) {
			if (ecranAppelant instanceof SWTPaAideControllerSWT) {
				setListeEntity(getModelDocumentEditable().remplirListe());
				dao.initValeurIdTable(taDocumentEditable);
				fireRetourEcran(new RetourEcranEvent(this, new ResultAide(
						//ibTaTable.champIdTable, ibTaTable.valeurIdTable,
						dao.getChampIdEntite(), dao.getValeurIdTable(),
						selectedDocumentEditable.getValue())));
				//				vue.getDisplay().asyncExec(new Runnable() {
				//				public void run() {
				//				vue.getShell().setVisible(false);
				//				}
				//				});
				//				retour = false;
				retour = true;
			} 
		}
		return retour;
	}

	public void retourEcran(final RetourEcranEvent evt) {
		if (evt.getRetour() != null
				&& (evt.getSource() instanceof SWTPaAideControllerSWT)) {
			if (getFocusAvantAideSWT() instanceof Text) {
				try {
					((Text) getFocusAvantAideSWT()).setText(((ResultAffiche) evt.getRetour()).getResult());					
					ctrlUnChampsSWT(getFocusAvantAideSWT());
				} catch (Exception e) {
					logger.error("",e);
					vue.getLaMessage().setText(e.getMessage());
				}
			}
			if (getFocusAvantAideSWT() instanceof Table) {
				if (getFocusAvantAideSWT() == vue.getTree()) {
					//ibTaTable.lgrLocateID(((ResultAffiche) evt.getRetour()).getIdResult());
					if(((ResultAffiche) evt.getRetour()).getSelection()!=null)
						treeViewer.getViewer().setSelection(((ResultAffiche) evt.getRetour()).getSelection(),true);
				}
			}
		} else 	if (evt.getRetour() != null) {
			if (getFocusAvantAideSWT() instanceof Table) {
				if (getFocusAvantAideSWT() == vue.getTree()) {
					//ibTaTable.lgrLocateID(((ResultAffiche) evt.getRetour()).getIdResult());
					if(((ResultAffiche) evt.getRetour()).getSelection()!=null)
						treeViewer.getViewer().setSelection(((ResultAffiche) evt.getRetour()).getSelection(),true);
				}
			}
		}
		super.retourEcran(evt);
	}

	@Override
	protected void actInserer() throws Exception {
		try {
			if(dao.getModeObjet().getMode().compareTo(ModeObjet.EnumModeObjet.C_MO_CONSULTATION)==0) {
				VerrouInterface.setVerrouille(true);
				setSwtOldDocumentEditable();
				swtDocumentEditable = new SWTDocumentEditable();
				taDocumentEditable = new TaDocumentEditable();			
				taDocumentEditable.setTypeLogiciel("OOo");

				dao.inserer(taDocumentEditable);
				List<SWTDocumentEditable> list = modelDocumentEditable.remplirListe();
				//				modelDocumentEditable.getListeObjet().add(swtDocumentEditable);
				list.add(swtDocumentEditable);

				//				writableList = new WritableList(realm, modelDocumentEditable.getListeObjet(), classModel);
				//				treeViewer.setInput(writableList);
				treeViewer.setInput(refreshTreeModel(list));
				treeViewer.getViewer().refresh();
				treeViewer.getViewer().setSelection(new StructuredSelection(swtDocumentEditable));
				initEtatBouton();
			}

		} catch (Exception e1) {
			vue.getLaMessage().setText(e1.getMessage());
			logger.error("Erreur : actionPerformed", e1);
		} finally {
			initEtatComposant();
			VerrouInterface.setVerrouille(false);
		}
	}

	@Override
	protected void actModifier() throws Exception {
		try {
			if(!LgrMess.isDOSSIER_EN_RESEAU()){
				setSwtOldDocumentEditable();
				taDocumentEditable = dao.findById(((SWTDocumentEditable) selectedDocumentEditable.getValue()).getIdDocumentDoc());
			}else{
				if(!setSwtOldCPaiementRefresh())throw new Exception();
			}

			dao.modifier(taDocumentEditable);			
			initEtatBouton();
		} catch (Exception e1) {
			vue.getLaMessage().setText(e1.getMessage());
			logger.error("Erreur : actionPerformed", e1);
		}
	}

	public boolean containsEntity(TaDocumentEditable entite){
		if(modelDocumentEditable.getListeEntity()!=null){
			for (Object e : modelDocumentEditable.getListeEntity()) {
				if(((TaDocumentEditable)e).getIdDocumentDoc()==
					entite.getIdDocumentDoc())return true;
			}
		}
		return false;
	}

	public boolean setSwtOldCPaiementRefresh() {
		try {	
			if (selectedDocumentEditable.getValue()!=null){
				TaDocumentEditable taArticleOld =dao.findById(taDocumentEditable.getIdDocumentDoc());
				taArticleOld=dao.refresh(taArticleOld);
				if(containsEntity(taDocumentEditable)) 
					modelDocumentEditable.getListeEntity().remove(taDocumentEditable);
				if(!taDocumentEditable.getVersionObj().equals(taArticleOld.getVersionObj())){
					taDocumentEditable=taArticleOld;
					if(!containsEntity(taDocumentEditable)) 
						modelDocumentEditable.getListeEntity().add(taDocumentEditable);					
					actRefresh();
					dao.messageNonAutoriseModification();
				}
				taDocumentEditable=taArticleOld;
				if(!containsEntity(taDocumentEditable)) 
					modelDocumentEditable.getListeEntity().add(taDocumentEditable);
				changementDeSelection();
				this.swtOldDocumentEditable=SWTDocumentEditable.copy((SWTDocumentEditable)selectedDocumentEditable.getValue());
			}
			return true;
		} catch (Exception e) {
			return false;
		}		
	}

	//	public void setSwtOldCPaiementRefresh() {
	//		if (selectedCPaiement.getValue()!=null){
	//			if(LgrMess.isDOSSIER_EN_RESEAU())dao.refresh(dao.findById(((SWTDocumentEditable) selectedCPaiement.getValue()).getIdCPaiement()));
	//			taCPaiement=dao.findById(((SWTDocumentEditable) selectedCPaiement.getValue()).getIdCPaiement());
	//			try {
	//				if(LgrMess.isDOSSIER_EN_RESEAU())actRefresh();
	//			} catch (Exception e) {
	//				logger.error("",e);
	//			}			
	//			this.swtOldCPaiement=SWTDocumentEditable.copy((SWTDocumentEditable)selectedCPaiement.getValue());
	//		}
	//	}
	@Override
	protected void actSupprimer() throws Exception {
		EntityTransaction transaction = dao.getEntityManager().getTransaction();
		try {
			VerrouInterface.setVerrouille(true);
			if(isUtilise())MessageDialog.openInformation(vue.getShell(), MessagesEcran
					.getString("Message.Attention"), MessagesEcran
					.getString("Message.suppression"));
			else		
				if (MessageDialog.openConfirm(vue.getShell(), MessagesEcran
						.getString("Message.Attention"), MessagesEcran
						.getString("DocumentEditable.Message.Supprimer"))) {



					dao.begin(transaction);
					TaDocumentEditable u = dao.findById(((SWTDocumentEditable) selectedDocumentEditable.getValue()).getIdDocumentDoc());

					if(u.getTaTDoc()!=null && u.getTaTDoc().size()>1) {
						//on supprime une relation
						String codeTypeDocSelection = treeViewer.getViewer().getTree().getSelection()[0].getParentItem().getText(0);
						if(codeTypeDocSelection!=null && !codeTypeDocSelection.equals("")) {
							boolean trouve = false;
							TaTDoc taTDocASupprimer = null;
							for (TaTDoc taTDoc : u.getTaTDoc()) {
								if(taTDoc.getCodeTDoc().equals(codeTypeDocSelection)) {
									taTDocASupprimer = taTDoc;
									trouve = true;
								}
							} 
							if(trouve) {
								u.removeTaTDoc(taTDocASupprimer);
								taDocumentEditable = dao.enregistrerMerge(taDocumentEditable);
							}
						}
					} else {
						//pas de relation (erreur) ou dernière relation, on supprime tout
						dao.supprimer(u);
					}
					dao.commit(transaction);

					modelDocumentEditable.removeEntity(taDocumentEditable);

					dao.getModeObjet().setMode(ModeObjet.EnumModeObjet.C_MO_CONSULTATION);
					actRefresh(); //ajouter pour tester jpa
				}
		} catch (Exception e1) {
			vue.getLaMessage().setText(e1.getMessage());
			logger.error("Erreur : actionPerformed", e1);
		} finally {
			if(transaction!=null && transaction.isActive()) {
				transaction.rollback();
			}
			initEtatBouton();
			VerrouInterface.setVerrouille(false);
		}

	}

	@Override
	protected void actFermer() throws Exception {
		// (controles de sortie et fermeture de la fenetre) => onClose()
		if(onClose())
			closeWorkbenchPart();
	}

	@Override
	protected void actAnnuler() throws Exception {
		// // verifier si l'objet est en modification ou en consultation
		// // si modification ou insertion, alors demander si annulation des
		// // modifications si ok, alors annulation si pas ok, alors arreter le
		// processus d'annulation
		// // si consultation declencher l'action "fermer".
		try {
			VerrouInterface.setVerrouille(true);
			// //InputVerifier inputVerifier =
			// getFocusCourant().getInputVerifier();
			// //getFocusCourant().setInputVerifier(null);
			//switch (ibTaTable.getFModeObjet().getMode()) {
			switch (dao.getModeObjet().getMode()) {
			case C_MO_INSERTION:
				if (silencieu || (!silencieu && MessageDialog.openQuestion(vue.getShell(), MessagesEcran
						.getString("Message.Attention"), MessagesEcran
						.getString("DocumentEditable.Message.Annuler")))) {
					remetTousLesChampsApresAnnulationSWT(dbc);
					if(((SWTDocumentEditable) selectedDocumentEditable.getValue()).getIdDocumentDoc()==null){
						modelDocumentEditable.getListeObjet().remove(((SWTDocumentEditable) selectedDocumentEditable.getValue()));
//						writableList = new WritableList(realm, modelDocumentEditable.getListeObjet(), classModel);
//						treeViewer.setInput(writableList);
						
						List<SWTDocumentEditable> list = modelDocumentEditable.remplirListe();
						treeViewer.setInput(refreshTreeModel(list));
						
						treeViewer.getViewer().refresh();
						treeViewer.selectionGrille(0);
					}
					//ibTaTable.annuler();
					dao.annuler(taDocumentEditable);
					hideDecoratedFields();
				}
				initEtatBouton();
				break;
			case C_MO_EDITION:
				if (silencieu || (!silencieu && MessageDialog.openQuestion(vue.getShell(), MessagesEcran
						.getString("Message.Attention"), MessagesEcran
						.getString("DocumentEditable.Message.Annuler")))) {
					//int rang = getExpandBar().getSelectionIndex();
					int rang = modelDocumentEditable.getListeObjet().indexOf(selectedDocumentEditable.getValue());
					// selectedCPaiement.setValue(swtOldCPaiement);
					remetTousLesChampsApresAnnulationSWT(dbc);
					modelDocumentEditable.getListeObjet().set(rang, swtOldDocumentEditable);
//					writableList = new WritableList(realm, modelDocumentEditable.getListeObjet(), classModel);
//					treeViewer.setInput(writableList);
		
					List<SWTDocumentEditable> list = modelDocumentEditable.remplirListe();
					treeViewer.setInput(refreshTreeModel(list));
					
					treeViewer.getViewer().refresh();
					treeViewer.getViewer().setSelection(new StructuredSelection(swtOldDocumentEditable), true);
					//ibTaTable.annuler();
					dao.annuler(taDocumentEditable);
					hideDecoratedFields();
				}
				initEtatBouton();

				break;
			case C_MO_CONSULTATION:
				//actionFermer.run();
				actFermer();
				break;
			default:
				break;
			}
			// getFocusCourant().setInputVerifier(inputVerifier);
			// initEtatBouton();
		} finally {
			VerrouInterface.setVerrouille(false);
		}
	}

	@Override
	protected void actImprimer() throws Exception {

		//		TaInfoEntrepriseDAO taInfoEntrepriseDAO = new TaInfoEntrepriseDAO(getEm());
		//		TaInfoEntreprise taInfoEntreprise = taInfoEntrepriseDAO.findInstance();
		//
		//		String nomChampIdTable =  dao.getChampIdTable();
		//
		//		FonctionGetInfosXmlAndProperties fonctionGetInfosXmlAndProperties = new FonctionGetInfosXmlAndProperties(mapperUIToModel);
		//		fonctionGetInfosXmlAndProperties.cleanValueMapAttributeTable();
		//
		//		fonctionGetInfosXmlAndProperties.setValueMapAttributeTable(TaDocumentEditable.class.getSimpleName()+".head");
		//		fonctionGetInfosXmlAndProperties.setValueMapAttributeTable(TaDocumentEditable.class.getSimpleName()+".detail");
		//		
		//		LinkedHashMap<String,AttributElementResport> mapAttributeTablHead = fonctionGetInfosXmlAndProperties.getMapAttributeTablHead(); 
		//		LinkedHashMap<String,AttributElementResport> mapAttributeTablDetail = fonctionGetInfosXmlAndProperties.getMapAttributeTablDetail();
		//		
		//		Collection<TaDocumentEditable> collectionTaDocumentEditable = modelCPaiement.getListeEntity();
		//		
		//		fonctionGetInfosXmlAndProperties.findInfosFileXml(TaDocumentEditable.class.getName(),SWTDocumentEditable.class.getName(),
		//				listeChamp,"mapping");
		//		fonctionGetInfosXmlAndProperties.getInfosObjetJPA(taCPaiement);
		//
		//		ConstEdition constEdition = new ConstEdition(); 
		////		Impression impression = new Impression(constEdition,taCPaiement,collectionTaDocumentEditable,nomChampIdTable,taCPaiement.getIdCPaiement());
		//		String nomDossier = null;
		//
		//		int nombreLine = collectionTaDocumentEditable.size();
		//
		//		if(nombreLine==0){
		//			MessageDialog.openWarning(vue.getShell(), ConstEdition.TITRE_MESSAGE_EDITION_VIDE,
		//					ConstEdition.EDITION_VIDE);
		//		}
		//		else{
		//			if(taInfoEntreprise.getIdInfoEntreprise()==0){
		//				nomDossier = ConstEdition.INFOS_VIDE;
		//			}
		//			else{
		//				nomDossier = taInfoEntreprise.getNomInfoEntreprise();	
		//			}
		//
		//			constEdition.addValueList(tableViewer, nomClassController);
		//
		//			/**
		//			 * pathFileReport ==> le path de ficher de edition dynamique
		//			 */
		//			String folderEditionDynamique = Const.C_RCP_INSTANCE_LOCATION+"/"+Const.C_NOM_PROJET_TMP+"/"+TaDocumentEditable.class.getSimpleName();
		//			constEdition.makeFolderEditions(folderEditionDynamique);
		//			Path pathFileReport = new Path(folderEditionDynamique+"/"+Const.C_NOM_VU_COND_PAIE+".rptdesign");
		//			final String pathFileReportDynamic = pathFileReport.toPortableString();
		//
		//			MakeDynamiqueReport DynamiqueReport = new MakeDynamiqueReport(constEdition.getNameTableEcran(),
		//					constEdition.getNameTableBDD(),pathFileReportDynamic,Const.C_NOM_VU_COND_PAIE,
		//					ConstEdition.PAGE_ORIENTATION_LANDSCAPE,nomDossier); 
		//			
		//			/**************************************************************/
		//			DynamiqueReport.setFonctionGetInfosXml(fonctionGetInfosXmlAndProperties);
		//			DynamiqueReport.setNomObjet(TaTTel.class.getSimpleName());
		//			/**************************************************************/
		//
		//			Map<String, AttributElementResport> attribuGridHeader = new LinkedHashMap<String, AttributElementResport>();
		//			String nameHeaderTitle = ConstEditionTiers.TITLE_EDITION_COND_PAIE;
		//			attribuGridHeader.put(nameHeaderTitle, new AttributElementResport("",ConstEdition.TEXT_ALIGN_CENTER,
		//					ConstEdition.FONT_SIZE_XX_LARGE,ConstEdition.FONT_WEIGHT_BOLD,"",
		//					ConstEdition.COLUMN_DATA_TYPE_STRING,ConstEdition.UNITS_VIDE,""));
		//			String nameSousHeaderTitle = ConstEditionTiers.SOUS_TITLE_EDITION_COND_PAIE;
		//			attribuGridHeader.put(nameSousHeaderTitle, new AttributElementResport("",ConstEdition.TEXT_ALIGN_CENTER,
		//					ConstEdition.FONT_SIZE_X_LARGE,ConstEdition.FONT_WEIGHT_BOLD,"",
		//					ConstEdition.COLUMN_DATA_TYPE_STRING,ConstEdition.UNITS_VIDE,ConstEdition.COLOR_GRAY));
		//
		//			//DynamiqueReport.buildDesignConfig();
		//			ConstEdition.CONTENT_COMMENTS = ConstEditionTiers.COMMENTAIRE_EDITION_DEFAUT;
		//			DynamiqueReport.setSimpleNameEntity(TaDocumentEditable.class.getSimpleName());
		//			DynamiqueReport.initializeBuildDesignReportConfig();
		//			DynamiqueReport.makePageMater("1", "1", "1", "1", "100");
		//			DynamiqueReport.makeReportHeaderGrid(3,5,100,ConstEdition.UNITS_PERCENTAGE,attribuGridHeader);
		////			DynamiqueReport.makeReportTableDB(100,ConstEdition.UNITS_PERCENTAGE,
		////					Const.C_NOM_VU_T_TEL,attribuTabHeader,attribuTabDetail,1,1,2,5,"30");
		//			DynamiqueReport.biuldTableReport("100", ConstEdition.UNITS_PERCENTAGE, 
		//					Const.C_NOM_VU_COND_PAIE,1,1,2,"40", mapAttributeTablHead, mapAttributeTablDetail);
		//			DynamiqueReport.savsAsDesignHandle();
		//			
		////			impression.imprimer(true,pathFileReportDynamic,null,"Condition Paiement",TaDocumentEditable.class.getSimpleName());
		//
		//			/** 01/03/2010 modifier les editions (zhaolin) **/
		//			BaseImpressionEdition impressionEdition = new BaseImpressionEdition(constEdition,taCPaiement,
		//					getEm(),collectionTaDocumentEditable,taCPaiement.getIdCPaiement());
		//			impressionEdition.impressionEditionTypeEntity(pathFileReportDynamic,"Condition Paiement");
		//			
		//		}
	}

	@Override
	protected boolean aideDisponible() {
		boolean result = false;
		//switch ((getThis().ibTaTable.getFModeObjet().getMode())) {
		switch ((getThis().dao.getModeObjet().getMode())) {
		case C_MO_CONSULTATION:
			if(getFocusCourantSWT().equals(vue.getTree()))
				result = true;
			break;
		case C_MO_EDITION:
		case C_MO_INSERTION:
			break;
		default:
			break;
		}
		return result;
	}

	@Override
	protected void actAide() throws Exception {
		actAide(null);
	}

	@Override
	protected void actAide(String message) throws Exception {
		//		if(aideDisponible()){
		//			try {
		//				VerrouInterface.setVerrouille(true);
		//				vue.setCursor(Display.getCurrent().getSystemCursor(SWT.CURSOR_WAIT));
		//				ParamAfficheAideRechercheSWT paramAfficheAideRecherche = new ParamAfficheAideRechercheSWT();
		//				//paramAfficheAideRecherche.setDb(getThis().getIbTaTable().getFIBBase());
		//				paramAfficheAideRecherche.setMessage(message);
		//				// Creation de l'ecran d'aide principal
		//				Shell s = new Shell(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),LgrShellUtil.styleLgr);
		//				PaAideSWT paAide = new PaAideSWT(s, SWT.NONE);
		//				SWTPaAideControllerSWT paAideController = new SWTPaAideControllerSWT(paAide);
		//				/***************************************************************/
		//				LgrPartListener.getLgrPartListener().setLgrActivePart(null);
		//				IEditorPart e = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().openEditor(new EditorInputAide(), EditorAide.ID);
		//				LgrPartListener.getLgrPartListener().setLgrActivePart(e);
		//				LgrPartListener.getLgrPartListener().getLgrNavigation().add(e);
		//				paAideController = new SWTPaAideControllerSWT(((EditorAide)e).getComposite());
		//				((LgrEditorPart)e).setController(paAideController);
		//				((LgrEditorPart)e).setPanel(((EditorAide)e).getComposite());
		//				/***************************************************************/
		//				//SWTBaseControllerSWTStandard controllerEcranCreation = null;
		//				JPABaseControllerSWTStandard controllerEcranCreation = null;
		//				ParamAffiche parametreEcranCreation = null;
		//				IEditorPart editorCreation = null;
		//				String editorCreationId = null;
		//				IEditorInput editorInputCreation = null;
		//				Shell s2 = new Shell(s, LgrShellUtil.styleLgr);
		//
		//				//switch ((getThis().ibTaTable.getFModeObjet().getMode())) {
		//				switch ((getThis().dao.getModeObjet().getMode())) {
		//				case C_MO_CONSULTATION:
		//					if(getFocusCourantSWT().equals(vue.getExpandBar())){
		//						PaConditionPaiementSWT paCPaiementSWT = new PaConditionPaiementSWT(s2,SWT.NULL);
		//						SWTPaDocumentEditableController swtPaCPaiementController = new SWTPaDocumentEditableController(paCPaiementSWT);
		//						
		//						paramAfficheAideRecherche.setForceAffichageAideRemplie(true);
		//						paramAfficheAideRecherche.setAfficheDetail(false);
		//
		//						editorCreationId = EditorConditionPaiement.ID;
		//						editorInputCreation = new EditorInputConditionPaiement();
		//
		//						ParamAfficheDocumentEditable paramAfficheCPaiement = new ParamAfficheDocumentEditable();
		//						paramAfficheAideRecherche.setJPQLQuery(dao.getJPQLQuery());
		//						paramAfficheCPaiement.setModeEcran(EnumModeObjet.C_MO_INSERTION);
		//						paramAfficheCPaiement.setEcranAppelant(paAideController);
		//						controllerEcranCreation = swtPaCPaiementController;
		//						parametreEcranCreation = paramAfficheCPaiement;
		//
		//						paramAfficheAideRecherche.setTypeEntite(TaDocumentEditable.class);
		//						paramAfficheAideRecherche.setChampsRecherche(Const.C_CODE_C_PAIEMENT);
		//						paramAfficheAideRecherche.setDebutRecherche(vue.getTfCODE_C_PAIEMENT().getText());
		//						paramAfficheAideRecherche.setControllerAppelant(getThis());
		//						paramAfficheAideRecherche.setModel(swtPaCPaiementController.getModelCPaiement());
		//						paramAfficheAideRecherche.setTypeObjet(swtPaCPaiementController.getClassModel());
		//						paramAfficheAideRecherche.setChampsIdentifiant(Const.C_ID_C_PAIEMENT);
		//					}
		//					break;
		//				case C_MO_EDITION:
		//				case C_MO_INSERTION:
		//					break;
		//				default:
		//					break;
		//				}
		//
		//				if (paramAfficheAideRecherche.getJPQLQuery() != null) {
		//
		//					PaAideRechercheSWT paAideRecherche1 = new PaAideRechercheSWT(s,SWT.NULL);
		//					SWTPaAideRechercheControllerSWT paAideRechercheController1 = new SWTPaAideRechercheControllerSWT(
		//							paAideRecherche1);
		//
		//					// Parametrage de la recherche
		//					paramAfficheAideRecherche.setFocusSWT(((PaAideRechercheSWT) paAideRechercheController1.getVue()).getTfChoix());
		//					paramAfficheAideRecherche.setRefCreationSWT(controllerEcranCreation);
		//					paramAfficheAideRecherche.setEditorCreation(editorCreation);
		//					paramAfficheAideRecherche.setEditorCreationId(editorCreationId);
		//					paramAfficheAideRecherche.setEditorInputCreation(editorInputCreation);
		//					paramAfficheAideRecherche.setParamEcranCreation(parametreEcranCreation);
		//					paramAfficheAideRecherche.setShellCreation(s2);
		//					paAideRechercheController1.configPanel(paramAfficheAideRecherche);
		//					// paramAfficheAideRecherche.setFocusDefaut(paAideRechercheController1.getVue().getTfChoix());
		//
		//					// Ajout d'une recherche
		//					paAideController.addRecherche(paAideRechercheController1,
		//							paramAfficheAideRecherche.getTitreRecherche());
		//
		//					// Parametrage de l'ecran d'aide principal
		//					ParamAfficheAideSWT paramAfficheAideSWT = new ParamAfficheAideSWT();
		//					ParamAfficheAide paramAfficheAide = new ParamAfficheAide();
		//
		//					// enregistrement pour le retour de l'ecran d'aide
		//					paAideController.addRetourEcranListener(getThis());
		//					Control focus = vue.getShell().getDisplay().getFocusControl();
		//					// affichage de l'ecran d'aide principal (+ ses recherches)
		//
		//					dbc.getValidationStatusMap().removeMapChangeListener(
		//							changeListener);
		//					//LgrShellUtil.afficheAideSWT(paramAfficheAide, null, paAide,paAideController, s);
		//					/*****************************************************************/
		//					paAideController.configPanel(paramAfficheAide);
		//					/*****************************************************************/
		//					dbc.getValidationStatusMap().addMapChangeListener(
		//							changeListener);
		//
		//				}
		//
		//			} finally {
		//				VerrouInterface.setVerrouille(false);
		//				vue.setCursor(Display.getCurrent().getSystemCursor(SWT.CURSOR_ARROW));
		//			}
		//		}
	}

	public IStatus validateUI() {
		return null;
	}

	public IStatus validateUIField(String nomChamp,Object value) {
		String validationContext = "DOCUMENT_DOC";
		try {
			IStatus s = null;
			boolean verrouilleModifCode = false;
			int change=0;

			if(nomChamp.equals(Const.C_DEFAUT_DOCUMENT_DOC)) { //traitement des booleens
				if((Boolean)value==true) value=new Integer(1); else value=new Integer(0);
			}

			TaDocumentEditable u = new TaDocumentEditable();
			PropertyUtils.setSimpleProperty(u, nomChamp, value);
			if(((SWTDocumentEditable) selectedDocumentEditable.getValue()).getIdDocumentDoc()!=null) {
				u.setIdDocumentDoc(((SWTDocumentEditable) selectedDocumentEditable.getValue()).getIdDocumentDoc());
			}
			if(nomChamp.equals(Const.C_DEFAUT_DOCUMENT_DOC)){
				Integer retour=0;
				if(value!=null)
					if(value instanceof Boolean)retour=LibConversion.booleanToInt((Boolean) value);
				PropertyUtils.setSimpleProperty(u, nomChamp, retour);
			}

			s = dao.validate(u,nomChamp,validationContext,verrouilleModifCode);
			if(s.getSeverity()!=IStatus.ERROR && change!=0) {
				if(nomChamp.equals(Const.C_DEFAUT_DOCUMENT_DOC)){
					taDocumentEditable.setDefaut(u.getDefaut());
				}
			}
			return s;
		} catch (IllegalAccessException e) {
			logger.error("",e);
		} catch (InvocationTargetException e) {
			logger.error("",e);
		} catch (NoSuchMethodException e) {
			logger.error("",e);
		}
		return null;
	}
	@Override
	protected void actEnregistrer() throws Exception {
		EntityTransaction transaction = dao.getEntityManager().getTransaction();
		boolean autreActionEnCour = false;
		try {
			ctrlUnChampsSWT(getFocusCourantSWT());
			ctrlTousLesChampsAvantEnregistrementSWT();
			dao.begin(transaction);

			if ((dao.getModeObjet().getMode().compareTo(ModeObjet.EnumModeObjet.C_MO_EDITION) == 0)||
					(dao.getModeObjet().getMode().compareTo(ModeObjet.EnumModeObjet.C_MO_INSERTION) == 0)) {
				//LgrDozerMapper<SWTDocumentEditable,TaDocumentEditable> mapper = new LgrDozerMapper<SWTDocumentEditable,TaDocumentEditable>();
				//mapper.map((SWTDocumentEditable) selectedDocumentEditable.getValue(),taDocumentEditable);
				MapperDocumentEditable mapper = new MapperDocumentEditable();
				mapper.setEm(getEm());
				mapper.dtoToEntity((SWTDocumentEditable) selectedDocumentEditable.getValue(),taDocumentEditable);

				//				if(((SWTDocumentEditable) selectedDocumentEditable.getValue())!=null 
				//						&& ((SWTDocumentEditable) selectedDocumentEditable.getValue()).getDefaut()!=null
				//						&& ((SWTDocumentEditable) selectedDocumentEditable.getValue()).getDefaut())
				//					//taDocumentEditable.getTaTCPaiement().setTaDocumentEditable(taDocumentEditable);	
				
				if(taDocumentEditable.getTaTDoc() == null
						|| taDocumentEditable.getTaTDoc().isEmpty() ) {
					if(dao.getModeObjet().getMode().compareTo(ModeObjet.EnumModeObjet.C_MO_INSERTION)==0) {
						MessageDialog.openInformation(vue.getShell(), MessagesEcran
								.getString("Message.Attention"), 
								"Pour enregistrer, il faut au moins sélectionner un type de document");
					}
					else { //mode edition
						actSupprimer();
					}
					autreActionEnCour = true;
				} else {

					taDocumentEditable = dao.enregistrerMerge(taDocumentEditable);
					//if(dao.getModeObjet().getMode().compareTo(ModeObjet.EnumModeObjet.C_MO_INSERTION) == 0)
					//					modelCPaiement.getListeEntity().add(taCPaiement);
				}
			} 
			if(!autreActionEnCour) {
				dao.commit(transaction);
				modelDocumentEditable.addEntity(taDocumentEditable);

				transaction = null;
				changementDeSelection();
				actRefresh();
			}
		} finally {
			if(!autreActionEnCour) {
				if(transaction!=null && transaction.isActive()) {
					transaction.rollback();
				}
				initEtatBouton();
			}
		}
	}

	public void initEtatComposant() {
		try {
			vue.getTfCode().setEditable(!isUtilise());
			changeCouleur(vue);
		} catch (Exception e) {
			vue.getLaMessage().setText(e.getMessage());
		}
	}

	public SWTDocumentEditable getSwtOldDocumentEditable() {
		return swtOldDocumentEditable;
	}

	public void setSwtOldDocumentEditable(SWTDocumentEditable swtOldCPaiement) {
		this.swtOldDocumentEditable = swtOldCPaiement;
	}

	public void setSwtOldDocumentEditable() {
		if (selectedDocumentEditable!=null && selectedDocumentEditable.getValue() != null)
			this.swtOldDocumentEditable = SWTDocumentEditable.copy((SWTDocumentEditable) selectedDocumentEditable.getValue());
		else {
			if (treeViewer.selectionGrille(0)){
				this.swtOldDocumentEditable = SWTDocumentEditable.copy((SWTDocumentEditable) selectedDocumentEditable.getValue());
				treeViewer.getViewer().setSelection(new StructuredSelection(
						(SWTDocumentEditable) selectedDocumentEditable.getValue()), true);
			}
		}
	}

	public void setVue(PaDocumentEditable vue) {
		super.setVue(vue);
		this.vue = vue;
	}

	@Override
	protected void initMapComposantDecoratedField() {
		if (mapComposantDecoratedField == null)
			mapComposantDecoratedField = new LinkedHashMap<Control, DecoratedField>();
		mapComposantDecoratedField.put(vue.getTfCode(), vue
				.getFieldCODE_C_PAIEMENT());
		mapComposantDecoratedField.put(vue.getTfCheminCorresp(), vue
				.getFieldFIN_MOIS_C_PAIEMENT());
		mapComposantDecoratedField.put(vue.getTfLibelle(), vue
				.getFieldLIB_C_PAIEMENT());
		mapComposantDecoratedField.put(vue.getTfCheminDocument(), vue
				.getFieldREPORT_C_PAIEMENT());
	}

	public Class getClassModel() {
		return classModel;
	}

	@Override
	protected void sortieChamps() {
		// TODO Raccord de methode auto-genere

	}

	@Override
	protected void actRefresh() throws Exception {

		TaDocumentEditable u = taDocumentEditable;
		if (u!=null && u.getIdDocumentDoc()==0)
			u=dao.findById(u.getIdDocumentDoc());
		//writableList = new WritableList(realm, modelDocumentEditable.remplirListe(), classModel);
		//treeViewer.setInput(writableList);

		TreePath[] expandedTreePaths = treeViewer.getViewer().getExpandedTreePaths();

		treeViewer.setInput(refreshTreeModel());

		treeViewer.getViewer().setExpandedTreePaths(expandedTreePaths);

		treeViewer.selectionGrille(
				treeViewer.selectionGrille(selectedDocumentEditable.getValue()));

		if(u != null) {
			Iterator<SWTDocumentEditable> ite = modelDocumentEditable.getListeObjet().iterator();
			SWTDocumentEditable tmp = null;
			int i = 0;
			boolean trouve = false;
			while (ite.hasNext() && !trouve) {
				tmp = ite.next();
				if(tmp.getIdDocumentDoc()==u.getIdDocumentDoc()) {
					treeViewer.getViewer().setSelection(new StructuredSelection(tmp));
					trouve = true;
				}
				i++;
			}
		}
	}

	public ModelGeneralObjet<SWTDocumentEditable,TaDocumentEditableDAO,TaDocumentEditable> getModelDocumentEditable() {
		return modelDocumentEditable;
	}

	public boolean isUtilise(){
		return (((SWTDocumentEditable)selectedDocumentEditable.getValue()).getIdDocumentDoc()!=null &&
				!dao.recordModifiable(dao.getNomTable(),
						((SWTDocumentEditable)selectedDocumentEditable.getValue()).getIdDocumentDoc()))||
						!dao.autoriseModification(taDocumentEditable);		
	}

	public TaDocumentEditableDAO getDao() {
		return dao;
	}

}
