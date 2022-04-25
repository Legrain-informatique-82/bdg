package fr.legrain.gestCom.librairiesEcran.swt;

import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.Panel;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JPanel;
import javax.swing.event.EventListenerList;

import org.apache.log4j.Logger;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.awt.SWT_AWT;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.ShellAdapter;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;



/**
* This code was generated using CloudGarden's Jigloo
* SWT/Swing GUI Builder, which is free for non-commercial
* use. If Jigloo is being used commercially (ie, by a corporation,
* company or business for any purpose whatever) then you
* should purchase a license for each developer using Jigloo.
* Please visit www.cloudgarden.com for details.
* Use of Jigloo implies acceptance of these licensing terms.
* *************************************
* A COMMERCIAL LICENSE HAS NOT BEEN PURCHASED
* for this machine, so Jigloo or this code cannot be used legally
* for any corporate or commercial purpose.
* *************************************
*/
public class CompositeSwing extends Composite {
	static Logger logger = Logger.getLogger(CompositeSwing.class.getName());
	
	public static final String ID = "RCPSwing.testSWTRCPEditor"; // TODO Needs to be whatever is mentioned in plugin.xml

    private JPanel panelSwing;
    private BaseControllerSWT controllerDuPanel;

	private Frame frameAWT;
	private Composite compositeEmbedded;
	//private PaTiersController paTiersController;
	private Panel panelAWT; //Container AWT "lourd" pour pouvoir recevoir les évènements
	
	private boolean hasFocus = false;

	public JPanel getPanelSwing() {
		return panelSwing;
	}
	
	public CompositeSwing(Composite parent, int style, JPanel panelSwing) {
		super(parent, style);
		this.panelSwing = panelSwing;
		initGUI();
	}
	
	/**
	 * 
	 * @param parent
	 * @param style
	 * @param panelSwing - JPanel Swing qui doit être embarqué dans le Composite
	 */
	public CompositeSwing(Shell parent, int style, JPanel panelSwing) {
		super(parent, style);
		this.panelSwing = panelSwing;
		initGUI();
	}
	
	/**
	 * 
	 * @param parent
	 * @param style
	 * @param panelSwing - JPanel Swing qui doit être embarqué dans le Composite
	 */
	public CompositeSwing(Shell parent, int style, JPanel panelSwing, BaseControllerSWT controllerDuPanel) {
		super(parent, style);
		this.controllerDuPanel = controllerDuPanel;
		parent.addShellListener(new LgrShellAdapter());
		controllerDuPanel.setParent(parent);
		this.panelSwing = panelSwing;
		initGUI();
	}
	
	public CompositeSwing(Shell parent, int style) {
		super(parent, style);
		initGUI();
	}
	
	class LgrShellAdapter extends ShellAdapter {

//		@Override
//		public void shellActivated(ShellEvent e) {
//			// TODO Raccord de méthode auto-généré
//			super.shellActivated(e);
//			if (controllerDuPanel.getFocusCourant()!=null){
//				System.out.println(controllerDuPanel.getFocusCourant().getName());
//				controllerDuPanel.getFocusCourant().requestFocus();
//			}
//			
//		}

		public void shellClosed(ShellEvent e) {
			try {
			// TODO Raccord de méthode auto-généré
			//	 e.doit = false; // cancel the closing event, window remains open
			//	 shell.setVisible(false); 
			e.doit = controllerDuPanel.onClose();
			//MessageDialog.openInformation(null,"shellClosed","Fermeture");
			//e.doit = false;
			}
			catch (Exception ex) {
				logger.error("Erreur : shellClosed", ex);
			}
		}	
	}
	
	/**
	 * this -> Composite -> Frame -> Panel -> JPanel
	 */
	private void initGUI() {
		try {
			//this
			FillLayout thisLayout = new FillLayout(SWT.HORIZONTAL);
			this.setLayout(thisLayout);
			this.setSize(563, 265);
			//Composite
			compositeEmbedded = new Composite(this, SWT.EMBEDDED);
			FillLayout composite1Layout = new FillLayout(SWT.HORIZONTAL);
			compositeEmbedded.setLayout(composite1Layout);	
			//Frame AWT => composant lourd pour certains problèmes de focus
			frameAWT = SWT_AWT.new_Frame(compositeEmbedded);
			compositeEmbedded.setData(frameAWT);	
			//Panel AWT
			panelAWT = new Panel();
			BorderLayout panel1Layout = new BorderLayout();
			panelAWT.setLayout(panel1Layout);
			frameAWT.add(panelAWT);				
			//Ajout du JPanel Swing
			panelAWT.add(getPanelSwing(), BorderLayout.CENTER);	
			
			////////////////////////////////////////
//			panelAWT.addFocusListener(new FocusAdapter() {
//
//				public void focusGained(FocusEvent e) {
//					System.out.println("panelAWT - focusGained");
//				}
//
//				public void focusLost(FocusEvent e) {
//					System.out.println("panelAWT - focusLost");
//				}
//		    	 
//		     });
//			
//			frameAWT.addFocusListener(new FocusAdapter() {
//
//				public void focusGained(FocusEvent e) {
//					System.out.println("frameAWT - focusGained");
//				}
//
//				public void focusLost(FocusEvent e) {
//					System.out.println("frameAWT - focusLost");
//				}
//		    	 
//		     });
//			
//			this.addFocusListener(new FocusListener() {
//
//				public void focusGained(org.eclipse.swt.events.FocusEvent e) {
//					System.out.println("shell - focusGained");
//				}
//
//				public void focusLost(org.eclipse.swt.events.FocusEvent e) {
//					System.out.println("shell - focusLost");
//				}
//		    	 
//		     });
//			
//			compositeEmbedded.addFocusListener(new FocusListener() {
//
//				public void focusGained(org.eclipse.swt.events.FocusEvent e) {
//					System.out.println("compositeEmbedded - focusGained");
//					//panelSwing.requestFocus();
//				}
//
//				public void focusLost(org.eclipse.swt.events.FocusEvent e) {
//					System.out.println("compositeEmbedded - focusLost");
//				}
//		    	 
//		     });
//			
//			this.addFocusListener(new FocusListener() {
//
//				public void focusGained(org.eclipse.swt.events.FocusEvent e) {
//					System.out.println("compositeSwing - focusGained");
//				}
//
//				public void focusLost(org.eclipse.swt.events.FocusEvent e) {
//					System.out.println("compositeSwing - focusLost");
//				}
//		    	 
//		     });
			
		} catch (Exception e) {
			logger.error("Erreur : initGUI", e);
		}
	}

}
