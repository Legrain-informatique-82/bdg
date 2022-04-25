package Interface;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

/**
* This code was edited or generated using CloudGarden's Jigloo
* SWT/Swing GUI Builder, which is free for non-commercial
* use. If Jigloo is being used commercially (ie, by a corporation,
* company or business for any purpose whatever) then you
* should purchase a license for each developer using Jigloo.
* Please visit www.cloudgarden.com for details.
* Use of Jigloo implies acceptance of these licensing terms.
* A COMMERCIAL LICENSE HAS NOT BEEN PURCHASED FOR
* THIS MACHINE, SO JIGLOO OR THIS CODE CANNOT BE USED
* LEGALLY FOR ANY CORPORATE OR COMMERCIAL PURPOSE.
*/
public class InterFaceMains extends JFrame {
	private JLabel jLabel1;
	private JLabel jLabel2;
	private JLabel jLabel3;
	private JButton jButtonAnnuler;
	private JButton jButtonValider;
	private JButton jButtonPath2;
	private JTextField jTextFieldPath2;
	private JButton jButtonPath1;
	private JTextField jTextFieldPath1;

	/**
	 * Create the frame
	 */
	public InterFaceMains() {
		super();
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.rowWeights = new double[] {0.1, 0.1, 0.1, 0.1};
		gridBagLayout.rowHeights = new int[] {7, 7, 7, 7};
		gridBagLayout.columnWeights = new double[] {0.1, 0.1, 0.1, 0.1};
		gridBagLayout.columnWidths = new int[] {7, 7, 7, 7};
		getContentPane().setLayout(gridBagLayout);
		setTitle("Analyse XML des Plugins");
		setBounds(100, 100, 706, 546);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		{
			jLabel1 = new JLabel();
			getContentPane().add(jLabel1, new GridBagConstraints(1, 0, 2, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
			jLabel1.setText("Choix Path Feature");
			jLabel1.setBorder(BorderFactory.createTitledBorder(""));
			jLabel1.setFont(new java.awt.Font("Tahoma",1,14));
			jLabel1.setHorizontalAlignment(SwingConstants.CENTER);
		}
		{
			jLabel2 = new JLabel();
			getContentPane().add(jLabel2, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
			jLabel2.setText("Path 1 :");
			jLabel2.setHorizontalAlignment(SwingConstants.CENTER);
			jLabel2.setFont(new java.awt.Font("Tahoma",1,11));
		}
		{
			jTextFieldPath1 = new JTextField();
			jTextFieldPath1.setEditable(false);
			getContentPane().add(getJTextFieldPath1(), new GridBagConstraints(1, 1, 2, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
		}
		{
			jButtonPath1 = new JButton();
			getContentPane().add(getJButtonPath1(), new GridBagConstraints(3, 1, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 25, 0));
			jButtonPath1.setText("Open Eclipse   ");
			//jButtonPath1.setSize(140, 21);
		}
		{
			jLabel3 = new JLabel();
			getContentPane().add(jLabel3, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
			jLabel3.setText("Path 2 :");
			jLabel3.setFont(new java.awt.Font("Tahoma",1,11));
		}
		{
			jTextFieldPath2 = new JTextField();
			jTextFieldPath2.setEditable(false);
			getContentPane().add(getJTextFieldPath2(), new GridBagConstraints(1, 2, 2, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
		}
		{
			jButtonPath2 = new JButton();
			getContentPane().add(getJButtonPath2(), new GridBagConstraints(3, 2, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 35, 0));
			jButtonPath2.setText("Open Workspace");
			//jButtonPath2.setSize(140, 21);
		}
		{
			jButtonValider = new JButton();
			getContentPane().add(getJButtonValider(), new GridBagConstraints(1, 3, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 25, 0));
			jButtonValider.setText("Valider");
		}
		{
			jButtonAnnuler = new JButton();
			getContentPane().add(getJButtonAnnuler(), new GridBagConstraints(2, 3, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 25, 0));
			jButtonAnnuler.setText("Annuler");
		}

//		AjouteElemnet objetAjouteElemnet = new AjouteElemnet(this);
//		objetAjouteElemnet.addMenu("File", "Open");
		//setJMenuBar(menuBar);

//		final JMenu menu = new JMenu();
//		menu.setText("New SubMenu");
//		menuBar.add(menu);
//
//		final JMenuItem newItemMenuItem = new JMenuItem();
//		newItemMenuItem.setText("New Item");
//		menu.add(newItemMenuItem);
		//
	}
	
	public JTextField getJTextFieldPath1() {
		return jTextFieldPath1;
	}
	
	public JButton getJButtonPath1() {
		return jButtonPath1;
	}
	
	public JTextField getJTextFieldPath2() {
		return jTextFieldPath2;
	}
	
	public JButton getJButtonPath2() {
		return jButtonPath2;
	}
	
	public JButton getJButtonValider() {
		return jButtonValider;
	}
	
	public JButton getJButtonAnnuler() {
		return jButtonAnnuler;
	}

	public void setJButtonAnnuler(JButton buttonAnnuler) {
		jButtonAnnuler = buttonAnnuler;
	}

	public void setJButtonValider(JButton buttonValider) {
		jButtonValider = buttonValider;
	}

	public void setJButtonPath2(JButton buttonPath2) {
		jButtonPath2 = buttonPath2;
	}

	public void setJTextFieldPath2(JTextField textFieldPath2) {
		jTextFieldPath2 = textFieldPath2;
	}

	public void setJButtonPath1(JButton buttonPath1) {
		jButtonPath1 = buttonPath1;
	}

	public void setJTextFieldPath1(JTextField textFieldPath1) {
		jTextFieldPath1 = textFieldPath1;
	}

}
