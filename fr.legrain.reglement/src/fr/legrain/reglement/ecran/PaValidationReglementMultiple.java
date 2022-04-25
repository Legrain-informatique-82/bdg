//package fr.legrain.reglement.ecran;
//
//import org.eclipse.swt.layout.FillLayout;
//import org.eclipse.swt.layout.FormLayout;
//import org.eclipse.swt.layout.GridData;
//import org.eclipse.swt.layout.GridLayout;
//import org.eclipse.swt.widgets.Button;
//import org.eclipse.swt.widgets.Composite;
//import org.eclipse.swt.widgets.Display;
//import org.eclipse.swt.widgets.Group;
//import org.eclipse.swt.widgets.Label;
//import org.eclipse.swt.widgets.Shell;
//import org.eclipse.swt.widgets.Table;
//import org.eclipse.swt.graphics.Point;
//import org.eclipse.swt.graphics.Rectangle;
//import org.eclipse.swt.SWT;
//
//
///**
//* This code was edited or generated using CloudGarden's Jigloo
//* SWT/Swing GUI Builder, which is free for non-commercial
//* use. If Jigloo is being used commercially (ie, by a corporation,
//* company or business for any purpose whatever) then you
//* should purchase a license for each developer using Jigloo.
//* Please visit www.cloudgarden.com for details.
//* Use of Jigloo implies acceptance of these licensing terms.
//* A COMMERCIAL LICENSE HAS NOT BEEN PURCHASED FOR
//* THIS MACHINE, SO JIGLOO OR THIS CODE CANNOT BE USED
//* LEGALLY FOR ANY CORPORATE OR COMMERCIAL PURPOSE.
//*/
//public class PaValidationReglementMultiple extends org.eclipse.swt.widgets.Composite {
////	private Group groupReglementAValider;
////	private Group groupReglementNonAffecte;
////	private Table tableReglementSession;
////	private Button btnGenrerAvoir;
////	private Label laMessage;
////	private Button btnSupprimerReglement;
////	private Table tableReglementNonAffecte;
////	private Button btnEnregistrer;
////	private Button btnPrecedent;
////	private Button btnFermer;
////	private Composite paBtn;
////
////	/**
////	* Auto-generated main method to display this 
////	* org.eclipse.swt.widgets.Composite inside a new Shell.
////	*/
////	public static void main(String[] args) {
////		showGUI();
////	}
////	
////	/**
////	* Overriding checkSubclass allows this class to extend org.eclipse.swt.widgets.Composite
////	*/	
////	protected void checkSubclass() {
////	}
////	
////	/**
////	* Auto-generated method to display this 
////	* org.eclipse.swt.widgets.Composite inside a new Shell.
////	*/
////	public static void showGUI() {
////		Display display = Display.getDefault();
////		Shell shell = new Shell(display);
////		PaValidationReglementMultiple inst = new PaValidationReglementMultiple(shell, SWT.NULL);
////		Point size = inst.getSize();
////		shell.setLayout(new FillLayout());
////		shell.layout();
////		if(size.x == 0 && size.y == 0) {
////			inst.pack();
////			shell.pack();
////		} else {
////			Rectangle shellBounds = shell.computeTrim(0, 0, size.x, size.y);
////			shell.setSize(shellBounds.width, shellBounds.height);
////		}
////		shell.open();
////		while (!shell.isDisposed()) {
////			if (!display.readAndDispatch())
////				display.sleep();
////		}
////	}
////
//	public PaValidationReglementMultiple(org.eclipse.swt.widgets.Composite parent, int style) {
//		super(parent, style);
////		initGUI();
//	}
////
////	private void initGUI() {
////		try {
////			GridLayout thisLayout = new GridLayout();
////			thisLayout.makeColumnsEqualWidth = true;
////			this.setLayout(thisLayout);
////			{
////				groupReglementAValider = new Group(this, SWT.NONE);
////				GridLayout groupReglementAValiderLayout = new GridLayout();
////				groupReglementAValiderLayout.numColumns = 2;
////				groupReglementAValider.setLayout(groupReglementAValiderLayout);
////				GridData groupReglementAValiderLData = new GridData();
////				groupReglementAValiderLData.verticalAlignment = GridData.FILL;
////				groupReglementAValiderLData.grabExcessHorizontalSpace = true;
////				groupReglementAValiderLData.grabExcessVerticalSpace = true;
////				groupReglementAValiderLData.horizontalAlignment = GridData.FILL;
////				groupReglementAValider.setLayoutData(groupReglementAValiderLData);
////				groupReglementAValider.setText("Règlement de la session en attente de validation");
////				{
////					GridData tableReglementSessionLData = new GridData();
////					tableReglementSessionLData.horizontalAlignment = GridData.FILL;
////					tableReglementSessionLData.grabExcessHorizontalSpace = true;
////					tableReglementSessionLData.verticalAlignment = GridData.FILL;
////					tableReglementSessionLData.grabExcessVerticalSpace = true;
////					tableReglementSession = new Table(groupReglementAValider, SWT.BORDER);
////					tableReglementSession.setLayoutData(tableReglementSessionLData);
////				}
////				{
////					btnSupprimerReglement = new Button(groupReglementAValider, SWT.PUSH | SWT.CENTER);
////					GridData btnSupprimerReglementLData = new GridData();
////					btnSupprimerReglementLData.verticalAlignment = GridData.BEGINNING;
////					btnSupprimerReglementLData.widthHint = 107;
////					btnSupprimerReglementLData.heightHint = 25;
////					btnSupprimerReglement.setLayoutData(btnSupprimerReglementLData);
////					btnSupprimerReglement.setText("Supprimer");
////				}
////			}
////			{
////				groupReglementNonAffecte = new Group(this, SWT.NONE);
////				GridLayout groupReglementNonAffecteLayout = new GridLayout();
////				groupReglementNonAffecteLayout.numColumns = 2;
////				groupReglementNonAffecte.setLayout(groupReglementNonAffecteLayout);
////				GridData groupReglementNonAffecteLData = new GridData();
////				groupReglementNonAffecteLData.verticalAlignment = GridData.FILL;
////				groupReglementNonAffecteLData.horizontalAlignment = GridData.FILL;
////				groupReglementNonAffecteLData.grabExcessVerticalSpace = true;
////				groupReglementNonAffecte.setLayoutData(groupReglementNonAffecteLData);
////				groupReglementNonAffecte.setText("Règlement non totalement affecté");}
////			{
////				GridData tableReglementNonAffecteLData = new GridData();
////				tableReglementNonAffecteLData.verticalAlignment = GridData.FILL;
////				tableReglementNonAffecteLData.horizontalAlignment = GridData.FILL;
////				tableReglementNonAffecteLData.grabExcessVerticalSpace = true;
////				tableReglementNonAffecteLData.grabExcessHorizontalSpace = true;
////				tableReglementNonAffecte = new Table(groupReglementNonAffecte, SWT.BORDER);
////				tableReglementNonAffecte.setLayoutData(tableReglementNonAffecteLData);
////			}
////			{
////				btnGenrerAvoir = new Button(groupReglementNonAffecte, SWT.PUSH | SWT.CENTER);
////				GridData btnGenrerAvoirLData = new GridData();
////				btnGenrerAvoirLData.verticalAlignment = GridData.BEGINNING;
////				btnGenrerAvoirLData.widthHint = 107;
////				btnGenrerAvoirLData.heightHint = 25;
////				btnGenrerAvoir.setLayoutData(btnGenrerAvoirLData);
////				btnGenrerAvoir.setText("Générer un avoir");
////			}
////			{
////				GridData paBtnLData = new GridData();
////				paBtnLData.heightHint = 36;
////				paBtnLData.verticalAlignment = GridData.END;
////				paBtnLData.grabExcessHorizontalSpace = true;
////				paBtnLData.horizontalAlignment = GridData.CENTER;
////				paBtnLData.widthHint = 254;
////				paBtn = new Composite(this, SWT.NONE);
////				GridLayout paBtnLayout = new GridLayout();
////				paBtnLayout.numColumns = 3;
////				paBtn.setLayout(paBtnLayout);
////				paBtn.setLayoutData(paBtnLData);
////				{
////					btnFermer = new Button(paBtn, SWT.PUSH | SWT.CENTER);
////					GridData btnFermerLData = new GridData();
////					btnFermer.setLayoutData(btnFermerLData);
////					btnFermer.setText("Fermer");
////				}
////				{
////					btnPrecedent = new Button(paBtn, SWT.PUSH | SWT.CENTER);
////					GridData btnPrecedentLData = new GridData();
////					btnPrecedent.setLayoutData(btnPrecedentLData);
////					btnPrecedent.setText("Précédent");
////				}
////				{
////					btnEnregistrer = new Button(paBtn, SWT.PUSH | SWT.CENTER);
////					GridData btnEnregistrerLData = new GridData();
////					btnEnregistrer.setLayoutData(btnEnregistrerLData);
////					btnEnregistrer.setText("Valider la session");
////				}
////			}
////			{
////				GridData laMessageLData = new GridData();
////				laMessageLData.horizontalAlignment = GridData.FILL;
////				laMessageLData.grabExcessHorizontalSpace = true;
////				laMessage = new Label(this, SWT.NONE);
////				laMessage.setLayoutData(laMessageLData);
////			}
////			this.layout();
////			pack();
////		} catch (Exception e) {
////			e.printStackTrace();
////		}
////	}
////	
////	public Group getGroupReglementNonAffecte() {
////		return groupReglementNonAffecte;
////	}
////	
////	public Composite getPaBtn() {
////		return paBtn;
////	}
////	
////	public Button getBtnFermer() {
////		return btnFermer;
////	}
////	
////	public Button getBtnPrecedent() {
////		return btnPrecedent;
////	}
////	
////	public Button getBtnEnregistrer() {
////		return btnEnregistrer;
////	}
////	
////	public Table getTableReglementNonAffecte() {
////		return tableReglementNonAffecte;
////	}
////	
////	public Table getTableReglementSession() {
////		return tableReglementSession;
////	}
////	
////	public Button getBtnSupprimerReglement() {
////		return btnSupprimerReglement;
////	}
////	
////	public Button getBtnGenrerAvoir() {
////		return btnGenrerAvoir;
////	}
////	
////	public Label getLaMessage() {
////		return laMessage;
////	}
//
//}
