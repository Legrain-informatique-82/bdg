package fr.legrain.gestCom.librairiesEcran.swt;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.swing.AbstractAction;
import javax.swing.AbstractButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.EventListenerList;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import javax.swing.text.Element;
import javax.swing.text.JTextComponent;
import javax.swing.text.Position;

import org.apache.log4j.Logger;
import org.eclipse.swt.widgets.Control;

import com.borland.dbswing.JdbTable;
import com.borland.dx.dataset.DataChangeEvent;
import com.borland.dx.dataset.NavigationEvent;

import fr.legrain.lib.data.ChangeModeEvent;
import fr.legrain.lib.data.ChangeModeListener;
import fr.legrain.lib.data.ExceptLgr;
import fr.legrain.lib.data.IBQuLgr;
import fr.legrain.lib.data.LgrConstantes;
import fr.legrain.lib.data.LibChaine;
import fr.legrain.lib.data.LibConversion;
import fr.legrain.lib.data.ModeObjet;
import fr.legrain.lib.data.VerrouInterface;
import fr.legrain.lib.data.ModeObjet.EnumModeObjet;
import fr.legrain.lib.gui.BaseController;
import fr.legrain.lib.gui.DeclencheAideEvent;
import fr.legrain.lib.gui.DeclencheAideListener;
import fr.legrain.lib.gui.DestroyEvent;
import fr.legrain.lib.gui.DestroyListener;
import fr.legrain.lib.gui.ResultAffiche;
import fr.legrain.lib.gui.RetourEcranEvent;
import fr.legrain.lib.gui.SortieChampsEvent;
import fr.legrain.lib.gui.SortieChampsListener;
import fr.legrain.lib.gui.grille.LgrJdbTable;
import fr.legrain.lib.gui.grille.LgrJdbTableEvent;
import fr.legrain.lib.gui.grille.LgrJdbTableListener;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: Le Grain SA</p>
 * @version 1.0
 */

