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
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.ExpandBar;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

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
public class PaExportationFactureDates extends org.eclipse.swt.widgets.Composite {

	{
		//Register as a resource user - SWTResourceManager will
		//handle the obtaining and disposing of resources
		SWTResourceManager.registerResourceUser(this);
	}
	
	private PaBtnReduit paBtn1;
	private Button cbReExport;
//	private Button cbGereRelation;
	private DateTime tfDATEFINVentes;
	private Label laDATEFINVentes;
	private DateTime tfDATEDEBVentes;
	private Label laDATEDEBVentes;
	
	private DateTime tfDATEFINReglement;
	private Label laListeDocument;
	private Button cbCentralisation;


	private Label laEspace0;
	private Label laespace1;
	private Button btnExporterReglement;
	private Button cbPointages2;
	private Button cbDocumentLie;
	private Button btnExporterVentes;
	private Button cbPointages;
	private Button cbReglementLie;
	private Button cbRexportRemise;
	private Button cbRexportReglement;
	private Button cbReExportAcompte;
	private Button cbRexportAvoirs;
	private Button cbRexportApporteurs;
	private Button cbRexportFacture;
	private Button btnListeDoc;
	private Label laDATEFINReglement;
	private DateTime tfDATEDEBReglement;
	private Label laDATEDEBReglement;

	private Group groupDateVentes;
	private Group groupDateReglements;
	private Group groupTout;
	
	private Button cbAcceptAvoir;
	private Button cbAcceptApporteur;
	private Button cbAcceptFacture;
	private Button cbAcceptRemise;
	private Button cbAcceptReglement;
	private Button cbAccepAcompte;

//	private Composite composite1;
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
		PaExportationFactureDates inst = new PaExportationFactureDates(shell, SWT.NULL);
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

	public PaExportationFactureDates(org.eclipse.swt.widgets.Composite parent, int style) {
		super(parent, style);
		initGUI();
	}

