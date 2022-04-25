package fr.legrain.gestCom.librairiesEcran.swt;

import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.log4j.Logger;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.core.commands.NotEnabledException;
import org.eclipse.core.commands.NotHandledException;
import org.eclipse.core.commands.common.NotDefinedException;
import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.UpdateValueStrategy;
import org.eclipse.core.databinding.beans.BeanProperties;
import org.eclipse.core.databinding.beans.BeansObservables;
import org.eclipse.core.databinding.observable.Realm;
import org.eclipse.core.databinding.observable.list.WritableList;
import org.eclipse.core.databinding.observable.map.IObservableMap;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.core.expressions.Expression;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.databinding.swt.SWTObservables;
import org.eclipse.jface.databinding.viewers.ObservableListContentProvider;
import org.eclipse.jface.databinding.viewers.ViewersObservables;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.ActiveShellExpression;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.handlers.IHandlerService;
import org.eclipse.ui.swt.IFocusService;

import fr.legrain.gestCom.Appli.Const;
import fr.legrain.gestCom.Appli.ModelGeneralObjet;
import fr.legrain.gestCom.librairiesEcran.swt.SWTPaAideControllerSWT.HandlerOK;
import fr.legrain.lib.data.ExceptLgr;
import fr.legrain.lib.data.IModelGeneral;
import fr.legrain.lib.data.LgrConstantes;
import fr.legrain.lib.data.LibConversion;
import fr.legrain.lib.data.LibDate;
import fr.legrain.lib.gui.JPABaseController;
import fr.legrain.lib.gui.ParamAffiche;
import fr.legrain.lib.gui.ResultAffiche;
import fr.legrain.lib.gui.aide.PaAideRechercheSWT;
import fr.legrain.lib.gui.grille.LgrObservableMapLabelProvider;
import fr.legrain.lib.gui.grille.LgrTableViewer;
import fr.legrain.lib.gui.grille.LgrViewerSupport;
import fr.legrain.lib.gui.grille.MyLabelProvider;
import fr.legrain.libMessageLGR.LgrMess;

public class SWTPaAideRechercheControllerSWT extends JPABaseController {

	static Logger logger = Logger.getLogger(SWTPaAideRechercheControllerSWT.class.getName());

	//TODO ajout d'un objet de retour pour recupérer la valeur des ecrans de créations

	public static final String C_COMMAND_CHANGE_CHAMPS_RECHERCHE_ID = "fr.legrain.gestionCommerciale.aide.champsRecherche";
	protected IHandlerService handlerService = (IHandlerService)PlatformUI.getWorkbench().getService(IHandlerService.class);
	protected HandlerChangeChampsRech handlerChangeChampsRech = new HandlerChangeChampsRech();

	private ActionChangeChampsRech actionChangeChampsRech = new ActionChangeChampsRech("Change champ recherche");
	private ActionUp actionUp = new ActionUp("");
	private ActionDown actionDown = new ActionDown("");
	private ActionUpTotal actionUpTotal = new ActionUpTotal("");
	private ActionDownTotal actionDownTotal = new ActionDownTotal("");

	protected HandlerOK handlerOK = new HandlerOK();
	public static final String C_COMMAND_AIDE_OK_ID = "fr.legrain.gestionCommerciale.aide.ok";

	private PaAideRechercheSWT vue = null;
	public Menu[] tabPopupsAide = null; 
	//#JPA
	private ViewerFilter filtreEcran = null;
	private String champsRechercheInitial = null; //champs sur lequel la recherche a été demandé
	private String champsId = null; //champs identifiant qui sert à renvoyer dans tous les cas la clé
	// de l'enregistrement
	//TODO ajout d'une info sur l'écran à appeler pour la creation
	public JPABaseControllerSWTStandard refCreation = null; //controller de l'écran à appeler pour une création
	public ParamAffiche paramEcranCreation = null; //paramètres pour l'écran de création
	public Shell shellCreation = null;
	private String cleListeTitre = null; //cle permettant de retrouver la liste des titres des colonnes
	public IEditorPart editorCreation = null;
	public IEditorInput editorInputCreation = null;
	public String editorCreationId = null;
	public Boolean afficheDetail=true;
	public Boolean afficheNouveau=true;
	//public Class refCreation = null;

	private Boolean affichageAideRemplie = true;

	private JPABaseControllerSWT controllerAppelant = null;

