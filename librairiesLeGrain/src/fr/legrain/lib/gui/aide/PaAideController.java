//package fr.legrain.lib.gui.aide;
//
//import java.awt.event.ActionEvent;
//import java.awt.event.FocusAdapter;
//import java.awt.event.FocusEvent;
//import java.awt.event.KeyEvent;
//import java.util.HashMap;
//import java.util.Iterator;
//import java.util.List;
//
//import javax.swing.AbstractAction;
//import javax.swing.InputVerifier;
//import javax.swing.JComponent;
//import javax.swing.JOptionPane;
//import javax.swing.JPopupMenu;
//import javax.swing.JTable;
//import javax.swing.KeyStroke;
//
//import org.apache.log4j.Logger;
//
//import sun.rmi.runtime.GetThreadPoolAction;
//
//import com.borland.dx.dataset.DataChangeEvent;
//import com.borland.dx.dataset.DataChangeListener;
//import com.borland.dx.dataset.NavigationEvent;
//import com.borland.dx.dataset.NavigationListener;
//import com.borland.dx.dataset.Variant;
//
//import fr.legrain.lib.data.ExceptLgr;
//import fr.legrain.lib.data.ModeObjet;
//import fr.legrain.lib.gui.BaseController;
//import fr.legrain.lib.gui.ParamAffiche;
//import fr.legrain.lib.gui.ResultAffiche;
//
//public class PaAideController extends BaseController implements
//		NavigationListener, DataChangeListener {
//
//	static Logger logger = Logger.getLogger(PaAideController.class.getName());
//	
//	private PaAide vue = null; // vue
//
//	// modèle
//
//	//private IB_TA_ADRESSE ibTaTable = new IB_TA_ADRESSE();
//
//	// correspondance composant graphique/champs bdd
//	private HashMap mapComposantChamps = null;
//
//	private HashMap mapActions = null;
//	//static private JComponent focusCourant = null;
//
//	private ActionOK actionOK = new ActionOK("OK"); 
//	private ActionNouveau actionNouveau = new ActionNouveau("Nouveau");
//	
//	private HashMap controllerVue = new HashMap();
//
//	public PaAideController(PaAide vue) {
//		try {
//			this.vue = vue;
//			initComposantsVue();
//	//		initMapComposantChamps();
//			initActions();
//			
//	//		ibTaTable.getFIBQuery().addNavigationListener(this);
//	//		actualiserForm();
//
//	//		focusCourant=vue.getTfAdresse1();
//			
//		} catch (ExceptLgr e) {
//			logger.error("Erreur : PaAideController", e);
//		}
//
//	}
//	
//	public PaAideController() {
//		try {
//			this.vue = new PaAide();
//			initComposantsVue();
//			initActions();
//		} catch (ExceptLgr e) {
//			logger.error("Erreur : PaAideController", e);
//		}
//	}
//	
//	/**
//	 * Ajout d'un panneau de recherche sous forme d'onglet
//	 * @param recherche
//	 * @param titre
//	 */
//	public void addRecherche(PaAideRechercheController recherche, String titre) {
//		this.vue.getTabPane().addTab(titre, null, recherche.getVue(), null);
//		controllerVue.put(recherche.getVue(), recherche);
//	}
//	
//	public ResultAffiche configPanel(ParamAffiche param){
//		return null;
//	}
//
//	/**
//	 * Initialisation des composants graphiques de la vue.
//	 * @throws ExceptLgr 
//	 */
//	private void initComposantsVue() throws ExceptLgr {
//	}
//
//	private void initActions() {
//		// formulaire Adresse
//		if (mapActions == null)
//			mapActions = new HashMap();
//		mapActions.put(vue.getBtnNouveau(), actionNouveau);
//		mapActions.put(vue.getBtnOK(), actionOK);
//	}
//
//	/**
//	 * MAJ du formulaire Adresse.
//	 * @todo A Finir
//	 */
//	public void actualiserForm() {
////		if ((ibTaTable.getFModeObjet().getMode() != ModeObjet.C_MO_INSERTION)
////				&& (ibTaTable.getFModeObjet().getMode() != ModeObjet.C_MO_EDITION)) {
////			remplirFormulaire(mapComposantChamps, ibTaTable);
////		}
//	}
//
//	/**
//	 * postRow
//	 * @param event  DataChangeEvent
//	 */
//	public void postRow(DataChangeEvent event) {
//		actualiserForm();
//	}
//
//	/**
//	 * dataChanged
//	 * @param event DataChangeEvent
//	 */
//	public void dataChanged(DataChangeEvent event) {
//		actualiserForm();
//	}
//
//	public void navigated(NavigationEvent event) {
//		// TODO actualisation après insertion, sinon vidage de la liste
//		actualiserForm();
//	}
//
//	class ActionOK extends AbstractAction {
//		public ActionOK(String name) {
//			super(name);
//		}
//		
//		public void actionPerformed(ActionEvent e) {
//			try {
//			//	((PaAideRecherche)vue.getTabPane().getSelectedComponent()).getTfChoix().getText();
//			} catch (Exception e1) {
//				logger.error("Erreur : actionPerformed", e1);
//			}
//		}
//	}
//
//	class ActionNouveau extends AbstractAction {
//		public ActionNouveau(String name) {
//			super(name);
//		}
//		
//		public void actionPerformed(ActionEvent e) {
//			try {
//				
//			} catch (Exception e1) {
//				logger.error("Erreur : actionPerformed", e1);
//			}
//		}
//	}
//
//	
//
//}