	private void initGUI() {
		try {
			GridLayout thisLayout = new GridLayout();
			this.setLayout(thisLayout);
			thisLayout.numColumns = 1;
			{
				
				scrolledComposite = new ScrolledComposite(this, SWT.H_SCROLL | SWT.V_SCROLL /*| SWT.BORDER*/);
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
				groupTout.setText("Gestion de l'exportation par date");
				groupTout.setFont(SWTResourceManager.getFont("Tahoma", 10, 2, false, false));
				//groupTout.setFont(SWTResourceManager.getFont("Tahoma", 10, 3, false, false));
				{
					groupDateVentes = new Group(groupTout, SWT.NONE);
					GridLayout group2Layout = new GridLayout();
					group2Layout.numColumns = 4;
					groupDateVentes.setLayout(group2Layout);
					GridData group2LData = new GridData();
					group2LData.heightHint = 301;
					group2LData.horizontalSpan = 2;
					group2LData.grabExcessHorizontalSpace = true;
					group2LData.verticalAlignment = GridData.BEGINNING;
					group2LData.horizontalAlignment = GridData.FILL;
					groupDateVentes.setLayoutData(group2LData);
					groupDateVentes.setText("Exportation des ventes");
					groupDateVentes.setFont(SWTResourceManager.getFont("Tahoma", 9, 1, false, false));
//					groupDateVentes.setFont(SWTResourceManager.getFont("Tahoma", 9, 1, false, false));
					{
						laDATEDEBVentes = new Label(groupDateVentes, SWT.NONE);
						GridData laDATEDEBLData = new GridData();
						laDATEDEBLData.heightHint = 13;
						laDATEDEBLData.widthHint = 107;
						laDATEDEBLData.horizontalAlignment = GridData.END;
						laDATEDEBVentes.setLayoutData(laDATEDEBLData);
						laDATEDEBVentes.setText("Date début");
					}
					{
						tfDATEDEBVentes = new DateTime(groupDateVentes, SWT.BORDER | SWT.DROP_DOWN);
						GridData tfDATEDEBLData = new GridData();
						tfDATEDEBLData.verticalAlignment = GridData.FILL;
						tfDATEDEBLData.widthHint = 130;
						tfDATEDEBVentes.setLayoutData(tfDATEDEBLData);
//						tfDATEDEBVentes.setPattern("dd/MM/yyyy");
						tfDATEDEBVentes.setSize(130, 18);
					}
					{
						laDATEFINVentes = new Label(groupDateVentes, SWT.NONE);
						laDATEFINVentes.setText("Date fin");
						GridData laDATEFINLData = new GridData();
						laDATEFINLData.heightHint = 13;
						laDATEFINVentes.setLayoutData(laDATEFINLData);
						laDATEFINVentes.setAlignment(SWT.CENTER);
					}
					{
						tfDATEFINVentes = new DateTime(groupDateVentes, SWT.BORDER | SWT.DROP_DOWN);
						GridData tfDATEFINLData = new GridData();
						tfDATEFINLData.verticalAlignment = GridData.FILL;
						tfDATEFINLData.widthHint = 130;
						tfDATEFINVentes.setLayoutData(tfDATEFINLData);
//						tfDATEFINVentes.setPattern("dd/MM/yyyy");
						tfDATEFINVentes.setSize(130, 18);
					}
					{
					cbAcceptFacture = new Button(groupDateVentes, SWT.CHECK | SWT.LEFT);
					GridData cbAcceptFactureLData = new GridData();
					cbAcceptFactureLData.horizontalSpan = 2;
					cbAcceptFacture.setLayoutData(cbAcceptFactureLData);
					cbAcceptFacture.setText("Factures de ventes");
					}
					{
						cbRexportFacture = new Button(groupDateVentes, SWT.CHECK | SWT.LEFT);
						GridData cbRexportFactureLData = new GridData();
						cbRexportFactureLData.horizontalSpan = 2;
						cbRexportFactureLData.horizontalAlignment = GridData.END;
						cbRexportFacture.setLayoutData(cbRexportFactureLData);
						cbRexportFacture.setText("Ré-exporter");
					}
					{
						cbAcceptAvoir = new Button(groupDateVentes, SWT.CHECK | SWT.LEFT);
						GridData cbAcceptFactureLData = new GridData();
						cbAcceptFactureLData.horizontalSpan = 2;
						cbAcceptAvoir.setLayoutData(cbAcceptFactureLData);
						cbAcceptAvoir.setText("Factures d'avoirs");
						}
					{
						cbRexportAvoirs = new Button(groupDateVentes, SWT.CHECK | SWT.LEFT);
						GridData cbRexportAvoirsLData = new GridData();
						cbRexportAvoirsLData.horizontalSpan = 2;
						cbRexportAvoirsLData.horizontalAlignment = GridData.END;
						cbRexportAvoirs.setLayoutData(cbRexportAvoirsLData);
						cbRexportAvoirs.setText("Ré-exporter");
					}
					{
						cbAcceptApporteur = new Button(groupDateVentes, SWT.CHECK | SWT.LEFT);
						GridData cbAcceptFactureLData = new GridData();
						cbAcceptFactureLData.horizontalSpan = 2;
						cbAcceptApporteur.setLayoutData(cbAcceptFactureLData);
						cbAcceptApporteur.setText("Factures apporteurs");
						}
					{
						cbRexportApporteurs = new Button(groupDateVentes, SWT.CHECK | SWT.LEFT);
						GridData cbRexportAvoirsLData = new GridData();
						cbRexportAvoirsLData.horizontalSpan = 2;
						cbRexportAvoirsLData.horizontalAlignment = GridData.END;
						cbRexportApporteurs.setLayoutData(cbRexportAvoirsLData);
						cbRexportApporteurs.setText("Ré-exporter");
					}					
					{
						laEspace0 = new Label(groupDateVentes, SWT.NONE);
						GridData laEspace0LData = new GridData();
						laEspace0LData.horizontalSpan = 3;
						laEspace0LData.widthHint = 0;
						laEspace0LData.heightHint = 10;
						laEspace0.setLayoutData(laEspace0LData);
					}
					{
						cbReglementLie = new Button(groupDateVentes, SWT.CHECK | SWT.LEFT);
						GridData cbReglementLieLData = new GridData();
						cbReglementLieLData.horizontalSpan = 2;
						cbReglementLieLData.grabExcessVerticalSpace = true;
						cbReglementLieLData.verticalAlignment = GridData.BEGINNING;
						cbReglementLie.setLayoutData(cbReglementLieLData);
						cbReglementLie.setText("Exporter les règlements liés aux documents");
					}
					{
						cbPointages = new Button(groupDateVentes, SWT.CHECK | SWT.LEFT);
						GridData cbPointagesLData = new GridData();
						cbPointagesLData.verticalAlignment = GridData.BEGINNING;
						cbPointages.setLayoutData(cbPointagesLData);
						cbPointages.setText("Exporter les pointages");
					}
					{
						cbCentralisation = new Button(groupDateVentes, SWT.CHECK | SWT.LEFT);
						GridData button1LData1 = new GridData();
						button1LData1.widthHint = 156;
						button1LData1.heightHint = 17;
						button1LData1.verticalAlignment = GridData.BEGINNING;
						button1LData1.grabExcessHorizontalSpace = true;
						cbCentralisation.setLayoutData(button1LData1);
						cbCentralisation.setText("Centraliser les écritures");
					}
					}
				{
					GridData expandBarLData = new GridData();
					expandBarLData.horizontalSpan = 3;
					expandBarLData.horizontalAlignment = GridData.FILL;
					expandBarLData.grabExcessHorizontalSpace = true;
					expandBarLData.heightHint = 165;
					expandBarLData.verticalAlignment = GridData.BEGINNING;
					expandBarLData.grabExcessVerticalSpace = true;
					expandBarOption = new ExpandBar(groupDateVentes, SWT.V_SCROLL);
					expandBarOption.setLayoutData(expandBarLData);
				}
				{
					btnExporterVentes = new Button(groupDateVentes, SWT.PUSH | SWT.CENTER);
					GridData btnExporterVentesLData = new GridData();
					btnExporterVentesLData.grabExcessHorizontalSpace = true;
					btnExporterVentesLData.widthHint = 160;
					btnExporterVentesLData.heightHint = 28;
					btnExporterVentesLData.grabExcessVerticalSpace = true;
					btnExporterVentesLData.verticalAlignment = GridData.BEGINNING;
					btnExporterVentes.setLayoutData(btnExporterVentesLData);
					btnExporterVentes.setText("Exporter les ventes");
					btnExporterVentes.setSize(160, 28);
				}

				{
					groupDateReglements = new Group(groupTout, SWT.NONE);
					GridLayout group2Layout = new GridLayout();
					group2Layout.numColumns = 4;
					GridData group2LData = new GridData();
					group2LData.heightHint = 189;
					group2LData.horizontalSpan = 2;
					group2LData.grabExcessHorizontalSpace = true;
					group2LData.verticalAlignment = GridData.BEGINNING;
					group2LData.horizontalAlignment = GridData.FILL;
					groupDateReglements.setLayout(group2Layout);
					groupDateReglements.setLayoutData(group2LData);
					groupDateReglements.setText("Exportation des règlements");
					groupDateReglements.setFont(SWTResourceManager.getFont("Tahoma", 9, 1, false, false));
//					groupDateReglements.setFont(SWTResourceManager.getFont("Tahoma", 9, 1, false, false));
					{
						laDATEDEBReglement = new Label(groupDateReglements, SWT.NONE);
						GridData laDATEDEBLData = new GridData();
						laDATEDEBLData.heightHint = 13;
						laDATEDEBLData.widthHint = 88;
						laDATEDEBLData.horizontalAlignment = GridData.END;
						laDATEDEBReglement.setLayoutData(laDATEDEBLData);
						laDATEDEBReglement.setText("Date début");
					}
					{
						tfDATEDEBReglement = new DateTime(groupDateReglements, SWT.BORDER | SWT.DROP_DOWN);
						GridData tfDATEDEBLData = new GridData();
						tfDATEDEBLData.verticalAlignment = GridData.FILL;
						tfDATEDEBLData.widthHint = 130;
						tfDATEDEBReglement.setLayoutData(tfDATEDEBLData);
//						tfDATEDEBReglement.setPattern("dd/MM/yyyy");
						tfDATEDEBReglement.setSize(130, 18);
					}
					{
						laDATEFINReglement = new Label(groupDateReglements, SWT.NONE);
						laDATEFINReglement.setText("Date fin");
						GridData laDATEFINLData = new GridData();
						laDATEFINLData.heightHint = 13;
						laDATEFINReglement.setLayoutData(laDATEFINLData);
						laDATEFINReglement.setAlignment(SWT.CENTER);
					}
					{
						tfDATEFINReglement = new DateTime(groupDateReglements, SWT.BORDER | SWT.DROP_DOWN);
						GridData tfDATEFINLData = new GridData();
						tfDATEFINLData.widthHint = 130;
						tfDATEFINLData.verticalAlignment = GridData.FILL;
						tfDATEFINReglement.setLayoutData(tfDATEFINLData);
//						tfDATEFINReglement.setPattern("dd/MM/yyyy");
						tfDATEFINReglement.setSize(130, 18);
					}
					{
						cbAccepAcompte = new Button(groupDateReglements, SWT.CHECK | SWT.LEFT);
						GridData cbAcceptFactureLData = new GridData();
						cbAcceptFactureLData.horizontalSpan = 2;
						cbAccepAcompte.setLayoutData(cbAcceptFactureLData);
						cbAccepAcompte.setText("Acomptes");
					}
					{
						cbReExportAcompte = new Button(groupDateReglements, SWT.CHECK | SWT.LEFT);
						GridData cbReExportAcompteLData = new GridData();
						cbReExportAcompteLData.horizontalAlignment = GridData.END;
						cbReExportAcompteLData.horizontalSpan = 2;
						cbReExportAcompte.setLayoutData(cbReExportAcompteLData);
						cbReExportAcompte.setText("Ré-exporter");
					}
					{
						cbAcceptReglement = new Button(groupDateReglements, SWT.CHECK | SWT.LEFT);
						GridData cbAcceptFactureLData = new GridData();
						cbAcceptFactureLData.horizontalSpan = 2;
						cbAcceptReglement.setLayoutData(cbAcceptFactureLData);
						cbAcceptReglement.setText("Règlements (autres que acomptes et remises)");
					}
					{
						cbRexportReglement = new Button(groupDateReglements, SWT.CHECK | SWT.LEFT);
						GridData cbRexportReglementLData = new GridData();
						cbRexportReglementLData.horizontalSpan = 2;
						cbRexportReglementLData.horizontalAlignment = GridData.END;
						cbRexportReglement.setLayoutData(cbRexportReglementLData);
						cbRexportReglement.setText("Ré-exporter");
					}
					{
						cbAcceptRemise = new Button(groupDateReglements, SWT.CHECK | SWT.LEFT);
						GridData cbAcceptFactureLData = new GridData();
						cbAcceptFactureLData.horizontalSpan = 2;
						cbAcceptRemise.setLayoutData(cbAcceptFactureLData);
						cbAcceptRemise.setText("Remises");
					}
					{
						cbRexportRemise = new Button(groupDateReglements, SWT.CHECK | SWT.LEFT);
						GridData cbRexportRemiseLData = new GridData();
						cbRexportRemiseLData.horizontalSpan = 2;
						cbRexportRemiseLData.horizontalAlignment = GridData.END;
						cbRexportRemise.setLayoutData(cbRexportRemiseLData);
						cbRexportRemise.setText("Ré-exporter");
					}
					{
						laespace1 = new Label(groupDateReglements, SWT.NONE);
						GridData laespace1LData = new GridData();
						laespace1LData.widthHint = 0;
						laespace1LData.heightHint = 10;
						laespace1.setLayoutData(laespace1LData);
					}
					{
						cbDocumentLie = new Button(groupDateReglements, SWT.CHECK | SWT.LEFT);
						GridData cbDocumentLieLData = new GridData();
						cbDocumentLieLData.horizontalSpan = 4;
						cbDocumentLieLData.verticalAlignment = GridData.FILL;
						cbDocumentLie.setLayoutData(cbDocumentLieLData);
						cbDocumentLie.setText("Exporter les documents liés aux règlements");
					}
					{
						cbPointages2 = new Button(groupDateReglements, SWT.CHECK | SWT.LEFT);
						GridData cbPointages2LData = new GridData();
						cbPointages2LData.horizontalSpan = 4;
						cbPointages2.setLayoutData(cbPointages2LData);
						cbPointages2.setText("Exporter les pointages");
					}
					{
						btnExporterReglement = new Button(groupDateReglements, SWT.PUSH | SWT.CENTER);
						GridData btnExporterReglementLData = new GridData();
						btnExporterReglementLData.horizontalSpan = 5;
						btnExporterReglementLData.grabExcessHorizontalSpace = true;
						btnExporterReglementLData.horizontalAlignment = GridData.CENTER;
						btnExporterReglementLData.widthHint = 160;
						btnExporterReglementLData.heightHint = 29;
						btnExporterReglement.setLayoutData(btnExporterReglementLData);
						btnExporterReglement.setText("Exporter les règlements");
					}
				}
				{
					cbReExport = new Button(groupTout, SWT.CHECK | SWT.LEFT);
					GridData button1LData = new GridData();
					button1LData.verticalAlignment = GridData.BEGINNING;
					button1LData.grabExcessHorizontalSpace = true;
					button1LData.horizontalSpan = 2;
					button1LData.horizontalAlignment = GridData.END;
					cbReExport.setLayoutData(button1LData);
					cbReExport.setText("Tout Ré-exporter");
				}
				{
					laListeDocument = new Label(groupTout, SWT.NONE);
					GridData laListeDocumentLData = new GridData();
					laListeDocumentLData.horizontalAlignment = GridData.END;
					laListeDocumentLData.grabExcessHorizontalSpace = true;
					laListeDocument.setLayoutData(laListeDocumentLData);
					laListeDocument.setText("Etat d'exportation des documents");
//					laListeDocument.setFont(SWTResourceManager.getFont("Tahoma", 8, 1, false, false));
				}
				{
					btnListeDoc = new Button(groupTout, SWT.PUSH | SWT.CENTER);
					GridData btnListeDocLData = new GridData();
					btnListeDocLData.horizontalAlignment = GridData.END;
					btnListeDocLData.widthHint = 181;
					btnListeDocLData.heightHint = 28;
					btnListeDoc.setLayoutData(btnListeDocLData);
					btnListeDoc.setText("Liste documents déjà exportés");
					btnListeDoc.setSize(181, 28);
				}
				{
					GridData paBtn1LData = new GridData();
					paBtn1LData.horizontalAlignment = GridData.CENTER;
					paBtn1LData.grabExcessHorizontalSpace = true;
					paBtn1LData.horizontalSpan = 2;
					paBtn1LData.verticalAlignment = GridData.END;
					paBtn1LData.heightHint = 34;
					paBtn1LData.grabExcessVerticalSpace = true;
					paBtn1LData.widthHint = 203;
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
					btnImprimerLData.horizontalAlignment = GridData.FILL;
					btnImprimerLData.grabExcessHorizontalSpace = true;
					btnImprimerLData.heightHint = 28;
					paBtn1.getBtnFermer().setLayoutData(btnFermerLData);
					paBtn1.getBtnImprimer().setLayoutData(btnImprimerLData);
					paBtn1.getBtnFermer().setText("< Précédent");
					paBtn1.getBtnFermer().setSize(82, 28);
					paBtn1.getBtnImprimer().setSize(50, 28);
				}

			}	
			this.layout();
			pack();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	



	

	
	public Button getCbReExport() {
		return cbReExport;
	}
	
	public PaBtnReduit getPaBtn1() {
		return paBtn1;
	}
	


	public Group getGroupDate() {
		return groupDateVentes;
	}


	public Button getCbAccepAcompte() {
		return cbAccepAcompte;
	}
	
	public Button getCbAcceptReglement() {
		return cbAcceptReglement;
	}
	
	public Button getCbAcceptRemise() {
		return cbAcceptRemise;
	}
	
	public Button getCbAcceptFacture() {
		return cbAcceptFacture;
	}
	
	public Button getCbAcceptAvoir() {
		return cbAcceptAvoir;
	}


//	public Button getCbGereRelation() {
//		return cbGereRelation;
//	}

	public Group getGroupDateReglements() {
		return groupDateReglements;
	}

	public DateTime getTfDATEFINVentes() {
		return tfDATEFINVentes;
	}

	public Label getLaDATEFINVentes() {
		return laDATEFINVentes;
	}

	public DateTime getTfDATEDEBVentes() {
		return tfDATEDEBVentes;
	}

	public Label getLaDATEDEBVentes() {
		return laDATEDEBVentes;
	}

	public DateTime getTfDATEFINReglement() {
		return tfDATEFINReglement;
	}

	public Label getLaDATEFINReglement() {
		return laDATEFINReglement;
	}

	public DateTime getTfDATEDEBReglement() {
		return tfDATEDEBReglement;
	}

	public Label getLaDATEDEBReglement() {
		return laDATEDEBReglement;
	}
	
	public Button getBtnListeDoc() {
		return btnListeDoc;
	}
	
	public Button getCbRexportFacture() {
		return cbRexportFacture;
	}
	
	public Button getCbRexportAvoirs() {
		return cbRexportAvoirs;
	}
	
	public Button getCbReExportAcompte() {
		return cbReExportAcompte;
	}
	
	public Button getCbRexportReglement() {
		return cbRexportReglement;
	}
	
	public Button getCbRexportRemise() {
		return cbRexportRemise;
	}

	public ExpandBar getExpandBarOption() {
		return expandBarOption;
	}


	
	public Button getCbPointages() {
		return cbPointages;
	}
	
	public Button getBtnExporterVentes() {
		return btnExporterVentes;
	}
	
	public Button getCbDocumentLie() {
		return cbDocumentLie;
	}
	
	public Button getCbPointages2() {
		return cbPointages2;
	}
	
	public Button getBtnExporterReglement() {
		return btnExporterReglement;
	}

	public Label getLaListeDocument() {
		return laListeDocument;
	}

	public Button getCbReglementLie() {
		return cbReglementLie;
	}

	public Group getGroupDateVentes() {
		return groupDateVentes;
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

	public Button getCbRexportApporteurs() {
		return cbRexportApporteurs;
	}

	public Button getCbAcceptApporteur() {
		return cbAcceptApporteur;
	}
}