	// modèle
	//#JPA
	//private QueryDataSet query = null;
	private String champsRecherche = null; //champs sur lequel la recherche est effectué à un moment précis
	protected SimpleDateFormat df = new SimpleDateFormat(LgrConstantes.C_DATE_FORMAT);

	private Class<?> typeObjet; // type d'objet contenu dans le viewer
	private Class<?> typeEntite;
	private Realm realm;
	private DataBindingContext dbc;
	private IModelGeneral model;
	private LgrTableViewer tableViewer;
	private WritableList writableList;
	private IObservableValue selectedObject;
	private String[] listeChamp;
	private String[] listeTitre;

	protected List<Control> listeComposantFocusable = null;
	protected Map<String, org.eclipse.core.commands.IHandler> mapCommand = null;

	public SWTPaAideRechercheControllerSWT(PaAideRechercheSWT vue) {
		try {
			super.setVue(vue);
			this.vue = vue;
			initComposantsVue();
			Menu popupMenuGrille = new Menu(vue.getShell(), SWT.POP_UP);
			tabPopupsAide = new Menu[] { popupMenuGrille };
			vue.getGrille().setMenu(popupMenuGrille);

			//			ActionContributionItem i = new ActionContributionItem(actionChangeChampsRech);
			//			i.fill(vue.getGrille().getMenu(),vue.getGrille().getMenu().getItemCount());

			//if (getFocusCourantSWT()!=null )getFocusCourantSWT().setFocus();


			listeComposantFocusable = new ArrayList<Control>();
			listeComposantFocusable.add(vue.getTfChoix());
			listeComposantFocusable.add(vue.getGrille());


			vue.getTfChoix().setToolTipText("TfChoix");
			vue.getGrille().setToolTipText("Grille");

			listeComponentFocusableSWT(listeComposantFocusable);
			//			changeCouleur(vue);
		} catch (ExceptLgr e) {
			logger.error("Erreur : PaAideRechercheController", e);
		}
	}
	//	public SWTPaAideRechercheControllerSWT() {
	//		try {
	//			this.vue = new PaAideRechercheSWT();
	//			initComposantsVue();
	////			if (getFocusCourant()!=null )getFocusCourant().requestFocus();
	//			//tabPopupsAide =new Menu[]{this.vue.getPopupMenuAide()};
	//			
	//			listeComposantFocusable = new ArrayList();
	//			listeComposantFocusable.add(vue.getTfChoix());
	//			listeComposantFocusable.add(vue.getGrille());
	//			listeComponentFocusableSWT(listeComposantFocusable);
	////			changeCouleur(vue);
	//		} catch (ExceptLgr e) {
	//			logger.error("Erreur : PaAideRechercheController", e);
	//		}
	//	}

	public String getChampsRechercheInitial() {
		return champsRechercheInitial;
	}

