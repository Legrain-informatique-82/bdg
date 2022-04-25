package fr.legrain.gestionDossier.wizards;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

import fr.legrain.lib.data.LgrConstantes;

import java.awt.BorderLayout;

import java.awt.Dimension;

import javax.swing.JFormattedTextField;
import javax.swing.WindowConstants;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.text.MaskFormatter;


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
public class PaInfoEntrepriseSimple extends fr.legrain.lib.gui.DefaultFrameBouton {
	private JPanel paFormulaire;
	private JTextField tfNOM;
	private JLabel laTELEPHONE;
	private JLabel laPAYS;
	private JLabel laVILLE;
	private JLabel laCODEPOSTAL;
	private JLabel laADRESSE3;
	private JLabel laADRESSE2;
	private JTextField tfRCS;
	private JLabel laRCS;
	private JTextField tfEMAIL;
	private JLabel laEMAIL;
	private JTextField tfWEB;
	private JTextField tfVILLE;
	private JTextField tfCODEXO;
	private JFormattedTextField tfDATEFIN;
	private JLabel laCODEXO;
	private JFormattedTextField tfDATEDEB;
	private JLabel laDATEFIN;
	private JLabel laDATEDEB;
	private JTextField tfFAX;
	private JTextField tfTELEPHONE;
	private JTextField tfPAYS;
	private JTextField tfCODEPOSTAL;
	private JTextField tfADRESSE3;
	private JTextField tfADRESSE2;
	private JLabel laFAX;
	private JLabel laWEB;
	private JTextField tfAPE;
	private JTextField tfTVA_INTRA;
	private JTextField tfCAPITAL;
	private JTextField tfSIRET;
	private JTextField tfADRESSE1;
	private JLabel laAPE;
	private JLabel laTVA_INTRA;
	private JLabel laCAPITAL;
	private JLabel laSIRET;
	private JLabel laADRESSE1;
	private JLabel laNOM;
	
	public PaInfoEntrepriseSimple() {
		super();
		initGUI();
	}
	
