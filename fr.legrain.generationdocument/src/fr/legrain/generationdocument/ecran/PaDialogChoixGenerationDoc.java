package fr.legrain.generationdocument.ecran;

import java.util.LinkedHashMap;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;

public class PaDialogChoixGenerationDoc extends Dialog {

	public static final int TYPE_AVOIR = 1;
	public static final int TYPE_DEVIS = 2;
	public static final int TYPE_FACTURE = 3;
	public static final int TYPE_PROFORMA = 4;
	public static final int TYPE_APPORTEUR = 5;
	public static final int TYPE_BON_COMMANDE = 6;
	public static final int TYPE_BON_LIVRAISON = 7;

	private int typeSrc = 0;
	private LinkedHashMap<Integer, Button> boutonChoix = new LinkedHashMap<Integer, Button>();
	
	private int selectedType = 0;
	private String nouveauLibelle = null;


	private PaChoixGenerationDocument paChoixGenerationDocument = null;

	public PaDialogChoixGenerationDoc(Shell parentShell, int typeSrc) {
		super(parentShell);
//		this.getShell().setText("Génération de document");
		this.typeSrc = typeSrc;
	}

	@Override
	protected Control createDialogArea(Composite parent) {
		Composite c = (Composite) super.createDialogArea(parent);
		paChoixGenerationDocument = new PaChoixGenerationDocument(c,SWT.NULL);

		if(typeSrc==TYPE_AVOIR) {
			
		} else if(typeSrc==TYPE_DEVIS) {
			addBtnFacture();
			addBtnBonLiv();
			addBtnBonCmd();
		} else if(typeSrc==TYPE_FACTURE) {
			addBtnBonLiv();
			addBtnAvoir();
		} else if(typeSrc==TYPE_PROFORMA) {
			addBtnFacture();
			addBtnBonLiv();
			addBtnBonCmd();
		} else if(typeSrc==TYPE_APPORTEUR) {

		} else if(typeSrc==TYPE_BON_COMMANDE) {
			addBtnFacture();
			addBtnBonLiv();
		} else if(typeSrc==TYPE_BON_LIVRAISON) {
			addBtnFacture();
		}
		
		if(!boutonChoix.isEmpty())
			boutonChoix.get(boutonChoix.keySet().iterator().next()).setSelection(true);
		
		paChoixGenerationDocument.getGroupChoix().layout();
		paChoixGenerationDocument.layout();
		return c;
	}

	private void addBtnAvoir() {
		Button btn = new Button(paChoixGenerationDocument.getGroupChoix(),SWT.RADIO);
		btn.setText("Avoir");
		boutonChoix.put(TYPE_AVOIR, btn);
	}

	private void addBtnDevis() {
		Button btn = new Button(paChoixGenerationDocument.getGroupChoix(),SWT.RADIO);
		btn.setText("Devis");
		boutonChoix.put(TYPE_DEVIS, btn);
	}

	private void addBtnFacture() {
		Button btn = new Button(paChoixGenerationDocument.getGroupChoix(),SWT.RADIO);
		btn.setText("Facture");
		boutonChoix.put(TYPE_FACTURE, btn);
	}

	private void addBtnProforma() {
		Button btn = new Button(paChoixGenerationDocument.getGroupChoix(),SWT.RADIO);
		btn.setText("Proforma");
		boutonChoix.put(TYPE_PROFORMA, btn);
	}

	private void addBtnApporteur() {
		Button btn = new Button(paChoixGenerationDocument.getGroupChoix(),SWT.RADIO);
		btn.setText("Facture apporteur");
		boutonChoix.put(TYPE_APPORTEUR, btn);
	}

	private void addBtnBonCmd() {
		Button btn = new Button(paChoixGenerationDocument.getGroupChoix(),SWT.RADIO);
		btn.setText("Bon de commande");
		boutonChoix.put(TYPE_BON_COMMANDE, btn);
	}

	private void addBtnBonLiv() {
		Button btn = new Button(paChoixGenerationDocument.getGroupChoix(),SWT.RADIO);
		btn.setText("Bon de livraison");
		boutonChoix.put(TYPE_BON_LIVRAISON, btn);
	}

	@Override
	protected void okPressed() {
		for (Integer i : boutonChoix.keySet()) {
			if(boutonChoix.get(i).getSelection()) {
				selectedType = i;
			}
		}
		nouveauLibelle = paChoixGenerationDocument.getTfLibelle().getText();
		super.okPressed();
	}

	public int getSelectedType() {
		return selectedType;
	}

	public String getNouveauLibelle() {
		return nouveauLibelle;
	}

}