	/**
	 * @inheritDoc
	 */
	public ResultAffiche configPanel(ParamAffiche param){
		if (param!=null){

			model = ((ParamAfficheAideRechercheSWT)param).getModel();
			if( model instanceof ModelGeneralObjet)
				((ModelGeneralObjet)model).setJPQLQuery(((ParamAfficheAideRechercheSWT)param).getJPQLQuery());
			typeObjet = ((ParamAfficheAideRechercheSWT)param).getTypeObjet();
			typeEntite = ((ParamAfficheAideRechercheSWT)param).getTypeEntite();

			//pour gérer les boutons nouveau et Détail qui ne peuvent pas toujours être disponibles
			afficheDetail=((ParamAfficheAideRechercheSWT)param).getAfficheDetail();
			if(((ParamAfficheAideRechercheSWT)param).getAfficheNouveau()==null)
				afficheNouveau=afficheDetail;	
			else
				afficheNouveau=((ParamAfficheAideRechercheSWT)param).getAfficheNouveau();

			this.champsRecherche = ((ParamAfficheAideRechercheSWT)param).getChampsRecherche();
			champsRechercheInitial = champsRecherche;
			champsId=((ParamAfficheAideRechercheSWT)param).getChampsIdentifiant();
			
			
			//modifié par isa le 25/03/2014 suite à choix fait par philippe
//			if(((ParamAfficheAideRechercheSWT)param).getForceAffichageAideRemplie())
//				affichageAideRemplie=true;				
//			else
//				affichageAideRemplie=LgrMess.isAfficheAideRemplie();

			affichageAideRemplie=((ParamAfficheAideRechercheSWT)param).getForceAffichageAideRemplie();				
			
			
			//if (((ParamAfficheAideRechercheSWT)param).getEditorCreation()!=null) {
			editorCreation = ((ParamAfficheAideRechercheSWT)param).getEditorCreation();
			editorCreationId = ((ParamAfficheAideRechercheSWT)param).getEditorCreationId();
			editorInputCreation = ((ParamAfficheAideRechercheSWT)param).getEditorInputCreation();
			//}

			refCreation = ((ParamAfficheAideRechercheSWT)param).getRefCreationSWT();
			paramEcranCreation = ((ParamAfficheAideRechercheSWT)param).getParamEcranCreation();
			cleListeTitre = ((ParamAfficheAideRechercheSWT)param).getCleListeTitre();

			controllerAppelant = ((ParamAfficheAideRechercheSWT)param).getControllerAppelant();

			if(((ParamAfficheAideRechercheSWT)param).getMessage()!=null)
				vue.getLaInfo().setText(((ParamAfficheAideRechercheSWT)param).getMessage());

			vue.getLaMessageAideNonRemplie().setVisible(!((ParamAfficheAideRechercheSWT)param).getForceAffichageAideRemplie());
			vue.getLaMessageAideNonRemplie().getVisible();
			shellCreation = ((ParamAfficheAideRechercheSWT)param).getShellCreation();

			bind(vue,typeObjet);
			vue.getTfChoix().setText(((ParamAfficheAideRechercheSWT)param).getDebutRecherche());
			changeChampsRech(champsRecherche);

			//#JPA
			vue.getTfChoix().addModifyListener(new Doc());
			vue.getTfChoix().addKeyListener(new KeyListener() {
				@Override
				public void keyReleased(KeyEvent e) {					
				}
				@Override
				public void keyPressed(KeyEvent e) {
					if(e.keyCode == SWT.CR || e.keyCode == SWT.KEYPAD_CR) {
						DocEnter doc = new DocEnter();
						doc.modifyText(null);	
					}
				}
			});
		}
		return null;
	}

	public void bind(PaAideRechercheSWT paAideRechercheSWT, Class typeObjet){
		try {
			//UI to model
			UpdateValueStrategy modelToUI = null;
			realm = SWTObservables.getRealm(vue.getParent().getDisplay());

			tableViewer = new LgrTableViewer(paAideRechercheSWT.getGrille());
			String cleTitre = cleListeTitre!=null ?  cleListeTitre : refCreation.getClass().getSimpleName();
			tableViewer.createTableCol(typeObjet,paAideRechercheSWT.getGrille(),cleTitre,Const.C_FICHIER_LISTE_CHAMP_GRILLE,0);
			listeChamp = tableViewer.setListeChampGrille(cleTitre,Const.C_FICHIER_LISTE_CHAMP_GRILLE);
			listeTitre = tableViewer.setListeTitreChampGrille(cleTitre,Const.C_FICHIER_LISTE_CHAMP_GRILLE);
			//			writableList =new WritableList(realm, new LinkedList(), typeObjet);
			//			LgrViewerSupport.bind(tableViewer,writableList,BeanProperties.values(listeChamp));
			ObservableListContentProvider viewerContent = new ObservableListContentProvider();		
			tableViewer.setContentProvider(viewerContent);

			IObservableMap[] attributeMaps = BeansObservables.observeMaps(
					viewerContent.getKnownElements(), typeObjet, listeChamp );

			tableViewer.setLabelProvider(new MyLabelProvider(attributeMaps));
			//			tableViewer.setLabelProvider(new LgrObservableMapLabelProvider(attributeMaps));
			//writableList =new WritableList(realm, model.remplirListe(), typeObjet);
			//tableViewer.setInput(writableList);

			selectedObject = ViewersObservables.observeSingleSelection(tableViewer);

			//			for (String champ : listeChamp) {
			//				modelToUI =  new UpdateValueStrategy();//.setAfterConvertValidator(new CtrlInterface(ibTaTableStandard,mapComposantChamps.get(c)));
			//				
			//				if( typeObjet.getDeclaredField(champ).getType().equals(BigDecimal.class) ) {
			//					modelToUI.setConverter(new ConvertBigDecimal2String());
			//				} else if( typeObjet.getDeclaredField(champ).getType().equals(Integer.class) ) {
			//					modelToUI.setConverter(new ConvertInteger2String());
			//				} else if( typeObjet.getDeclaredField(champ).getType().equals(Date.class) ) {
			//					modelToUI.setConverter(new ConvertDate2String());
			//				}
			//			}


			dbc = new DataBindingContext(realm);

			dbc.bindValue(SWTObservables.observeText(paAideRechercheSWT.getTfChoix(), SWT.FocusOut),
					BeansObservables.observeValue(realm, typeObjet.newInstance(), 
							champsRecherche),null,modelToUI);
			tableViewer.tri(typeObjet, cleTitre,Const.C_FICHIER_LISTE_CHAMP_GRILLE);

		} catch(Exception e) {
			logger.error("",e);
		}
	}

