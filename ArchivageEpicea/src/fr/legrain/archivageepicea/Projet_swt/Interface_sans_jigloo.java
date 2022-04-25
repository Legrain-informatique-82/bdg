package fr.legrain.archivageepicea.Projet_swt;

import java.awt.Dimension;
import java.awt.Toolkit;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.nebula.widgets.cdatetime.CDateTime;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.PlatformUI;

public class Interface_sans_jigloo extends Composite{

	final private static String pathFileFirebird = "C:/Archivage_epicea/archepi";
	static public makeCompositeLeft test = new makeCompositeLeft();
	//public makeCompositeLeft test = new makeCompositeLeft();
	private static 	int counter =0;

	//private  Shell shell = null;
	private Table historique = null;
	private Combo combo = null;
	private Menu mainMenu = null;
	private Menu fileMenu = null;
	private MenuItem fileItem = null;
	private MenuItem newFileItem = null;
	private Composite compositeLeft =null;
	private Composite compositeMenu=null;
	private Composite compositeRight =null;
	private Composite compositeLeftTop =null;
	private Composite compositeLeftBottom = null;
	private Group compositeLeftbas=null;
	private CDateTime periodeDebut = null;
	private CDateTime periodeFin   = null;
	private Text textCompteDebut   = null;
	private Text textCompteFin 	   = null;
	private Text textMontantDebut  = null;
	private Text textMontantFin    = null;
	private Text textRefernceDebut = null;
	private Text textRefernceFin   = null;
	private Text textLibelle       = null;
	private CTabFolder tabFolder   = null; 
	private Button buttonRecherche = null;
	private GridData gridDatabas1=null;
	private Button	effacer = null; 
	private Button  masquer=null;
	private Button btnMenu = null;
	private Composite compositeLeftmenu= null;
	public Interface_sans_jigloo(Composite parent, int style) {
		super(parent, style);
		creeInterfaceSWT();
	}

//	public Interface(Shell shellPrincipal) {
//		creeInterfaceSWT(shellPrincipal);
//	}

