package fr.legrain.calcultva.controller;


import java.util.ArrayList;
import java.util.LinkedHashMap;

import org.apache.log4j.Logger;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Menu;


import fr.legrain.calcultva.divers.ParamAideCalcTVA;
import fr.legrain.calcultva.divers.ResultAideCalcTVA;
import fr.legrain.calcultva.ecran.PaAideCalcTVASWT;
import fr.legrain.gestCom.librairiesEcran.swt.JPABaseControllerSWTStandard;
import fr.legrain.gestCom.librairiesEcran.swt.SWTBaseControllerSWTStandard;
import fr.legrain.lib.data.ExceptLgr;
import fr.legrain.lib.data.LibCalcul;
import fr.legrain.lib.data.LibChaine;
import fr.legrain.lib.data.LibConversion;
import fr.legrain.lib.data.ModeObjet;
import fr.legrain.lib.gui.ParamAffiche;
import fr.legrain.lib.gui.ResultAffiche;
import fr.legrain.lib.gui.RetourEcranEvent;
import fr.legrain.lib.gui.RetourEcranListener;


public class PaAideCalcTVAController extends JPABaseControllerSWTStandard implements
RetourEcranListener {
	
	
	static Logger logger = Logger.getLogger(PaAideCalcTVAController.class.getName());		
	private PaAideCalcTVASWT vue = null; // vue	
	private ResultAideCalcTVA result = new ResultAideCalcTVA();
	
	private Object ecranAppelant = null;
	
	public PaAideCalcTVAController(PaAideCalcTVASWT vue) {
		try {
			setVue(vue);
			this.vue=vue;
			vue.getShell().addShellListener(this);
			
			vue.getShell().addTraverseListener(new Traverse());
						
		
			initController();
			
		} catch (Exception e) {
			logger.error("Erreur ", e);
		}
		
	}
	
	
	private void initController() {
		try {
			initMapComposantChamps();
			initMapComposantDecoratedField();
			listeComponentFocusableSWT(listeComposantFocusable);
			initFocusOrder();
			initActions();
			initDeplacementSaisie(listeComposantFocusable);

			branchementBouton();

			Menu popupMenuFormulaire = new Menu(vue.getShell(), SWT.POP_UP);
			Menu popupMenuGrille = new Menu(vue.getShell(), SWT.POP_UP);
			Menu[] tabPopups = new Menu[] { popupMenuFormulaire,popupMenuGrille };
			this.initPopupAndButtons(mapActions, tabPopups);


			actionEnregistrer.setText("Valider F3");
			
			
			vue.getTfTaux().addFocusListener(new Doc());
			vue.getTfMtTTC().addFocusListener(new Doc());
			vue.getTfMtHT().addFocusListener(new Doc());
			vue.getTfMtTVA().addFocusListener(new Doc());
			
			initEtatBouton();
		} catch (Exception e) {
//			vue.getLaMessage().setText(e.getMessage());
			logger.error("Erreur : PaArticlesController", e);
		}
	}
	
	
	
	public ResultAffiche configPanel(ParamAffiche param){
		if (param!=null){
			if (((ParamAideCalcTVA)param).getFocusSWT()!=null)
				((ParamAideCalcTVA)param).getFocusSWT().forceFocus();
			
			if(param.getEcranAppelant()!=null) {
				ecranAppelant = param.getEcranAppelant();
			}
			if(((ParamAideCalcTVA)param).getTaux()!=null) 
				vue.getTfTaux().setText(((ParamAideCalcTVA)param).getTaux()); 
			else 
				vue.getTfTaux().setText(String.valueOf(LibCalcul.arrondi(new Double(0))));
			
			if(((ParamAideCalcTVA)param).getMtTTC()!=null) 
				vue.getTfMtTTC().setText(((ParamAideCalcTVA)param).getMtTTC()); 
			else 
				vue.getTfMtTTC().setText(String.valueOf(LibCalcul.arrondi(new Double(0))));
			
			if(((ParamAideCalcTVA)param).getMtHT()!=null) 
				vue.getTfMtHT().setText(((ParamAideCalcTVA)param).getMtHT()); 
			else 
				vue.getTfMtHT().setText(String.valueOf(LibCalcul.arrondi(new Double(0))));
			
			if(((ParamAideCalcTVA)param).getMtTVA()!=null) 
				vue.getTfMtTVA().setText(((ParamAideCalcTVA)param).getMtTVA()); 
			else 
				vue.getTfMtTVA().setText(String.valueOf(LibCalcul.arrondi(new Double(0))));
		
		}
		return null;
	}
	
	/**
	 * Initialisation des composants graphiques de la vue.
	 * @throws ExceptLgr 
	 */
	protected void initComposantsVue() throws ExceptLgr {
	}
	
	/**
	 * Initialisation des boutons suivant l'état de l'objet "ibTaTable"
	 */
	protected void initEtatBouton() {}	
	
	
	/**
	 * Initialisation des correspondances entre les champs de formulaire et les
	 * champs de bdd
	 */
	protected void initMapComposantChamps() {
		if (listeComposantFocusable == null) 
			listeComposantFocusable = new ArrayList();
		
		if (mapComposantChamps == null) 
			mapComposantChamps = new LinkedHashMap();
		listeComposantFocusable.add(vue.getTfTaux());
		listeComposantFocusable.add(vue.getTfMtTTC());
		listeComposantFocusable.add(vue.getTfMtHT());
		listeComposantFocusable.add(vue.getTfMtTVA());			
		
		listeComposantFocusable.add(vue.getPaBtnReduit1().getBtnImprimer());
		listeComposantFocusable.add(vue.getPaBtnReduit1().getBtnFermer());
		vue.getPaBtnReduit1().getBtnImprimer().setText("Valider [F3]");
		if(mapInitFocus == null) 
			mapInitFocus = new LinkedHashMap();
		mapInitFocus.put(ModeObjet.EnumModeObjet.C_MO_CONSULTATION,vue.getTfTaux());
		mapInitFocus.put(ModeObjet.EnumModeObjet.C_MO_INSERTION,vue.getTfTaux());
		mapInitFocus.put(ModeObjet.EnumModeObjet.C_MO_EDITION,vue.getTfTaux());
		
		//activeDocumentListener();
	}
	
	
	
	protected void initActions() {
		initCommands();
		if (mapActions == null)
			mapActions = new LinkedHashMap();
		
		mapActions.put(vue.getPaBtnReduit1().getBtnFermer(), actionFermer);
		mapActions.put(vue.getPaBtnReduit1().getBtnImprimer(), actionEnregistrer);
		
		Object[] tabActionsAutres = new Object[] { actionAide };
		mapActions.put(null, tabActionsAutres);
		
	}
	
	
	
	public PaAideCalcTVAController getThis(){
		return this;
	}
	
	@Override
	public boolean onClose() throws ExceptLgr {
		boolean retour = true;
		
		if(retour) {
			if(!LibChaine.empty(vue.getTfTaux().getText()))
				result.setTaux(vue.getTfTaux().getText());
			else
				result.setTaux("0.0");
			
			if(!LibChaine.empty(vue.getTfMtTTC().getText()))
				result.setMtTTC(vue.getTfMtTTC().getText());
			else
				result.setMtTTC("0.0");
			
			if(!LibChaine.empty(vue.getTfMtHT().getText()))
				result.setMtHT(vue.getTfMtHT().getText());
			else
				result.setMtHT("0.0");
			
			if(!LibChaine.empty(vue.getTfMtTVA().getText()))
				result.setMtTVA(vue.getTfMtTVA().getText());
			else
				result.setMtTVA("0.0");
			
			fireRetourEcran(new RetourEcranEvent(this,result));
//			vue.getDisplay().asyncExec(new Runnable() {
//				public void run() {
//					vue.getShell().setVisible(false);
//				}
//			});
		} 
//		else {
//			parent.setEnabled(true);
//			setFocusCourantSWT(vue.getTfTaux());
//			getFocusCourant().requestFocus();
//		}
		
		return retour;
	}
	
	public void retourEcran(RetourEcranEvent evt) {
		super.retourEcran(evt);
	}
	
	@Override
	protected void actInserer() throws Exception{}
	
	@Override
	protected void actModifier() throws Exception{}
	
	@Override
	protected void actSupprimer() throws Exception{}
	
	@Override
	protected void actFermer() throws Exception {
		// (controles de sortie et fermeture de la fenetre) => onClose()
		vue.getShell().close();
	}
	
	@Override
	protected void actAnnuler() throws Exception{
		actionFermer.run();
	}
	
	@Override
	protected void actImprimer() throws Exception{
		actionFermer.run();
	}

	@Override
	protected void actAide()  throws Exception {}

	@Override
	protected void actAide(String message) throws Exception {}

	@Override
	protected void actEnregistrer() throws Exception{
		actionFermer.run();
	}
	
	@Override
	public void initEtatComposant() {}

	
	protected class Doc implements org.eclipse.swt.events.FocusListener {
		double taux;
		double ttc;
		double ht;
		double tva;
		
		double ancienTaux;
		double ancienTtc;
		double ancientHt;
		double ancienTva;
		
		public void initValeur() {
			taux = LibConversion.stringToDouble(vue.getTfTaux().getText(),new Double(0));
			ttc = LibConversion.stringToDouble(vue.getTfMtTTC().getText(),new Double(0));
			ht = LibConversion.stringToDouble(vue.getTfMtHT().getText(),new Double(0));
			tva = LibConversion.stringToDouble(vue.getTfMtTVA().getText(),new Double(0));
		}
		
		public void modif(org.eclipse.swt.events.FocusEvent e) {
			try {
				
				initValeur();
				if(ancienTaux!=taux || ancienTtc!=ttc || ancientHt!=ht || ancienTva!=tva) {
					//modif du HT
					if (e.getSource().equals(vue.getTfMtHT())) {
						if(taux!=0) {
							ttc = ht+(ht*(taux/100));
							tva=ht*(taux/100);
						} else if(ttc!=0 && ht!=0) {
							tva=ttc-ht;
							taux=tva*(100/ht);
						} else if(tva!=0 && ht!=0) {
							ttc=ht+tva;
							taux=tva*(100/ht);
						}
					}
					//modif du TTC
					else if(e.getSource().equals(vue.getTfMtTTC()) ) {
						if(taux!=0) {
							ht=(100*ttc)/(100+taux);
							tva=ttc-ht;
						} else if(ht!=0 ) {
							tva=ttc-ht;
							taux=tva*(100/ht);
						} else if(tva!=0 && ht!=0) {
							ht=ttc-tva;
							taux=tva*(100/ht);
						}
					} 
					//modif de la TVA
					else if(e.getSource().equals(vue.getTfMtTVA()) ) {
						if(taux!=0) {
							ht=tva*(100/taux);
							ttc=ht+(ht*taux/100);
						} else if(ht!=0 ) {
							ttc = ht+tva;
							taux = tva*(100/ht);
						} else if(ttc!=0 && ht!=0) {
							ht = ttc-tva;
							taux=tva*(100/ht);
						}
					} 
					//modif du taux
					else if(e.getSource().equals(vue.getTfTaux()) ) {
						if(ttc!=0) {
							ht=(100*ttc)/(100+taux);
							tva=ttc-ht;
						} else if(ht!=0) {
							ttc = ht+(ht*(taux/100));
							tva=ht*(taux/100);
						} else if(tva!=0 && taux!=0) {
							ht=tva*(100/taux);
							ttc=ht+(ht*taux/100);
						}
					}
					
					ht = LibCalcul.arrondi(ht);
					ttc = LibCalcul.arrondi(ttc);
					tva = LibCalcul.arrondi(tva);
					
					//vue.getTfTaux().setText(String.valueOf(taux));
					vue.getTfMtTTC().setText(String.valueOf(ttc));
					vue.getTfMtHT().setText(String.valueOf(ht));
					vue.getTfMtTVA().setText(String.valueOf(tva));
				}
				
			} catch (Exception e1) {
				logger.error(e1);
			}
		}
		
	
		public void focusLost(org.eclipse.swt.events.FocusEvent e) {
			modif(e);
		}

		public void focusGained(org.eclipse.swt.events.FocusEvent e) {
			org.eclipse.swt.events.FocusEvent fe =e;
			if(vue.getTfMtHT().getText().equals("0.0") && !vue.getTfMtTTC().getText().equals("0.0")){
				Event e1 = new Event();
				e1.widget=vue.getTfMtTTC();
				fe=new org.eclipse.swt.events.FocusEvent(e1);
				}
			else
				if(!vue.getTfMtHT().getText().equals("0.0")){
					Event e1 = new Event();
					e1.widget=vue.getTfMtHT();
					fe=new org.eclipse.swt.events.FocusEvent(e1);
					}else
					if( !vue.getTfMtTVA().getText().equals("0.0")){
						Event e1 = new Event();
						e1.widget=vue.getTfMtTVA();
						fe=new org.eclipse.swt.events.FocusEvent(e1);
						};
			modif(fe);
			ancienTaux = LibConversion.stringToDouble(vue.getTfTaux().getText(),new Double(0));
			ancienTtc = LibConversion.stringToDouble(vue.getTfMtTTC().getText(),new Double(0));
			ancientHt = LibConversion.stringToDouble(vue.getTfMtHT().getText(),new Double(0));
			ancienTva = LibConversion.stringToDouble(vue.getTfMtTVA().getText(),new Double(0));
			
			if(e.widget.equals(vue.getTfMtTTC()))vue.getTfMtTTC().setSelection(0,vue.getTfMtTTC().getLineHeight());
			else if(e.widget.equals(vue.getTfMtHT()))vue.getTfMtHT().setSelection(0,vue.getTfMtHT().getLineHeight());
			else if(e.widget.equals(vue.getTfMtTVA()))vue.getTfMtTVA().setSelection(0,vue.getTfMtTVA().getLineHeight());
			else if(e.widget.equals(vue.getTfTaux()))vue.getTfTaux().setSelection(0,vue.getTfTaux().getLineHeight());
		}

	}


	@Override
	protected void actRefresh() throws Exception {
		// TODO Raccord de méthode auto-généré
		
	}


	@Override
	protected void initMapComposantDecoratedField() {
		// TODO Raccord de méthode auto-généré
		
	}


	@Override
	public Composite getVue() {
		// TODO Raccord de méthode auto-généré
		return null;
	}


	@Override
	protected void sortieChamps() {
		// TODO Raccord de méthode auto-généré
		
	}
	
	
}