	/**
	 * Change le champs sur lequel s'effectue la recherche
	 * @param champs - nom du champs
	 */
	void changeChampsRech(String champs) {
		PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell().setCursor(new Cursor(PlatformUI.getWorkbench().getDisplay(),SWT.CURSOR_WAIT));
		this.champsRecherche = champs;
		int i = -1;
		boolean trouve = false;

		while(!trouve && i<listeChamp.length) {
			i++;
			if(i<listeChamp.length) {
				if(listeChamp[i].equals(champsRecherche))
					trouve = true;
			}
		}

		if(trouve) {
			vue.getLaRecherche().setText("Recherche sur "+listeTitre[i]);
			vue.getLaRecherche().setToolTipText("Recherche sur "+listeTitre[i]);
		}

		try {
			if(filtreEcran!=null)
				tableViewer.removeFilter(filtreEcran);
			filtreEcran = new Filtre();
			tableViewer.addFilter(filtreEcran);

			//#JPA
			if(vue.getTfChoix().getText().equals("*")){
				affichageAideRemplie=true;
				vue.getTfChoix().setText("");
				model.razListEntity();
			}

			if(affichageAideRemplie==false ){
				DocEnter doc = new DocEnter();
				doc.modifyText(null);
			}
			else{
				List l = model.remplirListe();
				if(l.size()>=1) {
					writableList =new WritableList(realm, l, typeObjet);
					tableViewer.setInput(writableList);
					tableViewer.refresh();
					tableViewer.selectionGrille(0);				
				} else {
					//s'il n'y a aucune ligne, on retire une lettre jusqu'a ce qu'on obtienne  des resultats
					if(vue.getTfChoix().getText().length()>0) {
						vue.getTfChoix().setText(vue.getTfChoix().getText().substring(0,vue.getTfChoix().getText().length()-1));
						changeChampsRech(champs);
					}
				}
			}
		} catch (Exception e) {
			logger.error("Erreur : configPanel", e);
		} finally {
			PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell().setCursor(new Cursor(PlatformUI.getWorkbench().getDisplay(),SWT.CURSOR_ARROW));
		}
	}

	/**
	 * Filtre la recherche en fonction de <code>champsRecherche</code>
	 * @author nicolas
	 */
	protected class Filtre extends ViewerFilter {
		@Override
		public boolean select(Viewer viewer, Object parentElement, Object element) {
			if(champsRecherche!=null && vue.getTfChoix().getText()!=null) {
				try {
					//					String champModifie =champsRecherche.substring(0, 1).toUpperCase()+
					//							champsRecherche.substring(1);
					if(affichageAideRemplie==false){
						if(vue.getTfChoix().getText().trim().equals("*"))return true;
						//si date
						if(PropertyUtils.getPropertyType(element, champsRecherche)==java.util.Date.class){
							if(((java.util.Date)PropertyUtils.getProperty(element, champsRecherche)).compareTo(
									LibDate.stringToDate2(vue.getTfChoix().getText()))==0)
								return true;
							else
								return false;
						}
						//si boolean
						if(PropertyUtils.getPropertyType(element, champsRecherche)==Boolean.class){							
							if((Boolean)(PropertyUtils.getProperty(element, champsRecherche)).
									equals(LibConversion.StringToBoolean(vue.getTfChoix().getText(),null)))
								return true;
							else
								return false;
						}						
						else{	
							//if(	PropertyUtils.getProperty(element, champsRecherche).toString().equalsIgnoreCase(vue.getTfChoix().getText()))
							//if(	PropertyUtils.getProperty(element, champsRecherche).toString().toLowerCase().startsWith(vue.getTfChoix().getText().toLowerCase()))
							if(	PropertyUtils.getProperty(element, champsRecherche).toString().toLowerCase().contains(vue.getTfChoix().getText().toLowerCase()))
								return true;
							else
								return false;
						}
					}
					else{
						if(vue.getTfChoix().getText().trim().equals("*"))return true;
						else
							if(PropertyUtils.getProperty(element, champsRecherche)!=null){
								//if(	PropertyUtils.getProperty(element, champsRecherche).toString().startsWith(vue.getTfChoix().getText()))
								//if(	PropertyUtils.getProperty(element, champsRecherche).toString().toLowerCase().startsWith(vue.getTfChoix().getText().toLowerCase()))
								if( PropertyUtils.getProperty(element, champsRecherche).toString().toLowerCase().contains(vue.getTfChoix().getText().toLowerCase()))
									return true;
								else
									return false;
							}else 
								if((vue.getTfChoix().getText().trim().equals("")
								)&& element!=null)
									return true;
								else
									return false;
					}
				} catch (IllegalArgumentException e) {
					logger.error("", e);
				} catch (SecurityException e) {
					logger.error("", e);
				} catch (IllegalAccessException e) {
					logger.error("", e);
				} catch (InvocationTargetException e) {
					logger.error("", e);
				} catch (NoSuchMethodException e) {
					logger.error("", e);
				}
			}
			return false;
		}
	}