	public void creeInterfaceSWT() {
		try {
//			Display display = new Display();
//			shell = new Shell(shellPrincipal);
//			shell.setSize(300, 100);
//			shell.setText("Outil recherche");

			/**
			 * pour Menus
			 */
//			mainMenu=new Menu(shellPrincipal,SWT.BAR);
//			shellPrincipal.setMenuBar(mainMenu);
//			fileItem=new MenuItem(mainMenu,SWT.CASCADE); 
//			fileItem.setText("Importer &F"); 
//			fileMenu=new Menu(shellPrincipal,SWT.DROP_DOWN); 
//			fileItem.setMenu(fileMenu);
//			newFileItem=new MenuItem(fileMenu,SWT.CASCADE); 
//			newFileItem.setText("Importer une Ã©puration Epicea &C");
			 
			//Dimension wndSize = theKit.getScreenSize();
			
			
			
			RowLayout rowlayout=new RowLayout(); 
			rowlayout.pack=true;
			rowlayout.wrap=false;

			rowlayout.marginWidth=20;  
			rowlayout.marginHeight=20;
			rowlayout.spacing=10;
			this.setLayout(rowlayout);
			
			
			compositeLeft=new Composite(this,SWT.BORDER);
			compositeRight=new Composite(this,SWT.BORDER);

			//RowData rowdata0=new RowData(400,25);
			//compositeMenu.setLayoutData(rowdata0);
			
			RowData rowdata1=new RowData(400,1000);
			//RowData rowdata1=new RowData(400,775);
			compositeLeft.setLayoutData(rowdata1);
//			RowData rowdata2=new RowData(1100,900);
			RowData rowdata2=new RowData(800,775);
			compositeRight.setLayoutData(rowdata2);

			
			//PlatformUI.getWorkbench().getDisplay().get
			
			
			
			
			compositeLeftmenu = new Composite(compositeLeft,SWT.BORDER);
			FormLayout formlayouta= new FormLayout ();
			formlayouta.marginHeight = 10;
			formlayouta.marginWidth = 10;
			compositeLeft.setLayout (formlayouta);
			
			FormData formDataLeftMenu=new FormData(); 
			formDataLeftMenu.width=375;     
			formDataLeftMenu.height=25;     
			compositeLeftmenu.setLayoutData(formDataLeftMenu);
			
		
			GridData gridData22=new GridData();
			gridData22.horizontalSpan = 1;    
			gridData22.verticalSpan=1;        
			gridData22.horizontalAlignment = GridData.CENTER; 
			gridData22.verticalAlignment = GridData.FILL; 
			// Bouton menu
			btnMenu =new Button(compositeLeftmenu, SWT.PUSH);
			btnMenu.setText("Importer une Epuration Epicea &C");
			btnMenu.setBounds(0, 0, 375, 25);
			btnMenu.setLayoutData(gridData22);
			
			
			
			
			
			
			compositeLeftTop=new Composite(compositeLeft,SWT.BORDER);
			FormLayout formlayout= new FormLayout ();
			formlayout.marginHeight = 10;
			formlayout.marginWidth = 10;
			compositeLeft.setLayout (formlayout);
//			// Bouton menu
//			btnMenu =new Button(compositeLeftTop, SWT.PUSH);
//			btnMenu.setText("Importer une Ã©puration Epicea &C");
//			btnMenu.setBounds(0, 0, 400, 25);

			FormData formDataLeftTop=new FormData();
			formDataLeftTop.top= new FormAttachment(compositeLeftmenu,10,SWT.BOTTOM);
			formDataLeftTop.width=375;    
			formDataLeftTop.height=75;     
		
			
			
			compositeLeftTop.setLayoutData(formDataLeftTop);  

			
			compositeLeftBottom = new Composite(compositeLeft,SWT.BORDER);
			// ici taille du carrÃ© de recherche largeur,hauteur
			FormData formDataLeftBottom=new FormData(375,250); 
			formDataLeftBottom.top= new FormAttachment(compositeLeftTop,10,SWT.BOTTOM);
			compositeLeftBottom.setLayoutData(formDataLeftBottom);   

			GridLayout gridLayoutTop = new GridLayout();
			gridLayoutTop.numColumns = 3;
			gridLayoutTop.horizontalSpacing=10;
			gridLayoutTop.marginHeight = 20;
			gridLayoutTop.marginWidth = 15;
			compositeLeftTop.setLayout(gridLayoutTop);

			GridData gridData1=new GridData();
			gridData1.horizontalSpan = 2;    
			gridData1.verticalSpan=1;        
			gridData1.horizontalAlignment = GridData.CENTER; 
			gridData1.verticalAlignment = GridData.FILL;
			Label libelleTitel=new Label(compositeLeftTop,SWT.WRAP);
			libelleTitel.setText("Choisir votre dossier:");
			libelleTitel.setLayoutData(gridData1);

			GridData gridData2=new GridData();
			gridData2.horizontalSpan = 1;    
			gridData2.verticalSpan=1;        
			gridData2.horizontalAlignment = GridData.CENTER; 
			gridData2.verticalAlignment = GridData.FILL; 
			
			

			combo=new Combo(compositeLeftTop,SWT.NONE|SWT.READ_ONLY);
			//				x,y,largeur,hauteur
			combo.setBounds(10,40,100,25);
			combo.setLayoutData(gridData2);
			
		

			GridLayout gridLayoutBottom = new GridLayout();
			
			gridLayoutBottom.makeColumnsEqualWidth=false;
			gridLayoutBottom.numColumns = 6;
			gridLayoutBottom.horizontalSpacing=6;

			compositeLeftBottom.setLayout(gridLayoutBottom);

			GridData gridData3=new GridData();
			gridData3.grabExcessHorizontalSpace = true;
			gridData3.horizontalSpan = 6;    
			gridData3.verticalSpan=1;        
			gridData3.horizontalAlignment = GridData.CENTER; 
			gridData3.verticalAlignment = GridData.CENTER; 
			
			Label libelleTitel1=new Label(compositeLeftBottom,SWT.WRAP);
			libelleTitel1.setText("Vos critères de recherche : ");
			libelleTitel1.setLayoutData(gridData3);
//
//			test.makeLeft_libelle(compositeLeftBottom, "De la date :");
//			periodeDebut = test.makeLeft_date(compositeLeftBottom, 2);
//			test.makeLeft_libelle(compositeLeftBottom, "Au:");
//			periodeFin = test.makeLeft_date(compositeLeftBottom, 2);
//
//			test.makeLeft_libelle(compositeLeftBottom, "Du Compte:");
//			textCompteDebut = test.makeLeft_text(compositeLeftBottom, 2);
//
//			test.makeLeft_libelle(compositeLeftBottom, "Au Compte:");
//			textCompteFin = test.makeLeft_text(compositeLeftBottom, 2);
//
//			test.makeLeft_libelle(compositeLeftBottom, "Du Montant:");
//			textMontantDebut = test.makeLeft_text(compositeLeftBottom, 2);
//
//			test.makeLeft_libelle(compositeLeftBottom, "Au Montant:");
//			textMontantFin = test.makeLeft_text(compositeLeftBottom, 2);
//
//			test.makeLeft_libelle(compositeLeftBottom, "Référence Début:");
//			textRefernceDebut = test.makeLeft_text(compositeLeftBottom, 2);
//	
//			test.makeLeft_libelle(compositeLeftBottom, "Référence Fin");
//			textRefernceFin = test.makeLeft_text(compositeLeftBottom, 2);
//
//			test.makeLeft_libelle(compositeLeftBottom, "Libellé:");
//			textLibelle = test.makeLeft_text(compositeLeftBottom, 5);

		

			tabFolder = new CTabFolder(compositeRight, SWT.BORDER);
		
			//	tabFolder.setBounds(5, 5, 1090, 890);
			tabFolder.setBounds(5, 5, 780, 800);
		//	buttonRecherche = test.makeLeft_button(compositeLeftBottom);

			
		
			
			
			
			
			GridData gridData4=new GridData();
			gridData4.horizontalSpan = 4;    
			gridData4.verticalSpan=1;        
			gridData4.horizontalAlignment = GridData.CENTER; 
			gridData4.verticalAlignment = GridData.FILL; 
			

			compositeLeftbas=new Group(compositeLeft,SWT.BORDER);
			compositeLeftbas.setText("Votre historique de recherche");
			// 375 300
			FormData formDataLeftBottombis =new FormData(375,250); 
			formDataLeftBottombis.top= new FormAttachment(compositeLeftBottom,10,SWT.BOTTOM);

			GridLayout gridLayoutBottombis = new GridLayout();
			gridLayoutBottombis.makeColumnsEqualWidth=true;
			gridLayoutBottombis.numColumns = 4;
			gridLayoutBottombis.horizontalSpacing=6;
			gridLayoutBottombis.marginHeight = 100;
			gridLayoutBottombis.marginWidth = 15;
			compositeLeftbas.setLayoutData(formDataLeftBottombis);
			gridDatabas1=new GridData();
			gridDatabas1.horizontalSpan = 1;    
			gridDatabas1.verticalSpan=1;        
			gridDatabas1.horizontalAlignment = GridData.CENTER; 
			gridDatabas1.verticalAlignment = GridData.FILL; 

			historique=new Table(compositeLeftbas,SWT.NONE|SWT.READ_ONLY|SWT.V_SCROLL|SWT.H_SCROLL);

			historique.setBounds(0,50,375,130);
			historique.setLayoutData(gridDatabas1);

			effacer=new Button(compositeLeftbas,SWT.None);

			effacer.setText("effacer l'historique" );

			effacer.setBounds(10, 200, 200, 25);
			effacer.setLayoutData(gridDatabas1);


			masquer=new Button(compositeLeftbas,SWT.CHECK);
			masquer.setText("masquer/afficher l'historique" );
			masquer.setBounds(10, 20, 300, 25);
			masquer.setLayoutData(gridDatabas1);


		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public Button getMasquer() {
		return masquer;
	}

	public void setMasquer(Button masquer) {
		this.masquer = masquer;
	}

	public Button getEffacer() {
		return effacer;
	}

	public void setEffacer(Button effacer) {
		this.effacer = effacer;
	}

//	public void affiche() {
//		if(shell!=null) {
//			shell.pack();
//			shell.open();}
//	}

	public Combo getCombo() {
		return combo;
	}

	public void setCombo(Combo combo) {
		this.combo = combo;
	}

	public Composite getCompositeLeftBottom() {
		return compositeLeftBottom;
	}

	public void setCompositeLeftBottom(Composite compositeLeftBottom) {
		this.compositeLeftBottom = compositeLeftBottom;
	}

	public Menu getMainMenu() {
		return mainMenu;
	}

	public void setMainMenu(Menu mainMenu) {
		this.mainMenu = mainMenu;
	}

	public Menu getFileMenu() {
		return fileMenu;
	}

	public void setFileMenu(Menu fileMenu) {
		this.fileMenu = fileMenu;
	}

	public MenuItem getFileItem() {
		return fileItem;
	}

	public void setFileItem(MenuItem fileItem) {
		this.fileItem = fileItem;
	}

	public MenuItem getNewFileItem() {
		return newFileItem;
	}

	public void setNewFileItem(MenuItem newFileItem) {
		this.newFileItem = newFileItem;
	}

	public Composite getCompositeLeft() {
		return compositeLeft;
	}

	public void setCompositeLeft(Composite compositeLeft) {
		this.compositeLeft = compositeLeft;
	}

//	public Shell getShell() {
//		return shell;
//	}
//
//	public void setShell(Shell shell) {
//		this.shell = shell;
//	}

	public Composite getCompositeRight() {
		return compositeRight;
	}

	public void setCompositeRight(Composite compositeRight) {
		this.compositeRight = compositeRight;
	}

	public Button getButtonRecherche() {
		return buttonRecherche;
	}

	public void setButtonRecherche(Button buttonRecherche) {
		this.buttonRecherche = buttonRecherche;
	}

	public static int getCounter() {
		return counter;
	}

	public static void setCounter(int counter) {
		Interface_sans_jigloo.counter = counter;
	}

	public CDateTime getPeriodeDebut() {
		return periodeDebut;
	}

	public void setPeriodeDebut(CDateTime periodeDebut) {
		this.periodeDebut = periodeDebut;
	}

	public CDateTime getPeriodeFin() {
		return periodeFin;
	}

	public void setPeriodeFin(CDateTime periodeFin) {
		this.periodeFin = periodeFin;
	}

	public Text getTextCompteDebut() {
		return textCompteDebut;
	}

	public void setTextCompteDebut(Text textCompteDebut) {
		this.textCompteDebut = textCompteDebut;
	}

	public Text getTextCompteFin() {
		return textCompteFin;
	}

	public void setTextCompteFin(Text textCompteFin) {
		this.textCompteFin = textCompteFin;
	}

	public Text getTextMontantDebut() {
		return textMontantDebut;
	}

	public void setTextMontantDebut(Text textMontantDebut) {
		this.textMontantDebut = textMontantDebut;
	}

	public Text getTextMontantFin() {
		return textMontantFin;
	}

	public void setTextMontantFin(Text textMontantFin) {
		this.textMontantFin = textMontantFin;
	}

	public Text getTextRefernceDebut() {
		return textRefernceDebut;
	}

	public void setTextRefernceDebut(Text textRefernceDebut) {
		this.textRefernceDebut = textRefernceDebut;
	}

	public Text getTextRefernceFin() {
		return textRefernceFin;
	}

	public void setTextRefernceFin(Text textRefernceFin) {
		this.textRefernceFin = textRefernceFin;
	}

	public Text getTextLibelle() {
		return textLibelle;
	}

	public void setTextLibelle(Text textLibelle) {
		this.textLibelle = textLibelle;
	}

	public CTabFolder getTabFolder() {
		return tabFolder;
	}

	public void setTabFolder(CTabFolder tabFolder) {
		this.tabFolder = tabFolder;
	}

	public static String getPathFileFirebird() {
		return pathFileFirebird;
	}

	public Composite getCompositeLeftTop() {
		return compositeLeftTop;
	}

	public void setCompositeLeftTop(Composite compositeLeftTop) {
		this.compositeLeftTop = compositeLeftTop;
	}

	public Table getHistorique() {
		return historique;
	}

	public void setHistorique(Table historique) {
		this.historique = historique;
	}

	public Composite getCompositeLeftbas() {
		return compositeLeftbas;
	}
//
//	public void setCompositeLeftbas(Composite compositeLeftbas) {
//		this.compositeLeftbas = compositeLeftbas;
//	}

	public String toString() {
		return gridDatabas1.toString();
	}

	public boolean equals(Object obj) {
		return gridDatabas1.equals(obj);
	}

	public int hashCode() {
		return gridDatabas1.hashCode();
	}

	public GridData getGridDatabas1() {
		return gridDatabas1;
	}

	public void setGridDatabas1(GridData gridDatabas1) {
		this.gridDatabas1 = gridDatabas1;
	}

	public Button getBtnMenu() {
		return btnMenu;
	}

	public void setBtnMenu(Button btnMenu) {
		this.btnMenu = btnMenu;
	}

}
