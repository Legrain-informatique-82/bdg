package fr.legrain.archivageepicea.Projet_swt;
import com.cloudgarden.resource.SWTResourceManager;

import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.nebula.widgets.cdatetime.CDT;
import org.eclipse.swt.nebula.widgets.cdatetime.CDateTime;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;

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
public class Interface extends org.eclipse.swt.widgets.Composite {

	{
		//Register as a resource user - SWTResourceManager will
		//handle the obtaining and disposing of resources
		SWTResourceManager.registerResourceUser(this);
	}
	
	private Composite compositeLeft;
	private Composite compositeRight;
	private Composite compositeLeftmenu;
	private Label labelPeriodeDebut;
	private CTabFolder tabFolder;
	private Button effacer;
	private Table historique;
	private Button masquer;
	private Button buttonRecherche;
	private Group compositeLeftbas;
	private Text textLibelle;
	private Label labelLibelle;
	private Text textRefernceFin;
	private Label labelRefernceFin;
	private Text textRefernceDebut;
	private Label labelRefernceDebut;
	private Text textMontantFin;
	private Label labeltMontantFin;
	private Text textMontantDebut;
	private Label labelMontantDebut;
	private Text textCompteFin;
	private Label labelCompteFin;
	private Text textCompteDebut;
	private Label labelCompteDebut;
	private CDateTime periodeFin;
	private Label labelPeriodeFin;
	private CDateTime periodeDebut;
	private Label libelleTitel1;
	private Combo combo;
	private Label libelleTitel;
	private Composite compositeLeftBottom;
	private Composite compositeRecherche;
	private Composite compositeLeftTop;
	private Button btnMenu;

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
		Interface inst = new Interface(shell, SWT.NULL);
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

	public Interface(org.eclipse.swt.widgets.Composite parent, int style) {
		super(parent, style);
		initGUI();
	}