	//#JPA
	//	/**
	//	 * Filtre la recherche en fonction de <code>champsRecherche</code>
	//	 * @author nicolas
	//	 */
	//	protected class FiltreDataSet implements RowFilterListener {
	//		public void filterRow(ReadRow row, RowFilterResponse response) {
	//			Variant v = new Variant();
	//			if(!query.isOpen())
	//				query.open();
	//			if(champsRecherche!=null && vue.getTfChoix().getText()!=null) {
	//				row.getVariant(champsRecherche, v);
	//				if(v.toString().toLowerCase().startsWith(vue.getTfChoix().getText().toLowerCase()))
	//					response.add();
	//			} else {
	//				response.add();
	//			}
	//		}
	//	}

	//#JPA
	protected class DocEnter implements ModifyListener {
		//@Override
		public void modifyText(ModifyEvent e) {
			if(affichageAideRemplie==false){
				List l = null;
				try {
					if(vue.getTfChoix().getText().equals("*")){
						model.razListEntity();
						l=model.remplirListe();
					}
					else
						l =model.remplirListeElement(typeEntite.newInstance(),champsRecherche, vue.getTfChoix().getText());
				} catch (Exception e1) {
					logger.error("", e1);
				} 

				if(l!=null && l.size()>=1) 
				{
					writableList =new WritableList(realm, l, typeObjet);
					tableViewer.setInput(writableList);
					tableViewer.refresh();
					tableViewer.selectionGrille(0);				
				} 

			}
		}

	}
	protected class Doc implements ModifyListener {
		//@Override
		public void modifyText(ModifyEvent e) {
			if(affichageAideRemplie==true){
				if(vue.getTfChoix().getText().trim().equals("*")){
					model.razListEntity();
					List l = model.remplirListe();
					if(l.size()>=1) {
						writableList =new WritableList(realm, l, typeObjet);
						tableViewer.setInput(writableList);
						tableViewer.refresh();
						tableViewer.selectionGrille(0);				
					}
				}
				else{
					vue.setCursor(Display.getCurrent().getSystemCursor(SWT.CURSOR_WAIT));
					tableViewer.refresh();
					vue.setCursor(Display.getCurrent().getSystemCursor(SWT.CURSOR_ARROW));
				}
			}
		}
	}

	/**
	 * Initialisation des composants graphiques de la vue.
	 * @throws ExceptLgr 
	 */
	private void initComposantsVue() throws ExceptLgr {

		//		IHandlerService handlerService = (IHandlerService)PlatformUI.getWorkbench().getService(IHandlerService.class);
		//		IFocusService focusService = (IFocusService) PlatformUI.getWorkbench().getService(IFocusService.class);
		//
		//		String focusId = String.valueOf(this.hashCode());
		//		focusService.addFocusTracker(vue.getGrille(), focusId);
		//		focusService.addFocusTracker(vue.getLaRecherche(), focusId);
		//		focusService.addFocusTracker(vue.getTfChoix(), focusId);
		//
		//		Expression exp = new FocusControlExpression(focusId);
		//		handlerService.activateHandler(C_COMMAND_CHANGE_CHAMPS_RECHERCHE_ID, handlerChangeChampsRech, exp);

		vue.getTfChoix().addKeyListener(new KeyAdapter(){
			@Override
			public void keyReleased(KeyEvent e) {
				super.keyReleased(e);
				if(e.keyCode == SWT.ARROW_UP) {
					actionUp.run();
				}
				else
					if(e.keyCode == SWT.HOME) {
						actionUpTotal.run();
					}
					else if(e.keyCode == SWT.ARROW_DOWN) {
						actionDown.run();
					}
					else if(e.keyCode == SWT.END) {
						actionDownTotal.run();
					}
			}
		});

	}

