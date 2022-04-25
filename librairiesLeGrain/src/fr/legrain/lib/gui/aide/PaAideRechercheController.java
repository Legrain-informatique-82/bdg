//package fr.legrain.lib.gui.aide;
//
//import java.awt.event.ActionEvent;
//import java.awt.event.KeyEvent;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.TooManyListenersException;
//
//import javax.swing.AbstractAction;
//import javax.swing.JComponent;
//import javax.swing.JPopupMenu;
//import javax.swing.KeyStroke;
//import javax.swing.event.DocumentEvent;
//import javax.swing.event.DocumentListener;
//
//import org.apache.log4j.Logger;
//
//import com.borland.dx.dataset.DataChangeEvent;
//import com.borland.dx.dataset.DataChangeListener;
//import com.borland.dx.dataset.NavigationEvent;
//import com.borland.dx.dataset.NavigationListener;
//import com.borland.dx.dataset.ReadRow;
//import com.borland.dx.dataset.RowFilterListener;
//import com.borland.dx.dataset.RowFilterResponse;
//import com.borland.dx.dataset.Variant;
//import com.borland.dx.sql.dataset.QueryDataSet;
//
//import fr.legrain.lib.data.ExceptLgr;
//import fr.legrain.lib.data.IBQuLgr;
//import fr.legrain.lib.gui.BaseController;
//import fr.legrain.lib.gui.ParamAffiche;
//import fr.legrain.lib.gui.ResultAffiche;
//
//public class PaAideRechercheController extends BaseController implements
//		NavigationListener, DataChangeListener {
//
//	static Logger logger = Logger.getLogger(PaAideRechercheController.class.getName());
//	
//	//TODO ajout d'un objet de retour pour recupérer la valeur des ecrans de créations
//	
//	private PaAideRecherche vue = null; // vue
//	public JPopupMenu[] tabPopupsAide = null; 
//	private Filtre filtreEcran = null;
//	private String champsRechercheInitial = null; //champs sur lequel la recherche a été demandé
//	
//	//TODO
//	//ajout d'une info sur l'écran à appeler pour la creation
//	public BaseController refCreation = null;
//	public ParamAffiche paramEcranCreation = null;
//	//public Class refCreation = null;
//
//	// modèle
//	private QueryDataSet query = new QueryDataSet();
//	private String champsRecherche = null; //champs sur lequel la recherche est effectué à un moment précis
//	
//	protected List<JComponent> listeComposantFocusable = null;
//
//
//	public PaAideRechercheController(PaAideRecherche vue) {
//		try {
//			this.vue = vue;
//			initComposantsVue();
//			// TODO Initialiser les popups partout
//			tabPopupsAide =new JPopupMenu[]{vue.getPopupMenuAide()};
//			if (getFocusCourant()!=null )getFocusCourant().requestFocus();
//			
//			listeComposantFocusable = new ArrayList();
//			listeComposantFocusable.add(vue.getTfChoix());
//			listeComposantFocusable.add(vue.getJdbTable());
//			listeComponentFocusable(listeComposantFocusable);
//		} catch (ExceptLgr e) {
//			logger.error("Erreur : PaAideRechercheController", e);
//		}
//	}
//	
//	public PaAideRechercheController() {
//		try {
//			this.vue = new PaAideRecherche();
//			initComposantsVue();
//			if (getFocusCourant()!=null )getFocusCourant().requestFocus();
//			tabPopupsAide =new JPopupMenu[]{this.vue.getPopupMenuAide()};
//			
//			listeComposantFocusable = new ArrayList();
//			listeComposantFocusable.add(vue.getTfChoix());
//			listeComposantFocusable.add(vue.getJdbTable());
//			listeComponentFocusable(listeComposantFocusable);
//			
//		} catch (ExceptLgr e) {
//			logger.error("Erreur : PaAideRechercheController", e);
//		}
//	}
//	
//	public String getChampsRechercheInitial() {
//		return champsRechercheInitial;
//	}
//
//	public ResultAffiche configPanel(ParamAffiche param){
//		if (param!=null){
//			this.query = ((ParamAfficheAideRecherche)param).getQuery();
//			query.addNavigationListener(this);
//			vue.getJdbTable().setDataSet(query);
//			this.champsRecherche = ((ParamAfficheAideRecherche)param).getChampsRecherche();
//			champsRechercheInitial = champsRecherche;
//			changeChampsRech(champsRecherche);
//			if (((ParamAfficheAideRecherche)param).getFocusDefaut()!=null)
//			setFocusCourant(((ParamAfficheAideRecherche)param).getFocusDefaut());
//			else
//				setFocusCourant(vue.getTfChoix());	
//			getFocusCourant().requestFocus();
//			refCreation = ((ParamAfficheAideRecherche)param).getRefCreation();
//			paramEcranCreation = ((ParamAfficheAideRecherche)param).getParamEcranCreation();
//			vue.getTfChoix().getDocument().addDocumentListener(new Doc());
//			vue.getJdbTable().initNomColonne(query);
//		}
//		return null;
//	}
//	
//	/**
//	 * Change le champs sur lequel s'effectue la recherche
//	 * @param champs - nom du champs
//	 */
//	public void changeChampsRech(String champs) {
//		this.champsRecherche = champs;
//		vue.getLaRecherche().setText("Recherche sur "+IBQuLgr.getTitreChamp(champsRecherche));
//		try {
//			query.removeRowFilterListener(filtreEcran);
//			filtreEcran = new Filtre();
//			query.addRowFilterListener(filtreEcran);
//			query.refilter();
//		} catch (TooManyListenersException e) {
//			logger.error("Erreur : configPanel", e);
//		}
//	}
//	
//	/**
//	 * Filtre la recherche en fonction de <code>champsRecherche</code>
//	 * @author nicolas
//	 */
//	protected class Filtre implements RowFilterListener {
//		public void filterRow(ReadRow row, RowFilterResponse response) {
//			Variant v = new Variant();
//			if(!query.isOpen())
//				query.open();
//			if(champsRecherche!=null && vue.getTfChoix().getText()!=null) {
//				row.getVariant(champsRecherche, v);
//				if(v.toString().startsWith(vue.getTfChoix().getText()))
//					response.add();
//			} else {
//				response.add();
//			}
//		}
//	}
//	
//	protected class Doc implements DocumentListener {
//		public void insertUpdate(DocumentEvent e) {
//			query.refilter();
//		}
//
//		public void removeUpdate(DocumentEvent e) {
//			query.refilter();
//		}
//
//		public void changedUpdate(DocumentEvent e) {
//			query.refilter();
//		}
//	}
//
//	/**
//	 * Initialisation des composants graphiques de la vue.
//	 * @throws ExceptLgr 
//	 */
//	private void initComposantsVue() throws ExceptLgr {
//		//this.vue.getTfChoix().setEditable(false);
//		this.vue.getJdbTable().setEditable(false);
//		this.vue.getJdbTable().setRowHeaderVisible(false);
//		
//		vue.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
//				KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0), "up");
//		vue.getActionMap().put("up", new ActionUp("up"));
//		
//		vue.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
//				KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0), "down");
//		vue.getActionMap().put("down", new ActionDown("down"));
//		
//		vue.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
//				KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0), "down");
//		vue.getActionMap().put("down", new ActionDown("down"));
//		
//		vue.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
//				KeyStroke.getKeyStroke(KeyEvent.VK_F1, KeyEvent.CTRL_DOWN_MASK), "actionChangeChampsRech");
//		vue.getActionMap().put("actionChangeChampsRech", new ActionChangeChampsRech("actionChangeChampsRech"));
//		
//	}
//
//	/**
//	 * MAJ du formulaire Adresse.
//	 */
//	public void actualiserForm() {
//		Variant v = new Variant();
//		if(!query.isOpen())
//			query.open();
//		query.getVariant(champsRecherche,query.getRow(), v);
//		vue.getTfChoix().setText(v.toString());
//		vue.getLaRecherche().setText("Recherche sur "+champsRecherche);
//		query.refilter();
//	}
//	
//
//	/**
//	 * postRow
//	 * @param event DataChangeEvent
//	 */
//	public void postRow(DataChangeEvent event) {}
//
//	/**
//	 * dataChanged
//	 * @param event DataChangeEvent
//	 */
//	public void dataChanged(DataChangeEvent event) {}
//
//	public void navigated(NavigationEvent event) {}
//
//	public PaAideRecherche getVue() {
//		return vue;
//	}
//
//	public String getChampsRecherche() {
//		return champsRecherche;
//	}
//
//	public void setChampsRecherche(String champsRecherche) {
//		this.champsRecherche = champsRecherche;
//	}
//
//	public QueryDataSet getQuery() {
//		return query;
//	}
//
//	public void setQuery(QueryDataSet query) {
//		this.query = query;
//	}
//	
//	protected class ActionUp extends AbstractAction {
//		public ActionUp(String name) {
//			super(name);
//		}
//		
//		public void actionPerformed(ActionEvent e) {
//			try {
//				query.prior();
//			} catch (Exception e1) {
//				logger.error("Erreur : actionPerformed", e1);
//			}
//		}
//	}
//	
//	protected class ActionDown extends AbstractAction {
//		public ActionDown(String name) {
//			super(name);
//		}
//		
//		public void actionPerformed(ActionEvent e) {
//			try {
//				query.next();
//			} catch (Exception e1) {
//				logger.error("Erreur : actionPerformed", e1);
//			}
//		}
//	}
//	
//	protected class ActionChangeChampsRech extends AbstractAction {
//		public ActionChangeChampsRech(String name) {
//			super(name);
//		}
//		
//		public void actionPerformed(ActionEvent e) {
//			try {
//				if(query.getColumn(champsRecherche).getOrdinal()+1 < query.getColumnCount() )
//					champsRecherche = query.getColumn(query.getColumn(champsRecherche).getOrdinal()+1).getColumnName();
//				else
//					champsRecherche = query.getColumn(0).getColumnName();
//				changeChampsRech(champsRecherche);
//			} catch (Exception e1) {
//				logger.error("Erreur : actionPerformed", e1);
//			}
//		}
//	}
//		
//}