	private void initGUI() {
		try {
			GridLayout thisLayout = new GridLayout();
			thisLayout.numColumns = 2;
			this.setLayout(thisLayout);
			this.setSize(907, 849);
			{
				compositeRight = new Composite(this, SWT.BORDER);
				GridLayout compositeRightLayout = new GridLayout();
				compositeRight.setLayout(compositeRightLayout);
				GridData compositeRightLData = new GridData();
				compositeRightLData.verticalAlignment = GridData.FILL;
				compositeRightLData.grabExcessVerticalSpace = true;
				compositeRightLData.widthHint = 412;
				compositeRight.setLayoutData(compositeRightLData);
				{
					compositeLeftmenu = new Composite(compositeRight, SWT.BORDER);
					GridLayout compositeLeftmenuLayout = new GridLayout();
					compositeLeftmenuLayout.makeColumnsEqualWidth = true;
					GridData compositeLeftmenuLData = new GridData();
					compositeLeftmenuLData.heightHint = 37;
					compositeLeftmenuLData.horizontalAlignment = GridData.FILL;
					compositeLeftmenuLData.grabExcessHorizontalSpace = true;
					compositeLeftmenu.setLayoutData(compositeLeftmenuLData);
					compositeLeftmenu.setLayout(compositeLeftmenuLayout);
					{
						btnMenu = new Button(compositeLeftmenu, SWT.PUSH | SWT.CENTER);
						GridData btnMenuLData = new GridData();
						btnMenuLData.horizontalAlignment = GridData.FILL;
						btnMenuLData.grabExcessHorizontalSpace = true;
						btnMenuLData.grabExcessVerticalSpace = true;
						btnMenuLData.verticalAlignment = GridData.FILL;
						btnMenu.setLayoutData(btnMenuLData);
						btnMenu.setText("Importer un dossier");
					}
				}
				{
					compositeLeftTop = new Composite(compositeRight, SWT.BORDER);
					GridLayout compositeLeftTopLayout = new GridLayout();
					compositeLeftTopLayout.makeColumnsEqualWidth = true;
					compositeLeftTopLayout.numColumns = 2;
					GridData compositeLeftTopLData = new GridData();
					compositeLeftTopLData.grabExcessHorizontalSpace = true;
					compositeLeftTopLData.horizontalAlignment = GridData.FILL;
					compositeLeftTopLData.verticalAlignment = GridData.BEGINNING;
					compositeLeftTopLData.heightHint = 40;
					compositeLeftTop.setLayoutData(compositeLeftTopLData);
					compositeLeftTop.setLayout(compositeLeftTopLayout);
					{
						libelleTitel = new Label(compositeLeftTop, SWT.NONE);
						libelleTitel.setText("Choisir votre dossier : ");
					}
					{
						combo = new Combo(compositeLeftTop, SWT.READ_ONLY);
						GridData comboLData = new GridData();
						comboLData.horizontalAlignment = GridData.FILL;
						combo.setLayoutData(comboLData);
						combo.setText("votre choix");
					}
				}
				{
					compositeRecherche = new Composite(compositeRight, SWT.BORDER);
					GridLayout compositeRechercheLayout = new GridLayout();
					compositeRechercheLayout.makeColumnsEqualWidth = true;
					GridData compositeRechercheLData = new GridData();
					compositeRechercheLData.horizontalAlignment = GridData.FILL;
					compositeRechercheLData.grabExcessVerticalSpace = true;
					compositeRechercheLData.verticalAlignment = GridData.FILL;
					compositeRechercheLData.grabExcessHorizontalSpace = true;
					compositeRecherche.setLayoutData(compositeRechercheLData);
					compositeRecherche.setLayout(compositeRechercheLayout);
					{
						compositeLeftBottom = new Composite(compositeRecherche, SWT.BORDER);
						GridLayout compositeLeftBottomLayout = new GridLayout();
						compositeLeftBottomLayout.numColumns = 6;
						GridData compositeLeftBottomLData = new GridData();
						compositeLeftBottomLData.heightHint = 203;
						compositeLeftBottomLData.verticalAlignment = GridData.END;
						compositeLeftBottomLData.horizontalAlignment = GridData.FILL;
						compositeLeftBottomLData.grabExcessHorizontalSpace = true;
						compositeLeftBottom.setLayoutData(compositeLeftBottomLData);
						compositeLeftBottom.setLayout(compositeLeftBottomLayout);
						{
							libelleTitel1 = new Label(compositeLeftBottom, SWT.NONE);
							GridData libelleTitel1LData = new GridData();
							libelleTitel1LData.horizontalSpan = 6;
							libelleTitel1LData.horizontalAlignment = GridData.CENTER;
							libelleTitel1LData.grabExcessHorizontalSpace = true;
							libelleTitel1.setLayoutData(libelleTitel1LData);
							libelleTitel1.setText("Vos criteres de recherche : ");
							libelleTitel1.setFont(SWTResourceManager.getFont("Tahoma", 8, 1, false, false));
						}
						{
							labelPeriodeDebut = new Label(compositeLeftBottom, SWT.NONE);
							GridData labelPeriodeDebutLData = new GridData();
							labelPeriodeDebutLData.horizontalAlignment = GridData.CENTER;
							labelPeriodeDebut.setText("De la date :");
						}
						{
							GridData periodeDebutLData = new GridData();
							periodeDebutLData.horizontalAlignment = GridData.FILL;
							periodeDebutLData.horizontalSpan = 2;
							periodeDebutLData.verticalAlignment = GridData.FILL;
							periodeDebut = new CDateTime(compositeLeftBottom, CDT.BORDER | CDT.DROP_DOWN);
							periodeDebut.setLayoutData(periodeDebutLData);
							
						}
						{
							labelPeriodeFin = new Label(compositeLeftBottom, SWT.NONE);
							GridData labelPeriodeFinLData = new GridData();
							labelPeriodeFinLData.horizontalSpan = 2;
							GridData labelPeriodeFinLData1 = new GridData();
							labelPeriodeFinLData1.grabExcessHorizontalSpace = true;
							labelPeriodeFin.setText("À :");
						}
						{
							GridData periodeFinLData = new GridData();
							periodeFinLData.horizontalSpan = 2;
							periodeFinLData.verticalAlignment = GridData.FILL;
							periodeFinLData.horizontalAlignment = GridData.FILL;
							periodeFin = new CDateTime(compositeLeftBottom, CDT.BORDER | CDT.DROP_DOWN);
							periodeFin.setLayoutData(periodeFinLData);
						}
						{
							labelCompteDebut = new Label(compositeLeftBottom, SWT.NONE);
							labelCompteDebut.setText("Du Compte : ");
						}
						{
							GridData textCompteDebutLData = new GridData();
							textCompteDebutLData.horizontalSpan = 2;
							textCompteDebutLData.horizontalAlignment = GridData.FILL;
							textCompteDebutLData.heightHint = 13;
							textCompteDebut = new Text(compositeLeftBottom, SWT.BORDER);
							textCompteDebut.setLayoutData(textCompteDebutLData);
						}
						{
							labelCompteFin = new Label(compositeLeftBottom, SWT.NONE);
							labelCompteFin.setText("Au :");
						}
						{
							GridData textCompteFinLData = new GridData();
							textCompteFinLData.horizontalSpan = 2;
							textCompteFinLData.heightHint = 13;
							textCompteFinLData.horizontalAlignment = GridData.FILL;
							textCompteFin = new Text(compositeLeftBottom, SWT.BORDER);
							textCompteFin.setLayoutData(textCompteFinLData);
						}
						{
							labelMontantDebut = new Label(compositeLeftBottom, SWT.NONE);
							labelMontantDebut.setText("Du Montant : ");
						}
						{
							GridData textMontantDebutLData = new GridData();
							textMontantDebutLData.horizontalSpan = 2;
							textMontantDebutLData.horizontalAlignment = GridData.FILL;
							textMontantDebutLData.heightHint = 13;
							textMontantDebut = new Text(compositeLeftBottom, SWT.BORDER);
							textMontantDebut.setLayoutData(textMontantDebutLData);
						}
						{
							labeltMontantFin = new Label(compositeLeftBottom, SWT.NONE);
							GridData labeltMontantFinLData = new GridData();
							labeltMontantFinLData.horizontalAlignment = GridData.FILL;
							labeltMontantFin.setText("Au :");
						}
						{
							GridData textMontantFinLData = new GridData();
							textMontantFinLData.horizontalSpan = 2;
							textMontantFinLData.heightHint = 13;
							textMontantFinLData.horizontalAlignment = GridData.FILL;
							textMontantFin = new Text(compositeLeftBottom, SWT.BORDER);
							textMontantFin.setLayoutData(textMontantFinLData);
						}
						{
							labelRefernceDebut = new Label(compositeLeftBottom, SWT.NONE);
							labelRefernceDebut.setText("Reference Debut : ");
						}
						{
							GridData textRefernceDebutLData = new GridData();
							textRefernceDebutLData.horizontalSpan = 2;
							textRefernceDebutLData.horizontalAlignment = GridData.FILL;
							textRefernceDebut = new Text(compositeLeftBottom, SWT.BORDER);
							textRefernceDebut.setLayoutData(textRefernceDebutLData);
						}
						{
							labelRefernceFin = new Label(compositeLeftBottom, SWT.NONE);
							labelRefernceFin.setText("À :");
						}
						{
							GridData textRefernceFinLData = new GridData();
							textRefernceFinLData.horizontalSpan = 2;
							textRefernceFinLData.heightHint = 13;
							textRefernceFinLData.horizontalAlignment = GridData.FILL;
							textRefernceFin = new Text(compositeLeftBottom, SWT.BORDER);
							textRefernceFin.setLayoutData(textRefernceFinLData);
						}
						{
							labelLibelle = new Label(compositeLeftBottom, SWT.NONE);
							labelLibelle.setText("Libelle : ");
						}
						{
							textLibelle = new Text(compositeLeftBottom, SWT.BORDER);
							GridData textLibelleLData = new GridData();
							textLibelleLData.horizontalSpan = 5;
							textLibelleLData.heightHint = 13;
							textLibelleLData.horizontalAlignment = GridData.FILL;
							textLibelle.setLayoutData(textLibelleLData);
						}
						{
							buttonRecherche = new Button(compositeLeftBottom, SWT.PUSH | SWT.CENTER);
							GridData buttonRechercheLData = new GridData();
							buttonRechercheLData.horizontalSpan = 6;
							buttonRechercheLData.horizontalAlignment = GridData.CENTER;
							buttonRechercheLData.widthHint = 83;
							buttonRechercheLData.heightHint = 25;
							buttonRecherche.setLayoutData(buttonRechercheLData);
							buttonRecherche.setText("Recherche");
						}
					}
					{
						compositeLeftbas = new Group(compositeRecherche, SWT.NONE);
						GridLayout compositeLeftbasLayout = new GridLayout();
						compositeLeftbasLayout.makeColumnsEqualWidth = true;
						compositeLeftbas.setLayout(compositeLeftbasLayout);
						GridData compositeLeftbasLData = new GridData();
						compositeLeftbasLData.grabExcessVerticalSpace = true;
						compositeLeftbasLData.verticalAlignment = GridData.FILL;
						compositeLeftbasLData.horizontalAlignment = GridData.FILL;
						compositeLeftbas.setLayoutData(compositeLeftbasLData);
						compositeLeftbas.setText("Votre historique de recherche");
						compositeLeftbas.setFont(SWTResourceManager.getFont("Tahoma", 8, 1, false, false));
						{
							masquer = new Button(compositeLeftbas, SWT.CHECK | SWT.CENTER);
							masquer.setText("masquer/afficher l'historique");
						}
						{
							GridData historiqueLData = new GridData();
							historiqueLData.heightHint = 163;
							historiqueLData.grabExcessHorizontalSpace = true;
							historiqueLData.horizontalAlignment = GridData.FILL;
							historique = new Table(compositeLeftbas, SWT.SINGLE | SWT.FULL_SELECTION | SWT.H_SCROLL| SWT.V_SCROLL| SWT.BORDER);
							historique.setLayoutData(historiqueLData);
						}
						{
							effacer = new Button(compositeLeftbas, SWT.PUSH | SWT.CENTER);
							GridData effacerLData = new GridData();
							effacer.setLayoutData(effacerLData);
							effacer.setText("effacer l'historique");
						}
					}
				}
			}
			{
				compositeLeft = new Composite(this, SWT.BORDER);
				GridLayout compositeLeftLayout = new GridLayout();
				compositeLeftLayout.makeColumnsEqualWidth = true;
				compositeLeft.setLayout(compositeLeftLayout);
				GridData compositeLeftLData = new GridData();
				compositeLeftLData.grabExcessHorizontalSpace = true;
				compositeLeftLData.grabExcessVerticalSpace = true;
				compositeLeftLData.verticalAlignment = GridData.FILL;
				compositeLeftLData.horizontalAlignment = GridData.FILL;
				compositeLeft.setLayoutData(compositeLeftLData);
				{
					tabFolder = new CTabFolder(compositeLeft, SWT.NONE);
					GridData tabFolderLData = new GridData();
					tabFolderLData.grabExcessHorizontalSpace = true;
					tabFolderLData.horizontalAlignment = GridData.FILL;
					tabFolderLData.grabExcessVerticalSpace = true;
					tabFolderLData.verticalAlignment = GridData.FILL;
					tabFolder.setLayoutData(tabFolderLData);
					tabFolder.setSelection(0);
				}
			}
			this.layout();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public Composite getCompositeLeft(Composite parent) {
		return compositeLeft;
	}
	
	public Composite getCompositeRight(Composite parent) {
		return compositeRight;
	}
	
	public Composite getCompositeLeftmenu(Composite parent) {
		return compositeLeftmenu;
	}
	
	public Button getBtnMenu(Composite parent) {
		return btnMenu;
	}
	
	public Composite getCompositeLeftTop(Composite parent) {
		return compositeLeftTop;
	}
	
	public Composite getCompositeRecherche(Composite parent) {
		return compositeRecherche;
	}
	
	public Composite getCompositeLeftBottom(Composite parent) {
		return compositeLeftBottom;
	}
	
	public Composite getCompositeLeftbas(Composite parent) {
		return compositeLeftbas;
	}
	
	public Label getLibelleTitel(Composite parent) {
		return libelleTitel;
	}
	
	public Combo getCombo(Composite parent) {
		return combo;
	}
	
	public Label getLibelleTitel1(Composite parent) {
		return libelleTitel1;
	}
	
	public Label getLabelPeriodeDebut(Composite parent) {
		return labelPeriodeDebut;
	}
	
	public CDateTime getPeriodeDebut(Composite parent) {
		return periodeDebut;
	}
	
	public Label getLabelCompteDebut(Composite parent) {
		return labelCompteDebut;
	}
	
	public Text getTextCompteDebut(Composite parent) {
		return textCompteDebut;
	}
	
	public Label getLabelCompteFin(Composite parent) {
		return labelCompteFin;
	}
	
	public Label getLabelMontantDebut(Composite parent) {
		return labelMontantDebut;
	}
	
	public Text getTextMontantDebut(Composite parent) {
		return textMontantDebut;
	}
	
	public Text getTextMontantFin(Composite parent) {
		return textMontantFin;
	}
	
	public Text getTextRefernceDebut(Composite parent) {
		return textRefernceDebut;
	}
	
	public Text getTextRefernceFin(Composite parent) {
		return textRefernceFin;
	}
	
	public Text getTextLibelle(Composite parent) {
		return textLibelle;
	}
	
	public Button getButtonRecherche(Composite parent) {
		return buttonRecherche;
	}
	
	public Button getMasquer(Composite parent) {
		return masquer;
	}
	
	public Table getHistorique(Composite parent) {
		return historique;
	}
	
	public Button getEffacer(Composite parent) {
		return effacer;
	}
	
	public CTabFolder getTabFolder(Composite parent) {
		return tabFolder;
	}

	public Composite getCompositeLeft() {
		return compositeLeft;
	}

	public Composite getCompositeRight() {
		return compositeRight;
	}

	public Composite getCompositeLeftmenu() {
		return compositeLeftmenu;
	}

	public Label getLabelPeriodeDebut() {
		return labelPeriodeDebut;
	}

	public CTabFolder getTabFolder() {
		return tabFolder;
	}

	public Button getEffacer() {
		return effacer;
	}

	public Table getHistorique() {
		return historique;
	}

	public Button getMasquer() {
		return masquer;
	}

	public Button getButtonRecherche() {
		return buttonRecherche;
	}

	public Group getCompositeLeftbas() {
		return compositeLeftbas;
	}

	public Text getTextLibelle() {
		return textLibelle;
	}

	public Label getLabelLibelle() {
		return labelLibelle;
	}

	public Text getTextRefernceFin() {
		return textRefernceFin;
	}

	public Label getLabelRefernceFin() {
		return labelRefernceFin;
	}

	public Text getTextRefernceDebut() {
		return textRefernceDebut;
	}

	public Label getLabelRefernceDebut() {
		return labelRefernceDebut;
	}

	public Text getTextMontantFin() {
		return textMontantFin;
	}

	public Label getLabeltMontantFin() {
		return labeltMontantFin;
	}

	public Text getTextMontantDebut() {
		return textMontantDebut;
	}

	public Label getLabelMontantDebut() {
		return labelMontantDebut;
	}

	public Text getTextCompteFin() {
		return textCompteFin;
	}

	public Label getLabelCompteFin() {
		return labelCompteFin;
	}

	public Text getTextCompteDebut() {
		return textCompteDebut;
	}

	public Label getLabelCompteDebut() {
		return labelCompteDebut;
	}

	public CDateTime getPeriodeFin() {
		return periodeFin;
	}

	public Label getLabelPeriodeFin() {
		return labelPeriodeFin;
	}

	public CDateTime getPeriodeDebut() {
		return periodeDebut;
	}

	public Label getLibelleTitel1() {
		return libelleTitel1;
	}

	public Combo getCombo() {
		return combo;
	}

	public Label getLibelleTitel() {
		return libelleTitel;
	}

	public Composite getCompositeLeftBottom() {
		return compositeLeftBottom;
	}

	public Composite getCompositeRecherche() {
		return compositeRecherche;
	}

	public Composite getCompositeLeftTop() {
		return compositeLeftTop;
	}

	public Button getBtnMenu() {
		return btnMenu;
	}
	


}