public abstract class BaseControllerSWTStandard extends BaseControllerSWT implements
ChangeModeListener,DeclencheAideListener,SortieChampsListener, LgrJdbTableListener {
	
	static Logger logger = Logger.getLogger(BaseControllerSWTStandard.class.getName());
	protected JPanel vue = null;
	protected IBQuLgr ibTaTableStandard=null;
	private LgrJdbTable grille = null;
	private boolean autoInsert = true; //insertion autommatique quand on arrive en fin de grille
	
	protected EventListenerList listenerList = new EventListenerList();
	protected ActionInserer actionInserer = new ActionInserer(LgrConstantes.C_LIB_BTNINSERER);  //$NON-NLS-1$
	protected ActionEnregistrer actionEnregistrer = new ActionEnregistrer(LgrConstantes.C_LIB_BTNENREGISTRER);
	protected ActionModifier actionModifier = new ActionModifier(LgrConstantes.C_LIB_BTNMODIFIER);
	protected ActionSupprimer actionSupprimer = new ActionSupprimer(LgrConstantes.C_LIB_BTNSUPPRIMER);
	protected ActionFermer actionFermer = new ActionFermer(LgrConstantes.C_LIB_BTNFERMER);
	protected ActionAnnuler actionAnnuler = new ActionAnnuler(LgrConstantes.C_LIB_BTNANNULER);
	protected ActionImprimer actionImprimer = new ActionImprimer(LgrConstantes.C_LIB_BTNIMPRIMER);
	protected ActionAide actionAide = new ActionAide(LgrConstantes.C_LIB_BTNAIDE);
	
	protected Doc documentListener; //surveille les modifications des champs reliés à la bdd
    
	// correspondance composant graphique/champs bdd
	protected Map<JComponent,String> mapComposantChamps = null;
	
	
	//composant pouvant prendre le focus
	protected List<JComponent> listeComposantFocusable = null;
	//Composant qui possède le focus par défaut suivant le mode
	protected Map<ModeObjet.EnumModeObjet,JComponent> mapInitFocus = null;
	protected Map<ModeObjet.EnumModeObjet,Control> mapInitFocusSWT = null;
	
	protected Map mapActions = null;
	
	abstract protected void actInserer(ActionEvent e) throws Exception;
	abstract protected void actEnregistrer(ActionEvent e) throws Exception;
	abstract protected void actModifier(ActionEvent e) throws Exception;
	abstract protected void actSupprimer(ActionEvent e) throws Exception;
	abstract protected void actFermer(ActionEvent e) throws Exception;
	abstract protected void actAnnuler(ActionEvent e) throws Exception;
	abstract protected void actImprimer(ActionEvent e) throws Exception;
	abstract protected void actAide(ActionEvent e) throws Exception;
	
	abstract protected void initActions();
	
	abstract protected void initComposantsVue() throws ExceptLgr;
		
	abstract protected void initMapComposantChamps();
	
	abstract public void initEtatComposant();
	
//	abstract protected void initEtatBouton();
	
	//abstract public void actualiserForm();
		
	//abstract public void resetForm();
	
	public BaseControllerSWTStandard() {
		super();
	}
	
	
	/**
	 * 
	 * @param initRequete boolean - vrai chargement a partir de la <code>req</code>
	 */
	public void remplirInterface(boolean initRequete) {
		remplirFormulaire(mapComposantChamps,ibTaTableStandard,initRequete);
		initEtatComposant();		
	}
	
	public void changementMode(ChangeModeEvent evt) {
		initDeplacementSaisie(listeComposantFocusable,evt.getNouveauMode());
	}
	
	protected class ActionInserer extends AbstractAction {
		public ActionInserer(String name) {
			super(name);
		}
		
		public void actionPerformed(ActionEvent e) {
			try{
				actInserer(e);
			} catch(Exception evt) {
				logger.error("",evt);
			}
		}
	}

	protected class ActionEnregistrer extends AbstractAction {
		public ActionEnregistrer(String name) {
			super(name);
		}
		
		public void actionPerformed(ActionEvent e) {
			try {
				actEnregistrer(e);
			} catch(Exception evt) {
				logger.error("",evt);
			}
		}
	}
	
	protected class ActionAnnuler extends AbstractAction {
		public ActionAnnuler(String name) {
			super(name);
		}
		
		public void actionPerformed(ActionEvent e) {
			try {
				actAnnuler(e);
			} catch(Exception evt) {
				logger.error("",evt);
			}
		}
	}
	
	protected class ActionSupprimer extends AbstractAction {
		public ActionSupprimer(String name) {
			super(name);
		}
		
		public void actionPerformed(ActionEvent e) {
			try {
				actSupprimer(e);
			} catch(Exception evt) {
				logger.error("",evt);
			}
		}
	}
	
	protected class ActionModifier extends AbstractAction {
		public ActionModifier(String name) {
			super(name);
		}
		
		public void actionPerformed(ActionEvent e) {
			try {
				actModifier(e);
			} catch(Exception evt) {
				logger.error("",evt);
			}
		}
	}
	
	protected class ActionFermer extends AbstractAction {
		public ActionFermer(String name) {
			super(name);
		}
		
		public void actionPerformed(ActionEvent e) {
			try {
				actFermer(e);
			} catch(Exception evt) {
				logger.error("",evt);
			}
		}
	}
	
	protected class ActionImprimer extends AbstractAction {
		public ActionImprimer(String name) {
			super(name);
		}
		
		public void actionPerformed(ActionEvent e) {
			try {
				actImprimer(e);
			} catch(Exception evt) {
				logger.error("",evt);
			}
		}
	}

	protected class ActionAide extends AbstractAction {
		public ActionAide(String name) {
			super(name);
		}
		
		public void actionPerformed(ActionEvent e) {
			try {
				actAide(e);
			} catch(Exception evt) {
				logger.error("",evt);
			}
		}
	}
	
	protected void initEtatBouton() {
		initEtatBouton(true);
	} 
	
	/**
	 * 
	 * @param initFocus - si vrai initialise le focus en fonction du mode
	 */
	protected void initEtatBouton(boolean initFocus) {
		boolean trouve =false;		
		if (ibTaTableStandard.getFIBQuery().isOpen()){
			trouve=ibTaTableStandard.getFIBQuery().getRowCount()>0;
		switch (ibTaTableStandard.getFModeObjet().getMode()) {
		case C_MO_INSERTION:
			actionInserer.setEnabled(false);
			actionModifier.setEnabled(false);
			actionEnregistrer.setEnabled(true);
			actionAnnuler.setEnabled(true);
			actionImprimer.setEnabled(false);
			actionFermer.setEnabled(true);
			actionSupprimer.setEnabled(false);
			actionAide.setEnabled(true);
			if (grille!=null)grille.setEnabled(false);
			//if (grille!=null)grille.setColumnSortEnabled(false);
			break;
		case C_MO_EDITION:
			actionInserer.setEnabled(false);
			actionModifier.setEnabled(false);
			actionEnregistrer.setEnabled(true);
			actionAnnuler.setEnabled(true);
			actionImprimer.setEnabled(false);
			actionFermer.setEnabled(true);
			actionSupprimer.setEnabled(false);
			actionAide.setEnabled(true);
			if (grille!=null)grille.setEnabled(false);
			//if (grille!=null)grille.setColumnSortEnabled(false);
			break;
		case C_MO_CONSULTATION:
			actionInserer.setEnabled(true);
			actionModifier.setEnabled(trouve);
			actionEnregistrer.setEnabled(false);
			actionAnnuler.setEnabled(true);
			actionImprimer.setEnabled(true);
			actionFermer.setEnabled(true);
			actionSupprimer.setEnabled(trouve);
			actionAide.setEnabled(true);
			if (grille!=null)grille.setEnabled(true);
			//if (grille!=null)grille.setColumnSortEnabled(false);
			break;
		default:
			break;
		}
		}
		initEtatComposant();
//		if(initFocus)
//			initFocus(ibTaTableStandard,mapInitFocus);	
	}	
	
	// boucle sur tous les champs avant l'enregistrement
	protected boolean ctrlTousLesChampsAvantEnregistrement(boolean avecRequestFocus) {
		boolean res = true;
		try {
			// Enregistrement des controles de sortie de champs des formulaires
			Iterator iteChamps = mapComposantChamps.keySet().iterator();
			while (iteChamps.hasNext()) {
				JComponent courant = (JComponent) iteChamps.next();
				if(avecRequestFocus==false){
					if(!verifySansRequestFocus(courant)){
						setFocusCourant(courant);
						throw new ExceptLgr();
						}
				}else{
				if (!(courant).getInputVerifier().verify(courant)){
					setFocusCourant(courant);
					throw new ExceptLgr();
					}
				}				
			}
			return res;
		} catch (ExceptLgr ex) {
			logger.error("Erreur : CtrlTousLesChampsAvantEnregistrement", ex); //$NON-NLS-1$
			return false;
		}
	}

	
	// boucle sur tous les champs avant l'enregistrement
	protected boolean ctrlTousLesChampsAvantEnregistrement() {
		return ctrlTousLesChampsAvantEnregistrement(true);
	}
	
	public boolean verifySansRequestFocus(JComponent input) {
		boolean res = true;
		try {
			logger.debug(input.getName());
			// ///////////////////////////////***********************/////////////////		
			
			String nomChamp = null;
			String nouvelleValeur = null;
			if (mapComposantChamps.get(input) != null) { // le champs est
				// relié à la bdd, il faut faire un controle
				nomChamp = mapComposantChamps.get(input).toString();
				nouvelleValeur = BaseController.stringValue(input);
				if (!VerrouInterface.isVerrouille()){
					if (ibTaTableStandard.getFModeObjet().getMode()!=EnumModeObjet.C_MO_CONSULTATION){
						res = ibTaTableStandard.verifChamp(nomChamp, nouvelleValeur,null,null);
					}
				}
			}
			//
			return res;
//			// /////////////////////////////////*******************///////////////////
		} catch (Exception e) {
			logger.error("Erreur : verifySansRequestFocus", e);
			return false;
		}
	}

	
	public void actualiserForm() {
		boolean verrouille=VerrouInterface.isVerrouille();
		try{
			VerrouInterface.setVerrouille(true);
			// System.out.println(ibTaTable.getFModeObjet().getMode());
			if ((ibTaTableStandard.getFModeObjet().getMode().compareTo(ModeObjet.EnumModeObjet.C_MO_INSERTION)!=0)
					&& (ibTaTableStandard.getFModeObjet().getMode().compareTo(ModeObjet.EnumModeObjet.C_MO_EDITION)!=0)) {
				remplirFormulaire(mapComposantChamps, ibTaTableStandard,true);
				initEtatComposant();	
			}
		} finally {
			VerrouInterface.setVerrouille(verrouille);
		}
	}	

	/**
	 * Vide le formulaire
	 */
	public void resetForm() {
		//SwingUtilities.invokeLater(new Thread() {
		//	public void run() {
				boolean verrouille=VerrouInterface.isVerrouille();
				try {
					VerrouInterface.setVerrouille(true);
					for (final JComponent components : mapComposantChamps.keySet()) {
						if (components  instanceof JTextComponent ) {
							((JTextComponent)components).setText(null);
						}
					}
					initEtatComposant();
				} catch(java.lang.IllegalStateException e) {
					//si le composant texte est en train d'etre editer par l'utilisateur IllegalStateException est levée
					if(logger.isDebugEnabled())
						logger.debug("resetForm()",e);
				} finally {	
					VerrouInterface.setVerrouille(verrouille);
				}
		//	}
		//});
	}	
	
	/**
	 * postRow
	 * @param event DataChangeEvent
	 */
	public void postRow(DataChangeEvent event) {
		actualiserForm();
		initEtatComposant();
	}
	
	/**
	 * dataChanged
	 * @param event DataChangeEvent
	 */
	public void dataChanged(DataChangeEvent event) {
		actualiserForm();
		initEtatComposant();		
	}
	
	public void navigated(NavigationEvent event) {
		// TODO actualisation après insertion, sinon vidage de la liste
		actualiserForm();
		initEtatComposant();
	}
	public List<JComponent> getListeComposantFocusable() {
		return listeComposantFocusable;
	}
	public void setListeComposantFocusable(List<JComponent> listeComposantFocusable) {
		this.listeComposantFocusable = listeComposantFocusable;
	}
	
	public void retourEcran(RetourEcranEvent evt) {
		if (getFocusCourantApplication()!=null && listeComposantFocusable.contains(getFocusCourantApplication()))
			getFocusCourantApplication().requestFocus();
			else setFocusCourant(getFocusCourant());  
	}
	
	public void declencheAide(DeclencheAideEvent evt) throws Exception {
		actionAide.actionPerformed(null);
	}
	
	public void sortieChamps(SortieChampsEvent evt) throws Exception {
		// TODO Raccord de méthode auto-généré
		initEtatComposant();
	}
	
	/**
	 * Ajout d'un filtre aux composants texte limitant le nombre de caractères que l'on
	 * peut saisir à la taille du champ dans la base de données.
	 */
	protected void initMaxLenthTextComponent() {
		for (JComponent jComponent : mapComposantChamps.keySet()) {
			if(jComponent instanceof JTextComponent) {
				AbstractDocument doc = (AbstractDocument)((JTextComponent)jComponent).getDocument();
				if(doc!=null && ibTaTableStandard.getFIBQuery().isOpen() && ibTaTableStandard.getFIBQuery().getColumn(mapComposantChamps.get(jComponent)).getPrecision()>0) {
					doc.setDocumentFilter(new FixedSizeFilter(ibTaTableStandard.getFIBQuery().getColumn(mapComposantChamps.get(jComponent)).getPrecision()));
				}
			} 
		}
	}

	private boolean champMaj(String nomChamp){
		boolean res = false;
//		if(mapComposantChampsMajuscule!=null){
//			for (JComponent components : mapComposantChampsMajuscule) {
//				if (components.equals(jComponent)) res= true;
//			}
//		}

     res= (ibTaTableStandard.getListeChampMaj().
    		 containsKey(ibTaTableStandard.nomTable + "." +nomChamp));
		return res;
	}
	
	protected void initMajTextComponent() {
		for (JComponent jComponent : mapComposantChamps.keySet()) {
			if(jComponent instanceof JTextComponent) {
				AbstractDocument doc = (AbstractDocument)((JTextComponent)jComponent).getDocument();
				if(doc!=null && ibTaTableStandard.getFIBQuery().isOpen() && champMaj(mapComposantChamps.get(jComponent))) {
					doc.setDocumentFilter(new MajFilter(true));
				}
			} 
		}
	}
	

	private class FixedSizeFilter extends DocumentFilter {
        int maxSize;
    
        // limit is the maximum number of characters allowed.
        public FixedSizeFilter(int limit) {
            maxSize = limit;
        }
    
        // This method is called when characters are inserted into the document
        public void insertString(DocumentFilter.FilterBypass fb, int offset, String str,
                AttributeSet attr) throws BadLocationException {
            replace(fb, offset, 0, str, attr);
        }
    
        // This method is called when characters in the document are replace with other characters
        public void replace(DocumentFilter.FilterBypass fb, int offset, int length,
                String str, AttributeSet attrs) throws BadLocationException {
        	int newLength = 0;
        	if(str!=null) {
        		newLength = fb.getDocument().getLength()-length+str.length();
        	}
        		if (newLength <= maxSize) {
        			fb.replace(offset, length, str, attrs);
        		} else {
        			throw new BadLocationException("New characters exceeds max size of document", offset);
        		}
        	
        }
    }
	

	private class MajFilter extends DocumentFilter {
        boolean maj;
    
        // limit is the maximum number of characters allowed.
        public MajFilter(boolean majuscule) {
            maj = majuscule;
        }
    
        // This method is called when characters are inserted into the document
        public void insertString(DocumentFilter.FilterBypass fb, int offset, String str,
                AttributeSet attr) throws BadLocationException {
            replace(fb, offset, 0, str, attr);
        }
    
        // This method is called when characters in the document are replace with other characters
        public void replace(DocumentFilter.FilterBypass fb, int offset, int length,
                String str, AttributeSet attrs) throws BadLocationException {
        	if(str!=null && !LibChaine.empty(str)) {
        		fb.replace(offset, length, str.toUpperCase(), attrs);
        	} 
        	else
    			fb.replace(offset, length, str, attrs);
       	
        }
    }
	/**
	 * Surveille les modification des champs reliés à la bdd.
	 * Passage automatique du dataset en mode modification.
	 * @author nicolas
	 */
	protected class Doc implements DocumentListener,ActionListener {
		
		private IBQuLgr dataset = null; //dataset à passer en édition/insertion
		
		public Doc() {
			this.dataset = ibTaTableStandard;
		}
		
		public Doc(IBQuLgr dataset) {
			this.dataset = dataset;
		}
		
		public void modifMode(DocumentEvent e) {
			try {
				if (!VerrouInterface.isVerrouille() ){
					if(!dataset.dataSetEnModif()) {
						//dataset.modifier();
						if(!dataset.getFIBQuery().isEmpty()) {
							actModifier(null);
						} else {
							actInserer(null);
							
						}
						initEtatBouton(false);
					}
				}
			} catch (Exception e1) {
				logger.error("",e1);
			}
		}
		
		//pour les textfields
		public void insertUpdate(DocumentEvent e) {
			if (!VerrouInterface.isVerrouille() ){
				modifMode(e);
			}
		}
		
		//pour les textfields
		public void removeUpdate(DocumentEvent e) {
			if (!VerrouInterface.isVerrouille() ){
				modifMode(e);
			}
		}
		
		//pour les textfields
		public void changedUpdate(DocumentEvent e) {
			if (!VerrouInterface.isVerrouille() ){
				modifMode(e);
			}
		}

		//pour les abstractButton
		public void actionPerformed(ActionEvent e) {
			if (!VerrouInterface.isVerrouille() ){
				modifMode(null);
			}
		}
	}
	
	/**
	 * Active l'écoute de tous les champs du controller qui sont reliés à la bdd,
	 * si le dataset n'est pas en modification et qu'un des champs est modifié, le dataset
	 * passera automatiquement en édition.
	 * @see desactiveDocumentListener
	 */
	public void activeDocumentListener() {
		for (JComponent jComponent : mapComposantChamps.keySet()) {
			if(jComponent instanceof JTextComponent) {
				((JTextComponent)jComponent).getDocument().addDocumentListener(documentListener);
			} else if(jComponent instanceof AbstractButton) {
				((AbstractButton)jComponent).addActionListener(documentListener);
			}
		}
		initMaxLenthTextComponent();
		initMajTextComponent();
	}
		
	/**
	 * Active l'écoute de tous les champs du controller qui sont reliés à la bdd.
	 * @see activeDocumentListener
	 */
	public void desactiveDocumentListener() {
		for (JComponent jComponent : mapComposantChamps.keySet()) {
			if(jComponent instanceof JTextComponent) {
				((JTextComponent)jComponent).getDocument().removeDocumentListener(documentListener);
			} else if(jComponent instanceof AbstractButton) {
				((AbstractButton)jComponent).removeActionListener(documentListener);
			}
		} 
	}
	
	public void dernierLigneAtteinte(LgrJdbTableEvent evt) {
		try {
			if(isAutoInsert()) {
				actInserer(null);
			}
		} catch (Exception e) {
			logger.error("",e);
		}
	}
	
	public LgrJdbTable getGrille() {
		return grille;
	}
	
	public void setGrille(LgrJdbTable grille) {
		this.grille = grille;
		grille.addLgrJdbTableListener(this);
	}
	
	public boolean isAutoInsert() {
		return autoInsert;
	}
	
	public void setAutoInsert(boolean autoInsert) {
		this.autoInsert = autoInsert;
	}
	
	
	public void addDestroyListener(DestroyListener l) {
		listenerList.add(DestroyListener.class, l);
	}
	
	public void removeDestroyListener(DestroyListener l) {
		listenerList.remove(DestroyListener.class, l);
	}
	public void fireDestroy(DestroyEvent e) {
		Object[] listeners = listenerList.getListenerList();
		for (int i = listeners.length - 2; i >= 0; i -= 2) {
			if (listeners[i] == DestroyListener.class) {
				if (e == null)
					e = new DestroyEvent(this);
				( (DestroyListener) listeners[i + 1]).destroy(e);
			}
		}
	}
}
