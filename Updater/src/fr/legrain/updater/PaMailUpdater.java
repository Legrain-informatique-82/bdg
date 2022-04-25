package fr.legrain.updater;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JTextField;

import org.apache.log4j.Logger;

import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import javax.swing.JButton;

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
public class PaMailUpdater extends fr.legrain.lib.gui.DefaultFrameFormulaire {
	private JLabel laNomMAJ;
	private JLabel laDateMAJ;
	private JCheckBox cbAFaire;
	private JButton btnMAJ;
	private JLabel laAFaire;
	private JTextField tfDateMAJ;
	private JTextField tfNomMAJ;
	static Logger logger = Logger.getLogger(PaMailUpdater.class.getName());
	public PaMailUpdater() {
		super();
		initGUI();
	}
	
	private void initGUI() {
		try {
			FormLayout paCorpsFormulaireLayout = new FormLayout(
				"2dlu, 53dlu, 13dlu, 56dlu:grow, 8dlu",
				"max(p;5px), max(p;5px), max(p;5px), max(p;5px), max(p;5px), max(p;5px), max(p;5px), max(p;5px), max(p;5px), max(p;5px)");
			this.setPreferredSize(new java.awt.Dimension(581, 450));
			super.getPaCorpsFormulaire().setLayout(paCorpsFormulaireLayout);
			{
				tfNomMAJ = new JTextField();
				super.getPaCorpsFormulaire().add(
					tfNomMAJ,
					new CellConstraints("4, 2, 1, 1, default, default"));
			}
			{
				tfDateMAJ = new JTextField();
				super.getPaCorpsFormulaire().add(
					tfDateMAJ,
					new CellConstraints("4, 4, 1, 1, default, default"));
			}
			{
				laAFaire = new JLabel();
				super.getPaCorpsFormulaire().add(
					laAFaire,
					new CellConstraints("2, 6, 1, 1, default, default"));
				laAFaire.setText("A Faire");
			}
			{
				cbAFaire = new JCheckBox();
				super.getPaCorpsFormulaire().add(
					cbAFaire,
					new CellConstraints("4, 6, 1, 1, default, default"));
			}
			{
				laNomMAJ = new JLabel();
				super.getPaCorpsFormulaire().add(laNomMAJ, new CellConstraints("2, 2, 1, 1, default, default"));
				laNomMAJ.setText("Mise à jour");
			}
			{
				laDateMAJ = new JLabel();
				super.getPaCorpsFormulaire().add(laDateMAJ, new CellConstraints("2, 4, 1, 1, default, default"));
				laDateMAJ.setText("Date");
			}
			{
				btnMAJ = new JButton();
				super.getPaCorpsFormulaire().add(btnMAJ, new CellConstraints("2, 8, 3, 1, default, default"));
				btnMAJ.setText("Commencer la mise à jour");
			}
		} catch (Exception e) {
			logger.error("Erreur : initGUI", e);
		}
	}

	public JLabel getLaNomMAJ() {
		return laNomMAJ;
	}

	public void setLaNomMAJ(JLabel laNomMAJ) {
		this.laNomMAJ = laNomMAJ;
	}

	public JLabel getLaDateMAJ() {
		return laDateMAJ;
	}

	public void setLaDateMAJ(JLabel laDateMAJ) {
		this.laDateMAJ = laDateMAJ;
	}
	
	public JTextField getTfNomMAJ() {
		return tfNomMAJ;
	}
	
	public JTextField getTfDateMAJ() {
		return tfDateMAJ;
	}

	public void setTfDateMAJ(JTextField tfDateMAJ) {
		this.tfDateMAJ = tfDateMAJ;
	}

	public void setTfNomMAJ(JTextField tfNomMAJ) {
		this.tfNomMAJ = tfNomMAJ;
	}
	
	public JLabel getLaAFaire() {
		return laAFaire;
	}

	public void setLaAFaire(JLabel laAFaire) {
		this.laAFaire = laAFaire;
	}
	
	public JCheckBox getCbAFaire() {
		return cbAFaire;
	}

	public void setCbAFaire(JCheckBox cbAFaire) {
		this.cbAFaire = cbAFaire;
	}
	
	public JButton getBtnMAJ() {
		return btnMAJ;
	}

}
