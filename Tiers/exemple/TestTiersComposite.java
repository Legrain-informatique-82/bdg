package fr.legrain.tiers.shells;

import fr.legrain.appli.tiers.formlayout.PaTiers2;
import fr.legrain.appli.tiers.formlayout.PaTiersController;

import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.Panel;
import javax.swing.JButton;

import org.eclipse.swt.SWT;
import org.eclipse.swt.awt.SWT_AWT;
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
public class TestTiersComposite extends Composite {
//public class TestSWTRCPEditor extends Shell {

	public static final String ID = "RCPSwing.testSWTRCPEditor"; // TODO Needs to be whatever is mentioned in plugin.xml

	private JButton jButton1;
	private PaTiers2 paTiers21;
	private Panel panel1;
	private Frame frame1;
	private Composite composite1;
	private PaTiersController paTiersController;

	public PaTiers2 getPaTiers21() {
		return paTiers21;
	}
	
	public TestTiersComposite(Shell parent, int style) {
		super(parent, style);
		initGUI();
		paTiersController = new PaTiersController(paTiers21);
	}
	
	private void initGUI() {
		try {
			FillLayout thisLayout = new FillLayout(
				org.eclipse.swt.SWT.HORIZONTAL);
			this.setLayout(thisLayout);
			this.setSize(563, 265);
			{
				composite1 = new Composite(this, SWT.EMBEDDED);
				FillLayout composite1Layout = new FillLayout(
					org.eclipse.swt.SWT.HORIZONTAL);
				composite1.setLayout(composite1Layout);
				{
					frame1 = SWT_AWT.new_Frame(composite1);
					composite1.setData(frame1);
					{
						panel1 = new Panel();
						BorderLayout panel1Layout = new BorderLayout();
						panel1.setLayout(panel1Layout);
						frame1.add(panel1);
						{
							paTiers21 = new PaTiers2();
							panel1.add(getPaTiers21(), BorderLayout.CENTER);
							paTiers21.setPreferredSize(new java.awt.Dimension(
								579,
								379));
						}
						{
							jButton1 = new JButton();
							panel1.add(jButton1, BorderLayout.NORTH);
							jButton1.setText("jButton1");
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}  //  @jve:decl-index=0:visual-constraint="38,86"
