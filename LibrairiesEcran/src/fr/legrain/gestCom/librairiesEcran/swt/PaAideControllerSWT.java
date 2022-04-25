package fr.legrain.gestCom.librairiesEcran.swt;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.KeyStroke;

import org.apache.log4j.Logger;

import com.borland.dx.dataset.Variant;

import fr.legrain.lib.data.ExceptLgr;
import fr.legrain.lib.data.ModeObjet.EnumModeObjet;
import fr.legrain.lib.gui.OrdreFocus;
import fr.legrain.lib.gui.ParamAffiche;
import fr.legrain.lib.gui.ResultAffiche;
import fr.legrain.lib.gui.RetourEcranEvent;
import fr.legrain.lib.gui.RetourEcranListener;
import fr.legrain.lib.gui.aide.IAide;
import fr.legrain.lib.gui.aide.PaAide;
import fr.legrain.lib.gui.aide.ResultAide;
//import fr.legrain.lib.gui.aide.PaAideController;
import fr.legrain.lib.gui.aide.PaAideRecherche;
//import fr.legrain.lib.gui.aide.PaAideRechercheController;
import fr.legrain.lib.gui.aide.ParamAfficheAide;

public class PaAideControllerSWT extends BaseControllerSWT implements RetourEcranListener {
	
	static Logger logger = Logger.getLogger(PaAideControllerSWT.class.getName());
	
	private PaAide vue = null; // vue

	private JComponent appelant = null;
	private JPopupMenu[] tabPopups =null;
	// correspondance composant graphique/champs bdd
	private HashMap mapComposantChamps = null;

	private HashMap mapActions = null;

	private ActionOK actionOK = new ActionOK("Valider"); 
	private ActionNouveau actionNouveau = new ActionNouveau("Nouveau");
	private ActionAnnuler actionAnnuler = new ActionAnnuler("Annuler"); 
	
	protected List<JComponent> listeComposantFocusable = null;
	
	private boolean retourOK = false; //vrai ssi l'ecran a été fermé par l'action prévue à cet effet
	
	private HashMap controllerVue = new HashMap();
	
	public PaAideControllerSWT(PaAide vue) {
		try {
			this.vue = vue;
			initComposantsVue();
			initActions();
			initMapComposantChamps();
			listeComponentFocusable(listeComposantFocusable);
			initDeplacementSaisie(listeComposantFocusable);
			vue.setFocusCycleRoot(true);
			vue.setFocusTraversalPolicy(new OrdreFocus(listeComposantFocusable));

			if(this.getFocusCourant()!=null){
				logger.info("focus courant : "+this.getFocusCourant());
				this.getFocusCourant().requestFocus();
			}
		} catch (ExceptLgr e) {
			logger.error("Erreur : PaAideControllerSWT", e);
		}
	}
	public void initMapComposantChamps(){
		listeComposantFocusable = new ArrayList();
		listeComposantFocusable.add(vue.getBtnAnnuler());
		listeComposantFocusable.add(vue.getBtnOK());
		listeComposantFocusable.add(vue.getBtnNouveau());		
	}
	public PaAideControllerSWT() {
		try {
			this.vue = new PaAide();
			initComposantsVue();
			initActions();
			
			initMapComposantChamps();
			listeComponentFocusable(listeComposantFocusable);
			initDeplacementSaisie(listeComposantFocusable);
			vue.setFocusCycleRoot(true);
			vue.setFocusTraversalPolicy(new OrdreFocus(listeComposantFocusable));
			if(this.getFocusCourant()!=null){
				logger.info("focus courant : "+this.getFocusCourant());
				this.getFocusCourant().requestFocus();
			}
		} catch (ExceptLgr e) {
			logger.error("Erreur : PaAideControllerSWT", e);
		}
	}
	
	public JPanel getVue() {return vue;}
	
	/**
	 * Méthode appelée à la fermeture du Shell
	 */
	public boolean onClose() throws ExceptLgr {
		if(!retourOK)
			fireRetourEcran(new RetourEcranEvent(this,null));
		RecupFocusAppelant();
		((PaAideRechercheControllerSWT)controllerVue.get(((PaAideRecherche)vue.getTabPane().getSelectedComponent()))).getQuery().close();
		try {
			((PaAideRecherche)vue.getTabPane().getSelectedComponent()).getJdbTable().setDataSet(null);
			this.finalize();
		} catch (Throwable e) {
			logger.error("",e);
		}
		return true;
	}
	
//	/**
//	 * Ajout d'un panneau de recherche sous forme d'onglet
//	 * @param recherche
//	 * @param titre - titre de l'onglet
//	 */
//	public void addRecherche(PaAideRechercheController recherche, String titre) {
//		this.vue.getTabPane().addTab(titre, null, recherche.getVue(), null);
//		controllerVue.put(recherche.getVue(), recherche);
//		tabPopups=recherche.tabPopupsAide;
//		initPopupAndButtons(mapActions,tabPopups);
//	}
	