	private void initGUI() {
		try {
			this.setPreferredSize(new java.awt.Dimension(673, 593));
			{
				paFormulaire = new JPanel();
				this.add(paFormulaire, BorderLayout.CENTER);
				FormLayout jPanel1Layout = new FormLayout(
					"max(p;5dlu), 91dlu, max(p;5dlu), 70dlu, 58dlu, 38dlu, 55dlu, 102dlu",
					"max(p;5dlu), max(p;5dlu), max(p;5dlu), max(p;5dlu), max(p;5dlu), max(p;15dlu), 5dlu, max(p;15dlu), 5dlu, max(p;15dlu), 5dlu, max(p;15dlu), 5dlu, max(p;15dlu), 5dlu, max(p;15dlu), 5dlu, max(p;15dlu), 5dlu, max(p;15dlu), 5dlu, max(p;15dlu), 5dlu, max(p;15dlu), 5dlu, max(p;15dlu), max(p;5dlu), max(p;5dlu), 5dlu, max(p;5dlu), max(p;5dlu), max(p;5dlu)");
				paFormulaire.setPreferredSize(new Dimension(400, 300));
				paFormulaire.setLayout(jPanel1Layout);
				{
					laNOM = new JLabel();
					paFormulaire.add(laNOM, new CellConstraints(
						"2, 2, 1, 1, default, default"));
					laNOM.setText("Nom");
				}
				{
					laADRESSE1 = new JLabel();
					paFormulaire.add(laADRESSE1, new CellConstraints(
						"2, 4, 1, 1, default, default"));
					laADRESSE1.setText("Adresse 1");
				}
				{
					laSIRET = new JLabel();
					paFormulaire.add(laSIRET, new CellConstraints("2, 20, 1, 1, default, default"));
					laSIRET.setText("N° Siret");
				}
				{
					laCAPITAL = new JLabel();
					paFormulaire.add(laCAPITAL, new CellConstraints("2, 28, 1, 1, default, default"));
					laCAPITAL.setText("Capital");
				}
				{
					laTVA_INTRA = new JLabel();
					paFormulaire.add(laTVA_INTRA, new CellConstraints("2, 26, 1, 1, default, default"));
					laTVA_INTRA.setText("N° TVA intracommunautaire");
				}
				{
					laAPE = new JLabel();
					paFormulaire.add(laAPE, new CellConstraints("2, 22, 1, 1, default, default"));
					laAPE.setText("Code APE");
				}
				{
					tfNOM = new JTextField();
					paFormulaire.add(tfNOM, new CellConstraints("4, 2, 2, 1, default, default"));
				}
				{
					tfADRESSE1 = new JTextField();
					paFormulaire.add(tfADRESSE1, new CellConstraints("4, 4, 5, 1, default, default"));
				}
				{
					tfSIRET = new JTextField();
					paFormulaire.add(tfSIRET, new CellConstraints("4, 20, 3, 1, default, default"));
				}
				{
					tfCAPITAL = new JTextField();
					paFormulaire.add(tfCAPITAL, new CellConstraints("4, 28, 1, 1, default, default"));
				}
				{
					tfTVA_INTRA = new JTextField();
					paFormulaire.add(tfTVA_INTRA, new CellConstraints("4, 26, 3, 1, default, default"));
				}
				{
					tfAPE = new JTextField();
					paFormulaire.add(tfAPE, new CellConstraints("4, 22, 1, 1, default, default"));
				}
				{
					laWEB = new JLabel();
					paFormulaire.add(laWEB, new CellConstraints("2, 32, 1, 1, default, default"));
					laWEB.setText("Adresse Site");
				}
				{
					tfWEB = new JTextField();
					paFormulaire.add(tfWEB, new CellConstraints("4, 32, 5, 1, default, default"));
				}
				{
					laEMAIL = new JLabel();
					paFormulaire.add(laEMAIL, new CellConstraints("2, 30, 1, 1, default, default"));
					laEMAIL.setText("Adresse email");
				}
				{
					tfEMAIL = new JTextField();
					paFormulaire.add(tfEMAIL, new CellConstraints("4, 30, 5, 1, default, default"));
				}
				{
					laRCS = new JLabel();
					paFormulaire.add(laRCS, new CellConstraints("2, 24, 1, 1, default, default"));
					laRCS.setText("RCS");
				}
				{
					tfRCS = new JTextField();
					paFormulaire.add(getTfRCS(), new CellConstraints("4, 24, 2, 1, default, default"));
				}
				{
					laADRESSE2 = new JLabel();
					paFormulaire.add(laADRESSE2, new CellConstraints(
						"2, 6, 1, 1, default, default"));
					laADRESSE2.setText("Adresse 2");
				}
				{
					laADRESSE3 = new JLabel();
					paFormulaire.add(laADRESSE3, new CellConstraints("2, 8, 1, 1, default, default"));
					laADRESSE3.setText("Adresse 3");
				}
				{
					laCODEPOSTAL = new JLabel();
					paFormulaire.add(laCODEPOSTAL, new CellConstraints("2, 10, 1, 1, default, default"));
					laCODEPOSTAL.setText("Code postal");
				}
				{
					laVILLE = new JLabel();
					paFormulaire.add(laVILLE, new CellConstraints("2, 12, 1, 1, default, default"));
					laVILLE.setText("Ville");
				}
				{
					laPAYS = new JLabel();
					paFormulaire.add(laPAYS, new CellConstraints("2, 14, 1, 1, default, default"));
					laPAYS.setText("Pays");
				}
				{
					laTELEPHONE = new JLabel();
					paFormulaire.add(laTELEPHONE, new CellConstraints("2, 16, 1, 1, default, default"));
					laTELEPHONE.setText("Téléphone");
				}
				{
					laFAX = new JLabel();
					paFormulaire.add(laFAX, new CellConstraints("2, 18, 1, 1, default, default"));
					laFAX.setText("Fax");
				}
				{
					tfADRESSE2 = new JTextField();
					paFormulaire.add(tfADRESSE2, new CellConstraints("4, 6, 5, 1, default, default"));
				}
				{
					tfADRESSE3 = new JTextField();
					paFormulaire.add(tfADRESSE3, new CellConstraints("4, 8, 5, 1, default, default"));
				}
				{
					tfCODEPOSTAL = new JTextField();
					paFormulaire.add(getTfCODEPOSTAL(), new CellConstraints(
						"4, 10, 1, 1, default, default"));
				}
				{
					tfVILLE = new JTextField();
					paFormulaire.add(getTfVILLE(), new CellConstraints("4, 12, 2, 1, default, default"));
				}
				{
					tfPAYS = new JTextField();
					paFormulaire.add(getTfPAYS(), new CellConstraints("4, 14, 2, 1, default, default"));
				}
				{
					tfTELEPHONE = new JTextField();
					paFormulaire.add(getTfTELEPHONE(), new CellConstraints(
						"4, 16, 1, 1, default, default"));
				}
				{
					tfFAX = new JTextField();
					paFormulaire.add(getTfFAX(), new CellConstraints(
						"4, 18, 1, 1, default, default"));
				}
				MaskFormatter mf1 = new MaskFormatter(LgrConstantes.C_DATE_MASK_FORMATTER);
				mf1.setPlaceholderCharacter(LgrConstantes.C_DATE_MASK_PLACE_HOLDER);
				MaskFormatter mf2 = new MaskFormatter(LgrConstantes.C_DATE_MASK_FORMATTER);
				mf2.setPlaceholderCharacter(LgrConstantes.C_DATE_MASK_PLACE_HOLDER);
				{
					laDATEDEB = new JLabel();
					paFormulaire.add(laDATEDEB, new CellConstraints("7, 12, 1, 1, default, default"));
					laDATEDEB.setText("Date début");
				}
				{
					laDATEFIN = new JLabel();
					paFormulaire.add(laDATEFIN, new CellConstraints("7, 14, 1, 1, default, default"));
					laDATEFIN.setText("Date fin");
				}
				{
					tfDATEDEB = new JFormattedTextField(mf1);
					paFormulaire.add(getTfDATEDEB(), new CellConstraints("8, 12, 1, 1, default, default"));
				}
				{
					laCODEXO = new JLabel();
					paFormulaire.add(laCODEXO, new CellConstraints("7, 16, 1, 1, default, default"));
					laCODEXO.setText("Code exercice");
				}
				{
					tfDATEFIN = new JFormattedTextField(mf2);
					paFormulaire.add(getTfDATEFIN(), new CellConstraints(
						"8, 14, 1, 1, default, default"));
				}
				{
					tfCODEXO = new JTextField();
					paFormulaire.add(getTfCODEXO(), new CellConstraints(
						"8, 16, 1, 1, default, default"));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public JPanel getPaFormulaire() {
		return paFormulaire;
	}

	public void setPaFormulaire(JPanel panel1) {
		paFormulaire = panel1;
	}

	public JLabel getLaADRESSE1() {
		return laADRESSE1;
	}

	public void setLaADRESSE1(JLabel laADRESSE) {
		this.laADRESSE1 = laADRESSE;
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

	public JTextField getTfADRESSE1() {
		return tfADRESSE1;
	}

	public void setTfADRESSE1(JTextField tfADRESSE) {
		this.tfADRESSE1 = tfADRESSE;
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
	
	public JTextField getTfWEB() {
		return tfWEB;
	}
	
	public JTextField getTfEMAIL() {
		return tfEMAIL;
	}
	
	public JTextField getTfRCS() {
		return tfRCS;
	}
	
	public JTextField getTfADRESSE2() {
		return tfADRESSE2;
	}
	
	public JTextField getTfADRESSE3() {
		return tfADRESSE3;
	}
	
	public JTextField getTfCODEPOSTAL() {
		return tfCODEPOSTAL;
	}
	
	public JTextField getTfVILLE() {
		return tfVILLE;
	}
	
	public JTextField getTfPAYS() {
		return tfPAYS;
	}
	
	public JTextField getTfTELEPHONE() {
		return tfTELEPHONE;
	}
	
	public JTextField getTfFAX() {
		return tfFAX;
	}
	
	public JFormattedTextField getTfDATEDEB() {
		return tfDATEDEB;
	}
	

	
	public JFormattedTextField getTfDATEFIN() {
		return tfDATEFIN;
	}
	
	public JTextField getTfCODEXO() {
		return tfCODEXO;
	}

}
