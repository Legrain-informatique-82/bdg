package fr.legrain.archivageepicea.Projet_swt;

import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;

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
public class SwtDialog extends org.eclipse.swt.widgets.Dialog {

	private Shell dialogShell;
	private Composite compositeFileReport;
	private Composite compositeButtonValider;
	private Group groupEdtionDefaut;
	private Button buttonValiderImprimer;
	private Composite composite1;
	private Button RadioEditionPersonelle;
	private Button RadioEditionDefautComplexe;
	private Button RadioEditionDefautSimple;
	private Button buttonAnnulerImprimer;
	private ScrolledComposite scrolledCompositeFileReport;
	private Group groupEditionPersonnelle;

	/**
	* Auto-generated main method to display this 
	* org.eclipse.swt.widgets.Dialog inside a new Shell.
	*/
//	public static void main(String[] args) {
//		try {
//			Display display = Display.getDefault();
//			Shell shell = new Shell(display);
//			SwtDialog inst = new SwtDialog(shell, SWT.NULL);
//			inst.open();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}

	public SwtDialog(Shell parent, int style) {
		super(parent, style);
		initGUI();
	}
	
	public void open() {
		try {
			dialogShell.setLocation(getParent().toDisplay(100, 100));
			dialogShell.open();
			Display display = dialogShell.getDisplay();
			while (!dialogShell.isDisposed()) {
				if (!display.readAndDispatch())
					display.sleep();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void initGUI() {
		try {
			Shell parent = getParent();
			dialogShell = new Shell(parent, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);

			GridLayout dialogShellLayout = new GridLayout();
			dialogShellLayout.makeColumnsEqualWidth = true;
			dialogShell.setLayout(dialogShellLayout);
			dialogShell.layout();
			dialogShell.pack();			
			dialogShell.setSize(478, 420);
			dialogShell.setText("Choix File Edition");
			{
				compositeFileReport = new Composite(dialogShell, SWT.BORDER);
				GridLayout compositeFileReportLayout = new GridLayout();
				compositeFileReportLayout.makeColumnsEqualWidth = true;
				GridData compositeFileReportLData = new GridData();
				compositeFileReportLData.grabExcessHorizontalSpace = true;
				compositeFileReportLData.grabExcessVerticalSpace = true;
				compositeFileReportLData.verticalAlignment = GridData.FILL;
				compositeFileReportLData.horizontalAlignment = GridData.FILL;
				compositeFileReport.setLayoutData(compositeFileReportLData);
				compositeFileReport.setLayout(compositeFileReportLayout);
				{
					groupEdtionDefaut = new Group(compositeFileReport, SWT.NONE);
					GridLayout groupEdtionDefautLayout = new GridLayout();
					groupEdtionDefautLayout.makeColumnsEqualWidth = true;
					groupEdtionDefaut.setLayout(groupEdtionDefautLayout);
					GridData groupEdtionDefautLData = new GridData();
					groupEdtionDefautLData.heightHint = 95;
					groupEdtionDefautLData.horizontalAlignment = GridData.FILL;
					groupEdtionDefautLData.grabExcessHorizontalSpace = true;
					groupEdtionDefaut.setLayoutData(groupEdtionDefautLData);
					groupEdtionDefaut.setText("Edition Choix");
					{
						RadioEditionDefautSimple = new Button(groupEdtionDefaut, SWT.RADIO | SWT.LEFT);
						GridData RadioEditionDefautSimpleLData = new GridData();
						RadioEditionDefautSimpleLData.horizontalAlignment = GridData.FILL;
						RadioEditionDefautSimpleLData.verticalAlignment = GridData.FILL;
						RadioEditionDefautSimpleLData.grabExcessVerticalSpace = true;
						RadioEditionDefautSimple.setLayoutData(RadioEditionDefautSimpleLData);
						RadioEditionDefautSimple.setText("Edition Defaut Simple");
					}
					{
						RadioEditionDefautComplexe = new Button(groupEdtionDefaut, SWT.RADIO | SWT.LEFT);
						GridData RadioEditionDefautComplexeLData = new GridData();
						RadioEditionDefautComplexeLData.grabExcessVerticalSpace = true;
						RadioEditionDefautComplexeLData.verticalAlignment = GridData.FILL;
						RadioEditionDefautComplexe.setLayoutData(RadioEditionDefautComplexeLData);
						RadioEditionDefautComplexe.setText("Edition Defaut Complexe");
					}
					{
						RadioEditionPersonelle = new Button(groupEdtionDefaut, SWT.RADIO | SWT.LEFT);
						GridData RadioEditionPersonelleLData = new GridData();
						RadioEditionPersonelleLData.verticalAlignment = GridData.FILL;
						RadioEditionPersonelleLData.grabExcessVerticalSpace = true;
						RadioEditionPersonelleLData.horizontalAlignment = GridData.FILL;
						RadioEditionPersonelle.setLayoutData(RadioEditionPersonelleLData);
						RadioEditionPersonelle.setText("Edition Personelle");
					}
				}
				{
					groupEditionPersonnelle = new Group(compositeFileReport, SWT.NONE);
					GridLayout groupEditionPersonnelleLayout = new GridLayout();
					groupEditionPersonnelleLayout.makeColumnsEqualWidth = true;
					groupEditionPersonnelle.setLayout(groupEditionPersonnelleLayout);
					GridData groupEditionPersonnelleLData = new GridData();
					groupEditionPersonnelleLData.grabExcessHorizontalSpace = true;
					groupEditionPersonnelleLData.horizontalAlignment = GridData.FILL;
					groupEditionPersonnelleLData.verticalAlignment = GridData.FILL;
					groupEditionPersonnelleLData.grabExcessVerticalSpace = true;
					groupEditionPersonnelle.setLayoutData(groupEditionPersonnelleLData);
					groupEditionPersonnelle.setText("Edition Personnelle");
					{
						GridData scrolledCompositeFileReportLData = new GridData();
						scrolledCompositeFileReportLData.horizontalAlignment = GridData.FILL;
						scrolledCompositeFileReportLData.grabExcessHorizontalSpace = true;
						scrolledCompositeFileReportLData.verticalAlignment = GridData.FILL;
						scrolledCompositeFileReportLData.grabExcessVerticalSpace = true;
						scrolledCompositeFileReport = new ScrolledComposite(groupEditionPersonnelle, SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER);
						scrolledCompositeFileReport.setLayoutData(scrolledCompositeFileReportLData);
						scrolledCompositeFileReport.setEnabled(true);
						scrolledCompositeFileReport.setAlwaysShowScrollBars(true);
						scrolledCompositeFileReport.setDragDetect(true);
						scrolledCompositeFileReport.setExpandHorizontal(true);
						scrolledCompositeFileReport.setExpandVertical(true);
						{
							composite1 = new Composite(scrolledCompositeFileReport, SWT.NONE);
							GridLayout composite1Layout = new GridLayout();
							composite1Layout.makeColumnsEqualWidth = true;
							composite1.setLayout(composite1Layout);
							scrolledCompositeFileReport.setContent(composite1);
							composite1.setBounds(12, 12, 572, 437);
							composite1.setDragDetect(false);
						}
					}
				}
			}
			{
				compositeButtonValider = new Composite(dialogShell, SWT.BORDER);
				GridLayout compositeButtonValiderLayout = new GridLayout();
				compositeButtonValiderLayout.makeColumnsEqualWidth = true;
				compositeButtonValiderLayout.numColumns = 2;
				GridData compositeButtonValiderLData = new GridData();
				compositeButtonValiderLData.horizontalAlignment = GridData.FILL;
				compositeButtonValiderLData.verticalAlignment = GridData.BEGINNING;
				compositeButtonValiderLData.heightHint = 48;
				compositeButtonValider.setLayoutData(compositeButtonValiderLData);
				compositeButtonValider.setLayout(compositeButtonValiderLayout);
				{
					buttonValiderImprimer = new Button(compositeButtonValider, SWT.PUSH | SWT.CENTER);
					GridData buttonValiderImprimerLData = new GridData();
					buttonValiderImprimerLData.grabExcessVerticalSpace = true;
					buttonValiderImprimerLData.verticalAlignment = GridData.FILL;
					buttonValiderImprimerLData.horizontalAlignment = GridData.FILL;
					buttonValiderImprimerLData.grabExcessHorizontalSpace = true;
					buttonValiderImprimer.setLayoutData(buttonValiderImprimerLData);
					buttonValiderImprimer.setText("Valider Imprimer");
				}
				{
					buttonAnnulerImprimer = new Button(compositeButtonValider, SWT.PUSH | SWT.CENTER);
					GridData buttonAnnulerImprimerLData = new GridData();
					buttonAnnulerImprimerLData.horizontalAlignment = GridData.FILL;
					buttonAnnulerImprimerLData.grabExcessHorizontalSpace = true;
					buttonAnnulerImprimerLData.verticalAlignment = GridData.FILL;
					buttonAnnulerImprimerLData.grabExcessVerticalSpace = true;
					buttonAnnulerImprimer.setLayoutData(buttonAnnulerImprimerLData);
					buttonAnnulerImprimer.setText("Annuler Imprimer");
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public ScrolledComposite getScrolledCompositeFileReport(Composite parent) {
		return scrolledCompositeFileReport;
	}
		
	public Composite getCompositeFileReport(Composite parent) {
		return compositeFileReport;
	}
	
	public Composite getCompositeButtonValider(Composite parent) {
		return compositeButtonValider;
	}
	
	public Group getGroupEdtionDefaut(Composite parent) {
		return groupEdtionDefaut;
	}
	
	public Group getGroupEditionPersonnelle(Composite parent) {
		return groupEditionPersonnelle;
	}
	
	public Button getButtonValiderImprimer(Composite parent) {
		return buttonValiderImprimer;
	}
	
	public Button getButtonAnnulerImprimer(Composite parent) {
		return buttonAnnulerImprimer;
	}
	
	public Button getRadioEditionDefautSimple(Composite parent) {
		return RadioEditionDefautSimple;
	}
	
	public Button getRadioEditionDefautComplexe(Composite parent) {
		return RadioEditionDefautComplexe;
	}
	
	public Button getRadioEditionPersonelle(Composite parent) {
		return RadioEditionPersonelle;
	}

	public Shell getDialogShell() {
		return dialogShell;
	}

	public void setDialogShell(Shell dialogShell) {
		this.dialogShell = dialogShell;
	}

	public Composite getCompositeFileReport() {
		return compositeFileReport;
	}

	public void setCompositeFileReport(Composite compositeFileReport) {
		this.compositeFileReport = compositeFileReport;
	}

	public Composite getCompositeButtonValider() {
		return compositeButtonValider;
	}

	public void setCompositeButtonValider(Composite compositeButtonValider) {
		this.compositeButtonValider = compositeButtonValider;
	}

	public Group getGroupEdtionDefaut() {
		return groupEdtionDefaut;
	}

	public void setGroupEdtionDefaut(Group groupEdtionDefaut) {
		this.groupEdtionDefaut = groupEdtionDefaut;
	}

	public Button getButtonValiderImprimer() {
		return buttonValiderImprimer;
	}

	public void setButtonValiderImprimer(Button buttonValiderImprimer) {
		this.buttonValiderImprimer = buttonValiderImprimer;
	}

	public Button getRadioEditionPersonelle() {
		return RadioEditionPersonelle;
	}

	public void setRadioEditionPersonelle(Button radioEditionPersonelle) {
		RadioEditionPersonelle = radioEditionPersonelle;
	}

	public Button getRadioEditionDefautComplexe() {
		return RadioEditionDefautComplexe;
	}

	public void setRadioEditionDefautComplexe(Button radioEditionDefautComplexe) {
		RadioEditionDefautComplexe = radioEditionDefautComplexe;
	}

	public Button getRadioEditionDefautSimple() {
		return RadioEditionDefautSimple;
	}

	public void setRadioEditionDefautSimple(Button radioEditionDefautSimple) {
		RadioEditionDefautSimple = radioEditionDefautSimple;
	}

	public Button getButtonAnnulerImprimer() {
		return buttonAnnulerImprimer;
	}

	public void setButtonAnnulerImprimer(Button buttonAnnulerImprimer) {
		this.buttonAnnulerImprimer = buttonAnnulerImprimer;
	}

	public ScrolledComposite getScrolledCompositeFileReport() {
		return scrolledCompositeFileReport;
	}

	public void setScrolledCompositeFileReport(
			ScrolledComposite scrolledCompositeFileReport) {
		this.scrolledCompositeFileReport = scrolledCompositeFileReport;
	}

	public Group getGroupEditionPersonnelle() {
		return groupEditionPersonnelle;
	}

	public void setGroupEditionPersonnelle(Group groupEditionPersonnelle) {
		this.groupEditionPersonnelle = groupEditionPersonnelle;
	}

	public Composite getComposite1() {
		return composite1;
	}

	public void setComposite1(Composite composite1) {
		this.composite1 = composite1;
	}

}
