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

import fr.legrain.lib.data.LgrConstantes;
import fr.legrain.lib.gui.grille.LgrJdbTable;

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
public class DefaultFrameFormulaire extends JPanel {
	protected JPanel paTitre;
	protected JPanel paGrille;
	protected JPanel paBtn;
	protected JButton btnModifier;
	private JPopupMenu PopupMenuGrille;
	protected JPopupMenu popupMenuFormulaire;
	protected JScrollPane jScrollPaneGrille;
	protected JButton btnInserer;
	protected JPanel paCorpsFormulaire;
	protected JLabel laTitreFenetre;
	protected LgrJdbTable jdbGrille;
	protected JLabel laTitreGrille;
	protected JLabel laTitreFormulaire;
	protected JButton btnImprimer;
	protected JButton btnFermer;
	protected JButton btnSupprimer;
	protected JButton btnEnregistrer;
	protected JButton btnAnnuler;
	protected JPanel paFormulaire;
	
	public DefaultFrameFormulaire() {
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
					btnAnnuler.setText(LgrConstantes.C_LIB_BTNANNULER);
					btnAnnuler.setVerifyInputWhenFocusTarget(false);
				}
				{
					btnEnregistrer = new JButton();
					paBtn.add(btnEnregistrer);
					btnEnregistrer.setText(LgrConstantes.C_LIB_BTNENREGISTRER);
				}
				{
					btnInserer = new JButton();
					paBtn.add(btnInserer);
					btnInserer.setText(LgrConstantes.C_LIB_BTNINSERER);
				}
				{
					btnModifier = new JButton();
					paBtn.add(btnModifier);
					btnModifier.setText(LgrConstantes.C_LIB_BTNMODIFIER);
				}
				{
					btnSupprimer = new JButton();
					paBtn.add(btnSupprimer);
					btnSupprimer.setText(LgrConstantes.C_LIB_BTNSUPPRIMER);
				}
				{
					btnFermer = new JButton();
					paBtn.add(btnFermer);
					btnFermer.setText(LgrConstantes.C_LIB_BTNFERMER);
					btnFermer.setVerifyInputWhenFocusTarget(false);
				}
				{
					btnImprimer = new JButton();
					paBtn.add(btnImprimer);
					btnImprimer.setText(LgrConstantes.C_LIB_BTNIMPRIMER);
				}
			}
			{
				paTitre = new JPanel();
				this.add(paTitre, BorderLayout.NORTH);
				{
					laTitreFenetre = new JLabel();
					paTitre.add(laTitreFenetre);
					laTitreFenetre.setText("titre ecran");
				}
			}
			{
				paFormulaire = new JPanel();
				BorderLayout paFormulaireLayout = new BorderLayout();
				paFormulaire.setLayout(paFormulaireLayout);
				
				this.add(paFormulaire, BorderLayout.WEST);
				paFormulaire.setPreferredSize(new java.awt.Dimension(252, 324));
				paFormulaire.setBorder(BorderFactory.createEtchedBorder(BevelBorder.LOWERED));
				{
					paCorpsFormulaire = new JPanel();
					paFormulaire.add(paCorpsFormulaire, BorderLayout.CENTER);
			//		FormLayout paCorpsFormulaireLayout = new FormLayout(
//							"max(p;10px), 35dlu, 6dlu, 54dlu, 42dlu",
//					"max(p;5px), max(p;5px), max(p;5px), max(p;5px), max(p;5px), max(p;5px), max(p;5px), max(p;5px), max(p;5px), max(p;5px), max(p;5px), max(p;5px)");
//					paCorpsFormulaire.setLayout(paCorpsFormulaireLayout);
//					paCorpsFormulaire.setBounds(82, 218, 60, 30);
//					paCorpsFormulaire.setPreferredSize(new java.awt.Dimension(215, 302));
				}
				{
					laTitreFormulaire = new JLabel();
					paFormulaire.add(laTitreFormulaire, BorderLayout.NORTH);
					laTitreFormulaire.setText("titre formulaire");
					laTitreFormulaire
					.setHorizontalTextPosition(SwingConstants.CENTER);
					laTitreFormulaire
					.setHorizontalAlignment(SwingConstants.CENTER);
					laTitreFormulaire.setPreferredSize(new java.awt.Dimension(145, 18));
					laTitreFormulaire.setBounds(21, -142, 88, 280);
					laTitreFormulaire.setBorder(BorderFactory.createEtchedBorder(BevelBorder.LOWERED));
				}
			}
			{
				paGrille = new JPanel();
				BorderLayout paGrilleLayout = new BorderLayout();
				paGrille.setLayout(paGrilleLayout);
				this.add(paGrille, BorderLayout.CENTER);
				paGrille.setBorder(BorderFactory.createEtchedBorder(BevelBorder.LOWERED));
				paGrille.setPreferredSize(new java.awt.Dimension(449, 324));
				{
					PopupMenuGrille = new JPopupMenu();
					setComponentPopupMenu(paGrille, getPopupMenuGrille());
					PopupMenuGrille.setBounds(-27, -45, 10, 10);
				}
				{
					laTitreGrille = new JLabel();
					paGrille.add(laTitreGrille, BorderLayout.NORTH);
					laTitreGrille.setText("titre grille");
					laTitreGrille.setHorizontalAlignment(SwingConstants.CENTER);
				}
				{
					jScrollPaneGrille = new JScrollPane();
					paGrille.add(jScrollPaneGrille, BorderLayout.CENTER);
					jScrollPaneGrille.setPreferredSize(new java.awt.Dimension(423, 239));
					jScrollPaneGrille.setComponentPopupMenu(PopupMenuGrille);
					{
						TableModel jdbTitreModel = new DefaultTableModel(
							new String[][] { { "One", "Two" },
									{ "Three", "Four" } },
							new String[] { "Column 1", "Column 2" });
						jdbGrille = new LgrJdbTable();
						jScrollPaneGrille.setViewportView(jdbGrille);
						jdbGrille.setModel(jdbTitreModel);
						jdbGrille.setRowHeaderVisible(false);
						jdbGrille.setEditable(false);
						jdbGrille.setComponentPopupMenu(PopupMenuGrille);
						PopupMenuGrille.setAutoscrolls(true);
					}
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
	
	public LgrJdbTable getJdbGrille() {
		return jdbGrille;
	}
	
	public void setJdbGrille(LgrJdbTable jdbAdresse) {
		this.jdbGrille = jdbAdresse;
	}
	
	
	
	public JLabel getLaTitreFenetre() {
		return laTitreFenetre;
	}
	
	public void setLaTitreFenetre(JLabel laTitreFenetre) {
		this.laTitreFenetre = laTitreFenetre;
	}
	
	public JLabel getLaTitreFormulaire() {
		return laTitreFormulaire;
	}
	
	public void setLaTitreFormulaire(JLabel laTitreFormulaire) {
		this.laTitreFormulaire = laTitreFormulaire;
	}
	
	public JLabel getLaTitreGrille() {
		return laTitreGrille;
	}
	
	public void setLaTitreGrille(JLabel laTitreGrille) {
		this.laTitreGrille = laTitreGrille;
	}
	
	public JPanel getPaBtn() {
		return paBtn;
	}
	
	public void setPaBtn(JPanel paBtn) {
		this.paBtn = paBtn;
	}
	
	public JPanel getPaCorpsFormulaire() {
		return paCorpsFormulaire;
	}
	
	public void setPaCorpsFormulaire(JPanel paCorpsFormulaire) {
		this.paCorpsFormulaire = paCorpsFormulaire;
	}
	
	public JPanel getPaFormulaire() {
		return paFormulaire;
	}
	
	public void setPaFormulaire(JPanel paFormulaire) {
		this.paFormulaire = paFormulaire;
	}
	
	public JPanel getPaGrille() {
		return paGrille;
	}
	
	public void setPaGrille(JPanel paGrille) {
		this.paGrille = paGrille;
	}
	
	public JPanel getPaTitre() {
		return paTitre;
	}
	
	public void setPaTitre(JPanel paTitre) {
		this.paTitre = paTitre;
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
	
	public JPopupMenu getPopupMenuGrille() {
		return PopupMenuGrille;
	}

}
