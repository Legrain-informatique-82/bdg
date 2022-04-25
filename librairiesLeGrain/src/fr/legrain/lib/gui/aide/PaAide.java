package fr.legrain.lib.gui.aide;
import com.borland.dbswing.JdbTable;
import java.awt.BorderLayout;

import java.awt.Dimension;
import javax.swing.JButton;

import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTabbedPane;

import org.apache.log4j.Logger;

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
public class PaAide extends javax.swing.JPanel {
	private JPanel paBtn;
	private JButton btnOK;
	private JTabbedPane tabPane;
	private JLabel laTitre;
	private JPanel paTitre;
	private JButton btnNouveau;
	private JButton btnAnnuler;
	
	static Logger logger = Logger.getLogger(PaAide.class.getName());
	/**
	 * Auto-generated main method to display this 
	 * JPanel inside a new JFrame.
	 */
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.getContentPane().add(new PaAide());
		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
	}
	
	public PaAide() {
		super();
		initGUI();
	}
	
	private void initGUI() {
		try {
			BorderLayout thisLayout = new BorderLayout();
			this.setLayout(thisLayout);
			setPreferredSize(new Dimension(400, 300));
			this.setVerifyInputWhenFocusTarget(false);
			{
				paBtn = new JPanel();
				this.add(paBtn, BorderLayout.SOUTH);
				{
					btnAnnuler = new JButton();
					paBtn.add(btnAnnuler);
					btnAnnuler.setText("Annuler");
				}
				{
					btnOK = new JButton();
					paBtn.add(btnOK);
					btnOK.setText("Valider");
				}
				{
					btnNouveau = new JButton();
					paBtn.add(btnNouveau);
					btnNouveau.setText("Nouveau");
				}
			}
			{
				paTitre = new JPanel();
				this.add(paTitre, BorderLayout.NORTH);
				{
					laTitre = new JLabel();
					paTitre.add(laTitre);
					laTitre.setText("Aide");
				}
			}
			{
				tabPane = new JTabbedPane();
				this.add(tabPane, BorderLayout.CENTER);
			}
		} catch (Exception e) {
			logger.error("Erreur : initGUI", e);
		}
	}
	
	public JButton getBtnNouveau() {
		return btnNouveau;
	}
	
	public void setBtnNouveau(JButton btnNouveau) {
		this.btnNouveau = btnNouveau;
	}
	
	public JButton getBtnOK() {
		return btnOK;
	}
	
	public void setBtnOK(JButton btnOK) {
		this.btnOK = btnOK;
	}
	
	public JLabel getLaTitre() {
		return laTitre;
	}
	
	public void setLaTitre(JLabel laTitre) {
		this.laTitre = laTitre;
	}
	
	public JPanel getPaBtn() {
		return paBtn;
	}
	
	public void setPaBtn(JPanel paBtn) {
		this.paBtn = paBtn;
	}
	
	public JPanel getPaTitre() {
		return paTitre;
	}
	
	public void setPaTitre(JPanel paTitre) {
		this.paTitre = paTitre;
	}
	
	public JTabbedPane getTabPane() {
		return tabPane;
	}
	
	public void setTabPane(JTabbedPane tabPane) {
		this.tabPane = tabPane;
	}
	
	public JButton getBtnAnnuler() {
		return btnAnnuler;
	}
	
	public void setBtnAnnuler(JButton btnAnnuler) {
		this.btnAnnuler = btnAnnuler;
	}

}
