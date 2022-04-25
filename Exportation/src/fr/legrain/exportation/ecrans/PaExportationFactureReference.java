package fr.legrain.exportation.ecrans;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.ExpandBar;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import com.cloudgarden.resource.SWTResourceManager;

import fr.legrain.lib.gui.PaBtnReduit;


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
public class PaExportationFactureReference extends org.eclipse.swt.widgets.Composite {

	{
		//Register as a resource user - SWTResourceManager will
		//handle the obtaining and disposing of resources
		SWTResourceManager.registerResourceUser(this);
	}
	
	private PaBtnReduit paBtn1;
	private Button cbReExport;

	private Text tfNumFinAvoir;
	private Label laNumFinAvoir;
	private Text tfNumDebAvoir;
	private Label laNumDebAvoir;
	private Button cbExpAvoir;
	
	private Text tfNumFinApporteur;
	private Label laNumFinApporteur;
	private Text tfNumDebApporteur;
	private Label laNumDebApporteur;
	private Button cbExpApporteur;
	
	private Label laNumDeb;
	private Text tfNumFin;
	private Label laNumFin;
	private Text tfNumDeb;
	private Label laListeDocument;
	private Button cbCentralisation;
	private Label laespace0;
	private Label laEspace1;
	private Button cbPointages2;
	private Button cbDocumentLie;
	private Button btnExporterReglements;
	private Button BtnExporterFactures;
	private Button cbPointages;
	private Button cbReglement;
	private Button cbVideRemise;
	private Button cbVideAcompte;
	private Button cbVideAvoir;
	private Button cbVideApporteur;
	private Button cbVideReglement;
	private Button cbVideVentes;
	private Button btnListeDoc;

	private Button cbExpFacture;
	

	private Group groupReglement;
	private Group groupVentes;
	private Group groupTout;
	
//	private Composite composite1;
	
	private Text tfNumFinAcompte;
	private Label laNumFinAcompte;
	private Text tfNumDebAcompte;
	private Label laNumDebAcompte;
	private Button cbExpAcompte;

	private Text tfNumFinReglement;
	private Label laNumFinReglement;
	private Text tfNumDebReglement;
	private Label laNumDebReglement;
	private Button cbExpReglement;

	private Text tfNumFinRemise;
	private Label laNumFinRemise;
	private Text tfNumDebRemise;
	private Label laNumDebRemise;
	private Button cbExpRemise;
	
	private ExpandBar expandBarOption;
	private ScrolledComposite scrolledComposite = null;
	private Composite paEcran;
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
		PaExportationFactureReference inst = new PaExportationFactureReference(shell, SWT.NULL);
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

	public PaExportationFactureReference(org.eclipse.swt.widgets.Composite parent, int style) {
		super(parent, style);
		initGUI();
	}

