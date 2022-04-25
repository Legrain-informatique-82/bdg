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

import com.cloudgarden.resource.SWTResourceManager;


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
public class PaExportationFactureTous extends org.eclipse.swt.widgets.Composite {

	{
		//Register as a resource user - SWTResourceManager will
		//handle the obtaining and disposing of resources
		SWTResourceManager.registerResourceUser(this);
	}
	
	private Button btnExporter;
	private Button btnDates;
	private Button btnReference;

	private Group groupTout;
	private Label laListeDocument;
	private Button CbApporteurs;
	private Button cbCentralisation;
	private Button cbPointages;
	private Composite composite2;
	private Button cbReglement;
	private Group groupReference;
	private Group groupDates;
	
	private ExpandBar expandBarOption;
//	private PaBtnReduit paBtn1;
	private Button btnFermer;
	private Label laMessage;
	private Composite composite1;
	
	private Button btnListeDoc;
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
		PaExportationFactureTous inst = new PaExportationFactureTous(shell, SWT.NULL);
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

	public PaExportationFactureTous(org.eclipse.swt.widgets.Composite parent, int style) {
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
				ld.widthHint = 869;
				ld.heightHint = 533;
				scrolledComposite.setLayoutData(ld);
				scrolledComposite.setExpandHorizontal(true);
				scrolledComposite.setExpandVertical(true);
				{
					paEcran = new Composite(scrolledComposite, SWT.NONE);
					scrolledComposite.setContent(paEcran);
					GridLayout paEcranLayout = new GridLayout();
					paEcranLayout.numColumns = 4;
					paEcran.setLayout(paEcranLayout);
					{
						composite1 = new Composite(paEcran, SWT.BORDER);
						GridLayout composite1Layout = new GridLayout();
						composite1Layout.numColumns = 2;
						composite1Layout.verticalSpacing = 10;
						GridData composite1LData = new GridData();
						composite1LData.horizontalAlignment = GridData.CENTER;
						composite1LData.heightHint = 519;
						composite1LData.grabExcessVerticalSpace = true;
						composite1LData.grabExcessHorizontalSpace = true;
						composite1LData.widthHint = 865;
						composite1.setLayout(composite1Layout);
						composite1.setLayoutData(composite1LData);
						{
							groupTout = new Group(composite1, SWT.NONE);
							GridLayout group1LayoutRelation = new GridLayout();
							group1LayoutRelation.numColumns =2;
							groupTout.setLayout(group1LayoutRelation);
							GridData groupRelationLData = new GridData();
							groupRelationLData.horizontalAlignment = GridData.FILL;
							groupRelationLData.grabExcessHorizontalSpace = true;
							groupRelationLData.horizontalSpan = 2;
							groupRelationLData.heightHint = 268;
							groupRelationLData.grabExcessVerticalSpace = true;
							groupRelationLData.verticalAlignment = GridData.BEGINNING;
							groupTout.setLayoutData(groupRelationLData);
							{
								laMessage = new Label(groupTout, SWT.NONE);
								GridData laMessageLData = new GridData();
								laMessageLData.horizontalSpan = 2;
								laMessageLData.widthHint = 297;
								laMessageLData.heightHint = 29;
								laMessage.setLayoutData(laMessageLData);
								laMessage.setText("Exporte tous les documents non exportés");
								laMessage.setFont(SWTResourceManager.getFont("Tahoma", 8, 1, false, false));
							}
							{
								GridData composite2LData = new GridData();
								composite2LData.horizontalAlignment = GridData.FILL;
								composite2LData.verticalAlignment = GridData.FILL;
								composite2LData.grabExcessVerticalSpace = true;
								composite2LData.grabExcessHorizontalSpace = true;
								composite2 = new Composite(groupTout, SWT.NONE);
								GridLayout composite2Layout = new GridLayout();
								composite2Layout.numColumns = 5;
								composite2.setLayout(composite2Layout);
								composite2.setLayoutData(composite2LData);
								{
									cbReglement = new Button(composite2, SWT.CHECK | SWT.LEFT);
									GridData cbReglementLData = new GridData();
									cbReglementLData.horizontalSpan = 2;
									cbReglementLData.verticalAlignment = GridData.BEGINNING;
									cbReglementLData.verticalSpan = 6;
									cbReglement.setLayoutData(cbReglementLData);
									cbReglement.setText("Exporter avec les règlements");
								}
								{
									cbPointages = new Button(composite2, SWT.CHECK | SWT.LEFT);
									GridData cbReglementLiesLData = new GridData();
									cbReglementLiesLData.widthHint = 177;
									cbReglementLiesLData.heightHint = 17;
									cbReglementLiesLData.verticalAlignment = GridData.BEGINNING;
									cbPointages.setLayoutData(cbReglementLiesLData);
									cbPointages.setText("Exporter avec les pointages");
								}
								{
									cbCentralisation = new Button(composite2, SWT.CHECK | SWT.LEFT);
									GridData button1LData = new GridData();
									button1LData.heightHint = 17;
									button1LData.verticalAlignment = GridData.BEGINNING;
									button1LData.grabExcessHorizontalSpace = true;
									button1LData.horizontalAlignment = GridData.FILL;
									cbCentralisation.setLayoutData(button1LData);
									cbCentralisation.setText("Centraliser les écritures");
								}
								{
									CbApporteurs = new Button(composite2, SWT.CHECK | SWT.LEFT);
									GridData button1LData1 = new GridData();
									button1LData1.widthHint = 278;
									button1LData1.heightHint = 18;
									CbApporteurs.setLayoutData(button1LData1);
									CbApporteurs.setText("Exporter les Apporteurs");
								}
								{
									GridData expandBarLData = new GridData();
									expandBarLData.heightHint = 161;
									expandBarLData.verticalAlignment = GridData.BEGINNING;
									expandBarLData.grabExcessVerticalSpace = true;
									expandBarLData.horizontalSpan = 4;
									expandBarLData.grabExcessHorizontalSpace = true;
									expandBarLData.widthHint = 462;
									expandBarOption = new ExpandBar(composite2, SWT.V_SCROLL);
									expandBarOption.setLayoutData(expandBarLData);
								}
								{
									btnExporter = new Button(composite2, SWT.PUSH | SWT.CENTER);
									GridData btnExporterLData = new GridData();
									btnExporterLData.widthHint = 160;
									btnExporterLData.grabExcessVerticalSpace = true;
									btnExporterLData.heightHint = 28;
									btnExporterLData.verticalAlignment = GridData.BEGINNING;
									btnExporter.setLayoutData(btnExporterLData);
									btnExporter.setText("Tout exporter");
									btnExporter.setSize(160, 28);
								}
							}
							groupTout.setText("Transférer tous les documents non transférés");
						}
					}
				}
			}