	/**
	 * Ajout d'un panneau de recherche sous forme d'onglet
	 * @param rechercheSWT
	 * @param titre - titre de l'onglet
	 */
	public void addRecherche(PaAideRechercheControllerSWT rechercheSWT, String titre) {
		this.vue.getTabPane().addTab(titre, null, rechercheSWT.getVue(), null);
		controllerVue.put(rechercheSWT.getVue(), rechercheSWT);
		tabPopups=rechercheSWT.tabPopupsAide;
		initPopupAndButtons(mapActions,tabPopups);
		
		//ActionOK sur double click
		rechercheSWT.getVue().getJdbTable().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				super.mouseClicked(e);
				if(e.getClickCount()==2) {
					actionOK.actionPerformed(new ActionEvent(this,0,null));
				}
			}
		});
	}
	
	public ResultAffiche configPanel(ParamAffiche param){
		if(param!=null) {
			this.appelant = ((ParamAfficheAide)param).getAppelant();
			if(param.getFocus()!=null)setFocusCourantHorsApplication(param.getFocus());
			else setFocusCourantHorsApplication(((PaAideRecherche)vue.getTabPane().getSelectedComponent()).getTfChoix());
		}
		if (getFocusCourant()!=null)
			getFocusCourant().requestFocus();
		return null;
	}

	public void RecupFocusAppelant(){
	if (appelant!=null)  {
		if(getFocusCourantApplication()!=null)
			if (!getFocusCourantApplication().equals(appelant))
				setFocusCourantApplication(appelant);
	}
	if(getFocusCourantApplication()!=null)
		getFocusCourantApplication().requestFocus();
	}
	
	/**
	 * Initialisation des composants graphiques de la vue.
	 * @throws ExceptLgr 
	 */
	private void initComposantsVue() throws ExceptLgr {
		/////BtnOK		
		vue.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
				KeyStroke.getKeyStroke(KeyEvent.VK_F3, 0), "Valider");
		vue.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
				KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "Valider");
		vue.getActionMap().put("Valider", actionOK);
		
		vue.getBtnOK().getInputMap(JComponent.WHEN_FOCUSED).put(
				KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "Valider"); 
		vue.getBtnOK().getActionMap().put("Valider", actionOK); 
		
		//////BtnNouveau		
		vue.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
				KeyStroke.getKeyStroke(KeyEvent.VK_F6, 0), "Nouveau");
		vue.getActionMap().put("Nouveau", actionNouveau); 
		
		vue.getBtnNouveau().getInputMap(JComponent.WHEN_FOCUSED).put(
				KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "Nouveau"); 
		vue.getBtnNouveau().getActionMap().put("Nouveau", actionNouveau); 
		
	    //////	BtnAnnuler
		vue.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
				KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "Annuler"); 
		vue.getActionMap().put("Annuler", actionAnnuler); 
		
		vue.getBtnAnnuler().getInputMap(JComponent.WHEN_FOCUSED).put(
				KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "Annuler"); 
		vue.getBtnAnnuler().getActionMap().put("Annuler", actionAnnuler); 
}

	private void initActions() {
		// formulaire Adresse
		if (mapActions == null)
			mapActions = new LinkedHashMap();
		mapActions.put(vue.getBtnAnnuler(), actionAnnuler);
		mapActions.put(vue.getBtnOK(), actionOK);
		mapActions.put(vue.getBtnNouveau(), actionNouveau);
	}
	
	public void addRetourEcranListener(RetourEcranListener l) {
		listenerList.add(RetourEcranListener.class, l);
	}
	
	protected void fireRetourEcran(RetourEcranEvent e) {
		super.fireRetourEcran(e);
		retourOK = true;
	}
	
	private PaAideControllerSWT getThis() {
		return this;
	}

	class ActionOK extends AbstractAction {
		public ActionOK(String name) {
			super(name);
		}
		
		public void actionPerformed(ActionEvent e) {
			try {
				if (logger.isDebugEnabled())
					logger.debug("Debug : ActionOK - actionPerformed");
				//Aide aide = Aide.createAide();
				final Variant v = new Variant();
				final Variant vId = new Variant();
				((PaAideRechercheControllerSWT)controllerVue.get(((PaAideRecherche)vue.getTabPane().getSelectedComponent()))).
				getQuery().getVariant(
						((PaAideRechercheControllerSWT)controllerVue.get(((PaAideRecherche)vue.getTabPane().getSelectedComponent()))).getChampsRechercheInitial(),
						((PaAideRechercheControllerSWT)controllerVue.get(((PaAideRecherche)vue.getTabPane().getSelectedComponent()))).getQuery().getRow(),
						v);
				
				((PaAideRechercheControllerSWT)controllerVue.get(((PaAideRecherche)vue.getTabPane().getSelectedComponent()))).
				getQuery().getVariant(
						((PaAideRechercheControllerSWT)controllerVue.get(((PaAideRecherche)vue.getTabPane().getSelectedComponent()))).getChampsId(),
						((PaAideRechercheControllerSWT)controllerVue.get(((PaAideRecherche)vue.getTabPane().getSelectedComponent()))).getQuery().getRow(),
						vId);
		//		appelant.getAide().setChoix(v.toString());
				getThis().getParent().getDisplay().syncExec(new Runnable(){

					public void run() {
						// TODO Raccord de méthode auto-généré
						fireRetourEcran(new RetourEcranEvent(getThis(),new ResultAffiche(v.toString(),vId.toString())));						
					}					
				});
				swingClose();
				RecupFocusAppelant();				
			} catch (Exception e1) {
				logger.error("Erreur : actionPerformed", e1);
			}
		}
	}

	class ActionAnnuler extends AbstractAction {
		public ActionAnnuler(String name) {
			super(name);
		}
		
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() != null) {
				swingClose();
				RecupFocusAppelant();
			} else
				System.out.println("e.getSource = null"); //$NON-NLS-1$

		}
	}
	
	class ActionNouveau extends AbstractAction {
		public ActionNouveau(String name) {
			super(name);
		}
		
		public void actionPerformed(ActionEvent e) {
			try {
				logger.info("ActionNouveau");
//				Object o
//				=  ((PaAideRechercheController)controllerVue.get(((PaAideRecherche)vue.getTabPane().getSelectedComponent()))).
//				refCreation.getConstructors()[0].newInstance(new Object[]{null});
//				((fr.legrain.lib.gui.BaseController)o).show();
				
				((PaAideRechercheControllerSWT)controllerVue.get(((PaAideRecherche)vue.getTabPane().getSelectedComponent()))).refCreation.addRetourEcranListener(getThis());
				if (!((PaAideRechercheControllerSWT)controllerVue.get(((PaAideRecherche)vue.getTabPane().getSelectedComponent()))).getQuery().isOpen())
					((PaAideRechercheControllerSWT)controllerVue.get(((PaAideRecherche)vue.getTabPane().getSelectedComponent()))).getQuery().open();
				parent.getDisplay().syncExec(new Thread() {
					public void run() {
						LgrShellUtil.affiche(
								((PaAideRechercheControllerSWT)controllerVue.get(((PaAideRecherche)vue.getTabPane().getSelectedComponent()))).paramEcranCreation,
								null, 
								((PaAideRechercheControllerSWT)controllerVue.get(((PaAideRecherche)vue.getTabPane().getSelectedComponent()))).refCreation.getVue(), 
								((PaAideRechercheControllerSWT)controllerVue.get(((PaAideRecherche)vue.getTabPane().getSelectedComponent()))).refCreation, 
								getParent());
					}
				});
				setFocusCourant(((PaAideRecherche)vue.getTabPane().getSelectedComponent()).getTfChoix());
				//setFocusCourantHorsApplication(((PaAideRecherche)vue.getTabPane().getSelectedComponent()).getTfChoix());

			} catch (Exception e1) {
				logger.error("Erreur : actionPerformed", e1);
			}
		}
	}

	public void retourEcran(RetourEcranEvent evt) {
		if(getFocusCourant()!=null)
			getFocusCourant().requestFocus();
		logger.debug("retour creation : "+evt.getRetour().toString());
		if (!((PaAideRechercheControllerSWT)controllerVue.get(((PaAideRecherche)vue.getTabPane().getSelectedComponent()))).getQuery().isOpen())
			((PaAideRechercheControllerSWT)controllerVue.get(((PaAideRecherche)vue.getTabPane().getSelectedComponent()))).getQuery().open();
		((PaAideRechercheControllerSWT)controllerVue.get(((PaAideRecherche)vue.getTabPane().getSelectedComponent()))).refresh();
		vue.getTabPane().getSelectedComponent().requestFocus();
		//#JPA
		//((PaAideRechercheControllerSWT)controllerVue.get(((PaAideRecherche)vue.getTabPane().getSelectedComponent()))).locate(((ResultAide)evt.getRetour()).getIdChamps(),((ResultAide)evt.getRetour()).getIdValeur());
	}
	
	public void setFocusCourant(JComponent focusCourant) {
		setFocusCourantHorsApplication(focusCourant) ;
	}

}