	private void initGUI() {
		try {
			GridLayout thisLayout = new GridLayout();
			this.setLayout(thisLayout);
			{
				scrolledComposite = new ScrolledComposite(this, SWT.H_SCROLL | SWT.V_SCROLL);
				GridData ld = new GridData();
				ld.horizontalAlignment = GridData.CENTER;
				ld.grabExcessHorizontalSpace = true;
				ld.grabExcessVerticalSpace = true;
				scrolledComposite.setLayoutData(ld);
				
				scrolledComposite.setExpandHorizontal(true);
				scrolledComposite.setExpandVertical(true);
				
				paEcran = new Composite(scrolledComposite, SWT.NONE);
				GridLayout paEcranLayout = new GridLayout();
				paEcranLayout.numColumns = 4;
				paEcran.setLayout(paEcranLayout);
				
				scrolledComposite.setContent(paEcran);
				scrolledComposite.setMinSize(paEcran.computeSize(SWT.DEFAULT, SWT.DEFAULT));
				
				
				GridData paEcranLData = new GridData();
				paEcranLData.verticalAlignment = GridData.FILL;
				paEcranLData.grabExcessVerticalSpace = true;
				paEcranLData.horizontalAlignment = GridData.FILL;
				paEcranLData.grabExcessHorizontalSpace = true;
				
				
				groupTout = new Group(paEcran, SWT.NONE);
				GridLayout groupLayout1 = new GridLayout();
				groupLayout1.numColumns = 2;
				groupTout.setLayout(groupLayout1);
				GridData groupLData = new GridData();
				groupLData.verticalAlignment = GridData.FILL;
				groupLData.horizontalAlignment = GridData.FILL;
				groupLData.grabExcessHorizontalSpace = true;
				groupLData.horizontalSpan = 2;
				groupLData.grabExcessVerticalSpace = true;
				groupTout.setLayoutData(groupLData);
				groupTout.setText("Gestion de l'exportation par référence");
				groupTout.setFont(SWTResourceManager.getFont("Tahoma", 10, 2, false, false));
				{				
					
					groupVentes = new Group(groupTout, SWT.NONE);
					GridLayout group1Layout1 = new GridLayout();
					group1Layout1.numColumns = 6;
					groupVentes.setLayout(group1Layout1);
					GridData group1LData = new GridData();
					group1LData.verticalAlignment = GridData.BEGINNING;
					group1LData.horizontalAlignment = GridData.FILL;
					group1LData.grabExcessHorizontalSpace = true;
					group1LData.horizontalSpan = 2;
					groupVentes.setLayoutData(group1LData);
					groupVentes.setText("Exportation des ventes");
					groupVentes.setFont(SWTResourceManager.getFont("Tahoma", 9, 1, false, false));
					{
						cbVideVentes = new Button(groupVentes, SWT.CHECK | SWT.LEFT);
						GridData cbVideVentesLData = new GridData();
						cbVideVentesLData.widthHint = 16;
						cbVideVentesLData.heightHint = 17;
						cbVideVentes.setLayoutData(cbVideVentesLData);
					}
					
					{
						laNumDeb = new Label(groupVentes, SWT.NONE);
						GridData laNumDebLData = new GridData();
						laNumDebLData.heightHint = 13;
						laNumDebLData.widthHint = 70;
						laNumDeb.setLayoutData(laNumDebLData);
						laNumDeb.setText("De la vente");
					}
					{
						GridData tfNumDebLData = new GridData();
						tfNumDebLData.heightHint = 13;
						tfNumDebLData.widthHint = 74;
						tfNumDeb = new Text(groupVentes, SWT.BORDER);
						tfNumDeb.setLayoutData(tfNumDebLData);
					}
					{
						laNumFin = new Label(groupVentes, SWT.NONE);
						laNumFin.setText("à la vente");
						laNumFin.setAlignment(SWT.CENTER);
					}
					{
						GridData tfNumFinLData = new GridData();
						tfNumFinLData.heightHint = 13;
						tfNumFinLData.widthHint = 74;
						tfNumFin = new Text(groupVentes, SWT.BORDER);
						tfNumFin.setLayoutData(tfNumFinLData);
					}
					{
						GridData cbExpFactureLData = new GridData();
						cbExpFactureLData.horizontalAlignment = GridData.END;
						cbExpFacture = new Button(groupVentes, SWT.CHECK | SWT.LEFT);
						cbExpFacture.setLayoutData(cbExpFactureLData);
						cbExpFacture.setText("Ré-exporter");
					}
					
					{
						cbVideAvoir = new Button(groupVentes, SWT.CHECK | SWT.LEFT);
						GridData cbSelectionAvoirLData = new GridData();
						cbSelectionAvoirLData.widthHint = 19;
						cbSelectionAvoirLData.heightHint = 17;
						cbVideAvoir.setLayoutData(cbSelectionAvoirLData);
						cbVideAvoir.setText("  ");
					}
					
					{
						laNumDebAvoir = new Label(groupVentes, SWT.NONE);
						GridData laNumDebAvoirLData = new GridData();
						laNumDebAvoir.setLayoutData(laNumDebAvoirLData);
						laNumDebAvoir.setText("De l'avoir");
					}
					{
						GridData tfNumDebAvoirLData = new GridData();
						tfNumDebAvoirLData.heightHint = 13;
						tfNumDebAvoirLData.widthHint = 74;
						tfNumDebAvoir = new Text(groupVentes, SWT.BORDER);
						tfNumDebAvoir.setLayoutData(tfNumDebAvoirLData);
					}
					{
						laNumFinAvoir = new Label(groupVentes, SWT.NONE);
						laNumFinAvoir.setText("à l'avoir");
						laNumFinAvoir.setAlignment(SWT.CENTER);
					}
					{
						GridData tfNumFinAvoirLData = new GridData();
						tfNumFinAvoirLData.heightHint = 13;
						tfNumFinAvoirLData.widthHint = 74;
						tfNumFinAvoir = new Text(groupVentes, SWT.BORDER);
						tfNumFinAvoir.setLayoutData(tfNumFinAvoirLData);
					}
					{
						GridData cbExpAvoirLData = new GridData();
						cbExpAvoirLData.horizontalAlignment = GridData.END;
						cbExpAvoir = new Button(groupVentes, SWT.CHECK | SWT.LEFT);
						cbExpAvoir.setLayoutData(cbExpAvoirLData);
						cbExpAvoir.setText("Ré-exporter");
					}
					
					
					{
						cbVideApporteur = new Button(groupVentes, SWT.CHECK | SWT.LEFT);
						GridData cbSelectionAvoirLData = new GridData();
						cbSelectionAvoirLData.widthHint = 19;
						cbSelectionAvoirLData.heightHint = 17;
						cbVideApporteur.setLayoutData(cbSelectionAvoirLData);
						cbVideApporteur.setText("  ");
					}
					
					{
						laNumDebApporteur = new Label(groupVentes, SWT.NONE);
						GridData laNumDebApporteurLData = new GridData();
						laNumDebApporteur.setLayoutData(laNumDebApporteurLData);
						laNumDebApporteur.setText("De la facture apporteur");
					}
					{
						GridData tfNumDebApporteurLData = new GridData();
						tfNumDebApporteurLData.heightHint = 13;
						tfNumDebApporteurLData.widthHint = 74;
						tfNumDebApporteur = new Text(groupVentes, SWT.BORDER);
						tfNumDebApporteur.setLayoutData(tfNumDebApporteurLData);
					}
					{
						laNumFinApporteur = new Label(groupVentes, SWT.NONE);
						laNumFinApporteur.setText("à la facture apporteur");
						laNumFinApporteur.setAlignment(SWT.CENTER);
					}
					{
						GridData tfNumFinApporteurLData = new GridData();
						tfNumFinApporteurLData.heightHint = 13;
						tfNumFinApporteurLData.widthHint = 74;
						tfNumFinApporteur = new Text(groupVentes, SWT.BORDER);
						tfNumFinApporteur.setLayoutData(tfNumFinApporteurLData);
					}
					{
						GridData cbExpApporteurLData = new GridData();
						cbExpApporteurLData.horizontalAlignment = GridData.END;
						cbExpApporteur = new Button(groupVentes, SWT.CHECK | SWT.LEFT);
						cbExpApporteur.setLayoutData(cbExpApporteurLData);
						cbExpApporteur.setText("Ré-exporter");
					}
					{
						laespace0 = new Label(groupVentes, SWT.NONE);
						GridData laespace0LData = new GridData();
						laespace0LData.horizontalSpan = 6;
						laespace0LData.widthHint = -2;
						laespace0LData.heightHint = 15;
						laespace0.setLayoutData(laespace0LData);
					}
					{
						cbReglement = new Button(groupVentes, SWT.CHECK | SWT.LEFT);
						GridData cbReglementLData = new GridData();
						cbReglementLData.verticalAlignment = GridData.BEGINNING;
						cbReglementLData.grabExcessHorizontalSpace = true;
						cbReglementLData.horizontalSpan = 3;
						cbReglementLData.horizontalAlignment = GridData.END;
						cbReglementLData.widthHint = 362;
						cbReglementLData.heightHint = 17;
						cbReglementLData.verticalSpan = 4;
						cbReglement.setLayoutData(cbReglementLData);
						cbReglement.setText("Exporter les règlements liés aux documents");
					}
					{
						cbPointages = new Button(groupVentes, SWT.CHECK | SWT.LEFT);
						GridData cbPointagesLData = new GridData();
						cbPointagesLData.verticalAlignment = GridData.BEGINNING;
						cbPointagesLData.widthHint = 215;
						cbPointagesLData.heightHint = 16;
						cbPointages.setLayoutData(cbPointagesLData);
						cbPointages.setText("Exporter les pointages");
					}
					{
						cbCentralisation = new Button(groupVentes, SWT.CHECK | SWT.LEFT);
						GridData button1LData2 = new GridData();
						button1LData2.widthHint = 173;
						button1LData2.heightHint = 17;
						button1LData2.verticalAlignment = GridData.BEGINNING;
						button1LData2.grabExcessHorizontalSpace = true;
						cbCentralisation.setLayoutData(button1LData2);
						cbCentralisation.setText("Centraliser les écritures");
					}
					{
						GridData expandBarLData = new GridData();
						expandBarLData.verticalAlignment = GridData.BEGINNING;
						expandBarLData.grabExcessVerticalSpace = true;
						expandBarLData.heightHint = 164;
						expandBarLData.horizontalSpan = 4;
						expandBarLData.widthHint = 392;
						expandBarLData.grabExcessHorizontalSpace = true;
						expandBarOption = new ExpandBar(groupVentes, SWT.V_SCROLL);
						expandBarOption.setLayoutData(expandBarLData);
					}
					{
						BtnExporterFactures = new Button(groupVentes, SWT.PUSH | SWT.CENTER);
						GridData BtnExporterFacturesLData = new GridData();
						BtnExporterFacturesLData.heightHint = 28;
						BtnExporterFacturesLData.verticalAlignment = GridData.BEGINNING;
						BtnExporterFacturesLData.grabExcessHorizontalSpace = true;
						BtnExporterFacturesLData.widthHint = 167;
						BtnExporterFactures.setLayoutData(BtnExporterFacturesLData);
						BtnExporterFactures.setText("Exporter les ventes");
						BtnExporterFactures.setSize(167, 28);
					}

				}
				{
					groupReglement = new Group(groupTout, SWT.NONE);
					GridLayout group1Layout1 = new GridLayout();
					group1Layout1.numColumns = 6;
					groupReglement.setLayout(group1Layout1);
					GridData group1LData = new GridData();
					group1LData.verticalAlignment = GridData.BEGINNING;
					group1LData.horizontalAlignment = GridData.FILL;
					group1LData.grabExcessHorizontalSpace = true;
					group1LData.horizontalSpan = 2;
					group1LData.heightHint = 197;
					groupReglement.setLayoutData(group1LData);
					groupReglement.setText("Exportation des règlements");
					groupReglement.setFont(SWTResourceManager.getFont("Tahoma", 9, 1, false, false));
					{
						cbVideAcompte = new Button(groupReglement, SWT.CHECK | SWT.LEFT);
						GridData button1LData1 = new GridData();
						cbVideAcompte.setLayoutData(button1LData1);
					}
					
					{
						laNumDebAcompte = new Label(groupReglement, SWT.NONE);
						GridData laNumDebAcompteLData = new GridData();
						laNumDebAcompte.setLayoutData(laNumDebAcompteLData);
						laNumDebAcompte.setText("De l'acompte");
					}
					{
						GridData tfNumDebAcompteLData = new GridData();
						tfNumDebAcompteLData.heightHint = 13;
						tfNumDebAcompteLData.widthHint = 74;
						tfNumDebAcompte = new Text(groupReglement, SWT.BORDER);
						tfNumDebAcompte.setLayoutData(tfNumDebAcompteLData);
					}
					{
						laNumFinAcompte = new Label(groupReglement, SWT.NONE);
						laNumFinAcompte.setText("à l'acompte");
						laNumFinAcompte.setAlignment(SWT.CENTER);
					}
					{
						GridData tfNumFinAcompteLData = new GridData();
						tfNumFinAcompteLData.heightHint = 13;
						tfNumFinAcompteLData.widthHint = 74;
						tfNumFinAcompte = new Text(groupReglement, SWT.BORDER);
						tfNumFinAcompte.setLayoutData(tfNumFinAcompteLData);
					}
					{
						GridData cbExpAcompteLData = new GridData();
						cbExpAcompteLData.horizontalAlignment = GridData.END;
						cbExpAcompte = new Button(groupReglement, SWT.CHECK | SWT.LEFT);
						cbExpAcompte.setLayoutData(cbExpAcompteLData);
						cbExpAcompte.setText("Ré-exporter");
					}
					{
						cbVideReglement = new Button(groupReglement, SWT.CHECK | SWT.LEFT);
						GridData cbVideReglementLData = new GridData();
						cbVideReglement.setLayoutData(cbVideReglementLData);
					}
					
					
					{
						laNumDebReglement = new Label(groupReglement, SWT.NONE);
						laNumDebReglement.setText("Du règlement");
					}
					{
						GridData tfNumDebReglementLData = new GridData();
						tfNumDebReglementLData.heightHint = 13;
						tfNumDebReglementLData.widthHint = 74;
						tfNumDebReglement = new Text(groupReglement, SWT.BORDER);
						tfNumDebReglement.setLayoutData(tfNumDebReglementLData);
					}
					{
						laNumFinReglement = new Label(groupReglement, SWT.NONE);
						laNumFinReglement.setText("au règlement");
						laNumFinReglement.setAlignment(SWT.CENTER);
					}
					{
						GridData tfNumFinReglementLData = new GridData();
						tfNumFinReglementLData.heightHint = 13;
						tfNumFinReglementLData.widthHint = 74;
						tfNumFinReglement = new Text(groupReglement, SWT.BORDER);
						tfNumFinReglement.setLayoutData(tfNumFinReglementLData);
					}
					{
						GridData cbExpReglementLData = new GridData();
						cbExpReglementLData.horizontalAlignment = GridData.END;
						cbExpReglement = new Button(groupReglement, SWT.CHECK | SWT.LEFT);
						cbExpReglement.setLayoutData(cbExpReglementLData);
						cbExpReglement.setText("Ré-exporter");
					}
					{
						GridData cbVideRemiseLData = new GridData();
						cbVideRemise = new Button(groupReglement, SWT.CHECK | SWT.LEFT);
						cbVideRemise.setLayoutData(cbVideRemiseLData);
					}
					
					
					{
						laNumDebRemise = new Label(groupReglement, SWT.NONE);
						laNumDebRemise.setText("De la remise");
					}
					{
						GridData tfNumDebReglementLData = new GridData();
						tfNumDebReglementLData.heightHint = 13;
						tfNumDebReglementLData.widthHint = 74;
						tfNumDebRemise = new Text(groupReglement, SWT.BORDER);
						tfNumDebRemise.setLayoutData(tfNumDebReglementLData);
					}
					{
						laNumFinRemise = new Label(groupReglement, SWT.NONE);
						laNumFinRemise.setText("à la remise");
						laNumFinRemise.setAlignment(SWT.CENTER);
					}
					{
						GridData tfNumFinReglementLData = new GridData();
						tfNumFinReglementLData.heightHint = 13;
						tfNumFinReglementLData.widthHint = 74;
						tfNumFinRemise = new Text(groupReglement, SWT.BORDER);
						tfNumFinRemise.setLayoutData(tfNumFinReglementLData);
					}
					{
						GridData cbExpReglementLData = new GridData();
						cbExpReglementLData.horizontalAlignment = GridData.END;
						cbExpReglementLData.grabExcessHorizontalSpace = true;
						cbExpRemise = new Button(groupReglement, SWT.CHECK | SWT.LEFT);
						cbExpRemise.setLayoutData(cbExpReglementLData);
						cbExpRemise.setText("Ré-exporter");
					}
					{
						laEspace1 = new Label(groupReglement, SWT.NONE);
						GridData laEspace1LData = new GridData();
						laEspace1LData.horizontalSpan = 3;
						laEspace1.setLayoutData(laEspace1LData);
					}
					{
						cbDocumentLie = new Button(groupReglement, SWT.CHECK | SWT.LEFT);
						GridData cbDocumentLieLData = new GridData();
						cbDocumentLieLData.horizontalSpan = 4;
						cbDocumentLie.setLayoutData(cbDocumentLieLData);
						cbDocumentLie.setText("Exporter les documents liés aux règlements");
					}
					{
						cbPointages2 = new Button(groupReglement, SWT.CHECK | SWT.LEFT);
						GridData cbPointages2LData = new GridData();
						cbPointages2LData.horizontalSpan = 5;
						cbPointages2.setLayoutData(cbPointages2LData);
						cbPointages2.setText("Exporter les pointages");
					}
					{
						btnExporterReglements = new Button(groupReglement, SWT.PUSH | SWT.CENTER);
						GridData btnExporterReglementsLData = new GridData();
						btnExporterReglementsLData.horizontalSpan = 7;
						btnExporterReglementsLData.horizontalAlignment = GridData.CENTER;
						btnExporterReglementsLData.widthHint = 160;
						btnExporterReglementsLData.heightHint = 28;
						btnExporterReglementsLData.verticalAlignment = GridData.END;
						btnExporterReglementsLData.grabExcessVerticalSpace = true;
						btnExporterReglements.setLayoutData(btnExporterReglementsLData);
						btnExporterReglements.setText("Exporter les règlements");
						btnExporterReglements.setSize(160, 28);
					}
				}
				{
					cbReExport = new Button(groupTout, SWT.CHECK | SWT.LEFT);
					GridData button1LData = new GridData();
					button1LData.verticalAlignment = GridData.BEGINNING;
					button1LData.grabExcessHorizontalSpace = true;
					button1LData.horizontalAlignment = GridData.END;
					button1LData.horizontalSpan = 2;
					cbReExport.setLayoutData(button1LData);
					cbReExport.setText("Tout réexporter");
				}
				{
					laListeDocument = new Label(groupTout, SWT.NONE);
					GridData laListeDocumentLData = new GridData();
					laListeDocumentLData.horizontalAlignment = GridData.END;
					laListeDocumentLData.grabExcessHorizontalSpace = true;
					laListeDocument.setLayoutData(laListeDocumentLData);
					laListeDocument.setText("Etat d'exportation des documents");
					laListeDocument.setFont(SWTResourceManager.getFont("Tahoma", 8, 1, false, false));
				}
				{
					btnListeDoc = new Button(groupTout, SWT.PUSH | SWT.CENTER);
					GridData btnListeDocLData = new GridData();
					btnListeDocLData.verticalAlignment = GridData.BEGINNING;
					btnListeDocLData.widthHint = 160;
					btnListeDocLData.heightHint = 28;
					btnListeDocLData.horizontalAlignment = GridData.END;
					btnListeDoc.setLayoutData(btnListeDocLData);
					btnListeDoc.setText("Liste des documents");
					btnListeDoc.setSize(160, 28);
				}
				{
					GridData paBtn1LData = new GridData();
					paBtn1LData.horizontalAlignment = GridData.CENTER;
					paBtn1LData.grabExcessHorizontalSpace = true;
					paBtn1LData.horizontalSpan = 2;
					paBtn1LData.verticalAlignment = GridData.END;
					paBtn1LData.grabExcessVerticalSpace = true;
					paBtn1LData.heightHint = 34;
					paBtn1LData.widthHint = 215;
					paBtn1 = new PaBtnReduit(groupTout, SWT.NONE);
					GridLayout paBtn1Layout = new GridLayout();
					paBtn1Layout.numColumns = 2;
					paBtn1.setLayout(paBtn1Layout);
					paBtn1.getBtnImprimer().setText("Fermer");
					paBtn1.setLayoutData(paBtn1LData);
					GridData btnFermerLData = new GridData();
					btnFermerLData.horizontalAlignment = GridData.FILL;
					btnFermerLData.verticalAlignment = GridData.FILL;
					btnFermerLData.grabExcessVerticalSpace = true;
					btnFermerLData.grabExcessHorizontalSpace = true;
					GridData btnImprimerLData = new GridData();
					btnImprimerLData.grabExcessHorizontalSpace = true;
					btnImprimerLData.horizontalAlignment = GridData.FILL;
					btnImprimerLData.heightHint = 28;
					paBtn1.getBtnFermer().setLayoutData(btnFermerLData);
					paBtn1.getBtnImprimer().setLayoutData(btnImprimerLData);
					paBtn1.getBtnFermer().setText("< Précédent");
					paBtn1.getBtnFermer().setSize(82, 28);
					paBtn1.getBtnImprimer().setSize(50, 28);
				}
				
			}
			thisLayout.numColumns = 2;
			this.layout();
			pack();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public Text getTfNumFinAcompte() {
		return tfNumFinAcompte;
	}

	public Label getLaNumFinAcompte() {
		return laNumFinAcompte;
	}

	public Text getTfNumDebAcompte() {
		return tfNumDebAcompte;
	}

	public Label getLaNumDebAcompte() {
		return laNumDebAcompte;
	}

	public Text getTfNumFinReglement() {
		return tfNumFinReglement;
	}

	public Label getLaNumFinReglement() {
		return laNumFinReglement;
	}

	public Text getTfNumDebReglement() {
		return tfNumDebReglement;
	}

	public Label getLaNumDebReglement() {
		return laNumDebReglement;
	}

	public Label getLaNumDeb() {
		return laNumDeb;
	}
	
	public Text getTfNumDeb() {
		return tfNumDeb;
	}
	
	public Label getLaNumFin() {
		return laNumFin;
	}
	
	public Text getTfNumFin() {
		return tfNumFin;
	}
	
	
	public Button getCbReExport() {
		return cbReExport;
	}
	
	public PaBtnReduit getPaBtn1() {
		return paBtn1;
	}
	

	public Label getLaNumFinAvoir() {
		return laNumFinAvoir;
	}

	public Text getTfNumFinAvoir() {
		return tfNumFinAvoir;
	}

	public Label getLaNumDebAvoir() {
		return laNumDebAvoir;
	}


	public Text getTfNumDebAvoir() {
		return tfNumDebAvoir;
	}
	
	public Button getCbReExpFacture() {
		return cbExpFacture;
	}
	
	public Button getCbReExpAvoir() {
		return cbExpAvoir;
	}
	
	public Button getCbReExpAcompte() {
		return cbExpAcompte;
	}
	
	public Button getCbReExpReglement() {
		return cbExpReglement;
	}

	public Text getTfNumFinRemise() {
		return tfNumFinRemise;
	}

	public Text getTfNumDebRemise() {
		return tfNumDebRemise;
	}

	public Button getCbReExpRemise() {
		return cbExpRemise;
	}

	public Label getLaNumFinRemise() {
		return laNumFinRemise;
	}

	public Label getLaNumDebRemise() {
		return laNumDebRemise;
	}
	

//	public Button getCbGereRelation() {
//		return cbGereRelation;
//	}


	public ExpandBar getExpandBarOption() {
		return expandBarOption;
	}
	
	public Button getBtnListeDoc() {
		return btnListeDoc;
	}

	public Group getGroupReglement() {
		return groupReglement;
	}
	
	public Button getCbRempliReglement() {
		return cbVideReglement;
	}

	public Button getCbRempliVentes() {
		return cbVideVentes;
	}
	
	
	public Button getCbSelectionAvoir() {
		return cbVideAvoir;
	}
	
	public Button getCbRempliRemise() {
		return cbVideRemise;
	}
	
	public Button getCbReglement() {
		return cbReglement;
	}
	
	public Button getCbPointages() {
		return cbPointages;
	}
	
	public Button getBtnExporterFactures() {
		return BtnExporterFactures;
	}
	
	public Button getBtnExporterReglements() {
		return btnExporterReglements;
	}
	
	public Button getCbDocumentLie() {
		return cbDocumentLie;
	}
	
	public Button getCbPointages2() {
		return cbPointages2;
	}

	public Button getCbExpAvoir() {
		return cbExpAvoir;
	}

	public Button getCbRempliAcompte() {
		return cbVideAcompte;
	}

	public Button getCbRempliAvoir() {
		return cbVideAvoir;
	}

	public Button getCbExpFacture() {
		return cbExpFacture;
	}

	public Group getGroupVentes() {
		return groupVentes;
	}

	public Button getCbExpAcompte() {
		return cbExpAcompte;
	}

	public Button getCbExpReglement() {
		return cbExpReglement;
	}

	public Button getCbExpRemise() {
		return cbExpRemise;
	}

	public ScrolledComposite getScrolledComposite() {
		return scrolledComposite;
	}
	public Composite getPaEcran() {
		return paEcran;
	}

	public Button getCbCentralisation() {
		return cbCentralisation;
	}

	public Text getTfNumFinApporteur() {
		return tfNumFinApporteur;
	}

	public Label getLaNumFinApporteur() {
		return laNumFinApporteur;
	}

	public Text getTfNumDebApporteur() {
		return tfNumDebApporteur;
	}

	public Label getLaNumDebApporteur() {
		return laNumDebApporteur;
	}

	public Button getCbExpApporteur() {
		return cbExpApporteur;
	}

	public Button getCbRempliApporteur() {
		return cbVideApporteur;
	}
}
