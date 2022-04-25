package fr.legrain.document.ecran;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;


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
public class PaListeCreationDoc extends org.eclipse.swt.widgets.Composite {
	private Composite composite1;
	private Group groupFacture;
	private Button btnCreerFacture;
	private Label labelDevis;
	private Button btnListerAvoir;
	private Button btnListerAcompte;
	private Button btnCreerAcompte;
	private Label labelAcompte;
	private Group groupAcompte;
	private Button btnCreerAvoir;
	private Label labelAvoir;
	private Group groupAvoir;
	private Group groupProforma;
	private Group groupPrelevement;
	private Label labelRemise;
	private Button btnListerRemise;
	private Label labelAvisEcheance;
	private Button btnListerAvisEcheance;
	private Button btnCreerAvisEcheance;
	private Group groupAvisEcheance;
	private Button btnCreerRemise;
	private Group groupRemise;
	private Button btnListerReglement;
	private Button btnCreerReglement;
	private Label labelReglement;
	private Group groupReglement;
	private Button btnListerPrelevement;
	private Button btnCreerPrelevement;
	private Label labelPrelevement;
	private Button btnListerApporteur;
	private Button btnCreerApporteur;
	private Label labelApporteur;
	private Group groupApporteur;
	private Button btnListerProforma;
	private Button btnCreerProforma;
	private Label labelProforma;
	private Button btnListerBonCde;
	private Button btnCreerBonCde;
	private Label labelBonCde;
	private Group groupBonCde;
	private Button btnListerBonLiv;
	private Button btnCreerBonLiv;
	private Label labelBonLiv;
	private Group groupBonLiv;
	private Button btnListerDevis;
	private Button btnCreerDevis;
	private Group groupDevis;
	private Button btnListerFacture;
	private Label labelFacture;
	//private Label laCOMMENTAIRE;

	/**
	* Auto-generated main method to display this 
	* org.eclipse.swt.widgets.Composite inside a new Shell.
	*/
	public static void main(String[] args) {
		showGUI();
	}
		
	/**
	* Auto-generated method to display this 
	* org.eclipse.swt.widgets.Composite inside a new Shell.
	*/
	public static void showGUI() {
		Display display = Display.getDefault();
		Shell shell = new Shell(display);
		PaListeCreationDoc inst = new PaListeCreationDoc(shell, SWT.NULL);
		Point size = inst.getSize();
		shell.setLayout(new FillLayout());
		shell.layout();
		if(size.x == 0 && size.y == 0) {
			inst.pack();
			shell.pack();
		} else {
			Rectangle shellBounds = shell.computeTrim(0, 0, size.x, size.y);
			shell.setSize(shellBounds.width, shellBounds.height);
		}
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
	}

	public PaListeCreationDoc(org.eclipse.swt.widgets.Composite parent, int style) {
		super(parent, style);
		initGUI();
	}

