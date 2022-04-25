package fr.legrain.creationDocMultipleTiers.controllers;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.log4j.Logger;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
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
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.databinding.swt.SWTObservables;
import org.eclipse.jface.databinding.viewers.ViewersObservables;
import org.eclipse.jface.viewers.CheckStateChangedEvent;
import org.eclipse.jface.viewers.CheckboxTreeViewer;
import org.eclipse.jface.viewers.ICheckStateListener;
import org.eclipse.jface.viewers.ICheckStateProvider;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreePath;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Item;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.swt.widgets.Widget;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.PlatformUI;

import creationdocmultipletiers.Activator;
import fr.legrain.creationDocMultipleTiers.divers.DeclencheInitEtatBoutonControllerEvent;
import fr.legrain.creationDocMultipleTiers.divers.IDeclencheInitEtatBoutonControllerListener;
import fr.legrain.creationDocMultipleTiers.divers.ParamAffichecreationDocMultiple;
import fr.legrain.creationDocMultipleTiers.divers.ViewerSupportCreationDocLigne;
import fr.legrain.creationDocMultipleTiers.ecrans.PaSelectionLigneDocTree;
import fr.legrain.document.divers.ModelCreationDoc;
import fr.legrain.document.divers.ModelRecupDocumentCreationDoc;
import fr.legrain.document.divers.ModelTiersCreationDoc;
import fr.legrain.document.divers.TaCreationDoc;
import fr.legrain.document.divers.TypeDoc;
import fr.legrain.documents.dao.TaBonliv;
import fr.legrain.documents.dao.TaBonlivDAO;
import fr.legrain.gestCom.Appli.Const;
import fr.legrain.gestCom.Module_Document.IDocumentTiers;
import fr.legrain.gestCom.Module_Document.IHMEnteteDocument;
import fr.legrain.gestCom.Module_Document.SWTCreationDocLigne;
import fr.legrain.gestCom.Module_Tiers.SWTTiers;
import fr.legrain.gestCom.gestComBd.gestComBdPlugin;
import fr.legrain.gestCom.librairiesEcran.LibrairiesEcranPlugin;
import fr.legrain.gestCom.librairiesEcran.editor.EditorAide;
import fr.legrain.gestCom.librairiesEcran.editor.EditorInputAide;
import fr.legrain.gestCom.librairiesEcran.swt.JPABaseControllerSWTStandard;
import fr.legrain.gestCom.librairiesEcran.swt.LgrAbstractHandler;
import fr.legrain.gestCom.librairiesEcran.swt.LgrShellUtil;
import fr.legrain.gestCom.librairiesEcran.swt.ParamAfficheAideRechercheSWT;
import fr.legrain.gestCom.librairiesEcran.swt.ParamAfficheAideSWT;
import fr.legrain.gestCom.librairiesEcran.swt.SWTPaAideControllerSWT;
import fr.legrain.gestCom.librairiesEcran.swt.SWTPaAideRechercheControllerSWT;
import fr.legrain.gestCom.librairiesEcran.workbench.AnnuleToutEvent;
import fr.legrain.gestCom.librairiesEcran.workbench.LgrEditorPart;
import fr.legrain.gestCom.librairiesEcran.workbench.LgrPartListener;
import fr.legrain.lib.data.ExceptLgr;
import fr.legrain.lib.data.InfosVerifSaisie;
import fr.legrain.lib.data.ModeObjet;
import fr.legrain.lib.data.VerrouInterface;
import fr.legrain.lib.gui.ParamAffiche;
import fr.legrain.lib.gui.ResultAffiche;
import fr.legrain.lib.gui.RetourEcranEvent;
import fr.legrain.lib.gui.RetourEcranListener;
import fr.legrain.lib.gui.aide.PaAideRechercheSWT;
import fr.legrain.lib.gui.aide.PaAideSWT;
import fr.legrain.lib.gui.aide.ParamAfficheAide;
import fr.legrain.lib.gui.grille.LgrTreeViewer;
import fr.legrain.libMessageLGR.LgrMess;
import fr.legrain.tiers.ecran.ParamAfficheTiers;
import fr.legrain.tiers.editor.TiersMultiPageEditor;

