package fr.legrain.tiers.poubelle;

import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.Panel;

import javax.swing.JPanel;

import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.awt.SWT_AWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.SWT;

import fr.legrain.gestCom.librairiesEcran.swt.BaseControllerSWT;


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
public class NewComposite extends org.eclipse.swt.widgets.Composite {
    public JPanel panelSwing = new JPanel();
    public Panel panel;
    public BaseControllerSWT controllerDuPanel;

    public Frame frameAWT;
    public Composite compositeEmbedded;
    public Panel panelAWT;

	public NewComposite(org.eclipse.swt.widgets.Composite parent, int style) {
		super(parent, style);
		initGUI();
	}

	private void initGUI() {
		try {
			FillLayout thisLayout = new FillLayout(SWT.HORIZONTAL);
			this.setLayout(thisLayout);
			this.setSize(563, 265);
			//Composite
			compositeEmbedded = new Composite(this, SWT.EMBEDDED);
			FillLayout composite1Layout = new FillLayout(SWT.HORIZONTAL);
			compositeEmbedded.setLayout(composite1Layout);	
			//Frame AWT => composant lourd pour certains problèmes de focus
			frameAWT = SWT_AWT.new_Frame(compositeEmbedded);
			frameAWT.setName("titre frameAWT");
			frameAWT.setFocusable(false);
			frameAWT.setFocusTraversalKeysEnabled(false);
			compositeEmbedded.setData(frameAWT);	
			//Panel AWT
			panelAWT = new Panel();
			panelAWT.setName("panelAWT");
			panelAWT.setFocusable(false);
			panelAWT.setFocusTraversalKeysEnabled(false);
			BorderLayout panel1Layout = new BorderLayout();
			panelAWT.setLayout(panel1Layout);
			frameAWT.add(panelAWT);				
			//Ajout du JPanel Swing
			panelAWT.add(panelSwing, BorderLayout.CENTER);	
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
