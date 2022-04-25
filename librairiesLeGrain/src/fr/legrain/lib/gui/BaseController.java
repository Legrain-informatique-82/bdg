package fr.legrain.lib.gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Font;
import java.awt.Frame;
import java.awt.KeyboardFocusManager;
import java.awt.event.ActionEvent;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.text.*;

import org.apache.log4j.Logger;
import org.eclipse.swt.events.ShellAdapter;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Text;

import com.borland.dbswing.JdbTable;

import fr.legrain.lib.data.*;
import fr.legrain.lib.gui.aide.PaAide;
import fr.legrain.lib.gui.aide.PaAideRecherche;
import fr.legrain.lib.gui.grille.LgrDbTable;


/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: Le Grain SA</p>
 * @author nicolas
 * @version 1.0
 */

public abstract class BaseController {
	
	protected JPanel vue = null; // vue
	private JComponent focusCourant=null; //composant qui possède le focus actuellement
	private static JComponent focusCourantApplication=null; //composant qui possède le focus actuellement
	static Logger logger = Logger.getLogger(BaseController.class.getName());
	private static Component focusedContainer = null; //parent/Container (Swing) le plus éloigné du JComponent qui possède le focus actuellement
	static DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
	public BaseController() {
	}
	
	/**
	 * Configuration du JPanel
	 */
	abstract public ResultAffiche configPanel(ParamAffiche param);

	
	public void requestFocusGeneral(Component composant){
		//logger.info("RequestFocus : "+composant.getName());
		setFocusCourant((JComponent)composant);
		getFocusCourant().requestFocus();
	}
	
	/**
	 * Rempli les champs d'un formulaire à partir de l'enregistrement courant dans <code>IBQuLgr</code>
	 * @param m Hashtable - clé="composant graphique", valeur="nom du champs dans la bdd"
	 * @param req IBQuLgr - DataSet dans lequel chercher la valeur
	 * @param initRequete boolean - vrai chargement a partir de la <code>req</code>
	 */
	public void remplirFormulaire(Map m, IBQuLgr req, boolean initRequete) {
		//se positionner sur la bonne ligne dans la base de données
		//transfert de la requete vers la liste
		boolean verrouille=VerrouInterface.getVerrouInterface().isVerrouille();
		VerrouInterface.getVerrouInterface().setVerrouille(true);
		try{
			if (initRequete) 
				req.getQuery_Champ_Obj();
			
			Iterator iteValues = m.values().iterator();
			Iterator iteKeys   = m.keySet().iterator();
			Object composant, valeur;
			
			//récupération des infos de la liste vers l'IHM
			while(iteKeys.hasNext()){
				valeur = iteValues.next();
				composant = iteKeys.next();
				if(composant!=null) {
					if(valeur==null) {
						valeur = LgrConstantes.C_STR_VIDE;
					}
					if (composant instanceof JTextComponent ) { //jtextfield, jtextarea, ...
						try {
							if (composant instanceof JFormattedTextField) {
								( (JFormattedTextField) composant).setValue(req.getChamp_Obj(valeur.toString()));
							} else {
//								final JTextComponent composantTemp = (JTextComponent) composant;
//								final Object valeurTemp = valeur;
//								final IBQuLgr reqTemp = req;
//								SwingUtilities.invokeLater(new Runnable() {
//								public void run() {
//								VerrouInterface.setVerrouille(true);
								( (JTextComponent) composant).setText(req.getChamp_Obj(valeur.toString()));
//								VerrouInterface.setVerrouille(false);
//								}
//								});
							}
						} catch(IllegalStateException e){
							logger.warn("remplirFormulaire - IllegalStateException",e);
						}
					}
					else if (composant instanceof AbstractButton) { //jbutton, jcheckbox, ...
						/** @todo A changer suivant les valeurs choisies pour le domaine booleen d'IB */
						if(req.getChamp_Obj(valeur.toString())!=null && !req.getChamp_Obj(valeur.toString()).equals("")){
							if (Integer.valueOf(req.getChamp_Obj(valeur.toString())).intValue() ==0)
								( (AbstractButton) composant).setSelected(false);
							else if (Integer.valueOf(req.getChamp_Obj(valeur.toString())).intValue() == 1)
								( (AbstractButton) composant).setSelected(true);
						}else ( (AbstractButton) composant).setSelected(false);
					}
				}
			}
			
			if(req.getListeChampsCalcules()!=null) {
				//TODO a faire
			}
		}finally{
			if (!verrouille)VerrouInterface.getVerrouInterface().setVerrouille(false);
		}
	}
	