public class PaSelectionLigneDocControlleur extends
		JPABaseControllerSWTStandard implements RetourEcranListener{
	static Logger logger = Logger.getLogger(PaSelectionLigneDocControlleur.class.getName());	
	static String LIBELLE_GRILLE="Liste des documents à prendre en compte dans le(s) document(s) à créer";
	
	private PaSelectionLigneDocTree  vue = null;
	private TaBonlivDAO dao = null;
	private TaBonliv taBonLiv=null;
	private IObservableValue selectedDocument;
	private ModelRecupDocumentCreationDoc masterModelDocument = null;
	private ModelTiersCreationDoc masterModelTiers = null;
	private ModelCreationDoc modelCreation = null;
	private String selectedTypeCreation = "";
	private String selectedTypeSelection = "";
	
	private Object ecranAppelant = null;
	private Realm realm;
	private DataBindingContext dbc;
	private MapChangeListener changeListener = new MapChangeListener();
	private Class classModel = IHMEnteteDocument.class;
	private String nomClass = this.getClass().getSimpleName();
	private String finDeLigne = "\r\n";
	private String separateur = ";";
	protected Boolean enregistreDirect=false; 
	private Boolean SelectionTTC=null;
	private LgrTreeViewer treeViewer;
	
	
	public static final String C_COMMAND_DOCUMENT_TOUT_COCHER_ID = "fr.legrain.Publipostage.toutCocher";
	protected HandlerToutCocher handlerToutCocher = new HandlerToutCocher();
	
	public static final String C_COMMAND_DOCUMENT_TOUT_DECOCHER_ID = "fr.legrain.Publipostage.toutDeCocher";
	protected HandlerToutDeCocher handlerToutDeCocher = new HandlerToutDeCocher();
	
	public PaSelectionLigneDocControlleur(PaSelectionLigneDocTree vue,EntityManager em) {
		if(em!=null) {
			setEm(em);
		}
		dao =new TaBonlivDAO(getEm());
		modelCreation=new ModelCreationDoc();
//		modelFacture = new ModelGeneralObjet<IHMLRelance,TaLRelanceDAO,TaLRelance>
//		(dao,IHMLRelance.class);
		setVue(vue);

		vue.getShell().addShellListener(this);

		//Branchement action annuler : empeche la fermeture automatique de la fenetre sur ESC
		vue.getShell().addTraverseListener(new Traverse());

		initController();
		initSashFormWeight();
	}

	public PaSelectionLigneDocControlleur(PaSelectionLigneDocTree vue) {
		this(vue,null);
	}

	
	private void initController()	{
		try {	
			
			setDaoStandard(dao);

			initVue();			
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
			Menu[] tabPopups = new Menu[] {
					popupMenuFormulaire, popupMenuGrille };
			this.initPopupAndButtons(mapActions, tabPopups);
			vue.getParent().setMenu(popupMenuFormulaire);
			initEtatBouton(true);
			initSashFormWeight();
			
		} catch (Exception e) {
			vue.getLaMessage().setText(e.getMessage());
			logger.error("Erreur : PaTiersController", e);
		}
	}

	protected void initSashFormWeight() {
		int premierComposite = 30;
		int secondComposite = 70;
		vue.getCompositeForm().setWeights(new int[]{premierComposite,secondComposite});
	}
	protected void initVerifySaisie() {
		if (mapInfosVerifSaisie == null)
			mapInfosVerifSaisie = new HashMap<Control, InfosVerifSaisie>();
		
//		mapInfosVerifSaisie.put(vue.getTfTiers(), new InfosVerifSaisie(new TaTiers(),Const.C_CODE_TIERS,null));
//		mapInfosVerifSaisie.put(paFormulaireReglement.getTfCODE_DOCUMENT(), new InfosVerifSaisie(new TaFacture(),Const.C_CODE_DOCUMENT,null));
//		mapInfosVerifSaisie.put(paFormulaireReglement.getTfCODE_REGLEMENT(), new InfosVerifSaisie(new TaReglement(),Const.C_CODE_REGLEMENT,null));
//		mapInfosVerifSaisie.put(paFormulaireReglement.getTfCPT_COMPTABLE(), new InfosVerifSaisie(new TaCompteBanque(),Const.C_CPTCOMPTABLE,null));
//		mapInfosVerifSaisie.put(paFormulaireReglement.getTfLIBELLE_PAIEMENT(), new InfosVerifSaisie(new TaReglement(),Const.C_LIBELLE_PAIEMENT,null));
//		mapInfosVerifSaisie.put(paFormulaireReglement.getTfMONTANT_AFFECTE(), new InfosVerifSaisie(new TaRReglement(),Const.C_MONTANT_AFFECTE,null));
//		mapInfosVerifSaisie.put(paFormulaireReglement.getTfTYPE_PAIEMENT(), new InfosVerifSaisie(new TaTPaiement(),Const.C_CODE_T_PAIEMENT,null));
	
		
		initVerifyListener(mapInfosVerifSaisie, dao);
	}
	private void initVue() {
	}
	@Override
	protected void actAide() throws Exception {
		// TODO Auto-generated method stub
		actAide(null);
	}

	@Override
	protected boolean aideDisponible() {
		boolean result = false;
		if (getFocusCourantSWT().equals(vue.getTree()))
			result = true;
		return result;
	}
	@Override
	protected void actAide(String message) throws Exception {
		if (aideDisponible()) {
//			boolean affichageAideRemplie = DocumentPlugin.getDefault().getPreferenceStore().getBoolean(fr.legrain.document.preferences.PreferenceConstants.TYPE_AFFICHAGE_AIDE);
			boolean affichageAideRemplie = LgrMess.isAfficheAideRemplie();
			setActiveAide(true);
			boolean verrouLocal = VerrouInterface.isVerrouille();
			VerrouInterface.setVerrouille(true);
			try {
				vue.setCursor(Display.getCurrent().getSystemCursor(SWT.CURSOR_WAIT));
				ParamAfficheAideRechercheSWT paramAfficheAideRecherche = new ParamAfficheAideRechercheSWT();
//				paramAfficheAideRecherche.setDb(getThis().getIbTaTable().getFIBBase());
				paramAfficheAideRecherche.setMessage(message);
				// Creation de l'ecran d'aide principal
				Shell s = new Shell(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),LgrShellUtil.styleLgr);
				PaAideSWT paAide = new PaAideSWT(s, SWT.NONE);
				SWTPaAideControllerSWT paAideController = new SWTPaAideControllerSWT(paAide);
				/** ************************************************************ */
				LgrPartListener.getLgrPartListener().setLgrActivePart(null);
				IEditorPart e = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().openEditor(new EditorInputAide(), EditorAide.ID);
				LgrPartListener.getLgrPartListener().setLgrActivePart(e);
				LgrPartListener.getLgrPartListener().getLgrNavigation().add(e);
				paAideController = new SWTPaAideControllerSWT(((EditorAide) e).getComposite());
				((LgrEditorPart) e).setController(paAideController);
				((LgrEditorPart) e).setPanel(((EditorAide) e).getComposite());
				/** ************************************************************ */
				JPABaseControllerSWTStandard controllerEcranCreation = null;
				ParamAffiche parametreEcranCreation = null;
				IEditorPart editorCreation = null;
				String editorCreationId = null;
				IEditorInput editorInputCreation = null;
				Shell s2 = new Shell(s, LgrShellUtil.styleLgr);
					
				if (paramAfficheAideRecherche.getJPQLQuery() != null) {

					PaAideRechercheSWT paAideRecherche1 = new PaAideRechercheSWT(s, SWT.NULL);
					SWTPaAideRechercheControllerSWT paAideRechercheController1 = new SWTPaAideRechercheControllerSWT(paAideRecherche1);

					// Parametrage de la recherche
					paramAfficheAideRecherche.setFocusSWT(((PaAideRechercheSWT) paAideRechercheController1.getVue()).getTfChoix());
					paramAfficheAideRecherche.setRefCreationSWT(controllerEcranCreation);
					paramAfficheAideRecherche.setEditorCreation(editorCreation);
					paramAfficheAideRecherche.setEditorCreationId(editorCreationId);
					paramAfficheAideRecherche.setEditorInputCreation(editorInputCreation);
					paramAfficheAideRecherche.setParamEcranCreation(parametreEcranCreation);
					paramAfficheAideRecherche.setShellCreation(s2);
					paAideRechercheController1.configPanel(paramAfficheAideRecherche);
					// paramAfficheAideRecherche.setFocusDefaut(paAideRechercheController1.getVue().getTfChoix());

					// Ajout d'une recherche
					paAideController.addRecherche(paAideRechercheController1,paramAfficheAideRecherche.getTitreRecherche());

					// Parametrage de l'ecran d'aide principal
					ParamAfficheAideSWT paramAfficheAideSWT = new ParamAfficheAideSWT();
					ParamAfficheAide paramAfficheAide = new ParamAfficheAide();

					// enregistrement pour le retour de l'ecran d'aide
					paAideController.addRetourEcranListener(this);
					Control focus = vue.getShell().getDisplay().getFocusControl();
					// affichage de l'ecran d'aide principal (+ ses recherches)


				}

			} finally {
				VerrouInterface.setVerrouille(false);
				vue.setCursor(Display.getCurrent().getSystemCursor(SWT.CURSOR_ARROW));
			}
		}
	}



	@Override
	protected void actAnnuler() throws Exception {
		// TODO Auto-generated method stub
		List<SWTCreationDocLigne>list=new LinkedList<SWTCreationDocLigne>();
		for (Object obj : modelCreation.getListeObjet()) {
			list.clear();
			((SWTCreationDocLigne)obj).setAccepte(!((SWTCreationDocLigne)obj).getAccepte());
			list=getChildren((SWTCreationDocLigne)obj,list);
			if(list!=null)
				for (SWTCreationDocLigne e : list) {
					e.setAccepte(!e.getAccepte());
					treeViewer.getViewer().refresh(e);
				}
		}
		treeViewer.getViewer().refresh();
	}

	@Override
	protected void actEnregistrer() throws Exception {
		try {
			List<SWTCreationDocLigne> liste = new LinkedList<SWTCreationDocLigne>();
			for (SWTCreationDocLigne elem : modelCreation.getListeObjet()) {
				liste=getChildren(elem, liste);
				for (SWTCreationDocLigne e : liste) {
					if(e.getCodeDocumentRecup()!=null ){
						rechercheEntite(e).setAccepte(e.getAccepte());
					}
				}
			}
		} catch (Exception e) {
			logger.error("", e);
		}
	}

	@Override
	protected void actFermer() throws Exception {
		List<TaCreationDoc>listeTemp=new LinkedList<TaCreationDoc>();
		List<IDocumentTiers>list=new LinkedList<IDocumentTiers>();
		
		actEnregistrer();//pour mettre à jour le setAccepte() des entites
		//traitement des parents
		for (Object obj : modelCreation.getListeEntity()) {
			if(!((TaCreationDoc)obj).getAccepte())listeTemp.add((TaCreationDoc)obj);
		}
		modelCreation.getListeEntity().removeAll(listeTemp);
		listeTemp.clear();
		//traitement des childs
		for (Object obj : modelCreation.getListeEntity()) {
			list.clear();
			for (IDocumentTiers doc : ((TaCreationDoc)obj).getListeDoc()) {
				if(!doc.getAccepte())list.add(doc);
			}
			((TaCreationDoc)obj).getListeDoc().removeAll(list);
			if(((TaCreationDoc)obj).getListeDoc().size()==0)listeTemp.add(((TaCreationDoc)obj));
		}
		modelCreation.getListeEntity().removeAll(listeTemp);
		actRefresh();
	}

	@Override
	protected void actImprimer() throws Exception {

	}

	

	
	@Override
	protected void actInserer() throws Exception {

	}

	@Override
	protected void actModifier() throws Exception {

	}

	/**
	 * Creation des objet pour l'interface, a partir de l'entite principale.<br>
	 * Ici : creation d'une liste de ligne pour l'IHM, a partir de la liste des lignes contenue dans l'entite facture.
	 * @return
	 */
	public List<IHMEnteteDocument> IHMmodel() {
		LinkedList<TaBonliv> Ldocuments = new LinkedList<TaBonliv>();
		LinkedList<IHMEnteteDocument> lswt = new LinkedList<IHMEnteteDocument>();
		
		return lswt;
	}
	
	@Override
	protected void actRefresh() throws Exception {
		vue.setCursor(Display.getCurrent().getSystemCursor(SWT.CURSOR_WAIT));
		try{	
			SelectionTTC=null;
			String codeActuel="";

			modelCreation.remplirListe();
			TreePath[] expandedTreePaths = treeViewer.getViewer().getExpandedTreePaths();

//			List list=new LinkedList<SWTCreationDocLigne>();
//			list.add(refreshTreeModel());
//			WritableList writableListFacture = new WritableList(list, SWTCreationDocLigne.class);
			
			
			treeViewer.setInput(refreshTreeModel());

			treeViewer.getViewer().setExpandedTreePaths(expandedTreePaths);

			treeViewer.selectionGrille(
					treeViewer.selectionGrille(selectedDocument.getValue()));
			initEtatBouton(true);
			initialiseTitreGrille();
			fireDeclencheInitEtatBoutonController(new DeclencheInitEtatBoutonControllerEvent(this,null));
		}catch (Exception e) {
			logger.error("",e);
		}
		finally{
			vue.setCursor(Display.getCurrent().getSystemCursor(SWT.CURSOR_ARROW));
		}
	}

	@Override
	protected void actSupprimer() throws Exception {
		// TODO Auto-generated method stub

	}

	public void bind(PaSelectionLigneDocTree paSelectionLigneRelance) {
		try {
			realm = SWTObservables.getRealm(vue.getParent().getDisplay());

			//TreeViewer v = new TreeViewer(paSelectionLigneRelance.getTree());
			treeViewer = new LgrTreeViewer(new CheckboxTreeViewer(paSelectionLigneRelance.getTree()) );
			setTreeViewerStandard(treeViewer);

//"Code tiers",
			String[] titreColonnes =new String[] {"document créé","libelle document créé","document récupéré",
					"libelle ligne","date doc.","net TTC","Accepté"};
			treeViewer.createTableCol(vue.getTree(),titreColonnes,
//					"150",
					new String[] {"150","400","150","150","150","150","0"});
//			"codeTiers",
			String[] listeChamp = new String[] {"codeDocument","libelleDocument","codeDocumentRecup",
					"libelleLigne","dateDocument","netTtc","accepte"};
			treeViewer.setListeChamp(listeChamp);
		
			getTreeViewerStandard().getViewer().addCheckStateListener(new ICheckStateListener() {
				@Override
				public void checkStateChanged(CheckStateChangedEvent event) {
					List<SWTCreationDocLigne>list=new LinkedList<SWTCreationDocLigne>();
						list.clear();
						list=getChildren((SWTCreationDocLigne)event.getElement(),list);
						if(list!=null)
							for (SWTCreationDocLigne e : list) {
								e.setAccepte(event.getChecked());
								treeViewer.getViewer().refresh(e);
							}
						treeViewer.getViewer().setSelection(new StructuredSelection(recherche("id"
								, ((SWTCreationDocLigne)event.getElement()).getId())));
						
						if(selectedDocument.getValue()!=null){
							 ((SWTCreationDocLigne)event.getElement()).setAccepte(event.getChecked());
							((SWTCreationDocLigne)selectedDocument.getValue()).setAccepte(event.getChecked());
							validateUIField(Const.C_ACCEPTE,event.getChecked());
							treeViewer.getViewer().refresh(event.getElement());
						}
						fireDeclencheInitEtatBoutonController(new DeclencheInitEtatBoutonControllerEvent(this, null));
				}
			});	
			getTreeViewerStandard().getViewer().setCheckStateProvider(new ICheckStateProvider() {
				
				@Override
				public boolean isGrayed(Object element) {
					boolean retour=((SWTCreationDocLigne)element).getAccepte();
					if(!retour)
						return true;
					return false;
				}
				
				@Override
				public boolean isChecked(Object element) {
					boolean retour=((SWTCreationDocLigne)element).getAccepte();

					if(retour)
						return true;
					return false;
				}
			});
			
			SWTCreationDocLigne root = refreshTreeModel();

			IListProperty childrenProp = new DelegatingListProperty() {
				IListProperty inputChildren = BeanProperties.list("list", SWTCreationDocLigne.class);
				protected IListProperty doGetDelegate(Object source) {
					if (source instanceof SWTCreationDocLigne)
						return inputChildren;
					return null;
				}
			};

			IValueProperty[] labelProps = BeanProperties.values(SWTCreationDocLigne.class ,listeChamp);
			ViewerSupportCreationDocLigne.bind(treeViewer.getViewer(),
					root,
					childrenProp,
					labelProps
			);
			treeViewer.getViewer().refresh();
			
			vue.getTree().addMouseListener(
					new MouseAdapter() {

						public void mouseDoubleClick(MouseEvent e) {
							if(((SWTCreationDocLigne)selectedDocument.getValue())!=null &&
									((SWTCreationDocLigne)selectedDocument.getValue()).getCodeDocumentRecup()!=null){
								String idEditor = TypeDoc.getInstance().getEditorDoc()
										.get(getSelectedTypeSelection());								
								String valeurIdentifiant = ((SWTCreationDocLigne)selectedDocument.getValue()).getCodeDocumentRecup();
								ouvreDocument(valeurIdentifiant, idEditor);
							}
						}

					});


//			class LgrListener implements ISelectionChangedListener, ISelectionProvider {
//
//				protected ListenerList listenerList = new ListenerList();
//
//				@Override
//				public void selectionChanged(SelectionChangedEvent event) {
//					if(event.getSelection() instanceof TreeSelection) {
//						Object o = ((TreeSelection)event.getSelection()).getFirstElement();
//						if(o instanceof SWTCreationDocLigne) {
//							if(((SWTCreationDocLigne)o).getLibelleDocument()!=null
//									) {
//								fireChangementDeSelection(new SelectionChangedEvent(this, getSelection()));
//							} else {
//							}
//						}
//					}
//				}
//
//				@Override
//				public void setSelection(ISelection selection) {
//					treeViewer.getViewer().setSelection(selection);
//					fireChangementDeSelection(new SelectionChangedEvent(this, getSelection()));
//					initEtatBouton();
//				}
//
//				@Override
//				public void removeSelectionChangedListener(ISelectionChangedListener listener) {
//					listenerList.remove(listener);
//				}
//
//				@Override
//				public ISelection getSelection() {
//					if(treeViewer.getViewer().getSelection() instanceof TreeSelection) {
//						Object o = ((TreeSelection)treeViewer.getViewer().getSelection()).getFirstElement();
//						if(o instanceof SWTCreationDocLigne) {
//							if(((SWTCreationDocLigne)o).getCodeTiers()!=null || ((SWTCreationDocLigne)o).getCodeDocument()!=null
//									|| ((SWTCreationDocLigne)o).getCodeDocumentRecup()!=null) {
//								return treeViewer.getViewer().getSelection();
//							} else {
//								return null;
//							}
//						}
//					}
//					return treeViewer.getViewer().getSelection();
//				}
//
//				@Override
//				public void addSelectionChangedListener(ISelectionChangedListener listener) {
//					listenerList.add(listener);
//				}
//
//				protected void fireChangementDeSelection(SelectionChangedEvent e) {
//					Object[] listeners = listenerList.getListeners();
//					for (int i = listeners.length - 1; i >= 0; i -= 1) {
//						if (listeners[i] == ISelectionChangedListener.class) {
//							if (e == null)
//								e = new SelectionChangedEvent(this,null);
//							( (ISelectionChangedListener) listeners[i]).selectionChanged(e);
//						}
//					}
//				}
//			}
//
//			LgrListener l = new LgrListener();
			//selectedDocument = ViewersObservables.observeSingleSelection(l);
			selectedDocument = ViewersObservables.observeSingleSelection(treeViewer.getViewer());
			treeViewer.selectionGrille(0);

			dbc = new DataBindingContext(realm);

			dbc.getValidationStatusMap().addMapChangeListener(changeListener);
			treeViewer.selectionGrille(0);

			setDbcStandard(dbc);

			bindingFormMaitreDetail(dbc, realm, selectedDocument,classModel);
			changementDeSelection();
			selectedDocument.addChangeListener(new IChangeListener() {

				public void handleChange(ChangeEvent event) {
					changementDeSelection();
					
				}

			});
			changementDeSelection();

		} catch (Exception e) {
			logger.error("", e);
			vue.getLaMessage().setText(e.getMessage());
		}
	}
	
	private SWTCreationDocLigne refreshTreeModel() {
		return refreshTreeModel(null);
	}

	private SWTCreationDocLigne refreshTreeModel(List<SWTCreationDocLigne> list) {
		if(list == null && modelCreation!=null) //on travaille à partir de la liste d'objet d'interface passée en paramètre (qui ne correspond pas forcement aux lignes en bdd)
			list = modelCreation.remplirListe();
		SWTCreationDocLigne root = new SWTCreationDocLigne(0);
		root.setList(list);
		return root;
	}

	

