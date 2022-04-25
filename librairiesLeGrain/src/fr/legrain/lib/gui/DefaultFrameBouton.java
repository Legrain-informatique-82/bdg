package fr.legrain.lib.gui;
import java.awt.BorderLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import com.borland.dbswing.JdbTable;

import fr.legrain.lib.gui.grille.LgrJdbTable;

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
public class DefaultFrameBouton extends JPanel {
	protected JPanel paBtn;
	protected JButton btnModifier;
	protected JPopupMenu popupMenuFormulaire;
	protected JButton btnInserer;
	protected JButton btnImprimer;
	protected JButton btnFermer;
	protected JButton btnSupprimer;
	protected JButton btnEnregistrer;
	protected JButton btnAnnuler;

	public DefaultFrameBouton() {
		super();
		initGUI();
	}
	
	private void initGUI() {
		try {
			BorderLayout thisLayout = new BorderLayout();
			this.setLayout(thisLayout);
			this.setPreferredSize(new java.awt.Dimension(679, 390));
			{
				paBtn = new JPanel();
				this.add(paBtn, BorderLayout.SOUTH);
				paBtn.setBorder(BorderFactory.createEtchedBorder(BevelBorder.LOWERED));
				{
					btnAnnuler = new JButton();
					paBtn.add(btnAnnuler);
					btnAnnuler.setText("Annuler");
					btnAnnuler.setVerifyInputWhenFocusTarget(false);
				}
				{
					btnEnregistrer = new JButton();
					paBtn.add(btnEnregistrer);
					btnEnregistrer.setText("Enregistrer");
				}
				{
					btnInserer = new JButton();
					paBtn.add(btnInserer);
					btnInserer.setText("Insérer");
				}
				{
					btnModifier = new JButton();
					paBtn.add(btnModifier);
					btnModifier.setText("Modifier");
				}
				{
					btnSupprimer = new JButton();
					paBtn.add(btnSupprimer);
					btnSupprimer.setText("Supprimer");
				}
				{
					btnFermer = new JButton();
					paBtn.add(btnFermer);
					btnFermer.setText("Fermer");
					btnFermer.setVerifyInputWhenFocusTarget(false);
				}
				{
					btnImprimer = new JButton();
					paBtn.add(btnImprimer);
					btnImprimer.setText("Imprimer");
				}
			}
			{
				popupMenuFormulaire = new JPopupMenu();
				setComponentPopupMenu(this, popupMenuFormulaire);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Auto-generated method for setting the popup menu for a component
	 */
	private void setComponentPopupMenu(final java.awt.Component parent, final javax.swing.JPopupMenu menu) {
		parent.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mousePressed(java.awt.event.MouseEvent e) {
				if(e.isPopupTrigger())
					menu.show(parent, e.getX(), e.getY());
			}
			public void mouseReleased(java.awt.event.MouseEvent e) {
				if(e.isPopupTrigger())
					menu.show(parent, e.getX(), e.getY());
			}
		});
	}
	
	public JButton getBtnAnnuler() {
		return btnAnnuler;
	}
	
	public void setBtnAnnuler(JButton btnAnnuler) {
		this.btnAnnuler = btnAnnuler;
	}
	
	public JButton getBtnEnregistrer() {
		return btnEnregistrer;
	}
	
	public void setBtnEnregistrer(JButton btnEnregistrer) {
		this.btnEnregistrer = btnEnregistrer;
	}
	
	public JButton getBtnFermer() {
		return btnFermer;
	}
	
	public void setBtnFermer(JButton btnFermer) {
		this.btnFermer = btnFermer;
	}
	
	public JButton getBtnImprimer() {
		return btnImprimer;
	}
	
	public void setBtnImprimer(JButton btnImprimer) {
		this.btnImprimer = btnImprimer;
	}
	
	public JButton getBtnModifier() {
		return btnModifier;
	}
	
	public void setBtnModifier(JButton btnModifier) {
		this.btnModifier = btnModifier;
	}
	
	public JButton getBtnSupprimer() {
		return btnSupprimer;
	}
	
	public void setBtnSupprimer(JButton btnSupprimer) {
		this.btnSupprimer = btnSupprimer;
	}
	
	
	
	public JPanel getPaBtn() {
		return paBtn;
	}
	
	public void setPaBtn(JPanel paBtn) {
		this.paBtn = paBtn;
	}
	
	
	public JPopupMenu getPopupMenuFormulaire() {
		return popupMenuFormulaire;
	}
	
	public void setPopupMenuFormulaire(JPopupMenu popupMenu) {
		this.popupMenuFormulaire = popupMenu;
	}

	public JButton getBtnInserer() {
		return btnInserer;
	}

	public void setBtnInserer(JButton btnInserer) {
		this.btnInserer = btnInserer;
	}

}