	//TODO mettre en abstract
	/**
	 * Affichage d'un ecran avec une vue par défaut
	 */
	/*abstract*/ public void show() {
		//new BaseController().showDefault();
	}
	
	/**
	 * @return - Vue gérée par ce controller
	 */
	abstract public JPanel getVue();
	
	protected void setVue(JPanel vue) {
		this.vue = vue;
		changeCouleur(vue);
	}
	
	//TODO a tester
	/**
	 * A Tester
	 */
	static public String stringValue(JComponent input) {
		if(input!=null) {
			if (input instanceof ChampDateLgr) { //jtextfield, jtextarea, ...
				try{
          	  return format.format(LibDate.stringToDate(((JFormattedTextField) input).getText()));
				}catch (Exception e) {
					// todo: handle exception
				}
				}
			else 
			if (input instanceof JTextComponent) { //jtextfield, jtextarea, ...
				return ( (javax.swing.text.JTextComponent) input).getText();
			}
			else if (input instanceof AbstractButton) { //jbutton, jcheckbox, ...
				/** @todo A changer suivant les valeurs choisies pour le domaine booleen d'IB */
				if ( ( (AbstractButton) input).isSelected())
					return "true";
				else
					return "false";
			}
		}
		return null;
	}
	
	/**
	 * Initialisation des popups et boutons
	 */
	public void initPopupAndButtons (Map m, JPopupMenu []popups) {
		Iterator iteKeyActions = m.keySet().iterator();
//		for (int j = 0; j < popups.length; j++) {
//		popups[j].removeAll();
//		}
		while(iteKeyActions.hasNext()) {			
			Object keyCourante = (Object)iteKeyActions.next();
			if (keyCourante == null){
				//on traite les actions autres qui ne sont pas liées à des boutons
				Object [] Tab =((Object [])m.get(keyCourante)); 
				for(int i = 0; i < Tab.length; i++)
					if(Tab[i] != null){
						for (int j = 0; j < popups.length; j++) {
							popups[j].add((AbstractAction)Tab[i]);
						}				    	   
					}
			}
			else {
				for (int j = 0; j < popups.length; j++) {
					popups[j].add((AbstractAction)m.get(keyCourante));
				}				    	   
				((JButton)keyCourante).setAction((AbstractAction)m.get(keyCourante));
			}
		}			    
	}
	
	/**
	 * Positionnement du focus en fonction du mode de l'écran
	 * @param ibTaTable - Ensemble de données utilisé dans l'écran du controller
	 * @param focus - [clé : mode] [valeur : composant qui à le focus par défaut pour ce mode]
	 */
	protected void initFocus(IBQuLgr ibTaTable, Map<ModeObjet.EnumModeObjet,JComponent> focus) {
		switch (ibTaTable.getFModeObjet().getMode()) {
		case C_MO_INSERTION:
			if (ibTaTable.getFModeObjet().getFocusCourant()==null)
				ibTaTable.getFModeObjet().setFocusCourant(focus.get(ModeObjet.EnumModeObjet.C_MO_INSERTION));
			break;
		case C_MO_EDITION:
			if (ibTaTable.getFModeObjet().getFocusCourant()==null){
				if (focusCourant instanceof JTextComponent){
					if (((JTextComponent)focusCourant).isEditable())
					ibTaTable.getFModeObjet().setFocusCourant(focusCourant);
				}
				if (focusCourant instanceof JdbTable){
					if (((JdbTable)focusCourant).isEditable())
					ibTaTable.getFModeObjet().setFocusCourant(focusCourant);
				}					
			}
			if (ibTaTable.getFModeObjet().getFocusCourant()==null)
				ibTaTable.getFModeObjet().setFocusCourant(focus.get(ModeObjet.EnumModeObjet.C_MO_EDITION));
			break;
		default:
			ibTaTable.getFModeObjet().setFocusCourant(focus.get(ModeObjet.EnumModeObjet.C_MO_CONSULTATION));
		break;
		}
		setFocusCourant(ibTaTable.getFModeObjet().getFocusCourant());
		if(ibTaTable.getFModeObjet().getFocusCourant()!=null)
			ibTaTable.getFModeObjet().getFocusCourant().requestFocus();
	}
	