//	public void bind(PaSelectionLigneDocTree paSelectionLigneRelance){
//		try {
//			realm = SWTObservables.getRealm(vue.getParent().getDisplay());
//
//			String[] titreColonnes =new String[] {"Code document","Date","Date Livraison","Code tiers","Nom ","Net TTC","Remise ht","Escompte","Ttc","Accepté"};
//			getTreeViewerStandard().createTableCol(vue.getTree(),titreColonnes,
//					new String[] {"100","80","80","100","150","70","70","70","30","30"});
//			String[] listeChamp = new String[] {"codeDocument","dateDocument","dateLivDocument","codeTiers","nomTiers","netTtcCalc","txRemHtDocument","txRemTtcDocument","ttc","accepte"};
//			getTableViewerStandard().setListeChamp(listeChamp);
//			getTableViewerStandard().tri(classModel, listeChamp,titreColonnes);
//
//			ViewerSupportLocal.bind(getTableViewerStandard(), 
//					new WritableList(IHMmodel(), classModel),
//					BeanProperties.values(listeChamp)
//					);
//			getTableViewerStandard().addCheckStateListener(new ICheckStateListener() {
//				@Override
//				public void checkStateChanged(CheckStateChangedEvent event) {
//					String message="";
//					boolean saisieTtc=false;
//					try {
//						saisieTtc=((IHMEnteteDocument)event.getElement()).getTtc();
//						getTableViewerStandard().setSelection(new StructuredSelection(recherche(Const.C_CODE_DOCUMENT
//								, ((IHMEnteteDocument)event.getElement()).getCodeDocument())),true);
//						if(((IHMEnteteDocument)selectedDocument.getValue()).getTxRemHtDocument().
//								compareTo(BigDecimal.valueOf(0))!=0||((IHMEnteteDocument)selectedDocument.getValue()).
//								getTxRemTtcDocument().compareTo(BigDecimal.valueOf(0))!=0)
//							message="Ce document contient des remises globales qui ne peuvent pas" +
//							" être récupérées.";
//						for (Object doc : masterModelDocument.getListeObjet()) {
//							if(
//									//!((IHMEnteteDocument)doc).equals(((IHMEnteteDocument)selectedDocument.getValue()))&&
//									((IHMEnteteDocument)doc).getAccepte()&& event.getChecked()&&((IHMEnteteDocument)doc).getTtc().compareTo(saisieTtc)!=0)
//								message="Vous ne pouvez pas sélectionner à la fois des documents saisis en TTC et des documents saisis en HT.";
//						}
//						if(message==""){
//							((IHMEnteteDocument)selectedDocument.getValue()).setAccepte(event.getChecked());
//							validateUIField(Const.C_ACCEPTE,event.getChecked());
//						}else {
//							event.getCheckable().setChecked(((IHMEnteteDocument)selectedDocument.getValue()), false);
//							MessageDialog.openWarning(vue.getShell(),"Document incompatible",message);
//						}
//					} catch (Exception e) {
//						logger.error("", e);
//					}		
//				}
//			});	
//		
//		getTableViewerStandard().setCheckStateProvider(new ICheckStateProvider() {
//			
//			@Override
//			public boolean isGrayed(Object element) {
//				// TODO Auto-generated method stub
//				if(!((IHMEnteteDocument)element).getAccepte())
//					return true;
//				return false;
//			}
//			
//			@Override
//			public boolean isChecked(Object element) {
//				// TODO Auto-generated method stub
//				if(((IHMEnteteDocument)element).getAccepte())
//					return true;
//				return false;
//			}
//		});
//
////		affectationReglementControllerMini.bind();
//		vue.getTree().addMouseListener(
//				new MouseAdapter() {
//
//					public void mouseDoubleClick(MouseEvent e) {
//						String idEditor = TypeDoc.getInstance().getEditorDoc()
//								.get(TypeDoc.TYPE_BON_LIVRAISON);
//						String valeurIdentifiant = ((IHMEnteteDocument) selectedDocument
//								.getValue()).getCodeDocument();
//						ouvreDocument(valeurIdentifiant, idEditor);
//					}
//
//				});
//		dbc = new DataBindingContext(realm);
//
//		dbc.getValidationStatusMap().addMapChangeListener(changeListener);
//		setDbcStandard(dbc);
//		selectedDocument = ViewersObservables.observeSingleSelection(getTableViewerStandard());
//		bindingFormMaitreDetail(dbc, realm, selectedDocument,classModel);
//		selectedDocument.addChangeListener(new IChangeListener() {
//
//			public void handleChange(ChangeEvent event) {
//				changementDeSelection();
//			}
//
//		});
//
//		changementDeSelection();
//		initEtatBouton(true);
//	} catch(Exception e) {
//		logger.error("",e);
//		vue.getLaMessage().setText(e.getMessage());
//	}
//}