		      scrolledComposite.setMinSize(paEcran.computeSize(SWT.DEFAULT, SWT.DEFAULT));
			
		
			GridData paEcranLData = new GridData();
			paEcranLData.verticalAlignment = GridData.FILL;
			paEcranLData.grabExcessVerticalSpace = true;
			paEcranLData.horizontalAlignment = GridData.FILL;
			paEcranLData.grabExcessHorizontalSpace = true;

			{
				groupReference = new Group(composite1, SWT.NONE);
			GridLayout group1LayoutRelation = new GridLayout();
			group1LayoutRelation.numColumns =2;
			groupReference.setLayout(group1LayoutRelation);
			GridData groupRelationLData = new GridData();
			groupRelationLData.verticalAlignment = GridData.BEGINNING;
			groupRelationLData.horizontalAlignment = GridData.FILL;
			groupRelationLData.grabExcessHorizontalSpace = true;
			groupRelationLData.horizontalSpan = 2;
			groupRelationLData.grabExcessVerticalSpace = true;
			
			groupReference.setLayoutData(groupRelationLData);
			groupReference.setText("Exportation avec choix des documents par référence");
			groupReference.setFont(SWTResourceManager.getFont("Tahoma", 10, 1, false, false));
			groupReference.setForeground(SWTResourceManager.getColor(128, 128, 128));

			groupTout.setText("Gestion de l'exportation automatique");
			groupTout.setFont(SWTResourceManager.getFont("Tahoma", 10, 1, false, false));
			groupTout.setOrientation(SWT.HORIZONTAL);
			groupTout.setForeground(SWTResourceManager.getColor(128, 128, 128));
			{
				btnReference = new Button(groupReference, SWT.PUSH | SWT.CENTER);
				GridData btnReferenceLData = new GridData();
				btnReferenceLData.grabExcessHorizontalSpace = true;
				btnReferenceLData.widthHint = 160;
				btnReferenceLData.horizontalAlignment = GridData.CENTER;
				btnReferenceLData.grabExcessVerticalSpace = true;
				btnReferenceLData.heightHint = 28;
				btnReference.setLayoutData(btnReferenceLData);
				btnReference.setText("Exporter par Référence");
				btnReference.setSize(160, 28);
			}
			}
			{
				groupDates = new Group(composite1, SWT.NONE);
				GridLayout group1LayoutRelation = new GridLayout();
				group1LayoutRelation.numColumns =2;
				groupDates.setLayout(group1LayoutRelation);
				GridData groupRelationLData = new GridData();
				groupRelationLData.verticalAlignment = GridData.BEGINNING;
				groupRelationLData.horizontalAlignment = GridData.FILL;
				groupRelationLData.grabExcessHorizontalSpace = true;
				groupRelationLData.horizontalSpan = 2;
				groupDates.setLayoutData(groupRelationLData);
				groupDates.setFont(SWTResourceManager.getFont("Tahoma", 10, 1, false, false));
				groupDates.setText("Exportation avec choix des documents par date");
				groupDates.setForeground(SWTResourceManager.getColor(128, 128, 128));

			{
				btnDates = new Button(groupDates, SWT.PUSH | SWT.CENTER);
				GridData btnDatesLData = new GridData();
				btnDatesLData.grabExcessHorizontalSpace = true;
				btnDatesLData.widthHint = 160;
				btnDatesLData.heightHint = 28;
				btnDatesLData.horizontalAlignment = GridData.CENTER;
				btnDatesLData.grabExcessVerticalSpace = true;
				btnDates.setLayoutData(btnDatesLData);
				btnDates.setText("Exporter par Date");
				btnDates.setSize(160, 28);
			}
			}
			{
				laListeDocument = new Label(composite1, SWT.NONE);
				GridData laListeDocumentLData = new GridData();
				laListeDocumentLData.grabExcessHorizontalSpace = true;
				laListeDocumentLData.heightHint = 14;
				laListeDocumentLData.horizontalAlignment = GridData.END;
				laListeDocument.setLayoutData(laListeDocumentLData);
				laListeDocument.setText("Etat d'exportation des documents");
				laListeDocument.setFont(SWTResourceManager.getFont("Tahoma", 8, 1, false, false));
			}
			{
				btnListeDoc = new Button(composite1, SWT.PUSH | SWT.CENTER);
				GridData btnListeDocLData = new GridData();
				btnListeDocLData.verticalAlignment = GridData.BEGINNING;
				btnListeDocLData.horizontalAlignment = GridData.CENTER;
				btnListeDocLData.heightHint = 28;
				btnListeDocLData.widthHint = 174;
				btnListeDoc.setLayoutData(btnListeDocLData);
				btnListeDoc.setText("Liste des documents");
				btnListeDoc.setSize(174, 28);
			}
			{
				btnFermer = new Button(composite1, SWT.PUSH | SWT.CENTER);
				GridData btnDatesLData = new GridData();
				btnDatesLData.grabExcessHorizontalSpace = true;
				btnDatesLData.horizontalAlignment = GridData.CENTER;
				btnDatesLData.widthHint = 123;
				btnDatesLData.heightHint = 28;
				btnDatesLData.horizontalSpan = 3;
				btnDatesLData.verticalAlignment = GridData.END;
				btnDatesLData.grabExcessVerticalSpace = true;
				btnFermer.setLayoutData(btnDatesLData);
				btnFermer.setText("Fermer [F4]");
				btnFermer.setSize(123, 28);
			}

			thisLayout.numColumns = 2;


			pack();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	
	
	public Button getBtnExporter() {
		return btnExporter;
	}
	
	
	public Button getBtnReference() {
		return btnReference;
	}
	

	public Button getBtnDates() {
		return btnDates;
	}

	public Group getGroupTout() {
		return groupTout;
	}

	public Group getGroupReference() {
		return groupReference;
	}

	public Group getGroupDates() {
		return groupDates;
	}
	
	public Button getCbReglement() {
		return cbReglement;
	}

	public ExpandBar getExpandBarOption() {
		return expandBarOption;
	}


	
	public Label getLaMessage() {
		return laMessage;
	}

	public Button getBtnFermer() {
		return btnFermer;
	}

	public Button getBtnListeDoc() {
		return btnListeDoc;
	}

	public Button getCbPointages() {
		return cbPointages;
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

	public Button getCbApporteurs() {
		return CbApporteurs;
	}
}