	/**
	 * Initialisation des touches qui déclenchent le transfert du focus au composant suivant
	 * @param listeComponent - composants focusable
	 */
	public void initDeplacementSaisie(List<JComponent> listeComponent) {		
		HashSet setForward = new HashSet();
		setForward.add(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0));
	//	setForward.add(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0));
		setForward.add(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0));
		setForward.add(KeyStroke.getKeyStroke(KeyEvent.VK_TAB, 0));
		
		HashSet setForwardBtn = new HashSet();
		setForwardBtn.add(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0));
		setForwardBtn.add(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0));
		setForwardBtn.add(KeyStroke.getKeyStroke(KeyEvent.VK_TAB, 0));
		
		HashSet setBackward = new HashSet();
	//	setBackward.add(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0));
		setBackward.add(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0));
		setBackward.add(KeyStroke.getKeyStroke(KeyEvent.VK_TAB, KeyEvent.SHIFT_DOWN_MASK));
		
		HashSet setBackwardBtn = new HashSet();
		setBackwardBtn.add(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0));
		setBackwardBtn.add(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0));
		setBackwardBtn.add(KeyStroke.getKeyStroke(KeyEvent.VK_TAB, KeyEvent.SHIFT_DOWN_MASK));
		
		for (JComponent component : listeComponent) {
			if (!(component instanceof JTable) ) { //jtextfield, jtextarea, ...
				if (component instanceof JButton) {//if (component instanceof AbstractButton) {
					component.setFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS,setForwardBtn);
					component.setFocusTraversalKeys(KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS,setBackwardBtn);
				} else {
					component.setFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS,setForward);
					component.setFocusTraversalKeys(KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS,setBackward);
				}
				//component.setFocusTraversalKeys(KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS,setBackward);			
				}			
		}	
		changeCouleur(vue);
	}
	
	public void initDeplacementSaisie(List<JComponent> listeComponent, ModeObjet.EnumModeObjet mode) {
		HashSet setForwardTextArea = new HashSet();
		if(!mode.equals(ModeObjet.EnumModeObjet.C_MO_EDITION)
				&& !mode.equals(ModeObjet.EnumModeObjet.C_MO_INSERTION))
			setForwardTextArea.add(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0));
		setForwardTextArea.add(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0));
		setForwardTextArea.add(KeyStroke.getKeyStroke(KeyEvent.VK_TAB, 0));
		
		HashSet setForward = new HashSet();
		setForward.add(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0));
		if(!mode.equals(ModeObjet.EnumModeObjet.C_MO_EDITION)
				&& !mode.equals(ModeObjet.EnumModeObjet.C_MO_INSERTION))
		setForward.add(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0));
		setForward.add(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0));
		setForward.add(KeyStroke.getKeyStroke(KeyEvent.VK_TAB, 0));
		
		HashSet setForwardBtn = new HashSet();
		setForwardBtn.add(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0));
		setForwardBtn.add(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0));
		setForwardBtn.add(KeyStroke.getKeyStroke(KeyEvent.VK_TAB, 0));
		
		HashSet setBackward = new HashSet();
		if(!mode.equals(ModeObjet.EnumModeObjet.C_MO_EDITION)
				&& !mode.equals(ModeObjet.EnumModeObjet.C_MO_INSERTION))
		setBackward.add(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0));
		setBackward.add(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0));
		setBackward.add(KeyStroke.getKeyStroke(KeyEvent.VK_TAB, KeyEvent.SHIFT_DOWN_MASK));
		
		for (JComponent component : listeComponent) {
			if (!(component instanceof JTable) ) { //jtextfield, jtextarea, ...
				if (component instanceof JButton) { //if (component instanceof AbstractButton) {
					component.setFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS,setForwardBtn);					
				} else if (component instanceof JTextArea){
					component.setFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS,setForwardTextArea);
				} else {
					component.setFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS,setForward);
				}
				component.setFocusTraversalKeys(KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS,setBackward);			
			}			
		}	
	}
	
	/**
	 * Listes des composants focusable. Nécessaire pour la gestion de la propriété <code>focusCourant</code>
	 * @param listeComponent
	 */
	public void listeComponentFocusable(List<JComponent> listeComponent){
		
		VueFocusListener vueFocusListener=new VueFocusListener();
		for (JComponent components : listeComponent) {
			components.addFocusListener(vueFocusListener);
		}		
	}
	
	/**
	 * Sélectionne tout le texte du JTextComponent passé en paramètre
	 * @param c - final JTextComponent
	 */
	public void selectAll(final JTextComponent c) {
		c.setSelectionStart(0);
		c.setSelectionEnd(c.getDocument().getLength());
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				c.setSelectionStart(0);
				c.setSelectionEnd(c.getDocument().getLength());
				//c.update(c.getGraphics());
			}
		});
	}
	
	//static private Border bordureSelection = BorderFactory.createLineBorder(Color.RED,1);
	static private Border bordureSelection = new LineBorder(Color.RED,2,true);
	private Border ancienneBordure = null;
	
	/**
	 * Mise à jour de la propriété <code>focusCourant</code>
	 */
	private class VueFocusListener implements FocusListener{
		//public VueFocusListener(){}
		public void focusGained(FocusEvent e) {
			setFocusCourant((JComponent)e.getComponent());
			//focusCourant=((JComponent)e.getComponent());
//			focusCourant.getGraphics().setColor(Color.RED);
//			//focusCourant.getGraphics().drawString(BaseController.this.toString(), focusCourant.getBounds().x, focusCourant.getBounds().y);
//			focusCourant.getGraphics().drawRect(
//			focusCourant.getX(),
//			focusCourant.getY(), 
//			focusCourant.getWidth(),
//			focusCourant.getHeight());
			
			ancienneBordure = focusCourant.getBorder();
			focusCourant.setBorder(bordureSelection);
			
			if(e.getComponent() instanceof JTextComponent) {
				selectAll((JTextComponent)e.getComponent());
				((JTextComponent)e.getComponent()).setBackground(LgrConstantes.champsPointe);
			}
		}
		
		public void focusLost(FocusEvent e) {
			// TODO Raccord de méthode auto-généré
//			((JComponent)e.getComponent()).setBorder(null);
			((JComponent)e.getComponent()).setBorder(ancienneBordure);
			if(e.getComponent() instanceof JTextComponent) {
				selectAll((JTextComponent)e.getComponent());
				((JTextComponent)e.getComponent()).setBackground(LgrConstantes.fondCompSaisie);
			}
		}
		
	}
	
	/**
	 * Initialisation des couleurs d'un Container ainsi que de tous les composants
	 * qu'il contient
	 * @param c - Container dont n souhaite changer la couleur
	 */
	public void changeCouleur(Container c) {
//		Color fondPanel = new Color(251,250,247); //gris blanc
//		Color fondCompSaisie = new Color(239,237,233);  //gris/orange très clair
//		Color fondBouton = new Color(238,155,0); //orange
//		Color quadrillage = new Color(199,195,187); //et bordure bouton => gris 
//		Color champsPointe = new Color(242,230,207); //beige/orange clair
//		Color blanc = new Color(255,255,255);
//		Color noir = new Color(0,0,0);
//		Border bordureBtn = new LineBorder(quadrillage,1,true);
//		Border bordureChampsSasie = new LineBorder(fondBouton,1,true);
		
		if(c==null) c = this.getVue();
		
		for (int i = 0; i < c.getComponents().length; i++) {
			//Affectation des couleurs suivant le composant
			if(c.getComponents()[i] instanceof JPanel) {
				((JPanel)c.getComponents()[i]).setBackground(LgrConstantes.fondPanel);
			} else if(c.getComponents()[i] instanceof JScrollPane) {
					((JScrollPane)c.getComponents()[i]).setBackground(LgrConstantes.fondPanel);
			} else if(c.getComponents()[i] instanceof JViewport) {
				((JViewport)c.getComponents()[i]).setBackground(LgrConstantes.fondPanel);
			} else if(c.getComponents()[i] instanceof JButton) {
				((JButton)c.getComponents()[i]).setBorder(LgrConstantes.bordureBtn);
				//((JButton)c.getComponents()[i]).setBorder(BorderFactory.createEmptyBorder(1,1,1,1));
				((JButton)c.getComponents()[i]).setBackground(LgrConstantes.fondBouton);
				((JButton)c.getComponents()[i]).setForeground(LgrConstantes.blanc);
				//A cause du style Win XP
				((JButton)c.getComponents()[i]).setContentAreaFilled(false);
				((JButton)c.getComponents()[i]).setOpaque(true);
			} else if(c.getComponents()[i] instanceof JTextComponent) {
				((JTextComponent)c.getComponents()[i]).setBackground(LgrConstantes.fondCompSaisie);
				((JTextComponent)c.getComponents()[i]).setBorder(LgrConstantes.bordureChampsSasie);
			} else if(c.getComponents()[i] instanceof JCheckBox) {
				((JCheckBox)c.getComponents()[i]).setBackground(LgrConstantes.fondPanel); 
			} else if(c.getComponents()[i] instanceof JTable) {
				((JTable)c.getComponents()[i]).getTableHeader().setBackground(LgrConstantes.fondCompSaisie);
				((JTable)c.getComponents()[i]).setBackground(LgrConstantes.fondCompSaisie);
				((JTable)c.getComponents()[i]).setGridColor(LgrConstantes.quadrillage);
				((JTable)c.getComponents()[i]).setSelectionBackground(LgrConstantes.fondBouton);
			} 
			
			//Appel récursif sur les containers
			if(c.getComponents()[i] instanceof Container) {
				changeCouleur((Container)c.getComponents()[i]);
			}
			
			//Modification des titres pour les bordures avec titre
			if(c.getComponents()[i] instanceof JComponent) {
				if(((JComponent)c.getComponents()[i]).getBorder()!=null) {
					if(((JComponent)c.getComponents()[i]).getBorder() instanceof TitledBorder) {
						((TitledBorder)((JComponent)c.getComponents()[i]).getBorder()).setTitleColor(LgrConstantes.fondBouton);
						((TitledBorder)((JComponent)c.getComponents()[i]).getBorder()).setTitle(((TitledBorder)((JComponent)c.getComponents()[i]).getBorder()).getTitle().toUpperCase());
						((TitledBorder)((JComponent)c.getComponents()[i]).getBorder()).setTitleFont(new Font(null,Font.BOLD,12));
					}
				}
			}
		}

	}
	
	public JComponent getFocusCourant() {
		return focusCourant;
	}
	
	public void setFocusCourant(JComponent focusCourant) {
		this.focusCourant = focusCourant;
		
		if (focusCourant!=null){
		if(focusCourant instanceof JButton);
		else
			this.focusCourantApplication = focusCourant;
		
		focusedContainer = focusCourant.getParent();
		while(focusedContainer.getParent()!=null)
			focusedContainer = focusedContainer.getParent();
		}
		LgrConstantes.setFocusedLgr(focusedContainer);
	}
	
	public void setFocusCourantHorsApplication(JComponent focusCourant) {
		this.focusCourant = focusCourant;
		if (focusCourant!=null){
		focusedContainer = focusCourant.getParent();
		while(focusedContainer.getParent()!=null)
			focusedContainer = focusedContainer.getParent();
		}
		LgrConstantes.setFocusedLgr(focusedContainer);
	}
	
	

	public static Component getFocusedContainer() {
		return focusedContainer;
	}

	public static JComponent getFocusCourantApplication() {
		return focusCourantApplication;
	}

	public static void setFocusCourantApplication(JComponent focusCourantApplication) {
		BaseController.focusCourantApplication = focusCourantApplication;
	}




	
}