	private void initGUI() {
		try {

			GridLayout thisLayout = new GridLayout();
			thisLayout.numColumns = 2;
			this.setLayout(thisLayout);
			this.setSize(830, 579);
			{
				composite1 = new Composite(this, SWT.BORDER);
				GridLayout composite1Layout = new GridLayout();
				composite1Layout.makeColumnsEqualWidth = true;
				composite1Layout.numColumns = 3;
				GridData composite1LData = new GridData();
				composite1LData.verticalAlignment = GridData.FILL;
				composite1LData.horizontalAlignment = GridData.FILL;
				composite1LData.grabExcessHorizontalSpace = true;
				composite1LData.grabExcessVerticalSpace = true;
				composite1.setLayoutData(composite1LData);
				composite1.setLayout(composite1Layout);
				{
					groupFacture = new Group(composite1, SWT.NONE);
					GridLayout group1Layout = new GridLayout();
					GridData group1LData = new GridData();
					group1LData.horizontalAlignment = GridData.CENTER;
					group1LData.widthHint = 170;
					group1LData.heightHint = 106;
					groupFacture.setLayoutData(group1LData);
					groupFacture.setLayout(group1Layout);
					groupFacture.setText("FACTURE");
					{
						labelFacture = new Label(groupFacture, SWT.NONE);
						GridData laTypeDocumentLData = new GridData();
						laTypeDocumentLData.horizontalAlignment = GridData.FILL;
						laTypeDocumentLData.heightHint = 36;
						labelFacture.setLayoutData(laTypeDocumentLData);
						labelFacture.setOrientation(SWT.HORIZONTAL);
					}
					{
						btnCreerFacture = new Button(groupFacture, SWT.PUSH | SWT.CENTER);
						GridData btnCreerLData = new GridData();
						btnCreerLData.horizontalAlignment = GridData.FILL;
						btnCreerLData.heightHint = 27;
						btnCreerLData.grabExcessHorizontalSpace = true;
						btnCreerFacture.setLayoutData(btnCreerLData);
						btnCreerFacture.setText("Créer");
					}
					{
						btnListerFacture = new Button(groupFacture, SWT.PUSH | SWT.CENTER);
						GridData btnListerLData = new GridData();
						btnListerLData.heightHint = 27;
						btnListerLData.grabExcessHorizontalSpace = true;
						btnListerLData.horizontalAlignment = GridData.FILL;
						btnListerFacture.setLayoutData(btnListerLData);
						btnListerFacture.setText("Lister");
						btnListerFacture.setSize(113, 27);
					}
				}
				{
					GridData group2LData = new GridData();
					group2LData.widthHint = 170;
					group2LData.heightHint = 106;
					groupDevis = new Group(composite1, SWT.NONE);
					GridLayout group2Layout = new GridLayout();
					groupDevis.setLayout(group2Layout);
					groupDevis.setLayoutData(group2LData);
					groupDevis.setText("DEVIS");
					{
						labelDevis = new Label(groupDevis, SWT.NONE);
						GridData label1LData = new GridData();
						label1LData.horizontalAlignment = GridData.FILL;
						label1LData.heightHint = 36;
						labelDevis.setLayoutData(label1LData);
						labelDevis.setOrientation(SWT.HORIZONTAL);
						labelDevis.setSize(160, 36);
					}
					{
						btnCreerDevis = new Button(groupDevis, SWT.PUSH | SWT.CENTER);
						GridData button1LData = new GridData();
						button1LData.horizontalAlignment = GridData.FILL;
						button1LData.heightHint = 27;
						button1LData.grabExcessHorizontalSpace = true;
						btnCreerDevis.setLayoutData(button1LData);
						btnCreerDevis.setText("Créer");
					}
					{
						btnListerDevis = new Button(groupDevis, SWT.PUSH | SWT.CENTER);
						btnListerDevis.setText("Lister");
						btnListerDevis.setSize(113, 27);
					}
				}
				{
					GridData group3LData = new GridData();
					group3LData.widthHint = 170;
					group3LData.heightHint = 106;
					groupBonLiv = new Group(composite1, SWT.NONE);
					GridLayout group3Layout = new GridLayout();
					groupBonLiv.setLayout(group3Layout);
					groupBonLiv.setLayoutData(group3LData);
					groupBonLiv.setText("BON LIVRAISON");
					{
						labelBonLiv = new Label(groupBonLiv, SWT.NONE);
						GridData label2LData = new GridData();
						label2LData.horizontalAlignment = GridData.FILL;
						label2LData.heightHint = 36;

						labelBonLiv.setLayoutData(label2LData);
						labelBonLiv.setAlignment(SWT.LEFT);
						labelBonLiv.setOrientation(SWT.HORIZONTAL);
						labelBonLiv.setSize(160, 36);
					}
					{
						btnCreerBonLiv = new Button(groupBonLiv, SWT.PUSH | SWT.CENTER);
						GridData button3LData = new GridData();
						button3LData.horizontalAlignment = GridData.FILL;
						button3LData.heightHint = 27;
						button3LData.grabExcessHorizontalSpace = true;
						btnCreerBonLiv.setLayoutData(button3LData);
						btnCreerBonLiv.setText("Créer");
					}
					{
						btnListerBonLiv = new Button(groupBonLiv, SWT.PUSH | SWT.CENTER);
						btnListerBonLiv.setText("Lister");
						btnListerBonLiv.setSize(113, 27);
					}
				}
				{
					GridData group4LData = new GridData();
					group4LData.widthHint = 170;
					group4LData.heightHint = 106;
					groupBonCde = new Group(composite1, SWT.NONE);
					GridLayout group4Layout = new GridLayout();
					groupBonCde.setLayout(group4Layout);
					groupBonCde.setLayoutData(group4LData);
					groupBonCde.setText("BON DE COMMANDE");
					{
						labelBonCde = new Label(groupBonCde, SWT.NONE);
						GridData label3LData = new GridData();
						label3LData.horizontalAlignment = GridData.FILL;
						label3LData.heightHint = 36;
						labelBonCde.setLayoutData(label3LData);
						labelBonCde.setAlignment(SWT.LEFT);
						labelBonCde.setOrientation(SWT.HORIZONTAL);
						labelBonCde.setSize(160, 36);
					}
					{
						btnCreerBonCde = new Button(groupBonCde, SWT.PUSH | SWT.CENTER);
						GridData button5LData = new GridData();
						button5LData.horizontalAlignment = GridData.FILL;
						button5LData.heightHint = 27;
						button5LData.grabExcessHorizontalSpace = true;
						btnCreerBonCde.setLayoutData(button5LData);
						btnCreerBonCde.setText("Créer");
					}
					{
						btnListerBonCde = new Button(groupBonCde, SWT.PUSH | SWT.CENTER);
						btnListerBonCde.setText("Lister");
						btnListerBonCde.setSize(113, 27);
					}
				}
				{
					GridData group5LData = new GridData();
					group5LData.widthHint = 170;
					group5LData.heightHint = 106;
					groupAvoir = new Group(composite1, SWT.NONE);
					GridLayout group5Layout = new GridLayout();
					groupAvoir.setLayout(group5Layout);
					groupAvoir.setLayoutData(group5LData);
					groupAvoir.setText("FACTURE D'AVOIR");
					{
						labelAvoir = new Label(groupAvoir, SWT.NONE);
						GridData label4LData = new GridData();
						label4LData.horizontalAlignment = GridData.FILL;
						label4LData.heightHint = 36;
						labelAvoir.setLayoutData(label4LData);
						labelAvoir.setAlignment(SWT.LEFT);
						labelAvoir.setOrientation(SWT.HORIZONTAL);
						labelAvoir.setSize(160, 36);
					}
					{
						btnCreerAvoir = new Button(groupAvoir, SWT.PUSH | SWT.CENTER);
						GridData button7LData = new GridData();
						button7LData.horizontalAlignment = GridData.FILL;
						button7LData.heightHint = 27;
						button7LData.grabExcessHorizontalSpace = true;
						btnCreerAvoir.setLayoutData(button7LData);
						btnCreerAvoir.setText("Créer");
					}
					{
						btnListerAvoir = new Button(groupAvoir, SWT.PUSH | SWT.CENTER);
						btnListerAvoir.setText("Lister");
						btnListerAvoir.setSize(113, 27);
					}
				}
				{
					GridData group6LData = new GridData();
					group6LData.widthHint = 170;
					group6LData.heightHint = 106;
					groupAcompte = new Group(composite1, SWT.NONE);
					GridLayout group6Layout = new GridLayout();
					groupAcompte.setLayout(group6Layout);
					groupAcompte.setLayoutData(group6LData);
					groupAcompte.setText("FACTURE D'ACOMPTE");
					{
						labelAcompte = new Label(groupAcompte, SWT.NONE);
						GridData label5LData = new GridData();
						label5LData.horizontalAlignment = GridData.FILL;
						label5LData.heightHint = 36;
						labelAcompte.setLayoutData(label5LData);
						labelAcompte.setAlignment(SWT.LEFT);
						labelAcompte.setOrientation(SWT.HORIZONTAL);
						labelAcompte.setSize(160, 36);
					}
					{
						btnCreerAcompte = new Button(groupAcompte, SWT.PUSH | SWT.CENTER);
						GridData button9LData = new GridData();
						button9LData.horizontalAlignment = GridData.FILL;
						button9LData.heightHint = 27;
						button9LData.grabExcessHorizontalSpace = true;
						btnCreerAcompte.setLayoutData(button9LData);
						btnCreerAcompte.setText("Créer");
					}
					{
						btnListerAcompte = new Button(groupAcompte, SWT.PUSH | SWT.CENTER);
						btnListerAcompte.setText("Lister");
						btnListerAcompte.setSize(113, 27);
					}
				}
				{
					GridData group7LData = new GridData();
					group7LData.widthHint = 170;
					group7LData.heightHint = 106;
					groupProforma = new Group(composite1, SWT.NONE);
					GridLayout group7Layout = new GridLayout();
					groupProforma.setLayout(group7Layout);
					groupProforma.setLayoutData(group7LData);
					groupProforma.setText("PROFORMA");
					{
						labelProforma = new Label(groupProforma, SWT.NONE);
						GridData label6LData = new GridData();
						label6LData.horizontalAlignment = GridData.FILL;
						label6LData.heightHint = 36;
						labelProforma.setLayoutData(label6LData);
						labelProforma.setAlignment(SWT.LEFT);
						labelProforma.setOrientation(SWT.HORIZONTAL);
						labelProforma.setSize(160, 36);
					}
					{
						btnCreerProforma = new Button(groupProforma, SWT.PUSH | SWT.CENTER);
						GridData button11LData = new GridData();
						button11LData.horizontalAlignment = GridData.FILL;
						button11LData.heightHint = 27;
						button11LData.grabExcessHorizontalSpace = true;
						btnCreerProforma.setLayoutData(button11LData);
						btnCreerProforma.setText("Créer");
					}
					{
						btnListerProforma = new Button(groupProforma, SWT.PUSH | SWT.CENTER);
						btnListerProforma.setText("Lister");
						btnListerProforma.setSize(113, 27);
					}
				}
				{
					GridData group8LData = new GridData();
					group8LData.widthHint = 170;
					group8LData.heightHint = 106;
					groupApporteur = new Group(composite1, SWT.NONE);
					GridLayout group8Layout = new GridLayout();
					groupApporteur.setLayout(group8Layout);
					groupApporteur.setLayoutData(group8LData);
					groupApporteur.setText("FACTURE APPORTEUR");
					{
						labelApporteur = new Label(groupApporteur, SWT.NONE);
						GridData label7LData = new GridData();
						label7LData.horizontalAlignment = GridData.FILL;
						label7LData.heightHint = 36;
						labelApporteur.setLayoutData(label7LData);
						labelApporteur.setAlignment(SWT.LEFT);
						labelApporteur.setOrientation(SWT.HORIZONTAL);
						labelApporteur.setSize(160, 36);
					}
					{
						btnCreerApporteur = new Button(groupApporteur, SWT.PUSH | SWT.CENTER);
						GridData button13LData = new GridData();
						button13LData.horizontalAlignment = GridData.FILL;
						button13LData.heightHint = 27;
						button13LData.grabExcessHorizontalSpace = true;
						btnCreerApporteur.setLayoutData(button13LData);
						btnCreerApporteur.setText("Créer");
					}
					{
						btnListerApporteur = new Button(groupApporteur, SWT.PUSH | SWT.CENTER);
						btnListerApporteur.setText("Lister");
						btnListerApporteur.setSize(113, 27);
					}
				}
				{
					GridData group9LData = new GridData();
					group9LData.widthHint = 170;
					group9LData.heightHint = 106;
					groupPrelevement = new Group(composite1, SWT.NONE);
					GridLayout group9Layout = new GridLayout();
					groupPrelevement.setLayout(group9Layout);
					groupPrelevement.setLayoutData(group9LData);
					groupPrelevement.setText("PRELEVEMENT");
					{
						labelPrelevement = new Label(groupPrelevement, SWT.NONE);
						GridData label8LData = new GridData();
						label8LData.horizontalAlignment = GridData.FILL;
						label8LData.heightHint = 36;
						labelPrelevement.setLayoutData(label8LData);
						labelPrelevement.setAlignment(SWT.LEFT);
						labelPrelevement.setOrientation(SWT.HORIZONTAL);
						labelPrelevement.setSize(160, 36);
					}
					{
						btnCreerPrelevement = new Button(groupPrelevement, SWT.PUSH | SWT.CENTER);
						GridData button15LData = new GridData();
						button15LData.horizontalAlignment = GridData.FILL;
						button15LData.heightHint = 27;
						button15LData.grabExcessHorizontalSpace = true;
						btnCreerPrelevement.setLayoutData(button15LData);
						btnCreerPrelevement.setText("Créer");
					}
					{
						btnListerPrelevement = new Button(groupPrelevement, SWT.PUSH | SWT.CENTER);
						btnListerPrelevement.setText("Lister");
						btnListerPrelevement.setSize(113, 27);
					}
				}
				{
					GridData group10LData = new GridData();
					group10LData.widthHint = 170;
					group10LData.heightHint = 106;
					groupReglement = new Group(composite1, SWT.NONE);
					GridLayout group10Layout = new GridLayout();
					groupReglement.setLayout(group10Layout);
					groupReglement.setLayoutData(group10LData);
					groupReglement.setText("REGLEMENT");
					{
						labelReglement = new Label(groupReglement, SWT.NONE);
						GridData label9LData = new GridData();
						label9LData.horizontalAlignment = GridData.FILL;
						label9LData.heightHint = 36;
						labelReglement.setLayoutData(label9LData);
						labelReglement.setAlignment(SWT.LEFT);
						labelReglement.setOrientation(SWT.HORIZONTAL);
						labelReglement.setSize(160, 36);
					}
					{
						btnCreerReglement = new Button(groupReglement, SWT.PUSH | SWT.CENTER);
						GridData button17LData = new GridData();
						button17LData.horizontalAlignment = GridData.FILL;
						button17LData.heightHint = 27;
						button17LData.grabExcessHorizontalSpace = true;
						btnCreerReglement.setLayoutData(button17LData);
						btnCreerReglement.setText("Créer");
					}
					{
						btnListerReglement = new Button(groupReglement, SWT.PUSH | SWT.CENTER);
						btnListerReglement.setText("Lister");
						btnListerReglement.setSize(113, 27);
					}
				}
				{
					GridData group11LData = new GridData();
					group11LData.widthHint = 170;
					group11LData.heightHint = 106;
					groupRemise = new Group(composite1, SWT.NONE);
					GridLayout group11Layout = new GridLayout();
					groupRemise.setLayout(group11Layout);
					groupRemise.setLayoutData(group11LData);
					groupRemise.setText("REMISE");
					{
						labelRemise = new Label(groupRemise, SWT.NONE);
						GridData label10LData = new GridData();
						label10LData.horizontalAlignment = GridData.FILL;
						label10LData.heightHint = 36;
						labelRemise.setLayoutData(label10LData);
						labelRemise.setAlignment(SWT.LEFT);
						labelRemise.setOrientation(SWT.HORIZONTAL);
						labelRemise.setSize(160, 36);
					}
					{
						btnCreerRemise = new Button(groupRemise, SWT.PUSH | SWT.CENTER);
						GridData button19LData = new GridData();
						button19LData.horizontalAlignment = GridData.FILL;
						button19LData.heightHint = 27;
						button19LData.grabExcessHorizontalSpace = true;
						btnCreerRemise.setLayoutData(button19LData);
						btnCreerRemise.setText("Créer");
					}
					{
						btnListerRemise = new Button(groupRemise, SWT.PUSH | SWT.CENTER);
						btnListerRemise.setText("Lister");
						btnListerRemise.setSize(113, 27);
					}
				}
				{
					GridData group12LData = new GridData();
					group12LData.widthHint = 170;
					group12LData.heightHint = 106;
					groupAvisEcheance = new Group(composite1, SWT.NONE);
					GridLayout group12Layout = new GridLayout();
					groupAvisEcheance.setLayout(group12Layout);
					groupAvisEcheance.setLayoutData(group12LData);
					groupAvisEcheance.setText("AVIS D'ECHEANCE");
					{
						labelAvisEcheance = new Label(groupAvisEcheance, SWT.NONE);
						GridData label11LData = new GridData();
						label11LData.horizontalAlignment = GridData.FILL;
						label11LData.heightHint = 36;
						labelAvisEcheance.setLayoutData(label11LData);
						labelAvisEcheance.setAlignment(SWT.LEFT);
						labelAvisEcheance.setOrientation(SWT.HORIZONTAL);
						labelAvisEcheance.setSize(160, 36);
					}
					{
						btnCreerAvisEcheance = new Button(groupAvisEcheance, SWT.PUSH | SWT.CENTER);
						GridData button21LData = new GridData();
						button21LData.horizontalAlignment = GridData.FILL;
						button21LData.heightHint = 27;
						button21LData.grabExcessHorizontalSpace = true;
						btnCreerAvisEcheance.setLayoutData(button21LData);
						btnCreerAvisEcheance.setText("Créer");
					}
					{
						btnListerAvisEcheance = new Button(groupAvisEcheance, SWT.PUSH | SWT.CENTER);
						btnListerAvisEcheance.setText("Lister");
						btnListerAvisEcheance.setSize(113, 27);
					}
				}
				GridData button22LData = new GridData();
				button22LData.horizontalAlignment = GridData.FILL;
				button22LData.heightHint = 27;
				button22LData.grabExcessHorizontalSpace = true;
				btnListerAvisEcheance.setLayoutData(button22LData);
				GridData button20LData = new GridData();
				button20LData.horizontalAlignment = GridData.FILL;
				button20LData.heightHint = 27;
				button20LData.grabExcessHorizontalSpace = true;
				btnListerRemise.setLayoutData(button20LData);
				GridData button18LData = new GridData();
				button18LData.horizontalAlignment = GridData.FILL;
				button18LData.heightHint = 27;
				button18LData.grabExcessHorizontalSpace = true;
				btnListerReglement.setLayoutData(button18LData);
				GridData button16LData = new GridData();
				button16LData.horizontalAlignment = GridData.FILL;
				button16LData.heightHint = 27;
				button16LData.grabExcessHorizontalSpace = true;
				btnListerPrelevement.setLayoutData(button16LData);
				GridData button14LData = new GridData();
				button14LData.horizontalAlignment = GridData.FILL;
				button14LData.heightHint = 27;
				button14LData.grabExcessHorizontalSpace = true;
				btnListerApporteur.setLayoutData(button14LData);
				GridData button12LData = new GridData();
				button12LData.horizontalAlignment = GridData.FILL;
				button12LData.heightHint = 27;
				button12LData.grabExcessHorizontalSpace = true;
				btnListerProforma.setLayoutData(button12LData);
				GridData button10LData = new GridData();
				button10LData.horizontalAlignment = GridData.FILL;
				button10LData.heightHint = 27;
				button10LData.grabExcessHorizontalSpace = true;
				btnListerAcompte.setLayoutData(button10LData);
				GridData button8LData = new GridData();
				button8LData.horizontalAlignment = GridData.FILL;
				button8LData.heightHint = 27;
				button8LData.grabExcessHorizontalSpace = true;
				btnListerAvoir.setLayoutData(button8LData);
				GridData button6LData = new GridData();
				button6LData.horizontalAlignment = GridData.FILL;
				button6LData.heightHint = 27;
				button6LData.grabExcessHorizontalSpace = true;
				btnListerBonCde.setLayoutData(button6LData);
				GridData button4LData = new GridData();
				button4LData.horizontalAlignment = GridData.FILL;
				button4LData.heightHint = 27;
				button4LData.grabExcessHorizontalSpace = true;
				btnListerBonLiv.setLayoutData(button4LData);
				GridData button2LData = new GridData();
				button2LData.horizontalAlignment = GridData.FILL;
				button2LData.heightHint = 27;
				button2LData.grabExcessHorizontalSpace = true;
				btnListerDevis.setLayoutData(button2LData);
			}
			//			{
//				laCOMMENTAIRE = new Label(this, SWT.NONE);
//				laCOMMENTAIRE.setText("Commentaire");
//			}
			this.layout();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
//	public CCombo getComboTypeDoc() {
//		return comboTypeDoc;
//	}
//	
//	public CCombo getComboSousListe() {
//		return comboSousListe;
//	}
	
	public Label getLabelFacture() {
		return labelFacture;
	}
	
//	public Label getLaSousListe() {
//		return laSousListe;
//	}
	
	public Button getBtnCreerFacture() {
		return btnCreerFacture;
	}
	
	public Group getGroup1(Composite parent) {
		return groupFacture;
	}
	
	public Composite getComposite1(Composite parent) {
		return composite1;
	}
	
	public Button getBtnListerFacture() {
		return btnListerFacture;
	}

	public Button getBtnListerAvoir() {
		return btnListerAvoir;
	}

	public Button getBtnListerAcompte() {
		return btnListerAcompte;
	}

	public Button getBtnCreerAcompte() {
		return btnCreerAcompte;
	}

	public Button getBtnCreerAvoir() {
		return btnCreerAvoir;
	}

	public Button getBtnListerRemise() {
		return btnListerRemise;
	}

	public Button getBtnListerAvisEcheance() {
		return btnListerAvisEcheance;
	}

	public Button getBtnCreerAvisEcheance() {
		return btnCreerAvisEcheance;
	}

	public Button getBtnCreerRemise() {
		return btnCreerRemise;
	}

	public Button getBtnListerReglement() {
		return btnListerReglement;
	}

	public Button getBtnCreerReglement() {
		return btnCreerReglement;
	}

	public Button getBtnListerPrelevement() {
		return btnListerPrelevement;
	}

	public Button getBtnCreerPrelevement() {
		return btnCreerPrelevement;
	}

	public Button getBtnListerApporteur() {
		return btnListerApporteur;
	}

	public Button getBtnCreerApporteur() {
		return btnCreerApporteur;
	}

	public Button getBtnListerProforma() {
		return btnListerProforma;
	}

	public Button getBtnCreerProforma() {
		return btnCreerProforma;
	}

	public Button getBtnListerBonCde() {
		return btnListerBonCde;
	}

	public Button getBtnCreerBonCde() {
		return btnCreerBonCde;
	}

	public Button getBtnListerBonLiv() {
		return btnListerBonLiv;
	}

	public Button getBtnCreerBonLiv() {
		return btnCreerBonLiv;
	}

	public Button getBtnListerDevis() {
		return btnListerDevis;
	}

	public Button getBtnCreerDevis() {
		return btnCreerDevis;
	}

	public Label getLabelDevis() {
		return labelDevis;
	}

	public Label getLabelAcompte() {
		return labelAcompte;
	}

	public Label getLabelAvoir() {
		return labelAvoir;
	}

	public Label getLabelRemise() {
		return labelRemise;
	}

	public Label getLabelAvisEcheance() {
		return labelAvisEcheance;
	}

	public Label getLabelReglement() {
		return labelReglement;
	}

	public Label getLabelPrelevement() {
		return labelPrelevement;
	}

	public Label getLabelApporteur() {
		return labelApporteur;
	}

	public Label getLabelProforma() {
		return labelProforma;
	}

	public Label getLabelBonCde() {
		return labelBonCde;
	}

	public Label getLabelBonLiv() {
		return labelBonLiv;
	}

}