	public Composite getVue() {
		return vue;
	}

	public String getChampsRecherche() {
		return champsRecherche;
	}

	public void setChampsRecherche(String champsRecherche) {
		this.champsRecherche = champsRecherche;
	}

	//#JPA
	//	public QueryDataSet getQuery() {
	//		return query;
	//	}
	//
	//	public void setQuery(QueryDataSet query) {
	//		this.query = query;
	//	}

	protected class ActionUp extends Action {
		public ActionUp(String name) {
			super(name);
		}

		public void run() {
			try {
				tableViewer.selectionGrille(tableViewer.getTable().getSelectionIndex()-1);
			} catch (Exception e1) {
				logger.error("Erreur : actionPerformed", e1);
			}
		}
	}

	protected class ActionUpTotal extends Action {
		public ActionUpTotal(String name) {
			super(name);
		}

		public void run() {
			try {
				tableViewer.selectionGrille(0);
			} catch (Exception e1) {
				logger.error("Erreur : actionPerformed", e1);
			}
		}
	}
	protected class ActionDown extends Action {
		public ActionDown(String name) {
			super(name);
		}

		public void run() {
			try {
				tableViewer.selectionGrille(tableViewer.getTable().getSelectionIndex()+1);
			} catch (Exception e1) {
				logger.error("Erreur : actionPerformed", e1);
			}
		}
	}

	protected class ActionDownTotal extends Action {
		public ActionDownTotal(String name) {
			super(name);
		}

		public void run() {
			try {
				tableViewer.selectionGrille(tableViewer.getTable().getItemCount()-1);
			} catch (Exception e1) {
				logger.error("Erreur : actionPerformed", e1);
			}
		}
	}
	protected class HandlerChangeChampsRech extends LgrAbstractHandler {

		public Object execute(ExecutionEvent event) throws ExecutionException{
			try {
				int i = 0;
				boolean trouve = false;
				while(!trouve && i<listeChamp.length) {
					if(listeChamp[i].equals(champsRecherche))
						trouve = true;
					i++;
				}
				if(trouve && i<listeChamp.length) {
					changeChampsRech(listeChamp[i]);
				} else {
					changeChampsRech(listeChamp[0]);
				}

			} catch (Exception e1) {
				logger.error("Erreur : actionPerformed", e1);
			}
			return event;
		}
	}

	protected class ActionChangeChampsRech extends Action {
		public ActionChangeChampsRech(String name) {
			super(name);
		}

		public void run() {
			super.run();
			try {
				handlerService.executeCommand(C_COMMAND_CHANGE_CHAMPS_RECHERCHE_ID, null);
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

	protected class ActionRecherche extends Action {
		public ActionRecherche(String name) {
			super(name);
		}

		public void run() {

		}
	}

	public String getChampsId() {
		return champsId;
	}

	public void setChampsId(String champsId) {
		this.champsId = champsId;
	}

	public IObservableValue getSelectedObject() {
		return selectedObject;
	}

	public LgrTableViewer getTableViewer() {
		return tableViewer;
	}

	public Class getTypeObjet() {
		return typeObjet;
	}

	@Override
	protected void sortieChamps() {
		// TODO Raccord de méthode auto-généré

	}

	public Realm getRealm() {
		return realm;
	}

	public IModelGeneral getModel() {
		return model;
	}

	public HandlerChangeChampsRech getHandlerChangeChampsRech() {
		return handlerChangeChampsRech;
	}

	public Class<?> getTypeEntite() {
		return typeEntite;
	}

	protected class HandlerOK extends LgrAbstractHandler {

		public Object execute(ExecutionEvent event) throws ExecutionException{
			DocEnter doc = new DocEnter();
			doc.modifyText(null);	
			return event;
		}
	}

	public Boolean getAffichageAideRemplie() {
		return affichageAideRemplie;
	}
}
