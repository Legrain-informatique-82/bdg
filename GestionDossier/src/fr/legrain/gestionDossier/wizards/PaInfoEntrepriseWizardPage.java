package fr.legrain.gestionDossier.wizards;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

import java.awt.Dimension;

import javax.swing.WindowConstants;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

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
public class PaInfoEntrepriseWizardPage extends javax.swing.JPanel {
	private JLabel laNOM;
	private JLabel laADRESSE;
	private JLabel laTVA_INTRA;
	private JTextField tfNOM;
	private JTextField tfAPE;
	private JTextField tfTVA_INTRA;
	private JTextField tfCAPITAL;
	private JTextField tfSIRET;
	private JTextField tfADRESSE;
	private JLabel laAPE;
	private JLabel laCAPITAL;
	private JLabel laSIRET;
	
	public PaInfoEntrepriseWizardPage() {
		super();
		initGUI();
	}
	
	private void initGUI() {
		try {
			FormLayout thisLayout = new FormLayout(
				"max(p;5dlu), 91dlu, max(p;5dlu), 70dlu, max(p;15dlu)",
				"max(p;5dlu), max(p;5dlu), max(p;5dlu), max(p;5dlu), max(p;5dlu), max(p;5dlu), max(p;5dlu), max(p;5dlu), max(p;5dlu), max(p;5dlu), max(p;5dlu), max(p;5dlu)");
			this.setLayout(thisLayout);
			setPreferredSize(new Dimension(400, 300));
			{
				laNOM = new JLabel();
				this.add(laNOM, new CellConstraints(
					"2, 2, 1, 1, default, default"));
				laNOM.setText("Nom");
			}
			{
				laADRESSE = new JLabel();
				this.add(laADRESSE, new CellConstraints(
					"2, 4, 1, 1, default, default"));
				laADRESSE.setText("Adresse");
			}
			{
				laSIRET = new JLabel();
				this.add(laSIRET, new CellConstraints("2, 6, 1, 1, default, default"));
				laSIRET.setText("N° Siret");
			}
			{
				laCAPITAL = new JLabel();
				this.add(laCAPITAL, new CellConstraints("2, 8, 1, 1, default, default"));
				laCAPITAL.setText("Capital");
			}
			{
				laTVA_INTRA = new JLabel();
				this.add(laTVA_INTRA, new CellConstraints("2, 12, 1, 1, default, default"));
				laTVA_INTRA.setText("N° TVA intracommunautaire");
			}
			{
				laAPE = new JLabel();
				this.add(laAPE, new CellConstraints("2, 10, 1, 1, default, default"));
				laAPE.setText("Code APE");
			}
			{
				tfNOM = new JTextField();
				this.add(tfNOM, new CellConstraints(
					"4, 2, 1, 1, default, default"));
			}
			{
				tfADRESSE = new JTextField();
				this.add(tfADRESSE, new CellConstraints(
					"4, 4, 1, 1, default, default"));
			}
			{
				tfSIRET = new JTextField();
				this.add(tfSIRET, new CellConstraints("4, 6, 1, 1, default, default"));
			}
			{
				tfCAPITAL = new JTextField();
				this.add(tfCAPITAL, new CellConstraints("4, 8, 1, 1, default, default"));
			}
			{
				tfTVA_INTRA = new JTextField();
				this.add(tfTVA_INTRA, new CellConstraints("4, 12, 1, 1, default, default"));
			}
			{
				tfAPE = new JTextField();
				this.add(tfAPE, new CellConstraints("4, 10, 1, 1, default, default"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public JLabel getLaADRESSE() {
		return laADRESSE;
	}

	public void setLaADRESSE(JLabel laADRESSE) {
		this.laADRESSE = laADRESSE;
	}

	public JLabel getLaAPE() {
		return laAPE;
	}

	public void setLaAPE(JLabel laAPE) {
		this.laAPE = laAPE;
	}

	public JLabel getLaCAPITAL() {
		return laCAPITAL;
	}

	public void setLaCAPITAL(JLabel laCAPITAL) {
		this.laCAPITAL = laCAPITAL;
	}

	public JLabel getLaNOM() {
		return laNOM;
	}

	public void setLaNOM(JLabel laNOM) {
		this.laNOM = laNOM;
	}

	public JLabel getLaSIRET() {
		return laSIRET;
	}

	public void setLaSIRET(JLabel laSIRET) {
		this.laSIRET = laSIRET;
	}

	public JLabel getLaTVA_INTRA() {
		return laTVA_INTRA;
	}

	public void setLaTVA_INTRA(JLabel laTVA_INTRA) {
		this.laTVA_INTRA = laTVA_INTRA;
	}

	public JTextField getTfADRESSE() {
		return tfADRESSE;
	}

	public void setTfADRESSE(JTextField tfADRESSE) {
		this.tfADRESSE = tfADRESSE;
	}

	public JTextField getTfAPE() {
		return tfAPE;
	}

	public void setTfAPE(JTextField tfAPE) {
		this.tfAPE = tfAPE;
	}

	public JTextField getTfCAPITAL() {
		return tfCAPITAL;
	}

	public void setTfCAPITAL(JTextField tfCAPITAL) {
		this.tfCAPITAL = tfCAPITAL;
	}

	public JTextField getTfNOM() {
		return tfNOM;
	}

	public void setTfNOM(JTextField tfNOM) {
		this.tfNOM = tfNOM;
	}

	public JTextField getTfSIRET() {
		return tfSIRET;
	}

	public void setTfSIRET(JTextField tfSIRET) {
		this.tfSIRET = tfSIRET;
	}

	public JTextField getTfTVA_INTRA() {
		return tfTVA_INTRA;
	}

	public void setTfTVA_INTRA(JTextField tfTVA_INTRA) {
		this.tfTVA_INTRA = tfTVA_INTRA;
	}

}