private void changementDeSelection() {
	try {
		if(selectedDocument==null ||selectedDocument.getValue()==null)
			getTreeViewerStandard().selectionGrille(0);
		if(selectedDocument!=null && selectedDocument.getValue()!=null){

		}

		initEtatBouton(true);
	} catch (Exception e) {
		logger.error("",e );
	}
}

	@Override
	protected void initActions() {
		mapCommand = new HashMap<String, IHandler>();

		
		mapCommand.put(C_COMMAND_GLOBAL_ANNULER_ID, handlerAnnuler);// Inverser
		//mapCommand.put(C_COMMAND_GLOBAL_INSERER_ID, handlerInserer);
		mapCommand.put(C_COMMAND_GLOBAL_MODIFIER_ID, handlerToutCocher);//tout cocher
//		mapCommand.put(C_COMMAND_GLOBAL_ENREGISTRER_ID, handlerEnregistrer);//envoyer dans l'autre grille
		mapCommand.put(C_COMMAND_GLOBAL_SUPPRIMER_ID, handlerToutDeCocher);//tout décocher
//		mapCommand.put(C_COMMAND_GLOBAL_IMPRIMER_ID, handlerImprimer);
		mapCommand.put(C_COMMAND_GLOBAL_FERMER_ID, handlerFermer);//supprimer les non cochés

		mapCommand.put(C_COMMAND_GLOBAL_REFRESH_ID, handlerRefresh);
		mapCommand.put(C_COMMAND_GLOBAL_AIDE_ID, handlerAide);

		mapCommand.put(C_COMMAND_GLOBAL_PRECEDENT_ID, handlerPrecedent);
		mapCommand.put(C_COMMAND_GLOBAL_SUIVANT_ID, handlerSuivant);


		initFocusCommand(String.valueOf(this.hashCode()),listeComposantFocusable,mapCommand);

		if (mapActions == null)
			mapActions = new LinkedHashMap<Button, Object>();

		mapActions.put(vue.getBtnAnnuler(), C_COMMAND_GLOBAL_ANNULER_ID);
		mapActions.put(vue.getBtnModifier(), C_COMMAND_GLOBAL_MODIFIER_ID);
		mapActions.put(vue.getBtnSupprimer(), C_COMMAND_GLOBAL_SUPPRIMER_ID);
//		mapActions.put(vue.getBtnInserer(), C_COMMAND_GLOBAL_INSERER_ID);
//		mapActions.put(vue.getBtnEnregister(), C_COMMAND_GLOBAL_ENREGISTRER_ID);
//		mapActions.put(vue.getBtnImprimer(), C_COMMAND_GLOBAL_IMPRIMER_ID);
		mapActions.put(vue.getBtnFermer(), C_COMMAND_GLOBAL_FERMER_ID);
		
		Object[] tabActionsAutres = new Object[] { C_COMMAND_GLOBAL_AIDE_ID, C_COMMAND_GLOBAL_REFRESH_ID };
		mapActions.put(null, tabActionsAutres);
		initEtatBouton(true);
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
	protected void initMapComposantChamps() {
		if (mapComposantChamps == null) 
			mapComposantChamps = new LinkedHashMap<Control,String>();

		if (listeComposantFocusable == null) 
			listeComposantFocusable = new ArrayList<Control>();

		vue.getBtnInserer().setVisible(false);
		vue.getBtnEnregister().setVisible(false);
		vue.getBtnImprimer().setVisible(false);
//		vue.getBtnFermer().setVisible(false);
		
//		vue.getBtnAnnuler().getParent().moveAbove(null);
//		vue.getBtnInserer().getParent().moveBelow(vue.getBtnAnnuler());
//		vue.getBtnModifier().getParent().moveBelow(vue.getBtnInserer());
//		vue.getBtnSupprimer().getParent().moveBelow(vue.getBtnModifier());
//		vue.getBtnFermer().getParent().moveBelow(vue.getBtnSupprimer());
		
		
		vue.getPaFomulaire().setVisible(false);
//		listeComposantFocusable.add(vue.getBtnAnnuler());
		listeComposantFocusable.add(vue.getBtnAnnuler());//Inverser		
		listeComposantFocusable.add(vue.getBtnModifier());//tout cocher
//		listeComposantFocusable.add(vue.getBtnEnregister());//Envoyer dans l'autre grille
		listeComposantFocusable.add(vue.getBtnSupprimer());//tout Décocher
//		listeComposantFocusable.add(vue.getBtnImprimer());
		listeComposantFocusable.add(vue.getBtnFermer());
		if(mapInitFocus == null) 
			mapInitFocus = new LinkedHashMap<ModeObjet.EnumModeObjet,Control>();
		mapInitFocus.put(ModeObjet.EnumModeObjet.C_MO_INSERTION,vue.getTree());
		mapInitFocus.put(ModeObjet.EnumModeObjet.C_MO_EDITION,vue.getTree());
		mapInitFocus.put(ModeObjet.EnumModeObjet.C_MO_CONSULTATION,vue.getTree());

		activeModifytListener();

	}

	@Override
	protected void initMapComposantDecoratedField() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean onClose() throws ExceptLgr {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public ResultAffiche configPanel(ParamAffiche param) {
		if (param!=null){
			Map<String,String[]> map = dao.getParamWhereSQL();
			if (param.getFocusDefautSWT()!=null && !param.getFocusDefautSWT().isDisposed())
				param.getFocusDefautSWT().setFocus();
			else param.setFocusDefautSWT(vue.getTree());

				vue.getLaTitreGrille().setText(LIBELLE_GRILLE);



			if(param.getEcranAppelant()!=null) {
				ecranAppelant = param.getEcranAppelant();
			}
			if(param instanceof ParamAffichecreationDocMultiple){
				setEnregistreDirect(((ParamAffichecreationDocMultiple)param).getEnregistreDirect());
			}
		}	

		bind(vue);
		initSashFormWeight();
			getTreeViewerStandard().selectionGrille(0);
//			getTableViewerStandard().tri(classModel,nomClass,Const.C_FICHIER_LISTE_CHAMP_GRILLE);
			VerrouInterface.setVerrouille(false);
			initEtatBouton(true);
			return null;
	}

	@Override
	public Composite getVue() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void sortieChamps() {
		// TODO Auto-generated method stub

	}

	public void setVue(PaSelectionLigneDocTree vue) {
		super.setVue(vue);
		this.vue = vue;
	}



	@Override
	protected void initEtatBouton() {
		initEtatBouton(false);
	}
	
	protected void initImageBouton() {
		super.initImageBouton();
		vue.getBtnAnnuler().setImage(LibrairiesEcranPlugin.ir.get(LibrairiesEcranPlugin.IMAGE_REINITIALISER));
//		vue.getBtnEnregister().setImage(LibrairiesEcranPlugin.ir.get(LibrairiesEcranPlugin.IMAGE_ACCEPTER));
		vue.getBtnFermer().setImage(LibrairiesEcranPlugin.ir.get(LibrairiesEcranPlugin.IMAGE_FERMER));
//		vue.getBtnImprimer().setImage(Activator.getImageDescriptor("/icons/arrow_undo.png").createImage());
		//vue.getBtnInserer().setImage(LibrairiesEcranPlugin.ir.get(LibrairiesEcranPlugin.IMAGE_REINITIALISER));
		vue.getBtnModifier().setImage(LibrairiesEcranPlugin.ir.get(LibrairiesEcranPlugin.IMAGE_MODIFIER));
		vue.getBtnSupprimer().setImage(LibrairiesEcranPlugin.ir.get(LibrairiesEcranPlugin.IMAGE_SUPPRIMER));
//		vue.getBtnEnregister().setText("Valider la sélection");
//		vue.getBtnImprimer().setText("Remplacer la sélection");
		vue.getBtnFermer().setText("Supprimer les non cochés");
		vue.getBtnAnnuler().setText("Inverser les cochés");
		vue.getBtnModifier().setText("tout cocher");
		vue.getBtnSupprimer().setText("tout Décocher");
//		vue.getBtnInserer().setText("Inverser les cochés");
		vue.layout(true);
	}
	
	@Override
	protected void initEtatBouton(boolean initFocus) {
		boolean trouve = true;
		switch (daoStandard.getModeObjet().getMode()) {
		case C_MO_INSERTION:
			break;
		case C_MO_EDITION:
			break;
		case C_MO_CONSULTATION:
			enableActionAndHandler(C_COMMAND_GLOBAL_INSERER_ID,true);
			enableActionAndHandler(C_COMMAND_GLOBAL_MODIFIER_ID,true);
			enableActionAndHandler(C_COMMAND_GLOBAL_ENREGISTRER_ID,true);
			enableActionAndHandler(C_COMMAND_GLOBAL_ANNULER_ID,true);
			enableActionAndHandler(C_COMMAND_GLOBAL_IMPRIMER_ID,true);
			enableActionAndHandler(C_COMMAND_GLOBAL_FERMER_ID,true);
			enableActionAndHandler(C_COMMAND_GLOBAL_SUPPRIMER_ID,true);
			enableActionAndHandler(C_COMMAND_GLOBAL_AIDE_ID,true);
			enableActionAndHandler(C_COMMAND_GLOBAL_REFRESH_ID,true);
			if (vue.getTree()!=null)vue.getTree().setEnabled(true);
			break;
		default:
			break;
		}
		//	}
		initEtatComposant();
		if (initFocus)
			initFocusSWT(daoStandard, mapInitFocus);	
		}

//	public ModelGeneralObjet<IHMLRelance, TaLRelanceDAO, TaLRelance> getModelFacture() {
//		return modelFacture;
//	}
//
//	public void setModelFacture(
//			ModelGeneralObjet<IHMLRelance, TaLRelanceDAO, TaLRelance> modelFacture) {
//		this.modelFacture = modelFacture;
//	}



	
	public IStatus validateUI() throws Exception {
		if ((dao.getModeObjet().getMode().compareTo(ModeObjet.EnumModeObjet.C_MO_INSERTION) == 0)
				|| (dao.getModeObjet().getMode().compareTo(ModeObjet.EnumModeObjet.C_MO_EDITION) == 0)) {
			ctrlTousLesChampsAvantEnregistrementSWT();
		}
		return null;
	}

	public IStatus validateUIField(String nomChamp,Object value) {
		String validationContext = "RELANCE";
		try {
			IStatus s = null;
			if (nomChamp.equals(Const.C_CODE_DOCUMENT) ) {
				s=new Status(Status.OK,gestComBdPlugin.PLUGIN_ID,"OK");
			}
			if (nomChamp.equals(Const.C_CODE_TIERS) ) {
				s=new Status(Status.OK,gestComBdPlugin.PLUGIN_ID,"OK");
			}
			if (nomChamp.equals(Const.C_ACCEPTE) ) {
				s=new Status(Status.OK,gestComBdPlugin.PLUGIN_ID,"OK");			
			}
			if (s.getSeverity() != IStatus.ERROR) {
			}
			//			// new Status(Status.OK,gestComBdPlugin.PLUGIN_ID,"OK");
			return s;

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public IDocumentTiers rechercheEntite(SWTCreationDocLigne ligne) {
		int i = 0;
		Collection<TaCreationDoc> model=modelCreation.getListeEntity();
		Collection<IDocumentTiers> listTemp=null;
		for (TaCreationDoc taCreationDoc : model) {
			if(taCreationDoc.getCodeTiers().equals(ligne.getCodeTiersParent())
					&& taCreationDoc.getCodeDocument().equals(ligne.getCodeDocument())){
				listTemp=taCreationDoc.getListeDoc();
				for (IDocumentTiers iDocumentTiers : listTemp) {
					if(iDocumentTiers.getCodeDocument().equals(ligne.getCodeDocumentRecup()))
						return iDocumentTiers;
				}
			}
		}
		return null;

	}
	
	public Object recherche(String propertyName, Object value) {
		boolean trouve = false;
		int i = 0;
		Object properties =null;
		List<SWTCreationDocLigne> model=modelCreation.getListeObjet();
		List<SWTCreationDocLigne> listTemp=new LinkedList<SWTCreationDocLigne>();
		while(!trouve && i<model.size()){
			try {
				properties =PropertyUtils.getProperty(model.get(i), propertyName);
				if(properties!=null && properties.equals(value)){ 
					return model.get(i);
				} else {
					listTemp=getChildren(model.get(i), listTemp);
					for (SWTCreationDocLigne elem : listTemp) {
						properties =PropertyUtils.getProperty(elem, propertyName);
						if(properties!=null && properties.equals(value)) 
							return elem;
					}
					i++;
				}

			} catch (IllegalAccessException e) {
				logger.error("",e);
			} catch (InvocationTargetException e) {
				logger.error("",e);
			} catch (NoSuchMethodException e) {
				logger.error("",e);
			}
		}

		return null;

	}

	
	
	public Boolean getEnregistreDirect() {
		return enregistreDirect;
	}

	public void setEnregistreDirect(Boolean enregistreDirect) {
		this.enregistreDirect = enregistreDirect;
	}

	public void retourEcran(final RetourEcranEvent evt) {
		if (evt.getRetour() != null
				&& (evt.getSource() instanceof SWTPaAideControllerSWT)) {
			if (getFocusAvantAideSWT() instanceof Text) {
				try {
					((Text) getFocusAvantAideSWT()).setText(((ResultAffiche) evt
							.getRetour()).getResult());		



					ctrlUnChampsSWT(getFocusAvantAideSWT());
				} catch (Exception e) {
					logger.error("",e);
					if(getFocusAvantAideSWT()!=null)setFocusCourantSWT(getFocusAvantAideSWT());
					vue.getLaMessage().setText(e.getMessage());
				}
			}			
		} else if (evt.getRetour() != null){

		}
		super.retourEcran(evt);
	}

	public ModelRecupDocumentCreationDoc getMasterModelDocument() {
		return masterModelDocument;
	}

	public void setMasterModelDocument(
			ModelRecupDocumentCreationDoc masterModelDocument) {
		this.masterModelDocument = masterModelDocument;
	}

	public ModelCreationDoc getModelCreation() {
		return modelCreation;
	}

	public void setModelCreation(ModelCreationDoc modelCreation) {
		this.modelCreation = modelCreation;
	}


	private class HandlerToutCocher extends LgrAbstractHandler {

		public Object execute(ExecutionEvent event) throws ExecutionException {
			try {
				List<SWTCreationDocLigne>list=new LinkedList<SWTCreationDocLigne>();
				for (Object obj : modelCreation.getListeObjet()) {
					((SWTCreationDocLigne)obj).setAccepte(true);
					list.clear();
					list=getChildren((SWTCreationDocLigne)obj,list);
					if(list!=null)
						for (SWTCreationDocLigne e : list) {
							e.setAccepte(true);
							treeViewer.getViewer().refresh(e);
						}
				}

				treeViewer.getViewer().refresh();
			} catch (Exception e) {
				logger.error("", e);
			}
			return event;
		}
	}
	
	private class HandlerToutDeCocher extends LgrAbstractHandler {

		public Object execute(ExecutionEvent event) throws ExecutionException {
			try {
				List<SWTCreationDocLigne>list=new LinkedList<SWTCreationDocLigne>();
				for (Object obj : modelCreation.getListeObjet()) {
					((SWTCreationDocLigne)obj).setAccepte(false);
					list.clear();
					list=getChildren((SWTCreationDocLigne)obj,list);
					if(list!=null)
						for (SWTCreationDocLigne e : list) {
							e.setAccepte(false);
							treeViewer.getViewer().refresh(e);
						}
				}
				treeViewer.getViewer().refresh();
			} catch (Exception e) {
				logger.error("", e);
			}
			return event;
		}
	}



	private List<SWTCreationDocLigne> getChildren(SWTCreationDocLigne element,List<SWTCreationDocLigne> liste){
		List<SWTCreationDocLigne> l = element.getList();
		if(l!=null){
			for (SWTCreationDocLigne e2 : l) {
				liste.add(e2);
			}
			for (SWTCreationDocLigne swtCreationDocLigne : l) {
				l=getChildren(swtCreationDocLigne,liste);
			}
			
		}
		return liste;
	}

	private void initialiseTitreGrille(){
		int nombre=0;
		String typeDoc="";
		for (TaCreationDoc obj : modelCreation.getListeEntity()) {
			nombre+=obj.getListeDoc().size();
			while(typeDoc.equals("")&& obj.getListeDoc().size()>0){
				typeDoc=obj.getListeDoc().get(0).getTypeDocument();
			}
		}

		vue.getLaTitreGrille().setText(LIBELLE_GRILLE+" ("+modelCreation.getListeEntity().size()
				+" "+getSelectedTypeCreation()+"(s) à créer contenant "+nombre+" "+typeDoc+"(s))");
	}

	public IObservableValue getSelectedDocument() {
		return selectedDocument;
	}

	public void setSelectedDocument(IObservableValue selectedDocument) {
		this.selectedDocument = selectedDocument;
	}

	public ModelTiersCreationDoc getMasterModelTiers() {
		return masterModelTiers;
	}

	public void setMasterModelTiers(ModelTiersCreationDoc masterModelTiers) {
		this.masterModelTiers = masterModelTiers;
	}

	public String getSelectedTypeCreation() {
		return selectedTypeCreation;
	}

	public void setSelectedTypeCreation(String selectedTypeCreation) {
		this.selectedTypeCreation = selectedTypeCreation;
	}

	public String getSelectedTypeSelection() {
		return selectedTypeSelection;
	}

	public void setSelectedTypeSelection(String selectedTypeSelection) {
		this.selectedTypeSelection = selectedTypeSelection;
	}

	public void addDeclencheInitEtatBoutonControllerListener(IDeclencheInitEtatBoutonControllerListener l) {
		listenerList.add(IDeclencheInitEtatBoutonControllerListener.class, l);
	}

	public void removeDeclencheInitEtatBoutonControllerListener(IDeclencheInitEtatBoutonControllerListener l) {
		listenerList.remove(IDeclencheInitEtatBoutonControllerListener.class, l);
	}

	protected void fireDeclencheInitEtatBoutonController(DeclencheInitEtatBoutonControllerEvent e) {
		try {
			actEnregistrer();
		Object[] listeners = listenerList.getListenerList();
		for (int i = listeners.length - 2; i >= 0; i -= 2) {
			if (listeners[i] == IDeclencheInitEtatBoutonControllerListener.class) {
				if (e == null)
					e = new DeclencheInitEtatBoutonControllerEvent(this,e.getOption());
				( (IDeclencheInitEtatBoutonControllerListener) listeners[i + 1]).DeclencheInitEtatBoutonController(e);
			}
		}
		} catch (Exception e1) {
			logger.error("",e1);
		}
	}

}
